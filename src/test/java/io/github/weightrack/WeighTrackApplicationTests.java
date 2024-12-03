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
        List<PoundBillModel> poundBillModels = ExcelUtil.insertPoundBillByExcel();
        System.out.println("length:" + poundBillModels.size());
        for (PoundBillModel poundBillModel : poundBillModels) {
            poundBillService.insertPoundBill(poundBillModel);
        }
    }

}
