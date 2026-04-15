package stay_and_shop_system.user.ui;

import stay_and_shop_system.*;
import stay_and_shop_system.user.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

public class LoginPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel invalidLabel;

    static final String EMAIL_EMPTY = "Warning: email field is empty.";
    static final String USERNAME_EMPTY = "Warning: username field is empty.";
    static final String PASSWORD_EMPTY = "Warning: password field is empty.";
    static final String PHONENUMBER_EMPTY = "Warning: phone number field is empty.";
    static final String LOGIN_SUCCESS = "Successfully logged in! Redirecting you to homepage.";
    static final String LOGIN_FAILURE = "The username and/or password you have entered are incorrect.";
    static final String ACCOUNT_CREATE_SUCCESS = "Account created successfully! You are now logged in.";
    static final String ACCOUNT_ALREADY_EXISTS = "Account already exists! Please Sign In instead.";
    static final String FUBAR = "Whoops! Something went catastrophically wrong.";
    static final String RESPONSE_MISSING = "Missing Text";

    /**
     * Create the frame.
     */
    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Main.WINDOW_W, Main.WINDOW_H);
        setLocationRelativeTo(null);
        setTitle(Main.APP_TITLE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton homeButton = new JButton(Main.HOME_TEXT);
        homeButton.setBounds(0, 0, 140, 22);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePage2 newFrame = new HomePage2(); //Opening the second JFrame
                dispose(); //Disposing the First JFrame
            }
        });
        contentPane.add(homeButton);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setBounds((Main.WINDOW_W-170)/2, 20, 170, 20);
        contentPane.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds((Main.WINDOW_W-170)/2, 40, 170, 20);
        contentPane.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setBounds((Main.WINDOW_W-110)/2, 60, 110, 14);
        contentPane.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds((Main.WINDOW_W-170)/2, 80, 170, 20);
        contentPane.add(passwordField);

        JToggleButton btnShowPassword = new JToggleButton();
        btnShowPassword.setBounds((Main.WINDOW_W-170)/2+passwordField.getWidth(), 80, 20, 20);
        btnShowPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (btnShowPassword.isSelected()) passwordField.setEchoChar((char)0);
                else passwordField.setEchoChar('•');
            }
        });
        contentPane.add(btnShowPassword);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setBounds((Main.WINDOW_W-110)/2, 100, 110, 14);
        contentPane.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds((Main.WINDOW_W-170)/2, 120, 170, 20);
        contentPane.add(emailField);

        JLabel phoneNumberLabel = new JLabel("Phone Number");
        phoneNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        phoneNumberLabel.setBounds((Main.WINDOW_W-110)/2, 140, 110, 14);
        contentPane.add(phoneNumberLabel);

        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setBounds((Main.WINDOW_W-170)/2, 160, 170, 20);
        contentPane.add(phoneNumberField);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(160, 160, 160));
        panel.setBounds(143, 251, 360, 101);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("Not a member?");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds((panel.getWidth()-240)/2, 11, 240, 14);
        panel.add(lblNewLabel_3);

        JButton btnCreateAccount = new JButton("Join Now");
        btnCreateAccount.setBounds((panel.getWidth()-90)/2, 67, 90, 23);
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = String.valueOf(passwordField.getPassword());
                String email = emailField.getText().trim();
                String phoneNumber = phoneNumberField.getText().trim();

                String dialogMessage;
                passwordField.setText("");

                if (username.isEmpty()) dialogMessage = USERNAME_EMPTY;
                else if (password.isEmpty()) dialogMessage = PASSWORD_EMPTY;
                else if (email.isEmpty()) dialogMessage = EMAIL_EMPTY;
                else if (phoneNumber.isEmpty()) dialogMessage = PHONENUMBER_EMPTY;
                else {
//                	JOptionPane.showMessageDialog(usernameLabel, dialogMessage);
                    int res = AccountController.createAccount(email, username, password, phoneNumber);
                    dialogMessage = switch (res) {
                        case 0 -> ACCOUNT_CREATE_SUCCESS;
                        case 1 -> ACCOUNT_ALREADY_EXISTS;
                        case -1 -> FUBAR;
                        default -> RESPONSE_MISSING;
                    };

                    // Create Account Success
                    if (res == 0){
                        HomePage2 newFrame = new HomePage2();
                        dispose();
                    }
                }

                JOptionPane.showMessageDialog(usernameLabel, dialogMessage);
            }
        });
        panel.add(btnCreateAccount);

        JButton btnSignIn = new JButton("Sign In");
        btnSignIn.setBounds((Main.WINDOW_W-90)/2, 190, 90, 22);
        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = String.valueOf(passwordField.getPassword());
                String dialogMessage;
                passwordField.setText("");

                if (email.isEmpty()) dialogMessage = EMAIL_EMPTY;
                else if (password.isEmpty()) dialogMessage = PASSWORD_EMPTY;
                else {
                    int res = AccountController.login(email, password);
                    dialogMessage = switch (res) {
                        case 0 -> LOGIN_SUCCESS;
                        case 1 -> LOGIN_FAILURE;
                        default -> RESPONSE_MISSING;
                    };

                    // Login Success
                    if (res == 0){
                        HomePage2 newFrame = new HomePage2();
                        dispose();
                    }
                }
                JOptionPane.showMessageDialog(usernameLabel, dialogMessage);
            }
        });
        contentPane.add(btnSignIn);

        JButton btnResetPassword = new JButton("Forgot password?");
        btnResetPassword.setBounds((Main.WINDOW_W-170)/2, 224, 170, 22);
        contentPane.add(btnResetPassword);


        setVisible(true);
    }
    public void invalidInfo(String label) {
        if (!contentPane.isAncestorOf(invalidLabel) || invalidLabel.getText() != label) { // If contentPane does not contain invalidLabel
            if (contentPane.isAncestorOf(invalidLabel)) { // For testing purposes
                contentPane.remove(invalidLabel);
            }

            invalidLabel = new JLabel(label);
            invalidLabel.setHorizontalAlignment(SwingConstants.CENTER);
            Dimension prefSize = invalidLabel.getPreferredSize(); // So the text fits exactly in the box more properly
            invalidLabel.setBounds(280, 40, prefSize.width, prefSize.height);
            invalidLabel.setForeground(Color.RED);

            contentPane.add(invalidLabel);
            contentPane.revalidate();
            contentPane.repaint();

            // Note to self: I need to find a user based on the Username, then check if the password is right
            // This means that there can only be one Username for each person.
        }
    }
}