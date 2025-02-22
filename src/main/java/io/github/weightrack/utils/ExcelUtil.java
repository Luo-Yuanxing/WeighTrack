package io.github.weightrack.utils;

import io.github.weightrack.constants.AppConstants;
import io.github.weightrack.exception.ExcelException;
import io.github.weightrack.module.PoundBillModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Comparator;

@Slf4j
public class ExcelUtil {

    public static void setColumnName(Sheet sheet, String IOType) {
        // 判断IOType并选择对应的列名数组
        String[] columnNames = "0".equals(IOType) ? AppConstants.EXCEL_FILE_OUT_COL : AppConstants.EXCEL_FILE_IN_COL;

        // 在第一行设置列名
        Row row = sheet.createRow(0);  // 创建第一行，表头行

        // 遍历列名数组，设置每一列的标题
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);  // 设置列名
        }
    }

    public static int appendExcel(PoundBillModel[] poundBillModels, String IOType, String filePath) throws ExcelException {
        int modifyLength = 0;

        try (Workbook workbook = new XSSFWorkbook()) {

            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = numberOfSheets - 1; i >= 0; i--) {
                workbook.removeSheetAt(i); // 删除工作表
            }

            // 新建一个默认工作表
            workbook.createSheet("Sheet1");
            Sheet sheet = workbook.getSheet("Sheet1");

            if (sheet == null) {
                log.warn("sheet is null");
                throw new ExcelException("sheet is null");
            }

            Date date = java.sql.Date.valueOf(LocalDate.now());
            // poundBillModels排序
            Arrays.sort(poundBillModels, Comparator.comparingLong(poundBillModel ->
                    poundBillModel.getCreatTime().toInstant(ZoneOffset.UTC).getEpochSecond()));

            // 设置列名
            setColumnName(sheet, IOType);
            modifyLength++;

            for (PoundBillModel poundBillModel : poundBillModels) {
                Row currentRow = sheet.getRow(modifyLength);
                if (currentRow == null) {
                    currentRow = sheet.createRow(modifyLength);
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

                if (!IOType.equals("0")) {

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

                modifyLength++;
            }

            // 创建一个输出流来保存文件
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                // 将工作簿内容写入文件
                workbook.write(fileOut);
                log.debug("Excel文件已创建并保存！ {}", filePath);
            } catch (IOException e) {
                log.debug("文件保存失败: {}", filePath);
            } finally {
                // 关闭工作簿
                workbook.close();
            }

        } catch (IOException e) {
            throw new RuntimeException("文件处理失败", e);
        }

        return modifyLength;
    }
}
