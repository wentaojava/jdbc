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

    @Test
    //演示PS防注入攻击
    public void test4(){
        //假设注入攻击
        String name="tarena";
        String pwd="a' or 'b";

        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
            String sql="select * from users where username=? and password=?";
            PreparedStatement smt=conn.prepareStatement(sql);
            smt.setString(1,name);
            smt.setString(2,pwd);
            ResultSet res=smt.executeQuery();
            if(res.next()){
                System.out.println("查询成功");
            }else{
                System.out.println("查询失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Test
    //演示元数据
    public void test5(){
        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
            String sql="SELECT * FROM EMPS ORDER BY EMPNO";
            Statement smt=conn.createStatement();
            ResultSet res=smt.executeQuery(sql);
            //创建元数据,返回结果集的概述信息
            ResultSetMetaData rsmd=res.getMetaData();
            System.out.println(rsmd.getColumnCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Test
    //演示简单转账流程
    public void test6(){
        //假设用户登录的账号
        String user="00001";
        //假设收款账号
        String accept="00002";
        //假设转账1000
        double money=1000.0;

        Connection conn=null;
        try {
            conn=DBUtil.getConnection();

            //取消自动提交事务
           conn.setAutoCommit(false);

            //查询账号余额
            String sql="select money from accounts where id=?";
            PreparedStatement smt=conn.prepareStatement(sql);
            smt.setString(1,user);
            ResultSet res=smt.executeQuery();
            res.next();
            double paymoney=res.getDouble("money");
            if(paymoney>=money){
                //查询收款账号是否存在
                String sql2="select * from accounts where id=?";
                PreparedStatement smt2=conn.prepareStatement(sql2);
                smt2.setString(1,accept);
                ResultSet res2=smt2.executeQuery();
                double acceptMoney=0;//声明收款方余额
                if(!res2.next()){
                    System.out.println("收款账号不存在");
                    return;
                }else{
                    acceptMoney= res2.getDouble("money");
                }
                //开始转账
                String sql3="update accounts set money=? where id=?";
                PreparedStatement smt3=conn.prepareStatement(sql3);
                smt3.setDouble(1,paymoney-money);
                smt3.setString(2,user);
                smt3.executeUpdate();

                Integer.valueOf("断电了");

                String sql4="update accounts set money=? where id=?";
                PreparedStatement smt4=conn.prepareStatement(sql4);
                smt4.setDouble(1,acceptMoney+money);
                smt4.setString(2,accept);
                smt4.executeUpdate();
            }else {
                System.out.println("余额不足");
                return;
            }

            //手动提交事务
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            //发生异常时回滚事务
            DBUtil.rollBack(conn);
        }finally {
            DBUtil.closeConnection(conn);
        }
    }
}
