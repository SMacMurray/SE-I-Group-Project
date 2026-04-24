package stay_and_shop_system.occupancy.ui;

import stay_and_shop_system.ColorPalette;
import stay_and_shop_system.HomePage2;
import stay_and_shop_system.Main;
import stay_and_shop_system.SetupUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class ReservationSuccessPage extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel pagePane;

    private Timer timer;

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
        innerBar.setPreferredSize(new Dimension((int)(600 * 0.66), 30));

        innerBar.setFocusPainted(false);
        innerBar.setBackground(ColorPalette.OCEAN_LIGHTBLUE);

        outerBar.setLayout(new BorderLayout());
        outerBar.add(innerBar, BorderLayout.LINE_START);

        int maxWidth = (int)(600 * 0.985);
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
    public ReservationSuccessPage(int guestId, double total, Calendar checkInDate, Calendar checkOutDate) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null); // Centers the screen
        setTitle(Main.APP_TITLE);

        Object[] uiObjects = SetupUI.initializeScreen(popupPane, mainPane, this);
        popupPane = (JPanel) uiObjects[0];
        mainPane = (JPanel) uiObjects[1];

        JLabel background = createBackground();
        background.setLayout(new BorderLayout());


        JPanel progressWrapper = new JPanel(new FlowLayout());
        progressWrapper.setOpaque(false);
        JButton progressBar = makeProgressBar();
        progressWrapper.add(progressBar);
        background.add(progressWrapper, BorderLayout.PAGE_START);
        progressWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        GridBagConstraints c = new GridBagConstraints();
        JPanel successPaneWrapper = new JPanel();
        successPaneWrapper.setOpaque(false);
        JPanel successPane = new JPanel(new GridBagLayout());
        successPane.setPreferredSize(new Dimension(800, 500));
        successPaneWrapper.add(successPane);
        background.add(successPaneWrapper, BorderLayout.CENTER);

        Color oceanDarkBlueTransparent = new Color(ColorPalette.OCEAN_DARKBLUE.getRed(), ColorPalette.OCEAN_DARKBLUE.getGreen(), ColorPalette.OCEAN_DARKBLUE.getBlue(), 50);
        successPane.setBackground(oceanDarkBlueTransparent);
//        successPane.setOpaque(false);
        successPane.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, ColorPalette.DESATURATED_LIGHTBLUE));

        JLabel titleLabel = new JLabel("Room Reserved!");
        titleLabel.setFont(new Font("Serif", Font.ITALIC, 65));
        titleLabel.setForeground(ColorPalette.DESATURATED_LIGHTBLUE);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 100, 0);
        successPane.add(titleLabel, c);

        JPanel guestIdPanel = new JPanel();
        guestIdPanel.setOpaque(false);
        JLabel guestIdLabel = new JLabel("Remember your guestId to find your reservations: ");
        guestIdLabel.setFont(new Font("Serif", Font.BOLD, 25));
        guestIdLabel.setForeground(ColorPalette.OCEAN_DARKBLUE);
        guestIdPanel.add(guestIdLabel);
        JLabel guestIdText = new JLabel(Integer.toString(guestId));
        guestIdText.setFont(new Font("Serif", Font.PLAIN, 25));
        guestIdText.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        guestIdPanel.add(guestIdText);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);

        successPane.add(guestIdPanel, c);

        JPanel totalPanel = new JPanel();
        totalPanel.setOpaque(false);
        JLabel totalLabel = new JLabel("Your total: ");
        totalLabel.setFont(new Font("Serif", Font.BOLD, 25));
        totalLabel.setForeground(ColorPalette.OCEAN_DARKBLUE);
        totalPanel.add(totalLabel);
        JLabel totalTextLabel = new JLabel("$" + total);
        totalTextLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        totalTextLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        totalPanel.add(totalTextLabel);
        c.gridx = 0;
        c.gridy = 2;
        successPane.add(totalPanel, c);



        JButton homePageButton = new JButton("Go To Home Page");
        homePageButton.setFont(new Font("Serif", Font.ITALIC, 40));
        homePageButton.setForeground(ColorPalette.OCEAN_BLUE);
        homePageButton.setBackground(ColorPalette.OCEAN_DARKBLUE);
        homePageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePage2 newFrame = new HomePage2();
                dispose();
            }
        });
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(60, 0, 0, 0);
        successPane.add(homePageButton, c);

        JTextArea fillTextArea = new JTextArea();
        fillTextArea.setPreferredSize(new Dimension(250, 150));
        fillTextArea.setOpaque(false);
        fillTextArea.setBorder(null);
        fillTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        fillTextArea.setEnabled(false);
        fillTextArea.setFocusable(false);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(0, 0, 0, 0);
        successPane.add(fillTextArea, c);

        setVisible(true);

        timer.start();

    }
    @Override
    public void dispose() {
        timer.stop();
        super.dispose();
    }

}
