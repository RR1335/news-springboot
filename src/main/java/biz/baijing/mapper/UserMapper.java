package biz.baijing.mapper;

import biz.baijing.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Insert("insert into user(username, password, create_time, update_time) " +
            "values (#{username},#{password},now(),now())")
    void add(String username, String password);
}
