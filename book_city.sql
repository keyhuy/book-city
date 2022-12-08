/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.27 : Database - book_city
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`book_city` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `book_city`;

/*Table structure for table `b_book` */

DROP TABLE IF EXISTS `b_book`;

CREATE TABLE `b_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `price` decimal(11,2) DEFAULT NULL,
  `sales` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `img_path` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `b_book` */

insert  into `b_book`(`id`,`title`,`author`,`price`,`sales`,`stock`,`img_path`) values (1,'《C++》','小红','899.00',200,400,'static/img/java.png'),(2,'《Mybatis》','小谭','879.00',206,394,'static/img/java.png'),(3,'《java》','小月','999.00',210,390,'static/img/java.png'),(4,'《python》','小春','999.00',207,393,'static/img/java.png'),(5,'《SQL》','小绿','999.00',200,400,'static/img/java.png'),(6,'《javaScript》','小小','999.00',201,399,'static/img/java.png'),(7,'《html》','小东','499.80',201,399,'static/img/java.png'),(8,'《css》','小南','999.00',200,400,'static/img/java.png'),(9,'《spring》','小慧','999.00',203,397,'static/img/java.png'),(11,'《JQuery》','小蓝','119.00',200,400,'static/img/java.png'),(12,'《GoLang》','小龙','250.60',200,300,'static/img/java.png'),(13,'《c》','小何','303.00',220,300,'static/img/java.png'),(14,'《Vue》','小西','30.00',205,295,'static/img/java.png'),(17,'《Linux》','小王','303.45',221,344,'static/img/java.png'),(18,'《C#》','小雯','303.34',250,335,'static/img/java.png'),(19,'《MySQL》','小金','303.78',1000,665,'static/img/java.png'),(20,'《Redis》','小钟','78.90',220,333,'static/img/java.png');

/*Table structure for table `b_order` */

DROP TABLE IF EXISTS `b_order`;

CREATE TABLE `b_order` (
  `order_id` varchar(50) NOT NULL,
  `order_time` datetime DEFAULT NULL,
  `order_price` decimal(11,2) DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `b_order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `b_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `b_order` */

insert  into `b_order`(`order_id`,`order_time`,`order_price`,`order_status`,`user_id`) values ('1632645320324','2021-09-26 16:35:20','3996.00',2,2),('1632645518089','2021-09-26 16:38:38','2181.78',2,1),('1632668840453','2021-09-26 23:07:20','879.00',0,3),('1632750907927','2021-09-27 21:55:08','999.00',2,2),('1632815842442','2021-09-28 15:57:22','1998.00',1,3),('1633252615152','2021-10-03 17:16:55','1498.80',0,1);

/*Table structure for table `b_order_item` */

DROP TABLE IF EXISTS `b_order_item`;

CREATE TABLE `b_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `price` decimal(11,2) DEFAULT NULL,
  `total_price` decimal(11,2) DEFAULT NULL,
  `order_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `b_order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `b_order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

/*Data for the table `b_order_item` */

insert  into `b_order_item`(`id`,`name`,`count`,`price`,`total_price`,`order_id`) values (18,'《java》',1,'999.00','999.00','1632645320324'),(19,'《javaScript》',1,'999.00','999.00','1632645320324'),(20,'《spring》',2,'999.00','1998.00','1632645320324'),(21,'《python》',1,'999.00','999.00','1632645518089'),(22,'《Mybatis》',1,'879.00','879.00','1632645518089'),(23,'《MySQL》',1,'303.78','303.78','1632645518089'),(24,'《Mybatis》',1,'879.00','879.00','1632668840453'),(25,'《spring》',1,'999.00','999.00','1632750907927'),(26,'《java》',2,'999.00','1998.00','1632815842442'),(27,'《java》',1,'999.00','999.00','1633252615152'),(28,'《html》',1,'499.80','499.80','1633252615152');

/*Table structure for table `b_user` */

DROP TABLE IF EXISTS `b_user`;

CREATE TABLE `b_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `b_user` */

insert  into `b_user`(`id`,`username`,`password`,`email`) values (1,'Tom','Tom123','tom@qq.com'),(2,'Key','Key123','key@qq.com'),(3,'Tim','Tim123','tim@qq.com'),(9,'Dacy','Dacy123','dacy@qq.com'),(10,'baby','baby123','baby@qq.com'),(11,'Jay','Jay123','jay@qq.com'),(12,'Kim','Kim123','kim@qq.com'),(13,'Andy','Andy123','andy@qq.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
