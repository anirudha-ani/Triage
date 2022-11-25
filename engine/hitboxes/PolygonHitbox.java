package engine.hitboxes;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;

public class PolygonHitbox {

    public GameObject gameObject;
    protected Vec2d[] points;


    public PolygonHitbox(Vec2d... points) {
        this.points = points;
    }

    public int getNumPoints() {
        return points.length;
    }

    public Vec2d getPoint(int i) {
        return points[i];
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void setPoints(Vec2d[] points) {
        this.points = points;
    }
}
