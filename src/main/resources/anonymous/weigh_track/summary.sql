create table summary
(
    id        int auto_increment
        primary key,
    date      datetime     not null,
    coalType1 double       null,
    desc1     varchar(255) null,
    desc2     varchar(256) null
);

