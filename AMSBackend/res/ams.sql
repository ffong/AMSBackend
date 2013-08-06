drop table returnitem;
drop table return;
drop table leadsinger;
drop table hassong;
drop table purchaseitem;
drop table purchase;
drop table customer;
drop table item;
drop sequence receiptid;

ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MON-YYYY';

create table item (
	upc varchar2(10) not null PRIMARY KEY,
	title varchar2(255),
	item_type varchar2(3),
	item_category varchar2(12),
	company varchar2(50),
	item_year number(4),
	item_price number(7,2),
	item_stock number(7)
);

create table leadsinger (
	upc varchar2(10) not null,
	singer_name varchar2(50) not null,
	CONSTRAINTS leadsinger_pk 
		PRIMARY KEY (upc, singer_name),
	CONSTRAINTS leadsinger_upc_fk
		FOREIGN KEY(upc)
		REFERENCES item(upc)
		ON DELETE CASCADE
);

create table hassong (
	upc varchar2(10) not null,
	song_title varchar2(255) not null,
	CONSTRAINTS hassong_pk 
		PRIMARY KEY (upc, song_title),
	CONSTRAINTS hassong_upc_fk
		FOREIGN KEY(upc)
		REFERENCES item(upc)
		ON DELETE CASCADE
);
 
create table customer (
	cid varchar2(24) not null PRIMARY KEY,
	cust_name varchar2(50),
	cust_address varchar2(255),
	cust_password varchar2(30),
	cust_phone varchar2(13)
);

create table purchase (
	receiptid number(7) not null PRIMARY KEY,
	purchase_date date,
	cid varchar2(24),
	card_no varchar2(16),
	expiry_date date,
	expected_date date,
	delivery_date date,
	CONSTRAINTS purchase_cid_fk
		FOREIGN KEY(cid)
		REFERENCES customer(cid)
		ON DELETE CASCADE
);

create sequence receiptid 
	MINVALUE 1
	START WITH 1
	INCREMENT BY 1
	CACHE 10
;

create table purchaseitem (
	receiptid number(7) not null,
	upc varchar2(10) not null,
	quantity number(7),
	CONSTRAINTS purchaseitem_pk
		PRIMARY KEY(receiptid, upc),
	CONSTRAINTS purchaseitem_receiptid_fk
		FOREIGN KEY(receiptid)
		REFERENCES purchase(receiptid)
		ON DELETE CASCADE,
	CONSTRAINTS purchaseitem_upc_fk
		FOREIGN KEY(upc)
		REFERENCES item(upc)
		ON DELETE CASCADE
);
 
create table return (
	retid number(7) not null PRIMARY KEY,
	return_date date,
	receiptid number(7),
	CONSTRAINTS return_receiptid_fk
		FOREIGN KEY(receiptid)
		REFERENCES purchase(receiptid)
		ON DELETE CASCADE
); 

create table returnitem (
	upc varchar2(10) not null,
	retid number(7) not null,
	quantity number(7),
	CONSTRAINTS returnitem_pk
		PRIMARY KEY (upc, retid),
	CONSTRAINTS returnitem_upc_fk
		FOREIGN KEY(upc)
		REFERENCES item(upc)
		ON DELETE CASCADE,
	CONSTRAINTS returnitem_retid_fk
		FOREIGN KEY(retid)
		REFERENCES return(retid)
		ON DELETE CASCADE
);

insert into Customer values ('Jon', 'Jonathan Swift', '321 Partridge Rd.', 'password', '123213');
insert into Customer values ('Flora', 'Flora Fong', '321 Partridge Rd.', 'password', '123213');
insert into Customer values ('Gary', 'Gary Oak', '2 Pallet Town.', 'password', '123213');
insert into Customer values ('Alice', 'Alice Tiddel', '25 Worcestershire', 'password', '123213');

insert into Purchase values (1, '10-AUG-2013', 'Gary', 0000012321301234, '01-MAY-2015', null, null);
insert into Purchase values (2, '11-AUG-2013', 'Gary', 0000012321301234, '01-MAY-2015', null, null);
insert into Purchase values (3, '12-AUG-2013', 'Flora', 0000012321301234, '01-MAY-2015', '01-MAY-2015', '01-MAY-2015');
insert into Purchase values (4, '13-AUG-2013', 'Jon', 0000012321301234, '01-MAY-2015', null, null);
insert into Purchase values (5, '13-AUG-2013', 'Alice', null, null, null, null);
insert into Purchase values (6, '14-AUG-2013', 'Flora', 0000012321301234, '01-MAY-2015', '01-MAY-2015', '01-MAY-2015');
insert into Purchase values (7, '18-AUG-2013', 'Gary', 0000012321301234, '01-MAY-2015', null, null);
insert into Purchase values (8, '20-AUG-2013', 'Jon', null, null, null, null);

insert into Item values ('5', 'Burt Bacharach', 'pop', 'pop', 'Warner Music', 1998, 20.00, 5);
insert into Item values ('10', 'Zelda 25th Anniversary Symphony', 'CD', 'instrumental', 'Nintendo Music Company', 2005, 50.00, 3);
insert into Item values ('15', 'QUEEN', 'CD', 'rock', 'Skotein', 2011, 25.00, 5);
insert into Item values ('20', 'Meatloaf', 'DVD', 'rock', 'Pikachu Company', 1999, 10.00, 15);
insert into Item values ('25', 'Alice in Wonderland', 'DVD', 'new age', 'Tim Burton Inc.', 1995, 35.00, 9);
insert into Item values ('30', 'The Carpenters', 'CD', 'pop', 'Warner Music', 1978, 20.00, 1);
insert into Item values ('35', 'Persona Live Concert', 'DVD', 'pop', 'Atlus Music', 2012, 60.00, 5);

insert into PurchaseItem values (1, '15', 2);
insert into PurchaseItem values (4, '5', 2);
insert into PurchaseItem values (4, '30', 3);
insert into PurchaseItem values (5, '25', 4);
insert into PurchaseItem values (5, '30', 2);
insert into PurchaseItem values (6, '10', 1);
insert into PurchaseItem values (7, '15', 2);
insert into PurchaseItem values (2, '20', 2);

grant select on item to public;
grant select on returnitem to public;
grant select on leadsinger to public;
grant select on hassong to public;
grant select on return to public;
grant select on purchaseitem to public;
grant select on purchase to public;
grant select on customer to public;