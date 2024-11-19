package io.github.weightrack.module;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Data
public class PoundBillModel {
    // 打印接收到的数据
    private LocalTime date;
    private String coalType;
    private String plateNumber;
    private float grossWeight;
    private float tare;
    private float netWeight;
    private float primaryWeight;
    private float ProfitLossWeight;
    private LocalTime emptyLoadTime;
    private LocalTime fullLoadTime;
    private String outputUnit;
    private String inputUnit;
    private String weigher;
}
