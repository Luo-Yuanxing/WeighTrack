package io.github.weightrack.initializer;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.CoalTypeService;
import io.github.weightrack.service.ShowListService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SQLInitializer {

    @Autowired
    CoalTypeService coalTypeService;
    @Autowired
    ShowListService showListService;

    @PostConstruct
    public void init() {
        PoundBillModel[] poundBillModels = showListService.showList("all");
        for (PoundBillModel poundBillModel : poundBillModels) {
            coalTypeService.insertCoalType(poundBillModel.getCoalType());
        }
        Logger logger = LoggerFactory.getLogger(SQLInitializer.class);
        logger.info("煤种初始化完成");
    }
}
