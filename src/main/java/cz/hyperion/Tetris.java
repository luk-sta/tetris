package cz.hyperion;

import cz.hyperion.model.Board;
import cz.hyperion.model.BoardSize;
import cz.hyperion.model.FigureFactory;
import cz.hyperion.model.Position;
import cz.hyperion.view.KeyListener;
import cz.hyperion.view.TetrisView;

public class Tetris {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        BoardSize boardSize = new BoardSize(16, 28);
        var tetrisView = new TetrisView(boardSize.getWidth(), boardSize.getHeight());
        Board board = new Board(boardSize, tetrisView);
        FigureMovement figureMovement = new FigureMovement(board, 40);
        KeyListener keyListener = new KeyListener(figureMovement);
        tetrisView.addKeyListener(keyListener);
        while (true) {
            var initialPosition = new Position((boardSize.getWidth()) / 2, 40);
            var movingFigure = FigureFactory.random(initialPosition);
            if (!board.isShapeInside(movingFigure)) {
                break;
            }
            figureMovement.perform(movingFigure);
        }
    }

}