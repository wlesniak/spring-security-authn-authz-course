
INSERT into users values ('snakamoto','{noop}bitcoin','snakamoto@gmail.com',true);
INSERT into users values ('user','{noop}password','user@gmail.com',true);
INSERT into users values ('admin','{noop}god','admin@gmail.com',true);
INSERT into authorities values ('user','USER');
INSERT into authorities values ('admin','ADMIN');
INSERT into TRANSACTIONS values (1, 'user', 'BTC', 'BUY',1,5000);
INSERT into TRANSACTIONS values (2, 'user', 'BTC', 'BUY',3.5,5500);
INSERT into TRANSACTIONS values (3, 'user', 'LTC', 'BUY',100,50);
INSERT into TRANSACTIONS values (4, 'user', 'BTC', 'BUY',0.5,4000);
INSERT into TRANSACTIONS values (5, 'user', 'LTC', 'BUY',31,70);
INSERT into TRANSACTIONS values (6, 'user', 'BTC', 'BUY',4,5000);
