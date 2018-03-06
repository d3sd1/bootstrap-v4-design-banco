/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 5.5.58-0ubuntu0.14.04.1 : Database - sql11223846
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sql11223846` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `sql11223846`;

/*Table structure for table `api_calls` */

DROP TABLE IF EXISTS `api_calls`;

CREATE TABLE `api_calls` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_key` varchar(255) NOT NULL,
  `call_timestamp` datetime NOT NULL,
  `call_page` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `api_calls` */

insert  into `api_calls`(`id`,`api_key`,`call_timestamp`,`call_page`) values 
(1,'1CJXtd84aKXCSZG5xzworuBv47TNV4','2018-02-05 17:27:16','/notas'),
(2,'1CJXtd84aKXCSZG5xzworuBv47TNV4','2018-02-05 17:27:16','/alumnos'),
(3,'1CJXtd84aKXCSZG5xzworuBv47TNV4','2018-02-05 17:27:16','/asignaturas');

/*Table structure for table `api_keys` */

DROP TABLE IF EXISTS `api_keys`;

CREATE TABLE `api_keys` (
  `key` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`key`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `api_keys` */

insert  into `api_keys`(`key`,`description`) values 
('zfHgQTnBxUwQyI8SgdEAdaQSauHp6J23FlaV63wVcM1buqYjiRC','Cliente PHP'),
('1CJXtd84aKXCSZG5xzworuBv47TNV4','Cliente Java');

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(60) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `email` varchar(65) NOT NULL,
  `fecha_nacimiento` timestamp NULL DEFAULT NULL,
  `fecha_conexion` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `conteo_cuentas` tinyint(2) NOT NULL,
  `saldo` double NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

insert  into `clientes`(`dni`,`nombre`,`direccion`,`telefono`,`email`,`fecha_nacimiento`,`fecha_conexion`,`conteo_cuentas`,`saldo`) values 
('11111112B','Santiago Alonso','Avda. Andalucia Km. 6,200','913170047','tierno@galvan.es','1970-04-10 00:00:00','2012-03-25 00:00:00',2,146210),
('22222222C','Julian Orozco','Avda. Andalucia Km. 6,200','913170047','tierno@galvan.es','1970-09-19 00:00:00','2012-02-20 00:00:00',2,3300),
('33333333D','Pedro Aranguren','Avda. Andalucia Km. 6,200','913170047','tierno@galvan.es','1971-05-08 00:00:00','2012-02-20 00:00:00',1,1800);

/*Table structure for table `clientes_cuentas` */

DROP TABLE IF EXISTS `clientes_cuentas`;

CREATE TABLE `clientes_cuentas` (
  `dni` varchar(9) NOT NULL,
  `numero_cuenta` varchar(10) NOT NULL,
  PRIMARY KEY (`dni`,`numero_cuenta`),
  KEY `fk_3` (`numero_cuenta`),
  CONSTRAINT `fk_2` FOREIGN KEY (`dni`) REFERENCES `clientes` (`dni`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_3` FOREIGN KEY (`numero_cuenta`) REFERENCES `cuentas` (`numero_cuenta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clientes_cuentas` */

/*Table structure for table `cuentas` */

DROP TABLE IF EXISTS `cuentas`;

CREATE TABLE `cuentas` (
  `numero_cuenta` varchar(10) NOT NULL,
  `saldo` double NOT NULL,
  PRIMARY KEY (`numero_cuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `cuentas` */

insert  into `cuentas`(`numero_cuenta`,`saldo`) values 
('0000000033',1800);

/*Table structure for table `movimientos` */

DROP TABLE IF EXISTS `movimientos`;

CREATE TABLE `movimientos` (
  `numero_cuenta` varchar(10) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `descripcion` varchar(200) DEFAULT NULL,
  `importe` double DEFAULT NULL,
  PRIMARY KEY (`numero_cuenta`,`fecha`),
  CONSTRAINT `fk_1` FOREIGN KEY (`numero_cuenta`) REFERENCES `cuentas` (`numero_cuenta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `movimientos` */

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `apellidos` varchar(150) DEFAULT NULL,
  `dni` varchar(10) DEFAULT NULL,
  `pass` varchar(200) DEFAULT NULL,
  `token` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`id`,`nombre`,`apellidos`,`dni`,`pass`,`token`) values 
(1,'Andrei','Garcia','12345678A','1000:af516c67fe4d88cab91392a7d7427bc24ce4aad07c45111c:6ae301777891ac3b290ccf10b375a166de891c98dcc07559','eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCQlZBIiwic3ViIjoiSU5URVJOQUxfTE9HSU4iLCJuYW1lIjoiQW5kcmVpIEdhcmNpYSIsImRuaSI6IjEyMzQ1Njc4QSIsImlhdCI6MTUyMDM1OTQzMCwiZXhwIjoxNTIwMzY2NjMwfQ.AEXPvanyyJoGjoGEY_2LzO7LRxaw2ZaqwttTj5B-Jm4'),
(4,'HOLACA23','test568','12345687B','1000:7b97dbf23aad4cbc8796cea78d9ab338cdd062f26ef571f0:46fac134f8eb43d7601d1bc860ad6b7f5ad8c91c99441aff','eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCQlZBIiwic3ViIjoiSU5URVJOQUxfTE9HSU4iLCJuYW1lIjoiSE9MQUNBMjMgdGVzdDU2OCIsImRuaSI6IjEyMzQ1Njg3QiIsImlhdCI6MTUyMDM1NTI5OCwiZXhwIjoxNTIwMzYyNDk4fQ.n7zWRiCSUbf8BXEtk9qgnGZoGv7p3_ezVUU065xmeEc'),
(5,'23213','12','12345678C','1000:829911d75e2e7e1f68f62f399f2905290ab9785f24bb30a3:1e5f51aa786d0fe070e436d517ca176afd380a0141ed885a',NULL),
(6,'test 2','tesr 3','12345678G','1000:d960638c621389b0c1cf21212ba4e35e7e9f3607686a393b:cb82529b0ac907e132ab5d369c6d996e4fe2a971f9f05ed8',NULL),
(7,'xdd','xd','12345678L','1000:a856f4f2c5c061c09ede3415d6375190cb9c8c50d5d7c33d:642ce21904db1e764127707ded912e79d36ace4cbdca7136',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
