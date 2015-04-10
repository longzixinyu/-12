package com.ly;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class SheetClass {
   public String[] getResult(){

       //将城市名从excel变种取出并放入数组

       String[] result=new String[100];
       try {
           FileInputStream input = new FileInputStream("e:\\top100.xlsx");//文件输入流
           POIFSFileSystem fs = new POIFSFileSystem(input);
           HSSFWorkbook workbook = new HSSFWorkbook(fs);
           HSSFSheet sheet = workbook.getSheetAt(0);
           for (int i = 0; i <100 ; i++) {
               //获取城市名称字符串
               HSSFRow row = sheet.getRow(i+2);
               HSSFCell cell=row.getCell(2);
               result[i]=cell.getStringCellValue();
           }
           input.close(); //关闭输入流
       } catch (Exception e) {
           e.printStackTrace();
       }

          return result;
   }










}
