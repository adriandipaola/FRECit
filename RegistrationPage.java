import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RegistrationPage implements ActionListener {
    private JFrame regFrame = new JFrame();
    private JTextField regUserIDField = new JTextField();
    private JPasswordField regPasswordField = new JPasswordField();
    private JPasswordField confirmPasswordField = new JPasswordField();
    private JTextField accessCodeField = new JTextField(); // New field for access code
    private JLabel regUserIDLabel = new JLabel("New User ID:");
    private JLabel regPasswordLabel = new JLabel("New Password:");
    private JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
    private JLabel accessCodeLabel = new JLabel("Access Code:"); // Label for access code
    private JLabel regMessageLabel = new JLabel();
    private JLabel genderLabel = new JLabel("Gender:");
    private JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Non Male"});
    private JToggleButton showPasswordButton = new JToggleButton("Show Key");
    private IDandPasswords idAndPasswords;

    private static final String ACCESS_CODE = "FREC-IT"; // Hardcoded access code

    RegistrationPage(IDandPasswords idAndPasswords) {
        this.idAndPasswords = idAndPasswords;
        this.regUserIDLabel.setForeground(Color.white);
        this.regPasswordLabel.setForeground(Color.white);
        this.confirmPasswordLabel.setForeground(Color.white);
        this.accessCodeLabel.setForeground(Color.white);


        regUserIDLabel.setBounds(50, 50, 150, 25);
        regPasswordLabel.setBounds(50, 100, 150, 25);
        confirmPasswordLabel.setBounds(50, 150, 150, 25);
        accessCodeLabel.setBounds(50, 200, 150, 25); // Set bounds for access code label
        regMessageLabel.setBounds(50, 250, 400, 25);
        regMessageLabel.setFont(new Font(null, Font.ITALIC, 14));

        regUserIDField.setBounds(200, 50, 200, 25);
        regPasswordField.setBounds(200, 100, 150, 25);
        confirmPasswordField.setBounds(200, 150, 150, 25);
        accessCodeField.setBounds(200, 200, 200, 25); // Set bounds for access code field
        genderComboBox.setBounds(200, 250, 200, 25); // Adjusting bounds for gender combo box
        showPasswordButton.setBounds(360, 100, 120, 25);

        JButton registerUserButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        registerUserButton.setBounds(100, 300, 100, 25);
        cancelButton.setBounds(250, 300, 100, 25);

        registerUserButton.addActionListener(this);
        cancelButton.addActionListener(this);
        showPasswordButton.addItemListener(new PasswordVisibilityListener(regPasswordField, confirmPasswordField));

        regFrame.add(regUserIDLabel);
        regFrame.add(regPasswordLabel);
        regFrame.add(confirmPasswordLabel);
        regFrame.add(accessCodeLabel); // Add access code label to frame
        regFrame.add(regMessageLabel);
        regFrame.add(regUserIDField);
        regFrame.add(regPasswordField);
        regFrame.add(confirmPasswordField);
        regFrame.add(accessCodeField); // Add access code field to frame
        regFrame.add(genderComboBox);
        regFrame.add(showPasswordButton);
        regFrame.add(registerUserButton);
        regFrame.add(cancelButton);

        regFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        regFrame.getContentPane().setBackground(Main.COOL_BLUE);
        regFrame.setResizable(false);
        regFrame.setLocation(Main.screenWidth / 4, Main.screenHeight / 4);
        regFrame.setSize(500, 400);
        regFrame.setLayout(null);
        regFrame.setVisible(true);
    }


    public static void addFC (String name, String gender) {
        String data = "";
        try {
            FileInputStream fPath = new FileInputStream("C:\\Users\\alexn\\Queen's University\\GROUP-FREC - General\\FREC-App-Info.xlsx");//Put file path here, don't know how it will work with a shared file
            Workbook wb = WorkbookFactory.create(fPath);
            Sheet s = wb.getSheet("FC");
            Row r;

            int i = 0;
            r = s.getRow(i);
            while (r != null) {
                i++;
                r = s.getRow(i);
                System.out.println(i + " Hooray!");
            }
            r = s.createRow(i);
            r.createCell(0).setCellValue(name);
            r.createCell(1).setCellValue(gender);

            //ADRIAN CODE HERE!!! (in case the code fucks up)
            if (s.getLastRowNum() == 14) {
                if (r.getLastCellNum() != 2) {
                    //write the code that creates the panels!!!!
                }
            }

            FileOutputStream fos = new FileOutputStream("C:\\Users\\alexn\\Queen's University\\GROUP-FREC - General\\FREC-App-Info.xlsx");
            wb.write(fos);
            wb.close();
        } catch (Exception e) {
            System.out.println("Read Exception catch");
            e.printStackTrace();
        }
    }
    /* TODO:
        Check row 15 for a value
        if there is a name in row 15 column A, check column C for a value
        if there is no value in column C, run the code to create the panels!!!
     */

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Register")) {
            String userID = regUserIDField.getText();
            String password = String.valueOf(regPasswordField.getPassword());
            String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
            String accessCode = accessCodeField.getText(); // Get entered access code
            String gender = (String) genderComboBox.getSelectedItem();

            if (accessCode.equals(ACCESS_CODE)) { // Check if access code matches
                if (!userID.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
                    if (!idAndPasswords.userExists(userID)) {
                        idAndPasswords.addUser(userID, password, gender);
                        regMessageLabel.setForeground(Color.green);
                        regMessageLabel.setText("Registration Successful");
                        regFrame.dispose();

                        //add dude to the thing
                        addFC(userID, gender);
                        Main.generateFCMems();

                    } else {
                        regMessageLabel.setForeground(Color.red);
                        regMessageLabel.setText("User ID already exists");
                    }
                } else {
                    regMessageLabel.setForeground(Color.red);
                    regMessageLabel.setText("Passwords Don't Match");
                }
            } else {
                regMessageLabel.setForeground(Color.red);
                regMessageLabel.setText("Invalid Access Code");
            }
        } else if (e.getActionCommand().equals("Cancel")) {
            regFrame.dispose();
        }
    }

    // Password visibility listener class
    class PasswordVisibilityListener implements ItemListener {
        private JPasswordField passwordField1;
        private JPasswordField passwordField2;

        PasswordVisibilityListener(JPasswordField field1, JPasswordField field2) {
            this.passwordField1 = field1;
            this.passwordField2 = field2;
        }

        public void itemStateChanged(ItemEvent e) {
            int state = e.getStateChange();
            if (state == ItemEvent.SELECTED) {
                passwordField1.setEchoChar((char) 0); // Show password
                passwordField2.setEchoChar((char) 0); // Show confirm password
            } else {
                passwordField1.setEchoChar('*'); // Hide password
                passwordField2.setEchoChar('*'); // Hide confirm password
            }
        }
    }
}