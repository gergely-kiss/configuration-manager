create table registered_application
(
    application_id varchar(255) not null primary key,
    password varchar(255),
    role varchar(255)
);

create table configuration
(
    id serial not null primary key,
    application_id varchar(255) not null,
    key varchar(255),
    value varchar(255)
);

create table registered_password
(
    application_info varchar(255) not null primary key
);
