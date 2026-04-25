package stay_and_shop_system.occupancy.ui;

import org.jdesktop.swingx.JXTextField;
import stay_and_shop_system.ColorPalette;
import stay_and_shop_system.HomePage2;
import stay_and_shop_system.Main;
import stay_and_shop_system.SetupUI;
import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.ReservationController;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.ui.RoundJButton;
import stay_and_shop_system.ui.RoundJPasswordField;
import stay_and_shop_system.ui.RoundJTextField;
import stay_and_shop_system.user.AccountController;
import stay_and_shop_system.user.UserRepository;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CheckInPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel contentPane;
    private JLabel invalidLabel;
    private JPanel pagePane;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

    static private ReservationController rc = new ReservationController();

    static final String GUEST_ID_FIELD = "Guest ID";
    static final String RESERVATION_ID_FIELD = "Reservation ID";
    static final String DATE_FIELD = "(yyyy/MM/dd)";
    static final String GUEST_ID_EMPTY = "Warning: guest ID field is empty.";
    static final String RESERVATION_ID_EMPTY = "Warning: reservation ID field is empty.";
    static final String DATE_EMPTY = "Warning: date field is empty.";
    static final String LOGIN_SUCCESS = "Successfully logged in! Redirecting you to homepage.";
    static final String LOGIN_FAILURE = "The username and/or password you have entered are incorrect.";
    static final String ACCOUNT_CREATE_SUCCESS = "Account created successfully! You are now logged in.";
    static final String ACCOUNT_ALREADY_EXISTS = "Account already exists! Please Sign In instead.";
    static final String FUBAR = "Whoops! Something went catastrophically wrong.";
    static final String RESPONSE_MISSING = "Missing Text";

    /**
     * Create the frame.
     */
    public CheckInPage() {
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

        JLabel welcomeLabel = new JLabel("Check In or Check Out a Guest");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(ColorPalette.OCEAN_DARKBLUE);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomeLabel.setPreferredSize(new Dimension(500, 40));
        contentPane.add(welcomeLabel, c);

        JXTextField guestIdField = new RoundJTextField(25);
        guestIdField.setPrompt(GUEST_ID_FIELD);
        guestIdField.setPreferredSize(new Dimension(200, 30));
        guestIdField.setBackground(contentPane.getBackground());
        contentPane.add(guestIdField, c);

        JXTextField reservationIdField = new RoundJPasswordField(25);
        reservationIdField.setPrompt(RESERVATION_ID_FIELD);
        reservationIdField.setPreferredSize(new Dimension(200, 30));
        reservationIdField.setBackground(contentPane.getBackground());
        contentPane.add(reservationIdField, c);

        JXTextField dateField = new RoundJTextField(25);
        dateField.setPrompt(DATE_FIELD);
        dateField.setPreferredSize(new Dimension(200, 30));
        dateField.setBackground(contentPane.getBackground());
        contentPane.add(dateField, c);

        JSeparator horizontalLine = new JSeparator(SwingConstants.HORIZONTAL);
        horizontalLine.setPreferredSize(new Dimension(400, 10));
        horizontalLine.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        contentPane.add(horizontalLine, c);

        JButton checkInButton = new RoundJButton(25);
        checkInButton.setText("Check In");
        checkInButton.setPreferredSize(new Dimension(100, 30));
        checkInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int guestId = 0;
                int reservationId = 0;
                Reservation reservation = null;
                Calendar checkInDate = Calendar.getInstance();
                String dialogMessage = null;

                try { guestId = Integer.parseInt(guestIdField.getText().trim()); }
                catch (NumberFormatException ex) { dialogMessage = "Invalid Guest ID"; }
                try { reservationId = Integer.parseInt(reservationIdField.getText().trim()); }
                catch (NumberFormatException ex) { dialogMessage = "Invalid Reservation ID"; }
                try { checkInDate.setTime(dateFormatter.parse(dateField.getText().trim())); }
                catch (ParseException ex) { dialogMessage = "Invalid Date"; }

                if (dialogMessage == null) {
                    reservation = ReservationRepository.loadReservationOfId(reservationId);
                    if (reservation == null) {
                        dialogMessage = "Reservation Not Found";
                    }
                    else {
                        try {
                            rc.checkIn(reservation, checkInDate, guestId);
                            dialogMessage = "Guest successfully checked in!";
                        }
                        catch (IllegalArgumentException ex) {
                            dialogMessage = "Invalid Guest ID";
                        }
                    }
                }
                JOptionPane.showMessageDialog(contentPane, dialogMessage);
            }
        });
        contentPane.add(checkInButton, c);

        JButton checkOutButton = new RoundJButton(25);
        checkOutButton.setText("Check Out");
        checkOutButton.setPreferredSize(new Dimension(100, 30));
        checkOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int guestId = 0;
                int reservationId = 0;
                Reservation reservation;
                double cost = 0;
                String dialogMessage = null;

                try { guestId = Integer.parseInt(guestIdField.getText().trim()); }
                catch (NumberFormatException ex) { dialogMessage = "Invalid Guest ID"; }
                try { reservationId = Integer.parseInt(reservationIdField.getText().trim()); }
                catch (NumberFormatException ex) { dialogMessage = "Invalid Reservation ID"; }

                if (dialogMessage == null) {
                    reservation = ReservationRepository.loadReservationOfId(reservationId);
                    if (reservation == null) {
                        dialogMessage = "Reservation Not Found";
                    }
                    else {
                        try {
                            cost = rc.checkOut(reservation, guestId);
                            dialogMessage = "Guest successfully checked out! Your bill is $" + cost;
                        }
                        catch (IllegalArgumentException ex) {
                            dialogMessage = "Invalid Guest ID";
                        }
                    }
                }
                JOptionPane.showMessageDialog(contentPane, dialogMessage);
            }
        });
        contentPane.add(checkOutButton, c);


        JPanel footer = new JPanel();
        footer.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        footer.setPreferredSize(new Dimension(500, 100));
        mainPane.add(footer, BorderLayout.SOUTH);

        mainPane.add(contentPane, BorderLayout.CENTER);
        setVisible(true);
    }
}