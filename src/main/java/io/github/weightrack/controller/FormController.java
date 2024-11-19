package io.github.weightrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    @PostMapping("/getfrom/")
    public String getForm(
            @RequestParam("coal-type") String coalType,
            @RequestParam("plate-number") String plateNumber,
            @RequestParam("tare") String tare,
            @RequestParam("primary-weight") String primaryWeight,
            @RequestParam("empty-load-time") String emptyLoadTime,
            @RequestParam("full-load-time") String fullLoadTime,
            @RequestParam("output-unit") String outputUnit,
            @RequestParam("input-unit") String inputUnit,
            @RequestParam("weigher") String weigher

    ) {
        // 打印接收到的数据

        // 返回视图名称
        return "success"; // 对应 success.html
    }
}
