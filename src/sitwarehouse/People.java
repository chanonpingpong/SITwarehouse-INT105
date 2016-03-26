/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sitwarehouse;
import java.sql.*;

/**
 *
 * @author Superz
 */
public class People {
    private String name;
    private String address;
    private String phoneNumber;
    
    public enum gender {
        MALE, FEMALE
    }

    public People(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "People{" + "name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber + '}';
    }


    
    
}
