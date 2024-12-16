package io.github.weightrack.module;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PoundBillModel {

    private int id;

    // IOType: {'1': 'in', '2': 'out'}
    private String IOType;
    private String coalType;
    private String plateNumber;

    // String 版本为controller接受参数临时使用，service校验再进行正常版本的赋值
    private double grossWeight;
    private String grossWeightString;

    private double tareWeight;
    private String tareWeightString;

    private double netWeight;
    private String netWeightString;

    private double primaryWeight;
    private String primaryWeightString;

    private double ProfitLossWeight;
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
    private LocalDateTime printTime;
    private boolean printed;

    private int creatorId;

    private String poundID;

    public static PoundBillModel createPoundBillModel(String IOType,
                                                      String coalType,
                                                      String plateNumber,
                                                      String grossWeight,
                                                      String tare,
                                                      String primaryWeight,
                                                      String outputUnit,
                                                      String inputUnit,
                                                      String weigher) {
        PoundBillModel poundBillModel = new PoundBillModel();
        poundBillModel.setIOType(IOType);
        poundBillModel.setCoalType(coalType);
        poundBillModel.setPlateNumber(plateNumber);
        poundBillModel.setGrossWeightString(grossWeight);
        poundBillModel.setTareWeightString(tare);
        poundBillModel.setPrimaryWeightString(primaryWeight);
        poundBillModel.setOutputUnit(outputUnit);
        poundBillModel.setInputUnit(inputUnit);
        poundBillModel.setWeigher(weigher);

        return poundBillModel;
    }

    public void updatePrintTime(String printTime) {
        if (printTime == null) {
            this.setPrintTime(null);
            return;
        }
        if (printTime.isEmpty()) {
            this.setPrintTime(null);
            return;
        }
        if (printTime.contains("：")) {
            printTime = printTime.replace("：", ":");
        }
        int hour = Integer.parseInt(printTime.split(":")[0]);
        int minute = Integer.parseInt(printTime.split(":")[1]);
        LocalDateTime printTime_ = this.getCreatTime().withHour(hour).withMinute(minute).withSecond(0);
        this.setPrintTime(printTime_);
    }

    public PoundBillModel updatePoundBillModel(PoundBillModel newPoundBillModel) {
        if (newPoundBillModel.IOType != null) {
            this.IOType = newPoundBillModel.IOType;
        }
        if (newPoundBillModel.coalType != null) {
            this.coalType = newPoundBillModel.coalType;
        }
        if (newPoundBillModel.plateNumber != null) {
            this.plateNumber = newPoundBillModel.plateNumber;
        }

        if (newPoundBillModel.grossWeight != 0) {
            this.grossWeight = newPoundBillModel.grossWeight;
        }
        if (newPoundBillModel.tareWeight != 0) {
            this.tareWeight = newPoundBillModel.tareWeight;
        }
        if (newPoundBillModel.netWeight != 0) {
            this.netWeight = newPoundBillModel.netWeight;
        }
        if (newPoundBillModel.primaryWeight != 0) {
            this.primaryWeight = newPoundBillModel.primaryWeight;
        }
        if (newPoundBillModel.ProfitLossWeight != 0) {
            this.ProfitLossWeight = newPoundBillModel.ProfitLossWeight;
        }

        // 更新时间信息（忽略 String 版本字段）
        if (newPoundBillModel.emptyLoadTime != null) {
            this.emptyLoadTime = newPoundBillModel.emptyLoadTime;
        }
        if (newPoundBillModel.fullLoadTime != null) {
            this.fullLoadTime = newPoundBillModel.fullLoadTime;
        }
        // 更新时间字段
        if (this.printTime != null && newPoundBillModel.printTime != null) {
            this.printTime = newPoundBillModel.printTime;
        }

        // 更新其他字段
        if (newPoundBillModel.outputUnit != null) {
            this.outputUnit = newPoundBillModel.outputUnit;
        }
        if (newPoundBillModel.inputUnit != null) {
            this.inputUnit = newPoundBillModel.inputUnit;
        }
        if (newPoundBillModel.weigher != null) {
            this.weigher = newPoundBillModel.weigher;
        }
        if (newPoundBillModel.printed) {
            this.printed = true;
        }
        if (newPoundBillModel.creatorId != 0) {
            this.creatorId = newPoundBillModel.creatorId;
        }
        if (newPoundBillModel.poundID != null) {
            this.poundID = newPoundBillModel.poundID;
        }
        return this;
    }

}
