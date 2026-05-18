package com.chess.server.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String phone;
    private String nickname;
    private String avatar;
    private boolean vip;
    private String vipPlan;
    private LocalDateTime vipExpireDate;
    private String token;
}
