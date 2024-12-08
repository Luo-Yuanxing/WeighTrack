package io.github.weightrack.controller;

import io.github.weightrack.service.ShowListService;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowListController {

    @Autowired
    ShowListService showListService;

    @GetMapping("/showList/")
    public String showList(Model model) {
        PoundBillModel[] poundBillModels = showListService.showList("all");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "all");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/in")
    public String showListIn(Model model) {
        PoundBillModel[] poundBillModels = showListService.showList("in");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "in");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/out")
    public String showListOut(Model model) {
        PoundBillModel[] poundBillModels = showListService.showList("out");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "out");
        model.addAttribute("today", false);
        return "showList";
    }

    @GetMapping("/showList/today/in")
    public String showListTodayIn(Model model) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("in");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "todayIn");
        model.addAttribute("today", true);
        return "showList";
    }

    @GetMapping("/showList/today/out")
    public String showListTodayOut(Model model) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("out");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "todayOut");
        model.addAttribute("today", true);
        return "showList";
    }

    @GetMapping("/showList/today/")
    public String showListTodayAll(Model model) {
        PoundBillModel[] poundBillModels = showListService.showTodayList("all");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "todayAll");
        model.addAttribute("today", true);
        return "showList";
    }

}
