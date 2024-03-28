package cz.hyperion;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

class Body {
    public static final int BODY_SIZE = 16;
    public static final int BODY_CIRCLE_RADIUS = BODY_SIZE / 2;
    volatile Vector position;
    Vector velocity;
    public final double mass;
    public final Color color;

    public final Set<Body> swappedMomentumWith = new HashSet<>();

    public Body(double x, double y, double vx, double vy, double mass, Color color) {
        this.position = new Vector(x, y);
        this.velocity = new Vector(vx, vy);
        this.mass = mass;
        this.color = color;
    }

    Vector getMomentum() {
        return new Vector(velocity.x * mass, velocity.y * mass);
    }
}
