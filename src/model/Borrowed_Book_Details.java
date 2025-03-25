package model;

public class Borrowed_Book_Details {
    private String ID_Book;
    private String ID_Loan_slip;
    
    public Borrowed_Book_Details(){

    }
    public Borrowed_Book_Details(String ID_Book, String ID_Loan_slip){
        this.ID_Book = ID_Book;
        this.ID_Loan_slip = ID_Loan_slip;
    }

    public String getID_Book(){
        return this.ID_Book;
    }
    public void setID_Book(String ID_Book){
        this.ID_Book = ID_Book;
    }

    public String getID_Loan_slip(){
        return this.ID_Loan_slip;
    }
    public void setID_Loan_slip(String ID_Loan_slip){
        this.ID_Loan_slip = ID_Loan_slip;
    }
}
