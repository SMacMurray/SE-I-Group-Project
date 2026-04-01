package stay_and_shop_system.occupancy.ui;

import javax.swing.*;
import javax.swing.table.*; // Includes DefaulTableModel

import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.LooseParserModel;
import net.coderazzi.filters.gui.TableFilterHeader;
import stay_and_shop_system.GlobalVariables;
import stay_and_shop_system.HomePage;
import stay_and_shop_system.Main;
import stay_and_shop_system.occupancy.Room;

import java.awt.*; // Includes BorderLayout.*,
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BookingPage2 extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JPanel homePane;
	private JButton addRoomButton;
	private JScrollPane scrollPane;
	private JPanel alignScrollPane;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTable table;
	private JPanel descriptionPane;
	private JTextField startDateBox;
	private JTextField endDateBox;
	private JTextField guestsBox;
	private JTextField nameBox;
	private JTextField emailBox;
	private JTextField creditCardBox;
	
	private String[] columnNames = {
			"Room Number",
			"Beds",
			"Max Number of Guests",
			"Base Daily Rate",
			"Smoking Status",
			"Bed Sizes",
			"Quality Level",
			"Room Size"
	};
	
	
	public Object[][] formatRoomsAsArray(List<Room> rooms) {
		Object[][] roomsArray = new Object[rooms.size()][8];
		
		int i = 0;
		for (Room r : rooms) {
			roomsArray[i][0] = r.getNumber();
			roomsArray[i][1] = r.getBeds();
			roomsArray[i][2] = r.getMaxOccupancy();
			roomsArray[i][3] = r.getBaseDailyRate();
			roomsArray[i][4] = r.getSmokingStatus() ? "Permitted" : "Prohibited";
			for (int j = 0; j < r.getBedTypes().size(); ++j) {
				if (r.getBedTypes().size() - j == 1) {
					roomsArray[i][5] += r.getBedTypes().get(j).toString();
				}
				else {
					roomsArray[i][5] += r.getBedTypes().get(j).name() + ", ";
				}
			}
			// The 'null' comes from the array I made. Not from parsing the files incorrectly.
			roomsArray[i][5] = ((String)roomsArray[i][5]).replaceAll("null", "");
			roomsArray[i][6] = r.getQualityLevel().toString();
			roomsArray[i][7] = r.getRoomSize().toString();
			++i;
		}
		return roomsArray;
	}
	public void makeReservation() {
		
	}
	public BookingPage2(List<Room> rooms) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize( Main.WINDOW_W + 600, Main.WINDOW_H + 50);
		setLocationRelativeTo(null); // Centers the JFrame on the screen
		
		mainPane = new JPanel();
		setContentPane(mainPane);
		mainPane.setLayout(new BorderLayout());
		
		descriptionPane = new JPanel();
		descriptionPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		descriptionPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 10)); // Setting padding, positioning the content inside this pane
		
		
		final Class<?>[] columnClass = new Class[] {
                Integer.class, Integer.class, Integer.class, Double.class, String.class,
                String.class, String.class, String.class
        };
		DefaultTableModel model = new DefaultTableModel(formatRoomsAsArray(rooms), columnNames) {
			@Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                return columnClass[columnIndex];
            }
		};
		sorter = new TableRowSorter<DefaultTableModel>(model);
		table = new JTable(model);
		table.setRowSorter(sorter);
		TableFilterHeader filterHeader = new TableFilterHeader(table, new LooseParserModel(), AutoChoices.ENABLED);
		filterHeader.getParserModel().setIgnoreCase(true);
		table.setPreferredScrollableViewportSize(new Dimension(750, 300));
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		alignScrollPane = new JPanel();
		alignScrollPane.setLayout(new FlowLayout());
		alignScrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		alignScrollPane.add(scrollPane);
		
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
		
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        startDateBox = new JTextField(16);
        JPanel startDatePanel = new JPanel(new FlowLayout());
        startDatePanel.add(new JLabel("Start Date(yyyy/MM/dd): "));
        startDatePanel.add(startDateBox);
        descriptionPane.add(startDatePanel, c);
        c.gridx = 0;
        c.gridy = 1;
        endDateBox = new JTextField(16);
        JPanel endDatePanel = new JPanel(new FlowLayout());
        endDatePanel.add(new JLabel("End Date(yyyy/MM/dd): "));
        endDatePanel.add(endDateBox);
        descriptionPane.add(endDatePanel, c);
        c.gridx = 0;
        c.gridy = 2;
        guestsBox = new JTextField(16);
        JPanel guestsPanel = new JPanel(new FlowLayout());
        guestsPanel.add(new JLabel("Guest Amount: "));
        guestsPanel.add(guestsBox);
        descriptionPane.add(guestsPanel, c);
        nameBox = new JTextField(16);
        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(new JLabel("First and Last Name: "));
        namePanel.add(nameBox);
        c.gridx = 0;
        c.gridy = 3;
        descriptionPane.add(namePanel, c);
        emailBox = new JTextField(16);
        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(new JLabel("Email: "));
        emailPanel.add(emailBox);
        c.gridx = 0;
        c.gridy = 4;
        descriptionPane.add(emailPanel, c);
        creditCardBox = new JTextField(16);
        JPanel creditCardPanel = new JPanel(new FlowLayout());
        creditCardPanel.add(new JLabel("Credit Card Number: "));
        creditCardPanel.add(creditCardBox);
        c.gridx = 0;
        c.gridy = 5;
        descriptionPane.add(creditCardPanel, c);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        addRoomButton = new JButton("Add Room");
        addRoomButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int y = table.getSelectedRow();
        		
        		if (y < 0) {
        			JOptionPane.showMessageDialog(null, "You have not selected a row!");
        		}
        		else {
        			// Parsing the input
        			Calendar startDate = Calendar.getInstance();
            		Calendar endDate = Calendar.getInstance();
            		int guests = 0;
            		Room room = null;
            		String guestName = "";
            		String guestEmail = "";
            		String creditCardNumber = "";
	        		try {
	            		startDate.setTime(formatter.parse(startDateBox.getText()));
	            		endDate.setTime(formatter.parse(endDateBox.getText()));
	            		
	            		room = GlobalVariables.rs.getRoom((int)table.getValueAt(y, 0));
	            		guests = Integer.parseInt(guestsBox.getText());
	            		guestName = nameBox.getText();
	            		guestEmail = emailBox.getText();
	            		creditCardNumber = creditCardBox.getText();
	        		}
	        		catch(ParseException exp) {
	        			JOptionPane.showMessageDialog(null, "Invalid Date Format or Number");
	        		}
	        		
	        		// Checking for exceptions
	        		if (endDate.getTimeInMillis() < startDate.getTimeInMillis()) {
	        			JOptionPane.showMessageDialog(null, "The Start Date is more than the End Date!");
	        			
	        			return;
	        		}
                    //assert room != null;
                    if (guests > room.getMaxOccupancy()) {
	        			JOptionPane.showMessageDialog(null, "Can't have more guests than a room's max occupancy");
	        			
	        			return;
	        		}
	        		if (guests == 0) {
	        			JOptionPane.showMessageDialog(null, "Can't have a room with zero guests!");
	        			return;
	        		}
	        		if (guestName.isEmpty() || creditCardNumber.isEmpty() || guestEmail.isEmpty()) {
	        			JOptionPane.showMessageDialog(null, "Please fill in the unfilled containers");
	        			return;
	        		}
	        		
	        		// Reserving the room (Did not implement with the presence of Guest accounts yet)
	        		// Assuming no sign in is needed.
	        		int reply = JOptionPane.showConfirmDialog(
	        				null,
	        				"Are you sure you want to reserve this room?",
	        				"Reserve Room?",
	        				JOptionPane.YES_NO_OPTION
	        				);
	        		if (reply == JOptionPane.YES_OPTION) {
	        			GlobalVariables.reS.reserveRoom(room, startDate, endDate, guests, guestName, guestEmail, creditCardNumber);
	        			JOptionPane.showMessageDialog(null, "Reservation Successful!");
	        			
	        			HomePage newFrame = new HomePage();
	        			dispose();
	        			
	        		}
        		}
        		
        		
        	}
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        descriptionPane.add(addRoomButton, c);
        // Add an Alternative Case where the User can't register for a room they already registered for.
		
		mainPane.add(alignScrollPane, BorderLayout.LINE_START);
		//descriptionPane.setSize(new Dimension(100, table.getHeight() - 100));
		mainPane.add(descriptionPane, BorderLayout.CENTER);

		setVisible(true);
		if (rooms.isEmpty()) {
			JOptionPane.showMessageDialog(null, "There are no rooms with the given criteria.");
		}
	}
}
