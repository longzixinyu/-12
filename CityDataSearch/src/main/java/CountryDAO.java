import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class CountryDAO {
    public int[] getTop100CityIds()  {
        int CITY_NUMBER = 100;
        String[] result = new String[100];   //储存城市名数组
        int[] result1 = new int[100];   //储存城市id数组
        SheetClass sheetClass = new SheetClass();
        result = sheetClass.getResult();      //得到储存城市名的数组
        DataCon dataCon = new DataCon();
        Connection conn = dataCon.getConnection();  //连接数据库
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int i=0;
        String sql = "select * from guojiadiqudata where name=? and type=3";
        try {statement=conn.prepareStatement(sql);
            for (int j = 0; j <CITY_NUMBER ; j++) {
                statement.setString(1,result[j]);   //设置占位符参数
                resultSet=statement.executeQuery();   //执行sql语句
          while(resultSet.next()) {
              result1[i] = resultSet.getInt("id");   //获取结果集resultSet中的id
              i++;
          }}

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();  //关闭数据库连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result1;  //返回储存城市id的result1数组
    }}

