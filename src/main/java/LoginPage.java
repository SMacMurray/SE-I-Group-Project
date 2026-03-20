import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import java.sql.*;

public class LoginPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel invalidLabel;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;

    /**
     * Create the frame.
     */
    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, Main.WINDOW_W, Main.WINDOW_H);
        setTitle(Main.APP_TITLE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login features go here");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(110, 11, 416, 22);
        contentPane.add(lblNewLabel);
        
        JButton homeButton = new JButton(Main.HOME_TEXT);
        homeButton.setBounds(0, 0, 140, 22);
        homeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		HomePage newFrame = new HomePage(); //Opening the second JFrame
        		dispose(); //Disposing the First JFrame
        	}
        });
        contentPane.add(homeButton);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setBounds(260, 79, 109, 14);
        contentPane.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(235, 104, 168, 20);
        contentPane.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setBounds(260, 135, 109, 14);
        contentPane.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(235, 160, 168, 20);
        contentPane.add(passwordField);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(160, 160, 160));
        panel.setBounds(143, 251, 361, 101);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("Not a member?");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds(68, 11, 241, 14);
        panel.add(lblNewLabel_3);

        JButton btnNewButton = new JButton("Join Now");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton.setBounds(134, 67, 89, 23);
        panel.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Sign In");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Clicked Sign in.");
            	
            	try {
            		boolean found = AccountSystem.searchDatabase(usernameField.getText(), // Holds the exception
								String.valueOf(passwordField.getPassword()) ); 
            		if (found) {
            			invalidInfo("Found");
            		}
            		else {
            			invalidInfo("Not Found");
            		}
            	}
            	catch (SQLException exp) {
            		exp.printStackTrace();
            	}
            	
            		// Search function from a different class because this class has different logic than what will be used there.
            		// Will return a bool indicating if the User exists
            }
        });
        btnNewButton_1.setBounds(275, 191, 88, 22);
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Forgot password?");
        btnNewButton_2.setBounds(222, 224, 168, 22);
        contentPane.add(btnNewButton_2);


        setVisible(true);
    }
    public void invalidInfo(String label) {
    	if (!contentPane.isAncestorOf(invalidLabel) || invalidLabel.getText() != label) { // If contentPane does not contain invalidLabel
    		if (contentPane.isAncestorOf(invalidLabel)) { // For testing purposes
    			contentPane.remove(invalidLabel);
    		}
    		
    		invalidLabel = new JLabel(label);
    		invalidLabel.setHorizontalAlignment(SwingConstants.CENTER);
    		Dimension prefSize = invalidLabel.getPreferredSize(); // So the text fits exactly in the box more properly
    		invalidLabel.setBounds(280, 40, prefSize.width, prefSize.height);
    		invalidLabel.setForeground(Color.RED);
    		
    		contentPane.add(invalidLabel);
    		contentPane.revalidate();
    		contentPane.repaint();
    		
    		// Note to self: I need to find a user based on the Username, then check if the password is right
    		// This means that there can only be one Username for each person.
    	}
    }
}