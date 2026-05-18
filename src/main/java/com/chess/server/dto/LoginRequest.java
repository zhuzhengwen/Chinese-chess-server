package com.chess.server.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String code;
    private boolean mockLogin;
}
