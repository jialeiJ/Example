package com.vienna.jaray.write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.vienna.jaray.model.MultiLineHeadExcelModel;
import lombok.extern.slf4j.Slf4j;

/**
 * 多行复杂的表头写入Excel
 * @author Administrator
 *
 */
@Slf4j
public class ExcelWriteWithMultiHead {
	
	public void writeWithMultiHead() {
		try (OutputStream out = new FileOutputStream("withMultiHead.xlsx");) {
	         List<MultiLineHeadExcelModel> data = new ArrayList<>();
	         for (int i = 0; i < 100; i++) {
	            MultiLineHeadExcelModel item = new MultiLineHeadExcelModel();
	            item.setP1("p1" + i);
	            item.setP2("p2" + i);
	            item.setP3("p3" + i);
	            item.setP4("p4" + i);
	            item.setP5("p5" + i);
	            item.setP6("p6" + i);
	            item.setP7("p7" + i);
	            item.setP8("p8" + i);
	            item.setP9("p9" + i);
	            data.add(item);
	         }
			 EasyExcel.write(out, MultiLineHeadExcelModel.class)
			 	.excelType(ExcelTypeEnum.XLSX)
			 	.sheet("员工信息")
			 	.doWrite(data);
	      }catch (IOException e){
			log.error("异常", e.getMessage());
		}
	}

}
