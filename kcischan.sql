-- CREATE DATABASE kcischan;
-- CREATE USER 'kcischan'@'localhost' IDENTIFIED BY '';
-- GRANT ALL PRIVILEGES ON kcischan.* TO 'kcischan'@'localhost';
-- USE kcischan;
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
  `id` varchar(10) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `from_admin` tinyint(1) NOT NULL DEFAULT 0,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `board` tinyint(4) NOT NULL DEFAULT 1,
  `parent_id` varchar(10) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `attachment_extension` tinytext DEFAULT NULL,
  `op` varchar(100) DEFAULT NULL,
  `trip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `boards`;
CREATE TABLE `boards` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `pinned_post_id` varchar(10) DEFAULT NULL,
  `hidden` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);
LOCK TABLES `boards` WRITE;
INSERT INTO `boards` VALUES
(-2,'Uncensored','CONGRADUATION!!! YOU\'VE REACHED THE OUTLAWLAND!!!!!!!!',NULL,1),
(-1,'Hacking','Congraduation!!! You\'ve reached the ultimate board!',NULL),
(0,'Test','Testiuguo gfilyvsf vbiyfl gdv yi',NULL,1),
(1,'Random','Ripped off /b/. Almost everything is permitted',NULL,1),
(2,'Homework','Seek help for homework & ask for notes',NULL,0),
(3,'Games','Share things about games!',NULL,0),
(4,'Deals','Trade things!',NULL,0),
(5,'Romatic','Place to discuss & confess love',NULL,0),
(6,'Gossip','Share insane gossip you have heard!',NULL,0),
(7,'Anime & Manga','Anime & Manga topic',NULL,0),
(8,'Complains','Complain the school, your teacher, or events!',NULL,0),
(9,'Technology','Discuss & seek help for technologies',NULL,0),
(10,'Piracy','Share pirated software, media, or torrent',NULL,0),
(11,'Support','Ask for support from admins (could be suggestions or reports)',NULL,0);
UNLOCK TABLES;
DROP TABLE IF EXISTS `blogs`;
CREATE TABLE `blogs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
);
