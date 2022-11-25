package engine.components;

import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.hitboxes.CircleHitbox;
import engine.hitboxes.HitboxType;
import engine.hitboxes.PolygonHitbox;

public class CollisionComponent extends Component {
    AABHitbox aabHitbox = null;
    CircleHitbox circleHitbox = null;
    PolygonHitbox polygonHitbox = null;
    CollisionBehaviour collisionBehaviour = CollisionBehaviour.UNDEFINED;
    String collisionId = "";


    public CollisionComponent(GameObject gameObject, AABHitbox aabHitbox) {
        super("collisionComponent", gameObject);
        this.aabHitbox = aabHitbox;
        this.aabHitbox.setGameObject(this.getGameObject());
        this.setHitboxType(HitboxType.AAB);

    }

    public CollisionComponent(GameObject gameObject, CircleHitbox circleHitbox) {
        super("collisionComponent", gameObject);
        this.circleHitbox = circleHitbox;
        this.circleHitbox.setGameObject(this.getGameObject());
        this.setHitboxType(HitboxType.CIRCLE);
    }

    public CollisionComponent(GameObject gameObject, PolygonHitbox polygonHitbox) {
        super("collisionComponent", gameObject);
        this.polygonHitbox = polygonHitbox;
        this.polygonHitbox.setGameObject(this.getGameObject());
        this.setHitboxType(HitboxType.POLYGON);
    }

    @Override
    public CircleHitbox getCircleHitbox() {
        return circleHitbox;
    }

    @Override
    public AABHitbox getAabHitbox() {
        return aabHitbox;
    }

    public PolygonHitbox getPolygonHitbox() {
        return polygonHitbox;
    }

    public CollisionBehaviour getCollisionBehaviour() {
        return collisionBehaviour;
    }

    public void setCollisionBehaviour(CollisionBehaviour collisionBehaviour) {
        this.collisionBehaviour = collisionBehaviour;
    }

    public String getCollisionId() {
        return collisionId;
    }

    public void setCollisionId(String collisionId) {
        this.collisionId = collisionId;
    }
}
