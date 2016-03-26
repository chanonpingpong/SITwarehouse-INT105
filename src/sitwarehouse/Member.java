/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitwarehouse;
/**
 *
 * @author Superz
 */
public class Member extends People {
    private long memId;
    private String accId;
    private String accPass;
    private String bankName;
    private String bankId;
    
    public enum status{
        INUSE, BANNED, STANDBY, GOOD 
    }

    public Member(long memId, String accId, String accPass, String bankName, String bankId, String name, String address, String phoneNumber) {
        super(name, address, phoneNumber);
        this.memId = memId;
        this.accId = accId;
        this.accPass = accPass;
        this.bankName = bankName;
        this.bankId = bankId;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
     
    @Override
    public String toString() {
        return "Member{" + "memId=" + memId + ", accId=" + accId + ", accPass=" + accPass + ", bankName=" + bankName + ", bankId=" + bankId + '}';
    }
    
    
    
}
