-- MySQL Script generated by MySQL Workbench
-- Tue Feb 20 20:49:15 2024
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
-- Table `thinkeat`.`authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`authority` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`authority` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`user` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `enabled` TINYINT(1) NULL DEFAULT NULL,
  `nickname` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `raw_password` VARCHAR(255) NULL DEFAULT NULL,
  `token_expired` TINYINT(1) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  `authority_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKokrgxdbtf7tirfx1d1qtg9n24` (`authority_id` ASC) VISIBLE,
  CONSTRAINT `FKokrgxdbtf7tirfx1d1qtg9n24`
    FOREIGN KEY (`authority_id`)
    REFERENCES `thinkeat`.`authority` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`restaurant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`restaurant` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`restaurant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
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
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`eatrepo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`eatrepo` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`eatrepo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `article` VARCHAR(255) NULL DEFAULT NULL,
  `date` TIMESTAMP NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `price_id` INT NULL DEFAULT NULL,
  `restaurant_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKamogkeopgokjk7oga8nqv25qi` (`user_id` ASC) VISIBLE,
  INDEX `FKq2cbcwanu8or8d3r8be3e9t7e` (`price_id` ASC) VISIBLE,
  INDEX `FKlvrngxpspqlrn889h89xa3xow` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `FKamogkeopgokjk7oga8nqv25qi`
    FOREIGN KEY (`user_id`)
    REFERENCES `thinkeat`.`user` (`id`),
  CONSTRAINT `FKlvrngxpspqlrn889h89xa3xow`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `thinkeat`.`restaurant` (`id`),
  CONSTRAINT `FKq2cbcwanu8or8d3r8be3e9t7e`
    FOREIGN KEY (`price_id`)
    REFERENCES `thinkeat`.`price` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`comment` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comment_context` VARCHAR(255) NULL DEFAULT NULL,
  `date` TIMESTAMP NULL DEFAULT NULL,
  `eatrepo_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK6fkj5llv13f9clbpxj0c0pll8` (`eatrepo_id` ASC) VISIBLE,
  INDEX `FK8kcum44fvpupyw6f5baccx25c` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK6fkj5llv13f9clbpxj0c0pll8`
    FOREIGN KEY (`eatrepo_id`)
    REFERENCES `thinkeat`.`eatrepo` (`id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c`
    FOREIGN KEY (`user_id`)
    REFERENCES `thinkeat`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`tag` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`eatrepo_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`eatrepo_tag` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`eatrepo_tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `eatrepo_id` INT NULL DEFAULT NULL,
  `tag_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKly2gb8gkn1si4fgmj0y1fyex3` (`eatrepo_id` ASC) VISIBLE,
  INDEX `FKeqig3fpcogmh2ix19ahf87h6k` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `FKeqig3fpcogmh2ix19ahf87h6k`
    FOREIGN KEY (`tag_id`)
    REFERENCES `thinkeat`.`tag` (`id`),
  CONSTRAINT `FKly2gb8gkn1si4fgmj0y1fyex3`
    FOREIGN KEY (`eatrepo_id`)
    REFERENCES `thinkeat`.`eatrepo` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`favlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`favlist` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`favlist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `list_count` INT NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK1yyp986gfslayyywmlq842k3h` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK1yyp986gfslayyywmlq842k3h`
    FOREIGN KEY (`user_id`)
    REFERENCES `thinkeat`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`favlist_eatrepo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`favlist_eatrepo` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`favlist_eatrepo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `eatrepo_id` INT NULL DEFAULT NULL,
  `favlist_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKdtq89a4gilmbqxjoi0c4s2keu` (`eatrepo_id` ASC) VISIBLE,
  INDEX `FK5dgc1uauxkxlg3g54iyimrup7` (`favlist_id` ASC) VISIBLE,
  CONSTRAINT `FK5dgc1uauxkxlg3g54iyimrup7`
    FOREIGN KEY (`favlist_id`)
    REFERENCES `thinkeat`.`favlist` (`id`),
  CONSTRAINT `FKdtq89a4gilmbqxjoi0c4s2keu`
    FOREIGN KEY (`eatrepo_id`)
    REFERENCES `thinkeat`.`eatrepo` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`picture`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`picture` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`picture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `file_path` VARCHAR(255) NULL DEFAULT NULL,
  `filename` VARCHAR(255) NULL DEFAULT NULL,
  `html_path` VARCHAR(255) NULL DEFAULT NULL,
  `eatrepo_id` INT NULL DEFAULT NULL,
  `restaurant_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKidxtu2736ehbnowps61ydkdru` (`eatrepo_id` ASC) VISIBLE,
  INDEX `FKinqrbkoct9u536o2nut30rc6i` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `FKidxtu2736ehbnowps61ydkdru`
    FOREIGN KEY (`eatrepo_id`)
    REFERENCES `thinkeat`.`eatrepo` (`id`),
  CONSTRAINT `FKinqrbkoct9u536o2nut30rc6i`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `thinkeat`.`restaurant` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `thinkeat`.`user_authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `thinkeat`.`user_authority` ;

CREATE TABLE IF NOT EXISTS `thinkeat`.`user_authority` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `authority_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id` ASC) VISIBLE,
  INDEX `FKpqlsjpkybgos9w2svcri7j8xy` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89`
    FOREIGN KEY (`authority_id`)
    REFERENCES `thinkeat`.`authority` (`id`),
  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy`
    FOREIGN KEY (`user_id`)
    REFERENCES `thinkeat`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO thinkeat.price (id, name) VALUES ('1', '100元以內');
INSERT INTO thinkeat.price (id, name) VALUES ('2', '100~200元');
INSERT INTO thinkeat.price (id, name) VALUES ('3', '200~300元');
INSERT INTO thinkeat.price (id, name) VALUES ('4', '300~400元');
INSERT INTO thinkeat.price (id, name) VALUES ('5', '400~500元');

INSERT INTO thinkeat.tag (id, name) VALUES ('1', '台式');
INSERT INTO thinkeat.tag (id, name) VALUES ('2', '手搖杯');
INSERT INTO thinkeat.tag (id, name) VALUES ('3', '會辣');
INSERT INTO thinkeat.tag (id, name) VALUES ('4', '早餐');
INSERT INTO thinkeat.tag (id, name) VALUES ('5', '消夜');
INSERT INTO thinkeat.tag (id, name) VALUES ('6', '簡餐');
INSERT INTO thinkeat.tag (id, name) VALUES ('7', '下午茶');
INSERT INTO thinkeat.tag (id, name) VALUES ('8', '日式');

INSERT INTO `thinkeat`.`authority` (`id`, `description`, `name`) VALUES ('1', '一般會員', 'standard_user');
INSERT INTO `thinkeat`.`authority` (`id`, `description`, `name`) VALUES ('2', '管理員', 'admin');
INSERT INTO `thinkeat`.`authority` (`id`, `description`, `name`) VALUES ('3', '創辦人', 'founder');

INSERT INTO `thinkeat`.`user` (`id`, `date`, `enabled`, `nickname`, `password`, `raw_password`, `token_expired`, `username`, `authority_id`) VALUES ('1', '2024/01/27', '1', '一般會員', '$2y$10$oTWA1mrN67z2niF7d8HOduSaQ70cBtUhcqfoywhTnFkF5cZ9VozRG', '123', '0', 'user', '1');
INSERT INTO `thinkeat`.`user` (`id`, `date`, `enabled`, `nickname`, `password`, `raw_password`, `token_expired`, `username`, `authority_id`) VALUES ('2', '2024/01/27', '1', '管理員', '$2y$10$jPJux4x4yu9ZJ8Bepb0jSeAlPaYjq.vbWxsOFh4VzrZGOmu4HkvqS', '456', '0', 'admin', '2');
INSERT INTO `thinkeat`.`user` (`id`, `date`, `enabled`, `nickname`, `password`, `raw_password`, `token_expired`, `username`, `authority_id`) VALUES ('3', '2024/01/27', '1', '創辦者', '$2y$10$DHUH5Ay5OdgFMwLIhcT.4u3XpQsbiDwFwlY.AKuh2ZLAKNWsif7su', '789', '0', 'founder', '3');

INSERT INTO `thinkeat`.`favlist` (`id`, `list_count`, `name`, `user_id`) VALUES ('1', '1', '我的第一個清單', '1');
INSERT INTO `thinkeat`.`favlist` (`id`, `list_count`, `name`, `user_id`) VALUES ('2', '1', '我的第一個清單', '2');
INSERT INTO `thinkeat`.`favlist` (`id`, `list_count`, `name`, `user_id`) VALUES ('3', '1', '我的第一個清單', '3');