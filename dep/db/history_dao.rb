require 'mysql'
# 使用相对绝对路径引用包
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '..'))
require 'config'
# 声明全局变量, 节省内存
$client = Mysql.init
$client.options(Mysql::SET_CHARSET_NAME, 'utf8')
$client = Mysql.real_connect($db_host, $db_username, $db_password, $db_db, $db_port) #newtodo


def save_history(tx_hash, block_number, url, request, response, type)
  # 保存一个历史信息, 有据可查
  # type 1.推送平台 2.转ETH 3.转Token.. 更多的请看eth_dict表
  sql = "insert into eth_history (txHash, height, url, request, response, type) values ('#{tx_hash.to_s}', '#{block_number.to_s}', '#{url.to_s}', '#{request.to_s}', '#{response.to_s}', '#{type.to_s}')"
  begin
    query = $client.query(sql)
  rescue
    return nil
  end
  return true
end



if __FILE__ == $0
  save_history('txhash', '11111', 'www.baidu.com', {'a'=>'a', 'b'=>'b'}, {'c'=>'c', 'd'=>'c'}, '1')
end
