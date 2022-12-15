package triage.generators;

import engine.components.DrawableRectangleComponent;
import engine.components.SpriteComponent;
import engine.components.TransformComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import triage.GameState;
import triage.blueprints.SpriteSheetId;

public class BackgrounGenerator {

    GameState currentGameState;
    public BackgrounGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate() {

        // Because the origin point is (0,0) and end corner point is (width, height)
        GameObject backGround = new GameObject(
                GameObjectId.BACKGROUND.toString(),
                new TransformComponent(new Vec2d(0, 0),
                        currentGameState.getScreenSize()));

        // This is creating the sprite for the game object
        SpriteComponent backgroundSprite =
                new SpriteComponent(
                        backGround,currentGameState
                        .getGameAssets()
                        .getGameResource()
                        .getSpriteSheet(SpriteSheetId.BACKGROUND_CITY.toString())
                        , new Vec2d(0, 0)
                        , currentGameState.getScreenSize());

        // Adding the sprite to the game object
        backGround.addComponent(backgroundSprite);

        return backGround;
    }
}
