<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

require_once FCPATH.'branch/fqb/D_Module.php';

class Test extends D_Module {
	/**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();

    }
	
	public function a(){
		$list = $this->db->query('select ftype,fuid,ffees,flastupdattime from f_entrust_history where fid=2823')->row_array();
print_r($list);
	}
}