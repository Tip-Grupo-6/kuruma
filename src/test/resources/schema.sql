/*! SET storage_engine=INNODB */;

CREATE TABLE IF NOT EXISTS car(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    brand varchar(256),
    model varchar(256),
    `year` INTEGER,
    color varchar(20),
    image varchar(256),
    is_deleted boolean
);

CREATE TABLE IF NOT EXISTS car_item(
     id INTEGER PRIMARY KEY AUTO_INCREMENT,
     car_id INTEGER,
     name varchar(100),
     last_change date,
     next_change_due date,
     replacement_frequency INTEGER,
     due_status boolean,
     is_deleted boolean,
     FOREIGN KEY(car_id) REFERENCES car(id)
);