package engine.components;

import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.hitboxes.CircleHitbox;
import engine.hitboxes.HitboxType;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public abstract class Component {
    public int spriteIndexToLoad = 0;
    protected Vec2d positionOnWorld = new Vec2d(0, 0);
    HitboxType hitboxType = HitboxType.UNKNOWN;
    private String tag = "";
    private GameObject gameObject;
    private boolean visibile = true;

    public Component(String tag, GameObject gameObject) {
        this.tag = tag;
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void onTick(long nanoSinceLastTick) {

    }

    public void lateTick() {

    }

    public void draw(GraphicsContext g) {

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Vec2d getPositionOnWorld() {
        return positionOnWorld;
    }

    public void setPositionOnWorld(Vec2d positionOnWorld) {
        this.positionOnWorld = positionOnWorld;
    }

    public CircleHitbox getCircleHitbox() {
        return null;
    }

    public AABHitbox getAabHitbox() {
        return null;
    }

    public HitboxType getHitboxType() {
        return hitboxType;
    }

    public void setHitboxType(HitboxType hitboxType) {
        this.hitboxType = hitboxType;
    }

    public boolean isVisibile() {
        return visibile;
    }

    public void setVisibile(boolean visibile) {
        this.visibile = visibile;
    }

    public void setSpriteIndexToLoad(int spriteIndexToLoad) {

    }
}

