<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class Member_lock_sel extends M_Controller {

    /**
     * 首页
     */
    public function index() {
        // 重置页数和统计
        IS_POST && $_GET['page'] = $_GET['total'] = 0;

        // 根据参数筛选结果
        $param = $this->input->get(NULL,TRUE);
        unset($param['s'], $param['c'], $param['m'], $param['d'], $param['page']);

        // 数据库中分页查询
//        list($data, $param) = $this->member_model->limit_page($param, max((int)$_GET['page'], 1), (int)$_GET['total']);

        $userparam = $_POST;
        $fields = $userparam['data']['field']?$userparam['data']['field']:'phone';
        $keyword = $userparam['data']['keyword']?$userparam['data']['keyword']:'0';
        if ($fields == 'phone') {
            $sql = ' ftelephone = "'.$keyword.'"';
        } else {
            $sql = ' femail = "'.$keyword.'"';
        }
//        print_r($sql);
        $java_member = $this->db->query('select * from f_user where '.$sql)->row_array();
        if($java_member){
//            print_r($java_member['fid']);
            $coinlist = $this->db->query('select a.*,b.short_name from user_coin_wallet as a left join system_coin_type as b on a.coin_id=b.id where a.uid = "'.$java_member['fid'].'"')->result_array();
            $userrulelist = $this->db->query('select l.*,c.short_name,r.title from e_user_lock_num as l left join system_coin_type as c on l.coin_id = c.id left join e_user_lock_rule as r on l.rule_id = r.id where l.uid = '.$java_member['fid'].'  order by l.id desc limit 100')->result_array();
        }

//        print_r($rulelist);
        $field = array(
            'phone' => array('fieldname' => 'phone','name' => fc_lang('手机号码')),
            'email' => array('fieldname' => 'email','name' => fc_lang('会员邮箱')),
//                'uid' => array('fieldname' => 'uid','name' => fc_lang('uid')),
        );
        $this->template->assign(array(
            'list' => $java_member,
            'field' => $field,
            'coinlist' => $coinlist,
            'userrulelist' => $userrulelist,
            'param' => $param,
            'pages'	=> $this->get_pagination(dr_url('member_lock/index', $param), $param['total'])
        ));
        $this->template->display('member_lock_sel.html');
    }




}