package io.github.weightrack.controller;

import com.alibaba.fastjson2.JSON;
import io.github.weightrack.Service.CoalTypeService;
import io.github.weightrack.module.CoalType;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CoalTypeController {

    @Autowired
    CoalTypeService coalTypeService;

    @ResponseBody
    @GetMapping("/coalType")
    public String coalType() {
        CoalType[] coalTypes = coalTypeService.getCoalType();
        return JSON.toJSONString(coalTypes);
    }

    @PostMapping("/coalType/delete")
    public String deleteCoalType(@RequestParam List<String> coalTypes) {
        for (String coalType : coalTypes) {
            coalTypeService.deleteCoalTypeByName(coalType);
        }
        return "index";
    }


    @GetMapping("/coalTypeEdit")
    public String coalTypeEdit(Model model) {
        model.addAttribute("coalTypes", coalTypeService.getCoalType());
        return "coalTypeEdit";
    }
}
