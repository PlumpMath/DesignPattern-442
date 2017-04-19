package DesignPattern.visitor;

import DesignPattern.DBManager.DBConnection;
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
        jFrame = new JFrame("��½����");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(((int)dimension.getWidth() - 200) / 2, ((int)dimension.getHeight() - 300) / 2, 200, 150);
        jFrame.setResizable(false);
        jFrame.setLayout(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jLabelName = new JLabel("����");
        jLabelName.setBounds(10, 10, 100, 30);
        jFrame.add(jLabelName);

        jLabelPwd = new JLabel("����");
        jLabelPwd.setBounds(10, 40, 100, 30);
        jFrame.add(jLabelPwd);

        jTextFieldName = new JTextField();
        jTextFieldName.setBounds(50, 15, 130, 20);
        jFrame.add(jTextFieldName);

        jPasswordFieldPwd = new JPasswordField();
        jPasswordFieldPwd.setBounds(50, 45, 130, 20);
        jFrame.add(jPasswordFieldPwd);

        JButton button = new JButton("��¼");
        button.setBounds(10, 75, 170, 40);
        jFrame.add(button);
        jFrame.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection = DBConnection.getConn();
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
                            JOptionPane.showMessageDialog(null,"���û������ڣ�����������",
                                    "��ʾ",JOptionPane.ERROR_MESSAGE);
                            jTextFieldName.setText("");
                            jPasswordFieldPwd.setText("");
                        }else{
                            resultSet.first();
                            String passwordRight = resultSet.getString("password");
                            String type = resultSet.getString("type");
                            System.out.println(type);
                            if(password.equals(passwordRight)){
                                //������ȷ
                                if(type.equals("1")){
                                    //�û���¼��������Ϸ
                                    System.out.println("right player");

                                    User normalUser = new NormalUser();
                                    normalUser.setName(name);
                                    normalUser.setPassword(password);

                                    Home me = new Home();
                                    me.setMain(me);
                                    me.setPlayerName(name);
                                    me.setUser(normalUser);
                                }else if(type.equals("2")){
                                    //����Ա��¼���鿴�û��ĳɼ�
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
                                //�������
                                JOptionPane.showMessageDialog(null,"�������",
                                        "��ʾ",JOptionPane.ERROR_MESSAGE);
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
