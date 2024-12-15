package io.github.weightrack.service;

import io.github.weightrack.mapper.CoalTypeMapper;
import io.github.weightrack.mapper.DataSummaryMapper;
import io.github.weightrack.module.CoalType;
import org.intellij.lang.annotations.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataSummaryService {

    @Autowired
    CoalTypeMapper coalTypeMapper;

    @Autowired
    DataSummaryMapper dataSummaryMapper;

    public List<Map<String, Object>> getCountGroupByCoalType(String IOType, String start, String end) {
        //        System.out.println(countByCoalTypes);
        return dataSummaryMapper.getCountGroupByCoalType(IOType, start, end);
    }

    public List<Map<String, Object>> getCountGroupByDate(@Pattern("^([10])$") String IOType, String start, String end) {
        CoalType[] ct = coalTypeMapper.getCoalType();
        String[] coalTypes = CoalType.coalTypesToNames(ct);

        List<Map<String, Object>> countGroupByDate;
        countGroupByDate = dataSummaryMapper.getCountGroupByDate(IOType, start, end, coalTypes);
        return countGroupByDate;
    }
}
