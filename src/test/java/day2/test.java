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

    @Test
    //演示使用PS执行DML
    public void test2(){
        //定义变量用来模拟前段传入的数据
        int no=1; String name="张三丰";

        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
           //创建PS的同时也需要把SQL语句传入
            String sql="UPDATE emps set ename=? where empno=?";
            PreparedStatement smt=conn.prepareStatement(sql);
            //依次设置？所对应的数值
            smt.setString(1,name);
            smt.setInt(2,no);
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn);
        }

    }

    @Test
    //演示PS执行查询
    public void test3(){
        double sal=6000.0;

        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
            String sql="select * from emps where sal>?";
            PreparedStatement smt=conn.prepareStatement(sql);
            smt.setDouble(1,sal);
            ResultSet res=smt.executeQuery();
            while(res.next()){
                System.out.println(res.getString("ename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
