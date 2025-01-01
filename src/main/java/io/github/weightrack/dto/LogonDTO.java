package io.github.weightrack.dto;

import io.github.weightrack.module.User;
import lombok.Data;

@SuppressWarnings("ALL")
@Data
public class LogonDTO {
    private User user;
    private String message;
    private int userId;
}
