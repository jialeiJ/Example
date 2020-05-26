package com.vienna.jaray.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.vienna.jaray.model.ExcelPropertyModel;
import com.vienna.jaray.write.ExcelWriteWhitEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.netty.http.server.HttpServerResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ImportExportController {

    @GetMapping("/")
    public String jumpImportExport(){
        return "importExport";
    }

    @PostMapping("/export")
    public void exportExcel(HttpServerResponse response){
        try (OutputStream out = new FileOutputStream("withHeadwhitEntity.xlsx");) {
            List<ExcelPropertyModel> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                ExcelPropertyModel item = new ExcelPropertyModel("name_" + i, "age_" + i, "email_" + i,
                        "address_" + i, "sax_" + i,"height_" + i,"last_" + i);
                data.add(item);
            }
            EasyExcel.write(out)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("员工信息").head(ExcelPropertyModel.class)
                    .doWrite(data);
        } catch (IOException e) {
            log.error("异常", e.getMessage());
        }
    }
}
