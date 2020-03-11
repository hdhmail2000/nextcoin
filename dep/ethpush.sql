-- MySQL dump 10.13  Distrib 5.7.19, for Linux (x86_64)
--
-- Host: localhost    Database: ethpush
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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT 'NULL' COMMENT '用户账号',
  `blockheight` int(10) unsigned NOT NULL DEFAULT '0',
  `balance` double(30,8) NOT NULL DEFAULT '0.00000000',
  `hardforkBalance` double(30,8) NOT NULL DEFAULT '0.00000000',
  `hardforkHeight` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `addru` (`address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coldtx`
--

DROP TABLE IF EXISTS `coldtx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coldtx` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `txHash` varchar(255) CHARACTER SET utf8 NOT NULL,
  `addrFrom` varchar(255) CHARACTER SET utf8 NOT NULL,
  `addrTo` varchar(255) CHARACTER SET utf8 NOT NULL,
  `value` varchar(30) CHARACTER SET utf8 NOT NULL,
  `ether` varchar(30) CHARACTER SET utf8 NOT NULL,
  `confirmation` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `txs`
--

DROP TABLE IF EXISTS `txs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `txHash` varchar(255) NOT NULL,
  `isFilled` tinyint(1) NOT NULL DEFAULT '0',
  `blockHash` varchar(255) NOT NULL,
  `blockNumber` bigint(25) NOT NULL,
  `addrFrom` varchar(255) NOT NULL,
  `addrTo` varchar(255) NOT NULL,
  `value` varchar(25) NOT NULL DEFAULT '0',
  `ether` double(30,18) NOT NULL DEFAULT '0.000000000000000000',
  `gas` bigint(25) DEFAULT NULL,
  `gasPrice` bigint(25) DEFAULT '0',
  `nonce` int(10) unsigned zerofill DEFAULT '0000000000',
  `confirmation` int(10) unsigned NOT NULL DEFAULT '0',
  `isPosted` tinyint(1) NOT NULL DEFAULT '0',
  `isDeposit` tinyint(1) NOT NULL DEFAULT '0',
  `resultCode` int(10) DEFAULT NULL COMMENT '推送充值交易返回码',
  `assetType` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tx_id` (`txHash`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-05 22:21:20
