package io.github.weightrack.mapper;

import io.github.weightrack.module.CoalType;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CoalTypeMapper {

    @Select("select * from coal_type")
    CoalType[] getCoalType();

    @Insert("insert into coal_type (name) values (#{name})")
    void insertCoalType(String coalType);

    @Update("delete from coal_type where name = #{name}")
    void deleteCoalTypeByName(String name);

}
