DROP DATABASE if exists `testreferenzer`;
CREATE DATABASE IF NOT EXISTS testreferenzer CHARACTER SET utf8;
GRANT ALL ON testreferenzer.* TO 'testreferenzer'@'%' IDENTIFIED BY 'zergling' WITH GRANT OPTION; 
drop table if exists testreferenzer.reference;
drop table if exists testreferenzer.user;
drop table if exists testreferenzer.lob;
drop table if exists testreferenzer.branch;
drop table if exists testreferenzer.tech;
CREATE TABLE IF NOT EXISTS testreferenzer.lob
(
	ID int NOT NULL AUTO_INCREMENT,
	Name varchar(255),
	Description longtext,
	PRIMARY KEY (ID)
);
insert into testreferenzer.lob (Name,Description) values("Insurance", "adesso line of business");
insert into testreferenzer.lob (Name,Description) values("Banking", "adesso line of business");
insert into testreferenzer.lob (Name,Description) values("Consulting", "adesso line of business");
#select * from testreferenzer.lob;
CREATE TABLE IF NOT EXISTS testreferenzer.branch
(
	ID int NOT NULL AUTO_INCREMENT,
	Name varchar(255),
	Description longtext,
	PRIMARY KEY (ID)
);
insert into testreferenzer.branch (Name,Description) values("Versicherungen", "adesso branche");
insert into testreferenzer.branch (Name,Description) values("Gesundheitswesen", "adesso branche");
insert into testreferenzer.branch (Name,Description) values("Telekommunikation", "adesso branche");
CREATE TABLE IF NOT EXISTS testreferenzer.tech
(
	ID int NOT NULL AUTO_INCREMENT,
	Name varchar(255),
	Description longtext,
	PRIMARY KEY (ID)
);
insert into testreferenzer.tech (Name,Description) values("Java", "adesso technologie");
insert into testreferenzer.tech (Name,Description) values("Microsoft", "adesso technologie");
insert into testreferenzer.tech (Name,Description) values("Mainframe", "adesso technologie");
CREATE TABLE IF NOT EXISTS testreferenzer.user
(
	ID int NOT NULL AUTO_INCREMENT,
	Name varchar(255),
    Pass varchar(80),
    Role  varchar(255),
    FirstName varchar(255),
    LastName  varchar(255),
    Email varchar(255),
	Description longtext,
	PRIMARY KEY (ID)
);
insert into testreferenzer.user (Name,Pass, Role, FirstName, LastName, Email, Description) 
	values("j.dow","pass","ROLE_ADMIN", "John", "Dow", "j.dow@referenzer.de", "Referenzer Sysadmin");
insert into testreferenzer.user (Name,Pass, Role, FirstName, LastName, Email, Description) 
	values("j.foster","pass","ROLE_DBA", "Jane", "Foster", "j.foster@referenzer.de", "Referenzer database admin");
insert into testreferenzer.user (Name,Pass, Role, FirstName, LastName, Email, Description) 
	values("e.roberts","pass","ROLE_USER", "Eric", "Roberts", "e.roberts@referenzer.de", "Referenzer user");

CREATE TABLE IF NOT EXISTS testreferenzer.reference
(
	ID int NOT NULL AUTO_INCREMENT,
	Name varchar(255),
	Description longtext,
    ClientName varchar(255),
    ClientDescription longtext,
    lobID int,
    branchID int,
    techID int,
    creatorID int,
    approverID int,
    PersonDays int,
    Volume decimal(12,2),
    ProjectStart date,
    ProjectEnd date,
	PRIMARY KEY (ID)
);
alter table testreferenzer.reference 
	ADD CONSTRAINT FK_Lob_Reference 
    foreign key (lobID)
    references testreferenzer.lob(ID)
    on update cascade on delete cascade;
alter table testreferenzer.reference 
	ADD CONSTRAINT FK_Branch_Reference 
    foreign key (branchID)
    references testreferenzer.branch(ID)
    on update cascade on delete cascade;
alter table testreferenzer.reference 
	ADD CONSTRAINT FK_Tech_Reference 
    foreign key (techID)
    references testreferenzer.tech(ID)
    on update cascade on delete cascade;
alter table testreferenzer.reference 
	ADD CONSTRAINT FK_Creator_Reference 
    foreign key (creatorID)
    references testreferenzer.user(ID)
    on update cascade on delete cascade;
alter table testreferenzer.reference 
	ADD CONSTRAINT FK_Approver_Reference 
    foreign key (approverID)
    references testreferenzer.user(ID)
    on update cascade on delete cascade;

insert into testreferenzer.reference (Name,Description,
				ClientName, ClientDescription,
				lobID,branchID,techID,creatorID,approverID,
				PersonDays,Volume,ProjectStart,ProjectEnd) 
	values("Web-Entwicklung", "adesso Projekt für Webanwendung",
			"DKV", "Deutsche Krankenversicherung",
			1,1,1,1,2,
            10,9998.95,'2015-01-01','2015-12-31');
insert into testreferenzer.reference (Name,Description,
				ClientName, ClientDescription,
				lobID,branchID,techID,creatorID,approverID,
				PersonDays,Volume,ProjectStart,ProjectEnd) 
	values("Enterprise Content Management", "adesso Projekt für Management",
			"Deutsche Bank", "Die Deutsche Bank AG ist das nach Bilanzsumme und Mitarbeiterzahl größte Kreditinstitut Deutschlands",
			2,2,2,3,2,
            20,19998.95,'2016-01-01','2016-12-31');
insert into testreferenzer.reference (Name,Description,
				ClientName, ClientDescription,
				lobID,branchID,techID,creatorID,approverID,
				PersonDays,Volume,ProjectStart,ProjectEnd) 
	values("IT Consulting für Telekom", "adesso Projekt für Consulting",
			"Deutsche Telekom","Die Deutsche Telekom AG ist eines der größten europäischen Telekommunikationsunternehmen",
			3,3,3,2,1,
            30,29998.95,'2014-06-01','2015-12-30');

select name, firstname, lastname, email from testreferenzer.user;
select reference.Name as name, 
	lob.Name as lob,
    branch.Name as branche,
    tech.Name as tech,
    reference.PersonDays as PT,
    reference.Volume as Cost,
    reference.ProjectStart as Started
    from testreferenzer.reference, testreferenzer.branch, testreferenzer.lob, testreferenzer.tech
    where reference.branchID=branch.ID and reference.lobID=lob.ID
		and reference.techID=tech.ID;
        
select * from testreferenzer.reference;