package com.assignment.frame;

import com.assignment.model.MerchantModel;
import com.assignment.model.SysUserModel;
import com.assignment.model.TakeOutUserModel;
import com.assignment.model.UserModel;

/**
 * <Description>
 * 
 */
public class UserInfo {

    private static String roleType;

    private static SysUserModel sysUserModel;

    private static MerchantModel merchantModel;

    private static TakeOutUserModel takeOutUserModel;

    private static UserModel userModel;

    public static SysUserModel getSysUserModel() {
        return sysUserModel;
    }

    public static void setSysUserModel(SysUserModel sysUserModel) {
        UserInfo.sysUserModel = sysUserModel;
    }

    public static MerchantModel getMerchantModel() {
        return merchantModel;
    }

    public static void setMerchantModel(MerchantModel merchantModel) {
        UserInfo.merchantModel = merchantModel;
    }

    public static UserModel getUserModel() {
        return userModel;
    }

    public static void setUserModel(UserModel userModel) {
        UserInfo.userModel = userModel;
    }

    public static String getRoleType() {
        return roleType;
    }

    public static void setRoleType(String roleType) {
        UserInfo.roleType = roleType;
    }

    public static TakeOutUserModel getTakeOutUserModel() {
        return takeOutUserModel;
    }

    public static void setTakeOutUserModel(TakeOutUserModel takeOutUserModel) {
        UserInfo.takeOutUserModel = takeOutUserModel;
    }
}
