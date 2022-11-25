package engine.systems;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;

public class CollisionHappened {

    Vec2d firstMtv;
    Vec2d secondMtv;
    private GameObject firstObject;
    private GameObject secondObject;

    public CollisionHappened(GameObject first, GameObject second) {
        this.firstObject = first;
        this.secondObject = second;
    }

    public CollisionHappened(GameObject first, GameObject second, Vec2d firstMtv, Vec2d secondMtv) {
        this.firstObject = first;
        this.secondObject = second;
        this.firstMtv = firstMtv;
        this.secondMtv = secondMtv;
    }

    public GameObject getFirstObject() {
        return firstObject;
    }

    public void setFirstObject(GameObject firstObject) {
        this.firstObject = firstObject;
    }

    public GameObject getSecondObject() {
        return secondObject;
    }

    public void setSecondObject(GameObject secondObject) {
        this.secondObject = secondObject;
    }

    public Vec2d getFirstMtv() {
        return firstMtv;
    }

    public Vec2d getSecondMtv() {
        return secondMtv;
    }
}
