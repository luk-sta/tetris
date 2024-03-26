package cz.hyperion.model;

public record ElementShift(byte right, byte down) {

    public ElementShift(int right, int down) {
        this((byte) right, (byte) down);
    }

    public static final ElementShift SHIFT_LEFT = new ElementShift(-1, 0);

    public static final ElementShift SHIFT_RIGHT = new ElementShift(1, 0);

    public static final ElementShift SHIFT_DOWN = new ElementShift(0, 1);
}
