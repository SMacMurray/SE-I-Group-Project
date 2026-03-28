import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClerkAddRoomPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public ClerkAddRoomPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null); // Centers the JFrame on the screen
        setTitle("Clerk - Add Room");

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Add New Room");
        titleLabel.setBounds(290, 20, 150, 25);
        contentPane.add(titleLabel);

        JButton homeButton = new JButton(Main.HOME_TEXT);
        homeButton.setBounds(0, 0, 140, 22);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePage newFrame = new HomePage();
                dispose();
            }
        });
        contentPane.add(homeButton);

        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberLabel.setBounds(150, 80, 120, 25);
        contentPane.add(roomNumberLabel);

        JTextField roomNumberField = new JTextField();
        roomNumberField.setBounds(300, 80, 150, 25);
        contentPane.add(roomNumberField);

        JLabel bedsLabel = new JLabel("Number of Beds:");
        bedsLabel.setBounds(150, 120, 120, 25);
        contentPane.add(bedsLabel);

        JTextField bedsField = new JTextField();
        bedsField.setBounds(300, 120, 150, 25);
        contentPane.add(bedsField);

        JLabel occupancyLabel = new JLabel("Max Occupancy:");
        occupancyLabel.setBounds(150, 160, 120, 25);
        contentPane.add(occupancyLabel);

        JTextField occupancyField = new JTextField();
        occupancyField.setBounds(300, 160, 150, 25);
        contentPane.add(occupancyField);

        JLabel rateLabel = new JLabel("Base Daily Rate:");
        rateLabel.setBounds(150, 200, 120, 25);
        contentPane.add(rateLabel);

        JTextField rateField = new JTextField();
        rateField.setBounds(300, 200, 150, 25);
        contentPane.add(rateField);

        JLabel smokingLabel = new JLabel("Smoking:");
        smokingLabel.setBounds(150, 240, 120, 25);
        contentPane.add(smokingLabel);

        String[] smokingOptions = {"Permitted", "Not Permitted"};
        JComboBox<String> smokingBox = new JComboBox<>(smokingOptions);
        smokingBox.setBounds(300, 240, 150, 25);
        contentPane.add(smokingBox);

        JLabel bedTypesLabel = new JLabel("Bed Types:");
        bedTypesLabel.setBounds(150, 280, 120, 25);
        contentPane.add(bedTypesLabel);

        Room.BedType[] bedTypeOptions = Room.BedType.values();

        JComboBox<Room.BedType> bedTypeBox1 = new JComboBox<>(bedTypeOptions);
        bedTypeBox1.setBounds(300, 280, 100, 25);
        contentPane.add(bedTypeBox1);

        JComboBox<Room.BedType> bedTypeBox2 = new JComboBox<>(bedTypeOptions);
        bedTypeBox2.setBounds(410, 280, 100, 25);
        contentPane.add(bedTypeBox2);

        JComboBox<Room.BedType> bedTypeBox3 = new JComboBox<>(bedTypeOptions);
        bedTypeBox3.setBounds(300, 315, 100, 25);
        contentPane.add(bedTypeBox3);

        JComboBox<Room.BedType> bedTypeBox4 = new JComboBox<>(bedTypeOptions);
        bedTypeBox4.setBounds(410, 315, 100, 25);
        contentPane.add(bedTypeBox4);

        JLabel bedHintLabel = new JLabel("Choose up to 4 bed types");
        bedHintLabel.setBounds(300, 345, 200, 20);
        contentPane.add(bedHintLabel);

        JLabel qualityLabel = new JLabel("Quality Level:");
        qualityLabel.setBounds(150, 375, 120, 25);
        contentPane.add(qualityLabel);

        JComboBox<Room.QualityLevel> qualityBox = new JComboBox<>(Room.QualityLevel.values());
        qualityBox.setBounds(300, 375, 150, 25);
        contentPane.add(qualityBox);

        JLabel sizeLabel = new JLabel("Room Size:");
        sizeLabel.setBounds(150, 415, 120, 25);
        contentPane.add(sizeLabel);

        JComboBox<Room.RoomSize> sizeBox = new JComboBox<>(Room.RoomSize.values());
        sizeBox.setBounds(300, 415, 150, 25);
        contentPane.add(sizeBox);

        JButton addButton = new JButton("Add Room");
        addButton.setBounds(290, 465, 120, 30);
        contentPane.add(addButton);

        // default state: only first bed dropdown enabled
        bedTypeBox1.setEnabled(true);
        bedTypeBox2.setEnabled(false);
        bedTypeBox3.setEnabled(false);
        bedTypeBox4.setEnabled(false);

        bedsField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateBedBoxes() {
                try {
                    int beds = Integer.parseInt(bedsField.getText().trim());
                    bedTypeBox1.setEnabled(beds >= 1);
                    bedTypeBox2.setEnabled(beds >= 2);
                    bedTypeBox3.setEnabled(beds >= 3);
                    bedTypeBox4.setEnabled(beds >= 4);
                } catch (NumberFormatException ex) {
                    bedTypeBox1.setEnabled(true);
                    bedTypeBox2.setEnabled(false);
                    bedTypeBox3.setEnabled(false);
                    bedTypeBox4.setEnabled(false);
                }
            }

            public void insertUpdate(DocumentEvent e) {
                updateBedBoxes();
            }

            public void removeUpdate(DocumentEvent e) {
                updateBedBoxes();
            }

            public void changedUpdate(DocumentEvent e) {
                updateBedBoxes();
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int roomNumber = Integer.parseInt(roomNumberField.getText().trim());
                    int beds = Integer.parseInt(bedsField.getText().trim());
                    int maxOccupancy = Integer.parseInt(occupancyField.getText().trim());
                    double rate = Double.parseDouble(rateField.getText().trim());

                    if (roomNumber < 100 || roomNumber > 399) {
                        JOptionPane.showMessageDialog(null,
                                "Room number must be between 100 and 399.");
                        return;
                    }

                    if (beds < 1 || beds > 4) {
                        JOptionPane.showMessageDialog(null,
                                "Number of beds must be between 1 and 4.");
                        return;
                    }

                    if (maxOccupancy < 1) {
                        JOptionPane.showMessageDialog(null,
                                "Max occupancy must be at least 1.");
                        return;
                    }

                    if (rate < 0) {
                        JOptionPane.showMessageDialog(null,
                                "Base daily rate cannot be negative.");
                        return;
                    }

                    if (GlobalVariables.rs.getRoom(roomNumber) != null) {
                        JOptionPane.showMessageDialog(null,
                                "A room with that room number already exists.");
                        return;
                    }

                    boolean smokingStatus =
                            ((String) smokingBox.getSelectedItem()).equalsIgnoreCase("Permitted");

                    List<Room.BedType> bedTypes = new ArrayList<>();

                    if (beds >= 1) {
                        bedTypes.add((Room.BedType) bedTypeBox1.getSelectedItem());
                    }
                    if (beds >= 2) {
                        bedTypes.add((Room.BedType) bedTypeBox2.getSelectedItem());
                    }
                    if (beds >= 3) {
                        bedTypes.add((Room.BedType) bedTypeBox3.getSelectedItem());
                    }
                    if (beds >= 4) {
                        bedTypes.add((Room.BedType) bedTypeBox4.getSelectedItem());
                    }

                    Room.QualityLevel quality =
                            (Room.QualityLevel) qualityBox.getSelectedItem();
                    Room.RoomSize size =
                            (Room.RoomSize) sizeBox.getSelectedItem();

                    GlobalVariables.rs.createRoom(
                            roomNumber,
                            beds,
                            maxOccupancy,
                            rate,
                            smokingStatus,
                            bedTypes,
                            quality,
                            size
                    );
                    Room newRoom = GlobalVariables.rs.getRoom(roomNumber);
                    GlobalVariables.rs.saveRoomToCSV(newRoom);

                    JOptionPane.showMessageDialog(null,
                            "Room " + roomNumber + " added successfully.");

                    roomNumberField.setText("");
                    bedsField.setText("");
                    occupancyField.setText("");
                    rateField.setText("");

                    smokingBox.setSelectedIndex(0);
                    qualityBox.setSelectedIndex(0);
                    sizeBox.setSelectedIndex(0);

                    bedTypeBox1.setSelectedIndex(0);
                    bedTypeBox2.setSelectedIndex(0);
                    bedTypeBox3.setSelectedIndex(0);
                    bedTypeBox4.setSelectedIndex(0);

                    bedTypeBox1.setEnabled(true);
                    bedTypeBox2.setEnabled(false);
                    bedTypeBox3.setEnabled(false);
                    bedTypeBox4.setEnabled(false);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter valid numbers for room number, beds, occupancy, and rate.");
                }
            }
        });


        setVisible(true);
    }
}