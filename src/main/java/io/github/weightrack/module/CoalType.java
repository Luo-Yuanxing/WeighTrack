package io.github.weightrack.module;

import lombok.Data;

@Data
public class CoalType {
    private String name;
    private int id;

    public static String[] coalTypesToNames (CoalType[] coalTypes) {

        String[] names = new String[coalTypes.length];
        for (int i = 0; i < coalTypes.length; i++) {
            names[i] = coalTypes[i].getName();
        }
        return names;
    }
}
