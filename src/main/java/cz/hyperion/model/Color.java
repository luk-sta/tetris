package cz.hyperion.model;

public record Color(byte r, byte g, byte b) {
    public Color() {
        this(random(), random(), random());
    }

    private static byte random() {
        return (byte) (Math.random() * 200 + 20);
    }
}
