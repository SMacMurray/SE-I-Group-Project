package stay_and_shop_system.user.ui;

import stay_and_shop_system.Main;
import stay_and_shop_system.occupancy.*;
import stay_and_shop_system.user.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CancelReservationPage extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel mainPane;
    private JPanel reservationPane;
    private JPanel reservationInfoPane;
    private JPanel buttonsPane;


    public void loadGuestReservations(GuestInterface guest) {
        List<Reservation> reservations = guest.findReservations();
    }
    public CancelReservationPage(GuestInterface guest) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPane = new JPanel(new BorderLayout());
        setSize(Main.WINDOW_W, Main.WINDOW_H);
        setContentPane(mainPane);
        setTitle(Main.APP_TITLE);
        setLocationRelativeTo(null);

        ImageIcon anonIcon = new ImageIcon("src/main/resources/theAnon.png");
        JLabel anonImg = new JLabel(new ImageIcon(anonIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)) );
        mainPane.add(anonImg, BorderLayout.PAGE_START);

        reservationPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        mainPane.add(reservationPane, BorderLayout.CENTER);

        buttonsPane = new JPanel(new GridBagLayout());
        JScrollPane buttonScrollPane = new JScrollPane(buttonsPane);
        c.gridy = 0;
        c.gridx = 0;
        reservationPane.add(buttonScrollPane, c);

        reservationInfoPane = new JPanel(new GridBagLayout());
        c.gridy = 0;
        c.gridx = 1;
        reservationPane.add(reservationInfoPane, c);

        loadGuestReservations(guest);

        setVisible(true);


    }
}
