package triage.generators;

import engine.components.TextComponent;
import engine.components.TransformComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import triage.GameState;

public class TextGenerator {
    GameState currentGameState;
    public TextGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(GameObjectId textId, Vec2d textPosition, Font font, double fontSize, String text, Color color) {
        GameObject textObject = new GameObject(
                textId.toString(),
                new TransformComponent(textPosition, new Vec2d(fontSize,fontSize)));

        // 0 gets drawn last so always stays on top
        textObject.setzIndex(0);

        TextComponent textComponent = new TextComponent(
                "textComponent",
                textObject, text,
                font,
                color,
                new Vec2d(textPosition.x, textPosition.y), // This is weird because the text is drawn on top of origin not below
                new Vec2d(0, 0));

        textObject.addComponent(textComponent);
        return textObject;
    }
}
