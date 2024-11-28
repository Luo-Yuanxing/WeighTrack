package io.github.weightrack.Service;

import io.github.weightrack.Mapper.ShowListMapper;
import io.github.weightrack.module.PoundBillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowListService {
    @Autowired
    ShowListMapper showListMapper;
    public PoundBillModel[] showList() {
        PoundBillModel[] poundBillModels = showListMapper.showList();

        return poundBillModels;
    }
}
