package stay_and_shop_system.occupancy.ui;

import stay_and_shop_system.*;
import stay_and_shop_system.occupancy.ReservationController;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.RoomCriteria;
import stay_and_shop_system.occupancy.SearchController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Calendar;

public class SearchRoomPage2 extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel headerPane;
    private JPanel searchPane;
    private JPanel criteriaPanel;
    private JPanel pagePane;
    private JButton searchButton;

    private JTextField guestSRange;
    private JTextField guestERange;
    private JTextField bedSRange;
    private JTextField bedERange;
    private JList<String> smokingBox;
    private JList<String> floorList;
    private JList<String> bedList;
    private JList<String> roomTypeList;
    private JTextField costSRange;
    private JTextField costERange;
    private JTextField dateSRange;
    private JTextField dateERange;
    private Timer timer;

    ReservationController res = new ReservationController();
    SearchController sc = new SearchController();

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
//        pagePane = new ImagePanel("src/main/resources/HomePageOcean.jpeg");
        mainPane.add(pagePane, BorderLayout.CENTER);

        return background;
    }
    public JButton makeProgressBar() {
        JButton outerBar = new JButton();
        outerBar.setFocusPainted(false);
        outerBar.setBackground(ColorPalette.OCEAN_DARKBLUE);
        outerBar.setBorder(BorderFactory.createMatteBorder(5,5,5,5, ColorPalette.OCEAN_DARKBLUE));
        outerBar.setPreferredSize(new Dimension(600, 30));

        JButton innerBar = new JButton();
        innerBar.setPreferredSize(new Dimension(0, 30));

        innerBar.setFocusPainted(false);
        innerBar.setBackground(ColorPalette.OCEAN_LIGHTBLUE);

        outerBar.setLayout(new BorderLayout());
        outerBar.add(innerBar, BorderLayout.LINE_START);

        int maxWidth = (int)(600 * 0.33);
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

                    // Need to revalidate and repaint parent container.
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
    public void addSearchCriteria() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        JLabel dashLabel = new JLabel(" - ");
        dashLabel.setFont(new Font("Serif", Font.ITALIC, 16));

        JLabel guestsTitle = new JLabel("Guests");
        guestsTitle.setFont(new Font("Serif", Font.BOLD, 30));
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 25, 0);
        criteriaPanel.add(guestsTitle, c);

        JLabel guestCount = new JLabel("How Many Guests(Ex: 1 - 100)? : ");
        guestCount.setFont(new Font("Serif", Font.ITALIC, 16));
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 10, 0);
        criteriaPanel.add(guestCount, c);

        JPanel guestRangeWrapper = new JPanel();
        guestRangeWrapper.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        guestSRange = new JTextField(7);
        guestSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        guestRangeWrapper.add(guestSRange);
        guestRangeWrapper.add(new JLabel(" - "));
        guestERange = new JTextField(7);
        guestERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        guestRangeWrapper.add(guestERange);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 20, 0);
        criteriaPanel.add(guestRangeWrapper, c);

        JLabel bedCount = new JLabel("How Many Beds? ");
        bedCount.setFont(new Font("Serif", Font.ITALIC, 16));
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 0, 10, 0);
        criteriaPanel.add(bedCount, c);

        JPanel bedRangeWrapper = new JPanel();
        bedRangeWrapper.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        bedSRange = new JTextField(7);
        bedSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        bedRangeWrapper.add(bedSRange);
        bedRangeWrapper.add(new JLabel(" - "));
        bedERange = new JTextField(7);
        bedERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        bedRangeWrapper.add(bedERange);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(0, 0, 20, 0);
        criteriaPanel.add(bedRangeWrapper, c);

        JLabel smokingStatus = new JLabel("Smoking Status of Room(choose none/all for no preference): ");
        smokingStatus.setFont(new Font("Serif", Font.ITALIC, 16));
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(0, 0, 10, 0);
        criteriaPanel.add(smokingStatus, c);

        smokingBox = new JList<>(new String[] {"Permitted", "Prohibited"});
        smokingBox.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(0, 0, 40, 0);
        criteriaPanel.add(smokingBox, c);

        JLabel designTitle = new JLabel("Design");
        designTitle.setFont(new Font("Serif", Font.BOLD, 30));
        c.gridx = 0;
        c.gridy = 7;
        c.insets = new Insets(0, 0, 25, 0);
        criteriaPanel.add(designTitle, c);

        JLabel floorDesigns = new JLabel("Choose which floor design/s if needed");
        floorDesigns.setFont(new Font("Serif", Font.ITALIC, 16));
        c.gridx = 0;
        c.gridy = 8;
        c.insets = new Insets(0, 0, 10, 0);
        criteriaPanel.add(floorDesigns, c);

        floorList = new JList<>(new String[] {"Nature Retreat", "Urban Elegance","Vintage Charm"});
        floorList.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        floorList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        c.gridx = 0;
        c.gridy = 9;
        c.insets = new Insets(0, 0, 20, 0);
        criteriaPanel.add(floorList, c);

        JLabel bedTypes = new JLabel("Choose bed type/s if needed(rooms must have all selected)");
        bedTypes.setFont(new Font("Serif", Font.ITALIC, 16));
        c.gridx = 0;
        c.gridy = 10;
        c.insets = new Insets(0, 0, 10, 0);
        criteriaPanel.add(bedTypes, c);

        bedList = new JList<>(Arrays.stream(Room.BedType.values()).map(Enum::name).toArray(String[]::new));
        bedList.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        bedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        c.gridx = 0;
        c.gridy = 11;
        c.insets = new Insets(0, 0, 20, 0);
        criteriaPanel.add(bedList, c);

        JLabel roomType = new JLabel("Choose which room size/s if needed");
        roomType.setFont(new Font("Serif", Font.ITALIC, 16));
        c.gridx = 0;
        c.gridy = 12;
        c.insets = new Insets(0, 0, 10, 0);
        criteriaPanel.add(roomType, c);

        roomTypeList = new JList<>(Arrays.stream(Room.RoomSize.values()).map(Enum::name).toArray(String[]::new));
        roomTypeList.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        roomTypeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        c.gridx = 0;
        c.gridy = 13;
        c.insets = new Insets(0, 0, 40, 0);
        criteriaPanel.add(roomTypeList, c);

        JLabel costTitle = new JLabel("Cost");
        costTitle.setFont(new Font("Serif", Font.BOLD, 30));
        c.gridx = 0;
        c.gridy = 14;
        c.insets = new Insets(0, 0, 25, 0);
        criteriaPanel.add(costTitle, c);

        JLabel roomCost = new JLabel("Choose the cost range: ");
        roomCost.setFont(new Font("Serif", Font.ITALIC, 16));
        c.gridx = 0;
        c.gridy = 15;
        c.insets = new Insets(0, 0, 10, 0);
        criteriaPanel.add(roomCost, c);

        JPanel costRangeWrapper = new JPanel();
        costRangeWrapper.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        costSRange = new JTextField(7);
        costSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        costRangeWrapper.add(costSRange);
        costRangeWrapper.add(new JLabel(" - "));
        costERange = new JTextField(7);
        costERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        costRangeWrapper.add(costERange);
        c.gridx = 0;
        c.gridy = 16;
        c.insets = new Insets(0, 0, 40, 0);
        criteriaPanel.add(costRangeWrapper, c);

        JLabel dateTitle = new JLabel("Date of CheckIn / CheckOut");
        dateTitle.setFont(new Font("Serif", Font.BOLD, 30));
        c.gridx = 0;
        c.gridy = 17;
        c.insets = new Insets(0, 0, 25, 0);
        criteriaPanel.add(dateTitle, c);

        JLabel dateRange = new JLabel("Choose the date range(yyyy/MM/dd): ");
        dateRange.setFont(new Font("Serif", Font.ITALIC, 16));
        c.gridx = 0;
        c.gridy = 18;
        c.insets = new Insets(0, 0, 10, 0);
        criteriaPanel.add(dateRange, c);

        JPanel dateRangeWrapper = new JPanel();
        dateRangeWrapper.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        dateSRange = new JTextField(7);
        dateSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        dateRangeWrapper.add(dateSRange);
        dateRangeWrapper.add(new JLabel(" - "));
        dateERange = new JTextField(7);
        dateERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        dateRangeWrapper.add(dateERange);
        c.gridx = 0;
        c.gridy = 19;
        c.insets = new Insets(0, 0, 40, 0);
        criteriaPanel.add(dateRangeWrapper, c);
        // Made this so ALl the other cells align to the left since one cell needed to have weightx > 0. Do not need it anymore tho, probably bc the gridbaglayout does not haev a preferred size set.
//        JButton fillButton = new JButton();
//        SetupUI.makeButtonInvisible(fillButton);
//        c.gridx = 0;
//        c.gridy = 12;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weighty = 0.6; // any number higher than 0 because the rest of the components' weigths are defaulted to zero
//        c.weightx = 0.6; // any number higher than 0 because the rest of the components' weigths are defaulted to zero
//        c.insets = new Insets(0, 0, 0, 0);
//        criteriaPanel.add(fillButton, c);
    }
    public void addCriteriaListeners() {
        guestSRange.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                System.out.println("Text changed: " + guestSRange.getText());
                checkForExceptions();
            }
        });
        guestERange.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                System.out.println("Text changed: " + guestERange.getText());
                checkForExceptions();
            }
        });
        bedSRange.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                System.out.println("Text changed: " + guestSRange.getText());
                checkForExceptions();
            }
        });
        bedERange.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                System.out.println("Text changed: " + guestERange.getText());
                checkForExceptions();
            }
        });
        costSRange.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                System.out.println("Text changed: " + guestSRange.getText());
                checkForExceptions();
            }
        });
        costERange.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                System.out.println("Text changed: " + guestERange.getText());
                checkForExceptions();
            }
        });
        dateSRange.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                System.out.println("Text changed: " + guestSRange.getText());
                checkForExceptions();
            }
        });
        dateERange.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }

            public void update() {
                System.out.println("Text changed: " + guestERange.getText());
                checkForExceptions();
            }
        });
    }
    public void checkForExceptions() {
        boolean exception = false;
        JTextField[] jts = new JTextField[] { guestSRange, guestERange, bedSRange, bedERange};

        for (int i = 0; i < jts.length; i += 2) {
            int sR = (jts[i].getText().isEmpty() || !jts[i].getText().chars().allMatch(Character::isDigit)) ? -9999 : Integer.parseInt(jts[i].getText());
            int eR = (jts[i+1].getText().isEmpty() || !jts[i+1].getText().chars().allMatch(Character::isDigit)) ? -9999 : Integer.parseInt(jts[i+1].getText());

            if (sR < 1) {
                exception = true;
                jts[i].setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            }
            else jts[i].setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
            if (eR < 1) {
                exception = true;
                jts[i+1].setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            }
            else jts[i+1].setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
            if (exception) {
                changeEditabilityOfSearchButton(exception);
                return;
            }

            if (sR > eR) {
                jts[i].setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
                jts[i+1].setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
                changeEditabilityOfSearchButton(true);
                return;
            }
            else {
                jts[i].setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
                jts[i+1].setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
            }
        }

        double costS;
        try {
            costS = Double.parseDouble(costSRange.getText());
            costSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }
        catch (NumberFormatException e) {
            costSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        if (costS < 0) {
            costSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        else {
            costSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }
        double costE;
        try {
            costE = Double.parseDouble(costERange.getText());
            costERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }
        catch (NumberFormatException e) {
            costERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        if (costE < 0) {
            costERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        else {
            costERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }

        if (costS > costE) {
            costSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            costERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        else {
            costSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
            costERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }


        Calendar startDate = Calendar.getInstance();
        Calendar todayDate = Calendar.getInstance();
        try {
            startDate.setTime(res.getDateFormatter().parse(dateSRange.getText()));
            // Getting rid of the minutes and seconds in today's date.
            todayDate.setTime(res.getDateFormatter().parse(res.getDateFormatter().format(Calendar.getInstance().getTime())));
            dateSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }
        catch (Exception e) {
            dateSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        if (startDate.before(todayDate)) { // If startDate is before today's date.
            dateSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        else {
            dateSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }
        Calendar endDate = Calendar.getInstance();
        try {
            endDate.setTime(res.getDateFormatter().parse(dateERange.getText()));
            dateERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }
        catch (Exception e) {
            dateERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        if (endDate.before(todayDate)) {
            dateERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        else {
            dateERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }

        if (endDate.before(startDate)) {
            dateSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            dateERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.INVALID_RED));
            changeEditabilityOfSearchButton(true);
            return;
        }
        else {
            dateSRange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
            dateERange.setBorder(BorderFactory.createMatteBorder(2,2,2,2, ColorPalette.SATURATED_LIGHTBLUE));
        }

        changeEditabilityOfSearchButton(false);
    }
    public void changeEditabilityOfSearchButton(boolean cantEdit) {
        if (cantEdit) {
            searchButton.setEnabled(false);
            searchButton.setText("Can't Search Room");
        }
        else {
            searchButton.setEnabled(true);
            searchButton.setText("Search Room");
        }
    }
    public SearchRoomPage2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null); // Centers the screen
        setTitle(Main.APP_TITLE);

        Object[] uiObjects = SetupUI.initializeScreen(popupPane, mainPane, this);
        popupPane = (JPanel) uiObjects[0];
        mainPane = (JPanel) uiObjects[1];

        JLabel background = createBackground();

        background.setLayout(new BorderLayout());
        searchPane = new JPanel(new GridBagLayout());
        searchPane.setOpaque(false);
        searchPane.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        background.add(searchPane, BorderLayout.LINE_START);

        JLabel titleLabel = new JLabel("Search Your Room");
        titleLabel.setFont(new Font("Serif", Font.ITALIC, 40));
        titleLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        c.gridx = 0;
        c.gridy = 0;
        searchPane.add(titleLabel, c);

        criteriaPanel = new JPanel(new GridBagLayout());
        criteriaPanel.setOpaque(false);
        JScrollPane criteriaSP = new JScrollPane(criteriaPanel);
        criteriaSP.setPreferredSize(new Dimension(400, 500));
        criteriaSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        criteriaSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        criteriaSP.setOpaque(false);
        criteriaSP.getViewport().setOpaque(false);
        criteriaSP.setBorder(null);
        criteriaSP.setViewportBorder(null);
        criteriaSP.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE; // Color of the draggable bar
                this.trackColor = ColorPalette.OCEAN_DARKBLUE; // Color of the background track
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(30, 0, 0, 0);
        searchPane.add(criteriaSP, c);

        JButton fillButton1 = new JButton();
        fillButton1.setPreferredSize(new Dimension(300, 150));
        SetupUI.makeButtonInvisible(fillButton1);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        searchPane.add(fillButton1, c);


        JPanel progressWrapper = new JPanel(new FlowLayout());
        progressWrapper.setOpaque(false);
        JButton progressBar = makeProgressBar();
        progressWrapper.add(progressBar);
        background.add(progressWrapper, BorderLayout.PAGE_START);

        addSearchCriteria();
        addCriteriaListeners();

        JPanel searchBWrapper = new JPanel(new GridBagLayout());
        searchBWrapper.setOpaque(false);
        searchBWrapper.setPreferredSize(new Dimension(300, 300));
        searchButton = new JButton("Can't Search Room");
        searchButton.setEnabled(false);
        searchButton.setFont(new Font("Serif", Font.ITALIC, 27));
        searchButton.setForeground(ColorPalette.OCEAN_BLUE);
        searchButton.setBackground(ColorPalette.OCEAN_DARKBLUE);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] guestRange = new int[] { Integer.parseInt(guestSRange.getText()), Integer.parseInt(guestERange.getText()) };
                int[] bedRange = new int[] { Integer.parseInt(bedSRange.getText()), Integer.parseInt(bedERange.getText()) };
                List<Boolean> smokingStatuses = new ArrayList<>();
                for (String str : smokingBox.getSelectedValuesList()) {
                    if (str.equals("Permitted")) {
                        smokingStatuses.add(true);
                    }
                    else if (str.equals("Prohibited")){
                        smokingStatuses.add(false);
                    }
                    else {
                        smokingStatuses.add(null);
                    }
                }
                List<Integer> roomRanges = new ArrayList<>();
                for (String str : floorList.getSelectedValuesList()) {
                    if (str.equals("Nature Retreat")) {
                        roomRanges.add(100);
                    }
                    else if (str.equals("Urban Elegance")) {
                        roomRanges.add(200);
                    }
                    else if (str.equals("Vintage Charm")) {
                        roomRanges.add(300);
                    }
                    else {
                        roomRanges.add(null);
                    }
                }
                List<Room.BedType> bedTypes = new ArrayList<>();
                for (String str : bedList.getSelectedValuesList()) {
                    bedTypes.add(Room.BedType.valueOf(str));
                }
                List<Room.RoomSize> roomSizes = new ArrayList<>();
                for (String str : roomTypeList.getSelectedValuesList()) {
                    roomSizes.add(Room.RoomSize.valueOf(str));
                }
                double[] costRange = new double[] { Double.parseDouble(costSRange.getText()), Double.parseDouble(costERange.getText()) };
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                try {
                    startDate.setTime(res.getDateFormatter().parse(dateSRange.getText()));
                    endDate.setTime(res.getDateFormatter().parse(dateERange.getText()));
                }
                catch(ParseException exp) {
                    exp.printStackTrace();
                }
                Calendar[] dateRange = new Calendar[] { startDate, endDate};

                RoomCriteria rc = new RoomCriteria(guestRange, bedRange, smokingStatuses, roomRanges, bedTypes, roomSizes, costRange, dateRange);

                BookingPage newFrame = new BookingPage(sc.searchAvailableRooms(rc));
                dispose();
            }
        });
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.NONE;
        searchBWrapper.add(searchButton, c);
        JButton fillButton2 = new JButton();
        SetupUI.makeButtonInvisible(fillButton2);
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.175;
        searchBWrapper.add(fillButton2, c);
        background.add(searchBWrapper, BorderLayout.CENTER);

        setVisible(true);

        timer.start();

    }
    @Override
    public void dispose() {
        timer.stop(); // To prevent the timer from continuing when immediately leaving the page before the timer is done.
        super.dispose();
    }
}
