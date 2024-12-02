package io.github.weightrack.mapper;

import io.github.weightrack.module.CoalType;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CoalTypeMapper {

    @Select("select * from coal_type where isRemoved is false")
    CoalType[] getCoalType();

    @Insert("""
            INSERT INTO coal_type (name)
            VALUES (#{coalType})
            ON DUPLICATE KEY UPDATE
              isRemoved = IF(isRemoved = 1, 0, isRemoved);""")
    void insertCoalType(String coalType);

    @Update("update coal_type set isRemoved = 1 where name = #{name}")
    void deleteCoalTypeByName(String name);

}
