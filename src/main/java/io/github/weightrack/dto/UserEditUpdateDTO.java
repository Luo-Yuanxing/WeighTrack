package io.github.weightrack.dto;

import lombok.Data;

@Data
public class UserEditUpdateDTO {
    private int id;
    private String username;
    private String password;
    private String realName;

}
