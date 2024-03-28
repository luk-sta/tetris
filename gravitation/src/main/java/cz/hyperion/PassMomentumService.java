package cz.hyperion;

import java.util.Optional;
import java.util.function.Function;

final class PassMomentumService {

    void passMomentums(Body body1, Body body2) {
        if (body1.passedMomentumWith.contains(body2)) {
            return;
        }
        Vector momentum1 = body1.getMomentum();
        Vector momentum2 = body2.getMomentum();
        Optional<Boolean> elasticCollisionX = isElasticCollision(body1, body2, Vector::getX);
        if (elasticCollisionX.isEmpty()) {
            //nothing
        } else if (elasticCollisionX.get()) {
            body1.velocity.x = momentum2.x / body1.mass;
            body2.velocity.x = momentum1.x / body2.mass;
        } else if (Math.abs(body1.velocity.x) >= Math.abs(body2.velocity.x)) {
            double equalVelocity = (momentum1.x + momentum2.x) / (body1.mass + body2.mass);
            body1.velocity.x = 0.5 * equalVelocity;
            body2.velocity.x = ((momentum1.x + momentum2.x) - body1.velocity.x * body1.mass) / body2.mass;
        } else {
            double equalVelocity = (momentum1.x + momentum2.x) / (body1.mass + body2.mass);
            body2.velocity.x = 0.5 * equalVelocity;
            body1.velocity.x = ((momentum1.x + momentum2.x) - body2.velocity.x * body2.mass) / body1.mass;
        }

        Optional<Boolean> elasticCollisionY = isElasticCollision(body1, body2, Vector::getY);
        if (elasticCollisionY.isEmpty()) {
            //nothing
        } else if (elasticCollisionY.get()) {
            body1.velocity.y = momentum2.y / body1.mass;
            body2.velocity.y = momentum1.y / body2.mass;
        } else if (Math.abs(body1.velocity.y) >= Math.abs(body2.velocity.y)) {
            double equalVelocity = (momentum1.y + momentum2.y) / (body1.mass + body2.mass);
            body1.velocity.y = 0.5 * equalVelocity;
            body2.velocity.y = ((momentum1.y + momentum2.y) - body1.velocity.y * body1.mass) / body2.mass;
        } else {
            double equalVelocity = (momentum1.y + momentum2.y) / (body1.mass + body2.mass);
            body2.velocity.y = 0.5 * equalVelocity;
            body1.velocity.y = ((momentum1.y + momentum2.y) - body2.velocity.y * body2.mass) / body1.mass;
        }

        if (elasticCollisionX.isPresent() || elasticCollisionY.isPresent()) {
            body1.passedMomentumWith.add(body2);
            //            System.out.println("Passed momentum");
        }
    }

    private Optional<Boolean> isElasticCollision(Body body1, Body body2, Function<Vector, Double> getter) {
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
}
