package io.github.weightrack.service;

import io.github.weightrack.mapper.ShowListMapper;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.module.TodaySummaryPanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShowListService {
    @Autowired
    ShowListMapper showListMapper;

    public PoundBillModel[] showList(String option, int pageSize, int pageNumber) {
        int offset = (pageNumber - 1) * pageSize; // 计算偏移量
        return switch (option) {
            case "in" -> showListMapper.showList("1", offset, pageSize);
            case "out" -> showListMapper.showList("0", offset, pageSize);
            case "return" -> showListMapper.showList("2", offset, pageSize);
            case "turnover" -> showListMapper.showList("3", offset, pageSize);
//            case "all" -> showListMapper.showListAll(offset, pageSize);
            default -> throw new RuntimeException("option error");
        };
    }

    public PoundBillModel[] showTodayList(String option, int pageSize, int pageNumber) {
        int offset = (pageNumber - 1) * pageSize; // 计算偏移量
        return switch (option) {
            case "in" -> showListMapper.showTodayList("1", offset, pageSize);
            case "out" -> showListMapper.showTodayList("0", offset, pageSize);
            case "return" -> showListMapper.showTodayList("2", offset, pageSize);
            case "turnover" -> showListMapper.showTodayList("3", offset, pageSize);
//            case "all" -> showListMapper.showTodayListAll(offset, pageSize);
            default -> throw new RuntimeException("option error");
        };
    }

    public int getPageNumber(int pageSize, boolean isToday, String IOType) {
        if (isToday) {
            return switch (IOType) {
                case "in" -> showListMapper.showTodayListCount("1") / pageSize + 1;
                case "out" -> showListMapper.showTodayListCount("0") / pageSize + 1;
                case "return" -> showListMapper.showTodayListCount("2") / pageSize + 1;
//                case "all" -> showListMapper.showTodayListAllCount() / pageSize + 1;
                case "turnover" -> showListMapper.showTodayListCount("3") / pageSize + 1;
                default -> throw new RuntimeException("option error");
            };
        } else {
            return switch (IOType) {
                case "in" -> showListMapper.showListCount("1") / pageSize + 1;
                case "out" -> showListMapper.showListCount("0") / pageSize + 1;
                case "return" -> showListMapper.showListCount("2") / pageSize + 1;
//                case "all" -> showListMapper.showListAllCount() / pageSize + 1;
                case "turnover" -> showListMapper.showListCount("3") / pageSize + 1;
                default -> throw new RuntimeException("option error");
            };
        }
    }

    public PoundBillModel[] showListByPlateNumber(String plateNumber, String filter, String poundId) {
        if (plateNumber == null || plateNumber.isEmpty()) {
            plateNumber = "";
        }
        if (poundId == null || poundId.isEmpty()) {
            poundId = "";
        }
        return switch (filter) {
            case "not-in-pound" -> showListMapper.showListByPlateNumberNotInPound(plateNumber, poundId);
            case "already-in-pound" -> showListMapper.showListByPlateNumberAlreadyInPound(plateNumber, poundId);
            case "printed" -> showListMapper.showListByPlateNumberPrinted(plateNumber, poundId);
            default -> {
                log.error("filter error");
                yield null;
            }
        };
    }

    public TodaySummaryPanel getTodaySummaryPanel(String IOType) {
        TodaySummaryPanel todaySummaryPanel = new TodaySummaryPanel();
        todaySummaryPanel.setRegistered(showListMapper.getTodayCount(IOType));
        todaySummaryPanel.setEmptyWeighing(showListMapper.getTodayEmptyWeighing(IOType));
        todaySummaryPanel.setFullWeighing(showListMapper.getTodayFullWeighing(IOType));
        return todaySummaryPanel;
    }
}
