package day2;

import Util.DBUtil;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * 类的描述：
 *
 * @author wentao
 * @time Create in 15:22 2017/11/3 0003
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class test {
    @Test
    //测试连接池是否可用
    public void test1(){
        int id=5;
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            System.out.println(conn);
            Statement smt=conn.createStatement();
            String sql="select * from emps where empno='"+id+"'";
            ResultSet res=smt.executeQuery(sql);
            //获取返回数据的总列数
            ResultSetMetaData rsmd = res.getMetaData() ;

            while(res.next()){
                for(int i=1;i< rsmd.getColumnCount();i++){
                System.out.println(res.getString(i));}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn);
        }

    }
}
