package com.assignment.frame;

import com.assignment.common.Constant;
import com.assignment.dao.MerchantDao;
import com.assignment.dao.SysUserDao;
import com.assignment.dao.TakeOutUserDao;
import com.assignment.dao.UserDao;
import com.assignment.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private BackgroundPanel jContentPane = null;

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        // Some code to initialize the background image.
        // Here, we use the constructor to load the image. This
        // can vary depending on the use case of the panel.
        public BackgroundPanel(String fileName) throws IOException {
            backgroundImage = ImageIO.read(new File(fileName));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image.
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }

    private JButton jButton21 = null;
    private JButton jButton22 = null;
    private JTextField jTextField = null;
    private JPasswordField jPasswordField = null;
    private JLabel jLabel = null;

    static String currentUserId;

    public static String userName = null;
    public static String password = null;
    static boolean RELOAD = true;
    static int login_user_type;
    private JLabel jLabel_User = null;
    private JLabel jLabel_userName = null;
    private JLabel jLabel_password = null;
    private JLabel jLabel_privilege = null;
    private BtnListener btl = null;
    private JComboBox jComboBox = null;
    private JLabel jLabel_tips = null;
    private JLabel jLabel_title = null;
    private JLabel jLabel_welcome = null;

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static int getLogin_user_type() {
        return login_user_type;
    }

    private void initialize() {
        try {
            jContentPane = new BackgroundPanel("src/com/assignment/images/background.jpg"); // Replace with your actual image path
        } catch (IOException e) {
            System.err.println("Can't read input file!");
            e.printStackTrace();
        }
        jContentPane.setLayout(null);
        jContentPane.setBackground(new Color(255, 255, 255));
        jLabel_welcome = new JLabel();
        jLabel_welcome.setBounds(new Rectangle(100, 50, 515, 80));
        jLabel_welcome.setText("Welcome");
        Font font6 = new Font("黑体", Font.PLAIN, 60);
        jLabel_welcome.setFont(font6);

        jLabel_title = new JLabel();
        jLabel_title.setBounds(new Rectangle(95, 130, 415, 36));
        jLabel_title.setText("Quick bites");
        Font font5 = new Font("黑体", Font.PLAIN, 40);
        jLabel_title.setFont(font5);

        jLabel_tips = new JLabel();
        jLabel_tips.setBounds(new Rectangle(40, 300, 415, 36));
       
        Font font3 = new Font("黑体", Font.PLAIN, 18);
        jLabel_tips.setFont(font3);

        this.setSize(600, 700);
        this.setTitle("Welcome");
        this.setResizable(false);

        try {
            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// 使用windows外观
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jButton21 = new JButton();
        jButton21.setBounds(new Rectangle(60, 400, 117, 39));


        jButton21.setText("Log in");
        getRootPane().setDefaultButton(jButton21);

        jButton22 = new JButton();
        jButton22.setBounds(new Rectangle(240, 400, 117, 39));
        jButton22.setText("Out");

        jTextField = new JTextField(30);
        jTextField.setBounds(new Rectangle(280, 220, 186, 35));

        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(new Rectangle(280, 260, 186, 35));

        jLabel = new JLabel();
        jLabel.setText("");
        jLabel.setBounds(new Rectangle(0, -2, 436, 213));

        jLabel_password = new JLabel();
        jLabel_password.setBounds(new Rectangle(90, 260, 180, 42));
        jLabel_password.setText("Password：");
     
        Font font = new Font("黑体", Font.PLAIN, 24); // 字体名称，样式，大小
        jLabel_password.setFont(font);

        jLabel_userName = new JLabel();
        jLabel_userName.setBounds(new Rectangle(90, 220, 106, 29));
        jLabel_userName.setText("Name：");
        Font font2 = new Font("黑体", Font.PLAIN, 24);
        jLabel_userName.setFont(font2);
        jLabel_User = new JLabel();
        jLabel_User.setBounds(new Rectangle(15, 220, 412, 147));


        jLabel_privilege = new JLabel();
        jLabel_privilege.setBounds(new Rectangle(80, 340, 406, 28));
        jLabel_privilege.setText("Usertype：");
        Font font4 = new Font("黑体", Font.PLAIN, 20);
        jLabel_privilege.setFont(font4);

        jComboBox = new JComboBox();
        jComboBox.setBounds(new Rectangle(180, 340, 184, 34));
        jComboBox.addItem("Admin");
        jComboBox.addItem("Restaurant");
        jComboBox.addItem("Customer");
        jComboBox.addItem("Deliver");

        jContentPane.setLayout(null);
        jContentPane.setBackground(new Color(255, 255, 255));
        jContentPane.add(jLabel_userName, null);
        jContentPane.add(jLabel_password, null);
        jContentPane.add(jButton21, null);
        jContentPane.add(jButton22, null);
        jContentPane.add(jTextField, null);
        jContentPane.add(jPasswordField, null);
        jContentPane.add(jLabel, null);
        jContentPane.add(jLabel_User, null);

        jContentPane.add(jComboBox, null);
        jContentPane.add(jLabel_privilege, null);
        jContentPane.add(jLabel_tips, null);
        jContentPane.add(jLabel_title, null);
        jContentPane.add(jLabel_welcome, null);
        setContentPane(jContentPane);


        btl = new BtnListener();
        jButton21.addActionListener(btl);
        jButton22.addActionListener(btl);
        this.setLocationRelativeTo(null);


    }

    /**
    
     */
    public class BtnListener implements ActionListener {
        public Boolean login(Integer login_user_type, String userName, String password) {
            if (login_user_type == 0) {
                SysUserDao sysUserDao = new SysUserDao();
           
                SysUserModel sysUserModel = sysUserDao.getSysUserByNameAndPassword(userName, password);
                if (Objects.nonNull(sysUserModel)) {
                    UserInfo.setSysUserModel(sysUserModel);
                    UserInfo.setRoleType(Constant.ROLE_ADMIN);
                    return true;
                }
            } else if (login_user_type == 1) {
            
                MerchantDao merchantDao = new MerchantDao();
                MerchantModel merchantModel = merchantDao.getMerchantByNameAndPassword(userName, password);
                if (Objects.nonNull(merchantModel)) {
                    UserInfo.setMerchantModel(merchantModel);
                    UserInfo.setRoleType(Constant.ROLE_MERCHANT);
                    return true;
                }
            } else if (login_user_type == 2) {
          
                UserDao userDao = new UserDao();
                UserModel userModel = userDao.getUserByNameAndPassword(userName, password);
                if (Objects.nonNull(userModel)) {
                    UserInfo.setUserModel(userModel);
                    UserInfo.setRoleType(Constant.ROLE_USER);
                    return true;
                }
            } else if (login_user_type == 3) {
                //商户登录
                TakeOutUserDao takeOutUserDao = new TakeOutUserDao();
                TakeOutUserModel takeOutUserModel = takeOutUserDao.getUserByNameAndPassword(userName, password);
                if (Objects.nonNull(takeOutUserModel)) {
                    UserInfo.setTakeOutUserModel(takeOutUserModel);
                    UserInfo.setRoleType(Constant.ROLE_TAKE_OUT_USER);
                    return true;
                }
            }
            return false;
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButton21) {
                userName = jTextField.getText().trim();
                password = new String(jPasswordField.getPassword()).trim();//char to String

                login_user_type = jComboBox.getSelectedIndex();

                if ("".equals(userName)) {
                    JOptionPane.showMessageDialog(null, "You have to put usename");
                    return;
                }
                if ("".equals(password)) {
                    JOptionPane.showMessageDialog(null, "You have to put possword");
                    return;
                }

              
                if (login(login_user_type, userName, password)) {
                    dispose();
                    MainFrame mf = new MainFrame();
                    mf.setVisible(true);
                    JOptionPane.showMessageDialog(null, "Welcome " + userName + "Log in！", " System", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Login Failure");
                    return;
                }
            } else if (e.getSource() == jButton22) {
                System.exit(0);
            }
        }
    }


    /**
     * @param args
 
     */
    public static void main(String[] args) {
        Login login = new Login(RELOAD);
        login.setVisible(true);
    }

    public Login() {
        super();
        initialize();
    }

    public Login(boolean reload) {
        super();
        initialize();
       
    }
}  
