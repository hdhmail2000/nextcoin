<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

require FCPATH.'branch/fqb/D_File.php';

class Member_tpl extends D_File {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
        $this->path = $this->router->method == 'mobile' || $this->input->get('ismb') ? TPLPATH.'mobile/member/' : TPLPATH.'pc/member/';
        $this->template->assign(array(
			'path' => $this->path,
			'furi' => 'member_tpl/',
			'auth' => 'admin/member_tpl/',
			'menu' => $this->get_menu(array(
				fc_lang('模板管理') => 'admin/member_tpl/index',
				fc_lang('移动端模板') => 'admin/member_tpl/mobile',
				fc_lang('标签向导') => 'admin/member_tpl/tag',
			)),
            'ismb' => $this->router->method == 'mobile' ? 1 : 0,
		));
    }
	
}