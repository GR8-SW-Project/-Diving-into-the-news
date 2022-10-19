-- CREATE DATABASE dins default CHARACTER SET UTF8;
-- SHOW DATABASES;

CREATE TABLE `keywords` (
  `_id` int NOT NULL AUTO_INCREMENT,
  `keyword` varchar(256) NOT NULL,
  `importance` decimal(5,4) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3