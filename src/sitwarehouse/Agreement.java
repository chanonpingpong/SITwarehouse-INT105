package sitwarehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Agreement {

    public static boolean createAgreement(long warehouseId,double total, long memId, int AmountOfMonth){
        try{    
            if(Warehouse.getStatus(warehouseId).equalsIgnoreCase("STANDBY")){
                
                Warehouse.changeStatus(warehouseId);
                
                GregorianCalendar startDate,endDate,nextWeek;
                startDate = new GregorianCalendar();
                endDate = new GregorianCalendar();
                nextWeek = new GregorianCalendar();
                
                nextWeek.add(GregorianCalendar.MONTH, 3);
                endDate.add(GregorianCalendar.MONTH, AmountOfMonth);
                
                DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                Date firstDate = startDate.getTime();
                Date lastDate = endDate.getTime();
                Date nextWeekDate = nextWeek.getTime();
                
                String fd = df.format(firstDate); 
                String ld = df.format(lastDate);
                String nw = df.format(nextWeekDate);
                
                double nextAmount = total / AmountOfMonth;
                
                Connection cnb = ConnectionBuilder.connect();
                String SQL = "INSERT INTO AGREEMENT VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = cnb.prepareStatement(SQL);
                ps.setLong(1, genAmtId());
                ps.setLong(2, memId);
                ps.setLong(3, warehouseId);
                ps.setDouble(4, total);
                ps.setString(5, fd);
                ps.setString(6, ld);
                ps.setString(7, nw);
                ps.setDouble(8, total);
                ps.setDouble(9, nextAmount);
                ps.executeUpdate();
                ps.close();
                System.out.println("----------- AGREEMENT ADDED -----------");
                return true;
            }else{
                System.out.println("This warehouse not Available.");
                return false;
            }       
        }
        catch(SQLException err){
            System.err.println(err);
            return false;
        }
        catch(ClassNotFoundException err){
            System.err.println(err);
            return false;
        }
        catch(Exception err){
            return false;
        }
    }

    private static long genAmtId(){
        try{
            String SQL = "SELECT * FROM MYDB.SYSTEMDB";
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            long id = rs.getLong(6);
            id++;
            stmt.executeUpdate("UPDATE MYDB.SYSTEMDB SET AMTID="+id+"");
            return id;
        }
        catch(SQLException err){
            System.err.println(err);
        }
        catch(ClassNotFoundException err){
            System.err.println(err);
        }
        return 0;
    }    
    
    private static void getDetailsByMemId(long id){
        try{
            System.out.println("\n----------- GET AGREEMENT DETAILS BY MEMBER ID-----------");
            Connection cnb = ConnectionBuilder.connect();
            String SQL = "SELECT * FROM MYDB.AGREEMENT WHERE MEMID=?";
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while(rs.next()){
                long getAmtId = rs.getLong(1);
                long memId = rs.getLong(2);
                long whId = rs.getLong(3);
                String totalDepts = rs.getString(5);
                String startDate = rs.getString(6);
                String endDate = rs.getString(7);
                String nPaidDate = rs.getString(8);
                String arrears = rs.getString(9);
                System.out.println("Agreement ID: "+getAmtId+""
                        + "\nMember ID: "+memId+""
                        + "\nWharehouse ID: "+whId+""
                        + "\nTotal Dept: "+totalDepts+""
                        + "\nStart Date: "+startDate+""
                        + "\nLast Date: "+endDate+""
                        + "\nNext Date: "+nPaidDate+""
                        + "\nAmount of Arrears: "+arrears);
                System.out.println("---------------------------------------");
                count++;
            }
            System.out.println(count+" Result out");
            cnb.close();
        }
        catch(SQLException err){
            System.out.println("Wrong Member Id, Please try again.");
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }        
    }
    
    private static void getDetailsByAmtId(long amtId){
        try{
            System.out.println("\n----------- GET AGREEMENT DETAILS BY AMT ID-----------");
            Connection cnb = ConnectionBuilder.connect();
            String SQL = "SELECT * FROM MYDB.AGREEMENT WHERE AMTID=?";
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setLong(1, amtId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                long getAmtId = rs.getLong(1);
                long memId = rs.getLong(2);
                long whId = rs.getLong(3);
                String totalDepts = rs.getString(5);
                String startDate = rs.getString(6);
                String endDate = rs.getString(7);
                String nPaidDate = rs.getString(8);
                String arrears = rs.getString(9);
                System.out.println("Agreement ID: "+getAmtId+""
                        + "\nMember ID: "+memId+""
                        + "\nWharehouse ID: "+whId+""
                        + "\nTotal Dept: "+totalDepts+""
                        + "\nStart Date: "+startDate+""
                        + "\nLast Date: "+endDate+""
                        + "\nNext Date: "+nPaidDate+""
                        + "\nAmount of Arrears: "+arrears);
                cnb.close();                
            }else{
                System.out.println("Sorry this id does not exist.");
            }
        }
        catch(SQLException err){
            System.out.println(err);
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }        
    }
    
    private static void changeAgreementDetails(long id,String nextPayDate, double arrears){
        try{
            Connection cnb = ConnectionBuilder.connect();
            String SQL = "UPDATE AGREEMENT SET NPAIDDATE=?,ARREARS=? WHERE AMTID=?";
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setString(1, nextPayDate);
            ps.setDouble(2, arrears);
            ps.setLong(3, id);
            ps.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }        
    }
    
    public static boolean createPermission(long amtId, double amount, String bName, String bId){
        try{    
            if(amount < getNextAmount(amtId)){
                return false;
            }else{
                DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                GregorianCalendar startDate = new GregorianCalendar();
                Date day = startDate.getTime();
                String payDate = df.format(day);
                
                Connection cnb = ConnectionBuilder.connect();
                String SQL = "INSERT INTO PERMISSION VALUES(?,?,?,?,?,?,?)";
                PreparedStatement pstmt = cnb.prepareStatement(SQL);
                pstmt.setLong(1, genPmsId());
                pstmt.setLong(2, amtId);
                pstmt.setString(3, payDate);
                pstmt.setDouble(4, amount);
                pstmt.setBoolean(5, false);
                pstmt.setString(6, bName);
                pstmt.setString(7, bId);
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("----------- PERMISSION HAS BEEN ADDED -----------");
                return true;
            }
        }
        catch(Exception err){
            err.printStackTrace();
            return false;
        }         
    }
    
    
    
    public static long genPmsId(){
        try{
            String SQL = "SELECT * FROM MYDB.SYSTEMDB";
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            long id = rs.getLong(7);
            id++;
            stmt.executeUpdate("UPDATE MYDB.SYSTEMDB SET PMSID="+id+"");
            return id;
        }
        catch(Exception err){
            err.printStackTrace();
        }
        return 0;        
    }
    
    public static boolean submitPermission(long id){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String SQL = "SELECT * FROM PERMISSION WHERE PERMISSIONID="+id;
            ResultSet rs = stmt.executeQuery(SQL);
            if(rs.next()){
                long pmId = rs.getLong(1);
                long amtId = rs.getLong(2);
                String date = rs.getString(3);
                double amount = rs.getDouble(4);
                boolean isCheck = rs.getBoolean(5);
                if(isCheck){
                    return false;
                }else{
                    double updateArrears = getArrears(amtId) - amount;
                    
                    //Create date and format for GregorianCalendar
                    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                    Date dateFromString = df.parse(date);
                    
                    //Create GregorianCalendar and add 1 Month for next pay date
                    GregorianCalendar GCfromDate = new GregorianCalendar();
                    GCfromDate.setTime(dateFromString);
                    GCfromDate.add(GregorianCalendar.MONTH, 1);
                    
                    //Convert Date to String for store in Database
                    Date DateFromGC = GCfromDate.getTime();
                    String newDate = df.format(DateFromGC);
                    
                    changeAgreementDetails(amtId, newDate, updateArrears);
                    confirmPermission(pmId);
                    return true;
                }
            }else{
                return false;
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    
    public static double getNextAmount(long id){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String SQL = "SELECT NPAID FROM AGREEMENT WHERE AMTID="+id;
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            double nextAmout = rs.getDouble(1);
            return nextAmout;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static double getArrears(long id){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String SQL = "SELECT ARREARS FROM AGREEMENT WHERE AMTID="+id;
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            double arrears = rs.getDouble(1);
            return arrears;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }    
    
    public static boolean confirmPermission(long id){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String SQL = "UPDATE PERMISSION SET ISCHECK=TRUE WHERE PERMISSIONID="+id;
            stmt.executeUpdate(SQL);
            System.out.println("----------- CONFIRM PERMISSION NUMBER "+id+" SUCCESSFULLY ------------");
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        } 
    }    
    
    
}
