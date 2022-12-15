package triage.blueprints;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import triage.GameState;
import triage.generators.*;

public class GameWorldBluePrint {

    GameState currentGameState;

    public GameWorldBluePrint(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public void populateMenuScreen() {
        loadBackground(SpriteSheetId.BACKGROUND_GALAXY);
        loadButton();
    }
    public void populateFirstLevelScreen() {
        loadBackground(SpriteSheetId.BACKGROUND_SPACE);
        loadPlatformsLevelOne();
        Vec2d playerPositionOnWorld = new Vec2d(200,308);
        loadPlayer(playerPositionOnWorld);
    }

    public void loadBackground(SpriteSheetId backgroundSpriteId) {
        GameObject backgroundObject = new BackgroundGenerator(this.currentGameState).generate(backgroundSpriteId);
        currentGameState.getGameWorld().addGameObject(backgroundObject);
    }

    public void loadPlatformsLevelOne() {
        GameObject hiddenHitboxForFirstPlatform = new HiddenRectangleHitboxGenerator
                (this.currentGameState).generate(
                new Vec2d(53, 337),
                new Vec2d(297, 50)
        );
        currentGameState.getGameWorld().addGameObject(hiddenHitboxForFirstPlatform);

        GameObject hiddenHitboxForFirstPlatform2 = new HiddenRectangleHitboxGenerator
                (this.currentGameState).generate(
                new Vec2d(413, 352),
                new Vec2d(256, 50)
        );
        currentGameState.getGameWorld().addGameObject(hiddenHitboxForFirstPlatform2);

        GameObject hiddenHitboxForFirstPlatform3 = new HiddenRectangleHitboxGenerator
                (this.currentGameState).generate(
                new Vec2d(745, 302),
                new Vec2d(150, 50)
        );
        currentGameState.getGameWorld().addGameObject(hiddenHitboxForFirstPlatform3);
    }

    public void loadPlayer(Vec2d playerPositionOnWorld) {
        GameObject player = new PlayerGenerator(this.currentGameState).generate(playerPositionOnWorld);

        currentGameState.getGameWorld().addGameObject(player);

    }

    public void loadButton() {
        GameObject startButton = new ButtonGenerator(this.currentGameState)
                .generate(GameObjectId.START_BUTTON,
                        new Vec2d(400,400),
                        new Vec2d(60, 30),
                        "Start",
                        30);
        currentGameState.getGameWorld().addGameObject(startButton);
    }
}
