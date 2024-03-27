package cz.hyperion;

import cz.hyperion.exception.FinishException;
import cz.hyperion.exception.RestartException;
import cz.hyperion.model.Board;
import cz.hyperion.model.Figure;
import cz.hyperion.view.TetrisView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FigureMovement implements KeyStrokes {
    private final Board board;
    private final int baseSlowness;
    private final TetrisView tetrisView;
    private final AtomicInteger sleep;

    private volatile Figure figure;

    private final AtomicInteger running = new AtomicInteger(0);

    private final AtomicBoolean pause = new AtomicBoolean(false);

    public FigureMovement(Board board, int slowness) {
        this.board = board;
        this.baseSlowness = slowness;
        this.tetrisView = board.getView();
        sleep = new AtomicInteger(slowness);
    }

    @Override
    public void keyLeft() {
        newFigure(figure.moveLeft());
    }

    @Override
    public void keyRight() {
        newFigure(figure.moveRight());
    }

    @Override
    public void keyDown() {
        newFigure(figure.rotateRight());
    }

    @Override
    public void keyUp() {
        newFigure(figure.rotateLeft());
    }

    @Override
    public void keySpaceOn() {
        sleep.set(3);
    }

    @Override
    public void keySpaceOff() {
        sleep.set(baseSlowness - board.getPoints());
    }

    @Override
    public void keyQuit() {
        running.set(1);
    }

    @Override
    public void keyPauseResume() {
        pause.set(!pause.get());
    }

    @Override
    public void keyNew() {
        running.set(2);
    }

    public void perform(Figure s) throws InterruptedException {
        this.figure = s;
        do {
            checkGameState();
            Thread.sleep(sleep.get());
        } while (newFigure(figure.moveDown()));

        board.addFigure(figure);
        sleep.set(baseSlowness - board.getPoints());
    }

    private void checkGameState() throws InterruptedException {
        switch (running.get()) {
            case 1:
                throw new FinishException();
            case 2:
                throw new RestartException();
        }
        while (pause.get()) {
            Thread.sleep(100);
        }
    }

    private synchronized boolean newFigure(Figure newFigure) {
        if (!board.isFigureInside(newFigure)) {
            return false;
        }
        tetrisView.clear(figure);
        figure = newFigure;
        tetrisView.draw(figure);
        tetrisView.repaint();
        return true;
    }
}
