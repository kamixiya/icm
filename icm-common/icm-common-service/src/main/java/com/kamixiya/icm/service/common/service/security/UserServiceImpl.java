package com.kamixiya.icm.service.common.service.security;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.role.RoleDTO;
import com.kamixiya.icm.model.user.PasswordInfoDTO;
import com.kamixiya.icm.model.user.UserDTO;
import com.kamixiya.icm.model.user.UserEditInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * UserServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/3/13
 */
@Service("userService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl implements UserService{

    @Override
    public UserDTO findById(String id) {
        return null;
    }

    @Override
    public PageDataDTO<UserDTO> findOnePage(int page, int size, String sort, String name) {
        return null;
    }

    @Override
    public List<UserDTO> findAll(String name) {
        return null;
    }

    @Override
    public SimpleDataDTO<Boolean> checkAccountAvailability(String userId, String account) {
        return null;
    }

    @Override
    public void changePassword(String userId, PasswordInfoDTO passwordInfoDTO) {

    }

    @Override
    public UserDTO create(UserEditInfoDTO userEditInfoDTO) {
        return null;
    }

    @Override
    public UserDTO update(String id, UserEditInfoDTO userEditInfoDTO) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<RoleDTO> getRoles(String id) {
        return null;
    }

    @Override
    public void grantRoles(String id, String[] data) {

    }

    @Override
    public Collection<String> setWorkingOrganization(String organizationId) {
        return null;
    }

    @Override
    public String findByAccount(String account) {
        return null;
    }

    @Override
    public void updateLoginCountOnAnotherDay(String id) {

    }

    @Override
    public void resetLoginCountAfterSuccessfulLogin(String id) {

    }

    @Override
    public void loginFailed(String userId) {

    }
}
