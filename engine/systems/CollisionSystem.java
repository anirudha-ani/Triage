package engine.systems;

import engine.GameWorld;
import engine.UIElement.Viewport;
import engine.components.*;
import engine.gameobjects.GameObject;
import engine.hitboxes.*;
import engine.support.Vec2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollisionSystem extends System {
    private final ArrayList<GameObject> aabHitboxes = new ArrayList<>();
    private final ArrayList<GameObject> circleHitboxes = new ArrayList<>();
    private final ArrayList<GameObject> polyHitboxes = new ArrayList<>();

    public CollisionSystem(GameWorld gameWorld) {
        super(gameWorld);
    }

    public void addObject(GameObject gameObject) {

        Component collisionComponent = gameObject.getComponent("collisionComponent");
        if (collisionComponent != null) {
            if (collisionComponent.getHitboxType() == HitboxType.AAB) {
                aabHitboxes.add(gameObject);
            } else if (collisionComponent.getHitboxType() == HitboxType.CIRCLE) {
                circleHitboxes.add(gameObject);
            } else if (collisionComponent.getHitboxType() == HitboxType.POLYGON) {
                polyHitboxes.add(gameObject);
            }
        }
    }

    public void removeObject(GameObject gameObject) {
        aabHitboxes.remove(gameObject);
        circleHitboxes.remove(gameObject);
        polyHitboxes.remove(gameObject);
    }

    public double castRay(Ray s) {
        double finalLength = 10000;
        for (int i = 0; i < aabHitboxes.size(); i++) {
            if (aabHitboxes.get(i).isDeleted()) {
                continue;
            }
            AABHitbox hitbox = aabHitboxes.get(i).getComponent("collisionComponent").getAabHitbox();
            double length = raycast(hitbox, s);
//            java.lang.System.out.println("AAB Length = " + length);

            if (length > 0) {
                finalLength = Math.min(length, finalLength);
            }
        }

        for (int j = 0; j < circleHitboxes.size(); j++) {

            CircleHitbox hitbox = circleHitboxes.get(j).getComponent("collisionComponent").getCircleHitbox();
            double length = raycast(hitbox, s);
//            java.lang.System.out.println("Circle Length = " + length);

            if (length > 0) {
                finalLength = Math.min(length, finalLength);
            }
        }

        for (int j = 0; j < polyHitboxes.size(); j++) {
            CollisionComponent collisionComponent = (CollisionComponent) polyHitboxes.get(j).getComponent("collisionComponent");
            PolygonHitbox hitbox = collisionComponent.getPolygonHitbox();
            double length = raycast(hitbox, s);
//            java.lang.System.out.println("Poly Length = " + length);

            if (length > 0) {
                finalLength = Math.min(length, finalLength);
            }
        }

        return finalLength;
    }

    public boolean checkGravityCollision(AABHitbox gravityHitbox) {
        for (int i = 0; i < aabHitboxes.size(); i++) {
            if (aabHitboxes.get(i).isDeleted()) {
                continue;
            }
            AABHitbox firstHitbox = aabHitboxes.get(i).getComponent("collisionComponent").getAabHitbox();
            AABHitbox secondHitBox = gravityHitbox;
            Vec2d collision1 = collision(firstHitbox, secondHitBox);
            if (collision1 != null) {
                return true;
            }
        }

        for (int j = 0; j < circleHitboxes.size(); j++) {
            AABHitbox firstHitbox = gravityHitbox;
            CircleHitbox secondHitBox = circleHitboxes.get(j).getComponent("collisionComponent").getCircleHitbox();
            Vec2d collision1 = collision(firstHitbox, secondHitBox);
            if (collision1 != null) {
                return true;
            }
        }

        for (int j = 0; j < polyHitboxes.size(); j++) {
            AABHitbox firstHitbox = gravityHitbox;
            CollisionComponent collisionComponent = (CollisionComponent) polyHitboxes.get(j).getComponent("collisionComponent");
            PolygonHitbox secondHitBox = collisionComponent.getPolygonHitbox();
            Vec2d collision1 = collision(firstHitbox, secondHitBox);
            if (collision1 != null) {
                return true;
            }
        }
        return false;
    }

    public void checkCollision(ArrayList<CollisionHappened> collisionEvents) {

        for (int i = 0; i < aabHitboxes.size(); i++) {
            if (aabHitboxes.get(i).isDeleted()) {
                continue;
            }
            GameObject gameObject = aabHitboxes.get(i).getComponent("collisionComponent").getGameObject();

            CollisionComponent gravityComponent = (CollisionComponent) aabHitboxes.get(i).getComponent("gravity");

            if (gravityComponent != null) {
                if (checkGravityCollision(gravityComponent.getAabHitbox())) {
                    PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");
                    if (physicsComponent != null) {
                        physicsComponent.setGravityActivated(false);
                    }
                } else {
                    PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");
                    if (physicsComponent != null) {
                        physicsComponent.setGravityActivated(true);
                    }
                }
            }

            for (int j = i + 1; j < aabHitboxes.size(); j++) {
                if (aabHitboxes.get(j).isDeleted()) {
                    continue;
                }
                AABHitbox firstHitbox = aabHitboxes.get(i).getComponent("collisionComponent").getAabHitbox();
                AABHitbox secondHitBox = aabHitboxes.get(j).getComponent("collisionComponent").getAabHitbox();

//                java.lang.System.out.println(firstHitbox.getGameObject().getId() + "::" + secondHitBox.getGameObject().getId());
                Vec2d collision1 = collision(firstHitbox, secondHitBox);
                Vec2d collision2 = collision(secondHitBox, firstHitbox);
                if (collision1 != null) {

//                    java.lang.System.out.println("Collision  " + firstHitbox.getGameObject().getId() + " :: " + secondHitBox.getGameObject().getId());
                    CollisionHappened collisionEvent = new CollisionHappened(firstHitbox.getGameObject(), secondHitBox.getGameObject(), collision1, collision2);
                    collisionEvents.add(collisionEvent);
                    handleCollision(collisionEvent);
                }
            }
        }

        for (int i = 0; i < aabHitboxes.size(); i++) {
            for (int j = 0; j < circleHitboxes.size(); j++) {
                AABHitbox firstHitbox = aabHitboxes.get(i).getComponent("collisionComponent").getAabHitbox();
                CircleHitbox secondHitBox = circleHitboxes.get(j).getComponent("collisionComponent").getCircleHitbox();
                Vec2d collision1 = collision(firstHitbox, secondHitBox);
                Vec2d collision2 = collision(secondHitBox, firstHitbox);
                if (collision1 != null) {
                    CollisionHappened collisionEvent = new CollisionHappened(firstHitbox.getGameObject(), secondHitBox.getGameObject(), collision1, collision2);
                    collisionEvents.add(collisionEvent);
                    handleCollision(collisionEvent);
                }
            }
        }

        for (int i = 0; i < circleHitboxes.size(); i++) {
            GameObject gameObject = circleHitboxes.get(i).getComponent("collisionComponent").getGameObject();

            CollisionComponent gravityComponent = (CollisionComponent) circleHitboxes.get(i).getComponent("gravity");

            if (gravityComponent != null) {
                if (checkGravityCollision(gravityComponent.getAabHitbox())) {
                    PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");
                    if (physicsComponent != null) {
                        physicsComponent.setGravityActivated(false);
                    }
                } else {
                    PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");
                    if (physicsComponent != null) {
                        physicsComponent.setGravityActivated(true);
                    }
                }
            }
            for (int j = i + 1; j < circleHitboxes.size(); j++) {
                CircleHitbox firstHitbox = circleHitboxes.get(i).getComponent("collisionComponent").getCircleHitbox();
                CircleHitbox secondHitBox = circleHitboxes.get(j).getComponent("collisionComponent").getCircleHitbox();
//                java.lang.System.out.println(firstHitbox.toString() + "::" + secondHitBox.toString());
                Vec2d collision1 = collision(firstHitbox, secondHitBox);
                Vec2d collision2 = collision(secondHitBox, firstHitbox);
                if (collision1 != null) {
                    CollisionHappened collisionEvent = new CollisionHappened(firstHitbox.getGameObject(), secondHitBox.getGameObject(), collision1, collision2);
                    collisionEvents.add(collisionEvent);
                    handleCollision(collisionEvent);
                }
            }
        }

        for (int i = 0; i < polyHitboxes.size(); i++) {
            GameObject gameObject = polyHitboxes.get(i).getComponent("collisionComponent").getGameObject();

            CollisionComponent gravityComponent = (CollisionComponent) polyHitboxes.get(i).getComponent("gravity");

            if (gravityComponent != null) {
                if (checkGravityCollision(gravityComponent.getAabHitbox())) {
                    PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");
                    if (physicsComponent != null) {
                        physicsComponent.setGravityActivated(false);
                    }
                } else {
                    PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");
                    if (physicsComponent != null) {
                        physicsComponent.setGravityActivated(true);
                    }
                }
            }

            CollisionComponent firstCollisionComponent = (CollisionComponent) polyHitboxes.get(i).getComponent("collisionComponent");
            PolygonHitbox firstHitbox = firstCollisionComponent.getPolygonHitbox();

            for (int j = 0; j < circleHitboxes.size(); j++) {
                CircleHitbox secondHitBox = circleHitboxes.get(j).getComponent("collisionComponent").getCircleHitbox();
                Vec2d collision1 = collision(firstHitbox, secondHitBox);
                Vec2d collision2 = collision(secondHitBox, firstHitbox);
                if (collision1 != null) {
                    CollisionHappened collisionEvent = new CollisionHappened(firstHitbox.getGameObject(), secondHitBox.getGameObject(), collision1, collision2);
                    collisionEvents.add(collisionEvent);
                    handleCollision(collisionEvent);
                }
            }

            for (int j = 0; j < aabHitboxes.size(); j++) {
                if (aabHitboxes.get(j).isDeleted()) {
                    continue;
                }
                AABHitbox secondHitBox = aabHitboxes.get(j).getComponent("collisionComponent").getAabHitbox();

//                java.lang.System.out.println(firstHitbox.getGameObject().getId() + "::" + secondHitBox.getGameObject().getId());
                Vec2d collision1 = collision(firstHitbox, secondHitBox);
                Vec2d collision2 = collision(secondHitBox, firstHitbox);
                if (collision1 != null) {

//                    java.lang.System.out.println("Collision  " + firstHitbox.getGameObject().getId() + " :: " + secondHitBox.getGameObject().getId());
                    CollisionHappened collisionEvent = new CollisionHappened(firstHitbox.getGameObject(), secondHitBox.getGameObject(), collision1, collision2);
                    collisionEvents.add(collisionEvent);
                    handleCollision(collisionEvent);
                }
            }

            for (int j = i + 1; j < polyHitboxes.size(); j++) {

                CollisionComponent secondCollisionComponent = (CollisionComponent) polyHitboxes.get(j).getComponent("collisionComponent");
                PolygonHitbox secondHitBox = secondCollisionComponent.getPolygonHitbox();

//                java.lang.System.out.println(firstHitbox.getGameObject().getId() + "::" + secondHitBox.getGameObject().getId());
                Vec2d collision1 = collision(firstHitbox, secondHitBox);
                Vec2d collision2 = collision(secondHitBox, firstHitbox);
                if (collision1 != null) {

//                    java.lang.System.out.println("Collision  " + firstHitbox.getGameObject().getId() + " :: " + secondHitBox.getGameObject().getId());
                    CollisionHappened collisionEvent = new CollisionHappened(firstHitbox.getGameObject(), secondHitBox.getGameObject(), collision1, collision2);
                    collisionEvents.add(collisionEvent);
                    handleCollision(collisionEvent);
                }
            }
        }
    }

    public void handleCollision(CollisionHappened collisionEvent) {
        CollisionComponent collisionComponent1 = (CollisionComponent) collisionEvent.getFirstObject().getComponent("collisionComponent");
        CollisionComponent collisionComponent2 = (CollisionComponent) collisionEvent.getSecondObject().getComponent("collisionComponent");

        if (collisionComponent1 != null && collisionComponent2 != null) {
            if (collisionComponent1.getCollisionBehaviour() == CollisionBehaviour.STATIC && collisionComponent2.getCollisionBehaviour() == CollisionBehaviour.STATIC) {
                return;
            }

            GameObject gameObject = null;
            GameObject gameObject2 = null;
            Vec2d mtv = null;
            Vec2d mtv2 = null;

            if (collisionComponent1.getCollisionBehaviour() == CollisionBehaviour.STATIC) {
                gameObject = collisionEvent.getSecondObject();
                mtv = collisionEvent.getSecondMtv();
            } else if (collisionComponent2.getCollisionBehaviour() == CollisionBehaviour.STATIC) {
                gameObject = collisionEvent.getFirstObject();
                mtv = collisionEvent.getFirstMtv();
            } else {
                gameObject = collisionEvent.getFirstObject();
                mtv = collisionEvent.getFirstMtv();
                gameObject2 = collisionEvent.getSecondObject();
                mtv2 = collisionEvent.getSecondMtv();
            }

            TransformComponent transform = gameObject.getTransformComponent();
            double deltaX = mtv.x;
            double deltaY = mtv.y;
            if (gameObject2 != null) {
                deltaX /= 2;
                deltaY /= 2;
            }
//             java.lang.System.out.println("Delta x = " + deltaX + " Delta Y = "+ deltaY);
            Vec2d newPosition = new Vec2d(transform.getPositionOnWorld().x + deltaX, transform.getPositionOnWorld().y + deltaY);
//            transform.setPositionOnWorld(newPosition);
            Viewport viewport = getRefGameWorld().getRefScreen().getViewport();
            if (viewport != null) {
                viewport.shiftViewPort(new Vec2d(-1 * deltaX, -1 * deltaY));
            }

            PhysicsComponent physicsComponent1 = (PhysicsComponent) gameObject.getComponent("physics");
            Vec2d norMalizedMtv = mtv.normalize();
            if (mtv.x == 0 && mtv.y == 0) {
                return;
            }
            Double u1 = norMalizedMtv.dot(physicsComponent1.getVel());


            if (gameObject2 != null) {

                TransformComponent transform2 = gameObject2.getTransformComponent();
                double deltaX2 = mtv2.x / 2;
                double deltaY2 = mtv2.y / 2;

                Vec2d newPosition2 = new Vec2d(transform2.getPositionOnWorld().x + deltaX2, transform2.getPositionOnWorld().y + deltaY2);

                PhysicsComponent physicsComponent2 = (PhysicsComponent) gameObject2.getComponent("physics");
                Vec2d norMalizedMtv2 = mtv2.normalize();
                if (mtv2.x == 0 && mtv2.y == 0) {
                    return;
                }

                Double u2 = norMalizedMtv2.dot(physicsComponent2.getVel());

                double I1 = (physicsComponent1.getMass() * physicsComponent2.getMass() * (u2 - u1) *
                        (1 + Math.sqrt(physicsComponent1.getRestitution() * physicsComponent2.getRestitution())))
                        / (physicsComponent1.getMass() + physicsComponent2.getMass()) * .2;
                double I2 = (physicsComponent2.getMass() * physicsComponent1.getMass() * (u1 - u2) *
                        (1 + Math.sqrt(physicsComponent2.getRestitution() * physicsComponent1.getRestitution())))
                        / (physicsComponent2.getMass() + physicsComponent1.getMass()) * .2;

                Vec2d impulse1 = norMalizedMtv.smult(Math.abs(I1));
                Vec2d impulse2 = norMalizedMtv2.smult(Math.abs(I2));

                physicsComponent1.applyImpulse(impulse1);
                physicsComponent2.applyImpulse(impulse2);
                transform.setPositionOnWorld(newPosition);
                transform2.setPositionOnWorld(newPosition2);

            } else {
                double I1 = (physicsComponent1.getMass() * (-u1))
                        / (physicsComponent1.getMass());
                Vec2d impulse1 = mtv.normalize().smult(Math.abs(I1));
                physicsComponent1.setVel(new Vec2d(0, 0));
                if (Math.abs(I1) > 1) {
                    physicsComponent1.applyImpulse(impulse1);
                }
                transform.setPositionOnWorld(newPosition);

            }

        }
    }


    public Vec2d collision(AABHitbox s1, AABHitbox s2) {
        Vec2d topLeft = new Vec2d(s1.getTopLeft().x, s1.getTopLeft().y);
        Vec2d topRight = new Vec2d(s1.getTopLeft().x + s1.getSize().x, s1.getTopLeft().y);
        Vec2d bottomLeft = new Vec2d(s1.getTopLeft().x, s1.getTopLeft().y + s1.getSize().y);
        Vec2d bottomRight = new Vec2d(s1.getTopLeft().x + s1.getSize().x, s1.getTopLeft().y + s1.getSize().y);

        Vec2d topLeft2 = new Vec2d(s2.getTopLeft().x, s2.getTopLeft().y);
        Vec2d topRight2 = new Vec2d(s2.getTopLeft().x + s2.getSize().x, s2.getTopLeft().y);
        Vec2d bottomLeft2 = new Vec2d(s2.getTopLeft().x, s2.getTopLeft().y + s2.getSize().y);
        Vec2d bottomRight2 = new Vec2d(s2.getTopLeft().x + s2.getSize().x, s2.getTopLeft().y + s2.getSize().y);


        if (collision(s2, topLeft) != null || collision(s2, topRight) != null || collision(s2, bottomLeft) != null || collision(s2, bottomRight) != null
                || collision(s1, topLeft2) != null || collision(s1, topRight2) != null || collision(s1, bottomLeft2) != null || collision(s1, bottomRight2) != null) {
            double ans = Double.MAX_VALUE;

            double magnitude = 0;
            double angle = 0;

            magnitude = Math.abs((s1.topLeft.y + s1.size.y) - s2.topLeft.y);
            // System.out.println("1 = "+magnitude);
            if (magnitude < ans) {
                //System.out.println("01");
                ans = magnitude;
                angle = Math.PI + Math.PI / 2;
            }

            magnitude = Math.abs(s1.topLeft.y - (s2.topLeft.y + s2.size.y));
            // System.out.println("2 = "+magnitude);
            if (magnitude < ans) {
                //System.out.println("02");
                ans = magnitude;
                angle = Math.PI / 2;
            }

            magnitude = Math.abs((s1.topLeft.x + s1.size.x) - s2.topLeft.x);
            // System.out.println("3 = "+magnitude);

            if (magnitude < ans) {
                //System.out.println("03");
                ans = magnitude;
                angle = Math.PI;
            }

            magnitude = Math.abs(s1.topLeft.x - (s2.topLeft.x + s2.size.x));
            // System.out.println("4 = "+magnitude);
            if (magnitude < ans) {
                //System.out.println("04");
                ans = magnitude;
                angle = 0;
            }

            return new Vec2d(ans).fromPolar(angle, ans);
        }
        return null;

    }

    public Vec2d collision(AABHitbox s1, CircleHitbox s2) {

        if (collision(s1, s2.center) != null) {
            double x = 0, y = 0;
            double miniDist = Double.MAX_VALUE;
            double angle = 0;

            if ((s2.center.x - s1.topLeft.x) < miniDist) {
                x = s1.topLeft.x;
                y = s2.center.y;
                miniDist = (s2.center.x - s1.topLeft.x);
                angle = 0;
            }

            if ((s1.topLeft.x + s1.size.x - s2.center.x) < miniDist) {
                x = s1.topLeft.x + s1.size.x;
                y = s2.center.y;
                miniDist = (s1.topLeft.x + s1.size.x - s2.center.x);
                angle = Math.PI;
            }

            if ((s2.center.y - s1.topLeft.y) < miniDist) {
                y = s1.topLeft.y;
                x = s2.center.x;
                miniDist = (s2.center.y - s1.topLeft.y);
                angle = Math.PI / 2;
            }

            if ((s1.topLeft.y + s1.size.y - s2.center.y) < miniDist) {
                y = s1.topLeft.y + s1.size.y;
                x = s2.center.x;
                miniDist = (s1.topLeft.y + s1.size.y - s2.center.y);
                angle = Math.PI + Math.PI / 2;
            }
            Vec2d closestPoint = new Vec2d(x, y);
            double magnitude = s2.radius + s2.center.dist(closestPoint);
            return new Vec2d(magnitude).fromPolar(angle, magnitude);
        } else {
            double x, y;

            if (s2.center.x < s1.topLeft.x) {
                x = s1.topLeft.x;
            } else if (s2.center.x > s1.topLeft.x + s1.size.x) {
                x = s1.topLeft.x + s1.size.x;
            } else {
                x = s2.center.x;
            }

            if (s2.center.y < s1.topLeft.y) {
                y = s1.topLeft.y;
            } else if (s2.center.y > s1.topLeft.y + s1.size.y) {
                y = s1.topLeft.y + s1.size.y;
            } else {
                y = s2.center.y;
            }

            Vec2d clampedCenter = new Vec2d(x, y);

            if (clampedCenter.dist(s2.center) > s2.radius) {
                return null;
            } else {
                double magnitude = s2.radius - clampedCenter.dist(s2.center);
                double angle = s2.center.minus(clampedCenter).angle() + Math.PI;
                return new Vec2d(magnitude).fromPolar(angle, magnitude);
            }
        }

    }


    public Vec2d collision(AABHitbox s1, Vec2d s2) {
        if (s1.topLeft.x <= s2.x && s2.x <= s1.topLeft.x + s1.size.x & s1.topLeft.y <= s2.y && s2.y <= s1.topLeft.y + s1.size.y) {
            return new Vec2d(0, 0);
        }
        return null;
    }


    public Vec2d collision(AABHitbox s1, PolygonHitbox s2) {
        int numberOfPoints = s2.getNumPoints();

        ArrayList<Vec2d> axis = new ArrayList<>();

        for (int i = 0; i < numberOfPoints; i++) {
            double x1 = s2.getPoint(i).x;
            double y1 = s2.getPoint(i).y;

            double x2, y2;

            if (i == numberOfPoints - 1) {
                x2 = s2.getPoint(0).x;
                y2 = s2.getPoint(0).y;

            } else {
                x2 = s2.getPoint(i + 1).x;
                y2 = s2.getPoint(i + 1).y;
            }

            Vec2d vectorAlongEdge = new Vec2d(x2 - x1, y2 - y1);
            Vec2d perpendicularToEdge = vectorAlongEdge.perpendicular().normalize();

            axis.add(perpendicularToEdge);
        }

        axis.add(new Vec2d(0, 1));
        axis.add(new Vec2d(1, 0));

        double minMagnitude = Double.MAX_VALUE;
        Vec2d mtv = null;

        for (int i = 0; i < axis.size(); i++) {
            Vec2d currentAxis = axis.get(i);
            List<Double> s1Projections = new ArrayList<>();
            List<Double> s2Projections = new ArrayList<>();

            ArrayList<Vec2d> AABPoints = new ArrayList<>();

            AABPoints.add(new Vec2d(s1.topLeft));
            AABPoints.add(new Vec2d(s1.topLeft.x, s1.topLeft.y + s1.size.y));
            AABPoints.add(new Vec2d(s1.topLeft.x + s1.size.x, s1.topLeft.y + s1.size.y));
            AABPoints.add(new Vec2d(s1.topLeft.x + s1.size.x, s1.topLeft.y));

            for (int j = 0; j < AABPoints.size(); j++) {
                double projection = AABPoints.get(j).dot(currentAxis);
                s1Projections.add(projection);
            }

            for (int j = 0; j < s2.getNumPoints(); j++) {
                double projection = s2.getPoint(j).dot(currentAxis);
                s2Projections.add(projection);
            }

            Double mtv1d = intervalMTV(Collections.max(s1Projections), Collections.min(s1Projections),
                    Collections.max(s2Projections), Collections.min(s2Projections));

            if (mtv1d == null) {
                return null;
            }
            if (Math.abs(mtv1d) < minMagnitude) {
                minMagnitude = Math.abs(mtv1d);
                mtv = currentAxis.smult(mtv1d);
            }
        }
        return mtv;
    }

    // CIRCLES


    public Vec2d collision(CircleHitbox s1, AABHitbox s2) {
        Vec2d f = collision(s2, s1);
        return f == null ? null : f.reflect();
    }


    public Vec2d collision(CircleHitbox s1, CircleHitbox s2) {
        if (s1.center.dist2(s2.center) <= (s1.radius + s2.radius) * (s1.radius + s2.radius)) {
            double distanceBetweenCenter = s1.center.dist(s2.center);
            double magnitude = s1.radius + s2.radius - distanceBetweenCenter;
            double angle = s1.center.minus(s2.center).angle();
            return new Vec2d(magnitude).fromPolar(angle, magnitude);
        }
        return null;
    }


    public Vec2d collision(CircleHitbox s1, Vec2d s2) {
        if ((s1.center.x - s2.x) * (s1.center.x - s2.x) + (s1.center.y - s2.y) * (s1.center.y - s2.y) <= s1.radius * s1.radius) {
            return new Vec2d(0, 0);
        }
        return null;
    }


    public Vec2d collision(CircleHitbox s1, PolygonHitbox s2) {

        ArrayList<Vec2d> axisList = new ArrayList<>();

        double minDistanceFromCenter = Double.MAX_VALUE;
        Vec2d circleToPolyVector = null;

        for (int i = 0; i < s2.getNumPoints(); i++) {
            double distanceFromCenter = Math.abs(s1.center.dist(s2.getPoint(i)));

            if (distanceFromCenter < minDistanceFromCenter) {
                minDistanceFromCenter = distanceFromCenter;
                circleToPolyVector = s2.getPoint(i).minus(s1.center);
            }
        }

        axisList.add(circleToPolyVector.normalize());
//		System.out.println("Poly points ");
        for (int i = 0; i < s2.getNumPoints(); i++) {
            double x1 = s2.getPoint(i).x;
            double y1 = s2.getPoint(i).y;


            double x2, y2;

            if (i == s2.getNumPoints() - 1) {
                x2 = s2.getPoint(0).x;
                y2 = s2.getPoint(0).y;

            } else {
                x2 = s2.getPoint(i + 1).x;
                y2 = s2.getPoint(i + 1).y;
            }

            Vec2d vectorAlongEdge = new Vec2d(x2 - x1, y2 - y1);
            Vec2d perpendicularToEdge = vectorAlongEdge.perpendicular().normalize();

            axisList.add(perpendicularToEdge);
        }

        double minMagnitude = Double.MAX_VALUE;
        Vec2d mtv = null;


        for (int i = 0; i < axisList.size(); i++) {
            Vec2d currentAxis = axisList.get(i);
            List<Double> s1Projections = new ArrayList<>();
            List<Double> s2Projections = new ArrayList<>();


            double projection = currentAxis.smult(s1.radius).plus(s1.center).dot(currentAxis);
            s1Projections.add(projection);

            projection = currentAxis.smult(-1 * s1.radius).plus(s1.center).dot(currentAxis);

            s1Projections.add(projection);

            for (int j = 0; j < s2.getNumPoints(); j++) {
                double projection2 = s2.getPoint(j).dot(currentAxis);
                s2Projections.add(projection2);
            }

            Double mtv1d = intervalMTV(Collections.max(s1Projections), Collections.min(s1Projections),
                    Collections.max(s2Projections), Collections.min(s2Projections));

            if (mtv1d == null) {
                return null;
            }
            if (Math.abs(mtv1d) < minMagnitude) {
                minMagnitude = Math.abs(mtv1d);
                mtv = currentAxis.smult(mtv1d);
            }
        }
        return mtv;
    }

    // POLYGONS


    public Vec2d collision(PolygonHitbox s1, AABHitbox s2) {
        Vec2d f = collision(s2, s1);
        return f == null ? null : f.reflect();
    }


    public Vec2d collision(PolygonHitbox s1, CircleHitbox s2) {
        Vec2d f = collision(s2, s1);
        return f == null ? null : f.reflect();
    }

    public boolean isCounterClockWise(PolygonHitbox s1) {
        int numberOfPoints = s1.getNumPoints();
        double area = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            Vec2d firstPoint = s1.getPoint(i);
            Vec2d secondPoint;

            if (i == numberOfPoints - 1) {
                secondPoint = s1.getPoint(0);

            } else {
                secondPoint = s1.getPoint(i + 1);
            }
            area += firstPoint.cross(secondPoint);
        }

        return !(area > 0);
    }


    public Vec2d collision(PolygonHitbox s1, Vec2d s2) {
        int numberOfPoints = s1.getNumPoints();
        double area = 0;
        boolean isCounterClockWise = isCounterClockWise(s1);

        for (int i = 0; i < numberOfPoints; i++) {
            Vec2d firstPoint = s1.getPoint(i);
            Vec2d secondPoint;

            if (i == numberOfPoints - 1) {
                secondPoint = s1.getPoint(0);

            } else {
                secondPoint = s1.getPoint(i + 1);
            }
            if (!isCounterClockWise) {
                Vec2d temp = firstPoint;
                firstPoint = secondPoint;
                secondPoint = temp;
            }

            double x1 = firstPoint.x;
            double y1 = firstPoint.y;
            double x2 = secondPoint.x;
            double y2 = secondPoint.y;

            Vec2d vectorAlongEdge = new Vec2d(x2 - x1, y2 - y1);
            Vec2d vectorToPointFromNewBase = new Vec2d(s2.x - x1, s2.y - y1);

            if (vectorAlongEdge.cross(vectorToPointFromNewBase) > 0) {
                return null;
            }

        }
        return new Vec2d(0, 0);
    }

    Double intervalMTV(double max_r1, double min_r1, double max_r2, double min_r2) {
        Double aRight = max_r2 - min_r1;
        Double aLeft = max_r1 - min_r2;
        if (aLeft < 0 || aRight < 0) {
            return null;
        }
        if (aRight < aLeft) {
            return aRight;
        } else {
            return -aLeft;
        }
    }


    public Vec2d collision(PolygonHitbox s1, PolygonHitbox s2) {
        int numberOfPoints = s1.getNumPoints();

        ArrayList<Vec2d> axis = new ArrayList<>();

        for (int i = 0; i < numberOfPoints; i++) {
            double x1 = s1.getPoint(i).x;
            double y1 = s1.getPoint(i).y;

            double x2, y2;

            if (i == numberOfPoints - 1) {
                x2 = s1.getPoint(0).x;
                y2 = s1.getPoint(0).y;

            } else {
                x2 = s1.getPoint(i + 1).x;
                y2 = s1.getPoint(i + 1).y;
            }

            Vec2d vectorAlongEdge = new Vec2d(x2 - x1, y2 - y1);
            Vec2d perpendicularToEdge = vectorAlongEdge.perpendicular().normalize();

            axis.add(perpendicularToEdge);
        }

        numberOfPoints = s2.getNumPoints();

        for (int i = 0; i < numberOfPoints; i++) {
            double x1 = s2.getPoint(i).x;
            double y1 = s2.getPoint(i).y;

            double x2, y2;

            if (i == numberOfPoints - 1) {
                x2 = s2.getPoint(0).x;
                y2 = s2.getPoint(0).y;
            } else {
                x2 = s2.getPoint(i + 1).x;
                y2 = s2.getPoint(i + 1).y;
            }

            Vec2d vectorAlongEdge = new Vec2d(x2 - x1, y2 - y1);

            Vec2d perpendicularToEdge = vectorAlongEdge.perpendicular().normalize();

            axis.add(perpendicularToEdge);
        }

        double minMagnitude = Double.MAX_VALUE;
        Vec2d mtv = null;

        for (int i = 0; i < axis.size(); i++) {
            Vec2d currentAxis = axis.get(i);
            List<Double> s1Projections = new ArrayList<>();
            List<Double> s2Projections = new ArrayList<>();

            for (int j = 0; j < s1.getNumPoints(); j++) {
                double projection = s1.getPoint(j).dot(currentAxis);
                s1Projections.add(projection);
            }

            for (int j = 0; j < s2.getNumPoints(); j++) {
                double projection = s2.getPoint(j).dot(currentAxis);
                s2Projections.add(projection);
            }

            Double mtv1d = intervalMTV(Collections.max(s1Projections), Collections.min(s1Projections),
                    Collections.max(s2Projections), Collections.min(s2Projections));

            if (mtv1d == null) {
                return null;
            }
            if (Math.abs(mtv1d) < minMagnitude) {
                minMagnitude = Math.abs(mtv1d);
                mtv = currentAxis.smult(mtv1d);
            }
        }
        return mtv;
    }

    //RAY CASTING


    public float raycast(AABHitbox s1, Ray s2) {
        Vec2d[] points = {s1.getTopLeft(),
                s1.getTopLeft().plus(new Vec2d(0, s1.getSize().y)),
                s1.getTopLeft().plus(new Vec2d(s1.getSize().x, s1.getSize().y)),
                s1.getTopLeft().plus(new Vec2d(s1.getSize().x, 0)),
        };

        PolygonHitbox s = new PolygonHitbox(points);

        return raycast(s, s2);
    }

    public float raycast(CircleHitbox s1, Ray s2) {
        if (s1.center.dist(s2.src) > s1.radius) {
            Vec2d centerProjection = s1.getCenter().projectOntoLine(s2.src, s2.dir.plus(s2.src));

            Vec2d srcToProjection = centerProjection.minus(s2.src);
            if (srcToProjection.x * s2.dir.x < 0 || srcToProjection.y * s2.dir.y < 0) {
                return -1;
            }
            if (s1.center.dist(centerProjection) <= s1.radius) {
                double x = s1.center.dist(centerProjection);
                double L = s2.src.dist(centerProjection);
                return (float) (L - (Math.sqrt(s1.radius * s1.radius - x * x)));
            }
        } else {
            Vec2d centerProjection = s1.getCenter().projectOntoLine(s2.src, s2.dir.plus(s2.src));
            if (s1.center.dist(centerProjection) <= s1.radius) {
                double x = s1.center.dist(centerProjection);
                double L = s2.src.dist(centerProjection);
                return (float) ((Math.sqrt(s1.radius * s1.radius - x * x)) + L);
            }

        }

        return -1;
    }

    public float raycast(PolygonHitbox s1, Ray s2) {
        double ans = Double.MAX_VALUE;
        boolean triggered = false;
        for (int i = 1; i < s1.getNumPoints() + 1; i++) {
            Vec2d a = s1.getPoint((i - 1) % s1.getNumPoints());
            Vec2d b = s1.getPoint(i % s1.getNumPoints());

            Vec2d firstHand = a.minus(s2.src);
            Vec2d secondHand = b.minus(s2.src);

            if (s2.dir.cross(firstHand) * s2.dir.cross(secondHand) > 0) {
                continue;
            }
            triggered = true;
            Vec2d n = a.minus(b).perpendicular().normalize();

            double numerator = b.minus(s2.src).dot(n);
            double denominator = s2.dir.dot(n);

            ans = Math.min(ans, numerator / denominator);
        }
        if (triggered) {
            return (float) ans;
        }
        return -1;
    }

}
