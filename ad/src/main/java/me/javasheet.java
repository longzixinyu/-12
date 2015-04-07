package me;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ljc10860 on 2015/3/31.
 */
public class javasheet {
        HSSFWorkbook workbook = new HSSFWorkbook();
        int TOTAL_NUMBER=1000;
        int EVERY_NUMBER=200;
        int k=0;
        int SHEET_NUMBER=TOTAL_NUMBER/EVERY_NUMBER;
    public void addsheet(String[] content,String[] content1,String[] content2,String[] content3,String[] content4,String[] content5) {

        for (int i = 0; i < SHEET_NUMBER ; i++) {
            HSSFSheet sheet = workbook.createSheet("sheet"+i);
            HSSFRow  row=sheet.createRow(0);
            row.createCell(0).setCellValue("用户id：");
            row.createCell(2).setCellValue("用户昵称：");
            row.createCell(4).setCellValue("微博id：");
            row.createCell(6).setCellValue("微博创建时间：");
            row.createCell(8).setCellValue("微博mid：");
            row.createCell(10).setCellValue("微博内容：");
            for (int j=1;j<201;j++){
                HSSFRow  row1=sheet.createRow(i);
                row1.createCell(0).setCellValue(content[k]);
                row1.createCell(2).setCellValue(content[k]);
                row1.createCell(4).setCellValue(content[k]);
                row1.createCell(6).setCellValue(content[k]);
                row1.createCell(8).setCellValue(content[k]);
                row1.createCell(10).setCellValue(content[k]);
            }
            k=k+EVERY_NUMBER;
        }

        try {
           File file;
           file = new File("E:\\source\\workbook1.xls");
           FileOutputStream fos=new FileOutputStream(file);
           this.workbook.write(fos);
           fos.close();}
        catch (IOException e) {
           e.printStackTrace();
       }

    }


    }


