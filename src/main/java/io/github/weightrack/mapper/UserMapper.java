package io.github.weightrack.mapper;

import io.github.weightrack.module.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from users where username=#{username}")
    User findByUsername(String username);
}
