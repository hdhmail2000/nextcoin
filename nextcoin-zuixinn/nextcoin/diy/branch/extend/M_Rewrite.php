<?php
/**
 * 通过自定义url地址解析控制器及模块
 * 根据路由来匹配S变量
 */

if (!IS_ADMIN && $uri) {

    define('PAGE_CACHE_URL', ($is_mobile ? 'mobile-' : '').$host.'/'.ltrim($uri, '/'));
    // 加载单页缓存
    is_file(WEBPATH.'cache/page/'.md5(PAGE_CACHE_URL.max(intval($_GET['page']), 1)).'.html') && exit(file_get_contents(WEBPATH.'cache/page/'.md5(PAGE_CACHE_URL.max(intval($_GET['page']), 1)).'.html'));

    define('DR_URI', $uri);
    include WEBPATH.'config/routes.php';
    $rewrite = require WEBPATH.'config/rewrite.php';
    $routes = $rewrite && is_array($rewrite) && count($rewrite) > 0 ? array_merge($routes, $rewrite) : $routes;

    // 正则匹配路由规则
    $value = $u = '';
    foreach ($routes as $key => $val) {
        $match = array();
        if ($key == $uri || @preg_match('/^'.$key.'$/U', $uri, $match)) {
            unset($match[0]);
            $u = $val;
            $value = $match;
            break;
        }

    }
    if ($u) {
        if (strpos($u, 'index.php?') === 0) {
            // URL参数模式
            $_GET = array();
            $queryParts = explode('&', str_replace('index.php?', '', $u));
            foreach ($queryParts as $param) {
                $item = explode('=', $param);
                $_GET[$item[0]] = $item[1];
                if (strpos($item[1], '$') !== FALSE) {
                    $id = (int)substr($item[1], 1);
                    $_GET[$item[0]] = isset($match[$id]) ? $match[$id] : $item[1];
                }
            }
            !$_GET['c'] && $_GET['c'] = 'home';
            !$_GET['m'] && $_GET['m'] = 'index';
        } elseif (strpos($u, '/') !== false) {
            // URI分段模式
            $array = explode('/', $u);
            $s = array_shift($array);
            if ($s == 'member' || is_dir(FCPATH.'module/'.$s) || is_dir(FCPATH.'app/'.$s)) {
                $_GET['s'] = $s;
                $_GET['c'] = array_shift($array);
                $_GET['m'] = array_shift($array);
            } elseif (is_file(FCPATH.'dayrui/controllers/'.ucfirst($s).'.php')) {
                $_GET['c'] = $s;
                $_GET['m'] = array_shift($array);
            }
            // 组装GET参数
            if ($array) {
                foreach ($array as $k => $t) {
                    $i%2 == 0 && $_GET[str_replace('$', '_', $t)] = isset($array[$k+1]) ? $array[$k+1] : '';
                    $i ++;
                }
                if ($value) {
                    foreach ($_GET as $k => $v) {
                        if (strpos($v, '$') !== FALSE) {
                            $id = (int)substr($v, 1);
                            $_GET[$k] = isset($value[$id]) ? $value[$id] : $v;
                        }
                    }
                }
            }
        }
    } elseif (isset($_GET['s']) && !isset($_GET['c'])) {
        // 只存在唯一一个s参数时给他强制指向home控制器
        $_GET['c'] = 'home';
    }
}
// 判断s参数,“应用程序”文件夹目录
if (isset($_GET['s']) && preg_match('/^[a-z]+$/i', $_GET['s'])) {
    // 判断会员模块,排除后台调用
    if (!IS_ADMIN && $_GET['s'] == 'member') { // 会员
        $_GET['d'] = 'member'; // 将项目标识作为directory
        if ($_GET['mod'] && is_dir(FCPATH . 'module/' . $_GET['mod'])) { // 模块
            define('APPPATH', FCPATH . 'module/' . $_GET['mod'] . '/');
            !defined('APP_DIR') && define('APP_DIR', $_GET['mod']); // 模块目录名称
        } elseif ($_GET['app'] && is_dir(FCPATH . 'app/' . $_GET['app'] . '/')) { // 应用
            define('APPPATH', FCPATH . 'app/' . $_GET['app'] . '/');
            !defined('APP_DIR') && define('APP_DIR', $_GET['app']); // 应用目录名称
        } else {
            define('APPPATH', FCPATH . 'dayrui/');
            !defined('APP_DIR') && define('APP_DIR', 'member'); // 模块目录名称
            define('ENVIRONMENT', '../../../config');
        }
        define('IS_MEMBER', TRUE);
    } elseif (is_dir(FCPATH . 'module/' . $_GET['s'])) { // 模块
        define('APPPATH', FCPATH . 'module/' . $_GET['s'] . '/');
        !defined('APP_DIR') && define('APP_DIR', $_GET['s']); // 识别目录名称
        define('IS_MEMBER', FALSE);
        // 判断加载模块首页静态文件
        $file = WEBPATH.'cache/index/'.($is_mobile ? 'mobile-' : '').DOMAIN_NAME.'-'.APP_DIR.'-'.max(intval($_GET['page']), 1).'.html';
        !$uu && is_file($file) && exit(file_get_contents($file));
    } elseif (is_dir(FCPATH . 'app/' . $_GET['s'] . '/')) { // 应用
        define('APPPATH', FCPATH . 'app/' . $_GET['s'] . '/');
        !defined('APP_DIR') && define('APP_DIR', $_GET['s']); // 应用目录名称
        define('IS_MEMBER', FALSE);
    }
    !defined('ENVIRONMENT') && define('ENVIRONMENT', '../../../../config');
} else {
    // 系统主目录
    define('APPPATH', FCPATH . 'dayrui/');
    !defined('APP_DIR') && define('APP_DIR', '');
    define('IS_MEMBER', FALSE);
    define('ENVIRONMENT', '../../../config');
    // 判断加载网站首页静态文件
    $file = WEBPATH.'cache/index/'.($is_mobile ? 'mobile-' : '').DOMAIN_NAME.'-home-'.max(intval($_GET['page']), 1).'.html';
    !IS_ADMIN && !$uu && is_file($file) && exit(file_get_contents($file));
}