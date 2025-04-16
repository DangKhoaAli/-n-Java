package gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTextFieldUI;

public class RoundedTxtField extends JTextField {
    private int radius;

    public RoundedTxtField(int columns, int radius) {
        super(columns);
        this.radius = radius;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setUI(new BasicTextFieldUI());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // fill background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        g2.dispose();
    }
}
