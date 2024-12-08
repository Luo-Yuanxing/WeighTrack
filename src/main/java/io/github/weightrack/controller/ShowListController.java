package io.github.weightrack.controller;

import io.github.weightrack.service.ShowListService;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShowListController {

    @Autowired
    ShowListService showListService;

    private final int pageSize = 50;

    @ResponseBody
    @PostMapping("/showList/page_num")
    public int getPageNumber(@RequestParam("isToday") boolean isToday,
                                 @RequestParam("IOType") String IOType) {
        return showListService.getPageNumber(pageSize, isToday, IOType);
    }

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
        model.addAttribute("IOType", "in");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/out/{pageNumber}")
    public String showListOut(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showList("out", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "out");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/today/in/{pageNumber}")
    public String showListTodayIn(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("in", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "todayIn");
        model.addAttribute("today", true);
        return "showList";
    }

    @GetMapping("/showList/today/out/{pageNumber}")
    public String showListTodayOut(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("out", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "todayOut");
        model.addAttribute("today", true);
        return "showList";
    }

    @GetMapping("/showList/today/all/{pageNumber}")
    public String showListTodayAll(Model model, @PathVariable int pageNumber) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("all", pageSize, pageNumber);
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "todayAll");
        model.addAttribute("today", true);
        return "showList";
    }

}
