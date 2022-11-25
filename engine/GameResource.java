package engine;

import engine.resources.SpriteSheet;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class GameResource {
    private final ArrayList<SpriteSheet> availableSpriteSheets = new ArrayList<>();

    public void addSpriteSheet(String spriteSheetId, Image spriteSheetImage, int noOfSpritePerRow, int noOfSpritePerColumn, int spriteWidth, int spriteHeight) {
        availableSpriteSheets.add(new SpriteSheet(spriteSheetId, spriteSheetImage, noOfSpritePerRow, noOfSpritePerColumn, spriteWidth, spriteHeight));
    }

    public SpriteSheet getSpriteSheet(String spriteSheetId) {
        SpriteSheet targetSpriteSheet = null;
        for (int i = 0; i < availableSpriteSheets.size(); i++) {
            if (availableSpriteSheets.get(i).getSpriteSheetId() == spriteSheetId) {
                return availableSpriteSheets.get(i);
            }
        }

        return null;
    }
}
