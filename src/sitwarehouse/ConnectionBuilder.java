/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitwarehouse;
import java.sql.*;
/**
 *
 * @author Superz
 */
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
