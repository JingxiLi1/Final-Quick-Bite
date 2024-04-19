package com.assignment.frame;

import com.assignment.common.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;


public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JMenu jMenu_start = null;
    private JMenuBar jJMenuBar = null;
    private JMenu jMenu_backstage = null;
    private JMenu jMenu_order = null;
    private JMenuItem jMenuItem_relogin = null;
    private JMenuItem jMenuItem_exit = null;

    //商家管理
    private JMenuItem jMenuItem_merchant_manage = null;

    //商家管理
    private JMenuItem jMenuItem_dishes_manage = null;

    //购买商品
    private JMenuItem jMenuItem_buy = null;

    private JMenuItem jMenuItem_sys_info = null;

    private JMenuItem jMenuItem_order = null;

    private JMenuItem jMenuItem_history_order = null;

    private JLabel jLabel = null;
    private URL imgURL = null;
    private SystemTray sysTray = SystemTray.getSystemTray();
    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/assignment/images/icon.png"));
    private TrayIcon trayicon = new TrayIcon(image, "Food delivery", createMenu());

    public MainFrame() {
        super();
        initialize();
        initPrivilege();
    }

    private void initialize() {
        this.setSize(800, 544);
        this.setTitle("Quick bites");
        imgURL = this.getClass().getResource("/com/assignment/images/icon.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(imgURL));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter()// 系统关闭事件
        {
            public void windowClosing(WindowEvent e) {
                SystemTrayInitial();//初始化托盘图标
            }
        });

        jMenuItem_relogin = new JMenuItem();
        jMenuItem_relogin.setText("sign in again");


        jMenuItem_exit = new JMenuItem();
        jMenuItem_exit.setText("OUT");

        jMenuItem_merchant_manage = new JMenuItem();
        jMenuItem_merchant_manage.setText("Restaurant management");

        jMenuItem_dishes_manage = new JMenuItem();
        jMenuItem_dishes_manage.setText("Menu Management");

        jMenuItem_order = new JMenuItem();
        jMenuItem_order.setText("Order Information");

        jMenuItem_history_order = new JMenuItem();
        jMenuItem_history_order.setText("Order History");

        jMenuItem_sys_info = new JMenuItem();
        jMenuItem_sys_info.setText("System Description");

        jMenuItem_buy = new JMenuItem();
        jMenuItem_buy.setText("Purchase");

        jMenu_start = new JMenu();
        jMenu_start.setText("Start");
        jMenu_start.add(jMenuItem_relogin);

        jMenu_start.addSeparator();// 分割线
        jMenu_start.add(jMenuItem_exit);
        jMenu_backstage = new JMenu();
        jMenu_backstage.setText("System Management");

        jMenu_order = new JMenu();
        jMenu_order.setText("Order Management");
        jMenu_order.add(jMenuItem_order);

        jJMenuBar = new JMenuBar();
        jJMenuBar.setPreferredSize(new Dimension(10, 25));
        jJMenuBar.add(jMenu_start);

        setJMenuBar(jJMenuBar);


        if (Login.login_user_type == 0) {
            jMenu_backstage.add(jMenuItem_merchant_manage);
            jMenu_backstage.add(jMenuItem_dishes_manage);
            jJMenuBar.add(jMenu_backstage);
            jJMenuBar.add(jMenu_order);
        } else if (Login.login_user_type == 1) {
            jMenu_backstage.add(jMenuItem_merchant_manage);
            jMenu_backstage.add(jMenuItem_dishes_manage);
            jJMenuBar.add(jMenu_backstage);
            jJMenuBar.add(jMenu_order);
        } else if (Login.login_user_type == 2) {
            jMenu_order.add(jMenuItem_buy);
            jJMenuBar.add(jMenu_order);
        } else if (Login.login_user_type == 3) {
            jMenu_order.add(jMenuItem_history_order);
            jJMenuBar.add(jMenu_order);
        }


        jLabel = new JLabel();
        jLabel.setText("");
        jLabel.setBounds(new Rectangle(1, -2, 800, 544));


        imgURL = this.getClass().getResource("/com/assignment/images/main.png");



        // 从图像URL创建ImageIcon
        ImageIcon icon = new ImageIcon(imgURL);


        Image image = icon.getImage();

// size
        int newWidth = 800; 
        int newHeight = 544; 
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

// 创建新的ImageIcon并设置为JLabel的图标
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        jLabel.setIcon(scaledIcon);


        jContentPane = new JPanel();
        jContentPane.setLayout(null);
        jContentPane.add(jLabel, null);
        setContentPane(jContentPane);

        btnListener btn = new btnListener();
        jMenuItem_relogin.addActionListener(btn);


        jMenuItem_exit.addActionListener(btn);

        jMenuItem_merchant_manage.addActionListener(btn);
        jMenuItem_dishes_manage.addActionListener(btn);
        jMenuItem_order.addActionListener(btn);
        jMenuItem_sys_info.addActionListener(btn);
        jMenuItem_history_order.addActionListener(btn);
        jMenuItem_buy.addActionListener(btn);
    }

    /**
     
     */
    private void SystemTrayInitial() { 
        if (!SystemTray.isSupported()) 
        {
            return;
        }
        try {
            sysTray.add(trayicon);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        setVisible(false);
        trayicon.displayMessage("Food Delivery System", "", MessageType.INFO);
        trayicon.addActionListener(new ActionListener()// 击图标时显示窗体
        {
            public void actionPerformed(ActionEvent e) {
                sysTray.remove(trayicon);
                setVisible(true);
            }
        });
    }

    /**
     * @return
     * @初始化托盘右键
     */
    private PopupMenu createMenu() { // 创建系统栏菜单的方法
        PopupMenu menu = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() { // 系统栏退出事件
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        MenuItem openItem = new MenuItem("Open Mian Window");
        openItem.addActionListener(new ActionListener() {// 系统栏打开菜单项事件
            public void actionPerformed(ActionEvent e) {
                if (!isVisible()) {
                    setVisible(true);
                    sysTray.remove(trayicon);
                }
            }
        });

        MenuItem viewItem = new MenuItem("");
        viewItem.addActionListener(new ActionListener() {// 系统栏打开菜单项事件
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });


        menu.add(openItem);
        menu.add(viewItem);
        menu.addSeparator();
        menu.add(exitItem);
        return menu;
    }

    public void initPrivilege() {
//        if (Login.login_user_type == 0) {
//
//        } else if (Login.login_user_type == 1) {
//            jMenuItem_student_manage.setEnabled(false);
//            jMenuItem_merchant_manage.setEnabled(false);
//            jMenuItem_dishes_rating_manage.setEnabled(false);
//        } else if (Login.login_user_type == 2) {
//            //学生
//            jMenuItem_merchant_manage.setEnabled(false);
//            jMenuItem_dishes_manage.setEnabled(false);
//            jMenuItem_student_manage.setEnabled(false);
//        }
    }

    public class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jMenuItem_relogin) {
                dispose();
                Login login = new Login();
                login.setVisible(true);
            } else if (e.getSource() == jMenuItem_exit) {
                System.exit(0);
            } else if (e.getSource() == jMenuItem_merchant_manage) {
                //商户管理
                MerchantManage sm = new MerchantManage();
                sm.setVisible(true);
            } else if (e.getSource() == jMenuItem_dishes_manage) {
                DishesManage sm = new DishesManage();
                sm.setVisible(true);
            } else if (e.getSource() == jMenuItem_order) {
                if (UserInfo.getRoleType().equals(Constant.ROLE_ADMIN)) {
                    AdminOrderManage sm = new AdminOrderManage();
                    sm.setVisible(true);
                } else if (UserInfo.getRoleType().equals(Constant.ROLE_MERCHANT)) {
                    //商家订单
                    MerchantOrderManage sm = new MerchantOrderManage();
                    sm.setVisible(true);
                } else if (UserInfo.getRoleType().equals(Constant.ROLE_USER)) {
                    //用户订单
                    UserOrderManage sm = new UserOrderManage();
                    sm.setVisible(true);
                } else if (UserInfo.getRoleType().equals(Constant.ROLE_TAKE_OUT_USER)) {
                    //外卖人员订单
                    TakeUserOrderManage sm = new TakeUserOrderManage();
                    sm.setVisible(true);
                }
            } else if (e.getSource() == jMenuItem_history_order) {
                //外卖人员订单
                UserHistoryOrderManage sm = new UserHistoryOrderManage();
                sm.setVisible(true);
            } else if (e.getSource() == jMenuItem_buy) {
                //外卖人员订单
                BuyDishesManage sm = new BuyDishesManage();
                sm.setVisible(true);
            }
        }
    }


    public static void main(String[] args) {
        MainFrame login = new MainFrame();
        login.setVisible(true);
    }

}
