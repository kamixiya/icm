package com.kamixiya.icm.service.content.util;

import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.exception.CommonDataException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.repository.base.SystemFileRepository;
import com.kamixiya.icm.service.common.repository.organization.OrganizationRepository;
import com.kamixiya.icm.service.common.repository.security.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Component
public class CommonUtil {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final SystemFileRepository systemFileRepository;

    public CommonUtil(OrganizationRepository organizationRepository, UserRepository userRepository, SystemFileRepository systemFileRepository) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.systemFileRepository = systemFileRepository;
    }

    /**
     * 根据ID获取单位对象
     *
     * @param id 单位对象ID
     * @return 单位对象
     */
    public Organization getOrganization(String id) {
        Optional<Organization> optional = this.organizationRepository.findById(Long.parseLong(id));
        return optional.orElseThrow(() -> new NoSuchDataException(id));
    }

    /**
     * 获取用户对象
     *
     * @param id 用户ID
     * @return 用户对象
     */
    public User getUser(String id) {
        Optional<User> userOptional = this.userRepository.findById(Long.parseLong(id));
        return userOptional.orElseThrow(() -> new NoSuchDataException(id));
    }

    /**
     * 删除文件
     *
     * @param files 文件ID列表
     */
    public void deleteFile(List<SystemFile> files) {
        if (null != files && !files.isEmpty()) {
            this.systemFileRepository.deleteAll(files);
        }
    }

    /**
     * 获取变化的金额
     *
     * @param map              金额变更记录map
     * @param id               采购申请指标ID或指标库ID或合同指标ID
     * @param indexAmount      申请金额
     * @param amount           采购金额/指标总额 (指标总额 = 分配批复金额 + 调整金额)
     * @param passageAmount    在途金额
     * @param occupationAmount 占用金额
     * @return 变化金额
     */
    public Double getChangeAmount(Map<Long, Double> map, Long id, Double indexAmount, Double amount, Double passageAmount, Double occupationAmount) {
        BigDecimal availableAmount = new BigDecimal(amount.toString())
                .subtract(new BigDecimal(passageAmount.toString()))
                .subtract(new BigDecimal(occupationAmount.toString()));
        Double mapAmount = 0.0;
        //检查申请金额可用性
        if (map != null) {
            mapAmount = map.get(id);
            if (mapAmount != null) {
                availableAmount = availableAmount.subtract(new BigDecimal(mapAmount.toString()));
            }
        }
        BigDecimal reportAmount = new BigDecimal(indexAmount.toString());
        if (reportAmount.compareTo(availableAmount) > 0) {
            throw new CommonDataException("申请金额超过余额上限");
        }
        if (mapAmount != null) {
            reportAmount = reportAmount.add(new BigDecimal(mapAmount.toString()));
        }
        return reportAmount.doubleValue();
    }
}
