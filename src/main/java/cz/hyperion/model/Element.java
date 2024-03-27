package cz.hyperion.model;

import java.util.UUID;

public class Element {
    public static final int SIZE = 20;
    private final String id;
    final Position position;
    final Color color;

    public Element(Position position, Color color) {
        this.id= UUID.randomUUID().toString();
        this.position = position;
        this.color = color;
    }

    public Element(Position position, ElementShift shift, Color color) {
        this(new Position(position.x() + SIZE * shift.right(), position.y() + SIZE * shift.down()), color);
    }

    public Element(Element element, ElementShift shift) {
        this(element.position, shift, element.color);
    }

    public Element moveDown() {
        return new Element(new Position(this.position.x(), this.position.y() + 4), this.color);
    }

    public Element moveLeft() {
        return new Element(this, ElementShift.SHIFT_LEFT);
    }

    public Element moveRight() {
        return new Element(this, ElementShift.SHIFT_RIGHT);
    }

    public String getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }
}
