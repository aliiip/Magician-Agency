
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Connect {

    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/MagicianAgent";
    private static final String user = "java";
    private static final String password = "java";
    private static Connection connection;

    //Makes sure the connection is valid
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return connection;
    }
}
