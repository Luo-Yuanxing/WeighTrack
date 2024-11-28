package io.github.weightrack.module;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class PoundBillModel {

    private int id;
    private LocalTime date;
    private String IOType;
    private String coalType;
    private String plateNumber;

    // String 版本为controller接受参数临时使用，service校验再进行正常版本的赋值
    private float grossWeight;
    private String grossWeightString;

    private float tareWeight;
    private String tareWeightString;

    private float netWeight;
    private String netWeightString;

    private float primaryWeight;
    private String primaryWeightString;

    private float ProfitLossWeight;
    private String ProfitLossWeightString;

    private LocalDateTime emptyLoadTime;
    private String emptyLoadTimeString;
    private LocalDateTime fullLoadTime;
    private String fullLoadTimeString;
    private String outputUnit;
    private String inputUnit;
    private String weigher;

    private LocalDateTime creatTime;
    private LocalDateTime modifyTime;

    private User creator;
}
