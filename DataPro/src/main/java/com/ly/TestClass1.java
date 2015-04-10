package com.ly;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class TestClass1 {
    public static void main(String args[]) {
        try {
            int CITY_NUMBER=100;
            FileInputStream input=new FileInputStream("e:\\top100.xlsx");
            POIFSFileSystem ps=new POIFSFileSystem(input);
            HSSFWorkbook workbook=new HSSFWorkbook(ps);
            HSSFSheet sheet=workbook.getSheetAt(0);
            FileOutputStream output=new FileOutputStream("e:\\top100.xlsx");
            selectId selectid=new selectId();
            selectTotal selecttotal=new selectTotal();
            int[] result1=selectid.select();
            int[] result2=selecttotal.selectT();
            String[] result3=new String[CITY_NUMBER];
            for (int i = 0; i <CITY_NUMBER ; i++) {
             result3[i]=" MapbarLable_"+result1[i];
            }

            //将搜索结果存入原有数据库
            for (int i = 0; i < CITY_NUMBER ; i++) {
                HSSFRow row=sheet.getRow(i+2);
                HSSFCell cell1=row.getCell(3);
                HSSFCell cell2=row.getCell(4);
                cell1.setCellValue(result3[i]);
                cell2.setCellValue(result2[i]);
            }
            output.flush();  //刷新输出流
            output.close();  //关闭输出流
            input.close();  //关闭输入流
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
