create database if not exists jdbc;

use jdbc;

create table users
(
	id int auto_increment,
    name varchar(45) not null,
    
    constraint pk_users primary key(id)
);

insert into users (name) values ('Aluno Fiap');

create table if not exists products
(
	id int auto_increment,
    product_name varchar(45) not null,
    products_left int not null,
    
    constraint pk_products primary key (id)
);

insert into products (product_name, products_left) values ('Notebook', 100);

create table if not exists sold_products
(
	sold_count int not null
);

insert into sold_products values (0);
