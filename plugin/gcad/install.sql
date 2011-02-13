SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `dbgcad` DEFAULT CHARACTER SET latin1 ;
USE `dbgcad` ;

-- -----------------------------------------------------
-- Table `dbgcad`.`projects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`projects` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`projects` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `description` TEXT NOT NULL ,
  `startDate` DATETIME NOT NULL ,
  `endDate` DATETIME NOT NULL ,
  `budget` DOUBLE NOT NULL ,
  `quantityLines` INT NOT NULL ,
  `domain` TEXT NOT NULL ,
  `progLanguage` TEXT NOT NULL ,
  `estimatedHours` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`addresses` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`addresses` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `street` TEXT NOT NULL ,
  `city` TEXT NOT NULL ,
  `country` VARCHAR(45) NOT NULL ,
  `zip` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`companies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`companies` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`companies` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `cif` CHAR(9) NOT NULL ,
  `name` TEXT NOT NULL ,
  `information` TEXT NOT NULL ,
  `addressId` INT NULL DEFAULT -1 ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `cif_UNIQUE` (`cif` ASC) ,
  INDEX `fk_companies_address` (`addressId` ASC) ,
  CONSTRAINT `fk_companies_address`
    FOREIGN KEY (`addressId` )
    REFERENCES `dbgcad`.`addresses` (`id` )
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`users` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `role` INT NOT NULL ,
  `nif` CHAR(9) NOT NULL ,
  `login` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `name` TEXT NOT NULL ,
  `surname` TEXT NOT NULL ,
  `email` TEXT NULL ,
  `telephone` VARCHAR(9) NULL ,
  `companyId` INT NULL DEFAULT -1 ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `nif_UNIQUE` (`nif` ASC) ,
  INDEX `fk_user_company` (`companyId` ASC) ,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) ,
  CONSTRAINT `fk_user_company`
    FOREIGN KEY (`companyId` )
    REFERENCES `dbgcad`.`companies` (`id` )
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`usersProjects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`usersProjects` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`usersProjects` (
  `idUser` INT NOT NULL ,
  `idProject` INT NOT NULL ,
  PRIMARY KEY (`idUser`, `idProject`) ,
  INDEX `fk_userProject_employee` (`idUser` ASC) ,
  INDEX `fk_userProject_project` (`idProject` ASC) ,
  CONSTRAINT `fk_userProject_employee`
    FOREIGN KEY (`idUser` )
    REFERENCES `dbgcad`.`users` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_userProject_project`
    FOREIGN KEY (`idProject` )
    REFERENCES `dbgcad`.`projects` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`knowledge`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`knowledge` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`knowledge` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(255) NOT NULL ,
  `date` DATETIME NOT NULL ,
  `userId` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_knowledge_user` (`userId` ASC) ,
  CONSTRAINT `fk_knowledge_user`
    FOREIGN KEY (`userId` )
    REFERENCES `dbgcad`.`users` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`topics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`topics` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`topics` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `projectId` INT NOT NULL DEFAULT -1 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_topic_project` (`projectId` ASC) ,
  INDEX `fk_topic_knowledgr` (`id` ASC) ,
  CONSTRAINT `fk_topic_project`
    FOREIGN KEY (`projectId` )
    REFERENCES `dbgcad`.`projects` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_topic_knowledgr`
    FOREIGN KEY (`id` )
    REFERENCES `dbgcad`.`knowledge` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`proposals`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`proposals` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`proposals` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `description` TEXT NOT NULL ,
  `category` ENUM('Analysis', 'Design') NOT NULL ,
  `topicId` INT NOT NULL DEFAULT -1 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_proposal_topic` (`topicId` ASC) ,
  INDEX `fk_proposal_knowledge` (`id` ASC) ,
  CONSTRAINT `fk_proposal_topic`
    FOREIGN KEY (`topicId` )
    REFERENCES `dbgcad`.`topics` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_proposal_knowledge`
    FOREIGN KEY (`id` )
    REFERENCES `dbgcad`.`knowledge` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`answers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`answers` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`answers` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `description` TEXT NOT NULL ,
  `argument` ENUM('Pro', 'Contra') NOT NULL ,
  `proposalId` INT NOT NULL DEFAULT -1 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_anwser_proposal` (`proposalId` ASC) ,
  INDEX `fk_answer_knowledge` (`id` ASC) ,
  CONSTRAINT `fk_anwser_proposal`
    FOREIGN KEY (`proposalId` )
    REFERENCES `dbgcad`.`proposals` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_answer_knowledge`
    FOREIGN KEY (`id` )
    REFERENCES `dbgcad`.`knowledge` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`notifications`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`notifications` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`notifications` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `state` ENUM('Read','Unread') NOT NULL ,
  `knowledgeId` INT NOT NULL ,
  `projectId` INT NOT NULL DEFAULT -1 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_notifications_knowledge` (`knowledgeId` ASC) ,
  INDEX `fk_notifications_project` (`projectId` ASC) ,
  CONSTRAINT `fk_notifications_knowledge`
    FOREIGN KEY (`knowledgeId` )
    REFERENCES `dbgcad`.`knowledge` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_notifications_project`
    FOREIGN KEY (`projectId` )
    REFERENCES `dbgcad`.`projects` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


CREATE USER `gcad` IDENTIFIED BY 'gcad';

grant ALL on TABLE `dbgcad`.`addresses` to gcad;
grant ALL on TABLE `dbgcad`.`answers` to gcad;
grant ALL on TABLE `dbgcad`.`companies` to gcad;
grant ALL on TABLE `dbgcad`.`projects` to gcad;
grant ALL on TABLE `dbgcad`.`proposals` to gcad;
grant ALL on TABLE `dbgcad`.`topics` to gcad;
grant ALL on TABLE `dbgcad`.`users` to gcad;
grant ALL on TABLE `dbgcad`.`usersProjects` to gcad;
grant ALL on TABLE `dbgcad`.`knowledge` to gcad;
grant ALL on TABLE `dbgcad`.`notifications` to gcad;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`projects`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`projects` (`id`, `name`, `description`, `startDate`, `endDate`, `budget`, `quantityLines`, `domain`, `progLanguage`, `estimatedHours`) VALUES ('1', 'Project1', 'description 1', '2002-03-05', '2012-12-31', '23458.1', '12341', 'bank', 'C#', '9812');
INSERT INTO `dbgcad`.`projects` (`id`, `name`, `description`, `startDate`, `endDate`, `budget`, `quantityLines`, `domain`, `progLanguage`, `estimatedHours`) VALUES ('2', 'Project2', 'description2', '2000-01-01', '2013-12-31', '1315251.98', '1415125', 'defense', 'Java', '1914814');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`addresses`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`addresses` (`id`, `street`, `city`, `country`, `zip`) VALUES ('1', 'street', 'Ciudad Real', 'Spain', '13300');
INSERT INTO `dbgcad`.`addresses` (`id`, `street`, `city`, `country`, `zip`) VALUES ('2', 'street', 'Londres', 'UK', '12349CMA');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`companies`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`companies` (`id`, `cif`, `name`, `information`, `addressId`) VALUES ('1', '1291489IO', 'Indra', 'Info', '1');
INSERT INTO `dbgcad`.`companies` (`id`, `cif`, `name`, `information`, `addressId`) VALUES ('2', '91841340P', 'Ericsson', 'Info', '2');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`users`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`) VALUES ('1', '0', '06941082A', 'emp1', 'emp1', 'Juan', 'Lopez', NULL, NULL, '1');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`) VALUES ('2', '1', '58123457B', 'emp2', 'emp2', 'Antonio', 'Perez', 'antonio@gmail.com', '913517281', '2');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`) VALUES ('3', '0', '01921318C', 'emp3', 'emp3', 'Ana', 'Lopez', NULL, '671234091', '1');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`usersProjects`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('1', '1');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('1', '2');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('2', '2');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('3', '1');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('3', '2');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`knowledge`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('1', 'Patrones', '2011-01-22', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('2', 'Frameworks', '2011-01-21', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('3', 'Proposal 1', '2011-01-23', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('4', 'Proposal 2', '2011-01-31', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('5', 'Proposal 3', '2011-02-01', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('6', 'Proposal 4', '2011-02-23', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('7', 'Answer 1', '2011-03-01', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('8', 'Answer 2', '2011-04-01', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('9', 'Answer 3', '2011-03-31', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `date`, `userId`) VALUES ('10', 'Answer 4', '2011-03-12', '3');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`topics`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('1', '2');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('2', '2');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`proposals`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`proposals` (`id`, `description`, `category`, `topicId`) VALUES ('3', 'p1', 'Analysis', '1');
INSERT INTO `dbgcad`.`proposals` (`id`, `description`, `category`, `topicId`) VALUES ('4', 'p2', 'Design', '1');
INSERT INTO `dbgcad`.`proposals` (`id`, `description`, `category`, `topicId`) VALUES ('5', 'p3', 'Analysis', '2');
INSERT INTO `dbgcad`.`proposals` (`id`, `description`, `category`, `topicId`) VALUES ('6', 'p4', 'Analysis', '2');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`answers`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`answers` (`id`, `description`, `argument`, `proposalId`) VALUES ('7', 'a1', 'Pro', '3');
INSERT INTO `dbgcad`.`answers` (`id`, `description`, `argument`, `proposalId`) VALUES ('8', 'a2', 'Contra', '4');
INSERT INTO `dbgcad`.`answers` (`id`, `description`, `argument`, `proposalId`) VALUES ('9', 'a3', 'Pro', '4');
INSERT INTO `dbgcad`.`answers` (`id`, `description`, `argument`, `proposalId`) VALUES ('10', 'a4', 'Pro', '5');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`notifications`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`notifications` (`id`, `state`, `knowledgeId`, `projectId`) VALUES ('1', 'Read', '2', '2');
INSERT INTO `dbgcad`.`notifications` (`id`, `state`, `knowledgeId`, `projectId`) VALUES ('2', 'Unread', '4', '2');
INSERT INTO `dbgcad`.`notifications` (`id`, `state`, `knowledgeId`, `projectId`) VALUES ('3', 'Unread', '6', '2');

COMMIT;