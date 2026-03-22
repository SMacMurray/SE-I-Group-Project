import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class StorePage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public StorePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(Main.WINDOW_OFFSET_X, Main.WINDOW_OFFSET_Y, Main.WINDOW_W, Main.WINDOW_H);
        setTitle(Main.APP_TITLE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton homeButton = new JButton(Main.HOME_TEXT);
        homeButton.setBounds(0, 0, 140, 22);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePage newFrame = new HomePage(); //Opening the second JFrame
                dispose(); //Disposing the First JFrame
            }
        });
        contentPane.add(homeButton);

        JLabel lblNewLabel = new JLabel("Storefront goes here");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(112, 31, 416, 22);
        contentPane.add(lblNewLabel);

        // This component may need to be changed into something that can actually be entered.
        JTextField searchField = new JTextField();
        searchField.setText("Search...");
        searchField.setBounds(150, 1, 159, 20);
        contentPane.add(searchField);

        JButton btnNewButton = new JButton("View Cart");
        btnNewButton.setBounds(516, 0, 88, 22);
        contentPane.add(btnNewButton);

        setVisible(true);
    }
}