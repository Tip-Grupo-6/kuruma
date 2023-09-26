/*! SET storage_engine=INNODB */;

CREATE TABLE IF NOT EXISTS car(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    brand varchar(256),
    model varchar(256),
    `year` INTEGER,
    color varchar(20),
    image varchar(256),
    kilometers varchar(256),
    is_deleted boolean
);

CREATE TABLE IF NOT EXISTS maintenance_item(
     id INTEGER PRIMARY KEY AUTO_INCREMENT,
     code varchar(30) NOT NULL,
     description varchar(256) NOT NULL,
     replacement_frequency INT NOT NULL
);

CREATE TABLE IF NOT EXISTS car_item(
     id INTEGER PRIMARY KEY AUTO_INCREMENT,
     car_id INTEGER,
     maintenance_item_id INT NOT NULL,
     last_change date,
     is_deleted boolean,
     FOREIGN KEY(car_id) REFERENCES car(id),
     FOREIGN KEY(maintenance_item_id) REFERENCES maintenance_item(id)
);


-------------------------- INSERTS --------------------------

INSERT INTO maintenance_item(code, description, replacement_frequency) VAlUES('OIL', 'Aceite', 6);
INSERT INTO maintenance_item(code, description, replacement_frequency) VAlUES('WATER', 'Agua', 3);
INSERT INTO maintenance_item(code, description, replacement_frequency) VAlUES('TIRE_PRESSURE', 'Presión de neumáticos', 2);