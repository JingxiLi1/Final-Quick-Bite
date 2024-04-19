package com.assignment.dao;


import com.assignment.common.Constant;
import com.assignment.frame.UserInfo;
import com.assignment.model.MerchantModel;
import com.assignment.model.SysUserModel;
import com.assignment.util.DBTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 商户信息
 */
public class MerchantDao {

    private final static String PASSWORD = "1";

    public Boolean insetMerchant(MerchantModel merchantModel) {
        String sql = "INSERT INTO t_merchant (merchant_no, password, merchant_name,merchant_adress,phone,contact_person,create_time)" +
                "VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, merchantModel.getMerchantNo());
            statement.setString(2, PASSWORD);
            statement.setString(3, merchantModel.getMerchantName());
            statement.setString(4, merchantModel.getMerchantAdress());
            statement.setString(5, merchantModel.getPhone());
            statement.setString(6, merchantModel.getContactPerson());

            Timestamp currentTimestamp = Timestamp.valueOf(java.time.LocalDateTime.now());
            statement.setTimestamp(7, currentTimestamp);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deleteById(Integer id) {
        String sql = "DELETE FROM t_merchant WHERE id = ?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean updateMerchant(MerchantModel merchantModel) {
        String sql = "UPDATE t_merchant SET merchant_no = ?,merchant_name=?,merchant_adress=?,phone=?,contact_person=? WHERE id = ?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, merchantModel.getMerchantNo());
//            statement.setString(2, merchantModel.getPassword());

            statement.setString(2, merchantModel.getMerchantName());
            statement.setString(3, merchantModel.getMerchantAdress());

            statement.setString(4, merchantModel.getPhone());

            statement.setString(5, merchantModel.getContactPerson());

            statement.setInt(6, merchantModel.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public MerchantModel getMerchantById(Integer merchanId) {
        String sql = "SELECT * FROM t_merchant where id= ?";
        MerchantModel merchantModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, merchanId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String merchantNo = resultSet.getString("merchant_no");
                String password = resultSet.getString("password");
                String merchantName = resultSet.getString("merchant_name");
                String merchantAdress = resultSet.getString("merchant_adress");
                String phone = resultSet.getString("phone");
                String contactPerson = resultSet.getString("contact_person");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                merchantModel = new MerchantModel(id, merchantNo, merchantName, merchantAdress, phone,
                        contactPerson, password, createDate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return merchantModel;
    }


    public MerchantModel getMerchantByNo(String merchanNo) {
        String sql = "SELECT * FROM t_merchant where merchant_no= ?";
        MerchantModel merchantModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, merchanNo);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String merchantNo = resultSet.getString("merchant_no");
                String password = resultSet.getString("password");
                String merchantName = resultSet.getString("merchant_name");
                String merchantAdress = resultSet.getString("merchant_adress");
                String phone = resultSet.getString("phone");
                String contactPerson = resultSet.getString("contact_person");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                merchantModel = new MerchantModel(id, merchantNo, merchantName, merchantAdress, phone,
                        contactPerson, password, createDate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return merchantModel;
    }


    public List<MerchantModel> listMerchantModel() {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_merchant where 1=1");
        List<MerchantModel> merchantModels = new ArrayList<>();

        if (UserInfo.getRoleType().equals(Constant.ROLE_MERCHANT)) {
            //用户登录
            sql.append(" and id=" + UserInfo.getMerchantModel().getId());
        }

        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql.toString());
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String merchantNo = resultSet.getString("merchant_no");
                String password = resultSet.getString("password");
                String merchantName = resultSet.getString("merchant_name");
                String merchantAdress = resultSet.getString("merchant_adress");
                String phone = resultSet.getString("phone");
                String contactPerson = resultSet.getString("contact_person");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                merchantModels.add(new MerchantModel(id, merchantNo, merchantName, merchantAdress, phone,
                        contactPerson, password, createDate));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return merchantModels;
    }

    public List<MerchantModel> getMerchantByName(String merchantName) {
        String sql = "SELECT * FROM t_merchant where  merchant_name=?";
        List<MerchantModel> merchantModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, merchantName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String merchantNo = resultSet.getString("merchant_no");
                String password = resultSet.getString("password");
                String merchantname = resultSet.getString("merchant_name");
                String merchantAdress = resultSet.getString("merchant_adress");
                String phone = resultSet.getString("phone");
                String contactPerson = resultSet.getString("contact_person");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                merchantModels.add(new MerchantModel(id, merchantNo, merchantname, merchantAdress, phone,
                        contactPerson, password, createDate));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return merchantModels;
    }

    public Boolean login(String userName, String password) {
        MerchantModel merchantModel = getMerchantByNameAndPassword(userName, password);
        return Objects.nonNull(merchantModel);
    }

    //根据用户名密码获取用户信息
    public MerchantModel getMerchantByNameAndPassword(String merchantNo, String password) {
        String sql = "SELECT * FROM t_merchant where merchant_no= ? and password=?";
        SysUserModel sysUserModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, merchantNo);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String merchantno = resultSet.getString("merchant_no");
                String passWord = resultSet.getString("password");
                String merchantname = resultSet.getString("merchant_name");
                String merchantAdress = resultSet.getString("merchant_adress");
                String phone = resultSet.getString("phone");
                String contactPerson = resultSet.getString("contact_person");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                MerchantModel merchantModel = new MerchantModel(id, merchantno, merchantname, merchantAdress, phone,
                        contactPerson, passWord, createDate);
                return merchantModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        MerchantDao merchantDao1 = new MerchantDao();
    }
}
