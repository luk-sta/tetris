package cz.hyperion.model.shape;

import cz.hyperion.model.Element;
import cz.hyperion.model.ElementShift;
import cz.hyperion.model.Shape;

import java.util.List;

public class UShape extends Shape {
    public UShape(List<Element> elements) {
        super(elements);
    }

    public UShape(Element... elements) {
        super(elements);
    }

    @Override
    public IShape rotateLeft() {
        return new IShape(
                transform(new ElementShift(1, 1),
                        new ElementShift(0, 0),
                        new ElementShift(-1, -1),
                        new ElementShift(-2, -2)
                )
        );
    }

    @Override
    public IShape rotateRight() {
        return new IShape(
                transform(new ElementShift(2, -2),
                        new ElementShift(1, -1),
                        new ElementShift(0, 0),
                        new ElementShift(-1, 1)
                )
        );
    }
}
