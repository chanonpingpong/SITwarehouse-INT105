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
    private long warehouseId;
    private double totalDepts;
    private String startDate;
    private String endDate;
    private double nextPaid;
    private String nPaidDate;
    private double arrears;

    public Agreement() {
    }

    public Agreement(long warehouseId, double totalDepts, String startDate, String endDate, double nextPaid, String nPaidDate, double arrears) {
        this.warehouseId = warehouseId;
        this.totalDepts = totalDepts;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nextPaid = nextPaid;
        this.nPaidDate = nPaidDate;
        this.arrears = arrears;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }
    

    public double getTotalDepts() {
        return totalDepts;
    }

    public void setTotalDepts(double totalDepts) {
        this.totalDepts = totalDepts;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getNextPaid() {
        return nextPaid;
    }

    public void setNextPaid(double nextPaid) {
        this.nextPaid = nextPaid;
    }

    public String getnPaidDate() {
        return nPaidDate;
    }

    public void setnPaidDate(String nPaidDate) {
        this.nPaidDate = nPaidDate;
    }

    public double getArrears() {
        return arrears;
    }

    public void setArrears(double arrears) {
        this.arrears = arrears;
    }
    
    public void calcTotalDepts(double amount){
        totalDepts -= amount;
    }
    public void calcNextPaid(){
        nextPaid += arrears;
    }

    public static void createAgreement(long warehouseId,double total, long memId, double nextPaid, double arrears, int AmountOfMonth){
        try{    
                GregorianCalendar startDate,endDate,nextWeek;
                startDate = new GregorianCalendar();
                endDate = new GregorianCalendar();
                nextWeek = new GregorianCalendar();
                
                nextWeek.add(GregorianCalendar.MONTH, 1);
                endDate.add(GregorianCalendar.MONTH, AmountOfMonth);
                
                DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                Date firstDate = startDate.getTime();
                Date lastDate = endDate.getTime();
                Date nextWeekDate = nextWeek.getTime();
                String fd = df.format(firstDate); 
                String ld = df.format(lastDate);
                String nw = df.format(nextWeekDate);
                
                nextPaid = arrears / AmountOfMonth;
                
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
                ps.setDouble(8, arrears);
                ps.setDouble(9, nextPaid);
                ps.executeUpdate();
                ps.close();
                System.out.println("----------- AGREEMENT ADDED -----------");
        }
        catch(SQLException err){
            System.err.println(err);
        }
        catch(ClassNotFoundException err){
            System.err.println(err);
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
    
    private static void changeAgreementDetails(){
        
    }

    private static void updateNextPaidDate(){
        
    }
    
    
    public static void createPermission(long amtId, String paidDate, double paidAmount){
        try{
                Connection cnb = ConnectionBuilder.connect();
                String SQL = "INSERT INTO PERMISSION VALUES(?,?,?,?,?)";
                PreparedStatement pstmt = cnb.prepareStatement(SQL);
                pstmt.setLong(1, genPmsId());
                pstmt.setLong(2, amtId);
                pstmt.setString(3, paidDate);
                pstmt.setDouble(4, paidAmount);
                pstmt.setBoolean(5, false);
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("----------- PERMISSION HAS BEEN ADDED -----------");
        }
        catch(Exception err){
            err.printStackTrace();
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
    
    public static void submitPermission(long id, char cf){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String SQL = "SELECT * FROM PERMISSION WHERE PERMISSIONID="+id;
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            long outputId = rs.getLong(1);
            Boolean isCheck = rs.getBoolean(5);
            System.out.println("Permission ID: "+outputId);
            if(isCheck == true){
                System.out.println("Status: CONFIRMED");                
            }else{
                System.out.println("Status: NOT CONFIRM YET");
                if(cf == 'y' || cf == 'Y'){
                    confirmPermission(id);
                }else if(cf == 'n' || cf == 'N'){
                    System.out.println("-------- THIS PERMISSION HAVE NOTHING CHANGE ---------");
                }else{
                    System.out.println("-------- THIS METHOD ARE UNDER TESTING AND YOU INPUT THE WRONG ANSWER ----------");
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void confirmPermission(long id){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String SQL = "UPDATE PERMISSION SET ISCHECK=TRUE WHERE PERMISSIONID="+id;
            stmt.executeUpdate(SQL);
            System.out.println("----------- CONFIRM PERMISSION NUMBER "+id+" SUCCESSFULLY ------------");
        }
        catch(Exception e){
            e.printStackTrace();
        } 
    }    
    
    
    @Override
    public String toString() {
        return "Agreement{" + "warehouseId=" + warehouseId + ", totalDepts=" + totalDepts + ", startDate=" + startDate + ", endDate=" + endDate + ", nextPaid=" + nextPaid + ", nPaidDate=" + nPaidDate + ", arrears=" + arrears + '}';
    }
    
}
