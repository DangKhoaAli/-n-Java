package model;

public class Books {
    private String ID;
    private String name;
    private String author;
    private String category;
    private int quanlity;
    private float price;
    private float loan_fee;
    private String exist;

    public Books(){

    }
    public Books(String ID, String name, String author, String category, int quanlity, float price, float loan_fee, String exist){
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.category = category;
        this.quanlity = quanlity;
        this.price = price;
        this.loan_fee = loan_fee;
        this.exist = exist;
    }

    public String getID(){
        return this.ID;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getAuthor(){
        return this.author;
    }
    public void setAuthor(String author){
        this.author = author;
    }

    public String getCategory(){
        return this.category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public int getQuanlity() {
        return this.quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public float getPrice(){
        return this.price;
    }
    public void setPrice(float price){
        this.price = price;
    }

    public float getLoan_fee(){
        return this.loan_fee;
    }
    public void setLoan_fee(float loan_fee){
        this.loan_fee = loan_fee;
    }

    public String getExist(){
        return this.exist;
    }
    public void setExist(String exist){
        this.exist = exist;
    }
}
