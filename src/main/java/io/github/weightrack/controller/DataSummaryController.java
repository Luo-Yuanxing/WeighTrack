package io.github.weightrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataSummaryController {

    @GetMapping("/data-summary")
    public String dataSummary() {
        return "redirect:/data-summary/today";
    }

    @GetMapping("/data-summary/today")
    public String dataSummaryToday() {
        return "data-summary";
    }

    @GetMapping("/data-summary/month")
    public String dataSummaryMonth() {
        return "data-summary";
    }

    @GetMapping("/data-summary/quarter")
    public String dataSummaryQuarter() {
        return "data-summary";
    }

    @GetMapping("/data-summary/year")
    public String dataSummaryYear() {
        return "data-summary";
    }
}
