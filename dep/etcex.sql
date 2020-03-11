/*
Navicat MySQL Data Transfer

Source Server         : 币创-平台ETC-187
Source Server Version : 50550
Source Host           : 114.55.252.187:3306
Source Database       : etc

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2016-09-21 14:59:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `accounts`
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
`id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NULL' COMMENT '用户账号' ,
`blockheight`  int(10) UNSIGNED NOT NULL DEFAULT 0 ,
`balance`  varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '0' ,
`hardforkBalance`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' ,
`hardforkHeight`  int(10) NOT NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
UNIQUE INDEX `addru` (`address`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci
AUTO_INCREMENT=2020

;

-- ----------------------------
-- Table structure for `baseinfo`
-- ----------------------------
DROP TABLE IF EXISTS `baseinfo`;
CREATE TABLE `baseinfo` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`lastScanBlock`  int(10) UNSIGNED NOT NULL DEFAULT 0 ,
`usersMax`  int(10) NOT NULL DEFAULT 1000 ,
`lastLoadBlock`  int(10) UNSIGNED NOT NULL DEFAULT 0 ,
`isLoad`  tinyint(1) NOT NULL DEFAULT 1 ,
`isScan`  tinyint(1) NOT NULL DEFAULT 1 ,
`isPost`  tinyint(1) NOT NULL DEFAULT 1 ,
`rpcHost`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币服务器IP' ,
`rpcPort`  int(10) NOT NULL ,
`withdrawAddr`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0xabc' ,
`isCleanTxs`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '标记是否需要定期清空txs数据�?' ,
`storageBlockCount`  int(10) NOT NULL DEFAULT 40320 COMMENT 'txs保存的区块数量，之前的清�?' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci
AUTO_INCREMENT=2

;

-- ----------------------------
-- Table structure for `coldtx`
-- ----------------------------
DROP TABLE IF EXISTS `coldtx`;
CREATE TABLE `coldtx` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`txHash`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`addrFrom`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`addrTo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`value`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ether`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`confirmation`  int(10) NOT NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci
AUTO_INCREMENT=12

;

-- ----------------------------
-- Table structure for `txs`
-- ----------------------------
DROP TABLE IF EXISTS `txs`;
CREATE TABLE `txs` (
`id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`txHash`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`isFilled`  tinyint(1) NOT NULL DEFAULT 0 ,
`blockHash`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`blockNumber`  bigint(25) NOT NULL ,
`addrFrom`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`addrTo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`value`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' ,
`ether`  double(30,18) NOT NULL DEFAULT 0.000000000000000000 ,
`gas`  bigint(25) NULL DEFAULT NULL ,
`gasPrice`  bigint(25) NULL DEFAULT 0 ,
`nonce`  int(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000 ,
`confirmation`  int(10) UNSIGNED NOT NULL DEFAULT 0 ,
`isPosted`  tinyint(1) NOT NULL DEFAULT 0 ,
`isDeposit`  tinyint(1) NOT NULL DEFAULT 0 ,
`resultCode`  int(10) NULL DEFAULT NULL COMMENT '推送充值交易返回码' ,
`isSplit`  tinyint(1) NOT NULL DEFAULT 0 ,
PRIMARY KEY (`id`),
UNIQUE INDEX `tx_id` (`txHash`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=296516

;

-- ----------------------------
-- Auto increment value for `accounts`
-- ----------------------------
ALTER TABLE `accounts` AUTO_INCREMENT=2020;

-- ----------------------------
-- Auto increment value for `baseinfo`
-- ----------------------------
ALTER TABLE `baseinfo` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for `coldtx`
-- ----------------------------
ALTER TABLE `coldtx` AUTO_INCREMENT=12;

-- ----------------------------
-- Auto increment value for `txs`
-- ----------------------------
ALTER TABLE `txs` AUTO_INCREMENT=296516;