package com.chess.server.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.entity.User;
import com.chess.server.mapper.UserMapper;
import com.chess.server.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    public JwtInterceptor(JwtUtil jwtUtil, UserMapper userMapper) {
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
                if (user != null) {
                    req.setAttribute("currentUser", user);
                }
            }
        }
        return true;
    }
}
