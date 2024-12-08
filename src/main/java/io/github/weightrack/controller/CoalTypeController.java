package io.github.weightrack.controller;

import com.alibaba.fastjson2.JSON;
import io.github.weightrack.module.CoalType;
import io.github.weightrack.service.CoalTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CoalTypeController {

    @Autowired
    CoalTypeService coalTypeService;

    @ResponseBody
    @GetMapping("/coalType")
    public String coalType() {
        CoalType[] coalTypes = coalTypeService.getCoalTypes();
        return JSON.toJSONString(coalTypes);
    }

    @PostMapping("/coalType/delete")
    public String deleteCoalType(@RequestParam(value = "coalTypes", required = false) List<String> coalTypes, Model model) {
        if (coalTypes == null) {
            model.addAttribute("error", "请选择要删除的煤种");
            model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
            return "coalTypeEdit";
        }
        Logger logger = LoggerFactory.getLogger(CoalTypeController.class);
        logger.info("删除煤种：{}", coalTypes);
        for (String coalType : coalTypes) {
            coalTypeService.deleteCoalTypeByName(coalType);
        }
        return "redirect:/";
    }


    @GetMapping("/coalTypeEdit")
    public String coalTypeEdit(Model model) {
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());
        return "coalTypeEdit";
    }
}
