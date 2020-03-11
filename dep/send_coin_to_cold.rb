# 发送金额至冷钱包, 脚本只查看提现地址是否大于要要发送到冷钱包的额度
# 将提现地址的币发送到冷钱包
# 注意事项
# 1. 本脚本有些变量独立于配置文件, 需要单独设置
# 2. 需要添加
require 'logger'

$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))

require 'config'
require 'lib/bitcoin_rpc'
require 'db/baseinfo_dao'
require 'db/token_dao'
require 'db/account_dao'
require 'chain/txs'

if $0 == __FILE__
  #输出到文件，统一填写绝对路径，文件名log.out
  # logger = Logger.new(($log_path + 'send_to_colder.log').to_s)
  logger = Logger.new(STDOUT)  #输出到文件，文件名log.txt
  #file = File.open('foo.log', File::WRONLY | File::APPEND)
  # To create new (and to remove old) logfile, add File::CREAT like;
  # file = open('foo.log', File::WRONLY | File::APPEND | File::CREAT)
  #logger = Logger.new(file)


  logger.info("** START THE PROGRAM **")
  baseinfos = get_baseinfo_tb
  if baseinfos.length == 0
    logger.warn('[ERR] The baseinfo is not data')
    exit(1)
  end
  $usersMax = baseinfos['usersMax']
  $rpcHost = baseinfos['rpcHost']
  $rpcPort = baseinfos['rpcPort']

  #构建币RPC链接地址
  $rpcAddr = $rpc_addr % [$rpcHost, $rpcPort.to_s]
  coinrpc = BitcoinRPC.new($rpcAddr)

  $tokens = get_token_db
  if $tokens.size == 0
    # 如果数据库中没有任何一种代币, 退出程序
    $logger.warn('[WARNING] Token table is not data')
    exit(1)
  end

  # 每个币种要保留在提现的数量, 剩余的全提到冷钱包 TODO 需要单独处理的变量
  $each_min_balance = {
      'ETH' => 0.002,
      'ETC' => 0.002,
      'DOC' => 2000,
  }

  # 币种换算成整数
  $tokens.each do |k, v|
    $each_min_balance[v['SYMBOL']] *= v['COIN']
  end

  # ------------------------ 需要特殊设置的变量 ------------------------
  # 交易手续费, 确保快速到达, 有可能要高于config, 如果相同可以不要
  $gas = "0x15f90" # gas
  $gasPrice = "0x4e3b29200" #gas价格

  #################  冷地址和密码 #######################################
  # $accountsMax = 3000 #最大用户数量，避免检索所有待使用地址
  $withdrawAddr = "XXX" #交易平台提现地址，目的地
  $coldAddress = "XXX" #官方库神地址
  $maxSendEther  = 3000 #本次最大累积发送量
  $innerAddrPass = 'XXX' # 过渡地址密码
  $addressPass = "XXX" # 用户地址密码

  $DestinationAddress = $withdrawAddr #目标地址，可以是提现地址或者冷钱包地址
  # ------------------------ 需要特殊设置的变量 ------------------------

  #####################################################################
  #获取当前区块高度
  $latestBlockHeightx = coinrpc.eth_blockNumber()
  $latestBlockHeight = $latestBlockHeightx.hex
  logger.info("latestBlockHeight: " + $latestBlockHeight.to_s)

  if $DestinationAddress == $withdrawAddr
    # 需要处理所有和token和所有的用户地址
    # 获取帐户
    accounts = get_account_limit($usersMax)
    logger.debug('get accounts success')
    logger.info('please use token_send_user_to_withdraw.rb or contact The Administrator')
    exit(1)
  elsif $DestinationAddress == $coldAddress
    # 只处理提现地址
    $tokens.each do |key, value|
      # 重定义$decimal
      $decimal = value['COIN']
      # 重定义$symbol
      $symbol = value['SYMBOL']

      if ($symbol != 'ETH' and $symbol != 'ETC') and key.length > 20   # Token类型
        $amount_token = 0
        # TODO 处理, 处理Token报文
        $senddata = $functionAbi + $withdrawAddr[2,40] #地址要去掉0x前缀
        $txhash = build_tx_send_body($withdrawAddr, key, value:'0x0', gas:$gas, gas_price:$gasPrice, data:$senddata)
        begin
          $balance_hex = coinrpc.eth_call($txhash,'latest')
        rescue
          logger.warn("The #{ $address } request eth_call error: #{ $! }")
        end
      else
        begin
          $balance_hex = coinrpc.eth_getBalance($address, '0x' + $latestBlockHeight.to_s(16))
        rescue
          logger.warn("The #{ $address } request eth_getBalance error: #{ $! }")
        end
      end
      $balance = $balance_hex.hex.to_f / $decimal

      if $balance > $each_min_balance[$symbol]
        $need_transfer = ($balance - $each_min_balance[$symbol]).to_s(16) # 拿到要转出的额度
        if ($symbol != 'ETH'and $symbol != 'ETC') and key.length > 20
          # 处理各类token
          $senddata = build_tx_token_data($METHODID_TOKEN_TRANSFER, $DestinationAddress, $need_transfer)
          $txhash = build_tx_send_body($withdrawAcount, key, value:'0x0', gas:$gas, gas_price:$gasPrice, data:$senddata)
          logger.info("txhash1: " + $txhash.to_s)

          begin
            $sendRes = coinrpc.personal_signAndSendTransaction($txhash,$addressPass)
            logger.info("txid: " + $sendRes)
          rescue
            logger.info("TOKEN signAndSend error: #{$!}")
          end
        else
          # 如果用户帐户中存在ETH, 并且额度大于0.002的时候, 转出ETH, 并且在后面的TOKEN检测中不再转ETH
          send_eth_hash = build_tx_send_body($withdrawAcount, $DestinationAddress, value:$need_transfer, gas:$gas, gas_price:$gasPrice)
          logger.info("address: #{$address} has #{$balance_eth} ETHER send hash: " + send_eth_hash.to_s)
          begin
            $send_eth_res = coinrpc.personal_signAndSendTransaction(send_eth_hash,$addressPass)
            logger.info("txid: #{$send_eth_res}")
          rescue
            logger.info("ETH signAndSend error: #{$!}")
          end
        end
      end
    end
  else
    logger.warn('You type address not in $withdrawAddr or $coldAddress, please check')
    exit(1)
  end
  logger.info("** END THE PROGRAM **")
end
