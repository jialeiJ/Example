package com.vienna.jaray.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Table;

/**
 * sheet中添加多个表
 * @author Administrator
 *
 */
@Slf4j
public class ExcelWriteWithMultiTable {
	
	public void writeWithMultiTable() {
		try (OutputStream out = new FileOutputStream("withMultiTable.xlsx");) {
	         // 数据全是List<String> 无模型映射关系
	         List<List<String>> firstData = new ArrayList<>();
	         for (int i = 0; i < 5; i++) {
	            List<String> item = new ArrayList<>();
	            item.add("item0" + i);
	            item.add("item1" + i);
	            item.add("item2" + i);
				 firstData.add(item);
	         }

	         // 模型上有表头的注解
	         List<MultiLineHeadExcelModel> secondData = new ArrayList<>();
	         for (int i = 0; i < 5; i++) {
	            MultiLineHeadExcelModel item = new MultiLineHeadExcelModel();
	            item.p1 = "p1" + i;
	            item.p2 = "p2" + i;
	            item.p3 = "p3" + i;
	            item.p4 = "p4" + i;
	            item.p5 = "p5" + i;
	            item.p6 = "p6" + i;
	            item.p7 = "p7" + i;
	            item.p8 = "p8" + i;
	            item.p9 = "p9" + i;
	            secondData.add(item);
	         }

	         // 模型上没有注解，表头数据动态传入,此情况下模型field顺序与excel现实顺序一致
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

			 ExcelWriter excelWriter = EasyExcel.write(out).build();
             WriteSheet writeSheet = EasyExcel.writerSheet(1,"模板").build();
			 WriteTable writeTable = EasyExcel.writerTable(1).build();
			 excelWriter.write(firstData, writeSheet, writeTable);
             writeTable = EasyExcel.writerTable(2).head(MultiLineHeadExcelModel.class).build();
			 excelWriter.write(secondData, writeSheet, writeTable);
             writeTable = EasyExcel.writerTable(3).head(head).build();
			 excelWriter.write(firstData, writeSheet, writeTable);
			 excelWriter.finish();
	    }catch (IOException e){
			log.error("异常", e.getMessage());
		}
	}

	public static class MultiLineHeadExcelModel {

		@ExcelProperty(value = { "表头1", "表头1", "表头31" }, index = 0)
		private String p1;

		@ExcelProperty(value = { "表头1", "表头1", "表头32" }, index = 1)
		private String p2;

		@ExcelProperty(value = { "表头3", "表头3", "表头3" }, index = 2)
		private String p3;

		@ExcelProperty(value = { "表头4", "表头4", "表头4" }, index = 3)
		private String p4;

		@ExcelProperty(value = { "表头5", "表头51", "表头52" }, index = 4)
		private String p5;

		@ExcelProperty(value = { "表头6", "表头61", "表头611" }, index = 5)
		private String p6;

		@ExcelProperty(value = { "表头6", "表头61", "表头612" }, index = 6)
		private String p7;

		@ExcelProperty(value = { "表头6", "表头62", "表头621" }, index = 7)
		private String p8;

		@ExcelProperty(value = { "表头6", "表头62", "表头622" }, index = 8)
		private String p9;

		public String getP1() {
			return p1;
		}

		public void setP1(String p1) {
			this.p1 = p1;
		}

		public String getP2() {
			return p2;
		}

		public void setP2(String p2) {
			this.p2 = p2;
		}

		public String getP3() {
			return p3;
		}

		public void setP3(String p3) {
			this.p3 = p3;
		}

		public String getP4() {
			return p4;
		}

		public void setP4(String p4) {
			this.p4 = p4;
		}

		public String getP5() {
			return p5;
		}

		public void setP5(String p5) {
			this.p5 = p5;
		}

		public String getP6() {
			return p6;
		}

		public void setP6(String p6) {
			this.p6 = p6;
		}

		public String getP7() {
			return p7;
		}

		public void setP7(String p7) {
			this.p7 = p7;
		}

		public String getP8() {
			return p8;
		}

		public void setP8(String p8) {
			this.p8 = p8;
		}

		public String getP9() {
			return p9;
		}

		public void setP9(String p9) {
			this.p9 = p9;
		}
	}

}
