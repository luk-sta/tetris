package cz.hyperion.view;

import cz.hyperion.model.Element;
import cz.hyperion.model.Figure;
import cz.hyperion.model.Position;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

public class TetrisView implements AutoCloseable {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private final JFrame jFrame;
    private final Graphics2D graphics;

    private final TetrisPanel tetrisPanel;

    public TetrisView(int width, int height) {
        this.tetrisPanel = new TetrisPanel();
        jFrame = new JFrame();
        jFrame.setBackground(Color.WHITE);
        jFrame.setSize(width, height + 5);
        jFrame.setTitle("Tetris");
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setBackground(BACKGROUND_COLOR);
        tetrisPanel.setBackground(BACKGROUND_COLOR);
        jFrame.setVisible(true);
        jFrame.add(tetrisPanel);
        this.graphics = (Graphics2D) tetrisPanel.getGraphics();
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHints(rh);
    }

    public void addKeyListener(KeyListener keyListener) {
        jFrame.addKeyListener(keyListener);
    }

    public void draw(Element element) {
        tetrisPanel.elements.put(element.getId(), element);
    }

    public void clear(Element element) {
        tetrisPanel.elements.remove(element.getId());
    }

    //    private void draw(Element element, Color color) {
    //        if (element == null) {
    //            return;
    //        }
    //        Position position = element.getPosition();
    //        graphics.setColor(color);
    //        graphics.fillRect(position.x(), position.y(), Element.SIZE, Element.SIZE);
    //    }

    public void draw(Figure figure) {
        for (Element element : figure.getElements()) {
            draw(element);
        }
    }

    public void clear(Figure figure) {
        for (Element element : figure.getElements()) {
            clear(element);
        }
    }

    public void repaint() {
        tetrisPanel.repaint();
    }

    public void close() {
        jFrame.dispose();
    }
}
