$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '..'))

require 'json'

require 'db/txs_dao'
require 'db/token_dao'
require 'db/baseinfo_dao'

require 'lib/bitcoin_rpc'
require 'lib/tools'
require 'config'

def build_tx_token_data(method, addr, value)
  # 生成
  length_number = 136
  method_number = 32  # method共34位, 前导0x去掉为32位
  send_data = '0x'
  # 确认method不包含前导0x
  method = method[0,2] == '0x' ? method[2, method.length-2] : method
  method << ('0' * (method_number - method.length))
  # 确认addr位数, 并且确认前导不为0x
  addr = addr[0,2] == '0x' ? addr[2, addr.length-2] : addr
  addr = addr.length == 40 ? addr : '0' * (40 - addr.length) + addr
  # 处理金额
  value = value[0,2] == '0x' ? value[2, value.length-2] : value
  value = '0' * (length_number - method.length - addr.length - value.length) + value
  send_data << method << addr << value
  return send_data
end


def build_tx_send_body(from, to, value:'0x0', data:'0x', gas:'0xea60', gas_price:'0x4e3b29200')
  # 构建一笔交易的body
  # 此行注意, = 左边数量要与入参数量相同, 不然可能会获取到不正确的数据 todo 接收参数要与入参位置对应
  # 除from与to以外, 其他均是关键字参数, 必须要使用arg:arg形式调用
  from, to, gas, gas_price, value, data = add_more_head_0x(from, to, gas, gas_price, value, data)
  result = {}
  result['from'] = from
  result['to'] = to
  result['gas'] = gas
  result['gasPrice'] = gas_price
  result['value'] = value
  if !data.hex.equal?0 or (!data.empty? and (data.length > 2))
    result['data'] = data
  end
  return result
end

def build_post_url(txHash, to, sendether, symbol, sha3)
  # 不包含地址, 只处理?后面的
  return "txhash=" << txHash <<  "&to=" << to << "&value=" << sendether << "&symbol=" + symbol  << "&sign=" << sha3
end

class HandleBlock
  # 处理块和交易数据
  #
  attr :height, true
  attr :height_hex, true
  attr :blockHash, true
  attr :gas, true
  attr :gasPrice, true
  attr :txHash, true
  attr :from, true
  attr :to, true
  attr :input, true
  attr :nonce, true
  attr :transactionIndex, true
  attr :value, true
  attr :ether, true # token交易的以太数量
  attr :tx_type, true

  @result = {}
  @txs = []
  @asset_type = '0'
  @@functionAbi = '0x70a08231000000000000000000000000'
  # TODO 这个变量需要从配置文件中获取, 如果是测试, 可以临时替换(不建议)
  @@method_token_transfer = $METHODID_TOKEN_TRANSFER
  def initialize(height, url)
    set_height(height)
    @url = is_correct_addr(url) ? url : nil
    if !@url
      raise('The URL is invalid')
    end
    @client = BitcoinRPC.new(@url)
    @tokens = get_token_db
  end

  def set_height(height)
    # 设置要处理的高度
    if height.class != Fixnum
      raise('The HEIGHT must be number of int')
    end
    self.height = height
    self.height_hex = '0x' << height.to_s(16)
  end

  def get_block_body
    # 获取块内容
    blockInfo = @client.eth_getBlockByNumber(self.height_hex, true)
    @result = blockInfo
    if @result.keys.include?'hash'
      self.blockHash = @result['hash']
    else
      self.blockHash = nil
    end
    if @result.include?'transactions'
      @txs = @result['transactions']
    else
      @txs = []   # 确保每一次处理的时候txs都不会出现不确定的数据
    end
  end

  def sync_txs
    # 同步一个块中的数据
    @txs.each do | row |
      self.txHash = row['txHash']
      self.from = row['from']
      self.to = row['to']
      self.gas = row['gas']
      self.gasPrice = row['gasPrice']
      self.nonce = row['nonce']
      self.transactionIndex = row['transactionIndex']
      self.input = row['input']
      self.value = row['value']

      if self.to == nil
        next
      end

      if self.input.length > 50
        self.tx_type = 'TOKEN'
        result = sync_token_tx
      else
        self.tx_type = 'ETH'
        result = sync_eth_tx
      end

      if result['is_success']
        # 处理成功 保存数据
        save_tx(self.txHash, self.blockHash, self.height, self.from, self.to,
                self.value, self.ether, self.gas, self.gasPrice, self.nonce,
                @asset_type)
        # TODO 这里应该增加Log日志来处理
        puts result['msg']
      else
        # 记录错误
        puts result['msg']
      end
    end
  end

  def sync_one_tx(tx_hash)
    # 同步一个块中的数据
    response = {'code'=> 0, 'msg'=>'成功'}
    is_sync = false
    @txs.each do | row |
      self.txHash = row['hash']
      self.from = row['from']
      self.to = row['to']
      self.gas = row['gas']
      self.gasPrice = row['gasPrice']
      self.nonce = row['nonce']
      self.transactionIndex = row['transactionIndex']
      self.input = row['input']
      self.value = row['value']


      if self.txHash != tx_hash
        # 确保同步的是你要的tx hash
        next
      end

      if is_sync
        return response
      end

      if self.to == nil
        next
      end

      if self.input.length > 50
        self.tx_type = 'TOKEN'
        result = sync_token_tx
      else
        self.tx_type = 'ETH'
        result = sync_eth_tx
      end
      response['msg'] = result['msg']

      if result['is_success']
        # 处理成功 保存数据
        save_tx(self.txHash, self.blockHash, self.height, self.from,
                self.to, self.value, self.ether, self.gas, self.gasPrice,
                self.nonce, @asset_type)
        is_sync = true
        # TODO 这里应该增加Log日志来处理
        puts result['msg']
      else
        # 记录错误
        response['code'] = 1
        puts result['msg']
      end
    end
    return response
  end

  def sync_block
    # 同步单个块
    get_block_body
    sync_txs
  end

  def sync_blocks(s, e)
    # 同步多个块
    # s to e include e
    (s..e).each do | sync_height |
      set_height(sync_height)
      sync_block
    end
  end

  def sync_token_tx
    # 同步token交易
    response = {
        'is_success'=> false,
        'msg'=> 'failed',
    }
    if @tokens.keys.include?self.to
      method_id    = self.input[0,10]
      inner_addr_to = self.input[34,40]
      inner_value  = self.input[-24,24]
      if (@@method_token_transfer == method_id) && (self.to != nil)

        #通过对比区块前后余额以判断是否真的充值成功
        senddata = @@functionAbi + inner_addr_to #地址要去掉0x前缀

        #构造发送交易
        tx_data = build_tx_send_body(inner_addr_to, self.to, data:senddata)

        puts "tx_data is #{tx_data}"

        amount_pre = 0
        amount_cur = 0

        begin
          amount_pre = @client.eth_call(tx_data, add_head_0x((self.height-1).to_s(16)))
          amount_cur = @client.eth_call(tx_data, add_head_0x(self.height.to_s(16)))
          puts "amount_pre is #{amount_pre} amount_cur is #{amount_cur}"
        rescue
          response['msg'] = "[ERR] eth_call error at tx_hash: #{self.txHash} block_height is #{self.height} tx_data is #{tx_data} error: #{ $! }"
          return response
        end
        token_wei = @tokens[self.to]['COIN']

        balance_pre  = amount_pre.hex.to_f / token_wei
        balance_cur  = amount_cur.hex.to_f / token_wei
        # ether_receive = inner_value.hex.to_f / token_wei

        if (balance_cur - balance_pre) > 0 #测试发现计算时候会有精度误差，因为如果使用绝对等于来判断，会有误判
          @asset_type = @tokens[self.to]['COINTYPE'].to_s # 这个必须要在self.to被重赋值之前, 不然会异常
          self.to = "0x" + inner_addr_to
          value_hex = inner_value.hex.to_i
          self.value = '0x' + value_hex.to_s(16)
          self.ether = self.value.hex.to_f / $wei
          response['is_success'] = true
          response['msg'] = "#{self.txHash} success"
        else
          @asset_type = '-1'
          response['msg'] = "[WARN] The Tx is not success tx_hash: #{self.txHash} block_height is #{self.height}"
        end
      else
        response['msg'] = "[WARN] The tx is not token tx_hash: #{self.txHash} block_height is #{self.height}"
      end
    else
      response['msg'] = "[WAN] The user tx token not in ETH_TOKEN table: #{self.txHash} block_height is #{self.height}"
    end
    return response
  end

  def sync_eth_tx
    # 同步以太交易
    # todo 暂时如果是以太的交易, 只处理一个用户金额和@asset_type
    response = {
        'is_success'=> false,
        'msg'=> 'failed',
    }
    @asset_type = '0'
    self.ether = (self.value.hex.to_f / $wei)
    response['is_success'], response['msg'] = true, '成功'
    return response
  end


  def iter_blocks(s, e)
    # 使用yield方式处理块数据, 对外界友好
    # s, e 开始和结束高度
    (s..e).each do | height |
      set_height(height)
      yield get_block_body
      yield iter_txs
    end

  end

  def iter_txs
    # 使用yield方式处理交易数据, 对外界友好
  end
end


class HandleMisc
  # 处理杂项
  attr :height, true
  attr :always_sync, true
  attr :sync_time, true # 同步时间
  attr :sync_always_th, true

  def initialize(url, http_url)
    # @inner_addr = $innerAddr
    # @base_info = get_baseinfo_tb
    @url = is_correct_addr(url) ? url : nil
    if !@url
      raise('The URL is invalid')
    end
    @http_url = is_correct_url(http_url) ? http_url : nil
    if !@http_url
      raise('The POST URL is invalid')
    end
    # *****  必须的对象 *****
    @client = BitcoinRPC.new(@url)
    @mutex=Mutex.new
    @tokens = get_token_db
    @token_type = {}
    @tokens.values.each do | _each |
      @token_type[_each['COINTYPE']] = _each['SYMBOL']
    end

    # **** 初始化类变量 ****
    self.always_sync = true
    self.sync_time = 10
  end

  def deposit(last_scan_block, user_max, inner_addr)
    my_txs = get_txs_include_account_db(last_scan_block, user_max, inner_addr)
    #循环标记为充值交易
    my_txs.each do | tx |
      id = tx[0]
      address = tx[1]
      block_number = tx[3]
      #计算交易确认数
      confirmation = self.height - block_number.to_i + 1
      #更新数据库标记为充值交易，更新确认数
      update_one_txs(id, confirmation, 'confirmation')
    end
  end

  def post_to_web(tx_hash=nil, force=false)
    if tx_hash == nil
      txs = get_no_post_txs
    else
      txs = get_no_post_one_txs(tx_hash, force)
    end
    puts "txs is #{txs} it is #{txs.empty?}"

    if txs.empty?
      return 1101, '该条txhash数据不属于平台'
    end

    txs.each do | tx |

      id = tx[0]
      txHash = tx[1]
      to = tx[2]
      value = tx[3]
      assetType = tx[4].to_i
      sendether = tx[5].to_s

      #构造推送消息 现在改成了只对txid签名
      sha3 = @client.web3_sha3(txHash)

      #构造推送消息，附加sha3校验


      # 通用格式
      symbol = @token_type[assetType]
      senddata = build_post_url(txHash, to, sendether, symbol, sha3)

      uri = URI(@http_url + senddata)
      res = Net::HTTP.get(uri) # => String

        begin
          jsonhash = JSON.parse(res)
          resultCode = jsonhash['resultCode']
          resultMsg = jsonhash['resultMsg']
            #$logger.info($resultCode)
        rescue
          resultCode = 0
        end

        #判断返回值，约定好对方返回1 或者 0
        if resultCode >= 10000
          # $logger.info("post_deposit success,resultCode: " + resultCode.to_s)
          # TODO 此处浪费数据库session
          update_many_txs(id, {'isPosted'=>1, 'resultCode'=>resultCode})
          # TODO 这里要补全一个记录功能, 暂时没有数据库表
        else
          # 这里留着写日志
        end
      return resultCode, resultMsg
    end
  end

  def sync_height
    # 获取当前最新高度
    begin
      lasted_height = @client.eth_blockNumber()
      self.height = lasted_height.hex.to_i
    rescue
      # 不做任何处理
    end
  end

  def sync_height_always
    # 使用多线程处理, 保持在运行期间永远最新height
    require 'thread'
    self.sync_always_th = Thread.new do
      begin
        begin
          @mutex.lock # 加锁
          sync_height # 同步
        rescue
          # 异常不做任何处理
        ensure
          @mutex.unlock # 强制锁锁
        end
        sleep self.sync_time
      end while self.always_sync
    raise
      # 如果异常, 退出该线程
      return
    end
  end
end

if __FILE__ == $0
  puts build_tx_token_data('0xa9059cbb', '440f67fc48addb7b3069378942008fd948cc594d', '5f45a60')
  puts build_tx_send_body('address', 'to', value:'0x1021', gas:'0xgas', gas_price:'0xprice', data:'0xa9059cbb000000000000000000000000440f67fc48addb7b3069378942008fd948cc594d0000000000000000000000000000000000000000000000000000000005f45a60')
  # h = HandleBlock.new(100000, '114.114.114.114:99898')
  misc = HandleMisc.new('114.114.114.114:99898', 'http://www.baidu.com')
end
