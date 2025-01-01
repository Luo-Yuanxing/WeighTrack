package io.github.weightrack.mapper;

import io.github.weightrack.module.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from users where username=#{username}")
    User[] findByUsername(String username);

    @Select("select * from users where realName=#{realName}")
    User[] findByRealName(String realName);

    @Update("update users set password=#{password}, realName=#{realName} where username=#{username}")
    void updateByUser(User user);

    @Update("update users set lastLogin=now() where username=#{username}")
    void updateLastLogin(String username);

    @Insert("insert into users(username, password, realName, role) values(#{username}, #{password}, #{realName}, #{role})")
    int insertUser(User user);

    @Select("select * from users")
    User[] findAll();

    @Delete("delete from users where username=#{username}")
    int delete(String username);

    @Update("update users set password=#{password}, realName=#{realName} where username=#{username}")
    void updateUser(User user);
}
