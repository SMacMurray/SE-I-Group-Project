import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.*;

public class SearchRoomPage extends JFrame{
	private static final long serialVersionUID = 1L;
    private JPanel mainPane;
    private JPanel contentPane;
    private JLabel titleLabel;
    private SearchController sc = new SearchController();
    
    private static boolean checkForExceptions() {
    	
    	return false;
    }
    SearchRoomPage() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize( Main.WINDOW_W + 500, Main.WINDOW_H + 50);
		setLocationRelativeTo(null); // Centers the JFrame on the screen
		
		mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		
		titleLabel = new JLabel("What type of room do you want?");
		titleLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		JPanel titleWrapperPane = new JPanel();
		titleWrapperPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(15, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		titleWrapperPane.add(titleLabel, c);
		mainPane.add(titleWrapperPane, BorderLayout.PAGE_START);
		
		
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		mainPane.add(contentPane, BorderLayout.CENTER);
		
		
		
		c.insets = new Insets(0, 0, 15, 100); // left padding
		JLabel guestsLabel = new JLabel("Guests");
		guestsLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
		contentPane.add(guestsLabel, c);
		
		JPanel guestCountWrapper = new JPanel();
		JTextField guestCountField = new JTextField(8);
		JLabel guestCountLabel = new JLabel("Max Guest Count:");
		guestCountWrapper.add(guestCountLabel);
		guestCountWrapper.add(guestCountField);
		c.insets = new Insets(0, 0, 0, 100);
		c.gridy = 1;
		contentPane.add(guestCountWrapper, c);
		
		JPanel bedCountWrapper = new JPanel();
		JTextField bedCountField = new JTextField(8);
		JLabel bedCountLabel = new JLabel("Bed Count:");
		bedCountWrapper.add(bedCountLabel);
		bedCountWrapper.add(bedCountField);
		c.gridy = 2;
		contentPane.add(bedCountWrapper, c);
		
		JPanel smokingWrapper = new JPanel();
		JComboBox<Boolean> smokingBox = new JComboBox<Boolean>(new Boolean[] {true, false});
		JLabel smokingLabel = new JLabel("Smoking Status:");
		smokingWrapper.add(smokingLabel);
		smokingWrapper.add(smokingBox);
		c.gridy = 3;
		contentPane.add(smokingWrapper, c);
		
		
		JLabel designLabel = new JLabel("Design"); // right padding
		designLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 0, 15, 100);
		c.gridx = 1;
		c.gridy = 0;
		contentPane.add(designLabel, c);
		
		JPanel floorDesignWrapper = new JPanel();
		JLabel floorDesignLabel = new JLabel("Room Design:");
		JComboBox<String> floorDesignBox = new JComboBox<String>(new String[]{"Nature Retreat", "Urban Elegance","Vintage Charm" });
		floorDesignWrapper.add(floorDesignLabel);
		floorDesignWrapper.add(floorDesignBox);
		c.insets = new Insets(0, 0, 0, 100);
		c.gridy = 1;
		contentPane.add(floorDesignWrapper, c);
		
		JPanel bedTypeWrapper = new JPanel();
		JLabel bedTypeLabel = new JLabel("Bed Types:");
		DefaultListModel<Room.BedType> bedListModel = new DefaultListModel<>();
		for (Room.BedType bT : Room.BedType.values()) {
			bedListModel.addElement(bT);
		}
		JList<Room.BedType> bedList = new JList<>(bedListModel);
		bedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane bedListScroll = new JScrollPane(bedList);
		bedListScroll.setPreferredSize(new Dimension(70, 50));
		bedTypeWrapper.add(bedTypeLabel);
		bedTypeWrapper.add(bedListScroll);
		c.gridy = 2;
		contentPane.add(bedTypeWrapper, c);
		
		JPanel roomSizeWrapper = new JPanel();
		JLabel roomSizeLabel = new JLabel("Room Size:");
		JComboBox<Room.RoomSize> roomSizeBox = new JComboBox<Room.RoomSize>(Room.RoomSize.values());
		roomSizeWrapper.add(roomSizeLabel);
		roomSizeWrapper.add(roomSizeBox);
		c.gridy = 3;
		contentPane.add(roomSizeWrapper, c);
		
		
		
		JLabel costLabel = new JLabel("Cost");
		costLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
		c.insets = new Insets(0, 0, 15, 0);
		c.gridx = 2;
		c.gridy = 0;
		contentPane.add(costLabel, c);
		
		JPanel baseRateWrapper = new JPanel();
		JTextField baseRateField = new JTextField(8);
		JLabel baseRateLabel = new JLabel("Base Daily Rate:");
		baseRateWrapper.add(baseRateLabel);
		baseRateWrapper.add(baseRateField);
		c.insets = new Insets(0, 0, 0, 0);
		c.gridy = 1;
		contentPane.add(baseRateWrapper, c);
		
		JPanel qualityLevelWrapper = new JPanel();
		JComboBox<Room.QualityLevel> qualityLevelBox = new JComboBox<Room.QualityLevel>(Room.QualityLevel.values());
		JLabel qualityLevelLabel = new JLabel("Quality Level:");
		qualityLevelWrapper.add(qualityLevelLabel);
		qualityLevelWrapper.add(qualityLevelBox);
		c.gridy = 2;
		contentPane.add(qualityLevelWrapper, c);
		
		
		
		JButton searchRoomButton = new JButton("Search");
		c.insets = new Insets(50, 0, 0, 100);
		c.gridx = 1;
		c.gridy = 7;
		contentPane.add(searchRoomButton,c );

		searchRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkForExceptions()) { // Not completed yet
					return;
				}
				
				int roomNumber;
				if (floorDesignBox.getSelectedItem() == "Nature Retreat") {
					roomNumber = 100;
				}
				else if (floorDesignBox.getSelectedItem() == "Urban Elegance") {
					roomNumber = 200;
				}
				else roomNumber = 300;
				
				int beds = Integer.parseInt(bedCountField.getText());
				int maxOcc = Integer.parseInt(guestCountField.getText());
				double baseRate = Double.parseDouble(baseRateField.getText());
				boolean smokingStatus = (boolean)smokingBox.getSelectedItem();
				List<Room.BedType> bedTypes = Arrays.asList(bedList.getSelectedValues()).stream()
											.map(bT -> (Room.BedType)bT)
											.collect(Collectors.toList());
				Room.QualityLevel qualityLevel = (Room.QualityLevel)qualityLevelBox.getSelectedItem();
				Room.RoomSize roomSize = (Room.RoomSize)roomSizeBox.getSelectedItem();
				
				List<Room> rooms = sc.searchAvailableRooms(new Room(roomNumber, beds, maxOcc, baseRate, smokingStatus,
												bedTypes, qualityLevel, roomSize));
				
				
				BookingPage2 newFrame = new BookingPage2(rooms); //Opening the second JFrame
                dispose(); 
			}
		});
		
		
		
		setContentPane(mainPane);
		setVisible(true);
    }
}
