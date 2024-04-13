package com.vienna.jaray.service;

import com.vienna.jaray.config.ExecutorConfig;
import com.vienna.jaray.dao.entity.PaySalaryEntity;
import com.vienna.jaray.dao.mapper.PaySalaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Service
public class BatchInsertService {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    private final static Map<String, String> map = new HashMap<String, String>();

    /**
     * 测试多线程事务.
     * @param paySalaryEntityList
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveThread(List<PaySalaryEntity> paySalaryEntityList2) throws SQLException, IOException {
        long start = System.currentTimeMillis();
        ExecutorService service = ExecutorConfig.getThreadPool();
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSession currSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);

        List<PaySalaryEntity> paySalaryEntityList = new ArrayList<>(50000);
        List<String> allLines = Files.readAllLines(Paths.get("C:\\Users\\Jaray\\Desktop\\ADM_SXT_ACCT_SALA_DETAIL_DI_20230131.txt"));
        PaySalaryEntity paySalaryEntity = null;
        for (int k=0; k<allLines.size(); k++) {
            paySalaryEntity = buildObject(allLines.get(k));
            paySalaryEntityList.add(paySalaryEntity);
            if (paySalaryEntityList.size() % 50000 == 0 || k == (allLines.size() - 1)) {
//            if (paySalaryEntityList.size() == 1) {
                try {
                    List<Callable<Integer>> callableList  = new ArrayList<>();
                    List<List<PaySalaryEntity>> lists = ListUtils.partition(paySalaryEntityList, 10000);
                    for (int i =0; i<lists.size(); i++){
                        List<PaySalaryEntity> list  = lists.get(i);
                        int finalK = k;
                        // 开启批量处理模式 BATCH 、关闭自动提交事务 false
                        SqlSession threadSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
                        Callable<Integer> callable = () -> saveBatch(threadSqlSession, list, finalK);
                        callableList.add(callable);
                    }
                    //执行子线程
                    List<Future<Integer>> futures = service.invokeAll(callableList);
                    for (Future<Integer> future:futures) {
                        int result = future.get();
                        log.info("k= {}  result={}", k, result);
                        if (result<=0){
                            currSqlSession.rollback();
//                            return;
                        }
                    }
                    currSqlSession.commit();
                    paySalaryEntityList.clear();
                }catch (Exception e){
                    currSqlSession.rollback();
                    log.error("异常：{}", e);
                }
            }
        }

//        currSqlSession.commit();
        currSqlSession.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private Integer saveBatch(SqlSession sqlSession, List<PaySalaryEntity> list, int k) {
        try{
            PaySalaryMapper paySalaryMapper = sqlSession.getMapper(PaySalaryMapper.class);
            paySalaryMapper.saveBatch(list);
            sqlSession.commit();
        }catch (Exception e){
            sqlSession.rollback();
            log.error("入库异常：{}", e);
        }finally {
            sqlSession.close();
        }

        return 1;
    }

    private PaySalaryEntity buildObject (String row){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        //2、组装入库对象
        if (StringUtils.isEmpty(row)) {
            return null;
        }
        //分割每行的字段,使用0x01分割
        String[] row_split = row.split("\u0001");
        if (row_split.length < 33) {
            log.error("row length{}", row_split.length);
            return null;
        }
        Date jinbrq = null;
        Date dwOrigTranDt = null;
        Date tranDt = null;
        Date origTranDt = null;
        Date dataDt = null;
        try {
            if (!StringUtils.isEmpty(row_split[10])) {
                jinbrq = simpleDateFormat.parse(row_split[10]);
            }
            if (!StringUtils.isEmpty(row_split[13])) {
                dwOrigTranDt = simpleDateFormat.parse(row_split[13]);
            }
            if (!StringUtils.isEmpty(row_split[14])) {
                tranDt = simpleDateFormat.parse(row_split[14]);
            }
            if (!StringUtils.isEmpty(row_split[15])) {
                origTranDt = simpleDateFormat.parse(row_split[15]);
            }
            if (row_split.length > 32 && !StringUtils.isEmpty(row_split[32])) {
                dataDt = simpleDateFormat.parse(row_split[32]);
            }
        } catch (Exception e) {
//            log.error("日期转换异常：row={} e={}", row, e);
        }
        //组装对象
        PaySalaryEntity paySalaryEntity = new PaySalaryEntity();
        paySalaryEntity.setKehuzh(row_split[0]);
        paySalaryEntity.setName(row_split[1]);
        paySalaryEntity.setZhjhao(row_split[2]);
        paySalaryEntity.setDianhh(row_split[3]);
        paySalaryEntity.setYngyjg(row_split[4]);
        paySalaryEntity.setGnlisj(row_split[5]);
        paySalaryEntity.setWjigomc(row_split[6]);
        paySalaryEntity.setJigomc(row_split[7]);
        paySalaryEntity.setDanwbh(row_split[8]);
        paySalaryEntity.setDanwmc(row_split[9]);
        paySalaryEntity.setJinbrq(jinbrq);
        paySalaryEntity.setDwkehhao(row_split[11]);
        paySalaryEntity.setGuiydh(row_split[12]);
        paySalaryEntity.setDworigtrandt(dwOrigTranDt);
        paySalaryEntity.setTrandt(tranDt);
        paySalaryEntity.setOrigtrandt(origTranDt);
        paySalaryEntity.setJioyje(row_split[16]);
        paySalaryEntity.setYwzlbh(row_split[17]);
        paySalaryEntity.setYwzlbhmc(row_split[18]);
        paySalaryEntity.setNmgflag(row_split[19]);
        paySalaryEntity.setId(row_split[20]);
        paySalaryEntity.setGender(row_split[22]);
        paySalaryEntity.setAge(row_split[22]);
        paySalaryEntity.setKhzhlx(row_split[23]);
        paySalaryEntity.setDsyhbh(row_split[24]);
        paySalaryEntity.setChulzt(row_split[25]);
        paySalaryEntity.setZywwth(row_split[26]);
        paySalaryEntity.setZhyodm(row_split[27]);
        paySalaryEntity.setDliywh(row_split[28]);
        paySalaryEntity.setFarmerflag(row_split[29]);
        paySalaryEntity.setJio1gy(row_split[30]);
        paySalaryEntity.setDaifqd(row_split[31]);
        paySalaryEntity.setDatadt(dataDt);
        return paySalaryEntity;
    }

}
