package triage.blueprints;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import triage.GameState;
import triage.generators.BackgroundGenerator;
import triage.generators.HiddenRectangleHitboxGenerator;
import triage.generators.PlayerGenerator;

public class GameWorldBluePrint {

    GameState currentGameState;

    public GameWorldBluePrint(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public void populateStartScreen() {
        loadBackground();
        loadPlatforms();
        loadPlayer();
    }

    public void loadBackground() {
        GameObject backgroundObject = new BackgroundGenerator(this.currentGameState).generate();
        currentGameState.getGameWorld().addGameObject(backgroundObject);
    }

    public void loadPlatforms() {
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

    public void loadPlayer() {
        GameObject player = new PlayerGenerator(this.currentGameState).generate(
                new Vec2d(200,308)
        );

        currentGameState.getGameWorld().addGameObject(player);

    }
}
