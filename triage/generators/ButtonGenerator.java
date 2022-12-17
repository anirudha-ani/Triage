package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import triage.GameState;
import triage.blueprints.SpriteSheetId;

public class ButtonGenerator {
    GameState currentGameState;
    public ButtonGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(GameObjectId buttonId, Vec2d buttonPosition, Vec2d textPosition, Vec2d buttonSize, String buttonText, int fontSize, Color color, Color hoverColor, Color textColor) {

        GameObject button = new GameObject(
                buttonId.toString(),
                new TransformComponent(buttonPosition, buttonSize));

        button.setzIndex(0); // 0 gets drawn last so always stays on top

        DrawableRectangleComponent drawableBackgroundComponent = new DrawableRectangleComponent(button, true);
        button.setDefaultColor(color);
        button.setHoverColor(hoverColor);
        //button.setClickColor();

        TextComponent textInsideButtonComponent = new TextComponent(
                "textComponent",
                button, buttonText,
                Font.loadFont(getClass().getResourceAsStream("../fonts/Digitaltech-rm0K.otf"),fontSize),
                textColor,
                new Vec2d(textPosition.x, textPosition.y), // This is weird because the text is drawn on top of origin not below
                new Vec2d(0, 0));

        ClickableComponent clickableComponent = new ClickableComponent(button);

        // Adding the sprite to the game object
        button.addComponent(drawableBackgroundComponent);
        button.addComponent(textInsideButtonComponent);
        button.addComponent(clickableComponent);

        return button;
    }
}
