DROP DATABASE if exists TheInternsClub;

CREATE DATABASE TheInternsClub;

USE TheInternsClub;

CREATE TABLE `TheInternsClub`.`student` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(257) NOT NULL,
  `school` VARCHAR(45) NULL,
  `first` VARCHAR(45) NOT NULL,
  `last` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NULL,
  `pathPicture` VARCHAR(256) NULL,
  `resume` VARCHAR(12000) NULL,
  `bio` VARCHAR(1000) NULL,
  `university` VARCHAR(256) NULL,
  `Major` VARCHAR(256) NULL,
  `GPA` VARCHAR(256) NULL,
  `publicaccount` TINYINT NULL,
  `Resumetitle` VARCHAR(256) NULL,
  `joinDate` VARCHAR(11) NULL,
  `image` LONGBLOB NULL,
  PRIMARY KEY (`username`, `password`, `first`, `last`));

CREATE TABLE `TheInternsClub`.`professor` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(257) NOT NULL,
  `school` VARCHAR(45) NULL,
  `first` VARCHAR(45) NOT NULL,
  `last` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NULL,
  `pathPicture` VARCHAR(256) NULL,
  `bio` VARCHAR(1000) NULL,
  `joinDate` VARCHAR(11) NULL,
  `image` LONGBLOB NULL,
  `phone` VARCHAR(20) NULL,
  PRIMARY KEY (`username`, `password`, `first`, `last`));

CREATE TABLE `TheInternsClub`.`employer` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(257) NOT NULL,
  `company` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NULL,
  `position` VARCHAR(100) NULL, 
  `pathLogo` VARCHAR(256) NOT NULL,
  `phone` VARCHAR(20) NULL,
  `bio` VARCHAR(1000) NULL,
  `joinDate` VARCHAR(11) NULL,
  `image` LONGBLOB NULL,

  PRIMARY KEY (`username`, `password`, `company`, `pathLogo`));
  
  CREATE TABLE `TheInternsClub`.`jobs` (
  `JOBID` INT NOT NULL,
  `TITLE` VARCHAR(100) NOT NULL,
  `EMPLOYER` VARCHAR(100) NOT NULL,
  `DESCRIPTION` VARCHAR(1000) NOT NULL,
  `APPLIEDSTUDENT` VARCHAR(100) NOT NULL,
  `LETTER` VARCHAR(10000) NULL,
  `AVAILABLE` TINYINT NOT NULL);
  
  CREATE TABLE `TheInternsClub`.`notification` (
  `username` VARCHAR(100) NOT NULL,
  `message` VARCHAR(1000) NOT NULL,
  `r` TINYINT NULL);


