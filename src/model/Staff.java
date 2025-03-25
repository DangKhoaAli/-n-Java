package model;

import java.time.LocalDate;

public class Staff extends person{
    private double wage; //Lương
    
    public Staff(){

    }
    public Staff(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, double wage){
        super(ID, name, gender, birth, address, phone, email);
        this.wage = wage;
    }

// ------Các phương thức Get/Set--------//
    
    public Double getWage(){
        return this.wage;
    }
    public void setWage(double wage){
        this.wage = wage;
    }

}