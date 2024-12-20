package io.github.weightrack.module;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String realName;
    private LocalDateTime lastLogin;
}
