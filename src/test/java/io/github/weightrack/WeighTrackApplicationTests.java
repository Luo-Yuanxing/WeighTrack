package io.github.weightrack;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.PoundBillService;
import io.github.weightrack.utils.ExcelUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WeighTrackApplicationTests {

    @Autowired
    PoundBillService poundBillService;

    @Test
    void contextLoads() throws Exception {
        PoundBillModel[] poundBillModels = poundBillService.selectAll();
        for (PoundBillModel poundBillModel : poundBillModels) {
            if (poundBillModel.getCoalType() == null) {
                poundBillService.deleteById(poundBillModel.getId());
            }
        }
    }

}
