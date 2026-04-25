package stay_and_shop_system.occupancy.ui;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import stay_and_shop_system.*;
import stay_and_shop_system.occupancy.*;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.PaymentMethod;
import stay_and_shop_system.user.ui.CancelReservationPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Calendar;


// Reasoning behind Reserving room while an account of varying types exists, or an account does not exist.
// In order to convert a class to a guest, they need a paymentMethod at minimum(be a part of the guest Interface)
public class BookingPage extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel popupPane;
	private JPanel mainPane;
	private JPanel headerPane;
	private JPanel searchPane;
	private JPanel roomsPane;
	private JPanel guestInfoPanel;
	private JPanel pagePane;
	private JButton reserveButton;

	private JButton prevButton;
	private JButton currButton;

	private JTextField guestNameText;
	private JTextField guestEmailText;
	private JTextField guestPhoneText;
	private JTextField guestCCNText;
	private JTextField guestCCVText;
	private JTextField guestBillAText;
	private JTextField guestExpDateText;
	private JTextField guestCountText;

	private Timer timer;

	private ReservationController rc = new ReservationController();
	private RoomCriteria rCr;
	private SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");

	public JLabel createBackground() {
		pagePane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		ImageIcon oceanIcon = new ImageIcon("src/main/resources/rama-krushna-behera-_mxyYjyhWQ0-unsplash-edited2.png");
		ImageIcon newOceanIcon = new ImageIcon(oceanIcon.getImage().getScaledInstance((int)(oceanIcon.getIconWidth() * 0.38), (int)(oceanIcon.getIconHeight() * 0.38), Image.SCALE_SMOOTH));
		JLabel background = new JLabel(newOceanIcon);
		background.setLayout(new FlowLayout()); // AI: Allows adding buttons/text on top
		c.gridx = 0;
		c.gridy = 0;
		pagePane.add(background, c);
		JScrollPane backgroundWrapper = new JScrollPane(pagePane);
		backgroundWrapper.setViewportBorder(null);
		backgroundWrapper.setBorder(null);
		backgroundWrapper.setOpaque(false);
		backgroundWrapper.getViewport().setOpaque(false);
		backgroundWrapper.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE; // Color of the draggable bar
				this.trackColor = ColorPalette.OCEAN_DARKBLUE; // Color of the background track
			}
		});
		backgroundWrapper.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        pagePane = new ImagePanel("src/main/resources/HomePageOcean.jpeg");
		mainPane.add(backgroundWrapper, BorderLayout.CENTER);

		return background;
	}
	public JButton makeProgressBar() {
		JButton outerBar = new JButton();
		outerBar.setFocusPainted(false);
		outerBar.setBackground(ColorPalette.OCEAN_DARKBLUE);
		outerBar.setBorder(BorderFactory.createMatteBorder(5,5,5,5, ColorPalette.OCEAN_DARKBLUE));
		outerBar.setPreferredSize(new Dimension(600, 30));

		JButton innerBar = new JButton();
		innerBar.setPreferredSize(new Dimension((int)(600 * 0.33), 30));

		innerBar.setFocusPainted(false);
		innerBar.setBackground(ColorPalette.OCEAN_LIGHTBLUE);

		outerBar.setLayout(new BorderLayout());
		outerBar.add(innerBar, BorderLayout.LINE_START);

		int maxWidth = (int)(600 * 0.66);
		long currTime = System.currentTimeMillis();
		long duration = 1700; // 1000 millis = 1 second

		timer = new Timer(13, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(maxWidth);
				System.out.println(innerBar.getWidth());
				if (innerBar.getWidth() < maxWidth) {
					long elapsed = System.currentTimeMillis() - currTime;
					int addSize = Math.max(1, (int)(((duration - elapsed) * 5) / duration)); // elapsed/duration = x/5 - to find the integer value for each elapsed number
					innerBar.setPreferredSize(new Dimension(innerBar.getWidth() + addSize, innerBar.getHeight()));

					outerBar.revalidate();
					outerBar.repaint();
				} else {
					((Timer)e.getSource()).stop(); // Stop when max size reached
					innerBar.setPreferredSize(new Dimension(maxWidth, innerBar.getHeight()));
				}
			}
		});

		return outerBar;
	}
	public void addGuestInformation() {
		guestInfoPanel = new JPanel(new GridBagLayout());
		guestInfoPanel.setPreferredSize(new Dimension(500, 350));
		guestInfoPanel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.LINE_START;
		String internalPadding = "               ";

		JPanel guestNameWrapper = new JPanel();
		guestNameWrapper.setOpaque(false);
		// Dont ask me why I did this.
		JLabel guestName = new JLabel("Full Name:                 ");
		guestName.setFont(new Font("Serif", Font.ITALIC, 16));
		guestNameText = new JTextField(12);
		guestNameText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		guestNameWrapper.add(guestName);
		guestNameWrapper.add(guestNameText);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		c.insets = new Insets(0, 0, 0, 0);
		guestInfoPanel.add(guestNameWrapper, c);


		JPanel guestEmailWrapper = new JPanel();
		guestEmailWrapper.setOpaque(false);
		JLabel guestEmail = new JLabel("Email:                        ");
		guestEmail.setFont(new Font("Serif", Font.ITALIC, 16));
		guestEmailText = new JTextField(16);
		guestEmailText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		guestEmailWrapper.add(guestEmail);
		guestEmailWrapper.add(guestEmailText);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		guestInfoPanel.add(guestEmailWrapper, c);

		JPanel guestPhoneWrapper = new JPanel();
		guestPhoneWrapper.setOpaque(false);
		JLabel guestPhone = new JLabel("Phone Number(Inter.): ");
		guestPhone.setFont(new Font("Serif", Font.ITALIC, 16));
		guestPhoneText = new JTextField(16);
		guestPhoneText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		guestPhoneWrapper.add(guestPhone);
		guestPhoneWrapper.add(guestPhoneText);
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		guestInfoPanel.add(guestPhoneWrapper, c);

		JPanel guestCCNWrapper = new JPanel();
		guestCCNWrapper.setOpaque(false);
		JLabel guestCCN = new JLabel("Credit Card Number: ");
		guestCCN.setFont(new Font("Serif", Font.ITALIC, 16));
		guestCCNText = new JTextField(16);
		guestCCNText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		guestCCNWrapper.add(guestCCN);
		guestCCNWrapper.add(guestCCNText);
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0, 0, 0, 0);
		guestInfoPanel.add(guestCCNWrapper, c);


		JPanel guestCCVWrapper = new JPanel();
		guestCCVWrapper.setOpaque(false);
		JLabel guestCCV = new JLabel("CCV:                 ");
		guestCCV.setFont(new Font("Serif", Font.ITALIC, 16));
		guestCCVText = new JTextField(16);
		guestCCVText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		guestCCVWrapper.add(guestCCV);
		guestCCVWrapper.add(guestCCVText);
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		guestInfoPanel.add(guestCCVWrapper, c);

		JPanel guestBillAWrapper = new JPanel();
		guestBillAWrapper.setOpaque(false);
		JLabel guestBillA = new JLabel("Billing Address: ");
		guestBillA.setFont(new Font("Serif", Font.ITALIC, 16));
		guestBillAText = new JTextField(16);
		guestBillAText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		guestBillAWrapper.add(guestBillA);
		guestBillAWrapper.add(guestBillAText);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		guestInfoPanel.add(guestBillAWrapper, c);

		JPanel guestExpDateWrapper = new JPanel();
		guestExpDateWrapper.setOpaque(false);
		JLabel guestExpDate = new JLabel("Expiration Date: ");
		guestExpDate.setFont(new Font("Serif", Font.ITALIC, 16));
		guestExpDateText = new JTextField(16);
		guestExpDateText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		guestExpDateWrapper.add(guestExpDate);
		guestExpDateWrapper.add(guestExpDateText);
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		guestInfoPanel.add(guestExpDateWrapper, c);

		JPanel guestCountWrapper = new JPanel();
		guestCountWrapper.setOpaque(false);
		JLabel guestCount = new JLabel("Amount of Guests: ");
		guestCount.setFont(new Font("Serif", Font.ITALIC, 16));
		guestCountText = new JTextField(16);
		guestCountText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		guestCountWrapper.add(guestCount);
		guestCountWrapper.add(guestCountText);
		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(0, 0, 0, 0);
		guestInfoPanel.add(guestCountWrapper, c);

		JPanel reserveBWrapper = new JPanel(new GridBagLayout());
		reserveBWrapper.setOpaque(false);
//		searchBWrapper.setPreferredSize(new Dimension(300, 300));
		reserveButton = new JButton("Can't Reserve Room");
		reserveButton.setEnabled(false);
		reserveButton.setFont(new Font("Serif", Font.ITALIC, 27));
		reserveButton.setForeground(ColorPalette.OCEAN_BLUE);
		reserveButton.setBackground(ColorPalette.OCEAN_DARKBLUE);
		reserveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(
						null,
						"Do you want to reserve room " + currButton.getName() + "?",
						"Reserve Room " + currButton.getName() + "?",
						JOptionPane.YES_NO_OPTION
				);
				if (choice == JOptionPane.YES_OPTION) {
					try {
						Calendar expDate = Calendar.getInstance();
						expDate.setTime(formatter.parse(guestExpDateText.getText()));
						Object[] reserveInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(Integer.parseInt(currButton.getName())), rCr.getDateRange()[0],
								rCr.getDateRange()[1], Integer.parseInt(guestCountText.getText()), guestNameText.getText(),
								guestEmailText.getText(), guestPhoneText.getText(), guestCCNText.getText(), guestCCVText.getText(),
								guestBillAText.getText(), expDate);

						ReservationSuccessPage newFrame = new ReservationSuccessPage((int)reserveInfo[0], (double)reserveInfo[1],
								rCr.getDateRange()[0], rCr.getDateRange()[1]);
					}
					catch (ParseException exp) {
						JOptionPane.showMessageDialog(null, "Failed to reserve the room.");
						exp.printStackTrace();
					}
					dispose();
				}

			}
		});
		c.gridx = 4;
		c.gridy = 0;
		c.ipadx = 0;
		c.gridheight = 4;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.insets = new Insets(0, 0, 0, 45);
		c.fill = GridBagConstraints.HORIZONTAL;
		reserveBWrapper.add(reserveButton);
		guestInfoPanel.add(reserveBWrapper, c);


	}
	public void addGuestInfoListeners() {
		JTextField[] jts = new JTextField[] {guestNameText, guestEmailText, guestPhoneText, guestCCNText, guestCCVText, guestBillAText, guestExpDateText, guestCountText};
		for (JTextField jt : jts) {
			jt.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) { update(); }
				public void removeUpdate(DocumentEvent e) { update(); }
				public void changedUpdate(DocumentEvent e) { update(); }

				public void update() {
					checkForExceptions();
				}
			});
		}
	}
	public void checkForExceptions() {
		boolean exception = false;
		JTextField[] jts = new JTextField[] {guestNameText, guestEmailText, guestCCNText, guestCCVText, guestBillAText, guestExpDateText};

		for (int i = 0; i < jts.length; ++i) {

		}

		if (guestNameText.getText().isEmpty()) {
			guestNameText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		else {
			guestNameText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		}
		if (guestEmailText.getText().isEmpty()) {
			guestEmailText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		else {
			guestEmailText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		}

		boolean validEmail = isValidEmail(guestEmailText.getText());
		if (!validEmail) {
			guestEmailText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		else {
			guestEmailText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		}

		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		Phonenumber.PhoneNumber guestPhoneNumber = new Phonenumber.PhoneNumber();
		boolean validPhoneNumber = false;
		try {
			// Parsing international phone number
			guestPhoneNumber = phoneUtil.parse(guestPhoneText.getText(), null);

			validPhoneNumber = phoneUtil.isValidNumber(guestPhoneNumber);
		} catch (NumberParseException e) {
			guestPhoneText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		if (!validPhoneNumber) {
			guestPhoneText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		else {
			guestPhoneText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		}

		// Fake(for testing) credit card numbers: https://www.freeformatter.com/credit-card-number-generator-validator.html
		String ccn = guestCCNText.getText().replaceAll(" ", "");
		if (ccn.isEmpty() || ! LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(ccn)) {
			guestCCNText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		else {
			guestCCNText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		}
		changeEditabilityOfReserveButton(false);

		String ccv = guestCCVText.getText();
		// The regex below is for checking if it's only numbers in there
		if (ccv.length() > 4 || ccv.length() < 3 || !ccv.matches("[0-9]+")) {
			guestCCVText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		else {
			guestCCVText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		}

		String billA = guestBillAText.getText();
		boolean invalidAddress = false;
		if (billA.isEmpty()) {
			guestBillAText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		else {
			guestBillAText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		}

		Calendar expDate = Calendar.getInstance();
		Calendar todayDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
		try {
			expDate.setTime(formatter.parse(guestExpDateText.getText()));
			// Getting rid of the minutes and seconds in today's date.
			todayDate.setTime(formatter.parse(formatter.format(Calendar.getInstance().getTime())));

		}
		catch (Exception e) {
			guestExpDateText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		System.out.println(expDate.getTime());
		System.out.println(todayDate.getTime());
		if (expDate.before(todayDate)) {
			guestExpDateText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		else {
			guestExpDateText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
		}

		try {
			Integer.parseInt(guestCountText.getText());
		}
		catch (NumberFormatException e) {
			guestCountText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
			changeEditabilityOfReserveButton(true);
			return;
		}
		guestCountText.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));

		if (currButton == null) {
			changeEditabilityOfReserveButton(true);
			return;
		}
		changeEditabilityOfReserveButton(false);
	}
	public boolean isValidEmail(String email) {
		// The regex means the delimiter will be kept within the start of the next segment. The delimiters are in the brackets '.' and '@'.
		String[] spl = email.split("(?=[.@])");
		Arrays.stream(spl).forEach(s -> System.out.println(s));
		// There can be multiple dots before the @
		boolean invalidEmail = false, atSignExists = false;
		for (int i = 0; i < spl.length && !invalidEmail; ++i) {
			if (spl[i].contains("@")) {
				atSignExists = true;
				if (spl.length - i != 2 || spl[i+1].contains("@") || spl[i].length() == 1 || i == 0) {
					invalidEmail = true;
				}
			}
			else {
				if (spl[i].length() == 1 && !Character.isLetter(spl[i].charAt(0))) { // when consecutive '..'
					invalidEmail = true;
				}
			}
		}

		return (!invalidEmail && atSignExists);
	}
	public void changeEditabilityOfReserveButton(boolean cantEdit) {
		if (cantEdit) {
			reserveButton.setEnabled(false);
			reserveButton.setText("Can't Reserve Room");
		}
		else {
			reserveButton.setEnabled(true);
			reserveButton.setText("Reserve Room");
		}
	}
	public void loadRoomsOnScreen(List<Room> rooms, boolean modifyRoomBool) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;

		int i = 0;
		for (Room r : rooms) {
			JPanel roomPanel = new JPanel(new GridBagLayout());
			roomPanel.setPreferredSize(new Dimension(700, 440));
			GridBagConstraints c2 = new GridBagConstraints();

			// 4 X 4
			JLabel roomTitle = new JLabel("ROOM #" + r.getNumber() + " " + floorNumberToString(r.getNumber()) + " " + r.getRoomSize().toString());
			roomTitle.setFont(new Font("Serif", Font.ITALIC, 20));
			roomTitle.setForeground(ColorPalette.DESATURATED_DARKBLUE);
			c2.gridx = 0;
			c2.gridy = 0;
			c2.weightx = 2;
			roomPanel.add(roomTitle, c2);
			// return button's name to find room

			JPanel roomInfoPanel = new JPanel(new GridBagLayout());
			roomInfoPanel.setOpaque(false);
			GridBagConstraints c3 = new GridBagConstraints();
			c2.gridx = 1;
			c2.gridy = 1;
			c2.weightx = 1;
			roomPanel.add(roomInfoPanel, c2);

			JLabel bedsLabel = new JLabel("Bed Types: " + makeBedTypesString(r.getBedTypes()));
			bedsLabel.setFont(new Font("Serif", Font.ITALIC, 15));
			bedsLabel.setPreferredSize(new Dimension(300, 50));
			bedsLabel.setForeground(ColorPalette.DESATURATED_DARKBLUE);
			c3.gridx = 0;
			c3.gridy = 0;
			roomInfoPanel.add(bedsLabel, c3);

			JLabel costLabel = new JLabel("Cost: $" + r.getDailyRate() + " per night");
			costLabel.setFont(new Font("Serif", Font.ITALIC, 15));
			costLabel.setPreferredSize(new Dimension(300, 50));
			costLabel.setForeground(ColorPalette.DESATURATED_DARKBLUE);
			c3.gridx = 0;
			c3.gridy = 1;
			roomInfoPanel.add(costLabel, c3);

			JButton bookButton = new JButton("BOOK NOW");
			bookButton.setFont(new Font("Serif", Font.ITALIC, 20));
			bookButton.setForeground(ColorPalette.OCEAN_BLUE);
			bookButton.setPreferredSize(new Dimension(250, 50));
			bookButton.setBackground(ColorPalette.OCEAN_DARKBLUE);
			c2.gridx = 1;
			c2.gridy = 2;
			c2.insets = new Insets(20, 0, 0, 0);
			roomPanel.add(bookButton, c2);
			bookButton.setName(Integer.toString(r.getNumber()));
			bookButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (currButton != null) {
						currButton.setEnabled(true);
						prevButton = currButton;
					}
					currButton = (JButton) e.getSource();
					currButton.setEnabled(false);

					if (modifyRoomBool) {
						changeEditabilityOfReserveButton(false);
					}
					else {
						checkForExceptions();
					}
				}
			});

			JLabel roomImg = new JLabel("Loading...");
			roomImg.setPreferredSize(new Dimension(325, 225));
			roomImg.setOpaque(true);
			roomImg.setBackground(ColorPalette.INVALID_RED);
			c2.gridx = 0;
			c2.gridy = 1;
			c2.insets = new Insets(10, 0, 0, 0);
			roomPanel.add(roomImg, c2);
//			c.gridx = 0;
//			c.gridy = i;
//			++i;
//			roomsPane.add(roomPanel, c);
			roomsPane.add(roomPanel);
			loadImageForRoom(roomImg, r.getNumber());

		}
	}
	public String floorNumberToString(int number) {
		if (number >= 100 && number < 200) {
			return "Nature Retreat";
		}
		else if (number >= 200 && number < 300) {
			return "Urban Elegance";
		}
		else if (number >= 300 && number < 400) {
			return "Vintage Charm";
		}
		else {
			return "NULL";
		}
	}
	public String makeBedTypesString(List<Room.BedType> bTs) {
		String bStr = bTs.getFirst().toString();
		for (int i = 1; i < bTs.size(); ++i) {
			bStr += ", " + bTs.get(i);
		}

		return bStr;
	}
	public void loadImageForRoom(JLabel img, int roomNumber) {

		if (roomNumber >= 100 && roomNumber < 200) {
			ImageWorker iw = new ImageWorker("src/main/resources/small_pexels-luis-zambrano-3782493-16436919.jpg", img, 325, 225);
			iw.execute();
		} else if (roomNumber >= 200 && roomNumber < 300) {
			ImageWorker iw = new ImageWorker("src/main/resources/small_pexels-quang-nguyen-vinh-222549-14021928.jpg", img, 325, 225);
			iw.execute();
		} else if (roomNumber >= 300 && roomNumber < 400) {
			ImageWorker iw = new ImageWorker("src/main/resources/small_mohamed-jamil-latrach-2YgoP7wLq8k-unsplash.jpg.jpg", img, 325, 225);
			iw.execute();
		}
		else {
			img.setText("ROOM NUMBER NOT IN RANGE");
		}


	}
	public BookingPage(List<Room> rooms, RoomCriteria roomCriteria, Object[] reservationData) {
		rCr = roomCriteria;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);
		setLocationRelativeTo(null); // Centers the screen
		setTitle(Main.APP_TITLE);

		Object[] uiObjects = SetupUI.initializeScreen(popupPane, mainPane, this);
		popupPane = (JPanel) uiObjects[0];
		mainPane = (JPanel) uiObjects[1];

		JLabel background = createBackground();
		background.setLayout(new BorderLayout());

		roomsPane = new JPanel(new GridLayout(0, 1));
		roomsPane.setOpaque(false);
		loadRoomsOnScreen(rooms, (reservationData != null && reservationData.length > 1));

		JPanel roomsWrapper = new JPanel(new GridBagLayout()); // To force what im wrapping to be it's preferred size bc BorderLayout doesnt repsect it.
		roomsWrapper.setOpaque(false);
		JScrollPane roomsScrollPane = new JScrollPane(roomsPane);
		roomsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		roomsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		roomsScrollPane.setOpaque(false);
		roomsScrollPane.getViewport().setOpaque(false);
		roomsScrollPane.setBorder(null);
		roomsScrollPane.setViewportBorder(null);
		roomsScrollPane.setPreferredSize(new Dimension(700, 440));
		roomsScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE; // Color of the draggable bar
				this.trackColor = ColorPalette.OCEAN_DARKBLUE; // Color of the background track
			}
		});
		roomsScrollPane.add(new JLabel("Joms"));
		roomsWrapper.add(roomsScrollPane);
		background.add(roomsWrapper, BorderLayout.CENTER);



		if (reservationData == null || reservationData.length == 1) {
			JPanel progressWrapper = new JPanel(new FlowLayout());
			progressWrapper.setOpaque(false);
			JButton progressBar = makeProgressBar();
			progressWrapper.add(progressBar);
			background.add(progressWrapper, BorderLayout.PAGE_START);


			addGuestInformation();
			addGuestInfoListeners();
			if (reservationData != null)  {
				guestEmailText.setText((String)reservationData[0]);
				guestEmailText.setEnabled(false);
			}
		}
		else {
			guestInfoPanel = new JPanel(new GridBagLayout());
			guestInfoPanel.setPreferredSize(new Dimension(500, 350));
			guestInfoPanel.setOpaque(false);

			reserveButton = new JButton("Can't Reserve Room");
			reserveButton.setEnabled(false);
			reserveButton.setFont(new Font("Serif", Font.ITALIC, 27));
			reserveButton.setForeground(ColorPalette.OCEAN_BLUE);
			reserveButton.setBackground(ColorPalette.OCEAN_DARKBLUE);
			reserveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int choice = JOptionPane.showConfirmDialog(
							null,
							"Do you want to choose this room " + currButton.getName() + "?",
							"Choose Room " + currButton.getName() + "?",
							JOptionPane.YES_NO_OPTION
					);
					if (choice == JOptionPane.YES_OPTION) {
						int roomNum = Integer.parseInt(currButton.getName());
						reservationData[5] = roomNum;

						CancelReservationPage newFrame = new CancelReservationPage((int)reservationData[6], reservationData);
						dispose();
					}

				}
			});
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			guestInfoPanel.add(reserveButton, c);


		}
		background.add(guestInfoPanel, BorderLayout.PAGE_END);

		setVisible(true);

		if (reservationData == null) {
			timer.start();
		}
		else {


		}

	}
	@Override
	public void dispose() {
		if (timer != null) {
			timer.stop();
		}
		super.dispose();
	}
	// Taken From: https://stackoverflow.com/questions/4530428/to-display-an-image/4530659#4530659
	// To lazy load images
	class ImageWorker extends SwingWorker<Image, Void> {

		private final String imgPath;
		private final JLabel imgContainer;
//		private final int scaleX, scaleY;
		private final int sizeX, sizeY;

		public ImageWorker(String imgPath, JLabel imgContainer,int sX, int sY) {
			this.imgPath = imgPath;
			this.imgContainer = imgContainer;
			sizeX = sX;
			sizeY = sY;
		}
		@Override
		protected Image doInBackground() throws IOException {
			System.out.println("DoInBackground");
			BufferedImage image = ImageIO.read(new File(imgPath));
			return image.getScaledInstance(sizeX, sizeY, Image.SCALE_SMOOTH);
		}

		@Override
		protected void done() {
			try {
				// get() - get's the image of the ImageWorker
				ImageIcon icon = new ImageIcon(get());
				imgContainer.setIcon(icon);
				imgContainer.setText("");
				imgContainer.setOpaque(false);
				System.out.println("done()");

				roomsPane.revalidate();
				roomsPane.repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
