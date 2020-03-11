#!/usr/bin/ruby
# coding: utf-8

require 'pathname'

module Config
  # log path
  $log_path = Pathname.new(File.dirname(__FILE__)).realpath + 'log'

  # ************************** 数据库相关 ***************************
  # host
  $db_host = '172.21.196.137'
  $db_port = 3306
  $db_username = 'relang'
  $db_password = 'RelangDe88'    # todo 新服务必处理
  $db_db = 'ethpush'

  # ************************* RPC *****************************
  $rpc_addr = "http://usr:pass@%s:%s"
end

module Constant
  # 1后面18个0
  $wei = 1e18
  # 交易平台充值接口
  # $url = 'http://ip:8008/coin/eth/recharge.html?'    # todo 新服务必处理
  $url = 'http://172.21.196.141:8080/coin/eth/recharge.html?'    # todo 新服务必处理
  # ****************************** Token相关 ************************
  # Token 的转账方法
  $METHODID_TOKEN_TRANSFER  = '0xa9059cbb'
  # ABI
  $functionAbi = '0x70a08231000000000000000000000000'
  # 签名私钥（这个参数暂时不用）
  $key = 'nuKkluqvDUYyHPvtRm1p'
  # ****************************** 自动处理 *************************
  # 转币过渡地址
  $innerAddr = ''    # todo 新服务必处理
  # todo 过渡地址密码 mark
  $innerAddrPass = '' # 这里需要特别注意
  # todo 用户地址密码 mark efQdJBjYhFfbrIilHA8u
  $addressPass = '' # 这里需要特别注意
  # with raw Account
  $withdrawAcount = ''    # todo 新服务必处理
  # ***************  生成交易 ***************
  # Gas
  $gas = "0xea60" # 60000
  # Gas price
  $gasPrice = "0x4e3b29200" # 21gwei
  # token 余额超过这个值才会转走
  $threshold_token = 0.1
  # 地址的eth余额小于这个值才会往里转
  $threshold_eth = 0.002
  # 0.002 #从过渡地址转那么多余额给有Token的地址
  $sendHex = '0x71afd498d0000'


end


if $0 == __FILE__
  puts $log_path
  puts $wei.to_i
  puts 1e8 === 100000000
  $rpcHost = '127.0.0.1'
  $rpcPort = 7999
  # puts $rpc_addr << $rpcHost << ':' << $rpcPort.to_s
  puts $rpc_addr % [$rpcHost, $rpcPort]
  puts $rpc_addr
end
