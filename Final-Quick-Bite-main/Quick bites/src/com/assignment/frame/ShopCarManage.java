package com.assignment.frame;

import com.assignment.dao.MerchantDao;
import com.assignment.dao.OrderDao;
import com.assignment.dao.ShopCarModelDao;
import com.assignment.enums.PayChannel;
import com.assignment.model.DishesModel;
import com.assignment.model.MerchantModel;
import com.assignment.model.ShopCarModel;
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

//购买商品
public class ShopCarManage extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JToolBar jJToolBarBar = null;

    private JLabel jLabel_name = null;
    private JTextField jTextField_name = null;


    //    private JButton jButton_student_add = null;
    private JButton jButton_shop_car_delete = null;
    private JButton jButton_student_query = null;


    //确认按钮
    private JButton jButton_affirm = null;

    //清空按钮
    private JButton jButton_clear = null;

    private JLabel jLabel_merchant = null;
    private JComboBox jComboBox_merchant = null;


//    //查看修改
//    private JButton jButton_shopcar_edit = null;

    private JScrollPane jScrollPane = null;
    private JTable jTable = null;

    private JLabel jLabel_Row = null;
    private int counts = 0;

    //购物车
    private ShopCarModelDao shopCarModelDao = new ShopCarModelDao();

    private List<ShopCarModel> shopCars;  //  @jve:decl-index=0:

    private MerchantDao merchantDao = new MerchantDao();
    private List<MerchantModel> merchantModels = null;


    DefaultTableModel model = new DefaultTableModel();


    public ShopCarManage() {
        super();
        initialize();
        initMerchant();

        initData();


//        // 获取第一列的列模型
//        TableColumnModel columnModel = jTable.getColumnModel();
//        // 获取第一列的 TableColumn 对象
//        TableColumn firstColumn = columnModel.getColumn(0);
//        // 从 JTable 中移除第一列
//        columnModel.removeColumn(firstColumn);


    }

    //初始化商户信息
    private void initMerchant() {
        merchantModels = merchantDao.listMerchantModel();
        if (Objects.nonNull(merchantModels) && !merchantModels.isEmpty()) {
            for (MerchantModel merchantModel : merchantModels) {
                jComboBox_merchant.addItem(merchantModel.getMerchantName());
            }
        }
    }

    private void initialize() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600, 300);
        this.setTitle("Cart Information");
        this.setLocationRelativeTo(null);
        //this.setModal(true);

//        jButton_student_add = new JButton();
//        jButton_student_add.setText("添加");

        jLabel_name = new JLabel("Food Name: ");
        jTextField_name = new JTextField();

        jLabel_merchant = new JLabel("Restaurant: ");
        jComboBox_merchant = new JComboBox();


        jButton_shop_car_delete = new JButton();
        jButton_shop_car_delete.setText("Delet");

        jButton_student_query = new JButton();
        jButton_student_query.setText("Inquire");

        jButton_clear = new JButton();
        jButton_clear.setText("Empty cart");
        jButton_clear.setBackground(Color.yellow); 

        jButton_affirm = new JButton();
        jButton_affirm.setText("Checkout");
        jButton_affirm.setBackground(Color.GREEN); 

//        jButton_shopcar_edit = new JButton();
//        jButton_shopcar_edit.setText("");

        jTable = new JTable();
        jTable.setSelectionBackground(new Color(0, 255, 204));
        jTable.setSelectionForeground(new Color(153, 0, 0));
        jTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

//        jLabel_Row = new JLabel();
//        jLabel_Row.setSize(25, 30);

        jJToolBarBar = new JToolBar();

        jJToolBarBar.add(jLabel_merchant);
        jJToolBarBar.add(jComboBox_merchant);
        jJToolBarBar.add(Box.createHorizontalStrut(5)); // 添加一个水平间隙

        jJToolBarBar.add(jLabel_name);
        jJToolBarBar.add(jTextField_name);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); // 添加一个水平间隙

        jJToolBarBar.add(jButton_student_query);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); // 添加一个水平间隙

        jJToolBarBar.add(jButton_shop_car_delete);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); // 添加一个水平间隙

        jJToolBarBar.add(jButton_clear);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); // 添加一个水平间隙

        jJToolBarBar.add(jButton_affirm);


//        jJToolBarBar.add(jLabel_Row);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.setBorder(BorderFactory.createTitledBorder("My cart"));
        jContentPane.add(jScrollPane, BorderLayout.CENTER);
        jContentPane.add(jJToolBarBar, BorderLayout.NORTH);
        this.setContentPane(jContentPane);

        btnListener btl = new btnListener();
//        jButton_student_add.addActionListener(btl);
        jButton_shop_car_delete.addActionListener(btl);
        jButton_student_query.addActionListener(btl);
        jButton_affirm.addActionListener(btl);
        jButton_clear.addActionListener(btl);
        jComboBox_merchant.addActionListener(btl);
    }

    /**
     * @初始化界面
     */
    public void initData() {
        String heads[] = {"ID", "OrderID", "Name", "Price", "number", "Amount", "Restaurant", "Time"};
        model = new DefaultTableModel(null, heads);

        //获取当前用户的购物车列表
        shopCars = shopCarModelDao.getMerchantByUserId(UserInfo.getUserModel().getId(), getMerchantId(), null);
        flashData();
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public void flashData() {
        counts = shopCars.size();
        model.setRowCount(shopCars.size());// 设置行数

        int index = 1;
        for (int i = 0; i < counts; i++) {
            //ID
            model.setValueAt(shopCars.get(i).getId(), i, 0);

            //序号
            model.setValueAt(index, i, 1);

            DishesModel dishesModel = shopCars.get(i).getDishesModel();
            if (Objects.nonNull(dishesModel)) {
                model.setValueAt(dishesModel.getDishesName(), i, 2);
            } else {
                model.setValueAt(null, i, 2);
            }

            //价格
            model.setValueAt(shopCars.get(i).getPrice(), i, 3);

            //数量
            model.setValueAt(shopCars.get(i).getCount(), i, 4);
            //总金额
            model.setValueAt(shopCars.get(i).getTotalPrice(), i, 5);

            MerchantModel merchantModel = dishesModel.getMerchantModel();
            if (Objects.nonNull(merchantModel)) {
                model.setValueAt(merchantModel.getMerchantName(), i, 6);
            } else {
                model.setValueAt(null, i, 6);
            }

            Date createTime = shopCars.get(i).getCreateTime();
            if (Objects.nonNull(createTime)) {
                model.setValueAt(sdf.format(createTime), i, 7);
            } else {
                model.setValueAt("", i, 7);
            }

            index++;
        }
//        jLabel_Row.setText("$:" + counts + "");

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
    }

    /**
     * 内部类监听器模块
     */
    private class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton_shop_car_delete) {
                if (jTable.getSelectedRow() != -1) {// 选择行数如果非空
                    //这里是编号
                    String id = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
                    String goodName = jTable.getValueAt(jTable.getSelectedRow(), 2).toString();

                    int option = JOptionPane.showConfirmDialog(null, String.format("Are you sure Delet【%s】?", goodName), "confirm", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        Boolean addCar = shopCarModelDao.deleteShopCarById(Integer.parseInt(id));
                        if (addCar) {
                            JOptionPane.showMessageDialog(null, "Delete Successful!");

                            //删除记录
                            model.removeRow(jTable.getSelectedRow());
                        } else {
                            JOptionPane.showMessageDialog(null, "Delete Failed!");
                            return;
                        }
                    } else {
                        // 用户选择了取消删除，可以不执行任何操作或者给出相应提示
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select!");
                }
            } else if (e.getSource() == jButton_student_query) {
                String dishesName = jTextField_name.getText();
                Integer userId = UserInfo.getUserModel().getId();
                shopCars = shopCarModelDao.getMerchantByUserId(userId, getMerchantId(), dishesName);
                flashData();
            } else if (e.getSource() == jButton_affirm) {
                //结算...
                System.out.println("Chackout Restaurant:" + getMerchantId());

                // 创建包含所有支付方式的字符串数组
                String[] payChannelOptions = new String[PayChannel.values().length];
                for (int i = 0; i < PayChannel.values().length; i++) {
                    payChannelOptions[i] = PayChannel.values()[i].getDesc().toString();
                }
                // 弹出选择框，让用户选择订单状态
                String selectedStatus = (String) JOptionPane.showInputDialog(
                        null,
                        "Please select payment method:",
                        "Payment Method Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        payChannelOptions,
                        payChannelOptions[0] // 默认选择第一个状态
                );
                Integer channelCode = PayChannel.getChannelCodyByDescription(selectedStatus);
                if (Objects.isNull(channelCode)) {
                    JOptionPane.showMessageDialog(null, "Please select payment method!");
                    return;
                }

                OrderDao orderDao = new OrderDao();
                Boolean orderRes = orderDao.createOrder(getMerchantId(), channelCode);
                if (orderRes) {
                    JOptionPane.showMessageDialog(null, "Order Successful!");
                    //获取当前用户的购物车列表
                    shopCars = shopCarModelDao.getMerchantByUserId(UserInfo.getUserModel().getId(), getMerchantId(), null);
                    flashData();
                } else {
                    JOptionPane.showMessageDialog(null, "Order Failure!");
                    return;
                }
            } else if (e.getSource() == jButton_clear) {
                System.out.println("Empty cart");
                int option = JOptionPane.showConfirmDialog(null, "Are you empty your cart?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    Boolean addCar = shopCarModelDao.clearShopCarByUserId(UserInfo.getUserModel().getId());
                    if (addCar) {
                        JOptionPane.showMessageDialog(null, "Cart emptied successfully!");

                    
                        shopCars = shopCarModelDao.getMerchantByUserId(UserInfo.getUserModel().getId(), getMerchantId(), null);
                        flashData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Cart emptied failure!");
                        return;
                    }
                } else {
                   
                }
            }
           
        }
    }


    private String getMerchantNo() {
        int selectedIndex = jComboBox_merchant.getSelectedIndex();
        MerchantModel merchantModel = merchantModels.get(selectedIndex);
        return Objects.nonNull(merchantModel) ? merchantModel.getMerchantNo() : null;
    }

    private Integer getMerchantId() {
        int selectedIndex = jComboBox_merchant.getSelectedIndex();
        MerchantModel merchantModel = merchantModels.get(selectedIndex);
        return Objects.nonNull(merchantModel) ? merchantModel.getId() : null;
    }


    public static void main(String args[]) {
        UserInfo.setUserModel(new UserModel(1, "1", "1", "1", "1", 1, 1, "1", new Date()));

        ShopCarManage sm = new ShopCarManage();
        sm.setVisible(true);
    }

}
