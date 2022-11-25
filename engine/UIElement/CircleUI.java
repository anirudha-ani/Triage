package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleUI {

    private final Vec2d center;
    private final double radius;
    private Color backGroundColor = Color.RED;
    private double arcWidth = 30;
    private double arcHeight = 30;

    private boolean isStroke = false;
    private boolean isRoundedCorner = false;

    public CircleUI(Vec2d center, double radius) {
        this.center = center;
        this.radius = radius;
    }
    public CircleUI(Vec2d center, double radius, Color backGroundColor) {
        this.center = center;
        this.radius = radius;
        this.backGroundColor = backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void setArcWidth(double arcWidth) {
        this.arcWidth = arcWidth;
    }

    public void setArcHeight(double arcHeight) {
        this.arcHeight = arcHeight;
    }

    public void setStoke(boolean stroke) {
        isStroke = stroke;
    }

    public void drawElement(GraphicsContext g) {
        g.setFill(this.backGroundColor);

            if (isStroke) {
                g.strokeOval(center.x-radius, center.y-radius, radius * 2, radius * 2);
            } else {
                g.fillOval(center.x-radius, center.y-radius, radius * 2, radius * 2);
            }
    }
}
