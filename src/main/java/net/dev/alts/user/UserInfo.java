package net.dev.alts.user;

import com.alibaba.fastjson.annotation.*;

public class UserInfo {
    @JSONField(name="username", ordinal = 1)
    private String username;
    @JSONField(name="password", ordinal = 2)
    private String password;
    @JSONField(name="email", ordinal = 3)
    private String email;
    @JSONField(name="hwid", ordinal = 4)
    private String hwid;
    public UserInfo(String username, String password, String email, String hwid){
        this.username = username;
        this.password = password;
        this.email = email;
        this.hwid = hwid;
    }
    public UserInfo(){}
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getHwid() {
        return hwid;
    }
    public void setHwid(String hwid) {
        this.hwid = hwid;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
