package io.github.weightrack.service;

import io.github.weightrack.dto.UpdateDTO;
import io.github.weightrack.mapper.PoundBillMapper;
import io.github.weightrack.mapper.ShowListMapper;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class PoundBillService {

    @Autowired
    PoundBillMapper poundBillMapper;
    @Autowired
    private ShowListMapper showListMapper;

    public static void parseString(PoundBillModel poundBillModel) {
        // grossWeightString
        try {
            poundBillModel.setGrossWeight(Float.parseFloat(poundBillModel.getGrossWeightString()));
        } catch (Exception e) {
//            System.out.println("净重输入不合法");
        }
        // tareWeightString
        try {
            poundBillModel.setTareWeight(Float.parseFloat(poundBillModel.getTareWeightString()));
        } catch (Exception e) {
//            System.out.println("皮重输入不合法");
        }
        // netWeightString
        try {
            poundBillModel.setNetWeight(Float.parseFloat(poundBillModel.getNetWeightString()));
        } catch (Exception e) {
//            System.out.println("毛重输入不合法");
        }
        // primaryWeightString
        try {
            poundBillModel.setPrimaryWeight(Float.parseFloat(poundBillModel.getPrimaryWeightString()));
        } catch (Exception e) {
//            System.out.println("原发输入不合法");
        }
        // ProfitLossWeightString
        try {
            poundBillModel.setProfitLossWeight(Float.parseFloat(poundBillModel.getProfitLossWeightString()));
        } catch (Exception e) {
//            System.out.println("盈亏输入不合法");
        }
        // emptyLoadTimeString
        try {
            poundBillModel.setEmptyLoadTime(LocalDateTime.parse(poundBillModel.getEmptyLoadTimeString(), ofPattern("yyyy-MM-dd HH:mm")));
        } catch (Exception e) {
//            System.out.println("空载时间输入不合法");
        }
        // fullLoadTimeString
        try {
            poundBillModel.setFullLoadTime(LocalDateTime.parse(poundBillModel.getFullLoadTimeString(), ofPattern("yyyy-MM-dd HH:mm")));
        } catch (Exception e) {
//            System.out.println("满载时间输入不合法");
        }
        // 设置净重
        if (poundBillModel.getGrossWeight() != 0 && poundBillModel.getTareWeight() != 0) {
            poundBillModel.setNetWeight(poundBillModel.getGrossWeight() - poundBillModel.getTareWeight());
        }
        // 设置盈亏
        if (poundBillModel.getPrimaryWeight() != 0 && poundBillModel.getNetWeight() != 0) {
            poundBillModel.setProfitLossWeight(poundBillModel.getNetWeight() - poundBillModel.getPrimaryWeight());
        }

        if (poundBillModel.getPrintTimeString() != null && !poundBillModel.getPrintTimeString().isEmpty()) {
            String printTimeString = poundBillModel.getPrintTimeString();
            LocalDate now = LocalDate.now();
            LocalDateTime printTime = LocalDateTime.of(now, LocalTime.parse(printTimeString, DateTimeFormatter.ofPattern("HH:mm:ss")));
            poundBillModel.setPrintTime(printTime);
        }
    }

    public void insertPoundBill(PoundBillModel poundBillModel) {
        parseString(poundBillModel);

        if (poundBillModel.getCreatTime() == null) {
            poundBillModel.setCreatTime(LocalDateTime.now());
        }
        if (poundBillModel.getModifyTime() == null) {
            poundBillModel.setModifyTime(LocalDateTime.now());
        }

        if (poundBillModel.getPrimaryWeight() == 0) {
            poundBillModel.setProfitLossWeight(0);
        }

        poundBillMapper.insert(poundBillModel);
    }

    public void updateById(int id, UpdateDTO updateDTO) {
        PoundBillModel poundBillModel = PoundBillModel.fromDTO(updateDTO);
        parseString(poundBillModel);
        poundBillModel.setId(id);
        poundBillMapper.update(poundBillModel);
    }

    public PoundBillModel selectById(int id) {
        return poundBillMapper.selectById(id);
    }

    public void deleteById(int id) {
        poundBillMapper.deleteById(id);
    }

    public void cleanPoundBill() {
        PoundBillModel[] poundBillModels = poundBillMapper.selectAll();
        for (PoundBillModel poundBillModel : poundBillModels) {
            if (poundBillModel.getPrintTime() == null && !poundBillModel.getCreatTime().toLocalDate().equals(LocalDate.now())) {
                poundBillMapper.deleteById(poundBillModel.getId());
            }
        }
    }

    public PoundBillModel[] selectAll() {
        return poundBillMapper.selectAll();
    }

    public int count() {
        return poundBillMapper.count();
    }
}
