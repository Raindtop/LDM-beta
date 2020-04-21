package com.raindrop.core.Mapper.Role;

import com.raindrop.common.Model.Role.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/*
 * @Description TODO 角色操作
 * @Author zhang hesong
 * @Date 14:29 2020/4/21
 **/
public interface RoleMapper {
    /*
     * @Description TODO 添加角色
     * @param: [role]
     * @return: void
     * @auther: zhang hesong
     * @date: 15:03 2020/4/21
     */
    @Insert("insert into role(role_name,description,create_time,update_time) " +
            "values " +
            "(#{a.roleName},#{a.description},now(),now())")
    void insert(@Param("a")Role role);

    /*
     * @Description TODO 添加角色并返回角色主键
     * @param: [role]
     * @return: void
     * @auther: zhang hesong
     * @date: 15:03 2020/4/21
     */
    @Insert("insert into role(role_name,description,create_time,update_time) " +
            "values " +
            "(#{a.roleName},#{a.description},now(),now())")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", statementType = StatementType.STATEMENT, keyProperty="a.roleId", before = false, resultType = Long.class)
    void insertWithId(@Param("a") Role role);

    /*
     * @Description TODO 删除角色以及角色权限
     * @param: [id]
     * @return: void
     * @auther: zhang hesong
     * @date: 15:03 2020/4/21
     */
    @Delete("delete a,b from role a right join role_authority b on a.role_id=b.role_id where a.role_id=#{a}")
    void delete(@Param("a") Long id);

    /*
     * @Description TODO 更新角色信息
     * @param: [role]
     * @return: void
     * @auther: zhang hesong
     * @date: 15:04 2020/4/21
     */
    @Update("update role set roleName=#{a.roleName},description=#{a.description},update_time=now() where id=#{roleId}")
    void update(@Param("a")Role role);

    /*
     * @Description TODO 查找所有角色
     * @param: []
     * @return: java.util.List<com.raindrop.common.Model.Role.Role>
     * @auther: zhang hesong
     * @date: 15:06 2020/4/21
     */
    @Select("select * from role")
    List<Role> getRole();
}
