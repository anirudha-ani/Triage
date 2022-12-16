package engine.components;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextComponent extends Component {
//    private String fontName = "Impact";
//    private double fontSize = 30;

    private Font font;
    private Color fontColor = Color.BLACK;
    private Vec2d origin = new Vec2d(0, 0);
    private String content = "";
    private Vec2d offset = new Vec2d(0, 0);


    public TextComponent(String tag, GameObject gameObject, String content, Font font, Color fontColor, Vec2d origin, Vec2d padding) {
        super(tag, gameObject);
        this.fontColor = fontColor;
        this.origin = origin;
        this.content = content;
        this.offset = padding;
        this.font = font;
    }

    public Font getFont() {
        return font;
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

    @Override
    public void draw(GraphicsContext g) {
        g.setFont(font);
        g.setFill(this.fontColor);
        g.fillText(this.content, this.origin.x + this.offset.x, this.origin.y + this.offset.y);
    }
}
