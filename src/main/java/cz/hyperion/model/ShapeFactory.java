package cz.hyperion.model;

public final class ShapeFactory {
    public static Shape iShape(Position position, Color color) {
        return new Shape(new Element(position, color),
                new ElementShift(0, -2),
                new ElementShift(0, -1),
                new ElementShift(0, 1)
        );
    }

    public static Shape oShape(Position position, Color color) {
        return new Shape(new Element(position, color),
                new ElementShift(1, 0),
                new ElementShift(0, 1),
                new ElementShift(1, 1)
        );
    }

    public static Shape lShape(Position position, Color color) {
        return new Shape(new Element(position, color),
                new ElementShift(0, -2),
                new ElementShift(0, -1),
                new ElementShift(1, 0)
        );
    }

    public static Shape jShape(Position position, Color color) {
        return new Shape(new Element(position, color),
                new ElementShift(0, -2),
                new ElementShift(0, -1),
                new ElementShift(-1, 0)
        );
    }

    public static Shape zShape(Position position, Color color) {
        return new Shape(new Element(position, color),
                new ElementShift(-1, 0),
                new ElementShift(0, 1),
                new ElementShift(1, 1)
        );
    }

    public static Shape sShape(Position position, Color color) {
        return new Shape(new Element(position, color),
                new ElementShift(1, 0),
                new ElementShift(0, 1),
                new ElementShift(-1, 1)
        );
    }

    public static Shape eShape(Position position, Color color) {
        return new Shape(new Element(position, color),
                new ElementShift(0, -1),
                new ElementShift(1, 0),
                new ElementShift(0, 1)
        );
    }

    public static Shape random(Position position) {
        Color color = new Color();
        Shape s = switch ((int) (Math.random() * 7)) {
            case 0 -> iShape(position, color);
            case 1 -> oShape(position, color);
            case 2 -> lShape(position, color);
            case 3 -> jShape(position, color);
            case 4 -> zShape(position, color);
            case 5 -> sShape(position, color);
            case 6 -> eShape(position, color);
            default -> throw new IllegalStateException("Unexpected value: " + (int) (Math.random() * 7));
        };
        int rotations = (int) (Math.random() * 4);
        for (int i = 0; i < rotations; i++) {
            s = s.rotateRight();
        }
        return s;
    }

}
