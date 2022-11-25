package engine.resources;

import javafx.scene.image.Image;

public class SpriteSheet {
    private final Image spriteSheetImage;
    private final String spriteSheetId;
    private final int noOfSpritePerRow;
    private final int noOfSpritePerColumn;
    private int spriteWidth;
    private int spriteHeight;

    public SpriteSheet(String id, Image image, int noOfSpritePerRow, int noOfSpritePerColumn, int spriteWidth, int spriteHeight) {
        this.spriteSheetId = id;
        this.spriteSheetImage = image;
        this.noOfSpritePerColumn = noOfSpritePerColumn;
        this.noOfSpritePerRow = noOfSpritePerRow;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public Image getSpriteSheetImage() {
        return spriteSheetImage;
    }

    public String getSpriteSheetId() {
        return spriteSheetId;
    }

    public int getNoOfSpritePerRow() {
        return noOfSpritePerRow;
    }

    public int getNoOfSpritePerColumn() {
        return noOfSpritePerColumn;
    }
}
