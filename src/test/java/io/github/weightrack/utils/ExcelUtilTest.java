package io.github.weightrack.utils;

import io.github.weightrack.module.PoundBillModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ExcelUtilTest {
    /**
     *
     * @param sheetName         sheet名称
     * @param startRow          起始行
     * @param printTimeColumn   时间列索引
     * @param idPrefix          poundID前缀
     * @param ioType            IOType
     * @param outputUnitCol     输出单元列
     * @param inputUnitCol      输入单元列
     * @param weigherCol        司磅员列
     * @param hasPrimaryWeight  是否有primaryWeight
     * @param filePath          excel文件路径
     * @return                  List<PoundBillModel>
     * @throws Exception        未知异常
     */
    public static List<PoundBillModel> insertPoundBillByExcel(String sheetName, int startRow, int printTimeColumn, String idPrefix, String ioType, int outputUnitCol, int inputUnitCol, int weigherCol, boolean hasPrimaryWeight, String filePath) throws Exception {
        List<PoundBillModel> poundBillModels = new ArrayList<>();

        FileInputStream fileIn = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileIn);
        Sheet sheet = workbook.getSheet(sheetName);
        System.out.println("sheet长度：" + sheet.getLastRowNum());
        for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            PoundBillModel poundBillModel = new PoundBillModel();
            if (row == null || row.getCell(0) == null) {
                continue;
            }
            if (row.getCell(0).getCellType().equals(CellType.NUMERIC)) {

                int excelDate = (int) row.getCell(0).getNumericCellValue();
                LocalDate baseDate = LocalDate.of(1900, 1, 1);
                LocalDate date = baseDate.plusDays((long) excelDate - 2);

                double printTime;
                try {
                    printTime = row.getCell(printTimeColumn).getNumericCellValue();
                } catch (NullPointerException e) {
                    printTime = 0;
                }
                LocalDateTime localDateTime = getLocalDateTime(printTime, date);
                poundBillModel.setCreatTime(localDateTime);
                poundBillModel.setPrintTime(localDateTime);
                poundBillModel.setModifyTime(localDateTime);

                String poundID;
                Cell poundIDCell = row.getCell(1);
                if (poundIDCell == null) {
                    poundIDCell = row.createCell(1);
                    poundIDCell.setCellValue("");
                }
                if (poundIDCell.getCellType().equals(CellType.NUMERIC)) {
                    poundID = idPrefix + (int) poundIDCell.getNumericCellValue();
                } else if (poundIDCell.getCellType().equals(CellType.STRING)) {
                    poundID = poundIDCell.getStringCellValue();
                } else {
                    poundID = "";
                }
                poundBillModel.setPoundID(poundID);

                poundBillModel.setCoalType(getCellStringValue(row.getCell(2)));
                poundBillModel.setPlateNumber(getCellStringValue(row.getCell(3)));

                boolean grossFlag = setWeightFromCell(row.getCell(4), poundBillModel::setGrossWeight);
                boolean tareFlag = setWeightFromCell(row.getCell(5), poundBillModel::setTareWeight);
                boolean primaryFlag = false;

                if (hasPrimaryWeight) {
                    primaryFlag = setWeightFromCell(row.getCell(7), poundBillModel::setPrimaryWeight);
                }

                if (grossFlag && tareFlag) {
                    double netWeight = poundBillModel.getGrossWeight() - poundBillModel.getTareWeight();
                    poundBillModel.setNetWeight(netWeight);
                    if (hasPrimaryWeight && primaryFlag) {
                        poundBillModel.setProfitLossWeight(netWeight - poundBillModel.getPrimaryWeight());
                    }
                }

                setOptionalStringValue(row.getCell(outputUnitCol), poundBillModel::setOutputUnit);
                setOptionalStringValue(row.getCell(inputUnitCol), poundBillModel::setInputUnit);
                setOptionalStringValue(row.getCell(weigherCol), poundBillModel::setWeigher);

                poundBillModel.setIOType(ioType);
                poundBillModel.setPrinted(true);
            }
            poundBillModels.add(poundBillModel);
        }

        workbook.close();
        fileIn.close();
        return poundBillModels;
    }

    private static String getCellStringValue(Cell cell) {
        return cell == null ? "" : cell.getStringCellValue();
    }

    private static boolean setWeightFromCell(Cell cell, Consumer<Float> setter) {
        if (cell != null && cell.getCellType().equals(CellType.NUMERIC)) {
            setter.accept((float) cell.getNumericCellValue());
            return true;
        }
        return false;
    }

    private static void setOptionalStringValue(Cell cell, Consumer<String> setter) {
        if (cell != null) {
            setter.accept(cell.getStringCellValue());
        }
    }

    @NotNull
    private static LocalDateTime getLocalDateTime(double printTime, LocalDate date) {
        double timePart = printTime - Math.floor(printTime);  // 获取小数部分

        // 将小数部分转换为 LocalTime
        int hours = (int) (timePart * 24); // 计算小时
        int minutes = (int) ((timePart * 24 * 60) % 60); // 计算分钟
        int seconds = (int) ((timePart * 24 * 3600) % 60); // 计算秒

        // 创建 LocalTime 实例
        LocalTime localTime = LocalTime.of(hours, minutes, seconds);

        return LocalDateTime.of(date, localTime);
    }
}
