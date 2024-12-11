package io.github.weightrack.service;

import com.alibaba.fastjson2.JSON;
import io.github.weightrack.mapper.CoalTypeMapper;
import io.github.weightrack.mapper.DataSummaryMapper;
import io.github.weightrack.module.CoalType;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.module.SummaryTable;
import io.github.weightrack.utils.DateUtil;
import org.intellij.lang.annotations.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DataSummaryService {

    @Autowired
    CoalTypeMapper coalTypeMapper;

    @Autowired
    DataSummaryMapper dataSummaryMapper;

    public List<Map<String, Object>> getCountGroupByCoalType(String IOType, String start, String end) {
        List<Map<String, Object>> countByCoalTypes = dataSummaryMapper.getCountGroupByCoalType(IOType, start, end);
        System.out.println(countByCoalTypes);
        return countByCoalTypes;
    }

    public List<Map<String, Object>> getCountGroupByDate(@Pattern("^([10])$") String IOType, String start, String end) {
        CoalType[] ct = coalTypeMapper.getCoalType();
        String[] coalTypes = CoalType.coalTypesToNames(ct);

        List<Map<String, Object>> countGroupByDate;
        countGroupByDate = dataSummaryMapper.getCountGroupByDate(IOType, start, end, coalTypes);
        return countGroupByDate;
    }
}
