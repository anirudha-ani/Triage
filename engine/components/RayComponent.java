package engine.components;

import engine.UIElement.LineUI;
import engine.gameobjects.GameObject;
import engine.hitboxes.Ray;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class RayComponent extends DrawableComponent {
    LineUI line = new LineUI(true);
    Ray ray = null;
    private boolean show;
    private Vec2d start = null;
    private Vec2d end = null;

    public RayComponent(GameObject gameObject) {
        super("ray", gameObject);
        show = false;
    }

    @Override
    public void draw(GraphicsContext g) {
        if (show && start != null && end != null) {
            drawRay(g);
        }
    }

    public void drawRay(GraphicsContext g) {
//        LineUI line = new LineUI();
        this.setStart(getPositionOnWorld());
        line.setStartPosition(getPositionOnWorld());
        Vec2d dir = this.end.minus(this.start).normalize();
        ray = new Ray(getPositionOnWorld(), dir);

        double length = getGameObject().getRefGameWorld().collisionSystem.castRay(ray);
//        System.out.println("start = "+ start+ "end == "+ end + " dir = " + dir + " length = " + length);
        Vec2d endPosition = ray.src.plus(ray.dir.smult(length));
        line.setEndPosition(endPosition);


        line.drawElement(g);
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public Vec2d getStart() {
        return start;
    }

    public void setStart(Vec2d start) {
        this.start = start;
    }

    public Vec2d getEnd() {
        return end;
    }

    public void setEnd(Vec2d end) {
        this.end = end;
    }

    public Ray getRay() {
        return ray;
    }

    public void setRay(Ray ray) {
        this.ray = ray;
    }
}
