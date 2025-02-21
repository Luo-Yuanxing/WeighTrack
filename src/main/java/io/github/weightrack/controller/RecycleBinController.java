package io.github.weightrack.controller;

import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.service.RecycleBinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RecycleBinController {

    @Autowired
    RecycleBinService recycleBinService;

    @GetMapping("/recycle-bin")
    public String recycleBin(Model model,
                             @RequestParam("pageNum") String pageNum,
                             @RequestParam("pageSize") String pageSize) {
        PoundBillModel[] poundBillModels = recycleBinService.getRemoved(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        model.addAttribute("poundBillModels", poundBillModels);
        return "recycle-bin";
    }

    @ResponseBody
    @GetMapping("/recycle-bin/page_num")
    public int pageNumber(@RequestParam("pageSize") String pageSize) {
        int pageSizeInt;
        try {
            pageSizeInt = Integer.parseInt(pageSize);
        } catch (Exception e) {
            pageSizeInt = 50;
        }
        return recycleBinService.getPageNumber() / pageSizeInt + 1;
    }

    @ResponseBody
    @GetMapping("/recycle-bin/recover/{id}")
    public boolean recover(@PathVariable int id) {
        log.info("恢复回收站数据，id = {}", id);
        return recycleBinService.recover(id);
    }
    @ResponseBody
    @GetMapping("/recycle-bin/delete/{id}")
    public boolean delete(@PathVariable int id) {
        log.info("删除回收站数据，id = {}", id);
        return recycleBinService.delete(id);
    }

    @ResponseBody
    @GetMapping("/recycle-bin/delete-all")
    public void deleteAll() {
        int length = recycleBinService.deleteAll();
        log.info("回收站已清空，清理{}条数据", length);
    }

    @GetMapping("/recycle/plate-number-filter")
    public String plateNumberFilter(Model model,
                                    @RequestParam("plateNumber") String plateNumber,
                                    @RequestParam("poundId") String poundId) {
        model.addAttribute("poundBillModels", recycleBinService.showListIsRemoved(plateNumber, poundId));
        return "recycle-bin";
    }

}
