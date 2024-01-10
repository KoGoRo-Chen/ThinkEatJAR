-- MySQL Script generated by MySQL Workbench
-- Tue Jan  9 22:27:00 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema thinkeat
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `thinkeat` ;

-- -----------------------------------------------------
-- Schema thinkeat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `thinkeat` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `thinkeat` ;

-- -----------------------------------------------------
-- Table `thinkeat`.`cmt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`cmt` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`cmt` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `cmtContext` TEXT NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`restdata`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`restdata` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`restdata` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `resname` VARCHAR(50) NOT NULL,
  `resaddress` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`price`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`price` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`price` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `pricename` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`eatrepo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`eatrepo` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`eatrepo` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `resId` INT NULL DEFAULT NULL,
  `eatTitle` VARCHAR(100) NOT NULL,
  `eatdate` DATE NULL DEFAULT NULL,
  `priceId` INT NULL DEFAULT NULL,
  `eatrepo` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`Id`),
  INDEX `resId` (`resId` ASC) VISIBLE,
  INDEX `eatrepo_ref_price_idx` (`priceId` ASC) VISIBLE,
  CONSTRAINT `eatrepo_ibfk_1`
    FOREIGN KEY (`resId`)
    REFERENCES `thinkeat`.`restdata` (`Id`),
  CONSTRAINT `eatrepo_ref_price`
    FOREIGN KEY (`priceId`)
    REFERENCES `thinkeat`.`price` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`eatrepo_ref_cmt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`eatrepo_ref_cmt` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`eatrepo_ref_cmt` (
  `eatRepoId` INT NOT NULL,
  `cmtId` INT NOT NULL,
  PRIMARY KEY (`eatRepoId`, `cmtId`),
  INDEX `cmtId` (`cmtId` ASC) VISIBLE,
  CONSTRAINT `eatrepo_ref_cmt_ibfk_1`
    FOREIGN KEY (`eatRepoId`)
    REFERENCES `thinkeat`.`eatrepo` (`Id`),
  CONSTRAINT `eatrepo_ref_cmt_ibfk_2`
    FOREIGN KEY (`cmtId`)
    REFERENCES `thinkeat`.`cmt` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`pic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`pic` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`pic` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `picFilePath` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`eatrepo_ref_pic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`eatrepo_ref_pic` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`eatrepo_ref_pic` (
  `eatRepoId` INT NOT NULL,
  `picId` INT NOT NULL,
  PRIMARY KEY (`eatRepoId`, `picId`),
  INDEX `picId` (`picId` ASC) VISIBLE,
  CONSTRAINT `eatrepo_ref_pic_ibfk_1`
    FOREIGN KEY (`eatRepoId`)
    REFERENCES `thinkeat`.`eatrepo` (`Id`),
  CONSTRAINT `eatrepo_ref_pic_ibfk_2`
    FOREIGN KEY (`picId`)
    REFERENCES `thinkeat`.`pic` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`tag` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`tag` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `tagName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`eatrepo_ref_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`eatrepo_ref_tag` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`eatrepo_ref_tag` (
  `eatRepoId` INT NOT NULL,
  `tagId` INT NOT NULL,
  PRIMARY KEY (`eatRepoId`, `tagId`),
  INDEX `tagId` (`tagId` ASC) VISIBLE,
  CONSTRAINT `eatrepo_ref_tag_ibfk_1`
    FOREIGN KEY (`eatRepoId`)
    REFERENCES `thinkeat`.`eatrepo` (`Id`),
  CONSTRAINT `eatrepo_ref_tag_ibfk_2`
    FOREIGN KEY (`tagId`)
    REFERENCES `thinkeat`.`tag` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`favlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`favlist` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`favlist` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `favListName` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`favlist_ref_eatrepo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`favlist_ref_eatrepo` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`favlist_ref_eatrepo` (
  `favListId` INT NOT NULL,
  `eatRepoId` INT NOT NULL,
  PRIMARY KEY (`favListId`, `eatRepoId`),
  INDEX `eatRepoId` (`eatRepoId` ASC) VISIBLE,
  CONSTRAINT `favlist_ref_eatrepo_ibfk_1`
    FOREIGN KEY (`favListId`)
    REFERENCES `thinkeat`.`favlist` (`Id`),
  CONSTRAINT `favlist_ref_eatrepo_ibfk_2`
    FOREIGN KEY (`eatRepoId`)
    REFERENCES `thinkeat`.`eatrepo` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`level`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`level` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`level` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `levelName` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`service` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`service` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `serviceName` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`level_ref_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`level_ref_service` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`level_ref_service` (
  `levelId` INT NOT NULL,
  `serviceId` INT NOT NULL,
  PRIMARY KEY (`levelId`, `serviceId`),
  INDEX `serviceId` (`serviceId` ASC) VISIBLE,
  CONSTRAINT `level_ref_service_ibfk_1`
    FOREIGN KEY (`levelId`)
    REFERENCES `thinkeat`.`level` (`Id`),
  CONSTRAINT `level_ref_service_ibfk_2`
    FOREIGN KEY (`serviceId`)
    REFERENCES `thinkeat`.`service` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`user` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`user` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `levelId` INT NOT NULL,
  `nickName` VARCHAR(50) NOT NULL,
  `useraccount` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `user_ref_level_idx` (`levelId` ASC) VISIBLE,
  CONSTRAINT `user_ref_level`
    FOREIGN KEY (`levelId`)
    REFERENCES `thinkeat`.`level` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`user_ref_eatrepo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`user_ref_eatrepo` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`user_ref_eatrepo` (
  `userId` INT NOT NULL,
  `eatRepoId` INT NOT NULL,
  INDEX `userId` (`userId` ASC) VISIBLE,
  INDEX `eatRepoId` (`eatRepoId` ASC) VISIBLE,
  CONSTRAINT `user_ref_eatrepo_ibfk_1`
    FOREIGN KEY (`userId`)
    REFERENCES `thinkeat`.`user` (`Id`),
  CONSTRAINT `user_ref_eatrepo_ibfk_2`
    FOREIGN KEY (`eatRepoId`)
    REFERENCES `thinkeat`.`eatrepo` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`user_ref_favlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`user_ref_favlist` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`user_ref_favlist` (
  `userId` INT NOT NULL,
  `favListId` INT NOT NULL,
  PRIMARY KEY (`userId`, `favListId`),
  INDEX `favListId` (`favListId` ASC) VISIBLE,
  CONSTRAINT `user_ref_favlist_ibfk_1`
    FOREIGN KEY (`userId`)
    REFERENCES `thinkeat`.`user` (`Id`),
  CONSTRAINT `user_ref_favlist_ibfk_2`
    FOREIGN KEY (`favListId`)
    REFERENCES `thinkeat`.`favlist` (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;