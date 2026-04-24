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
import stay_and_shop_system.user.User;
import stay_and_shop_system.user.UserRepository;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ResetPasswordPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel contentPane;
    private JLabel invalidLabel;
    private JPanel pagePane;

    static final String EMAIL_FIELD = "Email of Account";
    static final String PASSWORD_FIELD = "New Password";
    static final String EMAIL_EMPTY = "Warning: email field is empty.";
    static final String PASSWORD_EMPTY = "Warning: password field is empty.";
    static final String ACCOUNT_NOT_FOUND = "Error: account not found.";
    static final String INVALID_PRIVILEGES = "Error: you do not have the required privileges to access the features on this page.";
    static final String PASSWORD_CHANGE_SUCCESS = "Successfully changed password!";
    static final String FUBAR = "Whoops! Something went catastrophically wrong.";
    static final String RESPONSE_MISSING = "Missing Text";

    /**
     * Create the frame.
     */
    public ResetPasswordPage() {
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

        JLabel welcomeLabel = new JLabel("Reset Password of any Account");
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

        JButton resetPasswordButton = new RoundJButton(25);
        resetPasswordButton.setText("Change Password");
        resetPasswordButton.setPreferredSize(new Dimension(200, 30));
        resetPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = String.valueOf(passwordField.getText());
                String dialogMessage;

                if (!(UserRepository.getSessionAccount() instanceof AdminInterface)) dialogMessage = INVALID_PRIVILEGES;
                else if (email.isEmpty()) dialogMessage = EMAIL_EMPTY;
                else if (password.isEmpty()) dialogMessage = PASSWORD_EMPTY;
                else {
                    int res = AccountController.updatePassword(email, password);
                    dialogMessage = switch (res) {
                        case 0 -> PASSWORD_CHANGE_SUCCESS;
                        case 1 -> ACCOUNT_NOT_FOUND;
                        case -1 -> FUBAR;
                        default -> RESPONSE_MISSING;
                    };
                }
                JOptionPane.showMessageDialog(contentPane, dialogMessage);
            }
        });
        contentPane.add(resetPasswordButton, c);

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