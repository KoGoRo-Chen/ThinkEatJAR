-- 刪除已存在資料表
drop table if exists shareeat;

create table if not exists shareeat(
	UserId int primary key auto_increment,
    EatId int primary key auto_increment,
    EatName varchar(50),
    EatDate timestamp,
    PriceId int,
    Comment varchar(500)
);