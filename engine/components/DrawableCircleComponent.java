package engine.components;

import engine.UIElement.CircleUI;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class DrawableCircleComponent extends DrawableComponent {
    Vec2d center;
    float radius;

    public DrawableCircleComponent(GameObject gameObject) {
        super("drawableCircle", gameObject);
        radius = (float) Math.min(getGameObject().getTransformComponent().getSizeOnWorld().x, getGameObject().getTransformComponent().getSizeOnWorld().y) / 2;
        center = getGameObject().getTransformComponent().getPositionOnWorld().plus(radius, radius);
    }

    @Override
    public void draw(GraphicsContext g) {
        drawCircle(g);
    }

    public void drawCircle(GraphicsContext g) {
        radius = (float) Math.min(getGameObject().getTransformComponent().getSizeOnWorld().x, getGameObject().getTransformComponent().getSizeOnWorld().y) / 2;
        center = getGameObject().getTransformComponent().getPositionOnWorld().plus(radius, radius);

        CircleUI circle = new CircleUI(center, radius);
        circle.setBackGroundColor(getGameObject().getTransformComponent().getBackGroundColor());
        circle.drawElement(g);
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
