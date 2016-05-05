package sitwarehouse;
import java.util.Scanner;
import java.sql.*;

public class SITOperation {
   public static Member mem = new Member();
   public static Employee em = new Employee();
  
    public static boolean login(String username, String password, String type) {
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String SQL;
            if(type.equalsIgnoreCase("employee")){
                SQL = "SELECT * FROM MYDB.EMPLOYEE WHERE EMPID='"+username+"' and EMPPASS='"+password+"'";
                ResultSet rs = stmt.executeQuery(SQL);
                if(rs.next()){
                long memId = rs.getLong(1);
                String bankname = rs.getString(2);
                String bankid = rs.getString(3);
                String name = rs.getString(4);
                String address = rs.getString(5);
                String phoneNumber = rs.getString(6);
                String accId = rs.getString(7);
                String accPass = rs.getString(8);
                System.out.println("-----------EMPLOYEE LOGIN SUCCESS----------");
                objMappingEmployee(memId, memId, name, accId, name, address, phoneNumber);  
                cnb.close();
                return true;
                }else{
                System.out.println("Username or Password is wrong, Please try again or contect adminstator.");
                return false;
                }
            }else{
                SQL = "SELECT * FROM MYDB.MEMBER WHERE ACCID='"+username+"' and ACCPASS='"+password+"'";
                ResultSet rs = stmt.executeQuery(SQL);
                if(rs.next()){
                    long memId = rs.getLong(1);
                    String accId = rs.getString(2);
                    String accPass = rs.getString(3);
                    String name = rs.getString(4);
                    String address = rs.getString(5);
                    String phoneNumber = rs.getString(6);
                    objMappingMember(memId, accId, accPass, name, address, phoneNumber);  
                    cnb.close();
                    return true;
                }else{
                    System.out.println("Username or Password is wrong, Please try again or contect adminstator.");
                    return false;
                }
            }
        
        }
        catch(SQLException err){
            System.out.println(err);
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }
        return false;
    }
    
    
    
    public static void objMappingMember(long memId, String accId, String accPass, String name, String address, String phoneNumber){    
        mem = new Member(memId, accId, accPass, name, address, phoneNumber);
        System.out.println("Member ID: "+mem.getMemId()+" is logged in");
    } 
     public static void objMappingEmployee(long emId, double emSalary, String emBankName, String emBankId, String name, String address, String phoneNumber){    
        em = new Employee(emId, emSalary, emBankName, emBankId, name, address, phoneNumber);
        System.out.println("Employee ID: "+em.getEmId()+" is logged in");
    } 
    public static void logout(String who){
        if(who.equalsIgnoreCase("member")){
            System.out.print("Member ID: "+mem.getMemId()+" is logged out");
            mem = null;
        }else if(who.equalsIgnoreCase("employee")){
            System.out.println("Employee ID: "+em.getEmId()+" is logged out");
            em = null;
        }
    }
    
}
