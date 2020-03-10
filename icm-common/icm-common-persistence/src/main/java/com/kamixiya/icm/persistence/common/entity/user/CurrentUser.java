package com.kamixiya.icm.persistence.common.entity.user;

import com.kamixiya.icm.model.user.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 系统当前登录用户
 *
 * @author Zhu Jie
 * @date 2020/3/11
 */
public class CurrentUser implements UserDetails {

    /**
     * 当前用户id
     */
    private String userId;

    /**
     * 关联的用户数据
     */
    private UserDTO user;

    /**
     * 当前选中的工作组织权限
     */
    private Collection<String> selectedOrganizationAuthorities;

    public CurrentUser(UserDTO user) {
        this.user = user;
        this.userId = user.getId();
    }

    /**
     * 获取当前用户权限集合
     *
     * @return 权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = this.user.getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        if (this.selectedOrganizationAuthorities != null) {
            authorities.addAll(
                    this.selectedOrganizationAuthorities
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet()));
        }
        return authorities;
    }

    /**
     * 获取当前登录用户password
     *
     * @return 用户password
     */
    @Override
    public String getPassword() {
        if (user.getLocalAccount()) {
            return user.getPassword();
        } else {
            return "";
        }
    }

    /**
     * 获取登录用户账号
     *
     * @return 用户账号
     */
    @Override
    public String getUsername() {
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (user.getLocalAccount()) {
            return (user.getAccountExpireDate() == null || user.getAccountExpireDate().after(new Date()));
        } else {
            return true;
        }
    }

    @Override
    public boolean isAccountNonLocked() {
        if (user.getLocalAccount()) {
            return !user.getAccountLocked();
        } else {
            return true;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (user.getLocalAccount()) {
            return user.getPasswordExpireDate() == null || user.getPasswordExpireDate().after(new Date());
        } else {
            return true;
        }
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    /**
     * 取得当前登录用户
     *
     * @return 当前登录用户，没有为null
     */
    public static CurrentUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof CurrentUser) {
            return (CurrentUser) (authentication.getPrincipal());
        }
        return null;
    }

    /**
     * 获取用户
     *
     * @return 用户dto
     */
    public UserDTO getUser() {
        return this.user;
    }

    /**
     * 获取当前登录用户id
     *
     * @return 用户id
     */
    public String getUserId() {
        return userId;
    }

}
