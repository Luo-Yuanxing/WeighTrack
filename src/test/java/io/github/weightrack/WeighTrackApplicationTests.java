package io.github.weightrack;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.PoundBillService;
import io.github.weightrack.utils.ExcelUtilTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class WeighTrackApplicationTests {

    @Autowired
    PoundBillService poundBillService;

    @Test
    void contextLoads() throws Exception {
        List<PoundBillModel> poundBillModelsIn = ExcelUtilTest.insertPoundBillByExcel_IN();
        for (PoundBillModel poundBillModel : poundBillModelsIn) {
            if (poundBillModel.getCoalType() != null) {
                poundBillService.insertPoundBill(poundBillModel);
                log.info("insert into poundBill {}", poundBillModel);
            }
        }

        List<PoundBillModel> poundBillModelsOut = ExcelUtilTest.insertPoundBillByExcel_OUT();
        for (PoundBillModel poundBillModel : poundBillModelsOut) {
            if (poundBillModel.getCoalType() != null) {
                poundBillService.insertPoundBill(poundBillModel);
                log.info("insert into poundBill {}", poundBillModel);
            }
        }
    }


}
