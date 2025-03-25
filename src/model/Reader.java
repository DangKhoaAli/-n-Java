package model;

import java.time.LocalDate;

public class Reader extends person{

    private LocalDate registrationDate ;
    
    public Reader(){

    }
    public Reader(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, LocalDate registrationDate){
        super(ID, name, gender, birth, address, phone, email);
        this.registrationDate = registrationDate;
    }

    public LocalDate getRegistrationDate(){
        return this.registrationDate;
    }
    public void setRegistrationDate(LocalDate registrationDate){
        this.registrationDate = registrationDate;
    }
    
}
