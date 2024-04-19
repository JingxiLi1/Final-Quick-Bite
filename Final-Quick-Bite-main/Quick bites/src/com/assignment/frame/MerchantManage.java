package com.assignment.frame;

import com.assignment.common.Constant;
import com.assignment.dao.MerchantDao;
import com.assignment.model.MerchantModel;

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

//商户管理
public class MerchantManage extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JToolBar jJToolBarBar = null;
    private JButton jButton_student_add = null;
    private JButton jButton_student_modify = null;
    private JButton jButton_student_query = null;
    private JButton jButton_student_delete = null;
    private JScrollPane jScrollPane = null;
    private JTable jTable = null;
    private JButton jButton_student_flash = null;
    private JLabel jLabel_Row = null;
    private int counts = 0;


    private MerchantDao merchantDao = new MerchantDao();


    private List<MerchantModel> merchantList;  //  @jve:decl-index=0:


    DefaultTableModel model = new DefaultTableModel();


    public MerchantManage() {
        super();
        initialize();
        initData();





    }

    private void initialize() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(658, 368);
        this.setTitle("Restaurant");
        this.setLocationRelativeTo(null);
        //this.setModal(true);

        jButton_student_add = new JButton();
        jButton_student_add.setText("Add");
        jButton_student_modify = new JButton();

        jButton_student_modify.setText("Change");
        jButton_student_query = new JButton();

        jButton_student_query.setText("Inquire");
        jButton_student_delete = new JButton();

        jButton_student_delete.setText("Delete");
        jButton_student_flash = new JButton();

        jButton_student_flash.setText("Update");
        jTable = new JTable();
        jTable.setSelectionBackground(new Color(0, 255, 204));
        jTable.setSelectionForeground(new Color(153, 0, 0));
        jTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jLabel_Row = new JLabel();
        jLabel_Row.setSize(25, 30);

        jJToolBarBar = new JToolBar();

        if (UserInfo.getRoleType() == Constant.ROLE_ADMIN) {
            jJToolBarBar.add(jButton_student_add);
            jJToolBarBar.add(jButton_student_delete);
            jJToolBarBar.add(jButton_student_modify);
            jJToolBarBar.add(jButton_student_query);
        } else if (UserInfo.getRoleType() == Constant.ROLE_MERCHANT) {
            jJToolBarBar.add(jButton_student_modify);
        }


        jJToolBarBar.add(jButton_student_flash);
        jJToolBarBar.add(jLabel_Row);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.setBorder(BorderFactory.createTitledBorder("Operational integration"));
        jContentPane.add(jScrollPane, BorderLayout.CENTER);
        jContentPane.add(jJToolBarBar, BorderLayout.NORTH);
        this.setContentPane(jContentPane);

        btnListener btl = new btnListener();
        jButton_student_add.addActionListener(btl);
        jButton_student_modify.addActionListener(btl);
        jButton_student_query.addActionListener(btl);
        jButton_student_delete.addActionListener(btl);
        jButton_student_flash.addActionListener(btl);
    }

    /**
     * @初始化界面
     */
    public void initData() {

        String heads[] = {"ID", "Restaurant ID", "name", "Adress", "Customer", "Phone", "Time"};
        model = new DefaultTableModel(null, heads);

        merchantList = merchantDao.listMerchantModel();
        flashData();

    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public void flashData() {
        counts = merchantList.size();
        model.setRowCount(merchantList.size());// 设置行数
        for (int i = 0; i < counts; i++) {
            model.setValueAt(merchantList.get(i).getId(), i, 0);
            model.setValueAt(merchantList.get(i).getMerchantNo(), i, 1);
            model.setValueAt(merchantList.get(i).getMerchantName(), i, 2);
            model.setValueAt(merchantList.get(i).getMerchantAdress(), i, 3);
            model.setValueAt(merchantList.get(i).getContactPerson(), i, 4);
            model.setValueAt(merchantList.get(i).getPhone(), i, 5);
            Date createTime = merchantList.get(i).getCreateTime();

            if (Objects.nonNull(createTime)) {
                model.setValueAt(sdf.format(createTime), i, 6);
            } else {
                model.setValueAt("", i, 6);
            }

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
    }

    
    private class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton_student_add) {
                MerchantAdd sa = new MerchantAdd(false, "1");
                sa.setVisible(true);
                flashData();
            } else if (e.getSource() == jButton_student_modify) {
                if (jTable.getSelectedRow() != -1) {
                    //这里是编号
                    String id = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
                    MerchantAdd sa = new MerchantAdd(true, id);
                    sa.setVisible(true);
                    flashData();
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select!");
                }
            } else if (e.getSource() == jButton_student_delete) {
                if (jTable.getSelectedRow() != -1) {
                    String id = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
                    merchantDao.deleteById(Integer.parseInt(id));

                    JOptionPane.showMessageDialog(null, "Delete Successfully！");

                    model.removeRow(jTable.getSelectedRow());
                    //flashData();
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select！");
                }
            } else if (e.getSource() == jButton_student_query) {

                String name;
                try {
                    name = JOptionPane.showInputDialog("Please enter name：");
                } catch (Exception e2) {
                    // TODO: handle exception
                    return;
                }
                merchantList = merchantDao.getMerchantByName(name);
                flashData();
            } else if (e.getSource() == jButton_student_flash) {
                initData();
            }
        }
    }


    public static void main(String args[]) {
        MerchantManage sm = new MerchantManage();
        sm.setVisible(true);
    }

}
