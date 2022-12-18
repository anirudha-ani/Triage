package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import triage.GameState;
import triage.blueprints.SpriteSheetId;

public class IconButtonGenerator {
    GameState currentGameState;
    public IconButtonGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(GameObjectId buttonId, Vec2d buttonPosition, Vec2d buttonSize, SpriteSheetId iconSpriteId, Vec2d iconSize, Vec2d iconPosition) {

        GameObject button = new GameObject(
                buttonId.toString(),
                new TransformComponent(buttonPosition, buttonSize));

        button.setzIndex(0); // 0 gets drawn last so always stays on top

        DrawableRectangleComponent drawableBackgroundComponent = new DrawableRectangleComponent(button, true);
        button.setDefaultColor(Color.WHITESMOKE);
        button.setHoverColor(Color.rgb(204,69,66));
        button.setClickColor(Color.RED);

        SpriteComponent imageInsideButtonComponent = new SpriteComponent(
                button,
                currentGameState
                        .getGameAssets()
                        .getGameResource()
                        .getSpriteSheet(iconSpriteId.toString())
                , iconPosition
                , iconSize);

        ClickableComponent clickableComponent = new ClickableComponent(button);

        // Adding the sprite to the game object
        button.addComponent(drawableBackgroundComponent);
        button.addComponent(imageInsideButtonComponent);
        button.addComponent(clickableComponent);

        return button;
    }
}
