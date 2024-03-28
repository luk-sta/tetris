package cz.hyperion;

import java.util.Optional;
import java.util.function.Function;

import static cz.hyperion.Body.BODY_SIZE;

final class MovementManager {
    public static final int SIZE = 500;
    private static final double G_CONST = 1;

    private final Body[] bodies;

    private int coef = 1;
    private int iter = 0;

    MovementManager(Body... bodies) {
        this.bodies = bodies;
    }

    public Body[] getBodies() {
        return bodies;
    }

    void move() {
        iter++;
        coef = 1;
        for (Body body : bodies) {
            double vx = Math.abs(body.velocity.x);
            if (vx > 1.0) {
                coef = Math.max(coef, (int) Math.ceil(vx));
            }
            double vy = Math.abs(body.velocity.y);
            if (vy > 1.0) {
                coef = Math.max(coef, (int) Math.ceil(vy));
            }
        }
        //        System.out.println(coef);
        for (int i = 0; i < coef; i++) {
            moveInt();
        }

    }

    private void moveInt() {
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
            if (distance < 5) {
                System.out.printf(
                        "distance: %f, dx: %f, dy: %f, v1x: %f, v1y: %f, v2x: %f, v2y: %f, coef: %d, iter: %d%n",
                        distance,
                        dx, dy,
                        body1.velocity.x,
                        body1.velocity.y,
                        body2.velocity.x,
                        body2.velocity.y,
                        coef, iter);
            }
            passMomentums(body1, body2);
            return;
        }
        body1.passedMomentumWith.remove(body2);

        final double baseForce = G_CONST / distanceSq;
        final double normDx = dx / distance;
        final double normDy = dy / distance;

        double force1 = -1 * baseForce * body2.mass;
        double accelerationX1 = force1 * normDx;
        double accelerationY1 = force1 * normDy;

        body1.velocity.x += accelerationX1 / coef;
        body1.velocity.y += accelerationY1 / coef;

        double force2 = baseForce * body1.mass;
        double accelerationX2 = force2 * normDx;
        double accelerationY2 = force2 * normDy;

        body2.velocity.x += accelerationX2 / coef;
        body2.velocity.y += accelerationY2 / coef;
    }

    private void passMomentums(Body body1, Body body2) {
        if (body1.passedMomentumWith.contains(body2)) {
            return;
        }
        Vector momentum1 = body1.getMomentum();
        Vector momentum2 = body2.getMomentum();
        Optional<Boolean> elasticCollisionX = elasticCollision(body1, body2, Vector::getX);
        if (elasticCollisionX.isEmpty()) {
            //nothing
        } else if (elasticCollisionX.get()) {
            body1.velocity.x = momentum2.x / body1.mass;
            body2.velocity.x = momentum1.x / body2.mass;
        } else if (Math.abs(body1.velocity.x) >= Math.abs(body2.velocity.x)) {
            body1.velocity.x = 0;
            body2.velocity.x = (momentum1.x + momentum2.x) / (body1.mass + body2.mass);
        } else {
            body1.velocity.x = (momentum1.x + momentum2.x) / (body1.mass + body2.mass);
            body2.velocity.x = 0;
        }

        Optional<Boolean> elasticCollisionY = elasticCollision(body1, body2, Vector::getY);
        if (elasticCollisionY.isEmpty()) {
            //nothing
        } else if (elasticCollisionY.get()) {
            body1.velocity.y = momentum2.y / body1.mass;
            body2.velocity.y = momentum1.y / body2.mass;
        } else if (Math.abs(body1.velocity.y) >= Math.abs(body2.velocity.y)) {
            body1.velocity.y = 0;
            body2.velocity.y = (momentum1.y + momentum2.y) / (body1.mass + body2.mass);
        } else {
            body1.velocity.y = (momentum1.y + momentum2.y) / (body1.mass + body2.mass);
            body2.velocity.y = 0;
        }

        if (elasticCollisionX.isPresent() || elasticCollisionY.isPresent()) {
            body1.passedMomentumWith.add(body2);
//            System.out.println("Passed momentum");
        }
    }

    private Optional<Boolean> elasticCollision(Body body1, Body body2, Function<Vector, Double> getter) {
        if (getter.apply(body1.velocity) >= 0 && getter.apply(body2.velocity) <= 0) {
            if (getter.apply(body1.position) < getter.apply(body2.position)) {
                return Optional.of(true);
            } else {
                return Optional.empty();
            }
        }
        if (getter.apply(body1.velocity) <= 0 && getter.apply(body2.velocity) >= 0) {
            if (getter.apply(body1.position) > getter.apply(body2.position)) {
                return Optional.of(true);
            } else {
                return Optional.empty();
            }
        }
        if (getter.apply(body1.velocity) >= 0 && getter.apply(body2.velocity) >= 0) {
            if (getter.apply(body1.position) < getter.apply(body2.position)
                    && getter.apply(body1.velocity) > getter.apply(body2.velocity)) {
                return Optional.of(body1.mass >= body2.mass);
            } else if (getter.apply(body1.position) > getter.apply(body2.position)
                    && getter.apply(body1.velocity) < getter.apply(body2.velocity)) {
                return Optional.of(body1.mass <= body2.mass);
            } else {
                return Optional.empty();
            }
        }
        if (getter.apply(body1.velocity) <= 0 && getter.apply(body2.velocity) <= 0) {
            if (getter.apply(body1.position) < getter.apply(body2.position)
                    && getter.apply(body1.velocity) > getter.apply(body2.velocity)) {
                return Optional.of(body1.mass <= body2.mass);
            } else if (getter.apply(body1.position) > getter.apply(body2.position)
                    && getter.apply(body1.velocity) < getter.apply(body2.velocity)) {
                return Optional.of(body1.mass >= body2.mass);
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private void updatePosition(Body body) {
        body.position.x += body.velocity.x / coef;
        if (body.position.x <= -1 * SIZE) {
            body.position.x = -1 * SIZE;
            body.velocity.x *= -1;
        }
        if (body.position.x >= SIZE) {
            body.position.x = SIZE;
            body.velocity.x *= -1;
        }
        body.position.y += body.velocity.y / coef;
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
