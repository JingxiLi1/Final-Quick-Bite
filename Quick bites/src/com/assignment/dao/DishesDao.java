package com.assignment.dao;

import com.assignment.common.Constant;
import com.assignment.frame.UserInfo;
import com.assignment.model.DishesModel;
import com.assignment.model.MerchantModel;
import com.assignment.util.DBTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 菜品信息
 *
 * @createDate: 2024/04/12 11:42
 */
public class DishesDao {

    //商户信息
    private MerchantDao merchantDao = new MerchantDao();

    public Boolean insetDishes(DishesModel dishesModel) {
        String sql = "INSERT INTO t_dishes (dishes_no, merchant_id, dishes_name,price,create_time)" +
                "VALUES (?,?,?,?,?)";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //获取当前登录人的商户号
            Integer merchantId = UserInfo.getMerchantModel().getId();

            statement.setString(1, dishesModel.getDishesNo());
            statement.setInt(2, merchantId);
            statement.setString(3, dishesModel.getDishesName());
            statement.setDouble(4, dishesModel.getPrice());

            Timestamp currentTimestamp = Timestamp.valueOf(java.time.LocalDateTime.now());
            statement.setTimestamp(5, currentTimestamp);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //根据ID 删除菜品信息
    public Boolean deleteById(Integer id) {
        String sql = "DELETE FROM t_dishes WHERE id = ?";
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

    //更新菜品信息
    public Boolean updateDishesModel(DishesModel upDishesModel) {
        String sql = "UPDATE t_dishes SET dishes_name = ?,price=? WHERE id = ?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, upDishesModel.getDishesName());
            statement.setDouble(2, upDishesModel.getPrice());

            statement.setInt(3, upDishesModel.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DishesModel getDishesModelById(Integer id) {
        String sql = "SELECT * FROM t_dishes where id= ?";
        DishesModel dishesModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishesId = resultSet.getInt("id");
                String dishesNo = resultSet.getString("dishes_no");
                Integer merchantId = resultSet.getInt("merchant_id");
                String dishesName = resultSet.getString("dishes_name");

                Double price = resultSet.getDouble("price");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                dishesModel = new DishesModel(dishesId, dishesNo, dishesName, price, merchantId);
                dishesModel.setCreateTime(createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishesModel;
    }


    public DishesModel getDishesModelByNo(String dishesNo) {
        String sql = "SELECT * FROM t_dishes where dishes_no= ?";
        DishesModel dishesModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, dishesNo);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishesId = resultSet.getInt("id");
                Integer merchantId = resultSet.getInt("merchant_id");
                String dishesName = resultSet.getString("dishes_name");

                Double price = resultSet.getDouble("price");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                dishesModel = new DishesModel(dishesId, dishesNo, dishesName, price, merchantId);
                dishesModel.setCreateTime(createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishesModel;
    }


    public List<DishesModel> getList() {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_dishes where 1=1");

        if (UserInfo.getRoleType().equals(Constant.ROLE_MERCHANT)) {
            //如果是商户登录 需要加上过滤 查看他自己的商品信息
            sql.append(" and merchant_id=" + UserInfo.getMerchantModel().getId());
        }

        List<DishesModel> dishesModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql.toString());
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishesId = resultSet.getInt("id");
                String dishesNo = resultSet.getString("dishes_no");
                Integer merchantId = resultSet.getInt("merchant_id");
                String dishesName = resultSet.getString("dishes_name");

                Double price = resultSet.getDouble("price");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                DishesModel dishesModel = new DishesModel(dishesId, dishesNo, dishesName, price, merchantId);
                dishesModel.setCreateTime(createDate);

                //设置商户信息
                MerchantModel merchantModel = merchantDao.getMerchantById(merchantId);
                dishesModel.setMerchantModel(merchantModel);

                dishesModels.add(dishesModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishesModels;
    }

    public List<DishesModel> getListByMerchantNo(Integer merchantId) {
        String sql = "SELECT * FROM t_dishes where  merchant_id=?";
        List<DishesModel> dishesModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, merchantId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishesId = resultSet.getInt("id");
                String dishesNo = resultSet.getString("dishes_no");
//                String merchantNo = resultSet.getString("merchant_no");
                String dishesName = resultSet.getString("dishes_name");

                Double price = resultSet.getDouble("price");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                DishesModel dishesModel = new DishesModel(dishesId, dishesNo, dishesName, price, merchantId);
                dishesModel.setCreateTime(createDate);
                dishesModels.add(dishesModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishesModels;
    }


    public List<DishesModel> getDataByName(String dishesName) {
        String sql = "SELECT * FROM t_dishes where  dishes_name=?";
        List<DishesModel> dishesModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, dishesName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishesId = resultSet.getInt("id");
                String dishesNo = resultSet.getString("dishes_no");
                Integer merchantId = resultSet.getInt("merchant_id");
//                String dishesName = resultSet.getString("dishes_name");

                Double price = resultSet.getDouble("price");
                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                DishesModel dishesModel = new DishesModel(dishesId, dishesNo, dishesName, price, merchantId);
                dishesModel.setCreateTime(createDate);

                //设置商户信息
                MerchantModel merchantModel = merchantDao.getMerchantById(merchantId);
                dishesModel.setMerchantModel(merchantModel);

                dishesModels.add(dishesModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishesModels;
    }


    public List<DishesModel> getDishesModelDetails(String dishesName, Integer merchantId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_dishes where 1=1 ");

        //美食信息
        if (Objects.nonNull(dishesName) && !dishesName.isEmpty()) {
            sql.append("and dishes_name like '%" + dishesName + "%'");
        }

        //商户信息
        if (Objects.nonNull(merchantId)) {
            sql.append("and merchant_id =" + merchantId);
        }

        List<DishesModel> dishesModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql.toString());
        ) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishesId = resultSet.getInt("id");
                String dishesno = resultSet.getString("dishes_no");
                Integer merchantid = resultSet.getInt("merchant_id");
                String dishesname = resultSet.getString("dishes_name");
                Double price = resultSet.getDouble("price");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                DishesModel dishesModel = new DishesModel(dishesId, dishesno, dishesname, price, merchantId);
                dishesModel.setCreateTime(createDate);

                //设置商户信息
                MerchantModel merchantModel = merchantDao.getMerchantById(merchantid);
                dishesModel.setMerchantModel(merchantModel);

                dishesModels.add(dishesModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishesModels;
    }

}
