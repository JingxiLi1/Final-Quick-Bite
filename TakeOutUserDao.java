package com.assignment.dao;

import com.assignment.model.TakeOutUserModel;
import com.assignment.util.DBTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <Description>
 */
public class TakeOutUserDao {

    //delivery person information
    public TakeOutUserModel getUserByNameAndPassword(String userName, String userPassword) {
        String sql = "SELECT * FROM t_take_out_user where user_account= ? and user_password=?";
        TakeOutUserModel userModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, userName);
            statement.setString(2, userPassword);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userAccount = resultSet.getString("user_account");
                String nickName = resultSet.getString("nick_name");

                Integer sex = resultSet.getInt("sex");
                Integer age = resultSet.getInt("age");
                String address = resultSet.getString("address");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                userModel = new TakeOutUserModel(id, userAccount, userName, nickName, userPassword, sex, age, address, createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userModel;
    }


    //getdelivery person info based on Id
    public TakeOutUserModel getUserById(Integer userId) {
        TakeOutUserModel userModel = null;
        if (Objects.isNull(userId)) {
            return userModel;
        }
        String sql = "SELECT * FROM t_take_out_user where id= ?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userAccount = resultSet.getString("user_account");
                String nickName = resultSet.getString("nick_name");

                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");

                Integer sex = resultSet.getInt("sex");
                Integer age = resultSet.getInt("age");
                String address = resultSet.getString("address");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                userModel = new TakeOutUserModel(id, userAccount, userName, nickName, userPassword, sex, age, address, createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userModel;
    }


    //user info
    public List<TakeOutUserModel> listTakeOutUser() {
        String sql = "SELECT * FROM t_take_out_user where id= ?";
        List<TakeOutUserModel> takeOutUsers = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
//            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userAccount = resultSet.getString("user_account");
                String nickName = resultSet.getString("nick_name");

                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");

                Integer sex = resultSet.getInt("sex");
                Integer age = resultSet.getInt("age");
                String address = resultSet.getString("address");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                TakeOutUserModel takeOutUser = new TakeOutUserModel(id, userAccount, userName, nickName, userPassword, sex, age, address, createDate);
                takeOutUsers.add(takeOutUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return takeOutUsers;
    }
}
