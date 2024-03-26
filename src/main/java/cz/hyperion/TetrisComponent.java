package cz.hyperion;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class TetrisComponent extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        Rectangle2D.Double r = new Rectangle2D.Double(50,75, 100,250);
        g2d.setColor(new Color(100, 149, 237));
        g2d.fill(r);

        Ellipse2D.Double e = new Ellipse2D.Double(200, 75, 100, 100);
        g2d.setColor(Color.BLUE);
        g2d.fill(e);

        Line2D.Double l = new Line2D.Double(100, 250, 300, 75);
        g2d.setColor(Color.BLACK);
        g2d.draw(l);
//        g2d.draw(r);
    }
}
