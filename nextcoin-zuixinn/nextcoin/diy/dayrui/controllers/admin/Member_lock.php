<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class Member_lock extends M_Controller {

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
        }
        $cointype = $this->db->query('select id,short_name from system_coin_type')->result_array();
        $rulelist = $this->db->query('select * from e_user_lock_rule')->result_array();
        $userrulelist = $this->db->query('select l.*,c.short_name,r.title from e_user_lock_num as l left join system_coin_type as c on l.coin_id = c.id left join e_user_lock_rule as r on l.rule_id = r.id  order by l.id desc limit 200')->result_array();
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
            'cointype' => $cointype,
            'rulelist' => $rulelist,
            'userrulelist' => $userrulelist,
            'param' => $param,
            'pages'	=> $this->get_pagination(dr_url('member_lock/index', $param), $param['total'])
        ));
        $this->template->display('member_lock.html');
    }


    /***
     * 锁定
     */
    public function lockcoin() {
        $data = $_POST;
//        print_r($data);die();
        if($data['data']['userid'] =='' && $data['data']['coinid'] =='' && $data['data']['ruleid'] =='' && $data['data']['coinnum'] =='' ){
            $success = array('msg' => fc_lang('操作失败，参数不能为空'), 'code' => 201, 'data'=>array());
            echo json_encode($success, JSON_UNESCAPED_UNICODE);
            header("Refresh:3;url=/admin.php?c=Member_lock&m=index");
            die();
        }
        $userinfo = $this->db->query('select * from f_user where fid= ' . $data['data']['userid'] )->row_array();
        if(!$userinfo){
            $success = array('msg' => fc_lang('用户不存在'), 'code' => 202, 'data'=>array());
            echo json_encode($success, JSON_UNESCAPED_UNICODE);
            header("Refresh:3;url=/admin.php?c=Member_lock&m=index");
            die();
        }
        $ruleinfo = $this->db->query('select * from e_user_lock_rule where id= ' . $data['data']['ruleid'] )->row_array();
        $usercoininfo = $this->db->query('select frozen from user_coin_wallet where uid= ' . $data['data']['userid'] .' and coin_id = ' . $data['data']['coinid'])->row_array();
        $frozen =  $usercoininfo['frozen']*1+$data['data']['coinnum']*1;
        $timestamp = time();
//        die();

        $this->db->trans_begin();

        $this->db->query('update user_coin_wallet set frozen= ' . $frozen . ' where uid= ' . $data['data']['userid'] .' and coin_id = '. $data['data']['coinid']);
        $this->db->insert('user_lock_num', array(
            'uid' => $data['data']['userid'],
            'coin_id' => $data['data']['coinid'],
            'total' => $data['data']['coinnum']*1,
            'time' => $timestamp,
            'rule_id' => $ruleinfo['id'],
            'rule' => $ruleinfo['rule'],
            'rule_level' => $ruleinfo['level'],
        ));

        if ($this->db->trans_status() === FALSE)
        {
            $this->db->trans_rollback();
            $success = array('msg' => fc_lang('操做失败'), 'code' => 203, 'data'=>array());
            echo json_encode($success, JSON_UNESCAPED_UNICODE);
            header("Refresh:3;url=/admin.php?c=Member_lock&m=index");
        }
        else
        {
            $this->db->trans_commit();
            $success = array('msg' => fc_lang('操作成功'), 'code' => 200, 'data'=>array());
            echo json_encode($success, JSON_UNESCAPED_UNICODE);
            header("Refresh:3;url=/admin.php?c=Member_lock&m=index");
        }

    }

    /**
     * 数据分页显示
     *
     * @param	array	$param	条件参数
     * @param	intval	$page	页数
     * @param	intval	$total	总数据
     * @return	array
     */
    public function limit_page($param, $page, $total) {

        if (!$total || IS_POST) {
            $select = $this->db->select('count(*) as total');
            $_param = $this->_where($select, $param);
            $data = $select->get('user_lock_num')->row_array();
            unset($select);
            $total = (int) $data['total'];
            if (!$total) {
                $_param['total'] = 0;
                return array(array(), $_param);
            }
            $page = 1;
        }

        $select = $this->db->select('member_data.*,member.*,member.uid as uid')->join('member_data', 'member_data.uid = member.uid', 'left')->limit(SITE_ADMIN_PAGESIZE, SITE_ADMIN_PAGESIZE * ($page - 1));
        $_param = $this->_where($select, $param);
        $order = dr_get_order_string(isset($_GET['order']) && strpos($_GET['order'], "undefined") !== 0 ? $this->input->get('order', TRUE) : 'member.uid desc', 'member.uid desc');
        $data = $select->order_by($order)->get('member')->result_array();
        $_param['total'] = $total;
        $_param['order'] = $order;

        return array($data, $_param);
    }

    /**
     * 条件查询
     *
     * @param	object	$select	查询对象
     * @param	array	$param	条件参数
     * @return	array
     */
    private function _where(&$select, $data) {


        // 存在POST提交时，重新生成缓存文件
        if (IS_POST) {
            $data = $this->input->post('data');
            foreach ($data as $i => $t) {
                if ($t == '') {
                    unset($data[$i]);
                }
            }
        }

        // 存在search参数时，读取缓存文件
        if ($data) {
            if (isset($data['keyword']) && $data['keyword'] != '' && $data['field']) {
                if ($data['field'] == 'uid') {
                    // 按id查询
                    $id = array();
                    $ids = explode(',', $data['keyword']);
                    foreach ($ids as $i) {
                        $id[] = (int)$i;
                    }
                    $select->where_in('member.uid', $id);
                }
            }
            // 查询会员组
            isset($data['groupid']) && $data['groupid'] && $select->where('groupid', (int)$data['groupid']);
        }

        // 判断groupid
        !isset($data['groupid']) && $_GET['groupid'] && $select->where('groupid', (int)$_GET['groupid']);

        return $data;
    }
}