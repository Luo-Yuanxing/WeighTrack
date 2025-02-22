package io.github.weightrack;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.PoundBillService;
import io.github.weightrack.utils.ExcelUtilTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.github.weightrack.utils.ExcelUtilTest.insertPoundBillByExcel;

@Slf4j
@SpringBootTest
public class WeighTrackApplicationTests {
    @Test
    public void loadFromExcel() throws Exception {
        String filePath = "";
        insertPoundBillByExcel("入库明细", 3, 9, "R", "1", 10, 11, 12, true, filePath);
        insertPoundBillByExcel("返仓明细", 3, 9, "R", "2", 10, 11, 12, true, filePath);
        insertPoundBillByExcel("内部周转明细", 3, 9, "R", "3", 10, 11, 12, true, filePath);
        insertPoundBillByExcel("出库明细", 2, 7, "C", "0", 8, 9, 10, false, filePath);

    }
}
