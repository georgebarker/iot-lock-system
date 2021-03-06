DROP TABLE IF EXISTS tag_room_combination;
DROP TABLE IF EXISTS room;

create table room(
    room_number VARCHAR(256) PRIMARY KEY NOT NULL,
    sensor_serial_number INT NOT NULL,
    lock_serial_number INT NOT NULL
);

CREATE TABLE IF NOT EXISTS sensor_event(
    sensor_event_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tag_id VARCHAR(256) NOT NULL,
    room_number VARCHAR(256),
    lock_serial_number INT,
    event_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    success BOOLEAN NOT NULL,
    message VARCHAR(256)
);


create table tag_room_combination(
    tag_id VARCHAR(256) NOT NULL,
    room_number VARCHAR(256) NOT NULL,    
    disabled BOOLEAN NOT NULL,
    PRIMARY KEY (tag_id, room_number),
    FOREIGN KEY (room_number) REFERENCES room(room_number)
);