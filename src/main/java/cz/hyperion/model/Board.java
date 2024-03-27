package cz.hyperion.model;

import cz.hyperion.view.TetrisView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
    private final int elementColumnCount;
    private final int elementRowCount;
    private final TetrisView tetrisView;

    private int points = 0;

    private final Element[][] elements;

    public Board(BoardSize boardSize, TetrisView tetrisView) {
        this.elementColumnCount = boardSize.getElementColumnCount();
        this.elementRowCount = boardSize.getElementRowCount();
        this.tetrisView = tetrisView;
        this.elements = new Element[elementRowCount][elementColumnCount];
    }

    public TetrisView getView() {
        return tetrisView;
    }

    public int getPoints() {
        return points;
    }

    public void addFigure(Figure figure) {
        Set<Integer> rowIndexesSet = new HashSet<>();
        for (Element element : figure.getElements()) {
            int r = addElement(element);
            rowIndexesSet.add(r);
        }
        List<Integer> rowIndexes = new ArrayList<>(rowIndexesSet);
        rowIndexes.sort(Comparator.comparingInt(i -> -1 * i));
        int shift = 0;
        for (Integer rowIndex : rowIndexes) {
            int r = rowIndex + shift;
            //System.out.println("Row index: " + r);
            if (isRowFull(elements[r])) {
                moveRowsDown(r);
                points += 1;
                System.out.println("Points: " + points);
                shift += 1;
            }
        }
        //System.out.println("___");
    }

    int addElement(Element element) {
        int c = getElementColumn(element);
        int r = getElementRow(element);
        elements[r][c] = element;
        return r;
    }

    private boolean isRowFull(Element[] row) {
        for (int c = 0; c < elementColumnCount; c++) {
            if (row[c] == null) {
                return false;
            }
        }
        return true;
    }

    private void moveRowsDown(int fromRow) {
        for (int r = fromRow; r > 0; r--) {
            for (int c = 0; c < elementColumnCount; c++) {
                Element oldElement = elements[r][c];
                tetrisView.clear(oldElement);
                Element e = elements[r - 1][c];
                if (e == null) {
                    elements[r][c] = null;
                    continue;
                }
                elements[r - 1][c] = null;
                tetrisView.clear(e);
                Element newElement = new Element(e, ElementShift.SHIFT_DOWN);
                tetrisView.draw(newElement);
                elements[r][c] = newElement;
            }
        }
        tetrisView.repaint();
    }

    public boolean isFigureInside(Figure figure) {
        return figure.getElements().stream().allMatch(this::isElementInside);
    }

    private int getElementRow(Element element) {
        int minY = Math.max(0, element.position.y());
        int maxY = minY + Element.SIZE;
        return (maxY - 1) / Element.SIZE;
    }

    private int getElementColumn(Element element) {
        int minX = element.position.x();
        int maxX = minX + Element.SIZE;
        return (maxX - 1) / Element.SIZE;
    }

    boolean isElementInside(Element element) {
        int minX = element.position.x();
        if (minX < 0) {
            return false;
        }

        int c = minX / Element.SIZE;
        int r = element.position.y() / Element.SIZE;
        if (!isInside(c, r)) {
            return false;
        }

        c = getElementColumn(element);
        r = getElementRow(element);
        if (!isInside(c, r)) {
            return false;
        }
        return true;
    }

    private boolean isInside(int c, int r) {
        if (c < 0 || c >= elementColumnCount || r < 0 || r >= elementRowCount) {
            return false;
        }
        return elements[r][c] == null;
    }

}
