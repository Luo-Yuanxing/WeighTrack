package io.github.weightrack.Service;

import io.github.weightrack.exception.InvalidForm;
import io.github.weightrack.module.enums.CoalType;

import io.github.weightrack.Mapper.PoundBillMapper;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PoundBillService {

    @Autowired
    PoundBillMapper poundBillMapper;

    public void insertPoundBill(PoundBillModel poundBillModel) throws InvalidForm {

        // 煤种校验
        if (!CoalType.isValidCoalType(poundBillModel.getCoalType())) {
            throw new InvalidForm("无效的煤种描述");
        }

        // 车牌号校验
        String plateNumber = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})";
        Pattern pattern = Pattern.compile(plateNumber);
        Matcher matcher = pattern.matcher(poundBillModel.getPlateNumber());
        boolean plateNumberFound = false;
        while (matcher.find()) {
            System.out.println("Found: " + matcher.group());
            plateNumberFound = true;
        }
        if (!plateNumberFound) {
            throw new InvalidForm("无效的车牌号");
        }


        poundBillMapper.insert(poundBillModel);
    }
}
