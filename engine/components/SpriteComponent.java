package engine.components;

import engine.gameobjects.GameObject;
import engine.resources.SpriteSheet;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class SpriteComponent extends Component {
    public final SpriteSheet spriteSheet;
    private Vec2d positionOnWorld;
    private Vec2d sizeOnWorld;


    public SpriteComponent(GameObject gameObject, SpriteSheet spriteSheet, Vec2d positionOnWorld, Vec2d sizeOnWorld) {
        super("spriteComponent", gameObject);
        this.spriteSheet = spriteSheet;
        this.positionOnWorld = positionOnWorld;
        this.sizeOnWorld = sizeOnWorld;
    }

    public SpriteComponent(String spriteId, GameObject gameObject, SpriteSheet spriteSheet, Vec2d positionOnWorld, Vec2d sizeOnWorld) {
        super(spriteId, gameObject);
        this.spriteSheet = spriteSheet;
        this.positionOnWorld = positionOnWorld;
        this.sizeOnWorld = sizeOnWorld;
    }

    @Override
    public Vec2d getPositionOnWorld() {
        return positionOnWorld;
    }

    @Override
    public void setPositionOnWorld(Vec2d positionOnWorld) {
        this.positionOnWorld = positionOnWorld;
    }

    public Vec2d getSizeOnWorld() {
        return sizeOnWorld;
    }

    public void setSizeOnWorld(Vec2d sizeOnWorld) {
        this.sizeOnWorld = sizeOnWorld;
    }

    public SpriteSheet getSpriteSheet(){
        return spriteSheet;
    }

    @Override
    public void setSpriteIndexToLoad(int spriteIndexToLoad) {
        this.spriteIndexToLoad = spriteIndexToLoad % (spriteSheet.getNoOfSpritePerRow() * spriteSheet.getNoOfSpritePerColumn());
    }

    @Override
    public void draw(GraphicsContext g) {
        int originRowIndex = (int) Math.floor(spriteIndexToLoad / spriteSheet.getNoOfSpritePerRow());
        int originColIndex = (int) Math.floor(spriteIndexToLoad % spriteSheet.getNoOfSpritePerRow());
//        System.out.println(this.getTag() + "++" + this.isVisibile());
        if (this.isVisibile()) {
            g.drawImage(
                    this.spriteSheet.getSpriteSheetImage(),
                    originColIndex * spriteSheet.getSpriteWidth(),
                    originRowIndex * spriteSheet.getSpriteHeight(),
                    spriteSheet.getSpriteWidth(),
                    spriteSheet.getSpriteHeight(),
                    positionOnWorld.x,
                    positionOnWorld.y,
                    sizeOnWorld.x,
                    sizeOnWorld.y);
        }

    }
}
