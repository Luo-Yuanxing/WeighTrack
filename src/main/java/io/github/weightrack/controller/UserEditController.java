package io.github.weightrack.controller;

import com.alibaba.fastjson2.JSON;
import io.github.weightrack.dto.UserEditUpdateDTO;
import io.github.weightrack.module.User;
import io.github.weightrack.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@Controller
public class UserEditController {
    @Autowired
    private UserService userService;

    @GetMapping("/user-edit")
    public String userEdit(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "user-edit";
    }

    @ResponseBody
    @PostMapping("/user-edit")
    public String updateUser(@RequestBody UserEditUpdateDTO userEditUpdateDTO) {
        @Data
        class Response {
            private String message;
            private String code;

            Response(String code, String message) {
                this.code = code;
                this.message = message;
            }


        }
        int updateLines = userService.updateById(User.fromUserEditUpdateDTO(userEditUpdateDTO));
        if (updateLines == 1) {
            log.info("修改用户成功: {}", userEditUpdateDTO);
            return JSON.toJSONString(new Response("0", "修改成功"));
        } else {
            return JSON.toJSONString(new Response("1", "修改失败"));
        }
    }

    @Value("${admin.password}")
    public String password;

    @Value("${admin.username}")
    public String username;


    @ResponseBody
    @PostMapping("/user-edit/delete")
    public String deleteUser(@RequestBody Map<String, String> data) {
        String deleteIdStr = data.get("deleteId");

        if (deleteIdStr == null || deleteIdStr.isEmpty()) {
            log.info("id为空");
            return createErrorResponse("删除失败，id为空");
        }

        int deleteId = Integer.parseInt(deleteIdStr);
        User user = userService.findUserById(deleteId);

        if (user == null) {
            log.info("用户不存在");
            return createErrorResponse("删除失败，用户不存在");
        }

        if (user.getUsername().equals(username)) {
            log.info("不能删除管理员用户");
            return createErrorResponse("不能删除系统管理员");
        }

        if (userService.deleteById(deleteId) != 1) {
            log.error("数据库错误, id: {}", deleteId);
            return createErrorResponse("删除失败");
        }

        log.info("删除用户：{}", user);
        return createSuccessResponse();
    }

    private String createErrorResponse(String message) {
        return String.format("{\"code\": \"1\", \"message\": \"%s\"}", message);
    }

    private String createSuccessResponse() {
        return String.format("{\"code\": \"0\", \"message\": \"%s\"}", "删除成功");
    }

}
