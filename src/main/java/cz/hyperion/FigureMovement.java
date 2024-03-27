package cz.hyperion;

import cz.hyperion.model.Board;
import cz.hyperion.model.Figure;
import cz.hyperion.view.TetrisView;

import java.util.function.Function;

class FigureMovement {
    private final Board board;
    private final GameContext gameContext;
    private final TetrisView tetrisView;

    private volatile Figure figure;

    FigureMovement(Board board, GameContext gameContext) {
        this.board = board;
        this.gameContext = gameContext;
        this.tetrisView = board.getView();
    }

    void perform(Figure s) throws InterruptedException {
        this.figure = s;
        do {
            gameContext.checkGameState();
            Thread.sleep(gameContext.getSleep());
        } while (move());

        board.addFigure(figure);
        gameContext.refreshSleep();
    }

    private boolean move() {
        Function<Figure, Figure> action = gameContext.getRequestedAction();
        if (action != null) {
            if (tryNewPlacement(action.apply(figure))) {
                gameContext.clearRequestedAction();
            }
        }
        return tryNewPlacement(figure.moveDown());
    }

    private boolean tryNewPlacement(Figure newFigure) {
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
