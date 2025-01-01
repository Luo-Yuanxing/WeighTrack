package io.github.weightrack.listener;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationStartupListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);
    String BROWSER_PATH = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("服务器启动成功！");
        try {
            new ProcessBuilder(BROWSER_PATH, "127.0.0.1").start();
        } catch (Exception e) {
            log.error("打开浏览器失败: {}", e.getMessage());
        }
    }
}
