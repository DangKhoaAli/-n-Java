package model;

import java.time.LocalDate;

public class person{
    private String ID;
    private String name;
    private String gender;
    private LocalDate birth;
    private String address;
    private String phone;
    private String email;
    private String exist;
    
    public person(){

    }
    public person(String ID, String name, String gender, LocalDate birth, String address, String phone, String email, String exist){
        this.ID = ID;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.exist = exist;
    }

// ------Các phương thức Get/Set--------//

    public String getID(){
        return this.ID;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public String getGender(){
        return this.gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }

    public LocalDate getBirth(){
        return this.birth;
    }
    public void setBirth(LocalDate birth){
        this.birth = birth;
    }

    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public String getPhone(){
        return this.phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getExist(){
        return this.exist;
    }
    public void setExist(String exist){
        this.exist = exist;
    }
    
}
