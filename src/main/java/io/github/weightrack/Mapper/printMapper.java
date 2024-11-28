package io.github.weightrack.Mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface printMapper {

    @Select("SELECT COUNT(*) FROM poundbill WHERE DATE(printTime) = CURDATE() AND isPrinted = 1")
    int getTodayPrintedCount();

}
