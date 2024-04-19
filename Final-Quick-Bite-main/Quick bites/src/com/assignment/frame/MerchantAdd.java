package com.assignment.frame;

import com.assignment.dao.MerchantDao;
import com.assignment.model.MerchantModel;
import com.assignment.util.VerifyUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MerchantAdd extends JDialog {
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel jLabel_number = null;
    private JLabel jLabel_name = null;

    private JLabel jLabel_merchantAdress = null;
    private JTextField jTextField_merchantAdress = null;


    private JLabel jLabel_phone = null;
    private JTextField jTextField_phone = null;

    private JLabel jLabel_contactPerson = null;
    private JTextField jTextField_contactPerson = null;


    private MerchantDao merchantDao = new MerchantDao();


    private JTextField jTextField_id = null;
    private JTextField jTextField_name = null;
    private JButton jButton_stu_add = null;
    private JButton jButton_cancel = null;

    private JRadioButton jRadioButton_Boy = null;
    private JRadioButton jRadioButton_Girl = null;
    private ButtonGroup buttonts = null;  //  @jve:decl-index=0:
    private String stu_sex = "Male";  //  @jve:decl-index=0:

    private boolean isModify = false;//是添加还是修改？

    private String id;

    
    public MerchantAdd(boolean isModify, String id) {

        super();
        this.id = id;

        initialize();
        this.isModify = isModify;


        if (isModify)
            modifyMerchant(isModify, id);
    }

    private void initialize() {
        this.setSize(300, 340);
        this.setModal(true);
        this.setTitle("Add Restaurant");
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("../images/icon.png")));


        jLabel_number = new JLabel();
        jLabel_number.setBounds(new Rectangle(26, 10, 71, 28));
        jLabel_number.setText("ID：");

        jLabel_name = new JLabel();
        jLabel_name.setBounds(new Rectangle(26, 50, 71, 22));
        jLabel_name.setText("Name：");

        jLabel_merchantAdress = new JLabel();
        jLabel_merchantAdress.setBounds(new Rectangle(26, 90, 71, 28));
        jLabel_merchantAdress.setText("Address：");

        jLabel_contactPerson = new JLabel();
        jLabel_contactPerson.setBounds(new Rectangle(26, 130, 71, 28));
        jLabel_contactPerson.setText("Manager：");

        jLabel_phone = new JLabel();
        jLabel_phone.setBounds(new Rectangle(26, 170, 71, 28));
        jLabel_phone.setText("Phone：");


        jTextField_id = new JTextField();
        jTextField_id.setBounds(new Rectangle(112, 10, 125, 24));

        jTextField_name = new JTextField();
        jTextField_name.setBounds(new Rectangle(112, 50, 125, 24));


        jTextField_merchantAdress = new JTextField();
        jTextField_merchantAdress.setBounds(new Rectangle(112, 90, 125, 24));


        jTextField_contactPerson = new JTextField();
        jTextField_contactPerson.setBounds(new Rectangle(112, 130, 125, 24));


        jTextField_phone = new JTextField();
        jTextField_phone.setBounds(new Rectangle(112, 170, 125, 24));


        jButton_stu_add = new JButton();
        jButton_stu_add.setBounds(new Rectangle(40, 220, 78, 26));
        jButton_stu_add.setText("Confirm");

        jButton_cancel = new JButton();
        jButton_cancel.setBounds(new Rectangle(156, 220, 78, 26));
        jButton_cancel.setText("Cancel");


        gettsButtonGroup();// 加入按钮组

        jContentPane = new JPanel();
        jContentPane.setLayout(null);
        jContentPane.add(jLabel_number, null);
        jContentPane.add(jLabel_name, null);

        jContentPane.add(jLabel_merchantAdress, null);
        jContentPane.add(jTextField_merchantAdress, null);

        jContentPane.add(jTextField_id, null);
        jContentPane.add(jTextField_name, null);
        jContentPane.add(jButton_stu_add, null);
        jContentPane.add(jButton_cancel, null);


        jContentPane.add(jLabel_contactPerson, null);
        jContentPane.add(jTextField_contactPerson, null);

        jContentPane.add(jLabel_phone, null);
        jContentPane.add(jTextField_phone, null);


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
    
     */
    private void modifyMerchant(boolean isModify, String id) {
        MerchantModel merchantModel = merchantDao.getMerchantById(Integer.parseInt(id));
        if (Objects.isNull(merchantModel)) {
            return;
        }

        jTextField_id.setText(merchantModel.getMerchantNo());
        jTextField_id.setEditable(false);

        jTextField_name.setText(merchantModel.getMerchantName());
        jTextField_phone.setText(merchantModel.getPhone());
        jTextField_contactPerson.setText(merchantModel.getContactPerson());
        jTextField_merchantAdress.setText(merchantModel.getMerchantAdress());

        this.setTitle("Change Restaurant");
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
                String no = jTextField_id.getText();
                String name = jTextField_name.getText();
                String phone = jTextField_phone.getText();
                String contactPerson = jTextField_contactPerson.getText();
                String merchantAdress = jTextField_merchantAdress.getText();

                if (VerifyUtils.isEmpty(no)) {
                    JOptionPane.showMessageDialog(null, "The ID cannot be empty");
                    return;
                }
                if (VerifyUtils.isEmpty(name)) {
                    JOptionPane.showMessageDialog(null, "The Name cannot be empty");
                    return;
                }
                if (VerifyUtils.isEmpty(phone)) {
                    JOptionPane.showMessageDialog(null, "The Phone cannot be empty");
                    return;
                }
                if (VerifyUtils.isEmpty(contactPerson)) {
                    JOptionPane.showMessageDialog(null, "The Manger cannot be empty");
                    return;
                }
                if (VerifyUtils.isEmpty(merchantAdress)) {
                    JOptionPane.showMessageDialog(null, "The Address cannot be empty");
                    return;
                }

                if (!isModify) {
                    MerchantModel merchantModel = merchantDao.getMerchantByNo(no);
                    if (Objects.nonNull(merchantModel)) {
                        JOptionPane.showMessageDialog(null, "please don't add it again.!");
                        return;
                    }

                    if (merchantDao.insetMerchant(new MerchantModel(this.id, no, name, merchantAdress, phone, contactPerson))) {
                        JOptionPane.showMessageDialog(null, "Add successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Add Failure");
                    }
                } else if (isModify) {
                    if (merchantDao.updateMerchant(new MerchantModel(this.id, no, name, merchantAdress, phone, contactPerson))) {
                        JOptionPane.showMessageDialog(null, "Update successfully");
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
        MerchantAdd merchantAdd = new MerchantAdd(true, "1");
        merchantAdd.setVisible(true);
    }
}