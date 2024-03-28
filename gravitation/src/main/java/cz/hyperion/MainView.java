package cz.hyperion;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

class MainView implements AutoCloseable {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    static final int WIDTH = MovementManager.SIZE * 2;
    static final int HEIGHT = MovementManager.SIZE * 2;
    private final JFrame jFrame;
    private final Timer tm;

    MainView(MovementManager movementManager) {
        jFrame = new JFrame();
        jFrame.setBackground(Color.WHITE);
        jFrame.setSize(WIDTH + 10, HEIGHT + 40);
        jFrame.setTitle("Gravitation");
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setBackground(BACKGROUND_COLOR);
        UniversePanel universeView = new UniversePanel(movementManager, WIDTH / 2, HEIGHT / 2);
        universeView.setSize(WIDTH, HEIGHT);
        universeView.setBackground(BACKGROUND_COLOR);
        jFrame.setVisible(true);
        jFrame.add(universeView);
        Graphics2D graphics = (Graphics2D) universeView.getGraphics();
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHints(rh);
        tm = new Timer(5, universeView);
        tm.start();
    }

    public void close() {
        tm.stop();
        jFrame.dispose();
    }
}
