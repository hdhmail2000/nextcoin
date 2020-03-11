<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');
//require dirname(__FILE__).'/Controller.php';
/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.2
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 */
require FCPATH.'module/exc/controllers/Controller.php';
class Home extends Controller {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
    }

    /**
     * 首页
     */
    public function index() {
		parent::_index();
    }
}