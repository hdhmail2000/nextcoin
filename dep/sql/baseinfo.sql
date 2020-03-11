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
-- Table structure for table `baseinfo`
--

DROP TABLE IF EXISTS `baseinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `baseinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `lastScanBlock` int(10) unsigned NOT NULL DEFAULT '0',
  `usersMax` int(10) NOT NULL DEFAULT '1000',
  `lastLoadBlock` int(10) unsigned NOT NULL DEFAULT '0',
  `isLoad` tinyint(1) NOT NULL DEFAULT '1',
  `isScan` tinyint(1) NOT NULL DEFAULT '1',
  `isPost` tinyint(1) NOT NULL DEFAULT '1',
  `rpcHost` varchar(15) CHARACTER SET utf8 NOT NULL COMMENT '币服务器IP',
  `rpcPort` int(10) NOT NULL,
  `withdrawAddr` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '0xabc',
  `isCleanTxs` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标记是否需要定期清空txs数据',
  `storageBlockCount` int(10) NOT NULL DEFAULT '40320' COMMENT 'txs保存的区块数量，之前的清',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `baseinfo`
--

LOCK TABLES `baseinfo` WRITE;
/*!40000 ALTER TABLE `baseinfo` DISABLE KEYS */;
INSERT INTO `baseinfo` VALUES (1,5491906,20160,754800,1,1,1,'127.0.0.1',18759,'0xd1bf71596b22f8e0a5e5617ad8cd606ef9b2e9f1',1,40320);
/*!40000 ALTER TABLE `baseinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-29 13:52:30
