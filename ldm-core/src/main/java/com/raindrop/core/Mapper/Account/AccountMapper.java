package com.raindrop.core.Mapper.Account;

import com.raindrop.common.Model.Account.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/*
 * @Description TODO 账号数据处理
 * @Author zhang hesong
 * @Date 14:32 2020/4/1
 **/
public interface AccountMapper {
    /*
     * @Description TODO 添加账号
     * @param: [account]
     * @return: void
     * @auther: zhang hesong
     * @date: 14:47 2020/4/1
     */
    @Insert("insert into account(name,mobile,email,head_img,role_id,dep_id,status,create_time,update_time)" +
            "values" +
            "(#{a.name},#{a.mobile},#{a.email},#{a.headImg},#{a.roleId},#{a.depId},#{a.status},now(),now())")
    void insert(@Param("a") Account account);

    /*
     * @Description TODO 添加账号同时获取主键
     * @param: [account]
     * @return: void
     * @auther: zhang hesong
     * @date: 14:47 2020/4/1
     */
    @Insert("insert into account(name,mobile,email,head_img,role_id,dep_id,status,create_time,update_time)" +
            "values" +
            "(#{a.name},#{a.mobile},#{a.email},#{a.headImg},#{a.roleId},#{a.depId},#{a.status},now(),now())")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", statementType = StatementType.STATEMENT, keyProperty="a.accountId", before = false, resultType = Long.class)
    void insertWithId(@Param("a") Account account);

    /*
     * @Description TODO 删除账号--物理删除--系统管理员
     * @param: [accountId]
     * @return: int
     * @auther: zhang hesong
     * @date: 14:59 2020/4/1
     */
    @Delete("delete from account where account_id=#{account}")
    int deletePhysical(@Param("account") Long accountId);

    /*
     * @Description TODO 删除账号--逻辑删除--部门管理员
     * @param: [accountId]
     * @return: int
     * @auther: zhang hesong
     * @date: 15:00 2020/4/1
     */
    @Update("update account set status=-10 where account_id=#{account}")
    int deleteLogical(@Param("account") Long accountId);

    /*
     * @Description TODO 更新账号
     * @param: [account]
     * @return: int
     * @auther: zhang hesong
     * @date: 15:03 2020/4/1
     */
    int update(@Param("account") Account account);

    /*
     * @Description TODO 查询账号
     * @param: [mobile, email, name] [手机号,邮箱,姓名]
     * @return: java.util.List<com.raindrop.common.Model.Account.Account>
     * @auther: zhang hesong
     * @date: 15:06 2020/4/1
     */
    List<Account> find(@Param("mobile") String mobile, @Param("email") String email, @Param("name") String name);
}
