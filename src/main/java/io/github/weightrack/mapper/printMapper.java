package io.github.weightrack.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface printMapper {

    @Select("SELECT COUNT(*) FROM poundbill WHERE DATE(printTime) = CURDATE() AND printed = 1")
    int getTodayPrintedCount();

}
