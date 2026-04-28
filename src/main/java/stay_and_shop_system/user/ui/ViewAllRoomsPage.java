package stay_and_shop_system.user.ui;

import stay_and_shop_system.*;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.ClerkInterface;
import stay_and_shop_system.user.User;
import stay_and_shop_system.user.UserRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAllRoomsPage extends JFrame {
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel pagePane;
    private JPanel contentPane;

    public ViewAllRoomsPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Main.WINDOW_W, Main.WINDOW_H);
        setLocationRelativeTo(null);
        setTitle(Main.APP_TITLE);

        User currentUser = UserRepository.getSessionAccount();

        if (!(currentUser instanceof ClerkInterface)) {
            JOptionPane.showMessageDialog(null, "You must be logged in as a clerk to view all rooms.");
            new HomePage2();
            dispose();
            return;
        }

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

        JLabel titleLabel = new JLabel("View All Rooms");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(500, 40));
        contentPane.add(titleLabel, c);

        JLabel subtitleLabel = new JLabel("All rooms currently stored in the hotel database");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setForeground(ColorPalette.DESATURATED_LIGHTBLUE);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        contentPane.add(subtitleLabel, c);

        List<Room> rooms = RoomRepository.loadRooms();

        String[] columnNames = {
                "Room #",
                "Beds",
                "Max Occupancy",
                "Base Rate",
                "Daily Rate",
                "Smoking",
                "Bed Types",
                "Quality",
                "Size",
                "Status"
        };

        Object[][] data = new Object[rooms.size()][columnNames.length];

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);

            data[i][0] = room.getNumber();
            data[i][1] = room.getBeds();
            data[i][2] = room.getMaxOccupancy();
            data[i][3] = "$" + String.format("%.2f", room.getBaseDailyRate());
            data[i][4] = "$" + String.format("%.2f", room.getDailyRate());
            data[i][5] = room.getSmokingStatus() ? "Permitted" : "Not Permitted";
            data[i][6] = formatBedTypes(room);
            data[i][7] = room.getQualityLevel();
            data[i][8] = room.getRoomSize();
            data[i][9] = room.getRoomStatus();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable roomsTable = new JTable(model);
        styleTable(roomsTable);

        JScrollPane tableScrollPane = new JScrollPane(roomsTable);
        tableScrollPane.setPreferredSize(new Dimension(950, 420));
        tableScrollPane.setBorder(BorderFactory.createLineBorder(ColorPalette.OCEAN_LIGHTBLUE, 2));
        tableScrollPane.getViewport().setBackground(ColorPalette.DESATURATED_DARKBLUE);
        tableScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE;
                this.trackColor = ColorPalette.OCEAN_DARKBLUE;
            }
        });

        contentPane.add(tableScrollPane, c);

        JLabel countLabel = new JLabel("Total Rooms: " + rooms.size());
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        countLabel.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(countLabel, c);

        JButton refreshButton = new JButton("Refresh");
        stylePrimaryButton(refreshButton);

        refreshButton.addActionListener(e -> {
            new ViewAllRoomsPage();
            dispose();
        });

        contentPane.add(refreshButton, c);

        pagePane.add(contentPane);
        setVisible(true);
    }

    private String formatBedTypes(Room room) {
        return room.getBedTypes()
                .stream()
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Serif", Font.PLAIN, 15));
        table.setRowHeight(32);
        table.setForeground(ColorPalette.OCEAN_DARKBLUE);
        table.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        table.setGridColor(ColorPalette.OCEAN_DARKBLUE);
        table.setSelectionBackground(ColorPalette.OCEAN_LIGHTBLUE);
        table.setSelectionForeground(ColorPalette.OCEAN_DARKBLUE);

        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 16));
        table.getTableHeader().setBackground(ColorPalette.SATURATED_BLUE);
        table.getTableHeader().setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(75);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(95);
        table.getColumnModel().getColumn(4).setPreferredWidth(95);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setPreferredWidth(160);
        table.getColumnModel().getColumn(7).setPreferredWidth(110);
        table.getColumnModel().getColumn(8).setPreferredWidth(100);
        table.getColumnModel().getColumn(9).setPreferredWidth(110);
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(ColorPalette.OCEAN_LIGHTBLUE);
        button.setForeground(ColorPalette.OCEAN_DARKBLUE);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(160, 38));
    }
}