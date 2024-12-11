package io.github.weightrack.mapper;

import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataSummaryMapper {

    @Select("select * from poundbill where printed = true and IOType = #{IOType} and creatTime between #{startTime} and #{endTime}")
    PoundBillModel[] getData(@Param("IOType") String IOType, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("select coalType, sum(netWeight) as totalNetWeight, sum(primaryWeight) as totalPrimaryWeight, sum(profitLossWeight) as totalProfitLossWeight, sum(printed) as totalTrips from poundbill where printed = true and IOType = #{IOType} and creatTime between #{startTime} and #{endTime} group by coalType")
    List<Map<String, Object>> getCountGroupByCoalType(@Param("IOType") String IOType, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map<String, Object>> getCountGroupByDate(@Param("IOType") String IOType,
                                                  @Param("startTime") String startTime,
                                                  @Param("endTime") String endTime,
                                                  @Param("coalTypes") String[] coalTypes);


}