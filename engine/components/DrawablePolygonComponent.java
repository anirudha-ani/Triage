package engine.components;

import engine.UIElement.PolygonUI;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class DrawablePolygonComponent extends DrawableComponent {
    Vec2d[] points;

    public DrawablePolygonComponent(GameObject gameObject, Vec2d[] points) {
        super("drawablePoly", gameObject);
        this.points = points;
    }

    @Override
    public void setPositionOnWorld(Vec2d positionOnWorld) {

        double deltaX = positionOnWorld.x - this.positionOnWorld.x;
        double deltaY = positionOnWorld.y - this.positionOnWorld.y;

        for (int i = 0; i < points.length; i++) {
            points[i] = points[i].plus((float) deltaX, (float) deltaY);
        }
        this.positionOnWorld = positionOnWorld;
    }

    @Override
    public void draw(GraphicsContext g) {
        drawPoly(g);
    }

    public void drawPoly(GraphicsContext g) {
        PolygonUI poly = new PolygonUI(points);
        poly.setBackGroundColor(getGameObject().getTransformComponent().getBackGroundColor());
        poly.drawElement(g);
    }

    public Vec2d[] getPoints() {
        return points;
    }
}
