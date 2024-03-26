package cz.hyperion;

import cz.hyperion.model.Board;
import cz.hyperion.model.Shape;
import cz.hyperion.view.TetrisView;

import java.util.concurrent.atomic.AtomicInteger;

public class ShapeMove implements KeyStrokes {
    private final Board board;
    private final int slowness;
    private final AtomicInteger sleep;

    private volatile Shape shape;
    private TetrisView tetrisView;

    public ShapeMove(Board board, int slowness) {
        this.board = board;
        this.slowness = slowness;
        sleep = new AtomicInteger(slowness);
    }

    public void setView(TetrisView tetrisView) {
        this.tetrisView = tetrisView;
    }

    @Override
    public void keyLeft() {
        newShape(shape.moveLeft());
    }

    @Override
    public void keyRight() {
        newShape(shape.moveRight());
    }

    @Override
    public void keyDown() {
        newShape(shape.rotateRight());
    }

    @Override
    public void keyUp() {
        newShape(shape.rotateLeft());
    }

    @Override
    public void keySpaceOn() {
        sleep.set(5);
    }

    @Override
    public void keySpaceOff() {
        sleep.set(slowness);
    }

    public void move(Shape s) {
        this.shape = s;
        do {
            try {
                Thread.sleep(sleep.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (newShape(shape.moveDown()));

        board.addShape(shape);
    }

    private boolean newShape(Shape newShape) {
        if (!board.isShapeInside(newShape)) {
            return false;
        }
        tetrisView.clear(shape);
        shape = newShape;
        tetrisView.draw(shape);
        return true;
    }
}
