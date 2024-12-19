package io.github.weightrack.mapper;

import io.github.weightrack.module.PoundBillModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RecycleBinMapper {

    @Select("select * from poundbill where removed = 1 LIMIT #{offset}, #{limit}")
    PoundBillModel[] getRemoved(int offset, int limit);

    @Select("select count(*) from poundbill where removed = 1")
    int getPageNumber();

    @Update("update poundbill set removed = 0 where id = #{id}")
    int recover(int id);

    @Delete("delete from poundbill where id = #{id}")
    int delete(int id);

    @Delete("delete from poundbill where removed = 1")
    void deleteAll();

    @Select("SELECT * FROM poundbill WHERE removed = 1 and (#{plateNumber} = '' OR plateNumber LIKE CONCAT('%', #{plateNumber}, '%')) and (#{poundId} = '' OR poundId LIKE CONCAT('%', #{poundId}, '%')) ORDER BY creatTime DESC")
    PoundBillModel[] showListIsRemoved(String plateNumber, String poundId);
}
