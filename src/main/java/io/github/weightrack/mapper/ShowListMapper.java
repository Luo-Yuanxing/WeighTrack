package io.github.weightrack.mapper;

import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShowListMapper {

    @Select("select * from poundbill where (printed = true) and IOType = #{IOType} order by creatTime desc")
    PoundBillModel[] showList(String IOType);
    @Select("select * from poundbill where printed = true order by creatTime desc")
    PoundBillModel[] showListAll();

    @Select("select * from poundbill where IOType = #{IOType} and date(creatTime) = curdate() order by creatTime desc")
    PoundBillModel[] showTodayList(String IOType);
    @Select("select * from poundbill where date(creatTime) = curdate() order by creatTime desc")
    PoundBillModel[] showTodayListAll();

//    @Select("select * from poundbill")
//    PoundBillModel[] selectAll();
}
