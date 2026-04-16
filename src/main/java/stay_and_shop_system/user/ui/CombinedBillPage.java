package stay_and_shop_system.user.ui;

import stay_and_shop_system.ColorPalette;
import stay_and_shop_system.Main;
import stay_and_shop_system.SetupUI;
import stay_and_shop_system.user.AccountSystem;
import stay_and_shop_system.user.CombinedBillService;
import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.store.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CombinedBillPage extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel popupPane;
    private JPanel mainPane;
    private JPanel pagePane;
    private JPanel contentPane;

    private final CombinedBillService billService = new CombinedBillService();

    public CombinedBillPage(boolean clerkMode) {
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
        contentPane.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(12, 0, 12, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel titleLabel = new JLabel(clerkMode ? "Guest Combined Bill" : "My Combined Bill");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(700, 45));
        contentPane.add(titleLabel, c);

        JLabel subtitleLabel = new JLabel("View hotel stay charges and shopping charges in one place");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setForeground(ColorPalette.DESATURATED_LIGHTBLUE);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        contentPane.add(subtitleLabel, c);

        JPanel lookupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        lookupPanel.setBackground(ColorPalette.OCEAN_BLUE);

        JLabel emailLabel = new JLabel("Guest Email:");
        emailLabel.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        emailLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        lookupPanel.add(emailLabel);

        JTextField emailField = new JTextField(24);
        emailField.setFont(new Font("Serif", Font.PLAIN, 16));
        lookupPanel.add(emailField);

        JButton loadButton = new JButton("Load Bill");
        loadButton.setFont(new Font("Serif", Font.BOLD, 16));
        loadButton.setBackground(ColorPalette.OCEAN_LIGHTBLUE);
        loadButton.setForeground(ColorPalette.OCEAN_DARKBLUE);
        lookupPanel.add(loadButton);

        if (!clerkMode) {
            String sessionEmail = "";
            if (AccountSystem.getSessionAccount() != null) {
                sessionEmail = AccountSystem.getSessionAccount().getEmail();
            }
            emailField.setText(sessionEmail);
            emailField.setEditable(false);
        }

        contentPane.add(lookupPanel, c);

        JTextArea billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setLineWrap(true);
        billArea.setWrapStyleWord(true);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        billArea.setBackground(ColorPalette.DESATURATED_LIGHTBLUE);
        billArea.setForeground(ColorPalette.DESATURATED_DARKBLUE);
        billArea.setBorder(new EmptyBorder(18, 18, 18, 18));

        JScrollPane billScrollPane = new JScrollPane(billArea);
        billScrollPane.setPreferredSize(new Dimension(760, 420));
        billScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.SATURATED_LIGHTBLUE;
                this.trackColor = ColorPalette.OCEAN_DARKBLUE;
            }
        });
        contentPane.add(billScrollPane, c);

        JPanel totalsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        totalsPanel.setBackground(ColorPalette.DESATURATED_DARKBLUE);

        JLabel stayTotalLabel = makeTotalCardLabel("Stay Total: $0.00");
        JLabel shoppingTotalLabel = makeTotalCardLabel("Shopping Total: $0.00");
        JLabel combinedTotalLabel = makeTotalCardLabel("Combined Total: $0.00");

        totalsPanel.add(wrapCard(stayTotalLabel));
        totalsPanel.add(wrapCard(shoppingTotalLabel));
        totalsPanel.add(wrapCard(combinedTotalLabel));

        contentPane.add(totalsPanel, c);

        loadButton.addActionListener(e -> {
            String email = emailField.getText().trim();

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a guest email.");
                return;
            }

            List<Reservation> reservations = billService.getReservationsForEmail(email);
            List<Product> products = billService.getProductsForEmail(email);

            double stayTotal = billService.getStayTotal(email);
            double shoppingTotal = billService.getShoppingTotal(email);
            double combinedTotal = billService.getCombinedTotal(email);

            stayTotalLabel.setText(String.format("Stay Total: $%.2f", stayTotal));
            shoppingTotalLabel.setText(String.format("Shopping Total: $%.2f", shoppingTotal));
            combinedTotalLabel.setText(String.format("Combined Total: $%.2f", combinedTotal));

            billArea.setText(buildBillText(email, reservations, products, stayTotal, shoppingTotal, combinedTotal));
            billArea.setCaretPosition(0);
        });

        pagePane.add(contentPane);
        setVisible(true);
    }

    private JLabel makeTotalCardLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(ColorPalette.OCEAN_LIGHTBLUE);
        label.setFont(new Font("Serif", Font.BOLD, 18));
        return label;
    }

    private JPanel wrapCard(JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorPalette.OCEAN_BLUE);
        panel.setBorder(new EmptyBorder(20, 10, 20, 10));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private String buildBillText(String email,
                                 List<Reservation> reservations,
                                 List<Product> products,
                                 double stayTotal,
                                 double shoppingTotal,
                                 double combinedTotal) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        StringBuilder sb = new StringBuilder();

        sb.append("THE OCEAN'S WATERS HOTEL\n");
        sb.append("COMBINED BILL SUMMARY\n");
        sb.append("============================================================\n\n");
        sb.append("Guest Email: ").append(email).append("\n\n");

        sb.append("HOTEL STAY CHARGES\n");
        sb.append("------------------------------------------------------------\n");

        if (reservations.isEmpty()) {
            sb.append("No reservations found.\n\n");
        } else {
            for (Reservation r : reservations) {
                long nights = (r.getEndDate().getTimeInMillis() - r.getStartDate().getTimeInMillis())
                        / (1000L * 60 * 60 * 24);
                if (nights <= 0) {
                    nights = 1;
                }

                double lineTotal = billService.calculateReservationTotal(r);

                sb.append("Room Number: ").append(r.getRoomNumber()).append("\n");
                sb.append("Guest Name: ").append(r.getGuestName()).append("\n");
                sb.append("Check-in: ").append(formatter.format(r.getStartDate().getTime())).append("\n");
                sb.append("Check-out: ").append(formatter.format(r.getEndDate().getTime())).append("\n");
                sb.append("Nightly Rate: $").append(String.format("%.2f", r.getRate())).append("\n");
                sb.append("Nights: ").append(nights).append("\n");
                sb.append("Reservation Total: $").append(String.format("%.2f", lineTotal)).append("\n");
                sb.append("------------------------------------------------------------\n");
            }
            sb.append("\n");
        }

        sb.append("Stay Total: $").append(String.format("%.2f", stayTotal)).append("\n\n");

        sb.append("SHOPPING CHARGES\n");
        sb.append("------------------------------------------------------------\n");

        if (products.isEmpty()) {
            sb.append("No store items found.\n\n");
        } else {
            for (Product p : products) {
                sb.append(p.getName())
                        .append(" - $")
                        .append(String.format("%.2f", p.getPrice()))
                        .append("\n");
                sb.append("Description: ").append(p.getDescription()).append("\n");
                sb.append("------------------------------------------------------------\n");
            }
            sb.append("\n");
        }

        sb.append("Shopping Total: $").append(String.format("%.2f", shoppingTotal)).append("\n\n");
        sb.append("COMBINED TOTAL: $").append(String.format("%.2f", combinedTotal)).append("\n");

        return sb.toString();
    }
}