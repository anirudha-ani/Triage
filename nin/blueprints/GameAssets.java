package nin.blueprints;

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
        Image grass = new Image("file:nin/spritesheets/grass.png", 32, 32, false, true);
        Image rock = new Image("file:nin/spritesheets/rock.png", 32, 32, false, true);
        Image playerLeft = new Image("file:nin/spritesheets/ACharLeft.png", 48, 48, false, true);
        Image playerRight = new Image("file:nin/spritesheets/ACgarRight.png", 48, 48, false, true);
        Image portal = new Image("file:nin/spritesheets/portal.png", 800, 773, false, true);


        gameResource.addSpriteSheet("rock", rock, 1, 1, 32, 32);
        gameResource.addSpriteSheet("grass", grass, 1, 1, 32, 32);
        gameResource.addSpriteSheet("playerLeft", playerLeft, 2, 2, 24, 24);
        gameResource.addSpriteSheet("playerRight", playerRight, 2, 2, 24, 24);
        gameResource.addSpriteSheet("portal", portal, 1, 1, 800, 773);

    }

    public GameResource getGameResource() {
        return gameResource;
    }
}
