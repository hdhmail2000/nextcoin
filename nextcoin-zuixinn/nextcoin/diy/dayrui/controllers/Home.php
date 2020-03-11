<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/* v3.1.0  */
 
class Home extends M_Controller {


    /**
     * 首页
     */
    public function index() {
		echo "<script>window.location='/index.php?s=exc&c=indexController'</script>";
         exit;
        //$this->_indexc();
    }

}