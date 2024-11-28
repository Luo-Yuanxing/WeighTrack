package io.github.weightrack.Mapper;

import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.jmx.export.annotation.ManagedOperation;

@Mapper
public interface ShowListMapper {

    @Select("select * from poundbill order by creatTime desc")
    PoundBillModel[] showList();
}
