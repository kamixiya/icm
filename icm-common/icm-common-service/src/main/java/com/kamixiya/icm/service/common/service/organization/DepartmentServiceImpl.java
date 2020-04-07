package com.kamixiya.icm.service.common.service.organization;

import com.kamixiya.icm.model.organization.department.DepartmentDTO;
import com.kamixiya.icm.model.organization.department.DepartmentEditInfoDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.service.common.entity.organization.Department;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.organization.Unit;
import com.kamixiya.icm.service.common.exception.HasRelatedDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.mapper.organization.DepartmentMapper;
import com.kamixiya.icm.service.common.mapper.organization.OrganizationMapper;
import com.kamixiya.icm.service.common.repository.organization.DepartmentRepository;
import com.kamixiya.icm.service.common.repository.organization.UnitRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DepartmentServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Slf4j
@Service("departmentService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final OrganizationService organizationService;
    private final UnitRepository unitRepository;
    private final DepartmentMapper departmentMapper;
    private final OrganizationMapper organizationMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, UnitRepository unitRepository, OrganizationService organizationService, DepartmentMapper departmentMapper, OrganizationMapper organizationMapper) {
        this.departmentRepository = departmentRepository;
        this.unitRepository = unitRepository;
        this.organizationService = organizationService;
        this.departmentMapper = departmentMapper;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public DepartmentDTO findById(String id) {

        Optional<Department> department = departmentRepository.findById(Long.valueOf(id));
        if (!department.isPresent()) {
            throw new NoSuchDataException(id);
        } else {
            return departmentMapper.toDTO(department.get());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrganizationDTO create(DepartmentEditInfoDTO departmentEditInfoDTO) {
        Department saveDepartment = departmentMapper.toEntity(departmentEditInfoDTO);
        final Organization org = organizationService.from(
                departmentEditInfoDTO.getName(),
                departmentEditInfoDTO.getShortName(),
                departmentEditInfoDTO.getAvailable(),
                departmentEditInfoDTO.getInterior(),
                departmentEditInfoDTO.getShowOrder(),
                OrganizationType.DEPARTMENT);

        if (departmentEditInfoDTO.getParentId() == null && departmentEditInfoDTO.getUnitId() == null) {
            throw new IllegalArgumentException("新建部门应在父部门或单位下");
        }

        // 如果父部门不为空，设置父部门
        if (departmentEditInfoDTO.getParentId() != null) {
            Optional<Department> depParent = departmentRepository.findById(Long.valueOf(departmentEditInfoDTO.getParentId()));
            if (depParent.isPresent()) {
                depParent.ifPresent(department -> org.setParent(department.getOrganization()));
            } else {
                throw new NoSuchDataException(departmentEditInfoDTO.getParentId());
            }
        }

        // 如果单位不为空且父部门为空，设置单位
        if (departmentEditInfoDTO.getUnitId() != null && departmentEditInfoDTO.getParentId() == null) {
            Optional<Unit> oldUnit = unitRepository.findById(Long.valueOf(departmentEditInfoDTO.getUnitId()));
            if (!oldUnit.isPresent()) {
                throw new NoSuchDataException(departmentEditInfoDTO.getUnitId());
            }
            Organization unitOrg = oldUnit.get().getOrganization();
            org.setParent(unitOrg);
        }

        saveDepartment.setOrganization(org);
        org.setDepartment(saveDepartment);

        saveDepartment = departmentRepository.save(saveDepartment);
        Organization savedOrg = saveDepartment.getOrganization();

        // 为父组织设置子组织
        if (savedOrg.getParent() != null) {
            savedOrg.getParent().getChildren().add(savedOrg);
        }
        return organizationMapper.toDTO(savedOrg, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DepartmentDTO update(String id, DepartmentEditInfoDTO departmentEditInfoDTO) {
        Optional<Department> dep = departmentRepository.findById(Long.valueOf(id));
        if (!dep.isPresent()) {
            throw new NoSuchDataException(id);
        } else {
            Department oldDepartment = dep.get();
            departmentMapper.updateEntity(departmentEditInfoDTO, oldDepartment);

            // 更新对应组织
            Organization oldOrg = oldDepartment.getOrganization();
            oldOrg.setName(departmentEditInfoDTO.getName());
            oldOrg.setShortName(departmentEditInfoDTO.getShortName());
            oldOrg.setShowOrder(departmentEditInfoDTO.getShowOrder());
            oldOrg.setAvailable(departmentEditInfoDTO.getAvailable());
            oldOrg.setInterior(departmentEditInfoDTO.getInterior());

            oldDepartment = departmentRepository.saveAndFlush(oldDepartment);
            return departmentMapper.toDTO(oldDepartment);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Optional<Department> dep = departmentRepository.findById(Long.valueOf(id));
        if (!dep.isPresent()) {
            throw new NoSuchDataException(id);
        }
        if (!dep.get().getOrganization().getChildren().isEmpty()) {
            throw new HasRelatedDataException(id);
        }
        if (dep.get().getOrganization().getParent() != null) {
            dep.get().getOrganization().getParent().getChildren().remove(dep.get().getOrganization());
        }
        departmentRepository.delete(dep.get());
    }
}