package io.github.weightrack.Mapper;


import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PoundBillMapper {

    @Insert("INSERT INTO poundbill (coalType, plateNumber, grossWeight, tareWeight, netWeight, primaryWeight, profitLossWeight, emptyLoadTime, fullLoadTime, outputUnit, inputUnit, weigher) " +
            "VALUES (#{coalType}, #{plateNumber}, #{grossWeight}, #{tareWeight}, #{netWeight}, #{primaryWeight}, #{profitLossWeight}, #{emptyLoadTime}, #{fullLoadTime}, #{outputUnit}, #{inputUnit}, #{weigher})")
    void insert(PoundBillModel poundBillModel);
}