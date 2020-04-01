package com.raindrop.core;

import com.alibaba.fastjson.JSON;
import com.raindrop.common.Model.Account.Account;
import com.raindrop.core.Mapper.Account.AccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LdmCoreApplicationTests {
    @Autowired
    private AccountMapper accountMapper;

    @Test
    void contextLoads() {
        Account account = Account.builder().name("Joke").mobile("11111111111").email("123123@qq.com").roleId(1l).depId(1l).status(0).build();
        accountMapper.insert(account);
        accountMapper.insertWithId(account);
        System.out.println("Id:" + account.getAccountId());

        System.out.println(JSON.toJSONString("mobile: " + accountMapper.find("11111111111","","")));
        System.out.println(JSON.toJSONString("email: " + accountMapper.find("","123123@qq.com","")));
        System.out.println(JSON.toJSONString("name: " + accountMapper.find("","","J")));

    }

}
