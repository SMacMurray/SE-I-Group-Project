package stay_and_shop_system.user.ui;

import stay_and_shop_system.HomePage;
import stay_and_shop_system.Main;
import stay_and_shop_system.user.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserHomePage extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JPanel userPane;
    private static JPanel buttonsPane;


    private void addRelevantUserButtons() {
        int buttonCount = 0;
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        User user = new GuestClerk("Johnny Test", "johnnyTest@gmail.com");

        if (user instanceof ClerkInterface) {
            JButton addRoomButton = new JButton("Add Room");
            c.gridx = 1;
            c.gridy = buttonCount;
            addRoomButton.setBorderPainted(false);
            buttonsPane.add(addRoomButton, c);
            buttonCount++;

            JButton viewRoomsButton = new JButton("View Rooms' Statuses");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(viewRoomsButton, c);
            buttonCount++;

            JButton modifyRoomButton = new JButton("Modify Room");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(modifyRoomButton, c);
            buttonCount++;

            JButton modifyMyInformationButton = new JButton("Modify My Information");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(modifyMyInformationButton, c);
            buttonCount++;

            JButton modifyReservationButton = new JButton("Modify Guest Reservation");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(modifyReservationButton, c);
            buttonCount++;

            // Make a JPanel that prompts for a user's name to search for.
            JButton cancelReservationButton = new JButton("Cancel Guest Reservation");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(cancelReservationButton, c);
            buttonCount++;
            cancelReservationButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CancelReservationPage newFrame = new CancelReservationPage((GuestInterface)user);
                    dispose();
                }
            });

            JButton checkGuestBill = new JButton("Check Guest's Bill");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(checkGuestBill, c);
            buttonCount++;

        }
        if (user instanceof AdminInterface) {

        }
        if (user instanceof GuestInterface) {
            JButton checkGuestBill = new JButton("Check My Bill");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(checkGuestBill, c);
            buttonCount++;

            JButton modifyReservationButton = new JButton("Modify My Reservation");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(modifyReservationButton, c);
            buttonCount++;

            JButton cancelReservationButton = new JButton("Cancel My Reservation");
            c.gridx = 1;
            c.gridy = buttonCount;
            buttonsPane.add(cancelReservationButton, c);
            buttonCount++;
            cancelReservationButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CancelReservationPage newFrame = new CancelReservationPage((GuestInterface)user);
                    dispose();
                }
            });
        }
    }
	public UserHomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Main.WINDOW_W, Main.WINDOW_H);
        setTitle(Main.APP_TITLE);
        setLocationRelativeTo(null);
        mainPane = new JPanel();
        setContentPane(mainPane);
        mainPane.setLayout(new BorderLayout());

        userPane = new JPanel();
        userPane.setLayout(new GridBagLayout());
        mainPane.add(userPane, BorderLayout.CENTER);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;

        JButton homeButton = new JButton(Main.HOME_TEXT);
        homeButton.setBounds(0, 0, 140, 22);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePage newFrame = new HomePage(); //Opening the second JFrame
                dispose(); //Disposing the First JFrame
            }
        });
        JPanel alignHomePane = new JPanel();
        alignHomePane.setLayout(new FlowLayout(FlowLayout.LEFT));
        alignHomePane.add(homeButton);
        mainPane.add(alignHomePane, BorderLayout.PAGE_START);




        ImageIcon anonIcon = new ImageIcon("src/main/resources/theAnon.png");
        JLabel anonImg = new JLabel(anonIcon);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        // If the actual amount of grids across y axis is less than this, nothing bad happens
        c.insets = new Insets(0,0,0,10);
        userPane.add(anonImg, c);


        buttonsPane = new JPanel();
        buttonsPane.setLayout(new GridBagLayout());
        JScrollPane buttonScrollPane = new JScrollPane(buttonsPane);
        buttonScrollPane.setBorder(BorderFactory.createEmptyBorder());
        buttonScrollPane.setPreferredSize(new Dimension(250, 250));
        buttonScrollPane.setViewportView(buttonsPane);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,0);
        userPane.add(buttonScrollPane, c);
        addRelevantUserButtons();

        setVisible(true);
	}
}
