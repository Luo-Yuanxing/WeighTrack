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

public class ExcelUtilTest {

    public static List<PoundBillModel> insertPoundBillByExcel_IN() throws Exception {
        //  NUMERIC	 STRING	 STRING	 STRING	 NUMERIC(毛重)	 NUMERIC	 FORMULA	 NUMERIC	 FORMULA	 NUMERIC	 STRING	 STRING	 STRING	 BLANK
        List<PoundBillModel> poundBillModels = new ArrayList<>();

        FileInputStream fileIn = new FileInputStream("C:\\Users\\lyp\\IdeaProjects\\WeighTrack\\过磅明细.xlsx");
        Workbook workbook = new XSSFWorkbook(fileIn);
        Sheet sheet = workbook.getSheet("入库明细");
        int length = sheet.getLastRowNum();
        System.out.println("sheet长度：" + length);
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            PoundBillModel poundBillModel = new PoundBillModel();
            if (row.getCell(0) == null) {
                continue;
            }
            if (row.getCell(0).getCellType().equals(CellType.NUMERIC)) {
                int excelDate = (int) row.getCell(0).getNumericCellValue();
                LocalDate baseDate = LocalDate.of(1900, 1, 1);
                long daysToAdd = (long) excelDate - 2;
                LocalDate date = baseDate.plusDays(daysToAdd);

                double printTime;
                try {
                    printTime = row.getCell(9).getNumericCellValue();
                } catch (Exception e) {
                    continue;
                }
                LocalDateTime localDateTime = getLocalDateTime(printTime, date);
                poundBillModel.setCreatTime(localDateTime);
                poundBillModel.setPrintTime(localDateTime);
                poundBillModel.setModifyTime(localDateTime);

                String poundID;
                Cell poundIDCell = row.getCell(1);
                if (poundIDCell.getCellType().equals(CellType.NUMERIC)) {
                    System.out.println("poundID:" + poundIDCell.getNumericCellValue());
                    poundID = "R" + String.valueOf((int) poundIDCell.getNumericCellValue());
                } else if (poundIDCell.getCellType().equals(CellType.STRING)) {
                    poundID = poundIDCell.getStringCellValue();
                } else {
                    poundID = "";
                }
                poundBillModel.setPoundID(poundID);

                poundBillModel.setCoalType(row.getCell(2).getStringCellValue());
                poundBillModel.setPlateNumber(row.getCell(3).getStringCellValue());

                boolean grossFlag = false;
                boolean tareFlag = false;
                boolean primaryFlag = false;

                try {
                    poundBillModel.setGrossWeight((float) row.getCell(4).getNumericCellValue());
                    grossFlag = true;
                } catch (Exception e) {

                }
                try {
                    poundBillModel.setTareWeight((float) row.getCell(5).getNumericCellValue());
                    tareFlag = true;
                } catch (Exception e) {

                }
                try {
                    poundBillModel.setPrimaryWeight((float) row.getCell(7).getNumericCellValue());
                    primaryFlag = true;
                } catch (Exception e) {

                }

                if (grossFlag && tareFlag) {
                    poundBillModel.setNetWeight(poundBillModel.getGrossWeight() - poundBillModel.getTareWeight());

                    if (primaryFlag) {
                        poundBillModel.setProfitLossWeight(poundBillModel.getNetWeight() - poundBillModel.getPrimaryWeight());
                    }
                }
                if (row.getCell(10) != null) {
                    poundBillModel.setOutputUnit(row.getCell(10).getStringCellValue());
                }
                if (row.getCell(11) != null) {
                    poundBillModel.setInputUnit(row.getCell(11).getStringCellValue());
                }
                if (row.getCell(12) != null) {
                    poundBillModel.setWeigher(row.getCell(12).getStringCellValue());
                }

                poundBillModel.setIOType("1");
                poundBillModel.setPrinted(true);
            }
            System.out.println(poundBillModel);
            poundBillModels.add(poundBillModel);
        }

        workbook.close();
        fileIn.close();
        return poundBillModels;
    }
    public static List<PoundBillModel> insertPoundBillByExcel_OUT() throws Exception {
        //  NUMERIC	 STRING	 STRING	 STRING	 NUMERIC(毛重)	 NUMERIC	 FORMULA	 NUMERIC	 FORMULA	 NUMERIC	 STRING	 STRING	 STRING	 BLANK
        List<PoundBillModel> poundBillModels = new ArrayList<>();

        FileInputStream fileIn = new FileInputStream("C:\\Users\\lyp\\IdeaProjects\\WeighTrack\\过磅明细.xlsx");
        Workbook workbook = new XSSFWorkbook(fileIn);
        Sheet sheet = workbook.getSheet("出库明细");
        int length = sheet.getLastRowNum();
        System.out.println("sheet长度：" + length);
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            PoundBillModel poundBillModel = new PoundBillModel();
            if (row.getCell(0) == null) {
                continue;
            }
            if (row.getCell(0).getCellType().equals(CellType.NUMERIC)) {
                int excelDate = (int) row.getCell(0).getNumericCellValue();
                LocalDate baseDate = LocalDate.of(1900, 1, 1);
                long daysToAdd = (long) excelDate - 2;
                LocalDate date = baseDate.plusDays(daysToAdd);

                double printTime;
                try {
                    printTime = row.getCell(7).getNumericCellValue();
                } catch (Exception e) {
                    continue;
                }
                LocalDateTime localDateTime = getLocalDateTime(printTime, date);
                poundBillModel.setCreatTime(localDateTime);
                poundBillModel.setPrintTime(localDateTime);
                poundBillModel.setModifyTime(localDateTime);

                String poundID;
                Cell poundIDCell = row.getCell(1);
                if (poundIDCell.getCellType().equals(CellType.NUMERIC)) {
                    System.out.println("poundID:" + poundIDCell.getNumericCellValue());
                    poundID = "C" + (int) poundIDCell.getNumericCellValue();
                } else if (poundIDCell.getCellType().equals(CellType.STRING)) {
                    poundID = poundIDCell.getStringCellValue();
                } else {
                    poundID = "";
                }
                poundBillModel.setPoundID(poundID);

                poundBillModel.setCoalType(row.getCell(2).getStringCellValue());
                poundBillModel.setPlateNumber(row.getCell(3).getStringCellValue());

                boolean grossFlag = false;
                boolean tareFlag = false;
                boolean primaryFlag = false;

                try {
                    poundBillModel.setGrossWeight((float) row.getCell(4).getNumericCellValue());
                    grossFlag = true;
                } catch (Exception e) {

                }
                try {
                    poundBillModel.setTareWeight((float) row.getCell(5).getNumericCellValue());
                    tareFlag = true;
                } catch (Exception e) {

                }

                if (grossFlag && tareFlag) {
                    poundBillModel.setNetWeight(poundBillModel.getGrossWeight() - poundBillModel.getTareWeight());
                }
                if (row.getCell(8) != null) {
                    poundBillModel.setOutputUnit(row.getCell(8).getStringCellValue());
                }
                if (row.getCell(9) != null) {
                    poundBillModel.setInputUnit(row.getCell(9).getStringCellValue());
                }
                if (row.getCell(10) != null) {
                    poundBillModel.setWeigher(row.getCell(10).getStringCellValue());
                }

                poundBillModel.setIOType("0");
                poundBillModel.setPrinted(true);
            }
            System.out.println(poundBillModel);
            poundBillModels.add(poundBillModel);
        }

        workbook.close();
        fileIn.close();
        return poundBillModels;
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
