package com.raindrop.common.Model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * @Description TODO 角色
 * @Author zhang hesong
 * @Date 10:42 2020/4/1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    /**
     * 主键
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
