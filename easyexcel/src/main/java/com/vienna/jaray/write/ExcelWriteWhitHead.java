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
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * 表头写入Excel
 * @author Administrator
 *
 */
@Slf4j
public class ExcelWriteWhitHead {
	
	public static void writeWhitHead() {
		try (OutputStream out = new FileOutputStream("withHead.xlsx");) {
	         List<List<String>> data = new ArrayList<>();
	         for (int i = 0; i < 100; i++) {
	            List<String> item = new ArrayList<>();
	            item.add("item0" + i);
	            item.add("item1" + i);
	            item.add("item2" + i);
	            data.add(item);
	         }
	         List<List<String>> head = new ArrayList<List<String>>();
	         List<String> headCoulumn1 = new ArrayList<String>();
	         List<String> headCoulumn2 = new ArrayList<String>();
	         List<String> headCoulumn3 = new ArrayList<String>();
	         headCoulumn1.add("第一列");
	         headCoulumn2.add("第二列");
	         headCoulumn3.add("第三列");
	         head.add(headCoulumn1);
	         head.add(headCoulumn2);
	         head.add(headCoulumn3);
			 EasyExcel.write(out).excelType(ExcelTypeEnum.XLSX)
					 .sheet("列表").table(1)
					 .head(head).doWrite(data);
	     }catch (IOException e){
			log.error("异常", e.getMessage());
		}
	}

}
