package io.github.weightrack.controller;

import io.github.weightrack.Service.PoundBillService;
import io.github.weightrack.Service.PrintService;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.utils.ImagePrinter;
import io.github.weightrack.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.SimpleFormatter;

@Controller
public class PrintController {

    @Autowired
    PrintService printService;

    @Autowired
    PoundBillService poundBillService;

    @GetMapping("/print/{id}")
    public String print(@PathVariable("id") int id, Model model) {
        PoundBillModel poundBillModel = printService.selectById(id);
        model.addAttribute("poundBillModel", poundBillModel);
        return "/print";
    }

    @PostMapping("/print/{id}")
    public String printWork(@PathVariable("id") int id, @RequestParam("update-print-time") String updatePrintTime, Model model) {
        LocalDateTime now = LocalDateTime.now();
        PoundBillModel poundBillModel = printService.selectById(id);
        if (updatePrintTime.isEmpty()) {
            if (poundBillModel.getPrintTime() == null) {
                poundBillModel.setPrintTime(now);
            }
        } else {
            if (updatePrintTime.contains("：")) {
                updatePrintTime = updatePrintTime.replace("：", ":");
            }
            String currentDateTimeString = now.toLocalDate() + " " + updatePrintTime;
            LocalDateTime dateTime = LocalDateTime.parse(currentDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            poundBillModel.setPrintTime(dateTime);
        }

        String[] data = new String[11];
        data[0] = now.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));;
        data[1] = poundBillModel.getPoundID();
        data[2] = poundBillModel.getPlateNumber();
        data[3] = poundBillModel.getCoalType();
        data[4] = poundBillModel.getOutputUnit();
        data[5] = poundBillModel.getInputUnit();
        data[6] = String.valueOf(poundBillModel.getGrossWeight());
        data[7] = String.valueOf(poundBillModel.getTareWeight());
        data[8] = String.valueOf(poundBillModel.getNetWeight());
        data[9] = now.format(DateTimeFormatter.ofPattern("HH:mm"));
        data[10] = poundBillModel.getWeigher();

        BufferedImage image = ImageUtil.createImage(data);
        ImageUtil.printRun(image);

        poundBillService.updateById(poundBillModel, poundBillModel.getId());

        model.addAttribute("message", "已请求打印任务");
        model.addAttribute("poundBillModel", poundBillModel);

        return "/print";
    }
}
