-- MySQL dump 10.13  Distrib 5.7.19, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: ethpush
-- ------------------------------------------------------
-- Server version	5.7.19-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `eth_token`
--

DROP TABLE IF EXISTS `eth_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eth_token` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '币名全称',
  `symbol` varchar(255) NOT NULL COMMENT '币名缩写',
  `zh_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `cointype` varchar(255) NOT NULL COMMENT '数字代号',
  `coin` varchar(255) NOT NULL COMMENT '小数位数',
  `contractAccount` varchar(255) NOT NULL COMMENT '数字代号',
  `isPosted` tinyint(1) NOT NULL DEFAULT '0',
  `isValid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_name` (`name`) USING BTREE,
  KEY `token_cointype` (`cointype`),
  KEY `token_symbol` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eth_token`
--

LOCK TABLES `eth_token` WRITE;
/*!40000 ALTER TABLE `eth_token` DISABLE KEYS */;
INSERT INTO `eth_token` VALUES (1,'ETH','ETH','以太','0','1000000000000000000','0x',1,1),(2,'Bytom','BTM','比原链','16','100000000','0xcb97e65f07da24d46bcdd078ebebd7c6e6e3d750',1,1),(3,'MaverickChain','MVC','小牛链','17','1000000000000000000','0xb17df9a3b09583a9bdcf757d6367171476d4d8a3',1,1),(4,'RealEstateChain','RES','RES','20','1000000000000000000','0x815a006b09ba628d6384f726231845b0c537910b',1,1),(5,'GECoin','GEC','游娱宝','15','1000','0x4a536c1ce7ad7f6e8d2e59135e17aef5ef4dd4e6',1,1),(6,'HotExchangeCoin','HTEC','热币','21','1000000000000000000','0xe4089b2e6c1bf4cb3dd267ed3b7fc98362e80e61',1,1),(7,'GameStationElectronicToken','GSET','游戏币','22','1000000000000000000','',1,1),(10,'DBCS','DBCS','域美链','25','1000000000000000000','0xc100014b2bb97f219a684c83825ed14c22b22a88',1,1),(11,'ICTA','ICTA','信讯链','26','1000000000','0x9e2b209afc38b74b3278b4e3e2e61dcefc752bb2',1,1),(12,'DZBN','DZBN','DZBN','27','1','0x12a980327906ccd059123b9ce7bdc8521e146f16',1,1),(13,'FBEI','FBEI','社群积分','28','1','0x46a9d789c221a1026b7f9f1f926ad79109c3bcb7',1,1),(14,'TMAC','TMAC','时间共链','29','1000000000000000000','0xa6858b98e5c14db67ec22c668701b1d33a3de1f7',1,1),(15,'MarryChain','MAC','婚链','30','100000000','0xa0c495426c1994a9f6eba89246c444243e51787e',1,1),(16,'IPFSASIC','IPAC','星际芯片','31','1000000000000000000','0xf06168cadc96dd4b165aed356c888fcaa36e4012',1,1),(17,'RCCC','RCCC','文兴链','32','1000000000000000000','0x33bfd20660eeaf952e8d5bc3236e1918701f17d0',1,1),(18,'ADD','ADD','爱特遗嘱','33','1000000000000000000','0xafcb884eaba89E6c349dF66C7973C052e5b4b281',1,1);
/*!40000 ALTER TABLE `eth_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-29 13:53:45
