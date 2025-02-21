package io.github.weightrack.service;

import io.github.weightrack.mapper.WeigherMapper;
import io.github.weightrack.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeigherService {
    @Autowired
    WeigherMapper weigherMapper;
    public String[] getAllWeighers() {
        User[] weighers = weigherMapper.getAllWeighers();
        String[] weigherNames = new String[weighers.length];
        for (int i = 0; i < weighers.length; i++) {
            weigherNames[i] = weighers[i].getRealName();
        }
        return weigherNames;
    }

}
