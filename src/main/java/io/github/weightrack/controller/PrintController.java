package io.github.weightrack.controller;

import com.alibaba.fastjson2.JSON;
import io.github.weightrack.dto.PoundBillListDTO;
import io.github.weightrack.dto.PrintDTO;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.PrintService;
import io.github.weightrack.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Controller
public class PrintController {

    // 使用一个线程安全的队列来串行化请求
    private final BlockingQueue<Runnable> printQueue = new LinkedBlockingQueue<>();
    @Autowired
    PrintService printService;

    @GetMapping("/print/{id}")
    public String print(@PathVariable("id") int id, Model model) {
        PoundBillModel poundBillModel = printService.selectById(id);
        model.addAttribute("poundBillModel", poundBillModel);
        return "print";
    }

    @ResponseBody
    @PostMapping("/print/{id}")
    public String printWork(@PathVariable("id") int id) {
        // 将打印请求封装成一个任务，放入队列中
        LocalDateTime now = LocalDateTime.now();
        PoundBillModel poundBillModel = printService.selectById(id);
        if (poundBillModel.getPrintTime() == null) {
            poundBillModel.setPrintTime(now);
        }
        log.info("print id: {}", poundBillModel.getId());
        poundBillModel.setPrinted(true);
        printService.updateById(poundBillModel.getId(), poundBillModel.getPrintTime(), poundBillModel.getPoundID());
        return "ok";
    }

    /**
     * 获取待打印数组数据，批量打印接口
     * @param poundBillListDTO poundBillListDTO.ids = [int]
     * @return JSON
     */
    @ResponseBody
    @PostMapping("/api/print/list")
    public String getPoundBillList(@RequestBody PoundBillListDTO poundBillListDTO) {
        String ids = String.join(",", poundBillListDTO.getIds());
        PoundBillModel[] poundBillModels = new PoundBillModel[poundBillListDTO.getIds().length];
        int index = 0;
        for (String id : poundBillListDTO.getIds()) {
            LocalDateTime now = LocalDateTime.now();
            PoundBillModel poundBillModel;
            try {
                poundBillModel = printService.selectById(Integer.parseInt(id));
            } catch (Exception e) {
                log.error("id: {} 打印失败，不能解析为 int", id);
                continue;
            }

            if (poundBillModel.getPrintTime() == null) {
                poundBillModel.setPrintTime(now);
            }
            poundBillModel.setPrinted(true);
            printService.updateById(poundBillModel.getId(), poundBillModel.getPrintTime(), poundBillModel.getPoundID());
            poundBillModels[index++] = poundBillModel;
        }
        log.info("id: {}已批量打印", ids);
        return JSON.toJSONString(poundBillModels);
    }
}