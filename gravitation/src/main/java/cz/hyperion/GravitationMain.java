package cz.hyperion;

import java.awt.Color;

public class GravitationMain {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("sun.java2d.opengl", "true");

        Body body1 = new Body(0, 0, 0, -0.1, 180, Color.RED);
        Body body2 = new Body(-50, 100, 1, -0.2, 10, Color.BLUE);
        Body body3 = new Body(50, -100, -1, -0.1, 10, Color.GREEN);
        Body body4 = new Body(80, 200, 1, -0.3, 30, Color.CYAN);

        Body body5 = new Body(-100, -400, 5, 0, 30, Color.BLACK);
        //        Body body6 = new Body(100, -400, -5, 0, 10, Color.RED);

        //        Body body5 = new Body(-100, -400, 5, 0, 30, Color.BLACK);
        //        Body body6 = new Body(-150, -400, 7, 0, 10, Color.RED);


        MovementManager movementManager = new MovementManager(
                body1, body2
                , body3
                , body4
                , body5
                //                , body6
        );
        try (MainView gravitationView = new MainView(movementManager)) {
            while (true) {
                Thread.sleep(10);
                movementManager.move();
            }
        }
    }
}