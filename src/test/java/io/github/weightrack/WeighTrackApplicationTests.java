package io.github.weightrack;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.PoundBillService;
import io.github.weightrack.utils.ExcelUtilTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static io.github.weightrack.utils.ExcelUtilTest.insertPoundBillByExcel;

@Slf4j
@SpringBootTest
public class WeighTrackApplicationTests {

    @Autowired
    PoundBillService poundBillService;

    @Test
    public void loadFromExcel() throws Exception {

        // 待导入数据库excel文件，最好是副本文件
        String filePath = "C:\\Users\\lyp\\Desktop\\鸿聚一号煤场库存出货台账.xlsx";

        List<PoundBillModel> pb1 = insertPoundBillByExcel("入库明细", 3, 9, "R", "1", 10, 11, 12, true, filePath);
        List<PoundBillModel> pb2 = insertPoundBillByExcel("返仓", 2, 9, "R", "2", 10, 11, 12, true, filePath);
        List<PoundBillModel> pb3 = insertPoundBillByExcel("内部周转", 2, 9, "R", "3", 10, 11, 12, true, filePath);
        List<PoundBillModel> pb4 = insertPoundBillByExcel("出库明细", 2, 7, "C", "0", 8, 9, 10, false, filePath);

        List<List<PoundBillModel>> pbs = new ArrayList<>();
        pbs.add(pb1);
        pbs.add(pb2);
        pbs.add(pb3);
        pbs.add(pb4);

        int modifyCount = 0;
        for (List<PoundBillModel> pb : pbs) {
            for (PoundBillModel poundBillModel : pb) {
                if (poundBillModel.getCoalType() == null || poundBillModel.getCoalType().isEmpty()) {
                    continue;
                }
                log.info(poundBillModel.toString());
                poundBillService.insertPoundBill(poundBillModel);
                modifyCount++;
            }
            log.warn("插入了 {} 条记录", modifyCount);
            modifyCount = 0;
        }

    }
}
