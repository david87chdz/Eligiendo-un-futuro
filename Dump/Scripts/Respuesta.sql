-- eligiendounfuturo.respuesta definition

CREATE TABLE `respuesta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comentario_id` int DEFAULT NULL,
  `contenido` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `colegio_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_6C6EC5EEF3F2D7EC` (`comentario_id`),
  KEY `IDX_6C6EC5EE7FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_6C6EC5EE7FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_6C6EC5EEF3F2D7EC` FOREIGN KEY (`comentario_id`) REFERENCES `comentario` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;