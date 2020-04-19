package com.kamixiya.icm.service.content.service.revenue;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.revenue.opening.*;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.revenue.opening.BankAccountOpening;
import com.kamixiya.icm.persistence.content.entity.revenue.opening.BankAccountOpeningAttach;
import com.kamixiya.icm.persistence.content.repository.revenue.BankAccountOpeningAttachRepository;
import com.kamixiya.icm.persistence.content.repository.revenue.BankAccountOpeningRepository;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.repository.base.SystemFileRepository;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import com.kamixiya.icm.service.content.mapper.revenue.BankAccountOpeningAttachMapper;
import com.kamixiya.icm.service.content.mapper.revenue.BankAccountOpeningMapper;
import com.kamixiya.icm.service.content.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 银行账户开户申请实现
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Service("bankAccountOpeningService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BankAccountOpeningServiceImpl implements BankAccountOpeningService {

    private final BankAccountOpeningMapper openingMapper;
    private final BankAccountOpeningAttachMapper attachMapper;

    private final BankAccountOpeningRepository openingRepository;
    private final BankAccountOpeningAttachRepository attachRepository;

    private final CommonUtil commonUtil;
    private final SystemFileRepository systemFileRepository;

    public BankAccountOpeningServiceImpl(BankAccountOpeningMapper openingMapper, BankAccountOpeningAttachMapper attachMapper,
                                         BankAccountOpeningRepository openingRepository, BankAccountOpeningAttachRepository attachRepository,
                                         SystemFileRepository systemFileRepository, CommonUtil commonUtil) {
        this.openingMapper = openingMapper;
        this.attachMapper = attachMapper;
        this.openingRepository = openingRepository;
        this.attachRepository = attachRepository;
        this.commonUtil = commonUtil;
        this.systemFileRepository = systemFileRepository;
    }

    @Override
    public PageDataDTO<BankAccountOpeningDTO> findOnePage(BankAccountOpeningQueryOptionDTO bankAccountOpeningQueryOptionDTO) {
        PredicateBuilder<BankAccountOpening> pb = Specifications.<BankAccountOpening>and()
                .eq(StringUtils.isNotBlank(bankAccountOpeningQueryOptionDTO.getAccountProperty()), "accountProperty", bankAccountOpeningQueryOptionDTO.getAccountProperty())
                .eq(StringUtils.isNotBlank(bankAccountOpeningQueryOptionDTO.getUnitName()), "unit.name", bankAccountOpeningQueryOptionDTO.getUnitName())
                .eq(StringUtils.isNotBlank(bankAccountOpeningQueryOptionDTO.getDepartmentName()), "department.name", bankAccountOpeningQueryOptionDTO.getDepartmentName())
                .like(StringUtils.isNotBlank(bankAccountOpeningQueryOptionDTO.getNameOfBank()), "nameOfBank", "%" + bankAccountOpeningQueryOptionDTO.getNameOfBank() + "%")
                .like(StringUtils.isNotBlank(bankAccountOpeningQueryOptionDTO.getFullNameOfBank()), "fullNameOfBank", "%" + bankAccountOpeningQueryOptionDTO.getFullNameOfBank() + "%")
                .like(StringUtils.isNotBlank(bankAccountOpeningQueryOptionDTO.getDeclarerName()), "declarer.name", "%" + bankAccountOpeningQueryOptionDTO.getDeclarerName() + "%");
        Page<BankAccountOpening> openingPage = this.openingRepository.findAll(pb.build(), PageHelper.generatePageRequest(
                bankAccountOpeningQueryOptionDTO.getPageQueryOption().getPage(),
                bankAccountOpeningQueryOptionDTO.getPageQueryOption().getSize(),
                bankAccountOpeningQueryOptionDTO.getPageQueryOption().getSort()));
        List<BankAccountOpeningDTO> content = this.openingMapper.toList(openingPage.getContent(), new CycleAvoidingMappingContext());
        return PageDataUtil.toPageData(openingPage, content);
    }

    @Override
    public BankAccountOpeningDTO findById(String id) {
        BankAccountOpening opening = getOne(id);
        return this.openingMapper.toDTO(opening, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BankAccountOpeningDTO create(BankAccountOpeningCreateInfoDTO createInfo) throws IOException {
        BankAccountOpening opening = this.openingMapper.toEntity(createInfo, new CycleAvoidingMappingContext());
        opening.setUnit(this.commonUtil.getOrganization(createInfo.getUnitId()));
        opening.setDepartment(this.commonUtil.getOrganization(createInfo.getDepartmentId()));
        opening.setDeclarer(this.commonUtil.getUser(createInfo.getDeclarerId()));
        opening = this.openingRepository.save(opening);
        saveAttachRelation(opening, createInfo, null);
        return this.openingMapper.toDTO(opening, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BankAccountOpeningDTO complete(String id, BankAccountOpeningEditInfoDTO editInfo) throws IOException {
        BankAccountOpening opening = getOne(id);
        //保存旧的文件id列表，用于更新时删除脏数据
        List<String> oldFileIds = new ArrayList<>();
        opening.getBankAccountOpeningAttaches().forEach(attach -> attach.getFiles().forEach(file -> oldFileIds.add(file.getId().toString())));
        deleteAttachRelation(opening);
        this.openingMapper.updateEntity(editInfo, opening, new CycleAvoidingMappingContext());
        opening.setState(StateType.DONE);
        saveAttachRelation(opening, editInfo, oldFileIds);
        opening = this.openingRepository.save(opening);
        return this.openingMapper.toDTO(opening, new CycleAvoidingMappingContext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws IOException {
        BankAccountOpening opening = getOne(id);
        deleteAttachRelation(opening);
        this.openingRepository.delete(opening);
    }

    /**
     * 根据银行账户申请ID获取银行账户申请信息
     *
     * @param id 银行账户申请ID
     * @return 银行账户申请信息
     */
    private BankAccountOpening getOne(String id) {
        Optional<BankAccountOpening> optional = this.openingRepository.findById(Long.parseLong(id));
        return optional.orElseThrow(() -> new NoSuchDataException(id));
    }

    /**
     * 保存附件的关联关系
     *
     * @param opening 银行账户开户实体
     * @param dto     银行账户开户创建信息
     */
    private void saveAttachRelation(BankAccountOpening opening, BankAccountOpeningCreateInfoDTO dto, List<String> fileIds) {
        if (null != dto.getBankAccountOpeningAttaches() && !dto.getBankAccountOpeningAttaches().isEmpty()) {
            List<BankAccountOpeningAttach> list = new ArrayList<>();
            List<BankAccountOpeningAttachEditInfoDTO> attachEditInfoList = dto.getBankAccountOpeningAttaches();
            if (null != fileIds && !fileIds.isEmpty()) {
                attachEditInfoList.forEach(attachDTO -> attachDTO.getFileIds().forEach(fileIds::remove));
                List<Long> oldFileIds = fileIds.stream().map(Long::parseLong).collect(Collectors.toList());
                this.commonUtil.deleteFile(this.systemFileRepository.findAllById(oldFileIds));
            }
            int showOrder = 1;
            for (BankAccountOpeningAttachEditInfoDTO attachDTO : dto.getBankAccountOpeningAttaches()) {
                BankAccountOpeningAttach attach = this.attachMapper.toEntity(attachDTO, new CycleAvoidingMappingContext());
                if (null != attachDTO.getFileIds() && !attachDTO.getFileIds().isEmpty()) {
                    List<Long> ids = attachDTO.getFileIds().stream().map(Long::parseLong).collect(Collectors.toList());
                    List<SystemFile> files = this.systemFileRepository.findAllById(ids);
                    attach.setFiles(files);
                }
                attach.setBankAccountOpening(opening);
                attach.setShowOrder(showOrder++);
                list.add(attach);
            }
            this.attachRepository.saveAll(list);
        }
    }

    /**
     * 删除附件信息关联的数据
     *
     * @param opening 银行账户开户实体
     */
    private void deleteAttachRelation(BankAccountOpening opening) {
        //删除附件信息
        this.attachRepository.deleteAll(opening.getBankAccountOpeningAttaches());
        opening.setBankAccountOpeningAttaches(Collections.emptyList());
    }
}
