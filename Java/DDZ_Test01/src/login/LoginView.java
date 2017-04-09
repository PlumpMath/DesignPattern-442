package login;

import DBManager.DBConnection;
import com.mysql.jdbc.Connection;
import view.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DrownFish on 2017/4/7.
 */
public class LoginView extends JFrame {

    JFrame jFrame;
    JLabel jLabelName;
    JLabel jLabelPwd;
    JTextField jTextFieldName;
    JPasswordField jPasswordFieldPwd;

    public static void main(String args[]) throws IOException, InterruptedException {
        LoginView loginView = new LoginView();

    }
    public LoginView()
    {
        jFrame = new JFrame("登陆界面");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(((int)dimension.getWidth() - 200) / 2, ((int)dimension.getHeight() - 300) / 2, 200, 150);
        jFrame.setResizable(false);
        jFrame.setLayout(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jLabelName = new JLabel("姓名");
        jLabelName.setBounds(10, 10, 100, 30);
        jFrame.add(jLabelName);

        jLabelPwd = new JLabel("密码");
        jLabelPwd.setBounds(10, 40, 100, 30);
        jFrame.add(jLabelPwd);

        jTextFieldName = new JTextField();
        jTextFieldName.setBounds(50, 15, 130, 20);
        jFrame.add(jTextFieldName);

        jPasswordFieldPwd = new JPasswordField();
        jPasswordFieldPwd.setBounds(50, 45, 130, 20);
        jFrame.add(jPasswordFieldPwd);

        JButton button = new JButton("登录");
        button.setBounds(10, 75, 170, 40);
        jFrame.add(button);
        jFrame.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DBConnection dbConnection = new DBConnection();
                Connection connection = dbConnection.getConn();
                try {
                    String name = jTextFieldName.getText();
                    String password = jPasswordFieldPwd.getText();

                    Statement statement = connection.createStatement();
                    String sql ="SELECT password,type FROM user WHERE name = '"+name+"'";
                    System.out.println(sql);
                    ResultSet resultSet = statement.executeQuery(sql);
                    if(resultSet!=null){
                        resultSet.last();
                        if(resultSet.getRow() == 0){
                            JOptionPane.showMessageDialog(null,"此用户不存在，请重新输入",
                                    "提示",JOptionPane.ERROR_MESSAGE);
                            jTextFieldName.setText("");
                            jPasswordFieldPwd.setText("");
                        }else{
                            resultSet.first();
                            String passwordRight = resultSet.getString("password");
                            String type = resultSet.getString("type");
                            System.out.println(type);
                            if(password.equals(passwordRight)){
                                //密码正确
                                if(type.equals("1")){
                                    //用户登录，进行游戏
                                    System.out.println("right player");

                                    User normalUser = new NormalUser();
                                    normalUser.setName(name);
                                    normalUser.setPassword(password);

                                    Home me = new Home();
                                    me.setMain(me);
                                    me.setPlayerName(name);
                                    me.setUser(normalUser);
                                }else if(type.equals("2")){
                                    //管理员登录，查看用户的成绩
                                    System.out.println("right admin");

                                    User adminUser = new AdminUser();
                                    adminUser.setName(name);
                                    adminUser.setPassword(password);

                                    Home me = new Home();
                                    me.setMain(me);
                                    me.setPlayerName(name);
                                    me.setUser(adminUser);
                                }
                            }else{
                                //密码错误
                                JOptionPane.showMessageDialog(null,"密码错误",
                                        "提示",JOptionPane.ERROR_MESSAGE);
                                jTextFieldName.setText("");
                                jPasswordFieldPwd.setText("");

                            }
                        }
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


    }
}
