package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    
 private static final String URL = "jdbc:mysql://localhost:3306/sistema_investimentos";
    private static final String USER = "appuser";
    private static final String PASSWORD = "254697";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver JDBC não encontrado");
        }
    }
}
