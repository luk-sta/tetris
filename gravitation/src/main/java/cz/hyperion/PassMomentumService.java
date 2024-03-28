package cz.hyperion;

import java.util.Optional;
import java.util.function.Function;

final class PassMomentumService {

    static final double MOMENTUM_PRESERVATION_COEF = 0.1;

    void passMomentums(Body body1, Body body2) {
        if (body1.passedMomentumWith.contains(body2)) {
            return;
        }
        //        System.out.println("Passing momentum");
        Vector momentum1 = body1.getMomentum();
        Vector momentum2 = body2.getMomentum();
        Optional<Boolean> elasticCollisionX = isElasticCollision(body1, body2, Vector::getX);
        if (elasticCollisionX.isEmpty()) {
            //System.out.println("No collision x");
            //nothing
        } else if (elasticCollisionX.get()) {
            body1.velocity.x = MOMENTUM_PRESERVATION_COEF * momentum2.x / body1.mass;
            body2.velocity.x = MOMENTUM_PRESERVATION_COEF * momentum1.x / body2.mass;
        } else if (Math.abs(body1.velocity.x) >= Math.abs(body2.velocity.x)) {
            double preserveTotalMomentum = MOMENTUM_PRESERVATION_COEF * (momentum1.x + momentum2.x);
            double equalVelocity = preserveTotalMomentum / (body1.mass + body2.mass);
            body1.velocity.x = 0.1 * equalVelocity;
            body2.velocity.x = (preserveTotalMomentum - body1.velocity.x * body1.mass) / body2.mass;
        } else {
            double preserveTotalMomentum = MOMENTUM_PRESERVATION_COEF * (momentum1.x + momentum2.x);
            double equalVelocity = preserveTotalMomentum / (body1.mass + body2.mass);
            body2.velocity.x = 0.1 * equalVelocity;
            body1.velocity.x = (preserveTotalMomentum - body2.velocity.x * body2.mass) / body1.mass;
        }

        Optional<Boolean> elasticCollisionY = isElasticCollision(body1, body2, Vector::getY);
        if (elasticCollisionY.isEmpty()) {
            //System.out.println("No collision y");
            //nothing
        } else if (elasticCollisionY.get()) {
            body1.velocity.y = momentum2.y / body1.mass;
            body2.velocity.y = momentum1.y / body2.mass;
        } else if (Math.abs(body1.velocity.y) >= Math.abs(body2.velocity.y)) {
            double preserveTotalMomentum = MOMENTUM_PRESERVATION_COEF * (momentum1.y + momentum2.y);
            double equalVelocity = preserveTotalMomentum / (body1.mass + body2.mass);
            body1.velocity.y = 0.1 * equalVelocity;
            body2.velocity.y = (preserveTotalMomentum - body1.velocity.y * body1.mass) / body2.mass;
        } else {
            double preserveTotalMomentum = MOMENTUM_PRESERVATION_COEF * (momentum1.y + momentum2.y);
            double equalVelocity = preserveTotalMomentum / (body1.mass + body2.mass);
            body2.velocity.y = 0.1 * equalVelocity;
            body1.velocity.y = (preserveTotalMomentum - body2.velocity.y * body2.mass) / body1.mass;
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
