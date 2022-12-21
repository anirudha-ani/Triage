package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class LineUI {
    private Vec2d startPosition;
    private Vec2d endPosition;
    private double lineWidth = 2;

    private boolean dashed;

    public LineUI(boolean d){
        this.dashed = d;
    }

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
        if(dashed){
            g.setStroke(Color.YELLOW);
            g.setLineCap(StrokeLineCap.ROUND);
            g.setLineDashes(5);
        }
        else {
            g.setStroke(Color.RED);
        }
        g.strokeLine(startPosition.x, startPosition.y,endPosition.x, endPosition.y);
    }
}
