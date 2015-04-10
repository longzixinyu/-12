package com.ly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class selectId {
    public int[] select() {
        int CITY_NUMBER=100;
        String [] result=new String[100];   //储存城市名数组
        int[] result1=new int[100];   //储存城市id数组
        SheetClass sheetClass=new SheetClass();
        result=sheetClass.getResult();
        DataCon dataCon = new DataCon();
        Connection conn =dataCon.getConnection();  //连接数据库
        ResultSet resultSet=null;  //初始化resultset集合
        PreparedStatement statement=null;
        String sql="select id from guojiadiqudata where name=? and type=3";
        try {
            statement = conn.prepareStatement(sql);
            for (int i = 0; i < CITY_NUMBER; i++)
            {
                statement.setString(1,result[i]);
            }
                resultSet=statement.executeQuery();
            for (int i = 0; i < CITY_NUMBER; i++){
                result1[i]=resultSet.getInt(i);         //顺序获取结果集resultset中元素并存入数组
            }

            conn.close();    //关闭数据库连接

        }catch (Exception e) {
            e.printStackTrace();
        }
        return result1;
    }
}
