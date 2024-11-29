package io.github.weightrack.controller;

import io.github.weightrack.Service.PoundBillService;
import io.github.weightrack.Service.PrintService;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.utils.ImagePrinter;
import io.github.weightrack.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/print/{id}")
    public String print(@PathVariable("id") int id, Model model) {
        PoundBillModel poundBillModel = printService.selectById(id);
        model.addAttribute("poundBillModel", poundBillModel);
        return "/print";
    }

    @PostMapping("/print/{id}")
    public String printWork(@PathVariable("id") int id, Model model) {

        PoundBillModel poundBillModel = printService.selectById(id);
        LocalDateTime now = LocalDateTime.now();

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
        data[9] = now.format(DateTimeFormatter.ofPattern("HH:mm"));;
        data[10] = poundBillModel.getWeigher();

        BufferedImage image = ImageUtil.createImage(data);
        ImageUtil.printRun(image);

        model.addAttribute("message", "已请求打印任务");
        model.addAttribute("poundBillModel", poundBillModel);
        return "/print";
    }
}
