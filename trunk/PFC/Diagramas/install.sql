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
  `seniority` INT NULL ,
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
  `description` VARCHAR(255) NOT NULL ,
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
  `category` ENUM('Analysis', 'Design', 'Development', 'Testing') NOT NULL ,
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
  `argument` ENUM('Agree', 'Disagree', 'Neutral') NOT NULL ,
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
  `subject` VARCHAR(255) NOT NULL ,
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


-- -----------------------------------------------------
-- Table `dbgcad`.`LogEntry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`LogEntry` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`LogEntry` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `user` VARCHAR(255) NULL ,
  `date` DATETIME NOT NULL ,
  `action` ENUM('create', 'read', 'update', 'delete', 'info') NOT NULL ,
  `message` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_log_user` (`user` ASC) ,
  CONSTRAINT `fk_log_user`
    FOREIGN KEY (`user` )
    REFERENCES `dbgcad`.`users` (`login` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`notificationsUsers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`notificationsUsers` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`notificationsUsers` (
  `idNotification` INT NOT NULL ,
  `idUser` INT NOT NULL ,
  `state` ENUM('Read','Unread') NOT NULL DEFAULT 'Unread' ,
  PRIMARY KEY (`idNotification`, `idUser`) ,
  INDEX `fk_notificationsUsers_user` (`idUser` ASC) ,
  INDEX `fk_notificationsUsers_notification` (`idNotification` ASC) ,
  CONSTRAINT `fk_notificationsUsers_user`
    FOREIGN KEY (`idUser` )
    REFERENCES `dbgcad`.`users` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_notificationsUsers_notification`
    FOREIGN KEY (`idNotification` )
    REFERENCES `dbgcad`.`notifications` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbgcad`.`files`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbgcad`.`files` ;

CREATE  TABLE IF NOT EXISTS `dbgcad`.`files` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `knowledgeId` INT NOT NULL ,
  `fileName` VARCHAR(255) NOT NULL ,
  `content` LONGBLOB NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_fileKnowledge_knowledge` (`knowledgeId` ASC) ,
  CONSTRAINT `fk_fileKnowledge_knowledge`
    FOREIGN KEY (`knowledgeId` )
    REFERENCES `dbgcad`.`knowledge` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `dbgcad`;

DELIMITER $$

USE `dbgcad`$$
DROP TRIGGER IF EXISTS `dbgcad`.`DeleteEntity` $$
USE `dbgcad`$$




CREATE TRIGGER DeleteEntity
AFTER DELETE ON notificationsUsers
FOR EACH ROW
BEGIN
DECLARE
  cont INT;
  SELECT COUNT(*) INTO cont FROM notificationsUsers WHERE idNotification = old.idNotification;
  IF (cont = 0) THEN
    DELETE FROM notifications WHERE id = OLD.idNotification;
  END IF;
END$$


DELIMITER ;

;
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
grant ALL on TABLE `dbgcad`.`LogEntry` to gcad;
grant ALL on TABLE `dbgcad`.`notificationsUsers` to gcad;
grant ALL on TABLE `dbgcad`.`files` to gcad;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`projects`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`projects` (`id`, `name`, `description`, `startDate`, `endDate`, `budget`, `quantityLines`, `domain`, `progLanguage`, `estimatedHours`) VALUES ('1', 'GCAD', 'Desktop Swing application used to manage knowledge in DGS', '2009-06-05', '2011-09-30', '23458.1', '12341', 'dgs', 'Java', '256045');
INSERT INTO `dbgcad`.`projects` (`id`, `name`, `description`, `startDate`, `endDate`, `budget`, `quantityLines`, `domain`, `progLanguage`, `estimatedHours`) VALUES ('2', 'PrintPro', 'Processor to print remotely', '2010-01-01', '2011-12-31', '12000.0', '9005', 'bank', 'C#', '10450');
INSERT INTO `dbgcad`.`projects` (`id`, `name`, `description`, `startDate`, `endDate`, `budget`, `quantityLines`, `domain`, `progLanguage`, `estimatedHours`) VALUES ('3', 'ROREM', 'Robot Remote Missile', '2006-01-01', '2014-05-06', '65217489425', '148482087', 'defense', 'Java', '985478537');
INSERT INTO `dbgcad`.`projects` (`id`, `name`, `description`, `startDate`, `endDate`, `budget`, `quantityLines`, `domain`, `progLanguage`, `estimatedHours`) VALUES ('4', 'CHS', 'County health system', '2010-05-15', '2013-06-03', '1500.50', '22045', 'health', 'C', '28356');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`addresses`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`addresses` (`id`, `street`, `city`, `country`, `zip`) VALUES ('1', 'Miguel Angel 23', 'Ciudad Real', 'Spain', '28010');
INSERT INTO `dbgcad`.`addresses` (`id`, `street`, `city`, `country`, `zip`) VALUES ('2', '124  Kensington High Stree', 'London', 'United Kingdom', 'W87RL');
INSERT INTO `dbgcad`.`addresses` (`id`, `street`, `city`, `country`, `zip`) VALUES ('3', '3360 Martin Farm Road', 'Suwanee ', 'United States', '30024');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`companies`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`companies` (`id`, `cif`, `name`, `information`, `addressId`) VALUES ('1', '1291489IO', 'Indra', 'Company specializing in software development, based in Madrid', '1');
INSERT INTO `dbgcad`.`companies` (`id`, `cif`, `name`, `information`, `addressId`) VALUES ('2', '91841340P', 'Ericsson', 'Company specializing in software development for mobile devices, based in London', '2');
INSERT INTO `dbgcad`.`companies` (`id`, `cif`, `name`, `information`, `addressId`) VALUES ('3', '54974268', 'DocPath', 'Development company specializing in document management software, headquartered in USA', '3');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`users`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('1', '0', '06941082A', 'emp1', 'emp1', 'Juan', 'Lopez', NULL, NULL, '1', '4');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('2', '1', '58123457B', 'emp2', 'emp2', 'Antonio', 'Perez', 'antonio@gmail.com', '913517281', '2', '5');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('3', '0', '01921318C', 'emp3', 'emp3', 'Ana', 'Garcia', NULL, '671234091', '2', '1');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('4', '0', '18451279R', 'emp4', 'emp4', 'Daniel', 'Cruz', NULL, '940127318', '1', '2');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('5', '0', '01778234B', 'emp5', 'emp5', 'Veronica', 'Jimenez', 'vero@hotmail.com', '555914531', '1', '2');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('7', '1', '45697814J', 'emp7', 'emp7', 'Frazer', 'Dixon', 'frad@yahoo.uk', NULL, '2', '10');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('8', '0', '95641258K', 'emp8', 'emp8', 'Rubens', 'Paulo', NULL, '547931689', '2', '3');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('6', '0', '69785642P', 'emp6', 'emp6', 'Vincent', 'Camarillo', 'vincentCamarillo@example.com', '877543788', '2', '15');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('9', '0', '98456789A', 'emp9', 'emp9 ', 'Minerva', 'McClain', 'minerva@comp.us', NULL, '1', '12');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('10', '1', '59612357B', 'emp10', 'emp10', 'Carin', 'Vazquez', 'carin.va@gmail.com', '569453789', '1', '11');
INSERT INTO `dbgcad`.`users` (`id`, `role`, `nif`, `login`, `password`, `name`, `surname`, `email`, `telephone`, `companyId`, `seniority`) VALUES ('11', '1', '23697465L', 'emp11', 'emp11', 'Sophia', 'Irwin', NULL, '555357426', '2', '7');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`usersProjects`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('1', '1');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('1', '2');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('2', '1');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('2', '2');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('3', '1');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('3', '2');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('3', '3');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('3', '4');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('4', '3');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('4', '4');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('5', '3');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('5', '4');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('6', '3');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('6', '4');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('7', '1');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('8', '2');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('8', '3');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('9', '1');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('9', '2');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('9', '3');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('9', '4');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('10', '4');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('11', '3');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('11', '4');
INSERT INTO `dbgcad`.`usersProjects` (`idUser`, `idProject`) VALUES ('1', '3');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`knowledge`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('1', 'Patterns', 'Topic to group decisions about design patterns', '2007-01-22', '10');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('2', 'Frameworks', 'Topic to group decisions about frameworks to use', '2011-01-21', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('3', 'Design', 'Topic to group design decisions ', '2009-12-01', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('4', 'Web Application', 'Topic to group decisions about Web tecnologies', '2011-06-01', '10');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('5', 'Analysis', 'Topic to group analysis decisions ', '2006-01-12', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('6', 'Communications', 'Topic to group communication decisions ', '2011-01-11', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('7', 'GUI', 'Topic to group decisiones about GUI tecnologies', '2011-04-01', '7');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('8', 'Frameworks', 'Topic to group decisions about frameworks to use', '2011-07-15', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('9', 'Web Application', 'Topic to group decisions about Web tecnologies', '2010-12-04', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('10', 'Uses RMI', 'Proposal 1 from topic 6', '2011-01-15', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('11', 'Uses ICE', 'Proposal 2 from topic 6', '2011-08-08', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('12', 'Client/Server', 'Proposal 1 from topic 3', '2009-12-22', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('13', 'Plugin ', 'Proposal 2 from topic 3', '2010-06-06', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('14', 'Web Application', 'Proposal 3 from topic 3', '2011-02-21', '9');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('15', 'Uses Swing', 'Proposal 1 from topic 7', '2011-04-02', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('16', 'Uses WPF', 'Proposal 2 from topic 7', '2011-05-15', '7');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('17', 'Uses GOF', 'Proposal 1 from topic 1', '2007-06-17', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('18', 'Uses Persistence', 'Proposal 2 from topic 1', '2008-01-12', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('19', 'Uses Struts', 'Proposal 1 from topic 2', '2011-01-22', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('20', 'Uses Spring', 'Proposal 2 from topic 2', '2011-01-23', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('21', 'Uses Hibernate', 'Proposal 3 from topic 2', '2011-04-15', '4');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('22', 'Uses WSDL', 'Proposal 4 from topic 2', '2011-03-15', '8');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('23', 'Uses HTML5', 'Proposal 1 from topic 4', '2011-06-01', '8');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('24', 'Uses CSS3', 'Proposal 2 from topic 4', '2011-06-02', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('25', 'Not uses IE', 'Proposal 3 from topic 4', '2011-07-01', '9');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('26', 'Makes requirements specification', 'Proposal 1 from topic 5', '2006-01-13', '5');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('27', 'Makes Gant Diagram', 'Proposal 2 from topic 5', '2007-06-15', '5');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('28', 'Makes Use Cases Diagram', 'Proposal 3 from topic 5', '2007-06-29', '6');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('29', 'Uses Swing Framework', 'Proposal 1 from topic 8', '2011-07-16', '10');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('30', 'Uses .NET 4.0', 'Proposal 2 from topic 8', '2011-08-01', '10');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('31', 'Uses ASP.NET', 'Proposal 1 from topic 9', '2011-09-05', '5');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('32', 'Security Problems', 'Answer 1 from proposal 6.1', '2011-02-15', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('33', 'Serialize Problems', 'Answer 2 from proposal 6.1', '2011-02-15', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('34', 'Scalability', 'Answer 1 from proposal 3.1', '2010-02-01', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('35', 'Limited UI', 'Answer 1 from proposal 3.2', '2010-07-01', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('36', 'Not works RMI', 'Answer 2 from proposal 3.2', '2010-08-15', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('37', 'ClassLoader', 'Answer 3 from proposal 3.2', '2010-09-26', '9');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('38', 'User experience', 'Answer 1 from proposal 3.3', '2011-03-15', '8');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('39', 'Limited UI', 'Answer 1 from proposal 7.1', '2011-04-03', '9');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('40', 'Extensible', 'Answer 2 from proposal 7.1', '2011-05-01', '8');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('41', 'Dynamic', 'Answer 1 from proposal 7.2', '2011-06-17', '2');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('42', 'Extensible', 'Answer 2 from proposal 7.2', '2011-06-18', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('43', 'Powerful', 'Answer 3 from proposal 7.2', '2011-09-01', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('44', 'Very used', 'Answer 1 from proposal 1.1', '2007-07-01', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('45', 'Powerful', 'Answer 1 from proposal 2.1', '2011-05-15', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('46', 'Java Based', 'Answer 2 from proposal 2.1', '2011-09-01', '4');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('47', 'Object Oriented', 'Answer 1 from proposal 2.3', '2011-04-16', '4');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('48', 'Serialize Problem', 'Answer 2 from proposal 2.3', '2011-04-17', '6');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('49', 'Powerful', 'Answer 3 from proposal 2.3', '2011-06-16', '4');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('50', 'Difficult', 'Answer 4 from proposal 2.3', '2011-04-26', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('51', 'Independent', 'Answer 1 from proposal 2.4', '2011-03-31', '8');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('52', 'Not uses flash', 'Answer 1 from proposal 4.1', '2011-07-01', '9');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('53', 'Compatibilities Problems', 'Answer 1 from proposal 4.1', '2011-08-01', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('54', 'Compatibilities Problems', 'Answer 2 from proposal 4.2', '2011-06-03', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('55', 'Compatibilities Problems', 'Answer 1 from proposal 4.3', '2011-07-04', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('56', 'Crashes', 'Answer 2 from proposal 4.3', '2011-09-05', '1');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('57', 'Planification', 'Answer 1 from proposal 5.2', '2007-07-28', '11');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('58', 'Planification', 'Answer 1 from proposal 5.3', '2008-01-15', '3');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('59', 'Iterative', 'Answer 2 from proposal 5.3', '2007-12-12', '6');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('60', 'Easy', 'Answer 1 from proposal 8.1', '2011-07-18', '10');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('61', 'Very Used', 'Answer 1 from proposal 8.2', '2011-08-02', '6');
INSERT INTO `dbgcad`.`knowledge` (`id`, `title`, `description`, `date`, `userId`) VALUES ('62', 'Microsoft', 'Answer 2 from proposal 8.2', '2011-08-02', '6');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`topics`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('3', '1');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('7', '1');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('6', '2');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('1', '3');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('2', '3');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('4', '3');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('5', '3');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('8', '4');
INSERT INTO `dbgcad`.`topics` (`id`, `projectId`) VALUES ('9', '4');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`proposals`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('10', 'Analysis', '6');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('11', 'Analysis', '6');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('12', 'Design', '3');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('13', 'Design', '3');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('14', 'Design', '3');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('15', 'Analysis', '7');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('16', 'Analysis', '7');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('17', 'Design', '1');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('18', 'Design', '1');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('19', 'Design', '2');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('20', 'Design', '2');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('21', 'Design', '2');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('22', 'Design', '2');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('23', 'Analysis', '4');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('24', 'Analysis', '4');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('25', 'Analysis', '4');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('26', 'Analysis', '5');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('27', 'Analysis', '5');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('28', 'Analysis', '5');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('29', 'Design', '8');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('30', 'Design', '8');
INSERT INTO `dbgcad`.`proposals` (`id`, `category`, `topicId`) VALUES ('31', 'Design', '9');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`answers`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('32', 'Contra', '10');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('33', 'Contra', '10');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('34', 'Pro', '12');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('35', 'Contra', '13');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('36', 'Contra', '13');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('37', 'Pro', '13');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('38', 'Pro', '14');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('39', 'Contra', '15');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('40', 'Pro', '15');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('41', 'Pro', '16');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('42', 'Pro', '16');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('43', 'Pro', '16');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('44', 'Pro', '17');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('45', 'Pro', '19');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('46', 'Pro', '19');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('47', 'Pro', '21');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('48', 'Contra', '21');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('49', 'Pro', '21');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('50', 'Contra', '21');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('51', 'Pro', '22');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('52', 'Contra', '23');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('53', 'Contra', '23');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('54', 'Contra', '24');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('55', 'Contra', '25');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('56', 'Contra', '25');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('57', 'Pro', '27');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('58', 'Pro', '28');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('59', 'Pro', '28');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('60', 'Pro', '29');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('61', 'Pro', '30');
INSERT INTO `dbgcad`.`answers` (`id`, `argument`, `proposalId`) VALUES ('62', 'Pro', '30');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`notifications`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`notifications` (`id`, `subject`, `knowledgeId`, `projectId`) VALUES ('1', 'a', '2', '2');
INSERT INTO `dbgcad`.`notifications` (`id`, `subject`, `knowledgeId`, `projectId`) VALUES ('2', 'b', '4', '2');
INSERT INTO `dbgcad`.`notifications` (`id`, `subject`, `knowledgeId`, `projectId`) VALUES ('3', 'c', '6', '2');

COMMIT;

-- -----------------------------------------------------
-- Data for table `dbgcad`.`notificationsUsers`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `dbgcad`;
INSERT INTO `dbgcad`.`notificationsUsers` (`idNotification`, `idUser`, `state`) VALUES ('1', '1', 'Read');
INSERT INTO `dbgcad`.`notificationsUsers` (`idNotification`, `idUser`, `state`) VALUES ('1', '2', 'Read');
INSERT INTO `dbgcad`.`notificationsUsers` (`idNotification`, `idUser`, `state`) VALUES ('2', '1', 'Unread');
INSERT INTO `dbgcad`.`notificationsUsers` (`idNotification`, `idUser`, `state`) VALUES ('2', '2', 'Read');

COMMIT;
