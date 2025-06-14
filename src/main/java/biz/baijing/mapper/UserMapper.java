package biz.baijing.mapper;

import biz.baijing.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * 查找用户通过用户名
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);


    /**
     * 插入「新」用户
     * @param username
     * @param password
     */
    // 默认 nickname 等于 username
    @Insert("insert into user(username, password, nickname, create_time, update_time) " +
            "values (#{username},#{password},#{username},now(),now())")
    void add(String username, String password);

    /**
     * 更新用户信息
     * @param user
     */
    @Update("update user  set nickname = #{nickname},email = #{email}, update_time = #{updateTime} where id = #{id}")
    void update(User user);

    /**
     * 更新头像
     *
     * @param id
     * @param avatarUrl
     */
    @Update("update user set user_pic = #{avatarUrl},update_time = now() where id = #{id}")
    void updateAvatar(Integer id, String avatarUrl);

    /**
     * 更新密码
     * @param id
     * @param nowPwd
     */
    @Update("update user set password = #{nowPwd} where id = #{id}")
    void updatePwd(Integer id, String nowPwd);
}
