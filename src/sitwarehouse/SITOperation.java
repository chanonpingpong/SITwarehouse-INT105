package sitwarehouse;
import java.util.Scanner;
import java.sql.*;

public class SITOperation {
   private static Member mem = new Member();
//Member Section    
    public static void createMember(String name, String address, String phoneNumber , String accId, String accPass, String bankName, String bankId, int gender){
        try{
            if(haveMemAccId(accId)){
                System.out.println("This username already be used. Please change");
            }else{
                String genderType;
                if(gender == 1){
                    genderType = "M";
                }else{
                    genderType = "F";
                }
                Connection cnb = ConnectionBuilder.connect();
                String SQL = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = cnb.prepareStatement(SQL);
                pstmt.setLong(1, genMemId());
                pstmt.setString(2, accId);
                pstmt.setString(3, accPass);
                pstmt.setString(4, bankName);
                pstmt.setString(5, bankId);
                pstmt.setString(6, name);
                pstmt.setString(7, address);
                pstmt.setString(8, genderType);
                pstmt.setString(9, phoneNumber);
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
    
    public static boolean haveMemAccId(String accId) throws SQLException, ClassNotFoundException{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.MEMBER");
            boolean alrHave = false;
            while(rs.next()){
                String checkId = rs.getString(2);
                if(checkId.equalsIgnoreCase(accId)){
                    alrHave = true;
                    stmt.close();
                    return alrHave;
                }
            }
            return alrHave;
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
    
    public static void changeStatusMember(){
        
    }
    
    public static void getMemberDetails(){
        try{
            System.out.println("\n-----------GET MEMBER DETAILS-----------");
            Scanner sc = new Scanner(System.in);
            System.out.print("Member ID: "); long memId = sc.nextLong();
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String sql = "SELECT * FROM MYDB.MEMBER WHERE MEMID="+memId;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            long memberId = rs.getLong(1);
            String accId = rs.getString(2);
            String bankName = rs.getString(4);
            String bankId = rs.getString(5);
            String name = rs.getString(6);
            String addr = rs.getString(7);
            String gd = rs.getString(8);
            String phoneNumber = rs.getString(9);
            String gender = "Female";
            if(gd.equalsIgnoreCase("m")){
                gender = "Male";
            }
            System.out.println("ID: "+memberId+""
                    + "\nName: "+name+""
                    + "\nGender: "+gender+""
                    + "\nBank: "+bankName+""
                    + "\nBank Id: "+bankId+""
                    + "\nAddress: "+addr+""
                    + "\nPhone Number: "+phoneNumber);
            cnb.close();
        }
        catch(SQLException err){
            System.out.println("Wrong Member Id, Please try again.");
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }        
    }
    
    public static void listMember(){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.MEMBER");
            System.out.println("\n------------LIST OF MEMBER-----------");
            while(rs.next()){
                long memId = rs.getLong(1);
                String accId = rs.getString(2);
                String accPass = rs.getString(3);
                String memBankName = rs.getString(4);
                String memBankId = rs.getString(5);
                String memName = rs.getString(6);
                String memAddress = rs.getString(7);
                String memGender = rs.getString(8);
                String memPhoneNumber = rs.getString(9);
                System.out.println(memId+" "+accId+" "+accPass+" "+memBankName+" "+memBankId+" "+memName+" "
                        + memAddress+" "+memPhoneNumber+" "+memGender);  
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
    
// End of Member Section
    
    
// Employee Section    
    public static void createEmployee(String name, String address, String phoneNumber , String accId, String accPass, String bankName, String bankId, int gender){
        try{
            if(haveEmpAccId(accId)){
                System.out.println("This username already be used. Please change");
            }else{
                String genderType;
                if(gender == 1){
                    genderType = "M";
                }else{
                    genderType = "F";
                }
                Connection cnb = ConnectionBuilder.connect();
                String SQL = "INSERT INTO EMPLOYEE VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = cnb.prepareStatement(SQL);
                pstmt.setLong(1, genEmpId());
                pstmt.setString(2, bankName);
                pstmt.setString(3, bankId);
                pstmt.setString(4, name);
                pstmt.setString(5, address);
                pstmt.setString(6, phoneNumber);
                pstmt.setString(7, genderType);
                pstmt.setString(8, accId);
                pstmt.setString(9, accPass);
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("-----------EMPLOYEE ADDED-----------");
            }
        }
        catch(SQLException err){
            System.err.println(err);
        }
        catch(ClassNotFoundException err){
            System.err.println(err);
        }        
    }
    
    public static long genEmpId(){
        try{
            String SQL = "SELECT * FROM MYDB.SYSTEMDB";
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            long id = rs.getLong(5);
            id++;
            stmt.executeUpdate("UPDATE MYDB.SYSTEMDB SET EMPID="+id+"");
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
    
    public static boolean haveEmpAccId(String accId) throws SQLException, ClassNotFoundException{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.EMPLOYEE");
            boolean alrHave = false;
            while(rs.next()){
                String checkId = rs.getString(9);
                if(checkId.equalsIgnoreCase(accId)){
                    alrHave = true;
                    stmt.close();
                    return alrHave;
                }
            }
            return alrHave;
    }
    
    public static void changeStatusEmployee(){
        
    }
        
    public static void getEmployeeDetails(){
        try{
            System.out.println("\n-----------GET MEMBER DETAILS-----------");
            Scanner sc = new Scanner(System.in);
            System.out.print("Member ID: "); long memId = sc.nextLong();
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String sql = "SELECT * FROM MYDB.MEMBER WHERE MEMID="+memId;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            long memberId = rs.getLong(1);
            String accId = rs.getString(2);
            String bankName = rs.getString(4);
            String bankId = rs.getString(5);
            String name = rs.getString(6);
            String addr = rs.getString(7);
            String gd = rs.getString(8);
            String phoneNumber = rs.getString(9);
            String gender = "Female";
            if(gd.equalsIgnoreCase("m")){
                gender = "Male";
            }
            System.out.println("ID: "+memberId+""
                    + "\nName: "+name+""
                    + "\nGender: "+gender+""
                    + "\nBank: "+bankName+""
                    + "\nBank Id: "+bankId+""
                    + "\nAddress: "+addr+""
                    + "\nPhone Number: "+phoneNumber);
            cnb.close();
        }
        catch(SQLException err){
            System.out.println("Wrong Member Id, Please try again.");
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }        
    }
    
    public static void listEmployee(){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.EMPLOYEE");
            System.out.println("\n-----------LIST OF EMPLOYEE-----------");
            while(rs.next()){
                long empId = rs.getLong(1);
                String empBankName = rs.getString(2);
                String empBankId = rs.getString(3);
                String empName = rs.getString(4);
                String empAddress = rs.getString(5);
                String empPhoneNumber = rs.getString(6);
                String empGender = rs.getString(7);
                String empUserId = rs.getString(8);
                String empPassword = rs.getString(9);
                System.out.println(empId+" "+empUserId+" "+empName+" "+empBankName+" "+empBankId+" "+empPhoneNumber+" "
                        +empAddress+" "+empGender);  
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
//End of Employee Section
    
//Warehouse Section    
    public static void createWarehouse(){
        try{
            Scanner sc = new Scanner(System.in);
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();    
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.SYSTEMDB");
            
            System.out.println("\n-----------ADD PRODUCT-----------");
            System.out.print("Name: "); String inputName = sc.next().toUpperCase();
            System.out.print("Size: "); String inputSize = sc.next().toUpperCase();
            System.out.print("Price: "); Double inputPrice = sc.nextDouble();
            String inputStatus = "STANDBY";
            long warehouseId = 0;
            
            if(inputSize.equalsIgnoreCase("small")){
                rs.next();
                warehouseId = rs.getLong(1);
                warehouseId++;
                stmt.execute("UPDATE SYSTEMDB SET SMALLID = "+warehouseId);
            }else if(inputSize.equalsIgnoreCase("medium")){
                rs.next();
                warehouseId = rs.getLong(2);
                warehouseId++;
                stmt.execute("UPDATE SYSTEMDB SET MEDIUMID = "+warehouseId);
            }else if(inputSize.equalsIgnoreCase("large")){
                rs.next();
                warehouseId = rs.getLong(3);
                warehouseId++;
                stmt.execute("UPDATE SYSTEMDB SET LARGEID = "+warehouseId);
            }
            
            String sql = "INSERT INTO MYDB.WAREHOUSE(WAREHOUSEID, WAREHOUSENAME, SIZE, PRICE, STATUS) "
                    + "VALUES ("+warehouseId+","
                    + " '"+inputName+"',"
                    + " '"+inputSize+"',"
                    + " "+inputPrice+","
                    + " '"+inputStatus+"')";
            stmt.execute(sql);
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
    
    public static void changeStatusWarehouse(){
        
    }
    
    public static void getWarehouseDetails(){
        try{
            System.out.println("\n-----------GET WAREHOUSE DETAILS-----------");
            Scanner sc = new Scanner(System.in);
            System.out.print("Warehouse ID: "); long whId = sc.nextLong();
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String sql = "SELECT * FROM MYDB.WAREHOUSE WHERE WAREHOUSEID="+whId;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            long warehouseId = rs.getLong(1);
            String name = rs.getString(2);
            String size = rs.getString(3);
            String price = rs.getString(4);
            String status = rs.getString(5);
            
            System.out.println("ID: "+warehouseId+"\nName: "+name+"\nSize: "+size+"\nPrice: "+price+"\nStatus: "+status);
            cnb.close();
        }
        catch(SQLException err){
            System.out.println("Warehouse ID is wrong, Please try again.");
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }        
    }
    
    public static void changeWarehouseDetails(){
        
    }
    
    public static void listWarehouse(){
        try{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.WAREHOUSE");
            System.out.println("\n-----------LIST OF WAREHOUSE-----------");
            System.out.println("ID  NAME  SIZE  PRICE  STATUS");
            while(rs.next()){
                long whId = rs.getLong(1);
                String whName = rs.getString(2);
                String whSize = rs.getString(3);
                Double whPrice = rs.getDouble(4);
                String whStatus = rs.getString(5);
                System.out.println(whId+" "+whName+" "+whSize+" "+whPrice+" "+whStatus);  
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
//End of Warehouse Section
    
    
//Agreement Section    
    public static void createAgreement(){
        
    }
    
    public static void getAgreementDetails(){
        
    }
    
    public static void changeAgreementDetails(){
        
    }
    
    public static void createPermission(){
        //for member when they want to tell employee that they just paid for warehouse dept
    }
    
    public static void submitPermission(){
        // when employee checked permission from member they have to submit that it the truth
        // change the agreement for total price for that user
    }
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
    
    public static void logout(){
        mem = null;
        System.out.println("\n-----------LOGOUT SUCCESS-----------");
    }
//End of Main System Section

}
