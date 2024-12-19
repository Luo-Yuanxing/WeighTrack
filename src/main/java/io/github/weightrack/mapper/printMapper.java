package io.github.weightrack.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface printMapper {

    @Select("SELECT COUNT(*) FROM poundbill WHERE IOType = #{IOType} and DATE(creatTime) = CURDATE() AND printed = 1")
    int getTodayPrintedCount(String IOType);

    @Update("update poundbill set poundID = #{poundID}, printed = 1, printTime = #{printTime} where id = #{id}")
    void updateById(int id, LocalDateTime printTime, String poundID);
}
