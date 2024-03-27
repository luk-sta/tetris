package cz.hyperion.view;

import cz.hyperion.model.Element;
import cz.hyperion.model.Position;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

class TetrisPanel extends JPanel {
    final Map<String, Element> elements = new HashMap<>();
    @Override
    public void paintComponent(Graphics g) {
                super.paintComponent(g);
        elements.values().forEach(element -> drawElement(g, element));
    }

    private void drawElement(Graphics g, Element element) {
        Position position = element.getPosition();
        var c = element.getColor();
        var color = new Color(c.r(), c.g(), c.b()).darker();
        g.setColor(color);
        g.fillRect(position.x(), position.y(), Element.SIZE, Element.SIZE);

    }
}
