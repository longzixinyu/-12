package com.ly;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ljc10860 on 2015/4/9.
 */
public class selectTotal {
     public int[] selectT(){
         int CITY_NUMBER=100;
         int[] result1=new int[CITY_NUMBER];    //储存城市id数组
         int[] result2=new int[CITY_NUMBER];     //储存总记录数数组
         String[] tablename=new String[CITY_NUMBER];   //储存城市对应表名
         selectId selectid=new selectId();
         result1=selectid.select();
         DataCon dataCon = new DataCon();
         Connection conn =dataCon.getConnection();   //连接数据库
         ResultSet resultSet=null;
         String sql=null;
         for (int i = 0; i <CITY_NUMBER; i++) {
                   tablename[i]=" MapbarLable_"+result1[i];
         }

         try {
             Statement statement=conn.createStatement();  //初始化statement
             for (int i = 0; i < CITY_NUMBER; i++)
             {
                sql="select count(1) from"+tablename[i];
             }
             resultSet=statement.executeQuery(sql);
             for (int i = 0; i < CITY_NUMBER; i++){
                 result2[i]=resultSet.getInt(i);         //顺序获取结果集resultset中元素并存入数组
             }
             conn.close();   //关闭数据库连接
         } catch (Exception e) {
             e.printStackTrace();
         }

       return result2;
     }



}
