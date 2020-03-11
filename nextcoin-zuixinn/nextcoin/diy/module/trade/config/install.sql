
DROP TABLE IF EXISTS  `e_1_trade`;
CREATE TABLE `e_1_trade` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `title` varchar(255) DEFAULT NULL COMMENT '主题',
  `thumb` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `keywords` varchar(255) DEFAULT NULL COMMENT '关键字',
  `description` text COMMENT '描述',
  `hits` mediumint(8) unsigned DEFAULT NULL COMMENT '浏览数',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者id',
  `author` varchar(50) NOT NULL COMMENT '作者名称',
  `status` tinyint(2) NOT NULL COMMENT '状态',
  `url` varchar(255) DEFAULT NULL COMMENT '地址',
  `link_id` int(10) NOT NULL DEFAULT '0' COMMENT '同步id',
  `tableid` smallint(5) unsigned NOT NULL COMMENT '附表id',
  `inputip` varchar(15) DEFAULT NULL COMMENT '录入者ip',
  `inputtime` int(10) unsigned NOT NULL COMMENT '录入时间',
  `updatetime` int(10) unsigned NOT NULL COMMENT '更新时间',
  `comments` int(10) unsigned NOT NULL COMMENT '评论数量',
  `favorites` int(10) unsigned NOT NULL COMMENT '收藏数量',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  `order_price` decimal(10,2) DEFAULT NULL,
  `order_volume` decimal(14,6) DEFAULT NULL,
  `deal_type` tinyint(1) DEFAULT '0',
  `order_city` mediumint(8) unsigned DEFAULT NULL,
  `min_value` decimal(14,6) DEFAULT NULL,
  `max_value` decimal(14,6) DEFAULT NULL,
  `symbol` int(5) DEFAULT '0',
  `symbol_name` varchar(10) DEFAULT NULL COMMENT '简称',
  `pay_type` varchar(255) DEFAULT NULL,
  `trade_total` decimal(20,6) DEFAULT NULL,
  `success_total` decimal(20,6) DEFAULT '0.000000',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`,`updatetime`),
  KEY `link_id` (`link_id`),
  KEY `comments` (`comments`),
  KEY `favorites` (`favorites`),
  KEY `status` (`status`),
  KEY `hits` (`hits`),
  KEY `displayorder` (`displayorder`,`updatetime`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='主表';

insert into `e_1_trade`(`id`,`catid`,`title`,`thumb`,`keywords`,`description`,`hits`,`uid`,`author`,`status`,`url`,`link_id`,`tableid`,`inputip`,`inputtime`,`updatetime`,`comments`,`favorites`,`displayorder`,`order_price`,`order_volume`,`deal_type`,`order_city`,`min_value`,`max_value`,`symbol`,`symbol_name`,`pay_type`,`trade_total`,`success_total`) values
('1','0','18580967515',null,null,null,null,'2','18580967515','10',null,'0','0','113.249.193.141','1531389092','1531389092','0','0','0',10.00,10.000000,'1',null,1.000000,10.000000,'2','ETH','支付宝,微信',10.000000,0.000000),
('2','0','18580967515',null,null,null,null,'2','18580967515','9',null,'0','0','113.249.193.141','1531392799','1531392799','0','0','0',5.00,11.000000,'1',null,1.000000,11.000000,'1','BTC','支付宝,微信',11.000000,0.000000),
('3','0','18580967515',null,null,null,null,'2','18580967515','9',null,'0','0','113.249.193.141','1531392833','1531392833','0','0','0',10.00,100.000000,'2',null,1.000000,100.000000,'2','ETH','银行卡,支付宝,微信',100.000000,0.000000),
('4','0','644795057@qq.com',null,null,null,null,'3','644795057@qq.com','9',null,'0','0','113.249.193.247','1531555328','1531555328','0','0','0',11.00,15.000000,'1',null,1.000000,15.000000,'2','ETH','支付宝',15.000000,0.000000),
('5','0','644795057@qq.com',null,null,null,null,'3','644795057@qq.com','9',null,'0','0','113.249.193.247','1531556379','1531556379','0','0','0',10.00,99.000000,'1',null,1.000000,100.000000,'1','BTC','支付宝',100.000000,0.000000),
('6','0','644795057@qq.com',null,null,null,null,'3','644795057@qq.com','9',null,'0','0','113.249.193.247','1531556408','1531556408','0','0','0',100.00,1000.000000,'1',null,50.000000,500.000000,'2','ETH','支付宝',1000.000000,0.000000),
('7','0','644795057@qq.com',null,null,null,null,'3','644795057@qq.com','9',null,'0','0','113.249.193.247','1531556454','1531556454','0','0','0',50.00,88.000000,'1',null,40.000000,80.000000,'1','BTC','支付宝',88.000000,0.000000),
('8','0','644795057@qq.com',null,null,null,null,'3','644795057@qq.com','9',null,'0','0','113.249.193.247','1531556479','1531556479','0','0','0',80.00,50.000000,'1',null,4.000000,30.000000,'1','BTC','支付宝',50.000000,0.000000),
('9','0','644795057@qq.com',null,null,null,null,'3','644795057@qq.com','9',null,'0','0','113.249.193.247','1531556528','1531556528','0','0','0',10.00,10000.000000,'2',null,100.000000,5000.000000,'1','BTC','支付宝',10000.000000,0.000000),
('10','0','18580967515',null,null,null,null,'2','18580967515','9',null,'0','0','113.249.193.247','1531556835','1531556835','0','0','0',10.00,50.000000,'2',null,1.000000,50.000000,'2','ETH','银行卡,支付宝,微信',50.000000,0.000000),
('11','0','18580967515',null,null,null,null,'10028','18580967515','10',null,'0','0','113.249.195.180','1532941305','1532941305','0','0','0',1.00,5.000000,'1',null,1.000000,10.000000,'33','USDT','支付宝',10.000000,5.000000),
('12','0','18883966624',null,null,null,null,'20307','18883966624','10',null,'0','0','100.117.231.98','1533207996','1533207996','0','0','0',4100.32,100.000000,'1',null,10.000000,100.000000,'38','ETH','支付宝',100.000000,0.000000),
('13','0','18883966624',null,null,null,null,'20307','18883966624','9',null,'0','0','100.117.231.94','1533209785','1533209785','0','0','0',4300.00,9.000000,'1',null,1.000000,10.000000,'38','ETH','支付宝',10.000000,0.000000),
('14','0','18883966624',null,null,null,null,'20307','18883966624','9',null,'0','0','100.117.231.90','1533209808','1533209808','0','0','0',4235.00,90.000000,'2',null,10.000000,100.000000,'38','ETH','支付宝',100.000000,10.000000),
('15','0','18883966624',null,null,null,null,'20307','18883966624','10',null,'0','0','100.117.231.45','1533209840','1533209840','0','0','0',6.32,100.000000,'1',null,10.000000,100.000000,'40','USDT','支付宝',100.000000,0.000000),
('16','0','18623425027',null,null,null,null,'20309','18623425027','9',null,'0','0','100.117.231.39','1533211024','1533211024','0','0','0',4532.00,9.000000,'1',null,1.000000,10.000000,'38','ETH','银行卡,支付宝',10.000000,1.000000),
('17','0','18580967515',null,null,null,null,'20314','18580967515','9',null,'0','0','100.117.231.96','1533267140','1533267140','0','0','0',100.00,50.000000,'2',null,1.000000,50.000000,'40','USDT','支付宝',50.000000,0.000000),
('18','0','18580967515',null,null,null,null,'20314','18580967515','9',null,'0','0','100.117.231.93','1533280266','1533280266','0','0','0',10.00,8.000000,'1',null,1.000000,10.000000,'38','ETH','支付宝',10.000000,1.000000),
('19','0','18580967515',null,null,null,null,'20314','18580967515','9',null,'0','0','100.117.231.29','1533362652','1533362652','0','0','0',10.00,10.000000,'1',null,1.000000,10.000000,'38','ETH','支付宝',10.000000,0.000000),
('20','0','18580967515',null,null,null,null,'10035','18580967515','9',null,'0','0','100.117.230.197','1533380925','1533380925','0','0','0',10.00,0.000000,'2',null,1.000000,10.000000,'33','USDT','支付宝',10.000000,0.000000),
('21','0','13895076192',null,null,null,null,'10016','13895076192','10',null,'0','0','100.117.230.232','1533540286','1533540286','0','0','0',4000.00,1.000000,'1',null,2.000000,20.000000,'31','ETH','银行卡',20.000000,9.000000),
('22','0','13983606341',null,null,null,null,'10015','13983606341','9',null,'0','0','100.117.230.202','1533547217','1533547217','0','0','0',10.00,24.000000,'1',null,1.000000,50.000000,'31','ETH','支付宝',50.000000,0.000000),
('23','0','张轲',null,null,null,null,'10017','张轲','10',null,'0','0','100.117.230.150','1533549966','1533549966','0','0','0',400.00,12.000000,'2',null,2.000000,20.000000,'31','ETH','支付宝',20.000000,0.000000),
('24','0','张丰良',null,null,null,null,'10016','张丰良','10',null,'0','0','100.117.230.194','1533550246','1533550246','0','0','0',3000.00,0.000000,'2',null,2.000000,20.000000,'31','ETH','银行卡',20.000000,20.000000),
('25','0','彭俊杰',null,null,null,null,'10035','彭俊杰','9',null,'0','0','100.117.230.197','1533550292','1533550292','0','0','0',2818.84,-160.000000,'2',null,10.000000,100.000000,'31','ETH','银行卡,支付宝',100.000000,0.000000),
('26','0','张丰良',null,null,null,null,'10016','张丰良','10',null,'0','0','100.117.230.253','1533551119','1533551119','0','0','0',3200.00,0.000000,'2',null,2.000000,20.000000,'31','ETH','银行卡',20.000000,0.000000),
('27','0','张丰良',null,null,null,null,'10016','张丰良','10',null,'0','0','100.117.230.248','1533551371','1533551371','0','0','0',2000.00,0.000000,'2',null,2.000000,15.000000,'31','ETH','支付宝',15.000000,0.000000),
('28','0','彭俊杰',null,null,null,null,'10035','彭俊杰','9',null,'0','0','100.117.230.172','1533559362','1533559362','0','0','0',10.00,100.000000,'1',null,1.000000,100.000000,'31','ETH','银行卡',100.000000,0.000000),
('29','0','张轲',null,null,null,null,'10017','张轲','10',null,'0','0','100.117.230.155','1533623323','1533623323','0','0','0',3000.00,96.000000,'1',null,1.000000,20.000000,'31','ETH','支付宝',100.000000,0.000000),
('30','0','陈奎',null,null,null,null,'10038','陈奎','10',null,'0','0','100.117.230.130','1533628312','1533628312','0','0','0',10.00,-50.000000,'2',null,1.000000,30.000000,'31','ETH','支付宝',20.000000,0.000000),
('31','0','陈奎',null,null,null,null,'10038','陈奎','9',null,'0','0','100.117.230.189','1533628871','1533628871','0','0','0',20.00,0.000000,'2',null,1.000000,10.000000,'31','ETH','支付宝',10.000000,2.000000),
('32','0','陈奎',null,null,null,null,'10038','陈奎','9',null,'0','0','100.117.230.177','1533629075','1533629075','0','0','0',100.00,0.000000,'2',null,1.000000,20.000000,'31','ETH','银行卡',20.000000,11.000000),
('33','0','张丰良',null,null,null,null,'10016','张丰良','9',null,'0','0','100.117.230.180','1533639847','1533639847','0','0','0',3000.00,15.000000,'1',null,1.000000,20.000000,'31','ETH','银行卡',20.000000,10.000000),
('34','0','彭俊杰',null,null,null,null,'10035','彭俊杰','9',null,'0','0','100.117.230.208','1533645591','1533645591','0','0','0',2.30,98.000000,'1',null,1.000000,100.000000,'31','ETH','银行卡',100.000000,1.000000),
('35','0','彭俊杰',null,null,null,null,'10035','彭俊杰','9',null,'0','0','100.117.230.177','1533650664','1533650664','0','0','0',2787.00,11.000000,'1',null,1.000000,11.000000,'31','ETH','银行卡',11.000000,0.000000),
('36','0','陈奎',null,null,null,null,'10038','陈奎','9',null,'0','0','100.117.230.233','1533651696','1533651696','0','0','0',10.00,19.000000,'1',null,1.000000,10.000000,'31','ETH','支付宝',20.000000,1.000000),
('37','0','张轲',null,null,null,null,'10017','张轲','10',null,'0','0','100.117.230.254','1533694737','1533694737','0','0','0',3000.00,3.000000,'1',null,1.000000,6.000000,'31','ETH','支付宝',6.000000,18.000000),
('38','0','彭俊杰',null,null,null,null,'10035','彭俊杰','9',null,'0','0','100.117.230.168','1533702841','1533702841','0','0','0',0.23,0.000000,'1',null,1.000000,10.000000,'36','F1','银行卡,支付宝',10.000000,0.000000),
('39','0','彭俊杰',null,null,null,null,'10035','彭俊杰','9',null,'0','0','100.117.230.166','1533702865','1533702865','0','0','0',2787.98,0.000000,'2',null,1.000000,10.000000,'31','ETH','银行卡,支付宝',10.000000,0.000000),
('40','0','张轲',null,null,null,'1','10017','张轲','10',null,'0','0','100.117.230.204','1533724292','1533724292','0','0','0',6500.00,9.000000,'1',null,1.000000,10.000000,'32','BTC','支付宝',10.000000,1.000000),
('41','0','张轲',null,null,null,null,'10017','张轲','9',null,'0','0','100.117.230.250','1533888674','1533888674','0','0','0',2800.00,6.000000,'1',null,1.000000,10.000000,'31','ETH','支付宝',10.000000,0.000000);
DROP TABLE IF EXISTS  `e_1_trade_buy`;
CREATE TABLE `e_1_trade_buy` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cid` int(10) unsigned NOT NULL COMMENT '文档id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT 'uid',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `thumb` varchar(255) NOT NULL COMMENT '缩略图',
  `url` varchar(255) NOT NULL COMMENT 'URL地址',
  `score` int(10) unsigned NOT NULL COMMENT '使用虚拟币',
  `inputtime` int(10) unsigned NOT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`),
  KEY `cid` (`cid`,`uid`,`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='主题购买记录表';

DROP TABLE IF EXISTS  `e_1_trade_category`;
CREATE TABLE `e_1_trade_category` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `pid` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '上级id',
  `pids` varchar(255) NOT NULL COMMENT '所有上级id',
  `name` varchar(30) NOT NULL COMMENT '栏目名称',
  `letter` char(1) NOT NULL COMMENT '首字母',
  `dirname` varchar(30) NOT NULL COMMENT '栏目目录',
  `pdirname` varchar(100) NOT NULL COMMENT '上级目录',
  `child` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否有下级',
  `childids` text NOT NULL COMMENT '下级所有id',
  `thumb` varchar(255) NOT NULL COMMENT '栏目图片',
  `show` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否显示',
  `permission` text COMMENT '会员权限',
  `setting` text NOT NULL COMMENT '属性配置',
  `displayorder` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `show` (`show`),
  KEY `module` (`pid`,`displayorder`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目表';

insert into `e_1_trade_category`(`id`,`pid`,`pids`,`name`,`letter`,`dirname`,`pdirname`,`child`,`childids`,`thumb`,`show`,`permission`,`setting`,`displayorder`) values
('1','0','0','交易列表','j','jylb','','0','1','','1',null,'{"edit":"0","linkurl":"","seo":{"show_title":"[\\u7b2c{page}\\u9875{join}]{title}{join}{catpname}{join}{modname}{join}{SITE_NAME}","list_title":"[\\u7b2c{page}\\u9875{join}]{catpname}{join}{modname}{join}{SITE_NAME}","list_keywords":"","list_description":""},"template":{"pagesize":"20","mpagesize":"20","show":"show.html","category":"category.html","list":"list.html","search":"search.html"}}','0');
DROP TABLE IF EXISTS  `e_1_trade_category_data`;
CREATE TABLE `e_1_trade_category_data` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目附加表';

DROP TABLE IF EXISTS  `e_1_trade_category_data_0`;
CREATE TABLE `e_1_trade_category_data_0` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目附加表';

DROP TABLE IF EXISTS  `e_1_trade_comment_data_0`;
CREATE TABLE `e_1_trade_comment_data_0` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `cid` int(10) unsigned NOT NULL COMMENT '关联id',
  `uid` mediumint(8) unsigned DEFAULT '0' COMMENT '会员ID',
  `url` varchar(250) DEFAULT NULL COMMENT '主题地址',
  `title` varchar(250) DEFAULT NULL COMMENT '主题名称',
  `author` varchar(250) DEFAULT NULL COMMENT '评论者',
  `content` text COMMENT '评论内容',
  `support` int(10) unsigned DEFAULT '0' COMMENT '支持数',
  `oppose` int(10) unsigned DEFAULT '0' COMMENT '反对数',
  `avgsort` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '平均分',
  `sort1` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `sort2` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `sort3` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `sort4` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `sort5` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `sort6` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `sort7` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `sort8` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `sort9` tinyint(1) unsigned DEFAULT '0' COMMENT '评分值',
  `reply` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '回复id',
  `in_reply` tinyint(1) unsigned DEFAULT '0' COMMENT '是否存在回复',
  `status` smallint(1) unsigned DEFAULT '0' COMMENT '审核状态',
  `inputip` varchar(50) DEFAULT NULL COMMENT '录入者ip',
  `inputtime` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '录入时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `cid` (`cid`),
  KEY `reply` (`reply`),
  KEY `support` (`support`),
  KEY `oppose` (`oppose`),
  KEY `avgsort` (`avgsort`),
  KEY `status` (`status`),
  KEY `aa` (`cid`,`status`,`inputtime`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论内容表';

DROP TABLE IF EXISTS  `e_1_trade_comment_index`;
CREATE TABLE `e_1_trade_comment_index` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cid` int(10) unsigned NOT NULL COMMENT '内容id',
  `support` int(10) unsigned DEFAULT '0' COMMENT '支持数',
  `oppose` int(10) unsigned DEFAULT '0' COMMENT '反对数',
  `comments` int(10) unsigned DEFAULT '0' COMMENT '评论数',
  `avgsort` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '平均分',
  `sort1` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `sort2` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `sort3` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `sort4` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `sort5` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `sort6` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `sort7` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `sort8` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `sort9` decimal(4,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '选项分数',
  `tableid` smallint(5) unsigned DEFAULT '0' COMMENT '附表id',
  PRIMARY KEY (`id`),
  KEY `cid` (`cid`),
  KEY `support` (`support`),
  KEY `oppose` (`oppose`),
  KEY `comments` (`comments`),
  KEY `avgsort` (`avgsort`),
  KEY `tableid` (`tableid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论索引表';

insert into `e_1_trade_comment_index`(`id`,`cid`,`support`,`oppose`,`comments`,`avgsort`,`sort1`,`sort2`,`sort3`,`sort4`,`sort5`,`sort6`,`sort7`,`sort8`,`sort9`,`tableid`) values
('1','40','0','0','0',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,'0');
DROP TABLE IF EXISTS  `e_1_trade_comment_my`;
CREATE TABLE `e_1_trade_comment_my` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cid` int(10) unsigned NOT NULL COMMENT '内容id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT 'uid',
  `title` varchar(250) DEFAULT NULL COMMENT '内容标题',
  `url` varchar(250) DEFAULT NULL COMMENT 'URL地址',
  `comments` int(10) unsigned DEFAULT '0' COMMENT '评论数量',
  PRIMARY KEY (`id`),
  KEY `cid` (`cid`),
  KEY `uid` (`uid`),
  KEY `comments` (`comments`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='我的评论表';

DROP TABLE IF EXISTS  `e_1_trade_data_0`;
CREATE TABLE `e_1_trade_data_0` (
  `id` int(10) unsigned NOT NULL,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `content` mediumtext COMMENT '内容',
  UNIQUE KEY `id` (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='附表';

insert into `e_1_trade_data_0`(`id`,`uid`,`catid`,`content`) values
('1','1','1',null),
('2','1','1',null);
DROP TABLE IF EXISTS  `e_1_trade_draft`;
CREATE TABLE `e_1_trade_draft` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned NOT NULL COMMENT '内容id',
  `eid` int(10) DEFAULT NULL COMMENT '扩展id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `content` mediumtext NOT NULL COMMENT '具体内容',
  `inputtime` int(10) unsigned NOT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`),
  KEY `eid` (`eid`),
  KEY `uid` (`uid`),
  KEY `cid` (`cid`),
  KEY `catid` (`catid`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='内容草稿表';

DROP TABLE IF EXISTS  `e_1_trade_favorite`;
CREATE TABLE `e_1_trade_favorite` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cid` int(10) unsigned NOT NULL COMMENT '文档id',
  `eid` int(10) unsigned DEFAULT NULL COMMENT '扩展id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT 'uid',
  `url` varchar(255) NOT NULL COMMENT 'URL地址',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `inputtime` int(10) unsigned NOT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `cid` (`cid`),
  KEY `eid` (`eid`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收藏夹表';

DROP TABLE IF EXISTS  `e_1_trade_flag`;
CREATE TABLE `e_1_trade_flag` (
  `flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '文档标记id',
  `id` int(10) unsigned NOT NULL COMMENT '文档内容id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` tinyint(3) unsigned NOT NULL COMMENT '栏目id',
  KEY `flag` (`flag`,`id`,`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标记表';

DROP TABLE IF EXISTS  `e_1_trade_hits`;
CREATE TABLE `e_1_trade_hits` (
  `id` int(10) unsigned NOT NULL COMMENT '文章id',
  `hits` int(10) unsigned NOT NULL COMMENT '总点击数',
  `day_hits` int(10) unsigned NOT NULL COMMENT '本日点击',
  `week_hits` int(10) unsigned NOT NULL COMMENT '本周点击',
  `month_hits` int(10) unsigned NOT NULL COMMENT '本月点击',
  `year_hits` int(10) unsigned NOT NULL COMMENT '年点击量',
  UNIQUE KEY `id` (`id`),
  KEY `day_hits` (`day_hits`),
  KEY `week_hits` (`week_hits`),
  KEY `month_hits` (`month_hits`),
  KEY `year_hits` (`year_hits`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='时段点击量统计';

insert into `e_1_trade_hits`(`id`,`hits`,`day_hits`,`week_hits`,`month_hits`,`year_hits`) values
('40','1','1','2','2','1');
DROP TABLE IF EXISTS  `e_1_trade_html`;
CREATE TABLE `e_1_trade_html` (
  `id` bigint(18) unsigned NOT NULL AUTO_INCREMENT,
  `rid` int(10) unsigned NOT NULL COMMENT '相关id',
  `cid` int(10) unsigned NOT NULL COMMENT '内容id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `type` tinyint(1) unsigned NOT NULL COMMENT '文件类型',
  `catid` tinyint(3) unsigned NOT NULL COMMENT '栏目id',
  `filepath` text NOT NULL COMMENT '文件地址',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `rid` (`rid`),
  KEY `cid` (`cid`),
  KEY `type` (`type`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='html文件存储表';

DROP TABLE IF EXISTS  `e_1_trade_index`;
CREATE TABLE `e_1_trade_index` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `status` tinyint(2) NOT NULL COMMENT '审核状态',
  `inputtime` int(10) unsigned NOT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`),
  KEY `status` (`status`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='内容索引表';

insert into `e_1_trade_index`(`id`,`uid`,`catid`,`status`,`inputtime`) values
('1','1','1','9','1531375789'),
('2','1','1','9','1531375930');
DROP TABLE IF EXISTS  `e_1_trade_recycle`;
CREATE TABLE `e_1_trade_recycle` (
  `id` int(10) unsigned NOT NULL,
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `content` mediumtext NOT NULL COMMENT '具体内容',
  `inputtime` int(10) unsigned NOT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `catid` (`catid`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='回收站表';

DROP TABLE IF EXISTS  `e_1_trade_search`;
CREATE TABLE `e_1_trade_search` (
  `id` varchar(32) NOT NULL,
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `params` text NOT NULL COMMENT '参数数组',
  `keyword` varchar(255) NOT NULL COMMENT '关键字',
  `contentid` mediumtext NOT NULL COMMENT 'id集合',
  `inputtime` int(10) unsigned NOT NULL COMMENT '搜索时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `catid` (`catid`),
  KEY `keyword` (`keyword`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='搜索表';

DROP TABLE IF EXISTS  `e_1_trade_search_index`;
CREATE TABLE `e_1_trade_search_index` (
  `id` varchar(32) NOT NULL,
  `cid` int(10) unsigned NOT NULL COMMENT '文档Id',
  `inputtime` int(10) unsigned NOT NULL COMMENT '搜索时间',
  KEY `id` (`id`),
  KEY `cid` (`cid`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='搜索索引表';

DROP TABLE IF EXISTS  `e_1_trade_tag`;
CREATE TABLE `e_1_trade_tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT 'tag名称',
  `code` varchar(200) NOT NULL COMMENT 'tag代码（拼音）',
  `hits` mediumint(8) unsigned NOT NULL COMMENT '点击量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `letter` (`code`,`hits`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='Tag标签表';

DROP TABLE IF EXISTS  `e_1_trade_verify`;
CREATE TABLE `e_1_trade_verify` (
  `id` int(10) unsigned NOT NULL,
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `author` varchar(50) NOT NULL COMMENT '作者',
  `status` tinyint(2) NOT NULL COMMENT '审核状态',
  `content` mediumtext NOT NULL COMMENT '具体内容',
  `backuid` mediumint(8) unsigned NOT NULL COMMENT '操作人uid',
  `backinfo` text NOT NULL COMMENT '操作退回信息',
  `inputtime` int(10) unsigned NOT NULL COMMENT '录入时间',
  UNIQUE KEY `id` (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`),
  KEY `status` (`status`),
  KEY `inputtime` (`inputtime`),
  KEY `backuid` (`backuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='内容审核表';

DROP TABLE IF EXISTS  `e_1_trading_mining_log`;
CREATE TABLE `e_1_trading_mining_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(32) DEFAULT NULL COMMENT '交易挖矿日期',
  `inputtime` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='交易挖矿记录';

insert into `e_1_trading_mining_log`(`id`,`date`,`inputtime`) values
('2','2018-08-02','2018-08-02 16:27:11'),
('5','2018-08-04','2018-08-04 18:50:07'),
('6','2018-08-04','2018-08-04 18:56:29');
SET FOREIGN_KEY_CHECKS = 1;

