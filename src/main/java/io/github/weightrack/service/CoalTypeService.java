package io.github.weightrack.service;

import io.github.weightrack.mapper.CoalTypeMapper;
import io.github.weightrack.module.CoalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoalTypeService {

    @Autowired
    CoalTypeMapper coalTypeMapper;

    public CoalType[] getCoalTypes() {
        return coalTypeMapper.getCoalType();
    }

    public void insertCoalType(String coalType) {
        coalType = coalType.strip();
        for (CoalType type : coalTypeMapper.getCoalType()) {
            if (type.getName().equals(coalType)) {
                return;
            }
        }
        if (coalType.isEmpty()) {
            return;
        }
        coalTypeMapper.insertCoalType(coalType);
    }

    public void deleteCoalTypeByName(String name) {
        coalTypeMapper.deleteCoalTypeByName(name);
    }
}
