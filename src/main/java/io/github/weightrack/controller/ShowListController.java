package io.github.weightrack.controller;

import io.github.weightrack.Mapper.ShowListMapper;
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
        PoundBillModel[] poundBillModels = showListService.showList();
        model.addAttribute("poundBillModels", poundBillModels);

        return "showList";
    }

}
