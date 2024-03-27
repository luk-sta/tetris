package cz.hyperion;

import cz.hyperion.exception.FinishException;
import cz.hyperion.exception.RestartException;
import cz.hyperion.model.Board;
import cz.hyperion.model.BoardSize;
import cz.hyperion.model.Element;
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
            GameContext gameContext = new GameContext(board, 20);
            FigureMovement figureMovement = new FigureMovement(board, gameContext);
            KeyListener keyListener = new KeyListener(gameContext);
            tetrisView.addKeyListener(keyListener);
            mainLoop(figureMovement);
            System.out.println("Points: " + board.getPoints());
        }
        Thread.sleep(3000);
    }

    private void mainLoop(FigureMovement figureMovement) throws InterruptedException {
        long start = System.currentTimeMillis();
        while (figureMovement.perform(getInitialPosition())) {
            System.out.println("Time sec: " + (System.currentTimeMillis() - start) / 1000);
        }
        System.out.println("Time sec: " + (System.currentTimeMillis() - start) / 1000);
    }

    private Position getInitialPosition() {
        int x = (boardSize.getWidth()) / 2 + (int) ((Math.random() - 0.5d) * 6) * Element.SIZE;
        //System.out.println(x);
        return new Position(x, 40);
    }
}