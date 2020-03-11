require 'logger'

$LOAD_PATH.unshift File.expand_path(File.join(File.dirname(__FILE__), '.'))

require 'config'
require 'lib/bitcoin_rpc'
require 'db/baseinfo_dao'
require 'db/txs_dao'

#使用教程
# 1.配置log目录路径
# 2.配置数据库
# 3.运行

#*****************************************************  开始执行主程序  **************************************************
if $0 == __FILE__

  #输出到文件，使用配置中的$log_path, 可以动态调整日志路径
  logger = Logger.new(($log_path + 'clean_db_txs.log').to_s)
  #logger = Logger.new(STDOUT)  #输出到文件，文件名log.txt

  logger.info("** START THE PROGRAM **")

  baseinfos = get_baseinfo_tb
  if baseinfos.length == 0
    logger.warn('[ERR] The baseinfo is not data')
    exit(1)
  end
  $lastLoadBlock = baseinfos['lastLoadBlock'].to_i
  $rpcHost = baseinfos['rpcHost']
  $rpcPort = baseinfos['rpcPort']
  $isCleanTxs = baseinfos['isCleanTxs']
  $storageBlockCount = baseinfos['storageBlockCount'].to_i

  $cleanBlockHeight = $lastLoadBlock - $storageBlockCount
  #logger.debug("baseinfo: #{baseinfos}")

  #如果需要根据最新区块来清理数据，需要这部分代码（目前的方案是根据数据库同步的最新区块删除）
  #清空历史数据，txs数据库列表只保存最近的所有交易以及所有的充值交易就行
  if ($isCleanTxs.to_i == 1) && ($cleanBlockHeight > 0)

     $deltxCount = get_need_clean_tx($cleanBlockHeight)
     #logger.info("$deltxCount : " + $deltxCount)

     del_clean_tx($cleanBlockHeight)
     #logger.info("sql_sel: " + $sql_sel)
     logger.info("Clean " + $deltxCount.to_s + " Txs where BlockNumber < " + $cleanBlockHeight.to_s)
  end
  logger.info("** END THE PROGRAM **")
end
