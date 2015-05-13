-- MySQL dump 10.13  Distrib 5.5.41, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mobile-campus
-- ------------------------------------------------------
-- Server version	5.5.41-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `career`
--

DROP TABLE IF EXISTS `career`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `career` (
  `code` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `UK_4i26x57mopr9pseu6r3d1faia` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `career`
--

LOCK TABLES `career` WRITE;
/*!40000 ALTER TABLE `career` DISABLE KEYS */;
INSERT INTO `career` VALUES (1,'IngenierÃ­a CIVIL'),(11,'IngenierÃ­a de ALIMENTOS'),(6,'IngenierÃ­a ELECTRICISTA'),(7,'IngenierÃ­a ELECTRÃ“NICA'),(4,'IngenierÃ­a en AGRIMENSURA'),(10,'IngenierÃ­a en INFORMÃTICA'),(2,'IngenierÃ­a INDUSTRIAL'),(5,'IngenierÃ­a MECÃNICA'),(3,'IngenierÃ­a NAVAL Y MECÃNICA'),(8,'IngenierÃ­a QUÃMICA'),(9,'Licenciatura en ANÃLISIS DE SISTEMAS');
/*!40000 ALTER TABLE `career` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isEnabled` tinyint(1) DEFAULT NULL,
  `distanceInKm` double DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kjggw5t2ownpf1udf58vmyv5r` (`userName`,`type`),
  KEY `FK_lv7uh5i3w6ryhhvmp7edjqtwe` (`userName`),
  CONSTRAINT `FK_lv7uh5i3w6ryhhvmp7edjqtwe` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES ('LOCATION_CONFIGURATION',1,1,5,'89001'),('LOCATION_CONFIGURATION',2,1,10,'89002'),('LOCATION_CONFIGURATION',3,1,10,'I89003'),('LOCATION_CONFIGURATION',4,1,5,'89004'),('LOCATION_CONFIGURATION',5,1,2,'89005'),('LOCATION_CONFIGURATION',6,1,10,'89006'),('LOCATION_CONFIGURATION',7,0,10,'I89007'),('LOCATION_CONFIGURATION',8,0,10,'89008'),('LOCATION_CONFIGURATION',9,0,10,'89009');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups_students`
--

DROP TABLE IF EXISTS `groups_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups_students` (
  `groupId` int(11) NOT NULL,
  `userName` varchar(255) NOT NULL,
  PRIMARY KEY (`groupId`,`userName`),
  KEY `FK_n7oo7lkoktwcifiyhmhi5uvh` (`userName`),
  KEY `FK_hpgy9nt18ruusuweevxu9g5n9` (`groupId`),
  CONSTRAINT `FK_hpgy9nt18ruusuweevxu9g5n9` FOREIGN KEY (`groupId`) REFERENCES `study_group` (`id`),
  CONSTRAINT `FK_n7oo7lkoktwcifiyhmhi5uvh` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups_students`
--

LOCK TABLES `groups_students` WRITE;
/*!40000 ALTER TABLE `groups_students` DISABLE KEYS */;
INSERT INTO `groups_students` VALUES (1,'89001'),(2,'89001'),(3,'89001'),(4,'89001'),(5,'89001'),(6,'89001'),(7,'89001'),(8,'89001'),(9,'89001');
/*!40000 ALTER TABLE `groups_students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `high_school`
--

DROP TABLE IF EXISTS `high_school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `high_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateFrom` datetime DEFAULT NULL,
  `dateTo` datetime DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `schoolName` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_igtktbf7mro8sfkbsijmmd2qk` (`userName`),
  KEY `FK_igtktbf7mro8sfkbsijmmd2qk` (`userName`),
  CONSTRAINT `FK_igtktbf7mro8sfkbsijmmd2qk` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `high_school`
--

LOCK TABLES `high_school` WRITE;
/*!40000 ALTER TABLE `high_school` DISABLE KEYS */;
INSERT INTO `high_school` VALUES (1,'2005-03-11 00:00:00','2010-12-11 00:00:00','TÃ©cnico en electrÃ³nica','Otro Krause','89001'),(2,'2008-03-07 00:00:00','2011-12-08 00:00:00','Bachiller en Cs EconÃ³micas','Media Nro 3','89002'),(3,NULL,NULL,'Bachiller Contable','EEM Nro 1','89005'),(4,'2011-05-07 00:00:00','2012-05-11 00:00:00','Secundario','Media 13','89009');
/*!40000 ALTER TABLE `high_school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(255) DEFAULT NULL,
  `dateFrom` datetime DEFAULT NULL,
  `dateTo` datetime DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8dl3c63q97ifs14uf5dwtkskr` (`userName`),
  CONSTRAINT `FK_8dl3c63q97ifs14uf5dwtkskr` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'PanaderÃ­a Pan de Dios','2013-05-11 00:00:00','2014-01-14 00:00:00','Aprendiz de panadero','89001'),(2,'ElectrÃ³nica La Resajjjjjjqqqwqqqwwww. hola chau que tal hila','2014-09-11 00:00:00','2015-05-09 00:00:00','DiseÃ±ador de circuitos','89001'),(3,'La Serenisima','2014-08-11 00:00:00',NULL,'Pasante','89002'),(4,'Chemical Brothers','2008-05-11 00:00:00','2011-03-11 00:00:00','TÃ©cnico en humo','I89003'),(5,'Monsanto','2013-03-12 00:00:00',NULL,'Fertilizador','I89003'),(6,'La llama que llama','2014-05-11 00:00:00',NULL,'Telemarketer','89006'),(7,'Tienda El buen vestir','2012-04-06 00:00:00',NULL,'Vendedora','89008'),(8,'Software Pepito','2012-08-09 00:00:00',NULL,'Developer','89004');
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6r3b9p3cdx1i867n0uuojgvcu` (`userName`),
  KEY `FK_6r3b9p3cdx1i867n0uuojgvcu` (`userName`),
  CONSTRAINT `FK_6r3b9p3cdx1i867n0uuojgvcu` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,-34.61458765,-58.37281021,'89001'),(2,-34.616655,-58.36461,'89002'),(3,-34.620389,-58.392098,'I89003'),(4,-34.315818,-59.37331,'89004'),(5,-34.115818,-58.57331,'89005'),(6,-34.915818,-59.37331,'89006'),(7,-34.612086,-58.382469,'I89007'),(8,NULL,NULL,'89008'),(9,NULL,NULL,'89009');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mates`
--

DROP TABLE IF EXISTS `mates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mates` (
  `userName` varchar(255) NOT NULL,
  `mateUserName` varchar(255) NOT NULL,
  KEY `FK_k6ke838lp1kn3iqpbnm7dft8n` (`mateUserName`),
  KEY `FK_bli0t8o6clpby9txnn8mpon6p` (`userName`),
  CONSTRAINT `FK_bli0t8o6clpby9txnn8mpon6p` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`),
  CONSTRAINT `FK_k6ke838lp1kn3iqpbnm7dft8n` FOREIGN KEY (`mateUserName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mates`
--

LOCK TABLES `mates` WRITE;
/*!40000 ALTER TABLE `mates` DISABLE KEYS */;
INSERT INTO `mates` VALUES ('89001','89008'),('89001','89005'),('89001','I89003'),('89001','I89007'),('89001','89002'),('89001','89006'),('89001','89004'),('89002','89001'),('89002','89009'),('89002','I89007'),('89002','I89003'),('89002','89006'),('I89003','89001'),('I89003','89002'),('I89003','I89007'),('I89003','89004'),('I89003','89006'),('I89003','89008'),('89004','89001'),('89004','I89003'),('89004','89009'),('89004','89005'),('89004','I89007'),('89004','89008'),('89009','89002'),('89009','89004'),('89009','89005'),('89005','89001'),('89005','89004'),('89005','89009'),('89005','89008'),('89005','I89007'),('I89007','89001'),('I89007','89002'),('I89007','I89003'),('I89007','89004'),('I89007','89005'),('89006','89001'),('89006','89002'),('89006','I89003'),('89006','89008'),('89008','89001'),('89008','I89003'),('89008','89004'),('89008','89005'),('89008','89006');
/*!40000 ALTER TABLE `mates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `isViewed` tinyint(1) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `applicantUserName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t8co5610disdud58lt0bdmy8d` (`userName`),
  KEY `FK_l04owyjpk0nbprajw9a1n0p16` (`applicantUserName`),
  CONSTRAINT `FK_l04owyjpk0nbprajw9a1n0p16` FOREIGN KEY (`applicantUserName`) REFERENCES `student` (`userName`),
  CONSTRAINT `FK_t8co5610disdud58lt0bdmy8d` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES ('APPLICATION_NOTIFICATION',5,'2015-05-11 13:53:31',0,'89002','89004'),('APPLICATION_NOTIFICATION',8,'2015-05-11 13:56:39',0,'89002','89005'),('APPLICATION_NOTIFICATION',10,'2015-05-11 13:56:54',0,'I89003','89005'),('APPLICATION_NOTIFICATION',13,'2015-05-11 14:01:18',0,'89004','89006'),('APPLICATION_NOTIFICATION',14,'2015-05-11 14:01:24',0,'89005','89006'),('APPLICATION_NOTIFICATION',20,'2015-05-11 14:04:58',0,'89006','I89007'),('APPLICATION_NOTIFICATION',23,'2015-05-11 14:07:55',0,'89002','89008'),('APPLICATION_NOTIFICATION',28,'2015-05-11 14:08:14',0,'I89007','89008'),('APPLICATION_NOTIFICATION',29,'2015-05-11 14:12:14',0,'89001','89009');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orientation`
--

DROP TABLE IF EXISTS `orientation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orientation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lo4lypyo2suamw0kuqvhteegd` (`code`),
  CONSTRAINT `FK_lo4lypyo2suamw0kuqvhteegd` FOREIGN KEY (`code`) REFERENCES `career` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orientation`
--

LOCK TABLES `orientation` WRITE;
/*!40000 ALTER TABLE `orientation` DISABLE KEYS */;
/*!40000 ALTER TABLE `orientation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contentType` varchar(255) DEFAULT NULL,
  `image` longblob,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lyw2vrecb83vbodehe9swpl0t` (`userName`),
  CONSTRAINT `FK_lyw2vrecb83vbodehe9swpl0t` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (1,'image/png','ÿØÿà\0JFIF\0\0\0\0\0\0ÿÛ\0„\0	( %!1!%)+...383,7(-.+\n\n\n\r,$$,,,,,,,,,,,,,,,,,,,,.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,ÿÀ\0\0á\0á\"\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0F\0	\0\0\0\0\0!1AQq\"a‘¡±2BRÁÑğ‚á#3r’ñCb²Â$4Ssƒ“¢ÒâÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0,\0\0\0\0\0\0\0!1AQBa\"2‘¡±qÁÿÚ\0\0\0?\0ôøA8$	€€rœaøJQ:„\0!Os´Vm1¼÷$Àï(ÁY‹ÓÒßf0úí$iO§ÙÑUÖ?KuGëÎìp¶ v fâ…ƒµz[°2¦ä½ØÁ{[¼Ìâdf>‹k`·Ó®ÀêOkÁÑ çË$ï\'Â€PO„!\01O„!\01øM!\05$a@	)B@”W4“\0„BH„€(€Nà8\0€N ˆ@\n\\-ÖÊt)º¥WµŒh—9Æ\0^#·ş“j•”št1—bPq?OøğCv\ZW4ÛwéM¶rê8}\\ZjàZÒ3İ÷3‡5ãW¥åZĞâúõò~\'O†C°(ÔŸ¼yæTÑÁÄœ¼;möj—EaíIƒãœ©U©pÀuæ£­;’ĞòÆüøŸ5ÒÏi}#4ª–‘‘c‹OÁQÜ<°®on/J6ë;C\\öÖŒYs i¼÷ÊöˆÚ¯ÖT‹0Ç6ƒ^ÌD‚×g—¾Z1Z­‡Úçİ¶U’i»\nÔ°é·ˆŸxNi®}?(wñFÛA•è;yhAÕ®\ZtSˆL„ ByB\0æP]M!\00„ÈM!\00 B|& ÂIÉ&\08$‚CN\0œ€@à6½VÓcã\rh.$ä\0JèÍzC¶²†¡ªHa7ŸuƒLw hğÿ\0I{eRğ´	m‡öl2&=÷$ñÉdóì¼Ó-•KŞçÄ“!À “A¨àÖ‚N@|Òe®‰t\Z;µšÂíÜAîÃ±kvaäÕ>ô^Kf©Æè\\U+khôğú^G‡~ª/Ğò…Î­Ëêö}ËI¤€1×2\0ù¬ííA­Ã <¸s+%^W5xhØó:Ö0ÜÛ÷Ù÷‚æËa£<VÖÀúî’Øn€ç\nØ\\Í§Ñ`—Î€¼§5£ÄXÍaoş6´]D:ÔWÙ1ĞÏrôJ÷^ñâŒ2LëÍQm—pb \ZÑi<UB»zSer.Åí­¢ì\'Ôên-5)8K]ß…Ñ„÷ƒé=½™m²Ò´S6«w€$4 ‘¨+äq„¯Nô\'µn£_ô7»öUd°ìTÌîõ8q´àh÷”A…„¤€M)È ”\nt!©#	&	Á\0ˆHÀ@\'\0“‚@\"\0!|÷égj\rªÔæ4Í*$±ƒBáƒŸ\Z’r<Ğ¹ŞF{®pa|§|´Š¯ŞÌ8Ï1ù©‘pEc¨^%z£-cÜê¯ÇvGÇÁaì./ªX}ËÛöJï(†ë\\õäíc·\rŞcUf¢\Z\0vªL@\\ì£ÔÔhÔw®{…íg{GìÛ.:œãÖUu‹e‰;Õ1=|x­~ûN¡t\n<ey4ØÎUºKD7¬f{U{îÇ;wP:3ÕÇ¼â¶ *«Mïg¦zuXŞeC¦RŸe]+¬\rÄ0E¼®juM ÄÄè­…ïf³Z™üAGµÛé<ÇµÄh£+@ä™âÛaqšOsÀ¤àÎØ-¤öÔa‡Ss^ÓÀ´‚«í…œ>ÎN¢JòËC¹xj½\nn\ZğyxšiONO«6b÷m¶ÉF»cöŒÀİxÁÍì2¬áyo ‹@õéïe@wNPá!Àxv/S]äjÌl œ@€I$\0(‚\0j)$˜\'€N NàD$p8À$ä+ä›ò×ëkÖpÀ:­GEÄõ¨K­>KåÍ¿»[e·T¢Ì˜‰À’î‘>)2âTlğŞµÓ^÷Ó]´©o8ÀhÄ¯Ÿ.zŞ®ÑMÜŠúÕf)4‰sbÔÜ#úYš¼-v‹A&“œÖœ¢sÃ>õŸ¼¶fß$º³Hã¾áàVªõ¼E™£&4føË€hÕÅgo»æĞİØ¢7^Àæšuj™ŒE‡\r0Œ,!™ı§MEN*óaÙÿ\0Ò,îéUßÇ)\'Íz•Ûkõ–\nÇs¹Ì§Qû1ˆ§Ñpéá=!„çªÙlİLA3Ÿä²m¹jmdĞuûk,¦t1‚ò{Òêu¥İ*¤IÈIí…éÛ_Oy¸,à¹İê^XıÇĞ\0KêDºH ˜ù\"-§ ¥ãª3÷nÁR\0oZ_1€Á˜ö‰Vm¹Ÿf±Óq=ª¾Ãúp5}c¡­\"hp D\ríĞd™%.ã¼\r@Ao\Znœ»§‚¹¹-[¹8ÓkéMµÕ5,Î\'‹¤ry5û¤õáâ½’ö³îÙê˜ƒ‚ñI]8]S8ñz4{G `=u¯e],W±…ä~€©Ë-u8¾›GáiÕzØ+¥lqËp S“Jd!âš€ B(À‚) ‚\0\'€pN4\'\0àQN@\0¯ôë³Û®m­£ÛpcÎƒ¢q=Á{yY¯Hwp´İ¶šdIõnsué°K|BL¤|Ãú²£=[ÜØpİâNy/¥l´º\r@<–Õ³ôÿ\0G«T´Št^\'İphŞğ[Û¾¶øiĞ€{ÂâLèõiĞTŸ{nÏÓ«‹š	ïR¸:æ€0\'wÙŞ‚)ÉiÌ4Ö9Mü…¥ÀË`Sì89Kµ:\ZJasnÇT’³+5ÕÙhq¨¨T.İà?’²¾«R-à¸ÜµÀ–Lğ=JÜ4Ş[‘Ê1óO¥t1£ÙÁ^î™ZœYEä¹™½ìáÌsx´ñÂMÊâÜŞ;ÿ\0ÃĞÓÍ{½ìè‚2×©dî;±¶–\0p>±àª›Íÿ\0PZQ›†Æ3¥\Z’×¦z£‹•–K-Á¨ÖÔ|æ\\à¡s²ÒcZ2kCG !t^’<v8%	PHÒšSŠi@	ŠE\01$a$À!<&ğ„p@ \0!q¯L9¤AÓRîš‚‘½.öÃ©º!Æf0Ä^åQ²5Ïªk]í0šgğ˜Émmör]¼=1ôÕcœöÓµ¹ÂZ×‘f<á–LõaQNàÖRz¢Ùê#^Ò\Z¡²Ôu9Ş4‹éÀõ¯?uÇPÕõ«Y®¤Tî:pÜ€[ÀâóÕ®8 E¤‚ö‡ñQnMâì­k{zÜ5j€MZ€H¦ïk™Í^ìu…ì‚÷ˆkK‹™qZ\n¥­‡=¸ğ2£n2&“\ZÁÓ’†‹O›X½a\\m5p*ºËk<çÁc‰iÇ4ó8Ø ¿+Ã™™ËÍHÙ;±Ì©NrpÈn¬(WƒâÆ³\ZŒîá+uvØ[Ha$Ä~ğ[Q¦ågÁZÊ	®Z-AZ½ÊcÒ)$‚@P(”P(\0$’IŒ!8&„à‡à˜Â\0pNME\0×àƒ1«µôwmözƒß¥R›»^ÙîwzÛª}¤²‡R.‰s´œcW‹5¤í$UYë`zÔ·§¯.¥KNÕìîÉ×1ıU­;Fï%Á#Ô‹*­Wr’ÖL5 :8oò\\™rR`ı¥J‡‰/WâĞ–j=¢‰#)ÍMì˜Ti¯»,Ç\0\\OüÃİQ¿³.šo¨Î°÷óJ°²Xq.IÃÉYnî¨ÌÍVÕ†Ø®¶Ğ§º	q2Iy“ŠxTİlgÑu­mÑR^¶½ü\'ï±ä“&\\;KpÊN8ä#è¶´–Oc¨NıLıÁÇ98÷-m ½\Z\nĞ<Úò¼Î©í\\×F­f9$%\"P!%	@ME˜$@\'„ÀœÁ<&„à @‚!$2£^Bi?’˜B‰xşéü”ËfiOîGšÛÿ\0ÙêC½“;„\'Ù>0§Q´ÃLc<{üía[ˆœ8j±Ô¤z.;º HÓÂìÏQhkìÕÀsğO¡SxFBpëûÁyõŠı-vëğ#)ò‡XVÚmÆë2Èé‚—\rLØ´1*Ûe 3xÈÀÎ8vcÉgµ\rÂt\0òÏ%Sxíø3–„œ	â¥«9ek¼†ö8a$NeSWµa¢\\L\0J¯kßTã-aºÃ–Jâê°ã\'=QeUän6ŒYÈàısÄ¦h…E²\")¼ppòWËĞ¢ïyõÕ¦ÂècE¡Î(B”%0	JR€\nM%\0’”	Á0\'€xN	€§	É€§ AEPï#û\'uÀñRét²P/ÖàÎÇ¾?ª‰Ëém\ZÓ_ZEQf\n®ÛbäUİ6¦U¥÷ó\\,ôÓ0¶íŸmY=c,Œh¨m[2öœ7ˆÈ$Õè6ª.iÂrì\\·çúGŠ.ÖÁd÷<ãõ-`t`j8å¥–è|Éf=z}ö¡İ€ìŸªQ›Ã¿IRæÆ ¸(ìV2sëÑ[Ó¡û9p]\02&Í%lÅ`Òö“»+FV>êa6†«±åªÚšß†•ácÍÅ+NıŒjzæ]º`àºJèM3•¡ Š&!„@L\0’(\0E\0B)¨„€xN0\'\0ğS—\Z•ƒD’\0UUm£ zÎàOŒ(”Ô~JQ¹rê¹¹ÜN3mÚ•!ÄïT¶‹Ê­\\òDä áYZ¤÷Ğ¼ĞÚ‡uÛ©Ô–±ÁÄc‡×U\"ÓD=¥§°ğ<W›İvãB {tÏ8#‚ô{%©µ˜ÓÌj¶Q²±šİÊz–g0ÁüŠa@æ‚ ‰\nk¶qiì?UÏ:-lvÓÄ/qMRœªú¶1Œa÷Á\\×³9¹ƒ÷Ö¡<•ƒÓs®-=ŠJÖbs?Õ\Z6\\uVnÓ¥+64CZÈ\\j©onL±]y—ÖêN°!AËDDª(«²>Í]äÔ5Ào3™ìkJÑ$ƒ\Z\ZÑ\0`:õ›F›ìDŸ¢ô)Ã$lyUjg•ÌşĞ_ÔèÖõn˜Da:y)7uµµ[½MÒ8pæ4X+ÒÒkUsÎ$’y(Ìyo²HäTJİâìÈ^Ç©µóÍ^^Ûm@AÄbVŠÃµÆ\0¨Ù9}pV±nÚ“tö5²Œ¨vÊoaÂ~ƒ‡b–¬(J	&I$Es{ÃA$À’t?nÚÊmèÒîã>¥!š*µÚÁ. %g/=¬\nBÄ~APÚŸh´:^P0\0ìLeÒóèíú¤{6XjÏh³ªİR¾/q#†ËƒYŠ´¥s_Ü>ª[.–e$ó?’,¢ÀW{«%+DÔ†µÇì+–]¬á<É9)4é5¹\09KÌº4¦Ëİ$R2È÷`\Z{pv«‹˜T³ºw„jÜL©9K«#xàiG}M=’ÚÚGRwV@2*}šø{}¬G_Õ\\jöeW÷ •Ê¥Íƒº<”Z7Í7g#Ä)LµÓ98yy­/r:u ögvÒø|J{lTÇ»âJíë™ñ7¼&›U1ï7½NXtƒ5GßìsÖäĞ9ã%D«{Rn¤òU_iÚ½§è‡8¢ã†«>?%ÅB\Z%ÆËß¶Áhèû€ñ\"OE­õ¸Ïß¬\'U½G‚Œ5¯ô@}ÖÃ‘#\\çÍF}Ê~ yŒUÊ¡T’äÚXJ2Ş?ğÏÔ¹˜ÙM}ÑSHïZ\rÔ×üÒ#üußäÏ¶«L·1‘æ®®ûş½(˜^ßˆ{Cê»˜ôÕy\"_¦R{6h¬W…:ÂXàxŒˆæ¥‰}<w„‚2pÀÕs^f İ¶5Ëxq+zu”´äâÄàjQY·]–è&ï$¶8N6ë?­¤öœ‹HY;=œ3\0Ğ8À[šbBËZlõ¯kA&g9®lBvG­ésŠrN×ìĞºjeª«³du¸à¡p;W·¸¬9=‘èOF;ÉÑZRpv9+f\\<_Üß©]Ûq7W»ÁZ¥3XÚş™J\n;Êı—-1ñÄ~JE;¶˜÷n>j•ËK„ÌÖ|J“FÇQÙ1İ¸y­3)	ò­Qí˜KÔ¶&}·ESèí]Ùqyã°+©JUªQ1xÚ¯m\n¶Ü,ÕÎì€º¶å¦>#ø•„¥*¼qèÍâj¿s!¶ê¤=Şòïªx»©|\rí¤ÊRUÑµGî“‡ètşÿ\0(HÙğ7ùBí(‹\"sË²9°Óøü¡q«uÒ>à¤)²‘K*è¥Vki?ÉNû‰šâ¸>â:?½¿š»zh*](ôj±•—¸£7¾6÷p;WÈJ¾H¥áûëwúFyÛ>txíoæ¸T¸ê·O#kHâ›):*>¡]sàÇZ,Uí0ó‰áC¢âÊpÑÃÇ½U—l–€AFTx,î™Ò½O4\\g×Jo–[õõŒw„—V§“§cvkmÚÖZg£€«˜#üZÏZŞÒxp–ÄF…x$âc®+èÊøs˜h8“êçvtn`Oj9.ªôRÕÔª¶ìÏ@8ÅõN$d×~Yé{uYÈçw6JäI½ÛKrĞ\"R¾ÜQn\reGu˜hï&|yÛª†wi28øÀ•ª£7Á¬&ğ\'/8«¶v§d)³“Iæ%D7å²¦&«ÀË¢|@T°òø#ÏÔÓ]TÈÈÈêÚ«;Ú­Pñï?5ÄÒã‰<ôV°İ²^#àõª—­çV˜ümò•\n¶ÔÙ[ıìÇÂ|b›2€ÏÃ#âºÓ`:aÙöU,<ybuåÑ¹©¶´M¨eF~Ü6:4\\‰Á¾@¬©\"NH‘<±Uá>išWí½Cƒh	Ò\\O\\Ù×\"E&Çœ{Öl‘–ñêŒ±M,1„ÃàŸŠåŸf‡ûai:R„üÜ¸;lmZz³ø?úT¤c×¬V(±‚:úá?:yv_3k­:ú¿äú9rvÚÚbŸı·ÿ\0î©‹f@ùp\\ÜÜ“‡V]Y¤©Ã¡çŸeÓ¶ÖÓ†ñ?ğÜ0ã‹”›ÚÕşò“]Ä²X|fV^ :aõ\\éV12œyÊ~(5°y$æômÅ“N¨<šÔ¸WÛ–ÇB“ñ8\0\nÇ`ÊÑò\nÍ>Íe¸l[H´‹N÷^Ø/š‡B«I:ºïå8¯1´Sš¨­\ZƒSÜ“¡±J¼–çµ‚§ÚWîÙªˆc‚óJ§ÓıİW·€#À.Ñ´ßf®Úßnæô¸À3Y¼;ZÜÒ5ÓàÅn$ºo‡À º¬bH×»Éi½\Zÿ\0½?ø?ÒQI~Æşô2üıóÿ\0Œ¨CÚ<¾I$œ63–çzY®ç\"’J˜‘İ™·ø~i?2’Jå3•”Šyö$’TÉ@\ZsRŸŸwšI$Æ†\r~ô\\[í’LìÇŞ…\n¹vıIÛ>¿‡Ì©ÿ\0İ»˜òI%Ü¨lE²gÚ|×6ë÷¢I í{Z¾Ëy”’U‘’U,ÇŞ‹®íóI$ĞÕû¿¾¤Ú_»şE$”vYÄûÜşª%OÜZ?éÿ\0œ¤’§°Gr$’LgÿÙ','89001'),(2,'image/png','ÿØÿà\0JFIF\0\0\0\0\0\0ÿÛ\0„\0	( %!1!%)+...383,7(-.,\n\n\n\r\Z,%,,,,,,,,,,,,,,,,,,,,,,-,,,,,,,,,,,,,,,,,-,,,,,,,,,ÿÀ\0\0á\0á\"\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0B\0\0\0\0\0!1AQq\"a±Á2‘¡ğr‚Ñ#3BRb’¢²áÂñ$4s“£ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0\'\0\0\0\0\0\0\0\0!1A2Q\"±Ñaq¡ÿÚ\0\0\0?\0¾!	Vˆ	R%HÂT‰B`!@1­Åc‡ùcGœíÏ.\nm¶Á”qÒ)ø[};Ü}Ä±™ªyãÌ\\€{Èãÿ\0\nnG#¹â[N#qdÌ\'?…À›\rmôTZió–1$İsÚ¿Åa®Kœ°æœ4[¼ù)¹*E…ÛOXâõ24nì¿e—ş.ç|SHî~3š®ï¾K6Ôr-ÕE€ÖoØëŠC§$6{µÁ§[‡Ø\nÌBÜG€ù§Pbg™úk¡Ú*È-»/¼o);êÈıU³	ÛÈfÎ.<MËê|ì¹¬ã†V%:sšáÃÆãşL¬+Œ®İ¡ÀAB3¢ÍqÌ\ZŒö	t|cq%¾Úz.™³ÛA[/³Àñ“Ûg‡Ş®YYÜt—H•	¤ˆB„!*D\0‘*D¡	P„ B\0P[a\n:wJm¼{,iâJœ{¬.t®\ríi?RCğ¡»[È›öôK*r+˜– é¤sŞI$“Òü‚Õ\ZÔ\Z·Eª…‡‹\räæVĞGh÷şø,Ã\\tˆk¬²HôQ»¾afF£.y,…¾KdqfİÚıRÙèFAÓÎŞY\'”ïstºi%9ãµácòYDës#1àƒMÓÕ‡e§È_ÑmŠI!x–n¹§\"<ˆäyÂ;8eoÛöO)@±Ï‚%\Zum”ÚVV31¹+~6pêŞaO.%EXêyZö|@ø8r¿‘àºö‰6¢&ÈÃ‘×˜<ŠÚ]±Ë!*I•\"\0H•\"\0B€„©B !*¥í+ü5·M¤“°ÏÚ>ë‚Ï>?òWBöÏˆoTÅ92=ãÊî&ß@¹Ù*/5s¢ï-¬+Jİn‘·—d››§ñÒ›q[¢Ã”íZF6VæP¼è\n¶ax„5d¥ÁÀàß$‹ÇÇk™Šô¹©İÉÂş‹«œ%¤ZÊ.»cãp¾é¾¹)IW|V(´õlvDîõÍ§öNæ‹1•1ÇÇŠsˆl£™rÌÇ#u|y9·hÕº‘ÑT³Òn6v}Olyúî&ÜØ‹;´=á7¥”X˜9tî)ôƒ&›åÀúÜ©&õcğ*oa6€Á?ºö$vé¹ÑÇápî&Ã¯U\'hYÙoë÷ê¢§&ùêÜ²åËÃPª]TÙ·¡ 6+üU3Käe™!æàkÄXø•>µb%H€„ ˆ„!\0%H•#	R%@(XLıÖ—éi?!tvú³Ş×T;€vàèÌ¿u^[*%ß{œ©Åß3U¬¬ãBÆ8•%Ma™ğL`=ÊE¤x¯zTñIP‚ã§ª²ĞÒh ğŒí’·PG¢Ç,b¡¦²–†7¥j•§jÉ¶˜²˜-¢–ùY9‰Ë#U ·Hy°Ğx*î7²¬•¦Ã>\núbZe€\'ñÑ|¶óå},”’àwN£˜RtÓ4Xäá—¢èûU³í3–vÈÙr&5Ôòº\'Ş×Ë÷\nñË|{e\Zÿ\0I/{ÀêÓÒã’mV.7†¢Àôà|ÂÊ±Ú;Àşş¾+[p~G¡Êÿ\0;¯lVoeø¿»©÷Döf£ó¶åŸõºø^o£¨1È2s\\;œ×_Ì/Cau‚h£•ºHĞî—Ôx…¦7†yÎvt„!R„!\0!A*D©J%@\nl*ıÕKô´N©jaR½®Unaîmÿ\0™#á™>IeÑÎÜ$z,í`±jÙ ÑBÃ\r“¨N`&ou¬ŸÒ6ï	S‹>ÛY[pñUŠæ¢€.lİx&iÂ”§QÔêFDkOâNãM\"N˜µŒ²nbö,Ú‡*I…Dk•{KÁ-üV5]n` qÚ!,ni\Z‚³·Wmu¹§¦›}ƒ#ìüÓfÉl¸iàVÚªsîŒóËÌ&õfÎ¸ÑÃşÿ\0U¾5Ë”Ó:¡›]Ï^£\"ºç²zıúWFMÌO6ü²v‡×yqö›´9®Éë·*Ë	ÊXËP!Íò#Åi…åSqØ•\"ÑH•B€D©*T‰PåŞÜ*¿‡OøÜóşRšê+Œûišõ·“oôS‘âçqê×ÇÍk‡_˜Ô©\\iy¹ñR´C0T\\y¹NEšåU‚Á†»0­4•LÍ¿P©xt&Ck–·‰Ğ•m ÙèH±¬lİ8ÚŸ¥©iÑÀø…+•hlNW‰äwtØQÕS»â$r½üÑñÇò©•ü/½<Ê©‡â„äíTì3#z;6”iArh%LëjÜĞl.QòOÅ!+Ôtó7‹€êB¬ÎÉä=¹l84˜[©vh;7J{Ã~¹İ\Z”sïixcC„Ñ¸.\"G]·åŸî»>7²Q:€]{.xÙq–°´–d*ÇçÏ-p¿?¾*kdê=İ\\GKHß©·ª€`¶IÜr–¼9º‹Ôiäµê²ôô«]t©½»ÑµÃˆÁÂş©ÂÙ€B€D!BD©¥H„•Â}¯>õÖå~«º»Eçÿ\0jb3Xü!=CsS—¥b¬SêRŸêê–—B±—B¥^…#.U––-ëuUÜ<ö•Û	€j£68wGK»‹Cœ;İÀ\0Y½»€SôôÁÉÜ8H\ZŸrÆ^ytüxáÏjë*Ã›½4äo–“ğÜ\\´•Û\nÁå’šIÙS-Û#Äl™íX›ğÓğ¿»B¦é0¶‚èR~éœXÓúGì´ùM\'áwÚ‰EŠ8’-#xZÁàk—yğW<\Z³Ş4™×P3PÆ‚8€ù¦ø\rÙ!oä°öÚt¸’¬íkÛpĞM…ïl‡îU¬3².+†	5¾z€H½ùÛTïi•É6¶	c’Ò½Î»Xîë8çº8ÚÊSgğRñRúz©#÷[¦Ëd6tg0{#æ®QàŞÌ@á½aË50hc·f6ÉmRFYawÚ©³8ü²µĞÔ‚Ù\06~v^EsŒf=Ù?ÄWh¨ÃÚE·GÉs-°ÂLo$i“¾¹ıs/Ô¼±ı*{Î}VÛåÑj”iÖË1¡[úrûzedŞ¤€ó‰·ğÉK±û8?/Êœ[°½„!B€Å*D©\0•\"T?EæÍ°›~¶¡ÜåwÓ/Eé\n‡Ù®<?%åüB]ù^ïî{~®%N]ªtJ¿šÆ}<VtºøD•-ËÅOµzcBë9^pi²\n‡MñhÂf±\n3ká®…Bíí\"ªa3_è­T%s»æ¬I24¦%¶#’W*Ó=£+†J/ş`<Š_R3hÌñUí«.‘Ã“ŠŒ{Uâ:g²±yµÙ^H“è‚ÄÆ·²Äd²kQ­Œ¤‰T¶Ê€:\"m˜ò*íPÕŒÇ½‡0Tgƒ€T6×‘Í$z…e9í;ó?ıÅc\Zé8oo@lY?ƒ‚ÿ\0Øß%<¡¶E¶¦ŒrkGú\Zrù©¥Ğç½‘B@!\"„‰RJ ”NÖV{ª:‡ñl2[©iêW›\0íŞ}¨O»C ¾n\0x\\2Œv”^Õ:mƒâozÙRŞÏ¢ÕNgTú©™¾\njçH¸h)êWYWô=\n°Ò¶éf¯\ZÓ€Õ+µÙ.m†×Ù^0éM‚Ã\'oğ´C:i‹bAŒq\Z€SA:ÓQgVšWöoßŞß=»º÷ë—Ñm£›İNOšÊ|„İ—iæ		”{16øsd:ñ$ß¹T±WMÂ«˜YqÉo÷Í7ÌzªöFæŒüÔœ0†æ\0¹ÔñEÈ¾$–s¿ôO`«¸L+bŞi%…ÖÚìv­6J]pÓã2›Y%•CâÒYü¤ıƒ:‚ÚjÚyœM­³ê,<Ñ—(ê8¡Îÿ\0™ßSu›—U‹\Z¤hé÷ŸGäCŞ6m¶…£hÿ\0ój•L°¶ÙlÒ¢xºÔ!	\n„ˆ@`•b²H‰V\'ÑBö½=©x¾F—hú.3í‹©{c¨ìÀŞn‘Öè\ZÑæW/§Yû«F@vÛÔ)9YØ=G™QºHŞ¡MÂË³õz”ªñVêg=K{|”6 Ë9lÂê7]nh³p±ºÉx¥vfò!\\pèî>’]à×qVìuË›»ÇXã-‘‘—Æİëj/l¹ª¨ÚY¯aIÈZçRmeĞëÈÜ;ºÁÔ.qZÆûàá‘‘Ğ(ÁÓ‡çöö•ƒ«¹…½³67°çªOµ…£µ‡h“½–Æ˜É$2äÒÛ­À‹uº¸PI °---×#÷š©/%ñİy<¼VaÚ]ënA3¯¦FŞKkö‚P/øIrÈ«mMl1±›îk@s@Ğfª;M´mx’8¯Û6ßĞZÖ%¿TêüWû×XxíşZhvº)ëZğît¸˜\'RÃ½ u¬mcßÊë~Ía\r†Ù\0/–ƒ€	Şí‰+<ûáZ™]\ZèªŞĞj·iÄ`ç+Ú?K{Nò\n×!\\³j±1=Kˆ7dCq¼‰½ŞáÔØx#	ºÃÉxA2.|Oß’°l}¼«ŒZá£úsóPğ³x÷÷’º{8Œ{Ç“®íÇ@ëX}V3—&]:^ğ6úñëö¥¢‰¶½ù­ëw8H•\"\0B€Ö”,R„IBTƒû[ÎXÿ\0ÂÃş§_ÑPi›Ø\'›¬®Õå½M¹1 õÌª•;„Î÷8ü–MMå‘½Gš£×ê ê˜:…=ş7ª*±Bã‘Ùÿ\0?5Óe7´lÌóè¡#.Ö|¿@UÏ	¬±\\Ê™ö±V*GC}9âèñæéˆ¸UŒW\r»‰gpSŒ3\Z©¯w¼YkN¬r½Å^^ß‰¤íTµ\rTz<¸iß|ïÌ)ºzqÉIÃF[zÿ\0PÊc¬¦ĞÑÉNæÙ»Ïpøl×’O\rr%lÂ02]¿(ãÙg.D©æR NÄn³Ïë²¸Üqãçm²|6\n6wÛ$æY¬«[I62ãbëÖó<wšâé¶ÛCîYîã?Åx·åiş£è¨‡u¡+¥t²:G›¹Æş<<Z×Ù¹jì‡™+|qÔsg–Ûh…Ú]Ì:+–ÁÏi‹y˜şï5S¦hk[}\Z.zê`˜‰†VÉÂı±ÜNkIuQfã¹F2f´ÒÎ×´9¦à‹‚·-ÜÁ\"TˆBRP±J€É\n?Æ`¥nôò61ÀÚ=\Z3+c>ÖovÒCİï&óió)Z4®ûLuêİáşÑû¨(›ØŒ~có%a‹â2T<É+·íM€\0x-Ÿƒ¸7êoè²m\r*>?¾js1GxõP•ê§ª˜?JUXû1ÚdÓÌŸU_!Xñ¼Ø;ßšİOç9n€dBë-4ìNY\Zœ•ŠK\r¬,p<EÂjÃØræ4…aÁ+Ìg,ÛÄq+Ë£§D§*j‘V0Êæ¼\\9OT±á¶\\Ä›Ö§¾Ái5Cšk<ÅÚh–U&X•u´Ìğsİ©§yo¼”’ç;uåuĞ$‰Sv¹ÛÏcx6äªq¼§.•ˆaı¯ÈhmÌ­ÙÒrÓïè·º{¼5¿EÏyà™—v­Ï%Ğç©\n—v:ù-4Ò\\Û˜DîÏ!•­aİÉh{x„DØ-§ŸÃÌl	ì8/À÷}ñ]$çhên{FÎàxx«f¶•41Û²4h]rmËx-qÏòÏ<7Ìuä…@`[JÚ–Üdm˜ËÄ©¶¸•£4Ø……’ (Ø¿´ªX®\"ŞÃûAk?Îí|#ö“Y-Äe7“ßâ÷z\0ªbà§jk«¨t.{œ÷\\âK‰ZãrÏİ]#…²â¤Û\\r	ä.øJaÉ;c²\njñiœö‚°Nû³À}@Uú®\na»GåoĞ)Ë¥ãİe‰³ïPVSR·éã»ÿ\0\n!ÉâYP²êD@šàvs‹yæ=U‹ğ¹góYçuW„Ü6†’ãE¾\ZJg¤æ§!Ã‡\0²µ¼ˆJ\ZiŠÅEHóñö–‹¹JC”n¯f°ÒYm|iàbö!;DÔĞW4ÚJ‚^ëjnºV2ë0ô\\Çˆ¼H½ïn6OÅéb7»¼4wñ?_$Æ	;W[ñÙŒgê<îyüÓ&Õ®¶òqø‚46JÜ@èàxÈ¦Ä¬KJq6™Øío÷Ñ&ñoÂrîı“2Ãu•ÈOEjB—–\'oFç1ÜÚHWLÚlŒ!µLo÷°Yã«t>\\ù²\"ıÊñºFNÓÿ\0™4?ß\'ÿ\0Lˆ\\VèU´é‚VÆë\0¤Ù[ƒE»ÉÍ7”Õl9¬d°êTÕâ3’jŞktnEœÙ„úšNÃzéû&$dVÚI;6ğSgÆò}½Ù=Ùü”tƒ4ö¨æG˜Kd)&1¸8p?atL5Í™ÍĞğâW:İRx*êwç›NN½RÏÃñİ^]3¥²±ÒÀ£0g¶F‡4‚\n°@Ë.gUgIËX†µ©éw¹\Z·›ÌôQütåeÍ¶Šï€÷ÔrÑt<aù®y,O–¤ˆ2îÆ¥Ã2GK¼|ä¬æ±Ú¹ŒÌ÷IÛş¬C[“AËTĞ-ø­D’L÷ÌâùNû»Ã#’nó¢íôáß!å`\\yŸšÈ•ˆD³YY#\Z²N&°-ä²jT‰è¶T#x!mOø|4„h›T¨HÙpCx$B\r½$<R¡J\"Ô-s|g©òBNÚ^›`øP±ŸQĞ!	{W§Tö{ü®ğ¡méËVhBibäÚt¨Jª*ØŸÄ¹¼Î‹ÿ\0’O4!_Óığ¾£ìWj?˜ïÌ|ÖpH…Õ:p{+P„¡ÖÔ9V–.Hôˆ@\"„ÿÙ','89002'),(3,'image/png','ÿØÿà\0JFIF\0\0\0\0\0\0ÿÛ\0„\0	( \Z%!1!%)+...383,7(-.+\n\n\n\r\Z,$ &,,,,,,,,,,,,,,-,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,ÿÀ\0\0Ã\"\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0<\0\0\0\0\0\0!1\"AQaq2‘¡±#ÁÑBRrğ3b‚ñ$Âác¢ÿÄ\0\Z\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0+\0\0\0\0\0\0\0\0!1\"2AQBaq±#‘¡ÑÿÚ\0\0\0?\0õ\"’RŠâÎLICPº1Æ§!¦Âr\Z‘\\4ênt)!\\]B\0n#©ä}?eTcD`q¥XZnm¥*	@V³ ¾œw´îwE–íƒÜIdC0$¶™¥âÎ)Üvõ	ğN&µLW†D‡‘Î.!Ğ»‘Î|–~jdµÎPf$×@\0$Ğ«Ìc>Zœ¦†•©Öææá¤WŠÍuu][UÔâíÕñTÀ×$×m©ˆt¡\0\\¹Ä\\tôR(2“`Aw7)^ƒrLGµ#jÔé©>)‡C6­õqæâ§ÉTâ‰qu/rM¼Ğ!q­:W¯ÿ\0ğuî\0à.i¿•MS¬‘;õ)îHj\rø 	FéJƒ¾Õ	§I¿ÀÛÕZb7Ÿÿ\0JÌ\"\r=¾É©\\„©ÛÁÙXT;5¨Ğ|Vã±=´‰*rDÌøDÜ[Í¿»¬D\Z	74°­úëdÜ\'9·$ßq¿®ägÁZÌú†Ne±X×°Õ®’}y¿ú?gdIw³mƒƒMœ:CıËÒÑwEVv!\nDAB\0„ B\0„ \n¸º¸³–(j\nëP€\'á©%±:n\Zt)åBR(˜\r¹b»DÈŒ&†Œ;…ZÒhvv½Û?’Åö¡x{ª\ZÆózrÃçn•âª«Ámã!1H‡&®q\0Väz[ÙC™ììV°Ši[ræµ=’øÓq\"8\0ØM[ÁÏŞxš7^enqj›kQÜ¿nÛcÅb`­H7ÜE© S%»:]sş:/J‰(Ò4	‡Ë YåVeñP2ğ0{¢&Õ}QEˆåFöËÑAQâø^]ÍÁTbĞI½´ê4ÈÔäy¼Ô¶RmåeÈ|R_Uk‰Â7U‚ÆúhFêë©]™Ãlú}<`NÁ-u³evê±Ö>—êÕô8_0á/\",\'\rÏ¹ÜWØ/¦åß™­#BôS‡,ªkqB°¬„ B\0„ B\0 BrÃ…u¨+­Æ„ä0¬S\"I†¡6ÄàL@„!08BËvú\\™v|F×€<–¥CÆ`gƒ£\\¤¢áFjñd í$Ìçúw##TP’ÓN[TW³NT}˜«â4n£~õWsabo Ùoä#™ŒÚ§€åäĞ°WÌ1W=¥YÍGh¸€²³}ª€\r\ZKºO5R„¥Â4)¤²O-M¸WUY‰ì3ÌYN—Šãß§‚n\rrI;ä®Æ°–½„B8,Ô¦ğW«9•`qÉ\\±Hâµiª;í2jaur³†ç¿+\\ï!OÄxj¾ˆìäó_	­$gcZ×¶¢¶­9¯#ìô†ÓºÖú­wdI£ƒØO[|k%‘T´Ëá]ò²z*¶œàB€!\0B€!uYË¦.¦&!Æ„ä4€—\rHD¦\'m‰À¤ B\n\0SNN „€ÇàÒF%ìhn{8l¸Tz•/ÇàÂ$TÃÙZb2à¾÷µÅ¿ÚñGo%…íd(PÀÈ\"#â\Z\n÷zÄä²ÕVé6Ğëw~Ä‰nÖÃˆhÖ¿]NZz?øª‚AÑ`ñy)˜qa=m3f‚Öˆb ìŠÔêñªÔvN…sšØÔZŞK%Zvòm„“OƒDÔ\Zpâ©ä æql¼9Á¥ÅÑ	kE7\nIZLj.°QåğÜÆ»önTa4•™l£uŒÉlJiï!Œ`¾[…l7V£R<ü¨ˆh\"05Ü›OUeü#…ƒ©É¬¾)è2Á¢ŸñœÓáXP‹ŠË¸Û!‘ªÏv¶L‡AóZg\nh¡âò&+@iªT¥r8³3Mø,ÊH .ÖäS÷Åm{~#Øıík‡²¢‹†.\rò±¤—I:ôÑm;\r…˜PÜâ(	£ôƒwxıº]SEuä£Aš” !tN8!@„\0!@„B„!g,¦¤¥54&:\nr\Zl%CR)‰ÀšjpÄu\\B\0êâ˜Í¶­>ÈƒôU³§)vP/©#v´æ­Ê7‹=ut_§}VfffHÄï\ZÊÑ”xJ°ÃdšÆœ¢”iK­¹Na“Ağâƒî°E-Ö:S“ÚQN³h¦¡44Ü§§f	$ªy¬Q‘v!O\Z:•^ÛğhFŠVÒÔ)c*ak‡êùâ;Ô#ˆú„¬ÅdX=õNC‰o[ñMk[&¿TâB¢Á3—&ƒ6Hó\0z-ûAA °X¬3ñ¾$\Z{­°]=\"[[úœÍ[m¥ô:„!k2„\0!@„\0!@\\]YË\0¥0¤U)¨Ú¥1 ¶)%1-6ÔàL@”“D¤À„À,VÌ¨SR#ÂixP©Ñh9m’f:h„7Ï¢t4Pr$$âLsC²ªŸ¡Y³ˆÍC‡˜@\Z8;i·×)\\˜¬ıNÏrÀôübEkâ£¶Q°†Ñ}Íü•Ü´³#1¯tİ**ZB\rÅ,—•…G^+Á7q4¨ÖÊvcß2ßØ¦9QòÃbjúª³dH®sÜ2³6VŠ÷¨Hq<•äóİ†YÆa•£¾×PÑÕĞ\nnÕ<hĞ\0\0Sè“vV\'gËÀ‰–€\'%ŸU<Z”©yŒ¢¥E\"2EäÙ^Â78eè-+Ë LfpZÎc‡7ÁŠy1Ç_ÒV½5U4ü˜uT[I¯±€®®‰Î!\0B€!\0B€3ÈWMœŞj¬Ê¤ §œ4n%$á§qFÖD@S°Òÿ\0ñîä”%œ7\"Âjyª8¶©ö•$!H\\B`up¡\0B¸¼µFp7mª¡•9IàOì-T˜”«s(MÅt<BÅ¨£èİ§­Œ¥Äòw²´Ğo=\ZmÇºĞ/­\0Zù‰ftá¹A-	º\0²¶×\'F\\` ”k·êS‘Å*¦LÎ1šRª†z~º›c”®&,UE©ä8³é¢z\0Ví°Ñq$å&;öª|»¨¤±Õº¢Dìl°ÓÑ F© ³€©ñK!>ÈÍÍ	ÁÍâ¸Ü¼¦,Ğ`¹¾à™ÂñÈ_Vÿ\0ºñZ©j¦±,£4½5TMÃ£Ù¨ğnÑÃŒ\0q\r\rÇ¡ú+°º0œf¯qêS9m’±Ô!\ndB\0„ UIª«±ªêJê–à:¸P„î\"³ŠC“²ÆÁEÅ·à¥Ê÷Bª<“|•ÅÕÅa«ˆIˆğ&ÀjR\0‹4T¬Ä×lY›$—:º›ÍFÆ»A±C,Ö´ß‰Ñb%cä„çïvË~¤,5u\r»@îhı55ºªÏ± ÄûC+ò5ä4kKTı’ÈĞ“FŒÏ$×ÕPÉ’µ)œVrŸ)¦·«ÏÃ Ylç#¯\ZŠÛcM†c/˜1\ZÚU£3/J¶´#¨¨QgcE¯qã¾ªfÙğİñ\rƒA\'¡µ?|®!¨ê*ä®bÔRTçÒ°b&#¸î*Á:«ÉØ5u(&PQX¦‘^Â­ÔØLNü\Z$Dxn¨r¹%µ\",ğ`âTÓÛ›n{Ôn¾Il÷4B‹|˜¤š¸ß÷dóJj=çü\'Q‘±FÊÃĞ#9¤šåĞò[\\µNhªæ‹“´ÓÈïÀ¤0Ã_qÀ¨©¸»Å”×ÓÂ¬m${„û#747WˆŞ:…-y>ˆ‘GÃqkOb¶øi‹F¿qÜï±]\Z:µ,Kóº¯O.¨e¨Ğ¡pÕ°ç„\0ŒË¡6\nPT\\•….®‡ƒ¹Jâøà\Z\'T<â·Ráè’y$Ö\nœTm…*Oº£bıæ©Ge(÷\rğH)%(®QL€Ù+#Úìp|2Ö]–¼i¯ª¹Æ±\r‘;Ma4ö^a‹L\n_ú›˜õq©Xõè_“±éšU)|I/°ìh¤K¼ïqkGZªÜAô-†4`©ŞU›¬ÆWF’óı 2<„?ˆòçwE\\ãËzËO‹ƒÉ1ñ~<ßÖo!½Ê6+š¯vƒÔ¦f\"ñ@ƒG¹]Ê07ºÍO¼¢]1ú±Ü\'’7º¾EdÉ×|6¹¼\0#]7¨¸Ô½E¿@Uø\\İaUÚñ!µN$È¸êZ=SOÅšu73RÕ@ŒÃû	Æ)‘ø0ö\'@ Dy:•)’dÜšehîŠ•zq\\4’àfùR A\Zù•ØpÉ>ÉÙ—eÙ=Ur•İ‹-a§½.IM±µÕ?&AnñôIû!>0e…ÊD&9×)0 ~\'ùqRCµ°à ì„u®ÈjÓ_Ìñ÷V‹Pw¨L†7j‚Üµ®‡wö*$$·fûG¤8ÆÖ\rqÔp<À/ c¨½²øÆ‚;LÙwĞ®’»}üÔ´ŠÉÉt„!o9pR‚l%…˜°p$¸p+¡%àx©#9æ·*\\-G<ƒqU*	²$Ÿf0.Ôì‡u7ŒîKÃÍ’]ÀûIEqÎ]*\'+`¥)mW\"ç%`ç¦œé©˜n=àrô¢ÉO»åËaê×´8Ó¨öÆn°ÎWóaµ}U$Û=£Åg0{Ô\\¸;Êç°§±VöÄcå€\0ÕÄù øpšÁŞ}îCğ·ê”Yñ§L¹ÓR“—¼~AJ8V.Ybğøyè›û­êu>JÛ\0eªwÿ\0’«1•ÙË1ñÓÒŠÏ4cåaó6UÍİ\\Rí&@wÄl_<Ö^+HuBĞ`OÚpâÒ«ãJ’ãA½(;0C’ÎÎ(Tˆ’Í†3?]Ãîœ€TĞ•IˆÎIGsÛg&fKÍœzÓrğ¨§KC©©Ğ\\ôR“¶!\'a¹§»÷Uõ©©RcÄ1Ë@8&)W„ã€‡¦c Ó™à›€Ò÷f(œ}Hctêw•.Ú·è7”<!T®EŠÖwøÄi£f°m;FJD(·qÌî;‡ «Q÷c¢eç¸Ü£‰×î”Ox×–‹™Ñ?°‡¡Tië÷ZnÆâ‘ÃP\"lñü7:ñYAY`qÀÏoº•6ã4ÌÚšjtåìzú…Ü<yàM5,,Åƒ&!×BäG&/$g¸ƒk…*Š+ÉfáH—(\\ğ@Æô¸v…Şƒªæ¡GÌ)5e±¬C3ˆ\Z5Ù~ëP÷P¼¾<Áø‘Zê×1wEŸY6’Hèú]99?y¨Ùb–›´Øô*’nS—…r êÒ¬ñqP5Ş«æ\"3¢ÅÒ%ÉVl—ÿ\0ëc‰%ÃĞ&¥Ûê¤Á?í…u.w¢NÌÑ\Z9‚¤ü‚x¸Î2ï›AºƒÈ+\'|—óİTbgæ“ÍYI»å¸rD»PxÀİó)ÈDìÔJE}#7™JÅ\"QÇÅA¬‰÷ç¦kdÌœ*š”É¹VR°èÏ¦6\ZSDôûò´0jnï²zU€UÇFİA„HJ¥bË~2ã©°P¥]•®ˆyµ½N§À\'1Éš¼1º7d(óÂì„ßÂ\0<Üuõ*ØGóúÎËQ Ä~Ÿ„~cöNÊ‡<çuÉĞõ\0(ññ\"6;¬·—yÇÆ©ùÉ°>\\-‰ãÿ\0JMoô%+‹1C*wó·¸ı“°Û¼¦¥¥òİÚ§ÉUJŞ	#…ÉÈ¥zqM½ügOÊÅ£šFâ‘PÁNÂuÇ‚v!.w„ê€x€}“/İoAì…ÛGŠ|‘šRÁM4§ÌX8\nåÆ¥8”ÄCŠH;*L±Q£:‡füT‰b’äLsº0Ãªsîªñ5ğ¡¹Ü4ê“•ÉB.VŠ;bƒ(<İÈpX<e¤ÌÀI}(¦«[†ÅdFÄ.¡}jj7r\n†}Æhq¡‚r\Z¹ššo#X*ÍÍİù;º=´o²¿Òª~R$6‘¤û¿.¢ôÙ¶Æ`-¡k‡îËŒÈD6§\"•;^ÆÚZË«‘È±)ã™Şe;Ùïæ×ÙB‰v3“B›€š=],Ñò1ùê¦áî±è âòŸÃŞœ—B$+wÎ`ş¯ªF\'¯=J$¿ŞDŸ Ti‡UÅ4ºˆ±R¬©Vínà«äZ­!¶áWQäaˆ?,0ıT\\9ù\Z÷şVšu6˜ÄZ8&^ïöñ:·êˆ¬\">x[3DÌí´|p£m¾!ü-\'ÄØ{¥ÈL\ríŞA>\n4Õ¤öƒĞ\\…¢Ùwú¹tİÁÃ…ıqvÃİoªLœ¶A™Ú”‰v½Ñf7ÔîhS\\?C”néÈ*ç/ßü%N;QÁ{”ÜWŞ4ıÄiÒmSôßğ´»ÊJ>äïì.$Ùuš(ªàˆ4h]/R·°…‚…¨QÁOÀ¹Qh‹=Ş[¸ßÒßd\"W¸ÏÒß`…ØG‹—$F¥„!g,j\"¸¡ö6R%Ğ„yà‹÷$ÓjĞ…ÍÈBª·k/Ów¢<ì‘!–[k-¸WD·C&—B\ZÌé.Ø™è#,ÑclÒÌÄnÍ]y\'qFÔtÑu	>QjäÊJ—ÒêN\Zv¼P…eO\'NŸiï”œ=Oä&¹‘şiıöQbjP„×\"òO“Ü¬¡›®¡Q>A•8™Úñ+şCú·ê„+WEL¶§¡ö\\i°ıaZ_&uÁ –†*E,ØaÍ	¥O5W;¹Æ¤›¡5>ãC\'KC•ôUÏq.º¥XKæ‹¤qB-ªT°¸ğ\\BŒˆ3İ¥{Œı-öB]pxÉrÿÙ','I89003'),(4,'image/png','ÿØÿà\0JFIF\0\0\0\0\0\0ÿÛ\0„\0	( \Z%!1!%)+...383,7(-.+\n\n\n\r\Z\Z,$,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,-,,,,,,,,,ÿÀ\0\0á\0á\"\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0@\0\0\0\0\0\0!1AQaq\"‘¡2ÁğBR±Ñ#3brs’²á45C‚ÂÒñÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0\"\0\0\0\0\0\0\0\0!1AQa2ÿÚ\0\0\0?\0ìÈBÂ!*\"„„ 4ş²ZPlÕ¸Äİ»iÓ…³>h\'ÓV_Ó/ssHs 0ßW³íZ¥2æ÷f±Úqi.-1Ù5ÑºJ«İëMÅgU¨X^ı§¹„´ä\0`pƒ³\rn•?qoÍwÊÚiŒ¨æ=ÍğìœñÙq\0yÎÖ‚J\ZÃ@—Tk\\ÒA‘„ÀÄˆ\'Ìe!y±·u*Kû×œ%ÏsœrÊI2`¶\r&æSø‹„“÷Ğç–<”~Óøz†Şá¯hs-9‘è¶‚¸ˆíêƒd=­ÙkZñ\0á„É_õ_´kzĞÛ‚ÚÁs‡vD~31­5*.jü…¢ÎíµX×°‡5à9®‚EoVT!@!@¨BBB„%H‰*TˆP‚¤J„„¨R–+\nõšÆ—=Á­Kœ@Íl%sØu¨S¥öV5•;Ö½¨;#)ÄAâ=bÒ+İ¡vV­NîÎ£™DÈÛ\0TŒAøƒfxNœÔ¹.Åä“€Ïw©ÃN`|“Jµ	\\8`±·­¤J½Ä5ÛŒ¿ 	½Zçìî¾B>ÃÕµÁk›ÿ\0öœÛÛCK7ÇïiŠ:2´ \rÉ3ÍÃëŠÔ×T$ˆm6ì´n™&|„û\'¶ÖnÙ€1Ÿ”hôRöš°÷ Æ$å¿õQúLÍ@QºsY KÉÚÇøÏ‡Ğ¾å2\0t|3·ÎHğ-êÚİGqp1òÉGi}F¨Æí0\\Ukÿ\0×R\Z»®µí\ZÇw”\'¹\0‡ÆƒÌÊízJÓº¢Ú´ˆ-8q´¯2Ô±|	%±æÑÇŒ«ªë5K*’ÒK²*4È$H9ôZçlu‡¡Ğ£´–§uAµi;hC†aÃqÄ5$µdHBT)„¨@ˆJ‘„ •\"¡•\nHI$…\"ÂTt½ãhĞ©UÆ\Z\\|²fòÖ™Ò«UÕKœâI$å&`p®ÑÛ~°ŠbÙ¦*W ‘Â“L“ÊHUÂ,è—íÄÏ\\V{«â6[[:¡RÖºS½D…d°¦°Ö¸éÆzŒ°Õ]àùŠÇVÛ2ğ¿ÉI[”¥!eúµ¼Ä†Öš\"“rhôR¢›F\09-véËB&Æ\r­n³kÃMÕKKêËv›-;ás]9júÈ8‰Ã#=ŞK¸T¦©\Zõ£Aq–gUlŞzSÉÎv;¥ËkT¡ÿ\0MìïÄ6£`8ƒÌı¡uÆ¸ Èä¼ë«W¡sJà\\àPéÍ¸ˆÇt…è{7M6-îİÁuâúpî{l„BÉ®£,ˆI	aBBĞ„\"B„„eíJùÕ´bÿ\0úqH	€qÏÄqU›Wæ8áî§;@¸ÛÒ7.wÏF ÆG¢€´o‹ªÇ_[gâÕ¢2Y,­èæ%X-*,6éñÕ†Ô©{s‚ƒ³t©«@±‹ğæ–	Ë–±l˜WRœ5Évå7¤ÂS†¶«xÁåDiºí ©‡¦•Û ¨ONÚ€6N\0dÇ5é\rZ¸ï,èTKèÓq#\"K¯>kõŸu\\¾ÓÊH+´v[wŞhÊBd²XyA0=WŠõÇåœ[„-˜„!	PƒD,ƒBÉ2B!	R E®çà8ìáŸkjGdg-è<§j9×5KÑsÜI™íA;–í@Smb§³uY£&Õ¨@ó\nSB²\Zo˜–¥€Omê‰09¨ª• ¦…£êˆ…¥âİm¦é°âTŞÖ*\'ª¥3AÂ³g\'È¬~Àö5õ\0ô•NEæ´ë6×ÌpÍjf¹ö…Ó-k¶ ƒÍ^ôU@ñÕ?ÂÓû4¯¦v0j·¤µª¨tG@Ş™²e2OÕRïL2Ÿ”¤6NÑlå™QÒÏ]I[i»‡Ã€«ı£dû)«m\'RZ+3goá?û\rÊ6ÏXjmwx\nlªKv%§ÂéòÄ…+a~Û†Î\0ùÁ¿E7×ÕsïÜV;K±§N¬NÃàóÿ\0j{±+ÁİÕa0vÁní¡z„šÛm·gU¹À‘ÔV½F·é[l“µR³óĞà=•ñ¾)¿êÿ\0§YBT.·–	aˆBB„ „ 0íªùìû3\Zâı²æ‚D‘\0æWO\\«·KWiP|!îaşcÀú*oãO—rWÒ…\'0LÉÇİJè—x<Ö\ZTK$cˆã‚ÇC~ïÍa[ó•)Ü’$,×º\ZÉ~¸\'ÖOÂ\n±híÇ	#õõTºãLçªÃtË˜C±kÚòšà–¹¹AV»ÍÃlÖ±›Ä¹ù4É\'dSÈ´x):V‘“²o¤)ÃpÏ‰*¿º¼ñIz¢ı”²¤$otWAÕ{ˆ`’©7-‡ªwV®ñ	¯‹øäí‹…ÛÄ9PÕ4cgd–É01ç‡ÉK[>L\'»*ÄêIé£ô=\ZmÙ;°\r-nOÊ‘e“Z$èœµ¼%eS}ıSœøˆÒBXáÅ§òMµ\"‰u\n¢·æìº\'ºI’oDš´ÓA”XF0×‘œCˆ÷L—üNŠ„!z0!@!@!@!@%BBT F¶hÆ\\YÖ¦ñ0Âöñ`.iê]#„ˆ8ƒBK.ÛªŞS»\ZfĞ9N\nÅ®Ú´ÛKÇÒnX=‡~Ã¦”ä¢gıõŞ¹ùÊìÖ¥’ÆëgÁV]]U\ZTÆ†¯â…§¦={^­ÊÑyO\\Qbü-e¸\"‘ks;ù,ämoKº¡õ³ğ‰ŠKTÃvœ	ÅVm¶©È ™“#<Rh6×	Ú.DAäµ³Ó,ï—®¾àÈnÉÇzÉ÷f˜ğäyJ¬ZÚ:­6ê4Çİ%°y‘˜ä¬¶tf™cÎÜˆ$ïÃr§+K¨’¶ª×	ÒÜ\n­B»íj÷n$±Çön?ây…b¥²Wûn~jGESô·’[ì?Ò‹¨sV]Z´\'f£„´Îó»¢ŸíSË®J°¤J…ÚóÈ„¨@ˆJ„„¨R* J„„©BT{AÕ/¶Ó¥½1\r“í™,\'qÌƒú®1y¢êÑ%µi=„vš@õÈ¯J¨½i°ïì«ÒÌº“¶£ià*Üõ¦wdãÎ‰Î|<&Î[¬Åa[æ¯ÖO†mp¢tÄœs\'è)Ë:åıŸc*¬4jÓ¨ç5…à~Ñe–ú·ø=¥g·»‚šÑÚ5,¤ê¨Ú/IÔ¨`»ºñ4C„`ãÊ½èí×—\\åğ––ğÏŠyà­Q9ü¤mh†ˆ&1Şµãq-GFZÓÛªfÔÔÄá9”™ÑC»Î¥M²8¸–É8’-Ê:™Ëó©­!M•Xiœï„Î!Ã\'yK¡ª—RY	êk¡ŞÊm©Uî}Fã8\08ˆŞ¤¬`7×İVû>át;fÅ6\rhôh\\õŞ\'4o.Ô®ŒßÁ>¹ä_	PºäBT D!!!BBB\"T óÎºhß³ßW¦0ûMşW€ñşQä¡èœUÇµ–Æ‘w:T±%I\nÃSÛ|ütMU»ğÇLÍ.’oAŞìÇº˜¹¯·Ô,lo5ü¢\rVíA\00µ a‰Ùô	ƒì¶·¤æÎÌ‘(×>M\'­ßOvÏ”\'ÔZ	Ày¨«K(ÜJ– Kpˆü”£Z§5¾9B`ÆÀ„ënsL«Ô€£Œú}«öıíÓ<nòø}á_&ªhîêÓ‡¤8òoİ?56º|yärù5úĞB´f„ %BBTˆ!2J„¡*	¡!,&ºRı–ô*Vªa”˜ç¸òh˜Î^h9l”âı§ñPg³œ\nçûjÉ­ÚY÷¥—/´Ñ\r1§·õ<U]æ:,5õ¾~Û×-RVÚLŒ%@²¤f·lÎJ9Ó¶.–Wr0ÍKhó\'ëÍQ4}éaƒ’²hı\"ïuK–¹ÒŞÜ\"2[v”#4»HÌzËUm8&.;Èù”ân’÷—£8á¹0¶qªöÛ@u$€˜»j©—zuÍMh: V¤?¾Æ~H«¥”‰PºÜ„BXB\"TBBTˆ!‰Pƒ$!€B!€B€\\K¶Mxv¬mÌ±®Šïyí?»iàÇ˜ÊßÚ¦º‹:…´ÔnuLà^x$]ËÎµ]*¶¦E÷BT-©ƒÀR0 ú&šOA9 ½&Çßo^#šeª7yÒ;ño>#æ®öˆœ¿ñÈ„üÍE¦¬sÎïÑeMÄt]ïV)ÕLğ8ã-L“ø›÷O0«÷Ú¿V‰ñ³Ã¹Ã„|ÖZÍËYf‘TªšpËq¸ŸRœÒÑrŸQÑ]T~âÖÚˆO™S6dËM=ZTÕ0n–™;£FÆ£öH Ç‰±êÕ¦*ìµ±™¨Æ´qtÈÊ³êüôëvÏÚ`<BÚ£ôE@İö5¥Ã|é!ŞŠAv8B„„„¨@‰I	Âd„¨P•Bë.´[Ø³j»üDxi·\ZèŞÌõ\Z	q\0$“\0™$ä/×^ÕÙJiXìÔvF¹Æ˜ş˜ûç]U\'\\uêâü–É¥Cu&œÇ\Z‡ïeTu)ÃˆQÔñİÃë=Õ*¹Ï{Œ¹Î2ây’˜V¥õš¤$O)OêUjÆ6O-p ÁDg+§êõğ¯L;áxÁÜ\'ø‡5Í\r(2ÎÒN¡P8e“›ÅªsxŠëM-8xgwÜ*n€iÀ™Ì,=FJEÜ‡µ¥†C€!®ÈƒøJ›¶;›‡;äVÊ]êµ7cN);t	¤|¾ï’Š¸ÑU){cƒ†-=\n¸Ú»pÀşeä‚ lv ôYkÃš×meA4y,i²7B¶ŞhF™4¼\'{ÉATµ ÁBçÖ.~ºq¼ëá½*\'zy¢ôX«]µ&\\8\Z±ûA>dpXÓ¢ç<SgÄìIü,Ëšµ[Û6›Z 4Oé<ÉÅiáÇoTóoó9ı¹oi·/mí71Îc»Ac‹Hı£÷„ÛCv•yBÜÚíªüQüãYYv±\"ö#B\'‰#ÜzªUQ@µ××4v}Úµ³àW¦ú\'ˆı£=@Ù\\´f™·¸Bµ:œC\\	[˜^fërÙF«˜CšKHÈƒt!GN=G\\\'Bv‰yB+³ğÕ`pxÇÖUóDv£iV`úâá´Ïîn#Ì\'¤-W´ë7n“ÛQ¿‰Ëz”!BÓ=)¥(ÛS5+Ôm6î9òhÍÇ\\çX»\\cv™gH¼åŞÔÁ£›Y›¼È\\·KéZ·5\rJõQÓ>#€sM‚„ñÑõ§µ¢Ae‹ o­PcÀÍİO¢æWWªòúsŞL¹Î2]Ô­#ò÷fíù*õ 7Ûò)wğü–p”7ôA¦›sı?2³-X¹Ğøâ9n?ín„\rœÎècS‡©I³õ(,Ú¦E7Š51cÈÙ\'î<áàJéTÄãƒÂá”İŠëº¦…Õ\ršŸ½¤\0sñä×È#šÓøVÅ†û‡ƒ‚’·¸‘áva3u\rç¹ÍÌuGvsø‡âWU,óäppèT.µ]SÛV©p\rkİ˜Ÿ\'§º~ÚÀ0¹ä´]‘h’¹•ş·\n·¤>IÉƒ/=şk?%ä_Sv:ÒmŒ]Z¾qUX{Á‰ºv²½§^˜©Ií{fZg¸ğè©vºEµ#-ûÓ[jÍ³«ßSı7¬ÆşíÍ9»g&¸g#šÏNz«îwÛÕ¬ûvVkdÓ¨C\ZøãÌÌó¿ßZ2½\'1ÂZñ×<AË‡é›opúNû®Ã˜ÜB×QœGÿ\0ñ\0¥#1õõ’ËgëªÍf)AI>·¥HqaVƒöè½ÔİÅ¤^#ªè:¿Ú­FÃnØ*xÏú¹¹;Ê6pI(=¢5ÎÊåÁ´«\r³“Ç›@z)õå¢º·eÚèêm¥Ã¶‰¹¨ã.ğ‰4ÜNxAòR:z¡òw]ØÕ/ËÑdG¾	úôUX­Øy-±oÉl@›)a(B\rui‡`@#šnëw·÷oÃğ¿ıÙ„ğ­Tj€íÇåÅ4+9ÃÄÒÒ0ÜZz-¤,Š!šIêæ˜}­vÕfcÄÃ›~¹&IÃåú ô5…ë*±¯a€ğOİ üÓ‚1ÇÂx„®cÙ¾!ÆÙÆC¥ÔÁã›š‘>Etºda‡Ÿ’Ú^Å*·Ú#«›aN‹pyı«Ús`É s1=9®FğF9ãğ<Éz½½›$H áÅ§1ÕPµÇSöæ­V\0FO-/à©ä¿E3WôÉ¦ı—²ïebÒWIÂA5D¥_¼vñ³TÃñsJÓiZµ\'í±í~,–·,\'V:Î—KÇg:`Õ¶îß=åiŸ¼È–;Ó¡Cv¹¢àS¸hşyü?5#«Ö€\n5©Iu8·­Æ¥\'\0iÔ<Á#ÑÊÅ­:;íuif]L–;DpµûpzƒzVŸ¯ÎV,syğHÜ¾º¬Vfxıq0°?]qÃëxX;ëÏÕK†”§#VÛ·R¬×³0¶£O6Ÿô›½cLøÇBƒºú]¿?oÕ\"â»SÔpÕÉFî§ä„*%˜Í+~hB‘™KÇ¢‰bw¦z?ïÿ\0Uß$!zì‘¸¡~‹Ml„š¯ÿ\0Cú­üÂî–ùù!\nøø­o£“zŸše¤ówôé›Ğ…¤ú‡Ÿ¯æÏş»Ét])ÿ\0/oıß$!rëÿ\0U·ñŸ~íÿ\0ËKÿ\05q©êR!tOŒ|½ÿ\0ˆ­ıWÿ\0‘M™ëú¡;õfÖïòZÎï­åP«?ÕR4½j§ûÁĞü„¡Ú„ÿÙ','89004'),(5,'image/png','ÿØÿà\0JFIF\0\0\0\0\0\0ÿÛ\0„\0	( \Z%!1!%)+...483,7(-.+\n\n\n\r\Z,$ $,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,4,,,ÿÀ\0\0Ş\0ã\"\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0F\0\0\0\0\0\0!1AQaqÑ\"2R‘¡±Tr“Áğ3B¢á#b’ÒñCD‚ƒ²Â$SsÿÄ\0\Z\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0\"\0\0\0\0\0\0\0\0!1A\"QğÿÚ\0\0\0?\0÷\0R¦R<Ñ¸\' „ „ „ „ „ „ „ „ „ „ „ „ „ „ ¥f·35¼í\ZÁIôæu¼ÁaÙú#r•f÷V¯F-§3­àîús:Şà²êz1l}9opIôæu¼Ác §ºŒ[N§Öğw£-Q§Ï‰0kÉ=€,º«n¡¥e¾–qÎ3.€5´ctÅûÎÀ§İON.•Ù~Îç˜ÿ\0çPù5%.PÙİp©7OB¦Œ\nÅ\rha>D.{(şíÁàÜL¸D@.¤x‰ÜÚN.ìò†Ï1é/Õ™SúT­Ë4Hp9®à¸ª\ZĞL‰5Ñ1t|ÁU¨ÚË&C€üC7¥Ãã½O¶£Õ‹¿nW¤pqÕĞô¥V—_ò»‚òü­Ê¶ÙY\0ÍRIÍ1\r¿9Ñ¿Ê[yyi¨ÒÙh1°îù»±^\\ê—cßS¥Öü®à—ë*}oÊîæë/*mº5/›ÎxüÀ®†ÁûC¬ß¼k*m±ßà¦ùÅdÅíÿ\0YÒë~WpKõ•>·åwÀdnSRµÜËŸÔ$ïÙ+r“ÉÄ¾>äÊ:N<k£úÎ—[ò»‚O¬éu¿+¸,§¶§Õ‹ úÎ—[ò»‚>´¥Öü®à¹äÔöÓÕÖ”ºß•Ü}iK­ù]Ás‰\n{j=QØ)S)tFáä»¸!!9»?DnR¨¬ı¹H¼İ½-	›4•\"lÒ]ídºDüíU7-Ä˜$ˆ;û”öÑÌ\'PUé—´Hhp€öhòVÚ4=(œ#|Øx¨-†.iğÂòViÛy®æ“ø_t÷â£¯e-ŒÀÒÂbïá?%2¢±h[\rœ»š’Ù½ºHn‚ÌHÕ1»å+MwfÙco—˜Î;\0W¹q\\¹Â…1‰~Öèi iùÅsí²6‚`é¾|1ş¸åŒñ@ş#;ñM†«>JíPÑy\'´Àï7*DÉæß¸ïÒº¹ÜU*Ğ\rÖ¡-8¶ıšUºàŒUgÈ¼wkVòSE³Z‹H-%¤^4v½g‘\\¬m­¢Sš.:*¤±Û®<…ğëâ7+kC©9®i‡4‡5ÃA‚<.Q3(œnŸB…gòw)‹]•Dàs€ĞàaÃwè´\\²´ÃR%HSa¥!JSID:ú]¸y\'¦RèÃÉ=lcBBsV~ˆÜ¤PÙú#r•y[zš9	¨M§EBDªvƒ_µRm2s#ªì?ÒáxñW”8“f•Ÿii‘ØŞü;Õ:ôhCZàu5î`Ÿô8BÖÏ×rCìùÁZUly%ZÏ¨ò^f£€>‘æCNh¾oÂ{UGX™8æw=²ÚÚ@6\0%×âqP‹s\\%¤¶|fş˜,læ	Ü™QŠÃj„Ê•Ù¤Ú£k)U£8‰óT+36à¶CÚî‰•Z×C;;VÍ¹ëh&¸ù&Q­;‘ñ\n[}LÛœF€MßÜjV“§/•ê¿²| {@› Ugü_ÿ\0Oèd¯%ı”?ÿ\0(‰ÿ\0	äw¶â½aË6ZxşSe!M•UôRSIAM%J4ìétFáä™K¢7$õµ€!@!AÌÙú#r‘GgèÊX^KÕ\"¤@!	aR„‰´ŒÕæÜ©Ë~–«áÙôiÜÀÓ,q\0g:z$çHÇ\0#Jô¨^[Ëk\'£­\\D4ÅF€\"C€3wñ‡Ål>¹r|yÅ¾¿8ÀíÁGc®s€\ZLÎ(¶^qW2MuGµ×†ƒµ¹–&ÊµİJ™\"f]£§rÆúIÒOq]*¨£äqò\\ÁO†Kô2‹š.Çˆ½uY.Ğ+3;\ZÉ†ÍœWOÉ†8çf¸ŒÜà[$Üp3v	{CÊ:!bØš\\y·Æ;7®—*Ù=+³~J¯e³¶›½pnS2ÔLÃË&÷ìÚ™Ö‘¡$lç+×¼«ömœmwD2‹CÎ×8¹­îò^¨ç,ü—·lq×F¹0§›+İSJziVÚ4ìèôFáä™G¢7$õ½çP„!\0„!5gèÊE\rŸ¢7)%x×\'«£›)%<¤I)´è¥!I(”ÚD¬nVä–ZlïÎæ1îc¼@’0¼y-”•Yœ×7[Hï§ªŒ¦ãÀ‘XÃ5*gé€3GiŸ%½as)ÓIÂ\nÍ´Òçæœ3Ù¡>Ğæh7ÄH•èKŒú“¤Ö—5Ó`¹›^K/çRÇÌ;[ÁjS¦É’gx*w9§¢DŒ!7U²9›6NªLz7´@ï+±É6/BÌÜI¼ìØ«Ó{´­\Z]2+½°òOÏÍé¶\\œêÕ)†Œç¹Æ´İ¡VËÕ½sñ9Í\0LI&ğ{%YÈü¯4«Š…-xhî°Ä<a[WVÅ|ä¯Gä¶AmœŞâ\\÷éqÑ¸jaÅU°[ÙYÔİ-\"ïí¡N\\±ÛwÛF0²’Se!(±ä¦’›)	R;Š=¸y\'¦QèÃÉ=z1æP„!\0„!-g<Ñ¹I*9æÊI^ÉìhéBl¢Ty\Z:R&Ê%O‘¢”‰%!*Ò¦C¥(*9Fr¦Ç˜òÇ%úS®æÕšÕyçÃ=„,U6<aÖùE’Åª”›9ÔÎİ-ÜEİÚ—•×Hp¼8‚•¿‡“qŸ9ªÍe&ƒFòKd«”¬ÍhĞ	Ó¡™¨«SRëms´¹éYZGÕ…7Š±[T¹]_9´Û­ÅßÊ#şË.\\Ñ°€¬r™ÿ\0¼`ÔÏ3ú,¶Ô›µ`´áñŸ.ë¯äÇ)Ÿet4ÉÀÆÖ“ßq^¥’²í:í8Gú‡á+ÀéÔ¾0:ÖK¶½„n3ÑvÃ¨®\\œ^]ºqòYÓİiT°Ÿ+ä×(KÏ£ª3¦è1wa»¿rë3–L¦šåÜ8”„¦’šJ„»ú=îIé”:-Ü<“×¤òèB€Bƒ‘³hÜ¥•^æÊI_3s{zH‘2Q*¾fI)¹É•æIÑÒ”ÒRJë*tYIœ›)„©N“.k”Ü•e º«™R%Â%¯$h;Vşr{L®˜eeÜsÏñK[\r;ùUCP­¬«JáÕ%½ÆQbôXª¸a%\\¦È	iSK\\ÀH­ryr®uc°æ~*…<TùC¦í¥A:~~oZ§Æ{õ,ó»¼ê\'hw<Ì÷¯Q4l)R‡c“«M#yôôs€Î¦0Î8˜»¶5¯GÈÖïOFN³;È¿ÆWdnezof4îqÍ3¼ì^—É\n„Q-À6¥@¡â.ÜBËËƒWnR(\rDÓQqñwÛÒèt[¸y\'¨ìıû#ÉH·¼Ê„ „ âìçš7)Uk;¹£rœ9|_^öBHJªSerd«â´‡ÊILÎH\\´bJlªyNİè›:I§zÉ©—]\\Ò?Y[x\'.>Xügåı\\|yxß­›}¹”Yœó@ÒN ¸¼©ÊJ•‰\0æ2bçJ«–­®ªf£®¸\r‹Îüó1C‰^‡çÇ†wİÿ\0ßy9ï-ë¨×y;U0ËÕ·àÀ«…ZB“\n•¥ò¬T*:CT0\0—;CGŠq›½#+§%”[à«tkşëO/46«ƒp1“†½k4|ö®ú×N;ÙÕqì„Q©ó¹2Ñ§ççJ‰…(kdÚ¹\0`‚ÂA|í+ÒrT,gDAqÆdæ€Ù;Nl¯-²Tı8.Ó%åvf5·ç\0A3\ZUøğÃ+ş•Ë,¤ÿ\0.î†Tk±æø…rAw^¸ê pâUêV¢Ø#AğÔ§?Éï¤áú²eÛÜ¬ıû#ÉH£³c}‘ä¤\\„ „ àh;š7)ƒÕ+;ù£r˜9|Ö|o¥ğ[\ZŠ¸z×/|¦ç(Ë”n¨»aÆ¼Á9z†½ 1¥Çó\n#Q`r‚Û\'0~\'yı\nÛù¿?³9/ÆÓËêÂÙõS(åUx\'	9£P	­¹ª£.nÆÏz³i0Ñc&8ê|¶å–ïÖV«.c:Ä“´6>%½ÊjMgåMv­1Şçò\nÍ7‹’öÕŒé%W@å!KG4½¾’s$gF0¬2ƒIyÚyÆ5Æ†±İ·O9•émèÌŸ’ZIæÓ»à5Ÿ{([(Òac€\rÍ¿¼ëT]Uõ\0“šÀ9¬5­Ñ¼í+3+41›ç¸	?;VÌ\'„ê3åş¯uÇ[ªçÔqÛıÔMı{±KPB…ïÔ¹,WºeDÑ)ãÄÆëP„”šIŠØ¡cÎ9®uŞM*°Añ]=–£^ô™Æì.ÃAÚ­Œl6wSÁÆ5ÂÚ¥RëÖu\ZŠĞ©rÓ…Ó–So¢lvÏa¾AL¡±}Û=†ù2Èè„ „ ó=NhÜ­2¢Ì ë†åbEãgÇ§ÕafQ ×$s–Ÿ&hÒ¨]Ÿˆ€Lo;Uµ˜Ê®Ì´m˜1x•pİLºûÿ\0nXòKËxõñQõnz…ÏL.Z°âtÏ9IR/Ô¸»]r÷¸ë“ã+©µcı—yÇgs†õé~\\f2×‹ûs¹Y\Z6aÎ;\0Á>Øp	lºN³>EPË–ÿ\0æoõÎÚ/´TÙš;š–…Uš•ºğqÉZcËíiŸcd€1&õ©h£›O4kŞ\\Fs¾t\0«d–Kçª\'·ó±^¶;îÇñÜÆ~:Û._Å\nõƒ1»¸õÌåŠ¬.2ãuĞI:Oy-…uV§Ò]^o¹º–Vú7:3ç8Ü\0 I†ÜºgÆ¹Á¸”×SÜšğš\nÌèq)öKŞİê\'+Y)“U“¯àT‡[,e†@9§ÁKb¤n-7êÍ8|FåÓ2ÉŸpmŞh94\0pd*Ş5W±8éïGè´›Rã¸¨)R“·ÍJáqV•>–±}Û=†ù:‚Ã÷löäë„ „ ò*.¸nR,¶eja£5(êeÖ\Z{ÀY®z˜ş[b¢BõÎ?/8ôZyPœ·S`ÿ\0J§ª:_Ûtéó¹®ªkğKõÅN·€W˜8eú6ém{.ò+!:Çê´)åJ¤ˆ-3uı‹8›ûÖ)¨ËË—•lT¨\Z@‚İ·hUébŸ^É,hà.¿ÆUAQìÄg\rx;ZeÕí›[øçè?iÇÄ«!ê:örÇ8‹Ú\\O³&`	Ä˜›‡jËgmôÜÉ§5³Ö3Ø0ø÷§Õ«œÿ\0dGiÇÈw¨óDèÀ&Ù‰8›Îó¡wÆë§¦û:ÕÎpoá`Î;Oáñö,;]¤±®¸Ü_ødtÎ¨[n§yÖVfY`m:—âÜ²µ8§€™:‚Wºûµ¤y+ƒ¡V¦@§ûÉÙçr§d³šWE’,°tÇÏšœ{¨­ª.1\nV¶{AcSŠê£8„\Z·ÔoSZ™|è>Tq¸î7ñUø¼}9aû¶{\rò\nu‡îéû\rÿ\0ˆS®j„!!>on,ì^ëC“<Ñÿ\0Cş8)>ÌØıZ‡ºgÏÂºû\\…ï?fl~­Cİ3‚>ÌØıZ‡»gğ¨óx/aA@^õöfÇêÔ=Ó8#ìÅÕh{¦pO\ny¼*Ì“;¾*\'Îâ½ìrjÈ?ËP÷màšy/cõZéœ\\zšVäñÛ²›gs£P8•¯ô^Óõ›ÿ\0E/äoŸgì¾¯Gİ·‚¿œRíâf†ŸŸì«:ÊÉÀ4Œ6Ü.Ö½ĞrzÊ?ËÑ÷mà“ìí—Õèû¶ğSç?á\Z¯\n©C8†è»à;ü”ĞÀIÙ‡zöï³vOV£¯îÛÁ(äí—Õèû¶ğQçÛÃÍq1°qYyR‰pôl.“´4kÚv/¡~ÎÙ}^»oÇrfÆq³P?í³‚Ÿ8WÉvú9¹ âÜæÀËOqU¨¶\\²¾µ©È¼ã.±YIÖhS\'É7ì6Mõ\'¸§Ár«íóæKÉÁ¬w÷­+=ÑŞ›ÉkÂË@´Î	~ÍXıZ‡»gÒe\"¯\n„×/wû3cõjéœy1cõZéœÎ\Zxşu(½`’4/ &,~«Cİ3‚%¬gü­tÎ\n™]¯.š6/»g°ß ¦HÖÀpªBB6˜¸nNBBBBBBBBBBBBBBBÿÙ','89005'),(6,'image/png','ÿØÿà\0JFIF\0\0\0\0\0\0ÿÛ\0„\0	( %!1$%),...383,7(-.+\n\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++ÿÀ\0\0å\0Ü\"\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0C\0\0\0\0\0!1AQaq\"‘¡±ğ2rÁá#BRb’²Ñ3Cs‚¢ñSc³Â$%ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÚ\0\0\0?\0ŞpF-8Õ§\Z‰h‘È‘‰N=–œ“B³*’EÁı/V\'0apB¢acµìMµß¿ğÚBY=&%³<P»mw Ÿjšµ´Øl¯ĞXiM®\0€o5”úMÎ[OQ¤NWJö¿Õõ–\\>‘5yEkiå-XZz@„Ø;	¯Å`î%›Ô\\ëiBĞ(˜ü²çèÏ:,­„¨u@Z‘\'RŸ¬ŸéÜwÒj³8×I¦§PĞ«N²}¤pÀxnq3±ºA+FªÕD¨š«¨aàÀó˜á–Y%–(¬0€ËV#‘ˆöX@Ë\0‰\"ĞJÀŒVVê±¨³È#P@$XåY…XÕ<¢:ˆÖ\nÆÓ\ZÀà>–ğ>¯3ªoZ¦ÖµÔ-¿Ùñ•JBt?M”¿û´Ø›ƒDXt±<ûÏÊPi †úMB6ç§ph‹}{¦Ó-Â3°:ğxÉYl\0ÜîH×Ï¬¶ašZVr|/¯Ëİ7˜j–ü`ml9Eb’âa*^0¿‹§î´¯ã©^\\±˜y_Ì0{‘áèïÇƒw¤ì_m~\ro)aa(^1œêRoñTûÉÄmü$ÿ\0èN°V-„y¬YK,S	e‹eaD‘\0ˆâ 0€¢ Ú4ˆ6G ŠHô\rDrÅ Q FÓTFÓX—Óµ0*a¼E–¶ó×á9.¦vMùPéÕFÚğ°*5éÄG¾qZmm—Òâ7;	jËjXÚWòÿ\0°Ü`ÖĞ-tkYmÎKÃ½Íï`7;y•Fh)‘p[¸sëÊÖšLÃ<¯WB4ı›’F»—æ~¬ u¼&cJöãÛşwÛÎIlÛ°«Lø0#Ş.9NÕE<Z\\uØXòÖDek!{fÇMìy| wç³‹‚„×bğ×™å}ª¯HÄª4·û´±´’q]»¬,©¶÷Ô÷:[~œ YkÓjNµÙ•¡âtüŸ1\\M¨º£/ì°ûCñÄN+–v±kûW¬mmAµ¶çÂ÷èÿ\0R«R?f¢Ü}ôéâ¤ÿ\0xa\0Ç¸Š\"ˆ‹e\"Xe‹\"HaÂL#X@\"ŒF‘Ğ¢9\"ÀXQ\Z\"Ö5DA4¯í•<¼¢šOUÜ°!@Pm~#¹î–$úc(ûŸZ<¿GøÀÙg9Å×)ÄšÚT%©µƒÓdöÇofà¾p5:‰hÄeå}ºLÊH±á$\\t6ÜwJÙ[9ÁdÃ‹*é²¤ÖP<Şa°e×§Öğ &W¾¼µ·v€Úmğ8*@{l-Ï[xÛãå°ş®÷ï•ìV8+İ¨Üj£ éæ| tœ®¾	AÂúj3Ÿx¾›iá1et*§áê’:Q7½»Ô\\ÎmÄcX»RBP--	G¶,ÁuÜMGfÍ\\MF¥ÅQ™€c[ÖTı!ã¨æÍb\n‹{]EµµÆ”¢öjuï£/ÏnS?ü}\Zƒ‰to…şµÙ|B`(Vãz÷¤¯[[Ú\'ˆ_•CfR¿6ßm;’)-S\rÄ¡\ZÕ(°!ĞŞÆÊu°:[• ijd•8¬£sÿ\0ÛÌŸF•Eaëi•±mn@ÖıE¯7¹^ÔÃ[[sŞo»7ÙZ5qU×¿D‡ì_ñ\nş±¾×ĞZûÚÁjÈñ*á¨T¬¼^Š3®Ü,Ê_MNÒIsĞ`Ñf˜E²Ç4[@KF4dA´3F¨€bˆ¢5DŒX§¼å>™\\¶.‚rZ7êså«ÓŞrïK´ÆaÜı–¤@ÿ\0K’˜@ĞR[-·ÒSó\Z|5Î›ØÚX±xóNÖş“Iœ°jªë¨eù`nråÔKXši)ùbmä//9U-5Şßó:W6ë\"e®põ/ÂHÜKzà\r¼$|^CÅ¶ğˆÂâ%J$b¤#nšiÓ¨Êrì-3Ó>·!Êkl_…A}Ät¹<šª›\\Ş?ÍÕ¼©#«ì–<µµºrÓóšªéJá…3ëxxC-e$]Eö\Z\r;¦Ò–\rw=y5)/	P)ím&Ó²•/M“ˆİ›tVÔçqå!‘Ë”×Ö¥V‹­jGÚƒ³)İX\rÁ·ÈÀ»4Ä–ãÓL:iÉ”î¬7Sõ¨ ó00D[FÀh\naÑÍ`$Å´sE\0Ğ@„`Z\0kkŠ#ZÆ¬IRô­ãÃS«miTû®8Oû¸e¹D×ö³k`±ØÒb£÷”qyˆ<âêñIurÛ%6èn{Áú]XÙ¼å‘ê ¤ráhÁht¼ºdÍøJ^ë-9EP-ç‡“Õ/5x\nœVåõò›Š;@WÁh¶PùH$êdê»H-¼S¶‘•TnDihJ`khV8:ş³_TöZƒ»“ÛªŞşCœºEÆ êØƒÌJ®ab¶3=—Í‚·öj„Xÿ\0t~tÿ\0æ:h&,Y€\"Ú4Å´cXE°€³0ˆÄ5‚#XÅ‹XÅ€Ğ#i˜¥†¦ô‹ÙE¡_‰èªÜ­¿Q·eğÖãÌr•Jx:´ô\Z¡ßÂ}åIŠ¢ÔŸ˜ºjÃfUÃT£UèÔĞ¡±ÓÄwoà`i©¸Ë1^iÀ±·C\'àÛXœ²¾€_§ô–{å7[A¯å,¹}] n)<mäZ-Å\0j™	·’ê‰£éÕ±aHQ©´ƒSÇÃãÜnÚs#ÌMšàÀv\n-Éğ\"XTĞ^éªÌòJ€[Øk~`òÔlfãáèhCw‘!x´Îøö ƒ¸6±7’Î\"›-_ï©X1ı¥?eíµ÷İ/¥À›–ƒÙ\nŸş‡³ õnıİó—÷`43\0ÀYm\ZÑF\00‚Vƒ­?	‹#ÀjÆ,RÃSÊcP1‚PÊ_¤>ÎT¬W‡Rî£†¢´Ê5£™\Z‹nAî´¸ƒçìmFı%:”Ë^ÁÕ–öŞÁ„õÛÆt¿Kx<5* kJ­‰èµû¸•=ó–Ò:‹òiËª\\yş2Ç–TÒß]>¼eK\0ÛkoÇIfËŞÖ¶Ğ,”[A‡Y­¤Š5àKs!TŞHv‘‹°+©™3ÕFR·±æö>3YœRÆcˆª\nT‡Ú±>°øtãˆeafPGCõõi\rê\n@ŠinñàyòHÅz;¤T‘SŠ¥¯Ä_RÖÓ‹q{\\ß¯„ÎSE°ˆ(³Š•I²\"^£m°Òç]mÊö–<›/\\n4+ñzªhîà1^#uT^%±İ®mm§FËòÊ8pE\ZINûğ¨ıæİ¼àW;=\0Õë¯\rZ‚Á9¢\\ÅŞH\Zr°çp-a1€`Y†`1€³¡\0Àâ‚` CX€b1bÄbÀjÆ,X7† %3x@‹Ÿe£†­Dşº½Ì5Cü@NE·\Zínş“èªfqİàEme³Pw\n‚äàc,`Utü¬%£P[ÀşR›–UåÎYp5/o¯­àn[ÆúËéÓç½ähÆ­`o·Ñ9k¾»á×M<u•¬GhUI±¿Ò\rnØ‘~õç¼‹k#µÉ·!åËò”ñÚú‡¥ºZH«Û[-¸í¿}´yì.\n¸—’(ó-ä–Ö2èÀTz5+¸²Õ*÷¸ñ8.ÖtËŒ´À0ŒSÆ‹h\0L+ˆF- 	ƒ~ù“ÀHŒX±\rLÃX¥ŒXC\ZJÆ\r37€Ô3’úY@1¨ßµAAòw´ë)9_¦5¾\"ù?û˜,şrÏ©*G¿×1,x\nÚ>bja”îr&´k|of”ÜúÇıtü½Ó“Q›5†ì~_	e«Fã}f“ƒ*n	#¤SÉ(ë{{ıÿ\0]âÑÙggğ¿Ú¨¥kµ7¨«ÂÙ%¬eK\r­¼ÔâqÜ:2üOÊ\\=v^¥g¥Œ¬: ‡¤›½B°íÉhn[M‡Ú ”Â¨UU@\0\0\0\0\0\0Ğrƒ\r “\0Z,Ã&@Y€ĞÚ- ‹c\r 4\00a\r\0Ã(j`8Å1 Æ«JÃSÂ0d@jÌ=1ÓáÏı¦ø0ş³§SœßÓ\"{xVıÚƒâ†9¦üz7Ì~_)½ÀÖÒW1HÒ÷’°İ•\n–úb×Kÿ\0„ªÓÆéc×åÿ\0o‚Ç]4<WŞÚîv·8zJ»}t¯GMâpU.îêM÷ç§—=·Œ­˜ş~7ì“³”ñškPP\Z£¯&U =Å˜_¨sG`\0°\0X¥‡@%°Œ[XòZ*<¸6¿‚ü%áŒA&zaŒca\0´[dÅ˜\0Ğ	†Æ,ÀÁ€LÌÄa¡©‘ÃF!LbÈêÑŠĞ$)YLj˜fñ|P”ÀeZëMK¹\nª.Iœ‹·½¨\\mEZkd¢ZÄîÜV÷\r&ËÒN~H(¤„];‹s÷m9S[‰ıÆÄ¬ŠŞÉ“Åµ;ßá\"­†¦ÿ\0V›·\ZƒÕ¹×¿BO×ôš›ÛC{uä?(WáøsüvÀã‘·¸:[^_+î\Z¥t,R@·==ùûåO˜”æH¾ºøímyÛ^³a—Ô©Š¯N\0MJ†ÃRUF·vè ÇÃ™0:·£œ5©V«kzÊ¶ªÓø™Ç”µ—`Ö…\Zt“ìÓ@¢û›\rIï\'SŞcIâ`2Z0<LY3$Å–æ1dÌ±‹cÌbÉ™&0<Lâ`q@Ö‡ŒVøá£Àš­­!£Æ­H£ÈkR1^ Ñ9†\'ÕÒvæÛÄè&RVsîĞÓ©z4˜q°Øokó09ÿ\0mÉ>2£=İüÎ\\{p¤Óò”¬ƒv>,.…Ñ«¦‘JİL*©ıÚµO¸¬ÿ\0Ê¨–ÔrÚCpF£míÒYòîÊãkh¸jŠ:ƒÕâÍî];;èÆ•2EvåH\\QzúÕó°î;Àç}˜ìN\'0!©SCşµ@lËM\rO\"¾úNËÙ.Èaòå>¨¨Àªÿ\0i­È\r‘{‡æô\0\0\0\0°\0h\0Ø\06‚^3A&	0K@\"bÉ˜-\0´“ŒÃ40<Li‚`–3 3A-$À‚ÍÖZ<5yÖÂõ°\'­XÔ©5‹Vf¶cJâ«QP¤@Ü«ÀÇcE\Zlí°åÌ’‰›zF¤ºaìÄ~³o!4˜ÒVÇ9õ‡ÙA \Zè;iÛC\'·«4²ïo½$d¸oUE;|L©gæøÊjv~&_Bé?h°Ş²‰°å9ÆUìÔe3±e”Ã¯	ğ”.Üöi°®+ &™:÷PÙÊ+S…G\0£b)b8ÅÁêÖçyô?¼\'Î]’røœ)]ÿ\0´Qÿ\0Ê³èj¯¦¬R#Šd\râ˜â‹â˜-\0ø —‹/´-´ĞK@\"ĞA&hLh$À-\0‰‹f˜sÍ,ĞÁf‹-¼ÌôôØÌIUb?T_ÇúNQ›ãšµFfë é==_Á.şpÀÓªxªí>b‘ÑÊ_ijî)™èrçám%š¦kRd¨¡•†fzÑJ©›V§ÄYpËQÖüÍÑş©> NÃQç§ .ôô\0´ÌôA&zz\00Lôô–Å==\0Á&zzÜÅ1˜€$Å==ÿÙ','89006'),(7,'image/png','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0<\0\0\0<\0\0\0:üÙr\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEß:bZk\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0äIDAThí›IsÛF…ß`\' Šc%ã%•8·üÿK®9§Rq«\\¦cŠ;Å\rsX±X’-o$2Î;“ıÕÌô`z\Zâ—_ÿÚà’òĞÜ·¾8`í¡\0\0EĞ5¦¡bµÎ¤¹´g=°mépmU×BÕ3Q1\r\0@–çøãuÃÉRÚ³ï\r8ğ,ÔC¡oÁĞõkóû«3Œg+©qHV„@«á¡{0ë!?”ejØL!äÅ$\r¸ÚxÜaÜt«ÎIAµ‚—§dy!%.:°€Àw_×Ğˆ¼;ı?\n¸¶ßşìa¾LÉÑIØ–¾ïÄw†İÊ4tüø´	ß5IQı+*pçQˆĞ·)^šªây§×6(~[Ñ€=Û@3öYv\0.¡¿¡*¼,F>>ª²¬®È4t´‰Ş4àª„õ¶U=th^4à›^&Ò5•æu‡eÿÖ°ÄW#\0ŠÂŠ3‹ÊÖALi¦(ÀÄ\']`q8Ä‡)Iÿÿ×U\ZXĞ·±|TšÊyÛ*\rìÚ::\'uF,ÕÏ/ÚŸÒÀI*§³«4ã”nKç’jO²S\Z8ÍŠ{fç	IX%übÛ®VIFñ¡lK“©Üâ9\0ÌÎ×\np÷lŠ,—w”¤)cÎõxäx?˜1¬®Õë·#äÅ$­­&SÎ”“íM^,–ÕUßÕ\Z	iˆÀë4ÇZB¶T?êáál8gÚ]zî1p·7EšqöK\0NæX¬¸³†\nœf^P2jšexõfHˆêªèçáád‰aİMÏ×X\'ü½]J@Ó¶rÚÇ¤\0›zùÃúFR»œœVËÛæù¡Œ°ÀgõuÜ·¸À\\N}K×e¦ìŠêøü›:~xÒ¤xÕ|?=kÒsX€Öß±UÅ2`™ÜF#\Z°méÔkÍ­\\gO›ZZq¹V¥›ts{G(Àº¦ &öa|(ÏµàVx£\\j¸¶fİCÚPI7×éÅ³&FÓ%Ş÷g•¬Ÿ}6°qh£{pùW,ÀEËC8ˆë$ÅßızƒùJ·â¶ß<ºŠVìá¨îB×¾¯¼(\nŒ&¼ëÏ0™İ¾ôÉÈ]ÛÄqÃE8R²ğ]¥(\n¢ĞEºX,¼ëOÑ,>y4½q„Ãª…ö‘ß«H	X†²ì¢zÚíÍnœîWGxD¡GM-¯³N–4MEû(@+®¢?šãmoŠÅN²\\Å¢ ‚“ã\0Nåğ@w¥(\n\Z‘‡¸æb4]àMw‚óËªªæTt<şªvPS÷¶B æ;«6ã9N»cˆ<Ï7û”Œdª(\nˆÍFVma?õ½RNo¸\0\0\0\0IEND®B`‚','I89007'),(8,'image/png','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0<\0\0\0<\0\0\0:üÙr\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEß:bZk\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0äIDAThí›IsÛF…ß`\' Šc%ã%•8·üÿK®9§Rq«\\¦cŠ;Å\rsX±X’-o$2Î;“ıÕÌô`z\Zâ—_ÿÚà’òĞÜ·¾8`í¡\0\0EĞ5¦¡bµÎ¤¹´g=°mépmU×BÕ3Q1\r\0@–çøãuÃÉRÚ³ï\r8ğ,ÔC¡oÁĞõkóû«3Œg+©qHV„@«á¡{0ë!?”ejØL!äÅ$\r¸ÚxÜaÜt«ÎIAµ‚—§dy!%.:°€Àw_×Ğˆ¼;ı?\n¸¶ßşìa¾LÉÑIØ–¾ïÄw†İÊ4tüø´	ß5IQı+*pçQˆĞ·)^šªây§×6(~[Ñ€=Û@3öYv\0.¡¿¡*¼,F>>ª²¬®È4t´‰Ş4àª„õ¶U=th^4à›^&Ò5•æu‡eÿÖ°ÄW#\0ŠÂŠ3‹ÊÖALi¦(ÀÄ\']`q8Ä‡)Iÿÿ×U\ZXĞ·±|TšÊyÛ*\rìÚ::\'uF,ÕÏ/ÚŸÒÀI*§³«4ã”nKç’jO²S\Z8ÍŠ{fç	IX%übÛ®VIFñ¡lK“©Üâ9\0ÌÎ×\np÷lŠ,—w”¤)cÎõxäx?˜1¬®Õë·#äÅ$­­&SÎ”“íM^,–ÕUßÕ\Z	iˆÀë4ÇZB¶T?êáál8gÚ]zî1p·7EšqöK\0NæX¬¸³†\nœf^P2jšexõfHˆêªèçáád‰aİMÏ×X\'ü½]J@Ó¶rÚÇ¤\0›zùÃúFR»œœVËÛæù¡Œ°ÀgõuÜ·¸À\\N}K×e¦ìŠêøü›:~xÒ¤xÕ|?=kÒsX€Öß±UÅ2`™ÜF#\Z°méÔkÍ­\\gO›ZZq¹V¥›ts{G(Àº¦ &öa|(ÏµàVx£\\j¸¶fİCÚPI7×éÅ³&FÓ%Ş÷g•¬Ÿ}6°qh£{pùW,ÀEËC8ˆë$ÅßızƒùJ·â¶ß<ºŠVìá¨îB×¾¯¼(\nŒ&¼ëÏ0™İ¾ôÉÈ]ÛÄqÃE8R²ğ]¥(\n¢ĞEºX,¼ëOÑ,>y4½q„Ãª…ö‘ß«H	X†²ì¢zÚíÍnœîWGxD¡GM-¯³N–4MEû(@+®¢?šãmoŠÅN²\\Å¢ ‚“ã\0Nåğ@w¥(\n\Z‘‡¸æb4]àMw‚óËªªæTt<şªvPS÷¶B æ;«6ã9N»cˆ<Ï7û”Œdª(\nˆÍFVma?õ½RNo¸\0\0\0\0IEND®B`‚','89008'),(9,'image/png','GIF89aXX÷\0\0ÿÿÿñôôáëëÿïÿæÛÛÛÄÙêşŞÿÖ÷ÖìÖÌÌÌ­ÎŞëÎàÅ½½½•ÁÕÙ½õ¥k±±±Ğ´€´ÎÄ®çœd¥¥¥ß”cÖ”c¾¤Ö”Zs£À™™™ÖŒ\\³ÎŒZk¾c½k™µª”k”­S–¸c’«¥Œ»~R„„„ğfB™†BµZ„œZ„”ªqJàc;zzz{/‚­ÜZ9TxœjE†sRs„‘aBx¤|kfffJjŒ[:´M/\0sªoo`€V8B^o\0k¤VVV›B)\0f™cUoL1\0^:P`WL\0X„GGGcB*ƒ7!k<\'\0Rz1ESn:\0MBV;%\0Inf3\0D:(:FJ1!V1\0\0?_,26V)\033\0D)\04M:%J#\00)\0&->)!\0\'=+,\0!\0-\Z\0\0!#\n\Z\0\0\0\0ş\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0!ÿNETSCAPE2.0\0\0\0!ù\n\0ÿ\0,\0\0\0\0XX\0ÿ\0—H° Áƒ*\\È°¡Ã‡#JœH±¢Å‹3jÜÈ±£Ç CŠI²¤É“(Sª\\É²¥Ë—0cÊœI³¦Í›8sêÜÉ³§ÏŸ@ƒ\nJ´¨Ñ£H“*]Ê´©Ó§P£JJµªÕ«X³jİÊµ«×¯`ÃŠK¶¬Ù³hÓª]Ë¶­Û·pãÊK·®İ»xóêİË·¯ß¿€L¸°áÃˆ+^Ì¸±ãÇ#KL¹²åË˜3kŞÌ¹³çÏ C‹Mº´éÓ¨S«^Íºµë×°cËM»¶íÛ¸sëŞÍ»·ïßÀƒN¼¸ñãÈ“+_Î¼¹óçĞ£KŸN½ºõëØ³kßÎ½»÷ïàÃ‹ÿO¾¼ùóèÓ«_Ï¾½û÷ğãËŸO¿¾ıûøóëßÏ¿¿ÿÿ\0(à€hà&¨à‚6èàƒF(á„Vhá…f¨á†vèá‡ †(âˆ$–hâ‰(¦¨âŠ,¶èâ‹0Æ(ãŒ4Öhã8æ¨ã<öèã@)äDiä‘H&©ä’L6éä“PF)å”TViå•Xf©å–\\véå—`†)æ˜d–iæ™h¦©æšl¶éæ›pÆ)çœtÖiçxæ©ç|öéçŸ€*è „jè¡ˆ&ªè¢Œ6êè£F*é¤”Vjé¥˜fªé¦œvêé§ …Ä¤–jj©H„JÚ¨C,á„Vlÿ!ë¬³ÂêÄ©ªª¼\nEfÈQ~øñÇ±È&ë‡m„Å½ê\Z«H¼\ZFz$«í¶ÜşQGV,«´ŒZíµÙvûG{´ëî»}pë‡[ŒJîa¼\"ñ«|t»‡o¬Æ`pağÁÆvÄ«-½ÑŞû¯C8¿ÛöqÇŒÅÇ ‡,òÇ\\Œ±FÃÚ¶E«óe®f¤›ì¿kŒÁÉç¬³Îñ†ÃÇò±Å¸-ß5ªÅr›ìÆhÜŒÅÎPG0ÉohkFÄEÇÅk1kÛÇN?-õØR÷|G²m@›5\\¼ZÑF¿ÈöaGÓ8“m÷ØOW¬j¯ÿ½®nk»ÇcÔ}÷áx£4ß¹úmV«[È¡ík8øåvc1Ğm`í8X¸F®íÜ–cn:Ş›#kËŸÕv£.öé´—­8²õ¶ŞÕÖ°Çm‡ìµŸ8²zô­ûU£FÑ†ÒÇşn¸ğĞó¬÷«üfÀİ<ğÑwõÙÇZÑ¸õPñº…Ìü~³÷ìï¬¹ÃuO>SmKìt·¯?Ïk ëóSJÅzw¬=TnvûKàÁöp¬â/€EáU×õ†õ)ğ‚Cƒÿ\0A¡Ô/YÎC /¾øup(G#àğgÁ\Z<VîNø\\…Af_k¡w>9<†81Ÿıÿ¶÷¼bı;V~Ä™|0n4¢×Ç@êq°‰19šFNÑ…XĞ[ü˜ˆÅ•ğÊ	o›Ù\ZŠøÅÆğV¸bS²µì%+‡^l£ÁW½9ªäeËÓáØ¨G0ŠÑs~I¯DwÇ²0<––˜È‘ä‹kè[× óøÈ)Vñ•	®œ9íëkÜëä#±`‡cu.”\ZÉWµ¶ÆÉ“ªœ\"èXR$_£ÜW&×E:\\æ²c@–ø|©RÉ²WQØ‚ÚPnil\r]4æ1õøI22s	Vƒ8Õa™ò”³!·ÙÉVşá•ß,ÂÀ<u0`êÔ&;!™ÿDÆ“ HPaÆhf³Òíó Z/ÿ9$lyw°À–O„Z4gïûƒ–ÉP€oÙEGª3ğ²£Ğr‚öÖ¨O’²“•Çê#J‡†dU°¥e™K/FW:¥âÚ\"²î€†uæ”ØN¸Ë?ğ!½$\ZC‡ Td‘N‡<{ÚÓLö†*«rKÕ_$‘å‡:˜aĞBd%GEO[¡ ;SØÉö\0´d­1¬íËè¶˜e\'äë›H°B53Q‰\ZÖtµ\'QñšÀ…İ¡®ÄkZ\nËT…a°öÌì\n£ÈX±>\rså_ÅåM ÚKšÃ”×9oŠÓÎ.d`@Êöj†pÿ©µ‰\rç4ÛÀÛŞ¶á¬Ê‹[Q[ëZè‰M¶^%«œe®Ê²êT¦ªêºhÑ‘Y÷º<}\Zr·ÅÉ’¨4*VÛh].€á­\nCƒz×Ë^ö¢÷…$;d½e†[•Ö/qÙ2ƒ\r\'³ÃÚÕ‡k6nş×xóúÙ5€/Y£•#~—0Ø>NŠ:åª€ç{`ÍŞaÁEêÏ´5¯zİ¶‰TEVQÁH²·:8±ší6ö†\ZÛøÆ6Æƒgvá~´FÖ·¾‹_•+¤G$™c9Œ,<¼¡L(B\rTP‚\rX€È²–·¼exP¸_}¬]w&ë[öãGâèı8ÿ´ÜÂC\Z²Pl \rHÀÀç>à\0€´ İç$ ÌÈº«.±\0†®jKõ•j“7EçÕ¼ò%l|P\n4ÀÏtFMêR›úÔ¥ŞslÚf¦>ÍÁÚâƒÒ\nÁäMp­6’g;³.¡@\0ŸEêbÛØ€®Ğìk¦\"5È€tš­×*;Rp¿xcô ı‡9@Á8À\0B}ìr›ûÔ\0æp¿ëQ»ĞÖCìÕ:ó\rsÌÁ+3dçÀ8@Ü>·ÀNêP€¬Æ¶÷àı0;nQâÌŒûjhã¡*ˆÀNğœ\0\r`ÂÒğıÅW\'÷Kÿ¼¯§Fµíõ¡]8²Âß“¿¡áæ¸Çw>ğ=×\0P4ê¹°†äúaÖşTªªú2X€\0iøêÌ×Wtma¼ãæ¹Ö;n ÜªÔ]%F,ä¡©SÕ’¸`ğxYXá&¿#@€¬oıî\'€V\rÅfgwfv¥_Uq™İA&€\nX°Ë1ü~PÈùğNù¼ ì¶j*ÉÃä>•Ş*<²È@Œz\0:8Ö‡×³À÷	OŸ|ågŸw ú”œ…äØëÚ†[uJ_2ËÛIM\0¾Û¡µy«+6`wÚ;_à|VAæUOrò>YŸ?{£D,($Àô¤>€ÿÈÿ€d²Å}ô%·ìŸÏ~s‹ÛãûÔ»÷jŞşR©\Z¢÷×¯çÄœüúô475ğ}ü×~xl€Qw?¹÷n`dzGøG@Yğ}¦v\0ş\'s9…^õz\0~	8‚ç¦w>\0tCU}ºDt@ãfG)ó„,iği§¦ÿgi<GÀg$øƒç&np{ëân«4A6kÚG(C`È2!ˆj6(uPC@Ó>€u„\\ˆjÆ€v5ôÇÇ7Iw(©‚Y% ‚¸€ƒ3vÉÂƒ:×…vXl{¦\0>\04å·JMu,u°2’bÇR€\0xè†}è1˜,Eÿ \0lx‡’(…ê†‚\0(†næÈ\"4g8(ú7i€x˜\0ÿ·ˆVHAk‰“¸Š¦6\00}‹I¸AKÈ\'…,%`h\n8~Çç1=…,Yà\0‡ÈŠÄXl7=pønd˜,WS‹y’_İ7Œ\nh|Z¥ƒqã{VŒÚè…0}Fè‡IäJ\'ö\'·øxPzæF\0©÷Æh6ˆ·…Û¸°(øvÇô‡ï4¶hJcˆ%¸x+„T@CQ8ÙŠ%à0í˜·#ÎH\'åˆYn—wOÉX—~à‰†‰—:®$?ähdÀÒh‘`‰È‚ªØ‘ó8y_‡ùH’ïÿd’}ƒÇÒƒ§Ó³.5P‡Æ6\0Í\'“\\H\0-Ù<\"9’œÃv‚öÓzö“ğë¢ H\0  À•H‰€\0’ÇòªÄhO	(§4CÙs€hs\0ºh‘ğ/P(–cù|@Ù<¥WÔ‘jB1áe„CØÌç~õØ€ë‚{	„êˆ,*¸Jù\'u\'¤²ÑtCÇÒÄÖsÃÆ˜:À‡.\0ˆ™?H\0\0>±˜Kc¥QC•K MÃ¢-oÙ~€)é\0šªI‚ÂÆw¸†PÈ÷\'©ÂHÛr1Iy{f–æè>œI¹U„–ù8=c\'Bä5w0gYÿ\0zywy,sĞƒåiÎ7y\røšÛdfmĞ‰dÒ*EE\0x¶gë©uà\0™‡OÇwX|ªGR^•™ƒiè3ß7n¡Ù~z,¹H \n§vI’“$˜X2%ƒä9š?Ø—\0Di¡$H\0€‚Ú¹MØhqÚÃœ’‰ëÏ©¢¨\r˜Œ/5=2U&iø’)úƒê†—‡é£%Šhğ	£\\PEEŸ[‚UeˆıÉ~KÚmé¤@8\0ÔHRzåOd’*éBj‡@(¸†`Ê…É”.õ‹i¤NH¡=Z¢	Ğ€(Š£qJ–5à—K>z¦5e˜[J–fÿÙ<\Z¨+:¨ì¸T±9Ÿˆz,xğoÄøŸ™7Â©	H\0’ºz;S2d¥VâP§D‹J–*€,]@ƒ Ê~“™>MùB`PEŒ#&ú¢4:°§©h 6¬ÄJ¬ºgñ«u9«³\'¦tJ©á8Cbb?o\0‰ÈF\0ãp\nà\0`%à5àE0®äJ®> 5à \0à\0z†­²hY ƒ°•Ì:{\Z¥UEf\n&<ùY€u:·q\r@ PEdo0xÀdİR`x0s–Z¨g\np{x Öz¯w‡\00¯\0ˆW±9o¼*•2XgxÖ\0\rà\0 ÀiğX6³qssÂÿÈŸE\04i0¢\0¨û“\rğuBJRîÔ¯_R-™4cwpkOyp b IPµVkµR µbÀpÜ2:‚F¹Èt×gk:jV¬lj>+“àS™U²¨Z%ú\"qê’Sû\'àğ\0P\0\0\0†{¸ˆ‹¸P\0ğ\0pI õt>€g\"kuùé®j¦\0ğ¹ º¡ë\0\r \0Âj”|ö¶ôhn§¡xUBPy%lUOÇÂSK°\0…›¸¼Û»¾{¸ğ\04àµO8}Uë5åJ®P\n{s½Ò;½Ó›â)eêJßç¶)ªlæZ±ÿ9›¼ºÄ°\0P\0\0¿Û¾îÛ»@p°-?À¾ ÄK³ú{Gre5 ½üYŒÂ&²ÌV\\\\\0>ğä¯K0DR°\0ïûÀÌ»0¼Èò‰»\04 p@»šåOûÁ Üa}àk ÀvÀ:‚îI…à›D‡\n&kv,IÀ¾<Ã¼\0óûR Ã¼Û¸@Wkµ?À4pPÄ+¸HœÄE|B,YËêB:Pz\',¡àvt»Ss5.¼ÈÃ‰\0,ğA\\\04|Æ\0\0Rp,p`ÆhüÆˆ\0‹;\'Á7¼4LPz­Zyeù½ÜTÈy´():l¸ Éÿ\0ÇŒÈâŒÉ¼\0\0¹Pœ,x°vU<{4ù¬Å¥WâË%å˜Çb°-jPÈ’Æ§ŒÃ«üÊaìIpÉÇât¿)™à0/J²î”ÀZ\0É‰ëÈÜ\"Ì°Œ¸Äì¥|ÌÌ,\0\'€È§D’)©¸v«ï–DÈy·MRbĞ»4Ğ-ÌÌ‡»Æâ|Î\0 \0?À<C¹Ç×É#kÀqè0\n*»(iÌˆ{İBè\\\07ŒÏpLÉé‹Î‡;wœ›d‰¡giÍÔËz$ ÊÆnœ¸ /‹|ÎÄÜÆ’Œb\0\rÆmÃ§T¡?:~wÀĞüÄK]$Xz,9ì»ß¬-á|Î,ÿàÒ’ü\0´Òè¼\0—|_Ú­©Âòì1$)­XÒ;3İ»°ÁLª|Ì?p,IÆĞÊÚ‚m¸@¼TéÎ%(©}0Ô<ÃG+]$öÃÏíÛ¸ƒ›Õj|,\'É8Í-æœÕ\0@Ì€Ğ³‡\0WÉbMsbÄ%gM×\\ÕÇĞg×Û2×tÕì¨qÎ§”Ù‰Ò”LJTÖBRS-Ø¾KØÁÉşÌ-hM×pÃ:°¬”7\0ÔìwŒÕMYÒÒ8üÔšÍÊ…-ÉŒ,¤Û1İÕœLŠ|İ×ãĞYÑ\0»Û¿KÎ¡ĞIÈXÛàµÎ‰¯-ºĞ¾=5ıdÙAò‰Ç2Æİ¾Œÿ­Ø‘,ËIğË¬Ùœı¨|üªë¢Ú$CÈ‰İ@‚²Ùİ}¸5íÊõ-ÎË}×›\\‚cZİ“«§ÚÚ‰šßm,·à°ÌØ¿Zy\Z(´’\r›&¥ÍI‚¦RÍàŠ{Éo­á«ìàım‘¹|,pàÜ­-Tîáû-¾Êäì!~lçYœ&NsIÄ7¿LaÇBÈ/>ÈrÑ/şÆpÉp\nfYÀ7PÑ–œW\"ÜÜï\0°\00À\'°å\\ŞåLìW>¸°MÎ/=äh<Æ²Mªu!ûUKNs”ıŞWJ@Ü=É<<ÆRÀµO[,3[,OëÑR@,¸€ænmæhÿ¼ßdP••—y»Ü×ŒÆ@.ßÙíéLå€çw¼¿4µÄ‹éˆ>Ã†ş.`Úş9İÅCı4&EéÙíÇ¢, Áœ1sğiĞ¼Şë¾Şëû.iOpà°=ê}Ãi`º|\\Œ÷æ¿S°ß…©.}0d\0> ® `ŸKºæÆ­`° àE\0d ³Ü\"?€ÅìqLÎpä”ç½éí~-­+û‚ëÊ«gz¶qƒVğ¿¶$ê\0`îP0óÅ4PŞğ^ÛØH{sj«ø~3zãCZ›Iã5]Pw†­¦ºĞ¹gãæ\0%@A)II0ÇÎàÿPÛP¶[7\0ã§¯¾İT:·¿-›Fw¨+‰Ã¦ò>Ğ%Í=ä ï]Ğ±•§×\";´Õ=V2\Z%£\"X‚„sÛëÕí¹q	`>à˜=^ç\Z>´«3î~0õ£1¤”„õØsGWGn)¬\r HOV?pàÆ]\0ë\\¶P¿ÛñÅsÿ$MˆYĞäi¯‘ÉŸ€•`¾İÁwŒ…6w¬©7^İ`/5í3¢Ì3.Àì¹q*0=~°ôY=I¿°³I\n\ru6‰ïEmú1ò¯\'z³N*n\rÀw\ZeØ°\0÷[O`+«º¹ûëÂê¾]EÅ$ªzJ5ÿÊg’ªQfÿÊTN’›,sàÄŸ€@:ıûõödıÀï\"H‹,§îõ)l*à0yp€ÆÁ¹\0áçÏÀoj4 p ÁB†\r>„1işôƒ…ËF=bƒÆãH’%MD™rÏÀ0C–¼„SæLš5mŞÄ™SçN=}ş\ZTèP¢EÖb† >…\ZUªDjô!G\n\r\n\0@ìX²\0X€F1	^L££Á\0…SíB<PñbÆ”A®\\£±ï`Â…I®üÓòèbÆ?†YòäÅHœğ˜å@İ»=fx€\0™·óÀ#%ÉÖ­[K\'Oÿi‚s|XP@ )hÏ\n»D#¸/â=`G®’¥KÊÍ?‡]útCÂì³a\0oîİŸ\"8àƒöxòä÷¼aRÂnÎŞ§nÎ20p_,kŞÚ\\¿~ÄŠ©ÿ0@$ğ%$ÚˆŒÚs¯Áîòzc .  c¬Ê£\r7²(¢†\rÜÌAÏ€B¾üJÂŒ	âk?ë¹g¤±Fo´éÀŠØND¹;€‰›ŠÀ‚jĞ	%—dÒ@ À ¡=@È?Ş8‘$,\"ücÒş¸ãE3‹G5×d³ÍætüƒÇ+çìì€\"‚B4ªÔ­O?ÿì“A:§\Z@¼?ÿğC	$‚jˆ\0±àÎ„ô$Ä¶@ÂMK/Å4Óœà„‚\0õ©âûƒ	A7C5UAC½‹\0²#%,ì(h®;ÿØƒ‹.#1+*Õ4Xa‡Å	¥¶DˆUeÊktXuY\0«\\OÂ\r‚\\H(<Lä5\\]Ç¸ğWbÏE7]éØbÑ¥eu\0°Û`7x¯<Ào1ÚÕ/ZÿPP!æÅU\\qÉˆRuf¸á£*Õ£ õîu\0œı£‹)‹şxÔK„ÿp¡GÁ5øL°â#\n`†9f™m² <íåXÄ„H%ùİœ§-ñ5Lúr 4rcˆ\0C¯MÙÌljùå™ÿ§¦Úa$ä°éŸ$@ ÿÈÂÊ­§­V¹@LŸ	ˆààúmº°§ÿĞÃåªïÆ]Ë\"–ºÆæ\0~3ºh€\r°CÑ>2#h¯*C{¿úªc	©óæ¼ó6‡¸Œ ;jíSÄíÒ­ŒÿH#œO0ŒCöè_&^Ÿˆ‚•ø­\\¿ËÿÈ|sÏ‡\'^@$·¢\rÌŞê¢^OaJ7\n¾xbèİƒ¯ .Á „S œËŞ“+ú9„/>}õßti¬Éëƒ	Ø»Yé‹ø– &´®ß»*ÓG÷ÃA„\0*\0àøÈ¦6 o}„ Q\0:3ğ oĞ‡ÿ(X@4‡ûE6€¿·¤bëŸÿ,€ŞqdV;ŠV³Ò\"æ_fpàÌ·Ãö1ÈS^iÈà„lÇ² ˜qŸªÁ@¹ 0…İéÚÇæÃ\r¤¸Kš–ÄWÃÁhÄm‰ÉaÃ÷\'´1\nmÔÜ\Z}8G¬Ñ}¥Q\"&RÑ4àˆ¥Iƒ”N§¸ÒØf= ¼\"–&\'œ¹ÍÁzAÉXF0L*êš ÂĞ†:,ï~¨CÌ`…8^’=ëPÈ\"P€~éc\r6ø\n RZ°@mtpÈDúh\0`a‹~§±ÃåËY-,#Ü •\"¬øÁn:DB,XP’ÿRF§<å…6L“u5XÏó$\"šŒ°:¨_	˜°Ë¿õÒGŸ\ZÓ¹ğ¯\"ˆ3\"ÚcœÊ)ê|p‚)‰U©÷aè- C@ŠMÎUÊ}ná`	P¥÷èf\"×C€ŸÜ9\'”­`ºB›ÁØj¯ŒoèC\0›ö;=hNf-¡J0Ó¸ Y°Piø`\'h“¡ê›à¶°Ê·Ì	µ´âTÀ“B\'nôGùÊ_p˜9íDoq‡*ãB;q•OSƒ™\\°GUQ>@â@ô†%\\ó§œÓæP©×ÙÒ©w]V`……lae®•¾.eúE[ƒ-¬‹^øÊ¬fÿñ[©¤\0@!˜Û‚Oßš7Ğ43%˜‹]ñ:ÚP)îBÁq\\\Z\nè”(`L\\5É¿TÚ´3úÇjKà,à3‰ô)Z\"H\\¶ĞÍt	fğä °)”´ÏÍ+øöê¸.,Õ)àÑ’>Or!íŠ«’	sëÂØEµÙåƒ*H«xªÙâ:¬³ÌcnR¡;_x Œs\\§¢ò¿-vn ,_\r6·¨ÉAaZm«PƒÕÑ­ámï¹†`OÎtÎ¥o†CÕÚx‚‰	¼ˆ«Ì–¨õá\rkƒ_k–ÎÌ	|c\nh¦\"¬x”\"n„×Í·d!íÔpá5€°1Şÿo$É”(­Ò¦v@1HUæ¸óÅl›ÙC-‡Kê™o¸:Öñ|tbXÈgÆ×Æ¤?˜%\Z˜àw‘¬¸/7Ì1¦xœ±=è\0%H¯pÙfK‹ x8¡hÑ¼h÷@ÉlŞï›™æ¥Œ	]xÃe	b‡ŒØYR	Ëó¥†pàsmÁiİi[	]èë$q·Œ†µ²Fõ$CÅ´pö˜ğğã4À.€Bƒ·$åä4ùÏ„Q¹5­4@gT+[C½jé„W hs¬µ­3\n\\¶ÖVe!aEFz.hOºqÀ7m9ÇÕÁ	V]vÌüİĞØÆzØ‚K©m£ÇşAóŞvÀÿA“Ñ[•\nà·î??Â…3:’Azj€¡Ìæé”¯\r/fî0¿9‰f<ˆp¼o\Z]­pŠxÊßã\00u!DàÆµK2çŠFu¤¸ÏØX˜MX oxä•R·æzÚ$wÌ6—Wj•7İ=@ Ø^îBÆÜ/rÎ\"@ÕÁVV•VXÏ­09¸TSJ€º¡&\\9\":Ût‹\n\0îtº‹jÁ>Ğ²­ïkõß!à\0q37=ÔÊÿêÅª>ê Á;íéÑİN¸‹´î—ø¥\"â%»p‡æ¸T\nEî&ªİmW#âÿ ÷¼\n®Ğşçz%?y Tş_Ä|îéDÿğóuĞVüÜ¢Mš>x\ZQ™.kP€	÷~ğìi_Gkÿ[÷×÷‘BHü‹ì¯nUÔAfgL\nÂµ„ĞTeéÆY°q(uşéÿğ}YX?öõï“	\r\rv°±,p€l,Œ‘\'&ƒ\ZÃ{©V£%”sÒ Ò?Ô«¿Æx¬>È»ıÓÀ¡€±12©‹òŞë<ú\0“¯¢\Z\'p‹rãrB¯Ò¹ ªÀÆ¸²¨áÛÀ\\Î+$&¨+ˆ\0©±WA¬ã3 ?ñ:–]ÃÁ§¢ˆ\ZH+ó±‚TšAû#»ÓAîB?¡’.”/Ü/Bèá¯$\"º’¯LÿÑ\0²‡h¾¨Z±ïúƒ°Ó¡(p‹NqÀ§j¶(:¨6øé«B»!À,¼\'TÑ\r*Qˆˆ€\"Ù\0P8ĞJ´ÄK´D(\0p€©’Í@\0öË™|)(` €‰\Z);ET$\"{\")\0“Ò	{·ª1´(À™gƒ=?ğC\nÄŸ«(D\r,«„ø”Ø€\r(¸©,è2Hƒ9˜ƒ=èƒ4¨Òè<˜ƒ4˜Ø\0Ol€>=YÃ<’?)¦¨±Bª¢‡Pˆ1±Å‘` @,4Ü ğÑ 1Øc,—IÂb¼	ËX9Ø-vÔ6ğHDr\0€Ä$!ÿêÆ‹ÄH‚ØÆpœ\"ĞMÜ€˜ˆ@QR¤G|*.ğÇáK\0Â¹G¨-},´v)ÀB€$!‚Æ(4ƒÜ‰! I2‘»&D³-¤’¦`D‰L(À4MëF?ğƒÓ`1 J)°Ê«ÄJ«L6ÈÈñèƒ9GÉ*1Éúò¨Äc‚\"˜%DÃC™“h2g’IKé·7°IÄÙç³1>`»¹t;ë€((¨«²œ¯ TUi\0 \0p$Á´läFÚ8\r­H\Z`ğ\0x€ğŠ€Ï‹²(‹\0ƒ®4¨”êĞ£5ä˜O	WjŠd>×zËÆ	¹Î³ÚXHØ‘ÿç{¾üCŸ¬-ğ$<è‚Ø#Â¬ÃüÂ¼HÌ €ÅT\Z¸)2xƒ9¸ƒÈ”L8PÕ`(€ñMÑ4Ïó\Z0ÍŒ4±\"øWZ–O)‘ì ‹ÍØÀŠ£á·\ZÿB¾†Z‚1»n!ÊAqH€S/,Hâ|‰!ˆ¢²Ğ\"ËşÑ“£dPÌ\rP¨¸4LÛÎ®ô8@ÊdOôdÑ%‹˜õ4Í.`”=aÎ§r€\Zğ‡È¨ò ’º˜·Á–.òsÑÍ]& Mßt4û\'£sPœ¨”\n$¼ƒÏÖÜŒDÜÅ”H\0Ñ,ÀÎœ’QÔ\0OõŠòtÑÿ4mQ(M•Ñ9(‚ù)PŞh®kÃ‰x3dò’Ãj)¿Ô3À$ˆ4p%†¤“)¡à$ˆú²(­‰L’&<ÊF17fì¸,eø_;’\"¸4íäNƒŠJ5ƒÊô\0˜\0ñ\05H‚PÕ\r€$pÓYÅ•JC¼¼•ÙB‘iƒ++° <pr‚ú85ƒEeÔ™8\'0	=´4(‚°\0ÃÃkÅVwLL\nxDÆô(xƒ;ĞÆ‹„Jï¬Ê`RåLĞDÓW%‹€ƒ‚pWóüZÅ×=(^*Ö í/l;_+\'ğ,(@B”„\0,˜¢O‚Òe¥‰ö	\"ÿ\'KÑ(GØ€(q€õØıØ\"éR/õSlÊŒ<\rQU\r\Z83MÕz­Wx5˜\0˜‹{Å×œµ0¤‚K1l9,€òÓ0‹&ˆšƒaÓÒÂ%\'Æá„Ø™Ğ&Mê&ÙÆmÄƒ;ÀÚ¬ÕZ­õÔO-< ÊÕàŠumW›M) X€z\r\0œÍÙ¸UÉ[½ÀÃ¹Da ÕÅ	ƒVƒ¥ÛŸ@øëC§}Ú˜˜ ËØ‚gM®¸-?àƒÇ­ƒ‚ƒ¯@[±(\0¶H¨\\\0Ùñ€\Z W5}€6eÜ¸İƒ2Ûš)Ù€üÉ•Äê¡=—)-ªüÛRLˆéiØ¦å¡Â5Üÿ5r+39Ğ>€J¨$QÇ­ƒÈ•Q2ƒ0°‚(€	8ñƒØÜ	 İ?È\ZxY·Í^ÚøÜ(Ûwıˆ*]Æí‚»¼Ñp)òº‘8,gš£	’+‚˜Û¤•Œâ2§”R‚]j;Tª\'ˆ‚+@àVàFàn£w`mj»J‘Ğ$èŞW]\0Y1À€ñmQ¸Å?‚ğÏN‹$ˆ×óUá0Ñ„ıŒŒR\0‰ëOYáUlr‰1»¼S&EĞŒA½]ÖŠàT\"â\"6b	Şİƒt	ÍUÕ(_ƒ’‚VUõ4M8`ƒ,fƒ]á.ÖÚ}@İØ€5ëƒ—ôˆÿ×Å&äñ,Û@Ş½„°\0&H‘S5Ş5%6]ô€ø.\'}18=>Ï\0á.Vd|ı77fğÅHvÈíØ\0&˜¢= ! …\Zd;¥ãyÔªÜpã©·œ$ˆ:¶ÿµã¨†ƒ$ğ€¯\0‹\0€´ø60ß;ğt”3Ï…eYå³`®\\äbÎYØá»\0h\0pf‡h\0ĞŠ|‹;7#üƒ*.\nJ.ƒ§*eaÓÙ[å·3–ÅÍ-fÙ \r8­«2Ä°ÅÊ-6æzÎÙLœ²11lÇ.ğç\nq²¡!?.¸Ìj¯ãá&ó5$Š²PÁñq&çÿr–mó5¨²k\'¶qJ{æhcîÜëÁHƒ:k.#ÇZK%h%»VãœÙ“BEíI‰¦„ŞÅı§:x^ÌØd\réè¡¶ç¿jÂ).¯½ƒ7¨³œÃ‚3ÒfCR)z<`‚÷e¡£èe‡•^b´éÈª(Ø‚²6ë¯N?>ã¨#ê¶.f—C9\\B;\04Xƒ»Æë5@ƒã«œ—‚âê·.P€÷kX°‘(†¾rr¢¹ùSå°fV!Şİ\Z”·¤i·ÖìîE©Èêë0ßqœ–’j;4šp:\0ğ®&pJf_lŠÜ·ƒè“ì\01¹ÿ?ğı”®Íî¸u8Ï~´Äà¹â¢à~4*I·YÚ8¤mAºˆãDe…Êí·kµ4pˆ(¸ào7ímÏ&œ>X©Ã:¶Èî¹cñ7³¨…µ±;Í[ìÛe§TÔÃİnè¨<äŒ’\Zï_Ï9h\0êv4ÒókÂX,<hkƒëˆĞßÊ‚½>X®=qÀ,ÍjÖb›\"6İ­éş~Ë *ÖÛAğZWñÒÀg\'•„K™©JŒÀªcéƒĞ£:¶yè·èƒ,PNYB6Ì‘Üpª³˜\Zpl? c\Zq 80ëÛAØè§òÒp9æÄ%îZğ“N@öN=·¸Ï®7µKÿXd‡Ì‹\rpÌììA*­P€Æ¦µÒî&ñû¶7¬ò=<0»{ê\"L¨fòâÙ³?øQ¥ÒxÁ:²Ô“P&Ó·0qÆk\nùÖ©dåo;¯<GKq>\'p ±Ìş×Sï)Œ°İÆ¿î\Z¨m£Ù¥L—cøi\"Ÿ\n1¦ôÒÆ|qO7FPO\Z\"$õcïŞ\"˜?Èd^Y¬ÌiuÔ¦9:¥‹Êb%è&;©õĞõ»¨’@+ÚÈn°ö::09ÙÁğFö=¿UÉ‹¡œ®ê\"Æ*tâJ,[¼v\\mD¥= Ø€OÔ(À]t9/qVÖr÷‰÷òÍÀöÿu_q<„¤­Úrú@iÜæV³Ë¬¶ÏˆÂ7À;sÅbÍtËòu?l+/gÔ½×hˆ\'u‰wC-1>qj•o˜´&Øğ®ñh{\"RìDºŸ®6Ÿ„\nv…7ÜAìÈY«€yR¯§SñWã”ÿ2Òƒ>0ÍÈêæK+&8ØÑB7$ŸsNÇùbÜEÙÁ0ú¨\'ğ\Zh³0öf¿ú©2•>2ÀÂÄÙ€Õi‹‚®Eÿ8ı^ò³ŸA«xvû7õ†À.I\Z°‘±­öz¬¿rc\"CŒ>€ .=¡\0§Û®s¥o«VÃÀ*Ùº)_|ñnXj\0¬xY“>³{ˆâÿ\"ó@dìö±A7ƒÎQêtagyƒs€\r óÕ\'pırÃ7cöØWz_5k+„ı¥¹ƒ|‡5rü}Üöô´&h{åpv¥Á2bsö åäKk<pó:‘$ªª€ó|ˆBº~Ã÷Ê$\"Ybğ Â%C¶üiø§Ãˆ\'R¬hñ\"ÆŒ\Z7r´˜&È èÒĞ.*W²léò¥K;\råLhó&Îœ:wòìéó§Í‚Vä8dB@$Ò¤\"$0Ù°ÄQ¥R§R­jõ*Ö¥4P‘D?r¶,ô,Ú´j×ş4e‹™6råšÙÅ‰ƒH†è„Å<j¤ÿ§#âÄŠ3\"*Òæ4\\“&æÌ,±€qfÛĞ¢G¯İk¥Ä> d\r9@‡C´®mû6î‘[-0¡,Q™({k’.nüxNÓfê`ô£GN3a¶XÉšyÄ<RX,\0àİ{Iü4.oş¼â,$•Ø\0±Ï˜Ëšç¿Ä²¦!\'Ä‘óïvˆ¨Q4‡]5\0{4”Låö „\"µ•*8mÅ—z”@a§zÔ!v\rùñCwß¹øİRl”5²qz9êhÑ(.4´}EÂ¤àfì÷!“La…‘¡B‘ÑÀS‘”EÿCwD¥„aŠ™°A¾Eœ5ù&“H(ÑY0q\'Pt¡æŒIĞ@ƒğâwĞp‘4`°€\0\n°\0?ä±#¥;úÀšRPåwù©JX ¡b›pšÚ\":ô†\n	˜	…CY8€iU”àĞ¥cêºkV4è€d@‘X[ègÖ©ÉŠ&Q\r½Q„$@Òz\r8@Á.ø g\Zs ÙlHñÃ	,P@\0ŞM€£Cp°˜.¡ßğC¥íá¾ùâkÇ·õZ9­TL9õ†| Ò÷FCu,©,Ãi-QX2u@dX­S!àT\Z\r8È+È!+åë™½Mœÿp6¼²OH˜á@›HšRëëQl B\rÛv‘ÆÃb‡?°ğÀ¡\r©á¼ñŠÁ¤”ŞqFT1C3hõ7A…2ùÛ‡\'u€ÿa™ÁF\"©$Ëq·Åˆx¨`&XÜĞP™=á­O)2á!oA\r:Ä‡a8¡²Ü‘ëEQ®±¯hÎ”:«àY¤ÖĞ%ñÀ¹NÇ;AÔçQ­Ä\rX·ĞÂ\rµÛ»ì´¡Å•ºñÙFÁmÃ$ªŠÂI¾|PK`·Çj\'À—1}¦%5DÀ…{_xæ	XàŸ\réÑ†e-Ìü©C¼Üé¬’´ÿıG9`*èÀA[DÆ…t©Î;0ÏªpµÙeívt`î„P…~™§\r¢\n,0¬øO3XP›Ø\'Bƒ¸Ï!5À{P¡eÁG?\"\0HWƒË}¯†c\"YWº 4‡4N?!œšeİl+j\0@ =Ía‡ñƒ¸X€4ÍEô\"Ñ“„-&A­kˆ`Çµ®=°ŒÔZ„Pô@¡{S±UCàS¼º¤wI\r%\'\'ÀüA=Ò«™­ÔÔ(`\04sU\røV 2Ò{\rÚ\0Ò0‘myôœ¢ôĞˆ™C€çöG†9®\"E;Á\0¨áƒÿA¥*]€° •\r9CìÌ¨Ë]bM	vdğÇ		é{\0Ãéª1kë»¤²RÕ<P “ @—øjZ¨ƒ†ÙÈqŠÉr]‚ÿp¾ô!šÈA‚FôrêFs[I€ÎjP„=ıO\\?`¨X@	 	9w©PÒîŒaö¬B\0	3œƒñìc¾²¸3nHh¬’£Ôà—è0€´5ä„b\'=mˆ³P@ip¢êà¸át´8HˆçÓ¥àl+\rĞ™Š0Êt6R ¹0PïÀ \rQíªUÛÑîŠñA7¯B\0ØüÁÇÃÿú@<ƒ}p&íìi²~ÊGˆ4$¾Š\0:#…Læ!«	Sƒê:TÂÍ/ `‚Ú0¤!>®-sv\\:Øìİ,\0²ğ\n:ÄjH‚S)€*~‡•`ÔÚVS«µ*tä~#EËZ0 	%m³#Ü«¬‡u*0\r@ 8<t,=,U\0‚,Ôà·“­¡Q½bRóiH!“@† İír— ¡A‚€º°çV%s¸_Rû÷¿,\ZmŠçJ×¢†Õ­m-µeì\ZÖ¨ÀÔ‰ì¹ÓcÂI`òK‚*%HÚ‚%uk*iş{Î•Š™J°·ˆW~\0KªIŞÉ–	†:˜pDêÿõ½ÉMo	C\\æ¢béØE/Ş\no7	¼\r×rG¡€˜ÀÏŒğ’º¥„`;úfF>²‘é‹Z‡^¥÷¬\r\0,Û–´Õ!lûÆ¼+¸ÄïSÏ!mã«è ±ÆôQ„amÀ¿4&âV\0I3ãG§{ë;å´…6è#ºˆJT‡èP‡±x¾	³ºD¿Y*š2¢e7P(á\r¥ÔÈöp;à«U£¨• êQ“šÔ ®ÂÊà;Ü³ÓÌB	¦÷à¤D9À›UD®l$ªS?]>xÂ„™AhÌ.Hl	]›óú†®~.Î.…éş!Ca(ËG#ÿÓğ±<ç“N°}]xhPö³)‹3Ë&Q[:)÷ë¯‰Ü¡:¨æq3Q[‡ŠÓ]+–Aøke5Ën‚ìh”Yké›\r1xºÉK²è@‡\'«._®ë¾l¡YÙCÎğ…‘“\\_pC¨–ó…!ÜGt2‡èE¼6î0S¬eıÒ–îĞë…‡4d\nEpA¦wğ	‘Õ¬+áu®“™™*·ËÿË\ZbKH¥RÆÃ— Ä§ˆ7Ì5)¶‚\0|˜\"ƒN_¶a9¤n˜d°‚d ï}¿€2ğ¬ Ah‚Üà?ŠğAÄÆâ)B†ó86ÿLO·ÍêwÏkY ó (A\rtĞó.¾[ğ>=êQŸÒßI5\0<fã|§9¶Z	†¥[êÚHb3ƒÕ¯Ş¡¬÷‘ë\Z€{´>ŞÛÀ’Œc›Ï¾aœ)\0Ex>±DÜ¦Cë$DİşC¦ ¾÷]úC #ÜÁ\r+¸€ßşa\ngPy Û°ÓvJ>\"]ÀØòQL˜ôYEæiqN(@4 >à*€xSıß˜€\n	Fq¯ùAx³H]fØQhœğƒ9˜˜hJõüÁ]ŞH5€âüA [’Î4@	@AùÌDË©ÆİuJ¬@ßÿ_ú%¡ú¹AC4$á|@àõ]¬€AÄA:QÒ±lA·İ´U\r¼AxÌ\rJˆÍÜ¶!\"\0òÊk8qÁ°xAˆA‚\nm5D	`“ğÖøÖ\rQ€šJÕ6˜\"-]\Z†¹@¨\\ã¤Lğq”Çıá*¡\'jÀ\Zıx¢Ja˜_á5AP›ÇOeb0A	(\0<ßİ<\".ÚFp•ñ@İt\0NÙ§ôá8 ÆI<5„P‰Ip	Íÿ¡Py\r\0¨I\ZøÀ°,\\.NŸ™D€$Rãì—DŠÜÀ)’âú9„ á9Ba\\ÀàÿA†E\rG|_ƒ!VCˆfcªŸ¬•©	tÀT”ºD–‘Š	#iD–\râÆ\0”€ĞÌA|Éò•š1Ğ¼ÆL\'Å¡?â ı ÅIÄ7\n‡›¤È¼;¶cú\r’4AÀ$)~@ù­\0ğJ¢v9üYjDßH%4\0ö…`î5Ät@ì\0&$•1ÓøA©8$&	ÈÖ‰dnøÊğ	@‹Òidè€IuAWİq’#\Z¥®¬ø€°LR-Ç}ßÙ¤\'_(–AMêåMŞàÁPĞXìPj’DWºeú\n¬HÁàšà€t\0DMïyÿUZeCb%[0˜—ô£X9\0GJDø€\n”Àj–À]\Z\r\nHÔ\Z®4€å”W™lc‚Ì£É‡maŠh¦f@4D¬@\'çM\n4AHD hÀ…ôÙ4Q\0iîæsÍ¡Òã`ØĞ	€yÚ’ÓI–A„g‚æğ9ÁˆÈÈl$ÉEÍ„(Îø@ÙÌæ”€¨\0\Zrç|®ÔøœdjrÊsò]K9F(L¾cğ@(:Ätn—A8&\r‚â\"5D¤\0T”]A˜@\ndÕá|¬gU^¥{¢J³|ÄvZÉğ†}6D\Z¸€mş\r(ÿ@d:q1A\r¸\0“2éşdz	íè[ŞÛİTjLMŒŸ`¾ä9>atşÁ\\€…Úä;~@xUDÈA›ğEÿ=šQ©ôÁùT\0T\ZÓ\Z 	t@\n”\'ŒÈfZ”–ÕègŞ¨Ã0DCüÈP‹pÊàÁ€€¬Ì¸@n„É)$®‡(UöÁ4A„6§š–©Mâ$šnhC´Aü¤&1¨Ö\\ƒ\\Èˆ€À\0<„ÿäeš\0²’€-‰‘,e1*¢¦vH[^@lí•PÀ¶ºŠH5ßõqD¤v†í*aåL	\\_:¹ršÿ#)^€t‰Â*`\"0~”à›ªFDš+IrÊà¡y*«DĞA\n\0²–gŠV†TâŞAk´Â˜¢şÁÖí¨XQÀ°\\Êì]^™èà¶¼ÁŒ,ÉÎA@jR€d$ÀBb‡5Àø@R:Ä”¬£˜_^€Pè_Úk¬Âc„éL‡&‘l¶,y•èÀA\nˆÀŸvÀH„\ZˆÀÂ–§	,ë§ ÄNl[xÜ…jõ¼ÁæıX_¥m8@Œå”*í¹\Z•õaéÇ¹Á–\Zá)¾*ŠéÏ­M^4Á·øA¨ÊÈFcÂí÷\0¤‰ZæŸš€4¬C\\Õ^mHî ÂÿÄï]¢×~İÄÑİ`ìT@1ı³…É\Zº¡âÆµ$@ÌEÜ»ö¨éD¡ß\'NÊ\0­ÖQëêÒI\\ÈT®‹ë=Â^íyÆ2Í¨ÚT]çòGŒõ\'‰nRHã°„Tâïn–IÔÔIDØlà\"ç\nxiî^h°E‘÷2ÒµÆÖPîÕ&lÖFÄ,ïÂ¾(ŒHzNuíôöÄ›6DĞb÷Z+ò‘ğÆ¯ÒÎ7NEÜ‹CäÀr®¯^~À¬€šF„jT+·†ö6€ñ\"ë‹¤C\0ÿZ.ÿMİ}üApTÇÿA\Z”\0É<ÈV”\0éÜÿAœ0ÜÎT…ècEújp;Ö¤¾^AÔè¨RªHøéÕïÿ¶ğ/¬ÂÚÒ\0Üñà\Z(OGª|_l\0-¾-nª™À†p{ïÍ5\0fuÁtiA;±\'\n¾âáX×ñ	ìhñ\'«»ğ\"»(	kŒŠ`J‹pn\Z¯ÅDAŠìc\rh\'Yº!ıÀ¨–ÒÌ²!û£J5ÔÀW€‘ë¥àm¨ °Á>Å¿¦2”+}ñ‹@;ò#¿èŒˆ¼\r&g²Z€†à#AÔÀğUÛ&…l@ÅõKä².›kQÓì],Ÿãù}° ğÓff—,ÿÒ7§™øÀ@\n/r$ˆ0?²	tÀ OòñˆM03ï„@DÁ3SDLêê‘C’«ıT¼sgPÉ@—3úE¡8„\Z<íŸóˆ\\	öê²¯HÙ$²>3ïxøòÃh‘ñfÔ°àğ@£ œ„à#GèŠíãHKtQJ†oAßb4NNG\'ìãzô°.ˆ	uk0-Á¦ôıâ\0şZ5²Â´ÃÏB\ZêMóÇ@DA´ÁuòôÅ9Î&“Ù ²Tç\"Å8E)Fgô§	{´\n£p0ò[·ÅøF´ôãj5äº´>‹ÀüÙp K Ég„µğ^uŠ­ÿXTO!vpï_oI7ÄĞu]_@8Ä<+òV÷óC˜4~ö!;Dñ¶aÓ¶aï3´Î<ïÓ´@Kv[ô…p¿	M^…Á¶â\0§¸jçnğÀ°äA¿t‹¢mH\"·T¨eC¤3bß6xc¦÷Á\Z86š1\ZwäD®²¬vƒ*$ø)\'Fólî\0Ô2ï-÷AHÀ„‚.#7S<ŸÖ3xøÂBm_—U|¤„³ş¡zKÎßâ{l‰óQ¯o¬@˜Ú©‹ÀÔ6aM>7’X¯7¿³wşAS\"8‹oqÂ*vë·c‹\rMD¸9O—„]…ïj)HÈ\0ikÿğî­ŠwÀ?­äÎ†€iGÄ5À>\Z6‘A~w‹×vAÂ€ñ ÷oÛ8rÄØ§î¸œªYïğwç®ùm(‹’@a¯sÔ`xLš¶ĞAÚ½u–f©È/Úö•#øŸŠ\0	ÁİÁúá2ƒ9œ8™%W¨M¤9Ğ&µCÔòd_óÀQãdMÌœxr›I+ÏìÀ\ZøŸ_¹:{Pr¨M+zÃ°7®8ú£ûc‘>ŸQ;ñ]styÚ6?;„p6qäÀ·Æèrœ•À$a§º³ïµà€N„1Â:Ëlr·½`­ï¦À4Ä¬*Ğ>7j¿¨mß3,gxúñ,ÑÎÿ7A9\\wS	(qİ}ËŠ?»½G-	\0Á–ÿA[ûµoÁŸ1–l»[nIC»s¯€k‹Úó´ÿA {n¸¾şˆ»Kßºi³©ßAğ€ÁÖÚ;É—§ ³6¿¹¿ÇwÜ&Á«²Ãı_6÷½†\0‘÷é\"¿(k\'`>áT$]‰º*[œUœ½”A„€(uCè|ÉG=_ã‡®|¬|İé€ÄÄ|.¦§”Á’Ãjnh½ß/ò\"gØ<^@mùHÆKœÜÊ¬íAlé;²ı°ˆ G½ß_îF©¼Õ“Æ“(¦×€Ä,0×/mlÕ¼½ZzCx —;0ÆÿQP:Lr°SØE>™QÎ”FEœªEÃßù}@(¸Ÿû}ªŸı@øà¯€d¢ßÛL‘&µĞúâÖ×‡½…6¹ÒdõKKîh«=:j@˜øÀ—è~º•øt£Ùİ–’;Š{C°(ëû½yN{@Ç¾G¥Šáw\nsÅ^æ$ ÕmZK¼qÜïşô,÷«–é†/<=?²›?uä¥½nøô@ÜaBáÀ€	&T¸aC‡!2Dp€\0JéÒçOGwÎ4‘\"Ã‡\'Qš<Ó1)LÄ”9“fM›7qæŒÙáIG=N,:”hQ£G‘&Uº”iS§O¡F5:Äÿ‰>=öIÅ‡Š\r\Z„+6‚%\\É’æÍ2>64¨x0b]»wñæÕ»—/^E:ºù eaÃ‡MºéègG˜5I\\ñ$ÃaË—OfÈ £Ö?x ¨K€n_Ó)Îu\0ÂG—;;ŞùbdÅÍ&-g0âñ‰¿¯™‚¸ˆy:n\"•ysçÏ¡GoŠ‰“0u`{Ü3çMwïŞçàá˜ıV(%<€àt{÷ïáÇÿx0fÌ¦ğî`â±ÌvğèÜì+ğ$Û>Èa%ÏÒ(\"®¹âãë\0Šj\0£\"Èx#»=ÜĞ‚‡4¸Í>ÂâèˆHNÅm\"„:ÿ5:jc¨!† NºyìÑGçtÜ¢\r=ü ÏÈ#±êã(\\ˆ #ŒRÊ)ß›ï3> ĞÀ“.Â#1\\ì/&gìh*ÛrKÛ4A‹=<ÓªôR#=*\'ªó Õj`\"\rñ`Sò‘BQ-1» 	ØÅG…#Aßp8â	/Ô@î?ê£\r3ÂØ(ê‚úÑÔSQíQG+Ìh£\"‘\\Œ=êø4Œ0äĞ#;<ºĞÁ‚Ñ&¤RØa‰eˆ\0:*£$5¹\\Î?ZJÑ¦ğ¨‰eÖ>ÍV˜¢DØæèuƒä\Z`®	ƒ½+Ï	+j×\"(\0A(È˜ÃH¦È¡¶Óÿ¶²oé0AZH	NR`8Â:ŠŒõ>ä0c‹%r,5Õ‹1ÎX)R©sÂŠ-Â0Cä‘G¾u+Få8\n!uÍŒ9G³¨Ø™i†ÏJe³ÅìĞs²4ƒ$m‰æ–-^ËÎ-&ºÚÀĞuWê©ÑM@â­¡&º˜c<òâôCÛ2è—h’vë¨Ñ‚Ù¦iR®ĞÔáìê0Ã\n5Î[o½IÅÑï¿ı&Õ($r\\‚Õ:\ZöìĞ2èÎš!µ èèŒC™Í`Ñ®ØOL™\"›ì‚³Ï&{… ´PÌH<æHƒŒ,˜®AöÙiŸ]&¸&#9îøš<´0b$²1\'ıÀ\0ÿøó¶Y”4/äö(3´hÂˆ&°Ç~Š/Üx+>êØï½É/ßü¥×«`Ã#_ (]Éé—|Â,:ÒbÙ-3Èa<5Âd0x¤f3¶ÈV™áqğŞÜ ¨=Ü¡S0‚¾X¼¢D7Œró …\"„]!qØCHô\"Í¬…!A¼õÀ?Èabã;ß\rqX>YgHÙéÃ[@³ÒÔˆÃ:H:2…ıˆD‹‰ÁÀj\"1t¤20à‰F6“ÈÀS(ƒzA#))n¨`>t Íh‹…1	À–ÂàˆtÀÊ¦ -ˆx.ô–ghH¸’y#•ÿ¶`†–Á&\rLPAzÌUDIN‰\0\rHCGš&>^@ŒòMş $j²Ì\nC°ğàzZøÂÜKYÎR–_øÂ¬ÇƒÈ`$Yã¡rVJ”(Ê“rdQ\nd$™Lae¦}Èt+´X!±™MUUr(¡Gæğ>Èoˆ“4\'_à\0{ı!£3P—<âÒE4úÃš)L}’í(æ²Pš$K*ìç\ZŸ©OÃ4ñ\'‚¢1ÓØ3+ğg?€­ ,È#mˆÂ5µR‘6‡:ËQŸØç^Á¥ä<çKıB<”\'îÜÖ\n’v¢NÖ²©B:ÿP¡5¡@MTÚşğŸA4¢8°cGâ@Rb´2<àè¦Ì°”«]eÊ!ÃĞÃì¤Áè!€¹Ê	Sµ&„\0 àHz™t²#@X*MRdO¡MÕ¨}õ«B5 è“LıM€¸2ä³¯\n‚ê:\"+Î«•µlRpd83 t¬Lj\\Z×úRè 6Œ½<r…ÂÚDÕÂd\\ÿ\Z[Ù\Z/<R\rÑÄzd\n¶™meš4¬Úğ²Å½,á¨sÒoÆ¦>°\0ÔæÚI\"ÀJgAB3€S¡\'/yªĞŠ:[ñ×0øK` ‚8æV&\"ØmGŒ ™ñjà+8¯Gä\0ãÿî×¸‚C¤XãÔ 8 ] •nÍ‚¿?èšœôˆ]qò×ş!¾äµğ…-ó¤V½ì½I(ßğµlF.Ä·Uş®˜«‚L$¬`ó†\"¨ !Ííç\0\rıáZö¹€ÿ:â…<Ô?)únm0œdÏu18 ²‡ù#Q¹¶äÍ.5;Ê²˜Ë^Íl6›R¬„³Ï%q,¥lÀ#<àkJB\0Çß„Z¡²’ñ<_gùÉP®§«laÍ4ákf n—­MÂ™THbÖÎËâ\"¿Ç¥YÍ50’åšµí´E)øîĞòê¿sS;X-”Û«Ì?L[¢¦o„+­&šÿÖ-.•¨9Ì)¦t”(˜mÉ`<^è³LêüZQ/Û¨ÿê\0mX	{ÄºWÌóf +ëåÔšÛ\"äáİ‘9@¡H\0Zı¬³	Á´\r5£•“?ÿ¼Ì¶·0/`ÛM£z&ÈÄ¦ğiíPW†£²Vq·ÃE#A}m÷gš‚\'İ8İè,G¬x™ um9A¬µŞ|o‘3+KÔ$ìz\rë›§î!!¸îû‡:è7á5/dI—ğßE&©‘viÅï2\0ÀüÁ\r˜6Lv¿m”é3FùÓ3GlÎİÕÏPõµFœä’ÄÜà6÷úÍƒd†\\ÇiLhRjĞt¨5`ÇJÿÌôb\Z“dŠrÕ.‡úİ£¹&¿„ß;éi²–øô,qıĞ_7¼ù	fÎ¶¯eÖ¤Õ~î‹w¤å¹‘úäùxáÒvÇûçßØ0õ?$€ÁSñéùek&æmØòáaxR5üá?,BSy¨ rôµ?0X— \\`0Ğƒ?Ÿ|¹z©®Ñ6&LDÀù¸Yù$	¾G{í+<Gˆ»ßÀ„ÔøÌ\'@’ÆêÜ0ÿ\"¾	¬NE4UŸş49wûşñü©şŞÙ…¬r¶/\0Ïç¬à:Ì(@æòÖêX:ï0JéäÍÓ’…êïó6ãYT‹ôTNØø¯ÿ´ëa¢`Ûÿ°ÉGG\Znç²‚t ˆ|m­@\0Ï2H­gkÂ¨ï«oãšìÔ<Œ\'Ú,Ğ0PßdnÖLğ÷fÑt<^Æ¬\"	¦FË#rÀÚ²ëY2oé˜N0²NCm0DQc¹0Hí\\	Õ0	¹i³–«ÆÍ¢ü$)×i@ƒÉ %½ŞOp aî¬1P¥ãø¦È„2®Ë´l\r!Ño}v¥W~å\\êGè¤¯6c<:\'¢”)õ5­%æÌÃæäF‘$§#øà£\"15¬\0Ì3Ş¢Ê…âhf\0(àY¾ ğ®ëà\rEŞÏ»öoïN%DÏsKRÿ±ŞV±Æ£\nO­ÑGËcÄn¹ş`+ÎÎ fæˆäQkÓŞEş®Š>—,Š	ÕB	ĞØÑ7ç²ï\Zñ1UÍpp…ï@œvo=¨„\0,-‰`+1LD`€-gçQĞJÿü,ëƒ³„£\005òTü+Æˆ¬JlF¦ êºƒÕ†!\0q’¼>@uŒ­ïD`\0Q¹0}‚æ6²\'d¬€Š¸Çw<¢AÉÆp/‚6còÙR\'ş£\'LD^²ÿ€ì€àÙËEÑ\rLò%Q\rÎ\'Í’¤‚²#Ú)š dHÜ² Ì$­/&„	xëÍÿÆÂ4ÏJò*e‹ÔèÀ%q€ÔÒ%é/bN|Î²1¥C:âü©2^È-wå-\"í‚\"H«!­-Hî:À0ÿ€p0-!1O%™*úJò/«/»’†xÒ1m*†Àp0åt´ ˆ²<dÌ¬Dò!@>Â´\n£ƒÌ1¢¨r¯Psõ.O/QÑô4%“¢m:Šo³;¿*\nTÑ2`tr`\nº\';GÏMb\0@`¦L¨ò#ÎÍù@‡/1;nsL°*³şc1¹Ó;\rtp\02‰.,ÊŸ4¬	¾à-!N6`â†ˆ\0JàYöÀ4åª·28ÜK\nÿOS?eËùÌ>Ù†»êa³şds\'Ëò@½s<rúìN…R	u¾Å3ö@ÀÒ£\" ¾&ç3é¢ò7Dà9”D1LBĞ=¬&&õs,Í @eTF«ƒ}\n(¼\nªªÌSBwí‘Tàk2)˜à)%ƒã%ì	ù˜4ÉÒ4b€5)ş|Oà®ò\"ãn²´O…\"7©H>Õ$šd 	Î@¸Æl<ú€Ê‚éPT—4s Eß£4€®\0è)°î*µMÆCbÔO“á\Z¦KÏF…4,¾\0Qµ£)MÛê>,t|R+uƒ#izƒß¤”¦TñÃ#QTetÚ )gr`GÿïIP3L·‹ïÒ¦\0Oo5¶ú§ßy’ô5oé`”X”áØKlõºF§	¼ç”Ó2ÚD?¶5&ŒcJõZ7(XŠ\'²M­5:­KÅÕ1‘@71©^}‰˜µ·TïRÑE£ÌµâÀ*í•¼‹sèT¬ÓFíÕ6äC5`5²:ZfÚ•ê+æèÍÍ¼tÏşà\n¤Uó\\óXtbõ,iÜÕBô$öZ³µF>dóq¶€€,pD²k\nH,jÄÎĞÉæH½ŠHiV¶2€ùäÔaÙÆNËà\\GQÿ€Oö6‘àX•¸E]±Â\rx D5TL°@Š®¦V¼ÿ4@Ó2õ©Õ`WÑIŸTÃVl—`dµA5 Ş+ö 	~oKòÍƒTä?Á²_ë¶”TÓeÑø4vjñõ22pSh“óÍLb£|,ŠrËK1·ëbgÂ+\r²reËS;ÂÔà1gI–k»6AõgA7ÕJ˜õšÀ¢T\'[VV»ëôÔrDe÷^Ó&ŞpÖµ´°rÏpX÷,Ç–€äk˜Êª·lhksü\08\rIÿ.w£÷¯ôR»RúDwG‘¡ê`{v	°#4èVà‹Ê ¬Ï”ÜöÙ\\¯¨KÚ×¯.—y!jŞ°Svõ.9|×°:öwÁ²$%L‚¾T—‰ÿÜ±®¸8±·¸¯tÒ>4fs+7ôf¨‚-X\rEV@~AJIOä˜4…è÷„\r#N%ÒÃÄ9Ô±}\'økex†áÉºZmŸBsU„HS€˜ÄğÖExu·®}4i®”‰Í’*ImqƒP’1x&º•èœ.‹±HO“ªÛ¦µ„í‡‘QõÀÉx#©BAó§qMI¨	~¥²¤O6Xs8í*^Ûæ?Íu{Ösö­1(60ĞäƒoêYp‹Eì)‚¹j‹%æT’³V™~ñ„ª\r–X“M(iqÍĞVkÒW*FS(ã”sxsÔàH¡,fİ‰ÿéê~g™–0ÓÖ‡ÓØ@è‘ES”DQ˜çø¾„Œ•fŞÜî„ÓÔÀÖ™7’:Â@ÌÎ`R§ù>.O©¼ş¨,ùV›ıWuz#!‚ëù*_ô´×œCv¢@ËãØAù‡?ŸhE$Eú¶V³˜vÿÀi55jûù%³DuîQ {2(¶@İ€PnÃİ®ïò¶Š™.œµù€4 ó`ˆ‰xS+ˆ3€šdÍ£Ïr9)NF:|	¡pÃÇã°vL öy[zƒ>\0oIOy0z¢5™uÚ,;æ:ÅŒĞˆ—®+°$ müà‰âÙX w©/Z[ö¨ÙÆ),£SS…Xh®ÿçº‘Õd‚¢™«ºƒ¤\r¸±&È\rl)xŞ–­åõï²­IÇfí¸mÜº¦M€Ée È0;³5;v©—\n*°&;7Ê‘6õz¯k¹:¶ W h”WDr—T±Õ¤Mà„Iï±áZ˜NI¾T)š@Î î Œb°ï ¦—„gj¡8¨ı 6MÛª;d†„{ğQ´ö¶å¸jM„0ûÎ¶™m<K‚‹¼ˆUÅèHÊhP€Z¼%|a1ºÅGªÃcBæS¬ÂD¸Ø¡M µà¬©™¨jb/@Óà¶¶iZ»IÇtŒ[‡ <Â%ü\rÂ#V8ÄC@DFºáÛ6ÿ9çZf›\'J1¿TƒTi—d`ÅY|Å{IœI¢ï­§î©ó\n¾|PCÄ\rUB‰ûet@@à+Èe,Œ¼àÅ,T@|€^¼æwÎàz8\"¯;\\TE·vXŞ4—>8(“•¼Œö È¼ÌÍœ¸Û‚x`$XHÆ®€Û¯±›!9ÕÂThŒà‚›«Àº\n]}Ğ£J\ZÀT@ÌHú¸´«Ü\Zµ»\r[`ş°<°¸@JbØ’{Ìû¼M(Í?„¾r\\ÉªpSĞ×ÆWzÔÓš_·xÿ \\ZğGÑ%)ù\"O€tèL6rúÑ³´†WsŸ×7#Ï…ÛÓ‡$ÿš€íš½FÊ9?K´2òœ(ËÆÎí	‰å\0Ö-`HÏ;¢£yİ@‘ -cuŸ!Ö@¸Û‡îÀŒªÇ8Û²å]ŞY©·Ë\0¸ƒ“ŠÜ |ÉÍÅk‚‹yÒFk‹V¡Î¶xAÃçŠsf¬Ä\rÆ3?wÜ½	j”z›\'ßIÓÊ_’;?´A>°JÓ»èPÉc¤Ñä¢èÏ6èJ`(ùµõ‰¾V ¸lQjlBh½XÀÆƒ$\0È¨\\âÉ]A­Y*_D~\r¤MÊ $x)£øÅV£Ÿü·<MG8—«ÏD“ª—«ªvÖàC 	˜µÎCÀQ’&¢÷Ê`À#ËÑ‰†ÿ·#vµyHà	\ZÆ„™(°Ä[ÆmÃAO\'Ba#7°’ÏIU\'’ŸZ`lRœ0+ğ€	,à¬Ò.r0ÔLr€šÄ=îvhëÊë#şR §¯Ëı½¼pÏÏ@†·™T]˜t2œLŸÛzË©•7ãªú€	Èïò%§’.©ZSŞÛóm3x£E’ab4ŸÊ„‚ õ-·l\nµxû€™b•ËÑ¡_!ŸÊ”™zt¦àkÌ¾ò¿~¨×#ù\rTAÅÀQ±Ç\rÌZúõi<C °,6‚\0”—,m\0B\r‰&\n\Z<ˆ0¡Â…\nIxùó§Ì‡!*Z¼ˆ1cE\r‚Ä±ÿ˜<‰2¥Ê•,[ºDIÀÌY‚3§Î<{úü	4¨Ğ¡D‹\Z=Š4©Ò¥L›.Aâ„Ä\'&$!â	M-!2hü\n6¬Ø±\Z3\\ÈÀãËwBh 7.ÙS za5¯Ş¼\"@lKQ.Æ+¾Ğ|ã\"Á€/;~ü’@šV8½Œ9³æÍœ;{¾ŒÄ\nÈ$öš!â\nÍ&Ş\n~\r{ì‡Ö2ÿq31¶î¸e¦–6\r|o\n;@òğ\n;CÇûi@€1äéÔ(²åÏÜ»{ÿ><h3ó¤ømõ¼‰‡÷¼;¾|å<š¬€/?¿Å+˜A\\€\rDDSÿ \'˜Wu”ÆHÒUa„*!ĞÀ™±x\ZnÈa‡.…„©a€\n—‚\Z Å!Ãú½¸›r®Á¨}@ƒ‰îhPì¹~d] ƒ4ù @I.Édè\0‘NIe•VŠ§ÇTxñe‚Š¹±‚4If™a} Cy0èÈã\"\0RbŠÅQkAÔ`tMî!ÌôÇ7]Ih¡†\nTh~@DUVá%H`Îif¥–Ò¨A#ª×f§%¦Pà_Ì(–WZĞÄt|®ZLö‡vˆÎJk­Fy92„b¤ÑÕ¥Â«_MØåi²§©öÇ2Pš‘‹gÿ€„‡$±Šít¸\0‘Qdhk¸â‹Ùäı!›&¨çåÀBKl¼ò–zêW¨«¬i\"à°èS¸aÓBD†‹e‹°c8`aMƒ’qÄ\'º„ˆÜÅ	>J\Zæ¼ğÀTåÛ©ìİÑñWÈÀD%pÌ/!@\0ÕñÔÄ:ï1TRıñÄ–Wu Õ—)ƒŒ4ÒùÇB“, qıj‘A^Ã4ıÑ…“2½Ò\0 t[ÏfŸ=+Qt»ÃÓÍÖ\n\0\'M·Ç\Z¬ µ@¸\rµi™ÜŞÑ}pwŞR\r0ƒÍ¸IŒ¥qáÃhON¹‡HäúG0ˆ@Bÿ\0ÑïÏÖMú¼+¯•|÷-Ü¾ıJ4˜7Bd„Lã’ƒM€Q:nåÂÏçª\"B\"ÀGs<Ì]zô—k²¾c\n0û‡ÈÍfH}qÖ³A\0»ƒ}\0³o<ñòÏïTˆvñ]Z»M\\`µôşÓxğı%ÂŞ¸ª$îá_WlÓèn}	S_ \"+úipƒJ©ÃT:Ğ¹qïUûŸ	Ëtc¡‹ø2 U>’3d@²kOæ¶²ÙeáALj\0>\0ƒD,âOÔö³#€Ğ ÃÓ—ÜrÂ(Âh6õƒZèB†ÀÍ@üÃn8˜ÿı¡ì!¶­ı!ñ3¢7ˆ„-t)Èy˜÷—ÑIqò™HoìuÅ,è<\"h×ŠZ”‘»©1\r\nà¡WE3(@Do¬dãX=N•H¾úCàÅÇPÂå-L™ I„\n\",‚—±@â‚Å=[ÛêÖ-‰KùoD\0ê€_ BÂş‰r˜¥\ns”xJ7ñ$S\0¼H‰\'UÍ’U`Xär‰ÍáµÁ.èY^óş‡`sœp!Œ\ZÂd†ÍÑB˜&OxJ ³³Ö4YE3›ıgÙìçä<´ß°ĞW}x9:–ÓıÁ8X¢:äË~yQ9Á\n.zQğÿ`\n³›ƒyÏ%@d“œ?O\n±¨@ä¥ÑŞ/ÿ@5„Ê, gæbàĞ‡çM«üCî‡ Ş¡³£I\rÊR	 B×D©SÇ¥¶~ÑE¡ŠÃ}fŠÕÁhêÖãœNÉ¹+|skdeK@šÔê€\0’ÜgÎ\nWZar¡×Ûq@™Urf@€tHŞWƒsàà\n=%+ÄĞ/ô¥µI×IÙâ\nYC]®zkl¿‚™×Ìf`Aéú«€şÆ9á	¤--p0_İ!w‹]R5f“ÈÊöJ»D×yHà«=.³2¥\"DÄ€EÏæ……Ä-.q-‘\"@}­íS[ù9ÛèŞj›ÿÓ˜ÿÀ\ZŞæÕ·Ë©p¡X_ÍµÍ…ĞHÛFéª·;ö³WHà¥p’J»¥È¯àİï’L{ËüƒZ^Ç,UZ¯¿c€>¡/ı$}³J‘åW¿$ã$Dp‡Ö\0¿ä\0ú„î?Ü¨dé{Õróà™ò‡iJL\'…IF€D±\ZÖvúXë3Q](®{œc•?şYİ‹•åˆÌ¡®ñJĞ[“îxÊI‚h2‡/X˜B¥dp§ÿ{oê—déä\r ­”L/•ß”!È]EûC»,Ó/ßˆ¡Á³€Xè%8Í*©åB£ e8+ú\'¸ªÿ^¨ÊPB<#T6õÃ®üÜ7vş!–„nÒ‘0$zÑ¤Ö‰¹ÈzgI-åq1¦“¥8Ìkn	c.ø9 ÁÍ¥^tmc¨.«z¬Îœ«_í)—‚„ŒµvÉ\0 ô>|«×ÔÖ	¨{×aOš¯ÈÆ¨BÒl—`³ccµ«½k‹=QØÚâTH¢nƒ—E‹à ›­>ÈÕ„×çŞq‚iâ½vôİ#’wßRùM&Ü»Ù5û]¿{ıï¿\\Uàãü@½¼`ğL3wïôÆø=œÔ–±¿ğJñZ\"÷Ê8Éë_4‡û$Õ$0¿Gnà’·G)æÊ«ëò|aÅKi õÌÿO¢¾çŞçêE\"DÊìs`-:¬ÙÙtœĞ?ìÖ´™NeEAÄ R\'¦Èúlu½”&T;lr§c(Qƒ=ìçBÊËî¿³§]YYi¶îdÆƒKŸ;d#®èá¥DûŞ­’Ê~ı÷è(¡Y¢ôVÃx®Á|/Ås:şñcÏ\ZIù“ˆ\r$\"×üæ©$ĞS¤!}§†S—¾Æ‹²ë¬Rì~^ö\'½í;•?ˆ@!}©O\0Í`EÉß¯·Ñ>õ<ñCiüã·i‹«İ½†ŸÄ¯K¶í•»¯O74İ©öÚçÑòúuÔæ;Éï+üøs9„àïıRT¿Gßÿ~1P!q\0n×iúf÷7[Ô§[wÇJfÿ€<âKÍ±Ş`py %\nY×ö+(ö€ÿã|6„Gş…åu\07öQàq%b±#hB%HG\'è&¾rzxj£ƒ¹4WÎâ€6è18€:ØD qÍÇ°ÒB8„–‚·q~Hø1J¨ƒ¨ôm pòG\0¨·T…ıä#v ZH‚h‚] ©oÀdÍ×X‡F…gxIWö9…l(/\\‡ÀÑ„1vhMk”‡zÈAµ…2ˆÄ\"ˆƒh\ZkLàƒ5V€¸¦kŒˆMíõ‰Ã2‰”¸røs`ÿtG7\0¾ãVˆKN×=G8Š¤è†9hŠúâD}ğ„©7\0$mV\0‹–Ä€<W‹éw‹K‡©*4Fy~¢Fa0Œ•t…@2|Ç˜„É˜‹À8uHy®2x±2n„x‘†ISŠÛøB¿Ä‹+ØZcx3åhD²H>éX7ëÈ’‚ö”z\0Œ~@ED}}€Hø¨Ú¸é1€\\S€ÿè>ÁNàÉI9U{P„¸o¸/T4İ—z¢o;A=ĞD`DÀ/I=À’¹S	’8’¼â:¡uÍ‡\0ú”Nà(P0¹F‰6\0=À”E©‘Qÿ•Iğ©•Sy”€Ui“B!‹5¨“[¨=9 )’\\ğ¸X†Æ’+ÙHIN@/`Ké”=`5Ù”w‰.Y“6 “V‰•KĞ€¹‚™€‰”MÉ—ase×X–ÂÂë§Œƒ¨=ÌBàxtãv#@#€•/à•=P©‰Nà”©	«¹\"š¢Išz©š?A—ğ¨i›0ğš»	©)£™š6 ‘Ù$•‰4Ù‡–Ñ A™˜ğ”ĞK`šµÙ=ğ°—¯I(\0uY‡Y(p•#p—êÙ›:±’J¹›½‰•DšÈyÿ”Oé›WÙƒÉœ@a1° #Óy0\0ØÉ‚pAíÙ«ùã™š#œv¹Qp•6 #`”iÀ¹D ›~¹”å)šÉ›¨	•\Z¦¹À9¢^Y¢# ”ú#Ö¢˜ 4² š^Ú\\–Çµi¡¢é+ù’N™•å—IY£\"p¢RJµi¥8¡—*ÊŸ½ÙKP¡N`šNœ[Ú´é”°É£<ÑcvF‹A*¤Œ’™”HÌòÎ—zçÕ6J¢Şi¢­)œ«ÉŸ©™”‡Ú¦i¢:A•WÉ¡,	¨p¢Â)œNšœËù¦O9ÎB™tZ)CJ¤ñ&È–I»}p˜4™¥K—/\0“€i—‹ù°J‘6€oY«;ê/é’0Éª.éªvÉªq©¢›jjç,@\nªùáw:ˆŠÓ§³œšú•¡•¹ØŠ¬ÖF]$Ô¬ó¢w£zğu‰Ìwt·ö­gqÙ5®ñÒxæ:4ìÁH§\ZRPíj6qšjñ*,ÄRôšŸ³({ğw¾èüÊ3r¶ss\n°»&qàšD°ëÒ)ÈlÏ¸ë±²!+²®\0!ù\0€\0,\Z\0\0\0‰\0\0ÿ\0	H° Áƒ*\\È°¡Ã…/^yH±¢Å‹3j$X!\0€&nI²¤É>ªÀÆÉ—0c¾¬°re€2sêÜÉP@Í•DòJTæÏšŠ*]JòhÍL£J}èt¥\0¡S³j˜²*È­`³BğªjØ³JGıx­[=Ö~}KWf×µfëê-©Vn\0¬{_$âQ.Áˆ1•\0EâÇã2ş¹òÂÅr“ZŞlP2ã¼œCc^k ´i@å:>Í™&ãÒ¬7fÀelË(hk¾]™c¼G¾@Q¡8Î³åNÑ„»*¬Ö™[5ó‡D \r¹stÕ\n×Úÿ(LV€m™D|’^áxÚ\0ØË´±vDû„Ùá4p¦ëªËİWoú}@9i”€õU JhRäÙ$!ƒÉ=‚0uğ\ZôŸ†@F\0`_ˆ©GbMÒ½Dı±HĞ‹U1`¢F\0ØˆĞ8V£:dUÜÓˆGş$\0hJ–Äd“?%%IŞQé$”Wb¤ –?É×å@6¼0B}	fM vID.¦¸#Aq®ùİ•#TáBv¾†æ}6¨ù‡õ9¡/õĞAT€ÂœDu §“\nJ[„3ı@æÔƒ O!D„¥´hR–*™—Ö¤U!J‘¤¾ÿÖ©CSÂø\'LD ê•«Á\Z«_Ó]”š&\nªS\\\näë¯†Íš®Éjy	-ËìZCR4êk9yøàŠY»–\0DÔÁ±/ZÙƒkñJ(ê·ÛAÕÑ&¦²èj¨ªCñ’UìFj¨£BµVBĞyoBu\"	\nÓ®µ°ˆğÍk»`bÊ\'cÿfT0¶Á&ôñQà\"„±–”|co¯¾·RL[²äÀ…bëìC64,ëC6×Ôq¯¤îk\\4_”§†-/ôJ\r%L¥»ış¤²FRy5CYCÍP¾GâL´S$]Q~¶tØ1Dßµm4Bœ@`vEï¥óBl÷ÿxÙµî¸³E\'Ã·7ßğA¸JÇÔµÃõ­Pz‹ïWßG\rıuâ¢b®åN/DüJğqå^Ëä-°E”=T]9zË5xšğ&P¡şSLSÙ¸C˜À€ç†¶nÒÒ`Ş­ò¾;Õ4Ö}ª½vôM†¬Ú}¶eôAB0BDU ;Y”‘z¬Ê?>‰üKÄ¹ºIyíôÏ/><A=@à³ĞXÒ_EŞ§Ÿ\08¯ Î™ÖÖ,ò´5-w¿\nJF^PxD\0v#É°®e½†e·S\nôâ\' ï&ùŸ–ö—q‘†K§Ó«n¥vûIÚ§\n„¿ªƒt›ÿˆÃAÑ°O\Z«ËÉvÀ\0wCb¥b³²]du“šzè–/\0š[åÒ¾‚¬‰…n¡Pª  =ÅÈ+!\\‰\rëR¦&V¤ˆmD?2Ç+á0}‰£Ç„¸Käƒ@$äA¸\'^0‘Š<HŞü\"¡÷A2’ô‚OûŞ—GLÊŒ1%_=9óĞã\")	B9–\rä„Sô¤‘Å•¡q•53$ €0â23DW¢»_bG…5Î0Uâ.cºGˆ*Á2?r8g^ì5cÄ–5-ò¸l’¥ŒÛÜiôsËp¾ÊN4\'ÂÖäKuísî¤2Îx>k…öš–ª™æOLÓ+ë\'CşÙ*ñ®ü$É½Ç õ±Œ:Y\"ÙÈbÇÓø¢11e˜bh5½Mzí|Háî4?èXÑF#%fE’Ré±2\"!½MOê±•“Aõ’‹\0FËà‘“³Ò?¥“H$Â \nŠeú½(¦Š£¦j€yÒ& ‘ô&öT¢Jmk«™[%/IÕU\"©¬+*‹´ê»›*i¬kª§\'áJ¥Z“®A²ë6_`U;éÕœ½»Ÿ@1‚=t¥¿ìŸa­2ÊxÏ	›ÏP°‚UıfİÎ4Ø“|	%e@\0\0!ùK\0€\0,#\0U\0	Ò\0\0ÿ\0	H° Áƒ*\\È°¡Ã‡#JœèÈ2B°A±£Ç CŠI²äÂ\n\0¨\\	\0“0cÊœI³fG\"Xê €£ÍŸ@ƒ\nÚ0çN|]Ê´©S#5ğò©Õ«X­\nz”AÖ¯`ÃÊìÁU*\n±hÓª…µìNkãÊ[ÁíÑ\nsóêıZ×.Òª{jÔ/K¼ƒ+–	ÁğßÅ#ƒìëx%bÉ˜37¤\\@\0ÀšC‹Ø¶³ÊË£Sc~azågÕ°%·¶»¶â­³_ÛŞ­—Ál•x—Ûá·ÊÃ“£µa¼¥òç`S\ZG½úSßÆƒ[ß¾´ôoêÜÃÿÿ$Û\\»øó4GÏ>&çÙæÛËÉ¼9\0ğóówT_¿ŠÅÙ‡ß.D^y&È³\r¨àƒ¤Ù\0jV(qTèÔ`”KÀ@~Õ˜}#h8Ô\'ú•TZ\Zg€Š@Ñ¢c/¢uãz4Òdn­¥(köuĞãLŞÍ&dXšæÕ‘0XäBiÒ2ißV„¥}ñaUŸqvIQ’ªDX_Vö¤™¡¦Tkfck3ÂIQÎ‰”RWµé˜5é§J9^EDŸ¦*‘”‡Jµ¤UïuæhDŒFº“‘Šf:è¥É©©[a6Ué§ 2$è¨+¹d@Zÿš*C±²J\'hL¡éW³&t§­tÊT­~•Ú+Aº»S¢¹š&ì±­ª¬k“.e(Ğ&Dì´’nX—Ù\ZD·Ôµ:U®@¢’[,®Aé–º¾ĞAø\0/DÒºËRD­j\0¸	Ñv;¹:ºşêdÀ³ã\0ØC¿=MÔpeÌÕ¢T\\/ÂvìĞ˜»xÖP= o…=lX™%›2Wô®ëĞÅ¹í›P¿7ëd®Î¾]VÎ1tÂD7Ä³}o6´´i&7M(ÍùÊSf@‰D£°­qÔn×8–mËšŠœĞ©h»ÀÊ:cÍªÛÉ·aHƒÿ*¶­`4öŞnÑ|)Û¶fèáıõš5°C$/ãU\rçƒ\ZxAR^™åP¡7«‘d´çËª­a\nzÒ¨ç†w…/(ígÇµ»_}h£¿¸+4îî¿Nàº»îsB“oZéòÙ`ûí½#d³ó».Ïİş*,ÜØ{­½x»+ÀìM¾TÏ‡¸»Æ?”ùltğÂK/Œ0:ğªW÷8¹_É¡Î‡˜M­z2±be\0Š=sÀC`CšgŸş¡ yÓâTP~ÇHĞ$ìŞø r609M}£‚I~´«ş™äÜ\" IÀ×\Z\n\'KcÀéƒÁtı¤v\r‹_Gÿö×\Z¸<‡A{İHlĞÃ|\"tW\0a‚BRE‰7{˜L`¥$™°®a*ìˆŸè¶µ&&Î†9İQĞémì‰ù•qvhn,\0èë;#\0:6$Šş‚£Dö8#¦±k‚\\HF† /e‰ŒHçŒµ+Î[‘<!M³¹†¸1e•tÏœ„¸I¦¬‘ŠL*ÈuR$‹äJBD0‚D]œÈ#ƒÊ˜ÄR*~4.³Lƒ¨±,ºiã/•ÉˆøÉ#4L™i¥Ê+ÓV³É0©v3úK‹qœÓ+	òÅ¸e3$›tRG¢´à•2M¤	×Ì‡˜r6ñ<È¢bçÿÎŸI¨¼&·VIv:Fƒ©%Úæ‘e²äœåô\\16§s\Zäzô|ˆCÃî ÙèC%ÂÄõIp£Óleì,*ÆŠF¤¤ë\0\Z€ÍaÑy\0É6MÃR@¼À›]ˆB‘Ù¿¡2¤I\'O+bĞØõ£e!ãAÚµ²ÅOçÁ%×·áu±cørZ’¦\Z¡âÒjLç¥c‚3!û\\k«`bTÇ² =€¡\\¥’G£¹¬!u]©IDª(…C„Eİ]r±&‰¨hT*öÈ*’½Îªû±¬ĞJ¢Ù¸!5\"’•–„‚Äî„í,Úx5“Àâ©ûò¨ŸXû±ÿª¶a´	Pí\nNªÔ>ùdˆYoËªÜÂd¸v™XOïI¶P•¸s2®IâÚœÑNP­\\HL]<ı„¹»\"ÑG²[™àj«»73ïG¶ªld$ä5LM?‚Ş›Õóª^«\0jß]yd§õeÕ8G2¸U\0×å—Ÿ2Zì5_øÂÈné\'” \0ÂùzÁ€½4§dF¤¿@Ç\"ö6N87ÍÒ÷:ë½…Ì3RûÅŸ\\*‘çNmŠyqš¤k›û7¤r€TAkÚû.¹eQ/ &<µëQ¤Vk‘¹aÄô­\"Ñ±i”,\ZêªØ“ëã²CÔj\ZÊBÇÇva)š“c‰Ùÿ1˜U\r’±5fâ\0­4ysáĞ“X	ÎYŠUÉúYÕpí7QFˆug§X„}R®|ş¼“xrw¶¦	^ aÿ6Ñ¹´„\Zm5úÄ	aK½Î4uro¤f5HL\\–È­ÙV«–µGn-w-Öºv®„†^na9Ø ñòoÊFéj\"»$Åöš¢oÖgVBxµathm˜ğz\'½Ô¶]ğÜí‘ÖALÆP›Ë½³aä›	f·C}œ‚¸«ĞòşcºCo›&\0FÎ÷Iì£8ø›cë8D¸Øœ%¸ˆ	W8	íÃ«fgOâ?™ò\Zbq¹e\ZãPÜ7GAï²Ää\0ÿjâÄ=!”Ÿ«á€÷b]î£êÆ|TC¦ùL­ìë|ƒ\"¸aNşó‚ÊHS3/úLz¾´€+}Ş°÷ÓÃéÙ©¿m·zD:¦¤k=&÷¾iÔNëu#ˆN–ÛµnM²\\Ün˜±WH`lÿ	İı’óT¸%R¯ÉŞå¦³h#ªïV|Yz9+r,ÔÙnşÛ’Ô³¢ßÇcRòìèìÔò%÷P¼z³¬k(ì¹JçÃãT…ı4ÓÔòN£4Q^(0Í¢Õfkú2NmóV¢õ»jrÁ©‰Z¢qìòÛæ¾°¬æºÄ*°|‚”ïP€(¾øXmùQ8$¥e•ú@Ş·øê÷ˆô¬²îD,\"|í$üR6«\0³ú›V(¨ú™ßJBGq ƒ/÷a r[&<òÆtÆWE·zr¥t×€ŒrÒ·VN×4×€\\ñMózkewºö|ĞE{È»Óz:‡‚¨Ó{ÖÆ‚Gõu€“åÏöS\rh‚ù&:õu|2˜}í‡:\"ˆqXƒ€]3„(×|Øƒ„4—WFhK?¸~z¶;hu,ò„¬Ò>Q~\Z7-N¶…à·@]30`86Ğô×^UX†\na/6-Å†D6@ï%‡VQvb\0AØ@#\0|xø›f/VˆÖiH\0!ù\0€\0,\Z\0\0!\0ÿ\0	H° Áƒ*\\È°¡Ã…K\">œH±¢Å‹3jÜÈ±£Æˆ %zI²¤É“(SbÉR¥Ë—0cÊŒÉ²æÌ›8sêÜ)°¦M@ƒ\n:ÑgK¢H“*åiôèÒ§P£’lêTªÕ«X!R™µ«×®[C~KViX±eÓªÍy–ëÚ·p]¶·®]séŞİË×aŞ%}Nøw°aÃ^Ì·0ãÇuCüÖ1åËc%cŞ6/çÏW5ƒ½Ô2éÓD=£^ğgJÓ¬Oo•;76êÚ¯ÛÚ6™¸·ïßÀƒN¼¸ñãÈ“+_Î¼¹óçĞ£KŸN½ºõëØ³kßÎ½»÷ïàÃ‹ÿO¾¼ùóèÓ«_Ï¾½û÷ğãËŸ/z7VúÆíßÇ_\\¿UşÇù€\nhùˆ‚È)8ƒF(á„Vhá…f¨á†vèá‡ †(âˆ$–hâ‰(¦¨âŠ,¶èâ‹0Æ(ãŒ4Öhã8æ¨ã<öèã@)äDiä‘H&©ä’L6éä“PF)å”TViå•Xf©å–\\véå—`†)æ˜d–iæ™h¦©æšl¶éæ›pÆ)çœtÖiçxæ©ç|öéçŸ€*è „Îé`CQÊ”Š.úd£6	©V’NJX¥–^ºd¦\naÊéA…†*ê¨¤–jê©¨¦ªêª¬¶êê«°Æÿ*ë¬´Öjë­¸æªë®¼öêë¯À+ì°Äkì±È&«ì²Ì6ëì³ĞF+í´ÔVkíµØf«í¶Üvëí·à†+î¸ä–kî¹è¦«îºì¶ëî»ğÆ+ï¼ôÖkï½øæ«ï¾üöëï¿\0,ğÀlğÁ\'¬ğÂ7ìğÃG,ñÄWlñ˜€¤;Ğ¹•ª¸š~Û)¸”v)·ˆzúéÆ*¯œ1“.Ô²Ë3¯sÌoŠ3A:ïÌr’>ó¬dĞ?ItÑFİĞJ3İ4’J/mtÔN\ruÔ/£œ²Ö\'gûÉ~m[”Ø_“¶Ù]c[6Ú%«½¶×o³ˆ5^.ÎMw‹voôbŞÕSÍ÷Jxÿ\r¸Ü‚_xá8E„/^Qãbä‰ONùã(^^¹‰šoNbç‹zè Nº‡¦ŸÎaêªgÈºá¨¿û†²gD{í}ˆûc°í¾Q@\0;','89009');
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `userName` varchar(255) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `currentCity` varchar(255) DEFAULT NULL,
  `dateOfBirth` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fileNumber` varchar(255) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `isExchangeStudent` tinyint(1) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `passportNumber` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('89001','Trabajo muy duro, como un esclavo...','CABA','1987-01-01 00:00:00','juan@gmail.com','89001',0,0,'Perez','Juan','Argentina',NULL,'94b8cea57c49a3007225a0c70c475450','1100000001'),('89002','Lo esencial es invisible a los ojos','Lanus','1992-02-02 00:00:00','laura@gmail.com','89002',1,0,'Gonzalez','Laura','Argentina',NULL,'94b8cea57c49a3007225a0c70c475450','1100000002'),('89004','workaholic 2.0','','1994-05-10 00:00:00','matias@gmail.com','89004',0,0,'Perez Garcia','Matias','',NULL,'94b8cea57c49a3007225a0c70c475450',''),('89005','Hoy empieza Tinelli ;)','Olivos',NULL,'julita@gmail.com','89005',1,0,'Lema','Julia','',NULL,'94b8cea57c49a3007225a0c70c475450',''),('89006',NULL,NULL,NULL,'lucas@gmail.com','89006',NULL,0,'Ramirez','Lucas',NULL,NULL,'94b8cea57c49a3007225a0c70c475450',NULL),('89008','no se que poner...','','1986-04-15 00:00:00','carito@gmaik.com','89008',1,0,'Perez','Caro','',NULL,'94b8cea57c49a3007225a0c70c475450',''),('89009','ah ja','Lanus','1990-05-11 00:00:00','juancho@gmail.com','89009',0,0,'Gomez','Juancho','Argentina',NULL,'94b8cea57c49a3007225a0c70c475450','1176459842'),('I89003','Vamos Racing!','Carhue','1993-03-03 00:00:00','leopoldo@gmail.con',NULL,0,1,'Biandratti','Leopoldo','Argentina','89003','94b8cea57c49a3007225a0c70c475450','1100000003'),('I89007','Too much information in my head to understand...','Caba',NULL,'jim@arnet.com',NULL,0,1,'Stevenson','Jim','EEUU','89007','94b8cea57c49a3007225a0c70c475450','');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_career`
--

DROP TABLE IF EXISTS `student_career`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_career` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l0mlhaib8q178tfxxy6t2edo2` (`code`,`userName`),
  KEY `FK_q9ym3wpq6g08cejonq1lagk0g` (`code`),
  KEY `FK_cpowc8ym6xfrx2mu0nys55tih` (`userName`),
  CONSTRAINT `FK_cpowc8ym6xfrx2mu0nys55tih` FOREIGN KEY (`userName`) REFERENCES `student` (`userName`),
  CONSTRAINT `FK_q9ym3wpq6g08cejonq1lagk0g` FOREIGN KEY (`code`) REFERENCES `career` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_career`
--

LOCK TABLES `student_career` WRITE;
/*!40000 ALTER TABLE `student_career` DISABLE KEYS */;
INSERT INTO `student_career` VALUES (8,2,'89006'),(17,2,'89008'),(15,3,'89008'),(3,4,'89002'),(12,4,'89009'),(14,6,NULL),(7,6,'89005'),(9,6,'89006'),(11,7,NULL),(1,7,'89001'),(16,7,'89008'),(13,7,'89009'),(5,8,'I89003'),(2,10,'89001'),(6,10,'89004'),(4,11,'89002'),(10,11,'I89007');
/*!40000 ALTER TABLE `student_career` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_session`
--

DROP TABLE IF EXISTS `student_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_session` (
  `userName` varchar(255) NOT NULL,
  `creationDate` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_session`
--

LOCK TABLES `student_session` WRITE;
/*!40000 ALTER TABLE `student_session` DISABLE KEYS */;
INSERT INTO `student_session` VALUES ('89001','2015-05-12 21:54:28','7b7b2603-df18-447a-a4e1-b174639b812e'),('89002','2015-05-12 21:49:40','a14d9f05-10c9-44d0-ba9b-e7aae571d0a9'),('89004','2015-05-11 17:51:57','bc414ae3-1cc5-475f-969b-d48176066c31'),('89005','2015-05-11 17:55:39','3291098e-59e0-4a20-b0ac-9863b7f5a59c'),('89006','2015-05-11 17:57:14','d00b0fd4-477b-43fb-b450-18accfc7f189'),('89008','2015-05-11 17:43:31','feb68115-25ff-4dda-9726-2e8e016a3ea6'),('89009','2015-05-11 17:44:37','39f7c305-a4b5-4a61-bdd0-6374fd109c4f'),('I89003','2015-05-12 21:52:14','16cc36e5-b354-491a-bec1-dd7f21972569'),('I89007','2015-05-12 21:53:09','8a2f1c5d-6b03-4ee6-9e0b-c55a37bd810a');
/*!40000 ALTER TABLE `student_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study_group`
--

DROP TABLE IF EXISTS `study_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `study_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ownerUserName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4y4j9gikg3lspot964327ws7j` (`ownerUserName`),
  CONSTRAINT `FK_4y4j9gikg3lspot964327ws7j` FOREIGN KEY (`ownerUserName`) REFERENCES `student` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study_group`
--

LOCK TABLES `study_group` WRITE;
/*!40000 ALTER TABLE `study_group` DISABLE KEYS */;
INSERT INTO `study_group` VALUES (1,'2015-05-11 13:34:47','','Estudiantes de electrÃ³nica','89001'),(2,'2015-05-11 13:35:07','','Clases de apoyo AnÃ¡lisis 2','89001'),(3,'2015-05-11 13:35:23','','Proba es lo mas','89001'),(4,'2015-05-11 13:35:36','','Bolsa de trabajo','89001'),(5,'2015-05-11 13:36:08','','Apoyo en FÃ­sica 2','89001'),(6,'2015-05-11 13:36:30','','Estudiantes de InformÃ¡tica','89001'),(7,'2015-05-11 13:36:57','','Laboratorio FÃ­sica 2','89001'),(8,'2015-05-11 13:37:09','','Feria de apuntes','89001'),(9,'2015-05-11 13:37:27','','Apuntes Proba','89001');
/*!40000 ALTER TABLE `study_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `careerCode` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `credits` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,4,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89002'),(2,4,'62.01',8,'FÃ­sica I A','89002'),(3,4,'70.02',6,'GeometrÃ­a Descriptiva','89002'),(4,4,'75.01',4,'ComputaciÃ³n','89002'),(5,4,'61.15',8,'MatemÃ¡tica Aplicada a la Agrimensura','89002'),(6,4,'62.03',8,'FÃ­sica II A','89002'),(7,4,'70.15',6,'CartografÃ­a','89002'),(8,4,'71.02',4,'Agrimensura Legal I','89002'),(9,4,'61.06',4,'Probabilidad y EstadÃ­stica A','89002'),(10,4,'70.04',4,'TopogrÃ¡fico','89002'),(11,4,'70.08',6,'TopografÃ­a I','89002'),(12,4,'70.31',6,'InformaciÃ³n Rural','89002'),(13,4,'71.24',4,'Agrimensura Legal II','89002'),(14,4,'70.07',6,'CÃ¡lculo de CompensaciÃ³n','89002'),(15,4,'70.09',6,'TopografÃ­a II','89002'),(16,4,'70.12',6,'Geodesia I','89002'),(17,4,'70.18',6,'Catastro y Valuaciones','89002'),(18,4,'70.11',16,'TopografÃ­a III (anual)','89002'),(19,4,'70.13',6,'Geodesia II','89002'),(20,4,'70.14',8,'FotogrametrÃ­a I','89002'),(21,4,'70.19',10,'Levantamiento y PrÃ¡ctica Profesional (anual)','89002'),(22,4,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89009'),(23,4,'62.01',8,'FÃ­sica I A','89009'),(24,4,'70.02',6,'GeometrÃ­a Descriptiva','89009'),(25,4,'75.01',4,'ComputaciÃ³n','89009'),(26,4,'61.15',8,'MatemÃ¡tica Aplicada a la Agrimensura','89009'),(27,4,'62.03',8,'FÃ­sica II A','89009'),(28,4,'70.15',6,'CartografÃ­a','89009'),(29,4,'71.02',4,'Agrimensura Legal I','89009'),(30,4,'61.06',4,'Probabilidad y EstadÃ­stica A','89009'),(31,4,'70.04',4,'TopogrÃ¡fico','89009'),(32,4,'70.08',6,'TopografÃ­a I','89009'),(33,4,'70.31',6,'InformaciÃ³n Rural','89009'),(34,4,'71.24',4,'Agrimensura Legal II','89009'),(35,4,'70.07',6,'CÃ¡lculo de CompensaciÃ³n','89009'),(36,4,'70.09',6,'TopografÃ­a II','89009'),(37,4,'70.12',6,'Geodesia I','89009'),(38,4,'70.18',6,'Catastro y Valuaciones','89009'),(39,4,'70.11',16,'TopografÃ­a III (anual)','89009'),(40,4,'70.13',6,'Geodesia II','89009'),(41,4,'70.14',8,'FotogrametrÃ­a I','89009'),(42,4,'70.19',10,'Levantamiento y PrÃ¡ctica Profesional (anual)','89009'),(43,6,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89005'),(44,6,'62.01',8,'FÃ­sica I A','89005'),(45,6,'67.03',4,'Medios de RepresentaciÃ³n C','89005'),(46,6,'75.01',4,'ComputaciÃ³n','89005'),(47,6,'61.08',8,'Algebra II','89005'),(48,6,'62.04',6,'FÃ­sica II B','89005'),(49,6,'63.01',6,'QuÃ­mica','89005'),(50,6,'61.06',4,'Probabilidad y EstadÃ­stica A','89005'),(51,6,'61.10',6,'AnÃ¡lisis MatemÃ¡tico III A','89005'),(52,6,'65.01',8,'Electrotecnia','89005'),(53,6,'67.10',6,'Calor y TermodinÃ¡mica','89005'),(54,6,'62.05',10,'FÃ­sica III','89005'),(55,6,'62.08',6,'Electromagnetismo A','89005'),(56,6,'64.05',6,'EstÃ¡tica y Resistencia de Materiales B','89005'),(57,6,'65.36',6,'Medidas ElÃ©ctricas (Res. 7063/96)','89005'),(58,6,'65.09',6,'TeorÃ­a de Circuitos','89005'),(59,6,'66.04',8,'ElectrÃ³nica I','89005'),(60,6,'75.12',6,'AnÃ¡lisis NumÃ©rico I','89005'),(61,6,'65.10',6,'TeorÃ­a de Campos','89005'),(62,6,'65.11',6,'TecnologÃ­a de Materiales I','89005'),(63,6,'67.36',6,'MecÃ¡nica Aplicada','89005'),(64,6,'65.12',4,'TecnologÃ­a de Materiales II','89005'),(65,6,'65.13',8,'TeorÃ­a de MÃ¡quinas ElÃ©ctricas I','89005'),(66,6,'67.37',6,'MÃ¡quinas EnergÃ©ticas','89005'),(67,6,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89006'),(68,6,'62.01',8,'FÃ­sica I A','89006'),(69,6,'67.03',4,'Medios de RepresentaciÃ³n C','89006'),(70,6,'75.01',4,'ComputaciÃ³n','89006'),(71,6,'61.08',8,'Algebra II','89006'),(72,6,'62.04',6,'FÃ­sica II B','89006'),(73,6,'63.01',6,'QuÃ­mica','89006'),(74,6,'61.06',4,'Probabilidad y EstadÃ­stica A','89006'),(75,6,'61.10',6,'AnÃ¡lisis MatemÃ¡tico III A','89006'),(76,6,'65.01',8,'Electrotecnia','89006'),(77,6,'67.10',6,'Calor y TermodinÃ¡mica','89006'),(78,6,'62.05',10,'FÃ­sica III','89006'),(79,6,'62.08',6,'Electromagnetismo A','89006'),(80,6,'64.05',6,'EstÃ¡tica y Resistencia de Materiales B','89006'),(81,6,'65.36',6,'Medidas ElÃ©ctricas (Res. 7063/96)','89006'),(82,6,'65.09',6,'TeorÃ­a de Circuitos','89006'),(83,6,'66.04',8,'ElectrÃ³nica I','89006'),(84,6,'75.12',6,'AnÃ¡lisis NumÃ©rico I','89006'),(85,6,'65.10',6,'TeorÃ­a de Campos','89006'),(86,6,'65.11',6,'TecnologÃ­a de Materiales I','89006'),(87,6,'67.36',6,'MecÃ¡nica Aplicada','89006'),(88,6,'65.12',4,'TecnologÃ­a de Materiales II','89006'),(89,6,'65.13',8,'TeorÃ­a de MÃ¡quinas ElÃ©ctricas I','89006'),(90,6,'67.37',6,'MÃ¡quinas EnergÃ©ticas','89006'),(91,7,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89001'),(92,7,'62.01',8,'FÃ­sica I A','89001'),(93,7,'61.08',8,'Ãlgebra II','89001'),(94,7,'61.10',6,'AnÃ¡lisis MatemÃ¡tico III A','89001'),(95,7,'62.03',8,'FÃ­sica II A','89001'),(96,7,'63.01',6,'QuÃ­mica','89001'),(97,7,'75.02',8,'Algoritmos y ProgramaciÃ³n I','89001'),(98,7,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89001'),(99,7,'62.01',8,'FÃ­sica I A','89001'),(100,7,'67.03',4,'Medios de RepresentaciÃ³n C','89001'),(101,7,'75.01',4,'ComputaciÃ³n','89001'),(102,7,'66.02',6,'Laborotorio','89001'),(103,7,'66.06',10,'AnÃ¡lisis de Circtuitos','89001'),(104,7,'66.07',8,'SeÃ±ales y Sistemas','89001'),(105,7,'66.08',8,'Circuitos ElectrÃ³nicos I','89001'),(106,7,'66.09',6,'Laboratorio de Microcomputadoras','89001'),(107,7,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89008'),(108,7,'62.01',8,'FÃ­sica I A','89008'),(109,7,'61.08',8,'Ãlgebra II','89008'),(110,7,'61.10',6,'AnÃ¡lisis MatemÃ¡tico III A','89008'),(111,7,'62.03',8,'FÃ­sica II A','89008'),(112,7,'63.01',6,'QuÃ­mica','89008'),(113,7,'75.02',8,'Algoritmos y ProgramaciÃ³n I','89008'),(114,7,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89008'),(115,7,'62.01',8,'FÃ­sica I A','89008'),(116,7,'67.03',4,'Medios de RepresentaciÃ³n C','89008'),(117,7,'75.01',4,'ComputaciÃ³n','89008'),(118,7,'66.02',6,'Laborotorio','89008'),(119,7,'66.06',10,'AnÃ¡lisis de Circtuitos','89008'),(120,7,'66.07',8,'SeÃ±ales y Sistemas','89008'),(121,7,'66.08',8,'Circuitos ElectrÃ³nicos I','89008'),(122,7,'66.09',6,'Laboratorio de Microcomputadoras','89008'),(123,7,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89009'),(124,7,'62.01',8,'FÃ­sica I A','89009'),(125,7,'61.08',8,'Ãlgebra II','89009'),(126,7,'61.10',6,'AnÃ¡lisis MatemÃ¡tico III A','89009'),(127,7,'62.03',8,'FÃ­sica II A','89009'),(128,7,'63.01',6,'QuÃ­mica','89009'),(129,7,'75.02',8,'Algoritmos y ProgramaciÃ³n I','89009'),(130,7,'61.03',8,'AnÃ¡lisis MatemÃ¡tico II A','89009'),(131,7,'62.01',8,'FÃ­sica I A','89009'),(132,7,'67.03',4,'Medios de RepresentaciÃ³n C','89009'),(133,7,'75.01',4,'ComputaciÃ³n','89009'),(134,7,'66.02',6,'Laborotorio','89009'),(135,7,'66.06',10,'AnÃ¡lisis de Circtuitos','89009'),(136,7,'66.07',8,'SeÃ±ales y Sistemas','89009'),(137,7,'66.08',8,'Circuitos ElectrÃ³nicos I','89009'),(138,7,'66.09',6,'Laboratorio de Microcomputadoras','89009');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-12 21:59:03
