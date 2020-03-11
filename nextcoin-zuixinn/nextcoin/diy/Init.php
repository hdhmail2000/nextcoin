<?php

define('EXT', '.php'); // PHP文件扩展名
define('SYSDIR', 'system'); // “系统文件夹”的名称
define('BASEPATH', FCPATH . 'system/'); // CI框架目录
define('VIEWPATH', FCPATH . 'dayrui/'); // 定义视图目录，我们把它当做主项目目录

require WEBPATH.'config/user_agents.php';

// 客户端判定
$host = strtolower($_SERVER['HTTP_HOST']);
$is_mobile = 0;
if ($mobiles) {
    foreach ($mobiles as $key => $val) {
        if (FALSE !== (strpos(strtolower($_SERVER['HTTP_USER_AGENT']), $key))) {
            // 表示移动端
            $is_mobile = 1;
            break;
        }
    }
}

define('DOMAIN_NAME', $host); // 当前域名

if (defined('APP_DIR')) {
    // 来自模块
    $_GET['s'] = APP_DIR;
} else {
    // 解析自定义域名
    if (is_file(WEBPATH . 'config/module_domain.php')){
        $domain = require WEBPATH . 'config/module_domain.php';
        if ($domain) {
            $dir = isset($domain[$host]) && $domain[$host] ? $domain[$host] : '';
            if (strpos($dir, 'm_') !== false) {
                $dir  = substr($dir, 2);
                !defined('IS_MOBILE_SELF') && define('IS_MOBILE_SELF', 1);
            }
            if ($dir && (is_dir(FCPATH.'module/'.$dir) || $dir == 'member')) {
                !$_GET['s'] && $_GET['s'] = $dir; // 强制定义为模块
            } elseif (isset($domain['space']) && $domain['space'] && strpos($host, $domain['space'])) {
                $domain = require WEBPATH . 'config'.'/domain.php';
                $system = require WEBPATH . 'config'.'/system.php';
                !isset($domain[$host]) && $system['SYS_DOMAIN'] != $host && !$_GET['s'] && $_GET['s'] = 'space'; // 强制定义为模块
            }
        }
        unset($domain);
    }
}

// 伪静态字符串
$uu = isset($_SERVER['HTTP_X_REWRITE_URL']) || trim($_SERVER['REQUEST_URI'], '/') == SELF ? trim($_SERVER['HTTP_X_REWRITE_URL'], '/') : ($_SERVER['REQUEST_URI'] ? trim($_SERVER['REQUEST_URI'], '/') : NULL);
$uri = strpos($uu, SELF) === 0 || strpos($uu, '?') === 0 ? '' : $uu; // 以index.php或者?开头的uri不做处理

if (!defined('IS_MEMBER')) {
    // 分析url
    require FCPATH.'branch/extend/M_Rewrite.php';
} else {
    // 通过百度编辑器/api接口定义的会员模块
    define('APPPATH', FCPATH . 'dayrui/');
    define('APP_DIR', 'member');
    $_GET['d'] = 'member'; // 将项目标识作为directory
    define('ENVIRONMENT', '../../../config');
}

// 请求URI字符串
!defined('DR_URI') && define('DR_URI', '');

require FCPATH.'branch/extend/M_Common.php';
require FCPATH.'branch/phpcmf/Phpcmf.php';

// CI框架核心文件
require BASEPATH . 'core/CodeIgniter.php';