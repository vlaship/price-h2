-- create schema if not exists price_db collate utf8_general_ci;

create table product (
	id bigint auto_increment primary key,
	brand varchar(255) null,
	category varchar(255) null,
	existm varchar(255) null,
	existp varchar(255) null,
	existv varchar(255) null,
	name_product varchar(255) null,
	our_price decimal(19,2) null,
	recommended_price decimal(19,2) null,
	vendor_code varchar(255) null
);

create table user (
	id bigint auto_increment primary key,
	name varchar(255) not null,
	password varchar(255) not null,
	role varchar(255) null,
	constraint unique_name unique (name)
);

