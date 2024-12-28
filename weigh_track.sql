create table coal_type
(
    id   int auto_increment
        primary key,
    name varchar(100) not null,
    constraint name
        unique (name)
);

create table poundbill
(
    id               int auto_increment
        primary key,
    IOType           char(10)                             null,
    coalType         varchar(30)                          null,
    plateNumber      varchar(30)                          null,
    grossWeight      float                                null,
    tareWeight       float                                null,
    netWeight        float                                null,
    primaryWeight    float                                null,
    profitLossWeight float                                null,
    emptyLoadTime    datetime                             null,
    fullLoadTime     datetime                             null,
    outputUnit       varchar(100)                         null,
    inputUnit        varchar(100)                         null,
    weigher          varchar(30)                          null,
    creatTime        datetime   default CURRENT_TIMESTAMP not null,
    modifyTime       datetime                             null,
    creatorId        int                                  null,
    poundID          varchar(200)                         null,
    printTime        datetime                             null,
    printed          tinyint(1) default 0                 not null,
    removed          tinyint(1) default 0                 not null comment 'ture：已删除，false：未删除'
);

create table users
(
    id        int auto_increment
        primary key,
    username  varchar(255)                       not null,
    password  varchar(255)                       not null,
    realName  varchar(255)                       not null comment '真实名称',
    lastLogin datetime default CURRENT_TIMESTAMP not null
);

