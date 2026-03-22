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
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class BookingPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;

    public BookingPage() {
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

        JLabel bookingLabel = new JLabel("Booking goes here");
        bookingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookingLabel.setBounds(112, 31, 416, 22);
        contentPane.add(bookingLabel);

        JCheckBox bed1CheckBox = new JCheckBox("1 Bed");
        bed1CheckBox.setBounds(74, 135, 66, 22);
        contentPane.add(bed1CheckBox);

        JLabel filterByLabel = new JLabel("Filter By:");
        filterByLabel.setBounds(10, 139, 48, 14);
        contentPane.add(filterByLabel);

        JCheckBox bed2CheckBox = new JCheckBox("2 Bed");
        bed2CheckBox.setBounds(169, 135, 66, 22);
        contentPane.add(bed2CheckBox);

        JCheckBox bedSuiteCheckBox = new JCheckBox("Suite");
        bedSuiteCheckBox.setBounds(271, 135, 66, 22);
        contentPane.add(bedSuiteCheckBox);

        JList floorList = new JList();
        floorList.setModel(new AbstractListModel() {
            String[] values = new String[] {"All", "Nature Retreat", "Urban Elegance", "Vintage Charm"};
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });
        floorList.setSelectedIndex(-1);
        floorList.setToolTipText("Floor");
        floorList.setBounds(441, 138, 87, 70);
        contentPane.add(floorList);

        JLabel floorLabel = new JLabel("Floor:");
        floorLabel.setBounds(383, 139, 48, 14);
        contentPane.add(floorLabel);

        JTextField checkinField = new JTextField();
        checkinField.setBounds(271, 64, 96, 20);
        contentPane.add(checkinField);
        checkinField.setColumns(10);

        JTextField checkoutField = new JTextField();
        checkoutField.setBounds(271, 95, 96, 20);
        contentPane.add(checkoutField);
        checkoutField.setColumns(10);

        JLabel checkinLabel = new JLabel("Check-In Date");
        checkinLabel.setBounds(169, 67, 88, 14);
        contentPane.add(checkinLabel);

        JLabel checkoutLabel = new JLabel("Check-Out Date");
        checkoutLabel.setBounds(169, 98, 48, 14);
        contentPane.add(checkoutLabel);


        setVisible(true);
    }
}