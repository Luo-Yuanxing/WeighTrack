package io.github.weightrack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app.properties")
@ConfigurationProperties(prefix = "admin")
@Data
public class AdminConfig {
    private String password;
    private String username;
}
