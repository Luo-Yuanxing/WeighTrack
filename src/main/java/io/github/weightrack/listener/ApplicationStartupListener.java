package io.github.weightrack.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartupListener.class);
    String BROWSER_PATH = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";

    @Value("${app.version}")
    private String currentVersion;
    @Value("${app.name}")
    private String appName;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            new ProcessBuilder(BROWSER_PATH, "127.0.0.1").start();
        } catch (Exception e) {
            log.error("打开浏览器失败: {}", e.getMessage());
        }
        // TODO: 版本升级修改版本信息
        String ASCIIArt = """
                
                __     __                            _              \s
                \\ \\   / /                           (_)             \s
                 \\ \\_/ /  _   _   __ _  _ __  __  __ _  _ __    __ _\s
                  \\   /  | | | | / _` || '_ \\ \\ \\/ /| || '_ \\  / _` |
                   | |   | |_| || (_| || | | | >  < | || | | || (_| |
                   |_|    \\__,_| \\__,_||_| |_|/_/\\_\\|_||_| |_| \\__, |
                                                                __/ |
                                                               |___/\s
                                                         \s""" + appName + """
                \n                                               \s""" + currentVersion;
        log.info(ASCIIArt);
    }

}
