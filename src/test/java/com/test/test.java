package com.test;

import Util.DBTool;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 类的描述：
 *
 * @author wentao
 * @time Create in 16:06 2017/11/2 0002
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class test {
    @Test
    //测试DB并执行插入语句
    public void test1(){
        Connection conn=null;
        try {
            conn= DBTool.getConnection();
            Statement smt=conn.createStatement();
            String sql="INSERT INTO EMPS (ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) " +
                    "VALUES('唐僧','经理',0,NOW(),8000.0,2000.0,3);";
            int res=smt.executeUpdate(sql);
            System.out.println(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            DBTool.closeConnection(conn);
        }
    }

    @Test
    //执行查询语句
    public void test2(){
        Connection conn=null;
        try {
            conn=DBTool.getConnection();
            Statement smt=conn.createStatement();
            String sql="SELECT * FROM EMPS WHERE DEPTNO='1' ORDER BY EMPNO";
            ResultSet res=smt.executeQuery(sql);
            //取数据
            while(res.next()){
                //获取每行中每一列的值
                System.out.print(res.getInt("empno")+"\t");
                System.out.println(res.getString("ename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBTool.closeConnection(conn);
        }
    }

    /**练习增删改查
     * */
    @Test
    //增
    public void test3(){
        Connection conn=null;
        try {
            conn=DBTool.getConnection();
            Statement smt=conn.createStatement();
            String sql="INSERT INTO EMPS (ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) VALUES" +
                    " ('沙和尚','驸马',0,'2011-11-03',8000.0,2000.0,2)";
            try{
                smt.executeUpdate(sql);
                System.out.println("数据插入成功！");
            }catch(Exception e){
                System.out.println("数据插入失败！");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBTool.closeConnection(conn);
        }
    }

    @Test
    //查询出id之后再进行修改ename
    public void test4(){
        Connection conn=null;
        int id=0;
        try {
            conn=DBTool.getConnection();
            Statement smt=conn.createStatement();
            String sql="SELECT EMPNO FROM EMPS WHERE ENAME='沙和尚'";
            ResultSet rs=smt.executeQuery(sql);
            while(rs.next()){
                id=rs.getInt("empno");
            }
            System.out.println(id);

            String sql1="update emps set ename='悟空' where empno="+id;
            smt.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBTool.closeConnection(conn);
        }
    }

    @Test
    //删除以上新增和修改只有的悟空数据
    public void test5(){
        Connection conn=null;
        try {
            conn=DBTool.getConnection();
            Statement smt=conn.createStatement();
            String sql="DELETE FROM EMPS WHERE ENAME='悟空'";
            smt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBTool.closeConnection(conn);
        }
    }

}
