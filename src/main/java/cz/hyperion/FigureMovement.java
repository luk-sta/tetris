package cz.hyperion;

import cz.hyperion.model.Board;
import cz.hyperion.model.Figure;
import cz.hyperion.model.FigureFactory;
import cz.hyperion.model.Position;
import cz.hyperion.view.TetrisView;

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

    boolean perform(Position initialPosition) throws InterruptedException {
        this.figure = FigureFactory.random(initialPosition);
        if (!board.isFigureInside(figure)) {
            return false;
        }
        do {
            gameContext.checkGameState();
            Thread.sleep(gameContext.getSleep());
        } while (move());

        board.addFigure(figure);
        gameContext.refreshSleep();
        SoundUtil.playBottomTap();
        return true;
    }

    private boolean move() {
        FigureAction action = gameContext.getRequestedAction();
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
