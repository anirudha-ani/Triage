package engine.components;

import engine.UIElement.RectangleUI;
import engine.gameobjects.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class DrawableRectangleComponent extends DrawableComponent {

    private boolean hasRoundedCorners = false;
    public DrawableRectangleComponent(GameObject gameObject) {
        super("drawableRect", gameObject);
    }

    public DrawableRectangleComponent(GameObject gameObject, boolean hasRoundedCorners) {
        super("drawableRect", gameObject);
        this.hasRoundedCorners = hasRoundedCorners;
    }

    @Override
    public void draw(GraphicsContext g) {
        drawRectangle(g);
    }

    public void drawRectangle(GraphicsContext g) {
        RectangleUI rect1 = new RectangleUI(getGameObject().getTransformComponent().getPositionOnWorld(), getGameObject().getTransformComponent().getSizeOnWorld());
        rect1.setBackGroundColor(getGameObject().getTransformComponent().getBackGroundColor());
        if(hasRoundedCorners) {
            rect1.setArcHeight(30);
            rect1.setArcWidth(30);
        }
        rect1.setRoundedCorner(hasRoundedCorners);
        rect1.drawElement(g);
    }
}
