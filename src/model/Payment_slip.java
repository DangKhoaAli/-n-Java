package model;

import java.time.LocalDate;

public class Payment_slip {
    private String ID;
    private String ID_Loan_slip;
    private String ID_Staff;
    private LocalDate payment_Date;
    private float late_fee;
    private float damage_fee;

    public Payment_slip(){

    }
    public Payment_slip(String ID, String ID_Loan_slip, String ID_Staff, LocalDate payment_Date, float late_fee, float damage_fee){
        this.ID = ID;
        this.ID_Loan_slip = ID_Loan_slip;
        this.ID_Staff = ID_Staff;
        this.payment_Date = payment_Date;
        this.late_fee = late_fee;
        this.damage_fee = damage_fee;
    }

    public String getID(){
        return this.ID;
    }

    public String ID_Loan_slip(){
        return this.ID_Loan_slip;
    }
    public void setID_Loan_slip(String ID_Loan_slip){
        this.ID_Loan_slip = ID_Loan_slip;
    }

    public String getID_Staff(){
        return this.ID_Staff;
    }
    public void setID_Staff(String ID_Staff){
        this.ID_Staff = ID_Staff;
    }

    public LocalDate getPayment_Date(){
        return this.payment_Date;
    }
    public void setPayment_Date(LocalDate payment_Date){
        this.payment_Date = payment_Date;
    }

    public float getLate_fee(){
        return this.late_fee;
    }
    public void setLate_fee(float late_fee){
        this.late_fee = late_fee;
    }

    public float getDamage_fee(){
        return this.damage_fee;
    }
    public void setDamage_fee(float damage_fee){
        this.damage_fee = damage_fee;
    }

}
