<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');
 
require FCPATH.'branch/fqb/D_File.php';

class Member_theme extends D_File {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
		$this->path = WEBPATH.'statics/';
		$this->template->assign(array(
			'path' => $this->path,
			'furi' => 'member_theme/',
			'auth' => 'admin/member_theme/',
			'menu' => $this->get_menu(array(
				fc_lang('风格管理') => 'admin/member_theme/index'
			)),
		));
    }
	
}