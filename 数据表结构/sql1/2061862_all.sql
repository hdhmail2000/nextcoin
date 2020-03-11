SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS  `activity_financial_statistics`;
CREATE TABLE `activity_financial_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) DEFAULT NULL,
  `record_number` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `coin_id` int(11) DEFAULT NULL,
  `amount` decimal(24,10) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `activity_share_coin_info`;
CREATE TABLE `activity_share_coin_info` (
  `id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `batch` int(11) DEFAULT NULL,
  `login_name` varchar(32) DEFAULT NULL,
  `ranking` int(11) DEFAULT NULL,
  `PV` int(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `activity_share_coin_type`;
CREATE TABLE `activity_share_coin_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `batch` int(11) DEFAULT NULL,
  `coin_id` int(11) DEFAULT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `capital_recharge_match`;
CREATE TABLE `capital_recharge_match` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(64) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `amount` decimal(24,10) DEFAULT NULL,
  `source` int(11) DEFAULT NULL,
  `balance` decimal(24,10) DEFAULT NULL,
  `match_order_id` int(11) DEFAULT NULL,
  `batch_no` bigint(11) DEFAULT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `gmt_pay` datetime DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `version` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `commission_record`;
CREATE TABLE `commission_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `introuid` bigint(20) NOT NULL COMMENT '推荐人id',
  `coinid` int(11) NOT NULL COMMENT '币种id',
  `amount` decimal(24,10) unsigned DEFAULT NULL COMMENT '金额',
  `status` tinyint(1) unsigned DEFAULT '1' COMMENT '状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(225) DEFAULT NULL COMMENT '备注',
  `coinname` varchar(32) DEFAULT NULL COMMENT '币种名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45688 DEFAULT CHARSET=utf8 COMMENT='佣金记录';

DROP TABLE IF EXISTS  `e_1_block`;
CREATE TABLE `e_1_block` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `code` varchar(100) NOT NULL COMMENT '别名',
  `content` text NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资料块表';

DROP TABLE IF EXISTS  `e_1_candy_buy`;
CREATE TABLE `e_1_candy_buy` (
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

DROP TABLE IF EXISTS  `e_1_candy`;
CREATE TABLE `e_1_candy` (
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
  `coin_id` int(10) DEFAULT '0',
  `circulation` int(10) DEFAULT '0',
  `received` decimal(20,8) DEFAULT NULL,
  `dailyreceipt` decimal(20,8) DEFAULT NULL,
  `lqfs` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`,`updatetime`),
  KEY `link_id` (`link_id`),
  KEY `comments` (`comments`),
  KEY `favorites` (`favorites`),
  KEY `status` (`status`),
  KEY `hits` (`hits`),
  KEY `displayorder` (`displayorder`,`updatetime`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='主表';

DROP TABLE IF EXISTS  `e_1_candy_category`;
CREATE TABLE `e_1_candy_category` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `pid` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '上级id',
  `pids` varchar(255) NOT NULL COMMENT '所有上级id',
  `name` varchar(200) NOT NULL COMMENT '栏目名称',
  `letter` char(1) NOT NULL COMMENT '首字母',
  `dirname` varchar(200) NOT NULL COMMENT '栏目目录',
  `pdirname` varchar(200) NOT NULL COMMENT '上级目录',
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

DROP TABLE IF EXISTS  `e_1_candy_category_data`;
CREATE TABLE `e_1_candy_category_data` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目附加表';

DROP TABLE IF EXISTS  `e_1_candy_category_data_0`;
CREATE TABLE `e_1_candy_category_data_0` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目附加表';

DROP TABLE IF EXISTS  `e_1_candy_comment_data_0`;
CREATE TABLE `e_1_candy_comment_data_0` (
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

DROP TABLE IF EXISTS  `e_1_candy_comment_index`;
CREATE TABLE `e_1_candy_comment_index` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论索引表';

DROP TABLE IF EXISTS  `e_1_candy_comment_my`;
CREATE TABLE `e_1_candy_comment_my` (
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

DROP TABLE IF EXISTS  `e_1_candy_data_0`;
CREATE TABLE `e_1_candy_data_0` (
  `id` int(10) unsigned NOT NULL,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `content` mediumtext COMMENT '内容',
  UNIQUE KEY `id` (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='附表';

DROP TABLE IF EXISTS  `e_1_candy_draft`;
CREATE TABLE `e_1_candy_draft` (
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

DROP TABLE IF EXISTS  `e_1_candy_favorite`;
CREATE TABLE `e_1_candy_favorite` (
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

DROP TABLE IF EXISTS  `e_1_candy_flag`;
CREATE TABLE `e_1_candy_flag` (
  `flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '文档标记id',
  `id` int(10) unsigned NOT NULL COMMENT '文档内容id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` tinyint(3) unsigned NOT NULL COMMENT '栏目id',
  KEY `flag` (`flag`,`id`,`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标记表';

DROP TABLE IF EXISTS  `e_1_candy_hits`;
CREATE TABLE `e_1_candy_hits` (
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

DROP TABLE IF EXISTS  `e_1_candy_html`;
CREATE TABLE `e_1_candy_html` (
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

DROP TABLE IF EXISTS  `e_1_candy_index`;
CREATE TABLE `e_1_candy_index` (
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

DROP TABLE IF EXISTS  `e_1_candy_recycle`;
CREATE TABLE `e_1_candy_recycle` (
  `id` int(10) unsigned NOT NULL,
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `content` mediumtext NOT NULL COMMENT '具体内容',
  `inputtime` int(10) unsigned NOT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `catid` (`catid`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='回收站表';

DROP TABLE IF EXISTS  `e_1_candy_search`;
CREATE TABLE `e_1_candy_search` (
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

DROP TABLE IF EXISTS  `e_1_candy_search_index`;
CREATE TABLE `e_1_candy_search_index` (
  `id` varchar(32) NOT NULL,
  `cid` int(10) unsigned NOT NULL COMMENT '文档Id',
  `inputtime` int(10) unsigned NOT NULL COMMENT '搜索时间',
  KEY `id` (`id`),
  KEY `cid` (`cid`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='搜索索引表';

DROP TABLE IF EXISTS  `e_1_candy_tag`;
CREATE TABLE `e_1_candy_tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT 'tag名称',
  `code` varchar(200) NOT NULL COMMENT 'tag代码（拼音）',
  `hits` mediumint(8) unsigned NOT NULL COMMENT '点击量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `letter` (`code`,`hits`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='Tag标签表';

DROP TABLE IF EXISTS  `e_1_candy_verify`;
CREATE TABLE `e_1_candy_verify` (
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

DROP TABLE IF EXISTS  `e_1_coin_record`;
CREATE TABLE `e_1_coin_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `fid` int(11) DEFAULT NULL,
  `coin_id` int(11) DEFAULT NULL COMMENT '币种id',
  `value` decimal(24,10) DEFAULT NULL COMMENT '数量',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `inputtime` int(11) DEFAULT NULL COMMENT '时间',
  `candyid` int(11) unsigned DEFAULT '0' COMMENT '糖果id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8 COMMENT='用户币加减记录';

DROP TABLE IF EXISTS  `e_1_exc`;
CREATE TABLE `e_1_exc` (
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
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`,`updatetime`),
  KEY `link_id` (`link_id`),
  KEY `comments` (`comments`),
  KEY `favorites` (`favorites`),
  KEY `status` (`status`),
  KEY `hits` (`hits`),
  KEY `displayorder` (`displayorder`,`updatetime`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='主表';

DROP TABLE IF EXISTS  `e_1_exc_category`;
CREATE TABLE `e_1_exc_category` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `pid` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '上级id',
  `pids` varchar(255) NOT NULL COMMENT '所有上级id',
  `name` varchar(200) NOT NULL COMMENT '栏目名称',
  `letter` char(1) NOT NULL COMMENT '首字母',
  `dirname` varchar(200) NOT NULL COMMENT '栏目目录',
  `pdirname` varchar(200) NOT NULL COMMENT '上级目录',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目表';

DROP TABLE IF EXISTS  `e_1_exc_category_data`;
CREATE TABLE `e_1_exc_category_data` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目附加表';

DROP TABLE IF EXISTS  `e_1_exc_buy`;
CREATE TABLE `e_1_exc_buy` (
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

DROP TABLE IF EXISTS  `e_1_exc_category_data_0`;
CREATE TABLE `e_1_exc_category_data_0` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目附加表';

DROP TABLE IF EXISTS  `e_1_exc_comment_data_0`;
CREATE TABLE `e_1_exc_comment_data_0` (
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

DROP TABLE IF EXISTS  `e_1_exc_comment_index`;
CREATE TABLE `e_1_exc_comment_index` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论索引表';

DROP TABLE IF EXISTS  `e_1_exc_comment_my`;
CREATE TABLE `e_1_exc_comment_my` (
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

DROP TABLE IF EXISTS  `e_1_exc_data_0`;
CREATE TABLE `e_1_exc_data_0` (
  `id` int(10) unsigned NOT NULL,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `content` mediumtext COMMENT '内容',
  UNIQUE KEY `id` (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='附表';

DROP TABLE IF EXISTS  `e_1_exc_draft`;
CREATE TABLE `e_1_exc_draft` (
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

DROP TABLE IF EXISTS  `e_1_exc_favorite`;
CREATE TABLE `e_1_exc_favorite` (
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

DROP TABLE IF EXISTS  `e_1_exc_flag`;
CREATE TABLE `e_1_exc_flag` (
  `flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '文档标记id',
  `id` int(10) unsigned NOT NULL COMMENT '文档内容id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` tinyint(3) unsigned NOT NULL COMMENT '栏目id',
  KEY `flag` (`flag`,`id`,`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标记表';

DROP TABLE IF EXISTS  `e_1_exc_hits`;
CREATE TABLE `e_1_exc_hits` (
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

DROP TABLE IF EXISTS  `e_1_exc_html`;
CREATE TABLE `e_1_exc_html` (
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

DROP TABLE IF EXISTS  `e_1_exc_index`;
CREATE TABLE `e_1_exc_index` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='内容索引表';

DROP TABLE IF EXISTS  `e_1_exc_search`;
CREATE TABLE `e_1_exc_search` (
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

DROP TABLE IF EXISTS  `e_1_exc_recycle`;
CREATE TABLE `e_1_exc_recycle` (
  `id` int(10) unsigned NOT NULL,
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `content` mediumtext NOT NULL COMMENT '具体内容',
  `inputtime` int(10) unsigned NOT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `catid` (`catid`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='回收站表';

DROP TABLE IF EXISTS  `e_1_exc_search_index`;
CREATE TABLE `e_1_exc_search_index` (
  `id` varchar(32) NOT NULL,
  `cid` int(10) unsigned NOT NULL COMMENT '文档Id',
  `inputtime` int(10) unsigned NOT NULL COMMENT '搜索时间',
  KEY `id` (`id`),
  KEY `cid` (`cid`),
  KEY `inputtime` (`inputtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='搜索索引表';

DROP TABLE IF EXISTS  `e_1_exc_tag`;
CREATE TABLE `e_1_exc_tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT 'tag名称',
  `code` varchar(200) NOT NULL COMMENT 'tag代码（拼音）',
  `hits` mediumint(8) unsigned NOT NULL COMMENT '点击量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `letter` (`code`,`hits`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='Tag标签表';

DROP TABLE IF EXISTS  `e_1_financial_member`;
CREATE TABLE `e_1_financial_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `fid` int(11) DEFAULT NULL COMMENT '产品Id',
  `coin` varchar(50) DEFAULT NULL COMMENT '币种',
  `type` int(1) DEFAULT NULL COMMENT '1活期 2定期',
  `num` decimal(10,2) DEFAULT NULL COMMENT '购买数量',
  `total_income` decimal(10,2) DEFAULT '0.00' COMMENT '总收益',
  `last_income` decimal(10,2) DEFAULT '0.00' COMMENT '昨日收益',
  `inputtime` int(10) DEFAULT NULL COMMENT '时间',
  `endtime` int(10) DEFAULT NULL COMMENT '到期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户购买的理财产品';

DROP TABLE IF EXISTS  `e_1_exc_verify`;
CREATE TABLE `e_1_exc_verify` (
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

DROP TABLE IF EXISTS  `e_1_form`;
CREATE TABLE `e_1_form` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `table` varchar(50) NOT NULL COMMENT '表名',
  `setting` text COMMENT '配置信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `table` (`table`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='表单模型表';

DROP TABLE IF EXISTS  `e_1_financial_member_log`;
CREATE TABLE `e_1_financial_member_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `fid` int(11) DEFAULT NULL COMMENT '产品id',
  `coin` varchar(255) DEFAULT NULL COMMENT '币种',
  `score` int(11) DEFAULT NULL COMMENT '数量',
  `surplus` int(11) DEFAULT NULL COMMENT '剩余',
  `type` int(11) DEFAULT NULL COMMENT '1收益 2转入 3转出',
  `inputtime` int(11) DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='用户产品购买/收益/转出记录';

DROP TABLE IF EXISTS  `e_1_help`;
CREATE TABLE `e_1_help` (
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
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`,`updatetime`),
  KEY `link_id` (`link_id`),
  KEY `comments` (`comments`),
  KEY `favorites` (`favorites`),
  KEY `status` (`status`),
  KEY `hits` (`hits`),
  KEY `displayorder` (`displayorder`,`updatetime`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='主表';

DROP TABLE IF EXISTS  `e_1_help_buy`;
CREATE TABLE `e_1_help_buy` (
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

DROP TABLE IF EXISTS  `e_1_help_category`;
CREATE TABLE `e_1_help_category` (
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目表';

DROP TABLE IF EXISTS  `e_1_help_category_data`;
CREATE TABLE `e_1_help_category_data` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目附加表';

DROP TABLE IF EXISTS  `e_1_help_comment_data_0`;
CREATE TABLE `e_1_help_comment_data_0` (
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

DROP TABLE IF EXISTS  `e_1_help_category_data_0`;
CREATE TABLE `e_1_help_category_data_0` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='栏目附加表';

DROP TABLE IF EXISTS  `e_1_help_comment_index`;
CREATE TABLE `e_1_help_comment_index` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论索引表';

DROP TABLE IF EXISTS  `e_1_help_comment_my`;
CREATE TABLE `e_1_help_comment_my` (
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

DROP TABLE IF EXISTS  `e_1_help_data_0`;
CREATE TABLE `e_1_help_data_0` (
  `id` int(10) unsigned NOT NULL,
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` smallint(5) unsigned NOT NULL COMMENT '栏目id',
  `content` mediumtext COMMENT '内容',
  UNIQUE KEY `id` (`id`),
  KEY `uid` (`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='附表';

DROP TABLE IF EXISTS  `e_1_help_draft`;
CREATE TABLE `e_1_help_draft` (
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

DROP TABLE IF EXISTS  `e_1_help_favorite`;
CREATE TABLE `e_1_help_favorite` (
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

DROP TABLE IF EXISTS  `e_1_help_flag`;
CREATE TABLE `e_1_help_flag` (
  `flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '文档标记id',
  `id` int(10) unsigned NOT NULL COMMENT '文档内容id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '作者uid',
  `catid` tinyint(3) unsigned NOT NULL COMMENT '栏目id',
  KEY `flag` (`flag`,`id`,`uid`),
  KEY `catid` (`catid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标记表';

DROP TABLE IF EXISTS  `e_1_help_hits`;
CREATE TABLE `e_1_help_hits` (
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

DROP TABLE IF EXISTS  `e_1_help_html`;
CREATE TABLE `e_1_help_html` (
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

SET FOREIGN_KEY_CHECKS = 1;

