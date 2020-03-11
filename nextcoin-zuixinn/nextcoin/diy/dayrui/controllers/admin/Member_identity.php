<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class Member_identity extends M_Controller {

	/**
	 * 首页
	 */
	public function index() {

        // 重置页数和统计
        IS_POST && $_GET['page'] = $_GET['total'] = 0;

        // 根据参数筛选结果
        $param = $this->input->get(NULL,TRUE);
        unset($param['s'],$param['c'], $param['m'], $param['d'], $param['page']);

		//query('select * from user_coin_wallet where uid='.$uid.' and coin_id='.$trade['symbol'])

		$sql = 'select * from f_user_identity where ';
		$fstatus = @$_REQUEST['fstatus']?$_REQUEST['fstatus']:0;
        $fcode = @$_REQUEST['fcode']?$_REQUEST['fcode']: '';
		if (0 <= $fstatus) {
			$sql .= " fstatus= '" . $fstatus . "' ";
		} else {
			$sql .= " 1=1 ";
		}

		if (!empty($fcode)) {
//			$sql .= " and fcode= '" . $fcode . "' ";
			$sql .= ' and fname like "%'.$fcode.'%"';
		}

		$sql .= " order by fcreatetime desc LIMIT ".((max((int)$_GET['page'], 1)-1)*SITE_ADMIN_PAGESIZE).", ".SITE_ADMIN_PAGESIZE;

        // 数据库中分页查询
		$list = $this->db->query($sql)->result_array();
		//echo $this->db->last_query();
		$data = $this->db->query("SELECT count(*) as total FROM f_user_identity where fstatus = '".$fstatus."'".' and fname like "%'.$fcode.'%"')->row_array();
        $total = (int) $data['total'];
        $param = array(
            "total"=> $total,
            "fstatus" => $fstatus,
            "fcode"   => $fcode
        );
        // 存储当前页URL
        $this->_set_back_url('member_identity/index', $param);
		$this->template->assign(array(
			'list' => $list,
			'fcode' => $fcode,
            'param' => $param,
            'pages'	=> $this->get_pagination(dr_url('member_identity/index', $param), $param['total'])
		));
		$this->template->display('member_identity.html');
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