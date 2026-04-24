package stay_and_shop_system.user.ui;

import stay_and_shop_system.ColorPalette;
import stay_and_shop_system.Main;
import stay_and_shop_system.SetupUI;
import stay_and_shop_system.occupancy.*;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.ui.SearchRoomPage2;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

public class CancelReservationPage extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel contentPane;
    private JPanel headerPane;
    private Reservation reservation;
    private JPanel modifyDataPane;
    private JPanel reservationDataPanel;
    private JPanel centerWrapper;

    private JButton modifySearchB;
    private JTextField reservationGuestNumberJTF;
    private JTextField reservationGuestNameJTF;
    private JTextField reservationGuestEmailJTF;
    private JTextField reservationCCNJTF;

    private ReservationController res = new ReservationController();

    private int guestId = 0;
    Object[] reservationData;

    public JTextArea createStyledJTextArea(String text) {
        JTextArea jta = new JTextArea(text);
        jta.setLineWrap(true);       // Enable wrapping
        //jta.setWrapStyleWord(false); // ALlow wrapping in a word and not only on spaces.
        jta.setOpaque(false);
        jta.setEditable(false);

        return jta;
    }
    public void createModifyDataPanel() {
        modifyDataPane = new JPanel(new GridBagLayout());
        modifyDataPane.setOpaque(false);
        modifyDataPane.setPreferredSize(new Dimension(400, 600));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel reservationTitleJL = new JLabel("");
        reservationTitleJL.setFont(new Font("Serif", Font.ITALIC, 30));
        reservationTitleJL.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(60, 0, 0, 0);
        modifyDataPane.add(reservationTitleJL, c);
        JLabel reservationRoomSearchJL = new JLabel("Search For a Room: ");
        reservationRoomSearchJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationRoomSearchJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(40, 0, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.anchor = GridBagConstraints.LINE_START;
        modifyDataPane.add(reservationRoomSearchJL, c);
        modifySearchB = new JButton("Room Number " + reservation.getRoomNumber());
        modifySearchB.setName(Integer.toString(reservation.getRoomNumber()));
        modifySearchB.setFont(new Font("Serif", Font.PLAIN, 18));
        modifySearchB.setForeground(ColorPalette.OCEAN_BLUE);
        modifySearchB.setBackground(ColorPalette.OCEAN_DARKBLUE);
        modifySearchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservationData = new Object[] { reservation, Integer.parseInt(reservationGuestNumberJTF.getText()),
                                                reservationGuestNameJTF.getText(), reservationGuestEmailJTF.getText(),
                                                reservationCCNJTF.getText(), null, guestId};
                SearchRoomPage2 newFrame = new SearchRoomPage2(reservationData);
                dispose();
            }
        });
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(40, 0, 0, 0);
        modifyDataPane.add(modifySearchB, c);
//        c.anchor = GridBagConstraints.LINE_START;
        JLabel reservationGuestNumberJL = new JLabel("Guest Number: ");
        reservationGuestNumberJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationGuestNumberJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        modifyDataPane.add(reservationGuestNumberJL, c);
        reservationGuestNumberJTF = new JTextField(16);
        reservationGuestNumberJTF.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        modifyDataPane.add(reservationGuestNumberJTF, c);
        JLabel reservationGuestNameJL = new JLabel("Guest Name: ");
        reservationGuestNameJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationGuestNameJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 4;
        modifyDataPane.add(reservationGuestNameJL, c);
        reservationGuestNameJTF = new JTextField(16);
        reservationGuestNameJTF.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        c.gridx = 1;
        c.gridy = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        modifyDataPane.add(reservationGuestNameJTF, c);
        JLabel reservationGuestEmailJL = new JLabel("Guest Email: ");
        reservationGuestEmailJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationGuestEmailJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 5;
        modifyDataPane.add(reservationGuestEmailJL, c);
        reservationGuestEmailJTF = new JTextField(16);
        reservationGuestEmailJTF.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        c.gridx = 1;
        c.gridy = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        modifyDataPane.add(reservationGuestEmailJTF, c);
        JLabel reservationCCNJL = new JLabel("Credit Card Number: ");
        reservationCCNJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationCCNJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 6;
        modifyDataPane.add(reservationCCNJL, c);
        reservationCCNJTF = new JTextField(16);
        reservationCCNJTF.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        c.gridx = 1;
        c.gridy = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        modifyDataPane.add(reservationCCNJTF, c);
        JButton modifyCancelB = new JButton("Cancel Changes");
        modifyCancelB.setFont(new Font("Serif", Font.PLAIN, 20));
        modifyCancelB.setForeground(ColorPalette.OCEAN_BLUE);
        modifyCancelB.setBackground(ColorPalette.OCEAN_DARKBLUE);
        modifyCancelB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerWrapper.remove(modifyDataPane);
                centerWrapper.add(reservationDataPanel, BorderLayout.CENTER);
                contentPane.revalidate();
                contentPane.repaint();
            }
        });
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.insets = new Insets(100, 0, 0, 0);
        modifyDataPane.add(modifyCancelB, c);
        JButton modifyChangeB = new JButton("Change Reservation");
        modifyChangeB.setFont(new Font("Serif", Font.PLAIN, 20));
        modifyChangeB.setForeground(ColorPalette.OCEAN_BLUE);
        modifyChangeB.setBackground(ColorPalette.OCEAN_DARKBLUE);
        modifyChangeB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to change this reservation?",
                        "Change Reservation?",
                        JOptionPane.YES_NO_OPTION
                );
                if (choice == JOptionPane.YES_OPTION) {
                    boolean changeEmail = false;
                    if (!reservationGuestEmailJTF.getText().equals(reservation.getGuestEmail())) {
                        int emailChoice = JOptionPane.showConfirmDialog(
                                null,
                                "Are you sure you want to change the email? This will cause the guest Id to change.",
                                "Change Email?",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (emailChoice == JOptionPane.NO_OPTION) {
                            return;
                        }
                        changeEmail = true;
                    }

                    int roomNumber = Integer.parseInt(modifySearchB.getName());
                    Reservation r;
                    try {
                        r = res.modifyReservation(reservation, roomNumber, Integer.parseInt(reservationGuestNumberJTF.getText()), reservationGuestNameJTF.getText(),
                                reservationGuestEmailJTF.getText(), reservationCCNJTF.getText()
                        );
                    }
                    catch (IllegalArgumentException exp) {
                        JOptionPane.showMessageDialog(null, exp.getMessage());
                        return;
                    }

                    if (changeEmail) {
                        JOptionPane.showMessageDialog(null, "New Guest Id: " + r.getGuestId());
                    }

                    CancelReservationPage newFrame = new CancelReservationPage(guestId, null);
                    dispose();
                }
            }
        });
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.insets = new Insets(15, 0, 0, 0);
        modifyDataPane.add(modifyChangeB, c);
        JButton fillButton1 = new JButton(); // to force the components to align to the top
        SetupUI.makeButtonInvisible(fillButton1);
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        c.weighty = 1;
        c.insets = new Insets(0, 0, 0, 0);
        modifyDataPane.add(fillButton1, c);

        // Room Search
        // Guest Num - Exception where num > rommMaxOcc
        // GuestName
        // GuestEmail - Alert, changing your email will change your guestId for this reservation. Better to not let them change it because will cause to have to search multiple reservations by certain guestIds each.
        // Credit Card Numbert
        // When Clerk Changes their email, will cause guestId to change

        reservationTitleJL.setText("RESERVATION AT ROOM " + reservation.getRoomNumber());
        String roomFloor;
//        reservationRoomSearchJL.setText("");
        reservationGuestNumberJTF.setText(Integer.toString(reservation.getGuestNumber()));
        reservationGuestNameJTF.setText(reservation.getGuestName());
        reservationGuestEmailJTF.setText(reservation.getGuestEmail());
        reservationCCNJTF.setText(reservation.getCreditCardNumber());
    }
    public CancelReservationPage(int gId, Object[] reData) {
        this.guestId = gId;
        reservationData = reData;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setTitle(Main.APP_TITLE);

        Object[] uiObjects = SetupUI.initializeScreen(popupPane, mainPane, this);
        popupPane = (JPanel) uiObjects[0];
        mainPane = (JPanel) uiObjects[1];
        headerPane = (JPanel) uiObjects[2];

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(ColorPalette.OCEAN_BLUE);
        mainPane.add(contentPane);

        centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        JSeparator verticalLine = new JSeparator(SwingConstants.VERTICAL);
        verticalLine.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        contentPane.add(verticalLine, BorderLayout.CENTER);
        centerWrapper.add(verticalLine, BorderLayout.LINE_START);

        // View Reservation Info
        reservationDataPanel = new JPanel(new GridBagLayout());
        reservationDataPanel.setOpaque(false);
        reservationDataPanel.setPreferredSize(new Dimension(400, 600));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel reservationTitleJL = new JLabel();
        reservationTitleJL.setFont(new Font("Serif", Font.ITALIC, 30));
        reservationTitleJL.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(60, 0, 0, 0);
        reservationDataPanel.add(reservationTitleJL, c);
        JLabel reservationRoomFloorJL = new JLabel();
        reservationRoomFloorJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationRoomFloorJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(40, 0, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.anchor = GridBagConstraints.LINE_START;
        reservationDataPanel.add(reservationRoomFloorJL, c);
        JLabel reservationStartDateJL = new JLabel();
        reservationStartDateJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationStartDateJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        reservationDataPanel.add(reservationStartDateJL, c);
        JLabel reservationEndDateJL = new JLabel();
        reservationEndDateJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationEndDateJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 3;
        reservationDataPanel.add(reservationEndDateJL, c);
        JLabel reservationGuestCountJL = new JLabel();
        reservationGuestCountJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationGuestCountJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 4;
        reservationDataPanel.add(reservationGuestCountJL, c);
        JLabel reservationRateJL = new JLabel();
        reservationRateJL.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationRateJL.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        c.gridx = 0;
        c.gridy = 5;
        reservationDataPanel.add(reservationRateJL, c);
        JButton reservationCancelB = new JButton("");
        reservationCancelB.setVisible(false);
        reservationCancelB.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationCancelB.setForeground(ColorPalette.OCEAN_BLUE);
        reservationCancelB.setBackground(ColorPalette.OCEAN_DARKBLUE);
        reservationCancelB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to cancel this reservation? You will be deducted $2.50 in cancellation fees.",
                        "Cancel Reservation?",
                        JOptionPane.YES_NO_OPTION
                );
                if (choice == JOptionPane.YES_OPTION) {
                    ReservationRepository.deleteReservation(reservation);
                    CancelReservationPage newFrame = new CancelReservationPage(guestId, null);
                    dispose();
                }
            }
        });
        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(150, 0, 0, 0);
        reservationDataPanel.add(reservationCancelB, c);

        // Making modifyDataPane
        modifyDataPane = new JPanel();

        JButton reservationModifyB = new JButton("Modify Reservation?");
        reservationModifyB.setVisible(false);
        reservationModifyB.setFont(new Font("Serif", Font.PLAIN, 20));
        reservationModifyB.setForeground(ColorPalette.OCEAN_BLUE);
        reservationModifyB.setBackground(ColorPalette.OCEAN_DARKBLUE);
        reservationModifyB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerWrapper.remove(reservationDataPanel);
                createModifyDataPanel();
                centerWrapper.add(modifyDataPane, BorderLayout.CENTER);

                centerWrapper.revalidate();
                centerWrapper.repaint();
                contentPane.revalidate();
                contentPane.repaint();
            }
        });
        c.gridx = 0;
        c.gridy = 7;
        c.insets = new Insets(25, 0, 0, 0);
        c.weighty = 0;
        reservationDataPanel.add(reservationModifyB, c);
        JButton fillButton1 = new JButton();
        fillButton1.setOpaque(false);
        fillButton1.setContentAreaFilled(false);
        fillButton1.setBorderPainted(false);
        fillButton1.setFocusPainted(false);
        c.gridx = 0;
        c.gridy = 8;
        c.insets = new Insets(0, 0, 0, 0);
        c.weighty = 0.5; // any number higher than 0 will work since the others are at 0;
        reservationDataPanel.add(fillButton1, c);



//        centerWrapper.add(reservationDataPanel, BorderLayout.CENTER);
        contentPane.add(centerWrapper, BorderLayout.CENTER);

        // Making JPanels not overlap when showing menubutton
        SetupUI.alterMenuButtonMouseEvent(headerPane, new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e); // This keeps the behavior previously defined of the MenuButton
                if (reservationDataPanel.isVisible()) {
                    reservationDataPanel.setVisible(false);
                } else reservationDataPanel.setVisible(true);
            }
        });

        // Setting the left side of the contentPane
        JPanel reservationsWrapper = new JPanel(new GridBagLayout()); // To make reservations pane it's actual size since BorderLayout doenst respect setPrefferedSize()
        JPanel reservationsPane = new JPanel(new GridBagLayout());
        reservationsPane.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        int buttonsCount = 0;
        for (Reservation r : ReservationRepository.loadReservationsOfGuestId(guestId)) {
            System.out.println("FOUND RESERVATION");
            JButton reservationButton = new JButton("Reservation at room " + r.getRoomNumber());
            reservationButton.setHorizontalAlignment(SwingConstants.LEFT);
            reservationButton.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
            reservationButton.setFont(new Font("Serif", Font.PLAIN, 23));
            reservationButton.setBackground(ColorPalette.SATURATED_BLUE);
            reservationButton.setPreferredSize(new Dimension(300, 50));
            reservationButton.setName(Integer.toString(r.getReservationId()));
            reservationButton.setActionCommand(Integer.toString(r.getReservationId()));
            c.gridx = 0;
            c.gridy = buttonsCount;
            c.weighty = 0;
            buttonsCount++;
            reservationsPane.add(reservationButton, c);

            reservationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    centerWrapper.remove(modifyDataPane);


                    reservation = ReservationRepository.loadReservationOfId(Integer.parseInt(e.getActionCommand()));
                    reservationTitleJL.setText("RESERVATION AT ROOM " + reservation.getRoomNumber());
                    String roomFloor;
                    if (reservation.getRoomNumber() >= 100 && reservation.getRoomNumber() < 200) roomFloor = "Nature Retreat";
                    else if (reservation.getRoomNumber() >= 200 && reservation.getRoomNumber() < 300) roomFloor = "Urban Elegance";
                    else  roomFloor = "Vintage Charm";
                    reservationRoomFloorJL.setText("Room Floor: " + roomFloor);
                    // Startdate
                    reservationStartDateJL.setText("Start Date: " + res.getDateFormatter().format(reservation.getStartDate().getTime()));
                    reservationEndDateJL.setText("End Date: " + res.getDateFormatter().format(reservation.getEndDate().getTime()));
                    reservationGuestCountJL.setText("Amount of Guests: " + reservation.getGuestNumber());
                    reservationRateJL.setText("Rate: " + reservation.getRate());
                    reservationCancelB.setVisible(true);
                    reservationModifyB.setVisible(true);

                    if (!reservation.getStartDate().before(Calendar.getInstance())) {
                        reservationCancelB.setText("Cancel Reservation?");
                        reservationCancelB.setEnabled(true);
                    }
                    else {
                        reservationCancelB.setText("Can't Cancel Reservation");
                        reservationCancelB.setEnabled(false);
                    }

                    centerWrapper.add(reservationDataPanel, BorderLayout.CENTER);
                    contentPane.revalidate();
                    contentPane.repaint();
                    // Enddate
                    // Amount of Guests
                    // rate
                    // cost
                    // Cancel Room Button

                }
            });
        }
        JButton fillButton2 = new JButton(); // to force the buttons to align to the top
        fillButton2.setName("0");
        c.gridx = 0;
        c.gridy = buttonsCount;
        c.weighty = 0.5; // any number higher than 0 will work since the others are at 0;
        fillButton2.setOpaque(false);
        fillButton2.setContentAreaFilled(false);
        fillButton2.setBorderPainted(false);
        fillButton2.setFocusPainted(false);
        reservationsPane.add(fillButton2, c);
        JScrollPane reservationsScrollPane = new JScrollPane(reservationsPane);
        reservationsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reservationsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        reservationsScrollPane.setBackground(ColorPalette.DESATURATED_DARKBLUE);
        reservationsScrollPane.getViewport().setBackground(ColorPalette.DESATURATED_DARKBLUE);
        reservationsScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE; // Color of the draggable bar
                this.trackColor = ColorPalette.OCEAN_DARKBLUE; // Color of the background track
            }
        });
        reservationsScrollPane.setPreferredSize(new Dimension(300, 550)); // May have to set the size of the scroll pane instead of teh content in it if you want a specific size. It worked better for me with GridLayout
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0; // any number higher than 0 will work since the others are at 0;
        c.fill = GridBagConstraints.NONE;
        reservationsWrapper.add(reservationsScrollPane, c);
        // Don't set the "padding" on the JScrollPane, set it on its wrapper since that is what is interacting with
        // the other components in the BorderLayout
        reservationsWrapper.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 80));
        reservationsWrapper.setOpaque(false);
        contentPane.add(reservationsWrapper, BorderLayout.LINE_START);


        if (reservationData != null) {
            for (Component btn : reservationsPane.getComponents()) {
                if (btn.getName() != null) {
                    if (btn instanceof JButton && Integer.parseInt(btn.getName()) == ((Reservation) reservationData[0]).getReservationId()) {
                        System.out.println(btn.getName() + " Btn name");
                        ((JButton) btn).doClick();
                        reservationModifyB.doClick();

                        modifySearchB.setText("Room " + reservationData[5]);
                        modifySearchB.setName(Integer.toString((int) reservationData[5]));
                        reservationGuestNumberJTF.setText(Integer.toString((int) reservationData[1]));
                        reservationGuestNameJTF.setText((String) reservationData[2]);
                        reservationGuestEmailJTF.setText((String) reservationData[3]);
                        reservationCCNJTF.setText((String) reservationData[4]);

                    }
                }
            }
        }


        setVisible(true);


    }
}
