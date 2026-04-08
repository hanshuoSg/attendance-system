-- MySQL dump 10.13  Distrib 9.6.0, for Win64 (x86_64)
--
-- Host: localhost    Database: attendance_system
-- ------------------------------------------------------
-- Server version	9.6.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '276d2df4-2dbf-11f1-8893-02004c4f4f50:1-47';

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `student_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学生姓名',
  `course_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程编号',
  `check_in_time` datetime NOT NULL COMMENT '签到时间',
  `seat_row` tinyint DEFAULT NULL COMMENT '座位行号',
  `seat_col` tinyint DEFAULT NULL COMMENT '座位列号',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'NORMAL' COMMENT '状态',
  `ip` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签到IP地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (11,'42411201','韩硕','CS101','2026-04-01 08:25:00',3,5,'NORMAL','192.168.1.100','2026-04-08 10:37:05'),(12,'42411202','李四','CS101','2026-04-01 08:30:00',4,6,'NORMAL','192.168.1.101','2026-04-08 10:37:05'),(13,'42411203','王五','CS101','2026-04-01 08:45:00',5,7,'LATE','192.168.1.102','2026-04-08 10:37:05'),(14,'42411204','赵六','CS102','2026-04-01 09:00:00',2,3,'NORMAL','192.168.1.103','2026-04-08 10:37:05'),(15,'42411205','钱七','CS102','2026-04-01 09:15:00',2,4,'LATE','192.168.1.104','2026-04-08 10:37:05');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `course_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程编号',
  `course_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名称',
  `class_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班级名称',
  `teacher_id` bigint NOT NULL COMMENT '教师ID',
  `classroom_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '教室名称',
  `rows` tinyint DEFAULT NULL COMMENT '教室行数',
  `cols` tinyint DEFAULT NULL COMMENT '教室列数',
  `exclude_seats` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '不可坐的座位位置',
  `weekday` tinyint DEFAULT NULL COMMENT '星期几',
  `start_week` int DEFAULT NULL COMMENT '开始周次',
  `end_week` int DEFAULT NULL COMMENT '结束周次',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`course_id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('CS101','Java程序设计','计算机3班',2,'数学A-101',8,10,'1,1;1,2',1,1,16,'2026-04-08 10:26:04'),('CS102','数据结构','计算机3班',3,'数学A-102',8,10,'2,3;2,4',3,1,16,'2026-04-08 10:26:04');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_selection`
--

DROP TABLE IF EXISTS `course_selection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_selection` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `student_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学生姓名',
  `gender` char(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `course_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程编号',
  `select_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `course_selection_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_selection`
--

LOCK TABLES `course_selection` WRITE;
/*!40000 ALTER TABLE `course_selection` DISABLE KEYS */;
INSERT INTO `course_selection` VALUES (1,'42411201','韩硕','女','CS101','2026-04-08 10:35:19'),(2,'42411202','李四','男','CS101','2026-04-08 10:35:19'),(3,'42411203','王五','女','CS101','2026-04-08 10:35:19'),(4,'42411204','赵六','男','CS102','2026-04-08 10:35:19'),(5,'42411205','钱七','女','CS102','2026-04-08 10:35:19');
/*!40000 ALTER TABLE `course_selection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '真实姓名',
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'TEACHER' COMMENT '角色: TEACHER/ADMIN',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-启用，0-禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123456','系统管理员','ADMIN','2026-04-08 10:26:04','admin@school.com','13800000000',1),(2,'wanglaoshi','123456','王老师','TEACHER','2026-04-08 10:26:04','wanglaoshi@school.com','13800000001',1),(3,'lilaoshi','123456','李老师','TEACHER','2026-04-08 10:26:04','lilaoshi@school.com','13800000002',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-08 10:45:26
