package sitwarehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Member extends People {
    private long memId;
    private String accId;
    private String accPass;
   // private String bankName;
 //   private String bankId;
    private String Confirm;

    public Member() {
    }

    public Member(long memId, String accId, String accPass, String Confirm) {
        this.memId = memId;
        this.accId = accId;
        this.accPass = accPass;
        this.Confirm = Confirm;
    }

  

    public String getConfirm() {
        return Confirm;
    }

    public void setConfirm(String Confirm) {
        this.Confirm = Confirm;
    }

    public long getMemId() {
        return memId;
    }

    public void setMemId(long memId) {
        this.memId = memId;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getAccPass() {
        return accPass;
    }

    public void setAccPass(String accPass) {
        this.accPass = accPass;
    }

    
     
    public static void create(String name, String address, String phoneNumber , String accId, String accPass){
        try{
            if(haveThisAccId(accId)){
                System.out.println("This username already be used. Please change");
            }else{
                Connection cnb = ConnectionBuilder.connect();
                String SQL = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?)";
                PreparedStatement pstmt = cnb.prepareStatement(SQL);
                pstmt.setLong(1, genMemId());
                pstmt.setString(2, accId);
                pstmt.setString(3, accPass);
                pstmt.setString(4, name);
                pstmt.setString(5, address);
                pstmt.setString(6, phoneNumber);
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("-----------MEMBER ADDED-----------");
            }
        }
        catch(SQLException err){
            System.err.println(err);
        }
        catch(ClassNotFoundException err){
            System.err.println(err);
        }
    }
    
    public static boolean haveThisAccId(String accId) throws SQLException, ClassNotFoundException{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.MEMBER");
            boolean itsHad = false;
            while(rs.next()){
                String checkId = rs.getString(2);
                if(checkId.equalsIgnoreCase(accId)){
                    itsHad = true;
                    stmt.close();
                    return itsHad;
                }
            }
            return itsHad;
    }
    
    public static long genMemId(){
        try{
            String SQL = "SELECT * FROM MYDB.SYSTEMDB";
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            long id = rs.getLong(4);
            id++;
            stmt.executeUpdate("UPDATE MYDB.SYSTEMDB SET MEMID="+id+"");
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

    public static void getDetailsById(long memId){
        try{
            System.out.println("\n-----------GET MEMBER DETAILS-----------");
            Connection cnb = ConnectionBuilder.connect();
            String SQL = "SELECT * FROM MYDB.MEMBER WHERE MEMID=?";
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setLong(1, memId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                long memberId = rs.getLong(1);
                String accId = rs.getString(2);
                String name = rs.getString(4);
                String addr = rs.getString(5);
                String phoneNumber = rs.getString(6);
                System.out.println("ID: "+memberId+"\nUsername: "+accId+ "\nName: "+name+""+ "\nAddress: "+addr+""+ "\nPhone Number: "+phoneNumber);
                cnb.close();                
            }else{
                System.out.println("This id is does not exist.");
            }
        }
        catch(SQLException err){
            System.out.println(err);
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }        
    }    
    
    public static void list(){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.MEMBER");
            System.out.println("\n------------LIST OF MEMBER-----------");
            while(rs.next()){
                long memId = rs.getLong(1);
                String accId = rs.getString(2);
                String memName = rs.getString(4);
                String memAddress = rs.getString(5);
                String memPhoneNumber = rs.getString(6);
                System.out.println(memId+" "+accId+" "+memName+" "+ memAddress+" "+memPhoneNumber);  
            }
            stmt.close();
        }
        catch(SQLException err){
            System.err.println(err);
        }
        catch(ClassNotFoundException err){
            System.err.println(err);
        }        
    }    
    
    @Override
    public String toString() {
        return "Member{" + "memId=" + memId + ", accId=" + accId + ", accPass=" + accPass + '}';
    }    
    
    
}
