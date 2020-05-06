package com.raindrop.common.Model.Account;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/*
 * @Description TODO 账号
 * @Author zhang hesong
 * @Date 9:56 2020/4/1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "account")
public class Account implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "account_id", type = IdType.AUTO)
    private Long accountId;

    /**
     * 昵称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 电话
     */
    @TableField(value = "mobile")
    private String mobile;
    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 头像
     */
    @TableField(value = "head_img")
    private String headImg;
    /**
     * 角色
     */
    @TableField(value = "role_id")
    private Long roleId;
    /**
     * 部门
     */
    @TableField(value = "dep_id")
    private Long depId;
    /**
     * 最后一次登录IP
     */
    @TableField(value = "last_login_id")
    private String lastLoginIp;
    /**
     * 最后一次登录时间
     */
    @TableField(value = "last_login_time")
    private String lastLoginTime;
    /**
     * 账号状态 0-正常  -5-停用  -10-删除
     */
    @TableField(value = "status")
    private int status;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private String createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private String updateTime;
}
