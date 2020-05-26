package com.vienna.jaray.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.vienna.jaray.model.ExcelPropertyModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class MultiLineHeadExcelListener<MultiLineHeadExcelModel> extends AnalysisEventListener<MultiLineHeadExcelModel> {
    private final List<MultiLineHeadExcelModel> multiLineHeadExcelModelList = new ArrayList();

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    @Override
    public void invoke(MultiLineHeadExcelModel model, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(model));
        multiLineHeadExcelModelList.add(model);
        // 实际数据量比较大时，rows里的数据可以存到一定量之后进行批量处理（比如存到数据库），
        // 然后清空列表，以防止内存占用过多造成OOM
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (multiLineHeadExcelModelList.size() >= BATCH_COUNT) {
            saveList();
            // 存储完成清理 list
            multiLineHeadExcelModelList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("read {} rows", multiLineHeadExcelModelList.size());
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveList();
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }
    }

    public List<MultiLineHeadExcelModel> getRows() {
        return multiLineHeadExcelModelList;
    }

    /**
     * 加上存储数据库
     */
    private void saveList() {
        log.info("{}条数据，开始存储数据库！", multiLineHeadExcelModelList.size());
        //userDAO.save((List<com.lk.model.User>) userList);
        log.info("存储数据库成功！");
    }
}
