require 'mysql'
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '..'))
require 'config'
# 声明全局变量, 节省内存
$client = Mysql.init
$client.options(Mysql::SET_CHARSET_NAME, 'utf8')
$client = Mysql.real_connect($db_host, $db_username, $db_password, $db_db, $db_port) #newtodo


def get_token_db
  # 获取数据库中的token, 返回{{}, {}, ...}
  sql_load_tokens = 'select * from eth_token where isValid=1'
  load_tokens = $client.query(sql_load_tokens)
  tokens = {}
  while row = load_tokens.fetch_row do
    # 装载所有的数据库数据, 将数据放到tokens中, 键为CoinType,
    # 使用$token.keys.include?CoinType判断是否有此币种, 同时使用CoinType也可以获取详情
    tokens[row[6]] = {
        'SYMBOL'=>row[2],
        'COINTYPE'=>row[4].to_i,
        'ISPOSTED'=>row[7].to_i,
        'COIN'=>row[5].to_i,
        'CONTRACT_ACCOUNT'=>row[6].downcase,
    }
  end
  return tokens
end







