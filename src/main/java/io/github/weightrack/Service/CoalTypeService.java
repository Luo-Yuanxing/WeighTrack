package io.github.weightrack.Service;

import io.github.weightrack.Mapper.CoalTypeMapper;
import io.github.weightrack.module.CoalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoalTypeService {

    @Autowired
    CoalTypeMapper coalTypeMapper;

    public CoalType[] getCoalType() {
        return coalTypeMapper.getCoalType();
    }

    public void insertCoalType(String coalType) {
        coalTypeMapper.insertCoalType(coalType);
    }

    public void deleteCoalTypeByName(String name) {
        coalTypeMapper.deleteCoalTypeByName(name);
    }
}
