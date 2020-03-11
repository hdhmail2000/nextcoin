<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');



class Update extends M_Controller {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
        $this->db->db_debug = TRUE;
    }

    /**
     * 更新程序
     */
    public function index() {


        $this->admin_msg('升级完成，请按F5刷新整个页面', '', 1);

    }

    private function set_lang($file, $version, $code) {


    }
}