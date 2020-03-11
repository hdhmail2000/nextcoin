<?php
/**
 * Created by PhpStorm.
 * User: chunjie
 * Date: 14-6-23
 * Time: 17:11
 */

class module_hooks {

    public $ci;

    /**
     * 构造函数
     */
    function __construct() {
        $this->ci = &get_instance();
    }

    // 发布时同步到其他站点
    function syn_content_add($param) {


    }
}
