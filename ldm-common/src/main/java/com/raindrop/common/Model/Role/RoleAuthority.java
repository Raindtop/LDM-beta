package com.raindrop.common.Model.Role;

import java.io.Serializable;

/*
 * @Description TODO 角色权限
 * @Author zhang hesong
 * @Date 11:29 2020/4/1
 **/
public class RoleAuthority implements Serializable {
    /**
     * 主键
     */
    private Long roleAuthorityId;
    /**
     * 角色
     */
    private Long roleId;
    /**
     * 菜单操作
     */
    private Long operationId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
