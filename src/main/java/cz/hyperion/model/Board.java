package cz.hyperion.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private final int elementColumnCount;
    private final int elementRowCount;
    private final int width;
    private final int height;

    private int points = 0;

    private final Element[][] elements;

    public Board(int elementColumnCount, int elementRowCount) {
        this.elementColumnCount = elementColumnCount;
        this.elementRowCount = elementRowCount;
        this.elements = new Element[elementRowCount][elementColumnCount];
        this.width = elementColumnCount * Element.SIZE;
        this.height = elementRowCount * Element.SIZE;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addShape(Shape shape) {
        List<Integer> rowIndexes = new LinkedList<>();
        for (Element element : shape.getElements()) {
            int c = element.position.x() / Element.SIZE;
            int r = element.position.y() / Element.SIZE;
            elements[r][c] = element;
            rowIndexes.add(r);
        }
        rowIndexes.sort(Comparator.comparingInt(i -> -1 * i));
        int shift = 0;
        for (Integer rowIndex : rowIndexes) {
            int r = rowIndex + shift;
            if (isRowFull(elements[r])) {
                moveRowsDown(r);
                points += 1;
                shift += 1;
            }
        }
    }

    private boolean isRowFull(Element[] row) {
        for (int r = 0; r < elementRowCount; r++) {
            if (row[r] == null) {
                return false;
            }
        }
        return true;
    }

    private void moveRowsDown(int fromRow) {
        for (int r = fromRow; r > 0; r--) {
            for (int c = 0; c < elementColumnCount; c++) {
                Element e = elements[r - 1][c];
                if (e == null) {
                    Element oldElement = elements[r][c];
                    // TODO clear oldElement in view
                    elements[r][c] = null;
                    continue;
                }
                // TODO clear e in view
                Element newElement = new Element(e, ElementShift.SHIFT_DOWN);
                // TODO display newElement in view
                elements[r][c] = newElement;
            }
        }
    }

    public boolean isShapeInside(Shape shape) {
        return shape.getElements().stream().allMatch(this::isElementInside);
    }

    private boolean isElementInside(Element element) {
        int minX = element.position.x();
        int minY = Math.max(0, element.position.y());
        int maxX = minX + Element.SIZE;
        int maxY = minY + Element.SIZE;
        if (minX < 0 || maxX >= width || maxY >= height) {
            return false;
        }

        int col = minX / Element.SIZE;
        int row = minY / Element.SIZE;

        return elements[row][col] == null;
    }
}
