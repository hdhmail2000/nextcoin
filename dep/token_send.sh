# 使用相对绝对路径, 不再关心文件的路径, 也不需要关心运行的位置
# ******   NOTICE   *******
# 注意, .sh文件必须与token_send_user_to_withdraw.rb文件在同一目录下, 不然会找不到执行文件
basepath=$(cd `dirname $0`;pwd);
ruby $basepath/token_send_user_to_withdraw.rb
