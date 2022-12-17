package triage.blueprints;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import triage.GameState;
import triage.generators.*;

public class GameWorldBluePrint {

    GameState currentGameState;

    int coinCount = 0;

    public GameWorldBluePrint(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public void populateMenuScreen() {
        loadBackground(SpriteSheetId.BACKGROUND_GALAXY);
        loadCoinText(String.valueOf(coinCount),50,new Vec2d(currentGameState.getScreenSize().x-120,55), Color.WHITE);
        loadCoinImage(new Vec2d(40,40), new Vec2d(currentGameState.getScreenSize().x-80,17));
        loadStartButton();
        loadSaveButton();
        loadSettingsButton();
        loadShoppingButton();


    }

    public void populateCartScreen() {
        loadBackground(SpriteSheetId.BACKGROUND_GALAXY);
        loadCart(SpriteSheetId.CART_BG,new Vec2d(180,10));

        loadCoinText("200",12,new Vec2d(615,220), Color.BLACK);
        loadCoinImage(new Vec2d(10,10),new Vec2d(645,210));

        loadCoinText("600",12,new Vec2d(615,432), Color.BLACK);
        loadCoinImage(new Vec2d(10,10),new Vec2d(645,422));

        loadCoinText("400",12,new Vec2d(310,432), Color.BLACK);
        loadCoinImage(new Vec2d(10,10),new Vec2d(340,422));

        loadUseButton(new Vec2d(280,250),true);
        loadUseButton(new Vec2d(570,250),false);
        loadUseButton(new Vec2d(570,460),false);
        loadUseButton(new Vec2d(270,460),false);
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

    public void loadCart(SpriteSheetId backgroundSpriteId, Vec2d playerPositionOnWorld) {
        GameObject backgroundObject = new ImageGenerator(this.currentGameState).generate(backgroundSpriteId,new Vec2d(600,500),new Vec2d(190,20));
        GameObject samuraiObject =  new CharacterPreviewGenerator(this.currentGameState).generate(playerPositionOnWorld, GameObjectId.HUNTRESS,SpriteSheetId.HUNTRESS, new Vec2d(300,300), new Vec2d(280,110));
        GameObject huntressObject =  new CharacterPreviewGenerator(this.currentGameState).generate(playerPositionOnWorld.plus(300,20),GameObjectId.SAMURAI,SpriteSheetId.SAMURAI, new Vec2d(300,280),new Vec2d(580,110));
        GameObject wizardObject =  new CharacterPreviewGenerator(this.currentGameState).generate(playerPositionOnWorld.plus(50,280),GameObjectId.WIZARD,SpriteSheetId.WIZARD, new Vec2d(210,170),new Vec2d(280,320));
        GameObject warriorObject =  new CharacterPreviewGenerator(this.currentGameState).generate(playerPositionOnWorld.plus(310,230),GameObjectId.WARRIOR,SpriteSheetId.WARRIOR, new Vec2d(280,280),new Vec2d(580,320));
        GameObject cartText = new TextGenerator(this.currentGameState)
                .generate(GameObjectId.TEXT,
                        new Vec2d(340,60),
                        Font.loadFont(getClass().getResourceAsStream("../fonts/Digitaltech-rm0K.otf"),35),35,"Unlock Character", Color.rgb(45,11,37)
                );



        currentGameState.getGameWorld().addGameObject(backgroundObject);
        currentGameState.getGameWorld().addGameObject(samuraiObject);
        currentGameState.getGameWorld().addGameObject(huntressObject);
        currentGameState.getGameWorld().addGameObject(wizardObject);
        currentGameState.getGameWorld().addGameObject(warriorObject);
        currentGameState.getGameWorld().addGameObject(cartText);
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
        GameObject loadButton = new ButtonGenerator(this.currentGameState)
                .generate(GameObjectId.START_BUTTON,
                        new Vec2d(currentGameState.getScreenSize().x/2.37,currentGameState.getScreenSize().y/2.4),
                        new Vec2d(currentGameState.getScreenSize().x/2.37+15,currentGameState.getScreenSize().y/2.4+35),
                        new Vec2d(170, 50),
                        "Start Game",
                        28,
                        Color.rgb(204,69,66),
                        Color.ORANGE,
                        Color.BLACK);
        currentGameState.getGameWorld().addGameObject(loadButton);
    }

    public void loadSaveButton() {
        GameObject saveButton = new ButtonGenerator(this.currentGameState)
                .generate(GameObjectId.LOAD_BUTTON,
                        new Vec2d(currentGameState.getScreenSize().x/2.33,currentGameState.getScreenSize().y/1.9),
                        new Vec2d(currentGameState.getScreenSize().x/2.33+15,currentGameState.getScreenSize().y/1.9+35),
                        new Vec2d(160, 50),
                        "Load Game",
                        28,
                        Color.rgb(204,69,66),
                        Color.ORANGE,
                        Color.BLACK);
        currentGameState.getGameWorld().addGameObject(saveButton);
    }

    public void loadSettingsButton() {
        GameObject settingsButton = new ButtonGenerator(this.currentGameState)
                .generate(GameObjectId.SETTINGS_BUTTON,
                        new Vec2d(currentGameState.getScreenSize().x/2.28,currentGameState.getScreenSize().y/1.58),
                        new Vec2d(currentGameState.getScreenSize().x/2.28+15,currentGameState.getScreenSize().y/1.58+35),
                        new Vec2d(140, 50),
                        "Settings",
                        28,
                        Color.rgb(204,69,66),
                        Color.ORANGE,
                        Color.BLACK);
        currentGameState.getGameWorld().addGameObject(settingsButton);
    }

    public void loadShoppingButton(){
        GameObject shopButton = new IconButtonGenerator(this.currentGameState)
                .generate(
                        GameObjectId.SHOPPING_BUTTON,
                        new Vec2d(50,20),
                        new Vec2d(50,50),
                        SpriteSheetId.SHOPPING_CART,
                        new Vec2d(35,35),
                        new Vec2d(57,28)
                );
        currentGameState.getGameWorld().addGameObject(shopButton);
    }

    public void loadUseButton(Vec2d buttonPosition, boolean boughtCharacter){
        if(boughtCharacter) {
            GameObject useButton = new ButtonGenerator(this.currentGameState)
                    .generate(GameObjectId.USE_BUTTON,
                            buttonPosition,
                            buttonPosition.plus(30, 25),
                            new Vec2d(100,40),
                            "USE",
                            20,
                            Color.rgb(45, 11, 37),
                            Color.ORANGE,
                            Color.WHITE);
            currentGameState.getGameWorld().addGameObject(useButton);
        }
        else{
            GameObject useButton = new ButtonGenerator(this.currentGameState)
                    .generate(GameObjectId.UNLOCK_BUTTON,
                            buttonPosition,
                            buttonPosition.plus(30, 25),
                            new Vec2d(125,40),
                            "UNLOCK",
                            20,
                            Color.rgb(45, 11, 37),
                            Color.ORANGE,
                            Color.WHITE);
            currentGameState.getGameWorld().addGameObject(useButton);
        }

    }



    public void loadCoinText(String coinCountText, double fontSize, Vec2d textPosition, Color color){
        GameObject coinText = new TextGenerator(this.currentGameState)
                .generate(GameObjectId.TEXT,
                        textPosition,
                        Font.loadFont(getClass().getResourceAsStream("../fonts/Bungee-Regular.ttf"),fontSize),fontSize,coinCountText, color
                        );

        currentGameState.getGameWorld().addGameObject(coinText);
    }

    public void loadCoinImage(Vec2d coinSize,Vec2d coinPosition){
        GameObject coinImage = new ImageGenerator(this.currentGameState)
                .generate(
                        SpriteSheetId.COIN_IMAGE,
                        coinSize,
                        coinPosition
                );

        currentGameState.getGameWorld().addGameObject(coinImage);
    }
}
