package io.github.weightrack.controller;

import com.alibaba.fastjson2.JSON;
import io.github.weightrack.dto.PoundBillListDTO;
import io.github.weightrack.dto.UpdateDTO;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.module.User;
import io.github.weightrack.service.CoalTypeService;
import io.github.weightrack.service.PoundBillService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@Controller
public class PoundBillController {

    @Autowired
    private PoundBillService poundBillService;

    @Autowired
    private CoalTypeService coalTypeService;

    @PostMapping("/creat")
    public String getForm(
            @RequestParam("IOType") String IOType,
            @RequestParam("coalType") String coalType,
            @RequestParam("plate-number") String plateNumber,
            @RequestParam("gross-weight") String grossWeight,
            @RequestParam("tare") String tare,
            @RequestParam("primary-weight") String primaryWeight,
            @RequestParam("output-unit") String outputUnit,
            @RequestParam("input-unit") String inputUnit,
            @RequestParam("weigher") String weigher,
            @RequestParam("other-coal-type") String otherCoalType,
            @RequestParam("creator-id") String creatorId,
            HttpServletRequest request) {

        if (coalType.equals("other")) {
            coalType = otherCoalType.strip();
            coalTypeService.insertCoalType(coalType);
        }

        PoundBillModel poundBillModel = PoundBillModel.createPoundBillModel(creatorId, IOType, coalType, plateNumber, grossWeight, tare, primaryWeight, outputUnit, inputUnit, weigher);
        Object user = request.getSession().getAttribute("user");
        if (!(user instanceof User)) {
            return "redirect:/login";
        }

        poundBillService.insertPoundBill(poundBillModel);

        String url = "redirect:/showList/today/";
        switch (poundBillModel.getIOType()) {
            case "1" -> url += "in/1";
            case "0" -> url += "out/1";
            case "2" -> url += "return/1";
            case "3" -> url += "turnover/1";
        }
        return url;
    }

    @GetMapping("/update/{id}")
    public String getPoundBillById(
            Model model,
            @PathVariable("id") int id) {
        model.addAttribute("poundBillModel", poundBillService.selectById(id));
        model.addAttribute("coalTypes", coalTypeService.getCoalTypes());

        return "update";
    }

    @ResponseBody
    @PostMapping("/update/{id}")
    public String updatePoundBillById(@PathVariable("id") int id,
                                      @RequestParam("IOType") String IOType,
                                      @RequestBody UpdateDTO updateDTO) {

        updateDTO.setIOType(IOType);
        poundBillService.updateById(id, updateDTO);
        PoundBillModel poundBillModel = poundBillService.selectById(id);
        if (poundBillModel.getCreatTime().toLocalDate().equals(LocalDate.now())) {
            return "{\"result\": \"ok\", \"isToday\": true}";
        } else {
            return "{\"result\": \"ok\", \"isToday\": false}";
        }
    }

    @ResponseBody
    @PostMapping("/api/poundBill/delete/{id}")
    public String deletePoundBillById(@PathVariable("id") int id) {
        poundBillService.deleteById(id);
        return "ok";
    }

}
