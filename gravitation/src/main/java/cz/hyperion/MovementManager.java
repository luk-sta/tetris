package cz.hyperion;

import static cz.hyperion.Body.BODY_SIZE;

final class MovementManager {
    public static final int SIZE = 500;
    private static final double G_CONST = 1;

    private final Body[] bodies;

    MovementManager(Body... bodies) {
        this.bodies = bodies;
    }

    public Body[] getBodies() {
        return bodies;
    }

    void move() {
        for (int i = 0; i < bodies.length; i++) {
            var body1 = bodies[i];
            for (int j = i + 1; j < bodies.length; j++) {
                var body2 = bodies[j];
                updateVelocity(body1, body2);
            }
        }

        for (Body body : bodies) {
            updatePosition(body);
        }
    }

    private void updateVelocity(Body body1, Body body2) {
        double dx = body1.position.x - body2.position.x;
        double dy = body1.position.y - body2.position.y;
        double distanceSq = dx * dx + dy * dy;
        double distance = Math.sqrt(distanceSq);
        if (distance <= BODY_SIZE) {
            swapMomentums(body1, body2);
            return;
        }

        final double baseForce = G_CONST / distanceSq;
        final double normDx = dx / distance;
        final double normDy = dy / distance;

        double force1 = -1 * baseForce * body2.mass;
        double accelerationX1 = force1 * normDx;
        double accelerationY1 = force1 * normDy;

        body1.velocity.x += accelerationX1;
        body1.velocity.y += accelerationY1;

        double force2 = baseForce * body1.mass;
        double accelerationX2 = force2 * normDx;
        double accelerationY2 = force2 * normDy;

        body2.velocity.x += accelerationX2;
        body2.velocity.y += accelerationY2;
    }

    private void swapMomentums(Body body1, Body body2) {
        Vector momentum1 = body1.getMomentum();
        Vector momentum2 = body2.getMomentum();
        body1.velocity.x = momentum2.x / body1.mass;
        body1.velocity.y = momentum2.y / body1.mass;
        body2.velocity.x = momentum1.x / body2.mass;
        body2.velocity.y = momentum1.y / body2.mass;
    }

    private void updatePosition(Body body) {
        body.position.x += body.velocity.x;
        if (body.position.x <= -1 * SIZE) {
            body.position.x = -1 * SIZE;
            body.velocity.x *= -1;
        }
        if (body.position.x >= SIZE) {
            body.position.x = SIZE;
            body.velocity.x *= -1;
        }
        body.position.y += body.velocity.y;
        if (body.position.y <= -1 * SIZE) {
            body.position.y = -1 * SIZE;
            body.velocity.y *= -1;
        }
        if (body.position.y >= SIZE) {
            body.position.y = SIZE;
            body.velocity.y *= -1;
        }
    }
}
