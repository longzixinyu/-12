import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class Test {
    private static final int CITY_NUMBER = 100;

    private static  final String FILE_PATH = "e:\\top100.xls";

    private static final String TABLE_PREFIX = "MapbarLable_";

    public static void main(String args[]) {
        FileInputStream input = null;
        FileOutputStream output=null;
        try {
            input = new FileInputStream(FILE_PATH);
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            Sheet sheet = workbook.getSheetAt(0);
            CountryDAO selectid = new CountryDAO();
            SelectTotal selecttotal = new SelectTotal();
            int[] result1 = selectid.getTop100CityIds(); //得到储存城市id的数组result1
            int[] result2 = selecttotal.selectT();   //得到存储记录总数的数组result2
            String[] result3 = new String[CITY_NUMBER];  //
            for (int i = 0; i < CITY_NUMBER; i++) {
                result3[i] = TABLE_PREFIX + result1[i]; //得到储存表名的数组result3
            }

              //将搜索结果存入原有excel表
              for (int i = 0; i < CITY_NUMBER; i++) {
                Row row = sheet.getRow(i + 2);
                row.createCell(3).setCellValue(result3[i]);
                row.createCell(4).setCellValue(result2[i]);
            }
           output = new FileOutputStream(FILE_PATH );
            workbook.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=output){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
