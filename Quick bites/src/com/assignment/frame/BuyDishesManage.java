package com.assignment.frame;

import com.assignment.dao.DishesDao;
import com.assignment.dao.MerchantDao;
import com.assignment.dao.ShopCarModelDao;
import com.assignment.model.DishesModel;
import com.assignment.model.MerchantModel;
import com.assignment.model.ShopCarModel;
import com.assignment.model.UserModel;
import com.assignment.util.VerifyUtils;

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

//Order
public class BuyDishesManage extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JToolBar jJToolBarBar = null;

    private JLabel jLabel_name = null;
    private JTextField jTextField_name = null;

    private JLabel jLabel_merchant = null;
    private JComboBox jComboBox_merchant = null;


    //    private JButton jButton_student_add = null;
    private JButton jButton_shop_car_add = null;
    private JButton jButton_student_query = null;

    //cart
    private JButton jButton_shopcar_query = null;

    private JScrollPane jScrollPane = null;
    private JTable jTable = null;

    private JLabel jLabel_Row = null;
    private int counts = 0;// 获取要查询的记录集数目

    private DishesDao dishesDao = new DishesDao();

    private List<DishesModel> dishes;  //  @jve:decl-index=0:


    DefaultTableModel model = new DefaultTableModel();

    //cart
    private ShopCarModelDao shopCarModelDao = new ShopCarModelDao();

    private MerchantDao merchantDao = new MerchantDao();
    private List<MerchantModel> merchantModels = null;

    public BuyDishesManage() {
        super();
        initialize();
        initMerchant();

        //初始化表格
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
        this.setSize(658, 368);
        this.setTitle("Purchase");
        this.setLocationRelativeTo(null);
        //this.setModal(true);

//        jButton_student_add = new JButton();
//        jButton_student_add.setText("添加");

        jLabel_name = new JLabel("Name: ");
        jTextField_name = new JTextField();

        jLabel_merchant = new JLabel("Restaurant: ");
        jComboBox_merchant = new JComboBox();

        jButton_shop_car_add = new JButton();
        jButton_shop_car_add.setText("Add Cart");
        jButton_shop_car_add.setBackground(Color.GREEN); // 价格低于10的按钮背景色为绿色

        jButton_student_query = new JButton();
        jButton_student_query.setText("Inquire");

        jButton_shopcar_query = new JButton();
        jButton_shopcar_query.setText("Cart");
        jButton_shopcar_query.setBackground(Color.GREEN); // 价格低于10的按钮背景色为绿色

        jTable = new JTable();
        jTable.setSelectionBackground(new Color(0, 255, 204));
        jTable.setSelectionForeground(new Color(153, 0, 0));
        jTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jLabel_Row = new JLabel();
        jLabel_Row.setSize(25, 30);

        jJToolBarBar = new JToolBar();

        jJToolBarBar.add(jLabel_name);
        jJToolBarBar.add(jTextField_name);

        jJToolBarBar.add(Box.createHorizontalStrut(10)); // 添加一个水平间隙

        jJToolBarBar.add(jLabel_merchant);
        jJToolBarBar.add(jComboBox_merchant);

        jJToolBarBar.add(Box.createHorizontalStrut(10)); // 添加一个水平间隙

        jJToolBarBar.add(jButton_student_query);

        jJToolBarBar.add(Box.createHorizontalStrut(10)); // 添加一个水平间隙

        jJToolBarBar.add(jButton_shop_car_add);

        jJToolBarBar.add(Box.createHorizontalStrut(10)); // 添加一个水平间隙

        jJToolBarBar.add(jButton_shopcar_query);


        jJToolBarBar.add(jLabel_Row);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.setBorder(BorderFactory.createTitledBorder("选择菜品"));
        jContentPane.add(jScrollPane, BorderLayout.CENTER);
        jContentPane.add(jJToolBarBar, BorderLayout.NORTH);
        this.setContentPane(jContentPane);

        btnListener btl = new btnListener();
//        jButton_student_add.addActionListener(btl);
        jButton_shop_car_add.addActionListener(btl);
        jButton_student_query.addActionListener(btl);
        jButton_shopcar_query.addActionListener(btl);
        jComboBox_merchant.addActionListener(btl);

    }

    /**
     * @初始化界面
     */
    public void initData() {
        String heads[] = {"ID", "Number", "Restaurant", "FoodID", "Name", "Price", "Address", "Time"};
        model = new DefaultTableModel(null, heads);

//        //获取当前选择的商户
//        int selectedIndex = jComboBox_merchant.getSelectedIndex();
//        MerchantModel merchantModel = merchantModels.get(selectedIndex);

        dishes = dishesDao.getDishesModelDetails(null,
                getMerchantId());
        flashData();
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public void flashData() {
        counts = dishes.size();
        model.setRowCount(dishes.size());// 设置行数

        int index = 1;
        for (int i = 0; i < counts; i++) {
            //ID
            model.setValueAt(dishes.get(i).getId(), i, 0);

            //序号
            model.setValueAt(index, i, 1);


            MerchantModel merchantModel = dishes.get(i).getMerchantModel();
            if (Objects.nonNull(merchantModel)) {
                model.setValueAt(merchantModel.getMerchantName(), i, 2);
            } else {
                model.setValueAt(null, i, 2);
            }

            model.setValueAt(dishes.get(i).getDishesNo(), i, 3);
            model.setValueAt(dishes.get(i).getDishesName(), i, 4);
            model.setValueAt(dishes.get(i).getPrice(), i, 5);

            if (Objects.nonNull(merchantModel)) {
                model.setValueAt(merchantModel.getMerchantAdress(), i, 6);
            } else {
                model.setValueAt(null, i, 6);
            }

            Date createTime = dishes.get(i).getCreateTime();
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
    }

    /**
     * 内部类监听器模块
     */
    private class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton_shop_car_add) {
                if (jTable.getSelectedRow() != -1) {// 选择行数如果非空
                    //这里是编号
                    String id = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();

                    String price = jTable.getValueAt(jTable.getSelectedRow(), 5).toString();

                    String count;
                    try {
                        count = JOptionPane.showInputDialog(null, "Please select the quantity",
                                "Quantity", JOptionPane.QUESTION_MESSAGE, null, null, "1").toString();
                    } catch (Exception e2) {
                        return;
                    }
                    if (!VerifyUtils.isNumber(count)) {
                        JOptionPane.showMessageDialog(null, "error");
                        return;
                    }

                    System.out.println("Cart id:" + id + "Quantity:" + count);

                    //获取当前用户信息
                    UserModel userModel = UserInfo.getUserModel();
                    Double goodPrice = Double.valueOf(price);
                    Double totlePrice = goodPrice * new Double(count);

                    Boolean addCar = shopCarModelDao.insetShopCarModel(new ShopCarModel(userModel.getId(), Integer.valueOf(id),
                            Integer.valueOf(count), goodPrice, totlePrice, getMerchantId()));
                    if (addCar) {
                        JOptionPane.showMessageDialog(null, "Add to Cart Success!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add cart!");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select product!");
                }
            } else if (e.getSource() == jButton_student_query) {
                String dishesName = jTextField_name.getText();
                dishes = dishesDao.getDishesModelDetails(dishesName, getMerchantId());
                flashData();
            } else if (e.getSource() == jButton_shopcar_query) {
                System.out.println("Check Shopping Cart Information");
                ShopCarManage shopCarManage = new ShopCarManage();
                shopCarManage.setVisible(true);
            } else if (e.getSource() == jComboBox_merchant) {
                System.out.println("Switch Restaurant NO:" + getMerchantNo());
                initData();
            }
        }
    }


    //获取商户号信息
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

        BuyDishesManage sm = new BuyDishesManage();
        sm.setVisible(true);
    }

}
