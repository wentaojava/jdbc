package entity;

//import sun.util.calendar.BaseCalendar;

import java.io.Serializable;
import java.util.Date;

/**
 * 类的描述：
 * 封装员工表数据
 * @author wentao
 * @time Create in 16:18 2017/11/15 0015
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class Emps implements Serializable{
    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private java.sql.Date hiredate;
    private float sal;
    private float comm;
    private Integer deptno;

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
       this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public java.sql.Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(java.sql.Date hiredate) {
        this.hiredate = hiredate;
    }

    public float getSal() {
        return sal;
    }

    public void setSal(float sal) {
        this.sal = sal;
    }

    public float getComm() {
        return comm;
    }

    public void setComm(float comm) {
        this.comm = comm;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }
}
