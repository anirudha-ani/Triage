package engine.components;

import engine.UIElement.RectangleUI;
import engine.gameobjects.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class DrawableRectangleComponent extends DrawableComponent {
    public DrawableRectangleComponent(GameObject gameObject) {
        super("drawableRect", gameObject);
    }

    @Override
    public void draw(GraphicsContext g) {
        drawRectangle(g);
    }

    public void drawRectangle(GraphicsContext g) {
        RectangleUI rect1 = new RectangleUI(getGameObject().getTransformComponent().getPositionOnWorld(), getGameObject().getTransformComponent().getSizeOnWorld());
        rect1.setBackGroundColor(getGameObject().getTransformComponent().getBackGroundColor());
        rect1.drawElement(g);
    }
}
