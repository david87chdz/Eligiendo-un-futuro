-- eligiendounfuturo.valoracion definition

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;