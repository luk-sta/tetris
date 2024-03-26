package cz.hyperion.view;

import cz.hyperion.model.Element;
import cz.hyperion.model.Figure;
import cz.hyperion.model.Position;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;

public class TetrisView {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private final JFrame jFrame;
    private final Graphics2D graphics;

    public TetrisView(int width, int height) {
        jFrame = new JFrame();
        jFrame.setBackground(Color.WHITE);
        jFrame.setSize(width, height);
        jFrame.setTitle("Tetris");
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setBackground(BACKGROUND_COLOR);
        jFrame.setVisible(true);
        this.graphics = (Graphics2D) jFrame.getGraphics();
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, width, height);
    }

    public void addKeyListener(KeyListener keyListener) {
        jFrame.addKeyListener(keyListener);
    }

    //    public int getWidth() {
    //        return width;
    //    }
    //
    //    public int getHeight() {
    //        return height;
    //    }
    //
    //    public KeyListener getKeyListener() {
    //        return keyListener;
    //    }

    public void draw(Element element) {
        var c = element.getColor();
        var color = new Color(c.r(), c.g(), c.b());
        draw(element, color);
    }

    public void clear(Element element) {
        draw(element, BACKGROUND_COLOR);
    }

    private void draw(Element element, Color color) {
        if (element == null) {
            return;
        }
        Position position = element.getPosition();
        graphics.setColor(color);
        graphics.fillRect(position.x(), position.y(), Element.SIZE, Element.SIZE);
    }

    public void draw(Figure shape) {
        for (Element element : shape.getElements()) {
            draw(element);
        }
    }

    public void clear(Figure shape) {
        for (Element element : shape.getElements()) {
            clear(element);
        }
    }
}
