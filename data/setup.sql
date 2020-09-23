DROP TABLE IF EXISTS lineStation;

CREATE TABLE lineStation(
    line    STRING NOT NULL,
    station STRING NOT NULL
);

INSERT INTO lineStation values
("Central", "Notting Hill Gate"),
("Central", "Bond Street"),
("Jubilee", "Bond Street"),
("Jubilee", "Green Park"),
("Jubilee", "Westminster"),
("Jubilee", "London Bridge"),
("Northern", "London Bridge"),
("Northern", "King's Cross St Pancras");