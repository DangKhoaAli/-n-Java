create database quanlithuvien;

create table Reader(
	ID varchar(20) primary key auto_increment,
    name varchar(100),
    birth date,
    address varchar(255),
    registrationDate date, 
    gender enum('Nam', 'Nu'),
    constraint chk_date check (registrationDate > birth)
);

create table supplier(
	ID varchar(20) primary key auto_increment,
    name varchar(100),
    phone varchar(10),
    address varchar(255)
);


create table Book(
	ID varchar(20) primary key auto_increment, -- mã sách theo tên từng tác phẩm
    name varchar(255),
    quanlity int,
    page_num int,
    price decimal(10,2),
    loan_fee decimal(10,2),
    constraint chk_fee check (loan_fee>=0 and price>0 and price>loan_fee)
);

create table provide(
	ID_Book varchar(20),
    ID_supplier varchar(20),
    quanlity int,
    year int,
    primary key(ID_Book, ID_supplier),
    foreign key (ID_Book) references Book(ID),
    foreign key (ID_supplier) references supplier(ID)
);

create table author(
	ID varchar(20) primary key auto_increment,
    name varchar(30)
);

create table category(
	ID varchar(20) primary key auto_increment,
    name varchar(30)
);

create table Book_Author(
	ID_Book varchar(20),
    ID_Author varchar(20),
    primary key (ID_Book, ID_Author),
    foreign key (ID_Book) references Book (ID),
    foreign key (ID_Author) references author(ID)
);

create table Book_Category(
	ID_Book varchar(20),
    ID_Category varchar(20),
    primary key (ID_Book, ID_Category),
    foreign key (ID_Book) references Book(ID),
    foreign key (ID_Category) references category(ID)
);

create table Book_Details(
	ID varchar(20) primary key auto_increment, -- mã chi tiết từng cuốn sách
    ID_Book varchar(20),
    status int, -- 0: Chưa được mượn, 1: Đã được mượn, -1: Bị hỏng-Không được mượn 
    num_page_dama int,
    foreign key (ID_Book) references Book(ID)
);

create table Staff(
	ID varchar(20) primary key auto_increment,
	name varchar(30) not null,
    address varchar(255),
    birth date not null,
    gender enum ('Nam','Nu') not null,
    phone varchar(10),
    email varchar(100),
    wage decimal(10,2),
    check(wage >= 0)
);

create table Loan_slip(
	ID varchar(20) primary key auto_increment,
    ID_Reader varchar(20),
    ID_Staff varchar(20),
    Borrow_Date date,
    Expected_Date date,
    loan_fee decimal(10,2),
    foreign key (ID_Reader) references Reader (ID),
    foreign key (ID_Staff) references Staff (ID)
);

create table Payment_slip(
	ID varchar(20) primary key auto_increment,
    ID_Loan_slip varchar(20),
    ID_Staff varchar(20),
    payment_Date date,
    late_fee decimal(10,2),
    dâmge_fee decimal(10,2),
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
