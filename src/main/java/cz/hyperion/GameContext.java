package cz.hyperion;

import cz.hyperion.exception.FinishException;
import cz.hyperion.exception.RestartException;
import cz.hyperion.model.Board;
import cz.hyperion.model.Figure;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

class GameContext implements KeyStrokes {

    private final Board board;
    private final AtomicInteger sleep;
    private final AtomicReference<State> state = new AtomicReference<>(State.RUNNING);
    private final AtomicReference<FigureAction> lastRequestedAction = new AtomicReference<>();
    private final int baseSlowness;

    GameContext(Board board, int slowness) {
        this.board = board;
        baseSlowness = slowness;
        sleep = new AtomicInteger(slowness);
    }

    private enum State {
        RUNNING,
        PAUSED,
        FINISHED,
        RESTART
    }

    @Override
    public void keyLeft() {
        setAction(Figure::moveLeft);
    }

    @Override
    public void keyRight() {
        setAction(Figure::moveRight);
    }

    @Override
    public void keyDown() {
        setAction(Figure::rotateRight);
    }

    @Override
    public void keyUp() {
        setAction(Figure::rotateLeft);
    }

    private void setAction(Function<Figure, Figure> actionFunction) {
        lastRequestedAction.set(new FigureAction(actionFunction));
    }

    @Override
    public void keySpaceOn() {
        sleep.set(1);
    }

    @Override
    public void keySpaceOff() {
        refreshSleep();
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

    void checkGameState() throws InterruptedException {
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

    void refreshSleep() {
        int s = Math.max(4, baseSlowness - board.getPoints() / 3);
        sleep.set(s);
    }

    int getSleep() {
        return sleep.get();
    }

    FigureAction getRequestedAction() {
        FigureAction figureAction = lastRequestedAction.get();
        if (figureAction == null) {
            return null;
        }
        if (figureAction.isStale()) {
            lastRequestedAction.set(null);
            return null;
        }
        return figureAction;
    }

    void clearRequestedAction() {
        lastRequestedAction.set(null);
    }
}
