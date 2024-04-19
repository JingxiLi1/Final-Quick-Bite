package com.assignment.frame;

import com.assignment.dao.DishesDao;
import com.assignment.model.DishesModel;
import com.assignment.util.VerifyUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DishesAdd extends JDialog {
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel jLabel_number = null;
    private JLabel jLabel_name = null;
    private JLabel jLabel_price = null;
    private JTextField jTextField_price = null;
    private DishesDao dishesDao = new DishesDao();
    private JTextField jTextField_id = null;
    private JTextField jTextField_name = null;
    private JButton jButton_stu_add = null;
    private JButton jButton_cancel = null;

    private JRadioButton jRadioButton_Boy = null;
    private JRadioButton jRadioButton_Girl = null;
    private ButtonGroup buttonts = null;  //  @jve:decl-index=0:
    private String stu_sex = "male";  //  @jve:decl-index=0:

    private boolean isModify = false;//是添加还是修改？

    private String id;

    /**
     *
     */
    public DishesAdd(boolean isModify, String id) {

        super();

        this.id = id;

        initialize();
        this.isModify = isModify;

        if (isModify)
            modifyMerchant(isModify, id);
    }

    private void initialize() {
        this.setSize(300, 300);
        this.setModal(true);
        this.setTitle("Add");
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("../images/icon.png")));


        jLabel_number = new JLabel();
        jLabel_number.setBounds(new Rectangle(26, 10, 71, 28));
        jLabel_number.setText("Item No.：");

        jLabel_name = new JLabel();
        jLabel_name.setBounds(new Rectangle(26, 50, 71, 22));
        jLabel_name.setText("Name：");

        jLabel_price = new JLabel();
        jLabel_price.setBounds(new Rectangle(26, 90, 71, 28));
        jLabel_price.setText("Price：");


        jTextField_id = new JTextField();
        jTextField_id.setBounds(new Rectangle(112, 10, 125, 24));

        jTextField_name = new JTextField();
        jTextField_name.setBounds(new Rectangle(112, 50, 125, 24));


        jTextField_price = new JTextField();
        jTextField_price.setBounds(new Rectangle(112, 90, 125, 24));


        jButton_stu_add = new JButton();
        jButton_stu_add.setBounds(new Rectangle(40, 150, 78, 26));
        jButton_stu_add.setText("Confirm");

        jButton_cancel = new JButton();
        jButton_cancel.setBounds(new Rectangle(156, 150, 78, 26));
        jButton_cancel.setText("Cancel");

        gettsButtonGroup();// 加入按钮组

        jContentPane = new JPanel();
        jContentPane.setLayout(null);
        jContentPane.add(jLabel_number, null);
        jContentPane.add(jLabel_name, null);

        jContentPane.add(jLabel_price, null);
        jContentPane.add(jTextField_price, null);

        jContentPane.add(jTextField_id, null);
        jContentPane.add(jTextField_name, null);
        jContentPane.add(jButton_stu_add, null);
        jContentPane.add(jButton_cancel, null);


        this.setContentPane(jContentPane);
        jButton_stu_add.addActionListener(new btListener(Integer.valueOf(this.id)));
        jButton_cancel.addActionListener(new btListener(Integer.valueOf(this.id)));
    }

    private void gettsButtonGroup() {// 按钮组
        if (buttonts == null) {
            buttonts = new ButtonGroup();
            buttonts.add(jRadioButton_Boy);
            buttonts.add(jRadioButton_Girl);
        }
    }

    /**
     * 接受参数，修改信息
     */
    private void modifyMerchant(boolean isModify, String id) {
        DishesModel dishesModel = dishesDao.getDishesModelById(Integer.valueOf(id));
        if (Objects.isNull(dishesModel)) {
            return;
        }

        jTextField_id.setText(dishesModel.getDishesNo());
        jTextField_id.setEditable(false);

        jTextField_name.setText(dishesModel.getDishesName());

        if (Objects.nonNull(dishesModel.getPrice())) {
            jTextField_price.setText(dishesModel.getPrice().toString());
        }


        this.setTitle("Change");
    }


    private class btListener implements ActionListener {

        private Integer id;

        public btListener(Integer id) {
            this.id = id;
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton_cancel) {
                dispose();

            } else if (e.getSource() == jButton_stu_add) {
                String id = jTextField_id.getText();
                String name = jTextField_name.getText();
                String price = jTextField_price.getText();

                if (VerifyUtils.isEmpty(id)) {
                    JOptionPane.showMessageDialog(null, "The ID cannot be empty");
                    return;
                }
                if (VerifyUtils.isEmpty(name)) {
                    JOptionPane.showMessageDialog(null, "The name cannot be empty");
                    return;
                }
                if (VerifyUtils.isEmpty(price)) {
                    JOptionPane.showMessageDialog(null, "The price cannot be empty");
                    return;
                }

                if (!isModify) {
                    DishesModel dishesModel = dishesDao.getDishesModelByNo(id);
                    if (Objects.nonNull(dishesModel)) {
                        JOptionPane.showMessageDialog(null, "please don't add it again.!");
                        return;
                    }

                    if (dishesDao.insetDishes(new DishesModel(id, name, Double.valueOf(price)))) {
                        JOptionPane.showMessageDialog(null, "Add successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Add Failure");
                    }
                } else if (isModify) {
                    if (dishesDao.updateDishesModel(new DishesModel(this.id, name, Double.valueOf(price)))) {
                        JOptionPane.showMessageDialog(null, " Update successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Update Failure");
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "wrong！");
                    dispose();
                }
            }
        }
    }

    public static void main(String[] args) {
        DishesAdd merchantAdd = new DishesAdd(true, "1");
        merchantAdd.setVisible(true);
    }
}