package stay_and_shop_system.user.ui;

import stay_and_shop_system.*;
import stay_and_shop_system.occupancy.Room;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModifyRoomPage extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel pagePane;
    private JPanel contentPane;

    public ModifyRoomPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Main.WINDOW_W, Main.WINDOW_H);
        setLocationRelativeTo(null);
        setTitle(Main.APP_TITLE);

        Object[] uiObjects = SetupUI.initializeScreen(popupPane, mainPane, this);
        popupPane = (JPanel) uiObjects[0];
        mainPane = (JPanel) uiObjects[1];

        pagePane = new JPanel(new GridBagLayout());
        pagePane.setBackground(ColorPalette.OCEAN_DARKBLUE);

        JScrollPane pageScrollPane = new JScrollPane(pagePane);
        pageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pageScrollPane.setBorder(null);
        pageScrollPane.setViewportBorder(null);
        pageScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE;
                this.trackColor = ColorPalette.OCEAN_DARKBLUE;
            }
        });

        mainPane.add(pageScrollPane, BorderLayout.CENTER);

        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(ColorPalette.DESATURATED_DARKBLUE);
        contentPane.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Modify Room");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(500, 40));
        contentPane.add(titleLabel, c);

        JLabel subtitleLabel = new JLabel("Load an existing room, update the fields, then save changes");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setForeground(ColorPalette.DESATURATED_LIGHTBLUE);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        contentPane.add(subtitleLabel, c);

        JTextField lookupRoomField = makeField();
        JTextField roomNumberField = makeField();
        JTextField bedsField = makeField();
        JTextField occupancyField = makeField();
        JTextField rateField = makeField();

        JComboBox<String> smokingBox = new JComboBox<>(new String[]{"Permitted", "Not Permitted"});
        styleComboBox(smokingBox);

        JComboBox<Room.BedType> bedTypeBox1 = new JComboBox<>(Room.BedType.values());
        JComboBox<Room.BedType> bedTypeBox2 = new JComboBox<>(Room.BedType.values());
        JComboBox<Room.BedType> bedTypeBox3 = new JComboBox<>(Room.BedType.values());
        JComboBox<Room.BedType> bedTypeBox4 = new JComboBox<>(Room.BedType.values());
        styleComboBox(bedTypeBox1);
        styleComboBox(bedTypeBox2);
        styleComboBox(bedTypeBox3);
        styleComboBox(bedTypeBox4);

        JComboBox<Room.QualityLevel> qualityBox = new JComboBox<>(Room.QualityLevel.values());
        JComboBox<Room.RoomSize> sizeBox = new JComboBox<>(Room.RoomSize.values());
        styleComboBox(qualityBox);
        styleComboBox(sizeBox);

        JPanel lookupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        lookupPanel.setOpaque(false);

        JLabel lookupLabel = new JLabel("Room Number to Load");
        lookupLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        lookupLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        JButton loadButton = new JButton("Load Room");
        stylePrimaryButton(loadButton);

        lookupPanel.add(lookupLabel);
        lookupPanel.add(lookupRoomField);
        lookupPanel.add(loadButton);
        contentPane.add(lookupPanel, c);

        contentPane.add(makeLabeledRow("New Room Number", roomNumberField), c);
        contentPane.add(makeLabeledRow("Number of Beds", bedsField), c);
        contentPane.add(makeLabeledRow("Max Occupancy", occupancyField), c);
        contentPane.add(makeLabeledRow("Base Daily Rate", rateField), c);
        contentPane.add(makeLabeledRow("Smoking", smokingBox), c);

        JPanel bedTypesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        bedTypesPanel.setOpaque(false);
        bedTypesPanel.add(bedTypeBox1);
        bedTypesPanel.add(bedTypeBox2);
        bedTypesPanel.add(bedTypeBox3);
        bedTypesPanel.add(bedTypeBox4);
        contentPane.add(makeLabeledRow("Bed Types", bedTypesPanel), c);

        JLabel bedHintLabel = new JLabel("Choose up to 4 bed types based on the number of beds");
        bedHintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bedHintLabel.setForeground(ColorPalette.DESATURATED_LIGHTBLUE);
        bedHintLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        contentPane.add(bedHintLabel, c);

        contentPane.add(makeLabeledRow("Quality Level", qualityBox), c);
        contentPane.add(makeLabeledRow("Room Size", sizeBox), c);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttonsPanel.setOpaque(false);

        JButton updateButton = new JButton("Update Room");
        stylePrimaryButton(updateButton);

        JButton clearButton = new JButton("Clear");
        styleSecondaryButton(clearButton);

        buttonsPanel.add(updateButton);
        buttonsPanel.add(clearButton);
        contentPane.add(buttonsPanel, c);

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

            @Override public void insertUpdate(DocumentEvent e) { updateBedBoxes(); }
            @Override public void removeUpdate(DocumentEvent e) { updateBedBoxes(); }
            @Override public void changedUpdate(DocumentEvent e) { updateBedBoxes(); }
        });

        clearButton.addActionListener(e -> {
            lookupRoomField.setText("");
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
        });

        loadButton.addActionListener(e -> {
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
        });

        updateButton.addActionListener(e -> {
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

                boolean smokingStatus = ((String) smokingBox.getSelectedItem()).equalsIgnoreCase("Permitted");

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
                            "Room update failed. Check whether the room exists or the new number is already taken.");
                    return;
                }

                GlobalVariables.rs.rewriteRoomsCSV();

                JOptionPane.showMessageDialog(null,
                        "Room " + originalRoomNumber + " updated successfully.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Please enter valid numbers for room number, beds, occupancy, and rate.");
            }
        });

        pagePane.add(contentPane);
        setVisible(true);
    }

    private JTextField makeField() {
        JTextField field = new RoundJTextField(25);
        field.setPreferredSize(new Dimension(260, 34));
        field.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        field.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        field.setFont(new Font("Serif", Font.PLAIN, 16));
        return field;
    }

    private <T extends JComponent> JPanel makeLabeledRow(String labelText, T component) {
        JPanel panel = new JPanel(new BorderLayout(10, 8));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(650, 65));

        JLabel label = new JLabel(labelText);
        label.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        label.setFont(new Font("Serif", Font.PLAIN, 20));

        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setPreferredSize(new Dimension(260, 34));
        comboBox.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        comboBox.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        comboBox.setFont(new Font("Serif", Font.PLAIN, 16));
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(ColorPalette.OCEAN_LIGHTBLUE);
        button.setForeground(ColorPalette.OCEAN_DARKBLUE);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(160, 38));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(ColorPalette.SATURATED_BLUE);
        button.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(160, 38));
    }
}