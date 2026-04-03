package stay_and_shop_system;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

// Note to self: in order to make A Scrollable Panel with a background, a JLabel with an image in it
// (and have the JLabel's layout set) will need to contain that JScrollPane(which contains the content you intend to make)

public class HomePage2 extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel mainPane;
    private JPanel headerPane;
    // Overriding JPanel class
    private JPanel pagePane;

    HomePage2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setTitle(Main.APP_TITLE);

        // Border layout is the only layout manager that fills it's objects when it's available, as far as im aware of.
        mainPane = new JPanel(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        setContentPane(mainPane);

        headerPane = new JPanel();
        mainPane.add(headerPane, BorderLayout.PAGE_START);
        headerPane.setBackground(ColorPalette.OCEAN_DARKBLUE);

        ImageIcon menuButtonIcon = new ImageIcon("src/main/resources/menuButton.png");
        ImageIcon newMenuButtonIcon = new ImageIcon(menuButtonIcon.getImage().getScaledInstance((int)(menuButtonIcon.getIconWidth() * 0.6), (int)(menuButtonIcon.getIconHeight() * 0.6), Image.SCALE_SMOOTH));
        JLabel menuButtonLabel = new JLabel(newMenuButtonIcon);
        headerPane.add(menuButtonLabel);
        JLabel jl = new JLabel("THE OCEAN'S WATERS HOTEL");
        jl.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        jl.setFont(new Font("Serif", Font.PLAIN, 30));
        headerPane.add(jl);
        JButton bookRoomButton = new JButton("Book Room");
        bookRoomButton.setBackground(ColorPalette.OCEAN_LIGHTBLUE);
        bookRoomButton.setForeground(ColorPalette.OCEAN_DARKBLUE);
        bookRoomButton.setFont(new Font("Serif", Font.BOLD, 16));
        headerPane.add(bookRoomButton);

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
        background.setLayout(new FlowLayout()); // Allows adding buttons/text on top
        pagePane.add(background);
//        pagePane = new ImagePanel("src/main/resources/HomePageOcean.jpeg");
        mainPane.add(pageScrollPane, BorderLayout.CENTER);


        setVisible(true);
    }
}
