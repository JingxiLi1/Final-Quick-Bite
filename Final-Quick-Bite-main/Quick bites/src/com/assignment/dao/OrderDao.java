package com.assignment.dao;

import com.assignment.common.Constant;
import com.assignment.enums.OrderStatus;
import com.assignment.enums.PayChannel;
import com.assignment.frame.UserInfo;
import com.assignment.model.*;
import com.assignment.util.DBTools;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 订单查询
 *
 * @createDate: 2024/04/14 18:28
 */
public class OrderDao {

    private MerchantDao merchantDao = new MerchantDao();

    private UserDao userDao = new UserDao();

    private ShopCarModelDao shopCarModelDao = new ShopCarModelDao();

    private OrderDetailDao orderDetailDao = new OrderDetailDao();

    private TakeOutUserDao takeOutUserDao = new TakeOutUserDao();

    /**
     * @param
     * @description: 对个人的购物车 (指定商户)一键下单
     * @date: 2024/4/14 21:24
     * @return:
     */
    public Boolean createOrder(Integer merchantId, Integer payChannel) {
        if (Objects.isNull(UserInfo.getUserModel())) {
            return false;
        }
        Integer userId = UserInfo.getUserModel().getId();
        if (Objects.isNull(userId)) {
            return false;
        }
        //查询当前用户的购物车信息
        List<ShopCarModel> shopCarModels = shopCarModelDao.getMerchantByUserId(userId, merchantId, null);

        if (Objects.isNull(shopCarModels) || shopCarModels.isEmpty()) {
            System.out.println("createOrder 购物车明细为空");
            return false;
        }

        Double totalAmount = 0.0;
        for (ShopCarModel shopCarModel : shopCarModels) {
            totalAmount += shopCarModel.getTotalPrice();
        }
        OrderModel orderModel = new OrderModel();
        orderModel.setTotalAmount(totalAmount);
        //商户信息
        orderModel.setMerchantId(merchantId);
        //商户信息
        orderModel.setUserId(merchantId);
        //支付方式
        orderModel.setPayChannel(payChannel);
        //支付方式
        orderModel.setStatus(OrderStatus.ORDER_PLACED.getStatusCode());

        OrderModel orderModelRes = insetOrder(orderModel);
        if (Objects.isNull(orderModelRes)) {
            return false;
        }
        //订单ID
        Integer orderId = orderModelRes.getId();

        for (ShopCarModel shopCarModel : shopCarModels) {
            OrderDetailModel orderDetailModel = new OrderDetailModel();
            orderDetailModel.setOrderId(orderId);
            orderDetailModel.setDishesId(shopCarModel.getDishesId());
            orderDetailModel.setCount(shopCarModel.getCount());
            orderDetailModel.setPrice(shopCarModel.getPrice());
            orderDetailModel.setTotalPrice(shopCarModel.getTotalPrice());
            Boolean dbRes = orderDetailDao.insetShopCarModel(orderDetailModel);
            if (!dbRes) {
                return false;
            }
        }
        //清空购物车信息
        boolean result = shopCarModelDao.clearShopCar(userId, merchantId);
        if (!result) {
            return false;
        }

        return true;
    }


    public OrderModel insetOrder(OrderModel orderModel) {
        String sql = "INSERT INTO t_order (merchant_id,order_no, user_id, total_amount,pay_channel,status,create_time)" +
                "VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, orderModel.getMerchantId());
            statement.setString(2, generateOrderCode());
            statement.setInt(3, orderModel.getUserId());
            statement.setDouble(4, orderModel.getTotalAmount());
            statement.setInt(5, orderModel.getPayChannel());
            statement.setInt(6, orderModel.getStatus());

            Timestamp currentTimestamp = Timestamp.valueOf(java.time.LocalDateTime.now());
            statement.setTimestamp(7, currentTimestamp);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // 获取生成的主键值
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // 返回插入的主键 ID
                        orderModel.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return orderModel;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 生成订单编码的方法
    private static String generateOrderCode() {
        // 生成时间部分
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timePart = dateFormat.format(new java.util.Date());
        // 生成随机数部分
        Random random = new Random();
        int randomPart = random.nextInt(1000); // 生成 0 到 9999 之间的随机数
        // 组合时间和随机数，生成订单编码
        String orderCode = timePart + String.format("%03d", randomPart); // 保证随机数部分始终是 4 位数
        return orderCode;
    }

    // 获取订单详情
    public OrderModel getOrderModelById(Integer id) {
        String sql = "SELECT * FROM t_order where id= ?";
        OrderModel orderModel = null;
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer merchantId = resultSet.getInt("merchant_id");
                String orderNo = resultSet.getString("order_no");
                Integer userId = resultSet.getInt("user_id");
                Double totalAmount = resultSet.getDouble("total_amount");
                Integer payChannel = resultSet.getInt("pay_channel");
                Integer status = resultSet.getInt("status");

                Integer takeOutUserId = resultSet.getInt("take_out_user_id");

                Timestamp createTime = resultSet.getTimestamp("create_time");
                java.sql.Date createDate = new java.sql.Date(createTime.getTime());

                orderModel = new OrderModel();
                orderModel.setId(id);
                orderModel.setMerchantId(merchantId);
                //外卖人员ID
                orderModel.setTakeOutUserId(takeOutUserId);
                orderModel.setOrderNo(orderNo);
                orderModel.setUserId(userId);
                orderModel.setTotalAmount(totalAmount);
                orderModel.setCreateTime(createDate);

                orderModel.setStatus(status);
                orderModel.setPayChannel(payChannel);

                //订单描述 和 支付方式
                orderModel.setStatusDesc(OrderStatus.getChannelCodyByCode(status));
                orderModel.setPayChannelDesc(PayChannel.getChannelCodyByCode(payChannel));

                //用户信息 商户信息
                MerchantModel merchantModel = merchantDao.getMerchantById(orderModel.getMerchantId());
                orderModel.setMerchantModel(merchantModel);

                UserModel userModel = userDao.getUserById(orderModel.getUserId());
                orderModel.setUserModel(userModel);

                TakeOutUserModel takeOutUserModel = takeOutUserDao.getUserById(orderModel.getTakeOutUserId());
                if (Objects.nonNull(takeOutUserModel)) {
                    orderModel.setTakeOutUserModel(takeOutUserModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderModel;
    }


    public List<OrderModel> listOrderModels(Integer merchantId, String orderNo, Integer status, Integer payChannel) {
        return listOrderModels(merchantId, orderNo, status, payChannel, null);
    }


    // 获取订单详情
    public List<OrderModel> listOrderModels(Integer merchantId, String orderNo, Integer status, Integer payChannel, Integer takeOutUserId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_order where 1=1");
//        if (UserInfo.getRoleType().equals(Constant.ROLE_ADMIN) && Objects.nonNull(merchantId)) {
//            //管理员可以进行  商户ID  查询
//            sql.append(" and merchant_id=" + merchantId);
//        }
        if (Objects.nonNull(merchantId)) {
            //管理员可以进行  商户ID  查询
            sql.append(" and merchant_id=" + merchantId);
        } else {
            if (UserInfo.getRoleType().equals(Constant.ROLE_MERCHANT)) {
                //管理员可以进行  商户ID  查询
                sql.append(" and merchant_id=" + UserInfo.getMerchantModel().getId());
            }
        }
        if (UserInfo.getRoleType().equals(Constant.ROLE_USER)) {
            //用户登录
            sql.append(" and user_id=" + UserInfo.getUserModel().getId());
        }
        if (Objects.nonNull(orderNo) && !orderNo.isEmpty()) {
            //用户登录
            sql.append(" and order_no=" + orderNo);
        }

        if (Objects.nonNull(status)) {
            //用户登录
            sql.append(" and status=" + status);
        }

        if (Objects.nonNull(payChannel)) {
            //用户登录
            sql.append(" and pay_channel=" + payChannel);
        }

        if (Objects.nonNull(takeOutUserId)) {
            //用户登录
            sql.append(" and take_out_user_id=" + takeOutUserId);
        }

        List<OrderModel> orderModels = new ArrayList<>();
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql.toString());
        ) {
//            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer merchantid = resultSet.getInt("merchant_id");
                String orderno = resultSet.getString("order_no");
                Integer userId = resultSet.getInt("user_id");
                Double totalAmount = resultSet.getDouble("total_amount");
                Integer paychannel = resultSet.getInt("pay_channel");
                Integer takeOutUserid = resultSet.getInt("take_out_user_id");


                Timestamp createTime = resultSet.getTimestamp("create_time");
                java.sql.Date createDate = new java.sql.Date(createTime.getTime());

                OrderModel orderModel = new OrderModel();
                orderModel.setId(id);
                orderModel.setMerchantId(merchantid);
                orderModel.setTakeOutUserId(takeOutUserid);
                orderModel.setOrderNo(orderno);
                orderModel.setUserId(userId);
                orderModel.setTotalAmount(totalAmount);
                orderModel.setCreateTime(createDate);

                orderModel.setStatus(resultSet.getInt("status"));
                orderModel.setPayChannel(paychannel);

                //订单描述 和 支付方式
                orderModel.setStatusDesc(OrderStatus.getChannelCodyByCode(orderModel.getStatus()));
                orderModel.setPayChannelDesc(PayChannel.getChannelCodyByCode(orderModel.getPayChannel()));

                //用户信息 商户信息
                MerchantModel merchantModel = merchantDao.getMerchantById(orderModel.getMerchantId());
                orderModel.setMerchantModel(merchantModel);

                UserModel userModel = userDao.getUserById(orderModel.getUserId());
                orderModel.setUserModel(userModel);

                //骑手信息
                TakeOutUserModel takeOutUserModel = takeOutUserDao.getUserById(orderModel.getTakeOutUserId());
                if (Objects.nonNull(takeOutUserModel)) {
                    orderModel.setTakeOutUserModel(takeOutUserModel);
                }

                orderModels.add(orderModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderModels;
    }

    public Boolean updateOrderStatus(Integer orderId, Integer status) {
        String sql = "UPDATE t_order SET status = ? WHERE id = ?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, status);
            statement.setInt(2, orderId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean taskOutOrderTaking(Integer orderId, Integer status, Integer taskeUserId) {
        String sql = "UPDATE t_order SET status = ?,take_out_user_id = ? WHERE id = ?";
        try (Connection connection = DBTools.getConnetion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, status);
            statement.setInt(2, taskeUserId);
            statement.setInt(3, orderId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
