package stay_and_shop_system.store.ui;

import stay_and_shop_system.*;
import stay_and_shop_system.store.StoreService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class StorePage extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel pagePane;
    private JPanel contentPane;

    private final StoreService storeService = new StoreService();

    private final String guestEmail;

    public StorePage(String guestEmail) {
        this.guestEmail = guestEmail;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Main.WINDOW_W, Main.WINDOW_H);
        setLocationRelativeTo(null); // Centers the JFrame
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
        contentPane.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(12, 0, 12, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel titleLabel = new JLabel("Store");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(700, 45));
        contentPane.add(titleLabel, c);

        JLabel subtitleLabel = new JLabel("Subtitle");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setForeground(ColorPalette.DESATURATED_LIGHTBLUE);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        contentPane.add(subtitleLabel, c);

        pagePane.add(contentPane);
        setVisible(true);
    }
}