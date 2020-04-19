package com.kamixiya.icm.service.content.service.revenue;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.revenue.Registration.*;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.revenue.Registration.BankAccountRegistration;
import com.kamixiya.icm.persistence.content.entity.revenue.Registration.BankAccountRegistrationAttach;
import com.kamixiya.icm.persistence.content.repository.revenue.BankAccountRegistrationAttachRepository;
import com.kamixiya.icm.persistence.content.repository.revenue.BankAccountRegistrationRepository;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.filter.CurrentUserUtil;
import com.kamixiya.icm.service.common.repository.base.SystemFileRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.content.mapper.revenue.BankAccountRegistrationAttachMapper;
import com.kamixiya.icm.service.content.mapper.revenue.BankAccountRegistrationMapper;
import com.kamixiya.icm.service.content.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 银行账户登记服务实现
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Service("bankAccountRegistrationService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BankAccountRegistrationServiceImpl implements BankAccountRegistrationService {

    private final BankAccountRegistrationMapper registrationMapper;
    private final BankAccountRegistrationAttachMapper attachMapper;

    private final BankAccountRegistrationRepository registrationRepository;
    private final BankAccountRegistrationAttachRepository attachRepository;
    private final SystemFileRepository systemFileRepository;

    private final CommonUtil commonUtil;
    private final UserRepository userRepository;

    public BankAccountRegistrationServiceImpl(BankAccountRegistrationMapper registrationMapper, BankAccountRegistrationAttachMapper attachMapper,
                                              BankAccountRegistrationRepository registrationRepository, BankAccountRegistrationAttachRepository attachRepository,
                                              SystemFileRepository systemFileRepository, CommonUtil commonUtil, UserRepository userRepository) {
        this.registrationMapper = registrationMapper;
        this.attachMapper = attachMapper;
        this.registrationRepository = registrationRepository;
        this.attachRepository = attachRepository;
        this.commonUtil = commonUtil;
        this.systemFileRepository = systemFileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PageDataDTO<BankAccountRegistrationDTO> findOnePage(BankAccountRegistrationQueryOptionDTO bankAccountRegistrationQueryOptionDTO) {
        PredicateBuilder<BankAccountRegistration> pb = Specifications.<BankAccountRegistration>and()
                .eq(StringUtils.isNotBlank(bankAccountRegistrationQueryOptionDTO.getUnitName()), "unit.name", bankAccountRegistrationQueryOptionDTO.getUnitName())
                .eq(StringUtils.isNotBlank(bankAccountRegistrationQueryOptionDTO.getDepartmentName()), "department.name", bankAccountRegistrationQueryOptionDTO.getDepartmentName())
                .like(StringUtils.isNotBlank(bankAccountRegistrationQueryOptionDTO.getAccountName()), "accountName", "%" + bankAccountRegistrationQueryOptionDTO.getAccountName() + "%")
                .like(StringUtils.isNotBlank(bankAccountRegistrationQueryOptionDTO.getAccount()), "account", "%" + bankAccountRegistrationQueryOptionDTO.getAccount() + "%")
                .like(StringUtils.isNotBlank(bankAccountRegistrationQueryOptionDTO.getRegisterName()), "registrant.name", "%" + bankAccountRegistrationQueryOptionDTO.getRegisterName() + "%")
                .ge(null != bankAccountRegistrationQueryOptionDTO.getBeginTime(), "createdTime", bankAccountRegistrationQueryOptionDTO.getBeginTime())
                .le(null != bankAccountRegistrationQueryOptionDTO.getEndTime(), "createdTime", bankAccountRegistrationQueryOptionDTO.getEndTime());
        Page<BankAccountRegistration> openingPage = this.registrationRepository.findAll(pb.build(), PageHelper.generatePageRequest(
                bankAccountRegistrationQueryOptionDTO.getPageQueryOption().getPage(),
                bankAccountRegistrationQueryOptionDTO.getPageQueryOption().getSize(),
                bankAccountRegistrationQueryOptionDTO.getPageQueryOption().getSort()));
        List<BankAccountRegistrationDTO> content = this.registrationMapper.toList(openingPage.getContent(), new CycleAvoidingMappingContext());
        return PageDataUtil.toPageData(openingPage, content);
    }

    @Override
    public BankAccountRegistrationDTO findById(String id) {
        BankAccountRegistration registration = getOne(id);
        return this.registrationMapper.toDTO(registration, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BankAccountRegistrationDTO create(BankAccountRegistrationCreateInfoDTO createInfo) throws IOException {
        BankAccountRegistration registration = this.registrationMapper.toEntity(createInfo, new CycleAvoidingMappingContext());
        registration.setUnit(this.commonUtil.getOrganization(createInfo.getUnitId()));
        registration.setDepartment(this.commonUtil.getOrganization(createInfo.getDepartmentId()));
        String userId = CurrentUserUtil.getInstance().getUserId();
        User user = this.userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new NoSuchDataException(userId));
        registration.setRegistrant(user);
        registration = this.registrationRepository.save(registration);
        saveAttachRelation(registration, createInfo, null);
        return this.registrationMapper.toDTO(registration, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BankAccountRegistrationDTO complete(String id, BankAccountRegistrationEditInfoDTO editInfo) {
        BankAccountRegistration registration = getOne(id);
        List<String> oldFileIds = new ArrayList<>();
        registration.getBankAccountRegistrationAttaches().forEach(attach -> attach.getFiles().forEach(file -> oldFileIds.add(file.getId().toString())));
        deleteAttachRelation(registration);
        this.registrationMapper.updateEntity(editInfo, registration, new CycleAvoidingMappingContext());
        registration.setState(StateType.DONE);
        saveAttachRelation(registration, editInfo, oldFileIds);
        registration = this.registrationRepository.save(registration);
        return this.registrationMapper.toDTO(registration, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws IOException {
        BankAccountRegistration registration = getOne(id);
        deleteAttachRelation(registration);
        this.registrationRepository.delete(registration);
    }

    /**
     * 根据银行账户登记ID获取银行账户登记信息
     *
     * @param id 银行账户登记ID
     * @return 银行账户登记信息
     */
    private BankAccountRegistration getOne(String id) {
        Optional<BankAccountRegistration> optional = this.registrationRepository.findById(Long.parseLong(id));
        return optional.orElseThrow(() -> new NoSuchDataException(id));
    }

    /**
     * 保存附件的关联关系
     *
     * @param registration 银行账户登记实体
     * @param dto          银行账户登记创建信息
     */
    private void saveAttachRelation(BankAccountRegistration registration, BankAccountRegistrationCreateInfoDTO dto, List<String> fileIds) {
        if (null != dto.getBankAccountRegistrationAttaches() && !dto.getBankAccountRegistrationAttaches().isEmpty()) {
            List<BankAccountRegistrationAttach> list = new ArrayList<>();
            List<BankAccountRegistrationAttachEditInfoDTO> attachEditInfoList = dto.getBankAccountRegistrationAttaches();
            if (null != fileIds && !fileIds.isEmpty()) {
                attachEditInfoList.forEach(attachDTO -> attachDTO.getFileIds().forEach(fileIds::remove));
                List<Long> oldFileIds = fileIds.stream().map(Long::parseLong).collect(Collectors.toList());
                this.commonUtil.deleteFile(this.systemFileRepository.findAllById(oldFileIds));
            }
            int showOrder = 1;
            for (BankAccountRegistrationAttachEditInfoDTO attachDTO : dto.getBankAccountRegistrationAttaches()) {
                BankAccountRegistrationAttach attach = this.attachMapper.toEntity(attachDTO, new CycleAvoidingMappingContext());
                if (null != attachDTO.getFileIds() && !attachDTO.getFileIds().isEmpty()) {
                    List<Long> ids = attachDTO.getFileIds().stream().map(Long::parseLong).collect(Collectors.toList());
                    List<SystemFile> files = this.systemFileRepository.findAllById(ids);
                    attach.setFiles(files);
                }
                attach.setBankAccountRegistration(registration);
                attach.setShowOrder(showOrder++);
                list.add(attach);
            }
            this.attachRepository.saveAll(list);
        }
    }

    /**
     * 删除附件信息关联的数据
     *
     * @param registration 银行账户登记实体
     */
    private void deleteAttachRelation(BankAccountRegistration registration) {
        //删除附件信息
        this.attachRepository.deleteAll(registration.getBankAccountRegistrationAttaches());
        registration.setBankAccountRegistrationAttaches(Collections.emptyList());
    }
}
