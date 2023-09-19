create database kuruma;

\c kuruma;

create table car(
    id INT GENERATED ALWAYS AS IDENTITY,
    brand varchar(256),
    model varchar(256),
    year int,
    color varchar(20),
    image varchar(256),
    is_deleted boolean,
    PRIMARY KEY(id)
);

create table car_item(
    id INT GENERATED ALWAYS AS IDENTITY,
    car_id int,
    name varchar(100),
    last_change date,
    next_change_due date,
    replacement_frequency int,
    due_status boolean,
    is_deleted boolean,
    PRIMARY KEY(id),
    CONSTRAINT fk_customer
        FOREIGN KEY(car_id)
            REFERENCES car(id)
)