package cz.hyperion;

import java.util.Arrays;

import static cz.hyperion.Body.BODY_SIZE;

final class MovementManager {
    public static final int SIZE = 500;
    private static final double G_CONST = 1;

    private final PassMomentumService passMomentumService = new PassMomentumService();
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
        if (iter % 100 == 0) {
            double totalKineticEnergy = Arrays.stream(bodies).mapToDouble(Body::getKineticEnergy).sum();
            double totalPotentialEnergy = computeTotalPotentialEnergy();
            System.out.printf("Total energy: %f, kinetic: %f, potential: %f%n",
                    (totalKineticEnergy + totalPotentialEnergy), totalKineticEnergy, totalPotentialEnergy);
        }
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

    private double computeTotalPotentialEnergy() {
        double totalPotentialEnergy = 0;
        for (int i = 0; i < bodies.length; i++) {
            var body1 = bodies[i];
            for (int j = i + 1; j < bodies.length; j++) {
                var body2 = bodies[j];
                double distance = distance(body1, body2);
                //                double f = G_CONST * body1.mass * body2.mass / (distance * distance);
                //                double g1 = f/body1.mass;
                //                double g1 = G_CONST * body2.mass / (distance * distance);
                //                double potentialEnergy = g1 * body1.mass * distance;
                double potentialEnergy1 = G_CONST * body2.mass * body1.mass / distance;
                totalPotentialEnergy += potentialEnergy1;
                double potentialEnergy2 = G_CONST * body1.mass * body2.mass / distance;
                totalPotentialEnergy += potentialEnergy2;
            }
        }
        return totalPotentialEnergy;
    }

    private void moveInt() {
        for (int i = 0; i < bodies.length; i++) {
            var body1 = bodies[i];
            for (int j = i + 1; j < bodies.length; j++) {
                var body2 = bodies[j];
                updateVelocity(body1, body2);
            }
        }

        for (int i = 0; i < bodies.length; i++) {
            updatePosition(i);
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
                //                throw new RuntimeException("Collision");
            }
            passMomentumService.passMomentums(body1, body2);
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

    private void updatePosition(int bodyIndex) {
        var body = bodies[bodyIndex];
        double newX = body.position.x + body.velocity.x / coef;
        double newY = body.position.y + body.velocity.y / coef;

        if (newX <= -1 * SIZE) {
            newX = -1 * SIZE;
            body.velocity.x *= -1;
        }
        if (newX >= SIZE) {
            newX = SIZE;
            body.velocity.x *= -1;
        }
        if (newY <= -1 * SIZE) {
            newY = -1 * SIZE;
            body.velocity.y *= -1;
        }
        if (newY >= SIZE) {
            newY = SIZE;
            body.velocity.y *= -1;
        }

        Vector newPosition = new Vector(newX, newY);
        for (int j = 0; j < bodyIndex; j++) {
            var body2 = bodies[j];
            double distance = distance(newPosition, body2.position);
            if (distance < BODY_SIZE) {
                return;
            }
        }
        body.position = newPosition;
    }

    private static double distance(Vector v1, Vector v2) {
        return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
    }

    private static double distance(Body b1, Body b2) {
        return distance(b1.position, b2.position);
    }


}
