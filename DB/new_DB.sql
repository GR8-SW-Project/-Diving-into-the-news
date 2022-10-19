-- CREATE DATABASE dins default CHARACTER SET UTF8;
-- SHOW DATABASES;

CREATE TABLE `news` (
  `_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(1024) NOT NULL,
  `headline` varchar(1024) NOT NULL,
  `date_news` date NOT NULL,
  `news_link` varchar(1024) NOT NULL,
  `content` mediumtext NOT NULL,
  `category` varchar(32) NOT NULL,
  `site` varchar(32) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb3