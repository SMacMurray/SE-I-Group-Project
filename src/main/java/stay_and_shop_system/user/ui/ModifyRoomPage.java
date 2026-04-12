package stay_and_shop_system.user.ui;

import stay_and_shop_system.GlobalVariables;
import stay_and_shop_system.HomePage2;
import stay_and_shop_system.Main;
import stay_and_shop_system.occupancy.Room;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ModifyRoomPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public ModifyRoomPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 620);
        setLocationRelativeTo(null);
        setTitle("Clerk - Modify Room");

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Modify Existing Room");
        titleLabel.setBounds(290, 20, 180, 25);
        contentPane.add(titleLabel);

        JButton homeButton = new JButton(Main.HOME_TEXT);
        homeButton.setBounds(0, 0, 160, 25);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage2();
                dispose();
            }
        });
        contentPane.add(homeButton);

        JLabel lookupRoomLabel = new JLabel("Room Number to Modify:");
        lookupRoomLabel.setBounds(120, 70, 160, 25);
        contentPane.add(lookupRoomLabel);

        JTextField lookupRoomField = new JTextField();
        lookupRoomField.setBounds(300, 70, 150, 25);
        contentPane.add(lookupRoomField);

        JButton loadButton = new JButton("Load Room");
        loadButton.setBounds(470, 70, 120, 25);
        contentPane.add(loadButton);

        JLabel newRoomNumberLabel = new JLabel("New Room Number:");
        newRoomNumberLabel.setBounds(120, 130, 140, 25);
        contentPane.add(newRoomNumberLabel);

        JTextField roomNumberField = new JTextField();
        roomNumberField.setBounds(300, 130, 150, 25);
        contentPane.add(roomNumberField);

        JLabel bedsLabel = new JLabel("Number of Beds:");
        bedsLabel.setBounds(120, 170, 140, 25);
        contentPane.add(bedsLabel);

        JTextField bedsField = new JTextField();
        bedsField.setBounds(300, 170, 150, 25);
        contentPane.add(bedsField);

        JLabel occupancyLabel = new JLabel("Max Occupancy:");
        occupancyLabel.setBounds(120, 210, 140, 25);
        contentPane.add(occupancyLabel);

        JTextField occupancyField = new JTextField();
        occupancyField.setBounds(300, 210, 150, 25);
        contentPane.add(occupancyField);

        JLabel rateLabel = new JLabel("Base Daily Rate:");
        rateLabel.setBounds(120, 250, 140, 25);
        contentPane.add(rateLabel);

        JTextField rateField = new JTextField();
        rateField.setBounds(300, 250, 150, 25);
        contentPane.add(rateField);

        JLabel smokingLabel = new JLabel("Smoking:");
        smokingLabel.setBounds(120, 290, 140, 25);
        contentPane.add(smokingLabel);

        String[] smokingOptions = {"Permitted", "Not Permitted"};
        JComboBox<String> smokingBox = new JComboBox<>(smokingOptions);
        smokingBox.setBounds(300, 290, 150, 25);
        contentPane.add(smokingBox);

        JLabel bedTypesLabel = new JLabel("Bed Types:");
        bedTypesLabel.setBounds(120, 330, 140, 25);
        contentPane.add(bedTypesLabel);

        Room.BedType[] bedTypeOptions = Room.BedType.values();

        JComboBox<Room.BedType> bedTypeBox1 = new JComboBox<>(bedTypeOptions);
        bedTypeBox1.setBounds(300, 330, 100, 25);
        contentPane.add(bedTypeBox1);

        JComboBox<Room.BedType> bedTypeBox2 = new JComboBox<>(bedTypeOptions);
        bedTypeBox2.setBounds(410, 330, 100, 25);
        contentPane.add(bedTypeBox2);

        JComboBox<Room.BedType> bedTypeBox3 = new JComboBox<>(bedTypeOptions);
        bedTypeBox3.setBounds(300, 365, 100, 25);
        contentPane.add(bedTypeBox3);

        JComboBox<Room.BedType> bedTypeBox4 = new JComboBox<>(bedTypeOptions);
        bedTypeBox4.setBounds(410, 365, 100, 25);
        contentPane.add(bedTypeBox4);

        JLabel bedHintLabel = new JLabel("Choose up to 4 bed types");
        bedHintLabel.setBounds(300, 395, 200, 20);
        contentPane.add(bedHintLabel);

        JLabel qualityLabel = new JLabel("Quality Level:");
        qualityLabel.setBounds(120, 430, 140, 25);
        contentPane.add(qualityLabel);

        JComboBox<Room.QualityLevel> qualityBox = new JComboBox<>(Room.QualityLevel.values());
        qualityBox.setBounds(300, 430, 150, 25);
        contentPane.add(qualityBox);

        JLabel sizeLabel = new JLabel("Room Size:");
        sizeLabel.setBounds(120, 470, 140, 25);
        contentPane.add(sizeLabel);

        JComboBox<Room.RoomSize> sizeBox = new JComboBox<>(Room.RoomSize.values());
        sizeBox.setBounds(300, 470, 150, 25);
        contentPane.add(sizeBox);

        JButton updateButton = new JButton("Update Room");
        updateButton.setBounds(300, 525, 150, 30);
        contentPane.add(updateButton);

        // Default state
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

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateBedBoxes();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateBedBoxes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateBedBoxes();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int roomNumber = Integer.parseInt(lookupRoomField.getText().trim());
                    Room room = GlobalVariables.rs.getRoom(roomNumber);

                    if (room == null) {
                        JOptionPane.showMessageDialog(null, "No room exists with that room number.");
                        return;
                    }

                    roomNumberField.setText(String.valueOf(room.getNumber()));
                    bedsField.setText(String.valueOf(room.getBeds()));
                    occupancyField.setText(String.valueOf(room.getMaxOccupancy()));
                    rateField.setText(String.valueOf(room.getBaseDailyRate()));
                    smokingBox.setSelectedItem(room.getSmokingStatus() ? "Permitted" : "Not Permitted");
                    qualityBox.setSelectedItem(room.getQualityLevel());
                    sizeBox.setSelectedItem(room.getRoomSize());

                    bedTypeBox1.setSelectedIndex(0);
                    bedTypeBox2.setSelectedIndex(0);
                    bedTypeBox3.setSelectedIndex(0);
                    bedTypeBox4.setSelectedIndex(0);

                    List<Room.BedType> bedTypes = room.getBedTypes();
                    if (bedTypes.size() > 0) bedTypeBox1.setSelectedItem(bedTypes.get(0));
                    if (bedTypes.size() > 1) bedTypeBox2.setSelectedItem(bedTypes.get(1));
                    if (bedTypes.size() > 2) bedTypeBox3.setSelectedItem(bedTypes.get(2));
                    if (bedTypes.size() > 3) bedTypeBox4.setSelectedItem(bedTypes.get(3));

                    bedTypeBox1.setEnabled(true);
                    bedTypeBox2.setEnabled(room.getBeds() >= 2);
                    bedTypeBox3.setEnabled(room.getBeds() >= 3);
                    bedTypeBox4.setEnabled(room.getBeds() >= 4);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a valid room number to load.");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int originalRoomNumber = Integer.parseInt(lookupRoomField.getText().trim());
                    int newRoomNumber = Integer.parseInt(roomNumberField.getText().trim());
                    int beds = Integer.parseInt(bedsField.getText().trim());
                    int maxOccupancy = Integer.parseInt(occupancyField.getText().trim());
                    double rate = Double.parseDouble(rateField.getText().trim());

                    if (originalRoomNumber < 100 || originalRoomNumber > 399) {
                        JOptionPane.showMessageDialog(null, "Original room number must be between 100 and 399.");
                        return;
                    }

                    if (newRoomNumber < 100 || newRoomNumber > 399) {
                        JOptionPane.showMessageDialog(null, "New room number must be between 100 and 399.");
                        return;
                    }

                    if (beds < 1 || beds > 4) {
                        JOptionPane.showMessageDialog(null, "Number of beds must be between 1 and 4.");
                        return;
                    }

                    if (maxOccupancy < 1) {
                        JOptionPane.showMessageDialog(null, "Max occupancy must be at least 1.");
                        return;
                    }

                    if (rate < 0) {
                        JOptionPane.showMessageDialog(null, "Base daily rate cannot be negative.");
                        return;
                    }

                    boolean smokingStatus =
                            ((String) smokingBox.getSelectedItem()).equalsIgnoreCase("Permitted");

                    List<Room.BedType> bedTypes = new ArrayList<>();
                    if (beds >= 1) bedTypes.add((Room.BedType) bedTypeBox1.getSelectedItem());
                    if (beds >= 2) bedTypes.add((Room.BedType) bedTypeBox2.getSelectedItem());
                    if (beds >= 3) bedTypes.add((Room.BedType) bedTypeBox3.getSelectedItem());
                    if (beds >= 4) bedTypes.add((Room.BedType) bedTypeBox4.getSelectedItem());

                    Room.QualityLevel quality = (Room.QualityLevel) qualityBox.getSelectedItem();
                    Room.RoomSize size = (Room.RoomSize) sizeBox.getSelectedItem();

                    boolean updated = GlobalVariables.rs.updateRoom(
                            originalRoomNumber,
                            newRoomNumber,
                            beds,
                            maxOccupancy,
                            rate,
                            smokingStatus,
                            bedTypes,
                            quality,
                            size
                    );

                    if (!updated) {
                        JOptionPane.showMessageDialog(null,
                                "Room update failed. Check whether the original room exists or the new room number is already taken.");
                        return;
                    }
                    GlobalVariables.rs.rewriteRoomsCSV();


                    JOptionPane.showMessageDialog(null,
                            "Room " + originalRoomNumber + " updated successfully.");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter valid numbers for room number, beds, occupancy, and rate.");
                }
            }
        });

        setVisible(true);
    }
}