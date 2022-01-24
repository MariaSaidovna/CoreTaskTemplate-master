package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД



    public static Connection getMySQLConnection() {

        String connectionURL = "jdbc:mysql://localhost:3306/usersdb?useSSL=false";
        String userName = "root";
        String password = "root";

        Connection conn;

        try {
            conn =  DriverManager.getConnection(connectionURL, userName, password);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void rollbackQuietly(Connection connection) {
        if(connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }


}
