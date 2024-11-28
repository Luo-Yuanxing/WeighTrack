package io.github.weightrack.controller;

import io.github.weightrack.Service.PoundBillService;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.module.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PoundBillController {

    @Autowired
    private PoundBillService poundBillService;

    @PostMapping("/commit")
    public String getForm(
            @RequestParam("IOType") String IOType,
            @RequestParam("coal-type") String coalType,
            @RequestParam("plate-number") String plateNumber,
            @RequestParam("gross-weight") String grossWeight,
            @RequestParam("tare") String tare,
            @RequestParam("primary-weight") String primaryWeight,
            @RequestParam("empty-load-time") String emptyLoadTime,
            @RequestParam("full-load-time") String fullLoadTime,
            @RequestParam("output-unit") String outputUnit,
            @RequestParam("input-unit") String inputUnit,
            @RequestParam("weigher") String weigher,
            HttpServletRequest request) {

        PoundBillModel poundBillModel = PoundBillModel.createPoundBillModel(IOType, coalType, plateNumber, grossWeight, tare,
                primaryWeight, emptyLoadTime, fullLoadTime,
                outputUnit, inputUnit, weigher);
        Object user = request.getSession().getAttribute("user");
        if (user instanceof User) {
            poundBillModel.setCreatorId(((User) user).getId());
        } else {
            throw new RuntimeException("用户未登录");
        }

        poundBillService.insertPoundBill(poundBillModel);

        // 返回视图名称
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String getPoundBillById(
            Model model,
            @PathVariable("id") int id) {
        model.addAttribute("poundBillModel", poundBillService.selectById(id));
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updatePoundBillById(@PathVariable("id") int id,
                                      @RequestParam("IOType") String IOType,
                                      @RequestParam("coal-type") String coalType,
                                      @RequestParam("plate-number") String plateNumber,
                                      @RequestParam("gross-weight") String grossWeight,
                                      @RequestParam("tare") String tare,
                                      @RequestParam("primary-weight") String primaryWeight,
                                      @RequestParam("empty-load-time") String emptyLoadTime,
                                      @RequestParam("full-load-time") String fullLoadTime,
                                      @RequestParam("output-unit") String outputUnit,
                                      @RequestParam("input-unit") String inputUnit,
                                      @RequestParam("weigher") String weigher) {

        PoundBillModel poundBillModel = PoundBillModel.createPoundBillModel(IOType, coalType, plateNumber, grossWeight, tare,
                primaryWeight, emptyLoadTime, fullLoadTime,
                outputUnit, inputUnit, weigher);

        // 此处应该调用更新的服务方法，例如 poundBillService.updatePoundBill(poundBillModel);
        poundBillService.updateById(poundBillModel, id);
        return "redirect:/showList";
    }
}
