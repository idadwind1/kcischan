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
  `status` enum('unknown','visible','deleted','deleted_by_admin','admin') NOT NULL DEFAULT 'visible',
  `board` tinyint(4) NOT NULL DEFAULT 1,
  `parent_id` varchar(10) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `attachment_extension` tinytext DEFAULT NULL,
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `boards`;
CREATE TABLE `boards` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `pinned_post_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
LOCK TABLES `boards` WRITE;
INSERT INTO `boards` VALUES
(-2,'Uncensored','CONGRADUATION!!! YOU\'VE REACHED THE OUTLAWLAND!!!!!!!!',NULL),
(-1,'Hacking','Congraduation!!! You\'ve reached the ultimate board!',NULL),
(1,'Random','Ripped off /b/. Almost everything is permitted.',NULL),
(2,'Homework','Seek help for homework & ask for notes.',NULL),
(3,'Games','Share things about games!',NULL),
(4,'Deals','Trade things!',NULL),
(5,'Romatic','Place to discuss & confess love.',NULL),
(6,'Gossip','Share insane gossip you have heard!',NULL),
(7,'Anime & Manga','Anime & Manga topic',NULL),
(8,'Complains','Complain the school, your teacher, or events!',NULL),
(9,'Technology','Discuss & seek help for technologies.',NULL),
(10,'Piracy','Share pirated software, media, or torrent.',NULL);
UNLOCK TABLES;
DROP TABLE IF EXISTS `blogs`;
CREATE TABLE `blogs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
);
