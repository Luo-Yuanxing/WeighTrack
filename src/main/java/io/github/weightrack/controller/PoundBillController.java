package io.github.weightrack.controller;

import io.github.weightrack.Service.PoundBillService;
import io.github.weightrack.exception.InvalidForm;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;

@Controller
public class PoundBillController {

    @Autowired()
    PoundBillService poundBillService;

    @PostMapping("/getform")
    public String getForm(
            @RequestParam("coal-type") String coalType,
            @RequestParam("plate-number") String plateNumber,
            @RequestParam("gross-weight") String grossWeight,
            @RequestParam("tare") String tare,
            @RequestParam("primary-weight") String primaryWeight,
            @RequestParam("empty-load-time") String emptyLoadTime,
            @RequestParam("full-load-time") String fullLoadTime,
            @RequestParam("output-unit") String outputUnit,
            @RequestParam("input-unit") String inputUnit,
            @RequestParam("weigher") String weigher

    ) throws InvalidForm {
        PoundBillModel poundBillModel = new PoundBillModel();
        poundBillModel.setCoalType(coalType);
        poundBillModel.setPlateNumber(plateNumber);
        poundBillModel.setGrossWeight(Float.parseFloat(grossWeight));
        poundBillModel.setTare(Float.parseFloat(tare));
        poundBillModel.setPrimaryWeight(Float.parseFloat(primaryWeight));
        poundBillModel.setEmptyLoadTime(LocalTime.parse(emptyLoadTime));
        poundBillModel.setFullLoadTime(LocalTime.parse(fullLoadTime));
        poundBillModel.setOutputUnit(outputUnit);
        poundBillModel.setInputUnit(inputUnit);
        poundBillModel.setWeigher(weigher);

        poundBillService.insertPoundBill(poundBillModel);

        // 返回视图名称
        return "/"; // 对应 success.html
    }
}
