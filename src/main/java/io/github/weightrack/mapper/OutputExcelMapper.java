package io.github.weightrack.mapper;

import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OutputExcelMapper {

    @Select("select * from poundbill where removed = 0 and IOType = #{IOType} and printed is true and date(creatTime) = date(now()) order by creatTime desc")
    PoundBillModel[] getTodayPrintedPoundBillModels(String IOType);
}
