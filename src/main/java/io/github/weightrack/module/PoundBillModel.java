package io.github.weightrack.module;

import io.github.weightrack.dto.UpdateDTO;
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
    private String printTimeString;
    private boolean printed;

    private int creatorId;

    private String poundID;

    public static PoundBillModel createPoundBillModel(String creatorId,
                                                      String IOType,
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
        poundBillModel.setCreatorId(Integer.parseInt(creatorId));
        return poundBillModel;
    }

    public static PoundBillModel fromDTO(UpdateDTO updateDTO) {
        PoundBillModel poundBillModel = new PoundBillModel();
        poundBillModel.setIOType(updateDTO.getIOType());
        poundBillModel.setCoalType(updateDTO.getCoalType());
        poundBillModel.setPlateNumber(updateDTO.getPlateNumber());
        poundBillModel.setGrossWeightString(updateDTO.getGrossWeight());
        poundBillModel.setTareWeightString(updateDTO.getTare());
        poundBillModel.setPrimaryWeightString(updateDTO.getPrimaryWeight());
        poundBillModel.setPrintTimeString(updateDTO.getPrintTime());
        poundBillModel.setOutputUnit(updateDTO.getOutputUnit());
        poundBillModel.setInputUnit(updateDTO.getInputUnit());
        return poundBillModel;
    }
}