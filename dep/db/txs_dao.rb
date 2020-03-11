require 'mysql'
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '..'))
require 'config'
# 声明全局变量, 节省内存
$client = Mysql.init
$client.options(Mysql::SET_CHARSET_NAME, 'utf8')
$client = Mysql.real_connect($db_host, $db_username, $db_password, $db_db, $db_port) #newtodo


# 将需要操作的数据库重构到文件中, 方便复用
# 下一步应该做什么: 1. 增加装饰器, 添加debug sql功能, 2. 增加debug result功能
# 未来规划: 使用ORM代码直接使用sql方法

def get_need_clean_tx(clean_height)
  # 获取需要清除tx的数量, 如果没有获取到数据或异常, 返回0
  sql = "SELECT COUNT(*) FROM txs where isDeposit = 0 and blockNumber < " + clean_height.to_s
  query = $client.query(sql)
  row = query.fetch_row
  result = row[0] ? row[0].to_i : 0
  return result
end


def del_clean_tx(clean_height)
  # 删除指定高度的需要清除的数据
  sql = "delete from txs where isDeposit = 0 and blockNumber < " + clean_height.to_s
  begin
    query = $client.query(sql)
  rescue
    return nil
  end
  return true
end


def save_tx(txHash, blockHash, blockNumber, addrFrom, addrTo, value, ether, gas, gasPrice, nonce, assetType)
  # 存储一笔tx 返回true 或nil
  sql = "INSERT IGNORE INTO txs(txHash,isFilled,blockHash,blockNumber,addrFrom,addrTo,value,ether,gas,gasPrice,nonce,assetType)VALUES('" + txHash +"'," + "1,'" + blockHash + "'," + blockNumber.to_s + ",'" + addrFrom + "','" + addrTo + "','" + value + "'," + ether.to_s + "," + gas.to_s + "," + gasPrice.to_s + "," + nonce.to_s + "," + assetType + ")"
  begin
    query = $client.query(sql)
  rescue
    return nil
  end
  return true

end


def update_one_txs(id, content, field)
  sql = 'UPDATE txs SET isDeposit = 1, ' + field + ' = ' + content.to_s + ' WHERE id = ' + id
  begin
    update = $client.query(sql)
  rescue
    return nil
  end
  return true
end


def update_many_txs(id, data)
  # data是字典格式, key需要对应数据库的字段, 值需要对应数据库的数据
  sql = 'UPDATE txs SET '
  sql_tmp = ''
  data.each do |k, v|
    sql_tmp << "#{k} = #{v},"
  end
  if sql_tmp[-1] == ','
    sql_tmp = sql_tmp[0..-2]
  end

  sql << sql_tmp << ' WHERE id=' << id

  begin
    update = $client.query(sql)
  rescue
    return nil
  end
  return true
end


def get_no_post_txs
  sql = 'select id, txHash, addrTo, value, assetType, ether from txs where isDeposit = 1 AND isPosted = 0'
  query = $client.query(sql)
  result = []
  while row = query.fetch_row do
    result << row
  end
  return result
end


def get_txs_include_account_db(last_scan_block, user_max, inner_addr)
  sql = "SELECT txs.id,txs.addrFrom,txs.addrTo,txs.blockNumber FROM txs,accounts WHERE txs.blockNumber > " + last_scan_block.to_s  + " AND txs.addrTo = accounts.address AND accounts.id < " + user_max.to_s + " AND txs.addrFrom != '" + inner_addr.to_s + "'"
  query = $client.query(sql)
  result = []
  while row = query.fetch_row do
    result << row
  end
  return result
end


def get_no_post_one_txs(tx_hash, force=false)
  # 重推某一条交易
  sql = "select t.id, t.txHash, t.addrTo, t.value, t.assetType, t.ether from txs t, accounts a where t.txHash='#{tx_hash}' "
  if !force
    sql << ' and t.isDeposit = 1 AND t.isPosted = 0'
  else
    sql << ' and t.addrTo = a.address'
  end
  puts sql
  query = $client.query(sql)
  result = []
  while row = query.fetch_row do
    result << row
  end
  return result
end


if __FILE__ == $0
  get_one = get_no_post_one_txs('0x49d265850d1c5354cf3364762f87a238da6c03bc0de4f19bf7bf48a7a7bc3e9e')
  puts "get one is #{get_one}"
end
