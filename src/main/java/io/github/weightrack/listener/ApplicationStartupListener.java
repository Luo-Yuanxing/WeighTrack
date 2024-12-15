package io.github.weightrack.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ApplicationStartupListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("服务器启动成功！");
        openBrowser("127.0.0.1");
    }

    String BROWSER_PATH = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
    public void openBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI(url));
                    return; // 如果成功打开，则结束方法
                } catch (IOException | URISyntaxException e) {
                    System.err.println("Failed to open browser using Desktop: " + e.getMessage());
                }
            } else {
                System.err.println("Desktop browse action is not supported.");
            }
        } else {
            System.err.println("Desktop is not supported. Falling back to specified browser.");
        }

        // 如果 Desktop 不支持或失败，则尝试指定浏览器路径
        openWithSpecificBrowser(url);
    }

    private void openWithSpecificBrowser(String url) {
        try {
            new ProcessBuilder(BROWSER_PATH, url).start();
        } catch (IOException e) {
            System.err.println("Failed to open browser with specified path: " + e.getMessage());
        }
    }
}
