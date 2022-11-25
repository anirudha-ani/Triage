package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextUI {
    private String fontName = "Impact";
    private double fontSize = 30;
    private Color fontColor = Color.BLACK;
    private Vec2d origin = new Vec2d(0, 0);
    private String content = "";
    private Vec2d offset = new Vec2d(0, 0);

    public TextUI() {
    }

    public TextUI(String content, String fontName, double fontSize, Color fontColor, Vec2d origin, Vec2d padding) {
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.origin = origin;
        this.content = content;
        this.offset = padding;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void setOrigin(Vec2d origin) {
        this.origin = origin;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOffset(Vec2d offset) {
        this.offset = offset;
    }

    public void drawElement(GraphicsContext g) {
        g.setFont(new Font(this.fontName, this.fontSize));
        g.setFill(this.fontColor);
        g.fillText(this.content, this.origin.x + this.offset.x, this.origin.y + this.offset.y);
    }
}
