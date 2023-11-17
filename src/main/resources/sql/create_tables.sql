create database kuruma;

\c kuruma;

CREATE TYPE RoleEnum AS ENUM('USER', 'ADMIN');
CREATE CAST (character varying AS RoleEnum) WITH INOUT AS ASSIGNMENT;

create table users(
      id INT GENERATED ALWAYS AS IDENTITY,
      name varchar(256) NOT NULL,
      email varchar(256) NOT NULL,
      password varchar(256) NOT NULL,
      role RoleEnum NOT NULL,
      is_deleted boolean,
      created_at date,
      updated_at date,
      PRIMARY KEY(id),
      UNIQUE (email)
);

create table car(
    id INT GENERATED ALWAYS AS IDENTITY,
    user_id INT NOT NULL,
    brand varchar(256),
    model varchar(256),
    year int,
    color varchar(20),
    image varchar(256),
    kilometers varchar(256),
    is_deleted boolean,
    created_at date,
    updated_at date,
    PRIMARY KEY(id),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
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
    notificated_at  date,
    created_at date,
    updated_at date,
    PRIMARY KEY(id),
    CONSTRAINT fk_maintenance_item FOREIGN KEY(maintenance_item_id) REFERENCES maintenance_item(id),
    CONSTRAINT fk_car FOREIGN KEY(car_id) REFERENCES car(id)
);

