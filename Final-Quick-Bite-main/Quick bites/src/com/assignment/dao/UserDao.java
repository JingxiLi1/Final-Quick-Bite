package com.assignment.dao;

import com.assignment.model.UserModel;
import com.assignment.util.DBTools;

import java.sql.*;

/**
 * <Description>
 */
public class UserDao {


    public UserModel getUserByNameAndPassword(String userName, String userPassword) {
        String sql = "SELECT * FROM t_user where user_account= ? and user_password=?";
        UserModel userModel = null;
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

                userModel = new UserModel(id, userAccount, userName, nickName, userPassword, sex, age, address, createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userModel;
    }



    public UserModel getUserById(Integer userId) {
        String sql = "SELECT * FROM t_user where id= ?";
        UserModel userModel = null;
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

                userModel = new UserModel(id, userAccount, userName, nickName, userPassword, sex, age, address, createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userModel;
    }
}
