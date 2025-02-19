package io.github.weightrack.service;

import io.github.weightrack.mapper.CoalTypeMapper;
import io.github.weightrack.mapper.DataSummaryMapper;
import io.github.weightrack.module.CoalType;
import org.intellij.lang.annotations.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataSummaryService {

    @Autowired
    CoalTypeMapper coalTypeMapper;

    @Autowired
    DataSummaryMapper dataSummaryMapper;

    public List<Map<String, Object>> getCountGroupByCoalType(String IOType, String start, String end) {
        return dataSummaryMapper.getCountGroupByCoalType(IOType, start, end);
    }

    public List<Map<String, Object>> getCountGroupByDate(@Pattern("^([102])$") String IOType, String start, String end) {
        CoalType[] ct = coalTypeMapper.getCoalType();
        String[] coalTypes = CoalType.coalTypesToNames(ct);

        List<Map<String, Object>> countGroupByDate = dataSummaryMapper.getCountGroupByDate(IOType, start, end, coalTypes);

        for (Map<String, Object> map : countGroupByDate) {
            // 安全处理：获取值并转换为字符串，处理null和空值
            Object unitValue = map.get("unit");
            String unitStr = (unitValue != null) ? unitValue.toString() : "";

            if (unitStr.isEmpty()) {
                map.put("unit", "#");
                continue;
            }

            String[] units = unitStr.split(",");
            Set<String> set = new HashSet<>(Arrays.asList(units));
            set.remove("");
            map.put("unit", set.isEmpty() ? "#" : String.join(",", set));
        }
        return countGroupByDate;
    }
}
