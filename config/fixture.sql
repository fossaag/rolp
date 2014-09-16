-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 19. März 2013 um 11:26
-- Server Version: 5.5.8
-- PHP-Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for static_fachtyp
-- ----------------------------
DROP TABLE IF EXISTS `static_fachtyp`;
CREATE TABLE `static_fachtyp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fachtyp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for static_formatierte_werte
-- ----------------------------
DROP TABLE IF EXISTS `static_pflichtfachsuchwort`;
CREATE TABLE `static_pflichtfachsuchwort` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `suchwort` varchar(255) DEFAULT NULL,
  `pflichtfachtemplate_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for static_pflichtfach
-- ----------------------------
DROP TABLE IF EXISTS `static_pflichtfachtemplates`;
CREATE TABLE `static_pflichtfachtemplates` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pflichtfachname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for static_halbjahr
-- ----------------------------
DROP TABLE IF EXISTS `static_halbjahr`;
CREATE TABLE `static_halbjahr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `halbjahr` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dynamic_authentication_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dynamic_authentication_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dynamic_lehrer
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dynamic_lehrer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `isAdmin` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Tabellenstruktur für Tabelle `config`
--
DROP TABLE IF EXISTS `static_config`;
CREATE TABLE IF NOT EXISTS `static_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `propkey` varchar(255) DEFAULT NULL,
  `propvalue` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- ----------------------------
-- Records 
-- ----------------------------
REPLACE INTO `dynamic_authentication_user` VALUES ('1', 'Schulleiter', '', '21232f297a57a5a743894a0e4a801fc3', 'admin');
REPLACE INTO `dynamic_lehrer` VALUES ('1', '1', null, true);
INSERT INTO `static_fachtyp` VALUES ('1', 'Pflichtfach'), ('2', 'Kurs');
INSERT INTO `static_pflichtfachtemplates` VALUES ('1', 'Deutsch'), ('2', 'Mathematik'), ('3', 'Englisch'), ('4', 'Geschichte');
INSERT INTO `static_halbjahr` VALUES ('1', '1. Halbjahr'), ('2', '2. Halbjahr');
INSERT INTO `static_pflichtfachsuchwort` VALUES ('1', 'Deutschunterricht', '1'), ('2', 'Englischunterricht', '3'), ('3', 'Mathe', '2'), ('4', 'Mathematikunterricht', '2'), ('5', 'Kurs', null);
INSERT INTO `static_config` (`propkey`, `propvalue`) VALUES
('localTempPath', './tmp/'),
('relativeNormalFontPath', '/VAADIN/themes/rolp/fonts/OpenSans-Regular.ttf'),
('relativeThinFontPath', '/VAADIN/themes/rolp/fonts/OpenSans-Light.ttf'),
('relativeBoldFontPath', '/VAADIN/themes/rolp/fonts/OpenSans-Bold.ttf'),
('relativeLogoPath', '/VAADIN/themes/rolp/images/demo_logo.PNG'),
('appSeverity', 'demo');