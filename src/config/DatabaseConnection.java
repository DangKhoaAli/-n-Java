package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlythuvien";
    private static final String USER = "root";
    private static final String PASSWORD = "120705";      

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Kết nối thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public class getConnection {
    }
}
