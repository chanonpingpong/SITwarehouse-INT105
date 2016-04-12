package sitwarehouse;

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

    @Override
    public String toString() {
        return "Employee{" + "emId=" + emId + ", emSalary=" + emSalary + ", emBankName=" + emBankName + ", emBankId=" + emBankId + '}';
    }

            
    
}
