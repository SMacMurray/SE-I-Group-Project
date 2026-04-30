package stay_and_shop_system.user.ui;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXTextField;
import stay_and_shop_system.*;
import stay_and_shop_system.ui.RoundJButton;
import stay_and_shop_system.ui.RoundJPasswordField;
import stay_and_shop_system.ui.RoundJTextField;
import stay_and_shop_system.user.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class LoginPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel contentPane;
    private JLabel invalidLabel;
    private JPanel pagePane;

    static final String EMAIL_FIELD = "Email";
    static final String PASSWORD_FIELD = "Password";
    static final String USERNAME_FIELD = "Username";
    static final String PHONENUMBER_FIELD = "Phone";
    static final String REQUIRED = " *REQUIRED";
    static final String EMAIL_EMPTY = "Warning: email field is empty.";
    static final String USERNAME_EMPTY = "Warning: username field is empty.";
    static final String PASSWORD_EMPTY = "Warning: password field is empty.";
    static final String PHONENUMBER_EMPTY = "Warning: phone number field is empty.";
    static final String INVALID_EMAIL = "Warning: please enter a valid email.";
    static final String INVALID_PHONENUMBER = "Warning: please enter a valid phone number.";
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
        setSize(1000, 800);
        setLocationRelativeTo(null); // Centers the screen
        setTitle(Main.APP_TITLE);

        // Header Stuff ------------------------------------------------------------------------------
        Object[] uiObjects = SetupUI.initializeScreen(popupPane, mainPane, this);
        popupPane = (JPanel) uiObjects[0];
        mainPane = (JPanel) uiObjects[1];

        pagePane = new JPanel(new GridBagLayout());
        JScrollPane pageScrollPane = new JScrollPane(pagePane);
        pageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pageScrollPane.setBorder(null);
        pageScrollPane.setViewportBorder(null);
        pageScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE; // Color of the draggable bar
                this.trackColor = ColorPalette.OCEAN_DARKBLUE; // Color of the background track
            }
        });
        mainPane.add(pageScrollPane, BorderLayout.CENTER);
        // -------------------------------------------------------------------------------------------

        contentPane = new JPanel( new GridBagLayout() );
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 20, 10, 20);

        JLabel welcomeLabel = new JLabel("Welcome to the Ocean's Water Hotel");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(ColorPalette.OCEAN_DARKBLUE);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomeLabel.setPreferredSize(new Dimension(500, 40));
        contentPane.add(welcomeLabel, c);

        JXTextField emailField = new RoundJTextField(25);
        emailField.setPrompt(EMAIL_FIELD);
        emailField.setPreferredSize(new Dimension(200, 30));
        emailField.setBackground(contentPane.getBackground());
        contentPane.add(emailField, c);

        JXTextField passwordField = new RoundJPasswordField(25);
        passwordField.setPrompt(PASSWORD_FIELD);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBackground(contentPane.getBackground());
        //passwordField.setEchoChar((char)0);
        contentPane.add(passwordField);

        ImageIcon showPasswordIcon = new ImageIcon("src/main/resources/showPassword.png");
        showPasswordIcon = new ImageIcon(showPasswordIcon.getImage().getScaledInstance((int)(showPasswordIcon.getIconWidth() * 0.2), (int)(showPasswordIcon.getIconHeight() * 0.2), Image.SCALE_SMOOTH));
        ImageIcon hidePasswordIcon = new ImageIcon("src/main/resources/hidePassword.png");
        hidePasswordIcon = new ImageIcon(hidePasswordIcon.getImage().getScaledInstance((int)(hidePasswordIcon.getIconWidth() * 0.0390625), (int)(hidePasswordIcon.getIconHeight() * 0.0390625), Image.SCALE_SMOOTH));

        // This button is currently deprecated as SwingX does not have an equivalent JXPasswordField
        // that would allow you to set echoChar... why.
        // This is positioned absolutely, because I can not for the life of me figure out how to make
        // this appear in line with the password field in GridBagLayout.
        JToggleButton showPasswordButton = new JToggleButton();
        showPasswordButton.setBounds(30, 300, 30, 30);
        ImageIcon finalShowPasswordIcon = showPasswordIcon;
        ImageIcon finalHidePasswordIcon = hidePasswordIcon;
        showPasswordButton.setIcon(finalHidePasswordIcon);
        showPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPasswordButton.setBackground(contentPane.getBackground());
                if (showPasswordButton.isSelected()){
                    //passwordField.setEchoChar((char)0);
                    showPasswordButton.setIcon(finalShowPasswordIcon);
                }
                else{
                    //passwordField.setEchoChar('•');
                    showPasswordButton.setIcon(finalHidePasswordIcon);
                }
            }
        });
        contentPane.add(passwordField, c);

        //mainPane.add(showPasswordButton);

        JXTextField usernameField = new RoundJTextField(25);
        usernameField.setPrompt(USERNAME_FIELD);
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setBackground(contentPane.getBackground());
        contentPane.add(usernameField, c);

        JXTextField phoneNumberField = new RoundJTextField(25);
        phoneNumberField.setPrompt(PHONENUMBER_FIELD);
        phoneNumberField.setPreferredSize(new Dimension(200, 30));
        phoneNumberField.setBackground(contentPane.getBackground());
        contentPane.add(phoneNumberField, c);

        JSeparator horizontalLine = new JSeparator(SwingConstants.HORIZONTAL);
        horizontalLine.setPreferredSize(new Dimension(400, 10));
        horizontalLine.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        contentPane.add(horizontalLine, c);

        JButton signInButton = new RoundJButton(25);
        signInButton.setText("Sign In");
        //signInButton.setBackground(Color.BLACK);
        //signInButton.setForeground(Color.WHITE);
        signInButton.setPreferredSize(new Dimension(100, 30));
        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = String.valueOf(passwordField.getText());
                String dialogMessage;
                //passwordField.setPassword("");
                //passwordField.setEchoChar((char)0);

                if (email.isEmpty()) dialogMessage = EMAIL_EMPTY;
                else if (password.isEmpty()) dialogMessage = PASSWORD_EMPTY;
                else {
                    int res = AccountController.login(email, password);
                    dialogMessage = switch (res) {
                        case 0 -> LOGIN_SUCCESS;
                        case 1 -> LOGIN_FAILURE;
                        case 3 -> INVALID_EMAIL;
                        default -> RESPONSE_MISSING;
                    };

                    // Login Success
                    if (res == 0){
                        HomePage2 newFrame = new HomePage2();
                        dispose();
                    }
                }
                if (!dialogMessage.equals(LOGIN_SUCCESS)){
                    emailField.setPrompt(EMAIL_FIELD + REQUIRED);
                    passwordField.setPrompt(PASSWORD_FIELD + REQUIRED);
                    usernameField.setPrompt(USERNAME_FIELD);
                    phoneNumberField.setPrompt(PHONENUMBER_FIELD);
                }
                JOptionPane.showMessageDialog(contentPane, dialogMessage);
            }
        });
        contentPane.add(signInButton, c);

        JPanel newMemberPanel = new JPanel(new GridBagLayout());
        newMemberPanel.setBackground(ColorPalette.OCEAN_LIGHTBLUE);
        newMemberPanel.setPreferredSize(new Dimension(200, 100));

        JLabel newMemberLabel = new JLabel("Not a member?");
        newMemberLabel.setFont(new Font("Serif", Font.BOLD, 20));
        newMemberPanel.add(newMemberLabel, c);

        JButton createAccountButton = new RoundJButton(25);
        createAccountButton.setText("Join Now");
        createAccountButton.setPreferredSize(new Dimension(100, 30));
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = String.valueOf(passwordField.getText());
                String email = emailField.getText().trim();
                String phoneNumber = phoneNumberField.getText().trim();

                String dialogMessage;
                //passwordField.setEchoChar((char)0);

                if (username.isEmpty()) dialogMessage = USERNAME_EMPTY;
                else if (password.isEmpty()) dialogMessage = PASSWORD_EMPTY;
                else if (email.isEmpty()) dialogMessage = EMAIL_EMPTY;
                else if (phoneNumber.isEmpty()) dialogMessage = PHONENUMBER_EMPTY;
                else {
                    int res = AccountController.createAccount(email, username, password, phoneNumber);
                    dialogMessage = switch (res) {
                        case 0 -> ACCOUNT_CREATE_SUCCESS;
                        case 1 -> ACCOUNT_ALREADY_EXISTS;
                        case 2 -> INVALID_PHONENUMBER;
                        case 3 -> INVALID_EMAIL;
                        case -1 -> FUBAR;
                        default -> RESPONSE_MISSING;
                    };
                    // Create Account Success
                    if (res == 0){
                        HomePage2 newFrame = new HomePage2();
                        dispose();
                    }
                }
                if (!dialogMessage.equals(ACCOUNT_CREATE_SUCCESS)){
                    emailField.setPrompt(EMAIL_FIELD + REQUIRED);
                    passwordField.setPrompt(PASSWORD_FIELD + REQUIRED);
                    usernameField.setPrompt(USERNAME_FIELD + REQUIRED);
                    phoneNumberField.setPrompt(PHONENUMBER_FIELD + REQUIRED);
                }
                JOptionPane.showMessageDialog(contentPane, dialogMessage);
            }
        });
        newMemberPanel.add(createAccountButton, c);

        contentPane.add(newMemberPanel, c);

        JPanel footer = new JPanel();
        footer.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        footer.setPreferredSize(new Dimension(500, 100));
        mainPane.add(footer, BorderLayout.SOUTH);

        mainPane.add(contentPane, BorderLayout.CENTER);
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