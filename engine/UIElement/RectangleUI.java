package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleUI {

    private final Vec2d origin;
    private final Vec2d widthHeight;
    private Color backGroundColor = Color.RED;
    private double arcWidth = 30;
    private double arcHeight = 30;

    private boolean isStroke = false;
    private boolean isRoundedCorner = false;

    public RectangleUI(Vec2d origin, Vec2d widthHeight) {
        this.origin = origin;
        this.widthHeight = widthHeight;
    }
    public RectangleUI(Vec2d origin, Vec2d widthHeight, Color backGroundColor) {
        this.origin = origin;
        this.widthHeight = widthHeight;
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

    public void setRoundedCorner(boolean roundedCorner) {
        isRoundedCorner = roundedCorner;
    }

    public void drawElement(GraphicsContext g) {
        g.setFill(this.backGroundColor);
        if (isRoundedCorner) {
            if (isStroke) {
                g.strokeRoundRect(this.origin.x, this.origin.y, this.widthHeight.x, this.widthHeight.y, this.arcWidth, this.arcWidth);
            } else {
                g.fillRoundRect(this.origin.x, this.origin.y, this.widthHeight.x, this.widthHeight.y, this.arcWidth, this.arcWidth);
            }
        } else {
            if (isStroke) {
                g.strokeRect(this.origin.x, this.origin.y, this.widthHeight.x, this.widthHeight.y);
            } else {
                g.fillRect(this.origin.x, this.origin.y, this.widthHeight.x, this.widthHeight.y);
            }

        }
    }
}
