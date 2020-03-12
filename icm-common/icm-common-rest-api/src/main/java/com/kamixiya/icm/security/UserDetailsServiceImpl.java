package com.kamixiya.icm.security;

import com.kamixiya.icm.model.user.UserDTO;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 本地数据库方式的用户服务
 *
 * @author Zhu Jie
 * @date 2020/3/12
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String userId = userService.findByAccount(username);
            UserDTO userDTO = userService.findById(userId);

            return new CurrentUser(userDTO);
        } catch (NoSuchDataException e) {
            throw new UsernameNotFoundException(String.format("找不到用户：%s", username));
        }
    }
}
