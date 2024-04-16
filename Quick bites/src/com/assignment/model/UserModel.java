package com.assignment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description>
 */
public class UserModel implements Serializable {

    //ID
    private Integer id;

    //账号
    private String userAccount;

    //用户名
    private String userName;

    //昵称
    private String nickName;

    //用户密码
    private String userPassword;

    //性别 1男 2女
    private Integer sex;

    //age
    private Integer age;

    private String address;

    //创建时间
    private Date createTime;

    public UserModel(Integer id, String userAccount, String userName, String nickName, String userPassword, Integer sex, Integer age, String address, Date createTime) {
        this.id = id;
        this.userAccount = userAccount;
        this.userName = userName;
        this.nickName = nickName;
        this.userPassword = userPassword;
        this.sex = sex;
        this.age = age;
        this.createTime = createTime;
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
