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
  `author` varchar(100) DEFAULT NULL,
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
(-2,'不受审查','你已进入无法之地！！！！！！',NULL,1),
(-1,'黑客','你已进入终极版块！',NULL,1),
(0,'测试','Testiuguo gfilyvsf vbiyfl gdv yi',NULL,1),
(1,'随机','抄袭 /b/。几乎什么都允许',NULL,1),
(2,'作业','求助作业 & 要笔记',NULL,0),
(3,'游戏','讨论游戏',NULL,0),
(4,'交易','用来交换物品',NULL,0),
(5,'恋爱','用来讨论 & 表白',NULL,0),
(6,'八卦','分享你听到的超级劲爆八卦',NULL,0),
(7,'动漫','关于动漫 & 漫画的讨论区',NULL,0),
(8,'吐槽','吐槽学校、老师、或活动',NULL,0),
(9,'科技','讨论科技问题或求助',NULL,0),
(10,'盗版','分享盗版软件、媒体或种子',NULL,0),
(11,'支持','联系管理员求帮助或提建议',NULL,0);
UNLOCK TABLES;

DROP TABLE IF EXISTS `blogs`;
CREATE TABLE `blogs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
);
