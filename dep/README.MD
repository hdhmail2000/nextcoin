# deposit token 钱包管理 重构版本

[ topic ]

## 原目录结构
```bash
.
├── README.MD
├── clean_db_txs.rb // 清理脚本, 每天0点10分执行
├── clean_tx.sh 
├── doc_get_balance.rb // 
├── etcex.sql
├── eth_get_balance.rb
├── eth_send_inner.sh
├── eth_send_inner_to_user.rb
├── eth_send_user_to_withdraw.rb
├── get_accounts_balance.rb
├── load_accounts.rb
├── lock/ 文件锁文件
├── log/ 日志文件
├── main.rb // 主文件 每分钟执行一次 
├── main.sh
├── main1.rb
├── require_test.rb
├── send_coin_to_cold_ddd.rb
├── test.rb
├── token_send.sh
└── token_send_user_to_withdraw.rb
```

## 现目录结构
```bash
.
├── DB.MD
├── README.MD
├── chain
│   └── txs.rb
├── clean_db_txs.rb
├── clean_tx.sh
├── config.rb
├── db
│   ├── account_dao.rb
│   ├── baseinfo_dao.rb
│   ├── coldtx_dao.rb
│   ├── token_dao.rb
│   └── txs_dao.rb
├── deposit.iml
├── doc_get_balance.rb
├── etcex.sql
├── eth_get_balance.rb
├── eth_send_inner.sh
├── eth_send_inner_to_user.rb
├── eth_send_user_to_withdraw.rb
├── get_accounts_balance.rb
├── lib
│   ├── bitcoin_rpc.rb
│   ├── lib.iml
│   └── tools.rb
├── load_accounts.rb
├── lock
│   ├── clean_tx.lock
│   ├── eth_send_inner.lock
│   ├── main.lock
│   ├── main.rb
│   └── token_send.lock
├── main.rb
├── main.sh
├── main1.rb
├── require_test.rb
├── send_coin_to_cold_ddd.rb
├── test.rb
├── token_send.sh
└── token_send_user_to_withdraw.rb
```

## 重构原则
1. 高内聚低耦合
2. MVC设计模式(部分拆分)
3. 单一职责原则
4. 最小全局变量原则(全局变量越多, 耦合度越高, 函数功能对外部依赖越大, 慎用)

## 上线顺序
1. 安装ETH | ETC节点
2. 安装数据库
3. 安装ruby & 库
4. 上传代码
5. 修改配置文件
6. 运行require_test.rb文件
7. 配置job

## 改动介绍
1. 数据库改动
    * 增加`eth_token`表, 代码中不再直接写入token有关信息.
    * 所有与数据库交到放到db/中, 每个库一个文件, 将操作封装函数, 方便复用.
    * db/文件中函数均使用$client连接.
2. 流程变动
    * 未变动流程.
    * 只对耦合代码优化, 减小代码复杂量, 增加复用.
3.  配置文件变动
    * 将各文件需要复用到的常量放入到配置文件(`config`)中.
    * 将数据库配置放到配置文件当中.
4. `require_test`文件
    * 在项目运行前使用此文件可以检测`必要的检查`, 并根据实际情况给出是提示.

## 数据库表结构
ethpush.sql





