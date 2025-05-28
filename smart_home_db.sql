-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smart_home_db
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `script` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','<< Flyway Baseline >>','BASELINE','<< Flyway Baseline >>',NULL,'root','2025-05-25 09:29:47',0,1),(2,'2','add device value column','SQL','V2__add_device_value_column.sql',1317895555,'root','2025-05-25 09:29:47',13,1),(3,'2','add device value column','DELETE','V2__add_device_value_column.sql',1317895555,'root','2025-05-25 09:47:43',0,1),(4,'3','add action type column','SQL','V3__add_action_type_column.sql',-68455315,'root','2025-05-25 09:48:40',35,1),(5,'4','make rule nullable in action log','SQL','V4__make_rule_nullable_in_action_log.sql',1600517079,'root','2025-05-25 10:18:38',53,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_devices`
--

DROP TABLE IF EXISTS `user_devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_devices` (
  `user_id` int NOT NULL,
  `device_id` int NOT NULL,
  `value` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `added_at` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`,`device_id`),
  KEY `device_id` (`device_id`),
  CONSTRAINT `user_devices_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `user_devices_ibfk_2` FOREIGN KEY (`device_id`) REFERENCES `устройства` (`id_устройства`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_devices`
--

LOCK TABLES `user_devices` WRITE;
/*!40000 ALTER TABLE `user_devices` DISABLE KEYS */;
INSERT INTO `user_devices` VALUES (8,1,'4','2025-05-28 07:32:50'),(8,2,'3','2025-05-28 09:11:53'),(8,3,'Вкл','2025-05-28 09:11:55'),(8,4,'Вкл','2025-05-27 17:15:18'),(8,5,'Движение','2025-05-28 07:32:51'),(10,1,'23','2025-05-27 15:42:22'),(10,2,'43','2025-05-27 15:42:31'),(10,3,'Вкл','2025-05-27 15:42:31'),(10,4,'Вкл','2025-05-27 15:42:30'),(10,5,'Движение','2025-05-27 15:42:30'),(13,1,'5','2025-05-27 07:15:31'),(13,2,'35','2025-05-27 07:27:35'),(14,1,'34','2025-05-27 15:43:22'),(14,2,'53','2025-05-27 15:43:22'),(14,3,'Вкл','2025-05-27 15:43:23'),(14,4,'Выкл','2025-05-27 15:43:23'),(14,5,'Нет движения','2025-05-27 15:43:23'),(15,1,'5','2025-05-27 17:16:41'),(15,2,'53','2025-05-27 17:16:40'),(15,3,'Выкл','2025-05-27 17:16:40'),(15,4,'Выкл','2025-05-27 17:16:40'),(15,5,'Нет движения','2025-05-27 17:16:39');
/*!40000 ALTER TABLE `user_devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `role` enum('ADMIN','USER') COLLATE utf8mb4_general_ci DEFAULT 'USER',
  `email` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin2004','admin2004','ADMIN','admin@example.com'),(2,'ivan','ivan123hash','USER','ivan@example.com'),(3,'olga','olga123hash','USER','olga@example.com'),(4,'maksim','maksim123hash','USER','maksim@example.com'),(5,'daria','daria123hash','USER','daria@example.com'),(7,'masha','$2a$10$btrWuHzVqtxAZv2LkFvCpOpnkLFFdqLWgSeXT8gh5TJK3N8Vgcdnq','USER','masha@example.com'),(8,'reyn21','$2a$10$znHwogWY/eq1wemqagdYxODUHz0VflCHgOoCpRQyadhTb36SvWenW','USER','reyn21@example.com'),(9,'newadmin','$2a$10$Qw8Qw8Qw8Qw8Qw8Qw8QwOeQw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8Q','ADMIN','newadmin@example.com'),(10,'deni21','$2a$10$t1vHFJTnrNbSu7lEOxE8De58VhgrUSU41VoaJ5xR2BZAetyNPFPn.','USER','deni21@example.com'),(11,'qwe112','$2a$10$EMQoNaDMVzid4TrsISVk0ecLHZVCJZtUGjl57dWT7agXkYzA9sx5K','USER','qwe112@example.com'),(12,'Adminka','$2a$12$q3AqbZvvRU94yAcnstIK1.wAgxjo/79QNMULueRNu3YhRilPxHJVW','ADMIN','Adminka@example.com'),(13,'qwerty228','$2a$12$/58ny2kzBIxBXLfwvZXSAeQo4N6PlBuY4OUbjK17bM0Gn5WB.yumW','USER','qwerty228@gmail.com'),(14,'WW228','$2a$12$MSO9Rvi2i2iqJxr.kwJZ/OYDF9SAgyiT4M/x89TKmms4trc3GNR7m','USER','WW228@mail.ru'),(15,'rys2281','$2a$12$E0mlgxzEcTo7ZtYtBSoSpeikxUiOkrCWgFm8Hq/nkVJhQo1t8r626','USER','rys2281@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `данные_устройств`
--

DROP TABLE IF EXISTS `данные_устройств`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `данные_устройств` (
  `id_данных` int NOT NULL AUTO_INCREMENT,
  `id_устройства` int NOT NULL,
  `дата_время` datetime DEFAULT CURRENT_TIMESTAMP,
  `значение` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_данных`),
  KEY `id_устройства` (`id_устройства`),
  CONSTRAINT `данные_устройств_ibfk_1` FOREIGN KEY (`id_устройства`) REFERENCES `устройства` (`id_устройства`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `данные_устройств`
--

LOCK TABLES `данные_устройств` WRITE;
/*!40000 ALTER TABLE `данные_устройств` DISABLE KEYS */;
INSERT INTO `данные_устройств` VALUES (1,1,'2025-05-24 17:06:27','22.5'),(2,1,'2025-05-24 17:06:27','22.8'),(3,2,'2025-05-24 17:06:27','44'),(4,2,'2025-05-24 17:06:27','45'),(5,5,'2025-05-24 17:06:27','обнаружено движение');
/*!40000 ALTER TABLE `данные_устройств` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `действия`
--

DROP TABLE IF EXISTS `действия`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `действия` (
  `id_действия` int NOT NULL AUTO_INCREMENT,
  `id_правила` int NOT NULL,
  `id_цели` int NOT NULL,
  `тип_действия` enum('TURN_ON','TURN_OFF','SET','ВКЛ','ВЫКЛ','УСТАНОВИТЬ') COLLATE utf8mb4_general_ci DEFAULT NULL,
  `значение` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_действия`),
  KEY `id_правила` (`id_правила`),
  KEY `id_цели` (`id_цели`),
  CONSTRAINT `действия_ibfk_1` FOREIGN KEY (`id_правила`) REFERENCES `правила` (`id_правила`) ON DELETE CASCADE,
  CONSTRAINT `действия_ibfk_2` FOREIGN KEY (`id_цели`) REFERENCES `устройства` (`id_устройства`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `действия`
--

LOCK TABLES `действия` WRITE;
/*!40000 ALTER TABLE `действия` DISABLE KEYS */;
INSERT INTO `действия` VALUES (1,1,4,'TURN_ON',NULL),(2,2,3,'TURN_ON',NULL),(3,3,4,'TURN_OFF',NULL),(4,4,3,'TURN_OFF',NULL),(5,5,3,'TURN_ON',NULL);
/*!40000 ALTER TABLE `действия` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `журнал_действий`
--

DROP TABLE IF EXISTS `журнал_действий`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `журнал_действий` (
  `id_журнала` int NOT NULL AUTO_INCREMENT,
  `id_правила` int DEFAULT NULL,
  `id_устройства` int NOT NULL,
  `дата_время` datetime DEFAULT CURRENT_TIMESTAMP,
  `выполнено` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `результат` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_пользователя` int DEFAULT NULL,
  `значение_устройства` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `тип_действия` enum('TURN_ON','TURN_OFF','SET') COLLATE utf8mb4_general_ci DEFAULT 'SET',
  PRIMARY KEY (`id_журнала`),
  KEY `id_правила` (`id_правила`),
  KEY `id_устройства` (`id_устройства`),
  CONSTRAINT `журнал_действий_ibfk_1` FOREIGN KEY (`id_правила`) REFERENCES `правила` (`id_правила`) ON DELETE CASCADE,
  CONSTRAINT `журнал_действий_ibfk_2` FOREIGN KEY (`id_устройства`) REFERENCES `устройства` (`id_устройства`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `журнал_действий`
--

LOCK TABLES `журнал_действий` WRITE;
/*!40000 ALTER TABLE `журнал_действий` DISABLE KEYS */;
INSERT INTO `журнал_действий` VALUES (1,1,4,'2025-05-24 17:06:27','ВКЛ','Успешно',NULL,NULL,'SET'),(2,2,3,'2025-05-24 17:06:27','ВКЛ','Успешно',NULL,NULL,'SET'),(3,3,4,'2025-05-24 17:06:27','ВЫКЛ','Успешно',NULL,NULL,'SET'),(4,4,3,'2025-05-24 17:06:27','ВЫКЛ','Ошибка',NULL,NULL,'SET'),(5,5,3,'2025-05-24 17:06:27','ВКЛ','Успешно',NULL,NULL,'SET'),(6,NULL,2,'2025-05-25 10:18:54','Добавлено устройство','Успешно',8,NULL,'SET'),(7,NULL,3,'2025-05-25 10:18:56','Добавлено устройство','Успешно',8,NULL,'SET'),(8,NULL,4,'2025-05-25 10:18:57','Добавлено устройство','Успешно',8,NULL,'SET'),(9,NULL,5,'2025-05-25 10:18:58','Добавлено устройство','Успешно',8,NULL,'SET'),(10,NULL,1,'2025-05-25 10:18:59','Добавлено устройство','Успешно',8,NULL,'SET'),(11,NULL,2,'2025-05-25 10:19:06','Добавлено устройство','Успешно',8,NULL,'SET'),(12,NULL,3,'2025-05-25 10:19:06','Добавлено устройство','Успешно',8,NULL,'SET'),(13,NULL,4,'2025-05-25 10:19:06','Добавлено устройство','Успешно',8,NULL,'SET'),(14,NULL,5,'2025-05-25 10:19:07','Добавлено устройство','Успешно',8,NULL,'SET'),(15,NULL,5,'2025-05-25 10:20:02','Добавлено устройство','Успешно',8,NULL,'SET'),(16,NULL,4,'2025-05-25 10:20:02','Добавлено устройство','Успешно',8,NULL,'SET'),(17,NULL,3,'2025-05-25 10:20:02','Добавлено устройство','Успешно',8,NULL,'SET'),(18,NULL,1,'2025-05-25 10:20:03','Добавлено устройство','Успешно',8,NULL,'SET'),(19,NULL,2,'2025-05-25 10:20:03','Добавлено устройство','Успешно',8,NULL,'SET'),(20,NULL,1,'2025-05-25 10:35:33','Добавлено устройство','Успешно',8,NULL,'SET'),(21,NULL,2,'2025-05-25 10:53:54','Добавлено устройство','Успешно',8,NULL,'SET'),(22,NULL,1,'2025-05-25 11:12:05','Добавлено устройство','Успешно',8,NULL,'SET'),(23,NULL,1,'2025-05-25 11:41:23','Добавлено устройство','Успешно',8,NULL,'SET'),(24,NULL,2,'2025-05-25 12:31:50','Добавлено устройство','Успешно',8,NULL,'SET'),(25,NULL,2,'2025-05-25 12:46:39','Добавлено устройство','Успешно',8,NULL,'SET'),(26,NULL,3,'2025-05-25 12:46:40','Добавлено устройство','Успешно',8,NULL,'SET'),(27,NULL,4,'2025-05-25 12:46:40','Добавлено устройство','Успешно',8,NULL,'SET'),(28,NULL,5,'2025-05-25 12:46:41','Добавлено устройство','Успешно',8,NULL,'SET'),(29,NULL,1,'2025-05-25 13:11:58','Добавлено устройство','Успешно',8,NULL,'SET'),(30,NULL,2,'2025-05-25 13:12:29','Добавлено устройство','Успешно',8,NULL,'SET'),(31,NULL,3,'2025-05-25 13:12:34','Добавлено устройство','Успешно',8,NULL,'SET'),(32,NULL,4,'2025-05-25 13:12:34','Добавлено устройство','Успешно',8,NULL,'SET'),(33,NULL,5,'2025-05-25 13:12:35','Добавлено устройство','Успешно',8,NULL,'SET'),(34,NULL,1,'2025-05-25 15:22:08','Добавлено устройство','Успешно',8,'22.5','SET'),(35,NULL,2,'2025-05-25 15:39:36','Добавлено устройство','Успешно',8,'45','SET'),(36,NULL,1,'2025-05-25 15:46:12','Добавлено устройство','Успешно',8,'22.5','SET'),(37,NULL,2,'2025-05-26 18:57:32','Добавлено устройство','Успешно',8,'45','SET'),(38,NULL,3,'2025-05-26 18:57:33','Добавлено устройство','Успешно',8,'выкл','SET'),(39,NULL,4,'2025-05-26 18:57:33','Добавлено устройство','Успешно',8,'выкл','SET'),(40,NULL,5,'2025-05-26 18:57:34','Добавлено устройство','Успешно',8,'нет движения','SET'),(41,NULL,1,'2025-05-26 20:06:59','Добавлено устройство','Успешно',12,'22.5','SET'),(42,NULL,1,'2025-05-26 20:07:14','Добавлено устройство','Успешно',12,'22.5','SET'),(43,NULL,2,'2025-05-26 20:07:14','Добавлено устройство','Успешно',12,'45','SET'),(44,NULL,1,'2025-05-26 20:24:22','Добавлено устройство','Успешно',8,'22.5','SET'),(45,NULL,1,'2025-05-26 20:58:56','Добавлено устройство','Успешно',8,'22.5','SET'),(46,NULL,2,'2025-05-26 20:58:57','Добавлено устройство','Успешно',8,'45','SET'),(47,NULL,3,'2025-05-26 20:58:57','Добавлено устройство','Успешно',8,'выкл','SET'),(48,NULL,4,'2025-05-26 20:58:57','Добавлено устройство','Успешно',8,'выкл','SET'),(49,NULL,5,'2025-05-26 20:58:58','Добавлено устройство','Успешно',8,'нет движения','SET'),(50,NULL,3,'2025-05-26 21:18:36','Добавлено устройство','Успешно',8,'выкл','SET'),(51,NULL,2,'2025-05-26 21:26:24','Добавлено устройство','Успешно',8,'45','SET'),(52,NULL,1,'2025-05-26 21:26:25','Добавлено устройство','Успешно',8,'22.5','SET'),(53,NULL,4,'2025-05-26 21:26:25','Добавлено устройство','Успешно',8,'выкл','SET'),(54,NULL,5,'2025-05-26 21:26:26','Добавлено устройство','Успешно',8,'нет движения','SET'),(55,NULL,2,'2025-05-26 21:33:18','Добавлено устройство','Успешно',8,'45','SET'),(56,NULL,3,'2025-05-26 21:33:18','Добавлено устройство','Успешно',8,'выкл','SET'),(57,NULL,4,'2025-05-26 21:33:19','Добавлено устройство','Успешно',8,'выкл','SET'),(58,NULL,5,'2025-05-26 21:33:19','Добавлено устройство','Успешно',8,'нет движения','SET'),(59,NULL,1,'2025-05-26 21:43:09','Добавлено устройство','Успешно',8,'22.5','SET'),(60,NULL,3,'2025-05-27 07:12:06','Добавлено устройство','Успешно',8,'выкл','SET'),(61,NULL,1,'2025-05-27 07:15:31','Добавлено устройство','Успешно',13,'22.5','SET'),(62,NULL,2,'2025-05-27 07:27:35','Добавлено устройство','Успешно',13,'','SET'),(63,NULL,2,'2025-05-27 07:27:39','Изменено значение устройства','Успешно',13,'35','SET'),(64,NULL,1,'2025-05-27 07:27:42','Изменено значение устройства','Успешно',13,'5','SET'),(65,NULL,5,'2025-05-27 15:41:31','Добавлено устройство','Успешно',8,'','SET'),(66,NULL,5,'2025-05-27 15:41:41','Изменено значение устройства','Успешно',8,'Нет движения','SET'),(67,NULL,1,'2025-05-27 15:42:22','Добавлено устройство','Успешно',10,'','SET'),(68,NULL,1,'2025-05-27 15:42:28','Изменено значение устройства','Успешно',10,'23','SET'),(69,NULL,5,'2025-05-27 15:42:30','Добавлено устройство','Успешно',10,'','SET'),(70,NULL,4,'2025-05-27 15:42:30','Добавлено устройство','Успешно',10,'','SET'),(71,NULL,3,'2025-05-27 15:42:31','Добавлено устройство','Успешно',10,'','SET'),(72,NULL,2,'2025-05-27 15:42:31','Добавлено устройство','Успешно',10,'','SET'),(73,NULL,2,'2025-05-27 15:42:33','Изменено значение устройства','Успешно',10,'43','SET'),(74,NULL,3,'2025-05-27 15:42:34','Изменено значение устройства','Успешно',10,'Вкл','SET'),(75,NULL,4,'2025-05-27 15:42:35','Изменено значение устройства','Успешно',10,'Вкл','SET'),(76,NULL,5,'2025-05-27 15:42:35','Изменено значение устройства','Успешно',10,'Движение','SET'),(77,NULL,1,'2025-05-27 15:43:22','Добавлено устройство','Успешно',14,'','SET'),(78,NULL,2,'2025-05-27 15:43:22','Добавлено устройство','Успешно',14,'','SET'),(79,NULL,3,'2025-05-27 15:43:23','Добавлено устройство','Успешно',14,'','SET'),(80,NULL,4,'2025-05-27 15:43:23','Добавлено устройство','Успешно',14,'','SET'),(81,NULL,5,'2025-05-27 15:43:23','Добавлено устройство','Успешно',14,'','SET'),(82,NULL,1,'2025-05-27 15:43:25','Изменено значение устройства','Успешно',14,'34','SET'),(83,NULL,2,'2025-05-27 15:43:26','Изменено значение устройства','Успешно',14,'53','SET'),(84,NULL,3,'2025-05-27 15:43:27','Изменено значение устройства','Успешно',14,'Вкл','SET'),(85,NULL,4,'2025-05-27 15:43:28','Изменено значение устройства','Успешно',14,'Выкл','SET'),(86,NULL,5,'2025-05-27 15:43:30','Изменено значение устройства','Успешно',14,'Нет движения','SET'),(87,NULL,1,'2025-05-27 17:13:03','Добавлено устройство','Успешно',8,'','SET'),(88,NULL,5,'2025-05-27 17:15:15','Изменено значение устройства','Успешно',8,'Нет движения','SET'),(89,NULL,3,'2025-05-27 17:15:16','Изменено значение устройства','Успешно',8,'Выкл','SET'),(90,NULL,4,'2025-05-27 17:15:18','Добавлено устройство','Успешно',8,'','SET'),(91,NULL,1,'2025-05-27 17:15:19','Добавлено устройство','Успешно',8,'','SET'),(92,NULL,2,'2025-05-27 17:15:20','Добавлено устройство','Успешно',8,'','SET'),(93,NULL,4,'2025-05-27 17:15:28','Изменено значение устройства','Успешно',8,'Вкл','SET'),(94,NULL,1,'2025-05-27 17:15:30','Изменено значение устройства','Успешно',8,'4','SET'),(95,NULL,2,'2025-05-27 17:15:31','Изменено значение устройства','Успешно',8,'5','SET'),(96,NULL,3,'2025-05-27 17:15:33','Изменено значение устройства','Успешно',8,'Вкл','SET'),(97,NULL,5,'2025-05-27 17:15:35','Изменено значение устройства','Успешно',8,'Движение','SET'),(98,NULL,5,'2025-05-27 17:16:39','Добавлено устройство','Успешно',15,'','SET'),(99,NULL,4,'2025-05-27 17:16:40','Добавлено устройство','Успешно',15,'','SET'),(100,NULL,3,'2025-05-27 17:16:40','Добавлено устройство','Успешно',15,'','SET'),(101,NULL,2,'2025-05-27 17:16:40','Добавлено устройство','Успешно',15,'','SET'),(102,NULL,1,'2025-05-27 17:16:41','Добавлено устройство','Успешно',15,'','SET'),(103,NULL,1,'2025-05-27 17:16:44','Изменено значение устройства','Успешно',15,'5','SET'),(104,NULL,2,'2025-05-27 17:16:45','Изменено значение устройства','Успешно',15,'53','SET'),(105,NULL,3,'2025-05-27 17:16:47','Изменено значение устройства','Успешно',15,'Выкл','SET'),(106,NULL,4,'2025-05-27 17:16:48','Изменено значение устройства','Успешно',15,'Выкл','SET'),(107,NULL,5,'2025-05-27 17:16:50','Изменено значение устройства','Успешно',15,'Нет движения','SET'),(108,NULL,1,'2025-05-28 07:26:52','Добавлено устройство','Успешно',8,'','SET'),(109,NULL,2,'2025-05-28 07:26:54','Добавлено устройство','Успешно',8,'','SET'),(110,NULL,2,'2025-05-28 07:27:02','Изменено значение устройства','Успешно',8,'34','SET'),(111,NULL,1,'2025-05-28 07:32:50','Добавлено устройство','Успешно',8,'','SET'),(112,NULL,5,'2025-05-28 07:32:51','Добавлено устройство','Успешно',8,'','SET'),(113,NULL,2,'2025-05-28 09:11:53','Добавлено устройство','Успешно',8,'','SET'),(114,NULL,3,'2025-05-28 09:11:55','Добавлено устройство','Успешно',8,'','SET'),(115,NULL,2,'2025-05-28 09:12:01','Изменено значение устройства','Успешно',8,'3','SET'),(116,NULL,1,'2025-05-28 09:12:04','Изменено значение устройства','Успешно',8,'4','SET'),(117,NULL,4,'2025-05-28 09:12:05','Изменено значение устройства','Успешно',8,'Вкл','SET'),(118,NULL,5,'2025-05-28 09:12:06','Изменено значение устройства','Успешно',8,'Движение','SET'),(119,NULL,3,'2025-05-28 09:12:06','Изменено значение устройства','Успешно',8,'Вкл','SET');
/*!40000 ALTER TABLE `журнал_действий` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `правила`
--

DROP TABLE IF EXISTS `правила`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `правила` (
  `id_правила` int NOT NULL AUTO_INCREMENT,
  `название` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `активно` tinyint(1) DEFAULT '1',
  `дата_создания` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_правила`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `правила`
--

LOCK TABLES `правила` WRITE;
/*!40000 ALTER TABLE `правила` DISABLE KEYS */;
INSERT INTO `правила` VALUES (1,'Включить отопление, если холодно',1,'2025-05-24 14:06:27'),(2,'Включить свет при движении',1,'2025-05-24 14:06:27'),(3,'Контроль влажности',1,'2025-05-24 14:06:27'),(4,'Ночной режим',1,'2025-05-24 14:06:27'),(5,'Пробуждение',1,'2025-05-24 14:06:27');
/*!40000 ALTER TABLE `правила` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `условия`
--

DROP TABLE IF EXISTS `условия`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `условия` (
  `id_условия` int NOT NULL AUTO_INCREMENT,
  `id_правила` int NOT NULL,
  `id_устройства` int NOT NULL,
  `оператор` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `значение` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `логика` enum('AND','OR') COLLATE utf8mb4_general_ci DEFAULT 'AND',
  PRIMARY KEY (`id_условия`),
  KEY `id_правила` (`id_правила`),
  KEY `id_устройства` (`id_устройства`),
  CONSTRAINT `условия_ibfk_1` FOREIGN KEY (`id_правила`) REFERENCES `правила` (`id_правила`) ON DELETE CASCADE,
  CONSTRAINT `условия_ibfk_2` FOREIGN KEY (`id_устройства`) REFERENCES `устройства` (`id_устройства`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `условия`
--

LOCK TABLES `условия` WRITE;
/*!40000 ALTER TABLE `условия` DISABLE KEYS */;
INSERT INTO `условия` VALUES (1,1,1,'<','20','AND'),(2,2,5,'=','обнаружено движение','AND'),(3,3,2,'>','60','AND'),(4,4,1,'<','21','AND'),(5,5,1,'>','22','AND');
/*!40000 ALTER TABLE `условия` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `устройства`
--

DROP TABLE IF EXISTS `устройства`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `устройства` (
  `id_устройства` int NOT NULL AUTO_INCREMENT,
  `название` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `тип` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `расположение` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `состояние` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `дата_добавления` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `id_пользователя` int NOT NULL,
  PRIMARY KEY (`id_устройства`),
  KEY `fk_device_user` (`id_пользователя`),
  CONSTRAINT `fk_device_user` FOREIGN KEY (`id_пользователя`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `устройства`
--

LOCK TABLES `устройства` WRITE;
/*!40000 ALTER TABLE `устройства` DISABLE KEYS */;
INSERT INTO `устройства` VALUES (1,'Датчик температуры в гостиной','сенсор','Гостиная','22.5','2025-05-24 14:06:27',12),(2,'Датчик влажности на кухне','сенсор','Кухня','45','2025-05-24 14:06:27',12),(3,'Реле света в спальне','реле','Спальня','выкл','2025-05-24 14:06:27',12),(4,'Выключатель отопления','переключатель','Холл','выкл','2025-05-24 14:06:27',12),(5,'Датчик движения у входа','сенсор','Прихожая','нет движения','2025-05-24 14:06:27',12);
/*!40000 ALTER TABLE `устройства` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-28 13:17:56
