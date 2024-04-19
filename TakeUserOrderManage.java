package com.assignment.frame;

import com.assignment.common.Constant;
import com.assignment.dao.MerchantDao;
import com.assignment.dao.OrderDao;
import com.assignment.dao.ShopCarModelDao;
import com.assignment.enums.OrderStatus;
import com.assignment.enums.PayChannel;
import com.assignment.model.MerchantModel;
import com.assignment.model.OrderModel;
import com.assignment.model.TakeOutUserModel;
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

//deliverydetails
public class TakeUserOrderManage extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JToolBar jJToolBarBar = null;

    private JToolBar jJToolBar_bottom = null;

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

//    //viewchanges
//    private JButton jButton_shopcar_edit = null;

    private JScrollPane jScrollPane = null;
    private JTable jTable = null;

    private JLabel jLabel_Row = null;
    private int counts = 0;// orderquantity

    //shoppingcart
    private ShopCarModelDao shopCarModelDao = new ShopCarModelDao();

    private OrderDao orderDao = new OrderDao();
    private List<OrderModel> orderModels = null;

    private List<MerchantModel> merchantModels = null;

    private MerchantDao merchantDao = new MerchantDao();

    DefaultTableModel model = new DefaultTableModel();


    //ordercobfirmation
    JButton order_affirm = null;


    public TakeUserOrderManage() {
        super();
        initialize();
        initMerchant();

        initData();

//        // columnmodelfirstcolum
//        TableColumnModel columnModel = jTable.getColumnModel();
       
//        TableColumn firstColumn = columnModel.getColumn(0);

//        columnModel.removeColumn(firstColumn);
    }

    private Integer getMerchantId() {
        int selectedIndex = jComboBox_merchant.getSelectedIndex();
        if (selectedIndex > 0) {
            MerchantModel merchantModel = merchantModels.get(selectedIndex - 1);
            return Objects.nonNull(merchantModel) ? merchantModel.getId() : null;
        } else return null;
    }

    //businessinfo
    private void initMerchant() {
        merchantModels = merchantDao.listMerchantModel();

        jComboBox_merchant.addItem("--所有--");
        if (Objects.nonNull(merchantModels) && !merchantModels.isEmpty()) {
            for (MerchantModel merchantModel : merchantModels) {
                jComboBox_merchant.addItem(merchantModel.getMerchantName());
            }
        }
    }

    private void initOrderPayChannel() {
        PayChannel[] values = PayChannel.values();
        jComboBox_payChannel.addItem("--all--");
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


//        jLabel_empty = new JLabel("     订单总数:              ");


        jButton_student_query = new JButton();
        jButton_student_query.setText("Inquire");
        jButton_student_query.setBackground(Color.GREEN); 


//        jButton_shopcar_edit = new JButton();
//        jButton_shopcar_edit.setText("");

        jTable = new JTable();
        jTable.setSelectionBackground(new Color(0, 255, 204));
        jTable.setSelectionForeground(new Color(153, 0, 0));
        jTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

//        jLabel_Row = new JLabel();
//        jLabel_Row.setSize(25, 30);

        jJToolBarBar = new JToolBar();

        jLabel_merchant = new JLabel("Restaurant: ");
        jComboBox_merchant = new JComboBox();

        jJToolBarBar.add(jLabel_merchant);
        jJToolBarBar.add(jComboBox_merchant);
        jJToolBarBar.add(Box.createHorizontalStrut(10)); // horizontalgap


//        jLabel_status = new JLabel("  order status ");
//        jComboBox_status = new JComboBox();
//        jJToolBarBar.add(jLabel_status);
//        jJToolBarBar.add(jComboBox_status);

//        jJToolBarBar.add(Box.createHorizontalStrut(10)); horizontalgap
//
//        jLabel_payChannel = new JLabel("  payment method  ");
//        jComboBox_payChannel = new JComboBox();
//        jJToolBarBar.add(jLabel_payChannel);
//        jJToolBarBar.add(jComboBox_payChannel);

        jJToolBarBar.add(Box.createHorizontalStrut(10)); 

        jJToolBarBar.add(jLabel_orderNo);
        jJToolBarBar.add(jTextField_orderNo);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); 

        jJToolBarBar.add(jButton_student_query);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); 


//        jJToolBarBar.add(jLabel_empty);

        jLabel_Row = new JLabel();
        jJToolBarBar.add(jLabel_Row);


        jJToolBar_bottom = new JToolBar();
        order_affirm = new JButton("Accept");

        jJToolBar_bottom.add(order_affirm);
        order_affirm.setBackground(Color.GREEN); 
        jJToolBar_bottom.add(Box.createHorizontalStrut(10)); 

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

        jButton_student_query.addActionListener(btl);
        order_affirm.addActionListener(btl);

    }

    /**
     * @初始化界面
     */
    public void initData() {
        String heads[] = {"ID", "OrderID", "Customer", "Restaurant", "Amount", "Payment", "State", "Time"};
        model = new DefaultTableModel(null, heads);

        //shoppinglist
        orderModels = orderDao.listOrderModels(getMerchantId(), null, OrderStatus.MERCHANT_ACCEPTED.getStatusCode(), null);
        flashData();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void flashData() {
        counts = orderModels.size();
        model.setRowCount(orderModels.size());

        int index = 1;
        for (int i = 0; i < counts; i++) {
            OrderModel orderModel = orderModels.get(i);
            //ID
            model.setValueAt(orderModel.getId(), i, 0);
            //orderId
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

            
            model.setValueAt(orderModel.getTotalAmount(), i, 4);

            
            model.setValueAt(orderModel.getPayChannelDesc(), i, 5);

            
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
        jTable.setAutoCreateRowSorter(true);


      
        TableColumnModel columnModel = jTable.getColumnModel();
        
        TableColumn firstColumn = columnModel.getColumn(0);
       
        firstColumn.setMinWidth(0);
        firstColumn.setMaxWidth(0);
        firstColumn.setWidth(0);
        firstColumn.setPreferredWidth(0);

        
        TableColumn column4 = jTable.getColumnModel().getColumn(4);
        
        column4.setPreferredWidth(15);

        TableColumn column5 = jTable.getColumnModel().getColumn(5);
        
        column5.setPreferredWidth(15);

        TableColumn column6 = jTable.getColumnModel().getColumn(6);
      
        column6.setPreferredWidth(15);

//        TableColumn column7 = jTable.getColumnModel().getColumn(7);

//        column7.setPreferredWidth(30);
    }

    /**
     * class listener
     */
    private class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton_student_query) {
                String orderNo = jTextField_orderNo.getText();
                orderModels = orderDao.listOrderModels(getMerchantId(), orderNo, OrderStatus.MERCHANT_ACCEPTED.getStatusCode(), null);
                flashData();
            } else if (e.getSource() == order_affirm) {
                if (jTable.getSelectedRow() != -1) {
                    
                    String orderId = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
                    System.out.println("orderId:" + orderId);

                    OrderModel orderModel = orderDao.getOrderModelById(Integer.parseInt(orderId));
                    if (Objects.isNull(orderModel)) {
                        JOptionPane.showMessageDialog(null, "Order information does not exist!");
                        return;
                    }
                    if (orderModel.getStatus().equals(OrderStatus.ORDER_PLACED.getStatusCode())) {
                        JOptionPane.showMessageDialog(null, "Restaurant not accept!");
                        return;
                    }
                    if (!orderModel.getStatus().equals(OrderStatus.MERCHANT_ACCEPTED.getStatusCode())) {
                        JOptionPane.showMessageDialog(null, "The order has been delivered, please don't confirm again.!");
                        return;
                    }

                    Boolean dbRes = orderDao.taskOutOrderTaking(Integer.parseInt(orderId), OrderStatus.ON_DELIVERY.getStatusCode(), getUserId());
                    if (dbRes) {
                        JOptionPane.showMessageDialog(null, "Order Confirmation Success!");

                       
                        orderModels = orderDao.listOrderModels(null, null, OrderStatus.MERCHANT_ACCEPTED.getStatusCode(), null);
                        flashData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Order Confirmation Failure!");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select!");
                    return;
                }
            }
        }
    }

    public Integer getUserId() {
        return UserInfo.getTakeOutUserModel().getId();
    }

    public static void main(String args[]) {
        UserInfo.setTakeOutUserModel(new TakeOutUserModel(1, "W01", "Tom", "Tom", "1", 1, 1, "1", new Date()));
        UserInfo.setRoleType(Constant.ROLE_TAKE_OUT_USER);

        TakeUserOrderManage sm = new TakeUserOrderManage();
        sm.setVisible(true);
    }

}
