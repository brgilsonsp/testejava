CREATE DATABASE `rastreabilidade` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
CREATE TABLE `calltrack` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ucidOrigin` varchar(255) DEFAULT NULL,
  `callidOrigin` varchar(255) DEFAULT NULL,
  `ucidTrack` varchar(255) DEFAULT NULL,
  `callidTrack` varchar(255) DEFAULT NULL,
  `dataChamadaTrack` varchar(255) DEFAULT NULL,
  `tipoChamadaTrack` varchar(255) DEFAULT NULL,
  `isRecordedECH` tinyint(1) DEFAULT NULL,  
  `dateSaved` varchar(255) DEFAULT NULL,
  `dateTracked` varchar(255) DEFAULT NULL,
  `observation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ucidOrigin` (`ucidOrigin`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

alter table calltrack add observation varchar(255);
alter table calltrack add dateSaved varchar(255);
alter table calltrack add dateTracked varchar(255);
