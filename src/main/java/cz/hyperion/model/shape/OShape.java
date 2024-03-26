package cz.hyperion.model.shape;

import cz.hyperion.model.Element;
import cz.hyperion.model.Shape;

import java.util.List;

public class OShape extends Shape {
    public OShape(List<Element> elements) {
        super(elements);
    }

    public OShape(Element... elements) {
        super(elements);
    }

    @Override
    public Shape rotateLeft() {
        return this;
    }

    @Override
    public Shape rotateRight() {
        return this;
    }
}
