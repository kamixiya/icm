package com.kamixiya.icm.service.common.service.organization;

import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.model.organization.unit.UnitDTO;
import com.kamixiya.icm.model.organization.unit.UnitEditInfoDTO;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.organization.Unit;
import com.kamixiya.icm.service.common.exception.HasRelatedDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.mapper.organization.OrganizationMapper;
import com.kamixiya.icm.service.common.mapper.organization.UnitMapper;
import com.kamixiya.icm.service.common.repository.organization.UnitRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UnitServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Slf4j
@Service("unitService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;

    @Autowired
    public UnitServiceImpl(OrganizationService organizationService, UnitRepository unitRepository, UnitMapper unitMapper, OrganizationMapper organizationMapper) {
        this.organizationService = organizationService;
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
        this.organizationMapper = organizationMapper;
    }


    @Override
    public UnitDTO findById(String id) {
        Optional<Unit> u = unitRepository.findById(Long.valueOf(id));
        if (u.isPresent()) {
            return unitMapper.toDTO(u.get());
        } else {
            throw new NoSuchDataException(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrganizationDTO create(UnitEditInfoDTO unitEditInfoDTO) {
        Unit savedUnit = unitMapper.toEntity(unitEditInfoDTO);

        // 创建组织
        final Organization org = organizationService.from(
                savedUnit.getName(),
                savedUnit.getShortName(),
                savedUnit.getAvailable(),
                unitEditInfoDTO.getInterior(),
                savedUnit.getShowOrder(),
                OrganizationType.UNIT);
        if (unitEditInfoDTO.getParentId() != null) {
            Optional<Unit> parent = unitRepository.findById(Long.valueOf(unitEditInfoDTO.getParentId()));
            if (!parent.isPresent()) {
                throw new NoSuchDataException(unitEditInfoDTO.getParentId());
            }
            parent.ifPresent(u -> org.setParent(u.getOrganization()));
        }
        savedUnit.setOrganization(org);
        org.setUnit(savedUnit);
        savedUnit = unitRepository.saveAndFlush(savedUnit);

        Organization savedOrg = savedUnit.getOrganization();

        // 为父组织设置子组织
        if (savedOrg.getParent() != null) {
            savedOrg.getParent().getChildren().add(savedOrg);
        }
        return organizationMapper.toDTO(savedOrg, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UnitDTO update(String id, UnitEditInfoDTO unitEditInfoDTO) {
        Optional<Unit> u = unitRepository.findById(Long.valueOf(id));
        if (!u.isPresent()) {
            throw new NoSuchDataException(id);
        } else {
            Unit oldUnit = u.get();
            unitMapper.updateEntity(unitEditInfoDTO, oldUnit);
            // 更新对应组织
            Organization oldOrg = oldUnit.getOrganization();
            oldOrg.setName(unitEditInfoDTO.getName());
            oldOrg.setShortName(unitEditInfoDTO.getShortName());
            oldOrg.setShowOrder(unitEditInfoDTO.getShowOrder());
            oldOrg.setAvailable(unitEditInfoDTO.getAvailable());
            oldOrg.setInterior(unitEditInfoDTO.getInterior());

            oldUnit = unitRepository.saveAndFlush(oldUnit);
            return unitMapper.toDTO(oldUnit);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Optional<Unit> unit = unitRepository.findById(Long.valueOf(id));
        if (!unit.isPresent()) {
            throw new NoSuchDataException(id);
        }
        if (!unit.get().getOrganization().getChildren().isEmpty()) {
            throw new HasRelatedDataException(id);
        }
        if (unit.get().getOrganization().getParent() != null) {
            unit.get().getOrganization().getParent().getChildren().remove(unit.get().getOrganization());
        }
        unitRepository.delete(unit.get());
    }

    @Override
    public SimpleDataDTO<Boolean> checkUnitNumber(String id, String unitNumber) {
        Long unitId = unitRepository.findUnitByUnitNumber(unitNumber);
        if (unitId == null) {
            return new SimpleDataDTO<>(true);
        } else {
            boolean rtn = id != null && id.equalsIgnoreCase(String.valueOf(unitId));
            return new SimpleDataDTO<>(rtn);
        }

    }
}
