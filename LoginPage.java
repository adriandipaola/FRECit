import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoginPage implements ActionListener {
    private JFrame frame = new JFrame();
    private JButton loginButton = new JButton("Login");
    private JButton resetButton = new JButton("Reset");
    private JButton registerButton = new JButton("Register");
    private JTextField userIDfield = new JTextField();
    private JPasswordField userPasswordField = new JPasswordField();
    private JLabel userIDLabel = new JLabel("User ID");
    private JLabel userPasswordLabel = new JLabel("Password");
    private JLabel messageLabel = new JLabel();
    private IDandPasswords idAndPasswords;


    LoginPage(IDandPasswords idAndPasswords) {
        try {
            FileInputStream fPath = new FileInputStream("C:\\Users\\alexn\\Queen's University\\GROUP-FREC - General\\FREC-App-Info.xlsx");//Put file path here, don't know how it will work with a shared file
            Workbook wb = WorkbookFactory.create(fPath);
            Sheet s = wb.getSheet("FC");
            Row r;


        } catch (Exception E) {
            System.out.println("NO");
            E.printStackTrace();
        }


        this.idAndPasswords = idAndPasswords;

        this.userIDLabel.setForeground(Color.white);
        this.userPasswordLabel.setForeground(Color.white);

        userIDLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);

        messageLabel.setBounds(135, 400, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 15));

        userIDfield.setBounds(120, 100, 200, 25);
        userPasswordField.setBounds(120, 150, 200, 25);

        loginButton.setBounds(120, 200, 100, 25);
        loginButton.addActionListener(this);

        resetButton.setBounds(220, 200, 100, 25);
        resetButton.addActionListener(this);

        registerButton.setBounds(120, 250, 200, 25);
        registerButton.addActionListener(this);

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDfield);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(registerButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Main.COOL_BLUE);

        frame.setResizable(false);
        frame.setLocation(Main.screenWidth / 4, Main.screenHeight / 4);

        frame.setSize(500, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    // add code to navigate user to the actual application here
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            userIDfield.setText("");
            userPasswordField.setText("");
            messageLabel.setText("");

        } else if (e.getSource() == loginButton) {
            String userID = userIDfield.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            if (idAndPasswords.authenticateUser(userID, password)) {
                messageLabel.setForeground(Color.green);
                messageLabel.setText("Login Successful");
                Main.frame.setVisible(true);

                //HERE BITCH
                //SET ALL FC to make

                frame.setVisible(false);
            } else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Invalid UserID or Password");
            }

        } else if (e.getSource() == registerButton) {
            new RegistrationPage(idAndPasswords);
        }
    }
}