package io.github.weightrack.mapper;

import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShowListMapper {

    @Select("SELECT * FROM poundbill WHERE removed = 0 and (printed = true) AND IOType = #{IOType} ORDER BY creatTime DESC LIMIT #{offset}, #{limit}")
    PoundBillModel[] showList(String IOType, int offset, int limit);

    @Select("SELECT * FROM poundbill WHERE removed = 0 and printed = true ORDER BY creatTime DESC LIMIT #{offset}, #{limit}")
    PoundBillModel[] showListAll(int offset, int limit);

    @Select("SELECT * FROM poundbill WHERE removed = 0 and IOType = #{IOType} AND DATE(creatTime) = CURDATE() ORDER BY creatTime DESC LIMIT #{offset}, #{limit}")
    PoundBillModel[] showTodayList(String IOType, int offset, int limit);

    @Select("SELECT * FROM poundbill WHERE removed = 0 and DATE(creatTime) = CURDATE() ORDER BY creatTime DESC LIMIT #{offset}, #{limit}")
    PoundBillModel[] showTodayListAll(int offset, int limit);

    @Select("SELECT COUNT(*) FROM poundbill WHERE removed = 0 and (printed = true) AND IOType = #{IOType}")
    int showListCount(String IOType);

    @Select("SELECT COUNT(*) FROM poundbill WHERE removed = 0 and printed = true")
    int showListAllCount();

    @Select("SELECT COUNT(*) FROM poundbill WHERE removed = 0 and IOType = #{IOType} AND DATE(creatTime) = CURDATE()")
    int showTodayListCount(String IOType);

    @Select("SELECT COUNT(*) FROM poundbill WHERE removed = 0 and DATE(creatTime) = CURDATE()")
    int showTodayListAllCount();


    @Select("SELECT * FROM poundbill WHERE removed = 0 and (#{plateNumber} = '' OR plateNumber LIKE CONCAT('%', #{plateNumber}, '%')) and (#{poundId} = '' OR poundId LIKE CONCAT('%', #{poundId}, '%')) and tareWeight = 0 and printed = false ORDER BY creatTime DESC")
    PoundBillModel[] showListByPlateNumberNotInPound(String plateNumber, String poundId);

    @Select("SELECT * FROM poundbill WHERE removed = 0 and (#{plateNumber} = '' OR plateNumber LIKE CONCAT('%', #{plateNumber}, '%')) and (#{poundId} = '' OR poundId LIKE CONCAT('%', #{poundId}, '%')) and tareWeight != 0 and printed = false ORDER BY creatTime DESC")
    PoundBillModel[] showListByPlateNumberAlreadyInPound(String plateNumber, String poundId);

    @Select("SELECT * FROM poundbill WHERE removed = 0 and (#{plateNumber} = '' OR plateNumber LIKE CONCAT('%', #{plateNumber}, '%')) and (#{poundId} = '' OR poundId LIKE CONCAT('%', #{poundId}, '%')) and printed = true ORDER BY creatTime DESC")
    PoundBillModel[] showListByPlateNumberPrinted(String plateNumber, String poundId);

    @Select("select count(*) from poundbill where IOType = #{IOType} and removed = 0 and date(now()) = date(creatTime)")
    int getTodayCount(String IOType);

    @Select("select count(*) from poundbill where tareWeight = 0 and removed = 0 and date(now()) = date(creatTime) and IOType = #{IOType}")
    int getTodayEmptyWeighing(String IOType);

    @Select("select count(*) from poundbill where tareWeight != 0 and removed = 0 and date(now()) = date(creatTime) and IOType = #{IOType}")
    int getTodayFullWeighing(String IOType);
}
