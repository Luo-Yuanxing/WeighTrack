package io.github.weightrack.dto;

import lombok.Data;

@Data
public class SignInDTO {
    private String username;
    private String password;
    private String realName;
}
