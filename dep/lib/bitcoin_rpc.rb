require 'uri'
require 'json'
require 'net/http'
require 'logger'
require 'open-uri'


class BitcoinRPC
  def initialize(service_url)
    @uri = URI.parse(service_url)
  end

  def method_missing(name, *args)
    post_body = {'method' => name, 'params' => args, 'id' => 'jsonrpc'}.to_json
    resp = JSON.parse(http_post_request(post_body))
    raise JSONRPCError, resp['error'] if resp['error']
    resp['result']
  end

  def http_post_request(post_body)
    http = Net::HTTP.new(@uri.host, @uri.port)
    request = Net::HTTP::Post.new(@uri.request_uri)
    request.basic_auth @uri.user, @uri.password
    request.content_type = 'application/json'
    request.body = post_body
    http.request(request).body
  end

  def senddata(url, data)
    url = url + data
    #logger.info(url)
    begin
      Net::HTTP.version_1_2 # 设定对象的运作方式
      if ($logger != nil)
        # $logger.info("链接地址参数:#{URI.escape(url)},文件名：#{__FILE__},第 #{__LINE__} 行")
        # $logger.info("传入data参数:#{data.to_json},文件名：#{__FILE__},第 #{__LINE__} 行")
      end
      ret_data = Net::HTTP.get(URI.parse(URI.escape(url)))
    rescue => exception
      # $logger.error("传递url地址为#{url}，错误!#{exception.to_s},文件名：#{__FILE__},第 #{__LINE__} 行")
      return nil
    end
    return ret_data
  end

  class JSONRPCError < RuntimeError;
  end
end



# Test
if $0 == __FILE__
  # 单元测试
  $LOAD_PATH << '.' << '..'
  require 'config'
  $rpcHost = '127.0.0.1'
  $rpcPort = 18336
  $rpcAddr = $rpc_addr % [$rpcHost, $rpcPort.to_s]
  puts "address: #{$rpcAddr}"
  coinrpc = BitcoinRPC.new($rpcAddr)
  puts 'start'
  block_number = coinrpc.eth_blockNumber()
  puts block_number
  puts 'end'
end