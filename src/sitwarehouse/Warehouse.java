package sitwarehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Warehouse {
    
    private String warehouseName;
    private String size;
    private double price;
    
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void create(String size, Double price){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.SYSTEMDB");
            
            System.out.println("\n-----------ADD PRODUCT-----------");
            
            long warehouseId = 0;
            if(size.equalsIgnoreCase("small")){
                rs.next();
                warehouseId = rs.getLong(1);
                warehouseId++;
                stmt.execute("UPDATE SYSTEMDB SET SMALLID = "+warehouseId);
            }else if(size.equalsIgnoreCase("medium")){
                rs.next();
                warehouseId = rs.getLong(2);
                warehouseId++;
                stmt.execute("UPDATE SYSTEMDB SET MEDIUMID = "+warehouseId);
            }else if(size.equalsIgnoreCase("large")){
                rs.next();
                warehouseId = rs.getLong(3);
                warehouseId++;
                stmt.execute("UPDATE SYSTEMDB SET LARGEID = "+warehouseId);
            }
            
            String upperSize = size.toUpperCase();
            String status = "STANDBY";
            
            String SQL = "INSERT INTO MYDB.WAREHOUSE(WAREHOUSEID, SIZE, PRICE, STATUS) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setLong(1, warehouseId);
            ps.setString(2, upperSize);
            ps.setDouble(3, price);
            ps.setString(4, status);
            ps.executeUpdate();
            cnb.close();
            System.out.println("-----------PRODUCT ADDED-----------");
        }
        catch(SQLException err){
            System.out.print(err);
        }
        catch(ClassNotFoundException err){
            System.out.print(err);
        }        
    }    

    public static void getDetailsById(long id){
        try{
            System.out.println("\n-----------GET WAREHOUSE DETAILS-----------");
            Connection cnb = ConnectionBuilder.connect();
            String SQL = "SELECT * FROM MYDB.WAREHOUSE WHERE WAREHOUSEID=?";            
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                long warehouseId = rs.getLong(1);
                String size = rs.getString(2);
                String price = rs.getString(3);
                String status = rs.getString(4);
                System.out.println("ID: "+warehouseId+"\nSize: "+size+"\nPrice: "+price+"\nStatus: "+status);
            }else{
                System.out.println("Sorry this id does not exist.");
            }
            cnb.close();
        }
        catch(SQLException err){
            System.out.println(err);
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }        
    }

    public static void changeDetailsById(long id, String size, Double price, String status){
        try{
            System.out.println("\n-----------SELECT WAREHOUSE TO CHANGE BY ID-----------");
            Connection cnb = ConnectionBuilder.connect();
            String querySQL = "SELECT * FROM MYDB.WAREHOUSE WHERE WAREHOUSEID=?";
            PreparedStatement ps = cnb.prepareStatement(querySQL);
            ResultSet rs = ps.executeQuery();
            rs.next();
            long warehouseId = rs.getLong(1);
            String querySize = rs.getString(2);
            Double queryPrice = rs.getDouble(3);
            String queryStatus = rs.getString(4);
            System.out.println("---- BEFORE CHANGE ----");
            System.out.println("ID: "+warehouseId+"\nSize: "+querySize+"\nPrice: "+queryPrice+"\nStatus: "+queryStatus);
            cnb.close();
            
            String updateSize,updateStatus;
            Double updatePrice;
            if(size.equalsIgnoreCase("n")){
                updateSize = querySize;
            }else{
                updateSize = size.toUpperCase();
            }
            if(price == -1){
                updatePrice = queryPrice;
            }else{
                updatePrice = price;
            }
            if(status.equalsIgnoreCase("n")){
                updateStatus = queryStatus;
            }else{
                updateStatus = status.toUpperCase();
            }
            String updateSQL = "UPDATE MYDB.WAREHOUSE SET SIZE=?,PRICE=?,STATUS=?,WHERE WAREHOUSEID=?";
            Connection cnb2 = ConnectionBuilder.connect();
            PreparedStatement ps2 = cnb2.prepareStatement(updateSQL);
            ps.setString(1, updateSize);
            ps.setDouble(2, updatePrice);
            ps.setString(3, updateStatus);
            ps.setLong(4, id);
            ps2.executeUpdate();
            System.out.println("---- CHANGE DETAILS SUCCESSFULLY ----");
            cnb2.close();
        }
        catch(SQLException err){
            System.out.println("Warehouse ID is wrong, Please try again.");
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }
    }
    
    public static void list(){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.WAREHOUSE");
            System.out.println("\n-----------LIST OF WAREHOUSE-----------");
            System.out.println("ID  SIZE  PRICE  STATUS");
            while(rs.next()){
                long whId = rs.getLong(1);
                String whSize = rs.getString(2);
                Double whPrice = rs.getDouble(3);
                String whStatus = rs.getString(4);
                System.out.println(whId+" "+whSize+" "+whPrice+" "+whStatus);  
            }
            cnb.close();
        }
        catch(SQLException err){
            System.out.print(err);
        }
        catch(ClassNotFoundException err){
            System.out.print(err);
        }
    }

    @Override
    public String toString() {
        return "Warehouse{" + "warehouseName=" + warehouseName + ", size=" + size + ", price=" + price + '}';
    }
}
