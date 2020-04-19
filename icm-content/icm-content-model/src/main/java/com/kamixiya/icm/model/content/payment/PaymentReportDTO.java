package com.kamixiya.icm.model.content.payment;

import com.kamixiya.icm.model.content.purchase.AttachDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationBaseDTO;
import com.kamixiya.icm.model.security.user.UserBaseDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@ApiModel(description = "事前资金申请详细信息")
@Getter
@Setter
@ToString
public class PaymentReportDTO extends PaymentReportInfoDTO {
    @ApiModelProperty(position = 110, value = "状态")
    private StateType state;

    @ApiModelProperty(position = 710, value = "申请单位")
    private OrganizationBaseDTO unit;

    @ApiModelProperty(position = 720, value = "申请部门")
    private OrganizationBaseDTO department;

    @ApiModelProperty(position = 730, value = "申请人")
    private UserBaseDTO declarer;

    @ApiModelProperty(position = 731, value = "是否释放")
    private Boolean isRelease;

    @ApiModelProperty(position = 740, value = "会议费")
    private List<PaymentReportMeetingDTO> meetings;

    @ApiModelProperty(position = 750, value = "培训费")
    private List<PaymentReportTrainingDTO> trainings;

    @ApiModelProperty(position = 760, value = "因公出国（境）")
    private List<PaymentReportAbroadExpenseDTO> abroadExpenses;

    @ApiModelProperty(position = 770, value = "接待对象")
    private List<PaymentReportGuestDTO> guests;

    @ApiModelProperty(position = 780, value = "主要行程安排")
    private List<PaymentReportItineraryDTO> itineraries;

    @ApiModelProperty(position = 790, value = "住宿安排")
    private List<PaymentReportAccommodationDTO> accommodations;

    @ApiModelProperty(position = 800, value = "接待费用")
    private List<PaymentReportGuestExpenseDTO> guestExpenses;

    @ApiModelProperty(position = 810, value = "差旅费")
    private List<PaymentReportTravelExpenseDTO> travelExpenses;

    @ApiModelProperty(position = 820, value = "劳务费")
    private List<PaymentReportLabourExpenseDTO> labourExpenses;

    @ApiModelProperty(position = 830, value = "合同付款信息")
    private List<PaymentReportContractDTO> contracts;

    @ApiModelProperty(position = 840, value = "事前资金详细信息（一般经费）")
    private List<PaymentReportGeneralDetailDTO> paymentDetails;

    @ApiModelProperty(position = 970, value = "会议费指标信息")
    private List<PaymentReportIndexDTO> meetingIndexes;

    @ApiModelProperty(position = 980, value = "培训费指标信息")
    private List<PaymentReportIndexDTO> trainingIndexes;

    @ApiModelProperty(position = 990, value = "因公出国（境）指标信息")
    private List<PaymentReportIndexDTO> abroadIndexes;

    @ApiModelProperty(position = 1000, value = "公务接待指标信息")
    private List<PaymentReportIndexDTO> officialIndexes;

    @ApiModelProperty(position = 1010, value = "差旅费指标信息")
    private List<PaymentReportIndexDTO> travelIndexes;

    @ApiModelProperty(position = 1020, value = "劳务费指标信息")
    private List<PaymentReportIndexDTO> serviceIndexes;

    @ApiModelProperty(position = 1030, value = "公务用车经指标信息")
    private List<PaymentReportIndexDTO> officialCarIndexes;

    @ApiModelProperty(position = 1040, value = "合同资金指标信息")
    private List<PaymentReportIndexDTO> contractIndexes;

    @ApiModelProperty(position = 1050, value = "非合同采购指标信息")
    private List<PaymentReportIndexDTO> purchaseIndexes;

    @ApiModelProperty(position = 1060, value = "一般经费指标信息")
    private List<PaymentReportIndexDTO> generalIndexes;

    @ApiModelProperty(position = 910, value = "附件信息")
    private List<AttachDTO> attaches;

    @ApiModelProperty(position = 960, value = "创建数据的用户ID，系统根据token自动填写", required = true)
    private UserBaseDTO createUser;

    @ApiModelProperty(position = 970, value = "创建数据的时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date createdTime;

    @ApiModelProperty(position = 980, value = "最后修改者的ID，系统根据token自动填写", required = true)
    private UserBaseDTO lastModifyUser;

    @ApiModelProperty(position = 990, value = "最后的修收时间，系统根据服务器时间自动填写", required = true, example = "2018-12-31 23:59:59")
    private Date lastModifiedTime;
}
