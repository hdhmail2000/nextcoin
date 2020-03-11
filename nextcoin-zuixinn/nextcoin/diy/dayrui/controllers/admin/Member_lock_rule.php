<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class Member_lock_rule extends M_Controller {

    /**
     * 首页
     */
    public function index() {
        // 重置页数和统计
        IS_POST && $_GET['page'] = $_GET['total'] = 0;

        // 根据参数筛选结果
        $param = $this->input->get(NULL,TRUE);
        unset($param['s'],$param['c'], $param['m'], $param['d'], $param['page']);

        $ruleparam = $_POST;
        $ruleres['title'] = $ruleparam['data']['title'];
        $ruleres['level'] = $ruleparam['data']['level'];
        $ruleres['rule'] = $ruleparam['data']['rule'];
        if ($ruleres['title'] && $ruleres['level'] && $ruleres['rule']) {
            $ruleres['time'] = time();
            $this->db->insert('user_lock_rule', $ruleres);
        }

        $rulelist = $this->db->query('select * from e_user_lock_rule')->result_array();
//        print_r($rulelist);
        $field = array(
            'phone' => array('fieldname' => 'phone','name' => fc_lang('手机号码')),
            'email' => array('fieldname' => 'email','name' => fc_lang('会员邮箱')),
//                'uid' => array('fieldname' => 'uid','name' => fc_lang('uid')),
        );
        $this->template->assign(array(
            'list' => $rulelist,
            'field' => $field,
            'param' => $param,
            'pages'	=> $this->get_pagination(dr_url('member_identity/index', $param), $param['total'])
        ));
        $this->template->display('member_lock_rule.html');
    }


    /***
     * 锁定
     */
    public function lockcoin() {
        $data = $_POST;
        print_r($data);
        $usercoininfo = $this->db->query('select * from user_coin_wallet where uid= ' . $data['userid'] .' and coin_id = ' . $data['coinid'])->row_array();
        if($usercoininfo){
            $this->db->query('update user_coin_wallet set total='. $fstatus . ', fupdatetime="' . date('Y-m-d H:i:s') . '" where fuid=' . $fuid);
            $this->db->insert('member_menu', array(
                'uri' => '',
                'url' => '',
                'pid' => 0,
                'mark' => 'm_mod',
                'name' => '内容',
                'target' => 0,
                'hidden' => 0,
                'displayorder' => 0,
            ));
            print_r($usercoininfo);
        }


    }
    /**
     * 审核
     */
    public function identity() {

        $fuid = @$_REQUEST['fuid']?$_REQUEST['fuid']:0;
        $fstatus = @$_REQUEST['fstatus']?$_REQUEST['fstatus']:0;
        if (!empty($fstatus) && !empty($fuid)) {
            $userinfo = $this->db->query('select * from f_user_identity where fuid=' . $fuid)->row_array();
            $this->db->query('update f_user_identity set fstatus='. $fstatus . ', fupdatetime="' . date('Y-m-d H:i:s') . '" where fuid=' . $fuid);
            if (1 == $fstatus) {//实名认证送币
                $this->db->query('update f_user set fhasrealvalidate='. $fstatus . ', frealname="'. $userinfo['fname'] . '", fidentityno="'. $userinfo['fcode'] . '", fhasrealvalidatetime="' . date('Y-m-d H:i:s') . '" where fid=' . $fuid);
                $user = $this->db->query('select * from f_user where fshowid='.$userinfo['fuid'])->row_array();
//				// 完成实名认证 奖励20平台币
//				$this->db->query('update user_coin_wallet set total=total+20 where coin_id=37 and uid='.$user['fshowid']);
//
//				//增加记录
//				$this->db->query('insert into f_coin_record(uid,coin_id,value,note,inputtime) values('.$user['fshowid'].',37,20,"完成实名认证",'.time().')');
            }
            $success = array('msg' => fc_lang('操作成功'), 'code' => 1, 'data'=>array());
            echo json_encode($success, JSON_UNESCAPED_UNICODE);
        }
    }

}