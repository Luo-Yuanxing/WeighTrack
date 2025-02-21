package io.github.weightrack.controller;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.ShowListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShowListController {

    private final int pageSize = 50;
    @Autowired
    ShowListService showListService;

    @ResponseBody
    @PostMapping("/showList/page_num")
    public int getPageNumber(@RequestParam("isToday") boolean isToday,
                             @RequestParam("IOType") String IOType) {
        return showListService.getPageNumber(pageSize, isToday, IOType);
    }

// ===============================================
//              展示全部历史数据
// ===============================================

    /**
     * @deprecated 这个方法已经不再推荐使用，未来版本将会删除此方法。
     * @since 2.2
     */
    @Deprecated
    @GetMapping("/showList/all/{pageNumber}")
    public String showList(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showList("all", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "all");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/in/{pageNumber}")
    public String showListIn(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showList("in", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "1");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/out/{pageNumber}")
    public String showListOut(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showList("out", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "0");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/return/{pageNumber}")
    public String showListReturn(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showList("return", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "2");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/turnover/{pageNumber}")
    public String showListTurnover(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showList("turnover", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "3");
        model.addAttribute("today", false);
        return "showList";
    }

// ===============================================
//              展示今日历史数据
// ===============================================

    @GetMapping("/showList/today/in/{pageNumber}")
    public String showListTodayIn(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("in", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "1");
        model.addAttribute("today", true);
        model.addAttribute("todaySummaryPanel", showListService.getTodaySummaryPanel("1"));
        return "showList";
    }

    @GetMapping("/showList/today/out/{pageNumber}")
    public String showListTodayOut(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("out", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "0");
        model.addAttribute("today", true);
        model.addAttribute("todaySummaryPanel", showListService.getTodaySummaryPanel("0"));
        return "showList";
    }

    @GetMapping("/showList/today/return/{pageNumber}")
    public String showListTodayReturn(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("return", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "2");
        model.addAttribute("today", true);
        model.addAttribute("todaySummaryPanel", showListService.getTodaySummaryPanel("2"));
        return "showList";
    }

    @GetMapping("/showList/today/turnover/{pageNumber}")
    public String showListTodayTurnover(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("turnover", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "3");
        model.addAttribute("today", true);
        model.addAttribute("todaySummaryPanel", showListService.getTodaySummaryPanel("3"));
        return "showList";
    }

    /**
     * @deprecated 这个方法已经不再推荐使用，未来版本将会删除此方法。
     * @since 2.2
     */
    @Deprecated
    @GetMapping("/showList/today/all/{pageNumber}")
    public String showListTodayAll(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("all", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "all");
        model.addAttribute("today", true);
        model.addAttribute("todaySummaryPanel", showListService.getTodaySummaryPanel("all"));
        return "showList";
    }

    @GetMapping("/showList/plate-number-filter/")
    public String showListByPlateNumber(@RequestParam("plateNumber") String plateNumber,
                                        @RequestParam("filter") String filter,
                                        @RequestParam("poundId") String poundId,
                                        Model model) {
        PoundBillModel[] poundBillModels = showListService.showListByPlateNumber(plateNumber.strip(), filter, poundId);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "all");
        model.addAttribute("today", false);
        return "showList";
    }

}
