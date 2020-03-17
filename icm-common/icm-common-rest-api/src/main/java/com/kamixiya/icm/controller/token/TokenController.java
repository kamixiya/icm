package com.kamixiya.icm.controller.token;

import com.kamixiya.icm.core.jwt.JwtTokenUtil;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.model.security.token.JwtTokenDTO;
import com.kamixiya.icm.model.security.token.UserLoginInfoDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import com.kamixiya.icm.security.CurrentUser;
import com.kamixiya.icm.service.common.exception.InvalidTokenException;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.service.organization.OrganizationService;
import com.kamixiya.icm.service.common.service.security.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * TokenController
 *
 * @author Zhu Jie
 * @date 2020/3/9
 */
@Api(tags = {"令牌管理"})
@RestController
@RequestMapping("/api/token")
@Validated
public class TokenController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final OrganizationService organizationService;

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Autowired
    public TokenController(AuthenticationManager authenticationManager, UserService userService, OrganizationService organizationService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @ApiOperation(value = "使用用户账号和密码生成访问令牌")
    @PostMapping("")
    public JwtTokenDTO generateToken(
            @ApiParam(value = "登录信息", required = true) @RequestBody @Validated UserLoginInfoDTO loginInfo, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(
                loginInfo.getAccount(),
                loginInfo.getPassword());
        principal.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        String userId = null;
        try {
            userId = userService.findByAccount(loginInfo.getAccount());
            if (StringUtils.isEmpty(userId)) {
                throw new NoSuchDataException(loginInfo.getAccount());
            }
            userService.updateLoginCountOnAnotherDay(userId);
            Authentication authentication = authenticationManager.authenticate(principal);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CurrentUser currentUser = (CurrentUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            userService.resetLoginCountAfterSuccessfulLogin(userId);
            String token = jwtTokenUtil.generateToken(userId, currentUser.getUsername());
            OrganizationType organizationType = loginInfo.getOrganizationType();
            String workingOrganization = organizationService.getWorkingOrganizationByUserIdAndOrganizationType(userId, organizationType);
            return createJwtTokenDto(userId, currentUser.getUsername(), token, workingOrganization);
        } catch (NoSuchDataException e) {
            // 为安全起见，不直接提示用户名不存在
            throw new BadCredentialsException("no such account");
        } catch (BadCredentialsException e) {
            if (userId != null) {
                userService.loginFailed(userId);
            }
            throw e;
        }
    }

    @ApiOperation(value = "在令牌未失效前，重新生成一个令牌")
    @GetMapping("")
    public JwtTokenDTO refreshToken(@ApiParam(value = "令牌", required = true, example = "Bearer ", defaultValue = "Bearer ") @RequestHeader(value = "Authorization") String authorization,
                                    @ApiParam(value = "组织机构的类型(用来设置后端保存数据表的时候的filterPath过滤路径)", required = true)
                                    @RequestParam(value = "organizationType") OrganizationType organizationType) {
        String tokenOld = authorization.startsWith("Bearer ") ? authorization.substring("Bearer ".length()) : null;
        // 验证token是否有效且未超期
        if (!jwtTokenUtil.validateToken(tokenOld)) {
            throw new InvalidTokenException();
        }

        // 验证当前登录人信息
        CurrentUser currentUser = generateUserDetails(tokenOld, null, null);
        if (currentUser == null) {
            throw new InvalidTokenException();
        }

        if (currentUser.isEnabled() && !currentUser.isPasswordChanged(jwtTokenUtil.getIssueDateFromToken(tokenOld))) {
            String token = jwtTokenUtil.refreshToken(tokenOld);
            String workingOrganization = organizationService.getWorkingOrganizationByUserIdAndOrganizationType(currentUser.getUserId(), organizationType);
            return createJwtTokenDto(currentUser.getUserId(), currentUser.getUsername(), token, workingOrganization);
        } else {
            throw new InvalidTokenException();
        }
    }

    @ApiOperation(value = "退出，对token失效")
    @DeleteMapping("")
    public SimpleDataDTO<Boolean> logout() {
        return new SimpleDataDTO<>(true);
    }

    /**
     * 组装 JwtTokenDTO
     *
     * @param userId   用户id
     * @param userName 用户名称
     * @param token    token
     * @return JwtTokenDTO
     */
    private JwtTokenDTO createJwtTokenDto(String userId, String userName, String token, String workingOrganization) {
        JwtTokenDTO userToken = new JwtTokenDTO();
        userToken.setUserId(userId);
        userToken.setUserName(userName);
        userToken.setIssued(jwtTokenUtil.getIssueDateFromToken(token));
        userToken.setExpires(jwtTokenUtil.getExpirationDateFromToken(token));
        userToken.setWorkingOrganization(workingOrganization);
        userToken.setToken(token);
        return userToken;
    }

    /**
     * 生成用户详细信息
     *
     * @param token               token 信息
     * @param workingOrganization 工作组织
     * @return 系统当前登录用户信息
     */
    private CurrentUser generateUserDetails(String token, String workingOrganization, String dataScopeOrganizationId) {
        String id = jwtTokenUtil.getIdFromToken(token);
        if (id == null) {
            return null;
        }
        UserDTO user = userService.findById(id);
        if (user != null) {
            return new CurrentUser(user);
        } else {
            return null;
        }
    }
}
