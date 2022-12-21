package triage.blueprints;

import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import triage.GameState;
import triage.generators.*;
import triage.generators.ObjectIds.GameObjectId;

public class GameWorldBluePrint {

    GameState currentGameState;

    int coinCount = 0;

    public GameWorldBluePrint(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public void populateMenuScreen() {
        loadBackground(SpriteSheetId.BACKGROUND_GALAXY);
        double coinSpacing = 100;
        if (coinCount>10 && coinCount<100){
            coinSpacing = 120;
        }
        else if(coinCount>100){
            coinSpacing = 150;
        }

        loadCoinText(String.valueOf(coinCount),40,new Vec2d(currentGameState.getScreenSize().x-coinSpacing,48), Color.WHITE);
        loadCoinImage(new Vec2d(30,30), new Vec2d(currentGameState.getScreenSize().x-60,19));
        loadStartButton();
        loadSaveButton();
        loadSettingsButton();
        loadShoppingButton();


    }

    public void populateCartScreen() {
        loadBackground(SpriteSheetId.BACKGROUND_GALAXY);
        double coinSpacing = 100;
        if (coinCount>10 && coinCount<100){
            coinSpacing = 120;
        }
        else if(coinCount>100){
            coinSpacing = 150;
        }

        loadCoinText(String.valueOf(coinCount),40,new Vec2d(currentGameState.getScreenSize().x-coinSpacing,48), Color.WHITE);
        loadCoinImage(new Vec2d(30,30), new Vec2d(currentGameState.getScreenSize().x-60,19));

        loadCart(SpriteSheetId.CART_BG,new Vec2d(180,10));

        loadCoinText("200",12,new Vec2d(615,220), Color.BLACK);
        loadCoinImage(new Vec2d(10,10),new Vec2d(645,210));

        loadCoinText("600",12,new Vec2d(615,432), Color.BLACK);
        loadCoinImage(new Vec2d(10,10),new Vec2d(645,422));

        loadCoinText("400",12,new Vec2d(310,432), Color.BLACK);
        loadCoinImage(new Vec2d(10,10),new Vec2d(340,422));


        loadUseButton(new Vec2d(280,250),true,true);
        loadUseButton(new Vec2d(570,250),false, coinCount>=200);
        loadUseButton(new Vec2d(570,460),false, coinCount>=600);
        loadUseButton(new Vec2d(270,460),false, coinCount>=400);
    }

    public void populateFirstLevelScreen() {
        loadBackground(SpriteSheetId.BACKGROUND_SPACE);
        loadPlatformsLevelOne();
        Vec2d playerPositionOnWorld = new Vec2d(100,100);
        //loadPlayer(playerPositionOnWorld);
        loadSamurai(playerPositionOnWorld);
        // Vec2d playerPositionOnWorld = new Vec2d(200,308);
        // loadPlayer(playerPositionOnWorld);
        loadGroundSentryEnemy();
        loadPauseButton();
        loadExitButton();
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

    public void loadSamurai(Vec2d playerPositionOnWorld) {
        GameObject player = new SamuraiGenerator(this.currentGameState).generate(playerPositionOnWorld);

        currentGameState.getGameWorld().addGameObject(player);
    }

    public void loadGroundSentryEnemy() {
        Vec2d sentryPositionOnWorld1 = new Vec2d(450,320);
        GameObject groundSentry1 = new GroundSentryGenerator(this.currentGameState).generate(sentryPositionOnWorld1);
        currentGameState.getGameWorld().addGameObject(groundSentry1);

        Vec2d sentryPositionOnWorld2 = new Vec2d(800,270);
        GameObject groundSentry2 = new GroundSentryGenerator(this.currentGameState).generate(sentryPositionOnWorld2);
        currentGameState.getGameWorld().addGameObject(groundSentry2);

        Vec2d sentryPositionOnWorld3 = new Vec2d(50,100);
        GameObject airSentry1 = new AirSentryGenerator(this.currentGameState).generate(sentryPositionOnWorld3);
        currentGameState.getGameWorld().addGameObject(airSentry1);
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
                        Color.BLACK,
                        true
                        );
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
                        Color.BLACK,
                        true
                        );
        currentGameState.getGameWorld().addGameObject(saveButton);
    }

    public void loadSettingsButton() {
        GameObject settingsButton = new ButtonGenerator(this.currentGameState)
                .generate(GameObjectId.SETTINGS_BUTTON,
                        new Vec2d(currentGameState.getScreenSize().x/2.28,currentGameState.getScreenSize().y/1.58),
                        new Vec2d(currentGameState.getScreenSize().x/2.28+15,currentGameState.getScreenSize().y/1.58+35),
                        new Vec2d(140, 50),
                        "Instructions",
                        20,
                        Color.rgb(204,69,66),
                        Color.ORANGE,
                        Color.BLACK,
                        true
                        );
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
                        new Vec2d(57,28),
                        Color.WHITESMOKE,
                        Color.rgb(204,69,66)
                );
        currentGameState.getGameWorld().addGameObject(shopButton);
    }

    public void loadPauseButton(){
        GameObject pauseButton = new IconButtonGenerator(this.currentGameState)
                .generate(
                        GameObjectId.PAUSE_BUTTON,
                        new Vec2d(870,20),
                        new Vec2d(50,50),
                        SpriteSheetId.PAUSE,
                        new Vec2d(35,35),
                        new Vec2d(877,28),
                        Color.TRANSPARENT,
                        Color.WHITE
                );
        currentGameState.getGameWorld().addGameObject(pauseButton);
    }

    public void loadExitButton(){
        GameObject pauseButton = new IconButtonGenerator(this.currentGameState)
                .generate(
                        GameObjectId.EXIT_BUTTON,
                        new Vec2d(810,20),
                        new Vec2d(50,50),
                        SpriteSheetId.EXIT,
                        new Vec2d(35,35),
                        new Vec2d(817,28),
                        Color.TRANSPARENT,
                        Color.WHITE
                );
        currentGameState.getGameWorld().addGameObject(pauseButton);
    }



    public void loadUseButton(Vec2d buttonPosition, boolean boughtCharacter, boolean unlocked){
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
                            Color.WHITE,
                            true
                            );
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
                            unlocked?Color.rgb(45, 11, 37):Color.rgb(187, 191, 202),
                            unlocked?Color.ORANGE:Color.rgb(187, 191, 202),
                            unlocked?Color.WHITE:Color.rgb(73, 84, 100),
                            unlocked
                            );
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
