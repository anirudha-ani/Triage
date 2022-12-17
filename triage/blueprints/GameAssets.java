package triage.blueprints;

import engine.GameResource;
import engine.components.SpriteComponent;
import javafx.scene.image.Image;

public class GameAssets {
    private final GameResource gameResource;

    public GameAssets() {
        gameResource = new GameResource();
    }

    public GameAssets(GameResource gameResource) {
        this.gameResource = gameResource;
    }

    public void LoadResources() {
        // Image takes a path, requested height, width, preserve ratio and smoothing
        Image playerLeft = new Image("file:triage/spritesheets/player/ACharLeft.png", 48, 48, false, true);
        Image playerRight = new Image("file:triage/spritesheets/player/ACharRight.png", 48, 48, false, true);
        Image spaceBackground = new Image("file:triage/spritesheets/backgrounds/SpaceBackground.jpg", 6000, 3000, false, true);
        Image galaxyBackground = new Image("file:triage/spritesheets/backgrounds/newspace.png", 2048, 1153, false, true);
        Image coin = new Image("file:triage/spritesheets/coins/Gold/Gold_21.png", 563,564,false,true);
        Image cart = new Image("file:triage/spritesheets/shopping.png", 512,512,false,true);

        // Sprite height and width indicates individual sprite height and width in a sprite sheet
        gameResource.addSpriteSheet(SpriteSheetId.PLAYER_LEFT.toString(), playerLeft, 2, 2, 24, 24);
        gameResource.addSpriteSheet(SpriteSheetId.PLAYER_RIGHT.toString(), playerRight, 2, 2, 24, 24);
        gameResource.addSpriteSheet(SpriteSheetId.BACKGROUND_SPACE.toString(), spaceBackground,1, 1, 6000, 3375);
        gameResource.addSpriteSheet(SpriteSheetId.BACKGROUND_GALAXY.toString(), galaxyBackground,1, 1, 2000, 1125);
        gameResource.addSpriteSheet(SpriteSheetId.COIN_IMAGE.toString(),coin,1,1,563,564);
        gameResource.addSpriteSheet(SpriteSheetId.SHOPPING_CART.toString(),cart,1,1,512,512);
    }

    public GameResource getGameResource() {
        return gameResource;
    }
}
