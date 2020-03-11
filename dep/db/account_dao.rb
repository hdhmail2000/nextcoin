require 'mysql'
# 使用相对绝对路径引用包
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '..'))
require 'config'
# 声明全局变量, 节省内存
$client = Mysql.init
$client.options(Mysql::SET_CHARSET_NAME, 'utf8')
$client = Mysql.real_connect($db_host, $db_username, $db_password, $db_db, $db_port) #newtodo


def get_account_amt
  # 统计有accounts表中有多少表
  sql = 'select count(*) from accounts'
  query = $client.query(sql)
  row = query.fetch_row
  result = row[0] ? row[0].to_i : 0
  return result
end


def save_account_to_db(accounti)
  # 下面这个语句可以 参考http://www.cnblogs.com/devcjq/articles/5149830.html
  sql = "INSERT INTO accounts(address) SELECT '"+ accounti +"' FROM DUAL WHERE NOT EXISTS(SELECT address FROM accounts WHERE address = '"+ accounti +"')"
  begin
    query = $client.query(sql)
  rescue
    return nil
  end
  return true
end


def get_account_limit(user_max)
  sql = "select * from accounts WHERE id < " + user_max.to_s
  query = $client.query(sql)
  result = []
  while row = query.fetch_row do
    result << row
  end
  return result
end


def update_account_balance(id, ether, height)
  # 更新帐户ETH额额
  sql = "UPDATE accounts SET blockheight = " + height.to_s + ",balance = '" + ether.to_s + "' WHERE id = " + id.to_s
  begin
    query = $client.query(sql)
  rescue
    return nil
  end
  return true
end