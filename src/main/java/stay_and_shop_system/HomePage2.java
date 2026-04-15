package stay_and_shop_system;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Note to self: in order to make A Scrollable Panel with a background, a JLabel with an image in it
// (and have the JLabel's layout set) will need to contain that JScrollPane(which contains the content you intend to make)

public class HomePage2 extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel headerPane;
    private JPanel pagePane;

    public HomePage2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null); // Centers the screen
        setTitle(Main.APP_TITLE);

        // Made SetupUI to make this code more readable, and also since we will be using this throughout the website
        Object[] uiObjects = SetupUI.initializeScreen(popupPane, mainPane, this);
        popupPane = (JPanel) uiObjects[0];
        mainPane = (JPanel) uiObjects[1];

        pagePane = new JPanel(new GridBagLayout());
        JScrollPane pageScrollPane = new JScrollPane(pagePane);
        pageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pageScrollPane.setBorder(null);
        pageScrollPane.setViewportBorder(null);
        pageScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE; // Color of the draggable bar
                this.trackColor = ColorPalette.OCEAN_DARKBLUE; // Color of the background track
            }
        });
        ImageIcon oceanIcon = new ImageIcon("src/main/resources/paul-berthelon-bravo-7fS_d461RNY-unsplash.jpg");
        ImageIcon newOceanIcon = new ImageIcon(oceanIcon.getImage().getScaledInstance((int)(oceanIcon.getIconWidth() * 0.2), (int)(oceanIcon.getIconHeight() * 0.2), Image.SCALE_SMOOTH));
        JLabel background = new JLabel(newOceanIcon);
        background.setLayout(new FlowLayout()); // AI: Allows adding buttons/text on top
        pagePane.add(background);
//        pagePane = new ImagePanel("src/main/resources/HomePageOcean.jpeg");
        mainPane.add(pageScrollPane, BorderLayout.CENTER);


        setVisible(true);
    }
}
