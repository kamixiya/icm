package com.kamixiya.icm.security;

import com.kamixiya.icm.model.user.UserDTO;
import com.kamixiya.icm.persistence.common.entity.user.CurrentUser;
import com.kamixiya.icm.service.common.security.UserService;
import com.kamixiya.icm.service.common.util.CurrentUserUtil;
import com.kamixiya.icm.service.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查HTTP请求的HEADER是否包含有效的token
 *
 * @author Zhu Jie
 * @date 2020/3/10
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PAYLOAD = "Bearer ";
    private final UserService userService;
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Autowired
    public JwtTokenFilter(UserService userService) {
        this.userService = userService;
    }

    private CurrentUser generateUserDetails(String token) {
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

    protected void customizeHeader(HttpServletRequest request) {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        Long start;
        Long end;
        start = System.currentTimeMillis();

        String authHeader = request.getHeader(TOKEN_HEADER);
        CurrentUser userDetails = null;

        if (authHeader != null) {
            String authToken = authHeader.startsWith(TOKEN_PAYLOAD) ? authHeader.substring(TOKEN_PAYLOAD.length()) : null;
            if (authToken != null && jwtTokenUtil.validateToken(authToken)) {
                customizeHeader(request);

                userDetails = generateUserDetails(authToken);

            }

            if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                CurrentUserUtil.getInstance().setUserId(userDetails.getUserId());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        end = System.currentTimeMillis();
        chain.doFilter(request, response);
    }
}
