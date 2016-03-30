package sitwarehouse;
import java.sql.*;

public class ConnectionBuilder {
    public static Connection connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        String host = "jdbc:derby://localhost:1527/SITwarehouseDB";
        String uName = "mydb";
        String uPass = "mydb";
        Connection con = DriverManager.getConnection(host, uName, uPass);
        return con;
    }
    
}
