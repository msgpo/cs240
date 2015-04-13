
drop table member;
drop table book;
drop table reading;
drop table category;
drop table category_book;


create table member
(
	id integer not null primary key autoincrement,
	name varchar(255) not null,
	email_address varchar(255) not null
);


create table book
(
	id integer not null primary key autoincrement,
	title varchar(255) not null,
	author varchar(255) not null,
	genre varchar(32) not null,
	constraint ck_genre check (genre in ('Unspecified', 'Fiction', 'NonFiction', 'HistoricalFiction')) 
);


create table reading
(
	member_id int not null,
	book_id int not null
);


create table category
(
	id integer not null primary key autoincrement,
	name varchar(255) not null,
	parent_id int
);


create table category_book
(
	category_id int not null,
	book_id int not null
);


insert into member (name, email_address) values ('Ann', 'ann@cs.byu.edu');
insert into member (name, email_address) values ('Bob', 'bob@cs.byu.edu');
insert into member (name, email_address) values ('Chris', 'chris@cs.byu.edu');

insert into book (title, author, genre) values ('Decision Points', 'George W. Bush', 'NonFiction');
insert into book (title, author, genre) values ('The Work and the Glory', 'Gerald Lund', 'HistoricalFiction');
insert into book (title, author, genre) values ('Dracula', 'Bram Stoker', 'Fiction');
insert into book (title, author, genre) values ('The Holy Bible', 'The Lord', 'NonFiction');

insert into reading (member_id, book_id) values (1, 1);
insert into reading (member_id, book_id) values (1, 2);
insert into reading (member_id, book_id) values (2, 2);
insert into reading (member_id, book_id) values (2, 3);
insert into reading (member_id, book_id) values (3, 3);
insert into reading (member_id, book_id) values (3, 4);

insert into category (name) values ('Top');
insert into category (name, parent_id) values ('Must Read', 1);
insert into category (name, parent_id) values ('Must Read (New)', 2);
insert into category (name, parent_id) values ('Must Read (Old)', 2);
insert into category (name, parent_id) values ('Must Read (Really Old)', 2);
insert into category (name, parent_id) values ('Optional', 1);
insert into category (name, parent_id) values ('Optional (New)', 3);
insert into category (name, parent_id) values ('Optional (Old)', 3);
insert into category (name, parent_id) values ('Optional (Really Old)', 3);

insert into category_book (category_id, book_id) values (7, 1);
insert into category_book (category_id, book_id) values (3, 2);
insert into category_book (category_id, book_id) values (8, 3);
insert into category_book (category_id, book_id) values (5, 4);



