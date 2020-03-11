require 'mysql'
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '..'))
require 'config'
# 声明全局变量, 节省内存
$client = Mysql.init
$client.options(Mysql::SET_CHARSET_NAME, 'utf8')
$client = Mysql.real_connect($db_host, $db_username, $db_password, $db_db, $db_port) #newtodo


def get_baseinfo_tb
  # 获取baseinfo数据
  # 返回一个{}
  sql = 'select * from baseinfo where id = 1'
  query = $client.query(sql)
  result = {}
  while row = query.fetch_row do
    # 装载所有的数据库数据, 将数据放到tokens中, 键为CoinType,
    # 使用$token.keys.include?CoinType判断是否有此币种, 同时使用CoinType也可以获取详情
    result['lastScanBlock'] = row[1].to_i
    result['usersMax'] = row[2]
    result['lastLoadBlock'] = row[3].to_i
    result['isLoad'] = row[4]
    result['isScan'] = row[5]
    result['isPost'] = row[6]
    result['rpcHost'] = row[7]
    result['rpcPort'] = row[8]
    result['withdrawAddr'] = row[9]
    result['isCleanTxs'] = row[10]
    result['storageBlockCount'] = row[11]
  end
  return result
end


def update_one_baseinfo(height, field)
  # 更新baseinfo中的某个字段 返回true 或nil
  sql = 'UPDATE baseinfo SET ' + field + ' = ' + height.to_s + ' WHERE id = 1'
  begin
    update = $client.query(sql)
  rescue
    return nil
  end
  return true
end