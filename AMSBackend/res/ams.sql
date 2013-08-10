drop table returnitem;
drop table return;
drop table leadsinger;
drop table hassong;
drop table purchaseitem;
drop table purchase;
drop table customer;
drop table item;
drop sequence receiptid;
drop sequence retid;

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
	title varchar2(255) not null,
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
 
create table ams_return (
	returnid number(7) not null PRIMARY KEY,
	return_date date,
	receiptid number(7),
	CONSTRAINTS return_receiptid_fk
		FOREIGN KEY(receiptid)
		REFERENCES purchase(receiptid)
		ON DELETE CASCADE
); 

create sequence retid 
	MINVALUE 1
	START WITH 1
	INCREMENT BY 1
	CACHE 10
;

create table returnitem (
	upc varchar2(10) not null,
	returnid number(7) not null,
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

insert into Purchase values (1, '10-JUL-2013', 'Gary', 0000012321301234, '01-MAY-2015', null, null);
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

/* daily sales report */
insert into Customer values ('ssnake', 'David', 'Philanthropy Nomad Airship', 'plissken', '5551043');
insert into Customer values ('Scott', 'Scott Summers', '677 Xavier Academy', 'cyclops', '9993213');
insert into Customer values ('MJ', 'Mary Jane Watson', '34 Broadway St.', 'spidey', '2119983');

insert into Purchase values (9, '01-AUG-2013', 'ssnake', 75648487383023, '01-JAN-2019', '18-JAN-2013', null);
insert into Purchase values (10, '01-AUG-2013', 'Scott', null, null, null, null);
insert into Purchase values (11, '01-AUG-2013', 'MJ', 11183763336337, '15-SEP-2016', '05-APR-2013', '04-APR-2013');

insert into Item values ('2244', 'Beethoven''s Ninth', 'DVD', 'classical', 'The Classical Music Company', 2012, 10.50, 25);
insert into Item values ('2555', 'The Four Seasons', 'CD', 'classical', 'The Classical Music Company', 2012, 5.00, 5);
insert into Item values ('3455', 'Grand Waltz Brilliante', 'DVD', 'classical', 'The Classical Music Company', 2012, 20.00, 30);
insert into Item values ('1255', 'White Album', 'CD', 'pop', 'The Beatles', 2012, 20.00, 60);
insert into Item values ('5666', 'Billie Jean', 'DVD', 'pop', 'Black or White', 2012, 10.00, 35);
insert into Item values ('1244', 'Dark Side of the Moon', 'DVD', 'rock', 'Pink Floyd Co.', 2012, 2.50, 12);
insert into Item values ('8844', 'Iron Maiden', 'DVD', 'rock', 'ROCK MUSIC INC.', 2012, 5.00, 65);

INSERT INTO "ITEM" VALUES ('1', 'Eragon', 'cd', 'rock', 'test', 2000, 35, 46);
INSERT INTO "ITEM" VALUES ('2', 'Crazy frog', 'dvd', 'pop', 'test', 2001, 53.50, 82);
INSERT INTO "ITEM" VALUES ('3', 'Moon', 'cd', 'rap', 'test', 2002, 25, 50);
INSERT INTO "ITEM" VALUES ('4', 'Crazy', 'dvd', 'country', 'test', 2003, 27, 48);
INSERT INTO "ITEM" VALUES ('5', 'Found It', 'cd', 'new age ', 'test', 2004, 30, 50);
INSERT INTO "ITEM" VALUES ('7', 'Gear up', 'dvd', 'instrumental', 'test', 2005, 31, 50);
INSERT INTO "ITEM" VALUES ('8', 'vande matram', 'cd', 'rock', 'test', 2007, 32, 43);

insert into PurchaseItem values (9, '2244', 2);
insert into PurchaseItem values (10, '2244', 6);
insert into PurchaseItem values (11, '2244', 2);
insert into PurchaseItem values (9, '2555', 2);
insert into PurchaseItem values (11, '2555', 3);
insert into PurchaseItem values (11, '3455', 1);
insert into PurchaseItem values (10, '1255', 5);
insert into PurchaseItem values (9, '5666', 10);
insert into PurchaseItem values (10, '1244', 20);
insert into PurchaseItem values (10, '8844', 20);
insert into PurchaseItem values (11, '8844', 20);
insert into PurchaseItem values (9, '8844', 10);

grant select on item to public;
grant select on returnitem to public;
grant select on leadsinger to public;
grant select on hassong to public;
grant select on return to public;
grant select on purchaseitem to public;
grant select on purchase to public;
grant select on customer to public;


create or replace trigger insert_login 
after insert on customer for each row 
begin 
insert into login values(:new.cid,:new.cust_passowrd,'customer');
end;

