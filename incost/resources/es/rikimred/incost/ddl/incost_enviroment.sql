-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.6.17 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             8.1.0.4545
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para incostdb
CREATE DATABASE IF NOT EXISTS `incostdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `incostdb`;


-- Volcando estructura para tabla incostdb.balance
CREATE TABLE IF NOT EXISTS `balance` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BALANCE_STATE` int(11) DEFAULT NULL,
  `BALANCE_STATUS` varchar(255) DEFAULT NULL,
  `HAS_SUB_BALANCE` tinyint(1) DEFAULT '0',
  `IN_USE` tinyint(1) DEFAULT '0',
  `LAST_UPDATE` datetime DEFAULT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `YEAR` int(11) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_BALANCE_PARENT_ID` (`PARENT_ID`),
  CONSTRAINT `FK_BALANCE_PARENT_ID` FOREIGN KEY (`PARENT_ID`) REFERENCES `balance` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.book
CREATE TABLE IF NOT EXISTS `book` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BOOK_BALANCE` decimal(12,2) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `TOTAL_BUDGET` decimal(12,2) DEFAULT NULL,
  `TOTAL_EXPENSES` decimal(12,2) DEFAULT NULL,
  `TOTAL_INCOME` decimal(12,2) DEFAULT NULL,
  `ID_USER` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_BOOK_ID_USER` (`ID_USER`),
  CONSTRAINT `FK_BOOK_ID_USER` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.book_balance
CREATE TABLE IF NOT EXISTS `book_balance` (
  `ID_BOOK` int(11) NOT NULL,
  `ID_BALANCE` int(11) NOT NULL,
  PRIMARY KEY (`ID_BOOK`,`ID_BALANCE`),
  KEY `FK_book_balance_ID_BALANCE` (`ID_BALANCE`),
  CONSTRAINT `FK_book_balance_ID_BALANCE` FOREIGN KEY (`ID_BALANCE`) REFERENCES `balance` (`ID`),
  CONSTRAINT `FK_book_balance_ID_BOOK` FOREIGN KEY (`ID_BOOK`) REFERENCES `book` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.budget
CREATE TABLE IF NOT EXISTS `budget` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(12,2) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ID_SUB_BALANCE` int(11) DEFAULT NULL,
  `READ_ONLY` tinyint(1) DEFAULT '0',
  `ID_BALANCE` int(11) DEFAULT NULL,
  `ID_CATEGORY` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_BUDGET_ID_CATEGORY` (`ID_CATEGORY`),
  KEY `FK_BUDGET_ID_BALANCE` (`ID_BALANCE`),
  CONSTRAINT `FK_BUDGET_ID_BALANCE` FOREIGN KEY (`ID_BALANCE`) REFERENCES `balance` (`ID`),
  CONSTRAINT `FK_BUDGET_ID_CATEGORY` FOREIGN KEY (`ID_CATEGORY`) REFERENCES `category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.cash
CREATE TABLE IF NOT EXISTS `cash` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(12,2) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `LAST_UPDATE` datetime DEFAULT NULL,
  `ID_BALANCE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CASH_ID_BALANCE` (`ID_BALANCE`),
  CONSTRAINT `FK_CASH_ID_BALANCE` FOREIGN KEY (`ID_BALANCE`) REFERENCES `balance` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.category
CREATE TABLE IF NOT EXISTS `category` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CATEGORY_TYPE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ID_USER` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CATEGORY_ID_USER` (`ID_USER`),
  CONSTRAINT `FK_CATEGORY_ID_USER` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.expenses
CREATE TABLE IF NOT EXISTS `expenses` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(12,2) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `EXPENSES_DATE` datetime DEFAULT NULL,
  `READ_ONLY` tinyint(1) DEFAULT '0',
  `ID_BUDGET` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EXPENSES_ID_BUDGET` (`ID_BUDGET`),
  CONSTRAINT `FK_EXPENSES_ID_BUDGET` FOREIGN KEY (`ID_BUDGET`) REFERENCES `budget` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.fixed_data
CREATE TABLE IF NOT EXISTS `fixed_data` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(12,2) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ID_CATEGORY` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_fixed_data_ID_CATEGORY` (`ID_CATEGORY`),
  CONSTRAINT `FK_fixed_data_ID_CATEGORY` FOREIGN KEY (`ID_CATEGORY`) REFERENCES `category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.income
CREATE TABLE IF NOT EXISTS `income` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(12,2) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `INCOME_DATE` datetime DEFAULT NULL,
  `ID_BALANCE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_INCOME_ID_BALANCE` (`ID_BALANCE`),
  CONSTRAINT `FK_INCOME_ID_BALANCE` FOREIGN KEY (`ID_BALANCE`) REFERENCES `balance` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.role
CREATE TABLE IF NOT EXISTS `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLEDESC` varchar(255) DEFAULT NULL,
  `ROLENAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.user
CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255) DEFAULT NULL,
  `LAST_NAME` varchar(255) DEFAULT NULL,
  `LOGIN` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.user_balance
CREATE TABLE IF NOT EXISTS `user_balance` (
  `ID_USER` int(11) NOT NULL,
  `ID_BALANCE` int(11) NOT NULL,
  PRIMARY KEY (`ID_USER`,`ID_BALANCE`),
  KEY `FK_user_balance_ID_BALANCE` (`ID_BALANCE`),
  CONSTRAINT `FK_user_balance_ID_BALANCE` FOREIGN KEY (`ID_BALANCE`) REFERENCES `balance` (`ID`),
  CONSTRAINT `FK_user_balance_ID_USER` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para tabla incostdb.user_roles
CREATE TABLE IF NOT EXISTS `user_roles` (
  `ID_ROLE` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  PRIMARY KEY (`ID_ROLE`,`ID_USER`),
  KEY `FK_user_roles_ID_USER` (`ID_USER`),
  CONSTRAINT `FK_user_roles_ID_ROLE` FOREIGN KEY (`ID_ROLE`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_user_roles_ID_USER` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.


-- Volcando estructura para vista incostdb.user_role_view
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `user_role_view` (
	`LOGIN` VARCHAR(255) NULL COLLATE 'utf8_general_ci',
	`PASSWORD` VARCHAR(255) NULL COLLATE 'utf8_general_ci',
	`ROLENAME` VARCHAR(255) NULL COLLATE 'utf8_general_ci'
) ENGINE=MyISAM;


-- Volcando estructura para vista incostdb.user_role_view
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `user_role_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` VIEW `user_role_view` AS select
`incostdb`.`user`.`LOGIN` AS `login`,
`incostdb`.`user`.`PASSWORD` AS `password`,
`incostdb`.`role`.`ROLENAME` AS `rolename`
from ((`incostdb`.`user_roles` join `incostdb`.`user`
on((`incostdb`.`user_roles`.`id_user` = `incostdb`.`user`.`ID`)))
join `incostdb`.`role`
on((`incostdb`.`user_roles`.`id_role` = `incostdb`.`role`.`ID`))) ;

