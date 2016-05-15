package sitwarehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Employee extends People{
    private long emId;
    private double emSalary;
    private String emBankName;
    private String emBankId;
    private String empName;

    public Employee() {
    }

    public Employee(long emId, double emSalary, String emBankName, String emBankId, String name, String address, String phoneNumber) {
        super(name, address, phoneNumber);
        this.emId = emId;
        this.emSalary = emSalary;
        this.emBankName = emBankName;
        this.emBankId = emBankId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public long getEmId() {
        return emId;
    }

    public void setEmId(long emId) {
        this.emId = emId;
    }

    public double getEmSalary() {
        return emSalary;
    }

    public void setEmSalary(double emSalary) {
        this.emSalary = emSalary;
    }

    public String getEmBankName() {
        return emBankName;
    }

    public void setEmBankName(String emBankName) {
        this.emBankName = emBankName;
    }

    public String getEmBankId() {
        return emBankId;
    }

    public void setEmBankId(String emBankId) {
        this.emBankId = emBankId;
    }

    public static void createEmployee(String name, String address, String phoneNumber , String accId, String accPass, String bankName, String bankId){
        try{
            if(haveThisAccId(accId)){
                System.out.println("This username already be used. Please change");
            }else{
                Connection cnb = ConnectionBuilder.connect();
                String SQL = "INSERT INTO EMPLOYEE VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = cnb.prepareStatement(SQL);
                pstmt.setLong(1, genEmpId());
                pstmt.setString(2, bankName);
                pstmt.setString(3, bankId);
                pstmt.setString(4, name);
                pstmt.setString(5, address);
                pstmt.setString(6, phoneNumber);
                pstmt.setString(7, accId);
                pstmt.setString(8, accPass);
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

    public static boolean haveThisAccId(String accId) throws SQLException, ClassNotFoundException{
            Connection cnb = ConnectionBuilder.connect();
            Statement stmt = cnb.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.EMPLOYEE");
            boolean itsHad = false;
            while(rs.next()){
                String checkId = rs.getString(9);
                if(checkId.equalsIgnoreCase(accId)){
                    itsHad = true;
                    stmt.close();
                    return itsHad;
                }
            }
            return itsHad;
    }

    public static void getDetailsById(long empId){
        try{
            System.out.println("\n-----------GET MEMBER DETAILS-----------");
            Connection cnb = ConnectionBuilder.connect();
            String SQL = "SELECT * FROM MYDB.MEMBER WHERE MEMID=?";
            PreparedStatement ps = cnb.prepareStatement(SQL);
            ps.setLong(1, empId);
            ResultSet rs = ps.executeQuery(SQL);
            if(rs.next()){
                long employeeId = rs.getLong(1);
                String bankName = rs.getString(2);
                String bankId = rs.getString(3);
                String name = rs.getString(4);
                String addr = rs.getString(5);
                String phoneNumber = rs.getString(6);
                String accId = rs.getString(7);
                System.out.println("ID: "+employeeId+""
                        + "\nName: "+name+""
                        + "\nBank: "+bankName+""
                        + "\nBank Id: "+bankId+""
                        + "\nAddress: "+addr+""
                        + "\nPhone Number: "+phoneNumber+""
                        + "\nUsername: "+accId);
                cnb.close();                
            }else{
                System.out.println("This id does not exist.");
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM MYDB.EMPLOYEE");
            System.out.println("\n-----------LIST OF EMPLOYEE-----------");
            while(rs.next()){
                long empId = rs.getLong(1);
                String bankName = rs.getString(2);
                String bankId = rs.getString(3);
                String name = rs.getString(4);
                String addr = rs.getString(5);
                String phoneNumber = rs.getString(6);
                String accId = rs.getString(7);
                System.out.println("ID: "+empId+""
                        + " Name: "+name+""
                        + " Bank: "+bankName+""
                        + " Bank Id: "+bankId+""
                        + " Address: "+addr+""
                        + " Phone Number: "+phoneNumber+""
                        + " Username: "+accId+"\n");
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
        return "Employee{" + "emId=" + emId + ", emSalary=" + emSalary + ", emBankName=" + emBankName + ", emBankId=" + emBankId + '}';
    }

            
    
}
