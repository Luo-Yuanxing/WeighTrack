package io.github.weightrack.controller;

import io.github.weightrack.module.CoalType;
import io.github.weightrack.service.CoalTypeService;
import io.github.weightrack.service.DataSummaryService;
import io.github.weightrack.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class DataSummaryController {

    @Autowired
    private DataSummaryService dataSummaryService;

    @Autowired
    private CoalTypeService coalTypeService;

    @GetMapping("/summary")
    public String summary(@RequestParam("IOType") String IOType,
                          @RequestParam("date-range") String dateRange,
                          Model model) {
        // 参数验证
        validateIOType(IOType);

        // 将IOType和dateRange直接传递给视图
        model.addAttribute("IOType", IOType);
        model.addAttribute("dateRange", dateRange);

        List<Map<String, Object>> summary = getSummaryByDateRange(IOType, dateRange);

        // 返回视图结果
        model.addAttribute("summary", summary);
        model.addAttribute("coalTypes", CoalType.coalTypesToNames(coalTypeService.getCoalTypes()));
        return "summary";
    }

    // 验证IOType参数
    private void validateIOType(String IOType) {
        if (!IOType.equals("1") && !IOType.equals("0")) {
            throw new RuntimeException("无效url参数");
        }
    }

    // 根据日期范围获取数据
    private List<Map<String, Object>> getSummaryByDateRange(String IOType, String dateRange) {
        String start;
        String end;

        // 根据dateRange来设置start和end时间
        switch (dateRange) {
            case "today":
                start = DateUtil.getTodayStartTime();
                end = DateUtil.getTodayEndTime();
                return dataSummaryService.getCountGroupByCoalType(IOType, start, end);
            case "month":
                start = DateUtil.getFirstDayOfMonth();
                end = DateUtil.getLastDayOfMonth();
                break;
            case "year":
                start = DateUtil.getFirstDayOfYear();
                end = DateUtil.getLastDayOfYear();
                break;
            case "all":
                start = DateUtil.getAllStartTime();
                end = DateUtil.getAllEndTime();
                break;
            default:
                throw new IllegalArgumentException("无效的日期范围参数");
        }

        // 调用默认的获取日期范围的统计方法
        return dataSummaryService.getCountGroupByDate(IOType, start, end);
    }
}
