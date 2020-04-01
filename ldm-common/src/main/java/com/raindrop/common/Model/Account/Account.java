package com.raindrop.common.Model.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * @Description TODO 账号
 * @Author zhang hesong
 * @Date 9:56 2020/4/1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    /**
     * 主键
     */
    private Long accountId;
    /**
     * 昵称
     */
    private String name;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String headImg;
    /**
     * 角色
     */
    private Long roleId;
    /**
     * 部门
     */
    private Long depId;
    /**
     * 最后一次登录IP
     */
    private String lastLoginIp;
    /**
     * 最后一次登录时间
     */
    private String lastLoginTime;
    /**
     * 账号状态 0-正常  -5-停用  -10-删除
     */
    private int status;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
