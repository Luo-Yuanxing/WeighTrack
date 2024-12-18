package io.github.weightrack.mapper;

import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShowListMapper {

    @Select("SELECT * FROM poundbill WHERE (printed = true) AND IOType = #{IOType} ORDER BY creatTime DESC LIMIT #{offset}, #{limit}")
    PoundBillModel[] showList(String IOType, int offset, int limit);

    @Select("SELECT * FROM poundbill WHERE printed = true ORDER BY creatTime DESC LIMIT #{offset}, #{limit}")
    PoundBillModel[] showListAll(int offset, int limit);

    @Select("SELECT * FROM poundbill WHERE IOType = #{IOType} AND DATE(creatTime) = CURDATE() ORDER BY creatTime DESC LIMIT #{offset}, #{limit}")
    PoundBillModel[] showTodayList(String IOType, int offset, int limit);

    @Select("SELECT * FROM poundbill WHERE DATE(creatTime) = CURDATE() ORDER BY creatTime DESC LIMIT #{offset}, #{limit}")
    PoundBillModel[] showTodayListAll(int offset, int limit);

    @Select("SELECT COUNT(*) FROM poundbill WHERE (printed = true) AND IOType = #{IOType}")
    int showListCount(String IOType);

    @Select("SELECT COUNT(*) FROM poundbill WHERE printed = true")
    int showListAllCount();

    @Select("SELECT COUNT(*) FROM poundbill WHERE IOType = #{IOType} AND DATE(creatTime) = CURDATE()")
    int showTodayListCount(String IOType);

    @Select("SELECT COUNT(*) FROM poundbill WHERE DATE(creatTime) = CURDATE()")
    int showTodayListAllCount();

    @Select("SELECT * FROM poundbill WHERE plateNumber = #{plateNumber} ORDER BY creatTime DESC LIMIT 10")
    PoundBillModel[] showListByPlateNumber(String plateNumber);
}
