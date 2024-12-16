package io.github.weightrack.controller;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.OutputExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

@Slf4j
@Controller
public class OutputExcelController {

    @Autowired
    OutputExcelService outputExcelService;

    @ResponseBody
    @GetMapping("/outputExcel")
    public String outputExcel(@RequestParam("IOType") String IOType) {
        return outputExcelService.getTodayPrintedPoundBillModels(IOType);
    }
}
