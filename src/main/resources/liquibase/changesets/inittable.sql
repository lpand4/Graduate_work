--Создание таблицы объектов в случае ее отсутсвия
CREATE TABLE IF NOT EXISTS place_of_work
(
    object_id BIGSERIAL PRIMARY KEY,
    name_of_object VARCHAR(255) NOT NULL,
    address_of_object VARCHAR(255)
    );
--Создание таблицы вентиляционных систем в случае ее отсутсвия
CREATE TABLE IF NOT EXISTS ventilation_system
(
    ventilation_system_id BIGSERIAL PRIMARY KEY,
    object_id INT,
    FOREIGN KEY (object_id) REFERENCES place_of_work,
    name_of_system VARCHAR(255) NOT NULL,
    full_air_volume INT NOT NULL
    );
--Создание таблицы точек измерения системы в случае ее отсутсвия
CREATE TABLE IF NOT EXISTS points
(
    point_id BIGSERIAL PRIMARY KEY,
    ventilation_system_id INT,
    FOREIGN KEY (ventilation_system_id) REFERENCES ventilation_system ,
    name_of_point VARCHAR(255) NOT NULL,
    type_measuring VARCHAR(255) NOT NULL,
    type_of_hole VARCHAR(255) NOT NULL,
    cross_sectional_area DECIMAL NOT NULL,
    air_volume DECIMAL NOT NULL,
    air_flow_rate DECIMAL NOT NULL,
    current_air_flow_rate DECIMAL,
    current_air_volume DECIMAL,
    discrepancy DECIMAL
    );
--Создание таблицы замеров в случае ее отсутсвия
CREATE TABLE IF NOT EXISTS measurements
(
    measurement_id BIGSERIAL PRIMARY KEY,
    point_id INT,
    FOREIGN KEY (point_id) REFERENCES points,
    note VARCHAR(255),
    value_of_measure DECIMAL NOT NULL
    );
