package stay_and_shop_system.user.ui;

import stay_and_shop_system.ColorPalette;
import stay_and_shop_system.Main;
import stay_and_shop_system.SetupUI;
import stay_and_shop_system.occupancy.*;
import stay_and_shop_system.user.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.List;

public class CancelReservationPage extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel contentPane;
    private JPanel headerPane;
    private Reservation cancelR;

    private ReservationService res = new ReservationService();

    public JTextArea createStyledJTextArea(String text) {
        JTextArea jta = new JTextArea(text);
        jta.setLineWrap(true);       // Enable wrapping
        //jta.setWrapStyleWord(false); // ALlow wrapping in a word and not only on spaces.
        jta.setOpaque(false);
        jta.setEditable(false);

        return jta;
    }
    public CancelReservationPage(GuestInterface guest) {
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

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        JSeparator verticalLine = new JSeparator(SwingConstants.VERTICAL);
        verticalLine.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        contentPane.add(verticalLine, BorderLayout.CENTER);
        centerWrapper.add(verticalLine, BorderLayout.LINE_START);

        JPanel reservationDataPanel = new JPanel(new GridBagLayout());
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
                        "Do you want to delete this reservation?",
                        "Delete Reservation?",
                        JOptionPane.YES_NO_OPTION
                );
                if (choice == JOptionPane.YES_OPTION) {
                    res.deleteReservation(cancelR);
                    CancelReservationPage newFrame = new CancelReservationPage(guest);
                    dispose();
                }
            }
        });
        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(150, 0, 0, 0);
        reservationDataPanel.add(reservationCancelB, c);
        JButton fillButton1 = new JButton(); // to force the components to align to the top
        c.gridx = 0;
        c.gridy = 7;
        c.insets = new Insets(0, 0, 0, 0);
        c.weighty = 0.5; // any number higher than 0 will work since the others are at 0;
        fillButton1.setOpaque(false);
        fillButton1.setContentAreaFilled(false);
        fillButton1.setBorderPainted(false);
        fillButton1.setFocusPainted(false);
        reservationDataPanel.add(fillButton1, c);
        centerWrapper.add(reservationDataPanel, BorderLayout.CENTER);
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
        // TODO: LOAD RESERVATIONS BY GUESTID.
        for (Reservation r : res.findReservationsOfGuest(guest)) {
            // TODO: Finish findReservationsOfGuest()
            System.out.println("FOUND RESERVATION");
            JButton reservationButton = new JButton("Reservation at room " + r.getRoomNumber());
            reservationButton.setHorizontalAlignment(SwingConstants.LEFT);
            reservationButton.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
            reservationButton.setFont(new Font("Serif", Font.PLAIN, 23));
            reservationButton.setBackground(ColorPalette.SATURATED_BLUE);
            reservationButton.setPreferredSize(new Dimension(300, 50));
            reservationButton.setActionCommand(Integer.toString(r.getReservationId()));
            c.gridx = 0;
            c.gridy = buttonsCount;
            c.weighty = 0;
            buttonsCount++;
            reservationsPane.add(reservationButton, c);

            reservationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelR = res.getReservationOfId(Integer.parseInt(e.getActionCommand()));
                    reservationTitleJL.setText("RESERVATION AT ROOM " + cancelR.getRoomNumber());
                    String roomFloor;
                    if (cancelR.getRoomNumber() >= 100 && cancelR.getRoomNumber() < 200) roomFloor = "Nature Retreat";
                    else if (cancelR.getRoomNumber() >= 200 && cancelR.getRoomNumber() < 300) roomFloor = "Urban Elegance";
                    else  roomFloor = "Vintage Charm";
                    reservationRoomFloorJL.setText("Room Floor: " + roomFloor);
                    // Startdate
                    reservationStartDateJL.setText("Start Date: " + res.getDateFormatter().format(cancelR.getStartDate().getTime()));
                    reservationEndDateJL.setText("End Date: " + res.getDateFormatter().format(cancelR.getEndDate().getTime()));
                    reservationGuestCountJL.setText("Amount of Guests: " + cancelR.getGuestNumber());
                    reservationRateJL.setText("Rate: " + cancelR.getRate());
                    reservationCancelB.setVisible(true);

                    if (!cancelR.getStartDate().before(Calendar.getInstance())) {
                        reservationCancelB.setText("Cancel Reservation?");
                        reservationCancelB.setEnabled(true);
                    }
                    else {
                        reservationCancelB.setText("Can't Cancel Reservation");
                        reservationCancelB.setEnabled(false);
                    }
                    // Enddate
                    // Amount of Guests
                    // rate
                    // cost
                    // Cancel Room Button

                }
            });
        }
        JButton fillButton2 = new JButton(); // to force the buttons to align to the top
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




        setVisible(true);


    }
}
