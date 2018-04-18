package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {
    // database
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Transport";
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    private static Statement statement;
    private static Connection connection;

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        DBService.statement = statement;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        DBService.connection = connection;
    }

    public static void createСonnection() {
        // Подключение драйвера
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found.");
            e.printStackTrace();
        }

        connection = null;

        // Соединение с бд
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
    }
}