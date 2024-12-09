create table coal_type
(
    id        int auto_increment
        primary key,
    name      varchar(100)         not null,
    isRemoved tinyint(1) default 0 null,
    constraint name
        unique (name)
);

