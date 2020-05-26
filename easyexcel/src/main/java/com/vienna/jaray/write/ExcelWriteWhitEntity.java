package com.vienna.jaray.write;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.vienna.jaray.model.ExcelPropertyModel;
import lombok.extern.slf4j.Slf4j;

/**
 * 实体类写入，添加ExcelProperty注解来生成表头，实体类数据作为Excel数据
 * @author Administrator
 *
 */
@Slf4j
public class ExcelWriteWhitEntity {

	public void writeWhitEntity() {
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
