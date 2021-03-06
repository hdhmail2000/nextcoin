<?php $drpath = DR_PATH; ?>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no" />
    <meta name="csrf-token" content="{{ csrf_token() }}">
    	<meta name="description" content="DOTA ONE" />
    <meta name="keywords" content="DOTA ONE" />
    <meta name="author" content="DOTA ONE" />
    <title><?php echo SITE_NAME; ?></title>

    <link rel="shortcut icon" href="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/favicon.png"/>
    <!--<link rel="bookmark" href="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/favicon.ico"/>-->
    <link rel="icon" href="<?php echo dr_get_file(dr_block('wangzhanicon')); ?>" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.bootcss.com/animate.css/3.5.2/animate.min.css" />
    <link rel="stylesheet" type="text/css" href="<?php echo HOME_THEME_PATH; ?>exc/css/swiper.min.css"/>
    <link rel="stylesheet" type="text/css" href="<?php echo HOME_THEME_PATH; ?>exc/css/jquery-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="<?php echo HOME_THEME_PATH; ?>exc/css/jquery-ui-slider-pips.min.css"/>
    <link rel="stylesheet" type="text/css" href="<?php echo HOME_THEME_PATH; ?>exc/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="<?php echo HOME_THEME_PATH; ?>exc/css/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" type="text/css" href="<?php echo HOME_THEME_PATH; ?>exc/font/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>css/reset.css" />
    <link rel="stylesheet" type="text/css" href="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>css/custom.css" />
    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/jquery-2.2.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdn.bootcss.com/wow/1.1.2/wow.min.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        new WOW().init();
    </script>
    <style>

    </style>
</head>

<body>