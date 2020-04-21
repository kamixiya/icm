package com.kamixiya.icm.service.content.service.contract;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionCreateInfoDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionEditInfoDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionQueryOptionDTO;
import com.kamixiya.icm.model.content.contract.common.ContractIndexEditInfoDTO;
import com.kamixiya.icm.model.content.contract.common.ContractParticipantEditInfoDTO;
import com.kamixiya.icm.model.content.contract.common.ContractPayeeEditInfoDTO;
import com.kamixiya.icm.model.content.contract.common.ContractPaymentEditInfoDTO;
import com.kamixiya.icm.model.content.purchase.AttachEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.persistence.content.entity.contract.*;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReport;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportIndex;
import com.kamixiya.icm.persistence.content.repository.contract.*;
import com.kamixiya.icm.persistence.content.repository.indexLibrary.IndexLibraryRepository;
import com.kamixiya.icm.persistence.content.repository.purchase.PurchaseReportIndexRepository;
import com.kamixiya.icm.persistence.content.repository.purchase.PurchaseReportRepository;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.exception.CommonDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.repository.base.SystemFileRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.content.mapper.contract.*;
import com.kamixiya.icm.service.content.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 合同订立服务实现
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service("contractConclusionService")
public class ContractConclusionServiceImpl implements ContractConclusionService {

    private final ContractConclusionMapper conclusionMapper;
    private final ContractConclusionAttachMapper conclusionAttachMapper;
    private final ContractConclusionIndexMapper conclusionIndexMapper;
    private final ContractConclusionParticipantMapper conclusionParticipantMapper;
    private final ContractConclusionPayeeMapper conclusionPayeeMapper;
    private final ContractConclusionPaymentMapper conclusionPaymentMapper;

    private final ContractConclusionRepository conclusionRepository;
    private final ContractConclusionAttachRepository conclusionAttachRepository;
    private final ContractConclusionIndexRepository conclusionIndexRepository;
    private final ContractConclusionParticipantRepository conclusionParticipantRepository;
    private final ContractConclusionPayeeRepository conclusionPayeeRepository;
    private final ContractConclusionPaymentRepository conclusionPaymentRepository;
    private final PurchaseReportRepository purchaseReportRepository;
    private final SystemFileRepository systemFileRepository;
    private final PurchaseReportIndexRepository purchaseReportIndexRepository;
    private final IndexLibraryRepository indexLibraryRepository;
    private final CommonUtil commonUtil;

    @Autowired
    public ContractConclusionServiceImpl(ContractConclusionMapper conclusionMapper, ContractConclusionAttachMapper conclusionAttachMapper,
                                         ContractConclusionIndexMapper conclusionIndexMapper, ContractConclusionParticipantMapper conclusionParticipantMapper,
                                         ContractConclusionPayeeMapper conclusionPayeeMapper, ContractConclusionPaymentMapper conclusionPaymentMapper,
                                         ContractConclusionRepository conclusionRepository, ContractConclusionAttachRepository conclusionAttachRepository,
                                         ContractConclusionIndexRepository conclusionIndexRepository, ContractConclusionParticipantRepository conclusionParticipantRepository,
                                         ContractConclusionPayeeRepository conclusionPayeeRepository, ContractConclusionPaymentRepository conclusionPaymentRepository,
                                         PurchaseReportRepository purchaseReportRepository, SystemFileRepository systemFileRepository,
                                         PurchaseReportIndexRepository purchaseReportIndexRepository, IndexLibraryRepository indexLibraryRepository, CommonUtil commonUtil) {
        this.conclusionMapper = conclusionMapper;
        this.conclusionAttachMapper = conclusionAttachMapper;
        this.conclusionIndexMapper = conclusionIndexMapper;
        this.conclusionParticipantMapper = conclusionParticipantMapper;
        this.conclusionPayeeMapper = conclusionPayeeMapper;
        this.conclusionPaymentMapper = conclusionPaymentMapper;
        this.conclusionRepository = conclusionRepository;
        this.conclusionAttachRepository = conclusionAttachRepository;
        this.conclusionIndexRepository = conclusionIndexRepository;
        this.conclusionParticipantRepository = conclusionParticipantRepository;
        this.conclusionPayeeRepository = conclusionPayeeRepository;
        this.conclusionPaymentRepository = conclusionPaymentRepository;
        this.purchaseReportRepository = purchaseReportRepository;
        this.systemFileRepository = systemFileRepository;
        this.purchaseReportIndexRepository = purchaseReportIndexRepository;
        this.indexLibraryRepository = indexLibraryRepository;
        this.commonUtil = commonUtil;
    }

    @Override
    public PageDataDTO<ContractConclusionDTO> findOnePage(ContractConclusionQueryOptionDTO contractConclusionQueryOptionDTO) {
        PredicateBuilder<ContractConclusion> pb = Specifications.<ContractConclusion>and()
                .eq(null != contractConclusionQueryOptionDTO.getImportant(), "important", contractConclusionQueryOptionDTO.getImportant())
                .like(StringUtils.isNotBlank(contractConclusionQueryOptionDTO.getName()), "name", "%" + contractConclusionQueryOptionDTO.getName() + "%")
                .eq(StringUtils.isNotBlank(contractConclusionQueryOptionDTO.getYear()), "year", contractConclusionQueryOptionDTO.getYear())
                .eq(null != contractConclusionQueryOptionDTO.getPurchase(), "purchase", contractConclusionQueryOptionDTO.getPurchase())
                .ge(null != contractConclusionQueryOptionDTO.getBeginDate(), "applyDate", contractConclusionQueryOptionDTO.getBeginDate())
                .le(null != contractConclusionQueryOptionDTO.getEndDate(), "applyDate", contractConclusionQueryOptionDTO.getEndDate())
                .eq(StringUtils.isNotBlank(contractConclusionQueryOptionDTO.getUnitId()), "unit.id", contractConclusionQueryOptionDTO.getUnitId())
                .eq(StringUtils.isNotBlank(contractConclusionQueryOptionDTO.getDepartmentId()), "department.id", contractConclusionQueryOptionDTO.getDepartmentId())
                .like(StringUtils.isNotBlank(contractConclusionQueryOptionDTO.getDeclarerName()), "declarer.name", "%" + contractConclusionQueryOptionDTO.getDeclarerName() + "%");
        Page<ContractConclusion> conclusionPage = this.conclusionRepository.findAll(pb.build(), PageHelper.generatePageRequest(
                contractConclusionQueryOptionDTO.getPageQueryOption().getPage(),
                contractConclusionQueryOptionDTO.getPageQueryOption().getSize(),
                contractConclusionQueryOptionDTO.getPageQueryOption().getSort()));
        List<ContractConclusionDTO> content = this.conclusionMapper.toList(conclusionPage.getContent());
        return PageDataUtil.toPageData(conclusionPage, content);
    }

    @Override
    public List<ContractConclusionDTO> findAll(ContractConclusionQueryOptionDTO contractConclusionQueryOptionDTO) {
        List<ContractConclusion> conclusions = this.conclusionRepository.findAll(Specifications.<ContractConclusion>and()
                .eq("state", StateType.DONE)
                .eq("department.id", contractConclusionQueryOptionDTO.getDepartmentId()).build());
        if (StringUtils.isNotBlank(contractConclusionQueryOptionDTO.getConclusionId())) {
            conclusions.add(getOne(contractConclusionQueryOptionDTO.getConclusionId()));
        }
        return this.conclusionMapper.toList(conclusions);
    }

    @Override
    public ContractConclusionDTO findById(String id) {
        ContractConclusion conclusion = getOne(id);
        return this.conclusionMapper.toDTO(conclusion, new CycleAvoidingMappingContext());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractConclusionDTO create(ContractConclusionCreateInfoDTO createInfo) throws IOException {
        ContractConclusion conclusion = this.conclusionMapper.toEntity(createInfo);
        conclusion.setState(StateType.UNDONE);
        conclusion.setUnit(this.commonUtil.getOrganization(createInfo.getUnitId()));
        conclusion.setDepartment(this.commonUtil.getOrganization(createInfo.getDepartmentId()));
        conclusion.setDeclarer(this.commonUtil.getUser(createInfo.getDeclarerId()));
        conclusion = this.conclusionRepository.save(conclusion);
        saveRelation(conclusion, createInfo, null, new HashMap<>(16));
        conclusion = this.conclusionRepository.save(conclusion);
        return this.conclusionMapper.toDTO(conclusion, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContractConclusionDTO complete(String id, ContractConclusionEditInfoDTO editInfo) throws IOException {
        ContractConclusion conclusion = getOne(id);
        //保存旧的文件id列表，用于更新时删除脏数据
        List<String> oldFileIds = new ArrayList<>();
        conclusion.getAttaches().forEach(attach -> attach.getFiles().forEach(file -> oldFileIds.add(file.getId().toString())));
        Map<Long, Double> map;
        if ((conclusion.getPurchaseReport() != null && StringUtils.isBlank(editInfo.getPurchaseReportId()))) {
            map = deleteRelation(conclusion, true);
        } else if ((conclusion.getPurchaseReport() == null && StringUtils.isNotBlank(editInfo.getPurchaseReportId()))) {
            map = deleteRelation(conclusion, true);
        } else {
            map = deleteRelation(conclusion, false);
        }
        this.conclusionMapper.updateEntity(editInfo, conclusion);
        conclusion.setState(StateType.DONE);
        saveRelation(conclusion, editInfo, oldFileIds, map);
        conclusion = this.conclusionRepository.save(conclusion);
        return this.conclusionMapper.toDTO(conclusion, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws IOException {
        ContractConclusion conclusion = getOne(id);
        Map<Long, Double> map = deleteRelation(conclusion, true);
        boolean isPurchase = conclusion.getPurchase();
        if (isPurchase) {
            List<PurchaseReportIndex> list = this.purchaseReportIndexRepository.findAllById(map.keySet());
            list.forEach(index -> this.purchaseReportIndexRepository.updateAmount(0.0, map.get(index.getId()), index.getId()));
        } else {
            List<IndexLibrary> list = this.indexLibraryRepository.findAllById(map.keySet());
            list.forEach(index -> this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, map.get(index.getId()), 0.0, index.getId()));
        }
        this.conclusionRepository.delete(conclusion);
    }

    @Override
    public Map<String, Double> getAvailable(String id, List<String> indexIds, Boolean isPurchase, String purchaseReportId) {
        List<Long> indexIdList = indexIds.stream().map(Long::parseLong).collect(Collectors.toList());
        boolean currentPurchase = isPurchase;
        //新增
        if (StringUtils.isBlank(id)) {
            //关联采购
            if (currentPurchase) {
                PurchaseReport purchaseReport = this.purchaseReportRepository.findById(Long.parseLong(purchaseReportId)).orElseThrow(() -> new NoSuchDataException(purchaseReportId));
                Set<PurchaseReportIndex> purchaseReportIndices = purchaseReport.getIndexes();
                return purchaseReportIndices.stream().collect(Collectors.toMap(index -> index.getIndex().getId().toString(),
                        index -> new BigDecimal(index.getAmount().toString())
                                .subtract(new BigDecimal(index.getOccupationAmount().toString()))
                                .subtract(new BigDecimal(index.getPassageAmount().toString()))
                                .doubleValue())
                );
            }
            //不关联采购
            List<IndexLibrary> indexes = this.indexLibraryRepository.findAllById(indexIdList);
            return indexes.stream().collect(Collectors.toMap(index -> index.getId().toString(), IndexLibrary::getAvailableAmount));
        }
        //修改
        ContractConclusion conclusion = getOne(id);
        //构建合同备案指标信息map, key为指标库id, value为合同订立指标信息对象
        Map<Long, ContractConclusionIndex> map = conclusion.getIndexes().stream().collect(Collectors.toMap(
                index -> index.getIndex().getId(), index -> index
        ));
        boolean beforePurchase = conclusion.getPurchase();
        //关联采购
        if (currentPurchase) {
            Set<PurchaseReportIndex> purchaseReportIndices;
            if (beforePurchase) {
                //修改前也是关联采购(关联 -> 关联)
                purchaseReportIndices = conclusion.getPurchaseReport().getIndexes();
            } else {
                //修改前不关联采购(不关联 -> 关联)
                PurchaseReport purchaseReport = this.purchaseReportRepository.findById(Long.parseLong(purchaseReportId)).orElseThrow(() -> new NoSuchDataException(purchaseReportId));
                purchaseReportIndices = purchaseReport.getIndexes();
            }
            return purchaseReportIndices.stream().collect(Collectors.toMap(index -> index.getIndex().getId().toString(),
                    index -> new BigDecimal(index.getAmount().toString())
                            .subtract(new BigDecimal(index.getOccupationAmount().toString()))
                            .subtract(new BigDecimal(index.getPassageAmount().toString()))
                            .add(new BigDecimal(map.get(index.getIndex().getId()) == null ? "0" : map.get(index.getIndex().getId()).getAmount().toString()))
                            .doubleValue())
            );
        }
        //不关联采购
        List<IndexLibrary> indexes = this.indexLibraryRepository.findAllById(indexIdList);
        Map<Long, Double> amountMap = new HashMap<>(16);
        map.keySet().forEach(indexId -> amountMap.put(indexId, map.get(indexId).getAmount()));
        Map<String, Double> resMap = new HashMap<>(16);
        indexes.forEach(index -> {
            BigDecimal availableAmount = new BigDecimal(index.getAvailableAmount().toString());
            Double amount = amountMap.get(index.getId());
            if (amount != null) {
                availableAmount = availableAmount.add(new BigDecimal(amount.toString()));
            }
            resMap.put(index.getId().toString(), availableAmount.doubleValue());
        });
        return resMap;
    }

    @Override
    public Double getPurchaseAvailable(String id, String purchaseReportId) {
        //采购申请实体
        Optional<PurchaseReport> optional = this.purchaseReportRepository.findById(Long.parseLong(purchaseReportId));
        PurchaseReport purchaseReport = optional.orElseThrow(() -> new NoSuchDataException(purchaseReportId));
        //采购总额
        BigDecimal purchaseTotal = new BigDecimal(purchaseReport.getTotal().toString());
        List<ContractConclusion> conclusions = this.conclusionRepository.findAll(Specifications.<ContractConclusion>and().eq("purchaseReport.id", purchaseReportId).build());
        //该采购的第一份合同
        if (conclusions.isEmpty()) {
            return purchaseTotal.doubleValue();
        }
        //修改时，过滤当前合同
        if (StringUtils.isNotBlank(id)) {
            conclusions = conclusions.stream().filter(conclusion -> !conclusion.getId().equals(Long.parseLong(id))).collect(Collectors.toList());
        }
        //所有该采购的合同总额
        BigDecimal contractTotal = new BigDecimal(conclusions.stream().map(ContractConclusion::getTotal).reduce(0.0, Double::sum).toString());
        return purchaseTotal.subtract(contractTotal).doubleValue();
    }

    @Override
    public Boolean getContractNoAvailable(String id, String contractNo) {
        ContractConclusion conclusion = this.conclusionRepository.findByContractNo(contractNo);
        if (null != conclusion) {
            if (StringUtils.isNotBlank(id) && !"null".equals(id)) {
                return conclusion.getId().equals(Long.parseLong(id));
            }
            return false;
        }
        return true;
    }

    @Override
    public Map<Long, Double> recoverAmount(ContractConclusion conclusion, boolean isRecover) {
        Map<Long, Double> map = new HashMap<>(16);
        boolean isPurchase = conclusion.getPurchase();
        if (isPurchase) {
            Set<PurchaseReportIndex> purchaseReportIndices = conclusion.getPurchaseReport().getIndexes();
            conclusion.getIndexes().forEach(contractConclusionIndex -> purchaseReportIndices.forEach(purchaseReportIndex -> {
                if (contractConclusionIndex.getIndex().getId().equals(purchaseReportIndex.getIndex().getId())) {
                    if (isRecover) {
                        this.purchaseReportIndexRepository.updateAmount(0.0, -contractConclusionIndex.getAmount(), purchaseReportIndex.getId());
                    } else {
                        map.put(purchaseReportIndex.getId(), -contractConclusionIndex.getAmount());
                    }
                }
            }));
        } else {
            //获取合同订立指标信息关联的指标列表
            List<IndexLibrary> indexLibraries = conclusion.getIndexes().stream().map(ContractConclusionIndex::getIndex).collect(Collectors.toList());
            conclusion.getIndexes().forEach(contractConclusionIndex -> indexLibraries.forEach(indexLibrary -> {
                if (contractConclusionIndex.getIndex().getId().equals(indexLibrary.getId())) {
                    if (isRecover) {
                        this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, -contractConclusionIndex.getAmount(), 0.0, indexLibrary.getId());
                    } else {
                        map.put(indexLibrary.getId(), -contractConclusionIndex.getAmount());
                    }
                }
            }));
        }
        return map;
    }

    /**
     * 根据合同订立ID获取合同订立信息
     *
     * @param id 合同订立ID
     * @return 合同订立信息
     */
    private ContractConclusion getOne(String id) {
        Optional<ContractConclusion> optional = this.conclusionRepository.findById(Long.parseLong(id));
        return optional.orElseThrow(() -> new NoSuchDataException(id));
    }

    /**
     * 删除关联数据
     *
     * @param conclusion 合同订立实体
     * @param isRecover 是否立刻回退金额
     */
    private Map<Long, Double> deleteRelation(ContractConclusion conclusion, boolean isRecover) {
        Map<Long, Double> map = recoverAmount(conclusion, isRecover);
        this.conclusionIndexRepository.deleteAll(conclusion.getIndexes());
        conclusion.setIndexes(Collections.emptySet());
        this.conclusionParticipantRepository.deleteAll(conclusion.getParticipants());
        conclusion.setParticipants(Collections.emptySet());
        this.conclusionPaymentRepository.deleteAll(conclusion.getPayments());
        conclusion.setPayments(Collections.emptySet());
        this.conclusionPayeeRepository.deleteAll(conclusion.getPayees());
        conclusion.setPayees(Collections.emptySet());
        this.conclusionAttachRepository.deleteAll(conclusion.getAttaches());
        conclusion.setAttaches(Collections.emptySet());
        return map;
    }

    /**
     * 保存关联数据
     *
     * @param conclusion 合同订立实体
     * @param createInfo 合同订立创建信息c
     */
    private void saveRelation(ContractConclusion conclusion, ContractConclusionCreateInfoDTO createInfo, List<String> fileIds, Map<Long, Double> map) {
        //判断本年支出金额总额和本年指标申请金额合计是否相等
        if (!CollectionUtils.isEmpty(createInfo.getIndexes())) {
            Double indexTotal = createInfo.getIndexes().stream().map(index -> index.getAmount()).reduce(0.0, Double::sum);
            String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            Double paymentTotal = createInfo.getPayments().stream().filter(payment -> payment.getYear().equals(currentYear)).map(ContractPaymentEditInfoDTO::getAmount).reduce(0.0, Double::sum);
            if (!indexTotal.equals(paymentTotal)) {
                throw new CommonDataException("本年支出金额总额和本年指标申请金额合计不相等!!!");
            }
        }
        PurchaseReport purchaseReport = null;
        boolean isPurchase = createInfo.getPurchase();
        if (isPurchase) {
            Optional<PurchaseReport> purchaseReportOptional = this.purchaseReportRepository.findById(Long.parseLong(createInfo.getPurchaseReportId()));
            purchaseReport = purchaseReportOptional.orElseThrow(() -> new NoSuchDataException(createInfo.getPurchaseReportId()));
        }
        conclusion.setPurchaseReport(purchaseReport);
        if (null != createInfo.getIndexes() && !createInfo.getIndexes().isEmpty()) {
            this.saveIndexRelation(conclusion, createInfo.getIndexes(), map);
        }
        if (null != createInfo.getParticipants() && !createInfo.getParticipants().isEmpty()) {
            this.saveParticipantRelation(conclusion, createInfo.getParticipants());
        }
        if (null != createInfo.getPayments() && !createInfo.getPayments().isEmpty()) {
            this.savePaymentRelation(conclusion, createInfo.getPayments());
        }
        if (null != createInfo.getPayees() && !createInfo.getPayees().isEmpty()) {
            this.savePayeeRelation(conclusion, createInfo.getPayees());
        }
        if (null != createInfo.getAttaches() && !createInfo.getAttaches().isEmpty()) {
            this.saveAttachRelation(conclusion, createInfo.getAttaches(), fileIds);
        }
    }

    /**
     * 保存指标信息关联数据
     *
     * @param conclusion 合同订立实体
     * @param indexes    合同订立——指标编辑信息
     */
    private void saveIndexRelation(ContractConclusion conclusion, List<ContractIndexEditInfoDTO> indexes, Map<Long, Double> map) {
        boolean isPurchase = conclusion.getPurchase();
        List<ContractConclusionIndex> list;
        if (isPurchase) {
            list = this.getIndexByPurchase(conclusion, indexes, map);
        } else {
            list = this.getIndexByIndexLibrary(conclusion, indexes, map);
        }
        this.conclusionIndexRepository.saveAll(list);
    }

    /**
     * 获取合同订立指标信息,用于关联采购
     *
     * @param conclusion 合同订立对象
     * @param indexes    合同订立——指标编辑信息
     * @return 合同订立——指标信息实体列表
     */
    private List<ContractConclusionIndex> getIndexByPurchase(ContractConclusion conclusion, List<ContractIndexEditInfoDTO> indexes, Map<Long, Double> map) {
        int showOrder = 1;
        List<ContractConclusionIndex> list = new ArrayList<>();
        //采购指标信息
        List<PurchaseReportIndex> purchaseReportIndices = this.purchaseReportIndexRepository.findAll(Specifications.<PurchaseReportIndex>and().in("index.id", indexes.stream().map(ContractIndexEditInfoDTO::getIndexId).map(Long::parseLong).toArray()).build());
        //过滤掉不是关联当前采购申请的采购指标
        purchaseReportIndices = purchaseReportIndices.stream().filter(purchaseReportIndex -> purchaseReportIndex.getPurchaseReport().getId().equals(conclusion.getPurchaseReport().getId())).collect(Collectors.toList());
        Map<String, PurchaseReportIndex> reportIndexMap = purchaseReportIndices.stream().collect(
                Collectors.toMap(index -> index.getIndex().getId().toString(), index -> index)
        );
        for (ContractIndexEditInfoDTO indexDTO : indexes) {
            //增加采购申请在途金额
            PurchaseReportIndex reportIndex = reportIndexMap.get(indexDTO.getIndexId());
            BigDecimal availableAmount = new BigDecimal(reportIndex.getAmount().toString())
                    .subtract(new BigDecimal(reportIndex.getPassageAmount().toString()))
                    .subtract(new BigDecimal(reportIndex.getOccupationAmount().toString()));
            //检查申请金额可用性
            Double mapAmount = this.verifyAvailability(map, reportIndex.getId(), availableAmount, indexDTO.getAmount());
            BigDecimal indexAmount = new BigDecimal(indexDTO.getAmount().toString());
            if (mapAmount != null) {
                indexAmount = indexAmount.add(new BigDecimal(mapAmount.toString()));
            }
            if(conclusion.getState().equals(StateType.DONE)){
                this.purchaseReportIndexRepository.updateAmount(-mapAmount, mapAmount, reportIndex.getId());
            }else {
                this.purchaseReportIndexRepository.updateAmount(0.0, indexAmount.doubleValue(), reportIndex.getId());
            }
            //构建合同订立指标信息对象
            ContractConclusionIndex index = this.buildContractConclusionIndex(conclusion, reportIndex.getIndex(), indexDTO, showOrder++);
            list.add(index);
            map.remove(reportIndex.getId());
        }
        if (!map.isEmpty()) {
            map.keySet().forEach(indexId -> this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, map.get(indexId), 0.0, indexId));
        }
        return list;
    }

    /**
     * 获取合同订立指标信息,用于不关联采购
     *
     * @param conclusion 合同订立对象
     * @param indexes    合同订立——指标编辑信息
     * @return 合同订立——指标信息实体列表
     */
    private List<ContractConclusionIndex> getIndexByIndexLibrary(ContractConclusion conclusion, List<ContractIndexEditInfoDTO> indexes, Map<Long, Double> map) {
        int showOrder = 1;
        List<ContractConclusionIndex> list = new ArrayList<>();
        List<Long> indexIds = indexes.stream().map(ContractIndexEditInfoDTO::getIndexId).map(Long::parseLong).collect(Collectors.toList());
        Map<String, IndexLibrary> indexLibraries = this.indexLibraryRepository.findAllById(indexIds).stream().collect(
                Collectors.toMap(index -> index.getId().toString(), index -> index)
        );
        for (ContractIndexEditInfoDTO indexDTO : indexes) {
            IndexLibrary indexLibrary = indexLibraries.get(indexDTO.getIndexId());
            BigDecimal availableAmount = new BigDecimal(indexLibrary.getAvailableAmount().toString());
            //检查申请金额可用性
            Double mapAmount = this.verifyAvailability(map, indexLibrary.getId(), availableAmount, indexDTO.getAmount());
            BigDecimal amount = new BigDecimal(indexDTO.getAmount().toString());
            if (mapAmount != null) {
                amount = amount.add(new BigDecimal(mapAmount.toString()));
            }
            if(conclusion.getState().equals(StateType.DONE)){
                this.indexLibraryRepository.updateAmount(0.0, 0.0, -mapAmount, mapAmount, 0.0, indexLibrary.getId());
            }else {
                this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, amount.doubleValue(), 0.0, indexLibrary.getId());
                this.indexLibraryRepository.flush();
            }
            //构建合同订立指标信息对象
            ContractConclusionIndex index = this.buildContractConclusionIndex(conclusion, indexLibrary, indexDTO, showOrder++);
            list.add(index);
            map.remove(indexLibrary.getId());
        }
        if (!map.isEmpty()) {
            List<Long> usedId = new ArrayList<>();
            map.keySet().forEach(indexId -> {
                this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, map.get(indexId), 0.0, indexId);
                usedId.add(indexId);
            });
            if (usedId.isEmpty()) {
                map.keySet().forEach(indexId -> this.purchaseReportIndexRepository.updateAmount(0.0, map.get(indexId), indexId));
            }
        }
        return list;
    }

    /**
     * 检查申请余额可用性
     *
     * @param map             金额变更记录map
     * @param id              采购申请指标ID或指标库ID
     * @param availableAmount 可用余额
     * @param indexAmount     申请金额
     * @return map的变更金额
     */
    private Double verifyAvailability(Map<Long, Double> map, Long id, BigDecimal availableAmount, Double indexAmount) {
        Double mapAmount = map.get(id);
        if (mapAmount != null) {
            availableAmount = availableAmount.subtract(new BigDecimal(mapAmount.toString()));
        }
        BigDecimal amount = new BigDecimal(indexAmount.toString());
        if (amount.compareTo(availableAmount) > 0) {
            throw new CommonDataException("申请金额超过余额上限");
        }
        return mapAmount;
    }

    /**
     * 构建合同订立指标对象
     *
     * @param conclusion   合同订立对象
     * @param indexLibrary 指标库对象
     * @param indexDTO     合同订立——指标编辑信息
     * @param showOrder    排序
     * @return 合同订立指标对象
     */
    private ContractConclusionIndex buildContractConclusionIndex(ContractConclusion conclusion, IndexLibrary indexLibrary, ContractIndexEditInfoDTO indexDTO, int showOrder) {
        ContractConclusionIndex index = this.conclusionIndexMapper.toEntity(indexDTO);
        index.setContractConclusion(conclusion);
        index.setIndex(indexLibrary);
        index.setPaidAmount(0.0);
        index.setShowOrder(showOrder);
        return index;
    }

    /**
     * 保存合同参与方信息关联数据
     *
     * @param conclusion   合同订立实体
     * @param participants 合同订立——参与方编辑信息
     */
    private void saveParticipantRelation(ContractConclusion conclusion, List<ContractParticipantEditInfoDTO> participants) {
        int showOrder = 1;
        List<ContractConclusionParticipant> list = new ArrayList<>();
        for (ContractParticipantEditInfoDTO participantDTO : participants) {
            ContractConclusionParticipant participant = this.conclusionParticipantMapper.toEntity(participantDTO);
            participant.setContractConclusion(conclusion);
            participant.setShowOrder(showOrder++);
            list.add(participant);
        }
        this.conclusionParticipantRepository.saveAll(list);
    }

    /**
     * 保存合同订立付款信息关联数据
     *
     * @param conclusion 合同订立实体
     * @param payments   合同订立——付款编辑信息
     */
    private void savePaymentRelation(ContractConclusion conclusion, List<ContractPaymentEditInfoDTO> payments) {
        int showOrder = 1;
        List<ContractConclusionPayment> list = new ArrayList<>();
        for (ContractPaymentEditInfoDTO paymentDTO : payments) {
            ContractConclusionPayment payment = this.conclusionPaymentMapper.toEntity(paymentDTO);
            payment.setContractConclusion(conclusion);
            payment.setShowOrder(showOrder++);
            list.add(payment);
        }
        this.conclusionPaymentRepository.saveAll(list);
    }

    /**
     * 保存收款人银行账户信息关联数据
     *
     * @param conclusion 合同订立实体
     * @param payees     合同订立——收款人银行账户编辑信息
     */
    private void savePayeeRelation(ContractConclusion conclusion, List<ContractPayeeEditInfoDTO> payees) {
        int showOrder = 1;
        List<ContractConclusionPayee> list = new ArrayList<>();
        for (ContractPayeeEditInfoDTO payeeDTO : payees) {
            ContractConclusionPayee payee = this.conclusionPayeeMapper.toEntity(payeeDTO);
            payee.setContractConclusion(conclusion);
            payee.setShowOrder(showOrder++);
            list.add(payee);
        }
        this.conclusionPayeeRepository.saveAll(list);
    }

    /**
     * 保存附件信息关联数据
     *
     * @param conclusion 合同订立实体
     * @param attaches   合同订立——采购计划附件编辑信息
     */
    private void saveAttachRelation(ContractConclusion conclusion, List<AttachEditInfoDTO> attaches, List<String> fileIds) {
        int showOrder = 1;
        List<ContractConclusionAttach> list = new ArrayList<>();
        if (null != fileIds && !fileIds.isEmpty()) {
            attaches.forEach(attachDTO -> attachDTO.getFileIds().forEach(fileIds::remove));
            List<Long> oldFileIds = fileIds.stream().map(Long::parseLong).collect(Collectors.toList());
            this.commonUtil.deleteFile(this.systemFileRepository.findAllById(oldFileIds));
        }
        for (AttachEditInfoDTO attachDTO : attaches) {
            ContractConclusionAttach attach = this.conclusionAttachMapper.toEntity(attachDTO);
            if (null != attachDTO.getFileIds() && !attachDTO.getFileIds().isEmpty()) {
                List<Long> ids = attachDTO.getFileIds().stream().map(Long::parseLong).collect(Collectors.toList());
                List<SystemFile> files = this.systemFileRepository.findAllById(ids);
                attach.setFiles(files);
            }
            attach.setContractConclusion(conclusion);
            attach.setShowOrder(showOrder++);
            list.add(attach);
        }
        this.conclusionAttachRepository.saveAll(list);
    }
}
