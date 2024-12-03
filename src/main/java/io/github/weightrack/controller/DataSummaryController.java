package io.github.weightrack.controller;

import io.github.weightrack.service.CoalTypeService;
import io.github.weightrack.service.DataSummaryService;
import io.github.weightrack.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataSummaryController {
    @Autowired
    private DataSummaryService dataSummaryService;

    @Autowired
    private CoalTypeService coalTypeService;

    @GetMapping("/data-summary/in/today")
    public String dataSummaryInToday(Model model) {
        model.addAttribute("summaryTable", dataSummaryService.getSummaryToday("in"));
        model.addAttribute("IOType", "in");
        model.addAttribute("date", "today");
        model.addAttribute("tableName", "今日入库汇总");
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "data-summary";
    }

    @GetMapping("/data-summary/in/month")
    public String dataSummaryInMonth(Model model) {
        model.addAttribute("summaryTable", dataSummaryService.getSummary("in", DateUtil.getFirstDayOfMonth(), DateUtil.getLastDayOfMonth()));
        model.addAttribute("IOType", "in");
        model.addAttribute("date", "month");
        model.addAttribute("tableName", "本月入库汇总");
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "data-summary";
    }

    @GetMapping("/data-summary/in/year")
    public String dataSummaryInYear(Model model) {
        model.addAttribute("summaryTable", dataSummaryService.getSummary("in", DateUtil.getFirstDayOfYear(), DateUtil.getLastDayOfYear()));
        model.addAttribute("IOType", "in");
        model.addAttribute("date", "year");
        model.addAttribute("tableName", "年度入库汇总");
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "data-summary";
    }

    @GetMapping("/data-summary/in/all")
    public String dataSummaryInAll(Model model) {
        model.addAttribute("summaryTable", dataSummaryService.getSummary("in", DateUtil.getAllStartTime(), DateUtil.getAllEndTime()));
        model.addAttribute("IOType", "in");
        model.addAttribute("date", "all");
        model.addAttribute("tableName", "历史入库汇总");
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "data-summary";
    }

    @GetMapping("/data-summary/out/today")
    public String dataSummaryOutToday(Model model) {
        model.addAttribute("summaryTable", dataSummaryService.getSummaryToday("out"));
        model.addAttribute("IOType", "out");
        model.addAttribute("date", "today");
        model.addAttribute("tableName", "今日出库汇总");
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "data-summary";
    }

    @GetMapping("/data-summary/out/month")
    public String dataSummaryOutMonth(Model model) {
        model.addAttribute("summaryTable", dataSummaryService.getSummary("out", DateUtil.getFirstDayOfMonth(), DateUtil.getLastDayOfMonth()));
        model.addAttribute("IOType", "out");
        model.addAttribute("date", "month");
        model.addAttribute("tableName", "本月出库汇总");
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "data-summary";
    }

    @GetMapping("/data-summary/out/year")
    public String dataSummaryOutYear(Model model) {
        model.addAttribute("summaryTable", dataSummaryService.getSummary("out", DateUtil.getFirstDayOfYear(), DateUtil.getLastDayOfYear()));
        model.addAttribute("IOType", "out");
        model.addAttribute("date", "year");
        model.addAttribute("tableName", "年度出库汇总");
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "data-summary";
    }

    @GetMapping("/data-summary/out/all")
    public String dataSummaryOutAll(Model model) {
        model.addAttribute("summaryTable", dataSummaryService.getSummary("out", DateUtil.getAllStartTime(), DateUtil.getAllEndTime()));
        model.addAttribute("IOType", "out");
        model.addAttribute("date", "all");
        model.addAttribute("tableName", "历史出库汇总");
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "data-summary";
    }

}
