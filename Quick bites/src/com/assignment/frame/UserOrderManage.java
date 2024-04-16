package com.assignment.frame;

import com.assignment.common.Constant;
import com.assignment.dao.MerchantDao;
import com.assignment.dao.OrderDao;
import com.assignment.dao.ShopCarModelDao;
import com.assignment.enums.OrderStatus;
import com.assignment.enums.PayChannel;
import com.assignment.model.MerchantModel;
import com.assignment.model.OrderModel;
import com.assignment.model.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//订单管理
public class UserOrderManage extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JToolBar jJToolBarBar = null;

    private JLabel jLabel_orderNo = null;
    private JTextField jTextField_orderNo = null;


    //    private JButton jButton_student_add = null;
    private JLabel jLabel_empty = null;
    private JButton jButton_student_query = null;

    private JLabel jLabel_merchant = null;
    private JComboBox jComboBox_merchant = null;


    private JLabel jLabel_status = null;
    private JComboBox jComboBox_status = null;


    private JLabel jLabel_payChannel = null;
    private JComboBox jComboBox_payChannel = null;


//    //查看修改
//    private JButton jButton_shopcar_edit = null;

    private JScrollPane jScrollPane = null;
    private JTable jTable = null;

    private JLabel jLabel_Row = null;
    private int counts = 0;// 获取要查询的记录集数目

    //购物车
    private ShopCarModelDao shopCarModelDao = new ShopCarModelDao();


    private OrderDao orderDao = new OrderDao();
    private List<OrderModel> orderModels = null;

    private List<MerchantModel> merchantModels = null;

    private MerchantDao merchantDao = new MerchantDao();


    DefaultTableModel model = new DefaultTableModel();

    private JToolBar jJToolBar_bottom = null;

    //订单确认
    JButton order_details = null;

    //订单确认
    JButton order_affirm = null;


    public UserOrderManage() {
        super();
        initialize();
        initMerchant();
        initOrderStatus();
        initOrderPayChannel();

        initData();

//        // 获取第一列的列模型
//        TableColumnModel columnModel = jTable.getColumnModel();
//        // 获取第一列的 TableColumn 对象
//        TableColumn firstColumn = columnModel.getColumn(0);
//        // 从 JTable 中移除第一列
//        columnModel.removeColumn(firstColumn);
    }

    private void initOrderPayChannel() {
        PayChannel[] values = PayChannel.values();
        jComboBox_payChannel.addItem("--所有--");
        for (PayChannel payChannel : values) {
            jComboBox_payChannel.addItem(payChannel.getDesc());
        }
    }

    private void initOrderStatus() {
        OrderStatus[] values = OrderStatus.values();
        jComboBox_status.addItem("--all--");
        for (OrderStatus orderStatus : values) {
            jComboBox_status.addItem(orderStatus.getDesc());
        }
    }

    //初始化商户信息
    private void initMerchant() {
        merchantModels = merchantDao.listMerchantModel();

        jComboBox_merchant.addItem("--all--");
        if (Objects.nonNull(merchantModels) && !merchantModels.isEmpty()) {
            for (MerchantModel merchantModel : merchantModels) {
                jComboBox_merchant.addItem(merchantModel.getMerchantName());
            }
        }
    }

    private void initialize() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(750, 300);
        this.setTitle("Order Information");
        this.setLocationRelativeTo(null);
        //this.setModal(true);

//        jButton_student_add = new JButton();
//        jButton_student_add.setText("添加");

        jLabel_orderNo = new JLabel("OrderID: ");
        jTextField_orderNo = new JTextField();

        jLabel_merchant = new JLabel("Restaurant: ");
        jComboBox_merchant = new JComboBox();

//        jLabel_empty = new JLabel("     订单总数:              ");


        jButton_student_query = new JButton();
        jButton_student_query.setText("Inquire");
        jButton_student_query.setBackground(Color.GREEN); // 价格低于10的按钮背景色为绿色


//        jButton_shopcar_edit = new JButton();
//        jButton_shopcar_edit.setText("修改商品");

        jTable = new JTable();
        jTable.setSelectionBackground(new Color(0, 255, 204));
        jTable.setSelectionForeground(new Color(153, 0, 0));
        jTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

//        jLabel_Row = new JLabel();
//        jLabel_Row.setSize(25, 30);

        jJToolBarBar = new JToolBar();

        jJToolBarBar.add(jLabel_merchant);
        jJToolBarBar.add(jComboBox_merchant);
        jJToolBarBar.add(Box.createHorizontalStrut(10)); // 添加一个水平间隙

        jLabel_status = new JLabel("  State  ");
        jComboBox_status = new JComboBox();
        jJToolBarBar.add(jLabel_status);
        jJToolBarBar.add(jComboBox_status);

        jJToolBarBar.add(Box.createHorizontalStrut(10)); // 添加一个水平间隙

        jLabel_payChannel = new JLabel("  Payment  ");
        jComboBox_payChannel = new JComboBox();
        jJToolBarBar.add(jLabel_payChannel);
        jJToolBarBar.add(jComboBox_payChannel);

        jJToolBarBar.add(Box.createHorizontalStrut(10)); // 添加一个水平间隙

        jJToolBarBar.add(jLabel_orderNo);
        jJToolBarBar.add(jTextField_orderNo);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); // 添加一个水平间隙

        jJToolBarBar.add(jButton_student_query);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); // 添加一个水平间隙


        order_affirm = new JButton("Confirm");
        order_affirm.setBackground(Color.GREEN); // 价格低于10的按钮背景色为绿色


        order_details = new JButton("Order Detail");
        order_details.setBackground(Color.cyan); // 价格低于10的按钮背景色为绿色

        jJToolBar_bottom = new JToolBar();
        jJToolBar_bottom.add(order_details);
        jJToolBar_bottom.add(order_affirm);

//        jJToolBarBar.add(jLabel_empty);

        jLabel_Row = new JLabel();
        jJToolBarBar.add(jLabel_Row);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.setBorder(BorderFactory.createTitledBorder("订单信息"));
        jContentPane.add(jScrollPane, BorderLayout.CENTER);
        jContentPane.add(jJToolBarBar, BorderLayout.NORTH);
        jContentPane.add(jJToolBar_bottom, BorderLayout.SOUTH);
        this.setContentPane(jContentPane);

        btnListener btl = new btnListener();
//        jButton_student_add.addActionListener(btl);

        jButton_student_query.addActionListener(btl);

        jComboBox_merchant.addActionListener(btl);
        order_affirm.addActionListener(btl);
        order_details.addActionListener(btl);
    }

    /**
     * @初始化界面
     */
    public void initData() {
        String heads[] = {"ID", "OrderID", "Customer", "Restaurant", "Amount", "Payment", "State", "Time"};
        model = new DefaultTableModel(null, heads);

        //获取当前用户的购物车列表
        orderModels = orderDao.listOrderModels(null, null, getOrderStatus(), getOrderPayChannel());
        flashData();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void flashData() {
        counts = orderModels.size();
        model.setRowCount(orderModels.size());// 设置行数

        int index = 1;
        for (int i = 0; i < counts; i++) {
            OrderModel orderModel = orderModels.get(i);
            //ID
            model.setValueAt(orderModel.getId(), i, 0);
            //订单编码
            model.setValueAt(orderModel.getOrderNo(), i, 1);

            UserModel userModel = orderModel.getUserModel();
            if (Objects.nonNull(userModel)) {
                model.setValueAt(userModel.getUserName(), i, 2);
            } else {
                model.setValueAt(null, i, 2);
            }

            MerchantModel merchantModel = orderModel.getMerchantModel();
            if (Objects.nonNull(merchantModel)) {
                model.setValueAt(merchantModel.getMerchantName(), i, 3);
            } else {
                model.setValueAt(null, i, 3);
            }

            //总金额
            model.setValueAt(orderModel.getTotalAmount(), i, 4);

            //支付方式
            model.setValueAt(orderModel.getPayChannelDesc(), i, 5);

            //订单状态
            model.setValueAt(orderModel.getStatusDesc(), i, 6);

            Date createTime = orderModel.getCreateTime();
            if (Objects.nonNull(createTime)) {
                model.setValueAt(sdf.format(createTime), i, 7);
            } else {
                model.setValueAt("", i, 7);
            }

            index++;
        }
        jLabel_Row.setText("Number:" + counts + "");

        jTable.setModel(model);
        jTable.setRowHeight(22);
        jTable.setAutoCreateRowSorter(true);//为JTable设置排序器


        // 获取列模型
        TableColumnModel columnModel = jTable.getColumnModel();
// 获取第一列的 TableColumn 对象
        TableColumn firstColumn = columnModel.getColumn(0);
// 将第一列的宽度设置为0，使其隐藏
        firstColumn.setMinWidth(0);
        firstColumn.setMaxWidth(0);
        firstColumn.setWidth(0);
        firstColumn.setPreferredWidth(0);

        // 获取第一列的 TableColumn 对象
        TableColumn column4 = jTable.getColumnModel().getColumn(4);
        // 设置首选宽度为150像素
        column4.setPreferredWidth(15);

        TableColumn column5 = jTable.getColumnModel().getColumn(5);
        // 设置首选宽度为150像素
        column5.setPreferredWidth(15);

        TableColumn column6 = jTable.getColumnModel().getColumn(6);
        // 设置首选宽度为150像素
        column6.setPreferredWidth(15);

//        TableColumn column7 = jTable.getColumnModel().getColumn(7);
//        // 设置首选宽度为150像素
//        column7.setPreferredWidth(30);
    }

    /**
     * 内部类监听器模块
     */
    private class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton_student_query) {
                String orderNo = jTextField_orderNo.getText();
                orderModels = orderDao.listOrderModels(getMerchantId(), orderNo, getOrderStatus(), getOrderPayChannel());
                flashData();
            } else if (e.getSource() == order_affirm) {
                if (jTable.getSelectedRow() != -1) {// 选择行数如果非空
                    //订单编号
                    String orderId = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
                    System.out.println("orderId:" + orderId);

                    OrderModel orderModel = orderDao.getOrderModelById(Integer.parseInt(orderId));
                    if (Objects.isNull(orderModel)) {
                        JOptionPane.showMessageDialog(null, "Order information does not exist!");
                        return;
                    }
//                    if (orderModel.getStatus().equals(OrderStatus.MERCHANT_ACCEPTED.getStatusCode())) {
//                        JOptionPane.showMessageDialog(null, "商家已接单!");
//                        return;
//                    }
//                    if (orderModel.getStatus().equals(OrderStatus.ON_DELIVERY.getStatusCode())) {
//                        JOptionPane.showMessageDialog(null, "骑手尚未接单!");
//                        return;
//                    }
                    if (!orderModel.getStatus().equals(OrderStatus.ON_DELIVERY.getStatusCode())) {
                        JOptionPane.showMessageDialog(null, "The deliver has not taken the order and cannot confirm!");
                        return;
                    }

                    Boolean dbRes = orderDao.updateOrderStatus(Integer.parseInt(orderId), OrderStatus.COMPLETED.getStatusCode());
                    if (dbRes) {
                        JOptionPane.showMessageDialog(null, "confirmed!");

                        //获取当前用户的购物车列表
                        orderModels = orderDao.listOrderModels(getMerchantId(), null, getOrderStatus(), getOrderPayChannel());
                        flashData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Confirmation Failure!");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select!");
                    return;
                }
            } else if (e.getSource() == order_details) {
                if (jTable.getSelectedRow() != -1) {// 选择行数如果非空
                    //订单编号
                    String orderId = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
                    System.out.println("OrderDetail orderId:" + orderId);
                    OrderDetailsManage orderDetailsManage = new OrderDetailsManage(Integer.parseInt(orderId));
                    orderDetailsManage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select!");
                    return;
                }
            }
        }
    }


    //获取商户号信息
    private String getMerchantNo() {
        int selectedIndex = jComboBox_merchant.getSelectedIndex();
        if (selectedIndex > 0) {
            MerchantModel merchantModel = merchantModels.get(selectedIndex - 1);
            return Objects.nonNull(merchantModel) ? merchantModel.getMerchantNo() : null;
        } else return null;
    }

    private Integer getMerchantId() {
        int selectedIndex = jComboBox_merchant.getSelectedIndex();
        if (selectedIndex > 0) {
            MerchantModel merchantModel = merchantModels.get(selectedIndex - 1);
            return Objects.nonNull(merchantModel) ? merchantModel.getId() : null;
        } else return null;
    }

    private Integer getOrderStatus() {
        int selectedIndex = jComboBox_status.getSelectedIndex();
        if (selectedIndex > 0) {
            OrderStatus orderStatus = OrderStatus.values()[(selectedIndex - 1)];
            return Objects.nonNull(orderStatus) ? orderStatus.getStatusCode() : null;
        } else return null;
    }

    private Integer getOrderPayChannel() {
        int selectedIndex = jComboBox_payChannel.getSelectedIndex();
        if (selectedIndex > 0) {
            PayChannel payChannel = PayChannel.values()[(selectedIndex - 1)];
            return Objects.nonNull(payChannel) ? payChannel.getChannelCode() : null;
        } else return null;
    }

    public static void main(String args[]) {
        UserInfo.setUserModel(new UserModel(1, "1", "1", "1", "1", 1, 1, "1", new Date()));
        UserInfo.setRoleType(Constant.ROLE_USER);

        UserOrderManage sm = new UserOrderManage();
        sm.setVisible(true);
    }

}
