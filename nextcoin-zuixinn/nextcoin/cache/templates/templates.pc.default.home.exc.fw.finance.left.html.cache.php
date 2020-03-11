<div class="col-xs-2 financial-sidebar">
    <h2 class="financial-sidebar-tetil"><?php echo $finance['account_center']; ?></h2>
    <ul class="financial-sidebar-nav">
        <li class="financial-sidebar-bar">
            <a href="/index.php?s=exc&c=financeController" <?php if ($method=='' || $method=='index') { ?>class="active"<?php } ?>><?php echo $finance['account_asset']; ?></a>
        </li>
        <li class="financial-sidebar-bar">
            <a href="/index.php?s=exc&c=financeController&m=commission" <?php if ($method=='commission') { ?>class="active"<?php } ?>><?php echo $finance['recommend_my']; ?></a>
        </li>
        <li class="financial-sidebar-bar">
            <a href="/index.php?s=exc&c=financeController&m=unLockUser" <?php if ($method=='unLockUser') { ?>class="active"<?php } ?>><?php echo $finance['unLockUser_my']; ?></a>
        </li>
        <!--<?php if (is_array($data)) { $count=count($data);foreach ($data as $k=>$item) {  if ($item['shortName'] == 'USDT') { ?>
        <li class="financial-sidebar-bar">
            <a href="/index.php?s=exc&c=financeController&m=c2cDeposit&symbol=<?php echo $item['coinId']; ?>" <?php if ($method=='c2cDeposit') { ?>class="active"<?php } ?>>C2C<?php echo $finance['account_deposit']; ?></a>
        </li>
        <?php }  } } ?>-->
        <!--<li class="financial-sidebar-bar">
            <a href="/index.php?s=exc&c=financeController&m=sdRecord" <?php if ($method=='sdRecord') { ?>class="active"<?php } ?>><?php echo $finance['manual_recharge_details']; ?></a>
        </li>-->
        <!--<li class="financial-sidebar-bar">
            <a href="/index.php?s=exc&c=financeController&m=transferAccounts" <?php if ($method=='transferAccounts') { ?>class="active"<?php } ?>><?php echo $finance['account_transfer']; ?></a>
        </li>-->
        <!--<li class="financial-sidebar-bar">
            <a href="/index.php?s=exc&c=financeController&m=authentication" <?php if ($method=='authentication') { ?>class="active"<?php } ?>><?php echo $finance['account_mining']; ?></a>
        </li>-->
    </ul>
</div>