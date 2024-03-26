package cz.hyperion;

import cz.hyperion.model.Board;
import cz.hyperion.model.Position;
import cz.hyperion.model.ShapeFactory;
import cz.hyperion.view.KeyListener;
import cz.hyperion.view.TetrisView;

public class Tetris {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        Board board = new Board(16, 28);
        ShapeMove shapeMove = new ShapeMove(board, 40);
        KeyListener keyListener = new KeyListener(shapeMove);
        var tetrisView = new TetrisView(board, keyListener);
        shapeMove.setView(tetrisView);
        while (true) {
            var initialPosition = new Position((board.getWidth()) / 2, 40);
            var shape = ShapeFactory.random(initialPosition);
            if (!board.isShapeInside(shape)) {
                break;
            }
            shapeMove.move(shape);
//            var newShape = shape;
//            do {
//                tetrisView.clear(shape);
//                tetrisView.draw(newShape);
//                shape = newShape;
//                try {
//                    Thread.sleep(40);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                newShape = shape.moveDown();
//            } while (board.isShapeInside(newShape));
//            board.addShape(shape);
        }
    }

}