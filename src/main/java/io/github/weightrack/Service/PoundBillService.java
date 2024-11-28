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
        validate(poundBillModel);
        poundBillModel.setCreatTime(LocalDateTime.now());
        poundBillModel.setModifyTime(LocalDateTime.now());
        poundBillMapper.insert(poundBillModel);
    }

    public void updateById(PoundBillModel poundBillModel, int id) {
        validate(poundBillModel);
        poundBillModel.setId(id);
        poundBillModel.setModifyTime(LocalDateTime.now());
        poundBillMapper.updateById(poundBillModel);
    }

    public PoundBillModel selectById(int id) {
        return poundBillMapper.selectById(id);
    }

    // 数据校验方法
    private void validate(PoundBillModel poundBillModel) {
        // 煤种校验
        if (!Set.of("4-1水洗煤", "4-2水洗煤", "4-2原煤", "5-1水洗煤", "5-1原煤", ">6-1水洗煤", "6-1原煤")
                .contains(poundBillModel.getCoalType())) {
            poundBillModel.setCoalType("");
        }

        // 车牌号校验
        String plateNumber = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})";
        Pattern pattern = Pattern.compile(plateNumber);
        Matcher matcher = pattern.matcher(poundBillModel.getPlateNumber());
        boolean plateNumberFound = false;
        while (matcher.find()) {
            plateNumberFound = true;
        }
        if (!plateNumberFound) {
            poundBillModel.setPlateNumber("");
        }

        // 毛重校验
        if (!poundBillModel.getGrossWeightString().isEmpty()) {
            try {
                poundBillModel.setGrossWeight(Float.parseFloat(poundBillModel.getGrossWeightString()));
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            }
        }

        // 皮重校验
        if (!poundBillModel.getTareWeightString().isEmpty()) {
            try {
                poundBillModel.setTareWeight(Float.parseFloat(poundBillModel.getTareWeightString()));
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            }
        }

        // 原发校验
        if (!poundBillModel.getPrimaryWeightString().isEmpty()) {
            try {
                poundBillModel.setPrimaryWeight(Float.parseFloat(poundBillModel.getPrimaryWeightString()));
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            }
        }

        // 设置净重
        if (poundBillModel.getGrossWeight() != 0 && poundBillModel.getTareWeight() != 0) {
            poundBillModel.setNetWeight(poundBillModel.getGrossWeight() - poundBillModel.getTareWeight());
        }

        // 设置盈亏
        if (poundBillModel.getPrimaryWeight() != 0 && poundBillModel.getNetWeight() != 0) {
            poundBillModel.setProfitLossWeight(poundBillModel.getNetWeight() - poundBillModel.getPrimaryWeight());
        }

        // 格式化时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 过空时间赋值
        if (!poundBillModel.getEmptyLoadTimeString().isEmpty()) {
            LocalDateTime parsedEmptyLoadTime = LocalDateTime.parse(poundBillModel.getEmptyLoadTimeString(), formatter);
            poundBillModel.setEmptyLoadTime(parsedEmptyLoadTime);
        }

        // 过重时间赋值
        if (!poundBillModel.getFullLoadTimeString().isEmpty()) {
            LocalDateTime parsedFullLoadTime = LocalDateTime.parse(poundBillModel.getFullLoadTimeString(), formatter);
            poundBillModel.setFullLoadTime(parsedFullLoadTime);
        }
        System.out.println(poundBillModel);
    }

}
