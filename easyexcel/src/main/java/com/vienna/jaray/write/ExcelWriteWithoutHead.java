package com.vienna.jaray.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * 无表头写入Excel
 * @author Administrator
 *
 */
@Slf4j
public class ExcelWriteWithoutHead {
	
	public void writeWithoutHead() {
		
		try (OutputStream out = new FileOutputStream("withoutHead.xlsx");) {
	         List<List<String>> data = new ArrayList<>();
	         for (int i = 0; i < 100; i++) {
	            List<String> item = new ArrayList<>();
	            item.add("item0" + i);
	            item.add("item1" + i);
	            item.add("item2" + i);
	            data.add(item);
	         }
			 EasyExcel.write(out)
			 	.excelType(ExcelTypeEnum.XLSX)
			 	.sheet("员工信息")
			 	.doWrite(data);
		}catch (IOException e){
			log.error("异常", e.getMessage());
		}
		
	}
	
}
