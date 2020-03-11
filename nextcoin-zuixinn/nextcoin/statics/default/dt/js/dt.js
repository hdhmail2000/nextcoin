function buy_group(username,nowGroup,beforeGroup){
	if(username == '' || username == undefined && username != 'admine'){
		layer.msg('请登录,3s后自动跳转……');
		window.setTimeout("location.href='/index.php?s=exc&c=userController&m=login'",3000);
	}
	layer.confirm('确定购买吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){
		$.ajax({
			type:"get",
			url:"/index.php?s=help&c=member_controller&m=upgrade&username="+username+'&now_group='+nowGroup+'&before_group='+beforeGroup,
			async:true,
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data == 0){
					layer.msg('请先实名认证');
				}else if(data == -2){
					layer.msg('余额不足');
				}else if(data == -3){
					layer.msg('当前等级比购买的等级高');
				}else if(data > 0){
					layer.msg('购买成功');
					window.setTimeout("window.location.reload()",3000);
				}else if(data == -4){
					layer.msg('请登录,3s后自动跳转……');
				window.setTimeout("location.href='/index.php?s=exc&c=userController&m=login'",3000);
				}else{
					layer.msg('失败');
				}
			}
		});
	});
	
}
