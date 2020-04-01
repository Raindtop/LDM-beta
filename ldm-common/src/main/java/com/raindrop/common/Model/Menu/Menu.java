package com.raindrop.common.Model.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/*
 * @Description TODO 菜单
 * @Author zhang hesong
 * @Date 11:21 2020/4/1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {
    /**
     * 主键
     */
    private Long menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单编码
     */
    private String menuCode;
    /**
     * 链接地址
     */
    private String url;
    /**
     * 菜单描述
     */
    private String description;
    /**
     * 图标
     */
    private String icon;
    /**
     * 图标颜色
     */
    private String iconColor;
    /**
     * 父级菜单
     */
    private Long parentMenuId;
    /**
     * 排序
     */
    private int sort;
    /**
     * 类型 0-菜单  5-按钮
     */
    private int type;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 子菜单
     */
    private List<Menu> childMenu;
}
