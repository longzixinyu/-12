import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class SelectCityName {
   public String[] getCityName(){

       //将城市名从excel变种取出并放入数组
       String[] result=new String[100];
       FileInputStream input=null;

       try {
         input = new FileInputStream("e:\\top100.xls");//文件输入流
          HSSFWorkbook workbook = new  HSSFWorkbook (input);
           Sheet sheet = workbook.getSheetAt(0);
           for (int i = 0; i <100 ; i++) {
               //获取城市名称字符串
               Row row = sheet.getRow(i+2);
               Cell cell=row.getCell(2);
               result[i]=cell.getStringCellValue();
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
       return result;
   }
}
