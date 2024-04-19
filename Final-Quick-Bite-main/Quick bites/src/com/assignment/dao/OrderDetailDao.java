package com.assignment.dao;

import com.assignment.model.DishesModel;
import com.assignment.model.OrderDetailModel;
import com.assignment.util.DBTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <Description>
 *
 * @createDate: 2024/04/14 23:34
 */
public class OrderDetailDao {

    private DishesDao dishesDao = new DishesDao();

    public Boolean insetShopCarModel(OrderDetailModel orderDetailModel) {
        String sql = "INSERT INTO t_order_detail (order_id,dishes_id, count, price,total_price,create_time)" +
                "VALUES (?,?,?,?,?,?)";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderDetailModel.getOrderId());
            statement.setInt(2, orderDetailModel.getDishesId());
            statement.setInt(3, orderDetailModel.getCount());

            statement.setDouble(4, orderDetailModel.getPrice());
            statement.setDouble(5, orderDetailModel.getTotalPrice());

            Timestamp currentTimestamp = Timestamp.valueOf(java.time.LocalDateTime.now());
            statement.setTimestamp(6, currentTimestamp);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


  
    public List<OrderDetailModel> getOrderDetailModelsByOrderId(Integer orderId) {
        String sql = "SELECT * FROM t_order_detail where  order_id=?";
        List<OrderDetailModel> orderDetailModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Integer orderid = resultSet.getInt("order_id");
                Integer dishesId = resultSet.getInt("dishes_id");
                Integer count = resultSet.getInt("count");
                Double price = resultSet.getDouble("price");
                Double totalPrice = resultSet.getDouble("total_price");
                Timestamp createTime = resultSet.getTimestamp("create_time");
                Date createDate = new Date(createTime.getTime());

                DishesModel dishesModel = dishesDao.getDishesModelById(dishesId);
                OrderDetailModel orderDetailModel = new OrderDetailModel(id, orderid, dishesId, count,
                        price, totalPrice, createDate);
                orderDetailModel.setDishesModel(dishesModel);

                orderDetailModels.add(orderDetailModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetailModels;
    }

}
