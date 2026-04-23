package stay_and_shop_system;

import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.ui.*;
import stay_and_shop_system.user.*;
import stay_and_shop_system.user.ui.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Objects;


public class SetupUI {
    public static void setUpJOptionPaneDesign() {
        UIManager.put("OptionPane.background", ColorPalette.OCEAN_DARKBLUE);
        UIManager.put("OptionPane.messageForeground", ColorPalette.OCEAN_LIGHTBLUE);
    }
    public static JLabel getScaledImage(String imgPath, Double xSize, Double ySize) {
        ImageIcon icon = new ImageIcon(imgPath);
        ImageIcon newIcon = new ImageIcon(icon.getImage().getScaledInstance((int)(icon.getIconWidth() * xSize), (int)(icon.getIconHeight() * ySize), Image.SCALE_SMOOTH));
        JLabel jLabel = new JLabel(newIcon);

        return jLabel;
    }
    public static JButton makeButtonInvisible(JButton jb) {
        jb.setOpaque(false);
        jb.setContentAreaFilled(false);
        jb.setBorderPainted(false);
        jb.setFocusPainted(false);

        return jb;
    }
    public static void alterMenuButtonMouseEvent(JPanel headerPane, MouseAdapter ma) {
        JLabel menuButton = null;
        for (Component c : headerPane.getComponents()) {
            if (c.getName() != null) {
                if (c.getName().equals("MenuButton")) {
                    menuButton = (JLabel) c;
                }
            }
        }

        if (menuButton != null) {
            menuButton.addMouseListener(ma);
        }
        else {
            throw new RuntimeException("Could not find MenuButton : SetupUI");
        }
    }
    public static JPanel makeSearchBar() {
        JPanel jp = new JPanel();
        JTextField jtf = new JTextField(13);
        jtf.setFont(new Font("Serif", Font.PLAIN, 14));
        jtf.setForeground(ColorPalette.OCEAN_DARKBLUE); // Sets text color
        JLabel searchImg = getScaledImage("src/main/resources/searchBarImg.png", 0.05, 0.05);
        jp.add(jtf);
        jp.add(searchImg);

        jp.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        jtf.setOpaque(false);
        jtf.setBorder(null);

        return jp;
    }


    private static JButton setupSideBarButton(String text, int top, int bottom) {
        JButton button = new JButton(text);
        button.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        button.setFont(new Font("Serif", Font.PLAIN, 23));
        button.setBackground(ColorPalette.SATURATED_BLUE);
        button.setPreferredSize(new Dimension(300, 40));
        button.setBorder(BorderFactory.createMatteBorder(top, 0, bottom, 0, ColorPalette.OCEAN_DARKBLUE));

        return button;
    }

    private static JPanel setupButtonsPane(JFrame frame) {
        int buttonCount = 0;
        JPanel buttonsPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        // TODO: Allow ButtonsPane to work with currently signed in User
//        User user = new GuestClerk("Johnny Test", "johnnyTest@gmail.com", 0, "", new PaymentMethod());
        User user = UserRepository.getSessionAccount();

        Font font = new Font("Serif", Font.PLAIN, 23);

        JButton loginButton = setupSideBarButton((UserRepository.getSessionAccount() == null ? "Sign In" : "Log Out"), 0, 0);
        c.gridx = 0;
        c.gridy = buttonCount;
        buttonCount++;
        buttonsPane.add(loginButton, c);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (UserRepository.getSessionAccount() == null){
                    LoginPage newFrame = new LoginPage();
                    frame.dispose();
                }
                else {
                    AccountController.logout();
                    HomePage2 newFrame = new HomePage2();
                    frame.dispose();
                }
            }
        });

        if (user instanceof ClerkInterface) {
            JButton addRoomButton = setupSideBarButton("Add Room", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount++;
            buttonsPane.add(addRoomButton, c);
            addRoomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClerkAddRoomPage newFrame = new ClerkAddRoomPage();
                    frame.dispose();
                }
            });

            JButton modifyRoomButton = setupSideBarButton("Modify Room", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount++;
            buttonsPane.add(modifyRoomButton, c);

            modifyRoomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ModifyRoomPage newFrame = new ModifyRoomPage();
                    frame.dispose();
                }
            });

            JButton viewRoomsButton = setupSideBarButton("View All Rooms", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount++;
            buttonsPane.add(viewRoomsButton, c);

            JButton modifyMyInformationButton = setupSideBarButton("Modify My Information", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount++;
            buttonsPane.add(modifyMyInformationButton, c);

            JButton modifyReservationButton = setupSideBarButton("Modify Guest Information", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount++;
            buttonsPane.add(modifyReservationButton, c);

            JButton cancelReservationButton = setupSideBarButton("Cancel Guest Reservation", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount++;
            buttonsPane.add(cancelReservationButton, c);
            cancelReservationButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (user instanceof GuestInterface guestUser) {
                        CancelReservationPage newFrame = new CancelReservationPage(guestUser.getGuestId());
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "This clerk account is not also a guest.");
                    }
                }
            });

            JButton checkGuestBill = setupSideBarButton("Check Guest Bill", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            buttonsPane.add(checkGuestBill, c);

            checkGuestBill.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CombinedBillPage newFrame = new CombinedBillPage(true);
                    frame.dispose();
                }
            });
        }
        if (user instanceof AdminInterface) {
            // Based on the Project 12 deliverable, the Admin does not change the default password(since the clerk would), but can leave a predefined email.
            JButton createClerkButton = setupSideBarButton("Create Hotel Clerk", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            createClerkButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CreateHotelClerkPage newFrame = new CreateHotelClerkPage();
                    frame.dispose();
                }
            });
            buttonsPane.add(createClerkButton, c);

            // Joel: Make sure to check User has a password in the first place.
            JButton resetUserPassButton = setupSideBarButton("Reset User Password", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            resetUserPassButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ResetPasswordPage newFrame = new ResetPasswordPage();
                    frame.dispose();
                }
            });
            buttonsPane.add(resetUserPassButton, c);
        }
        if (user instanceof GuestInterface) {
            // Need to find checked in reservation either by date or kept on the Guest.
            JButton checkGuestBill = setupSideBarButton("Check My Bill", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            buttonsPane.add(checkGuestBill, c);
            checkGuestBill.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CombinedBillPage newFrame = new CombinedBillPage(false);
                    frame.dispose();
                }
            });

            JButton cancelReservationButton = setupSideBarButton("View My Reservations", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            buttonsPane.add(cancelReservationButton, c);
            cancelReservationButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    List<Reservation> reservationList = ReservationRepository.loadReservationsOfGuestId(((GuestInterface)user).getGuestId());
                    if (reservationList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no reservations attached to this guest Id");
                    }
                    else {
                        CancelReservationPage newFrame = new CancelReservationPage(((GuestInterface)user).getGuestId());
                        frame.dispose();
                    }
                }
            });

            JButton shopButton = setupSideBarButton("Shop", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            buttonsPane.add(shopButton, c);
            shopButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Whatever is shopped will be attatched to the reservation that fits within the current time (or the reservation thats the one thats checked in
                    List<Reservation> reservationList = ReservationRepository.loadReservationsOfGuestId(((GuestInterface)user).getGuestId());
                    if (reservationList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no reservations attached to this guest Id");
                    }
                    else {
                        ShoppingPage newFrame = new ShoppingPage();
                        frame.dispose();
                        throw new RuntimeException("TODO: Gavin needs to finish the Shopping");
                    }
                }
            });
        }
        else { // Not signed in
            // Need to find checked in reservation either by date or kept on the Guest.
            JButton checkGuestBill = setupSideBarButton("Check My Bill", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            buttonsPane.add(checkGuestBill, c);
            checkGuestBill.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CombinedBillPage newFrame = new CombinedBillPage(false);
                    frame.dispose();
                }
            });

            JButton cancelReservationButton = setupSideBarButton("View My Reservations", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            buttonsPane.add(cancelReservationButton, c);
            cancelReservationButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String response = JOptionPane.showInputDialog(null, "Enter your guest Id:", "View Reservations", JOptionPane.QUESTION_MESSAGE);
                    if (response != null) { // if was not canceled or not empty
                        response = (response.isEmpty()) ? "-1" : response;
                        List<Reservation> reservationList = ReservationRepository.loadReservationsOfGuestId(Integer.parseInt(response));
                        if (reservationList.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "There are no reservations attached to this guest Id");
                        } else {
                            CancelReservationPage newFrame = new CancelReservationPage(Integer.parseInt(response));
                            frame.dispose();
                        }
                    }
                }
            });

            JButton shopButton = setupSideBarButton("Shop", 4, 0);
            c.gridx = 0;
            c.gridy = buttonCount;
            buttonCount++;
            buttonsPane.add(shopButton, c);
            shopButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Whatever is shopped will be attatched to the reservation that fits within the current time (or the reservation  thats checked in based on how we implement it).
                    List<Reservation> reservationList = ReservationRepository.loadReservationsOfGuestId(((GuestInterface)user).getGuestId());
                    if (reservationList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There are no reservations attached to this guest Id");
                    }
                    else {
                        ShoppingPage newFrame = new ShoppingPage();
                        frame.dispose();
                        throw new RuntimeException("TODO: Gavin needs to finish the Shopping");
                    }
                }
            });
        }
        // Aligning buttons to top if there aren't enough buttons to fill JScrollPane.
        JButton fillButton = new JButton();
        SetupUI.makeButtonInvisible(fillButton);
        c.gridx = 0;
        c.gridy = buttonCount;
        c.weighty = 0.175; // rand number higher than 0 (default weight is 0)
        buttonsPane.add(fillButton, c);

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


        JLabel userImg = getScaledImage("src/main/resources/userImg.png", 0.2, 0.2);
        c.gridx = 0;
        c.gridy = 0;
        // The 2 below lines allow me to anchor it to the left - Although you may need to set at least one of them to have a weight higher than 0 for this to work.
        c.fill = GridBagConstraints.NONE; // Stops GridBagLayout from stretching horizontally to the container's width
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 5, 0);
        sidebarPanel.add(userImg, c);

        // JTextArea and not JLabel to allow for textWrapping
        JTextArea accountStatusTextArea = new JTextArea("NOT LOGGED IN");
        accountStatusTextArea.setLineWrap(true);       // Enable wrapping
        accountStatusTextArea.setWrapStyleWord(false); // ALlow wrapping in a word and not only on spaces.
        accountStatusTextArea.setOpaque(false);
        accountStatusTextArea.setEditable(false);
        accountStatusTextArea.setFont(new Font("Serif", Font.PLAIN, 18));
        accountStatusTextArea.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        if (UserRepository.getSessionAccount() != null) {
            accountStatusTextArea.setText("LOGGED IN AS: " + UserRepository.getSessionAccount().getName());
        }
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 10, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        sidebarPanel.add(accountStatusTextArea, c);

        JSeparator horizontalLine = new JSeparator(SwingConstants.HORIZONTAL);
        horizontalLine.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 30, 0);
        sidebarPanel.add(horizontalLine, c);

        JPanel buttonsPane = setupButtonsPane(frame);
        buttonsPane.setOpaque(false);
        JScrollPane buttonsScrollPane = new JScrollPane(buttonsPane);
        buttonsScrollPane.setPreferredSize(new Dimension(300, 400));
        buttonsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        buttonsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonsScrollPane.setBorder(null);
        buttonsScrollPane.setViewportBorder(null);
        buttonsScrollPane.getViewport().setOpaque(false);
        buttonsScrollPane.setOpaque(false);
        buttonsScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE; // Color of the draggable bar
                this.trackColor = ColorPalette.OCEAN_DARKBLUE; // Color of the background track
            }
        });
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 0, 0);
        sidebarPanel.add(buttonsScrollPane, c);

        JPanel searchPane = makeSearchBar();
        c.gridx = 0;
        c.gridy = 4;
        c.fill = GridBagConstraints.NONE;
        sidebarPanel.add(searchPane, c);

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
        JLabel menuButtonLabel = getScaledImage("src/main/resources/menuButton.png", 0.6, 0.6);
        menuButtonLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuButtonLabel.setName("MenuButton");
        JPanel sideBarPane = setupPopup(frame);
        popupPane.add(sideBarPane, BorderLayout.LINE_START);
        sideBarPane.setVisible(false);
        menuButtonLabel.addMouseListener(new MouseAdapter() { // JLabel doesnt have actionListener
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("test menuButton was clicked!");
                if (sideBarPane.isVisible()) {
                    sideBarPane.setVisible(false);
                    sideBarPane.repaint();
                }
                else sideBarPane.setVisible(true);
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
                SearchRoomPage2 newFrame = new SearchRoomPage2();
//                BookingPage newFrame = new BookingPage(new ArrayList<Room>());
//                ReservationSuccessPage newFrame = new ReservationSuccessPage(102043040, 510.99, Calendar.getInstance(), Calendar.getInstance());

                frame.dispose();
            }
        });
        c.gridy = 0;
        c.gridx = 2;
        c.weightx = 0.1;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        headerPane.add(bookRoomButton, c);

        return new Object[] {popupPane, mainPane, headerPane};
    }
}
