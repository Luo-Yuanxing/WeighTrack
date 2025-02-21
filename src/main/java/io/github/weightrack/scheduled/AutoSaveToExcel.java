package io.github.weightrack.scheduled;

import io.github.weightrack.service.OutputExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AutoSaveToExcel {

    @Autowired
    OutputExcelService outputExcelService;

    @Scheduled(cron = "0 */5 * * * *")
    public void autoSaveToExcel() {
        log.info("自动保存到Excel");

        outputExcelService.getTodayPrintedPoundBillModels("0");
        outputExcelService.getTodayPrintedPoundBillModels("1");
        outputExcelService.getTodayPrintedPoundBillModels("2");
        outputExcelService.getTodayPrintedPoundBillModels("3");
    }

}
