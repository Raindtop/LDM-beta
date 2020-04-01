package com.raindrop.common.Model.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * @Description TODO 菜单权限
 * @Author zhang hesong
 * @Date 13:04 2020/4/1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuOperation implements Serializable {
    /**
     * 主键
     */
    private Long operationId;
    /**
     * 菜单主键
     */
    private Long menuId;
    /**
     * 操作名称
     */
    private String operationName;
    /**
     * 创建日期
     */
    private String createTime;
    /**
     * 更新日期
     */
    private String updateTime;
}
