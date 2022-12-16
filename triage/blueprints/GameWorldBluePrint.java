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
        loadStartButton();
        loadSaveButton();
        loadSettingsButton();
        loadCoinText();


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

    public void loadStartButton() {
        System.out.println(currentGameState.getScreenSize());
        GameObject startButton = new ButtonGenerator(this.currentGameState)
                .generate(GameObjectId.START_BUTTON,
                        new Vec2d(currentGameState.getScreenSize().x/2.37,currentGameState.getScreenSize().y/2.4),
                        new Vec2d(currentGameState.getScreenSize().x/2.37+10,currentGameState.getScreenSize().y/2.4+35),
                        new Vec2d(170, 50),
                        "Start Game",
                        25);
        currentGameState.getGameWorld().addGameObject(startButton);
    }

    public void loadSaveButton() {
        System.out.println(currentGameState.getScreenSize());
        GameObject startButton = new ButtonGenerator(this.currentGameState)
                .generate(GameObjectId.LOAD_BUTTON,
                        new Vec2d(currentGameState.getScreenSize().x/2.35,currentGameState.getScreenSize().y/1.9),
                        new Vec2d(currentGameState.getScreenSize().x/2.35+15,currentGameState.getScreenSize().y/1.9+35),
                        new Vec2d(160, 50),
                        "Load Game",
                        25);
        currentGameState.getGameWorld().addGameObject(startButton);
    }

    public void loadSettingsButton() {
        GameObject startButton = new ButtonGenerator(this.currentGameState)
                .generate(GameObjectId.SETTINGS_BUTTON,
                        new Vec2d(currentGameState.getScreenSize().x/2.285,currentGameState.getScreenSize().y/1.58),
                        new Vec2d(currentGameState.getScreenSize().x/2.285+10,currentGameState.getScreenSize().y/1.58+35),
                        new Vec2d(140, 50),
                        "Settings",
                        25);
        currentGameState.getGameWorld().addGameObject(startButton);
    }

    public void loadCoinText(){
        GameObject coinText = new TextGenerator(this.currentGameState)
                .generate(GameObjectId.COIN_COUNT,
                        new Vec2d(currentGameState.getScreenSize().x-10,currentGameState.getScreenSize().y+10),
                        30,"0"
                        );

        currentGameState.getGameWorld().addGameObject(coinText);
    }
}
