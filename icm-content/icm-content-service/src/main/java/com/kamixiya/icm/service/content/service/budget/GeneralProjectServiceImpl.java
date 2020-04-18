package com.kamixiya.icm.service.content.service.budget;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectCreateInfoDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectEditInfoDTO;
import com.kamixiya.icm.model.content.budget.general.attach.GeneralProjectAttachEditInfoDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProject;
import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProjectAttach;
import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProjectDetail;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexType;
import com.kamixiya.icm.persistence.content.repository.budget.GeneralProjectAttachRepository;
import com.kamixiya.icm.persistence.content.repository.budget.GeneralProjectDetailRepository;
import com.kamixiya.icm.persistence.content.repository.budget.GeneralProjectRepository;
import com.kamixiya.icm.persistence.content.repository.indexLibrary.IndexLibraryRepository;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.repository.organization.OrganizationRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.content.mapper.budget.GeneralProjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * GeneralProjectServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Service("generalProjectService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class GeneralProjectServiceImpl implements GeneralProjectService {

    private final GeneralProjectRepository generalProjectRepository;
    private final GeneralProjectMapper generalProjectMapper;
    private final GeneralProjectDetailRepository generalProjectDetailRepository;
    private final GeneralProjectAttachRepository generalProjectAttachRepository;
    private final OrganizationRepository organizationRepository;
    private final IndexLibraryRepository indexLibraryRepository;

    public GeneralProjectServiceImpl(GeneralProjectRepository generalProjectRepository, GeneralProjectMapper generalProjectMapper,
                                     GeneralProjectDetailRepository generalProjectDetailRepository,
                                     GeneralProjectAttachRepository generalProjectAttachRepository,
                                     OrganizationRepository organizationRepository, IndexLibraryRepository indexLibraryRepository) {
        this.generalProjectRepository = generalProjectRepository;
        this.generalProjectMapper = generalProjectMapper;
        this.generalProjectDetailRepository = generalProjectDetailRepository;
        this.generalProjectAttachRepository = generalProjectAttachRepository;
        this.organizationRepository = organizationRepository;
        this.indexLibraryRepository = indexLibraryRepository;
    }

    /**
     * 分页查询项目库信息
     *
     * @param page                页号，从0开始
     * @param size                每页纪录条数
     * @param sort                排序字段, 例如：字段1,asc,字段2,desc
     * @param departmentId        申报部门
     * @param name                项目名称
     * @param projectType         项目类别
     * @param detailedProjectType 项目类别明细
     * @param code                项目编号
     * @param year                申报年份
     * @return 分页查询项目库信息
     */
    @Override
    public PageDataDTO<GeneralProjectDTO> findOnePage(int page, int size, String sort, String departmentId, String name, String projectType, String detailedProjectType, String code, String year) {
        PredicateBuilder<GeneralProject> pb = getGeneralProjectBuilder(departmentId, name, projectType, detailedProjectType, code, year, false);
        List<GeneralProject> generalProjects;
        Page<GeneralProject> projectPage = generalProjectRepository.findAll(pb.build(), PageHelper.generatePageRequest(page, size, sort));
        generalProjects = projectPage.getContent();
        List<GeneralProjectDTO> toList = generalProjectMapper.toList(generalProjects, new CycleAvoidingMappingContext());
        return PageDataUtil.toPageData(projectPage, toList);
    }

    /**
     * 新建项目库
     *
     * @param generalProjectCreateInfo 项目库编辑信息
     * @return 新建项目库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralProjectDTO create(GeneralProjectCreateInfoDTO generalProjectCreateInfo) {
        GeneralProject project = generalProjectMapper.toEntity(generalProjectCreateInfo, new CycleAvoidingMappingContext());
        setUploadFile(generalProjectCreateInfo.getProjectAttaches(), project.getProjectAttaches());
        // 设置组织机构数据
        String unitId = generalProjectCreateInfo.getUnitId();
        String departmentId = generalProjectCreateInfo.getDepartmentId();
        String administrativeDepartmentId = generalProjectCreateInfo.getAdministrativeDepartmentId();
        if (!"null".equals(unitId) && !StringUtils.isBlank(unitId)) {
            project.setUnit(organizationRepository.findById(Long.valueOf(unitId)).orElse(new Organization()));
        }
        if (!"null".equals(departmentId) && !StringUtils.isBlank(departmentId)) {
            project.setDepartment(organizationRepository.findById(Long.valueOf(departmentId)).orElse(new Organization()));
        }
        if (!"null".equals(administrativeDepartmentId) && !StringUtils.isBlank(administrativeDepartmentId)) {
            project.setAdministrativeDepartment(organizationRepository.findById(Long.valueOf(administrativeDepartmentId)).orElse(new Organization()));
        }
        project.setProjectProperty(project.getProjectProperty() == null ? "NEW_PROJECT" : project.getProjectProperty());
        project.setStateType(StateType.UNDONE);
        project = generalProjectRepository.save(project);
        // 设置关联表数据
        setProjectProperties(project);
        project = generalProjectRepository.save(project);

        return generalProjectMapper.toDTO(project, new CycleAvoidingMappingContext());
    }
    /**
     * 设置上传文件数据
     *
     * @param attachEditInfoDTOList 上传列表
     * @param attachList            设置上传数据列表
     */
    private void setUploadFile(List<GeneralProjectAttachEditInfoDTO> attachEditInfoDTOList, List<GeneralProjectAttach> attachList) {
        for (int i = 0; i < attachEditInfoDTOList.size(); i++) {
            String fileId = attachEditInfoDTOList.get(i).getFileId();
            if (StringUtils.isNotBlank(fileId) && !"null".equals(fileId)) {
                SystemFile systemFile = new SystemFile();
                systemFile.setId(Long.valueOf(fileId));
                attachList.get(i).setFile(systemFile);
            }
        }
    }

    /**
     * 保存project下子表数据
     *
     * @param project 项目库信息
     */
    private void setProjectProperties(GeneralProject project) {
        List<GeneralProjectDetail> projectDetails = project.getProjectDetails();
        List<GeneralProjectAttach> projectAttaches = project.getProjectAttaches();
        saveProjectAttaches(project, projectAttaches);
        saveProjectDetails(project, projectDetails);
    }

    /**
     * 保存项目明细
     *
     * @param project        项目
     * @param projectDetails 项目明细列表
     */
    private void saveProjectDetails(GeneralProject project, List<GeneralProjectDetail> projectDetails) {
        if (projectDetails != null) {
            for (int i = 0; i < projectDetails.size(); i++) {
                GeneralProjectDetail projectDetail = projectDetails.get(i);
                projectDetail.setProject(project);
                projectDetail.setShowOrder(i);
                projectDetail.setCode(project.getCode() + "-" + String.format("%03d", i + 1));
            }
            generalProjectDetailRepository.saveAll(projectDetails);
        }
    }

    /**
     * 保存附件信息
     *
     * @param project         项目
     * @param projectAttaches 附件列表
     */
    private void saveProjectAttaches(GeneralProject project, List<GeneralProjectAttach> projectAttaches) {
        if (projectAttaches != null && !projectAttaches.isEmpty()) {
            for (int i = 0; i < projectAttaches.size(); i++) {
                GeneralProjectAttach projectAttach = projectAttaches.get(i);
                projectAttach.setProject(project);
                projectAttach.setShowOrder(i);
            }
            generalProjectAttachRepository.saveAll(projectAttaches);
        }
    }

    /**
     * 提交项目库
     *
     * @param id                     项目Id
     * @param generalProjectEditInfo 项目库编辑信息
     * @return 提交项目库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralProjectDTO complete(String id, GeneralProjectEditInfoDTO generalProjectEditInfo){
        Optional<GeneralProject> optional = generalProjectRepository.findById(Long.valueOf(id));
        GeneralProject project = optional.orElseThrow(() -> new NoSuchDataException(id));
        // 先删除子表信息
        deleteChildCollection(project);
        // setNull主表项目详细信息
        setNull(project);
        generalProjectRepository.save(project);

        generalProjectMapper.updateEntity(generalProjectEditInfo, project, new CycleAvoidingMappingContext());
        project.setStateType(StateType.DONE);
        setUploadFile(generalProjectEditInfo.getProjectAttaches(), project.getProjectAttaches());
        setProjectProperties(project);
        setIndexLibrary(project);
        generalProjectRepository.save(project);
        return generalProjectMapper.toDTO(project, new CycleAvoidingMappingContext());
    }

    private void setIndexLibrary(GeneralProject project) {
        int showOrder = 1;
        if(project.getProjectDetails() != null && !project.getProjectDetails().isEmpty()) {
            for (GeneralProjectDetail detail : project.getProjectDetails()) {
                IndexLibrary indexLibrary = new IndexLibrary();
                //分配金额/批复金额,大于等于零
                indexLibrary.setAllocationAmount(detail.getAmount());
                //可用金额（= 分配批复金额 + 调整金额 - 占用金额 - 在途金额）
                indexLibrary.setAvailableAmount(detail.getAmount());
                //占用金额,大于等于零
                indexLibrary.setOccupationAmount(0.0);
                //在途金额
                indexLibrary.setPassageAmount(0.0);
                //调整金额
                indexLibrary.setAdjustmentAmount(0.0);
                indexLibrary.setProjectCode(project.getCode() + "-" + String.format("%03d", showOrder));
                indexLibrary.setShowOrder(showOrder++);
                indexLibrary.setProjectName(detail.getIntroduction());
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
     * 设置空数据
     *
     * @param project 项目库信息
     */
    private void setNull(GeneralProject project) {
        project.setProjectAttaches(null);
        project.setProjectDetails(null);
    }

    /**
     * 删除项目下属的数据
     *
     * @param project 项目信息
     */
    private void deleteChildCollection(GeneralProject project) {
        List<GeneralProjectDetail> projectDetails = project.getProjectDetails();
        List<GeneralProjectAttach> projectAttaches = project.getProjectAttaches();
        if (!projectDetails.isEmpty()) {
            generalProjectDetailRepository.deleteAll(projectDetails);
        }
        if (!projectAttaches.isEmpty()) {
            generalProjectAttachRepository.deleteAll(projectAttaches);
        }
    }

    /**
     * 根据ID查询项目库
     *
     * @param id       项目Id
     * @return 根据ID查询项目库
     */
    @Override
    public GeneralProjectDTO findById(String id){
        Optional<GeneralProject> optional = generalProjectRepository.findById(Long.valueOf(id));
        GeneralProject generalProject = optional.orElseThrow(() -> new NoSuchDataException(id));
        return generalProjectMapper.toDTO(generalProject, new CycleAvoidingMappingContext());
    }

    /**
     * 删除项目库
     *
     * @param id 项目Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Optional<GeneralProject> optional = generalProjectRepository.findById(Long.valueOf(id));
        GeneralProject project = optional.orElseThrow(() -> new NoSuchDataException(id));
        // 先删除项目详细信息
        deleteChildCollection(project);
        generalProjectRepository.delete(project);
    }

    /**
     * 查找所有项目
     *
     * @param unitId              申报单位
     * @param departmentId        申报部门
     * @param name                项目名称
     * @param projectType         项目类别
     * @param detailedProjectType 项目类别明细
     * @return 查找所有项目
     */
    @Override
    public List<GeneralProjectDTO> findAll(String unitId, String departmentId, String name, String projectType, String detailedProjectType) {
        PredicateBuilder<GeneralProject> pb = getGeneralProjectBuilder(departmentId, name, projectType, detailedProjectType, "", "", true);
        List<GeneralProject> generalProjectList = generalProjectRepository.findAll(pb.build());
        return generalProjectMapper.toList(generalProjectList, new CycleAvoidingMappingContext());
    }

    /**
     * 构建基本查找信息
     *
     * @param departmentId        部门id
     * @param name                项目名称
     * @param projectType         项目类型
     * @param detailedProjectType 项目详细类型
     * @param isAll 是否查询全部
     * @return 构建基本查找信息
     */
    private PredicateBuilder<GeneralProject> getGeneralProjectBuilder(String departmentId, String name, String projectType, String detailedProjectType, String code, String year, boolean isAll) {
        return Specifications.<GeneralProject>and()
                .eq(StringUtils.isNotBlank(departmentId) && !"null".equals(departmentId), "department.id", departmentId)
                .eq(StringUtils.isNotBlank(year) && !"null".equals(year), "year", year)
                .like(StringUtils.isNotBlank(name) && !"null".equals(name), "name", "%" + name + "%")
                .like(StringUtils.isNotBlank(projectType) && !"null".equals(projectType), "projectType", "%" + projectType + "%")
                .like(StringUtils.isNotBlank(detailedProjectType) && !"null".equals(detailedProjectType), "projectDetailedType", "%" + detailedProjectType + "%")
                .like(StringUtils.isNotBlank(code) && !"null".equals(code), "code", "%" + code + "%")
                .eq(isAll, "stateType", StateType.DONE);

    }
}

