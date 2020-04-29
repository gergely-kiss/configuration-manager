create table registered_application
(
    application_id varchar(255) not null primary key,
    password varchar(255),
    role varchar(255)
);

create table app_property
(
    id serial not null primary key,
    app_id varchar(255) not null,
    property_key varchar(255),
    property_value varchar(255)
);

create table registered_password
(
    application_info varchar(255) not null primary key
);
