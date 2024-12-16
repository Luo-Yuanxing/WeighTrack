package io.github.weightrack.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface printMapper {

    @Select("SELECT COUNT(*) FROM poundbill WHERE IOType = #{IOType} and DATE(creatTime) = CURDATE() AND printed = 1")
    int getTodayPrintedCount(String IOType);

}
