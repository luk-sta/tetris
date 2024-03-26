package cz.hyperion.model;

public record Color(short r, short g, short b) {
    public Color() {
        this(random(), random(), random());
    }

    private static short random() {
        return (short) (Math.random() * 200 + 20);
    }
}
