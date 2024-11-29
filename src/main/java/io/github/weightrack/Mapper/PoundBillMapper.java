package io.github.weightrack.Mapper;


import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PoundBillMapper {

    @Insert("INSERT INTO poundbill (IOType, coalType, plateNumber, grossWeight, tareWeight, netWeight, primaryWeight, profitLossWeight, emptyLoadTime, fullLoadTime, outputUnit, inputUnit, weigher, creatTime, modifyTime, creatorId) " +
            "VALUES (#{IOType}, #{coalType}, #{plateNumber}, #{grossWeight}, #{tareWeight}, #{netWeight}, #{primaryWeight}, #{profitLossWeight}, #{emptyLoadTime}, #{fullLoadTime}, #{outputUnit}, #{inputUnit}, #{weigher}, #{creatTime}, #{modifyTime}, #{creatorId})")
    void insert(PoundBillModel poundBillModel);

    @Update("update poundbill set IOType=#{IOType}, coalType=#{coalType}, plateNumber=#{plateNumber}, grossWeight=#{grossWeight}, tareWeight=#{tareWeight}, netWeight=#{netWeight}, primaryWeight=#{primaryWeight}, profitLossWeight=#{profitLossWeight}, emptyLoadTime=#{emptyLoadTime}, fullLoadTime=#{fullLoadTime}, outputUnit=#{outputUnit}, inputUnit=#{inputUnit}, weigher=#{weigher}, printTime=#{printTime}, isPrinted=#{isPrinted}, poundID=#{poundID}, modifyTime=now() where id=#{id}")
    void updateById(PoundBillModel poundBillModel);

    @Select("select * from poundbill where id = #{id}")
    PoundBillModel selectById(int id);

    @Delete("delete from poundbill where id = #{id}")
    void deleteById(int id);
}