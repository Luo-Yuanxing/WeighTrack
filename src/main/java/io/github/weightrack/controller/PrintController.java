package io.github.weightrack.controller;

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

    public PrintController() {
        // 启动一个线程来按顺序处理队列中的打印任务
        new Thread(() -> {
            try {
                while (true) {
                    Runnable task = printQueue.take();  // 阻塞式等待任务
                    task.run();  // 执行任务
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

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
}