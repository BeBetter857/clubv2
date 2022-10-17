package com.hotwave.clubv2.filter;

import com.hotwave.clubv2.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-10-16 18:29:00
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        logger.info("token: {} ",token);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String username;
        try {
            Claims claims = JwtUtil.getTokenBody(token);
            username = claims.getSubject();
            logger.info("username: {}",username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
    }
}
