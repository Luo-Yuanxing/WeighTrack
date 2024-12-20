package io.github.weightrack.mapper;

import io.github.weightrack.module.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from users where username=#{username}")
    User findByUsername(String username);

    @Update("update users set password=#{password}, realName=#{realName} where username=#{username}")
    void updateByUser(User user);

    @Update("update users set lastLogin=now() where username=#{username}")
    void updateLastLogin(String username);

    @Insert("insert into users(username, password, realName) values(#{username},#{password},#{realName})")
    int insertUser(String username, String password, String realName);

    @Select("select * from users")
    User[] findAll();

    @Delete("delete from users where username=#{username}")
    int delete(String username);
}
