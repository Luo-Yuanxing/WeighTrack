package io.github.weightrack.mapper;

import io.github.weightrack.module.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WeigherMapper {

    @Select("select * from users")
    User[] getAllWeighers();
}
