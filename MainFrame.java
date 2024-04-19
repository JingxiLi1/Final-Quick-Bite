/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quickbites;

/**
 *
 * @author Zoher
 */
import Quickbites.Constant;

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

    //Restaruant Management
    private JMenuItem jMenuItem_merchant_manage = null;

    //Restaruant Management
    private JMenuItem jMenuItem_dishes_manage = null;

    //Purchase 
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
        this.setSize(800, 544);// size for the Panel
        this.setTitle("Quick bites");
        imgURL = this.getClass().getResource("/com/assignment/images/icon.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(imgURL));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter()// system shutdown event
        {
            public void windowClosing(WindowEvent e) {
                SystemTrayInitial();//Initialize tray icon
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

        jMenu_start.addSeparator();// Dividing line
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
//		jLabel.setIcon(new ImageIcon(imgURL));


        // Create from image URLImageIcon
        ImageIcon icon = new ImageIcon(imgURL);

// Get original image
        Image image = icon.getImage();

// Resizw Image
        int newWidth = 800; // new width
        int newHeight = 544; // new width
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

// Create a new ImageIcon and set it as the icon of the JLabel
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
     * @Initialize tray
     */
    private void SystemTrayInitial() { // tray
        if (!SystemTray.isSupported()) // Determine whether the current system supports system bars
        {
            return;
        }
        try {
            sysTray.add(trayicon);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        setVisible(false);
        trayicon.displayMessage("Food Delivery System", "", MessageType.INFO);// The message dialog displayed when the form tray
        trayicon.addActionListener(new ActionListener()// Show form when icon is clicked
        {
            public void actionPerformed(ActionEvent e) {
                sysTray.remove(trayicon);
                setVisible(true);
            }
        });
    }

    /**
     * @return
     * @Initialize tray right click
     */
    private PopupMenu createMenu() { // How to create a system bar menu
        PopupMenu menu = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() { // System bar exit event
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        MenuItem openItem = new MenuItem("Open Mian Window");
        openItem.addActionListener(new ActionListener() {// System bar open menu item event
            public void actionPerformed(ActionEvent e) {
                if (!isVisible()) {
                    setVisible(true);
                    sysTray.remove(trayicon);
                }
            }
        });

        MenuItem viewItem = new MenuItem("");
        viewItem.addActionListener(new ActionListener() {// System bar open menu item event
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
                Login login = new Login();// Call the parameterless constructor to avoid repeated music playback
                login.setVisible(true);
            } else if (e.getSource() == jMenuItem_exit) {
                System.exit(0);
            } else if (e.getSource() == jMenuItem_merchant_manage) {
                //Merchant management
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
                    //Merchant order
                    MerchantOrderManage sm = new MerchantOrderManage();
                    sm.setVisible(true);
                } else if (UserInfo.getRoleType().equals(Constant.ROLE_USER)) {
                    //User order
                    UserOrderManage sm = new UserOrderManage();
                    sm.setVisible(true);
                } else if (UserInfo.getRoleType().equals(Constant.ROLE_TAKE_OUT_USER)) {
                    //Take user order
                    TakeUserOrderManage sm = new TakeUserOrderManage();
                    sm.setVisible(true);
                }
            } else if (e.getSource() == jMenuItem_history_order) {
                //User History
                UserHistoryOrderManage sm = new UserHistoryOrderManage();
                sm.setVisible(true);
            } else if (e.getSource() == jMenuItem_buy) {
                //Purchase Dishes
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

