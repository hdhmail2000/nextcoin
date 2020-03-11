#
# build in
require 'logger'

$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))

require 'config'
require 'lib/bitcoin_rpc'
require 'db/token_dao'
require 'db/account_dao'
require 'db/baseinfo_dao'

#使用教程
# 0. 前提：配置好baseinfo表格
# 1. 配置数据库
# 2. 运行程序

if $0 == __FILE__

  #输出到文件，统一填写绝对路径，文件名log.out
  # $logger = Logger.new(($log_path + 'load_accounts.log').to_s)
  logger = Logger.new(STDOUT)  #输出到文件，文件名log.txt

  logger.info("** START THE PROGRAM **")

  # 加载baseinfo数据, 如果数据库没有配置则不退出
  baseinfos = get_baseinfo_tb
  if baseinfos.length == 0
    logger.warn('[ERR] The baseinfo is not data')
    exit(1)
  end
  $rpcHost = baseinfos['rpcHost']
  $rpcPort = baseinfos['rpcPort']
  $withdrawAddr = baseinfos['withdrawAddr']
  logger.info("withdrawAddr: " + $withdrawAddr)

  #RPC地址
  $rpcAddr = $rpc_addr % [$rpcHost, $rpcPort.to_s]
  #logger.info("rpcAddr: " + $rpcAddr)
  coinrpc = BitcoinRPC.new($rpcAddr)


  #调用币的rpc接口，获取所有账号
  accounts = coinrpc.eth_accounts()
  #logger.info(accounts)

  accountsCount = accounts.length
  #logger.info(accountsCount)

  #记录原有的账号数量
  $accountNum1 = get_account_amt
  #logger.info($accountNum1)

  #循环检测，输入账号，已经存在的账号不录入
  $i=0
  $accountNum = accountsCount.to_i
  while $i < $accountNum do
    $accounti = accounts[$i].to_s
    logger.info("load_addr: " + $accounti)

    #自己的提现地址不加载
    if $accounti.downcase == $withdrawAddr.downcase
      $i += 1
      next
    end

    # 存储新的帐户
    save_account_to_db($accounti)

    #用这个方法id会跳跃
    #$sql_sel = ""
    #$sql_sel << "INSERT IGNORE INTO accounts(address) VALUES('" + $accounti + "')"
    #logger.info($sql_sel)
    #rslt = db.query($sql_sel)

    $i += 1
  end

  #统计新的账号数
  $accountNum2 = get_account_amt
  #logger.info($accountNum2)

  #新增的账号数
  $newAddAccountNum = $accountNum2 - $accountNum1

  logger.info("Add: "   + $newAddAccountNum.to_s + " Account(s)")
  logger.info("Total: " + $accountNum2.to_s + " Account(s)")
  logger.info("** END THE PROGRAM **")
end
