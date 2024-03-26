package cz.hyperion.model.shape;

import cz.hyperion.model.Element;
import cz.hyperion.model.ElementShift;
import cz.hyperion.model.Shape;

import java.util.List;

public class IShape extends Shape {
    public IShape(List<Element> elements) {
        super(elements);
    }

    public IShape(Element... elements) {
        super(elements);
    }

    @Override
    public UShape rotateLeft() {
        return new UShape(
                transform(new ElementShift(-2, 2),
                        new ElementShift(-1, 1),
                        new ElementShift(0, 0),
                        new ElementShift(1, -1)
                )
        );
    }

    @Override
    public UShape rotateRight() {
        return new UShape(
                transform(new ElementShift(2, 2),
                        new ElementShift(1, 1),
                        new ElementShift(0, 0),
                        new ElementShift(-1, -1)
                )
        );
    }
}
