package com.assignment.dao;

import com.assignment.model.SysUserModel;
import com.assignment.util.DBTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <Description>
 */
public class SysUserDao {

    //新增系统用户
    public Boolean insertUser(SysUserModel sysUserModel) {
        String sql = "INSERT INTO t_sys_user (user_name, user_password, user_privilege,create_time) VALUES (?, ?, ?,?)";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sysUserModel.getUserName());
            statement.setString(2, sysUserModel.getPassword());
            statement.setString(3, sysUserModel.getUserPrivilege());

            Timestamp currentTimestamp = Timestamp.valueOf(java.time.LocalDateTime.now());
            statement.setTimestamp(4, currentTimestamp);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //查询所有的系统用户
    public List<SysUserModel> lisetUser() {
        String sql = "SELECT * FROM t_sys_user";
        List<SysUserModel> sysUserModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");
                String userPrivilege = resultSet.getString("user_privilege");

                sysUserModels.add(new SysUserModel(userId, userName, userPassword, userPrivilege));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sysUserModels;
    }

    //查询所有的系统用户
    public SysUserModel getSysUserById(Integer userId) {
        String sql = "SELECT * FROM t_sys_user where user_id= ?";
        SysUserModel sysUserModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");
                String userPrivilege = resultSet.getString("user_privilege");
                sysUserModel = new SysUserModel(id, userName, userPassword, userPrivilege);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sysUserModel;
    }

    public Boolean updateUser(Integer userId, String password, String userName) {
        String sql = "UPDATE t_sys_user SET user_password = ?,user_name = ? WHERE user_id = ?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, password);
            statement.setString(1, userName);
            statement.setInt(2, userId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //根据ID删除用户
    public Boolean deleteById(Integer userId) {
        String sql = "DELETE FROM t_sys_user WHERE user_id = ?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);


            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //根据用户名密码获取用户信息
    public SysUserModel getSysUserByNameAndPassword(String userName, String userPassword) {
        String sql = "SELECT * FROM t_sys_user where user_name= ? and user_password=?";
        SysUserModel sysUserModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, userName);
            statement.setString(2, userPassword);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String password = resultSet.getString("user_password");
                String userPrivilege = resultSet.getString("user_privilege");
                sysUserModel = new SysUserModel(id, name, password, userPrivilege);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sysUserModel;
    }

    public Boolean login(String userName, String userPassword) {
        SysUserModel sysUserModel = getSysUserByNameAndPassword(userName, userPassword);
        return Objects.nonNull(sysUserModel);
    }

    public static void main(String[] args) {
        SysUserDao sysUserDao = new SysUserDao();
        sysUserDao.insertUser(new SysUserModel(1, "admin", "1", "你好"));

        SysUserModel sysUserModel = sysUserDao.getSysUserById(18);
        System.out.println(sysUserModel);

        List<SysUserModel> sysUserModels = sysUserDao.lisetUser();
        System.out.println(sysUserModels);
    }
}
