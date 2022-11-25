package engine.hitboxes;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;

public class AABHitbox extends Hitbox {
    public Vec2d topLeft;
    public Vec2d size;
    private GameObject gameObject;

    public AABHitbox(Vec2d topLeft, Vec2d size) {
        this.topLeft = topLeft;
        this.size = size;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Vec2d getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Vec2d topLeft) {
        this.topLeft = topLeft;
    }

    public Vec2d getSize() {
        return size;
    }

    public void setSize(Vec2d size) {
        this.size = size;
    }


}
