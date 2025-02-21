package io.github.weightrack.controller;

import com.alibaba.fastjson2.JSON;
import io.github.weightrack.service.WeigherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeigherController {

    @Autowired
    WeigherService weigherService;
    @SuppressWarnings("unused")
    @ResponseBody
    @GetMapping("/weigher-list")
    public String weigherList() {
        return JSON.toJSONString(weigherService.getAllWeighers());
    }

}
