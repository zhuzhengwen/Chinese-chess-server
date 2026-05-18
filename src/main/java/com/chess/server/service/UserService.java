package com.chess.server.service;

import com.chess.server.dto.LoginRequest;
import com.chess.server.dto.UserDTO;
import com.chess.server.entity.User;
import com.chess.server.repository.UserRepository;
import com.chess.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserDTO login(LoginRequest req) {
        User user = userRepository.findByPhone(req.getPhone()).orElseGet(() -> {
            User newUser = new User();
            newUser.setPhone(req.getPhone());
            newUser.setNickname("棋友" + req.getPhone().substring(7));
            newUser.setRole("USER");
            return userRepository.save(newUser);
        });
        String token = jwtUtil.generateToken(user.getPhone());
        return toDTO(user, token);
    }

    public UserDTO getUserByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .map(u -> toDTO(u, null))
                .orElse(null);
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(u -> toDTO(u, null))
                .orElse(null);
    }

    public void updateVipStatus(Long userId, String plan, LocalDateTime expireDate) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setVip(true);
            user.setVipPlan(plan);
            user.setVipExpireDate(expireDate);
            userRepository.save(user);
        });
    }

    private UserDTO toDTO(User user, String token) {
        return UserDTO.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .vip(user.isVip())
                .vipPlan(user.getVipPlan())
                .vipExpireDate(user.getVipExpireDate())
                .token(token)
                .build();
    }
}
