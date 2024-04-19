/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quickbites;

/**
 *
 * @author Zoher
 */


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

// Order management
public class AdminOrderManage extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JToolBar jJToolBarBar = null;

    private JLabel jLabel_orderNo = null;
    private JTextField jTextField_orderNo = null;

    // private JButton jButton_student_add = null;
    private JLabel jLabel_empty = null;
    private JButton jButton_student_query = null;

    private JLabel jLabel_merchant = null;
    private JComboBox jComboBox_merchant = null;

    private JLabel jLabel_status = null;
    private JComboBox jComboBox_status = null;

    private JLabel jLabel_payChannel = null;
    private JComboBox jComboBox_payChannel = null;

    private JScrollPane jScrollPane = null;
    private JTable jTable = null;

    private JLabel jLabel_Row = null;
    private int counts = 0; // Get the number of records to be queried

    // Shopping cart
    private ShopCarModelDao shopCarModelDao = new ShopCarModelDao();

    private OrderDao orderDao = new OrderDao();
    private List<OrderModel> orderModels = null;

    private List<MerchantModel> merchantModels = null;

    private MerchantDao merchantDao = new MerchantDao();

    DefaultTableModel model = new DefaultTableModel();

    public AdminOrderManage() {
        super();
        initialize();
        initMerchant();
        initOrderStatus();
        initOrderPayChannel();

        initData();
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

    // Initialize merchant information
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
        this.setTitle("OrderInfo");
        this.setLocationRelativeTo(null);

        jLabel_orderNo = new JLabel("OrderID: ");
        jTextField_orderNo = new JTextField();

        jLabel_merchant = new JLabel("Restaurant: ");
        jComboBox_merchant = new JComboBox();

        jButton_student_query = new JButton();
        jButton_student_query.setText("Inquire");
        jButton_student_query.setBackground(Color.GREEN);

        jTable = new JTable();
        jTable.setSelectionBackground(new Color(0, 255, 204));
        jTable.setSelectionForeground(new Color(153, 0, 0));
        jTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jJToolBarBar = new JToolBar();

        jJToolBarBar.add(jLabel_merchant);
        jJToolBarBar.add(jComboBox_merchant);
        jJToolBarBar.add(Box.createHorizontalStrut(10));

        jLabel_status = new JLabel("  State  ");
        jComboBox_status = new JComboBox();
        jJToolBarBar.add(jLabel_status);
        jJToolBarBar.add(jComboBox_status);

        jJToolBarBar.add(Box.createHorizontalStrut(10));

        jLabel_payChannel = new JLabel("  Payment  ");
        jComboBox_payChannel = new JComboBox();
        jJToolBarBar.add(jLabel_payChannel);
        jJToolBarBar.add(jComboBox_payChannel);

        jJToolBarBar.add(Box.createHorizontalStrut(10));

        jJToolBarBar.add(jLabel_orderNo);
        jJToolBarBar.add(jTextField_orderNo);

        jJToolBarBar.add(Box.createHorizontalStrut(5));

        jJToolBarBar.add(jButton_student_query);

        jJToolBarBar.add(Box.createHorizontalStrut(5));

        jLabel_Row = new JLabel();
        jJToolBarBar.add(jLabel_Row);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.setBorder(BorderFactory.createTitledBorder("OrderInfo"));
        jContentPane.add(jScrollPane, BorderLayout.CENTER);
        jContentPane.add(jJToolBarBar, BorderLayout.NORTH);
        this.setContentPane(jContentPane);

        btnListener btl = new btnListener();
        jButton_student_query.addActionListener(btl);
        jComboBox_merchant.addActionListener(btl);
    }

    /**
     * Initialize the interface
     */
    public void initData() {
        String heads[] = {"ID", "OrderID", "Customer", "Restaurant", "Amount", "Payment", "State", "Time", "Deliver"};
        model = new DefaultTableModel(null, heads);

        // Get the current user's shopping cart list
        orderModels = orderDao.listOrderModels(null, null, getOrderStatus(), getOrderPayChannel());
        flashData();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void flashData() {
        counts = orderModels.size();
        model.setRowCount(orderModels.size());// Set the number of rows


        int index = 1;
        for (int i = 0; i < counts; i++) {
            OrderModel orderModel = orderModels.get(i);
            //ID
            model.setValueAt(orderModel.getId(), i, 0);
            //Order code
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

            //amount
            model.setValueAt(orderModel.getTotalAmount(), i, 4);

            //payment method
            model.setValueAt(orderModel.getPayChannelDesc(), i, 5);

            //state
            model.setValueAt(orderModel.getStatusDesc(), i, 6);

            Date createTime = orderModel.getCreateTime();
            if (Objects.nonNull(createTime)) {
                model.setValueAt(sdf.format(createTime), i, 7);
            } else {
                model.setValueAt("", i, 7);
            }

            TakeOutUserModel takeOutUserModel = orderModel.getTakeOutUserModel();
            if (Objects.nonNull(takeOutUserModel)) {
                model.setValueAt(takeOutUserModel.getUserName(), i, 8);
            } else {
                model.setValueAt(null, i, 8);
            }

            index++;
        }
        jLabel_Row.setText("Number of order:" + counts + "");

        jTable.setModel(model);
        jTable.setRowHeight(22);
        jTable.setAutoCreateRowSorter(true);//Set sorter for JTable


        // Get column model

        TableColumnModel columnModel = jTable.getColumnModel();
// Get the TableColumn object of the first column
        TableColumn firstColumn = columnModel.getColumn(0);
// Set the width of the first column to 0, making it hidden
        firstColumn.setMinWidth(0);
        firstColumn.setMaxWidth(0);
        firstColumn.setWidth(0);
        firstColumn.setPreferredWidth(0);

        // Get the TableColumn object of the first column
        TableColumn column4 = jTable.getColumnModel().getColumn(4);
        // Set preferred width to 150 pixels
        column4.setPreferredWidth(15);

        TableColumn column5 = jTable.getColumnModel().getColumn(5);
        // Set preferred width to 150 pixels
        column5.setPreferredWidth(15);

        TableColumn column6 = jTable.getColumnModel().getColumn(6);
        // Set preferred width to 150 pixels
        column6.setPreferredWidth(15);

//        TableColumn column7 = jTable.getColumnModel().getColumn(7);
//        // Set preferred width to 150 pixels
//        column7.setPreferredWidth(30);
    }

    /**
     * Internal class listener module
     */
    private class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton_student_query) {
                String orderNo = jTextField_orderNo.getText();
                orderModels = orderDao.listOrderModels(getMerchantId(), orderNo, getOrderStatus(), getOrderPayChannel());
                flashData();
            }
//            else if (e.getSource() == jButton_shopcar_edit) {
//                System.out.println("Modify shopping cart information");
//                if (jTable.getSelectedRow() != -1) {
//                    // Select row number if non-empty
//                } else {
//                    JOptionPane.showMessageDialog(null, "Please select the corresponding product!");
//                }
//            }
        }
    }


    //Get merchant number information
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

        AdminOrderManage sm = new AdminOrderManage();
        sm.setVisible(true);
    }

}


