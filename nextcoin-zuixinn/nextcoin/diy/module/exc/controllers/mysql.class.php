<?php
/**
 *数据库连接类
 *创建人 ：田洪强
 *
 *用处：封装数据库操作
 *
 *
 *
 * */
//define('DB_HOST', 'rm-t4n440nk2m2fy00i0.mysql.singapore.rds.aliyuncs.com');
//define('DB_NAME', 'duota_db');
//define('DB_USER', 'duota_mysql');
//define('DB_PASS', 'Duotamysql123');
 
//define('DB_HOST', 'rm-t4n9c028ca2d0jkr3.mysql.singapore.rds.aliyuncs.com:3306');
//define('DB_NAME', 'fw_coin_db');
//define('DB_USER', 'fw_coin');
//define('DB_PASS', '1aIwqZEG4J9tLyJKAjFX');

//define('DB_HOST', 'rm-t4nhp3nn71k45au69.mysql.singapore.rds.aliyuncs.com:3306');
//define('DB_NAME', 'fch_mysql_db');
//define('DB_USER', 'fch_db');
//define('DB_PASS', '2OOQKC9xDwwLysufpb');

//define('DB_HOST', 'rm-t4n49tmw1165z0s4k.mysql.singapore.rds.aliyuncs.com');
//define('DB_NAME', 'tonglian');
//define('DB_USER', 'qibei');
//define('DB_PASS', 'vDN1AmloYBc9h7mt');

define('DB_HOST', 'rm-t4n34sn8i748n37j4.mysql.singapore.rds.aliyuncs.com');
define('DB_NAME', 'daerwen_data');
define('DB_USER', 'daerwen_db');
define('DB_PASS', '6m3bZHE4RoOwIDDKMyCq3undsVrxUs');

define('DB_PORT', '3306');
define( "DB_CHARSET" , 'UTF8');
define( "DB_TABLE_PFX" , '');
define( "PAGE_SIZE", 10);
class DB {
	private $sql;
	private $wheresql;//条件AND sql
	private $tableName;//表名
	private $whereinsql;//条件IN sql
	private $whereorsql;//条件OR sql
	private $limitsql;//分页sql
	private $ordersql;//排序sql
	public  $result;
	//public $pageNums = PAGE_SIZE;//每页显示的条数
	public $ds = 0;//是否打印sql语句，0不，1要
	public static $db;
	public $pdo;

	//得到一个数据库对象
	public static function getDBClass() {
		if(!(self::$db instanceof self)){
			self::$db = new self;
		}
		return self::$db;
	}

	/**
	 * 构造方法，得到数据库连接
	 * */
	private function __construct() { 
		$dsn = "mysql:host=" . DB_HOST . ";dbname=" . DB_NAME;
		$dsn = "mysql:host=" . DB_HOST . ";port=" . DB_PORT . ";dbname=" . DB_NAME;

		try {
			$this->pdo = new PDO($dsn, DB_USER, DB_PASS, array(PDO::ATTR_PERSISTENT => true,PDO::ATTR_ORACLE_NULLS => PDO::NULL_TO_STRING,PDO::ATTR_ERRMODE=>PDO::ERRMODE_EXCEPTION));
		} catch (PDOException $e) {
			echo 'error:'.$e->getMessage();
			$this->pdo = new PDO($dsn, DB_USER, DB_PASS);
		}
		$chart_set = "set names " . DB_CHARSET;
		$this->pdo->exec($chart_set); //设置连接编码
	}
	public function table($tableName){
		if(empty($tableName)){
			return false;
		}else{
			$this->tableName=DB_TABLE_PFX . $tableName;
			return $this;
		}
	}


	 /**
	 * 修改
	 */
	public function update($tablename, $data){
		if(!empty($tablename)){
			$isql = "";
			if (is_array($data)) {//字段-数组值
				foreach ($data as $key=>$val) {
					if(is_numeric($val)){
						$isql .= ', ' . '`' . $key ."` = " . $val . " ";
					}else{
						$isql .= ', ' . '`' . $key ."` = '" . $val . "' ";
					}
				}
				$isql=substr($isql,1);
			} else {//字段-值
				$isql = $data;
			}
			$this->sql = "UPDATE " . DB_TABLE_PFX . $tablename . " SET " . $isql;
			//echo $this->sql;
			if($this->execute_sql()) {
				return true;
			}
	    }
		return FALSE;
	}

	/**
	 * 添加一个数据到数据库
	 */
	public function add($table_name, $arr){
		$cloumns=$values='';
		if (is_array($arr)) {
			foreach($arr as $key=>$value){
				$cloumns.=$key.",";
					$values.="'".$value."'".",";

			}
			$cloumns = " (" . rtrim($cloumns,",") . " ) ";
			$values = rtrim($values,",");
			$this->sql = "INSERT INTO ". DB_TABLE_PFX . $table_name . $cloumns .
						"VALUES (" . $values . ")";
						//echo $this->sql;
			if ($this->execute_sql()) {
					return true;
			}
		}
		return false;
	}

	/*
	 * 删除
	 */
	public function del($table){
		$this->sql = "DELETE FROM " . DB_TABLE_PFX . $table;
		if($this->execute_sql()) {
				return true;
		}
	    	return false;
	}
	/**
     * 查询单条数据
     */
    public function find($table_name, $filed=null) {
        $issql = "";
        if (!empty($filed)) {
            if (is_array($filed)) {
                foreach ($filed as $key=>$val) {
                    $issql .= "," . $val;
                }
                $issql = substr($issql, 1);
            } else {
                $issql = $filed;
            }
        } else {
            $issql = "*";
        }

        $this->sql = "SELECT " . $issql . " FROM " . DB_NAME . "." . DB_TABLE_PFX . $table_name ;
		
        return $this->find_array_assoc();
    }

	/**
	 * 查询
	 */
	 public function select($table_name, $filed=null) {
	 	$issql = "";
	 	if (!empty($filed)) {
	 		if (is_array($filed)) {
		 		foreach ($filed as $key=>$val) {
		 			$issql .= "," . $val;
		 		}
		 		$issql =	substr($isql, 1);
		 	} else {
		 		$issql = $filed;
		 	}
	 	} else {
	 		$issql = "*";
	 	}
	 	$this->sql = "SELECT " . $issql . " FROM " . DB_NAME . "." . DB_TABLE_PFX . $table_name;
		return $this->select_list_assoc();
	 }


	/**
	 * sql语句拼接
	 */
	 public function set_sql($sql = "") {
	 	if (empty($sql)) {
	 		$wsql = $this->whereinsql . $this->whereorsql . $this->wheresql;
	 		if (!empty($wsql)) {
	 			$wsql = " where 1=1 " . $wsql;
	 		}
	 		$this->sql = $this->sql . $wsql . $this->ordersql . $this->limitsql;

	 	} else {
	 		$this->sql = $sql;
	 	}
		//echo $this->sql;
		if (!empty($this->ds)) {
			echo $this->sql;
		}
	 }


	/**
	 * 设置IN条件
	 *
	 * 使用方法
	 * clu传入数组时，用键值对的方式做为处理,如果有多个，将默认用type连接,值里面不支持数组
	 * data不传人值时，将clu直接作为sql处理
	 * data传入字符串时，clu不为空时，将clu作为列，data作为值处理
	 * data传入数组时，clu不为空，将clu作为列，data作遍历值处理
	 * type 并列调用时作为参数符
	 *
	 */
	public function where_in($clu=null, $data=null, $type = "AND") {
		if (!empty($clu)) {
			if (!empty($data)) {
				if (is_array($data)) {//字段-数组值
					$this->whereinsql .= ' ' . $type . '`' . $clu .'` IN(' . implode(',',$data) . ') ';
				} else {//字段-值
					$this->whereinsql .= ' ' . $type . '`' . $clu .'` IN(' . $data . ') ';
				}
			} else {//直接传wheresql
				if(is_array($clu)) {
					$isql = "";
					foreach($clu as $key=>$val) {
						$isql .= ' ' . $type . '`' . $key .'` IN(' . $val . ') ';
					}
					$this->whereinsql .= ' ' . $type . '(' . $isql . ')';
				} else {
					$this->whereinsql .= ' ' . $type . '(' . $clu . ')';
				}
			}
		}
		return self::$db;
	}


	/**
	 * 设置OR条件
	 * 使用方法
	 *
	 * where传入字符串时，data作为值处理
	 * where传入数组时，data作遍历值处理
	 * type 并列调用时作为参数符
	 *
	 */
	public function where_or($where=null, $type = "AND") {
		if (!empty($where)) {
			$isql = "";
			if (is_array($where)) {
				foreach($where as $key=>$val) {
					$isql .= 'OR ' . "`" . $key ."` = '" . $val . "'";
				}
				$isql =	substr($isql, 2);
			} else {
				$isql = $where;
			}
			$this->whereorsql .= ' ' . $type . '(' . $isql . ')';
		}
		return self::$db;
	}


	/*
	 * 设置AND条件
	 * 使用方法
	 *
	 * where传入字符串时，data作为值处理
	 * where传入数组时，data作遍历值处理
	 * type 并列调用时作为参数符
	 *
	 */
	 public function where($where, $type = "AND") {
	 	
	 	$this->where='';
	 	if (!empty($where)) {
			$isql = "";
			if (is_array($where)) {
				foreach($where as $key=>$val) {
					if(empty($val) && $val != 0){
						continue;
					}
					if(is_numeric($val)){
						$isql .= 'AND ' . "`" . $key ."` = '" . $val."' ";
					}else{
						$isql .= 'AND ' . "`" . $key ."` = '" . $val . "'";
					}
				}
				$isql =	substr($isql, 3);
			} else {
				$isql = $where;
			}
			if($isql){
				$this->wheresql .= $type . '(' . $isql . ')';
			}
		}
		
		return self::$db;
	 }


	/*
	 * 分页显示数据
	 */
	public function limit($page, $pagesize=PAGE_SIZE){
		if ($page <= 0) {
			$page = 1;
		}
		$this->limitsql = ' LIMIT ' . ($page - 1) * $pagesize . ',' . $pagesize;

		return self::$db;
	}


	 /*
	 * 排序
	 */
	public function order($filed, $sort='desc'){
		if (!empty($filed)) {
			if (is_array($filed)) {
				$this->ordersql = ' ORDER BY ' . implode(',',$filed) . ' ' . $sort;
			} else {
				$this->ordersql = ' ORDER BY ' . $filed . ' ' . $sort;
			}
		}

		return self::$db;
	}


	/**
	 * 析构关闭连接
	 */
	/*function __destruct()
	{
		self::$db->colse_conn();
	}*/

	/**
	 * 创建数据库
	 * */
	public function create_database() {
		//mysql_query("create database " . $this->dbname,$this->con);
		$sql = "create database " . DB_NAME;
		$this->pdo->exec($sql);
	}


	/**
	 * 执行没有返回结果的语句
	 * */
	public function execute_sql() {
		$this->set_sql();
		
		if (false === $this->pdo->exec($this->sql)) {
			$this->initsql();
			return false;
		}
		$this->initsql();
		return true;
	}
	/*
	执行原生的sql 并返回数据
	 */
	public  function getData($sql){
		$this->sql=$sql;
		return $this->select_list_assoc();
	}

	/**
	 * 初始化sql
	 */
	 private function initsql() {
	 	$this->sql = "";
		$this->wheresql = "";//条件AND sql
		$this->tableName = "";//表名
		$this->whereinsql = "";//条件IN sql
		$this->whereorsql = "";//条件OR sql
		$this->limitsql = "";//分页sql
		$this->ordersql = "";//排序sql
	 }
	 /**
     * 执行语句返回关联数组
     * MYSQL_ASSOC - 关联数组
     * */
    public function find_array_assoc() {
        $this->set_sql();
        if (empty($this->sql)) {
            $this->initsql();
            return false;
        }
        //echo $this->sql;
        $this->result = $this->pdo->query($this->sql);
        if(!$this->result){
            $this->initsql();
            return false;
        }

        $this->result->setFetchMode(PDO::FETCH_ASSOC);//FETCH_NUM
        $arr = $this->result->fetch();
        if (!empty($arr)) {
            foreach ($arr as $key => $value) {
                if (NULL == $value || "null" == $value || "" == $value) {
                    $arr[$key] = "";
                }
            }
        }

        $this->initsql();
        return $arr;
    }

	/**
	 * 执行语句返回关联list,
	 * MYSQL_ASSOC - 关联数组
	 * */
	public function select_list_assoc() {
		$this->set_sql();
		if (empty($this->sql)) {
			$this->initsql();
			return false;
		}
		// echo $this->sql;
		$this->result = $this->pdo->query($this->sql);
		if(!$this->result){
			$this->initsql();
			return false;
		}

		$this->result->setFetchMode(PDO::FETCH_ASSOC);//FETCH_NUM
		$list = array();
		$list = $this->result->fetchAll();

		if (count($list) > 0) {
			for ($i = 0; $i < count($list); $i ++) {
				if (isset($list[$i])){
					if (!empty($list[$i])) {
						if(is_array($list[$i])) {
							foreach ($list[$i] as $key=>$value) {
								if (NULL == $value || "null" == $value || "" == $value) {
									$list[$i][$key] = "";
								}
							}
						}
					}
				}
			}
		}
		
		$this->initsql();
		return $list;
	}


	/**
	 * 取得刚刚插入的信息的id
	 */
	public function last_insert_id() {
		return $this->pdo->lastInsertId();
	}

	/**
	 * 关闭连接
	 * */
	public function colse_conn() {
		//mysql_close($this->con);
		self::$db = null;
	}

	//将下划线后面的数字大写
	function _ucwords($str) {
		$list = explode("_", $str);
		$str_ = "";
		foreach ($list as $l) {
			$str_.= ucwords($l);
		}
		return $str_;
	}

	//备份数据库
	function backup_db() {
		$file_name = date("Y-m-d_H-i-s") . ".sql";

		$query  = 'show tables';
		$tables_arr = $this->select_list_assoc($query);
		$file_content = '';
		//遍历所有表
		foreach($tables_arr as $value){
			$table = $value['Tables_in_' . DB_NAME];
			if(!empty($table) && DB_TABLE_PFX ."area" != $table){

				$query = 'show create table '.$table;

				$file_content  .= "DROP TABLE IF EXISTS `$table`;\r\n";
				//表结构
				$table_destruct = $this->select_list_assoc($query);

				$file_content  .= str_replace("\n", '', str_replace("\r\n", "\n", $table_destruct[0]['Create Table'])) . ";\r\n\r\n";
						//$msg = $table.'表结构备份完毕';

				//表数据
				$query = 'SELECT * FROM '.$table;
				$table_data = $this->select_list_assoc($query);
						foreach($table_data as $key => $value){
						$file_content .= "INSERT INTO `$table` VALUES('";
								$value_arr     = array_values($value);
							$file_content .= implode("','" , $value_arr);
							$file_content .= "');\r\n\r\n";
						}
						//$msg .= $table.'表数据备份完毕';
			}
		}

		//$file_content = iconv('GBK' , 'UTF-8' , $file_content);

		$file = ROOT_PATH . "\\data\\backup\\mysql\\" . DB_NAME . "\\" . date("Y-m-d_H-i-s") . ".sql";
	    $file_tool = new FileAddFolderTool();
	    if ("78" == $file_tool->createAndWirteFile($file, $file_content)) {
	    	return $file;
	    } else {
	    	return false;
	    }
	}
	/*
	 * 开启事务
	 * */
	public function begin(){
		return $this->pdo->beginTransaction();
	}
	//提交事务
	public function commit(){
		return $this->pdo->commit();
	}
	//回滚事务
	public function rollBack(){
		return $this->pdo->rollBack();
	}
}