package io.github.weightrack.dto;

import lombok.Data;

@Data
public class UpdateDTO {
    private String id;
    private String IOType;
    private String coalType;
    private String plateNumber;
    private String grossWeight;
    private String tare;
    private String primaryWeight;
    private String printTime;
    private String outputUnit;
    private String inputUnit;
    private String note;
}
