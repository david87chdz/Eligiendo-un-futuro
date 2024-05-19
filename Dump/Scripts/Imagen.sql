-- eligiendounfuturo.imagen definition
CREATE TABLE `imagen` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `colegio_id` int DEFAULT NULL,
  `url` mediumblob,
  `descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `activo` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `imagen_UN` (`colegio_id`,`descripcion`),
  KEY `IDX_8319D2B37FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_8319D2B37FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;