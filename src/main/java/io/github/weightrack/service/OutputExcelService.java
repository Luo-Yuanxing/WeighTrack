package io.github.weightrack.service;

import io.github.weightrack.exception.ExcelException;
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

        // 获取第一个匹配的 .xlsx 文件
        File rootDir = new File(rootPath);
        File excelFile = null;

        File[] files = rootDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".xlsx"));
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.getName().startsWith("new")) {
                    break;
                }
                excelFile = file;
            }
        } else {
            log.error("未找到符合条件的 .xlsx 文件");
        }

        // 使用 File excelFile 对象
        if (excelFile == null) {
            // 示例：打印文件路径
            log.error("Excel文件对象获取失败");
            return "error: 找不到默认位置的Excel文件";
        }

        if (excelFile.exists()) {
            log.info("打开文件 过磅明细.xlsx  at: {}", excelFile.getAbsolutePath());
        } else {
            log.error("过磅明细.xlsx 找不到  at:{}", excelFile.getAbsolutePath());
            return "error: 找不到文件： 过磅明细.xlsx";
        }
        int modifyLength;
        try {
            modifyLength = ExcelUtil.appendExcel(poundBillModels, IOType, excelFile);
        } catch (ExcelException e) {
            return "error: " + e.getMessage();
        }
        String IOTypeName = switch (IOType) {
            case "0" -> "出库记录";
            case "1" -> "入库记录";
            case "2" -> "返仓记录";
            case "3" -> "内部周转记录";
            default -> "未知记录";
        };
        log.info("sheet: {}， 插入了{}行记录", IOTypeName, modifyLength);
        return "ok";
    }
}
