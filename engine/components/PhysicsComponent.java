package engine.components;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;

public class PhysicsComponent extends Component {
    float mass;
    Vec2d pos, vel;
    Vec2d impulse, force;
    double restitution = 1;
    long timePassedSinceLastAction = 0;

    boolean gravityActivated = true;

    boolean frictionActivated = true;

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

        t = t / 10000000;
        if (t > 10) {
            return;
        }
        if (frictionActivated) {
            applyForce(new Vec2d(-1 * vel.x, 0).smult(.13));
        }

        pos = new Vec2d(getGameObject().getTransformComponent().getPositionOnWorld().x, getGameObject().getTransformComponent().getPositionOnWorld().y);

        vel = vel.plus(force.smult(t).sdiv(mass).plus(impulse.sdiv(mass)));
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
}