package model;

import java.time.LocalDate;

public class Loan_slip {
    private String ID;
    private String ID_Reader;
    private String ID_Staff;
    private int so_luong;
    private LocalDate Borrow_Date;
    private LocalDate Expected_Date;
    private float fee;

    public Loan_slip(){

    }
    public Loan_slip(String ID, String ID_Reader, String ID_Staff, int so_luong, LocalDate Borrow_Date, LocalDate Expected_Date, float fee){
        this.ID = ID;
        this.ID_Reader = ID_Reader;
        this.ID_Staff = ID_Staff;

        this.Borrow_Date = Borrow_Date;
        this.Expected_Date =Expected_Date;
        this.fee = fee;
    }

    public String getID(){
        return this.ID;
    }

    public String getID_Reader(){
        return this.ID_Reader;
    }
    public void setID_Reader(String ID_Reader){
        this.ID_Reader =ID_Reader;
    }

    public String getID_Staff(){
        return this.ID_Staff;
    }
    public void setID_Staff(String ID_Staff){
        this.ID_Staff = ID_Staff;
    }

    public int getSo_luong(){
        return this.so_luong;
    }
    public void setSo_luong(int so_luong){
        this.so_luong = so_luong;
    }

    public LocalDate getBorrow_Day(){
        return this.Borrow_Date;
    }
    public void setBorrow_Day(LocalDate Borrow_Date){
        this.Borrow_Date = Borrow_Date;
    }

    public LocalDate getEXpected_Date(){
        return this.Expected_Date;
    }
    public void setExpected_Date(LocalDate Expected_Date){
        this.Expected_Date = Expected_Date;
    }

    public float getFee(){
        return this.fee;
    }
    public void setFee(float fee){
        this.fee = fee;
    }


}
