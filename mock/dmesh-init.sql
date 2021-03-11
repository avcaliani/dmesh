-- USER TABLE
create table users (
    id bigint not null auto_increment,
    username varchar(255) unique,
    description varchar(255),
    password varchar(255),
    primary key (id)
);

-- USER DATA
insert into users (username, description, password) values ('admin'      , 'API Admin'                    , '$2a$10$HL3xKAl1qQUQ.6AOS6O8..xO1rqKOHwT0c28UT0YFIqet.wNYDgVa'); -- admin
insert into users (username, description, password) values ('de-customer', 'Customer Team - Data Engineer', '$2a$10$5rfIbcBLpVkmHA1PY7b5S.fbZfrUgUlYnDTrMH8/MkeZd1eQtDkFq'); -- 123456
insert into users (username, description, password) values ('da-customer', 'Customer Team - Data Analyst' , '$2a$10$5rfIbcBLpVkmHA1PY7b5S.fbZfrUgUlYnDTrMH8/MkeZd1eQtDkFq'); -- 123456
