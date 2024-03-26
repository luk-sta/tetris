package cz.hyperion;

import cz.hyperion.model.Board;
import cz.hyperion.model.Position;
import cz.hyperion.model.ShapeFactory;
import cz.hyperion.view.TetrisView;

public class Tetris {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        Board board = new Board(16, 28);
        var tetrisView = new TetrisView(board);
        while (true) {
            var initialPosition = new Position((board.getWidth()) / 2, 40);
            var shape = ShapeFactory.random(initialPosition);
            if (!board.isShapeInside(shape)) {
                break;
            }
            var newShape = shape;
            do {
                tetrisView.clear(shape);
                tetrisView.draw(newShape);
                shape = newShape;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                newShape = shape.moveDown();
            } while (board.isShapeInside(newShape));
            board.addShape(shape);
        }
    }

}