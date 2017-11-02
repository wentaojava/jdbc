package Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**类的描述：
 * 读取配置文件返回数据库连接
 * @author wentao
 * @time Create in 15:45 2017/11/2 0002
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class DBTool {
    private static String driverStr;
    private static String connStr;
    private static String username;
    private static String password;
     //加载配置文件
    static {
        Properties properties=new Properties();
        try {
            properties.load(DBTool.class.getClassLoader()
                    .getResourceAsStream("DB.properties"));
            driverStr=properties.getProperty("driverStr");
            connStr=properties.getProperty("connStr");
            username=properties.getProperty("dbUserName");
            password=properties.getProperty("dbPassword");
            Class.forName(driverStr);

        } catch (IOException e) {
            e.printStackTrace();
            //抛出异常
            throw new RuntimeException("读取配置文件失败！",e);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("加载驱动失败！",e);
        }

     }
    //返回连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connStr,username,password);
    }
    //关闭连接
    public static void closeConnection(Connection conn){
        if(conn !=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭连接失败！",e);
            }
        }

    }
}
