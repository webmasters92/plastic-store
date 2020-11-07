CREATE DATABASE IF NOT EXISTS `plastic_store_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `plastic_store_db`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: plastic_store_db
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart`
(
    `cartId`     int NOT NULL AUTO_INCREMENT,
    `customerId` int NOT NULL,
    `total`      int NOT NULL,
    PRIMARY KEY (`cartId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `cart`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `color`      varchar(255) DEFAULT NULL,
    `price`      int NOT NULL,
    `quantity`   int NOT NULL,
    `size`       varchar(255) DEFAULT NULL,
    `totalPrice` int NOT NULL,
    `cart_id`    int          DEFAULT NULL,
    `product_id` int          DEFAULT NULL,
    `color_id`   int          DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK99e0am9jpriwxcm6is7xfedy3` (`cart_id`),
    KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
    KEY `FKg4qoihenxb0iroyhs566xehur` (`color_id`),
    CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
    CONSTRAINT `FK99e0am9jpriwxcm6is7xfedy3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cartId`),
    CONSTRAINT `FKg4qoihenxb0iroyhs566xehur` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_items`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories`
(
    `id`   int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories`
    DISABLE KEYS */;
INSERT INTO `categories`
VALUES (1, 'Nameštaj'),
       (2, 'Kupatilo'),
       (3, 'Saksije i bašta'),
       (4, 'Čišćenje'),
       (5, 'Kuhinja'),
       (6, 'Dečiji program'),
       (7, 'Ostalo');
/*!40000 ALTER TABLE `categories`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colors`
(
    `id`   int          NOT NULL,
    `code` varchar(255) NOT NULL,
    `name` varchar(32)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors`
    DISABLE KEYS */;
INSERT INTO `colors`
VALUES (1, '#00FF00', 'Svetlo zelena'),
       (2, '#8B4513', 'Braon'),
       (3, '#ecf0f1 ', 'Bela'),
       (4, '#95a5a6 ', 'Siva'),
       (5, '#FFA500', 'Narandžasta'),
       (6, '#FFFF00', 'Žuta'),
       (7, '#000000', 'Crna'),
       (8, '#FF00FF', 'Roze'),
       (9, '#800000', 'Bordo'),
       (10, '#008000', 'Tamno zelena'),
       (11, '#00FFFF', 'Tirkizna'),
       (12, '#800080', 'Ljubičasta'),
       (13, '#2c3e50 ', 'Antracit'),
       (14, '#d35400 ', 'Terakot'),
       (15, '#F0E68C', 'Svetla moka'),
       (16, '#CD853F', 'Tamna moka'),
       (17, '#FFFACD', 'Lan');
/*!40000 ALTER TABLE `colors`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers`
(
    `id`           int NOT NULL AUTO_INCREMENT,
    `address`      varchar(255) DEFAULT NULL,
    `city`         varchar(255) DEFAULT NULL,
    `country`      varchar(255) DEFAULT NULL,
    `date_created` datetime     DEFAULT NULL,
    `email`        varchar(255) DEFAULT NULL,
    `first_name`   varchar(255) DEFAULT NULL,
    `last_name`    varchar(255) DEFAULT NULL,
    `password`     varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `reset_token`  varchar(255) DEFAULT NULL,
    `username`     varchar(255) DEFAULT NULL,
    `zip_code`     int          DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `customers`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guests`
--

DROP TABLE IF EXISTS `guests`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests`
(
    `id`           int NOT NULL AUTO_INCREMENT,
    `address`      varchar(255) DEFAULT NULL,
    `city`         varchar(255) DEFAULT NULL,
    `country`      varchar(255) DEFAULT NULL,
    `email`        varchar(255) DEFAULT NULL,
    `first_name`   varchar(255) DEFAULT NULL,
    `last_name`    varchar(255) DEFAULT NULL,
    `password`     varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `username`     varchar(255) DEFAULT NULL,
    `zip_code`     int          DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests`
--

LOCK TABLES `guests` WRITE;
/*!40000 ALTER TABLE `guests`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `guests`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence`
    DISABLE KEYS */;
INSERT INTO `hibernate_sequence`
VALUES (1),
       (1),
       (1),
       (1);
/*!40000 ALTER TABLE `hibernate_sequence`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images`
(
    `id`         int         NOT NULL AUTO_INCREMENT,
    `name`       varchar(64) NOT NULL,
    `url`        varchar(64) NOT NULL,
    `product_id` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKghwsjbjo7mg3iufxruvq6iu3q` (`product_id`),
    CONSTRAINT `FKghwsjbjo7mg3iufxruvq6iu3q` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `images`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages`
(
    `id`           int    NOT NULL,
    `answered`     bit(1) NOT NULL,
    `date_created` datetime     DEFAULT NULL,
    `email`        varchar(255) DEFAULT NULL,
    `name`         varchar(255) DEFAULT NULL,
    `subject`      varchar(255) DEFAULT NULL,
    `message`      varchar(500) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `messages`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items`
(
    `id`            int    NOT NULL AUTO_INCREMENT,
    `color`         varchar(255) DEFAULT NULL,
    `price`         double NOT NULL,
    `quantity`      int    NOT NULL,
    `size`          varchar(255) DEFAULT NULL,
    `totalPrice`    int    NOT NULL,
    `order_id`      int          DEFAULT NULL,
    `product_id`    int          DEFAULT NULL,
    `product_color` int          DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
    KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
    KEY `FKheb8bxpvx6lh3eikwrrlslyyu` (`product_color`),
    CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    CONSTRAINT `FKheb8bxpvx6lh3eikwrrlslyyu` FOREIGN KEY (`product_color`) REFERENCES `colors` (`id`),
    CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders`
(
    `id`            int NOT NULL AUTO_INCREMENT,
    `customer_id`   int NOT NULL,
    `date_created`  datetime     DEFAULT NULL,
    `message`       varchar(100) DEFAULT NULL,
    `order_status`  varchar(255) DEFAULT NULL,
    `order_total`   double       DEFAULT NULL,
    `order_payment` varchar(255) DEFAULT NULL,
    `shipping`      double       DEFAULT NULL,
    `guest_id`      int          DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKiqncvp2qrd60b82jmprx7eyxm` (`guest_id`),
    CONSTRAINT `FKiqncvp2qrd60b82jmprx7eyxm` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `orders`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persistent_logins`
(
    `series`    varchar(64) NOT NULL,
    `last_used` datetime    NOT NULL,
    `token`     varchar(64) NOT NULL,
    `username`  varchar(64) NOT NULL,
    PRIMARY KEY (`series`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_attributes`
--

DROP TABLE IF EXISTS `product_attributes`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_attributes`
(
    `product_id`       int NOT NULL,
    `discounted_price` int          DEFAULT NULL,
    `product_price`    int          DEFAULT NULL,
    `product_size`     varchar(255) DEFAULT NULL,
    KEY `FKcex46yvx4g18b2pn09p79h1mc` (`product_id`),
    CONSTRAINT `FKcex46yvx4g18b2pn09p79h1mc` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_attributes`
--

LOCK TABLES `product_attributes` WRITE;
/*!40000 ALTER TABLE `product_attributes`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `product_attributes`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_color`
--

DROP TABLE IF EXISTS `product_color`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_color`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `code`       varchar(255) NOT NULL,
    `name`       varchar(32)  NOT NULL,
    `product_id` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKjs0ht7btbgt5u0jpossmgvfk5` (`product_id`),
    CONSTRAINT `FKjs0ht7btbgt5u0jpossmgvfk5` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_color`
--

LOCK TABLES `product_color` WRITE;
/*!40000 ALTER TABLE `product_color`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `product_color`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products`
(
    `id`              int NOT NULL AUTO_INCREMENT,
    `available`       bit(1)       DEFAULT NULL,
    `code`            int          DEFAULT NULL,
    `date_created`    datetime     DEFAULT NULL,
    `date_updated`    datetime     DEFAULT NULL,
    `description`     varchar(255) DEFAULT NULL,
    `manufacturer`    varchar(255) DEFAULT NULL,
    `name`            varchar(255) DEFAULT NULL,
    `sale`            bit(1)       DEFAULT NULL,
    `status`          bit(1)       DEFAULT NULL,
    `category_id`     int          DEFAULT NULL,
    `sub_category_id` int          DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
    KEY `FKno5p9kcr384tg56cbk8l9l6h2` (`sub_category_id`),
    CONSTRAINT `FKno5p9kcr384tg56cbk8l9l6h2` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_categories` (`id`),
    CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `products`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions`
(
    `id`          int NOT NULL AUTO_INCREMENT,
    `category_id` int DEFAULT NULL,
    `product_id`  int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK909g9g1svefta8r8hvluj7j0n` (`category_id`),
    KEY `FK5ukm0jhih3cbin6dhkppos7ot` (`product_id`),
    CONSTRAINT `FK5ukm0jhih3cbin6dhkppos7ot` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
    CONSTRAINT `FK909g9g1svefta8r8hvluj7j0n` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions`
--

LOCK TABLES `promotions` WRITE;
/*!40000 ALTER TABLE `promotions`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `promotions`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews`
(
    `id`        int NOT NULL,
    `comment`   varchar(255) DEFAULT NULL,
    `email`     varchar(255) DEFAULT NULL,
    `productId` int NOT NULL,
    `rating`    int NOT NULL,
    `reviewer`  varchar(255) DEFAULT NULL,
    `userId`    int NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles`
(
    `roleId` int NOT NULL,
    `role`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`roleId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `roles`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_categories`
--

DROP TABLE IF EXISTS `sub_categories`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_categories`
(
    `id`          int NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) DEFAULT NULL,
    `category_id` int          DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKjwy7imy3rf6r99x48ydq45otw` (`category_id`),
    CONSTRAINT `FKjwy7imy3rf6r99x48ydq45otw` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 56
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_categories`
--

LOCK TABLES `sub_categories` WRITE;
/*!40000 ALTER TABLE `sub_categories`
    DISABLE KEYS */;
INSERT INTO `sub_categories`
VALUES (1, 'Baštenski nameštaj', 1),
       (2, 'Fiokari', 1),
       (3, 'Plastične stolice', 1),
       (4, 'Plastični stolovi', 1),
       (5, 'Korpe za veš', 2),
       (6, 'Zavese za kupatilo', 2),
       (7, 'Setovi kupatilo', 2),
       (8, 'Kupatilo ostalo', 2),
       (9, 'Saksije okrugle', 3),
       (10, 'Saksije četvrtaste', 3),
       (11, 'Saksije magnetika', 3),
       (12, 'Žardinjere', 3),
       (13, 'PVC ograde', 3),
       (14, 'Bašta ostalo', 3),
       (15, 'Brisko', 4),
       (16, 'Kante za smeće', 4),
       (17, 'Đubrovnici i metle', 4),
       (18, 'Čišćenje ostalo', 4),
       (19, 'Oceđivači i posude', 5),
       (20, 'Organizeri', 5),
       (21, 'Silikonske Mušeme', 5),
       (22, 'Kutije za hleb', 5),
       (23, 'Činije', 5),
       (24, 'Modle', 5),
       (25, 'Noževi i daske', 5),
       (26, 'Tacne', 5),
       (27, 'Korpice', 5),
       (28, 'Vangle', 5),
       (29, 'Četke', 5),
       (30, 'Zvona', 5),
       (31, 'Cediljke', 5),
       (32, 'Flaše', 5),
       (33, 'Čaše', 5),
       (34, 'Bokali', 5),
       (35, 'Levak', 5),
       (36, 'Posuđe', 5),
       (37, 'Kadice za bebe', 6),
       (38, 'Kutije za igračke', 6),
       (39, 'Noše i adapteri za decu', 6),
       (40, 'Pertini igračke', 6),
       (41, 'Dečiji nameštaj', 6),
       (42, 'Igračke', 6),
       (43, 'Edukativne igračke', 6),
       (44, 'Plastične police', 7),
       (45, 'Specijalno', 7),
       (46, 'Korpe ostalo', 7),
       (47, 'Štipaljke', 7),
       (48, 'Burad', 7),
       (49, 'Gajbe', 7),
       (50, 'Ofingeri', 7),
       (51, 'Klaser kutije', 7),
       (52, 'Hranilice i pojilice za piliće', 7),
       (53, 'Alat i radni pribor', 7),
       (54, 'Četke ostalo', 7),
       (55, 'Lavori', 7);
/*!40000 ALTER TABLE `sub_categories`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role`
(
    `user_id` int NOT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    UNIQUE KEY `UK_it77eq964jhfqtu54081ebtio` (`role_id`),
    CONSTRAINT `FKftwvf24o6btr5cilynp8cw9ev` FOREIGN KEY (`user_id`) REFERENCES `customers` (`id`),
    CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`roleId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist`
(
    `id`        int NOT NULL,
    `productId` int NOT NULL,
    `userId`    int NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `wishlist`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-10-17 13:38:31
