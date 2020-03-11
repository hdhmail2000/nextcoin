# 判断用户中是否有足够的ETH支付手续费, 如果不足则补充
require 'logger'
require 'open-uri'
#require 'md5'
#require 'digest/md5'
require 'openssl'

require 'mysql'

$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))

require 'config'
require 'lib/bitcoin_rpc'
require 'chain/txs'
require 'db/token_dao'
require 'db/txs_dao'
require 'db/baseinfo_dao'
require 'db/account_dao'

#使用教程
# 1.配置log目录路径
# 2.配置数据库
# 3.配置baseinfo的最大用户数
# 4.运行

#*****************************************************  开始执行主程序  **************************************************

if $0 == __FILE__
  #输出到文件，统一填写绝对路径，文件名log.out
  #logger = Logger.new(($log_path + 'eth_send_inner.log').to_s)
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
  $log_file = $logPeriod + '_eth_send_inner.log'
  $log_path = Pathname.new(File.dirname(__FILE__)).realpath + 'log'
  logger =  Logger.new(($log_path +$log_file).to_s)

  logger.info("** START THE PROGRAM **")

  $tokens = get_token_db
  if $tokens.size == 0
    # 如果数据库中没有任何一种代币, 退出程序
    $logger.warn('[WARNING] Token table is not data')
    exit(1)
  end

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
  #logger.info("rpcAddr: " + $rpcAddr)
  coinrpc = BitcoinRPC.new($rpcAddr)

  #获取当前区块高度
  $latestBlockHeightx = coinrpc.eth_blockNumber()
  $latestBlockHeight = $latestBlockHeightx.hex
  logger.info("latestBlockHeight: " + $latestBlockHeight.to_s)

  #从数据库读取所有账号
  rslt = get_account_limit($usersMax)
  #logger.info("rslt: " + rslt.to_s)

  #依次处理每一笔交易
  rslt.each do | row |
    $id = row[0]
    $address = row[1]
    # 中间地址不处理, 提现地址不处理
    if [$innerAddr, $withdrawAcount].include?$address
      next
    end
    # 遍历每一个币种, 并发送查询申请, 如果没有余额将不处理
    $tokens.each do |key, value|
      # 重定义$decimal
      $decimal = value['COIN']
      # 重定义$symbol
      $symbol = value['SYMBOL']
      $senddata = $functionAbi + $address[2,40] #地址要去掉0x前缀

      if ($symbol == 'ETH' or $symbol == 'ETC') or value['CONTRACT_ACCOUNT'].length < 20
        next
      end

      #构造发送交易
      $txhash = {'from' => $address,
          'to' => key,  # 不同的币种对应不同的合约地址
          'gas' => $gas,
          'gasPrice' => $gasPrice,
          'value' => '0x0',   # ETH金额为0
          'data' => $senddata   # token金额为无
      }
      # logger.info("txhash: " + $txhash.to_s)

      $amount_token = 0
      begin
        # options may also be supplied as a second parameter with `earliest`, `latest`, `offset` and `max`, as defined for `eth.filter`.
        $amount_token = coinrpc.eth_call($txhash,'latest')

        # 确认是有余额的, 如果没有余额直接下一个
        if $amount_token.hex.to_f == 0
          next
        end
      rescue
        logger.info("eth_call Raise Error: #{$!}" + $amount_token.to_s)
        # exit(1)
      end

      $balance_eth = coinrpc.eth_getBalance($address, '0x' + $latestBlockHeight.to_s(16))
      #logger.info("balance_eth: " + $balance_eth)

      $balance_token = $amount_token.hex.to_f / $decimal
      $balance_eth = $balance_eth.hex.to_f / $wei

      if $balance_token > 0
        logger.info("address: " + $address)
        logger.info("balance_token: " + $balance_token.to_s + " " + $symbol)
        logger.info("balance_eth: " + $balance_eth.to_s + " ETH")
      end

      if(($balance_token > $threshold_token) && ($balance_eth < $threshold_eth))
          # 构造发送交易
          # $txhash = {'from' => $innerAddr,
          # 'to' => $address,
          # 'gas' => $gas,
          # 'gasPrice' => $gasPrice,
          # # 'value' => $sendHex
          # 'value' => "0x#{(($threshold_eth - $balance_eth) * $wei).to_i.to_s(16)}"
          # }
          tx_value = (($threshold_eth - $balance_eth) * $wei).to_i.to_s(16)
          $txhash = build_tx_send_body($innerAddr, $address, value:tx_value, gas:$gas, gas_price:$gasPrice)
          logger.info("txhash1: " + $txhash.to_s)
          begin
            $sendRes = coinrpc.personal_signAndSendTransaction($txhash,$innerAddrPass)
            logger.info("txid: " + $sendRes)
            # TODO 如果对一个用户转出ETH成功了, 将不再检测是否还有其他的代币, 将直接进行下一帐户检测
            break
          rescue
            logger.info("SEND AND SING Error: #{$!}")
            # exit(1)
          end
      end
    end
  end
  logger.info("** END THE PROGRAM **")
end
