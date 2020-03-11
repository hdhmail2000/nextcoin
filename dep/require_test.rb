#!/usr/bin/ruby
# coding: utf-8
# 测试脚本, 确认数据库, 变量, 数据表好用
$LOAD_PATH << '.'
$block = "\033[30m%s\033[0m"
$red = "\033[31m%s\033[0m"
$green = "\033[32m%s\033[0m"
$yellow = "\033[33m%s\033[0m"
$blue = "\033[34m%s\033[0m"
$white = "\033[37m%s\033[0m"
$pack_ok = true
$config_ok = true
$db_ok = true

def put_has_color(color, contend)
  puts color % contend
end

def test_require_pack(packs)
  packs.each do | pack |
    begin
      require pack
      put_has_color($green, '%s包引用成功' % pack)
    rescue LoadError
      put_has_color($red, '%s包引用失败' % pack)
      $pack_ok = false
    rescue Mysql::Error
      put_has_color($red, '%s包引用失败, 数据库连接不成功' % pack)
    end
  end
end

def test_db_tbs(tables)
  begin
    require 'mysql'
  rescue LoadError
    put_has_color($red, 'mysql包引用失败')
    return
  end

  tables.each do | table |
    sql = 'select count(1) from %s' % table
    begin
      $client = Mysql.init
      $client.options(Mysql::SET_CHARSET_NAME, 'utf8')
      $client = Mysql.real_connect($db_host, $db_username, $db_password, $db_db, $db_port)
      query = $client.query(sql)
      while row = query.fetch_row do
        put_has_color($green, "%s数据库正常 表中有 #{$yellow} 条数据" % [table, row[0]])
        if ['baseinfo', 'eth_token'].include?table
          if row[0].to_i < 1
            put_has_color($red, "%s 表中需要至少 %s 条数据" % [table, 1])
            $db_ok = false
          end
        end
      end
    rescue
      put_has_color($red, '%s数据库异常' % table)
      $db_ok = false
    end
  end
end


def test_config_file
  begin
    require 'config'
    configs = {'db_host'=>$db_host, 'db_port'=>$db_port, 'db_username'=>$db_username, 'db_password'=>$db_password,
     'db_db'=>$db_db, 'wei'=>$wei.to_i, 'url'=>$url, 'rpc_addr'=>$rpc_addr % ['127.0.0.1', '80'],
    'withdrawAcount'=>$withdrawAcount, 'innerAddr'=>$innerAddr, 'log_path'=>$log_path, 'threshold_token'=>$threshold_token,
    'threshold_eth'=>$threshold_eth}
  rescue LoadError
    put_has_color($red, '配置文件导入失败')
    return
  end
  configs.each do | k, cfg |
    begin
      put_has_color($green, "#{k} is #{$yellow} 配置获取成功" % cfg)
    rescue
      put_has_color($red, "#{k} 配置获取失败")
      $config_ok = false
    end
  end
end

# 使用相对绝对路径引用包
$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))

load_pack = ['net/http', 'uri', 'json', 'logger', 'open-uri', 'digest/sha1', 'mysql', 'config', 'lib/bitcoin_rpc',
             'lib/tools', 'db/token_dao', 'db/txs_dao', 'db/txs_dao', 'db/baseinfo_dao',
             'db/account_dao', 'db/coldtx_dao', 'chain/txs', 'chain/txs', 'chain/robotization', ]

tables = ['eth_token', 'baseinfo', 'txs', 'accounts', 'coldtx']

put_has_color($blue, "***************  包导入测试开始 ***************\n")
test_require_pack(load_pack)
put_has_color($blue, "***************  包导入测试完成 ***************\n")

put_has_color($blue, "***************  配置文件测试开始 ***************\n")
test_config_file
put_has_color($blue, "***************  配置文件测试结束 ***************\n")

put_has_color($blue, "***************  数据库表检测开始 ***************\n")
test_db_tbs(tables)
put_has_color($blue, "***************  数据库表检测结束 ***************\n")

if $pack_ok and $config_ok and $db_ok
  put_has_color($green, '当前配置可以运行脚本')
else
  put_has_color($red, "当前配置不满足运行, 检测结果:
    模块#{$pack_ok}
    数据库#{$db_ok}
    配置#{$config_ok}
")
end