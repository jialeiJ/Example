package com.vienna.jaray;

import com.vienna.jaray.read.ExcelRead;
import com.vienna.jaray.write.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class EasyexcelApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 实体类写入
     */
    @Test
    public void testExcelWriteWhitEntity(){
        new ExcelWriteWhitEntity().writeWhitEntity();
    }

    /**
     * 表头写入
     */
    @Test
    public void testExcelWriteWhitHead(){
        new ExcelWriteWhitHead().writeWhitHead();
    }

    /**
     * 多行复杂的表头写入
     */
    @Test
    public void testExcelWriteWithMultiHead(){
        new ExcelWriteWithMultiHead().writeWithMultiHead();
    }

    /**
     * 多表写入
     */
    @Test
    public void testExcelWriteWithMultiTable(){
        new ExcelWriteWithMultiTable().writeWithMultiTable();
    }

    /**
     * 无表头写入
     */
    @Test
    public void testExcelWriteWithoutHead(){
        new ExcelWriteWithoutHead().writeWithoutHead();
    }

    /**
     * 读取
     */
    @Test
    public void testExcelRead(){
        new ExcelRead().excelSimpleRead();
    }

    /**
     * 读取
     */
    @Test
    public void testMultiLineHeadRead(){
        new ExcelRead().excelMultiHeadRead();
    }
}
