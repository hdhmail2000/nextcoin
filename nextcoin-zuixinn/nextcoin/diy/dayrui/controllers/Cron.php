<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/* v3.1.0  */

class Cron extends M_Controller {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
        $this->output->enable_profiler(FALSE);
    }

    /**
     * 执行任务和队列
     */
    public function index() {

        // 定时处理首页和单页
        $this->load->helper('directory');
        if (SYS_CACHE_INDEX || SYS_CACHE_MINDEX) {
            $files = directory_map(WEBPATH.'cache/index/', 1);
            if ($files) {
                foreach ($files as $t) {
                    $file = WEBPATH.'cache/index/'.$t;
                    if (strpos($t, '-home.html')) {
                        // 首页
                        if (filemtime($file) + SYS_CACHE_INDEX < SYS_TIME) {
                            @unlink($file);
                        }
                    } else {
                        // 模块
                        if (filemtime($file) + SYS_CACHE_MINDEX < SYS_TIME) {
                            @unlink($file);
                        }
                    }
                }
            }
        }

        // 单页
        if (SYS_CACHE_PAGE) {
            $files = directory_map(WEBPATH.'cache/page/', 1);
            if ($files) {
                foreach ($files as $t) {
                    $file = WEBPATH.'cache/page/'.$t;
                    if (filemtime($file) + SYS_CACHE_PAGE < SYS_TIME) {
                        @unlink($file);
                    }
                }
            }
        }

        // 自动更新模块缓存（3小时一次）移动端不执行
        if (!$this->mobile) {
            $file = WEBPATH.'cache/cron/module.cache';
            $auto = is_file($file) ? (int)file_get_contents($file) : 0;
            if (!$auto || $auto + 10800 < SYS_TIME) {
                $this->clear_cache('module');
                @file_put_contents($file, SYS_TIME);
            }
        }

        // 定时删除缓存文件（3天一次）
        if (defined('SYS_AUTO_CACHE_TIME') && SYS_AUTO_CACHE_TIME) {
            $num = 3600*24*max(1, intval(SYS_AUTO_CACHE_TIME));
            $file = WEBPATH.'cache/cron/file.cache';
            $auto = is_file($file) ? (int)file_get_contents($file) : 0;
            if (!$auto || $auto + $num < SYS_TIME) {
                $this->load->helper('file');
                delete_files(WEBPATH.'cache/sql/');
                delete_files(WEBPATH.'cache/file/');
                delete_files(WEBPATH.'cache/page/');
                delete_files(WEBPATH.'cache/index/');
                delete_files(WEBPATH.'cache/templates/');
                @file_put_contents($file, SYS_TIME);
            }
        }


        // 每天更新模块内容的日点击量

        // 未到发送时间
        if (get_cookie('cron')) {
            exit('未到执行时间');
        }

        // 清理一个小时未活动的会员
        $this->db->where('`time` < '.(SYS_TIME - 3600))->delete('member_online');

        // 在线人数清理
        $num = max(100, (int)SYS_ONLINE_NUM);
        $this->db->query('delete from '.$this->db->dbprefix('member_online').' where uid not in (select t.uid from (select uid from '.$this->db->dbprefix('member_online').' order by `time` desc limit '.$num.') as t)');

        // 一次执行的任务数量
        $pernum = defined('SYS_CRON_NUMS') && SYS_CRON_NUMS ? SYS_CRON_NUMS : 10;

        // 用户每多少秒调用本程序
        set_cookie('cron', 1, SYS_CRON_TIME);

        // 查询所有队列记录
        $queue = $this->db->order_by('status ASC,id ASC')->limit($pernum)->get('cron_queue')->result_array();
        if (!$queue) {
            // 所有任务执行完毕
            $this->db->query('TRUNCATE `'.$this->db->dbprefix('cron_queue').'`');
        } else {
            foreach ($queue as $data) {
                $this->cron_model->execute($data);
            }
        }


        // 订单自动收货
        if (is_dir(FCPATH.'module/order/')) {
            $module = $this->get_cache('module-'.SITE_ID.'-order');
            if ($module) {
                $this->mconfig = $mconfig = isset($module['setting'][SITE_ID]) ? $module['setting'][SITE_ID] : $module['setting'];
                $time = (int)$mconfig['config']['shtime'] * 3600 * 24;
                if ($time) {
                    // 查询时间段 shipping_time
                    $time = SYS_TIME - $time;
                    $list = $this->db->where('order_status', 4)
                        ->where('shipping_time<'.$time)->order_by('id ASC')
                        ->limit(30)->get(SITE_ID.'_order')->result_array();
                    if ($list) {
                        $this->load->add_package_path(FCPATH.'module/order/');
                        $this->load->helper('order');
                        $this->load->model('order_model');
                        foreach ($list as $order) {
                            $this->order_model->shouhuo($order);
                        }
                    }

                }
            }
        }

        // 本次任务执行完毕
        exit('本次任务执行完毕');
    }

}