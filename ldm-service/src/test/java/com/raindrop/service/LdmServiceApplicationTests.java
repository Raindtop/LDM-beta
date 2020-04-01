package com.raindrop.service;

import com.alibaba.fastjson.JSON;
import com.raindrop.common.Model.Account.Account;
import com.raindrop.core.Mapper.Account.AccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LdmServiceApplication.class)
@WebAppConfiguration
public class LdmServiceApplicationTests {
    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void contextLoads() {
        Account account = Account.builder().name("Joke").mobile("11111111111").email("123123@qq.com").roleId(1l).depId(1l).status(0).build();
        accountMapper.insert(account);
        accountMapper.insertWithId(account);
        System.out.println("Id:" + account.getAccountId());

        System.out.println(JSON.toJSONString("mobile: " + accountMapper.find("11111111111","","")));
        System.out.println(JSON.toJSONString("email: " + accountMapper.find("","123123@qq.com","")));
        System.out.println(JSON.toJSONString("name: " + accountMapper.find("","","J")));

    }

    @Test
    public void update() {
        Account account = Account.builder().accountId(1l).name("Jokessssssssss").mobile("11111111111").email("123123@qq.com").roleId(1l).depId(1l).status(0).build();
        accountMapper.update(account);
        accountMapper.deletePhysical(6l);
        accountMapper.deleteLogical(2l);
    }
}

