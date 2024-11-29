package io.github.weightrack.Service;

import io.github.weightrack.exception.InvalidForm;

import io.github.weightrack.Mapper.PoundBillMapper;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class PoundBillService {

    @Autowired
    PoundBillMapper poundBillMapper;

    public void insertPoundBill(PoundBillModel poundBillModel) {
        // 设置净重
        if (poundBillModel.getGrossWeight() != 0 && poundBillModel.getTareWeight() != 0) {
            poundBillModel.setNetWeight(poundBillModel.getGrossWeight() - poundBillModel.getTareWeight());
        }
        // 设置盈亏
        if (poundBillModel.getPrimaryWeight() != 0 && poundBillModel.getNetWeight() != 0) {
            poundBillModel.setProfitLossWeight(poundBillModel.getNetWeight() - poundBillModel.getPrimaryWeight());
        }
        poundBillModel.setCreatTime(LocalDateTime.now());
        poundBillModel.setModifyTime(LocalDateTime.now());
        poundBillMapper.insert(poundBillModel);
    }

    public void updateById(PoundBillModel poundBillModel, int id) {
        // 设置净重
        if (poundBillModel.getGrossWeight() != 0 && poundBillModel.getTareWeight() != 0) {
            poundBillModel.setNetWeight(poundBillModel.getGrossWeight() - poundBillModel.getTareWeight());
        }
        // 设置盈亏
        if (poundBillModel.getPrimaryWeight() != 0 && poundBillModel.getNetWeight() != 0) {
            poundBillModel.setProfitLossWeight(poundBillModel.getNetWeight() - poundBillModel.getPrimaryWeight());
        }

        poundBillModel.setId(id);
        poundBillModel.setModifyTime(LocalDateTime.now());
        poundBillMapper.updateById(poundBillModel);
    }

    public PoundBillModel selectById(int id) {
        return poundBillMapper.selectById(id);
    }

    // 数据校验方法

    public void deleteById(int id) {
        poundBillMapper.deleteById(id);
    }
}
