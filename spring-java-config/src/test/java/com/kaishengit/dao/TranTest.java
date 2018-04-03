package com.kaishengit.dao;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TranTest {

    @Test
    public void test() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///mydb?useSSL=false","root","rootroot");
        PreparedStatement statement = null;

        //设置手动提交事务
        connection.setAutoCommit(false);
        try {

            String sql = "insert into product(name,inventory) values('aa',10)";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            //提交事务
            connection.commit();
        } catch (SQLException ex) {
            //回滚事务
            connection.rollback();
        } finally {
            statement.close();
            connection.close();
        }

    }
}
