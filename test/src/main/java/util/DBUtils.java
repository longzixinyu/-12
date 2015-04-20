package util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Description Of The Class<br/>
 *
 * @author Quinn Wang(王中奎)
 * @version 1.0.0, 2015/4/13 11:05
 * @since 2015/4/13 11:05
 */
public class DBUtils {

    public static final Log logger = LogFactory.getLog(DBUtils.class);

    private static ComboPooledDataSource ds = null;

    static {
        initConnect();
    }

    /**
     * 初始化数据库连接池
     */
    public static void initConnect(){
        try {
            Properties properties = new Properties();
            InputStream inputStream = DBUtils.class.getResourceAsStream("/c3p0ForSqlServer.properties");
            properties.load(inputStream);
            if (null == ds) {
                ds = new ComboPooledDataSource();
                // 设置JDBC的Driver类
                ds.setDriverClass(properties.getProperty("db.driver")); // 参数由
                ds.setJdbcUrl(properties.getProperty("db.url"));
                // 设置数据库的登录用户名
                ds.setUser(properties.getProperty("db.username"));
                // 设置数据库的登录用户密码
                ds.setPassword(properties.getProperty("db.password"));
                // 设置连接池初始化大小
                ds.setInitialPoolSize(Integer.parseInt(properties.getProperty("db.initialPoolSize")));
                // 设置连接池的最大连接数
                ds.setMaxPoolSize(Integer.parseInt(properties.getProperty("db.maxPoolSize")));
                // 设置连接池的最小连接数
                ds.setMinPoolSize(Integer.parseInt(properties.getProperty("db.minPoolSize")));
                ds.setTestConnectionOnCheckin(true);
                ds.setIdleConnectionTestPeriod(30);
                ds.setIdleConnectionTestPeriod(100);
                ds.setTestConnectionOnCheckout(true);
            }

        } catch (PropertyVetoException e) {
            logger.error("连接池加载失败");
        } catch (IOException e) {
            logger.error("连接池加载失败");
        }

    }

    /**
     * 获取数据库链接
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        if (null == ds) {
            initConnect();
        }

        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            logger.error("获取链接异常",e);
        }
        return conn;
    }

    /**
     * 释放资源
     * @param conn
     * @param st
     * @param rs
     */
    public static void releaseSource(Connection conn,Statement st,ResultSet rs){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }
}
