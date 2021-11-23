drop database if exists db_sneakers_shop
go

create database db_sneakers_shop
go

use db_sneakers_shop
go

-- Brand (id, name, email)
create table Brand (
	id int primary key identity, 
	name nvarchar(100), 
	email varchar(200),
	logo nvarchar(MAX) null default 'img/brand/logo.png'
)

-- Product (id, name, idBrand, image, describe, quantity, cost, saleDate)
create table Product(
	id int primary key identity, 
	idBrand int references brand(id),
	name nvarchar(200), 
	image nvarchar(max), 
	describe nvarchar(max), 
	quantity int check (quantity >=0 ), 
	cost real check (cost >= 0), 
	saleDate date default getdate()
)

-- Guess (phoneNumber, fullname, sex, address, email)
create table Guess (
	phoneNumber varchar(10) primary key,
	fullname nvarchar(200),
	sex varchar(6) check (sex = 'Nam' or sex = N'Nữ'), 
	address nvarchar(max), 
	email varchar(max),
	totalCost real not null default 0,
	discount int not null default 0 check (discount <=30)
)

-- Account (id, username, password, fullname, avatar, phoneNumber, address, sex, dateOfBirth, isAdmin)
create table Account (
	id int primary key identity, 
	username varchar(50) unique, 
	password varchar(100) check (len(password) >= 8), 
	fullname nvarchar(200), 
	avatar nvarchar(max) default 'img/avatar/avatar.png', 
	phoneNumber varchar(10), 
	address nvarchar(max), 
	sex varchar(3) check (sex = 'Nam' or sex = N'Nữ'),
	dateOfBirth date check (datediff(year, dateOfBirth, getdate()) >= 18), 
	isAdmin bit default 0
)

-- Order (id, staffName, phoneNumGuess, cost, purchaseDate)
create table [Order] (
	id int primary key identity, 
	staffName varchar(50) references Account(username), 
	phoneNumGuess varchar(10) references Guess(phoneNumber), 
	cost real not null default 0 check (cost >= 0),  
	purchaseDate date not null default getdate()
)

-- OrderDetail (id, idOrder, idProduct, quantity, cost)
create table OrderDetail (
	id int primary key identity, 
	idOrder int references [Order](id), 
	idProduct int references Product(id), 
	quantity int check (quantity >= 0), 
	cost real not null default 0 check (cost >= 0)
)
