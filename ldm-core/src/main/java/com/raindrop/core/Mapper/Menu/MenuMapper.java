package com.raindrop.core.Mapper.Menu;

import com.raindrop.common.Model.Menu.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/*
 * @Description TODO 菜单管理
 * @Author zhang hesong
 * @Date 13:48 2020/4/21
 **/
public interface MenuMapper {
    /*
     * @Description TODO 添加菜单
     * @param: [menu]
     * @return: void
     * @auther: zhang hesong
     * @date: 13:50 2020/4/21
     */
    @Insert("insert into menu(menu_name,menu_code,url,description,icon,icon_color,parent_menu_id,sort,type,create_time,update_time) " +
            "values " +
            "(#{a.menuName},#{a.menuCode},#{a.url},#{a.description},#{a.icon},#{a.iconColor},#{a.parentMenuId},#{a.sort},now(),now())")
    void insert(@Param("a") Menu menu);

    /*
     * @Description TODO 添加菜单同时获取主键
     * @param: [menu]
     * @return: void
     * @auther: zhang hesong
     * @date: 13:50 2020/4/21
     */
    @Insert("insert into menu(menu_name,menu_code,url,description,icon,icon_color,parent_menu_id,sort,type,create_time,update_time) " +
            "values " +
            "(#{a.menuName},#{a.menuCode},#{a.url},#{a.description},#{a.icon},#{a.iconColor},#{a.parentMenuId},#{a.sort},now(),now())")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", statementType = StatementType.STATEMENT, keyProperty="a.menuId", before = false, resultType = Long.class)
    void insertWithId(@Param("a") Menu menu);

    /*
     * @Description TODO 删除菜单以及子菜单
     * @param: [id]
     * @return: void
     * @auther: zhang hesong
     * @date: 14:02 2020/4/21
     */
    @Delete("delete from menu where menu_id=#{a} or parent_menu_id=#{id}")
    void delete(@Param("a") Long id);

    /*
     * @Description TODO 更新菜单
     * @param: [menu]
     * @return: void
     * @auther: zhang hesong
     * @date: 14:26 2020/4/21
     */
    @Update("update menu set menuName=#{a.menuName},description=#{a.description},icon=#{a.icon},icon_color=#{a.iconColor},sort=#{a.sort},update_time=now() " +
            "where menu_id=#{a.menuId}")
    void update(@Param("a") Menu menu);

    /*
     * @Description TODO 查找所有的父级菜单
     * @param: []
     * @return: java.util.List<com.raindrop.common.Model.Menu.Menu>
     * @auther: zhang hesong
     * @date: 14:06 2020/4/21
     */
    @Select("select * from menu where parent_menu_id=0")
    @ResultType(Menu.class)
    List<Menu> getParentMenu();

    /*
     * @Description TODO 查询父级菜单下的子菜单
     * @param: [menuId]
     * @return: java.util.List<com.raindrop.common.Model.Menu.Menu>
     * @auther: zhang hesong
     * @date: 14:17 2020/4/21
     */
    @Select("select * from menu where parent_menu_id=#{a}")
    List<Menu> getMenuByParentId(@Param("a") Long menuId);
}
