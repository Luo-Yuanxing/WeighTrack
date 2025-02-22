package io.github.weightrack.service;

import io.github.weightrack.exception.ExcelException;
import io.github.weightrack.mapper.OutputExcelMapper;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class OutputExcelService {
    @Autowired
    OutputExcelMapper outputExcelMapper;

    public String getTodayPrintedPoundBillModels(String IOType) {
        String rootPath = System.getProperty("user.dir");

        // 获取 IOType 对应的文件名
        String IOTypeName = switch (IOType) {
            case "0" -> "出库记录";
            case "1" -> "入库记录";
            case "2" -> "返仓记录";
            case "3" -> "内部周转记录";
            default -> throw new IllegalArgumentException("IOType must be 0, 1, 2 or 3");
        };

        // 获取当前日期，格式化为 "yyyy-MM-dd"
        String today = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());

        // 拼接路径
        String filePath = Paths.get(rootPath, "excel", today, IOTypeName + ".xlsx").toString();

        // 创建文件对象
        File excelFile = new File(filePath);

        // 创建父目录（如果不存在的话）
        File parentDir = excelFile.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                log.debug("目录已创建: {}", parentDir.getAbsolutePath());
            } else {
                log.debug("目录创建失败: {}", parentDir.getAbsolutePath());
            }
        }

        // 确保文件创建完成
        if (excelFile.delete()) {
            log.debug("文件已删除: {}", excelFile.getAbsolutePath());
        } else {
            log.debug("文件删除失败: {}", excelFile.getAbsolutePath());
        }

        if (excelFile.exists()) {
            log.debug("文件仍然存在: {}", excelFile.getAbsolutePath());
            throw new RuntimeException("无法删除的文件: " + excelFile.getAbsolutePath());
        }

        PoundBillModel[] poundBillModels = outputExcelMapper.getTodayPrintedPoundBillModels(IOType);
        int modifyLength;
        try {
            modifyLength = ExcelUtil.appendExcel(poundBillModels, IOType, filePath);
        } catch (ExcelException e) {
            return "error: " + e.getMessage();
        }

        log.debug("sheet: {}， 插入了{}行记录", IOTypeName, modifyLength);
        return "ok";
    }
}
