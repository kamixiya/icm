package com.kamixiya.icm.service.content.service.budget;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.budget.adjustment.*;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.budget.adjustment.*;
import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProject;
import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProjectDetail;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexType;
import com.kamixiya.icm.persistence.content.repository.adjustment.IndexAdjustmentCreateDetailRepository;
import com.kamixiya.icm.persistence.content.repository.adjustment.IndexAdjustmentDetailRepository;
import com.kamixiya.icm.persistence.content.repository.adjustment.IndexAdjustmentRepository;
import com.kamixiya.icm.persistence.content.repository.budget.GeneralProjectDetailRepository;
import com.kamixiya.icm.persistence.content.repository.budget.GeneralProjectRepository;
import com.kamixiya.icm.persistence.content.repository.indexLibrary.IndexLibraryRepository;
import com.kamixiya.icm.service.common.entity.AbstractBaseEntity;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.exception.CommonDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.repository.base.SystemFileRepository;
import com.kamixiya.icm.service.common.repository.organization.OrganizationRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.content.mapper.budget.IndexAdjustmentCreateDetailMapper;
import com.kamixiya.icm.service.content.mapper.budget.IndexAdjustmentDetailMapper;
import com.kamixiya.icm.service.content.mapper.budget.IndexAdjustmentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * IndexAdjustmentServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/17
 */
@Service("indexAdjustmentService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class IndexAdjustmentServiceImpl implements IndexAdjustmentService {

    private static final String WORKFLOW_KEY = "INDEX_ADJUSTMENT";
    private static final String DATA_SCOPE_FILTER = "INDEX_ADJUSTMENT";

    private final IndexAdjustmentMapper indexAdjustmentMapper;
    private final IndexAdjustmentDetailMapper indexAdjustmentDetailMapper;
    private final IndexAdjustmentCreateDetailMapper indexAdjustmentCreateDetailMapper;
    private final IndexAdjustmentRepository indexAdjustmentRepository;
    private final IndexAdjustmentDetailRepository indexAdjustmentDetailRepository;
    private final IndexAdjustmentCreateDetailRepository indexAdjustmentCreateDetailRepository;
    private final IndexLibraryRepository indexLibraryRepository;
    private final SystemFileRepository systemFileRepository;
    private final OrganizationRepository organizationRepository;
    private final GeneralProjectRepository generalProjectRepository;
    private final GeneralProjectDetailRepository generalProjectDetailRepository;

    public IndexAdjustmentServiceImpl(IndexAdjustmentMapper indexAdjustmentMapper, IndexAdjustmentDetailMapper indexAdjustmentDetailMapper,
                                      IndexAdjustmentCreateDetailMapper indexAdjustmentCreateDetailMapper,
                                      IndexAdjustmentRepository indexAdjustmentRepository, IndexLibraryRepository indexLibraryRepository,
                                      IndexAdjustmentDetailRepository indexAdjustmentDetailRepository, IndexAdjustmentCreateDetailRepository indexAdjustmentCreateDetailRepository,
                                      SystemFileRepository systemFileRepository, OrganizationRepository organizationRepository, GeneralProjectRepository generalProjectRepository, GeneralProjectDetailRepository generalProjectDetailRepository) {
        this.indexAdjustmentMapper = indexAdjustmentMapper;
        this.indexAdjustmentDetailMapper = indexAdjustmentDetailMapper;
        this.indexAdjustmentCreateDetailMapper = indexAdjustmentCreateDetailMapper;
        this.indexAdjustmentRepository = indexAdjustmentRepository;
        this.indexAdjustmentDetailRepository = indexAdjustmentDetailRepository;
        this.indexAdjustmentCreateDetailRepository = indexAdjustmentCreateDetailRepository;
        this.indexLibraryRepository = indexLibraryRepository;
        this.systemFileRepository = systemFileRepository;
        this.organizationRepository = organizationRepository;
        this.generalProjectRepository = generalProjectRepository;
        this.generalProjectDetailRepository = generalProjectDetailRepository;
    }


    @Override
    public IndexAdjustmentDTO findById(String id) {
        IndexAdjustment adjustment = getOne(id);
        IndexAdjustmentDTO adjustmentDTO = this.indexAdjustmentMapper.toDTO(adjustment, new CycleAvoidingMappingContext());
        List<IndexAdjustmentDetailDTO> increaseDetails = new ArrayList<>();
        List<IndexAdjustmentDetailDTO> reduceDetails = new ArrayList<>();

        adjustment.getAdjustmentDetails().forEach(detail -> {
            if (detail.getDetailType() == AdjustDetailType.INCREASE) {
                increaseDetails.add(indexAdjustmentDetailMapper.toDTO(detail));
            } else if (detail.getDetailType() == AdjustDetailType.REDUCE) {
                reduceDetails.add(indexAdjustmentDetailMapper.toDTO(detail));
            }
        });
        adjustmentDTO.setIncreaseDetails(increaseDetails);
        adjustmentDTO.setReduceDetails(reduceDetails);

        return adjustmentDTO;
    }

    @Override
    public PageDataDTO<IndexAdjustmentDTO> findOnePage(int page, int size, String sort, String year, String projectCode, String projectName, AdjustType type, Double amountBegin, Double amountEnd) throws ParseException {
        Date adjustDateBegin = null;
        Date adjustDateEnd = null;
        if (StringUtils.isNotBlank(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            adjustDateBegin = sdf.parse(year);
            year = (Integer.parseInt(year) + 1) + "";
            adjustDateEnd = sdf.parse(year);
        }
        PredicateBuilder<IndexAdjustment> pb = Specifications.<IndexAdjustment>and()
                .ge(StringUtils.isNotBlank(year), "adjustDate", adjustDateBegin)
                .le(StringUtils.isNotBlank(year), "adjustDate", adjustDateEnd
                )
                .like(StringUtils.isNotBlank(projectCode), "projectCode", "%" + projectCode + "%")
                .like(StringUtils.isNotBlank(projectName), "projectName", "%" + projectName + "%")
                .eq(null != type, "adjustType", type)
                .ge(amountBegin != null, "total", amountBegin)
                .le(amountEnd != null, "total", amountEnd);
        Page<IndexAdjustment> adjustmentPage = this.indexAdjustmentRepository.findAll(pb.build(), PageHelper.generatePageRequest(page, size, sort));
        List<IndexAdjustmentDTO> content = this.indexAdjustmentMapper.toList(adjustmentPage.getContent(), new CycleAvoidingMappingContext());
        return PageDataUtil.toPageData(adjustmentPage, content);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IndexAdjustmentDTO create(IndexAdjustmentCreateInfoDTO createInfo) {
        IndexAdjustment adjustment = this.indexAdjustmentMapper.toEntity(createInfo, new CycleAvoidingMappingContext());
        setUnitAndDepartment(adjustment, createInfo);
        adjustment.setStateType(StateType.UNDONE);
        adjustment = this.indexAdjustmentRepository.save(adjustment);
        saveFile(adjustment, createInfo.getFileIds());
        saveRelation(adjustment, createInfo);
        adjustment = this.indexAdjustmentRepository.save(adjustment);
        return this.indexAdjustmentMapper.toDTO(adjustment, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IndexAdjustmentDTO complete(String id, IndexAdjustmentEditInfoDTO editInfo) {
        IndexAdjustment adjustment = getOne(id);
        deleteRelation(adjustment);
        List<SystemFile> fileIds = adjustment.getFiles();
        adjustment.setFiles(Collections.emptyList());
        this.indexAdjustmentMapper.updateEntity(editInfo, adjustment);
        setUnitAndDepartment(adjustment, editInfo);
        adjustment.setStateType(StateType.DONE);
        updateFile(fileIds, editInfo.getFileIds(), adjustment);
        saveRelation(adjustment, editInfo);
        adjustment = this.indexAdjustmentRepository.save(adjustment);
        setIndexLibrary(adjustment);
        return this.indexAdjustmentMapper.toDTO(adjustment, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        IndexAdjustment adjustment = getOne(id);
        deleteRelation(adjustment);
        deleteFile(adjustment);
        this.indexAdjustmentRepository.delete(adjustment);
    }

    @Override
    public Boolean checkAvailability(String id, String indexId, Double amount) {
        BigDecimal beforeAmount = new BigDecimal("0");

        if (!StringUtils.isBlank(id)) {
            IndexAdjustment adjustment = getOne(id);
            if (AdjustType.REDUCE.equals(adjustment.getAdjustType())) {
                Long indexIdLong = Long.valueOf(indexId);

                beforeAmount = adjustment.getAdjustmentDetails().stream().filter(detail -> detail.getDetailType() == AdjustDetailType.REDUCE && detail.getIndexLibrary().getId().equals(indexIdLong))
                        .map(item -> new BigDecimal(Double.toString(item.getAdjustAmount()))).reduce(new BigDecimal("0"), BigDecimal::add);
            }
        }

        Optional<IndexLibrary> optional = this.indexLibraryRepository.findById(Long.parseLong(indexId));
        IndexLibrary indexLibrary = optional.orElseThrow(() -> new NoSuchDataException(indexId));
        BigDecimal availableAmount = BigDecimal.valueOf(indexLibrary.getAvailableAmount());
        availableAmount = availableAmount.add(beforeAmount);
        return availableAmount.compareTo(new BigDecimal(Double.toString(amount))) >= 0;
    }

    private void setIndexLibrary(IndexAdjustment adjustment) {
        int showOrder = 1;
        if(adjustment.getCreateDetails() != null && !adjustment.getCreateDetails().isEmpty()) {
            GeneralProject project = this.generalProjectRepository.findByCode(adjustment.getProjectCode());
            for (IndexAdjustmentCreateDetail detail : adjustment.getCreateDetails()) {
                //添加到项目库
                GeneralProjectDetail projectDetail = new GeneralProjectDetail();
                projectDetail.setProject(project);
                projectDetail.setIntroduction(detail.getProjectName());
                projectDetail.setAmount(detail.getIndexAmount());
                projectDetail.setShowOrder(project.getProjectDetails().size() + 1);
                projectDetail.setCode(project.getCode() + "-" + String.format("%03d", project.getProjectDetails().size() + 1));
                projectDetail = this.generalProjectDetailRepository.save(projectDetail);

                IndexLibrary indexLibrary = new IndexLibrary();
                //分配金额/批复金额,大于等于零
                indexLibrary.setAllocationAmount(0.0);
                //可用金额（= 分配批复金额 + 调整金额 - 占用金额 - 在途金额）
                indexLibrary.setAvailableAmount(detail.getIndexAmount());
                //占用金额,大于等于零
                indexLibrary.setOccupationAmount(0.0);
                //在途金额
                indexLibrary.setPassageAmount(0.0);
                //调整金额
                indexLibrary.setAdjustmentAmount(detail.getIndexAmount());
                indexLibrary.setProjectCode(projectDetail.getCode());
                indexLibrary.setShowOrder(showOrder++);
                indexLibrary.setProjectName(detail.getProjectName());
                indexLibrary.setYear(project.getYear().toString());
                indexLibrary.setDepartment(project.getDepartment());
                indexLibrary.setIndexType(IndexType.APPROVAL);
                indexLibrary.setUnit(getUnit(project.getDepartment()));
                indexLibrary.setLargeProjectName(project.getName());
                indexLibrary.setLargeProjectCode(project.getCode());
                this.indexLibraryRepository.save(indexLibrary);
            }
        }
    }

    /**
     *  通过部门查询当前单位
     * @param o 组织对象
     * @return 单位对象
     */
    private Organization getUnit(Organization o){
        if (null == o){
            return null;
        }
        if (o.getOrganizationType() == OrganizationType.UNIT) {
            return o;
        }else {
            return getUnit(o.getParent());
        }

    }

    /**
     * 保存文件
     *
     * @param adjustment 预算调整对象
     * @param fileIds    文件id数组
     */
    private void saveFile(IndexAdjustment adjustment, List<String> fileIds) {
        if (null != fileIds && !fileIds.isEmpty()) {
            List<Long> ids = new ArrayList<>();
            fileIds.forEach(id -> ids.add(Long.parseLong(id)));
            List<SystemFile> files = this.systemFileRepository.findAllById(ids);
            adjustment.setFiles(files);
        }
    }

    /**
     * 删除文件
     *
     * @param adjustment 预算调整对象
     */
    private void deleteFile(IndexAdjustment adjustment) {
        if (null != adjustment.getFiles() && !adjustment.getFiles().isEmpty()) {
            this.systemFileRepository.deleteAll(adjustment.getFiles());
            adjustment.setFiles(Collections.emptyList());
        }
    }

    /**
     * 更新文件
     *
     * @param oldFiles   旧文件对象数组
     * @param newFiles   新文件ID数组
     * @param adjustment 预算调整对象
     */
    private void updateFile(List<SystemFile> oldFiles, List<String> newFiles, IndexAdjustment adjustment) {
        if (null == oldFiles || oldFiles.isEmpty()) {
            saveFile(adjustment, newFiles);
        } else if (null == newFiles || newFiles.isEmpty()) {
            this.systemFileRepository.deleteAll(oldFiles);
        } else {
            List<SystemFile> deleteFiles = new ArrayList<>();
            oldFiles.forEach(file -> {
                //获取需要删除的多余文件
                if (!newFiles.contains(file.getId().toString())) {
                    deleteFiles.add(file);
                }
            });
            if (!deleteFiles.isEmpty()) {
                this.systemFileRepository.deleteAll(deleteFiles);
            }
            if (!newFiles.isEmpty()) {
                saveFile(adjustment, newFiles);
            }
        }
    }


    /**
     * 删除关联数据
     *
     * @param adjustment 预算调整对象
     */
    private void deleteRelation(IndexAdjustment adjustment) {
        recoverAmount(adjustment);
        this.indexAdjustmentDetailRepository.deleteAll(adjustment.getAdjustmentDetails());
        adjustment.setAdjustmentDetails(Collections.emptyList());
        this.indexAdjustmentCreateDetailRepository.deleteAll(adjustment.getCreateDetails());
        adjustment.setCreateDetails(Collections.emptyList());
    }

    /**
     * 恢复调减过的指标金额
     *
     * @param adjustment 预算调整对象
     */
    private void recoverAmount(IndexAdjustment adjustment) {
        if (null != adjustment.getAdjustmentDetails() && !adjustment.getAdjustmentDetails().isEmpty()) {
            adjustment.getAdjustmentDetails().forEach(detail -> {
                if (detail.getDetailType() == AdjustDetailType.REDUCE) {
                    this.indexLibraryRepository.updateAmount(0.0, 0.0, 0.0, 0.0, detail.getAdjustAmount(), detail.getIndexLibrary().getId());
                }
            });
        }
    }


    /**
     * 保存关联数据
     *
     * @param adjustment 预算调整对象
     * @param dto        预算调整DTO
     */
    private void saveRelation(IndexAdjustment adjustment, IndexAdjustmentCreateInfoDTO dto) {
        //指标调增信息
        if (null != dto.getIncreaseDetails() && !dto.getIncreaseDetails().isEmpty()) {
            List<IndexAdjustmentDetail> list = getIndexAdjustmentDetailRelationList(adjustment, dto.getIncreaseDetails(), AdjustDetailType.INCREASE);
            this.indexAdjustmentDetailRepository.saveAll(list);
        }
        //指标调减信息
        if (null != dto.getReduceDetails() && !dto.getReduceDetails().isEmpty()) {
            List<IndexAdjustmentDetail> list = getIndexAdjustmentDetailRelationList(adjustment, dto.getReduceDetails(), AdjustDetailType.REDUCE);
            this.indexAdjustmentDetailRepository.saveAll(list);
        }
        //指标新增信息
        if (null != dto.getCreateDetails() && !dto.getCreateDetails().isEmpty()) {
            List<Long> departmentIds = dto.getCreateDetails().stream().map(IndexAdjustmentCreateDetailEditInfoDTO::getDepartmentId)
                    .map(Long::parseLong).collect(Collectors.toList());
            Map<Long, Organization> organizationMap =this.organizationRepository.findAllById(departmentIds).stream().collect(Collectors.toMap(AbstractBaseEntity::getId,
                    department -> department));
            int showOrder = 1;
            List<IndexAdjustmentCreateDetail> createDetails = new ArrayList<>();
            for (IndexAdjustmentCreateDetailEditInfoDTO createDetailEditInfoDTO : dto.getCreateDetails()) {
                IndexAdjustmentCreateDetail createDetail = this.indexAdjustmentCreateDetailMapper.toEntity(createDetailEditInfoDTO, new CycleAvoidingMappingContext());
                if (StringUtils.isNotBlank(createDetailEditInfoDTO.getDepartmentId())) {
                    createDetail.setDepartment(organizationMap.get(Long.parseLong(createDetailEditInfoDTO.getDepartmentId())));
                }
                createDetail.setAdjustment(adjustment);
                createDetail.setShowOrder(showOrder++);
                createDetail = indexAdjustmentCreateDetailRepository.save(createDetail);
                createDetails.add(createDetail);
            }
            adjustment.setCreateDetails(createDetails);
        }
    }

    /**
     * 设置单位和部门信息
     *
     * @param adjustment 预算调整对象
     * @param createInfo 预算调整DTO
     */
    private void setUnitAndDepartment(IndexAdjustment adjustment, IndexAdjustmentCreateInfoDTO createInfo) {
        Optional<Organization> unitOptional = this.organizationRepository.findById(Long.parseLong(createInfo.getUnitId()));
        adjustment.setUnit(unitOptional.orElseThrow(() -> new NoSuchDataException(createInfo.getUnitId())));
        Optional<Organization> departmentOptional = this.organizationRepository.findById(Long.parseLong(createInfo.getDepartmentId()));
        adjustment.setDepartment(departmentOptional.orElseThrow(() -> new NoSuchDataException(createInfo.getDepartmentId())));
    }

    /**
     * 根据ID获取预算调整对象
     *
     * @param id 预算调整对象id
     * @return 预算调整对象
     */
    private IndexAdjustment getOne(String id) {
        Optional<IndexAdjustment> optional = this.indexAdjustmentRepository.findById(Long.parseLong(id));
        return optional.orElseThrow(() -> new NoSuchDataException(id));
    }

    /**
     * 获取调增或调减的关联关系列表
     * @param adjustment 预算调整对象
     * @param detailEditInfoList 指标（预算）调整明细编辑信息DTO列表
     * @param adjustDetailType 调增类型
     * @return 调增或调减的关联关系列表
     */
    private List<IndexAdjustmentDetail> getIndexAdjustmentDetailRelationList(IndexAdjustment adjustment, List<IndexAdjustmentDetailEditInfoDTO> detailEditInfoList, AdjustDetailType adjustDetailType) {
        List<IndexAdjustmentDetail> list = new ArrayList<>();
        List<Long> indexIds = detailEditInfoList.stream().map(IndexAdjustmentDetailEditInfoDTO::getIndexId).map(Long::parseLong).collect(Collectors.toList());
        List<IndexLibrary> indexLibraries = this.indexLibraryRepository.findAllById(indexIds);
        Map<String, IndexLibrary> indexLibraryMap = indexLibraries.stream().collect(Collectors.toMap(
                index -> index.getId().toString(), index -> index
        ));
        int showOrder = 1;
        for (IndexAdjustmentDetailEditInfoDTO indexAdjustmentDetailEditInfoDTO : detailEditInfoList) {
            IndexAdjustmentDetail indexAdjustmentDetail = this.indexAdjustmentDetailMapper.toEntity(indexAdjustmentDetailEditInfoDTO, new CycleAvoidingMappingContext());
            IndexLibrary indexLibrary = indexLibraryMap.get(indexAdjustmentDetailEditInfoDTO.getIndexId());
            //调减
            if (AdjustDetailType.REDUCE.equals(adjustDetailType)) {
                if (indexLibrary.getAvailableAmount().compareTo(indexAdjustmentDetail.getAdjustAmount()) < 0) {
                    throw new CommonDataException("调减金额不能超过可用余额");
                }
                this.indexLibraryRepository.updateAmount(indexAdjustmentDetail.getAdjustAmount(), 0.0, 0.0, 0.0, 0.0, indexLibrary.getId());
            }
            indexAdjustmentDetail.setIndexLibrary(indexLibrary);
            indexAdjustmentDetail.setAdjustment(adjustment);
            indexAdjustmentDetail.setDetailType(adjustDetailType);
            indexAdjustmentDetail.setShowOrder(showOrder++);
            list.add(indexAdjustmentDetail);
        }
        return list;
    }
}
