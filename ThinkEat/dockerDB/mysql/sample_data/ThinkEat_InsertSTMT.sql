-- MySQL Script generated by MySQL Workbench
-- Fri Jan 12 19:09:37 2024
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
-- Table `thinkeat`.`access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`access` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`access` (
                                                   `access_id` INT NOT NULL AUTO_INCREMENT,
                                                   `access_function` VARCHAR(255) NULL DEFAULT NULL,
    `access_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`access_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`authority` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`authority` (
                                                      `authority_id` INT NOT NULL AUTO_INCREMENT,
                                                      `authority_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`authority_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`authority_access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`authority_access` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`authority_access` (
                                                             `auth_ac_ref_id` INT NOT NULL AUTO_INCREMENT,
                                                             `access_for_ref` INT NULL DEFAULT NULL,
                                                             `authority_for_ref` INT NULL DEFAULT NULL,
                                                             PRIMARY KEY (`auth_ac_ref_id`),
    INDEX `FKohijii6jhof6lra5c1u3r3tqo` (`access_for_ref` ASC) VISIBLE,
    INDEX `FKkg129hid4y0vrlg7sunnxpo0` (`authority_for_ref` ASC) VISIBLE,
    CONSTRAINT `FKkg129hid4y0vrlg7sunnxpo0`
    FOREIGN KEY (`authority_for_ref`)
    REFERENCES `thinkeat`.`authority` (`authority_id`),
    CONSTRAINT `FKohijii6jhof6lra5c1u3r3tqo`
    FOREIGN KEY (`access_for_ref`)
    REFERENCES `thinkeat`.`access` (`access_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`user` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`user` (
                                                 `user_id` INT NOT NULL AUTO_INCREMENT,
                                                 `nick_name` VARCHAR(255) NULL DEFAULT NULL,
    `password` VARCHAR(255) NULL DEFAULT NULL,
    `user_name` VARCHAR(255) NULL DEFAULT NULL,
    `authority_id` INT NULL DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    INDEX `FKokrgxdbtf7tirfx1d1qtg9n24` (`authority_id` ASC) VISIBLE,
    CONSTRAINT `FKokrgxdbtf7tirfx1d1qtg9n24`
    FOREIGN KEY (`authority_id`)
    REFERENCES `thinkeat`.`authority` (`authority_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`restaurant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`restaurant` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`restaurant` (
                                                       `restaurant_id` INT NOT NULL AUTO_INCREMENT,
                                                       `address` VARCHAR(255) NULL DEFAULT NULL,
    `name` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`restaurant_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`price`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`price` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`price` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `name` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`eatrepo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`eatrepo` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`eatrepo` (
                                                    `eatrepo_id` INT NOT NULL AUTO_INCREMENT,
                                                    `article` VARCHAR(255) NULL DEFAULT NULL,
    `date` TIMESTAMP NULL DEFAULT NULL,
    `title` VARCHAR(255) NULL DEFAULT NULL,
    `user_id` INT NULL DEFAULT NULL,
    `price_id` INT NULL DEFAULT NULL,
    `restaurant_id` INT NULL DEFAULT NULL,
    PRIMARY KEY (`eatrepo_id`),
    UNIQUE INDEX `UK_dh6attirf96mdmdqkrxikdwtu` (`price_id` ASC) VISIBLE,
    INDEX `FKamogkeopgokjk7oga8nqv25qi` (`user_id` ASC) VISIBLE,
    INDEX `FKlvrngxpspqlrn889h89xa3xow` (`restaurant_id` ASC) VISIBLE,
    CONSTRAINT `FKamogkeopgokjk7oga8nqv25qi`
    FOREIGN KEY (`user_id`)
    REFERENCES `thinkeat`.`user` (`user_id`),
    CONSTRAINT `FKlvrngxpspqlrn889h89xa3xow`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `thinkeat`.`restaurant` (`restaurant_id`),
    CONSTRAINT `FKq2cbcwanu8or8d3r8be3e9t7e`
    FOREIGN KEY (`price_id`)
    REFERENCES `thinkeat`.`price` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`comment` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`comment` (
                                                    `comment_id` INT NOT NULL AUTO_INCREMENT,
                                                    `comment_context` VARCHAR(255) NULL DEFAULT NULL,
    `eatrepo_id` INT NULL DEFAULT NULL,
    `user_id` INT NULL DEFAULT NULL,
    PRIMARY KEY (`comment_id`),
    INDEX `FK6fkj5llv13f9clbpxj0c0pll8` (`eatrepo_id` ASC) VISIBLE,
    INDEX `FK8kcum44fvpupyw6f5baccx25c` (`user_id` ASC) VISIBLE,
    CONSTRAINT `FK6fkj5llv13f9clbpxj0c0pll8`
    FOREIGN KEY (`eatrepo_id`)
    REFERENCES `thinkeat`.`eatrepo` (`eatrepo_id`),
    CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c`
    FOREIGN KEY (`user_id`)
    REFERENCES `thinkeat`.`user` (`user_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`tag` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`tag` (
                                                `tag_id` INT NOT NULL AUTO_INCREMENT,
                                                `name` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`tag_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`eatrepo_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`eatrepo_tag` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`eatrepo_tag` (
                                                        `er_tag_ref_id` INT NOT NULL AUTO_INCREMENT,
                                                        `eatrepo_for_ref` INT NULL DEFAULT NULL,
                                                        `tag_for_ref` INT NULL DEFAULT NULL,
                                                        `eatrepo_id_ref` INT NOT NULL,
                                                        `tag_id_ref` INT NOT NULL,
                                                        PRIMARY KEY (`er_tag_ref_id`),
    INDEX `FK9wucgnk32r7xonyie8n0p1oay` (`eatrepo_for_ref` ASC) VISIBLE,
    INDEX `FK9r2mdu0ax57dyj66l3q46ci0u` (`tag_for_ref` ASC) VISIBLE,
    INDEX `FKs1vgh2nsx0d7nblmnwsx0il78` (`tag_id_ref` ASC) VISIBLE,
    INDEX `FK3yqhfsviwd4i5fxm497nl5bvq` (`eatrepo_id_ref` ASC) VISIBLE,
    CONSTRAINT `FK3yqhfsviwd4i5fxm497nl5bvq`
    FOREIGN KEY (`eatrepo_id_ref`)
    REFERENCES `thinkeat`.`eatrepo` (`eatrepo_id`),
    CONSTRAINT `FK9r2mdu0ax57dyj66l3q46ci0u`
    FOREIGN KEY (`tag_for_ref`)
    REFERENCES `thinkeat`.`tag` (`tag_id`),
    CONSTRAINT `FK9wucgnk32r7xonyie8n0p1oay`
    FOREIGN KEY (`eatrepo_for_ref`)
    REFERENCES `thinkeat`.`eatrepo` (`eatrepo_id`),
    CONSTRAINT `FKs1vgh2nsx0d7nblmnwsx0il78`
    FOREIGN KEY (`tag_id_ref`)
    REFERENCES `thinkeat`.`tag` (`tag_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`fav_list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`fav_list` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`fav_list` (
                                                     `favlist_id` INT NOT NULL AUTO_INCREMENT,
                                                     `fav_list_name` VARCHAR(255) NULL DEFAULT NULL,
    `user_id` INT NULL DEFAULT NULL,
    PRIMARY KEY (`favlist_id`),
    INDEX `FKh3vsraoq0m9uq83uw6f0ki838` (`user_id` ASC) VISIBLE,
    CONSTRAINT `FKh3vsraoq0m9uq83uw6f0ki838`
    FOREIGN KEY (`user_id`)
    REFERENCES `thinkeat`.`user` (`user_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`favlist_eatrepo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`favlist_eatrepo` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`favlist_eatrepo` (
                                                            `fl_er_ref_id` INT NOT NULL AUTO_INCREMENT,
                                                            `eatrepo_for_ref` INT NULL DEFAULT NULL,
                                                            `favlist_for_ref` INT NULL DEFAULT NULL,
                                                            `fav_list_id_ref` INT NOT NULL,
                                                            `eatrepo_id_ref` INT NOT NULL,
                                                            PRIMARY KEY (`fl_er_ref_id`),
    INDEX `FK2ro2t6g5vdv2utdg9l80vmjga` (`eatrepo_for_ref` ASC) VISIBLE,
    INDEX `FKtn9q3n4yc9c8hf9dfmiw41trc` (`favlist_for_ref` ASC) VISIBLE,
    INDEX `FKbleukcgr2cg2825udvwyj3v05` (`eatrepo_id_ref` ASC) VISIBLE,
    INDEX `FK5nxp0ggjqyg1mtkgq6iskpos6` (`fav_list_id_ref` ASC) VISIBLE,
    CONSTRAINT `FK2ro2t6g5vdv2utdg9l80vmjga`
    FOREIGN KEY (`eatrepo_for_ref`)
    REFERENCES `thinkeat`.`eatrepo` (`eatrepo_id`),
    CONSTRAINT `FK5nxp0ggjqyg1mtkgq6iskpos6`
    FOREIGN KEY (`fav_list_id_ref`)
    REFERENCES `thinkeat`.`fav_list` (`favlist_id`),
    CONSTRAINT `FKbleukcgr2cg2825udvwyj3v05`
    FOREIGN KEY (`eatrepo_id_ref`)
    REFERENCES `thinkeat`.`eatrepo` (`eatrepo_id`),
    CONSTRAINT `FKtn9q3n4yc9c8hf9dfmiw41trc`
    FOREIGN KEY (`favlist_for_ref`)
    REFERENCES `thinkeat`.`fav_list` (`favlist_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`picture`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`picture` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`picture` (
                                                    `picture_id` INT NOT NULL AUTO_INCREMENT,
                                                    `path` VARCHAR(255) NULL DEFAULT NULL,
    `eatrepo_id` INT NULL DEFAULT NULL,
    PRIMARY KEY (`picture_id`),
    INDEX `FKidxtu2736ehbnowps61ydkdru` (`eatrepo_id` ASC) VISIBLE,
    CONSTRAINT `FKidxtu2736ehbnowps61ydkdru`
    FOREIGN KEY (`eatrepo_id`)
    REFERENCES `thinkeat`.`eatrepo` (`eatrepo_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;