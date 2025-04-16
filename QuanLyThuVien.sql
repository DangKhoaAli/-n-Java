create database QLTV;

    create table Reader(
        ID varchar(20) primary key,
        name varchar(100),
        gender enum('Nam', 'Nu'),
        birth date,
        address varchar(255),
        phone varchar(10),
        email varchar(255),
        registrationDate date, 
        exist enum('0', '1') not null,
        constraint chk_date check (registrationDate > birth)
    );

    create table Book(
        ID varchar(20) primary key,
        name varchar(255),
        author varchar(50),
        category varchar(50),
        quanlity int,
        price decimal(10,2),
        loan_fee decimal(10,2),
        exist enum ('0', '1') not null
    );

    create table Book_Details(
        ID varchar(20) primary key,
        ID_Book varchar(20),
        supplier varchar(255),
        year int,
        page_num int,
        status varchar(50),
        num_page_dama int,
        foreign key (ID_Book) references Book(ID)
    );

create table Staff(
        ID varchar(20) primary key,
        name varchar(30) not null,
        gender enum ('Nam','Nu') not null,
        birth date not null,
        address varchar(255),
        phone varchar(10),
        email varchar(100),
        wage decimal(10,2),
        exist enum('0', '1') not null,
        matkhau varchar(20),
        check(wage >= 0)
    );

    create table Loan_slip(
        ID varchar(20) primary key,
        ID_Reader varchar(20),
        ID_Staff varchar(20),
        so_luong int,
        Borrow_Date date,
        Expected_Date date,
        loan_fee decimal(10,2),
        foreign key (ID_Reader) references Reader (ID),
        foreign key (ID_Staff) references Staff (ID)
    );

    create table Payment_slip(
        ID varchar(20) primary key,
        ID_Loan_slip varchar(20),
        ID_Staff varchar(20),
        so_luong int,
        payment_Date date,
        late_fee decimal(10,2),
        damage_fee decimal(10,2),
        foreign key (ID_Loan_slip) references Loan_slip (ID),
        foreign key (ID_Staff) references Staff(ID)
    );

    create table Borrowed_Book_Details(
        ID_Loan_slip varchar(20),
        ID_Book varchar(20),
        primary key (ID_Loan_slip, ID_Book),
        foreign key (ID_Loan_slip) references Loan_slip(ID),
        foreign key (ID_Book) references Book_Details (ID) -- Tham chiếu với mã chi tiết
    );

    create table Book_Details_Returned(
        ID_Payment_slip varchar(20),
        ID_Book varchar(20),
        status int,
        penalty_fee decimal(10,2),
        primary key(ID_Payment_slip, ID_Book),
        foreign key (ID_Payment_slip) references Payment_slip(ID),
        foreign key (ID_Book) references Book_Details(ID)
    );