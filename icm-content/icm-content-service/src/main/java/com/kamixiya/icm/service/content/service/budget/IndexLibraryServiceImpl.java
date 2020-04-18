package com.kamixiya.icm.service.content.service.budget;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;
import com.kamixiya.icm.model.content.budget.indexLibrary.UnitAndDepartmentIdDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.persistence.content.repository.indexLibrary.IndexLibraryRepository;
import com.kamixiya.icm.service.common.entity.organization.Department;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.filter.CurrentUserUtil;
import com.kamixiya.icm.service.common.repository.organization.OrganizationRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.content.mapper.budget.IndexLibraryMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 指标库服务 实现
 *
 * @author Zhu Jie
 * @date 2020/4/17
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service("indexLibraryService")
public class IndexLibraryServiceImpl implements IndexLibraryService {

    private final IndexLibraryMapper indexLibraryMapper;
    private final IndexLibraryRepository indexLibraryRepository;
    private final OrganizationRepository organizationRepository;


    @Autowired
    public IndexLibraryServiceImpl(IndexLibraryMapper indexLibraryMapper, IndexLibraryRepository indexLibraryRepository, OrganizationRepository organizationRepository) {
        this.indexLibraryMapper = indexLibraryMapper;
        this.indexLibraryRepository = indexLibraryRepository;
        this.organizationRepository = organizationRepository;
    }

    /**
     * 获取单位id和部门id
     *
     * @param organization
     * @param unitAndDepartmentId
     */
    private void getParentDepartment(Organization organization, UnitAndDepartmentIdDTO unitAndDepartmentId) {
        if (organization == null) {
            return;
        }
        if (OrganizationType.DEPARTMENT.equals(organization.getOrganizationType())) {
            unitAndDepartmentId.setDepartmentId(organization.getId());
        } else if (OrganizationType.UNIT.equals(organization.getOrganizationType())) {
            unitAndDepartmentId.setUnitId(organization.getId());
        }
        if (!unitAndDepartmentId.allHasValue()) {
            getParentDepartment(organization.getParent(), unitAndDepartmentId);
        }
    }

    /**
     * 查询所有指标
     *
     * @param sort           排序字段, 例如：字段1,asc,字段2,desc
     * @param year           项目名称
     * @param projectName    项目名称
     * @param projectCode    项目编号
     * @param departmentName 部门名称
     * @param departmentId   申报部门ID
     * @param unitName       单位名称
     * @return 指标库列表
     */
    @Override
    public List<IndexLibraryDTO> findAll(String sort, String year, String projectName, String projectCode, String departmentName, String departmentId, String unitName) {
        if (StringUtils.isBlank(departmentId)) {
            return new ArrayList<>();
        }
        PredicateBuilder<IndexLibrary> pb = Specifications.<IndexLibrary>and()
                .eq(StringUtils.isNotBlank(year) && !"null".equals(year), "year", year)
                .like(StringUtils.isNotBlank(projectCode) && !"null".equals(projectCode), "projectCode", projectCode + "%")
                .like(StringUtils.isNotBlank(projectName) && !"null".equals(projectName), "projectType", "%" + projectName + "%")
                .like(StringUtils.isNotBlank(departmentName) && !"null".equals(departmentName), "department.name", "%" + departmentName + "%")
                .eq(StringUtils.isNoneBlank(departmentId), "department.id", departmentId)
                .like(StringUtils.isNotBlank(unitName) && !"null".equals(unitName), "unitName.name", "%" + unitName + "%");
        List<IndexLibrary> indexLibraryList = indexLibraryRepository.findAll(pb.build());
        return indexLibraryMapper.toList(indexLibraryList);
    }


    /**
     * 分页查询指标详细信息
     *
     * @param page        页号，从0开始
     * @param size        每页纪录条数
     * @param sort        排序字段, 例如：字段1,asc,字段2,desc
     * @param projectName 项目名称
     * @param unitId      单位id
     * @param departmentId      部门id
     * @param indexProjectName  预算指标名称
     * @return 分页的指标详细信息
     */
    @Override
    public PageDataDTO<IndexLibraryDTO> findOnePage(int page, int size, String sort, String projectName, String unitId, String departmentId, String indexProjectName) {
        Optional<Organization> optional;
        List<Long> departIds = new ArrayList<>();
        Organization unit;
        if (StringUtils.isNotBlank(unitId)) {
            optional = this.organizationRepository.findById(Long.valueOf(unitId));
            if (optional.isPresent()) {
                unit = optional.get();
                unit.getChildren().forEach(depart -> departIds.add(depart.getId()));
            }
        }
        if (StringUtils.isNotBlank(departmentId)) {
            departIds.add(Long.valueOf(departmentId));
        }
        PredicateBuilder<IndexLibrary> pb = Specifications.<IndexLibrary>and()
                .like(StringUtils.isNotBlank(projectName) && !"null".equals(projectName), "projectName", "%" + projectName + "%")
                .in(!departIds.isEmpty(), "department.id", departIds.toArray(new Object[]{}))
                .like(StringUtils.isNotBlank(indexProjectName), "index.projectName", "%" + indexProjectName + "%");
        Page<IndexLibrary> indexLibraryPage = indexLibraryRepository.findAll(pb.build(), PageHelper.generatePageRequest(page, size, sort));
        List<IndexLibrary> indexLibraryList = indexLibraryPage.getContent();
        return PageDataUtil.toPageData(indexLibraryPage, indexLibraryMapper.toList(indexLibraryPage.getContent()));
    }

    /**
     * 根据部门获得上级单位
     *
     * @param department 部门
     * @return 单位
     */
    private Organization getUnitByDepartment(Organization department) {
        if (department == null || department.getOrganizationType() == OrganizationType.UNIT) {
            return department;
        } else {
            return getUnitByDepartment(department.getParent());
        }
    }

    /**
     * 根据id查找指标数据
     *
     * @param id 指标Id
     * @return 根据Id查询指标详细信息
     */
    @Override
    public IndexLibraryDTO findById(String id) {
        Optional<IndexLibrary> optional = indexLibraryRepository.findById(Long.valueOf(id));
        IndexLibrary indexLibrary = optional.orElseThrow(() -> new NoSuchDataException(id));
        return indexLibraryMapper.toDTO(indexLibrary);
    }

    /**
     * 根据指标id获取指标占用金额
     *
     * @param id 指标id
     * @return 根据指标id获取指标占用金额
     */
    @Override
    public Double getOccupationAmountById(String id) {
        Optional<IndexLibrary> optional = indexLibraryRepository.findById(Long.valueOf(id));
        IndexLibrary indexLibrary = optional.orElseThrow(() -> new NoSuchDataException(id));
        return indexLibrary.getOccupationAmount();
    }
}
