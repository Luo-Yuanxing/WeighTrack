package io.github.weightrack.utils;

import io.github.weightrack.module.PoundBillModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Comparator;

@Slf4j
public class ExcelUtil {

    public static int appendExcel(PoundBillModel[] poundBillModels, String IOType, File file) {
        int modifyLength = 0;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            Sheet sheet;
            if (IOType.equals("1")) {
                sheet = workbook.getSheet("入库明细");
            } else {
                sheet = workbook.getSheet("出库明细");
            }
            int length = sheet.getLastRowNum();
            log.info("sheet长度：{}", length);

            int i;
            for (i = 3; ; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    break;
                }
                if (row.getCell(0) != null) {
                    if (row.getCell(0).getCellType().equals(CellType.NUMERIC)) {
                        int excelDate = (int) row.getCell(0).getNumericCellValue();
                        LocalDate baseDate = LocalDate.of(1900, 1, 1);
                        long daysToAdd = (long) excelDate - 2;
                        LocalDate date = baseDate.plusDays(daysToAdd);
                        if (date.equals(LocalDate.now())) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            log.info("当前待插入行索引： {}", i);

            Date date = java.sql.Date.valueOf(LocalDate.now());
            // poundBillModels排序
            Arrays.sort(poundBillModels, Comparator.comparingLong(poundBillModel ->
                    poundBillModel.getCreatTime().toInstant(ZoneOffset.UTC).getEpochSecond()));
            for (PoundBillModel poundBillModel : poundBillModels) {
                modifyLength++;
                Row currentRow = sheet.getRow(i);
                if (currentRow == null) {
                    currentRow = sheet.createRow(i);
                }
                // 设置日期
                Cell DateCell = currentRow.createCell(0, CellType.NUMERIC);
                DateCell.setCellValue(date);
                CellStyle cellStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
                DateCell.setCellStyle(cellStyle);

                // 设置榜单编号
                Cell poundIdCell = currentRow.createCell(1, CellType.STRING);
                poundIdCell.setCellValue(poundBillModel.getPoundID());

                // 设置煤种
                Cell coalTypeCell = currentRow.createCell(2, CellType.STRING);
                coalTypeCell.setCellValue(poundBillModel.getCoalType());

                // 设置车牌号
                Cell plateNumberCell = currentRow.createCell(3, CellType.STRING);
                plateNumberCell.setCellValue(poundBillModel.getPlateNumber());

                // 设置毛重
                Cell grossWeightCell = currentRow.createCell(4, CellType.NUMERIC);
                grossWeightCell.setCellValue(poundBillModel.getGrossWeight());

                // 设置皮重
                Cell tareWeightCell = currentRow.createCell(5, CellType.NUMERIC);
                tareWeightCell.setCellValue(poundBillModel.getTareWeight());

                // 设置净重
                Cell netWeightCell = currentRow.createCell(6, CellType.NUMERIC);
                netWeightCell.setCellValue(poundBillModel.getNetWeight());

                if (IOType.equals("1")) {

                    // 设置原发
                    Cell primaryCell = currentRow.createCell(7, CellType.NUMERIC);
                    primaryCell.setCellValue(poundBillModel.getPrimaryWeight());

                    // 设置盈亏
                    Cell profitLossCell = currentRow.createCell(8, CellType.NUMERIC);
                    profitLossCell.setCellValue(poundBillModel.getProfitLossWeight());

                    // 设置打印时间
                    Cell printTimeCell = currentRow.createCell(9, CellType.NUMERIC);
                    printTimeCell.setCellValue(poundBillModel.getCreatTime());

                    // 设置发货单位
                    Cell deliveryUnitCell = currentRow.createCell(10, CellType.STRING);
                    deliveryUnitCell.setCellValue(poundBillModel.getOutputUnit());

                    // 设置收货单位
                    Cell receivingUnitCell = currentRow.createCell(11, CellType.STRING);
                    receivingUnitCell.setCellValue(poundBillModel.getInputUnit());

                    // 设置司磅员
                    Cell weighmanCell = currentRow.createCell(12, CellType.STRING);
                    weighmanCell.setCellValue(poundBillModel.getWeigher());

                } else {
                    // 设置打印时间
                    Cell printTimeCell = currentRow.createCell(7, CellType.NUMERIC);
                    printTimeCell.setCellValue(poundBillModel.getCreatTime());

                    // 设置发货单位
                    Cell deliveryUnitCell = currentRow.createCell(8, CellType.STRING);
                    deliveryUnitCell.setCellValue(poundBillModel.getOutputUnit());

                    // 设置收货单位
                    Cell receivingUnitCell = currentRow.createCell(9, CellType.STRING);
                    receivingUnitCell.setCellValue(poundBillModel.getInputUnit());

                    // 设置司磅员
                    Cell weighmanCell = currentRow.createCell(10, CellType.STRING);
                    weighmanCell.setCellValue(poundBillModel.getWeigher());
                }

                i++;
            }

            // 保存文件
            fileInputStream.close();
            workbook.write(fileOutputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return modifyLength;
    }
}
