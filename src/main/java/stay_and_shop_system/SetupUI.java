package stay_and_shop_system;

import stay_and_shop_system.occupancy.ui.SearchRoomPage;
import stay_and_shop_system.user.*;
import stay_and_shop_system.user.ui.CancelReservationPage;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SetupUI {
    private static JButton setupSideBarButton(String text, int top, int bottom) {
        JButton button = new JButton(text);
        button.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        button.setFont(new Font("Serif", Font.PLAIN, 23));
        button.setBackground(ColorPalette.SATURATED_BLUE);
        button.setBorder(BorderFactory.createMatteBorder(top, 0, bottom, 0, ColorPalette.OCEAN_DARKBLUE));

        return button;
    }

    private static JPanel setupButtonsPane(JFrame frame) {
        int buttonCount = 0;
        JPanel buttonsPane = new JPanel(new GridLayout(0, 1));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        User user = new GuestClerk("Johnny Test", "johnnyTest@gmail.com");

        Font font = new Font("Serif", Font.PLAIN, 23);
        if (user instanceof ClerkInterface) {
            JButton addRoomButton = setupSideBarButton("Add Room", 0, 0);
            buttonsPane.add(addRoomButton);

            JButton viewRoomsButton = setupSideBarButton("View All Rooms", 2, 0);
            buttonsPane.add(viewRoomsButton);

            JButton modifyRoomButton = setupSideBarButton("Modify Room", 2, 0);
            buttonsPane.add(modifyRoomButton);

            JButton modifyMyInformationButton = setupSideBarButton("Modify My Information", 2, 0);
            buttonsPane.add(modifyMyInformationButton);

            JButton modifyReservationButton = setupSideBarButton("Modify My Information", 2, 0);
            buttonsPane.add(modifyReservationButton);

            // Make a JPanel that prompts for a user's name to search for.
            JButton cancelReservationButton = new JButton("Cancel Guest Reservation");
            buttonsPane.add(cancelReservationButton);
            cancelReservationButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CancelReservationPage newFrame = new CancelReservationPage((GuestInterface)user);
                    frame.dispose();
                }
            });

            JButton checkGuestBill = new JButton("Check Guest's Bill");
            buttonsPane.add(checkGuestBill);

        }
        if (user instanceof AdminInterface) {

        }
        if (user instanceof GuestInterface) {
            JButton checkGuestBill = new JButton("Check My Bill");
            buttonsPane.add(checkGuestBill);

            JButton modifyReservationButton = new JButton("Modify My Reservation");
            buttonsPane.add(modifyReservationButton);

            JButton cancelReservationButton = new JButton("Cancel My Reservation");
            buttonsPane.add(cancelReservationButton);
            cancelReservationButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CancelReservationPage newFrame = new CancelReservationPage((GuestInterface)user);
                    frame.dispose();
                }
            });
        }

        return buttonsPane;
    }
    public static JPanel setupPopup(JFrame frame) {
        // TODO: Make the side popup for the User to see all their actions
        //  associated with their Interface(s) and if they have reserved a room
        JPanel sidebarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        sidebarPanel.setPreferredSize(new Dimension(350, sidebarPanel.getHeight()));
        sidebarPanel.setBackground(ColorPalette.DESATURATED_DARKBLUE);

        JLabel accountStatusLabel = new JLabel("Logged out");
        accountStatusLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        accountStatusLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        if (Main.SessionAccount != null) {
            accountStatusLabel.setText("Logged in: " + Main.SessionAccount.getName());
        }
        c.gridx = 0;
        c.gridy = 0;
        sidebarPanel.add(accountStatusLabel, c);

        JPanel buttonsPane = setupButtonsPane(frame);
        JScrollPane buttonsScrollPane = new JScrollPane(buttonsPane);
        buttonsPane.setPreferredSize(new Dimension(300, 400));
        buttonsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        buttonsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonsScrollPane.setBorder(null);
        buttonsScrollPane.setViewportBorder(null);
        buttonsScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE; // Color of the draggable bar
                this.trackColor = ColorPalette.OCEAN_DARKBLUE; // Color of the background track
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        sidebarPanel.add(buttonsScrollPane, c);

        return sidebarPanel;
//        throw new RuntimeException("TODO: finish setupPopup");
    }
    public static Object[] initializeScreen(JPanel popupPane, JPanel mainPane, JFrame frame) {
        popupPane = new JPanel(new BorderLayout());
        // Border layout is the only layout manager that fills it's objects when it's available(besides GridBagLayout), as far as im aware of.
        mainPane = new JPanel(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        popupPane.add(mainPane, BorderLayout.CENTER);
        frame.setContentPane(popupPane);

        JPanel headerPane = new JPanel(new GridBagLayout());
        mainPane.add(headerPane, BorderLayout.PAGE_START);
        headerPane.setBackground(ColorPalette.OCEAN_DARKBLUE);
        headerPane.setPreferredSize(new Dimension(headerPane.getWidth(), headerPane.getHeight() + 50));

        // Was playing with weightx and Insets in order to center the title
        ImageIcon menuButtonIcon = new ImageIcon("src/main/resources/menuButton.png");
        ImageIcon newMenuButtonIcon = new ImageIcon(menuButtonIcon.getImage().getScaledInstance((int)(menuButtonIcon.getIconWidth() * 0.6), (int)(menuButtonIcon.getIconHeight() * 0.6), Image.SCALE_SMOOTH));
        JLabel menuButtonLabel = new JLabel(newMenuButtonIcon);
        menuButtonLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel test = setupPopup(frame);
//        test.add(new JLabel("MOOOOOOOOMM"));
        popupPane.add(test, BorderLayout.LINE_START);
//        popupPane.setComponentZOrder(test, 0);
        test.setVisible(false);
        menuButtonLabel.addMouseListener(new MouseAdapter() { // JLabel doesnt have actionListener
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("test menuButton was clicked!");
                if (test.isVisible()) {
                    test.setVisible(false);
                }
                else test.setVisible(true);
            }
        });
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 0.2;
        c.fill = GridBagConstraints.NONE;
        headerPane.add(menuButtonLabel, c);
        JLabel titleLabel = new JLabel("THE OCEAN'S WATERS HOTEL");
        titleLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Title was clicked!");
                HomePage2 newFrame = new HomePage2();
                frame.dispose();
            }
        });
        c.gridy = 0;
        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 40, 0, 0);
        headerPane.add(titleLabel, c);
        JButton bookRoomButton = new JButton("Book Room");
        bookRoomButton.setBackground(ColorPalette.OCEAN_LIGHTBLUE);
        bookRoomButton.setForeground(ColorPalette.OCEAN_DARKBLUE);
        bookRoomButton.setFont(new Font("Serif", Font.BOLD, 16));
        bookRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchRoomPage newFrame = new SearchRoomPage();
                frame.dispose();
            }
        });
        c.gridy = 0;
        c.gridx = 2;
        c.weightx = 0.1;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        headerPane.add(bookRoomButton, c);

        return new Object[] {popupPane, mainPane};
    }
}
