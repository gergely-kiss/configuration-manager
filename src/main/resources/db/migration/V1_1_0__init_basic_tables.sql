create table app
(
    app_id varchar(255) not null primary key,
    app_info varchar(255),
    role varchar(255)
);

create table app_property
(
    id serial not null primary key,
    app_id varchar(255) not null,
    property_key varchar(255),
    property_value varchar(255)
);

create table app_info
(
    app_info varchar(255) not null primary key
);
