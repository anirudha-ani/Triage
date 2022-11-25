package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineUI {
    private Vec2d startPosition;
    private Vec2d endPosition;
    private double lineWidth = 2;

    public void setStartPosition(Vec2d startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(Vec2d endPosition) {
        this.endPosition = endPosition;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void drawElement(GraphicsContext g) {
        g.setLineWidth(lineWidth);
        g.setStroke(Color.RED);
        g.strokeLine(startPosition.x, startPosition.y,endPosition.x, endPosition.y);
    }
}
