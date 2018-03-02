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

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(60) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `email` varchar(65) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `fecha_conexion` date NOT NULL,
  `numero_cuenta` tinyint(2) NOT NULL,
  `saldo` double NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

insert  into `clientes`(`dni`,`nombre`,`direccion`,`telefono`,`email`,`fecha_nacimiento`,`fecha_conexion`,`numero_cuenta`,`saldo`) values 
('11111111A','Antonio Perez','Avda. Andalucia Km. 6,200','913170047','tierno@galvan.es','1963-02-26','2010-03-17',1,146210),
('11111112B','Santiago Alonso','Avda. Andalucia Km. 6,200','913170047','tierno@galvan.es','1963-02-26','2012-03-25',2,146210),
('22222222C','Julian Orozco','Avda. Andalucia Km. 6,200','913170047','tierno@galvan.es','1963-02-26','2012-02-20',2,3300),
('33333333D','Pedro Aranguren','Avda. Andalucia Km. 6,200','913170047','tierno@galvan.es','1963-02-26','2012-02-20',1,1800);

/*Table structure for table `cuentas` */

DROP TABLE IF EXISTS `cuentas`;

CREATE TABLE `cuentas` (
  `numero_cuenta` varchar(10) NOT NULL,
  `dni1` varchar(9) NOT NULL,
  `dni2` varchar(9) DEFAULT NULL,
  `saldo` double NOT NULL,
  PRIMARY KEY (`numero_cuenta`),
  KEY `cu_dn1` (`dni1`,`dni2`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `cuentas` */

insert  into `cuentas`(`numero_cuenta`,`dni1`,`dni2`,`saldo`) values 
('0000000011','11111111A','11111112B',150710),
('0000000022','11111112B','22222222C',1500),
('0000000033','22222222C','33333333D',1800);

/*Table structure for table `movimientos` */

DROP TABLE IF EXISTS `movimientos`;

CREATE TABLE `movimientos` (
  `numero_cuenta` varchar(10) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `descripcion` varchar(200) DEFAULT NULL,
  `importe` double DEFAULT NULL,
  PRIMARY KEY (`numero_cuenta`,`fecha`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `movimientos` */

insert  into `movimientos`(`numero_cuenta`,`fecha`,`descripcion`,`importe`) values 
('0000000022','2018-03-01 18:02:21','test',20);

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `apellidos` varchar(150) DEFAULT NULL,
  `dni` varchar(10) DEFAULT NULL,
  `pass` varchar(200) DEFAULT NULL,
  `token` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`id`,`nombre`,`apellidos`,`dni`,`pass`,`token`) values 
(1,'Andrei','García','12345678A','1000:f35a6048ff7632502d1a02418cd6e4e17bafbdb90dc5dcf4:241fddd0ec26d875df27dc2979913b690658dae185f56807','W4DP5DG5IHQWKEVE2XIWK8GN4I7WZGFBKEFGW813X3MDZBDA8DTTR1DV75VE531HBA86A73ZASCCJMCMT7HCHJIC4ET55XZ');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
