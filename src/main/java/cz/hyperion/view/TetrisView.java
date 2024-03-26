package cz.hyperion.view;

import cz.hyperion.model.Board;
import cz.hyperion.model.Element;
import cz.hyperion.model.Position;
import cz.hyperion.model.Shape;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;

public class TetrisView {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private final JFrame jFrame;
    private final Graphics2D graphics;

    public TetrisView(Board board, KeyListener keyListener) {
//        var tetrisCanvas = new TetrisViewComponent();
//        tetrisCanvas.setBackground(Color.WHITE);
        jFrame = new JFrame();
        jFrame.setBackground(Color.WHITE);
        //        jFrame.add(tetrisCanvas);
        jFrame.setSize(board.getWidth(), board.getHeight());
        jFrame.setTitle("Tetris");
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setBackground(BACKGROUND_COLOR);
        jFrame.addKeyListener(keyListener);
        //jFrame.setLayout(null);
        jFrame.setVisible(true);
        this.graphics = (Graphics2D) jFrame.getGraphics();
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, board.getWidth(), board.getHeight());
    }

    public void draw(Element element) {
        var c = element.getColor();
        var color = new Color(c.r(), c.g(), c.b());
        draw(element, color);
    }

    public void clear(Element element) {
        draw(element, BACKGROUND_COLOR);
    }

    private void draw(Element element, Color color) {
        Position position = element.getPosition();
        graphics.setColor(color);
        graphics.fillRect(position.x(), position.y(), Element.SIZE, Element.SIZE);
    }

    public void draw(Shape shape) {
        for (Element element : shape.getElements()) {
            draw(element);
        }
    }

    public void clear(Shape shape) {
        for (Element element : shape.getElements()) {
            clear(element);
        }
    }
}
