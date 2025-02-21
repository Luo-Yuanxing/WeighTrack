package io.github.weightrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WeighTrackApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        SpringApplication.run(WeighTrackApplication.class, args);
    }

}
