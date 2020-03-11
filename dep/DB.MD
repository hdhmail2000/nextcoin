# 数据库

## token表
```mysql
CREATE TABLE `eth_token` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '币名全称',
  `symbol` varchar(255) NOT NULL COMMENT '币名缩写',
  `zh_name` varchar(255) COMMENT '中文名',
  `cointype` varchar(255) NOT NULL COMMENT '数字代号',
  `coin` varchar(255) NOT NULL COMMENT '小数位数',
  `contractAccount` varchar(255) NOT NULL COMMENT '数字代号',
  `isPosted` tinyint(1) NOT NULL DEFAULT '0',
  `isValid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_name` (`name`) USING BTREE,
  INDEX token_cointype(cointype),  
  INDEX token_symbol(symbol)  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```


```mysql
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```