package cz.hyperion.model;

public class BoardSize {
    private final int elementColumnCount;
    private final int elementRowCount;
    private final int width;
    private final int height;

    public BoardSize(int elementColumnCount, int elementRowCount) {
        this.elementColumnCount = elementColumnCount;
        this.elementRowCount = elementRowCount;
        this.width = elementColumnCount * Element.SIZE;
        this.height = elementRowCount * Element.SIZE;
    }

    public int getElementColumnCount() {
        return elementColumnCount;
    }

    public int getElementRowCount() {
        return elementRowCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
