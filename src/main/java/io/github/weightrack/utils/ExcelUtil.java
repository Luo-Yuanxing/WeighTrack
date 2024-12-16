package io.github.weightrack.utils;

import io.github.weightrack.module.PoundBillModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class ExcelUtil {

    public static int appendExcel(PoundBillModel[] poundBillModels, String IOType, File file) {
        int modifyLength = 0;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(file);) {

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
            List<PoundBillModel> poundBillModelList = new ArrayList<>(Arrays.asList(poundBillModels));
            poundBillModelList = poundBillModelList.reversed();
            for (PoundBillModel poundBillModel : poundBillModelList) {
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
                    printTimeCell.setCellValue(poundBillModel.getPrintTime());

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
                    printTimeCell.setCellValue(poundBillModel.getPrintTime());

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

    public static List<PoundBillModel> insertPoundBillByExcel() throws Exception {
        //  NUMERIC	 STRING	 STRING	 STRING	 NUMERIC(毛重)	 NUMERIC	 FORMULA	 NUMERIC	 FORMULA	 NUMERIC	 STRING	 STRING	 STRING	 BLANK
        List<PoundBillModel> poundBillModels = new ArrayList<>();

        FileInputStream fileIn = new FileInputStream("D:\\零时文件\\WeChat Files\\luoyaping2012\\FileStorage\\File\\2024-12\\每日过磅明细-1.xlsx");
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
                    poundID = "R" + (int) poundIDCell.getNumericCellValue();
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
                } catch (Exception ignored) {
                }
                try {
                    poundBillModel.setTareWeight((float) row.getCell(5).getNumericCellValue());
                    tareFlag = true;
                } catch (Exception ignored) {
                }
                try {
                    poundBillModel.setPrimaryWeight((float) row.getCell(7).getNumericCellValue());
                    primaryFlag = true;
                } catch (Exception ignored) {
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

        FileInputStream fileIn = new FileInputStream("D:\\零时文件\\WeChat Files\\luoyaping2012\\FileStorage\\File\\2024-12\\每日过磅明细-1.xlsx");
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
