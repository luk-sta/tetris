package cz.hyperion.model;

import java.util.Collection;
import java.util.List;

public final class FigureFactory {

    private static final Collection<ElementShift> ISHAPE =
            List.of(new ElementShift(0, -2), new ElementShift(0, -1), new ElementShift(0, 1));
    private static final Collection<ElementShift> OSHAPE =
            List.of(new ElementShift(1, 0), new ElementShift(0, 1), new ElementShift(1, 1));
    private static final Collection<ElementShift> LSHAPE =
            List.of(new ElementShift(0, -2), new ElementShift(0, -1), new ElementShift(1, 0));
    private static final Collection<ElementShift> JSHAPE =
            List.of(new ElementShift(0, -2), new ElementShift(0, -1), new ElementShift(-1, 0));
    private static final Collection<ElementShift> ZSHAPE =
            List.of(new ElementShift(-1, 0), new ElementShift(0, 1), new ElementShift(1, 1));
    private static final Collection<ElementShift> SSHAPE =
            List.of(new ElementShift(1, 0), new ElementShift(0, 1), new ElementShift(-1, 1));
    private static final Collection<ElementShift> ESHAPE =
            List.of(new ElementShift(0, -1), new ElementShift(1, 0), new ElementShift(0, 1));
    //    private static final Collection<ElementShift> PSHAPE =
    //            List.of(new ElementShift(0, -1), new ElementShift(1, 0), new ElementShift(0, 1), new ElementShift
    //            (-1, 0));
    private static final Collection<ElementShift> CSHAPE =
            List.of(new ElementShift(0, -1), new ElementShift(1, -1), new ElementShift(0, 1), new ElementShift(1, 1));


    private static final List<Collection<ElementShift>> SHAPE_SHIFTS = List.of(
            ISHAPE, OSHAPE, LSHAPE, JSHAPE, ZSHAPE, SSHAPE, ESHAPE,
            //PSHAPE,
            CSHAPE
    );

    public static Figure random(Position position) {
        Color color = new Color();
        int index = (int) (Math.random() * SHAPE_SHIFTS.size());
        Collection<ElementShift> elementShifts = SHAPE_SHIFTS.get(index);
        Figure f = new Figure(new Element(position, color), elementShifts);

        int rotations = (int) (Math.random() * 4);
        for (int i = 0; i < rotations; i++) {
            f = f.rotateRight();
        }
        return f;
    }

}
