package com.kamixiya.icm.service.content.service.payment;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.payment.*;
import com.kamixiya.icm.model.content.purchase.AttachEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusion;
import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusionIndex;
import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusionPayment;
import com.kamixiya.icm.persistence.content.entity.contract.ContractPaymentState;
import com.kamixiya.icm.persistence.content.entity.payment.*;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReport;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportIndex;
import com.kamixiya.icm.persistence.content.repository.contract.ContractConclusionIndexRepository;
import com.kamixiya.icm.persistence.content.repository.contract.ContractConclusionPaymentRepository;
import com.kamixiya.icm.persistence.content.repository.contract.ContractConclusionRepository;
import com.kamixiya.icm.persistence.content.repository.indexLibrary.IndexLibraryRepository;
import com.kamixiya.icm.persistence.content.repository.payment.*;
import com.kamixiya.icm.persistence.content.repository.purchase.PurchaseReportIndexRepository;
import com.kamixiya.icm.persistence.content.repository.purchase.PurchaseReportRepository;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.exception.CommonDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.repository.base.SystemFileRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.content.mapper.payment.*;
import com.kamixiya.icm.service.content.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 事前资金申请服务实现
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Service("paymentReportService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PaymentReportServiceImpl implements PaymentReportService {

    private static final String EXPENSE_TYPE_NOT_FOUND = "费用类型找不到";

    private final PaymentReportAbroadExpenseMapper abroadExpenseMapper;
    private final PaymentReportAccommodationMapper accommodationMapper;
    private final PaymentReportAttachMapper attachMapper;
    private final PaymentReportContractMapper reportContractMapper;
    private final PaymentReportGuestExpenseMapper guestExpenseMapper;
    private final PaymentReportGuestMapper guestMapper;
    private final PaymentReportIndexMapper indexMapper;
    private final PaymentReportItineraryMapper itineraryMapper;
    private final PaymentReportLabourExpenseMapper labourExpenseMapper;
    private final PaymentReportMeetingMapper meetingMapper;
    private final PaymentReportMapper reportMapper;
    private final PaymentReportTrainingMapper trainingMapper;
    private final PaymentReportTravelExpenseMapper travelExpenseMapper;
    private final PaymentReportTravelDetailMapper travelDetailMapper;
    private final PaymentReportTrainingDetailMapper trainingDetailMapper;
    private final PaymentReportTeacherMapper teacherMapper;
    private final PaymentReportMeetingDetailMapper meetingDetailMapper;
    private final PaymentReportDetailMapper reportDetailMapper;
    private final PaymentReportAbroadDetailMapper abroadDetailMapper;

    private final PaymentReportAbroadExpenseRepository abroadExpenseRepository;
    private final PaymentReportAccommodationRepository accommodationRepository;
    private final PaymentReportAttachRepository attachRepository;
    private final PaymentReportContractRepository reportContractRepository;
    private final PaymentReportGuestExpenseRepository guestExpenseRepository;
    private final PaymentReportGuestRepository guestRepository;
    private final PaymentReportIndexRepository indexRepository;
    private final PaymentReportItineraryRepository itineraryRepository;
    private final PaymentReportLabourExpenseRepository labourExpenseRepository;
    private final PaymentReportMeetingRepository meetingRepository;
    private final PaymentReportRepository reportRepository;
    private final PaymentReportTrainingRepository trainingRepository;
    private final PaymentReportTravelExpenseRepository travelExpenseRepository;
    private final PaymentReportTravelDetailRepository travelDetailRepository;
    private final PaymentReportTrainingDetailRepository trainingDetailRepository;
    private final PaymentReportTeacherRepository teacherRepository;
    private final PaymentReportMeetingDetailRepository meetingDetailRepository;
    private final PaymentReportDetailRepository reportDetailRepository;
    private final PaymentReportAbroadDetailRepository abroadDetailRepository;
    private final IndexLibraryRepository indexLibraryRepository;
    private final PurchaseReportRepository purchaseReportRepository;
    private final PurchaseReportIndexRepository purchaseReportIndexRepository;
    private final ContractConclusionRepository contractRepository;
    private final ContractConclusionIndexRepository contractIndexRepository;
    private final UserRepository userRepository;
    private final ContractConclusionPaymentRepository contractPaymentRepository;
    private final SystemFileRepository systemFileRepository;

    private final CommonUtil commonUtil;

    @Autowired
    public PaymentReportServiceImpl(PaymentReportAbroadExpenseMapper abroadExpenseMapper, PaymentReportAccommodationMapper accommodationMapper,
                                    PaymentReportAttachMapper attachMapper, PaymentReportContractMapper reportContractMapper,
                                    PaymentReportGuestExpenseMapper guestExpenseMapper, PaymentReportGuestMapper guestMapper,
                                    PaymentReportIndexMapper indexMapper, PaymentReportItineraryMapper itineraryMapper,
                                    PaymentReportLabourExpenseMapper labourExpenseMapper, PaymentReportMeetingMapper meetingMapper,
                                    PaymentReportMapper reportMapper, PaymentReportTrainingMapper trainingMapper,
                                    PaymentReportTravelExpenseMapper travelExpenseMapper, PaymentReportTravelDetailMapper travelDetailMapper,
                                    PaymentReportTrainingDetailMapper trainingDetailMapper, PaymentReportTeacherMapper teacherMapper,
                                    PaymentReportMeetingDetailMapper meetingDetailMapper, PaymentReportDetailMapper reportDetailMapper,
                                    PaymentReportAbroadDetailMapper abroadDetailMapper,
                                    PaymentReportAbroadExpenseRepository abroadExpenseRepository, PaymentReportAccommodationRepository accommodationRepository,
                                    PaymentReportAttachRepository attachRepository, PaymentReportContractRepository reportContractRepository,
                                    PaymentReportGuestExpenseRepository guestExpenseRepository, PaymentReportGuestRepository guestRepository,
                                    PaymentReportIndexRepository indexRepository, PaymentReportItineraryRepository itineraryRepository,
                                    PaymentReportLabourExpenseRepository labourExpenseRepository, PaymentReportMeetingRepository meetingRepository,
                                    PaymentReportRepository reportRepository, PaymentReportTrainingRepository trainingRepository,
                                    PaymentReportTravelExpenseRepository travelExpenseRepository,
                                    PaymentReportTravelDetailRepository travelDetailRepository, PaymentReportTrainingDetailRepository trainingDetailRepository,
                                    PaymentReportTeacherRepository teacherRepository, PaymentReportMeetingDetailRepository meetingDetailRepository,
                                    PaymentReportDetailRepository reportDetailRepository, PaymentReportAbroadDetailRepository abroadDetailRepository,
                                    IndexLibraryRepository indexLibraryRepository,
                                    PurchaseReportRepository purchaseReportRepository, PurchaseReportIndexRepository purchaseReportIndexRepository,
                                    ContractConclusionRepository contractRepository, ContractConclusionIndexRepository contractIndexRepository,
                                    UserRepository userRepository, ContractConclusionPaymentRepository contractPaymentRepository,
                                    SystemFileRepository systemFileRepository, CommonUtil commonUtil) {
        this.abroadExpenseMapper = abroadExpenseMapper;
        this.accommodationMapper = accommodationMapper;
        this.attachMapper = attachMapper;
        this.reportContractMapper = reportContractMapper;
        this.guestExpenseMapper = guestExpenseMapper;
        this.guestMapper = guestMapper;
        this.indexMapper = indexMapper;
        this.itineraryMapper = itineraryMapper;
        this.labourExpenseMapper = labourExpenseMapper;
        this.meetingMapper = meetingMapper;
        this.reportMapper = reportMapper;
        this.trainingMapper = trainingMapper;
        this.travelExpenseMapper = travelExpenseMapper;
        this.travelDetailMapper = travelDetailMapper;
        this.trainingDetailMapper = trainingDetailMapper;
        this.teacherMapper = teacherMapper;
        this.meetingDetailMapper = meetingDetailMapper;
        this.reportDetailMapper = reportDetailMapper;
        this.abroadDetailMapper = abroadDetailMapper;
        this.abroadExpenseRepository = abroadExpenseRepository;
        this.accommodationRepository = accommodationRepository;
        this.attachRepository = attachRepository;
        this.reportContractRepository = reportContractRepository;
        this.guestExpenseRepository = guestExpenseRepository;
        this.guestRepository = guestRepository;
        this.indexRepository = indexRepository;
        this.itineraryRepository = itineraryRepository;
        this.labourExpenseRepository = labourExpenseRepository;
        this.meetingRepository = meetingRepository;
        this.reportRepository = reportRepository;
        this.trainingRepository = trainingRepository;
        this.travelExpenseRepository = travelExpenseRepository;
        this.contractRepository = contractRepository;
        this.contractIndexRepository = contractIndexRepository;
        this.userRepository = userRepository;
        this.contractPaymentRepository = contractPaymentRepository;
        this.commonUtil = commonUtil;
        this.travelDetailRepository = travelDetailRepository;
        this.trainingDetailRepository = trainingDetailRepository;
        this.teacherRepository = teacherRepository;
        this.meetingDetailRepository = meetingDetailRepository;
        this.reportDetailRepository = reportDetailRepository;
        this.abroadDetailRepository = abroadDetailRepository;
        this.indexLibraryRepository = indexLibraryRepository;
        this.purchaseReportRepository = purchaseReportRepository;
        this.purchaseReportIndexRepository = purchaseReportIndexRepository;
        this.systemFileRepository = systemFileRepository;
    }

    @Override
    public PageDataDTO<PaymentReportDTO> findOnePage(PaymentReportQueryOptionDTO paymentReportQueryOption) {
        PredicateBuilder<PaymentReport> pb = Specifications.<PaymentReport>and()
                .eq(StringUtils.isNotBlank(paymentReportQueryOption.getUnitId()), "unit.id", paymentReportQueryOption.getUnitId())
                .like(StringUtils.isNotBlank(paymentReportQueryOption.getDeclarerName()), "declarer.name", "%" + paymentReportQueryOption.getDeclarerName() + "%")
                .eq(StringUtils.isNotBlank(paymentReportQueryOption.getDepartmentId()), "department.id", paymentReportQueryOption.getDepartmentId())
                .like(StringUtils.isNotBlank(paymentReportQueryOption.getTitle()), "title", "%" + paymentReportQueryOption.getTitle() + "%")
                .ge(null != paymentReportQueryOption.getBeginAmount(), "total", paymentReportQueryOption.getBeginAmount())
                .le(null != paymentReportQueryOption.getEndAmount(), "total", paymentReportQueryOption.getEndAmount())
                .ge(null != paymentReportQueryOption.getBeginDate(), "applyDate", paymentReportQueryOption.getBeginDate())
                .le(null != paymentReportQueryOption.getEndDate(), "applyDate", paymentReportQueryOption.getEndDate())
                .eq(null != paymentReportQueryOption.getExpenseType(), "expenseType", paymentReportQueryOption.getExpenseType())
                .predicate(StringUtils.isNotBlank(paymentReportQueryOption.getCode()), (root, criteriaQuery, criteriaBuilder) -> {
                    Join purchaseReportJoin = root.join("purchaseReport", JoinType.LEFT);
                    Join purchaseReport2Join = root.join("purchaseReport2", JoinType.LEFT);
                    return criteriaBuilder.or(
                            criteriaBuilder.equal(purchaseReportJoin.get("code").as(String.class), paymentReportQueryOption.getCode()),
                            criteriaBuilder.equal(purchaseReport2Join.get("code").as(String.class), paymentReportQueryOption.getCode())
                    );
                });
        Page<PaymentReport> paymentReportPage = this.reportRepository.findAll(pb.build(), PageHelper.generatePageRequest(
                paymentReportQueryOption.getPageQueryOption().getPage(),
                paymentReportQueryOption.getPageQueryOption().getSize(),
                paymentReportQueryOption.getPageQueryOption().getSort()));
        List<PaymentReportDTO> content = this.reportMapper.toList(paymentReportPage.getContent());
        return PageDataUtil.toPageData(paymentReportPage, content);
    }

    @Override
    public PaymentReportDTO findById(String id) {
        PaymentReport paymentReport = getOne(id);
        return this.reportMapper.toDTO(paymentReport);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentReportDTO create(PaymentReportCreateInfoDTO createInfo) {
        PaymentReport report = this.reportMapper.toEntity(createInfo);
        report.setState(StateType.UNDONE);
        report.setUnit(this.commonUtil.getOrganization(createInfo.getUnitId()));
        report.setDeclarer(this.commonUtil.getUser(createInfo.getDeclarerId()));
        report.setDepartment(this.commonUtil.getOrganization(createInfo.getDepartmentId()));
        report.setIsRelease(false);
        //保存特殊关联
        this.saveSpecialRelation(report, createInfo);
        report = this.reportRepository.save(report);
        //保存关联数据
        saveRelation(report, createInfo, new HashMap<>(16), null);
        return this.reportMapper.toDTO(report);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentReportDTO complete(String id, PaymentReportEditInfoDTO editInfo) {
        PaymentReport paymentReport = getOne(id);
        //保存旧的文件id列表，用于更新时删除脏数据
        List<String> oldFileIds = new ArrayList<>();
        paymentReport.getAttaches().forEach(attach -> attach.getFiles().forEach(file -> oldFileIds.add(file.getId().toString())));
        Map<String, Map<Long, Double>> map;
        //判断会议费的前后是否都关联采购或指标库
        if (!paymentReport.getMeetingIndexes().isEmpty() && !editInfo.getMeetingIndexes().isEmpty()) {
            if ((paymentReport.getPurchaseReport() != null && StringUtils.isBlank(editInfo.getPurchaseReportId()))) {
                map = deleteRelation(paymentReport, true);
            } else if ((paymentReport.getPurchaseReport() == null && StringUtils.isNotBlank(editInfo.getPurchaseReportId()))) {
                map = deleteRelation(paymentReport, true);
            } else {
                map = deleteRelation(paymentReport, false);
            }
        } else {
            map = deleteRelation(paymentReport, false);
        }
        this.reportMapper.updateEntity(paymentReport, editInfo);
        //保存特殊关联
        this.saveSpecialRelation(paymentReport, editInfo);
        paymentReport.setState(StateType.DONE);
        if (StateType.DONE.equals(paymentReport.getState())) {
            paymentReport.setCompleteDate(new Date());
        }
        saveRelation(paymentReport, editInfo, map, oldFileIds);
        paymentReport = this.reportRepository.save(paymentReport);
        return this.reportMapper.toDTO(paymentReport);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        PaymentReport report = getOne(id);
        deleteRelation(report, true);
        this.reportRepository.delete(report);
    }

    @Override
    public PaymentReportAvailableAmountDTO getAvailable(String id, String contractId, String purchaseId, String purchaseId2, List<String> indexIds) {
        PaymentReportAvailableAmountDTO availableAmountDTO = new PaymentReportAvailableAmountDTO();
        Map<String, Double> purchaseReportIndexes = new HashMap<>(16);
        Map<String, Double> indexLibraryIndexes = new HashMap<>(16);
        Map<String, Double> contractIndexes = new HashMap<>(16);
        if (StringUtils.isNotBlank(purchaseId)) {
            purchaseReportIndexes = this.mergePurchaseReportIndexMap(purchaseId, purchaseReportIndexes);
        }
        if (StringUtils.isNotBlank(purchaseId2)) {
            purchaseReportIndexes = this.mergePurchaseReportIndexMap(purchaseId2, purchaseReportIndexes);
        }
        if (null != indexIds && !indexIds.isEmpty()) {
            List<Long> longIds = indexIds.stream().map(Long::parseLong).collect(Collectors.toList());
            List<IndexLibrary> indexLibraries = this.indexLibraryRepository.findAllById(longIds);
            Map<String, Double> availableAmount = indexLibraries.stream().collect(Collectors.toMap(index -> index.getId().toString(), IndexLibrary::getAvailableAmount));
            indexLibraryIndexes = this.mergeMap(indexLibraryIndexes, availableAmount);
        }
        if (StringUtils.isNotBlank(contractId)) {
            ContractConclusion contract = this.contractRepository.findById(Long.parseLong(contractId)).orElseThrow(() -> new NoSuchDataException(contractId));
            Map<String, Double> availableAmount = contract.getIndexes().stream().collect(Collectors.toMap(contractIndex -> contractIndex.getIndex().getId().toString(),
                    contractIndex -> new BigDecimal(contractIndex.getAmount())
                            .subtract(new BigDecimal(contractIndex.getPaidAmount()))
                            .subtract(new BigDecimal(contractIndex.getPassageAmount()))
                            .doubleValue())
            );
            contractIndexes = this.mergeMap(contractIndexes, availableAmount);
        }
        if (StringUtils.isNotBlank(id)) {
            PaymentReport report = this.getOne(id);
            if (!report.getMeetingIndexes().isEmpty()) {
                boolean isPurchase = report.getPurchase();
                if (isPurchase) {
                    purchaseReportIndexes = handleAmount(report.getMeetingIndexes(), purchaseReportIndexes);
                } else {
                    indexLibraryIndexes = handleAmount(report.getMeetingIndexes(), indexLibraryIndexes);
                }
            }
            indexLibraryIndexes = handleAmount(report.getTrainingIndexes(), indexLibraryIndexes);
            indexLibraryIndexes = handleAmount(report.getAbroadIndexes(), indexLibraryIndexes);
            indexLibraryIndexes = handleAmount(report.getOfficialIndexes(), indexLibraryIndexes);
            indexLibraryIndexes = handleAmount(report.getTravelIndexes(), indexLibraryIndexes);
            indexLibraryIndexes = handleAmount(report.getServiceIndexes(), indexLibraryIndexes);
            indexLibraryIndexes = handleAmount(report.getOfficialCarIndexes(), indexLibraryIndexes);
            contractIndexes = handleAmount(report.getContractIndexes(), contractIndexes);
            purchaseReportIndexes = handleAmount(report.getPurchaseIndexes(), purchaseReportIndexes);
            indexLibraryIndexes = handleAmount(report.getGeneralIndexes(), indexLibraryIndexes);
        }
        availableAmountDTO.setPurchaseReportIndexes(purchaseReportIndexes);
        availableAmountDTO.setIndexLibraryIndexes(indexLibraryIndexes);
        availableAmountDTO.setContractIndexes(contractIndexes);
        return availableAmountDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PaymentReportDTO> release(List<String> ids) {
        List<Long> longIds = ids.stream().map(Long::parseLong).collect(Collectors.toList());
        List<PaymentReport> paymentReports = this.reportRepository.findAllById(longIds);
        paymentReports.forEach(report -> {
            releaseAmount(report);
            report.setIsRelease(true);
        });
        return this.reportMapper.toList(paymentReports);
    }

    /**
     * 保存特殊关联
     *
     * @param report     事前资金申请
     * @param createInfo 事前资金申请创建信息
     */
    private void saveSpecialRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo) {
        //会议费是否关联采购
        if (StringUtils.isNotBlank(createInfo.getPurchaseReportId())) {
            Optional<PurchaseReport> purchaseReportOptional = this.purchaseReportRepository.findById(Long.parseLong(createInfo.getPurchaseReportId()));
            PurchaseReport purchaseReport = purchaseReportOptional.orElseThrow(() -> new NoSuchDataException(createInfo.getPurchaseReportId()));
            report.setPurchaseReport(purchaseReport);
            report.setPurchase(true);
        } else {
            report.setPurchase(false);
        }
        //非会议费的采购申请关联
        if (StringUtils.isNotBlank(createInfo.getPurchaseReportId2())) {
            Optional<PurchaseReport> purchaseReportOptional = this.purchaseReportRepository.findById(Long.parseLong(createInfo.getPurchaseReportId2()));
            PurchaseReport purchaseReport = purchaseReportOptional.orElseThrow(() -> new NoSuchDataException(createInfo.getPurchaseReportId2()));
            report.setPurchaseReport2(purchaseReport);
        }
        //合同费用关联
        if (StringUtils.isNotBlank(createInfo.getContractId())) {
            Optional<ContractConclusion> contractOptional = this.contractRepository.findById(Long.parseLong(createInfo.getContractId()));
            ContractConclusion contract = contractOptional.orElseThrow(() -> new NoSuchDataException(createInfo.getContractId()));
            report.setContract(contract);
        }
    }

    /**
     * 合并修改前的金额
     *
     * @param paymentReportIndex    事前资金申请——指标信息集合
     * @param purchaseReportIndexes 可用金额map
     * @return 合并后的可用金额map
     */
    private Map<String, Double> handleAmount(Set<PaymentReportIndex> paymentReportIndex, Map<String, Double> purchaseReportIndexes) {
        if (!paymentReportIndex.isEmpty()) {
            Map<String, Double> reportAmount = paymentReportIndex.stream().collect(Collectors.toMap(reportIndex -> reportIndex.getIndex().getId().toString(), PaymentReportIndex::getAmount));
            return this.mergeMap(purchaseReportIndexes, reportAmount);
        }
        return purchaseReportIndexes;
    }

    /**
     * 合并采购指标map
     *
     * @param purchaseId            采购申请ID
     * @param purchaseReportIndexes 采购指标可用余额
     * @return 合并后的采购指标map
     */
    private Map<String, Double> mergePurchaseReportIndexMap(String purchaseId, Map<String, Double> purchaseReportIndexes) {
        PurchaseReport purchaseReport = this.purchaseReportRepository.findById(Long.parseLong(purchaseId)).orElseThrow(() -> new NoSuchDataException(purchaseId));
        Map<String, Double> availableAmount = purchaseReport.getIndexes().stream().collect(Collectors.toMap(reportIndex -> reportIndex.getIndex().getId().toString(),
                reportIndex -> new BigDecimal(reportIndex.getAmount())
                        .subtract(new BigDecimal(reportIndex.getOccupationAmount()))
                        .subtract(new BigDecimal(reportIndex.getPassageAmount()))
                        .doubleValue())
        );
        return this.mergeMap(purchaseReportIndexes, availableAmount);
    }

    /**
     * 合并可用余额map,键相同,金额相加
     *
     * @param map1 可用余额map1
     * @param map2 可用余额map2
     * @return 合并后的可用余额map
     */
    private Map<String, Double> mergeMap(Map<String, Double> map1, Map<String, Double> map2) {
        return Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (value1, value2) -> new BigDecimal(value1.toString()).add(new BigDecimal(value2.toString())).doubleValue()));
    }

    /**
     * 释放金额
     *
     * @param report 事前资金申请实体
     */
    private void releaseAmount(PaymentReport report) {
        switch (report.getExpenseType()) {
            case MEETING:
                releaseMeetingAmount(report.getMeetingIndexes(), report.getPurchaseReport());
                break;
            case TRAINING:
                releaseTrainingAmount(report.getTrainingIndexes());
                break;
            case ABROAD:
                releaseAbroadAmount(report.getAbroadIndexes());
                break;
            case OFFICIAL:
                releaseOfficialAmount(report.getOfficialIndexes());
                break;
            case TRAVEL:
                releaseTravelAmount(report.getTravelIndexes());
                break;
            case SERVICE:
                releaseServiceAmount(report.getServiceIndexes());
                break;
            case OFFICIAL_CAR:
                releaseOfficialCarAmount(report.getOfficialCarIndexes());
                break;
            case CONTRACT:
                releaseContractAmount(report.getContractIndexes(), report.getContract());
                break;
            case PURCHASE:
                releasePurchaseAmount(report.getPurchaseIndexes(), report.getPurchaseReport2());
                break;
            case GENERAL:
                releaseGeneralAmount(report.getGeneralIndexes());
                break;
            case MULTI:
                releaseMultiAmount(report);
                break;
            default:
                throw new CommonDataException(EXPENSE_TYPE_NOT_FOUND);
        }
    }

    /**
     * 释放会议费金额
     *
     * @param meetingIndexes 事前资金申请——指标信息
     * @param purchaseReport 采购申请
     */
    private void releaseMeetingAmount(Set<PaymentReportIndex> meetingIndexes, PurchaseReport purchaseReport) {
        if (purchaseReport != null) {
            releasePurchaseReportAmount(meetingIndexes, purchaseReport);
        } else {
            releaseIndexLibraryAmount(meetingIndexes);
        }
    }

    /**
     * 释放培训费金额
     *
     * @param trainingIndexes 事前资金申请——指标信息
     */
    private void releaseTrainingAmount(Set<PaymentReportIndex> trainingIndexes) {
        releaseIndexLibraryAmount(trainingIndexes);
    }

    /**
     * 释放因公出国（境）经费金额
     *
     * @param abroadIndexes 事前资金申请——指标信息
     */
    private void releaseAbroadAmount(Set<PaymentReportIndex> abroadIndexes) {
        releaseIndexLibraryAmount(abroadIndexes);
    }

    /**
     * 释放公务接待费金额
     *
     * @param officialIndexes 事前资金申请——指标信息
     */
    private void releaseOfficialAmount(Set<PaymentReportIndex> officialIndexes) {
        releaseIndexLibraryAmount(officialIndexes);
    }

    /**
     * 释放差旅费金额
     *
     * @param travelIndexes 事前资金申请——指标信息
     */
    private void releaseTravelAmount(Set<PaymentReportIndex> travelIndexes) {
        releaseIndexLibraryAmount(travelIndexes);
    }

    /**
     * 释放劳务费金额
     *
     * @param serviceIndexes 事前资金申请——指标信息
     */
    private void releaseServiceAmount(Set<PaymentReportIndex> serviceIndexes) {
        releaseIndexLibraryAmount(serviceIndexes);
    }

    /**
     * 释放公务用车金额
     *
     * @param officialCarIndexes 事前资金申请——指标信息
     */
    private void releaseOfficialCarAmount(Set<PaymentReportIndex> officialCarIndexes) {
        releaseIndexLibraryAmount(officialCarIndexes);
    }

    /**
     * 释放合同资金申请金额
     *
     * @param contractIndexes 事前资金申请——指标信息
     * @param contract        合同备案
     */
    private void releaseContractAmount(Set<PaymentReportIndex> contractIndexes, ContractConclusion contract) {
        releaseContractIndexAmount(contractIndexes, contract);
    }

    /**
     * 释放非合同采购金额
     *
     * @param purchaseIndexes 事前资金申请——指标信息
     * @param purchaseReport  采购申请
     */
    private void releasePurchaseAmount(Set<PaymentReportIndex> purchaseIndexes, PurchaseReport purchaseReport) {
        releasePurchaseReportAmount(purchaseIndexes, purchaseReport);
    }

    /**
     * 释放一般经费金额
     *
     * @param generalIndexes 事前资金申请——指标信息
     */
    private void releaseGeneralAmount(Set<PaymentReportIndex> generalIndexes) {
        releaseIndexLibraryAmount(generalIndexes);
    }

    /**
     * 释放多类经费金额
     *
     * @param report 事前资金申请
     */
    private void releaseMultiAmount(PaymentReport report) {
        this.releaseMeetingAmount(report.getMeetingIndexes(), report.getPurchaseReport());
        this.releaseTrainingAmount(report.getTrainingIndexes());
        this.releaseAbroadAmount(report.getAbroadIndexes());
        this.releaseOfficialAmount(report.getOfficialIndexes());
        this.releaseTravelAmount(report.getTravelIndexes());
        this.releaseServiceAmount(report.getServiceIndexes());
        this.releaseOfficialCarAmount(report.getOfficialCarIndexes());
        this.releaseContractAmount(report.getContractIndexes(), report.getContract());
        this.releasePurchaseAmount(report.getPurchaseIndexes(), report.getPurchaseReport2());
        this.releaseGeneralAmount(report.getGeneralIndexes());
    }

    /**
     * 释放采购金额
     *
     * @param indexSet       事前资金申请——指标信息集合
     * @param purchaseReport 采购申请
     */
    private void releasePurchaseReportAmount(Set<PaymentReportIndex> indexSet, PurchaseReport purchaseReport) {
        if (null != indexSet && !indexSet.isEmpty()) {
            Map<Long, Long> purchaseIndexIdMap = purchaseReport.getIndexes().stream().collect(Collectors.toMap(index -> index.getIndex().getId(), PurchaseReportIndex::getId));
            indexSet.forEach(index -> {
                Double releasableAmount = this.getReleasableAmount(index);
                this.purchaseReportIndexRepository.updateAmount(-releasableAmount, 0.0, purchaseIndexIdMap.get(index.getIndex().getId()));
            });
        }
    }

    /**
     * 释放指标金额
     *
     * @param indexSet 事前资金申请——指标信息集合
     */
    private void releaseIndexLibraryAmount(Set<PaymentReportIndex> indexSet) {
        if (null != indexSet && !indexSet.isEmpty()) {
            indexSet.forEach(index -> {
                Double releasableAmount = this.getReleasableAmount(index);
                this.indexLibraryRepository.updateAmount(0.0, 0.0, -releasableAmount, 0.0, 0.0, index.getIndex().getId());
            });
        }
    }

    /**
     * 释放合同金额
     *
     * @param indexSet 事前资金申请——指标信息集合
     * @param contract 合同备案
     */
    private void releaseContractIndexAmount(Set<PaymentReportIndex> indexSet, ContractConclusion contract) {
        if (null != indexSet && !indexSet.isEmpty()) {
            Map<Long, Long> contractIndexIdMap = contract.getIndexes().stream().collect(Collectors.toMap(index -> index.getIndex().getId(), ContractConclusionIndex::getId));
            indexSet.forEach(index -> {
                Double releasableAmount = this.getReleasableAmount(index);
                this.contractIndexRepository.updateAmount(-releasableAmount, 0.0, contractIndexIdMap.get(index.getIndex().getId()));
            });
        }
    }

    /**
     * 计算可释放金额
     *
     * @param index 事前资金申请——指标信息
     * @return 可释放金额
     */
    private Double getReleasableAmount(PaymentReportIndex index) {
        //可释放金额 = 申请金额 -  在途金额 - 释放金额
        BigDecimal releasableAmount = new BigDecimal(index.getAmount().toString())
                .subtract(new BigDecimal(index.getPassageAmount().toString()))
                .subtract(new BigDecimal(index.getReleaseAmount().toString()));
        return releasableAmount.doubleValue();
    }

    /**
     * 根据事前资金申请ID获取事前资金申请信息
     *
     * @param id 事前资金申请ID
     * @return 事前资金申请信息
     */
    private PaymentReport getOne(String id) {
        Optional<PaymentReport> optional = this.reportRepository.findById(Long.parseLong(id));
        return optional.orElseThrow(() -> new NoSuchDataException(id));
    }

    /**
     * 删除关联数据
     *
     * @param report 事前资金申请实体
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteRelation(PaymentReport report, boolean isRecover) {
        Map<String, Map<Long, Double>> map;
        switch (report.getExpenseType()) {
            case MEETING:
                map = deleteMeetingRelation(report, isRecover);
                break;
            case TRAINING:
                map = deleteTrainingRelation(report, isRecover);
                break;
            case ABROAD:
                map = deleteAbroadRelation(report, isRecover);
                break;
            case OFFICIAL:
                map = deleteOfficialRelation(report, isRecover);
                break;
            case TRAVEL:
                map = deleteTravelRelation(report, isRecover);
                break;
            case SERVICE:
                map = deleteServiceRelation(report, isRecover);
                break;
            case OFFICIAL_CAR:
                map = deleteOfficialCarRelation(report, isRecover);
                break;
            case CONTRACT:
                map = deleteContractRelation(report, isRecover);
                break;
            case PURCHASE:
                map = deletePurchaseRelation(report, isRecover);
                break;
            case GENERAL:
                map = deleteGeneralRelation(report, isRecover);
                break;
            case MULTI:
                map = deleteMultiRelation(report, isRecover);
                break;
            default:
                throw new CommonDataException(EXPENSE_TYPE_NOT_FOUND);
        }
        this.attachRepository.deleteAll(report.getAttaches());
        report.setAttaches(Collections.emptySet());
        return map;
    }

    /**
     * 删除会议费关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteMeetingRelation(PaymentReport report, boolean isRecover) {
        if (null != report.getMeetings() && !report.getMeetings().isEmpty()) {
            report.getMeetings().forEach(meeting -> {
                if (null != meeting.getPaymentDetails() && !meeting.getPaymentDetails().isEmpty()) {
                    this.meetingDetailRepository.deleteAll(meeting.getPaymentDetails());
                    meeting.setPaymentDetails(Collections.emptySet());
                }
            });
            this.meetingRepository.deleteAll(report.getMeetings());
            report.setMeetings(Collections.emptySet());
        }
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getMeetingIndexes(), ExpenseType.MEETING, isRecover);
        this.indexRepository.deleteAll(report.getMeetingIndexes());
        report.setMeetingIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除培训费关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteTrainingRelation(PaymentReport report, boolean isRecover) {
        if (null != report.getTrainings() && !report.getTrainings().isEmpty()) {
            report.getTrainings().forEach(training -> {
                if (null != training.getPaymentDetails() && !training.getPaymentDetails().isEmpty()) {
                    this.trainingDetailRepository.deleteAll(training.getPaymentDetails());
                    training.setPaymentDetails(Collections.emptySet());
                }
                if (null != training.getTeachers() && !training.getTeachers().isEmpty()) {
                    this.teacherRepository.deleteAll(training.getTeachers());
                    training.setTeachers(Collections.emptyList());
                }
            });
            this.trainingRepository.deleteAll(report.getTrainings());
            report.setTrainings(Collections.emptySet());
        }
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getTrainingIndexes(), ExpenseType.TRAINING, isRecover);
        this.indexRepository.deleteAll(report.getTrainingIndexes());
        report.setTrainingIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除因公出国（境）经费关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteAbroadRelation(PaymentReport report, boolean isRecover) {
        if (null != report.getAbroadExpenses() && !report.getAbroadExpenses().isEmpty()) {
            report.getAbroadExpenses().forEach(abroadExpense -> {
                if (null != abroadExpense.getPaymentDetails() && !abroadExpense.getPaymentDetails().isEmpty()) {
                    this.abroadDetailRepository.deleteAll(abroadExpense.getPaymentDetails());
                    abroadExpense.setPaymentDetails(Collections.emptySet());
                }
            });
            this.abroadExpenseRepository.deleteAll(report.getAbroadExpenses());
            report.setAbroadExpenses(Collections.emptySet());
        }
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getAbroadIndexes(), ExpenseType.ABROAD, isRecover);
        this.indexRepository.deleteAll(report.getAbroadIndexes());
        report.setAbroadIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除公务接待费关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteOfficialRelation(PaymentReport report, boolean isRecover) {
        if (null != report.getGuests() && !report.getGuests().isEmpty()) {
            this.guestRepository.deleteAll(report.getGuests());
            report.setGuests(Collections.emptySet());
        }
        if (null != report.getItineraries() && !report.getItineraries().isEmpty()) {
            this.itineraryRepository.deleteAll(report.getItineraries());
            report.setItineraries(Collections.emptySet());
        }
        if (null != report.getAccommodations() && !report.getAccommodations().isEmpty()) {
            this.accommodationRepository.deleteAll(report.getAccommodations());
            report.setAccommodations(Collections.emptySet());
        }
        if (null != report.getGuestExpenses() && !report.getGuestExpenses().isEmpty()) {
            this.guestExpenseRepository.deleteAll(report.getGuestExpenses());
            report.setGuestExpenses(Collections.emptySet());
        }
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getOfficialIndexes(), ExpenseType.OFFICIAL, isRecover);
        this.indexRepository.deleteAll(report.getOfficialIndexes());
        report.setOfficialIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除差旅费关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteTravelRelation(PaymentReport report, boolean isRecover) {
        if (null != report.getTravelExpenses() && !report.getTravelExpenses().isEmpty()) {
            report.getTravelExpenses().forEach(travelExpense -> {
                if (null != travelExpense.getPaymentDetails() && !travelExpense.getPaymentDetails().isEmpty()) {
                    this.travelDetailRepository.deleteAll(travelExpense.getPaymentDetails());
                    travelExpense.setPaymentDetails(Collections.emptySet());
                }
            });
            this.travelExpenseRepository.deleteAll(report.getTravelExpenses());
            report.setTravelExpenses(Collections.emptySet());
        }
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getTravelIndexes(), ExpenseType.TRAVEL, isRecover);
        this.indexRepository.deleteAll(report.getTravelIndexes());
        report.setTravelIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除劳务费关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteServiceRelation(PaymentReport report, boolean isRecover) {
        if (null != report.getLabourExpenses() && !report.getLabourExpenses().isEmpty()) {
            this.labourExpenseRepository.deleteAll(report.getLabourExpenses());
            report.setLabourExpenses(Collections.emptySet());
        }
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getServiceIndexes(), ExpenseType.SERVICE, isRecover);
        this.indexRepository.deleteAll(report.getServiceIndexes());
        report.setServiceIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除公务用车关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteOfficialCarRelation(PaymentReport report, boolean isRecover) {
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getOfficialCarIndexes(), ExpenseType.OFFICIAL_CAR, isRecover);
        this.indexRepository.deleteAll(report.getOfficialCarIndexes());
        report.setOfficialCarIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除合同资金申请关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteContractRelation(PaymentReport report, boolean isRecover) {
        if (null != report.getContracts() && !report.getContracts().isEmpty()) {
            for (PaymentReportContract contract : report.getContracts()) {
                if (contract.getRequestPayment()) {
                    Optional<ContractConclusionPayment> contractPaymentOptional = this.contractPaymentRepository.findById(contract.getContractPaymentId());
                    ContractConclusionPayment contractPayment = contractPaymentOptional.orElseThrow(() -> new NoSuchDataException(contract.getContractPaymentId()));
                    contractPayment.setPaymentState(ContractPaymentState.TO_PAY);
                    this.contractPaymentRepository.save(contractPayment);
                    break;
                }
            }
            this.reportContractRepository.deleteAll(report.getContracts());
            report.setContracts(Collections.emptySet());
        }
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getContractIndexes(), ExpenseType.CONTRACT, isRecover);
        this.indexRepository.deleteAll(report.getContractIndexes());
        report.setContractIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除非合同采购关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deletePurchaseRelation(PaymentReport report, boolean isRecover) {
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getPurchaseIndexes(), ExpenseType.PURCHASE, isRecover);
        this.indexRepository.deleteAll(report.getPurchaseIndexes());
        report.setPurchaseIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除事前资金详细信息（一般经费）关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteGeneralRelation(PaymentReport report, boolean isRecover) {
        if (null != report.getPaymentDetails() && !report.getPaymentDetails().isEmpty()) {
            this.reportDetailRepository.deleteAll(report.getPaymentDetails());
            report.setPaymentDetails(Collections.emptySet());
        }
        Map<String, Map<Long, Double>> map = this.recoverAmount(report, report.getGeneralIndexes(), ExpenseType.GENERAL, isRecover);
        this.indexRepository.deleteAll(report.getGeneralIndexes());
        report.setGeneralIndexes(Collections.emptySet());
        return map;
    }

    /**
     * 删除多类经费关联
     *
     * @param report 事前资金申请
     * @param isRecover 是否立刻回退金额
     * @return 金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private Map<String, Map<Long, Double>> deleteMultiRelation(PaymentReport report, boolean isRecover) {
        Map<String, Map<Long, Double>> map = new HashMap<>(16);
        map.putAll(this.deleteMeetingRelation(report, isRecover));
        map.putAll(this.deleteTrainingRelation(report, isRecover));
        map.putAll(this.deleteAbroadRelation(report, isRecover));
        map.putAll(this.deleteOfficialRelation(report, isRecover));
        map.putAll(this.deleteTravelRelation(report, isRecover));
        map.putAll(this.deleteServiceRelation(report, isRecover));
        map.putAll(this.deleteOfficialCarRelation(report, isRecover));
        map.putAll(this.deleteContractRelation(report, isRecover));
        map.putAll(this.deletePurchaseRelation(report, isRecover));
        map.putAll(this.deleteGeneralRelation(report, isRecover));
        return map;
    }

    /**
     * 恢复指标金额
     *
     * @param report        事前资金申请
     * @param reportIndices 事前资金申请——指标信息
     * @param isRecover 是否立刻回退金额
     * @return 变更金额map, key为采购申请指标ID或指标库ID或合同指标ID, value为变化的金额
     */
    private Map<String, Map<Long, Double>> recoverAmount(PaymentReport report, Set<PaymentReportIndex> reportIndices, ExpenseType expenseType, boolean isRecover) {
        if (null != reportIndices && !reportIndices.isEmpty()) {
            boolean isPurchase = report.getPurchase();
            Map<Long, Double> map;
            if (ExpenseType.PURCHASE.equals(expenseType)) {
                map = this.recoverPurchaseAmount(reportIndices, report.getPurchaseReport2(), isRecover);
            } else if (ExpenseType.MEETING.equals(expenseType) && isPurchase) {
                map = this.recoverPurchaseAmount(reportIndices, report.getPurchaseReport(), isRecover);
            } else if (ExpenseType.CONTRACT.equals(expenseType)) {
                map = this.recoverContractAmount(reportIndices, report.getContract(), isRecover);
            } else {
                map = this.recoverIndexLibraryAmount(reportIndices, isRecover);
            }
            Map<String, Map<Long, Double>> resMap = new HashMap<>(16);
            resMap.put(expenseType.toString(), map);
            return resMap;
        }
        return new HashMap<>(16);
    }

    /**
     * 恢复采购指标金额
     *
     * @param reportIndices  事前资金申请——指标信息
     * @param purchaseReport 采购申请
     * @param isRecover 是否立刻回退金额
     * @return 变更金额map, key为采购申请指标ID或指标库ID或合同指标ID, value为变化的金额
     */
    private Map<Long, Double> recoverPurchaseAmount(Set<PaymentReportIndex> reportIndices, PurchaseReport purchaseReport, boolean isRecover) {
        Map<Long, Double> map = new HashMap<>(16);
        Set<PurchaseReportIndex> purchaseReportIndices = purchaseReport.getIndexes();
        reportIndices.forEach(reportIndex -> purchaseReportIndices.forEach(purchaseReportIndex -> {
            if (reportIndex.getIndex().getId().equals(purchaseReportIndex.getIndex().getId())) {
                if (isRecover) {
                    this.purchaseReportIndexRepository.updateAmount(0.0, -reportIndex.getAmount(), purchaseReportIndex.getId());
                } else {
                    map.put(purchaseReportIndex.getId(), -reportIndex.getAmount());
                }
            }
        }));
        return map;
    }

    /**
     * 恢复合同指标金额
     *
     * @param reportIndices 事前资金申请——指标信息
     * @param contract      合同备案
     * @param isRecover 是否立刻回退金额
     * @return 变更金额map, key为采购申请指标ID或指标库ID或合同指标ID, value为变化的金额
     */
    private Map<Long, Double> recoverContractAmount(Set<PaymentReportIndex> reportIndices, ContractConclusion contract, boolean isRecover) {
        Map<Long, Double> map = new HashMap<>(16);
        Set<ContractConclusionIndex> contractIndices = contract.getIndexes();
        reportIndices.forEach(reportIndex -> contractIndices.forEach(contractIndex -> {
            if (reportIndex.getIndex().getId().equals(contractIndex.getIndex().getId())) {
                if (isRecover) {
                    this.contractIndexRepository.updateAmount(0.0, -reportIndex.getAmount(), contractIndex.getId());
                } else {
                    map.put(contractIndex.getId(), -reportIndex.getAmount());
                }
            }
        }));
        return map;
    }

    /**
     * 恢复指标库指标金额
     *
     * @param reportIndices 事前资金申请——指标信息
     * @param isRecover 是否立刻回退金额
     * @return 变更金额map, key为采购申请指标ID或指标库ID或合同指标ID, value为变化的金额
     */
    private Map<Long, Double> recoverIndexLibraryAmount(Set<PaymentReportIndex> reportIndices, boolean isRecover) {
        Map<Long, Double> map = new HashMap<>(16);
        List<IndexLibrary> indexLibraries = reportIndices.stream().map(PaymentReportIndex::getIndex).collect(Collectors.toList());
        reportIndices.forEach(reportIndex -> indexLibraries.forEach(indexLibrary -> {
            if (reportIndex.getIndex().getId().equals(indexLibrary.getId())) {
                if (isRecover) {
                    this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, -reportIndex.getAmount(), 0.0, indexLibrary.getId());
                } else {
                    map.put(indexLibrary.getId(), -reportIndex.getAmount());
                }
            }
        }));
        return map;
    }

    /**
     * 保存关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     * @param fileIds    附件ID列表
     */
    private void saveRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<String, Map<Long, Double>> map, List<String> fileIds) {
        switch (createInfo.getExpenseType()) {
            case MEETING:
                saveMeetingRelation(report, createInfo, map.get(ExpenseType.MEETING.toString()));
                break;
            case TRAINING:
                saveTrainingRelation(report, createInfo, map.get(ExpenseType.TRAINING.toString()));
                break;
            case ABROAD:
                saveAbroadRelation(report, createInfo, map.get(ExpenseType.ABROAD.toString()));
                break;
            case OFFICIAL:
                saveOfficialRelation(report, createInfo, map.get(ExpenseType.OFFICIAL.toString()));
                break;
            case TRAVEL:
                saveTravelRelation(report, createInfo, map.get(ExpenseType.TRAVEL.toString()));
                break;
            case SERVICE:
                saveServiceRelation(report, createInfo, map.get(ExpenseType.SERVICE.toString()));
                break;
            case OFFICIAL_CAR:
                saveOfficialCarRelation(report, createInfo, map.get(ExpenseType.OFFICIAL_CAR.toString()));
                break;
            case CONTRACT:
                saveContractRelation(report, createInfo, map.get(ExpenseType.CONTRACT.toString()));
                break;
            case PURCHASE:
                savePurchaseRelation(report, createInfo, map.get(ExpenseType.PURCHASE.toString()));
                break;
            case GENERAL:
                saveGeneralRelation(report, createInfo, map.get(ExpenseType.GENERAL.toString()));
                break;
            case MULTI:
                saveMultiRelation(report, createInfo, map);
                break;
            default:
                throw new CommonDataException(EXPENSE_TYPE_NOT_FOUND);
        }
        if (null != createInfo.getAttaches() && !createInfo.getAttaches().isEmpty()) {
            this.saveAttachRelation(report, createInfo.getAttaches(), fileIds);
        }
    }

    /**
     * 保存会议费关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveMeetingRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        if (null != createInfo.getMeetings() && !createInfo.getMeetings().isEmpty()) {
            //支出详情列表
            List<PaymentReportMeetingDetail> list = new ArrayList<>();
            int showOrder = 1;
            for (PaymentReportMeetingEditInfoDTO meetingDTO : createInfo.getMeetings()) {
                PaymentReportMeeting meeting = this.meetingMapper.toEntity(meetingDTO);
                meeting.setPaymentReport(report);
                meeting.setShowOrder(showOrder++);
                meeting = this.meetingRepository.save(meeting);
                int showOrder2 = 1;
                for (PaymentReportDetailEditInfoDTO detailDTO : meetingDTO.getPaymentDetails()) {
                    PaymentReportMeetingDetail paymentReportMeetingDetail = this.meetingDetailMapper.toEntity(detailDTO);
                    paymentReportMeetingDetail.setMeeting(meeting);
                    paymentReportMeetingDetail.setShowOrder(showOrder2++);
                    list.add(paymentReportMeetingDetail);
                }
            }
            this.meetingDetailRepository.saveAll(list);
        }
        this.saveIndexRelation(report, createInfo.getMeetingIndexes(), ExpenseType.MEETING, map);
    }

    /**
     * 保存培训费关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveTrainingRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        if (null != createInfo.getTrainings() && !createInfo.getTrainings().isEmpty()) {
            //支出详情列表
            List<PaymentReportTrainingDetail> list = new ArrayList<>();
            //师资费列表
            List<PaymentReportTeacher> teachers = new ArrayList<>();
            int showOrder = 1;
            for (PaymentReportTrainingEditInfoDTO trainingDTO : createInfo.getTrainings()) {
                PaymentReportTraining training = this.trainingMapper.toEntity(trainingDTO);
                training.setPaymentReport(report);
                training.setShowOrder(showOrder++);
                training = this.trainingRepository.save(training);
                int showOrder2 = 1;
                for (PaymentReportDetailEditInfoDTO detailDTO : trainingDTO.getPaymentDetails()) {
                    PaymentReportTrainingDetail trainingDetail = this.trainingDetailMapper.toEntity(detailDTO);
                    trainingDetail.setTraining(training);
                    trainingDetail.setShowOrder(showOrder2++);
                    list.add(trainingDetail);
                }
                showOrder2 = 1;
                for (PaymentReportTeacherEditInfoDTO teacherDTO : trainingDTO.getTeachers()) {
                    PaymentReportTeacher teacher = this.teacherMapper.toEntity(teacherDTO);
                    teacher.setTraining(training);
                    teacher.setShowOrder(showOrder2++);
                    teachers.add(teacher);
                }
            }
            this.trainingDetailRepository.saveAll(list);
            this.teacherRepository.saveAll(teachers);
        }
        this.saveIndexRelation(report, createInfo.getTrainingIndexes(), ExpenseType.TRAINING, map);
    }

    /**
     * 保存因公出国(境)关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveAbroadRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        if (null != createInfo.getAbroadExpenses() && !createInfo.getAbroadExpenses().isEmpty()) {
            //支出详情列表
            List<PaymentReportAbroadDetail> list = new ArrayList<>();
            int showOrder = 1;
            for (PaymentReportAbroadExpenseEditInfoDTO abroadExpenseDTO : createInfo.getAbroadExpenses()) {
                PaymentReportAbroadExpense abroadExpense = this.abroadExpenseMapper.toEntity(abroadExpenseDTO);
                abroadExpense.setPaymentReport(report);
                abroadExpense.setShowOrder(showOrder++);
                int showOrder2 = 1;
                abroadExpense = this.abroadExpenseRepository.save(abroadExpense);
                for (PaymentReportDetailEditInfoDTO detailDTO : abroadExpenseDTO.getPaymentDetails()) {
                    PaymentReportAbroadDetail paymentReportAbroadDetail = this.abroadDetailMapper.toEntity(detailDTO);
                    paymentReportAbroadDetail.setAbroadExpense(abroadExpense);
                    paymentReportAbroadDetail.setShowOrder(showOrder2++);
                    list.add(paymentReportAbroadDetail);
                }
            }
            this.abroadDetailRepository.saveAll(list);
        }
        this.saveIndexRelation(report, createInfo.getAbroadIndexes(), ExpenseType.ABROAD, map);
    }

    /**
     * 保存公务接待费关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveOfficialRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        int showOrder = 1;
        if (null != createInfo.getGuests() && !createInfo.getGuests().isEmpty()) {
            for (PaymentReportGuestEditInfoDTO guestDTO : createInfo.getGuests()) {
                PaymentReportGuest guest = this.guestMapper.toEntity(guestDTO);
                guest.setPaymentReport(report);
                guest.setShowOrder(showOrder++);
                this.guestRepository.save(guest);
            }
        }
        showOrder = 1;
        if (null != createInfo.getItineraries() && !createInfo.getItineraries().isEmpty()) {
            for (PaymentReportItineraryEditInfoDTO itineraryDTO : createInfo.getItineraries()) {
                PaymentReportItinerary itinerary = this.itineraryMapper.toEntity(itineraryDTO);
                itinerary.setPaymentReport(report);
                itinerary.setShowOrder(showOrder++);
                this.itineraryRepository.save(itinerary);
            }
        }
        showOrder = 1;
        if (null != createInfo.getAccommodations() && !createInfo.getAccommodations().isEmpty()) {
            for (PaymentReportAccommodationEditInfoDTO accommodationDTO : createInfo.getAccommodations()) {
                PaymentReportAccommodation accommodation = this.accommodationMapper.toEntity(accommodationDTO);
                accommodation.setPaymentReport(report);
                accommodation.setShowOrder(showOrder++);
                this.accommodationRepository.save(accommodation);
            }
        }
        showOrder = 1;
        if (null != createInfo.getGuestExpenses() && !createInfo.getGuestExpenses().isEmpty()) {
            for (PaymentReportGuestExpenseEditInfoDTO guestExpenseDTO : createInfo.getGuestExpenses()) {
                PaymentReportGuestExpense guestExpense = this.guestExpenseMapper.toEntity(guestExpenseDTO);
                guestExpense.setPaymentReport(report);
                guestExpense.setShowOrder(showOrder++);
                this.guestExpenseRepository.save(guestExpense);
            }
        }
        this.saveIndexRelation(report, createInfo.getOfficialIndexes(), ExpenseType.OFFICIAL, map);
    }

    /**
     * 保存差旅费关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveTravelRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        if (null != createInfo.getTravelExpenses() && !createInfo.getTravelExpenses().isEmpty()) {
            //支出详情列表
            List<PaymentReportTravelDetail> list = new ArrayList<>();
            int showOrder = 1;
            for (PaymentReportTravelExpenseEditInfoDTO travelExpenseDTO : createInfo.getTravelExpenses()) {
                PaymentReportTravelExpense travelExpense = this.travelExpenseMapper.toEntity(travelExpenseDTO);
                List<Long> userIds = travelExpenseDTO.getUserIds().stream().map(Long::parseLong).collect(Collectors.toList());
                List<User> users = this.userRepository.findAllById(userIds);
                travelExpense.setUsers(users);
                travelExpense.setPaymentReport(report);
                travelExpense.setShowOrder(showOrder++);
                travelExpense = this.travelExpenseRepository.save(travelExpense);
                int showOrder2 = 1;
                for (PaymentReportTravelDetailEditInfoDTO detailDTO : travelExpenseDTO.getPaymentDetails()) {
                    PaymentReportTravelDetail paymentReportTravelDetail = this.travelDetailMapper.toEntity(detailDTO);
                    paymentReportTravelDetail.setTravelExpense(travelExpense);
                    paymentReportTravelDetail.setShowOrder(showOrder2++);
                    list.add(paymentReportTravelDetail);
                }
            }
            this.travelDetailRepository.saveAll(list);
        }
        this.saveIndexRelation(report, createInfo.getTravelIndexes(), ExpenseType.TRAVEL, map);
    }

    /**
     * 保存劳务费关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveServiceRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        if (null != createInfo.getLabourExpenses() && !createInfo.getLabourExpenses().isEmpty()) {
            int showOrder = 1;
            for (PaymentReportLabourExpenseEditInfoDTO labourExpenseDTO : createInfo.getLabourExpenses()) {
                PaymentReportLabourExpense labourExpense = this.labourExpenseMapper.toEntity(labourExpenseDTO);
                labourExpense.setPaymentReport(report);
                labourExpense.setShowOrder(showOrder++);
                this.labourExpenseRepository.save(labourExpense);
            }
        }
        this.saveIndexRelation(report, createInfo.getServiceIndexes(), ExpenseType.SERVICE, map);
    }

    /**
     * 保存公务用车经费关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveOfficialCarRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        this.saveIndexRelation(report, createInfo.getOfficialCarIndexes(), ExpenseType.OFFICIAL_CAR, map);
    }

    /**
     * 保存合同资金关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveContractRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        if (null != createInfo.getContracts() && !createInfo.getContracts().isEmpty()) {
            List<Long> paymentIds = createInfo.getContracts().stream().map(contract -> contract.getContractPaymentId()).map(Long::parseLong).collect(Collectors.toList());
            List<ContractConclusionPayment> contractPayments = this.contractPaymentRepository.findAllById(paymentIds);
            Map<Long, ContractConclusionPayment> paymentMap = contractPayments.stream().collect(Collectors.toMap(ContractConclusionPayment::getId, payment -> payment));
            boolean isPayOne = false;
            int showOrder = 1;
            Double indexTotal = 0.0;
            if (!CollectionUtils.isEmpty(createInfo.getContractIndexes())) {
                indexTotal = createInfo.getContractIndexes().stream().map(index -> index.getAmount()).reduce(0.0, Double::sum);
            }
            for (PaymentReportContractEditInfoDTO contractDTO : createInfo.getContracts()) {
                PaymentReportContract paymentReportContract = this.reportContractMapper.toEntity(contractDTO);
                paymentReportContract.setPaymentReport(report);
                paymentReportContract.setShowOrder(showOrder++);
                ContractConclusionPayment payment = paymentMap.get(Long.parseLong(contractDTO.getContractPaymentId()));
                if (null != contractDTO.getRequestPayment() && contractDTO.getRequestPayment()) {
                    if (isPayOne) {
                        throw new CommonDataException("一次申请只能支付一条付款信息");
                    }
                    if (!indexTotal.equals(payment.getAmount())) {
                        throw new CommonDataException("当前支付金额与指标申请总额不一致");
                    }
                    if (StateType.DONE == report.getState()) {
                        payment.setPaymentState(ContractPaymentState.PAID);
                        paymentReportContract.setPaymentState(ContractPaymentState.PAID);
                    } else {
                        payment.setPaymentState(ContractPaymentState.PAYING);
                        paymentReportContract.setPaymentState(ContractPaymentState.PAYING);
                    }
                    this.contractPaymentRepository.save(payment);
                    isPayOne = true;
                }
                this.reportContractRepository.save(paymentReportContract);
            }
            if (!isPayOne) {
                throw new CommonDataException("一次申请必须支付一条付款信息");
            }
        }
        this.saveIndexRelation(report, createInfo.getContractIndexes(), ExpenseType.CONTRACT, map);
    }

    /**
     * 保存非合同采购关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void savePurchaseRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        this.saveIndexRelation(report, createInfo.getPurchaseIndexes(), ExpenseType.PURCHASE, map);
    }

    /**
     * 保存一般经费关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveGeneralRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<Long, Double> map) {
        if (null != createInfo.getPaymentDetails() && !createInfo.getPaymentDetails().isEmpty()) {
            int showOrder = 1;
            for (PaymentReportGeneralDetailEditInfoDTO paymentDetailDTO : createInfo.getPaymentDetails()) {
                PaymentReportDetail paymentReportDetail = this.reportDetailMapper.toEntity(paymentDetailDTO);
                paymentReportDetail.setExpenseType(paymentDetailDTO.getExpenseType());
                paymentReportDetail.setPaymentReport(report);
                paymentReportDetail.setShowOrder(showOrder++);
                this.reportDetailRepository.save(paymentReportDetail);
            }
        }
        this.saveIndexRelation(report, createInfo.getGeneralIndexes(), ExpenseType.GENERAL, map);
    }

    /**
     * 保存多类经费关联数据
     *
     * @param report     事前资金申请实体
     * @param createInfo 事前资金申请创建信息
     * @param map        金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveMultiRelation(PaymentReport report, PaymentReportCreateInfoDTO createInfo, Map<String, Map<Long, Double>> map) {
        this.saveMeetingRelation(report, createInfo, map.get(ExpenseType.MEETING.toString()));
        this.saveTrainingRelation(report, createInfo, map.get(ExpenseType.TRAINING.toString()));
        this.saveAbroadRelation(report, createInfo, map.get(ExpenseType.ABROAD.toString()));
        this.saveOfficialRelation(report, createInfo, map.get(ExpenseType.OFFICIAL.toString()));
        this.saveTravelRelation(report, createInfo, map.get(ExpenseType.TRAVEL.toString()));
        this.saveServiceRelation(report, createInfo, map.get(ExpenseType.SERVICE.toString()));
        this.saveOfficialCarRelation(report, createInfo, map.get(ExpenseType.OFFICIAL_CAR.toString()));
        this.saveContractRelation(report, createInfo, map.get(ExpenseType.CONTRACT.toString()));
        this.savePurchaseRelation(report, createInfo, map.get(ExpenseType.PURCHASE.toString()));
        this.saveGeneralRelation(report, createInfo, map.get(ExpenseType.GENERAL.toString()));
    }

    /**
     * 保存附件信息关联数据
     *
     * @param report   事前资金申请实体
     * @param attaches 采购计划附件编辑信息
     * @param fileIds 文件ID列表
     */
    private void saveAttachRelation(PaymentReport report, List<AttachEditInfoDTO> attaches, List<String> fileIds) {
        int showOrder = 1;
        List<PaymentReportAttach> list = new ArrayList<>();
        if (null != fileIds && !fileIds.isEmpty()) {
            attaches.forEach(attachDTO -> {
                if (!CollectionUtils.isEmpty(attachDTO.getFileIds())) {
                    attachDTO.getFileIds().forEach(fileIds::remove);
                }
            });
            List<Long> oldFileIds = fileIds.stream().map(Long::parseLong).collect(Collectors.toList());
            this.commonUtil.deleteFile(this.systemFileRepository.findAllById(oldFileIds));
        }
        for (AttachEditInfoDTO attachDTO : attaches) {
            PaymentReportAttach attach = this.attachMapper.toEntity(attachDTO);
            if (null != attachDTO.getFileIds() && !attachDTO.getFileIds().isEmpty()) {
                List<Long> ids = attachDTO.getFileIds().stream().map(Long::parseLong).collect(Collectors.toList());
                List<SystemFile> files = this.systemFileRepository.findAllById(ids);
                attach.setFiles(new HashSet<>(files));
            }
            attach.setPaymentReport(report);
            attach.setShowOrder(showOrder++);
            list.add(attach);
        }
        this.attachRepository.saveAll(list);
    }

    /**
     * 保存指标关联
     *
     * @param report      事前资金申请实体
     * @param dtoList     事前资金申请——指标编辑信息
     * @param expenseType 费用类型
     * @param map         金额变动map
     */
    private void saveIndexRelation(PaymentReport report, List<PaymentReportIndexEditInfoDTO> dtoList, ExpenseType expenseType, Map<Long, Double> map) {
        boolean isPurchase = report.getPurchase();
        if (ExpenseType.PURCHASE.equals(expenseType)) {
            this.savePurchaseIndex(report, dtoList, expenseType, map);
        } else if (ExpenseType.MEETING.equals(expenseType) && isPurchase) {
            this.savePurchaseIndex(report, dtoList, expenseType, map);
        } else if (ExpenseType.CONTRACT.equals(expenseType)) {
            this.saveContractIndex(report, dtoList, expenseType, map);
        } else {
            this.saveIndexLibraryIndex(report, dtoList, expenseType, map);
        }
    }

    /**
     * 保存采购指标关联
     *
     * @param report      事前资金申请实体
     * @param dtoList     事前资金申请——指标编辑信息
     * @param expenseType 费用类型
     * @param map         金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void savePurchaseIndex(PaymentReport report, List<PaymentReportIndexEditInfoDTO> dtoList, ExpenseType expenseType, Map<Long, Double> map) {
        if (null != dtoList && !dtoList.isEmpty()) {
            Map<Long, IndexLibrary> indexLibraryMap = this.getIndexLibraryMap(dtoList);
            PurchaseReport purchaseReport;
            if (ExpenseType.MEETING.equals(expenseType)) {
                purchaseReport = report.getPurchaseReport();
            } else if (ExpenseType.PURCHASE.equals(expenseType)) {
                purchaseReport = report.getPurchaseReport2();
            } else {
                throw new CommonDataException("没有关联的采购数据");
            }
            Map<String, PurchaseReportIndex> reportIndexMap = purchaseReport.getIndexes().stream().collect(
                    Collectors.toMap(index -> index.getIndex().getId().toString(), index -> index)
            );
            int showOrder = 1;
            for (PaymentReportIndexEditInfoDTO indexDTO : dtoList) {
                PaymentReportIndex paymentReportIndex = this.indexMapper.toEntity(indexDTO);
                PurchaseReportIndex purchaseReportIndex = reportIndexMap.get(indexDTO.getIndexId());
                //获取变化后的金额
                Double indexAmount = this.commonUtil.getChangeAmount(map, purchaseReportIndex.getId(), indexDTO.getAmount(),
                        purchaseReportIndex.getAmount(), purchaseReportIndex.getPassageAmount(), purchaseReportIndex.getOccupationAmount());
                if (StateType.DONE.equals(report.getState())) {
                    BigDecimal occupationAmount = new BigDecimal(paymentReportIndex.getAmount().toString()).add(new BigDecimal(indexAmount));
                    this.purchaseReportIndexRepository.updateAmount(occupationAmount.doubleValue(), -occupationAmount.doubleValue(), purchaseReportIndex.getId());
                } else {
                    this.purchaseReportIndexRepository.updateAmount(0.0, indexAmount, purchaseReportIndex.getId());
                }
                this.enduranceReportIndex(paymentReportIndex, indexLibraryMap.get(Long.parseLong(indexDTO.getIndexId())), expenseType, report, showOrder++);
                this.indexRepository.save(paymentReportIndex);
            }
        }
    }

    /**
     * 保存指标库指标关联
     *
     * @param report      事前资金申请实体
     * @param dtoList     事前资金申请——指标编辑信息
     * @param expenseType 费用类型
     * @param map         金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveIndexLibraryIndex(PaymentReport report, List<PaymentReportIndexEditInfoDTO> dtoList, ExpenseType expenseType, Map<Long, Double> map) {
        if (null != dtoList && !dtoList.isEmpty()) {
            Map<Long, IndexLibrary> indexLibraryMap = this.getIndexLibraryMap(dtoList);
            int showOrder = 1;
            for (PaymentReportIndexEditInfoDTO indexDTO : dtoList) {
                PaymentReportIndex paymentReportIndex = this.indexMapper.toEntity(indexDTO);
                IndexLibrary indexLibrary = indexLibraryMap.get(Long.parseLong(indexDTO.getIndexId()));
                BigDecimal indexLibraryAmount = new BigDecimal(indexLibrary.getAllocationAmount().toString()).add(new BigDecimal(indexLibrary.getAdjustmentAmount().toString()));
                //获取变化后的金额
                Double indexAmount = this.commonUtil.getChangeAmount(map, indexLibrary.getId(), indexDTO.getAmount(),
                        indexLibraryAmount.doubleValue(), indexLibrary.getPassageAmount(), indexLibrary.getOccupationAmount());
                if (StateType.DONE.equals(report.getState())) {
                    BigDecimal occupationAmount = new BigDecimal(paymentReportIndex.getAmount().toString()).add(new BigDecimal(indexAmount));
                    this.indexLibraryRepository.updateAmount(0.0, 0.0, occupationAmount.doubleValue(), -occupationAmount.doubleValue(), 0.0, indexLibrary.getId());
                } else {
                    this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, indexAmount, 0.0, indexLibrary.getId());
                }
                this.enduranceReportIndex(paymentReportIndex, indexLibrary, expenseType, report, showOrder++);
                if (map != null) {
                    map.remove(indexLibrary.getId());
                }
            }
        }
        if (map != null && !map.isEmpty()) {
            map.keySet().forEach(indexId -> this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, map.get(indexId), 0.0, indexId));
        }
    }

    /**
     * 保存合同指标关联
     *
     * @param report      事前资金申请实体
     * @param dtoList     事前资金申请——指标编辑信息
     * @param expenseType 费用类型
     * @param map         金额变更记录map, key为采购申请指标id/指标库id/合同指标id, value为变化的金额
     */
    private void saveContractIndex(PaymentReport report, List<PaymentReportIndexEditInfoDTO> dtoList, ExpenseType expenseType, Map<Long, Double> map) {
        if (null != dtoList && !dtoList.isEmpty()) {
            Map<Long, IndexLibrary> indexLibraryMap = this.getIndexLibraryMap(dtoList);
            Map<String, ContractConclusionIndex> contractIndexMap = report.getContract().getIndexes().stream().collect(
                    Collectors.toMap(index -> index.getIndex().getId().toString(), index -> index)
            );
            int showOrder = 1;
            for (PaymentReportIndexEditInfoDTO indexDTO : dtoList) {
                PaymentReportIndex paymentReportIndex = this.indexMapper.toEntity(indexDTO);
                IndexLibrary indexLibrary = indexLibraryMap.get(Long.parseLong(indexDTO.getIndexId()));
                ContractConclusionIndex contractIndex = contractIndexMap.get(indexDTO.getIndexId());
                //获取变化后的金额
                Double indexAmount = this.commonUtil.getChangeAmount(map, contractIndex.getId(), indexDTO.getAmount(),
                        contractIndex.getAmount(), contractIndex.getPassageAmount(), contractIndex.getPaidAmount());
                if (StateType.DONE.equals(report.getState())) {
                    BigDecimal occupationAmount = new BigDecimal(paymentReportIndex.getAmount().toString()).add(new BigDecimal(indexAmount));
                    this.contractIndexRepository.updateAmount(occupationAmount.doubleValue(), -occupationAmount.doubleValue(), contractIndex.getId());
                } else {
                    this.contractIndexRepository.updateAmount(0.0, indexAmount, contractIndex.getId());
                }
                this.enduranceReportIndex(paymentReportIndex, indexLibrary, expenseType, report, showOrder++);
            }
        }
    }

    /**
     * 构建指标库map, key为指标库ID, value为指标库对象
     *
     * @param dtoList 事前资金申请——指标编辑信息
     * @return 指标库map
     */
    private Map<Long, IndexLibrary> getIndexLibraryMap(List<PaymentReportIndexEditInfoDTO> dtoList) {
        List<Long> indexIds = dtoList.stream().map(PaymentReportIndexEditInfoDTO::getIndexId).map(Long::parseLong).collect(Collectors.toList());
        return this.indexLibraryRepository.findAllById(indexIds).stream().collect(Collectors.toMap(IndexLibrary::getId, index -> index));
    }

    /**
     * 构建事前资金指标信息，并保存数据库
     *
     * @param paymentReportIndex 事前资金申请——指标信息
     * @param indexLibrary       指标库
     * @param expenseType        费用类型
     * @param report             事前资金申请实体
     * @param showOrder          排序
     */
    private void enduranceReportIndex(PaymentReportIndex paymentReportIndex, IndexLibrary indexLibrary, ExpenseType expenseType, PaymentReport report, int showOrder) {
        paymentReportIndex.setIndex(indexLibrary);
        paymentReportIndex.setExpenseType(expenseType);
        paymentReportIndex.setPaymentReport(report);
        paymentReportIndex.setShowOrder(showOrder);
        this.indexRepository.save(paymentReportIndex);
    }
}
