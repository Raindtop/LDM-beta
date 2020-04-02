package com.raindrop.common.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * @Description TODO Redis的常量类
 * @Author zhang hesong
 * @Date 16:47 2020/4/2
 **/
@Getter
@AllArgsConstructor
public enum RedisConstant {
    /**
     * 账号常量
     */
    account( 1,"account_");

    private int code;
    private String value;

}
