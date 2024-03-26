package cz.hyperion.model;

import cz.hyperion.model.shape.IShape;
import cz.hyperion.model.shape.OShape;
import cz.hyperion.model.shape.UShape;

public class ShapeFactory {
    public static IShape iShape(Position position, Color color){
        return new IShape(new Element(position, color),
                new Element(position, new ElementShift(0, 1), color),
                new Element(position, new ElementShift(0, 2), color),
                new Element(position, new ElementShift(0, 3), color)
                );
    }

    public static UShape uShape(Position position, Color color){
        return new UShape(new Element(position, color),
                new Element(position, new ElementShift(1, 0), color),
                new Element(position, new ElementShift(2, 0), color),
                new Element(position, new ElementShift(3, 0), color)
        );
    }

    public static OShape oShape(Position position, Color color){
        return new OShape(new Element(position, color),
                new Element(position, new ElementShift(1, 0), color),
                new Element(position, new ElementShift(1, 1), color),
                new Element(position, new ElementShift(0, 1), color)
        );
    }
}
