package triage.generators;

import engine.components.SpriteComponent;
import engine.components.TransformComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import triage.GameState;
import triage.blueprints.SpriteSheetId;

public class ImageGenerator {

    GameState currentGameState;

    public ImageGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(SpriteSheetId imageSpriteId, Vec2d imageSize, Vec2d imagePosition) {
        GameObject imageObject = new GameObject(
                GameObjectId.IMAGE.toString(),
                new TransformComponent(imagePosition,
                        imageSize));

        imageObject.setzIndex(0);

        SpriteComponent imageSprite =
                new SpriteComponent(
                        imageObject,
                        currentGameState
                                .getGameAssets()
                                .getGameResource()
                                .getSpriteSheet(imageSpriteId.toString())
                        , imagePosition
                        , imageSize);

        imageObject.addComponent(imageSprite);

        return imageObject;

    }

}