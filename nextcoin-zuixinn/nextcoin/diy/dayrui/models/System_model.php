<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class System_model extends CI_Model {

    public $config;
    public $closes;

    /*
     * 系统模型类
     */
    public function __construct() {
        parent::__construct();
        $this->config = array(
            'SYS_LOG' => '后台操作日志开关',
            'SYS_KEY' => '网站安全密钥',
            'SYS_REFERER' => '移动端api安全密钥',
            'SYS_DEBUG'	=> '调试器开关',
            'SYS_HTTPS'	=> 'HTTPS安全模式',
            'SYS_HELP_URL' => '系统帮助url前缀部分',
            'SYS_EMAIL' => '系统收件邮箱，用于接收系统信息',
            'SYS_CACHE_TYPE' => '缓存方式',
            'SYS_ATTACHMENT_DIR' => '系统附件目录名称',
            'SYS_ATTACHMENT_DB' => '附件归档存储开关',
            'SYS_UPLOAD_DIR' => '附件上传目录',
            'SYS_CATE_SHARE' => '共享栏目展示方式',
            'SYS_ATTACHMENT_URL' => '附件域名设置',
            'SYS_CRON_QUEUE' => '任务队列方式',
            'SYS_CRON_NUMS' => '每次执行任务数量',
            'SYS_CRON_TIME' => '每次执行任务间隔',
            'SYS_ONLINE_NUM' => '服务器最大在线人数',
            'SYS_ONLINE_TIME' => '会员在线保持时间(秒)',
            'SYS_TEMPLATE' => '网站风格目录名称',
            'SYS_THUMB_DIR' => '缩略图目录',

            'SYS_NAME' => '',
            'SYS_CMS' => '',
            'SYS_NEWS' => '',
            'SYS_SYNC_ADMIN' => '后台同步登录开关',
            'SYS_THEME_DOMAIN' => '风格域名',
            'SYS_UPDATE' => '兼容升级开关',

            'SYS_AUTO_CACHE' => '自动缓存',
            'SYS_AUTO_CACHE_TIME' => '自动清理缓存',

            'SITE_EXPERIENCE' => '经验值名称',
            'SITE_SCORE' => '虚拟币名称',
            'SITE_MONEY' => '金钱名称',
            'SITE_CONVERT' => '虚拟币兑换金钱的比例',
            'SITE_ADMIN_CODE' => '后台登录验证码开关',
            'SITE_ADMIN_PAGESIZE' => '后台数据分页显示数量',

            'SYS_GEE_CAPTCHA_ID' => '极验验证ID',
            'SYS_GEE_PRIVATE_KEY' => '极验验证KEY',

            'SYS_CACHE_INDEX' => '站点首页静态化',
            'SYS_CACHE_MINDEX' => '模块首页静态化',
            'SYS_CACHE_MSHOW' => '模块内容缓存期',
            'SYS_CACHE_MSEARCH' => '模块搜索缓存期',
            'SYS_CACHE_SITEMAP' => 'Sitemap.xml更新周期',
            'SYS_CACHE_LIST' => 'List标签查询缓存',
            'SYS_CACHE_MEMBER' => '会员信息缓存期',
            'SYS_CACHE_ATTACH' => '附件信息缓存期',
            'SYS_CACHE_FORM' => '表单内容缓存期',
            'SYS_CACHE_POSTER' => '广告内容缓存期',
            'SYS_CACHE_SPACE' => '会员空间内容缓存期',
            'SYS_CACHE_TAG' => 'Tag内容缓存期',
            'SYS_CACHE_COMMENT' => '评论统计缓存期',
            'SYS_CACHE_PAGE' => '单页静态化',

        );
        $this->closes = array(
            'MEMBER' => array(
                'name' => '前端会员系统',
                'tips' => '前端会员注册、登陆、涉及到会员的功能等，一般企业站不会用到会员系统',
            ),
            'WEIXIN' => array(
                'name' => '微信公众号',
                'tips' => '涉及到微信服务的功能，公众号接入等',
            ),
            'TAG' => array(
                'name' => 'TAG关键词库',
                'tips' => '关键词功能，关闭之后文章将不会匹配到本地关键词',
            ),
            'NYRTJ' => array(
                'name' => '日周月年访问统计',
                'tips' => '统计“日周月年”时间段的访问量，关闭后可减少负载',
            ),
            'TAGRK' => array(
                'name' => '关键词自动入库TAG表',
                'tips' => '发布内容时获取的关键字自动入库到关键词库表中，关闭后可减少负载',
            ),
            'VERIFY' => array(
                'name' => '内容审核流程',
                'tips' => '用户会员投稿后，管理员审核其内容的流程',
            ),
            'DOWNSER' => array(
                'name' => '下载镜像服务器',
                'tips' => '下载软件时的镜像服务器配置，下载站会用到此功能',
            ),
            'LINKAGE' => array(
                'name' => '联动菜单',
                'tips' => '地区联动等菜单缓存，关闭后可减少负载',
            ),
            'XTTX' => array(
                'name' => '后台系统提醒',
                'tips' => '前端用户交互操作时对后台管理员的提醒通知',
            ),
            'XNB' => array(
                'name' => '虚拟货币',
                'tips' => '虚拟货币',
            ),
        );

    }

    /*
     * 保存配置文件
     *
     * @param	array	$system	旧数据
     * @param	array	$config	新数据
     * @param	array	$action	是否来自附件栏目
     * @return	void
     */
    public function save_config($system, $config, $action = '') {

        $data = array();
        $this->load->library('dconfig');
        if ($action == 'file') {
            $cfg = $config;
            $config = $system;
            $config['SYS_ATTACHMENT_DB'] = $cfg['SYS_ATTACHMENT_DB'];
            $config['SYS_ATTACHMENT_DIR'] = $cfg['SYS_ATTACHMENT_DIR'];
            $config['SYS_UPLOAD_DIR'] = $cfg['SYS_UPLOAD_DIR'];
            $config['SYS_ATTACHMENT_URL'] = $cfg['SYS_ATTACHMENT_URL'];
            $config['SYS_THUMB_DIR'] = $cfg['SYS_THUMB_DIR'];
        }
        if ($action == 'close') {
            $cfg = $config;
            $config = $system;
            foreach ($this->closes as $i => $t) {
                $config['SYS_CLOSE_'.$i] = (int)$cfg['SYS_CLOSE_'.$i];
                $this->config['SYS_CLOSE_'.$i] = $t['name'];
            }
        } else {
            foreach ($this->closes as $i => $t) {
                $this->config['SYS_CLOSE_'.$i] = $t['name'];
            }
        }

        foreach ($this->config as $i => $note) {
            // 处理逻辑值
            if (in_array($i, array('SYS_AUTO_CACHE', 'SYS_DEBUG',
                'SYS_CATE_SHARE',
                'SYS_UPDATE',
                'SYS_HTTPS',
                'SYS_NEWS', 'SYS_LOG',
                'SITE_ADMIN_CODE', 'SYS_CRON_QUEUE', 'SYS_SYNC_ADMIN'))) {
                $value = isset($config[$i]) ? $config[$i] : 0;
            } else {
                $value = isset($config[$i]) ? $config[$i] : $system[$i];
            }
            if (strlen($value) == 4 && $value == 'TRUE') {
                $value = 1;
            } elseif (strlen($value) == 5 && $value == 'FALSE') {
                $value = 0;
            } elseif ($i == 'SYS_HELP_URL') {
                $value = $system['SYS_HELP_URL'];
            } elseif ($i == 'SYS_UPLOAD_DIR') {
                $value = addslashes($value);
            } elseif ($i == 'SYS_KEY' && strpos($value, '************') !== FALSE) {
                $value = $system['SYS_KEY'];
            } elseif ($i == 'SYS_REFERER' && strpos($value, '************************') !== FALSE) {
                $value = $system['SYS_REFERER'];
            }
            $data[$i] = $value;
        }

        $this->dconfig->file(WEBPATH.'config/system.php')->note('系统配置文件')->space(32)->to_require_one($this->config, $data);

        return $data;
    }

    /*
     * 缓存表
     *
     * @return	array
     */
    public function cache() {

        $table = array();

        // 主数据库表查询
        $_table = $this->db->query("SHOW TABLE STATUS FROM `{$this->db->database}`")->result_array();
        foreach ($_table as $t) {
            if (strpos($t['Name'], $this->db->dbprefix) === 0 && strpos($t['Name'], '-') === false) {
                $query = $this->db->query('SHOW FULL COLUMNS FROM `'.$t['Name'].'`');
                if (!$query) {
                    $this->db->query('REPAIR TABLE '.$t['Name']);
                    $query = $this->db->query('SHOW FULL COLUMNS FROM `'.$t['Name'].'`');
                    if (!$query) {
                        continue;
                    }
                }
                $_field = $query->result_array();
                foreach ($_field as $c) {
                    $t['field'][$c['Field']] = array(
                        'name' => $c['Field'],
                        'type' => $c['Type'],
                        'note' => $c['Comment']
                    );
                }
                $table[$t['Name']]	= array(
                    'name' => $t['Name'],
                    'rows' => $t['Rows'],
                    'note' => $t['Comment'],
                    'free' => $t['Data_free'], // 多余空间
                    'field' => $t['field'],
                    'siteid' => 0, // 主数据库
                    'update' => $t['Update_time'],
                    'filesize' => $t['Data_length'] + $t['Index_length'],
                    'collation'	=> $t['Collation'],
                );
            }
        }

        $files = directory_map(WEBPATH.'cache/data/', 1);
        foreach ($files as $file) {
            if (strpos($file, 'mytable-') === 0) {
                @unlink(WEBPATH.'cache/data/'.$file);
            }
        }

        $this->dcache->set('table', $table);

        return $table;
    }

    /*
     * 系统表
     *
     * @return	array
     */
    public function get_system_table() {
        return $this->dcache->get('table');
    }

    /*
     * 站点表
     *
     * @param	intval	$siteid
     * @return	array
     */
    public function get_site_table($siteid) {
        return $this->dcache->get('table');
    }


    // 更新URL缓存
    public function urlrule() {

        $this->ci->dcache->delete('urlrule');
        $data = $this->db->get('urlrule')->result_array();
        $cache = array();
        if ($data) {
            foreach ($data as $t) {
                $t['value'] = dr_string2array($t['value']);
                if ($t['value'] && ($t['type'] == 2 || $t['type'] == 3)) {
                    // 当为共享模块URL时,复制值给独立模块
                    foreach ($t['value'] as $var => $val) {
                        strpos($var, 'share_') === 0 && $t['value'][str_replace('share_', '', $var)] = $val;
                    }
                }
                $cache[$t['id']] = $t;
            }
            $this->ci->dcache->set('urlrule', $cache);
        }

        $this->ci->clear_cache('urlrule');
        return $cache;
    }

    // 更新远程附件缓存
    public function attachment() {

        $this->ci->dcache->delete('attachment');

        $cache = array();
        foreach ($this->site_info as $sid => $t) {
            $db = $this->site[$sid];
            if ($db) {
                $data = $this->db->get($sid.'_remote')->result_array();
                $cache[$sid] = array('data' => array(), 'ext' => array());
                if ($data) {
                    foreach ($data as $t) {
                        $t['value'] = dr_string2array($t['value']);
                        $cache[$sid]['data'][$t['id']] = $t;
                        $exts = @explode(',', $t['exts']);
                        if ($exts) {
                            foreach ($exts as $e) {
                                $e && $cache[$sid]['ext'][$e] = $t['id'];
                            }
                        }
                    }
                }
            }
        }

        $this->ci->dcache->set('attachment', $cache);
        $this->ci->clear_cache('attachment');

        return $cache;
    }

    // 更新邮件缓存
    public function email() {

        $this->dcache->delete('email');
        $data = $this->db->order_by('displayorder asc')->get('mail_smtp')->result_array();
        $data && $this->dcache->set('email', $data);
        $this->ci->clear_cache('email');
        return $data;
    }

    // 更新审核流程缓存
    public function verify() {

        $data = array();
        $_data = $this->db->order_by('id ASC')->get('admin_verify')->result_array();
        if ($_data) {
            foreach ($_data as $t) {
                $t['num'] = count($t['verify']);
                $t['verify'] = dr_string2array($t['verify']);
                // 格式化
                if ($t['verify']) {
                    $i = 1;
                    $role = array();
                    foreach ($t['verify'] as $a) {
                        $role[$i] = $a;
                        $i++;
                    }
                    $t['verify'] = $role;
                }
                $data[$t['id']] = $t;
            }
            $this->ci->dcache->set('verify', $data);
        } else {
            $this->ci->dcache->delete('verify');
        }

        $this->ci->clear_cache('verify');

        return $data;
    }

    // 更新下载镜像缓存
    public function downservers() {

        $data = $this->db->order_by('displayorder asc')->get('downservers')->result_array();
        $this->ci->dcache->delete('downservers');
        $cache = array();
        if ($data) {
            foreach ($data as $t) {
                $cache[$t['id']] = $t;
            }
            $this->ci->dcache->set('downservers', $cache);
        }

        $this->ci->clear_cache('downservers');
        return $cache;
    }

    // 更新评论缓存
    public function comment() {

        $data = $this->db->get('comment')->result_array();
        $this->ci->dcache->delete('comment');
        $cache = array();
        if ($data) {
            foreach ($data as $t) {
                // 自定义字段
                $my = array();
                $field = $this->db->where('disabled', 0)->where('relatedid', 0)->where('relatedname', $t['name'])->order_by('displayorder ASC, id ASC')->get('field')->result_array();
                if ($field) {
                    foreach ($field as $f) {
                        $f['setting'] = dr_string2array($f['setting']);
                        $my[$f['fieldname']] = $f;
                    }
                }
                $cache[$t['name']] = array(
                    'name' => $t['name'],
                    'field' => $my,
                    'value' => dr_string2array($t['value']),
                );
            }
            $this->ci->dcache->set('comment', $cache);
        }

        $this->ci->clear_cache('comment');
        return $cache;
    }

    // 文字块缓存
    public function block($site) {

        $this->ci->clear_cache('block-'.$site);
        $this->ci->dcache->delete('block-'.$site);

        $data = $this->db->get($site.'_block')->result_array();
        // 检查字段是否存在
        $this->db->data_cache['field_names'] = [];
        if (!$this->db->field_exists('code', $site.'_block')) {
            $this->db->query('ALTER TABLE `'.$this->db->dbprefix($site.'_block').'` ADD `code` VARCHAR(100) NOT NULL');
        }

        $cache = array();
        if ($data) {
            foreach ($data as $t) {
                if (!$t['code']) {
                    // 填充默认code
                    $t['code'] = $t['id'];
                    $this->db->where('id', $t['id'])->update($site.'_block', array(
                        'code' => $t['id'],
                    ));
                }
                $t = dr_get_block_value($t);
                switch (intval($t['i'])) {
                    case 1:
                        // 文本内容
                        $value = $t['value_1'];
                        break;
                    case 2:
                        // 丰富文本
                        $value = htmlspecialchars_decode($t['value_2']);
                        break;
                    case 3:
                        // 单文件
                        $value = $t['value_3'];
                        break;
                    case 4:
                        // 多文件
                        $value = dr_string2array($t['value_4']);
                        break;
                }

                $cache[$t['code']] = array(
                    1 => $t['name'],
                    0 => $value,
                );
            }
            $this->ci->dcache->set('block-'.$site, $cache);
        }

        return $cache;
    }

    // 全局变量缓存
    public function sysvar() {

        $this->ci->clear_cache('sysvar');
        $this->ci->dcache->delete('sysvar');

        $data = $this->db->get('var')->result_array();
        $cache = array();
        if ($data) {
            foreach ($data as $t) {
                $cache[$t['cname']] = $t['value'];
            }
            $this->ci->dcache->set('sysvar', $cache);
        }

        return $cache;
    }

    // 检查、创建、删除表单统计字段
    // auto=1 创建，auto=-1 删除
    public function create_form_total_field($sid, $dir, $table, $auto) {

        $field = $table.'_total';
        $table = $this->db->dbprefix($sid.'_'.$dir);

        if (!$this->db->table_exists($table)) {
            return;
        }

        // 检查字段是否存在
        $this->db->data_cache['field_names'] = [];
        if ($this->db->field_exists($field, $table)) {
            if ($auto == 1) {
                return; // 字段存在时跳过创建
            } else {
                // 字段存在时删除它
                $this->db->query("ALTER TABLE `{$table}` DROP `{$field}`");
            }
        }

        // 删除操作时直接跳过
        if ($auto == -1) {
            return;
        }

        // 创建字段
        $this->db->query("ALTER TABLE `{$table}` ADD `{$field}` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '表单统计' , ADD INDEX (`{$field}`) ;");

    }

    // 插入后台菜单
    public function add_admin_menu($data) {
        $this->db->insert('admin_menu', $data);
        return $this->db->insert_id();
    }

    // 插入会员菜单
    public function add_member_menu($data) {

        $data['icon'] = $data['icon'] ? $data['icon'] : 'fa fa-th-large';
        $this->db->insert('member_menu', $data);
        return $this->db->insert_id();
    }

    // 安装应用菜单
    public function add_app_menu($menu, $dir, $id) {

        // 后台菜单
        if (isset($menu['admin']) && isset($menu['admin']['menu']) && $menu['admin']['menu']) {
            // 查询应用的顶级菜单
            $top = $this->db->select('id')->where('mark', 'myapp')->where('pid', 0)->get('admin_menu')->row_array();
            if (!$top) {
                // 模糊查询
                $top = $this->db->select('id')->where('name', '插件')->where('pid', 0)->get('admin_menu')->row_array();
                if (!$top) {
                    $this->system_model->add_admin_menu(array(
                        'uri' => '',
                        'pid' => 0,
                        'mark' => 'myapp',
                        'name' => '插件',
                        'hidden' => 0,
                        'displayorder' => 0,
                    ));
                    $top['id'] = $this->db->insert_id();
                }
            }
            $topid = (int)$top['id'];
            // 分组菜单
            if ($menu['admin']['name'] && $menu['admin']['icon']) {
                // 新建分组菜单
                $this->system_model->add_admin_menu(array(
                    'uri' => '',
                    'pid' => $topid,
                    'mark' => 'appp-'.$dir,
                    'name' => $menu['admin']['name'],
                    'icon' => $menu['admin']['icon'],
                    'hidden' => 0,
                    'displayorder' => 0,
                ));
                $leftid = $this->db->insert_id();
            } else {
                // 查询现有的分组菜单
                $left = $this->db->select('id')->where('mark', 'cloud-cloud')->get('admin_menu')->row_array();
                $leftid = (int)$left['id'];
                if (!$leftid) {
                    $this->system_model->add_admin_menu(array(
                        'uri' => '',
                        'pid' => $topid,
                        'mark' => 'cloud-cloud',
                        'name' => '应用插件',
                        'hidden' => 0,
                        'displayorder' => 0,
                    ));
                    $leftid = $this->db->insert_id();
                }
            }
            // 链接菜单
            foreach ($menu['admin']['menu'] as $link) {
                $muri = dr_replace_m_uri($link, $id, $dir);
                if (!$this->db->where('uri', $muri)->count_all_results('admin_menu')) {
                    $this->system_model->add_admin_menu(array(
                        'pid' => $leftid,
                        'uri' => $muri,
                        'mark' => 'app-'.$dir,
                        'name' => $link['name'],
                        'icon' => $link['icon'] ? $link['icon'] : dr_get_icon($link['uri']),
                        'hidden' => 0,
                        'displayorder' => 0,
                    ));
                }
            }
        }

        // 会员菜单
        if (isset($menu['member']) && isset($menu['member']['menu']) && $menu['member']['menu']) {
            // 查询内容的顶级菜单
            $top = $this->db->where('mark', 'm_app')->get('member_menu')->row_array();
            if (!$top) {
                $this->system_model->add_member_menu(array(
                    'uri' => '',
                    'url' => '',
                    'pid' => 0,
                    'mark' => 'm_app',
                    'name' => '应用',
                    'icon' => 'fa fa-th-large',
                    'target' => 0,
                    'hidden' => 0,
                    'displayorder' => 0,
                ));
                $top['id'] = $this->db->insert_id();
            }
            $topid = $top['id'];
            // 分组菜单
            if ($menu['member']['name']) {
                // 新建分组菜单
                $this->system_model->add_member_menu(array(
                    'uri' => '',
                    'url' => '',
                    'pid' => $topid,
                    'mark' => 'app-'.$dir,
                    'name' => $menu['member']['name'],
                    'icon' => $menu['member']['icon'] ? $menu['member']['icon'] : 'fa fa-th-large',
                    'target' => 0,
                    'hidden' => 0,
                    'displayorder' => 0,
                ));
                $leftid = $this->db->insert_id();
            } else {
                // 查询现有的分组菜单
                $left = $this->db->select('id')->where('pid', $topid)->get('member_menu')->row_array();
                $leftid = (int)$left['id'];
                if (!$leftid) {
                    $this->system_model->add_member_menu(array(
                        'uri' => '',
                        'url' => '',
                        'pid' => $topid,
                        'mark' => 'app-'.$dir,
                        'name' => '我的应用',
                        'icon' => 'fa fa-th-large',
                        'target' => 0,
                        'hidden' => 0,
                        'displayorder' => 0,
                    ));
                    $leftid = $this->db->insert_id();
                }
            }
            // 分组菜单名称
            foreach ($menu['member']['menu'] as $link) {
                $muri = dr_replace_m_uri($link, $id, $dir);
                if (!$this->db->where('uri', $muri)->count_all_results('member_menu')) {
                    $this->system_model->add_member_menu(array(
                        'pid' => $leftid,
                        'url' => '',
                        'uri' => $muri,
                        'mark' => 'app-'.$dir,
                        'name' => $link['name'],
                        'icon' => $link['icon'] ? $link['icon'] : 'fa fa-th-large',
                        'target' => 0,
                        'hidden' => 0,
                        'displayorder' => 0,
                    ));
                }
            }
        }

    }
}