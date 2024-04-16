package com.assignment.model;

import java.io.Serializable;
import java.util.Date;

public class SysUserModel implements Serializable {

    private Integer userId;
    private String userName;
    private String password;

    private String userPrivilege;

    private Date createTime;

    public SysUserModel(Integer userId, String userName, String password, String userPrivilege) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userPrivilege = userPrivilege;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserPrivilege() {
        return userPrivilege;
    }

    public void setUserPrivilege(String userPrivilege) {
        this.userPrivilege = userPrivilege;
    }
}
