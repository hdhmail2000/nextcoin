<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/**
 * v3.2
 */

/**
 * 系统配置文件
 */

return array(

	'SYS_LOG'                       => 0, //后台操作日志开关
	'SYS_KEY'                       => 'poscms7a6ab9b24816df905219861ee7ebffd4', //网站安全密钥
	'SYS_REFERER'                   => '', //移动端api安全密钥
	'SYS_DEBUG'                     => 0, //调试器开关
	'SYS_HTTPS'                     => 1, //HTTPS安全模式
	'SYS_HELP_URL'                  => '', //系统帮助url前缀部分
	'SYS_EMAIL'                     => '', //系统收件邮箱，用于接收系统信息
	'SYS_CACHE_TYPE'                => 0, //缓存方式
	'SYS_ATTACHMENT_DIR'            => '', //系统附件目录名称
	'SYS_ATTACHMENT_DB'             => '', //附件归档存储开关
	'SYS_UPLOAD_DIR'                => 'uploadfile', //附件上传目录
	'SYS_CATE_SHARE'                => 0, //共享栏目展示方式
	'SYS_ATTACHMENT_URL'            => '', //附件域名设置
	'SYS_CRON_QUEUE'                => 0, //任务队列方式
	'SYS_CRON_NUMS'                 => 20, //每次执行任务数量
	'SYS_CRON_TIME'                 => 300, //每次执行任务间隔
	'SYS_ONLINE_NUM'                => 1000, //服务器最大在线人数
	'SYS_ONLINE_TIME'               => 7200, //会员在线保持时间(秒)
	'SYS_TEMPLATE'                  => 'templates', //网站风格目录名称
	'SYS_THUMB_DIR'                 => '', //缩略图目录
	'SYS_NAME'                      => 'POSCMS', //
	'SYS_CMS'                       => 'NEXTCOIN', //
	'SYS_NEWS'                      => 1, //
	'SYS_SYNC_ADMIN'                => 0, //后台同步登录开关
	'SYS_THEME_DOMAIN'              => '', //风格域名
	'SYS_UPDATE'                    => 0, //兼容升级开关
	'SYS_AUTO_CACHE'                => 0, //自动缓存
	'SYS_AUTO_CACHE_TIME'           => 0, //自动清理缓存
	'SITE_EXPERIENCE'               => '经验值', //经验值名称
	'SITE_SCORE'                    => '虚拟币', //虚拟币名称
	'SITE_MONEY'                    => '金钱', //金钱名称
	'SITE_CONVERT'                  => 10, //虚拟币兑换金钱的比例
	'SITE_ADMIN_CODE'               => 0, //后台登录验证码开关
	'SITE_ADMIN_PAGESIZE'           => 20, //后台数据分页显示数量
	'SYS_GEE_CAPTCHA_ID'            => '', //极验验证ID
	'SYS_GEE_PRIVATE_KEY'           => '', //极验验证KEY
	'SYS_CACHE_INDEX'               => 300, //站点首页静态化
	'SYS_CACHE_MINDEX'              => 300, //模块首页静态化
	'SYS_CACHE_MSHOW'               => 300, //模块内容缓存期
	'SYS_CACHE_MSEARCH'             => 300, //模块搜索缓存期
	'SYS_CACHE_SITEMAP'             => 300, //Sitemap.xml更新周期
	'SYS_CACHE_LIST'                => 300, //List标签查询缓存
	'SYS_CACHE_MEMBER'              => 300, //会员信息缓存期
	'SYS_CACHE_ATTACH'              => 300, //附件信息缓存期
	'SYS_CACHE_FORM'                => 300, //表单内容缓存期
	'SYS_CACHE_POSTER'              => 300, //广告内容缓存期
	'SYS_CACHE_SPACE'               => 300, //会员空间内容缓存期
	'SYS_CACHE_TAG'                 => 300, //Tag内容缓存期
	'SYS_CACHE_COMMENT'             => 0, //评论统计缓存期
	'SYS_CACHE_PAGE'                => 0, //单页静态化
	'SYS_CLOSE_MEMBER'              => '', //前端会员系统
	'SYS_CLOSE_WEIXIN'              => '', //微信公众号
	'SYS_CLOSE_TAG'                 => '', //TAG关键词库
	'SYS_CLOSE_NYRTJ'               => '', //日周月年访问统计
	'SYS_CLOSE_TAGRK'               => '', //关键词自动入库TAG表
	'SYS_CLOSE_VERIFY'              => '', //内容审核流程
	'SYS_CLOSE_DOWNSER'             => '', //下载镜像服务器
	'SYS_CLOSE_LINKAGE'             => '', //联动菜单
	'SYS_CLOSE_XTTX'                => '', //后台系统提醒
	'SYS_CLOSE_XNB'                 => '', //虚拟货币

);