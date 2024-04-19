package com.assignment.dao;

import com.assignment.model.DishesModel;
import com.assignment.model.MerchantModel;
import com.assignment.model.ShopCarModel;
import com.assignment.util.DBTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Cart
 *
 * @createDate: 2024/04/13 16:34
 */
public class ShopCarModelDao {

    private DishesDao dishesDao = new DishesDao();

    private MerchantDao merchantDao = new MerchantDao();

    public Boolean insetShopCarModel(ShopCarModel shopCarModel) {
        String sql = "INSERT INTO t_shop_car (user_id,merchant_id, dishes_id, count,price,create_time)" +
                "VALUES (?,?,?,?,?,?)";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shopCarModel.getUserId());
            statement.setInt(2, shopCarModel.getMerchantId());

            statement.setInt(3, shopCarModel.getDishesId());
            statement.setInt(4, shopCarModel.getCount());
            statement.setDouble(5, shopCarModel.getPrice());

            Timestamp currentTimestamp = Timestamp.valueOf(java.time.LocalDateTime.now());
            statement.setTimestamp(6, currentTimestamp);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param id id
     * @description:
     * @return:
     */
    public Boolean deleteShopCarById(Integer id) {
        String sql = "DELETE FROM t_shop_car WHERE id = ? ";
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

    /**
     * @param userId   用户ID
     * @param dishesId 商品ID
     * @description:
     * @return:
     */
    public Boolean deleteShopCar(Integer userId, Integer dishesId) {
        String sql = "DELETE FROM t_shop_car WHERE user_id = ? and dishes_id=?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, dishesId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   
     
    public Boolean clearShopCarByUserId(Integer userId) {
        String sql = "DELETE FROM t_shop_car WHERE user_id = ?";
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

   
    public Boolean clearShopCar(Integer userId, Integer merchantId) {
        String sql = "DELETE FROM t_shop_car WHERE user_id = ? and merchant_id=?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, merchantId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<ShopCarModel> getMerchantByUserId(Integer userId, Integer merchantId, String dishesName) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_shop_car where user_id=" + userId + " and merchant_id=" + merchantId);

        if (Objects.nonNull(dishesName) && !dishesName.isEmpty()) {
            List<DishesModel> dishesModelDetails = dishesDao.getDishesModelDetails(dishesName, null);
            if (Objects.isNull(dishesModelDetails) || dishesModelDetails.isEmpty()) {
                return Collections.emptyList();
            }
        
            StringBuilder concatenatedField = new StringBuilder();
      
            for (int i = 0; i < dishesModelDetails.size(); i++) {
                DishesModel dishes = dishesModelDetails.get(i);
      
                Integer dishesId = dishes.getId();
              
                concatenatedField.append(dishesId);
                
                if (i < dishesModelDetails.size() - 1) {
                    concatenatedField.append(", ");
                }
            }
            sql.append(" and dishes_id in (" + concatenatedField + ")");
        }


        List<ShopCarModel> shopCarModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql.toString());
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");

                Integer dishesId = resultSet.getInt("dishes_id");
                Integer count = resultSet.getInt("count");
                Double price = resultSet.getDouble("price");
                Double totalPrice = price * new Double(count);

                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                ShopCarModel shopCarModel = new ShopCarModel(id, userId, dishesId, count, price, totalPrice,
                        createDate);

             
                DishesModel dishesModel = dishesDao.getDishesModelById(dishesId);
                shopCarModel.setDishesModel(dishesModel);

                if (Objects.nonNull(dishesModel)) {
              
                    MerchantModel merchantModel = merchantDao.getMerchantById(dishesModel.getMerchantId());
                    dishesModel.setMerchantModel(merchantModel);
                }

                shopCarModel.setTotalPrice(price * new Double(count));

                shopCarModels.add(shopCarModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopCarModels;
    }
}
