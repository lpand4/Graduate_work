--Создание таблицы объектов в случае ее отсутсвия
CREATE TABLE IF NOT EXISTS placeOfWork
(
    objectId BIGSERIAL PRIMARY KEY,
    nameOfObject VARCHAR(255) NOT NULL,
    addressOfObject VARCHAR(255)
    );
--Создание таблицы вентиляционных систем в случае ее отсутсвия
CREATE TABLE IF NOT EXISTS ventilationSystem
(
    ventilationSystemId BIGSERIAL PRIMARY KEY,
    objectId INT REFERENCES placeOfWork (objectId),
    nameOfSystem VARCHAR(255) NOT NULL,
    fullAirVolume INT NOT NULL
    );
--Создание таблицы точек измерения системы в случае ее отсутсвия
CREATE TABLE IF NOT EXISTS points
(
    pointId BIGSERIAL PRIMARY KEY,
    ventilationSystemId INT REFERENCES ventilationSystem (ventilationSystemId),
    nameOfPoint VARCHAR(255) NOT NULL,
    typeMeasuring VARCHAR(255) NOT NULL,
    typeOfHole VARCHAR(255) NOT NULL,
    crossSectionalArea DECIMAL NOT NULL,
    airVolume DECIMAL NOT NULL,
    airFlowRate DECIMAL NOT NULL
    );
--Создание таблицы замеров в случае ее отсутсвия
CREATE TABLE IF NOT EXISTS measurements
(
    measurementsId BIGSERIAL PRIMARY KEY,
    pointId INT REFERENCES points (pointId),
    nameOfMeasure VARCHAR(255) NOT NULL,
    frequency DECIMAL NOT NULL,
    valueOfMeasure DECIMAL NOT NULL
    );
