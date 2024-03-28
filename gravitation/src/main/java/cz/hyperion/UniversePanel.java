package cz.hyperion;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static cz.hyperion.Body.BODY_CIRCLE_RADIUS;
import static cz.hyperion.Body.BODY_SIZE;

class UniversePanel extends JPanel implements ActionListener {

    private final MovementManager movement;
    private final int centerX;
    private final int centerY;


    UniversePanel(MovementManager movement, int centerX, int centerY) {
        this.movement = movement;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Body body : movement.getBodies()) {
            draw(g, body);
        }
    }

    private void draw(Graphics g, Body body) {
        g.setColor(body.color);
        g.fillOval(centerX + (int) body.position.x - BODY_CIRCLE_RADIUS,
                centerY + (int) body.position.y - BODY_CIRCLE_RADIUS, BODY_SIZE,
                BODY_SIZE);
        //System.out.println(body.position.x + " " + body.position.y);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
