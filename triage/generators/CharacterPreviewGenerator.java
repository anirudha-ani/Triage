package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import triage.GameState;
import triage.blueprints.SpriteSheetId;
import triage.generators.ObjectIds.GameObjectId;

public class CharacterPreviewGenerator {

    GameState currentGameState;
    public CharacterPreviewGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(Vec2d positionInWorld, GameObjectId gameID, SpriteSheetId spriteId, Vec2d playerSize, Vec2d boxPosition) {

        GameObject player = new GameObject(
                gameID.toString(),
                new TransformComponent( new Vec2d(boxPosition.x, boxPosition.y), new Vec2d(100, 120)));

        player.setzIndex(0);

        DrawableRectangleComponent drawableBackgroundComponent = new DrawableRectangleComponent(player);
        player.setDefaultColor(Color.WHITESMOKE);

        // SpriteId "left" and "right" is a legacy id, don't rename it. It will break some engine side code
        SpriteComponent spriteComponent1 = new SpriteComponent(
                "left",
                player,
                currentGameState
                        .getGameAssets()
                        .getGameResource()
                        .getSpriteSheet(spriteId.toString()),
                positionInWorld,
                playerSize);


        player.setStatus("left");

        player.addComponent(drawableBackgroundComponent);
        player.addComponent(spriteComponent1);

        return player;
    }
}
