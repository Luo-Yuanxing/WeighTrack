package io.github.weightrack.service;

import io.github.weightrack.mapper.RecycleBinMapper;
import io.github.weightrack.module.PoundBillModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecycleBinService {

    @Autowired
    RecycleBinMapper recycleBinMapper;

    public PoundBillModel[] getRemoved(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return recycleBinMapper.getRemoved(offset, pageSize);
    }



    public int getPageNumber() {
        return recycleBinMapper.getPageNumber();
    }

    public boolean recover(int id) {
        return recycleBinMapper.recover(id) == 1;
    }

    public boolean delete(int id) {
        return recycleBinMapper.delete(id) == 1;
    }

    public int deleteAll() {
        return recycleBinMapper.deleteAll();
    }

    public Object showListIsRemoved(String plateNumber, String poundId) {
        if (plateNumber == null || plateNumber.isEmpty()) {
            plateNumber = "";
        }
        if (poundId == null || poundId.isEmpty()) {
            poundId = "";
        }
        return recycleBinMapper.showListIsRemoved(plateNumber, poundId);
    }
}
