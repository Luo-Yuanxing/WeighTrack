package io.github.weightrack.mapper;

import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DataSummaryMapper {

    @Select("select * from poundbill where printed = true and IOType = #{IOType} and creatTime between #{startTime} and #{endTime}")
    PoundBillModel[] getData(@Param("IOType") String IOType, @Param("startTime") String startTime, @Param("endTime") String endTime);


}
