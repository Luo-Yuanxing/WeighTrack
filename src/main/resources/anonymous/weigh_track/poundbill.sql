create table poundbill
(
    id               int auto_increment
        primary key,
    IOType           int                                  null,
    coalType         varchar(30)                          null,
    plateNumber      varchar(30)                          null,
    grossWeight      float                                null,
    tareWeight       float                                null,
    netWeight        float                                null,
    primaryWeight    float                                null,
    ProfitLossWeight float                                null,
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
    printed          tinyint(1) default 0                 null
);

