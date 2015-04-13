import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class SelectTotal {
    public int[] selectT(){
         int CITY_NUMBER=100;
         int[] result1=new int[CITY_NUMBER];    //储存城市id数组
         int[] result2=new int[CITY_NUMBER];     //储存总记录数数组
         String[] tablename=new String[CITY_NUMBER];   //储存城市对应表名
         CountryDAO selectid=new CountryDAO();
         result1=selectid.getTop100CityIds();
         DataCon dataCon = new DataCon();
         Connection conn =dataCon.getConnection();   //连接数据库
         ResultSet resultSet=null;
         String sql=null;
         int i=0;
         for (int j = 0;j <CITY_NUMBER; j++) {
                   tablename[j]=" MapbarLable_"+result1[j];
         }

         try {
             Statement statement=conn.createStatement();  //初始化statement
             for (int j = 0; j < CITY_NUMBER; j++)
             {sql="select count(1) from"+tablename[j];
                 resultSet=statement.executeQuery(sql);
                 while(resultSet.next()){
                     result2[j]= resultSet.getInt(1);
                     i++;
                 }
             }

         } catch (Exception e) {
             e.printStackTrace();
         }finally {
             try {
                 conn.close();   //关闭数据库连接
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
             return result2;
     }}
