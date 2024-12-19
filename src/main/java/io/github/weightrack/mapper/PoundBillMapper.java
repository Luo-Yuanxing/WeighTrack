package io.github.weightrack.mapper;


import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PoundBillMapper {

    @Insert("INSERT INTO poundbill (IOType, coalType, plateNumber, grossWeight, tareWeight, netWeight, primaryWeight, profitLossWeight, emptyLoadTime, fullLoadTime, outputUnit, inputUnit, weigher, modifyTime, creatorId, poundID, printTime, printed, creatTime) " +
            "VALUES (#{IOType}, #{coalType}, #{plateNumber}, #{grossWeight}, #{tareWeight}, #{netWeight}, #{primaryWeight}, #{profitLossWeight}, #{emptyLoadTime}, #{fullLoadTime}, #{outputUnit}, #{inputUnit}, #{weigher}, #{modifyTime}, #{creatorId}, #{poundID}, #{printTime}, #{printed}, #{creatTime})")
    void insert(PoundBillModel poundBillModel);

    @Update("update poundbill set IOType=#{IOType}, coalType=#{coalType}, plateNumber=#{plateNumber}, grossWeight=#{grossWeight}, tareWeight=#{tareWeight}, netWeight=#{netWeight}, primaryWeight=#{primaryWeight}, profitLossWeight=#{profitLossWeight}, emptyLoadTime=#{emptyLoadTime}, fullLoadTime=#{fullLoadTime}, outputUnit=#{outputUnit}, inputUnit=#{inputUnit}, weigher=#{weigher}, printTime=#{printTime}, printed=#{printed}, poundID=#{poundID}, modifyTime=now(), printTime=#{printTime} where id=#{id}")
    void update(PoundBillModel poundBillModel);

    @Select("select * from poundbill where id = #{id}")
    PoundBillModel selectById(int id);

    @Delete("delete from poundbill where id = #{id}")
    void deleteById(int id);

    @Select("select * from poundbill")
    PoundBillModel[] selectAll();

    @Select("select count(*) from poundbill")
    int count();
}