package io.github.weightrack.mapper;


import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PoundBillMapper {

    @Insert("INSERT INTO poundbill (IOType, coalType, plateNumber, grossWeight, tareWeight, netWeight, primaryWeight, profitLossWeight, emptyLoadTime, fullLoadTime, outputUnit, inputUnit, weigher, modifyTime, creatorId, poundID, printTime, printed, creatTime) " +
            "VALUES (#{IOType}, #{coalType}, #{plateNumber}, #{grossWeight}, #{tareWeight}, #{netWeight}, #{primaryWeight}, #{profitLossWeight}, #{emptyLoadTime}, #{fullLoadTime}, #{outputUnit}, #{inputUnit}, #{weigher}, #{modifyTime}, #{creatorId}, #{poundID}, #{printTime}, #{printed}, #{creatTime})")
    void insert(PoundBillModel poundBillModel);

    @Update("update poundbill set note = #{note}, IOType=#{IOType}, coalType=#{coalType}, plateNumber=#{plateNumber}, grossWeight=#{grossWeight}, tareWeight=#{tareWeight}, netWeight=#{netWeight}, primaryWeight=#{primaryWeight}, profitLossWeight=#{profitLossWeight}, outputUnit=#{outputUnit}, inputUnit=#{inputUnit}, printTime=#{printTime}, modifyTime=now() where id=#{id}")
    void update(PoundBillModel poundBillModel);

    @Select("select * from poundbill where id = #{id}")
    PoundBillModel selectById(int id);

    @Delete("update poundbill set removed = 1 where id = #{id}")
    void deleteById(int id);

    @Select("select * from poundbill where removed = 0")
    PoundBillModel[] selectAll();

    @Select("select count(*) from poundbill where removed = 0")
    int count();

}