package engine.hitboxes;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;

public class CircleHitbox {
    public Vec2d center;
    public float radius;
    public GameObject gameObject;

    public CircleHitbox(Vec2d center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Vec2d getCenter() {
        return center;
    }

    public void setCenter(Vec2d center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
