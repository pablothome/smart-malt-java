-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: smart_malt
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cpf_cliente` varchar(11) NOT NULL,
  `nome_cliente` varchar(50) DEFAULT NULL,
  `telefone_cliente` varchar(15) DEFAULT NULL,
  `email_cliente` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cpf_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('19194994994','fgdfgdf','94949649494','dsssd@dsdfa.com'),('19595959595','Girlene','55555656556','gisii@fasfs.com'),('39140588888','Jennifer','20555522232','pfdfa@wfss.com'),('39155520200','Girlene','19565656565','fsffas@fdfdsfas.com'),('43553454554','qwq','45354353543','gggf@fdfsd.com'),('46466565656','ytytryt','77765767657','sdsf@fdffdd'),('46546945695','Cleiton','61616161262','cleitin@fdsfdas.com'),('49494949449','PAPAP','19595955955','fdfs@ffasfs.com'),('54645646566','LPLPLP','26546525265','NKJNFSA@asfsa.con'),('99988855566','Abner','45454646464','abner@gmail.com');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compra` (
  `id_compra` int NOT NULL AUTO_INCREMENT,
  `id_fornecedor` int NOT NULL,
  `total_compra` decimal(10,2) NOT NULL,
  `data_compra` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_compra`),
  KEY `id_fornecedor` (`id_fornecedor`),
  CONSTRAINT `compra_ibfk_1` FOREIGN KEY (`id_fornecedor`) REFERENCES `fornecedor` (`id_fornecedor`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` VALUES (1,3,1500.00,'2026-03-30 22:45:36'),(2,3,25000.00,'2026-03-30 22:48:45'),(3,1,37.90,'2026-03-31 16:32:28'),(4,3,35.00,'2026-03-31 16:38:26'),(5,4,67.50,'2026-03-31 17:40:04'),(6,1,120.00,'2026-03-31 17:56:26'),(7,3,35.00,'2026-04-01 23:13:32'),(8,3,55.00,'2026-04-01 23:35:52'),(9,1,450.00,'2026-04-02 02:22:32'),(10,1,450.00,'2026-04-02 19:23:41'),(11,6,500.00,'2026-04-02 19:24:22'),(12,1,151.20,'2026-04-02 19:25:19'),(13,6,90.00,'2026-04-02 19:25:54'),(14,5,45.00,'2026-04-07 22:15:37'),(15,3,50.00,'2026-04-07 23:00:13'),(16,5,125.00,'2026-04-08 14:23:12'),(17,3,70.00,'2026-04-08 14:24:14'),(18,5,600.00,'2026-04-11 19:49:47'),(19,6,150.00,'2026-04-15 21:30:21'),(20,1,75.00,'2026-04-15 21:30:41'),(21,6,75.00,'2026-04-15 21:31:20'),(22,8,105.00,'2026-04-15 21:31:39'),(23,8,80.00,'2026-04-15 21:32:05'),(24,1,135.00,'2026-04-15 21:32:21'),(25,1,105.00,'2026-04-15 21:32:38'),(26,6,456.00,'2026-04-22 22:53:59'),(29,1,100.00,'2026-05-07 23:37:14'),(30,3,85.00,'2026-05-07 23:38:55'),(31,8,75.00,'2026-05-11 22:18:14'),(32,7,102.50,'2026-05-18 22:39:36');
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrada`
--

DROP TABLE IF EXISTS `entrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrada` (
  `id_entrada` int NOT NULL AUTO_INCREMENT,
  `id_fornecedor` int NOT NULL,
  `data_entrada` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `valor_total` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id_entrada`),
  KEY `id_fornecedor` (`id_fornecedor`),
  CONSTRAINT `entrada_ibfk_1` FOREIGN KEY (`id_fornecedor`) REFERENCES `fornecedor` (`id_fornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrada`
--

LOCK TABLES `entrada` WRITE;
/*!40000 ALTER TABLE `entrada` DISABLE KEYS */;
/*!40000 ALTER TABLE `entrada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornecedor` (
  `id_fornecedor` int NOT NULL AUTO_INCREMENT,
  `nome_fornecedor` varchar(50) NOT NULL,
  `telefone_fornecedor` varchar(15) NOT NULL,
  `cnpj_fornecedor` varchar(20) NOT NULL,
  `email_fornecedor` varchar(50) NOT NULL,
  PRIMARY KEY (`id_fornecedor`),
  UNIQUE KEY `cnpj_fornecedor` (`cnpj_fornecedor`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` VALUES (1,'Amanda','(12) 56596-5965','61.644.694/9494-94','fsddf@dsds.com'),(3,'Kleber','(79) 79779-7979','44.946.464/4646-46','klkfdlf@jojofa.com'),(4,'Sampaio','(64) 16464-6464','53.564.646/4646-46','opopopqweq@.com'),(5,'Abner','(55) 56565-6566','48.487.487/8897-87','dsfsda@dfsa.com'),(6,'Jesebel','(88) 94974-9797','15.454.646/4646-46','kmnskda@sads.com'),(7,'Abgail','(89) 89898-9898','77.777.777/7777-77','sfas@sfasfsa.com'),(8,'Jair','(43) 42434-2444','33.444.234/3442-34','jair@fsfas.com'),(9,'Rogério','(46) 46444-6446','45.454.454/5545-54','rogerinho09@gmail.com');
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_compra`
--

DROP TABLE IF EXISTS `item_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_compra` (
  `id_item` int NOT NULL AUTO_INCREMENT,
  `id_compra` int DEFAULT NULL,
  `id_produto` int DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `preco_compra` decimal(10,2) DEFAULT NULL,
  `total_item` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_item`),
  KEY `id_compra` (`id_compra`),
  KEY `id_produto` (`id_produto`),
  CONSTRAINT `item_compra_ibfk_1` FOREIGN KEY (`id_compra`) REFERENCES `compra` (`id_compra`),
  CONSTRAINT `item_compra_ibfk_2` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id_produto`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_compra`
--

LOCK TABLES `item_compra` WRITE;
/*!40000 ALTER TABLE `item_compra` DISABLE KEYS */;
INSERT INTO `item_compra` VALUES (1,29,4,10,10.00,100.00),(2,30,4,10,3.50,35.00),(3,30,15,5,10.00,50.00),(4,31,12,10,7.50,75.00),(5,32,11,10,3.50,35.00),(6,32,20,15,4.50,67.50);
/*!40000 ALTER TABLE `item_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_entrada`
--

DROP TABLE IF EXISTS `item_entrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_entrada` (
  `id_item` int NOT NULL AUTO_INCREMENT,
  `id_entrada` int NOT NULL,
  `id_produto` int NOT NULL,
  `quantidade` int NOT NULL,
  `preco_compra` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_item`),
  KEY `id_entrada` (`id_entrada`),
  KEY `id_produto` (`id_produto`),
  CONSTRAINT `item_entrada_ibfk_1` FOREIGN KEY (`id_entrada`) REFERENCES `entrada` (`id_entrada`),
  CONSTRAINT `item_entrada_ibfk_2` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id_produto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_entrada`
--

LOCK TABLES `item_entrada` WRITE;
/*!40000 ALTER TABLE `item_entrada` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_entrada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_pedido`
--

DROP TABLE IF EXISTS `item_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_pedido` (
  `id_item` int NOT NULL AUTO_INCREMENT,
  `id_pedido` int NOT NULL,
  `id_produto` int NOT NULL,
  `quantidade` int NOT NULL,
  `preco_venda` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_item`),
  KEY `fk_id_pedido` (`id_pedido`),
  KEY `fk_item_produto` (`id_produto`),
  CONSTRAINT `fk_id_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`),
  CONSTRAINT `fk_item_produto` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id_produto`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_pedido`
--

LOCK TABLES `item_pedido` WRITE;
/*!40000 ALTER TABLE `item_pedido` DISABLE KEYS */;
INSERT INTO `item_pedido` VALUES (1,6,11,15,5.40),(2,7,7,20,79.90),(3,8,6,10,45.00),(4,9,15,10,7.99),(5,10,4,10,4.90),(6,10,15,10,7.99),(7,11,12,20,7.80),(8,12,7,5,79.90),(9,13,17,20,5.00),(14,16,21,20,25.00),(15,16,19,20,80.00),(16,17,8,20,6.90),(17,18,12,5,7.80),(18,18,11,15,5.40),(19,19,16,18,15.50),(20,19,4,20,4.90),(21,20,20,30,4.50),(22,20,22,10,20.00);
/*!40000 ALTER TABLE `item_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id_pedido` int NOT NULL AUTO_INCREMENT,
  `cpf_cliente` varchar(11) DEFAULT NULL,
  `data_pedido` date DEFAULT NULL,
  `valor_total` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id_pedido`),
  KEY `fk_pedido_cliente` (`cpf_cliente`),
  CONSTRAINT `fk_pedido_cliente` FOREIGN KEY (`cpf_cliente`) REFERENCES `cliente` (`cpf_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,'39140588888','2026-03-27',34.50),(2,'39140588888','2026-03-29',34.50),(3,'46466565656','2026-03-30',90.00),(4,'19194994994','2026-03-31',35.00),(5,'19194994994','2026-03-31',67.50),(6,'43553454554','2026-03-31',81.00),(7,'39140588888','2026-03-31',1598.00),(8,'19194994994','2026-04-01',450.00),(9,'19194994994','2026-04-01',79.90),(10,'49494949449','2026-05-12',128.90),(11,'99988855566','2026-05-14',156.00),(12,'39140588888','2026-05-14',399.50),(13,'49494949449','2026-05-14',100.00),(16,'54645646566','2026-05-14',2100.00),(17,'54645646566','2026-05-14',138.00),(18,'39140588888','2026-05-14',120.00),(19,'43553454554','2026-05-14',377.00),(20,'99988855566','2026-05-20',335.00);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto` (
  `id_produto` int NOT NULL AUTO_INCREMENT,
  `nome_produto` varchar(50) DEFAULT NULL,
  `marca_produto` varchar(40) DEFAULT NULL,
  `categoria_produto` varchar(30) DEFAULT NULL,
  `preco_produto` decimal(10,2) DEFAULT NULL,
  `quantidade_estoque` int DEFAULT NULL,
  `estoque_minimo` int DEFAULT '5',
  `ativo` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_produto`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (4,'SAas','Brahma','Cerveja',4.90,0,0,1),(6,'fsfsfa','fsfa','ffafd',45.00,15,5,1),(7,'DSDA','OPPOP','Vinho',79.90,0,5,1),(8,'dsasdsaa','fsfa','ewqeq',6.90,0,5,1),(11,'gfgddfgd','hjhgjhg','fdfsdfs',5.40,10,5,1),(12,'sfafafa','sdsadad','sdsadsd',7.80,0,0,1),(15,'ewqeq','eqweq','ewqeqw',7.99,10,5,0),(16,'FDSD','uyuyy','Conhaque',15.50,0,0,1),(17,'Baleeeal','Jupira','Conhaque',5.00,0,5,1),(18,'Caribe','IOIOO','Vinho',80.00,8,5,1),(19,'KLKLK','YUYUYU','Cerveja',80.00,0,5,1),(20,'Skol','Ambev','Cerveja',4.50,15,5,1),(21,'Dreher','Ambev','Conhaque',25.00,0,5,1),(22,'Jurupinga','Ambev','Vinho Branco',20.00,5,5,1);
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nome_usuario` varchar(100) NOT NULL,
  `login_usuario` varchar(50) NOT NULL,
  `senha_usuario` varchar(100) NOT NULL,
  `perfil_usuario` enum('FUNCIONARIO','GERENTE') NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `login_usuario` (`login_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Administrador','admin','123','GERENTE'),(2,'Funcionário Caixa','caixa','123','FUNCIONARIO');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venda`
--

DROP TABLE IF EXISTS `venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venda` (
  `id_venda` int NOT NULL AUTO_INCREMENT,
  `cpf_cliente` varchar(14) NOT NULL,
  `id_produto` int NOT NULL,
  `quantidade` int NOT NULL,
  `preco_venda` decimal(10,2) NOT NULL,
  `total_venda` decimal(10,2) NOT NULL,
  `data_venda` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_venda`),
  KEY `cpf_cliente` (`cpf_cliente`),
  KEY `id_produto` (`id_produto`),
  CONSTRAINT `venda_ibfk_1` FOREIGN KEY (`cpf_cliente`) REFERENCES `cliente` (`cpf_cliente`),
  CONSTRAINT `venda_ibfk_2` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id_produto`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venda`
--

LOCK TABLES `venda` WRITE;
/*!40000 ALTER TABLE `venda` DISABLE KEYS */;
INSERT INTO `venda` VALUES (1,'39155520200',6,10,45.00,450.00,'2026-03-31 15:47:37'),(2,'39140588888',4,20,4.50,90.00,'2026-03-31 15:48:35'),(3,'99988855566',6,10,45.00,450.00,'2026-03-31 16:32:47'),(4,'19194994994',7,20,79.90,1598.00,'2026-04-01 23:22:10'),(5,'54645646566',6,50,45.00,2250.00,'2026-04-02 22:33:18'),(6,'49494949449',15,10,7.99,79.90,'2026-04-07 19:15:18'),(7,'43553454554',16,7,45.90,321.30,'2026-04-07 19:59:48'),(8,'99988855566',7,35,79.90,2796.50,'2026-04-07 20:02:56'),(9,'19194994994',6,15,45.00,675.00,'2026-04-08 11:34:47'),(10,'46466565656',11,137,5.40,739.80,'2026-04-08 11:35:05'),(11,'19194994994',7,25,79.90,1997.50,'2026-04-08 11:37:02'),(12,'46546945695',15,22,7.99,175.78,'2026-04-08 11:56:26'),(13,'19194994994',4,110,4.90,539.00,'2026-04-08 11:57:06'),(14,'54645646566',8,15,6.90,103.50,'2026-04-10 12:47:03'),(15,'19194994994',12,33,7.80,257.40,'2026-04-11 16:44:55'),(16,'99988855566',18,50,80.00,4000.00,'2026-04-11 16:48:10'),(17,'19595959595',7,10,79.90,799.00,'2026-04-22 19:50:26'),(18,'46546945695',17,5,5.00,25.00,'2026-04-22 19:57:11'),(19,'46546945695',18,2,80.00,160.00,'2026-04-22 19:57:32'),(20,'39140588888',19,30,80.00,2400.00,'2026-04-28 20:25:47'),(21,'19194994994',18,10,80.00,800.00,'2026-05-07 20:40:20'),(22,'46546945695',18,10,80.00,800.00,'2026-05-11 19:18:45');
/*!40000 ALTER TABLE `venda` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-10 12:10:07
