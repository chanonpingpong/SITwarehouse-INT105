package sitwarehouse;
import java.util.Scanner;
import java.sql.*;

public class SITOperation {
   private static Member mem = new Member();
  // private static Employee em = new Employee();
//Member Section    

    

    

      

    

    
// End of Member Section
    
    
// Employee Section    

    

    

            

    

//End of Employee Section
    
//Warehouse Section    

        

    

    

//End of Warehouse Section
    
    
//Agreement Section    

    

    

    
//End of Agreement Section    
    
//Main System Section    
    public static void login(){
        try{
            System.out.println("\n-----------LOGIN-----------");
            Scanner sc = new Scanner(System.in);
            System.out.print("Employee(0), Member(1): "); int check = sc.nextInt();
            System.out.print("Username: "); String username = sc.next();
            System.out.print("Password: "); String password = sc.next();
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String sql ="";
            if(check == 1){
                sql = "SELECT * FROM MYDB.MEMBER WHERE ACCID='"+username+"' and ACCPASS='"+password+"'";
            }else if(check == 0){
                sql = "SELECT * FROM MYDB.EMPLOYEE WHERE EMPID='"+username+"' and EMPPASS='"+password+"'";
            }else{
                System.out.println("You have to select 1 or 0 only!");
            }            
           
            
           if(check==1){ 
                ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            long memId = rs.getLong(1);
            String accId = rs.getString(2);
            String accPass = rs.getString(3);
            String bankName = rs.getString(4);
            String bankId = rs.getString(5);
            String name = rs.getString(6);
            String address = rs.getString(7);
            String gender = rs.getString(8);
            String phoneNumber = rs.getString(9);
            objMapping(memId, accId, accPass, bankName, bankId, name, address, phoneNumber); 
           
           }else if(check==0){
                  ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            long memId = rs.getLong(1);
          //  String accId = rs.getString(2);
            String accPass = rs.getString(9);
            String bankName = rs.getString(2);
            String bankId = rs.getString(8);
            String name = rs.getString(4);
            String address = rs.getString(5);
            String gender = rs.getString(7);
            String phoneNumber = rs.getString(6);
               objMappingEmployee(memId, memId, bankName, bankId, name, address, phoneNumber);
           }
           
            System.out.println("-----------LOGIN SUCCESS----------");
            cnb.close();
        }
        catch(SQLException err){
            System.out.println("Username or Password is wrong, Please try again or contect adminstator.");
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }
    }
    
    public static void objMapping(long memId, String accId, String accPass, String bankName, String bankId, String name, String address, String phoneNumber){    
        Member mem = new Member(memId, accId, accPass, bankName, bankId, name, address, phoneNumber);
        System.out.println("Hello "+mem.getName()+"\nHow are you today? ");
    } 
     public static void objMappingEmployee(long emId, double emSalary, String emBankName, String emBankId, String name, String address, String phoneNumber){    
        Employee em = new Employee(emId, emSalary, emBankName, emBankId, name, address, phoneNumber);
        System.out.println("Hello "+em.getName()+"\nHow are you today? ");
    } 
    public static void logout(){
        mem = null;
        System.out.println("\n-----------LOGOUT SUCCESS-----------");
    }
//End of Main System Section

}
