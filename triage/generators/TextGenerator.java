package triage.generators;

import engine.components.TextComponent;
import engine.components.TransformComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import triage.GameState;

public class TextGenerator {
    GameState currentGameState;
    public TextGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(GameObjectId textId, Vec2d textPosition, int fontSize, String coinText) {
        GameObject coinCoint = new GameObject(
                textId.toString(),
                new TransformComponent(textPosition, new Vec2d(fontSize,fontSize)));

        // 0 gets drawn last so always stays on top

        TextComponent textComponent = new TextComponent(
                "textComponent",
                coinCoint, coinText,
                "Courier New",
                fontSize,
                Color.WHITE,
                new Vec2d(textPosition.x, textPosition.y), // This is weird because the text is drawn on top of origin not below
                new Vec2d(0, 0));

        coinCoint.addComponent(textComponent);
        return coinCoint;
    }
}
