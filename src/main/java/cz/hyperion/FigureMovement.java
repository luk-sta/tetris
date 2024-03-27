package cz.hyperion;

import cz.hyperion.exception.FinishException;
import cz.hyperion.exception.RestartException;
import cz.hyperion.model.Board;
import cz.hyperion.model.Figure;
import cz.hyperion.view.TetrisView;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FigureMovement implements KeyStrokes {
    private final Board board;
    private final int baseSlowness;
    private final TetrisView tetrisView;
    private final AtomicInteger sleep;

    private volatile Figure figure;

    private final AtomicReference<State> state = new AtomicReference<>(State.RUNNING);

    //    private final AtomicBoolean pause = new AtomicBoolean(false);

    private enum State {
        RUNNING,
        PAUSED,
        FINISHED,
        RESTART
    }

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
        sleep.set(1);
    }

    @Override
    public void keySpaceOff() {
        sleep.set(baseSlowness - board.getPoints());
    }

    @Override
    public void keyQuit() {
        state.set(State.FINISHED);
    }

    @Override
    public void keyPauseResume() {
        switch (state.get()) {
            case PAUSED -> state.set(State.RUNNING);
            case RUNNING -> state.set(State.PAUSED);
        }
    }

    @Override
    public void keyNew() {
        state.set(State.RESTART);
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
        boolean wait = true;
        while (wait) {
            switch (state.get()) {
                case RUNNING -> wait = false;
                case PAUSED -> Thread.sleep(100);
                case RESTART -> throw new RestartException();
                case FINISHED -> throw new FinishException();
            }
        }
    }

    private boolean newFigure(Figure newFigure) {
        if (!board.isFigureInside(newFigure)) {
//            System.out.println("New figure not inside.");
            return false;
        }
        tetrisView.clear(figure);
        figure = newFigure;
        tetrisView.draw(figure);
        tetrisView.repaint();
        return true;
    }
}
