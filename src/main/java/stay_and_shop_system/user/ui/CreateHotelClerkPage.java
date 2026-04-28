package stay_and_shop_system.user.ui;

import org.jdesktop.swingx.JXTextField;
import stay_and_shop_system.ColorPalette;
import stay_and_shop_system.HomePage2;
import stay_and_shop_system.Main;
import stay_and_shop_system.SetupUI;
import stay_and_shop_system.ui.RoundJButton;
import stay_and_shop_system.ui.RoundJPasswordField;
import stay_and_shop_system.ui.RoundJTextField;
import stay_and_shop_system.user.AccountController;
import stay_and_shop_system.user.AdminInterface;
import stay_and_shop_system.user.UserRepository;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CreateHotelClerkPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel contentPane;
    private JPanel pagePane;

    static final String EMAIL_FIELD = "Email";
    static final String PASSWORD_FIELD = "Password (defaults to \"Password\")";
    static final String USERNAME_FIELD = "Username";
    static final String PHONENUMBER_FIELD = "Phone";
    static final String EMAIL_EMPTY = "Warning: email field is empty.";
    static final String USERNAME_EMPTY = "Warning: username field is empty.";
    static final String PASSWORD_EMPTY = "Warning: password field is empty.";
    static final String PHONENUMBER_EMPTY = "Warning: phone number field is empty.";
    static final String INVALID_PRIVILEGES = "Error: you do not have the required privileges to access the features on this page.";
    static final String ACCOUNT_CREATE_SUCCESS = "A new Clerk account has been successfully created.";
    static final String ACCOUNT_ALREADY_EXISTS = "Warning: an account with the given email already exists.";
    static final String FUBAR = "Whoops! Something went catastrophically wrong.";
    static final String RESPONSE_MISSING = "Missing Text";

    /**
     * Create the frame.
     */
    public CreateHotelClerkPage() {
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

        JLabel welcomeLabel = new JLabel("Create a Clerk Account");
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
        contentPane.add(passwordField, c);

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

        JButton createAccountButton = new RoundJButton(25);
        createAccountButton.setText("Create Clerk");
        createAccountButton.setPreferredSize(new Dimension(100, 30));
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = String.valueOf(passwordField.getText());
                String email = emailField.getText().trim();
                String phoneNumber = phoneNumberField.getText().trim();

                String dialogMessage;

                if (password.isEmpty()) password = "Password";
                if (!(UserRepository.getSessionAccount() instanceof AdminInterface)) dialogMessage = INVALID_PRIVILEGES;
                else if (username.isEmpty()) dialogMessage = USERNAME_EMPTY;
                else if (email.isEmpty()) dialogMessage = EMAIL_EMPTY;
                else if (phoneNumber.isEmpty()) dialogMessage = PHONENUMBER_EMPTY;
                else {
                    int res = AccountController.createClerk(email, username, password, phoneNumber);
                    dialogMessage = switch (res) {
                        case 0 -> ACCOUNT_CREATE_SUCCESS;
                        case 1 -> ACCOUNT_ALREADY_EXISTS;
                        case -1 -> FUBAR;
                        default -> RESPONSE_MISSING;
                    };
                }
                JOptionPane.showMessageDialog(contentPane, dialogMessage);
            }
        });
        contentPane.add(createAccountButton, c);

        JPanel footer = new JPanel();
        footer.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        footer.setPreferredSize(new Dimension(500, 100));
        mainPane.add(footer, BorderLayout.SOUTH);

        mainPane.add(contentPane, BorderLayout.CENTER);
        setVisible(true);
    }
}