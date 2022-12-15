package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import triage.GameState;
import triage.blueprints.SpriteSheetId;

public class HiddenRectangleHitboxGenerator {
    GameState currentGameState;
    public HiddenRectangleHitboxGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(Vec2d positionInWorld, Vec2d size) {

        // Because the origin point is (0,0) and end corner point is (width, height)
        GameObject hiddenRectangleHitboxObject = new GameObject(
                GameObjectId.HIDDEN_RECTANGLE_HITBOX.toString(),
                new TransformComponent(positionInWorld, size));

        DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(hiddenRectangleHitboxObject);
        hiddenRectangleHitboxObject.setDefaultColor(Color.RED);

        AABHitbox hitbox = new AABHitbox(positionInWorld, size);
        CollisionComponent collisionComponent = new CollisionComponent(hiddenRectangleHitboxObject, hitbox);
        collisionComponent.setCollisionBehaviour(CollisionBehaviour.STATIC);

        hiddenRectangleHitboxObject.addComponent(drawableComponent);
        hiddenRectangleHitboxObject.addComponent(collisionComponent);

        return hiddenRectangleHitboxObject;
    }
}
