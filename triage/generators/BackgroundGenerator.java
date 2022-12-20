package triage.generators;

import engine.components.SpriteComponent;
import engine.components.TransformComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import triage.GameState;
import triage.blueprints.SpriteSheetId;
import triage.generators.ObjectIds.GameObjectId;

public class BackgroundGenerator {

    GameState currentGameState;
    public BackgroundGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(SpriteSheetId backgroundSpriteId) {

        // Because the origin point is (0,0) and end corner point is (width, height)
        GameObject backGroundObject = new GameObject(
                GameObjectId.BACKGROUND.toString(),
                new TransformComponent(new Vec2d(0, 0),
                        currentGameState.getScreenSize()));

        // This is creating the sprite for the game object
        SpriteComponent backgroundSprite =
                new SpriteComponent(
                        backGroundObject,
                        currentGameState
                        .getGameAssets()
                        .getGameResource()
                        .getSpriteSheet(backgroundSpriteId.toString())
                        , new Vec2d(0, 0)
                        , currentGameState.getScreenSize());

        // Adding the sprite to the game object
        backGroundObject.addComponent(backgroundSprite);

        return backGroundObject;
    }
}
