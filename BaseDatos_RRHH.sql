-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rrhh_institucion
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `funcionarios`
--
CREATE DATABASE IF NOT EXISTS rrhh_institucion CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE rrhh_institucion;
DROP TABLE IF EXISTS `funcionarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionarios` (
  `id_funcionario` int NOT NULL AUTO_INCREMENT,
  `tipo_identificacion` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `numero_identificacion` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL,
  `nombres` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidos` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `estado_civil` varchar(30) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `sexo` char(1) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `direccion` varchar(200) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `telefono` varchar(20) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  PRIMARY KEY (`id_funcionario`),
  UNIQUE KEY `numero_identificacion` (`numero_identificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionarios`
--

LOCK TABLES `funcionarios` WRITE;
/*!40000 ALTER TABLE `funcionarios` DISABLE KEYS */;
INSERT INTO `funcionarios` VALUES (1,'CC','1020304050','Juan Carlos','Pérez Gómez','Casado','M','Calle 10 # 20-30','3001234567','1985-04-12'),(2,'CC','1020304051','María Fernanda','López Ríos','Soltera','F','Carrera 45 # 12-08','3109876543','1990-09-23'),(3,'CE','E-1234567','Andrés','Martínez Silva','Unión libre','M','Diagonal 78 # 5-14','3205551234','1988-01-30'),(5,'CC','1020304052','Laurita','Restrepo Mejía','Viudo/a','F','Transversal 3 # 8-24','3157778899','1992-11-05');
/*!40000 ALTER TABLE `funcionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo_familiar`
--

DROP TABLE IF EXISTS `grupo_familiar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupo_familiar` (
  `id_grupo_familiar` int NOT NULL AUTO_INCREMENT,
  `id_funcionario` int NOT NULL,
  `nombres` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidos` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `rol` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  PRIMARY KEY (`id_grupo_familiar`),
  KEY `fk_gf_funcionario` (`id_funcionario`),
  CONSTRAINT `fk_gf_funcionario` FOREIGN KEY (`id_funcionario`) REFERENCES `funcionarios` (`id_funcionario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo_familiar`
--

LOCK TABLES `grupo_familiar` WRITE;
/*!40000 ALTER TABLE `grupo_familiar` DISABLE KEYS */;
INSERT INTO `grupo_familiar` VALUES (1,1,'Ana María','Pérez López','Esposa','1987-06-15'),(2,1,'Samuel','Pérez López','Hijo','2015-03-22'),(3,2,'Carmen','Ríos de López','Madre','1960-10-09'),(4,3,'Sofía','Martínez Rojas','Hija','2018-07-30'),(6,5,'Diego','Restrepo Mejía','Hermano','1995-02-11');
/*!40000 ALTER TABLE `grupo_familiar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_academica`
--

DROP TABLE IF EXISTS `info_academica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info_academica` (
  `id_academica` int NOT NULL AUTO_INCREMENT,
  `id_funcionario` int NOT NULL,
  `universidad` varchar(150) COLLATE utf8mb4_spanish_ci NOT NULL,
  `nivel_estudio` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `titulo_estudio` varchar(150) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id_academica`),
  KEY `fk_ia_funcionario` (`id_funcionario`),
  CONSTRAINT `fk_ia_funcionario` FOREIGN KEY (`id_funcionario`) REFERENCES `funcionarios` (`id_funcionario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_academica`
--

LOCK TABLES `info_academica` WRITE;
/*!40000 ALTER TABLE `info_academica` DISABLE KEYS */;
INSERT INTO `info_academica` VALUES (1,1,'Universidad de Antioquia','Pregrado','Ingeniería de Sistemas'),(2,1,'Universidad Nacional','Posgrado','Especialización en Seguridad Informática'),(3,2,'ITM','Pregrado','Administración de Empresas'),(4,3,'Politécnico Colombiano JIC','Tecnología','Tecnología en Sistemas'),(7,5,'Institución Universitaria Digital','Pregrado','Ingeniería de Software');
/*!40000 ALTER TABLE `info_academica` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-24 22:34:49
