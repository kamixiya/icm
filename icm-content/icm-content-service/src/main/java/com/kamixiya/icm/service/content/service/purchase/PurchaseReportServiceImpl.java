package com.kamixiya.icm.service.content.service.purchase;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.purchase.*;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.persistence.content.entity.purchase.*;
import com.kamixiya.icm.persistence.content.repository.indexLibrary.IndexLibraryRepository;
import com.kamixiya.icm.persistence.content.repository.purchase.*;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.exception.CommonDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.repository.base.SystemFileRepository;
import com.kamixiya.icm.service.common.repository.organization.OrganizationRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.content.mapper.purchase.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 采购申请服务实现类
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Service("purchaseReportService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PurchaseReportServiceImpl implements PurchaseReportService {

    private final PurchaseReportRepository purchaseReportRepository;
    private final PurchaseReportDetailRepository purchaseReportDetailRepository;
    private final PurchaseReportDetailMapper purchaseReportDetailMapper;
    private final PurchaseReportDetailIndexRepository purchaseReportDetailIndexRepository;
    private final PurchaseReportIndexRepository purchaseReportIndexRepository;
    private final SystemFileRepository systemFileRepository;
    private final PurchaseReportMapper purchaseReportMapper;
    private final PurchaseReportIndexMapper purchaseReportIndexMapper;
    private final PurchaseAttachRepository purchaseAttachRepository;
    private final PurchaseAttachMapper purchaseAttachMapper;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final IndexLibraryRepository indexLibraryRepository;
    private final PurchaseReportDetailIndexMapper purchaseReportDetailIndexMapper;

    @Autowired
    public PurchaseReportServiceImpl(PurchaseReportRepository purchaseReportRepository, PurchaseReportDetailRepository purchaseReportDetailRepository,
                                     PurchaseReportDetailMapper purchaseReportDetailMapper, PurchaseReportIndexRepository purchaseReportIndexRepository,
                                     SystemFileRepository systemFileRepository,
                                     PurchaseReportMapper purchaseReportMapper, PurchaseReportIndexMapper purchaseReportIndexMapper,
                                     PurchaseAttachRepository purchaseAttachRepository, PurchaseAttachMapper purchaseAttachMapper,
                                     OrganizationRepository organizationRepository, UserRepository userRepository,
                                     ObjectMapper objectMapper, IndexLibraryRepository indexLibraryRepository,
                                     PurchaseReportDetailIndexRepository purchaseReportDetailIndexRepository, PurchaseReportDetailIndexMapper purchaseReportDetailIndexMapper) {
        this.purchaseReportRepository = purchaseReportRepository;
        this.purchaseReportDetailRepository = purchaseReportDetailRepository;
        this.purchaseReportDetailMapper = purchaseReportDetailMapper;
        this.purchaseReportDetailIndexRepository = purchaseReportDetailIndexRepository;
        this.purchaseReportIndexRepository = purchaseReportIndexRepository;
        this.systemFileRepository = systemFileRepository;
        this.purchaseReportMapper = purchaseReportMapper;
        this.purchaseReportIndexMapper = purchaseReportIndexMapper;
        this.purchaseAttachRepository = purchaseAttachRepository;
        this.purchaseAttachMapper = purchaseAttachMapper;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.indexLibraryRepository = indexLibraryRepository;
        this.purchaseReportDetailIndexMapper = purchaseReportDetailIndexMapper;
    }

    @Override
    public PageDataDTO<PurchaseReportDTO> findOnePage(int page, int size, String sort, StateType stateType, String declarerName, String declarerId, String title, String unitId, Date beginDate, Date endDate, Double beginAmount, Double endAmount, String code, String detailId) {
        //  实现 分页查询采购申请信息
        PredicateBuilder<PurchaseReport> pb = getAllSpecification(stateType, declarerName, declarerId, title, unitId, beginDate, endDate, beginAmount, endAmount, code, detailId);
        Page<PurchaseReport> reportPage = this.purchaseReportRepository.findAll(pb.build(), PageHelper.generatePageRequest(page, size, sort));
        List<PurchaseReportDTO> purchaseReportDTOS = this.purchaseReportMapper.toList(reportPage.getContent());
        return PageDataUtil.toPageData(reportPage, purchaseReportDTOS);
    }


    @Override
    public List<PurchaseReportDTO> findAvailable(String departmentId, String id) {
        List<PurchaseReport> list = this.purchaseReportRepository.findAll(
                Specifications.<PurchaseReport>and()
                        .eq("state", StateType.DONE)
                        .eq(StringUtils.isNotBlank(departmentId), "department.id", departmentId)
                        .predicate(((root, query, cb) -> {
                            Join<PurchaseReport, PurchaseReportIndex> indexJoin = root.join("indexes", JoinType.LEFT);
                            return cb.and(cb.greaterThan(indexJoin.get("availableAmount"), 0));
                        }))
                        .build());
        Set<PurchaseReport> reports = new LinkedHashSet<>(list);
        if (StringUtils.isNotBlank(id) && !"undefined".equalsIgnoreCase(id)) {
            PurchaseReport report = purchaseReportRepository.getOne(Long.valueOf(id));
            reports.add(report);
        }
        return conversionDTOList(reports);
    }

    @Override
    public List<PurchaseReportDTO> findAllDone(String id, String departmentId) {
        //  实现 查询全部采购信息
        Long deptId = null;
        if (StringUtils.isNotBlank(departmentId)){
            deptId = Long.valueOf(departmentId);
        }
        Set<PurchaseReport> set = new HashSet<>(purchaseReportRepository.findAll(
                Specifications.<PurchaseReport>and()
                        .eq("state", StateType.DONE)
                        .eq(StringUtils.isNotBlank(departmentId), "department.id", deptId)
                        .build()));
        if (StringUtils.isNotBlank(id) && !"undefined".equalsIgnoreCase(id)) {
            List<PurchaseReport> reportList = purchaseReportRepository.findAll(
                    Specifications.<PurchaseReport>and()
                            .eq("id", Long.valueOf(id))
                            .build()
            );
            set.addAll(reportList);
        }
        return conversionDTOList(new ArrayList<>(set));
    }

    /**
     * 将查询出的实体集合转换成dto集合，并填充数据
     *
     * @param list  实体集合
     * @return dto集合
     */
    private List<PurchaseReportDTO> conversionDTOList(Collection<PurchaseReport> list) {
        List<PurchaseReportDTO> dtoList = purchaseReportMapper.toList(list);
        Map<Long, Set<PurchaseReportDetail>> detailMap = new HashMap<>();
        Map<Long, Set<PurchaseAttach>> attachMap = new HashMap<>();
        Map<Long, Set<PurchaseReportIndex>> indexMap = new HashMap<>();
        list.forEach(report -> {
            attachMap.put(report.getId(), report.getAttaches());
            indexMap.put(report.getId(), report.getIndexes());
            detailMap.put(report.getId(), report.getDetails());
        });

        dtoList.forEach(dto -> {
            Long key = Long.valueOf(dto.getId());
            Set<PurchaseReportDetail> details = detailMap.get(key);
            dto.setAttaches(purchaseAttachMapper.toList(new ArrayList<>(attachMap.get(key))));
            dto.setIndexes(purchaseReportIndexMapper.toList(new ArrayList<>(indexMap.get(key))));
           if (null != details && !details.isEmpty()) {
                dto.setDetails(purchaseReportDetailMapper.toList(new ArrayList<>(details)));
            }
        });
        return dtoList;
    }

    @Override
    public PurchaseReportDTO findById(String id) {
        //  实现 根据ID查询采购申请
        PurchaseReport entity = purchaseReportRepository.findOne(Specifications.<PurchaseReport>and()
                .eq("id", Long.valueOf(id))
                .build()).orElseThrow(() -> new NoSuchDataException(id));
        PurchaseReportDTO purchaseReportDTO = this.purchaseReportMapper.toDTO(entity);
        purchaseReportDTO.setAttaches(purchaseAttachMapper.toList(new ArrayList<>(entity.getAttaches())));
        if (entity.getDetails() != null && !entity.getDetails().isEmpty()) {
            purchaseReportDTO.setDetails(purchaseReportDetailMapper.toList(new ArrayList<>(entity.getDetails())));

        }
        purchaseReportDTO.setIndexes(purchaseReportIndexMapper.toList(new ArrayList<>(entity.getIndexes())));
        purchaseReportDTO.setAttaches(purchaseAttachMapper.toList(new ArrayList<>(entity.getAttaches())));
        return purchaseReportDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchaseReportDTO create(PurchaseReportCreateInfoDTO createInfoDTO) throws IOException {
        //  实现 新建采购申请
        PurchaseReport entity = this.purchaseReportMapper.toEntity(createInfoDTO);
        entity.setDepartment(this.organizationRepository.findById(Long.parseLong(createInfoDTO.getDepartmentId())).orElseThrow(() -> new NoSuchDataException(createInfoDTO.getDepartmentId())));
        entity.setUnit(this.organizationRepository.findById(Long.parseLong(createInfoDTO.getUnitId())).orElseThrow(() -> new NoSuchDataException(createInfoDTO.getUnitId())));
        entity.setDeclarer(this.userRepository.findById(Long.parseLong(createInfoDTO.getDeclarerId())).orElseThrow(() -> new NoSuchDataException(createInfoDTO.getDeclarerId())));
        entity.setState(StateType.UNDONE);
        entity = this.purchaseReportRepository.save(entity);
        entity = saveRelation(entity, createInfoDTO);
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        Map<String, Object> map = objectMapper.convertValue(createInfoDTO, javaType);
        entity = this.purchaseReportRepository.save(entity);
        return this.purchaseReportMapper.toDTO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchaseReportDTO complete(String id, PurchaseReportEditInfoDTO editInfoDTO) throws IOException {
        //  实现 提交采购申请
        PurchaseReport entity = this.purchaseReportRepository.findById(Long.parseLong(id)).orElseThrow(() -> new NoSuchDataException(id));
        Set<PurchaseAttach> files = entity.getAttaches();
        Set<Long> oldFileIds = new HashSet<>();
        for (PurchaseAttach attach : files) {
            List<SystemFile> systemFiles = attach.getFiles();
            if (!systemFiles.isEmpty()) {
                oldFileIds.addAll(systemFiles.stream().map(SystemFile::getId).collect(Collectors.toSet()));
            }
        }
        List<String> dtoFileIds = new ArrayList<>();
        if (null != editInfoDTO.getAttaches()) {
            editInfoDTO.getAttaches().forEach(attachDto -> dtoFileIds.addAll(attachDto.getFileIds()));
        }

        deleteRelation(entity);

        Set<Long> newFileIds = new HashSet<>();
        editInfoDTO.getAttaches().forEach(attach -> {
            List<String> fileIds = attach.getFileIds();
            if (fileIds != null && !fileIds.isEmpty()) {
                newFileIds.addAll(fileIds.stream().map(Long::valueOf).collect(Collectors.toSet()));
            }
        });
        List<SystemFile> removeFiles = new ArrayList<>();
        if (!oldFileIds.isEmpty()) {
            removeFiles = systemFileRepository.findAll(
                    Specifications.<SystemFile>and().in("id", oldFileIds.toArray())
                            .notIn(!newFileIds.isEmpty(), "id", newFileIds.toArray()).build());
        }
        purchaseReportMapper.updateEntity(editInfoDTO, entity);
        entity.setState(StateType.DONE);
        entity = purchaseReportRepository.save(entity);
        //保存关联
        entity = saveRelation(entity, editInfoDTO);
        //删除无用文件
        if (!removeFiles.isEmpty()) {
            systemFileRepository.deleteAll(removeFiles);
        }
        entity = purchaseReportRepository.save(entity);

        return purchaseReportMapper.toDTO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        // 实现 删除采购申请
        PurchaseReport entity = this.purchaseReportRepository.findById(Long.parseLong(id)).orElseThrow(() -> new NoSuchDataException(id));
        if (!StateType.UNDONE.equals(entity.getState())) {
            throw new CommonDataException("非草稿状态不可删除");
        }
        deleteRelation(entity);
        this.purchaseReportRepository.delete(entity);
    }

    @Override
    public Map<String, Double> getIndex(String id, List<String> ids) {
        //通过ids获取 指标list 转换成map<指标Id,指标>
        Map<String, IndexLibrary> indexMap = new HashMap<>();
        //指标库可用余额
        List<IndexLibrary> libraries = indexLibraryRepository.findAllById(ids.stream().map(Long::valueOf).collect(Collectors.toList()));
        libraries.forEach(indexLibrary -> indexMap.put(indexLibrary.getId().toString(), indexLibrary));
        //计算指标库中的可用余额
        Map<String, Double> availableAmountMap = new HashMap<>();
        for (String indexId : indexMap.keySet()) {
            IndexLibrary library = indexMap.get(indexId);
            Double availableAmount = new BigDecimal(library.getAllocationAmount()).add(new BigDecimal(library.getAdjustmentAmount()))
                    .subtract(new BigDecimal(library.getOccupationAmount())).subtract(new BigDecimal(library.getPassageAmount())).doubleValue();
            availableAmountMap.put(indexId, availableAmount);
        }

        //通过id获取purchaseReport
        PurchaseReport entity = null;
        if (StringUtils.isNotBlank(id) && !"undefined".equals(id)) {
            entity = purchaseReportRepository.findById(Long.valueOf(id)).orElseThrow(() -> new NoSuchDataException(id));
        }
        //通过实体获取品目指标list
        Map<String, Double> currentYearAmountMap = new HashMap<>();
        if (null != entity) {
            Set<PurchaseReportDetail> details = entity.getDetails();
            Set<PurchaseReportIndex> consumableIndices = entity.getIndexes();
            if (null != details && !details.isEmpty()) {
                //品目指标信息
                List<PurchaseReportDetailIndex> detailIndices = new ArrayList<>();
                details.forEach(detail -> detailIndices.addAll(detail.getIndexes()));
                // 转换成map<指标id,品目指标>  此处使用guava的Multimap
                Multimap<String, PurchaseReportDetailIndex> detailIndexMultimap = ArrayListMultimap.create();
                detailIndices.forEach(detailIndex -> detailIndexMultimap.put(detailIndex.getIndex().getId().toString(), detailIndex));
                // 计算相同指标id的品目所占用的金额/采购金额  转换成map<指标id,占用金额>
                for (String indexId : detailIndexMultimap.keySet()) {
                    List<PurchaseReportDetailIndex> list = new ArrayList<>(detailIndexMultimap.get(indexId));
                    Double currentYearAmount = list.stream().map(detailIndex -> new BigDecimal(detailIndex.getCurrentYearAmount())).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue();
                    currentYearAmountMap.put(indexId, currentYearAmount);
                }
                return calculationAmount(availableAmountMap, currentYearAmountMap);
            }
            Map<String, Double> consumableAmount = new HashMap<>();
            if (null != consumableIndices && !consumableIndices.isEmpty()) {
                //取出耗材中的占用金额
                consumableIndices.forEach(consumable -> consumableAmount.put(consumable.getIndex().getId().toString(), consumable.getAmount()));
                //计算真实可用余额
                return calculationAmount(availableAmountMap, consumableAmount);
            }
        }
        return availableAmountMap;
    }

    /**
     * 计算真实可用余额
     *
     * @param availableAmountMap    指标可用余额
     * @param occupationAmountMap   占用余额
     * @return 真实可用余额
     */
    private Map<String, Double> calculationAmount(Map<String, Double> availableAmountMap, Map<String, Double> occupationAmountMap) {
        if (availableAmountMap != null && !availableAmountMap.isEmpty()) {
            //计算真实可用余额
            for (String indexId : availableAmountMap.keySet()) {
                BigDecimal currentYearAmount = BigDecimal.ZERO;
                if (null != occupationAmountMap.get(indexId)) {
                    currentYearAmount = new BigDecimal(occupationAmountMap.get(indexId));
                }
                BigDecimal availAmount = new BigDecimal(availableAmountMap.get(indexId));
                occupationAmountMap.put(indexId, availAmount.add(currentYearAmount).doubleValue());
            }
        }
        return occupationAmountMap;
    }

    /**
     * 保存关联
     *
     * @param entity        实体
     * @param createInfoDTO 编辑信息
     */
    private PurchaseReport saveRelation(PurchaseReport entity, PurchaseReportCreateInfoDTO createInfoDTO) {
        int showOrder = 1;
        if (null != createInfoDTO.getAttaches() && !createInfoDTO.getAttaches().isEmpty()) {
            List<PurchaseAttach> attaches = new ArrayList<>();
            List<PurchaseAttach> finalAttaches;
            for (AttachEditInfoDTO attach : createInfoDTO.getAttaches()) {
                List<SystemFile> files = this.systemFileRepository.findAllById(attach.getFileIds().stream().map(Long::valueOf).collect(Collectors.toList()));
                PurchaseAttach purchaseAttach = this.purchaseAttachMapper.toEntity(attach);
                purchaseAttach.setFileType(attach.getFileType());
                purchaseAttach.setPurchaseReport(entity);
                purchaseAttach.setShowOrder(showOrder++);
                purchaseAttach.setFiles(files);
                attaches.add(purchaseAttach);
            }
            finalAttaches = this.purchaseAttachRepository.saveAll(attaches);
            entity.setAttaches(new HashSet<>(finalAttaches));
        }

        if (null != createInfoDTO.getDetails() && !createInfoDTO.getDetails().isEmpty()) {
            //  填充属性
            showOrder = 1;
            List<PurchaseReportDetail> list = new ArrayList<>();
            for (PurchaseReportDetailEditInfoDTO dto : createInfoDTO.getDetails()) {
                PurchaseReportDetail purchaseReportDetail = this.purchaseReportDetailMapper.dtoToEntity(dto);
                purchaseReportDetail.setPurchaseReport(entity);
                purchaseReportDetail.setPurchaseType(dto.getPurchaseType());
                purchaseReportDetail.setPurchaseDetail(dto.getPurchaseDetail());
                purchaseReportDetail.setSpec(dto.getSpec());
                purchaseReportDetail.setPrice(dto.getPrice());
                purchaseReportDetail.setQuantityTotal(dto.getIndexes().stream().map(editDto -> new BigDecimal(editDto.getQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
                purchaseReportDetail.setTotal(dto.getIndexes().stream().map(editDto -> new BigDecimal(editDto.getQuantity() * dto.getPrice())).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
                purchaseReportDetail.setShowOrder(showOrder++);
                purchaseReportDetail = purchaseReportDetailRepository.save(purchaseReportDetail);
                int detailIndexShowOrder = 1;
                List<PurchaseReportDetailIndex> reportDetailIndices = new ArrayList<>();
                List<PurchaseReportIndex> purchaseReportIndices = new ArrayList<>();
                for (PurchaseReportIndexEditInfoDTO index : dto.getIndexes()) {
                    PurchaseReportDetailIndex reportDetailIndex = purchaseReportDetailIndexMapper.toEntity(index);
                    //修改指标库数据
                    IndexLibrary indexLibrary = indexLibraryRepository.findById(Long.valueOf(index.getIndexId())).orElseThrow(() -> new NoSuchDataException(index.getIndexId()));
                    //更新品目指标可用余额、占用
                    indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, reportDetailIndex.getCurrentYearAmount(), 0.0, Long.valueOf(index.getIndexId()));
                    //更新采购指标
                    reportDetailIndex.setAvailableAmount(index.getCurrentYearAmount());
                    reportDetailIndex.setOccupationAmount(0.0);
                    reportDetailIndex.setPassageAmount(0.0);
                    reportDetailIndex.setCurrentYearAmount(index.getCurrentYearAmount());
                    reportDetailIndex.setPurchaseReportDetail(purchaseReportDetail);
                    reportDetailIndex.setShowOrder(detailIndexShowOrder);
                    reportDetailIndex.setIndex(indexLibrary);
                    reportDetailIndices.add(reportDetailIndex);
                    //审结时将在途金额变为占用
                    if (StateType.DONE.equals(entity.getState())) {
                        indexLibraryRepository.updateAmount(0.0, 0.0, reportDetailIndex.getCurrentYearAmount(), -reportDetailIndex.getCurrentYearAmount(), 0.0, Long.valueOf(index.getIndexId()));
                    }

                    PurchaseReportIndex purchaseReportIndex = purchaseReportIndexMapper.toEntity(index);
                    purchaseReportIndex.setAvailableAmount(index.getCurrentYearAmount());
                    purchaseReportIndex.setPurchaseReport(entity);
                    purchaseReportIndex.setIndex(indexLibrary);
                    purchaseReportIndex.setShowOrder(detailIndexShowOrder++);
                    purchaseReportIndex.setAmount(index.getAmount());
                    purchaseReportIndex.setPassageAmount(0.0);
                    purchaseReportIndex.setIndex(indexLibrary);
                    purchaseReportIndex.setOccupationAmount(0.0);
                    purchaseReportIndex.setQuantity(index.getQuantity());
                    purchaseReportIndices.add(purchaseReportIndex);
                }
                reportDetailIndices = purchaseReportDetailIndexRepository.saveAll(reportDetailIndices);
                purchaseReportDetail.setIndexes(reportDetailIndices);
                list.add(purchaseReportDetail);
                entity.setIndexes(new HashSet<>(purchaseReportIndices));
                entity.setTotal(purchaseReportIndices.stream().map(index -> new BigDecimal(index.getAmount())).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
                purchaseReportIndexRepository.saveAll(purchaseReportIndices);
            }
            entity.setTotal(list.stream().map(detail -> new BigDecimal(detail.getTotal())).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
            entity.setDetails(new HashSet<>(list));
        }
        return entity;
    }

    /**
     * 删除关联
     *
     * @param entity 实体信息
     */
    private void deleteRelation(PurchaseReport entity) {
        List<PurchaseReportDetailIndex> indices = new ArrayList<>();
        if (entity.getDetails() != null && !entity.getDetails().isEmpty()) {
            entity.getDetails().forEach(detail -> indices.addAll(detail.getIndexes()));
            //释放品目关联的指标
            if (!indices.isEmpty()) {
                for (PurchaseReportDetailIndex detailIndex : indices) {
                    indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, -detailIndex.getCurrentYearAmount(), 0.0, detailIndex.getIndex().getId());
                }
            }
        } else if (null != entity.getIndexes() && !entity.getIndexes().isEmpty()) {
            //释放耗材的指标
            for (PurchaseReportIndex purchaseReportIndex : entity.getIndexes()) {
                indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, -purchaseReportIndex.getAmount(), 0.0, purchaseReportIndex.getIndex().getId());
            }
        }

        this.purchaseReportDetailIndexRepository.deleteAll(indices);
        this.purchaseReportDetailRepository.deleteAll(entity.getDetails());
        entity.setDetails(Collections.emptySet());

        this.purchaseReportIndexRepository.deleteAll(entity.getIndexes());
        entity.setIndexes(Collections.emptySet());

        purchaseAttachRepository.deleteAll(entity.getAttaches());
        entity.setAttaches(Collections.emptySet());
    }

    /**
     * 构建查询条件
     *
     * @param stateType          状态
     * @param departmentId       申请部门id
     * @param declarerName       申请人姓名
     * @param title              标题
     * @param beginAmount        最小金额
     * @param endAmount          最大金额
     * @param code               编码
     * @param detail       品目名称
     * @return 构建的条件集合
     */
    private PredicateBuilder<PurchaseReport> getAllSpecification(StateType stateType, String departmentId, String declarerName, String title, String unitId, Date beginDate, Date endDate, Double beginAmount, Double endAmount, String code, String detail) {
        return Specifications.<PurchaseReport>and()
                .eq(StringUtils.isNotBlank(departmentId), "department.id", departmentId)
                .predicate(null != beginAmount, ((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("total").as(Double.class), beginAmount))))
                .predicate(null != endAmount, ((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("total").as(Double.class), endAmount))))
                .like(StringUtils.isNotBlank(code), "code", "%" + code + "%")
                .like(StringUtils.isNotBlank(declarerName), "declarer.name", "%" + declarerName + "%")
                .like(StringUtils.isNotBlank(title), "title", "%" + title + "%")
                .eq(StringUtils.isNotBlank(unitId), "unit.id", unitId)
                .le(Objects.nonNull(endDate), "applyDate", endDate)
                .ge(Objects.nonNull(beginDate), "applyDate", beginDate)
                .like(StringUtils.isNotBlank(detail), "purchaseDetail", "%" + detail + "%");
    }

}
