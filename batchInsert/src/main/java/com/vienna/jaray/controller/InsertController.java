package com.vienna.jaray.controller;

import com.vienna.jaray.dao.entity.PaySalaryEntity;
import com.vienna.jaray.dao.mapper.PaySalaryMapper;
import com.vienna.jaray.service.BatchInsertService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class InsertController {

    @Autowired
    private PaySalaryMapper paySalaryMapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private BatchInsertService batchInsertService;

    @GetMapping("/test")
    public void test() throws IOException, SQLException {
//        List<PaySalaryEntity> paySalaryEntityList = new ArrayList<>(500000);
//        PaySalaryEntity paySalaryEntity = null;
//        for (int i=0; i<50000; i++) {
//            paySalaryEntity = new PaySalaryEntity();
//            paySalaryEntity.setId(String.valueOf(i));
//            paySalaryEntityList.add(paySalaryEntity);
//        }
//        batchInsertService.testSaveThread(paySalaryEntityList);
        batchInsertService.saveThread(null);
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
