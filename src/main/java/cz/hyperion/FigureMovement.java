package cz.hyperion;

import cz.hyperion.model.Board;
import cz.hyperion.model.Figure;
import cz.hyperion.view.TetrisView;

import java.util.concurrent.atomic.AtomicInteger;

public class FigureMovement implements KeyStrokes {
    private final Board board;
    private final int slowness;
    private final TetrisView tetrisView;
    private final AtomicInteger sleep;

    private volatile Figure figure;

    public FigureMovement(Board board, int slowness) {
        this.board = board;
        this.slowness = slowness;
        this.tetrisView = board.getView();
        sleep = new AtomicInteger(slowness);
    }

    @Override
    public void keyLeft() {
        newShape(figure.moveLeft());
    }

    @Override
    public void keyRight() {
        newShape(figure.moveRight());
    }

    @Override
    public void keyDown() {
        newShape(figure.rotateRight());
    }

    @Override
    public void keyUp() {
        newShape(figure.rotateLeft());
    }

    @Override
    public void keySpaceOn() {
        sleep.set(5);
    }

    @Override
    public void keySpaceOff() {
        sleep.set(slowness);
    }

    public void perform(Figure s) {
        this.figure = s;
        do {
            try {
                Thread.sleep(sleep.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (newShape(figure.moveDown()));

        board.addShape(figure);
    }

    private boolean newShape(Figure newShape) {
        if (!board.isShapeInside(newShape)) {
            return false;
        }
        tetrisView.clear(figure);
        figure = newShape;
        tetrisView.draw(figure);
        return true;
    }
}
