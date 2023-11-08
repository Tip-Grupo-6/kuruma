create database kuruma;

\c kuruma;

create table car(
    id INT GENERATED ALWAYS AS IDENTITY,
    brand varchar(256),
    model varchar(256),
    year int,
    color varchar(20),
    image varchar(256),
    kilometers varchar(256),
    is_deleted boolean,
    created_at date,
    updated_at date,
    PRIMARY KEY(id)
);

create table maintenance_item(
     id INT GENERATED ALWAYS AS IDENTITY,
     code varchar(30) NOT NULL,
     description varchar(256) NOT NULL,
     replacement_frequency INT NOT NULL,
     created_at date,
     updated_at date,
     PRIMARY KEY(id),
     UNIQUE (code)
);

create table car_item(
    id INT GENERATED ALWAYS AS IDENTITY,
    car_id INT NOT NULL,
    maintenance_item_id INT NOT NULL,
    last_change date,
    is_deleted boolean,
    created_at date,
    updated_at date,
    PRIMARY KEY(id),
    CONSTRAINT fk_car FOREIGN KEY(car_id) REFERENCES car(id),
    CONSTRAINT fk_maintenance_item FOREIGN KEY(maintenance_item_id) REFERENCES maintenance_item(id)
);
create table notification(
    id INT GENERATED ALWAYS AS IDENTITY,
    maintenance_item_id INT NOT NULL,
    car_id INT NOT NULL,
    frequency INT NOT NULL,
    message varchar(256) NOT NULL,
    is_deleted boolean,
    created_at date,
    updated_at date,
    PRIMARY KEY(id),
    CONSTRAINT fk_maintenance_item FOREIGN KEY(maintenance_item_id) REFERENCES maintenance_item(id),
    CONSTRAINT fk_car FOREIGN KEY(car_id) REFERENCES car(id)
);
create table users(
    id INT GENERATED ALWAYS AS IDENTITY,
    name varchar(256) NOT NULL,
    email varchar(256) NOT NULL,
    password varchar(256) NOT NULL,
    is_deleted boolean,
    created_at date,
    updated_at date,
    PRIMARY KEY(id),
    UNIQUE (email)
);
