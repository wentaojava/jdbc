package Util;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**类的描述：
 *利用连接池提供数据库连接
 * @author wentao
 * @time Create in 15:01 2017/11/3 0003
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class DBUtil {
    //连接池对象-由dbcp提供
    private static BasicDataSource ds;
    //读取配置文件
    static {
        Properties properties=new Properties();
        try {
            properties.load(DBTool.class.getClassLoader()
                    .getResourceAsStream("DB.properties"));
            String  driverStr=properties.getProperty("driverStr");
            String  connStr=properties.getProperty("connStr");
            String  username=properties.getProperty("dbUserName");
            String  password=properties.getProperty("dbPassword");
            String  initsize=properties.getProperty("initsize");
            String  maxsize=properties.getProperty("maxsize");
            //创建连接池
            ds=new BasicDataSource();
            //设置参数
            ds.setDriverClassName(driverStr);
            ds.setUrl(connStr);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setInitialSize(new Integer(initsize));
            ds.setMaxActive(new Integer(maxsize));

        }catch (IOException e) {
            e.printStackTrace();
            //抛出异常
            throw new RuntimeException("读取配置文件失败！",e);
      }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void closeConnection(Connection conn){
        if(conn!=null){
            try {
                ds.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("归还连接失败",e);
            }
        }
    }

    //回滚事务
    public static void rollBack(Connection conn){
        if(conn!=null){
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("回滚事务失败！",e);
            }
        }
    }
}
