package model;

public class Book_Details_Returned {
    private String ID_Book;
    private String ID_Payment_slip;
    private int status;
    private float penalty_fee;

    public Book_Details_Returned(){

    }
    public Book_Details_Returned(String ID_Book, String ID_Payment_slip, int status, float penalty_fee){
        this.ID_Book = ID_Book;
        this.ID_Payment_slip = ID_Payment_slip;
        this.status = status;
        this.penalty_fee = penalty_fee;
    }

    public String getID_Book(){
        return this.ID_Book;
    }
    public void setID_Book(String ID_Book){
        this.ID_Book = ID_Book;
    }

    public String getID_Payment_slip(){
        return ID_Payment_slip;
    }
    public void setID_Payment_slip(String ID_Payment_slip){
        this.ID_Payment_slip = ID_Payment_slip;
    }

    public int getStatus(){
        return this.status;
    }
    public void setStatus(int status){
        this.status = status;
    }

    public float getPenalty_fee(){
        return this.penalty_fee;
    }
}
