package engine.components;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;

public class PhysicsComponent extends Component {
    float mass;
    Vec2d pos, vel;

    double maxXVel = 4;
    double minXVel = -4;
    double minYVel = -3.5;
    Vec2d impulse, force;
    double restitution = 1;
    long timePassedSinceLastAction = 0;

    boolean gravityActivated = true;

    boolean frictionActivated = true;
    boolean isVelocityCapped = true;

    public PhysicsComponent(GameObject gameObject, float mass) {
        super("physics", gameObject);
        this.mass = mass;
        pos = new Vec2d(gameObject.getTransformComponent().getPositionOnWorld().x, gameObject.getTransformComponent().getPositionOnWorld().y);
        vel = new Vec2d(0);
        force = new Vec2d(0);
        impulse = new Vec2d(0);
    }

    public void applyForce(Vec2d f) {
        force = force.plus(f);
    }

    public void applyImpulse(Vec2d p) {
        impulse = impulse.plus(p);
    }

    @Override
    public void onTick(long t) {

        // This hacky code was written to stop absurd amount of tick value to break the physics
        t = t / 10000000;
        if (t > 10) {
            return;
        }

        if (gravityActivated) {
            applyForce(new Vec2d(0, 0.1 * mass));
        }
        if (frictionActivated) {
            applyForce(new Vec2d(-1 * vel.x, 0).smult(.45));
        }
        pos = new Vec2d(getGameObject().getTransformComponent().getPositionOnWorld().x, getGameObject().getTransformComponent().getPositionOnWorld().y);

        vel = vel.plus(force.smult(t).sdiv(mass).plus(impulse.sdiv(mass)));


        if (isVelocityCapped) {
            if (vel.x > maxXVel) {
                vel = new Vec2d(maxXVel, vel.y);
            }
            if (vel.x < minXVel) {
                vel = new Vec2d(minXVel, vel.y);
            }
            if (vel.y < minYVel) {
                vel = new Vec2d(vel.x, minYVel);
            }
        }


        pos = pos.plus(vel.smult(t));

        getGameObject().getTransformComponent().setPositionOnWorld(pos);
        force = new Vec2d(0);
        impulse = new Vec2d(0);
    }

    public boolean isGravityActivated() {
        return gravityActivated;
    }

    public void setGravityActivated(boolean gravityActivated) {
        this.gravityActivated = gravityActivated;
    }

    public Vec2d getVel() {
        return vel;
    }

    public void setVel(Vec2d vel) {
        this.vel = vel;
    }

    public float getMass() {
        return mass;
    }

    public double getRestitution() {
        return restitution;
    }

    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }

    public boolean isFrictionActivated() {
        return frictionActivated;
    }

    public void setFrictionActivated(boolean frictionActivated) {
        this.frictionActivated = frictionActivated;
    }

    public double getMaxXVel() {
        return maxXVel;
    }

    public void setMaxXVel(double maxXVel) {
        this.maxXVel = maxXVel;
    }

    public double getMinXVel() {
        return minXVel;
    }

    public void setMinXVel(double minXVel) {
        this.minXVel = minXVel;
    }

    public double getMinYVel() {
        return minYVel;
    }

    public void setMinYVel(double minYVel) {
        this.minYVel = minYVel;
    }

    public boolean isVelocityCapped() {
        return isVelocityCapped;
    }

    public void setVelocityCapped(boolean velocityCapped) {
        isVelocityCapped = velocityCapped;
    }
}