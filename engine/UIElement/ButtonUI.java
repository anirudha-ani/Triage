package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ButtonUI {
    Vec2d buttonOrigin;
    Vec2d buttonShape;
    double buttonFontSize;
    Color backGroundColor = Color.AQUAMARINE;
    Color fontColor = Color.CADETBLUE;
    String content = "Button";

    public ButtonUI(Vec2d buttonOrigin, Vec2d buttonShape, double buttonFontSize) {
        this.buttonOrigin = buttonOrigin;
        this.buttonShape = buttonShape;
        this.buttonFontSize = buttonFontSize;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void drawElement(GraphicsContext g) {

        RectangleUI buttonArea = new RectangleUI(this.buttonOrigin, this.buttonShape);
        buttonArea.setBackGroundColor(this.backGroundColor);
        buttonArea.drawElement(g);

        TextUI textUI = new TextUI();
        textUI.setOrigin(this.buttonOrigin);
        textUI.setOffset(new Vec2d(this.buttonShape.x * 0.19, this.buttonShape.y - (this.buttonShape.y - this.buttonFontSize) / 2));
        textUI.setContent(this.content);
        textUI.setFontSize(this.buttonFontSize);
        textUI.setFontColor(this.fontColor);
        textUI.drawElement(g);
    }
}
