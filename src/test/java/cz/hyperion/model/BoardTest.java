package cz.hyperion.model;

import cz.hyperion.view.TetrisView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void testIsElementInside() {
        BoardSize boardSize = new BoardSize(1, 2);
        TetrisView tetrisView = Mockito.mock(TetrisView.class);
        Board board = new Board(boardSize, tetrisView);
        Element element = new Element(new Position(0, Element.SIZE), new Color());
        assertTrue(board.isElementInside(element));

        board.addElement(element);
        assertFalse(board.isElementInside(element));

        Element element2 = new Element(new Position(0, 0), new Color());
        assertTrue(board.isElementInside(element2));

        Element element3 = new Element(new Position(0, 1), new Color());
        assertFalse(board.isElementInside(element3));
    }
}
