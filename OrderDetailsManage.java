package Quickbites;

import com.assignment.common.Constant;
import com.assignment.dao.MerchantDao;
import com.assignment.dao.OrderDao;
import com.assignment.dao.OrderDetailDao;
import com.assignment.dao.ShopCarModelDao;
import com.assignment.model.DishesModel;
import com.assignment.model.MerchantModel;
import com.assignment.model.OrderDetailModel;
import com.assignment.model.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//Order management
public class OrderDetailsManage extends JDialog {


    private Integer orderId;


    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JToolBar jJToolBarBar = null;

    private JLabel jLabel_orderNo = null;
    private JTextField jTextField_orderNo = null;


    //    private JButton jButton_student_add = null;
    private JLabel jLabel_empty = null;
    private JButton jButton_student_query = null;




//    //View changes
//    private JButton jButton_shopcar_edit = null;

    private JScrollPane jScrollPane = null;
    private JTable jTable = null;

    private JLabel jLabel_Row = null;
    private int counts = 0;// Get the number of record sets to be queried

    //shopping cart
    private ShopCarModelDao shopCarModelDao = new ShopCarModelDao();


    private OrderDao orderDao = new OrderDao();
    private OrderDetailDao orderDetailDao = new OrderDetailDao();
    private List<OrderDetailModel> orderDetailModels = null;

    private List<MerchantModel> merchantModels = null;

    private MerchantDao merchantDao = new MerchantDao();


    DefaultTableModel model = new DefaultTableModel();


    public OrderDetailsManage(Integer orderId) {
        super();
        this.orderId = orderId;
        initialize();

        initData();

//        // Get the column model of the first column
//        TableColumnModel columnModel = jTable.getColumnModel();
//        // Get the TableColumn object of the first column
//        TableColumn firstColumn = columnModel.getColumn(0);
//        // Remove first column from JTable
//        columnModel.removeColumn(firstColumn);
    }




    private void initialize() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(750, 300);
        this.setTitle("Order Details");
        this.setLocationRelativeTo(null);
        //this.setModal(true);

//        jButton_student_add = new JButton();
//        jButton_student_add.setText("添加");

        jLabel_orderNo = new JLabel("orderID: ");
        jTextField_orderNo = new JTextField();





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


        jJToolBarBar.add(Box.createHorizontalStrut(10)); // Add a horizontal gap



        jJToolBarBar.add(Box.createHorizontalStrut(10)); // Add a horizontal gap



        jJToolBarBar.add(Box.createHorizontalStrut(10)); // Add a horizontal gap

        jJToolBarBar.add(jLabel_orderNo);
        jJToolBarBar.add(jTextField_orderNo);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); // Add a horizontal gap

        jJToolBarBar.add(jButton_student_query);

        jJToolBarBar.add(Box.createHorizontalStrut(5)); // Add a horizontal gap


//        jJToolBarBar.add(jLabel_empty);

        jLabel_Row = new JLabel();
        jJToolBarBar.add(jLabel_Row);

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTable);
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.setBorder(BorderFactory.createTitledBorder("Order Information"));
        jContentPane.add(jScrollPane, BorderLayout.CENTER);
//        jContentPane.add(jJToolBarBar, BorderLayout.NORTH);
        this.setContentPane(jContentPane);

//        btnListener btl = new btnListener();
////        jButton_student_add.addActionListener(btl);
//
//        jButton_student_query.addActionListener(btl);
//
//        jComboBox_merchant.addActionListener(btl);
    }

    /**
     * @Initialization interface

     */
    public void initData() {
        String heads[] = {"ID", "Name", "Price", "Quantities", "Total amount"};
        model = new DefaultTableModel(null, heads);

        //Get the current user's shopping cart list
        orderDetailModels = orderDetailDao.getOrderDetailModelsByOrderId(orderId);
        flashData();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void flashData() {
        counts = orderDetailModels.size();
        model.setRowCount(orderDetailModels.size());// Set the number of rows

        int index = 1;
        for (int i = 0; i < counts; i++) {
            OrderDetailModel orderDetailModel = orderDetailModels.get(i);
            //ID
            model.setValueAt(index, i, 0);


            DishesModel dishesModel = orderDetailModel.getDishesModel();
            if (Objects.nonNull(dishesModel)) {
                model.setValueAt(dishesModel.getDishesName(), i, 1);
            } else {
                model.setValueAt(null, i, 1);
            }

            model.setValueAt(orderDetailModel.getPrice(), i, 2);
            model.setValueAt(orderDetailModel.getCount(), i, 3);
            model.setValueAt(orderDetailModel.getTotalPrice(), i, 4);


            index++;
        }
        jLabel_Row.setText("Number:" + counts + "");

        jTable.setModel(model);
        jTable.setRowHeight(22);
        jTable.setAutoCreateRowSorter(true);//Set sorter for JTable


    }


    public static void main(String args[]) {
        UserInfo.setUserModel(new UserModel(1, "1", "1", "1", "1", 1, 1, "1", new Date()));
        UserInfo.setRoleType(Constant.ROLE_USER);

        OrderDetailsManage sm = new OrderDetailsManage(4);
        sm.setVisible(true);
    }

}
