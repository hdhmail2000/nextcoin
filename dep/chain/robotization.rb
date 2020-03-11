$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '..'))

require 'db/baseinfo_dao'
require 'chain/txs'
require 'config'


def sync_one_tx(height, tx_hash, force=false)
  tx_hash = add_head_0x(tx_hash)
  response = {'code'=> 0, 'msg'=>'成功'}
  baseinfos = get_baseinfo_tb
  # last_scan_block = baseinfos['lastScanBlock']
  user_max = baseinfos['usersMax']
  last_load_block = baseinfos['lastLoadBlock']
  is_load = baseinfos['isLoad']
  is_scan = baseinfos['isScan']
  is_post = baseinfos['isPost']
  rpc_host = baseinfos['rpcHost']
  rpc_port = baseinfos['rpcPort']
  rpc_addr = $rpc_addr % [rpc_host, rpc_port.to_s]

  handle_tx = HandleBlock.new(height, rpc_addr)
  handle_misc = HandleMisc.new(rpc_addr, $url)

  handle_tx.get_block_body
  sync_tx = handle_tx.sync_one_tx(tx_hash)

  if force
    is_post = 1
  end
  if sync_tx['code'] == 0
    # 成功则推送
    if is_post.to_i == 1
      handle_misc.sync_height
      handle_misc.deposit(height, user_max, $innerAddr)
      response['code'], response['msg'] = handle_misc.post_to_web(tx_hash, force)
      if response['code'] != 10000
        response['msg'] = "推送Code不为10000, result_msg: #{ response['msg'] }"
      end
    else
      response['code'], response['msg'] = 1, '数据库未开启推送'
    end
  else
    # 没有同步成功 处理方法
    response['code'], response['msg'] = sync_tx['code'], sync_tx['msg']
  end
  return response
end


if __FILE__ == $0
  # 脚本手动推送
  info = sync_one_tx(1000000, '0xea1093d492a1dcb1bef708f771a99a96ff05dcab81ca76c31940300177fcf49f', true)
  puts info
end
