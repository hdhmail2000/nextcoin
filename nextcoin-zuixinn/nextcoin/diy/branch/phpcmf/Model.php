<?php namespace Phpcmf;

// 模型类
class Model {

    public $ci;
    public $db;
    public $id;
    public $key;
    public $table;
    public $prefix;

    public $uid;
    public $admin;
    public $member;

    protected $date_field;
    protected $param;
    protected $init;

    public function __construct(...$params) {
        // 数据库
        $this->ci = & get_instance();
        $this->db = & $this->ci->db;
        $this->prefix = $this->db->dbprefix;
        $this->key = $this->id = 'id';
        $this->uid = \Phpcmf\Service::C()->uid;
        $this->site = \Phpcmf\Service::C()->site;
        $this->admin = \Phpcmf\Service::C()->admin;
        $this->member = \Phpcmf\Service::C()->member;
    }

    // 设置初始化查询条件
    public function init($data) {

        isset($data['id']) && $this->id = $this->key = $data['id'];
        isset($data['table']) && $this->table = $data['table'];
        isset($data['field']) && $this->field = $data['field'];
        isset($data['date_field']) && $this->date_field = $data['date_field'];

        isset($data['order_by']) && $this->param['order_list'] = $data['order_by'];
        isset($data['order_list']) && $this->param['order_list'] = $data['order_list'];
        isset($data['where_list']) && $this->param['where_list'] = $data['where_list'];
        isset($data['join_list']) && $this->param['join_list'] = $data['join_list'];
        isset($data['select_list']) && $this->param['select_list'] = $data['select_list'];

        $this->init = $data; // 方便调用

        return $this;
    }

    // 设置列表搜索条件
    public function set_where_list($where) {
        $this->param['where_list'] = $where;
    }

    // 设置操作主键
    public function id($id) {
        $this->key = $id;
        return $this;
    }

    // 设置操作表
    public function table($name) {
        $this->table = $name;
        return $this;
    }

    // 设置操作站点的表
    public function table_site($name, $site = SITE_ID) {
        $this->table = $site.'_'.$name;
        return $this;
    }

    // 获取表前缀
    public function dbprefix($name = '') {
        return $this->prefix.$name;
    }

    // 执行sql
    public function query($sql) {

        if (!$this->db->simple_query($sql)) {
            $error = $this->db->error();
            log_message('error', $sql.': '.$error['message']);
            return $this->_return_error($error['message']);
        }

        return dr_return_data(1);
    }

    // 附表不存在时创建附表
    public function is_data_table($table, $tid) {
        if (!$this->db->query("SHOW TABLES LIKE '".$this->dbprefix($table.$tid)."'")->row_array()) {
            // 附表不存在时创建附表
            $sql = $this->db->query("SHOW CREATE TABLE `".$this->dbprefix($table)."0`")->row_array();
            $this->db->query(str_replace(
                array($sql['Table'], 'CREATE TABLE '),
                array($this->dbprefix($table.$tid), 'CREATE TABLE IF NOT EXISTS '),
                $sql['Create Table']
            ));
        }
    }

    // 字段值是否存在
    public function is_exists($id, $name, $value) {

        $builder = $this->db;

        // 条件
        if ($this->param['where']) {
            foreach ($this->param['where'] as $v) {
                count($v) == 2 ? $builder->where($v[0], $v[1]) : $builder->where($v);
            }
        }

        $rt = $builder->where($name, $value)->where($this->key.'<>', $id)->count_all_results($this->table);

        $this->_clear();

        return $rt;
    }


    // 统计数量
    public function counts($table = '', $where = '') {

        $builder = $this->db;

        // 条件
        if ($this->param['where']) {
            foreach ($this->param['where'] as $v) {
                count($v) == 2 ? $builder->where($v[0], $v[1]) : $builder->where($v);
            }
        }

        $where && $builder->where($where);

        $this->_clear();

        return $builder->count_all_results(!$table ? $this->table : $table);
    }

    // 插入数据
    public function insert($data) {

        $this->db->insert($this->table, $data);
        $rt = $this->db->error();
        if ($rt['code']) {
            log_message('error', $this->table.': '.$rt['message'].'<br>'.FC_NOW_URL);
            return $this->_return_error($this->table.': '.$rt['message']);
        }

        $id = $this->db->insert_id();
        !$id && $id = intval($data[$this->key]);

        if (!$id) {
            log_message('error', $this->table.': 主键获取失败<br>'.FC_NOW_URL);
            return $this->_return_error($this->table.': 主键获取失败');
        }

        $this->_clear();

        return dr_return_data($id);
    }

    // 插入数据
    public function replace($data) {

        $this->db->replace($this->table, $data);
        $rt = $this->db->error();
        if ($rt['code']) {
            log_message('error', $this->table.': '.$rt['message'].'<br>'.FC_NOW_URL);
            return $this->_return_error($this->table.': '.$rt['message']);
        }

        $id = $this->db->insert_id();
        !$id && $id = intval($data[$this->key]);

        if (!$id) {
            log_message('error', $this->table.': 主键获取失败<br>'.FC_NOW_URL);
            return $this->_return_error($this->table.': 主键获取失败');
        }

        $this->_clear();

        return dr_return_data($id);
    }

    // 更新数据
    public function update($id, $data, $where = '') {

        $db = $this->db;
        $db->where($this->key, (int)$id);

        $where && $db->where($where);
        $db->update($this->table, $data);

        $rt = $this->db->error();
        if ($rt['code']) {
            log_message('error', $this->table.': '.$rt['message'].'<br>'.FC_NOW_URL);
            return $this->_return_error($this->table.': '.$rt['message']);
        }

        $this->_clear();

        return dr_return_data($id);
    }

    // 删除数据
    /*
    * 主键
    * */
    public function delete($id = 0 , $where = '') {

        $db = $this->db;

        $where && $db->where($where);
        $id && $db->where($this->key, (int)$id);

        // 条件
        if ($this->param['where']) {
            foreach ($this->param['where'] as $v) {
                count($v) == 2 ? $db->where($v[0], $v[1]) : $db->where($v);
            }
        }

        // in条件
        if ($this->param['where_in']) {
            foreach ($this->param['where_in'] as $v) {
                count($v) == 2 ? $db->where_in($v[0], $v[1]) : $db->where_in($v);
            }
        }

        // 执行删除
        $db->delete($this->table);

        $rt = $this->db->error();
        if ($rt['code']) {
            log_message('error', $this->table.': '.$rt['message'].'<br>'.FC_NOW_URL);
            return $this->_return_error($this->table.': '.$rt['message']);
        }


        $this->_clear();

        return dr_return_data($id);
    }

    // 批量删除数据
    /*
    * 主键数组
    * */
    public function deleteAll($ids, $where = '') {

        $db = $this->db;
        $where && $db->where($where);

        // 条件
        if ($this->param['where']) {
            foreach ($this->param['where'] as $v) {
                count($v) == 2 ? $db->where($v[0], $v[1]) : $db->where($v);
            }
        }

        // in条件
        if ($this->param['where_in']) {
            foreach ($this->param['where_in'] as $v) {
                count($v) == 2 ? $db->where_in($v[0], $v[1]) : $db->where_in($v);
            }
        }

        $db->where_in($this->key, (array)$ids)->delete($this->table);

        $rt = $this->db->error();
        if ($rt['code']) {
            log_message('error', $this->table.': '.$rt['message'].'<br>'.FC_NOW_URL);
            return $this->_return_error($this->table.': '.$rt['message']);
        }


        $this->_clear();

        return dr_return_data(1);
    }

    /*
     * 保存单个数据
     * 主键
     * 字段名
     * 字段值
     * */
    public function save($id, $name, $value, $where = '') {

        $db = $this->db;
        $where && $db->where($where);
        $db->where($this->key, (int)$id)->update($this->table, [$name => $value]);

        $rt = $this->db->error();
        if ($rt['code']) {
            return $this->_return_error($this->table.': '.$rt['message']);
        }


        $this->_clear();

        return dr_return_data($id);
    }

    /*
     * 获取单个数据
     * 主键
     * */
    public function get($id) {

        $query = $this->db->where($this->key, (int)$id)->get($this->table);
        if (!$query) {
            return [];
        }

        $rt = $query->row_array();
        $this->_clear();

        return $rt;
    }

    /*
     * 获取全部数据
     * 指定数量
     * 数组主键id
     * */
    public function getAll($num = 0, $key = '') {

        $builder = $this->db;

        // 条件
        if ($this->param['where']) {
            foreach ($this->param['where'] as $v) {
                count($v) == 2 ? $builder->where($v[0], $v[1]) : $builder->where($v);
            }
        }

        // in条件
        if ($this->param['where_in']) {
            foreach ($this->param['where_in'] as $v) {
                count($v) == 2 ? $builder->where_in($v[0], $v[1]) : $builder->where_in($v);
            }
        }

        // 排序
        $this->param['order'] && $builder->order_by($this->param['order']);

        // 数量控制
        $num && $builder->limit($num);

        $query = $builder->get($this->table);
        if (!$query) {
            $this->_clear();
            return [];
        }

        $rt = $query->result_array();
        if ($rt && $key) {
            $rt2 = $rt;
            $rt = [];
            foreach ($rt2 as $i => $t) {
                $rt[(isset($t[$key]) ? $t[$key] : $i)] = $t;
            }
        }

        $this->_clear();

        return $rt;
    }

    /*
     * 获取单个数据
     * */
    public function getRow() {

        $builder = $this->db;

        // 条件
        if ($this->param['where']) {
            foreach ($this->param['where'] as $v) {
                count($v) == 2 ? $builder->where($v[0], $v[1]) : $builder->where($v);
            }
        }

        // in条件
        if ($this->param['where_in']) {
            foreach ($this->param['where_in'] as $v) {
                count($v) == 2 ? $builder->where_in($v[0], $v[1]) : $builder->where_in($v);
            }
        }

        if (!$builder) {
            return [];
        }

        // 排序
        $this->param['order'] && $builder->order_by($this->param['order']);

        $rt = $builder->get($this->table);

        $data = [];
        $rt && $data = $rt->row_array();

        $this->_clear();

        return $data;
    }

    /*
     * 操作数据
     * 数据不存在-1, 变更值0, 变更至1
     * */
    public function used($id, $name) {

        $data = $this->db->select($name)->where('id', (int)$id)->get($this->table)->row_array();

        if ($data) {
            $value = $data[$name] ? 0 : 1;
            // 更新
            $this->db->where('id', $id)->update($this->table, [$name => $value]);
            return $value;
        }

        return -1;
    }

    // 条件
    public function where($name, $value = '') {
        $this->param['where'][] = strlen($value) ? [$name, $value] : $name;
        return $this;
    }

    // in条件
    public function where_in($name, $value) {

        if (is_array($value) && $value) {
            $this->param['where_in'][] = [$name, $value];
        }

        return $this;
    }

    // 排序
    public function order_by($value) {
        $this->param['order'] = $value;
        return $this;
    }

    // 运行SQL
    public function query_sql($sql, $more = 0) {


        $sql = str_replace('{dbprefix}', $this->prefix, $sql);
        $query = $this->db->query($sql);
        if (!$query) {
            return [];
        }

        return $more ? $query->result_array() : $query->row_array();
    }

    // 批量执行
    public function query_all($sql) {

        if (!$sql) {
            return '';
        }

        $sql = str_replace('{dbprefix}', $this->prefix, $sql);
        $sql_data = explode(';SQL_FINECMS_EOL', trim(str_replace(array(PHP_EOL, chr(13), chr(10)), 'SQL_FINECMS_EOL', $sql)));

        foreach($sql_data as $query){
            if (!$query) {
                continue;
            }
            $ret = '';
            $queries = explode('SQL_FINECMS_EOL', trim($query));
            foreach($queries as $query) {
                $ret.= $query[0] == '#' || $query[0].$query[1] == '--' ? '' : $query;
            }
            if (!$ret) {
                continue;
            }
            if (!$this->db->simple_query($ret)) {
                $rt = $this->db->error();
                return $ret.': '.$rt['message'];
            }
        }

        return '';
    }

    // 获取当前执行后的sql语句
    public function get_sql_query() {
        return '';
    }

    private function _clear() {
        $this->key = $this->id;
        $this->date_field = 'inputtime';
        $this->field = [];
        $this->param = [];
    }

    // 显示数据库错误
    private function _return_error($msg) {
        return IS_ADMIN || IS_DEV ? dr_return_data(0, $msg) : dr_return_data(0, dr_lang('系统错误'));
    }
}
