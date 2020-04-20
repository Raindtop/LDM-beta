package com.raindrop.security.UserDetails;

import com.raindrop.common.Constant.RedisConstant;
import com.raindrop.common.Model.Account.Account;
import com.raindrop.common.Model.Account.AccountManager;
import com.raindrop.common.Utils.RedisUtil;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
 * @Description TODO 查找当前用户
 * @Author zhang hesong
 * @Date 17:11 2020/4/2
 **/
@Component
@Log
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private AccountManager accountManager;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        log.info(name);
        Account account = null;
        if(name.equals("admin")){
            account = Account.builder().accountId(1L).password(passwordEncoder.encode("123456")).name("admin").mobile("admin").email("635880154@qq.com").roleId(1L)
                    .depId(1L).status(5).build();
        }
        redisUtil.get(RedisConstant.account.getValue() + name);
        return account == null ? null : new CurrentAccount(account);
    }
}
