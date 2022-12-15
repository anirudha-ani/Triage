package triage.blueprints;

import engine.GameResource;
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
        Image cityBackground = new Image("file:triage/spritesheets/backgrounds/SpaceBackground.jpg", 6000, 3000, false, true);

        // Sprite height and width indicates individual sprite height and width in a sprite sheet
        gameResource.addSpriteSheet(SpriteSheetId.PLAYER_LEFT.toString(), playerLeft, 2, 2, 24, 24);
        gameResource.addSpriteSheet(SpriteSheetId.PLAYER_RIGHT.toString(), playerRight, 2, 2, 24, 24);
        gameResource.addSpriteSheet(SpriteSheetId.BACKGROUND_CITY.toString(), cityBackground,1, 1, 6000, 3375);
    }

    public GameResource getGameResource() {
        return gameResource;
    }
}
