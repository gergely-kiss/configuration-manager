create table "config-manager".registered_application
(
    application_id varchar(255) not null,
    password varchar(255)
);

create unique index registered_application_application_id_uindex
    on "config-manager".registered_application (application_id);

alter table "config-manager".registered_application
    add constraint registered_application_pk
        primary key (application_id);

create table "config-manager".configuration
(
    id serial not null,
    application_id varchar(255) not null,
    key varchar(255),
    value varchar(255)
);

create unique index configuration_id_uindex
    on "config-manager".configuration (id);

alter table "config-manager".configuration
    add constraint configuration_pk
        primary key (id);