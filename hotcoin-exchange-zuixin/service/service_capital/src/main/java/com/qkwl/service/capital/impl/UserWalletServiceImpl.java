package com.qkwl.service.capital.impl;

import com.qkwl.common.dto.wallet.UserCoinWallet;
import com.qkwl.common.rpc.capital.IUserWalletService;
import com.qkwl.service.capital.dao.UserCoinWalletMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户钱包接口
 */
@Service("userWalletService")
public class UserWalletServiceImpl implements IUserWalletService {
    @Autowired
    private UserCoinWalletMapper coinWalletMapper;

    @Override
    public UserCoinWallet getUserCoinWallet(Integer userId, Integer coinId) {
        return coinWalletMapper.selectByUidAndCoin(userId, coinId);
    }

    @Override
    public List<UserCoinWallet> listUserCoinWallet(Integer userId) {
        return coinWalletMapper.selectByUid(userId);
    }
}
