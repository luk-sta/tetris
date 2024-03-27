package cz.hyperion;

import cz.hyperion.exception.FinishException;
import cz.hyperion.exception.RestartException;
import cz.hyperion.model.Board;
import cz.hyperion.model.BoardSize;
import cz.hyperion.model.FigureFactory;
import cz.hyperion.model.Position;
import cz.hyperion.view.KeyListener;
import cz.hyperion.view.TetrisView;

public class TetrisGame {
    private final BoardSize boardSize;

    public TetrisGame() {
        boardSize = new BoardSize(16, 28);
    }

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("sun.java2d.opengl", "true");
        TetrisGame tetrisGame = new TetrisGame();
        while (true) {
            try {
                tetrisGame.run();
            } catch (FinishException e) {
                System.out.println("Game over.");
                return;
            } catch (RestartException e) {
                System.out.println("New game.");
            }
        }
    }

    private void run() throws InterruptedException {
        try (var tetrisView = new TetrisView(boardSize.getWidth(), boardSize.getHeight())) {
            Board board = new Board(boardSize, tetrisView);
            FigureMovement figureMovement = new FigureMovement(board, 20);
            KeyListener keyListener = new KeyListener(figureMovement);
            tetrisView.addKeyListener(keyListener);
            while (true) {
                var initialPosition = new Position((boardSize.getWidth()) / 2, 40);
                var movingFigure = FigureFactory.random(initialPosition);
                if (!board.isFigureInside(movingFigure)) {
                    break;
                }
                figureMovement.perform(movingFigure);
            }
            System.out.println("Points: " + board.getPoints());
        }
    }
}