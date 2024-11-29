package io.github.weightrack.controller;

import io.github.weightrack.Service.ShowListService;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowListController {

    @Autowired
    ShowListService showListService;

    @GetMapping("/showList")
    public String showList(Model model) {
        PoundBillModel[] poundBillModels = showListService.showList("all");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "all");
        return "showList";
    }

    @GetMapping("/showList/in")
    public String showListIn(Model model) {
        PoundBillModel[] poundBillModels = showListService.showList("in");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "in");
        return "showList";
    }

    @GetMapping("/showList/out")
    public String showListOut(Model model) {
        PoundBillModel[] poundBillModels = showListService.showList("out");
        model.addAttribute("poundBillModels", poundBillModels);
        model.addAttribute("IOType", "out");
        return "showList";
    }

}
