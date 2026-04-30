package stay_and_shop_system.user.ui;

import stay_and_shop_system.*;
import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.ui.RoundJPasswordField;
import stay_and_shop_system.ui.RoundJTextField;
import stay_and_shop_system.user.AccountController;
import stay_and_shop_system.user.ClerkInterface;
import stay_and_shop_system.user.User;
import stay_and_shop_system.user.UserRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ModifyMyInformationPage extends JFrame {
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel pagePane;
    private JPanel contentPane;

    public ModifyMyInformationPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Main.WINDOW_W, Main.WINDOW_H);
        setLocationRelativeTo(null);
        setTitle(Main.APP_TITLE);

        User currentUser = UserRepository.getSessionAccount();

        if (!(currentUser instanceof ClerkInterface)) {
            JOptionPane.showMessageDialog(null, "You must be logged in as a clerk to modify your information.");
            new HomePage2();
            dispose();
            return;
        }

        Object[] uiObjects = SetupUI.initializeScreen(popupPane, mainPane, this);
        popupPane = (JPanel) uiObjects[0];
        mainPane = (JPanel) uiObjects[1];

        pagePane = new JPanel(new GridBagLayout());
        pagePane.setBackground(ColorPalette.OCEAN_DARKBLUE);

        JScrollPane pageScrollPane = new JScrollPane(pagePane);
        pageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pageScrollPane.setBorder(null);
        pageScrollPane.setViewportBorder(null);
        pageScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE;
                this.trackColor = ColorPalette.OCEAN_DARKBLUE;
            }
        });

        mainPane.add(pageScrollPane, BorderLayout.CENTER);

        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(ColorPalette.DESATURATED_DARKBLUE);
        contentPane.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Modify My Information");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(500, 40));
        contentPane.add(titleLabel, c);

        JLabel subtitleLabel = new JLabel("Update your clerk account details");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setForeground(ColorPalette.DESATURATED_LIGHTBLUE);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        contentPane.add(subtitleLabel, c);

        JTextField nameField = makeField();
        JTextField emailField = makeField();
        JTextField phoneField = makeField();
        JPasswordField passwordField = new JPasswordField(25);
        stylePasswordField(passwordField);

        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
        phoneField.setText(currentUser.getPhoneNumber());

        contentPane.add(makeLabeledRow("Name", nameField), c);
        contentPane.add(makeLabeledRow("Email", emailField), c);
        contentPane.add(makeLabeledRow("Phone Number", phoneField), c);
        contentPane.add(makeLabeledRow("New Password Optional", passwordField), c);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttonsPanel.setOpaque(false);

        JButton saveButton = new JButton("Save Changes");
        stylePrimaryButton(saveButton);

        JButton cancelButton = new JButton("Cancel");
        styleSecondaryButton(cancelButton);

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        contentPane.add(buttonsPanel, c);

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty.");
                return;
            }

            if (email.isEmpty() || !Reservation.validateEmail(email)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email.");
                return;
            }

            if (phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phone number cannot be empty.");
                return;
            }

            int result = AccountController.updateCurrentUserInformation(email, name, phone, password);

            switch (result) {
                case 0 -> {
                    JOptionPane.showMessageDialog(null, "Your information was updated successfully.");
                    new HomePage2();
                    dispose();
                }
                case 1 -> JOptionPane.showMessageDialog(null, "That email is already being used by another account.");
                case 2 -> JOptionPane.showMessageDialog(null, "You must be logged in to update your information.");
                default -> JOptionPane.showMessageDialog(null, "Something went wrong while updating your information.");
            }
        });

        cancelButton.addActionListener(e -> {
            new HomePage2();
            dispose();
        });

        pagePane.add(contentPane);
        setVisible(true);
    }

    private JTextField makeField() {
        JTextField field = new RoundJTextField(25);
        field.setPreferredSize(new Dimension(260, 34));
        field.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        field.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        field.setFont(new Font("Serif", Font.PLAIN, 16));
        return field;
    }

    private void stylePasswordField(JPasswordField field) {
        field.setPreferredSize(new Dimension(260, 34));
        field.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        field.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        field.setFont(new Font("Serif", Font.PLAIN, 16));
    }

    private JPanel makeLabeledRow(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(10, 8));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(650, 65));

        JLabel label = new JLabel(labelText);
        label.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        label.setFont(new Font("Serif", Font.PLAIN, 20));

        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(ColorPalette.OCEAN_LIGHTBLUE);
        button.setForeground(ColorPalette.OCEAN_DARKBLUE);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(170, 38));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(ColorPalette.SATURATED_BLUE);
        button.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(140, 38));
    }
}