-- eligiendounfuturo.oferta definition

CREATE TABLE `oferta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actividad_id` int NOT NULL,
  `colegio_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `oferta_UN` (`actividad_id`,`colegio_id`),
  KEY `IDX_7479C8F26014FACA` (`actividad_id`),
  KEY `IDX_7479C8F27FDC9E6F` (`colegio_id`),
  CONSTRAINT `FK_7479C8F26014FACA` FOREIGN KEY (`actividad_id`) REFERENCES `actividad` (`id`),
  CONSTRAINT `FK_7479C8F27FDC9E6F` FOREIGN KEY (`colegio_id`) REFERENCES `colegio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;