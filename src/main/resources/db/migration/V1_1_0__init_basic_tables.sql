create table registered_application
(
    application_id varchar(255) not null,
    password varchar(255)
);

create unique index registered_application_application_id_uindex
    on registered_application (application_id);

alter table registered_application
    add constraint registered_application_pk
        primary key (application_id);

create table configuration
(
    id serial not null,
    application_id varchar(255) not null,
    key varchar(255),
    value varchar(255)
);

create unique index configuration_id_uindex
    on configuration (id);

alter table configuration
    add constraint configuration_pk
        primary key (id);