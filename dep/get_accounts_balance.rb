require 'logger'

$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))

require 'config'
require 'lib/bitcoin_rpc'
require 'db/baseinfo_dao'
require 'db/token_dao'
require 'db/account_dao'
require 'chain/txs'

#*****************************************************  开始执行主程序  **************************************************

if $0 == __FILE__
  #输出到文件，统一填写绝对路径，文件名log.out
  # logger = Logger.new(($log_path + 'get_balance.log').to_s)
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

  $tokens = get_token_db
  if $tokens.size == 0
    # 如果数据库中没有任何一种代币, 退出程序
    $logger.warn('[WARNING] Token table is not data')
    exit(1)
  end

  #构建币RPC链接地址
  $rpcAddr = $rpc_addr % [$rpcHost, $rpcPort.to_s]
  coinrpc = BitcoinRPC.new($rpcAddr)

  #获取当前区块高度
  $latestBlockHeightx = coinrpc.eth_blockNumber()
  $latestBlockHeight = $latestBlockHeightx.hex
  logger.info("latestBlockHeight: " + $latestBlockHeight.to_s)

  #从数据库读取所有账号
  accounts = get_account_limit($usersMax)
  logger.debug('[DEBUG] get accounts success')

  # 处理各币种数量总额, 包括ETH
  $balance_each_token_sums = {}
  $tokens.each do |k, v|
    $balance_each_token_sums[v['SYMBOL']] = 0.0
  end

  #依次处理每一笔交易
  accounts.each do | row |
    $id = row[0]
    $address = row[1]
    #logger.info("id: " + $id)
    # TODO 处理各币种, 将ETH与TOKEN区分处理
    $tokens.each do |key, value|
      # 重定义$decimal
      $decimal = value['COIN']
      # 重定义$symbol
      $symbol = value['SYMBOL']

      if ($symbol != 'ETH' and $symbol != 'ETC') and key.length > 20   # Token类型
        $amount_token = 0
        # TODO 处理, 处理Token报文
        $senddata = $functionAbi + $address[2,40] #地址要去掉0x前缀
        $txhash = build_tx_send_body($address, key, value:'0x0', gas:$gas, gas_price:$gasPrice, data:$senddata)
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
      if $balance > 0.0
        $balance_each_token_sums[$symbol] += $balance
        logger.info("The address #{$address} has #{ $balance } #{ $symbol }")
      end

      # 当为ETH时, 存储至数据库中
      if ($symbol == 'ETH' or $symbol == 'ETC') and key.length < 20
        update_account_balance($id, $balance, $latestBlockHeight)
      end
    end
  end

  $balance_each_token_sums.each do |k, v|
    logger.info("#{ k } balanceSum: #{ v }")
  end
  logger.info("** END THE PROGRAM **")
end
