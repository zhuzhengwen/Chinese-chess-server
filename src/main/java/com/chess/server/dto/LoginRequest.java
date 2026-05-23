package com.chess.server.dto;

public class LoginRequest {
    private String type;
    private String username;
    private String password;
    private String phone;
    private String code;
    private boolean mockLogin;
    private String verifyType;
    private String contact;
    private String newPassword;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public boolean isMockLogin() { return mockLogin; }
    public void setMockLogin(boolean mockLogin) { this.mockLogin = mockLogin; }
    public String getVerifyType() { return verifyType; }
    public void setVerifyType(String verifyType) { this.verifyType = verifyType; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
