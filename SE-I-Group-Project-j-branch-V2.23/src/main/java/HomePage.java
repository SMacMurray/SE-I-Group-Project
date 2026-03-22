import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HomePage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public HomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, Main.WINDOW_W, Main.WINDOW_H);
        setTitle(Main.APP_TITLE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to my hotel storefront.");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(110, 11, 416, 22);
        contentPane.add(welcomeLabel);

        JButton homeButton = new JButton(Main.HOME_TEXT);
        homeButton.setBounds(0, 0, 140, 22);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePage newFrame = new HomePage(); //Opening the second JFrame
                dispose(); //Disposing the First JFrame
            }
        });
        contentPane.add(homeButton);

        int cat_w = 200;
        int cat_h = 200;
        JLabel testPicture = new JLabel("");
        testPicture.setIcon(new ImageIcon(Main.class.getResource("iphone_ringtone_cat.jpg")));
        testPicture.setBounds((Main.WINDOW_W - cat_w) / 2, 100, cat_w, cat_h);
        contentPane.add(testPicture);

        JButton bookingButton = new JButton("Find a Room");
        bookingButton.setBounds(10, 63, 122, 22);
        bookingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	SearchRoomPage newFrame = new SearchRoomPage(); //Opening the second JFrame
                dispose(); //Disposing the First JFrame
            }
        });
        contentPane.add(bookingButton);

        JButton storeButton = new JButton("HOT SHOPPING DEALS");
        storeButton.setBounds(231, 63, 186, 22);
        storeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StorePage newFrame = new StorePage(); //Opening the second JFrame
                dispose(); //Disposing the First JFrame
            }
        });
        contentPane.add(storeButton);

        JButton loginButton = new JButton("Sign Up or Login");
        loginButton.setBounds(483, 63, 133, 22);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginPage newFrame = new LoginPage(); //Opening the second JFrame
                dispose(); //Disposing the First JFrame
            }
        });
        contentPane.add(loginButton);

        setVisible(true);
    }

}