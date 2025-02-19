package io.github.weightrack.service;

import io.github.weightrack.mapper.PoundBillMapper;
import io.github.weightrack.mapper.printMapper;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Service
public class PrintService {

    @Autowired
    PoundBillMapper poundBillMapper;
    @Autowired
    printMapper printMapper;

    public PoundBillModel selectById(int id) {
        PoundBillModel poundBillModel = poundBillMapper.selectById(id);
        int count = printMapper.getTodayPrintedCount(poundBillModel.getIOType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String dateStr = dateFormat.format(new Date(System.currentTimeMillis()));

        String countStr = String.format("%04d", count + 1);
        String poundID = "E"; // 若E为开头则出bug
        switch (poundBillModel.getIOType()) {
            case "1" -> poundID = "R" + dateStr + countStr;
            case "0" -> poundID = "C" + dateStr + countStr;
            case "2" -> poundID = "F" + dateStr + countStr;
        }
        if (poundBillModel.getPoundID() == null || poundBillModel.getPoundID().isEmpty()) {
            poundBillModel.setPoundID(poundID);
        }
        poundBillModel.setPrinted(true);
        return poundBillModel;
    }

    public void updateById(int id, LocalDateTime printTime, String poundID) {
        printMapper.updateById(id, printTime, poundID);

    }

    public PoundBillModel[] getListByIds(String ids) {
        return printMapper.getListByIds(ids);
    }
}
