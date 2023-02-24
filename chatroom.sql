-- Active: 1677199975569@@192.168.1.18@3306@chatroom
CREATE DATABASE if not EXISTS chatroom;
use chatroom;
CREATE Table if NOT exists user(
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key',
    username VARCHAR(255) NOT NULL COMMENT 'Username',
    password VARCHAR(255) NOT NULL COMMENT 'Password',
    type VARCHAR(100) NOT NULL COMMENT 'Type',
    create_time DATETIME COMMENT 'Create Time'
);

insert into user values(1,"leesure","123456","manager", now());
insert into user values(2,"jay","zhou","worker", now());
insert into user values(3,"eason","chen","singer", now());