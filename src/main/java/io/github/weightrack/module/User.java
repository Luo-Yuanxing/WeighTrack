package io.github.weightrack.module;

import io.github.weightrack.dto.UserEditUpdateDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String realName;
    private LocalDateTime lastLogin;

    private String role;

    public User(int id, String username, String password, String realName, String role, LocalDateTime lastLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.role = role;
        this.lastLogin = lastLogin;
    }

    public User(String username, String password, String realName, String role) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.role = role;
    }

    public User(int id, String username, String password, String realName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realName = realName;
    }

    public static User fromUserEditUpdateDTO(UserEditUpdateDTO userEditUpdateDTO) {
        return new User(userEditUpdateDTO.getId(), userEditUpdateDTO.getUsername(), userEditUpdateDTO.getPassword(), userEditUpdateDTO.getRealName());
    }
}
