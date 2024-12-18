package io.github.weightrack.service;

import io.github.weightrack.mapper.ShowListMapper;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowListService {
    @Autowired
    ShowListMapper showListMapper;

    public PoundBillModel[] showList(String option, int pageSize, int pageNumber) {
        int offset = (pageNumber - 1) * pageSize; // 计算偏移量
        return switch (option) {
            case "in" -> showListMapper.showList("1", offset, pageSize);
            case "out" -> showListMapper.showList("0", offset, pageSize);
            case "all" -> showListMapper.showListAll(offset, pageSize);
            default -> throw new RuntimeException("option error");
        };
    }

    public PoundBillModel[] showTodayList(String option, int pageSize, int pageNumber) {
        int offset = (pageNumber - 1) * pageSize; // 计算偏移量
        return switch (option) {
            case "in" -> showListMapper.showTodayList("1", offset, pageSize);
            case "out" -> showListMapper.showTodayList("0", offset, pageSize);
            case "all" -> showListMapper.showTodayListAll(offset, pageSize);
            default -> throw new RuntimeException("option error");
        };
    }

    public int getPageNumber(int pageSize, boolean isToday, String IOType) {
        if (isToday) {
            return switch (IOType) {
                case "in" -> showListMapper.showTodayListCount("1") / pageSize + 1;
                case "out" -> showListMapper.showTodayListCount("0") / pageSize + 1;
                case "all" -> showListMapper.showTodayListAllCount() / pageSize + 1;
                default -> throw new RuntimeException("option error");
            };
        } else {
            return switch (IOType) {
                case "in" -> showListMapper.showListCount("1") / pageSize + 1;
                case "out" -> showListMapper.showListCount("0") / pageSize + 1;
                case "all" -> showListMapper.showListAllCount() / pageSize + 1;
                default -> throw new RuntimeException("option error");
            };
        }
    }

    public PoundBillModel[] showListByPlateNumber(String plateNumber) {
        return showListMapper.showListByPlateNumber(plateNumber);
    }
}
