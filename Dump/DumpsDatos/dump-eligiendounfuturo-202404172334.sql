-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: eligiendounfuturo
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actividad`
--

DROP TABLE IF EXISTS `actividad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actividad` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actividad`
--

LOCK TABLES `actividad` WRITE;
/*!40000 ALTER TABLE `actividad` DISABLE KEYS */;
INSERT INTO `actividad` VALUES (1,'Karate','Clases de karate','Extraescolar'),(2,'Programación','Java','Extraescolar'),(3,'Robótica','Siemens','Extraescolar');
/*!40000 ALTER TABLE `actividad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colegio`
--

DROP TABLE IF EXISTS `colegio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colegio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `provincia` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `localidad` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `denominacion` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `naturaleza` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `comedor` tinyint(1) NOT NULL,
  `concierto` tinyint(1) NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `web` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `localizacion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_AA00D876DB38439E` (`usuario_id`),
  CONSTRAINT `FK_AA00D876DB38439E` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colegio`
--

LOCK TABLES `colegio` WRITE;
/*!40000 ALTER TABLE `colegio` DISABLE KEYS */;
INSERT INTO `colegio` VALUES (1,4,'HÉROES DE LA INDEPENDENCIA','PALENCIA','TORQUEMADA','COLEGIO DE EDUCACION INFANTIL Y PRIMARIA','PÚBLICO',0,0,'34002563@educa.jcyl.es','http://ceipheroesdelaindependencia.centros.educa.jcyl.es/sitio/',NULL,'AVENIDA ALBERTO ACITORES 36','979100154','42.03165, -4.31973'),(2,1,'AVE MARÍA','PALENCIA','PALENCIA','COLEGIO DE EDUCACION INFANTIL Y PRIMARIA','PÚBLICO',1,0,'34001728@educa.jcyl.es','http://ceipavemaria.centros.educa.jcyl.es',NULL,'PASEO DEL OTERO 15','979742956',NULL),(3,2,'PAN Y GUINDAS','PALENCIA','PALENCIA','COLEGIO DE EDUCACION INFANTIL Y PRIMARIA','PÚBLICO',1,0,'34003257@educa.jcyl.es','http://ceippanyguindas.centros.educa.jcyl.es',NULL,'AVENIDA BUENOS AIRES 2','979726230',NULL),(4,3,'CIUDAD DE BUENOS AIRES','PALENCIA','PALENCIA','COLEGIO DE EDUCACION INFANTIL Y PRIMARIA','PÚBLICO',1,0,'34003026@educa.jcyl.es','http://ceipciudaddebuenosaires.centros.educa.jcyl.es',NULL,'CALLE PADILLA','979720563',NULL),(5,17,'RAIMUNDO DE BLAS SAZ','VALLADOLID','ARROYO DE LA ENCOMIENDA','COLEGIO DE EDUCACION INFANTIL Y PRIMARIA','PÚBLICO',1,0,'47005826@educa.jcyl.es','http://ceipraimundodeblassaz.centros.educa.jcyl.es',NULL,'CALLE/ ALMENDRERA 17  47195','983408223','-4.77572, 41.62636');
/*!40000 ALTER TABLE `colegio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comentario`
--

DROP TABLE IF EXISTS `comentario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int DEFAULT NULL,
  `colegio_id` int DEFAULT NULL,
  `contenido` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_4B91E702DB38439E` (`usuario_id`),
  KEY `IDX_4B91E7027FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_4B91E7027FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`),
  CONSTRAINT `FK_4B91E702DB38439E` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentario`
--

LOCK TABLES `comentario` WRITE;
/*!40000 ALTER TABLE `comentario` DISABLE KEYS */;
INSERT INTO `comentario` VALUES (1,5,1,'Aqui estudie yo','2024-04-02 15:43:00'),(2,5,2,'Aqui estudie yo','2024-04-02 15:43:00'),(3,5,4,'Genial','2024-04-07 12:38:19'),(4,4,4,'guay','2024-04-08 23:00:23'),(5,4,2,'Hola caracola','2024-04-09 22:47:55');
/*!40000 ALTER TABLE `comentario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctrine_migration_versions`
--

DROP TABLE IF EXISTS `doctrine_migration_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctrine_migration_versions` (
  `version` varchar(191) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int DEFAULT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctrine_migration_versions`
--

LOCK TABLES `doctrine_migration_versions` WRITE;
/*!40000 ALTER TABLE `doctrine_migration_versions` DISABLE KEYS */;
INSERT INTO `doctrine_migration_versions` VALUES ('DoctrineMigrations\\Version20240325134915','2024-03-25 14:49:33',968),('DoctrineMigrations\\Version20240325142428','2024-03-25 15:24:35',105),('DoctrineMigrations\\Version20240327155721','2024-03-27 16:57:36',425);
/*!40000 ALTER TABLE `doctrine_migration_versions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagen`
--

DROP TABLE IF EXISTS `imagen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imagen` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `colegio_id` int DEFAULT NULL,
  `url` mediumblob,
  `descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_8319D2B37FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_8319D2B37FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagen`
--

LOCK TABLES `imagen` WRITE;
/*!40000 ALTER TABLE `imagen` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messenger_messages`
--

DROP TABLE IF EXISTS `messenger_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messenger_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `headers` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `available_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `delivered_at` datetime DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)',
  PRIMARY KEY (`id`),
  KEY `IDX_75EA56E0FB7336F0` (`queue_name`),
  KEY `IDX_75EA56E0E3BD61CE` (`available_at`),
  KEY `IDX_75EA56E016BA31DB` (`delivered_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messenger_messages`
--

LOCK TABLES `messenger_messages` WRITE;
/*!40000 ALTER TABLE `messenger_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messenger_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oferta`
--

DROP TABLE IF EXISTS `oferta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oferta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actividad_id` int NOT NULL,
  `colegio_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_7479C8F26014FACA` (`actividad_id`),
  KEY `IDX_7479C8F27FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_7479C8F26014FACA` FOREIGN KEY (`actividad_id`) REFERENCES `actividad` (`id`),
  CONSTRAINT `FK_7479C8F27FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oferta`
--

LOCK TABLES `oferta` WRITE;
/*!40000 ALTER TABLE `oferta` DISABLE KEYS */;
INSERT INTO `oferta` VALUES (1,1,1),(2,1,4),(3,2,1),(4,3,3);
/*!40000 ALTER TABLE `oferta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respuesta`
--

DROP TABLE IF EXISTS `respuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `respuesta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comentario_id` int DEFAULT NULL,
  `contenido` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `colegio_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_6C6EC5EEF3F2D7EC` (`comentario_id`),
  KEY `IDX_6C6EC5EE7FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_6C6EC5EE7FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`),
  CONSTRAINT `FK_6C6EC5EEF3F2D7EC` FOREIGN KEY (`comentario_id`) REFERENCES `comentario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `respuesta`
--

LOCK TABLES `respuesta` WRITE;
/*!40000 ALTER TABLE `respuesta` DISABLE KEYS */;
INSERT INTO `respuesta` VALUES (1,2,'Yo también','2024-04-02',1);
/*!40000 ALTER TABLE `respuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'School'),(4,'Admin'),(5,'User');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rol_id` int NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellidos` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuario_UN` (`email`),
  KEY `IDX_2265B05D4BAB96C` (`rol_id`),
  CONSTRAINT `FK_2265B05D4BAB96C` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,'AVE MARIA',NULL,'34001728@educa.jcyl.es','$2a$10$yBNrcZsi2FGjlwwhB5nK.u9znVMLh/EsePFv.1AYRS/MKKa7hD/RS'),(2,1,'PAN Y GUINDAS',NULL,'34003257@educa.jcyl.es','$2a$10$yBNrcZsi2FGjlwwhB5nK.u9znVMLh/EsePFv.1AYRS/MKKa7hD/RS'),(3,1,'CIUDAD DE BUENOS AIRES',NULL,'34003026@educa.jcyl.es','$2a$10$yBNrcZsi2FGjlwwhB5nK.u9znVMLh/EsePFv.1AYRS/MKKa7hD/RS'),(4,1,'HÉROES DE LA INDEPENDENCIA',NULL,'34002563@educa.jcyl.es','$2a$10$yBNrcZsi2FGjlwwhB5nK.u9znVMLh/EsePFv.1AYRS/MKKa7hD/RS'),(5,4,'Luis','luis','eligiendounfuturo@gmail.com','$2a$10$yBNrcZsi2FGjlwwhB5nK.u9znVMLh/EsePFv.1AYRS/MKKa7hD/RS'),(6,5,'pruebas',NULL,'pruebas@pruebas.com','pruebas'),(8,5,'david','david','david','$2a$10$yBNrcZsi2FGjlwwhB5nK.u9znVMLh/EsePFv.1AYRS/MKKa7hD/RS'),(13,5,'david','','david87chdz@gmail.com','$2a$10$yBNrcZsi2FGjlwwhB5nK.u9znVMLh/EsePFv.1AYRS/MKKa7hD/RS'),(14,1,'mara','mara','mara','$2a$10$hFNiZC9bm74.ju31e3yx9.27N7Zft8WOpT0vJY/37EnEpbNB05Gdu'),(16,1,'mara','mara','maras','$2a$10$x1K4kKGz/RVwTmanp8kjFe/ksuPHK./3B/ksC1O1f/PFT.o36e2jq'),(17,1,'mara','mara','47005826@educa.jcyl.es','$2a$10$GUgaoDJl2dGxKmu8Iho0v..kQ1gQLcpvRb7iSs7NbKxYXFFz7uFHq');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valoracion`
--

DROP TABLE IF EXISTS `valoracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `valoracion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `usuario_id` int DEFAULT NULL,
  `colegio_id` int DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `puntuacion` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_6D3DE0F4DB38439E` (`usuario_id`),
  KEY `IDX_6D3DE0F47FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_6D3DE0F47FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`),
  CONSTRAINT `FK_6D3DE0F4DB38439E` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valoracion`
--

LOCK TABLES `valoracion` WRITE;
/*!40000 ALTER TABLE `valoracion` DISABLE KEYS */;
INSERT INTO `valoracion` VALUES (2,5,1,'2024-04-03 16:07:00.000000',5),(3,5,1,'2024-04-03 16:07:00.000000',5),(4,NULL,NULL,NULL,4),(5,NULL,NULL,NULL,2),(6,4,4,NULL,4),(7,4,4,NULL,5),(8,4,4,NULL,1),(9,4,4,NULL,2),(10,4,4,NULL,5),(11,4,4,NULL,3),(12,4,4,NULL,2);
/*!40000 ALTER TABLE `valoracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'eligiendounfuturo'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-17 23:34:39
