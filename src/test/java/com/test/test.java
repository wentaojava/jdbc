package com.test;

import Util.DBTool;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 类的描述：
 *
 * @author wentao
 * @time Create in 16:06 2017/11/2 0002
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class test {
    @Test
    //测试DB
    public void test1(){
        Connection conn=null;
        try {
            conn= DBTool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            DBTool.closeConnection(conn);
        }
    }
}
