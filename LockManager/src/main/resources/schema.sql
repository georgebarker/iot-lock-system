DROP TABLE IF EXISTS tag_sensor_combination;
DROP TABLE IF EXISTS sensor_event;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS sensor;

CREATE TABLE tag(
    tag_id VARCHAR(256) PRIMARY KEY NOT NULL,
    tag_description VARCHAR(256),
    disabled BOOLEAN NOT NULL
);

CREATE TABLE sensor(
    sensor_serial_number INT PRIMARY KEY NOT NULL,
    sensor_description VARCHAR(256),
    disabled BOOLEAN NOT NULL
);

CREATE TABLE sensor_event(
    sensor_event_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tag_id VARCHAR(256) NOT NULL,
    sensor_serial_number INT NOT NULL,
    event_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    success BOOLEAN NOT NULL
);

create table tag_sensor_combination(
    tag_id VARCHAR(256) NOT NULL,
    sensor_serial_number INT NOT NULL,
    disabled BOOLEAN NOT NULL,
    PRIMARY KEY (tag_id, sensor_serial_number)
);