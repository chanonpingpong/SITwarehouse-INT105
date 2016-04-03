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
            String size = rs.getString(2);
            String price = rs.getString(3);
            String status = rs.getString(4);
            
            System.out.println("ID: "+warehouseId+"\nSize: "+size+"\nPrice: "+price+"\nStatus: "+status);
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
        try{
            System.out.println("\n-----------SELECT WAREHOUSE TO CHANGE BY ID-----------");
            Scanner sc = new Scanner(System.in);
            System.out.print("Warehouse ID: "); long whId = sc.nextLong();
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String sqlSelect = "SELECT * FROM MYDB.WAREHOUSE WHERE WAREHOUSEID="+whId;
            ResultSet rs = stmt.executeQuery(sqlSelect);
            rs.next();
            long warehouseId = rs.getLong(1);
            String size = rs.getString(2);
            Double price = rs.getDouble(3);
            String status = rs.getString(4);
            System.out.println("---- BEFORE CHANGE ----");
            System.out.println("ID: "+warehouseId+"\nSize: "+size+"\nPrice: "+price+"\nStatus: "+status);
            cnb.close();
            
            System.out.println("\n---- INPUT FOR CHANGE TO ----");
            System.out.print("Size: "); String inputSize = sc.next().toUpperCase();
            System.out.print("Price: "); Double inputPrice = sc.nextDouble();
            System.out.print("Status: "); String inputStatus = sc.next().toUpperCase();
            String reSize = "", reStatus = "";
            Double rePrice = 0.0;
            if(inputSize.equalsIgnoreCase("n")){
                reSize = size;
            }else{
                reSize = inputSize;
            }
            if(inputPrice == -1){
                rePrice = price;
            }else{
                rePrice = inputPrice;
            }
            if(inputStatus.equalsIgnoreCase("n")){
                reStatus = status;
            }else{
                reStatus = inputStatus;
            }
            String sqlUpdate = "UPDATE MYDB.WAREHOUSE SET SIZE='"+reSize+"',PRICE="+rePrice+",STATUS='"+reStatus+"' "
                    + "WHERE WAREHOUSEID="+warehouseId;
            Connection cnb2 = ConnectionBuilder.connect();
            Statement stmt2 = cnb2.createStatement();
            stmt2.executeUpdate(sqlUpdate);
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
    
    public static void listWarehouse(){
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
//End of Warehouse Section
    
    
//Agreement Section    
    public static void createAgreement(long warehouseId, long memId,String startDate, String endDate, double nextPaid, String nPaidDate, double arrears){
        try{
                Connection cnb = ConnectionBuilder.connect();
                String SQL = "INSERT INTO AGREEMENT VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = cnb.prepareStatement(SQL);
                pstmt.setLong(1, genAmtId());
                pstmt.setLong(2, memId);
                pstmt.setLong(3, warehouseId);
                pstmt.setDouble(4, 250000);
                pstmt.setString(5, startDate);
                pstmt.setString(6, endDate);
                pstmt.setString(7, nPaidDate);
                pstmt.setDouble(8, arrears);
                pstmt.setDouble(9, nextPaid);
                pstmt.executeUpdate();
                pstmt.close();
                System.out.println("----------- AGREEMENT ADDED -----------");
        }
        catch(SQLException err){
            System.err.println(err);
        }
        catch(ClassNotFoundException err){
            System.err.println(err);
        }         
    }
    
    public static long genAmtId(){
        try{
            String SQL = "SELECT * FROM MYDB.SYSTEMDB";
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            long id = rs.getLong(6);
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
    
    public static void getAgreementDetailsByMemId(){
        try{
            System.out.println("\n----------- GET AGREEMENT DETAILS -----------");
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
    
    public static void getAgreementDetailsByAmtId(){
        try{
            System.out.println("\n----------- GET AGREEMENT DETAILS -----------");
            Scanner sc = new Scanner(System.in);
            System.out.print("Agreement ID: "); long amtId = sc.nextLong();
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            String sql = "SELECT * FROM MYDB.AGREEMENT WHERE AMTID="+amtId;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
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
        }
        catch(SQLException err){
            System.out.println("Wrong Member Id, Please try again.");
        }
        catch(ClassNotFoundException err){
            System.out.println(err);
        }        
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
