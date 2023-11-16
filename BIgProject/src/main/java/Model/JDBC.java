package Model;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static final String Url = "jdbc:mysql://localhost:3306/QuanLyTaiChinh2";
    private static final String username = "root";
    private static final String password = "040704";
    public static java.sql.Connection connection() throws SQLException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        java.sql.Connection connection = DriverManager.getConnection(Url, username, password);
        return connection;
}
}
