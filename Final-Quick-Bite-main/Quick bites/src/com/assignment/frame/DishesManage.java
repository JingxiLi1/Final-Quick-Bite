package com.assignment.frame;

import com.assignment.common.Constant;
import com.assignment.dao.DishesDao;
import com.assignment.model.DishesModel;
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


public class DishesManage extends JDialog {

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

    private DishesDao dishesDao = new DishesDao();

    private List<DishesModel> dishes;

    DefaultTableModel model = new DefaultTableModel();

    public DishesManage() {
        super();
        initialize();
        initData();
    }

    private void initialize() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(658, 368);
        this.setTitle("Manage Menu");
        this.setLocationRelativeTo(null);
        //this.setModal(true);

        jButton_student_add = new JButton();
        jButton_student_add.setText("Add");
        jButton_student_modify = new JButton();

        jButton_student_modify.setText("Change");
        jButton_student_query = new JButton();

        jButton_student_query.setText("Inquire");
        jButton_student_delete = new JButton();

        jButton_student_delete.setText("Delet");
        jButton_student_flash = new JButton();

        jButton_student_flash.setText("Update");
        jTable = new JTable();
        jTable.setSelectionBackground(new Color(0, 255, 204));
        jTable.setSelectionForeground(new Color(153, 0, 0));
        jTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jLabel_Row = new JLabel();
        jLabel_Row.setSize(25, 30);

        jJToolBarBar = new JToolBar();

        if (!UserInfo.getRoleType().equals(Constant.ROLE_ADMIN)) {
            jJToolBarBar.add(jButton_student_add);
            jJToolBarBar.add(jButton_student_delete);
            jJToolBarBar.add(jButton_student_modify);
        }

//        jJToolBarBar.add(jButton_student_add);
//        jJToolBarBar.add(jButton_student_delete);
//        jJToolBarBar.add(jButton_student_modify);

        jJToolBarBar.add(jButton_student_query);

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


  
    public void initData() {

        if (UserInfo.getRoleType().equals(Constant.ROLE_ADMIN)) {
            String[] heads = {"ID", "Name", "Food ID", "Name", "Price", "Time"};
            model = new DefaultTableModel(null, heads);
        } else {
            String[] heads = {"ID", "Food ID", "Name", "Price", "Time"};
            model = new DefaultTableModel(null, heads);
        }

        dishes = dishesDao.getList();
        flashData();
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void flashData() {
        counts = dishes.size();
        model.setRowCount(dishes.size());// 设置行数
        for (int i = 0; i < counts; i++) {
            if (UserInfo.getRoleType().equals(Constant.ROLE_ADMIN)) {
                model.setValueAt(dishes.get(i).getId(), i, 0);

                MerchantModel merchantModel = dishes.get(i).getMerchantModel();
                if (Objects.nonNull(merchantModel)) {
                    model.setValueAt(merchantModel.getMerchantName(), i, 1);
                } else {
                    model.setValueAt("", i, 1);
                }

                model.setValueAt(dishes.get(i).getDishesNo(), i, 2);
                model.setValueAt(dishes.get(i).getDishesName(), i, 3);
                model.setValueAt(dishes.get(i).getPrice(), i, 4);

                Date createTime = dishes.get(i).getCreateTime();
                if (Objects.nonNull(createTime)) {
                    model.setValueAt(sdf.format(createTime), i, 5);
                } else {
                    model.setValueAt("", i, 5);
                }
            } else {
                model.setValueAt(dishes.get(i).getId(), i, 0);
                model.setValueAt(dishes.get(i).getDishesNo(), i, 1);
                model.setValueAt(dishes.get(i).getDishesName(), i, 2);
                model.setValueAt(dishes.get(i).getPrice(), i, 3);

                Date createTime = dishes.get(i).getCreateTime();

                if (Objects.nonNull(createTime)) {
                    model.setValueAt(sdf.format(createTime), i, 4);
                } else {
                    model.setValueAt("", i, 4);
                }
            }

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
            if (e.getSource() == jButton_student_add) {

                DishesAdd sa = new DishesAdd(false, "1");
                sa.setVisible(true);
                flashData();

            } else if (e.getSource() == jButton_student_modify) {
                if (jTable.getSelectedRow() != -1) {// 选择行数如果非空
                    String id = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
                    DishesAdd sa = new DishesAdd(true, id);
                    sa.setVisible(true);
                    flashData();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the rows!");
                }

            } else if (e.getSource() == jButton_student_delete) {// 支持多行删除
                if (jTable.getSelectedRow() != -1) {
                    String id = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
                    dishesDao.deleteById(Integer.parseInt(id));

                    JOptionPane.showMessageDialog(null, "Deleted successfully！");

                    model.removeRow(jTable.getSelectedRow());
                    //flashData();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the rows！");
                }
            } else if (e.getSource() == jButton_student_query) {
                String name;
                try {
                    name = JOptionPane.showInputDialog("please enter the name：");
                } catch (Exception e2) {
                    // TODO: handle exception
                    return;
                }
                dishes = dishesDao.getDataByName(name);
                flashData();
            } else if (e.getSource() == jButton_student_flash) {
                initData();
            }
        }
    }


    public static void main(String args[]) {
        DishesManage sm = new DishesManage();
        sm.setVisible(true);
    }

}
