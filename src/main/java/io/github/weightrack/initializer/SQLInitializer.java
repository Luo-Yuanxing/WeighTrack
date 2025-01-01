package io.github.weightrack.initializer;

import com.zaxxer.hikari.HikariDataSource;
import io.github.weightrack.exception.UsersException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class SQLInitializer {

    @Value("${admin.password}")
    public String password;

    @Value("${admin.username}")
    public String username;

    @Autowired
    CoalTypeService coalTypeService;
    @Autowired
    PoundBillService poundBillService;
    @Autowired
    UserService userService;
    @Autowired
    private HikariDataSource dataSource;

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

    @PostConstruct
    public void initUserTable() {
        log.info("检查user表");
        Connection connection = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            // 获取数据库连接
            connection = dataSource.getConnection();

            // 获取数据库元数据
            DatabaseMetaData metaData = connection.getMetaData();

            // 检查 users 表中是否有 role 字段
            resultSet = metaData.getColumns(null, null, "users", "role");

            if (!resultSet.next()) {
                // 如果不存在 role 字段，则添加该字段
                String alterTableSQL = "ALTER TABLE users ADD COLUMN role VARCHAR(100) DEFAULT 'normal' NOT NULL";
                stmt = connection.createStatement();
                stmt.executeUpdate(alterTableSQL);
                log.info("添加 role 字段到表 user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
    }

    @PostConstruct
    public void initAdmin() {
        log.info("初始化管理员");
        User user = null;
        try {
            user = userService.findUserByUsername("administrators");
        } catch (UsersException e) {
            if (e.getMessage().equals("数据库中存在多个相同的用户名")) {
                int deleteCount = userService.delete("administrators");
                log.info("删除重复的管理员账户: {}个用户", deleteCount);
            }
        }
        if (user == null) {

            user = new User("administrators", password, "系统管理员", "admin");
            try {
                userService.insertUser(user);
            } catch (UsersException ignored) {
            }
            log.info("创建管理员账户：administrators");
        } else {
            if (!user.getPassword().equals(password)) {
                user.setPassword(password);
                userService.updateUser(user);
            }
        }
    }
}
