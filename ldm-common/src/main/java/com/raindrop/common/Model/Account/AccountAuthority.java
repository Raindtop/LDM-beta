package com.raindrop.common.Model.Account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * @Description TODO 账号权限
 * @Author zhang hesong
 * @Date 11:07 2020/4/1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountAuthority implements Serializable {
    /**
     * 主键
     */
    private Long accountAuthorityId;
    /**
     * 账号主键
     */
    private Long accountId;
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
