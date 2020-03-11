require 'logger'
require 'open-uri'
require 'openssl'

require 'mysql'

# 使用相对绝对路径引用包
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))

require 'config'
require 'lib/bitcoin_rpc'
require 'db/token_dao'
require 'db/baseinfo_dao'
require 'db/account_dao'
require 'chain/txs'


#使用教程
# 1.配置log目录路径
# 2.配置数据库
# 3.配置baseinfo的最大用户数
# 4.运行

#*****************************************************  开始执行主程序  **************************************************

if $0 == __FILE__
  #输出到文件，只要指定文件名, 路径由系统自动判定
  #logger = Logger.new(($log_path + 'token_send.log').to_s)
  #logger = Logger.new(STDOUT)  #输出到文件，文件名log.txt
  #file = File.open('foo.log', File::WRONLY | File::APPEND)
  # To create new (and to remove old) logfile, add File::CREAT like;
  # file = open('foo.log', File::WRONLY | File::APPEND | File::CREAT)
  #logger = Logger.new(file)
  time = Time.new
  period = 0
  if time.day <= 7
        period = 1
  else
        period = time.day / 7
  end
  $logPeriod = time.strftime("%Y-%m") +"-"+period.to_s+"week"
  $log_file = $logPeriod + '_token_send.log'
  $log_path = Pathname.new(File.dirname(__FILE__)).realpath + 'log'
  logger =  Logger.new(($log_path +$log_file).to_s)  


  logger.info("** START THE PROGRAM **")

  #获取数据库基本参数信息
  baseinfos = get_baseinfo_tb
  $lastScanBlock = baseinfos['lastScanBlock']
  $usersMax = baseinfos['usersMax']
  $lastLoadBlock = baseinfos['lastLoadBlock']
  $isLoad = baseinfos['isLoad']
  $isScan = baseinfos['isScan']
  $isPost = baseinfos['isPost']
  $rpcHost = baseinfos['rpcHost']
  $rpcPort = baseinfos['rpcPort']

  #构建币RPC链接地址
  $rpcAddr = $rpc_addr % [$rpcHost, $rpcPort.to_s]
  coinrpc = BitcoinRPC.new($rpcAddr)

  #获取当前区块高度
  $latestBlockHeightx = coinrpc.eth_blockNumber()
  $latestBlockHeight = $latestBlockHeightx.hex
  logger.info("latestBlockHeight: " + $latestBlockHeight.to_s)

  $tokens = get_token_db
  if $tokens.size == 0
    # 如果数据库中没有任何一种代币, 退出程序
    $logger.warn('[WARNING] Token table is not data')
    exit(1)
  end
  # 获取帐户
  accounts = get_account_limit($usersMax)
  logger.debug('[DEBUG] get accounts success')

  # 转帐ETH 如果为true, 表示可以转出, false表示不可以
  $is_send_eth = false

  #依次处理每一笔交易
  accounts.each do | row |
    $id = row[0]
    $address = row[1]
    # 中间地址不处理
    if $address == $innerAddr
      next
    end
    $is_send_eth = true # 打开该帐户发送ETH开关
    $tokens.each do |key, value|
      # 重定义$decimal
      $decimal = value['COIN']
      # 重定义$symbol
      $symbol = value['SYMBOL']

      $senddata = $functionAbi + $address[2,40] #地址要去掉0x前缀
      #构造发送交易
      $amount_token = '0x0'
      if ($symbol != 'ETH' and $symbol != 'ETC') and key.length > 20
        $txhash = build_tx_send_body($address, key, value:'0x0', gas:$gas, gas_price:$gasPrice, data:$senddata)
        begin
          $amount_token = coinrpc.eth_call($txhash,'latest')
            #logger.info("amount_token: " + $amount_token)
        rescue
          logger.info("eth_call Raise Error: #{$!}")
          # exit(1)
        end
      end

      # logger.info("txhash: " + $txhash.to_s)

      $balance_eth = coinrpc.eth_getBalance($address, '0x' + $latestBlockHeight.to_s(16))
      # logger.info("balance_eth: " + $balance_eth)

      $balance_token = $amount_token.hex.to_f / $decimal
      $balance_eth = $balance_eth.hex.to_f / $wei

      if $balance_token > 0
        logger.info("address: " + $address)
        logger.info("balance_token: " + $balance_token.to_s + " " + $symbol)
        logger.info("balance_eth: " + $balance_eth.to_s + " ETH")
      end

      $senddata = build_tx_token_data($METHODID_TOKEN_TRANSFER, $withdrawAcount, $amount_token)
      # 只要用户地址不是提现地址, 就开始处理
      if $withdrawAcount != $address
        if $balance_token > $threshold_token and $balance_eth > 0
          # 处理各类token
          $txhash = build_tx_send_body($address, key, value:'0x0', gas:$gas, gas_price:$gasPrice, data:$senddata)
          logger.info("txhash1: " + $txhash.to_s)

          begin
            $sendRes = coinrpc.personal_signAndSendTransaction($txhash,$addressPass)
            logger.info("txid: " + $sendRes)
          rescue
            logger.info("TOKEN signAndSend error: #{$!}")
            #exit(1)
          end
        end

        if $is_send_eth and ($balance_eth > $threshold_eth)
          # 如果用户帐户中存在ETH, 并且额度大于0.002的时候, 转出ETH, 并且在后面的TOKEN检测中不再转ETH
          send_ether = add_head_0x((($balance_eth - $threshold_eth) * $wei).to_i.to_s(16))
          send_eth_hash = build_tx_send_body($address, $withdrawAcount, value:send_ether, gas:$gas, gas_price:$gasPrice)
          logger.info("address: #{$address} has #{$balance_eth} ETHER send hash: " + send_eth_hash.to_s)
          begin
            $send_eth_res = coinrpc.personal_signAndSendTransaction(send_eth_hash,$addressPass)
            $is_send_eth = false  # 关闭该帐户发送ETH开关
            logger.info("txid: #{$send_eth_res}")
          rescue
            logger.info("ETH signAndSend error: #{$!}")
          end
        end
      end
    end

  end

  logger.info("** END THE PROGRAM **")
end
