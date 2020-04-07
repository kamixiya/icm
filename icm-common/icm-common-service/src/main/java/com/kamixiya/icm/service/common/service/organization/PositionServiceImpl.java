package com.kamixiya.icm.service.common.service.organization;

import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.model.organization.position.PositionDTO;
import com.kamixiya.icm.model.organization.position.PositionEditInfoDTO;
import com.kamixiya.icm.service.common.entity.organization.Department;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.organization.Position;
import com.kamixiya.icm.service.common.entity.organization.Unit;
import com.kamixiya.icm.service.common.exception.HasRelatedDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.mapper.organization.OrganizationMapper;
import com.kamixiya.icm.service.common.mapper.organization.PositionMapper;
import com.kamixiya.icm.service.common.repository.organization.DepartmentRepository;
import com.kamixiya.icm.service.common.repository.organization.PositionRepository;
import com.kamixiya.icm.service.common.repository.organization.UnitRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * PositionServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Slf4j
@Service("positionService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final OrganizationService organizationService;
    private final PositionMapper positionMapper;
    private final DepartmentRepository departmentRepository;
    private final UnitRepository unitRepository;
    private final OrganizationMapper organizationMapper;
    private final UserRepository userRepository;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository, PositionMapper positionMapper, OrganizationService organizationService, DepartmentRepository departmentRepository, UnitRepository unitRepository,
                               OrganizationMapper organizationMapper,
                               UserRepository userRepository) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
        this.organizationService = organizationService;
        this.departmentRepository = departmentRepository;
        this.unitRepository = unitRepository;
        this.organizationMapper = organizationMapper;
        this.userRepository = userRepository;
    }

    @Override
    public PositionDTO findById(String id) {
        Optional<Position> o = positionRepository.findById(Long.valueOf(id));
        if (!o.isPresent()) {
            throw new NoSuchDataException(id);
        } else {
            Position position = o.get();
            PositionDTO positionDTO = positionMapper.toDTO(position);
            return positionDTO;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrganizationDTO create(PositionEditInfoDTO positionEditInfoDTO) {

        if (log.isDebugEnabled()) {
            log.info("创建岗位");
        }

        Position position = positionMapper.toEntity(positionEditInfoDTO);
        final Organization org = organizationService.from(positionEditInfoDTO.getName(), positionEditInfoDTO.getShortName(), positionEditInfoDTO.getAvailable(), positionEditInfoDTO.getInterior(), positionEditInfoDTO.getShowOrder(), OrganizationType.POSITION);

        if (positionEditInfoDTO.getParentId() == null && positionEditInfoDTO.getDepartmentId() == null && positionEditInfoDTO.getUnitId() == null) {
            throw new IllegalArgumentException("新建岗位应在父岗位、父部门、或单位下");
        }

        //如果父岗位不为空
        if (positionEditInfoDTO.getParentId() != null) {
            Optional<Position> o = positionRepository.findById(Long.valueOf(positionEditInfoDTO.getParentId()));
            if (!o.isPresent()) {
                throw new NoSuchDataException(positionEditInfoDTO.getParentId());
            }
            o.ifPresent(position1 -> org.setParent(position1.getOrganization()));
        }

        //如果部门不为空
        if (positionEditInfoDTO.getDepartmentId() != null && positionEditInfoDTO.getParentId() == null) {
            Optional<Department> o = departmentRepository.findById(Long.valueOf(positionEditInfoDTO.getDepartmentId()));
            if (!o.isPresent()) {
                throw new NoSuchDataException(positionEditInfoDTO.getDepartmentId());
            }
            Organization organization = o.get().getOrganization();
            org.setParent(organization);
        }

        //如果单位不为空
        if (positionEditInfoDTO.getUnitId() != null && positionEditInfoDTO.getParentId() == null && positionEditInfoDTO.getDepartmentId() == null) {
            Optional<Unit> o = unitRepository.findById(Long.valueOf(positionEditInfoDTO.getUnitId()));
            if (!o.isPresent()) {
                throw new NoSuchDataException(positionEditInfoDTO.getUnitId());
            }
            Organization organization = o.get().getOrganization();
            org.setParent(organization);
        }

        position.setOrganization(org);
        org.setPosition(position);
        position = positionRepository.save(position);

        if (log.isDebugEnabled()) {
            log.info("保存岗位");
        }

        Organization savedOrg = position.getOrganization();

        // 为父组织设置子组织
        if (savedOrg.getParent() != null) {
            savedOrg.getParent().getChildren().add(savedOrg);
        }

        if (log.isDebugEnabled()) {
            log.info("岗位执行完毕");
        }
        return this.organizationMapper.toDTO(savedOrg, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"DataScope", "Positions"}, allEntries = true)
    public PositionDTO update(String id, PositionEditInfoDTO positionEditInfoDTO) {
        Optional<Position> o = positionRepository.findById(Long.valueOf(id));
        if (!o.isPresent()) {
            throw new NoSuchDataException(id);
        } else {
            Position position = o.get();
            positionMapper.updateEntity(positionEditInfoDTO, position);

            Organization oldOrg = position.getOrganization();
            oldOrg.setName(positionEditInfoDTO.getName());
            oldOrg.setShortName(positionEditInfoDTO.getShortName());
            oldOrg.setShowOrder(positionEditInfoDTO.getShowOrder());
            oldOrg.setAvailable(positionEditInfoDTO.getAvailable());
            oldOrg.setInterior(positionEditInfoDTO.getInterior());
            Position p = positionRepository.saveAndFlush(position);

            PositionDTO positionDTO = positionMapper.toDTO(p);
            return positionDTO;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"DataScope"}, allEntries = true)
    public void delete(String id) {
        Optional<Position> o = positionRepository.findById(Long.valueOf(id));
        if (!o.isPresent()) {
            throw new NoSuchDataException(id);
        }
        o.ifPresent(position -> {
            if (!position.getOrganization().getChildren().isEmpty() || !position.getOrganization().getEmployees().isEmpty()) {
                throw new HasRelatedDataException(id);
            } else {
                if (position.getOrganization().getParent() != null) {
                    position.getOrganization().getParent().getChildren().remove(position.getOrganization());
                }
                positionRepository.delete(position);
            }
        });
    }

}
