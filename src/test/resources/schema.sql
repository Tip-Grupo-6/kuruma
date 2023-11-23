/*! SET storage_engine=INNODB */;
create table users(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name varchar(256) NOT NULL,
    email varchar(256) NOT NULL,
    password varchar(256) NOT NULL,
    role varchar(256),
    is_deleted boolean,
    created_at date,
    updated_at date,
    PRIMARY KEY(id),
    UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS car(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    brand varchar(256),
    model varchar(256),
    `year` INTEGER,
    color varchar(20),
    image varchar(256),
    kilometers varchar(256),
    is_deleted boolean,
    created_at date,
    updated_at date,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS maintenance_item(
     id INTEGER PRIMARY KEY AUTO_INCREMENT,
     code varchar(30) NOT NULL,
     description varchar(256) NOT NULL,
     replacement_frequency INT NOT NULL,
     created_at date,
     updated_at date
);

CREATE TABLE IF NOT EXISTS car_item(
     id INTEGER PRIMARY KEY AUTO_INCREMENT,
     car_id INTEGER,
     maintenance_item_id INT NOT NULL,
     last_change date,
     is_deleted boolean,
     created_at date,
     updated_at date,
     FOREIGN KEY(car_id) REFERENCES car(id),
     FOREIGN KEY(maintenance_item_id) REFERENCES maintenance_item(id)
);

CREATE TABLE IF NOT EXISTS notification(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    maintenance_item_id INTEGER NOT NULL,
    car_id INTEGER NOT NULL,
    frequency INTEGER NOT NULL,
    message varchar(256) NOT NULL,
    is_deleted boolean,
    notificated_at  date,
    created_at date,
    updated_at date,
    FOREIGN KEY(maintenance_item_id) REFERENCES maintenance_item(id),
    FOREIGN KEY(car_id) REFERENCES car(id)
);

CREATE TABLE IF NOT EXISTS suscription(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    endpoint varchar(512),
    `key` varchar(256),
    auth varchar(100),
    is_deleted boolean,
    created_at date,
    updated_at date,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

-------------------------- INSERTS --------------------------

INSERT INTO maintenance_item(code, description, replacement_frequency) VAlUES('OIL', 'Aceite', 6);
INSERT INTO maintenance_item(code, description, replacement_frequency) VAlUES('WATER', 'Agua', 3);
INSERT INTO maintenance_item(code, description, replacement_frequency) VAlUES('TIRE_PRESSURE', 'Presión de neumáticos', 2);

INSERT INTO users (id, name, email, password, role, is_deleted, created_at, updated_at) VALUES(1, 'user', 'mail@mail.com', '$2a$10$LJXsgDicTbiFhrANBn4lv.Rn95sWk49Iwdu/hSX8C.ebTuhBPG4Xa', 'USER', false, '2023-11-10', '2023-11-10');