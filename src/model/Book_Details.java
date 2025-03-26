package model;

public class Book_Details {
    private String ID_Details;
    private String ID_Book;
    private String status;
    private int page_num_dama;

    public Book_Details(){

    }
    public Book_Details(String ID_Details, String ID_Book, String status, int page_num_dama){
        this.ID_Details = ID_Details;
        this.ID_Book = ID_Book;
        this.status = status;
        this.page_num_dama = page_num_dama;
    }

    public String getID_Details() {
        return ID_Details;
    }
    public void setID_Details(String ID_Details) {
        this.ID_Details = ID_Details;
    }

    public String getID_Book() {
        return ID_Book;
    }
    public void setID_Book(String ID_Book) {
        this.ID_Book = ID_Book;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getPage_num_dama() {
        return page_num_dama;
    }
    public void setPage_num_dama(int page_num_dama) {
        this.page_num_dama = page_num_dama;
    }
}
