package io.github.weightrack.controller;

import io.github.weightrack.dto.PrintDTO;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.PoundBillService;
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

    @Autowired
    PrintService printService;

    @Autowired
    PoundBillService poundBillService;

    // 使用一个线程安全的队列来串行化请求
    private final BlockingQueue<Runnable> printQueue = new LinkedBlockingQueue<>();

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
    public String printWork(@PathVariable("id") int id, @RequestBody PrintDTO printDTO) {
        // 将打印请求封装成一个任务，放入队列中
        boolean addedToQueue = printQueue.offer(() -> {
            LocalDateTime now = LocalDateTime.now();
            PoundBillModel poundBillModel = printService.selectById(id);
            if (printDTO.isUpdatePrintTime()) {
                String updatePrintTime = printDTO.getUpdatePrintTime();
                if (updatePrintTime.contains("：")) {
                    updatePrintTime = updatePrintTime.replace("：", ":");
                }
                String currentDateTimeString = now.toLocalDate() + " " + updatePrintTime;
                LocalDateTime dateTime = LocalDateTime.parse(currentDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                poundBillModel.setPrintTime(dateTime);
                log.info("id: {} update print time to {}", poundBillModel.getId(), poundBillModel.getPrintTime());
            } else {
                if (poundBillModel.getPrintTime() == null) {
                    poundBillModel.setPrintTime(now);
                }
            }

            String[] data = new String[11];
            data[0] = poundBillModel.getCreatTime().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
            data[1] = poundBillModel.getPoundID();
            data[2] = poundBillModel.getPlateNumber();
            data[3] = poundBillModel.getCoalType();
            data[4] = poundBillModel.getOutputUnit();
            data[5] = poundBillModel.getInputUnit();
            data[6] = String.valueOf(poundBillModel.getGrossWeight());
            data[7] = String.valueOf(poundBillModel.getTareWeight());
            data[8] = String.valueOf(poundBillModel.getNetWeight());
            data[9] = poundBillModel.getPrintTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            data[10] = poundBillModel.getWeigher();

            BufferedImage image = ImageUtil.createImage(data);
            ImageUtil.printRun(image);
            log.info("print to id: {}", poundBillModel.getId());
            poundBillModel.setPrinted(true);
            printService.updateById(poundBillModel.getId(), poundBillModel.getPrintTime());
        });
        if (!addedToQueue) {
            log.error("Failed to add print task to queue");
        }
        return "ok";
    }
}