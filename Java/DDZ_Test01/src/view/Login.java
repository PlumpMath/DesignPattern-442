package view;

import DBManager.DBConnection;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DrownFish on 2017/4/7.
 */
public class Login extends JFrame {

    JFrame jFrame;
    JLabel jLabelName;
    JLabel jLabelPwd;
    JTextField jTextFieldName;
    JPasswordField jPasswordFieldPwd;

    public static void main(String args[]){
        Login login = new Login();
    }
    public Login()
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

        JButton button = new JButton("Login");
        button.setBounds(10, 75, 170, 40);
        jFrame.add(button);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DBConnection dbConnection = new DBConnection();
                Connection connection = dbConnection.getConn();
                try {
                    Statement statement = connection.createStatement();
                    String sql ="SELECT password FROM user WHERE name = '"+jTextFieldName.getText()+"'";

                    System.out.println(sql);
                    ResultSet resultSet = statement.executeQuery(sql);
                    if(resultSet==null){
                        JOptionPane.showMessageDialog(null,"此用户不存在，请重新输入",
                                "提示",JOptionPane.ERROR_MESSAGE);
                        jTextFieldName.setText("");
                        jTextFieldName.setText("");
                    }else {
                        resultSet.next();
                        String password = resultSet.getString("password");
                        if(password.equals(jPasswordFieldPwd.getText())){
                            //密码正确
                            System.out.println("right");
                            //跳转界面 界面关闭，并且跳转到下一个界面

                        }else{
                            //密码错误
                            JOptionPane.showMessageDialog(null,"密码错误",
                                    "提示",JOptionPane.ERROR_MESSAGE);
                            jTextFieldName.setText("");
                            jTextFieldName.setText("");
                        }
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });


    }
}
