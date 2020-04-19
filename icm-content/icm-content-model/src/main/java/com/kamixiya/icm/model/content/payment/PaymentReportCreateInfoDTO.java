package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.model.content.purchase.AttachEditInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请创建信息")
@Getter
@Setter
@ToString
public class PaymentReportCreateInfoDTO extends PaymentReportBaseDTO {
    @ApiModelProperty(position = 500, value = "单位ID", required = true)
    @NotBlank
    private String unitId;

    @ApiModelProperty(position = 510, value = "部门ID", required = true)
    @NotBlank
    private String departmentId;

    @ApiModelProperty(position = 520, value = "申请人Id", required = true)
    @NotBlank
    private String declarerId;

    @ApiModelProperty(position = 530, value = "关联采购备案单ID,是否关联采购为是时非空")
    private String purchaseReportId;

    @ApiModelProperty(position = 532, value = "合同ID,合同费用时非空")
    private String contractId;

    @ApiModelProperty(position = 534, value = "采购ID（非会议费）")
    private String purchaseReportId2;

    @ApiModelProperty(position = 540, value = "会议费")
    @Valid
    private List<PaymentReportMeetingEditInfoDTO> meetings;

    @ApiModelProperty(position = 550, value = "培训费")
    @Valid
    private List<PaymentReportTrainingEditInfoDTO> trainings;

    @ApiModelProperty(position = 560, value = "因公出国（境）")
    @Valid
    private List<PaymentReportAbroadExpenseEditInfoDTO> abroadExpenses;

    @ApiModelProperty(position = 570, value = "接待对象")
    @Valid
    private List<PaymentReportGuestEditInfoDTO> guests;

    @ApiModelProperty(position = 580, value = "主要行程安排")
    @Valid
    private List<PaymentReportItineraryEditInfoDTO> itineraries;

    @ApiModelProperty(position = 790, value = "住宿安排")
    @Valid
    private List<PaymentReportAccommodationEditInfoDTO> accommodations;

    @ApiModelProperty(position = 800, value = "接待费用")
    @Valid
    private List<PaymentReportGuestExpenseEditInfoDTO> guestExpenses;

    @ApiModelProperty(position = 810, value = "差旅费")
    @Valid
    private List<PaymentReportTravelExpenseEditInfoDTO> travelExpenses;

    @ApiModelProperty(position = 820, value = "劳务费")
    @Valid
    private List<PaymentReportLabourExpenseEditInfoDTO> labourExpenses;

    @ApiModelProperty(position = 830, value = "合同付款信息")
    @Valid
    private List<PaymentReportContractEditInfoDTO> contracts;

    @ApiModelProperty(position = 840, value = "事前资金详细信息（一般经费）")
    @Valid
    private List<PaymentReportGeneralDetailEditInfoDTO> paymentDetails;

    @ApiModelProperty(position = 960, value = "附件信息")
    @Valid
    private List<AttachEditInfoDTO> attaches;

    @ApiModelProperty(position = 970, value = "会议费指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> meetingIndexes;

    @ApiModelProperty(position = 980, value = "培训费指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> trainingIndexes;

    @ApiModelProperty(position = 990, value = "因公出国（境）指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> abroadIndexes;

    @ApiModelProperty(position = 1000, value = "公务接待指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> officialIndexes;

    @ApiModelProperty(position = 1010, value = "差旅费指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> travelIndexes;

    @ApiModelProperty(position = 1020, value = "劳务费指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> serviceIndexes;

    @ApiModelProperty(position = 1030, value = "公务用车经指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> officialCarIndexes;

    @ApiModelProperty(position = 1040, value = "合同资金指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> contractIndexes;

    @ApiModelProperty(position = 1050, value = "非合同采购指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> purchaseIndexes;

    @ApiModelProperty(position = 1060, value = "一般经费指标信息")
    @Valid
    private List<PaymentReportIndexEditInfoDTO> generalIndexes;
}
