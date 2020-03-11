<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class Site extends M_Controller {
	
    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
		
		$this->template->assign(array(
			'menu' => $this->get_menu_v3(array(
				fc_lang('网站管理') => array('admin/site/index', 'globe'),
				fc_lang('添加') => array('admin/site/add_js', 'plus'),
				fc_lang('配置') => array(isset($_GET['id']) && $_GET['id'] ? 'admin/site/config/id/'.(int)$_GET['id'] : 'admin/site/config', 'cog'),
			))
		));
		
		$this->load->model('site_model');
		$this->load->library('dconfig');
    }


    /**
     * 获取树结构
     */
    protected function _get_tree($data) {

        $tree = array();
        $urlrule = $this->get_cache('urlrule');
        $category = $this->get_cache('module-'.SITE_ID.'-share', 'category');

        foreach($data as $t) {
            $id = $t['id'];
            $url = dr_url_prefix($category[$t['id']]['url'] ? $category[$t['id']]['url'] : 'index.php?c=category&id='.$t['id']);
            $t['option'] = '<label><a class="ago" href="'.$url.'" target="_blank"> <i class="fa fa-send"></i> '.fc_lang('访问').'</a></label>
            <label>';
            $t['option'].= '<select class="form-control" name="data[category]['.$id.'][urlrule]">';
            $t['option'].= '<option value="0"> -- </option>';
			if ($urlrule) {
                foreach ($urlrule as $u) {
                    if ($u['type']==3) {
                        $t['option'].= '<option value="'.$u['id'].'" '.($u['id'] == $t['setting']['urlrule'] ? 'selected' : '').'> '.$u['name'].' </option>';
                    }
                }
            }
            $t['option'].= '</select></label>';
            $t['option'].= '<label class="">&nbsp;&nbsp;<a href="'.dr_url('urlrule/index').'" style="color:blue !important">'.fc_lang('[URL规则管理]').'</a></label>';


            // 判断是否生成静态
            if ($t['setting']['html']) {
                $t['html'] = '<label><a class="badge badge-success" href='.dr_url('category_share/html', array('id' => $t['id'])).'> '.fc_lang('是').' </a></label>';
            } else {
                $t['html'] = '<label><a class="badge badge-warning" href='.dr_url('category_share/html', array('id' => $t['id'])).'> '.fc_lang('否').' </a></label>';
            }

            $t['dirname'] = dr_strcut($t['dirname'], 15);
            $tree[$t['id']] = $t;
        }

        return $tree;
    }

	/**
     * URL结构
     */
    public function html() {

        $data = array();
        $site = $this->site_model->get_site_info(SITE_ID);
        $data['SITE_MOBILE_HTML'] = $site['SITE_MOBILE_HTML'];
        $data['SITE_REWRITE'] = $site['SITE_REWRITE'];

        // 模块
        $cache = $this->get_cache('module', SITE_ID);
        $module = array();
        $this->load->model('module_model');
        if ($cache) {
            $i = 2;
            foreach ($cache as $dir) {
                $m = $this->get_cache('module-'.SITE_ID.'-'.$dir);
                $data['module'][$dir] = $this->module_model->get($dir);
                $module[$i] = $m;
                $i++;
            }
        }

        // 共享栏目
        $this->load->model('category_share_model');
        $this->load->library('dtree');
        $this->dtree->icon = array('&nbsp;&nbsp;&nbsp;│ ','&nbsp;&nbsp;&nbsp;├─ ','&nbsp;&nbsp;&nbsp;└─ ');
        $this->dtree->nbsp = '&nbsp;&nbsp;&nbsp;';
        $tree = array();
        $category = $this->category_share_model->repair();
        if ($category) {
            $tree = $this->_get_tree($category);
        }
        $str = "<tr class='\$class'>";
        $str.= "<td>\$id</td>";
        $str.= "<td>\$spacer\$name  \$parent</td>";
        $str.= "<td>\$dirname</td>";

        $str.= "<td>\$html</td>";
        $str.= "<td class='dr_option'>\$option</td>";
        $str.= "</tr>";
        $this->dtree->init($tree);

        if (IS_POST) {
            $page = (int)$this->input->post('page');
            $post = $this->input->post('data', true);
            // 站点
            $site['SITE_MOBILE_HTML'] = $post['SITE_MOBILE_HTML'];
            $site['SITE_REWRITE'] = $post['SITE_REWRITE'];
            $this->site_model->edit_site(SITE_ID, array(
                'name' => $site['SITE_NAME'],
                'domain' => $site['SITE_DOMAIN'],
                'setting' => $site
            ));
            // 模块
            if ($module) {
                foreach ($module as $t) {
                    $dir = $t['dirname'];
                    $save = $data['module'][$dir];
                    $save['site'][SITE_ID]['use'] = 1;
                    if ($t['share'] == 0) {
                        // 独立模块规则
                        $save['site'][SITE_ID]['html'] = $post['module'][$dir]['html'];
                        $save['site'][SITE_ID]['domain'] = $post['module'][$dir]['domain'];
                        $save['site'][SITE_ID]['mobile_domain'] = $post['module'][$dir]['mobile_domain'];
                    }
                    $save['site'][SITE_ID]['urlrule'] = $post['module'][$dir]['urlrule'];
                    $this->db->where('dirname', $t['dirname'])->update('module', array(
                        'site' => dr_array2string($save['site']),
                    ));
                }
            }
            // 栏目
            $category = $this->get_cache('module-'.SITE_ID.'-share', 'category');
            if ($category) {
                foreach ($category as $t) {
                    $cat = $this->category_share_model->get($t['id']);
                    $cat['setting']['urlrule'] = $post['category'][$t['id']]['urlrule'];
                    $this->db->where('id', $t['id'])->update(
                        $this->category_share_model->tablename,
                        array(
                            'setting' => dr_array2string($cat['setting'])
                        )
                    );
                }
            }

            $this->system_log('修改网站静态配置'); // 记录日志
            exit($this->admin_msg(fc_lang('操作成功，更新缓存生效'), dr_url('site/html', array('page' => $page)), 1));
        }

        $this->template->assign(array(
            'sid' => SITE_ID,
            'data' => $data,
            'page' => (int)$this->input->get('page'),
            'module' => $module,
            'category' => $this->dtree->get_tree(0, $str),
            'menu' => $this->get_menu_v3(array(
                fc_lang('URL结构') => array('admin/site/html', 'cog'),
            ))
        ));
        $this->template->display('site_html.html');
    }

	/**
     * 切换
     */
    public function select() {
	
		$id	= (int)$this->input->get('id');
		if (!isset($this->site_info[$id])) {
            exit($this->admin_msg(fc_lang('域名配置文件中站点(#%s)不存在', $id)));
        }

        // 异步通知对方域名
        $this->cache->file->save('admin_login_site_select', dr_array2string(array(
            'uid' => $this->admin['uid'],
            'password' => $this->member['password'],
        )), 300);

        $this->admin_msg(
            fc_lang('正在切换到【%s】...',
            $this->site_info[$id]['SITE_NAME']).'<script src="'.$this->site_info[$id]['SITE_URL'].'index.php?c=api&m=aslogin&code='.dr_authcode($this->member['uid'].'-'.md5($this->member['uid'].$this->member['password']), 'ENCODE').'"></script>',
            $this->site_info[$id]['SITE_URL'].SELF, 1,
            0
        );
	}

    /**
     * 管理
     */
    public function index() {
	
		if (IS_POST) {
			$ids = $this->input->post('ids');
			if (!$ids) {
                exit(dr_json(0, fc_lang('您还没有选择呢')));
            }
			$_data = $this->input->post('data');
			foreach ($ids as $id) {
                if ($this->db->where('id<>', (int)$id)->where('domain', $_data[$id]['domain'])->count_all_results('site')) {
                    exit(dr_json(0, fc_lang('域名【%s】已经被使用了', $_data[$id]['domain'])));
                }
				$this->db->where('id', (int)$id)->update('site', $_data[$id]);
			}
            $this->site_model->cache();
            $this->system_log('修改网站站点【#'.@implode(',', $ids).'】'); // 记录日志
			exit(dr_json(1, fc_lang('操作成功，更新缓存生效')));
		}

		$this->template->assign('list', $this->site_model->get_site_data());
		$this->template->display('site_index.html');
	}
	
	/**
     * 添加
     */
    public function add() {
	
		if (IS_POST) {
			$this->load->library('dconfig');
			$data = $this->input->post('data', TRUE);
			$domain	= require WEBPATH.'config/domain.php';
			if (!$data['name']) {
                exit(dr_json(0, '', 'name'));
            } elseif (!preg_match('/[\w-_\.]+\.[\w-_\.]+/i', $data['domain'])) {
                exit(dr_json(0, '', 'domain'));
            } elseif (in_array($data['domain'], $domain)) {
                exit(dr_json(0, fc_lang('%s已经存在', $data['domain']), 'domain'));
            } elseif ($this->db->where('domain', $data['domain'])->count_all_results('site')) {
                exit(dr_json(0, fc_lang('域名【%s】已经被使用了', $data['domain']), 'domain'));
            }
			// 初始化网站配置
			$cfg['SITE_NAME'] = $data['name'];
			$cfg['SITE_DOMAIN'] = $data['domain'];
			$cfg['SITE_DOMAINS'] = '';
			$cfg['SITE_TIMEZONE'] = '8';
			$cfg['SITE_LANGUAGE'] = 'zh-cn';
			$cfg['SITE_TIME_FORMAT'] = 'Y-m-d H:i';
			// 入库
			$data['setting'] = $cfg;
			$id	= $this->site_model->add_site($data);
			if (!$id) {
                exit(dr_json(0, dr_fc_lang('数据异常，入库失败')));
            }
            /*
			if ($id > 1) {
				// 创建静态生成文件夹
				$html = WEBPATH.'html/'.$id.'/';
				dr_mkdirs($html);
				file_put_contents($html.'index.php', "<?php
require dirname(dirname(dirname(__FILE__))).'/index.php';");
				// 创建ucenter接口文件
				dr_mkdirs($html.'api/');
				file_put_contents($html.'api/uc.php', "<?php
require dirname(dirname(dirname(dirname(__FILE__)))).'/api/uc.php';;");
			}*/
			// 安装站点时执行的SQL
			if (is_file(WEBPATH.'cache/install/site/install.sql')
				&& $sql = file_get_contents(WEBPATH.'cache/install/site/install.sql')) {
				$this->sql_query(str_replace(
					array('{dbprefix}', '{siteid}'),
					array($this->db->dbprefix, $id),
					$sql
				));
			}
			// 保存域名
			$domain[$data['domain']] = $id;
			$size = $this->dconfig->file(WEBPATH.'config/site/'.$id.'.php')->note('站点配置文件')->space(32)->to_require_one($this->site_model->config, $cfg);
			if (!$size) {
                exit(dr_json(0, fc_lang('网站域名文件创建失败，请检查config目录权限')));
            }
			$size = $this->dconfig->file(WEBPATH.'config/domain.php')->note('站点域名文件')->space(32)->to_require_one($domain);
			if (!$size) {
                exit(dr_json(0, fc_lang('站点配置文件创建失败，请检查config目录权限')));
            }
            $this->site_model->cache();
            $this->system_log('添加网站站点【#'.$id.'】'.$data['name']); // 记录日志
			exit(dr_json(1, fc_lang('操作成功，更新缓存生效')));
		} else {
			$this->template->display('site_add.html');
		}
    }
	
	/**
     * 站点配置
     */
    public function config() {

		$id = isset($_GET['id']) ? max((int)$_GET['id'], 1) : SITE_ID;
        if ($this->admin['adminid'] > 1
            && !@in_array($id, $this->admin['role']['site'])) {
            $this->admin_msg(fc_lang('抱歉！您无权限操作(%s)', 'site'));
        }

		$data = $this->site_model->get_site_info($id);
		if (!$data) {
            $this->admin_msg(fc_lang('域名配置文件中站点(#%s)不存在', $id));
        }

		$page = max((int)$this->input->get('page'), 0);
        $result	= '';

		if (IS_POST) {
			$cfg = $this->input->post('data', true);
			$cfg['SITE_DOMAIN'] = $this->input->post('domain');
            // 查询非当前站点绑定的域名
            $as = array();
            $all = $this->db->where('id<>', $id)->get('site')->result_array();
            if ($all) {
                foreach ($all as $b) {
                    $set = dr_string2array($b['setting']);
                    $as[] = $b['domain'];
                    if ($set['SITE_MOBILE']) {
                        $as[] = $set['SITE_MOBILE'];
                    }
                    if ($set['SITE_DOMAINS']) {
                        $_arr = @explode(',', $set['SITE_DOMAINS']);
                        if ($_arr) {
                            foreach ($_arr as $_a) {
                                if ($_a) {
                                    $as[] = $_a;
                                }
                            }
                        }
                    }
                }
            }
            // 判断域名是否可用
            if (in_array($cfg['SITE_DOMAIN'], $as)) {
                $result = fc_lang('域名【%s】已经被使用了', $cfg['SITE_DOMAIN']);
            } else {
                $cfg['SITE_DOMAINS'] = str_replace(PHP_EOL, ',', $cfg['SITE_DOMAINS']);
                // 多域名验证
                if ($cfg['SITE_DOMAINS']) {
                    $arr = @explode(',', $cfg['SITE_DOMAINS']);
                    if ($arr) {
                        foreach ($arr as $a) {
                            if (in_array($a, $as)
                                || $a == $cfg['SITE_DOMAIN']
                                || $a == $cfg['SITE_MOBILE']) {
                                $result = fc_lang('域名【%s】已经被使用了', $a);
                                break;
                            }
                        }
                    }
                }
                if (!$result) {
                    $cfg['SITE_NAVIGATOR'] = @implode(',', $this->input->post('navigator', TRUE));
                    $cfg['SITE_IMAGE_CONTENT'] = $cfg['SITE_IMAGE_WATERMARK'] ? $cfg['SITE_IMAGE_CONTENT'] : 0;
                    if (!$cfg['SITE_THEME']) {
                        $cfg['SITE_THEME'] = $data['SITE_THEME'];
                    }
                    if (!$cfg['SITE_TEMPLATE']) {
                        $cfg['SITE_TEMPLATE'] = $data['SITE_TEMPLATE'];
                    }
                    $data = array(
                        'name' => $cfg['SITE_NAME'],
                        'domain' => $cfg['SITE_DOMAIN'],
                        'setting' => $cfg
                    );
                    $this->site_model->edit_site($id, $data);
                    $domain	= require WEBPATH.'config/domain.php';
                    $domain[$cfg['SITE_DOMAIN']] = $id;
                    if ($cfg['SITE_MOBILE']) {
                        $domain[$cfg['SITE_MOBILE']] = $id;
                    }
                    $this->dconfig->file(WEBPATH.'config/site/'.$id.'.php')->note('站点配置文件')->space(32)->to_require_one($this->site_model->config, $cfg);
                    $this->dconfig->file(WEBPATH.'config/domain.php')->note('站点域名文件')->space(32)->to_require_one($domain);
                    $result	= 1;
                }
            }
            $data = $cfg;
            $this->site_model->cache();
			// 删除站点首页缓存
			$this->load->helper('file');
			delete_files(WEBPATH.'cache/index/');
            $this->system_log('配置网站站点【#'.$id.'】'.$data['name']); // 记录日志
            $page = max((int)$this->input->post('page'), 0);
		}
		
		$this->load->helper('directory');
		$files = directory_map(WEBPATH.'statics/watermark/', 1);
		$opacity = array();
		foreach ($files as $t) {
			if (substr($t, -3) == 'ttf') {
				$font[] = $t;
			} else {
				$opacity[] = $t;
			}
		}

        $template_path = array();
        $template_path1 = dr_dir_map(TPLPATH.'pc/web/', 1);
        $template_path1 && $template_path = $template_path1;

        $template_path2 = @array_diff(dr_dir_map(TPLPATH.'pc/', 1), array('web', 'member'));

        $template_path2 && $template_path = ($template_path ? array_merge($template_path, $template_path2) : $template_path2);

		$client = array(1,2,3,4,5);

		$this->template->assign(array(
			'id' => $id,
            'ip' => $this->_get_server_ip(),
            'data' => $data,
            'client' => $client,
			'page' => $page,
			'lang' => dr_dir_map(FCPATH.'dayrui/language/', 1),
			'theme' => dr_get_theme(),
			'result' => $result,
			'is_theme' => strpos($data['SITE_THEME'], 'http://') === 0 ? 1 : 0,
			'navigator' => @explode(',', $data['SITE_NAVIGATOR']),
			'wm_opacity' => $opacity,
			'wm_font_path' => $font,
			'template_path' => @array_unique($template_path),
			'wm_vrt_alignment' => array('top', 'middle', 'bottom'),
			'wm_hor_alignment' => array('left', 'center', 'right'),
		));
		$this->template->display('site_config.html');
    }
	
	/**
     * 删除
     */
    public function del() {
		$id = (int)$this->input->get('id');
		if (!$this->site_info[$id]) {
            $this->admin_msg(fc_lang('站点不存在，请尝试更新一次缓存'));
        } elseif ($id == 1) {
            $this->admin_msg(fc_lang('主站点不能删除'));
        }
		// 卸载模块
		$module = $this->db->get('module')->result_array();
		if ($module) {
			$this->load->model('module_model');
			foreach ($module as $t) {
				$site = dr_string2array($t['site']);
				if (isset($site[$id])) {
					$this->module_model->uninstall($t['id'], $t['dirname'], $id, count($site));
				}
			}
		}
        // 删除相关表
		foreach (array(
		    'page', 'form', 'remote', 'block', 'navigator', 'weixin', 'weixin_keyword', 'weixin_menu',
		    'tag', 'weixin_follow', 'weixin_group', 'weixin_material_file', 'weixin_material_image',
		    'weixin_material_news', 'weixin_material_text', 'weixin_message', 'weixin_user',
            'share_category', 'share_extend_index', 'share_index') as $table) {
            $this->db->query('DROP TABLE IF EXISTS `'.$this->db->dbprefix($id.'_'.$table).'`');
        }
		// 删除站点
		$this->db->delete('site', 'id='.$id);
        // 删除字段
		$this->db->where('relatedid', $id)->where('relatedname', 'page')->delete('field');
		// 删除该站配置
		unlink(WEBPATH.'config/site/'.$id.'.php');
		// 删除该站附件
		$this->load->model('attachment_model');
		$this->attachment_model->delete_for_site($id);
		// 执行的SQL
		if (is_file(WEBPATH.'cache/install/site/uninstall.sql')
			&& $sql = file_get_contents(WEBPATH.'cache/install/site/uninstall.sql')) {
			$this->sql_query(str_replace(
				array('{dbprefix}', '{siteid}'),
				array($this->db->dbprefix, $id),
				$sql
			));
		}
        $this->system_log('删除网站站点【#'.$id.'】'); // 记录日志
		$this->admin_msg(fc_lang('操作成功，更新缓存生效'), dr_url('site/index'), 1);
    }
	
	/**
     * 缓存
     */
    public function cache() {
		$this->site_model->cache();
        (int)$_GET['admin'] or $this->admin_msg(fc_lang('操作成功，正在刷新...'), isset($_SERVER['HTTP_REFERER']) ? $_SERVER['HTTP_REFERER'] : '', 1);
	}
}