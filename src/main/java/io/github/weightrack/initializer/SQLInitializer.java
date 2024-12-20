package io.github.weightrack.initializer;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.module.User;
import io.github.weightrack.service.CoalTypeService;
import io.github.weightrack.service.PoundBillService;
import io.github.weightrack.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class SQLInitializer {

    @Autowired
    CoalTypeService coalTypeService;
    @Autowired
    PoundBillService poundBillService;
    @Autowired
    UserService userService;

    @PostConstruct
    public void initCoalType() {
        PoundBillModel[] poundBillModels = poundBillService.selectAll();
        for (PoundBillModel poundBillModel : poundBillModels) {
            coalTypeService.insertCoalType(poundBillModel.getCoalType());
        }
        Logger logger = LoggerFactory.getLogger(SQLInitializer.class);
        logger.info("煤种初始化完成");
    }

    @PostConstruct
    public void cleanPoundBill() {
        // 清除未打印数据
        poundBillService.cleanPoundBill();
    }

    @PostConstruct
    public void cleanOldUsers() {
        User[] users = userService.findAll();
        LocalDateTime currentTime = LocalDateTime.now();
        for (User user : users) {
            LocalDateTime lastLogin = user.getLastLogin();
            // 判断用户的最后登录时间是否超过1年
            if (lastLogin != null && ChronoUnit.YEARS.between(lastLogin, currentTime) >= 1) {
                // 执行删除操作
                if (userService.delete(user.getUsername()) == 1) {
                    log.info("删除用户: {}", user.getUsername());
                }
            }
        }
    }
}
