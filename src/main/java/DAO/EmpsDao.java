package DAO;

import Util.DBUtil;
import entity.Emps;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 类的描述：
 *员工表的算法封装类
 * @author wentao
 * @time Create in 16:29 2017/11/15 0015
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class EmpsDao implements Serializable{

    /**方法描述
     * 增加一个员工
     * @author wentao
     * @param
     * @return
     */
    public void save(Emps emps){
        
    }

    /**方法描述
     * 根据ID修改员工信息
     * @author wentao
     * @param
     * @return
     */
    public void update(Emps emp){
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update emps set ename=?,job=?,mgr=?,hiredate=?,sal=?,comm=?,deptno=? " +
                        "where empno=?";
            PreparedStatement smt=conn.prepareStatement(sql);
            smt.setString(1,emp.getEname());
            smt.setString(2,emp.getJob());
            smt.setInt(3,emp.getMgr());
            smt.setDate(4,emp.getHiredate());
            smt.setFloat(5,emp.getSal());
            smt.setFloat(6,emp.getComm());
            smt.setInt(7,emp.getDeptno());
            smt.setInt(8,emp.getEmpno());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改员工信息失败！",e);
        }finally {
            DBUtil.closeConnection(conn);
        }

    }

    /**方法描述
     * 根据ID删除一个员工
     * @author wentao
     * @param
     * @return
     */
    public void delete(int id){


    }

    /**方法描述
     * 根据ID查询一个员工
     * @author wentao
     * @param
     * @return
     */
    public Emps findById(int id){
        Connection conn=null;
        try {
            conn=DBUtil.getConnection();
            String sql="select * from emps where empno=?";
            PreparedStatement smt=conn.prepareStatement(sql);
            smt.setInt(1,id);
            ResultSet rs=smt.executeQuery();
            if(rs.next()){
                Emps emp=new Emps();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setMgr(rs.getInt("mgr"));
                emp.setHiredate(rs.getDate("hiredate"));
                emp.setSal(rs.getFloat("sal"));
                emp.setComm(rs.getFloat("comm"));
                emp.setDeptno(rs.getInt("deptno"));
                return emp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败！",e);
        }finally {
            DBUtil.closeConnection(conn);
        }
        return null;

    }

    /**方法描述
     * 根据部门id查询员工
     * @author wentao
     * @param
     * @return
     */
    public List<Emps> findByDept(int deptno){
return null;
    }

}
