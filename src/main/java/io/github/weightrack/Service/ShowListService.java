package io.github.weightrack.Service;

import io.github.weightrack.Mapper.ShowListMapper;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowListService {
    @Autowired
    ShowListMapper showListMapper;
    public PoundBillModel[] showList(String option) {
        if (option.equals("in")) {
            return showListMapper.showList("1");
        } else if (option.equals("out")) {
            return showListMapper.showList("0");
        } else if (option.equals("all")) {
            return showListMapper.showList(null);
        } else {
            throw new RuntimeException("option error");
        }
    }
}
