package stay_and_shop_system;

import javax.swing.*;
import java.awt.*;

public class RoundJButton extends JButton {
    private Shape shape;
    private int arcAmount = 0;
    public RoundJButton(int arcAmount) {
        super();
        this.arcAmount = arcAmount;
        setOpaque(false);
    }
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arcAmount, arcAmount);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arcAmount, arcAmount);
    }
}
