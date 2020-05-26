package com.vienna.jaray.read;

import java.io.*;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.vienna.jaray.model.ExcelPropertyModel;
import com.vienna.jaray.model.MultiLineHeadExcelModel;
import com.vienna.jaray.read.listener.ExcelPropertyMofelExcelListener;
import com.vienna.jaray.read.listener.MultiLineHeadExcelListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelRead {

	public void excelSimpleRead() {
		try (InputStream in = new FileInputStream("withHeadwhitEntity.xlsx");) {
			// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
			List<ExcelPropertyModel> list = EasyExcel.read(in, ExcelPropertyModel.class, new ExcelPropertyMofelExcelListener()).sheet("员工信息").doReadSync();
			for (Object object : list) {
				System.out.println((ExcelPropertyModel) object);
			}
	    }catch (IOException e){
			log.error("异常", e.getMessage());
		}
	}

	public void excelMultiHeadRead() {
		try (InputStream in = new FileInputStream("withMultiHead.xlsx");) {
			// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
			List<ExcelPropertyModel> list = EasyExcel.read(in, MultiLineHeadExcelModel.class, new MultiLineHeadExcelListener()).headRowNumber(3).sheet("员工信息").doReadSync();
			for (Object object : list) {
				System.out.println((MultiLineHeadExcelModel) object);
			}
		}catch (IOException e){
			log.error("异常", e.getMessage());
		}
	}

}
