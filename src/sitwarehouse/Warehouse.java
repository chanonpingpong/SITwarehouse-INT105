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

    public static void changeStatus(long id){
        try{
            String SQL = "UPDATE MYDB.WAREHOUSE SET STATUS=INUSE,WHERE WAREHOUSEID=?";
            Connection cnb = ConnectionBuilder.connect();
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setLong(1, id);
            ps.executeUpdate();
            cnb.close();
        }
        catch(SQLException err){
            System.out.println("Warehouse ID is wrong, Please try again.");
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }
    }
    
            public static String getStatus(long id){
        try{
            System.out.println("\n-----------GET WAREHOUSE DETAILS-----------");
            Connection cnb = ConnectionBuilder.connect();
            String SQL = "SELECT STATUS FROM MYDB.WAREHOUSE WHERE WAREHOUSEID=?";            
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            String status;
            if(rs.next()){
                status = rs.getString(1);
            }else{
                System.out.println("Sorry this id does not exist.");
                status = "";
            }
            cnb.close();
            return status;
        }
        catch(SQLException err){
            System.out.println(err);
            return "";
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
            return "";
        }        
    }

    public static boolean changeDetailsById(long id, String size, Double price, String status){
        try{
            String SQL = "UPDATE MYDB.WAREHOUSE SET SIZE='"+size+"',PRICE="+price+",STATUS='"+status+"' WHERE WAREHOUSEID="+id;
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            stmt.executeUpdate(SQL);
            System.out.println("---- CHANGE DETAILS SUCCESSFULLY ----");
            cnb.close();
            return true;
        }
        catch(SQLException err){
            System.out.println("Warehouse ID is wrong, Please try again.");
            return false;
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
            return false;
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
    
    public static double getPrice(long id){
        try{
            System.out.println("\n-----------GET WAREHOUSE DETAILS-----------");
            Connection cnb = ConnectionBuilder.connect();
            String SQL = "SELECT PRICE FROM MYDB.WAREHOUSE WHERE WAREHOUSEID="+id;            
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            if(rs.next()){
                String priceFromDB = rs.getString(1);
                double priceOutput = Double.parseDouble(priceFromDB);
                return priceOutput;
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
        return 0;
    }

    @Override
    public String toString() {
        return "Warehouse{" + "warehouseName=" + warehouseName + ", size=" + size + ", price=" + price + '}';
    }
}
