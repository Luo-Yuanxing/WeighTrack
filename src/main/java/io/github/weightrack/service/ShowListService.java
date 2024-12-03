package io.github.weightrack.service;

import io.github.weightrack.mapper.ShowListMapper;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowListService {
    @Autowired
    ShowListMapper showListMapper;

    public PoundBillModel[] showList(String option) {
        return switch (option) {
            case "in" -> showListMapper.showList("1");
            case "out" -> showListMapper.showList("0");
            case "all" -> showListMapper.showListAll();
            default -> throw new RuntimeException("option error");
        };
    }

    public PoundBillModel[] showTodayList(String option) {
        return switch (option) {
            case "in" -> showListMapper.showTodayList("1");
            case "out" -> showListMapper.showTodayList("0");
            case "all" -> showListMapper.showTodayListAll();
            default -> throw new RuntimeException("option error");
        };
    }
}
