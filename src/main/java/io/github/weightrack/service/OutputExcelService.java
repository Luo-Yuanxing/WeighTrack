package io.github.weightrack.service;

import io.github.weightrack.mapper.OutputExcelMapper;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class OutputExcelService {
    @Autowired
    OutputExcelMapper outputExcelMapper;

    public String getTodayPrintedPoundBillModels(String IOType) {
        PoundBillModel[] poundBillModels = outputExcelMapper.getTodayPrintedPoundBillModels(IOType);
        String rootPath = System.getProperty("user.dir");
        File excelFile = new File(rootPath, "每日过磅明细.xlsx");

        if (excelFile.exists()) {
            log.info("打开文件 每日过磅明细.xlsx  at: {}", excelFile.getAbsolutePath());
        } else {
            log.error("每日过磅明细.xlsx 找不到  at:{}", excelFile.getAbsolutePath());
            return "error: 找不到文件： 每日过磅明细.xlsx";
        }
        int modifyLength = ExcelUtil.appendExcel(poundBillModels, IOType, excelFile);
        log.info("sheet: {}， 插入了{}行记录", IOType.equals("1") ? "入库记录" : "出库记录", modifyLength);
        return "ok";
    }
}
