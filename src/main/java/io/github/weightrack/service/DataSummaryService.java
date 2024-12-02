package io.github.weightrack.service;

import io.github.weightrack.mapper.CoalTypeMapper;
import io.github.weightrack.mapper.DataSummaryMapper;
import io.github.weightrack.module.CoalType;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.module.SummaryTable;
import io.github.weightrack.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DataSummaryService {

    @Autowired
    CoalTypeMapper coalTypeMapper;

    @Autowired
    DataSummaryMapper dataSummaryMapper;

    public SummaryTable getSummary(String IOType, LocalDateTime startTime, LocalDateTime endTime) {

        System.out.println(IOType);
        System.out.println(startTime);
        System.out.println(endTime);

        SummaryTable summaryTable = new SummaryTable();
        LinkedHashMap<String, LinkedHashMap<String, String>> summary = new LinkedHashMap<>();
        PoundBillModel[] poundBillModels;

        // 获取煤种信息
        CoalType[] coalTypes = coalTypeMapper.getCoalType();
        List<String> columnNames = new ArrayList<>();

        columnNames.add("日期");

        for (CoalType coalType : coalTypes) {
            columnNames.add(coalType.getName());
        }

        // sql
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (IOType.equals("in")) {
            summaryTable.setTableName("入库报表");
            columnNames.add("来源");
            poundBillModels = dataSummaryMapper.getData("1", startTime.format(formatter), endTime.format(formatter));
        } else if (IOType.equals("out")) {
            summaryTable.setTableName("出库报表");
            columnNames.add("运往地");
            poundBillModels = dataSummaryMapper.getData("0", startTime.format(formatter), endTime.format(formatter));
        } else {
            throw new RuntimeException("不存在的请求");
        }
        summaryTable.setColumnNames(columnNames);

        for (PoundBillModel poundBillModel : poundBillModels) {
            String creatTime = poundBillModel.getCreatTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            summary.putIfAbsent(creatTime, new LinkedHashMap<>());
            summary.get(creatTime).merge(poundBillModel.getCoalType(), String.valueOf(poundBillModel.getNetWeight()), (o, n) -> String.valueOf(Double.parseDouble(o) + Double.parseDouble(n)));
            summary.get(creatTime).merge("unit", poundBillModel.getOutputUnit(), (o, n) -> o + "," + n);
        }
        summary.forEach((key, value) -> {
            List<String> row = new ArrayList<>();
            // 日期
            row.add(key);
            for (CoalType coalType : coalTypes) {
                row.add(value.getOrDefault(coalType.getName(), "0"));
            }
            row.add(value.getOrDefault("unit", ""));
            System.out.println(value);
            summaryTable.addRow(row);
        });
        System.out.println(summaryTable);
        return summaryTable;
    }

    public SummaryTable getSummaryToday(String IOType) {
        SummaryTable summaryTable = new SummaryTable();
        LinkedHashMap<String, LinkedHashMap<String, Double>> summary = new LinkedHashMap<>();
        PoundBillModel[] poundBillModels;
        // 生成本日报表
        summaryTable.setColumnNames(Arrays.asList("煤种", "总毛重", "总皮重", "总净重", "总盈亏", "车次"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (IOType.equals("in")) {
            summaryTable.setTableName("入库报表");
            poundBillModels = dataSummaryMapper.getData("1", DateUtil.getTodayStartTime().format(formatter), DateUtil.getTodayEndTime().format(formatter));
        } else if (IOType.equals("out")) {
            summaryTable.setTableName("出库报表");
            poundBillModels = dataSummaryMapper.getData("0", DateUtil.getTodayStartTime().format(formatter), DateUtil.getTodayEndTime().format(formatter));
        } else {
            throw new RuntimeException("不存在的请求");
        }
        for (PoundBillModel poundBillModel : poundBillModels) {
            summary.putIfAbsent(poundBillModel.getCoalType(), new LinkedHashMap<>());
            summary.get(poundBillModel.getCoalType()).merge("总毛重", (double) poundBillModel.getGrossWeight(), Double::sum);
            summary.get(poundBillModel.getCoalType()).merge("总皮重", (double) poundBillModel.getTareWeight(), Double::sum);
            summary.get(poundBillModel.getCoalType()).merge("总净重", (double) poundBillModel.getNetWeight(), Double::sum);
            summary.get(poundBillModel.getCoalType()).merge("总盈亏", (double) poundBillModel.getProfitLossWeight(), Double::sum);
            summary.get(poundBillModel.getCoalType()).merge("车次", 1.0, Double::sum);
        }
        summary.forEach((key, value) -> {
            List<String> row = new ArrayList<>();
            row.add(key);
            value.forEach((columnName, total) -> row.add(total.toString()));
            summaryTable.addRow(row);
            System.out.println(row);
        });
        System.out.println(summaryTable);
        return summaryTable;
    }
}
