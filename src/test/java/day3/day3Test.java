package day3;

import DAO.EmpsDao;
import Util.DBUtil;
import entity.Emps;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * 类的描述：
 *
 * @author wentao
 * @time Create in 15:13 2017/11/6 0006
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class day3Test {
    @Test
    //批量添加108个员工
    public void test1(){
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into emps (ename,job,mgr,hiredate,sal,comm,deptno)" +
                    " values(?,?,?,?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(sql);
            for(int i=0;i<108;i++){
                ps.setString(1,"好汉"+i);
                ps.setString(2,"打劫"+i);
                ps.setInt(3,0);
                ps.setDate(4,new Date(System.currentTimeMillis()));
                ps.setDouble(5,6000);
                ps.setDouble(6,1000);
                ps.setInt(7,2);
                //将数据暂存至PS对象
                ps.addBatch();
                //每50次批量提交一次
                if(i%50==0){
                 ps.executeBatch();
                 //清空暂存的数据，便于下一批
                 ps.clearBatch();
                }
            }
            //避免零头未提交，再单独提交一次
            ps.executeBatch();


            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.rollBack(conn);
        }finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Test
    /**方法描述 包含事务
     * 先增加一个部门，再给这个部门增加一个员工
     * 演示如何获得自增主键
     * @author wentao
     * @param
     * @return
     */
    public void test2(){
        //模拟部门数据
        String s1="测试部";
        String s2="无锡";
        //模拟员工数据
        String name="测试1";
        String jon="测试";
        int mgr=1;
        Date date=new Date(System.currentTimeMillis());
        double sal=1000;
        double common=2000;

        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
            conn.setAutoCommit(false);
          //增加一个部门
            String sql="insert into depts (dname,loc) values(?,?)";
            //参数2是数组，告诉PS返回哪些字段
            PreparedStatement smt=conn.prepareStatement(sql,
                    new String[]{"deptno"});

            smt.setString(1,s1);
            smt.setString(2,s2);
            smt.executeUpdate();
            //从PS中获取主键,结果集中包含一行一列（只写了一个字段）
            ResultSet rs=smt.getGeneratedKeys();
            rs.next();
            //此处参数只能写序号
            int id=rs.getInt(1);

            //增加一个员工
            String sql2="insert into emps (ename,job,mgr,hiredate,sal,comm,deptno)" +
                    " values(?,?,?,?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(sql2);
            ps.setString(1,name);
            ps.setString(2,jon);
            ps.setInt(3,mgr);
            ps.setDate(4,date);
            ps.setDouble(5,sal);
            ps.setDouble(6,common);
            ps.setInt(7,id);
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.rollBack(conn);
        }finally {
            DBUtil.closeConnection(conn);
        }
    }
    
    @Test
    /**方法描述
     * 演示分页查询
     * @author wentao
     * @param
     * @return
     */
    public void test3(){
        //假设每页显示10行
        int size=10;
        //假设显示第三页数据
        int page=3;

        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
            //mysql分页查询语句
            //查询第11到第15条数据
            //select * from table_name limit 10,5
            String sql="SELECT * FROM EMPS ORDER BY EMPNO ASC LIMIT ?,?";
            PreparedStatement smt=conn.prepareStatement(sql);
            //起始行
            smt.setInt(1,(page-1)*size);
            //结束行
            smt.setInt(2,size);
            ResultSet rs=smt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt("empno"));
                System.out.println(rs.getString("ename"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeConnection(conn);
        }

    }

    /**方法描述
     * 测试EmpsDao
     * @author wentao
     * @param
     * @return
     */
    @Test
    public void test4(){
        EmpsDao dao=new EmpsDao();
        Emps e=dao.findById(118);
        if(e!=null){
            System.out.println(e.getEname());
            e.setEname("开发");
            dao.update(e);
        }

    }

}
