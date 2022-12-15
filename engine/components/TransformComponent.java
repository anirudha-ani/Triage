package engine.components;

import engine.gameobjects.GameObject;
import engine.hitboxes.HitboxType;
import engine.hitboxes.PolygonHitbox;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class TransformComponent {

    private Vec2d positionOnWorld;
    private Vec2d sizeOnWorld;
    private Color backGroundColor = Color.TRANSPARENT;
    private boolean mouseHovering = false;
    private boolean mouseGrabbing = false;
    GameObject gameObject;


    public TransformComponent(Vec2d position, Vec2d size) {
        this.positionOnWorld = position;
        this.sizeOnWorld = size;
    }

    public Vec2d getPositionOnWorld() {
        return positionOnWorld;
    }

    public void setPositionOnWorld(Vec2d position) {
        double deltaX = position.x - this.positionOnWorld.x;
        double deltaY = position.y - this.positionOnWorld.y;
        CollisionComponent collisionComponent = (CollisionComponent) gameObject.getComponent("collisionComponent");
        Component gravityComponent = gameObject.getComponent("gravity");


        for (Component component : gameObject.get_components()) {
            Vec2d prevPosition = component.getPositionOnWorld();
            component.setPositionOnWorld(new Vec2d(prevPosition.x + deltaX, prevPosition.y + deltaY));
        }
        this.positionOnWorld = position;
        if (collisionComponent != null) {
            if (collisionComponent.getHitboxType() == HitboxType.AAB) {
                Vec2d newHitboxPosition = new Vec2d(collisionComponent.getAabHitbox().getTopLeft().x + deltaX, collisionComponent.getAabHitbox().getTopLeft().y + deltaY);

                collisionComponent.getAabHitbox().setTopLeft(newHitboxPosition);
            }
            if (collisionComponent.getHitboxType() == HitboxType.CIRCLE) {
                Vec2d newHitboxPosition = new Vec2d(collisionComponent.getCircleHitbox().getCenter().x + deltaX, collisionComponent.getCircleHitbox().getCenter().y + deltaY);

                collisionComponent.getCircleHitbox().setCenter(newHitboxPosition);
            }
            if (collisionComponent.getHitboxType() == HitboxType.POLYGON) {
                PolygonHitbox hitbox = collisionComponent.getPolygonHitbox();
                DrawablePolygonComponent drawablePolygonComponent = (DrawablePolygonComponent)collisionComponent.getGameObject().getComponent("drawablePoly");
                hitbox.setPoints(drawablePolygonComponent.getPoints());
            }
        }
        if (gravityComponent != null) {
            if (gravityComponent.getHitboxType() == HitboxType.AAB) {
                Vec2d newHitboxPosition = new Vec2d(gravityComponent.getAabHitbox().getTopLeft().x + deltaX, gravityComponent.getAabHitbox().getTopLeft().y + deltaY);

                gravityComponent.getAabHitbox().setTopLeft(newHitboxPosition);
            }
        }
    }

    public Vec2d getSizeOnWorld() {
        return sizeOnWorld;
    }

    public void setSizeOnWorld(Vec2d size) {
        this.sizeOnWorld = size;
    }

    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public boolean isMouseHovering() {
        return mouseHovering;
    }

    public void setMouseHovering(boolean mouseHovering) {
        this.mouseHovering = mouseHovering;
    }

    public boolean isMouseGrabbing() {
        return mouseGrabbing;
    }

    public void setMouseGrabbing(boolean mouseGrabbing) {
        this.mouseGrabbing = mouseGrabbing;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
