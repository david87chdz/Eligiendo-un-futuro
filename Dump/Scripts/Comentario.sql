-- eligiendounfuturo.comentario definition

CREATE TABLE `comentario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int DEFAULT NULL,
  `colegio_id` int DEFAULT NULL,
  `contenido` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `activo` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_4B91E702DB38439E` (`usuario_id`),
  KEY `IDX_4B91E7027FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_4B91E7027FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`),
  CONSTRAINT `FK_4B91E702DB38439E` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;