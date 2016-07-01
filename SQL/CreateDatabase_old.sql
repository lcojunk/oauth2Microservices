CREATE DATABASE IF NOT EXISTS demoreferenzer CHARACTER SET utf8;
GRANT ALL ON demoreferenzer.* TO 'demoreferenzer'@'%' IDENTIFIED BY 'zergling' WITH GRANT OPTION;
CREATE TABLE IF NOT EXISTS demoreferenzer.reference
(
	ID int NOT NULL AUTO_INCREMENT,
	Name varchar(255),
	Description longtext,
	PRIMARY KEY (ID)
);
insert into demoreferenzer.reference (Name,Description) values("Serah Farron", "Final Fantasy XXIII");
insert into demoreferenzer.reference (Name,Description) values("Clair Farron", "Final Fantasy XXIII");
insert into demoreferenzer.reference (Name,Description) values("Shepard", "Mass Effect 1-3");
