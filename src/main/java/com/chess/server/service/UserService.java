package com.chess.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chess.server.dto.LoginRequest;
import com.chess.server.dto.LoginResponse;
import com.chess.server.dto.UserDTO;
import com.chess.server.entity.User;
import com.chess.server.mapper.UserMapper;
import com.chess.server.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest req) {
        User user;
        if (req.isMockLogin()) {
            user = userMapper.selectOne(new QueryWrapper<User>().eq("username", "admin"));
            if (user == null) user = userMapper.selectList(null).get(0);
        } else if (req.getUsername() != null) {
            user = userMapper.selectOne(new QueryWrapper<User>().eq("username", req.getUsername()));
            if (user == null) throw new RuntimeException("用户名或密码错误");
            if (req.getPassword() != null && user.getPasswordHash() != null
                    && !passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
                throw new RuntimeException("用户名或密码错误");
            }
        } else {
            throw new RuntimeException("请提供用户名");
        }
        return new LoginResponse(jwtUtil.generateToken(user.getUsername()), toDTO(user));
    }

    public UserDTO register(LoginRequest req) {
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username", req.getUsername())) > 0) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setNickname(req.getUsername());
        user.setRole("USER");
        user.setVip(false);
        if (req.getPassword() != null) user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        if ("phone".equals(req.getVerifyType())) user.setPhone(req.getContact());
        if ("email".equals(req.getVerifyType())) user.setEmail(req.getContact());
        userMapper.insert(user);
        return toDTO(user);
    }

    public void updateVip(Long userId, String plan, LocalDateTime expireDate) {
        User user = new User();
        user.setId(userId);
        user.setVip(true);
        user.setVipPlan(plan);
        user.setVipExpireDate(expireDate);
        userMapper.updateById(user);
    }

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        dto.setNickname(user.getNickname());
        dto.setAvatar(user.getAvatar());
        dto.setVip(user.getVip() != null && user.getVip());
        dto.setVipPlan(user.getVipPlan());
        dto.setVipExpireDate(user.getVipExpireDate());
        dto.setRole(user.getRole());
        return dto;
    }
}
