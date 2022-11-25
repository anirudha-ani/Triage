package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PolygonUI {
    double[] xPoints;
    double[] yPoints;
    int nPoints;
    private Color backGroundColor = Color.RED;

    private boolean isStroke = false;

    public PolygonUI(Vec2d[] points) {
        nPoints = points.length;
        xPoints = new double[nPoints];
        yPoints = new double[nPoints];

        for (int i = 0; i < nPoints; i++) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
        }
    }

    public PolygonUI(Vec2d[] points, Color backGroundColor) {
        nPoints = points.length;
        xPoints = new double[nPoints];
        yPoints = new double[nPoints];

        for (int i = 0; i < nPoints; i++) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
        }
        this.backGroundColor = backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void setStoke(boolean stroke) {
        isStroke = stroke;
    }

    public void drawElement(GraphicsContext g) {
        g.setFill(this.backGroundColor);

        if (isStroke) {
            g.strokePolygon(xPoints, yPoints, nPoints);
        } else {
            g.fillPolygon(xPoints, yPoints, nPoints);
        }
    }
}
