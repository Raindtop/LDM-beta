package com.raindrop.security.UserDetails;

import com.raindrop.common.Constant.RedisConstant;
import com.raindrop.common.Model.Account.AccountManager;
import com.raindrop.common.Utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/*
 * @Description TODO 查找当前用户
 * @Author zhang hesong
 * @Date 17:11 2020/4/2
 **/
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        System.out.println(name);
        redisUtil.get(RedisConstant.account.getValue() + name);
        return null;
    }
}
