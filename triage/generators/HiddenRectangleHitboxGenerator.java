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

        GameObject hiddenRectangleHitboxObject = new GameObject(
                GameObjectId.HIDDEN_RECTANGLE_HITBOX.toString(),
                new TransformComponent(positionInWorld, size));

        // Keeping this just for visualization while debugging
        // TODO: remove it in the final version
        DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(hiddenRectangleHitboxObject);
        hiddenRectangleHitboxObject.setDefaultColor(Color.RED);

        // Hitboxes are a part of the collisionComponent
        // Collision behaviour static means it won't move after collision
        AABHitbox hitbox = new AABHitbox(positionInWorld, size);
        CollisionComponent collisionComponent = new CollisionComponent(hiddenRectangleHitboxObject, hitbox);
        collisionComponent.setCollisionBehaviour(CollisionBehaviour.STATIC);

        hiddenRectangleHitboxObject.addComponent(drawableComponent);
        hiddenRectangleHitboxObject.addComponent(collisionComponent);

        return hiddenRectangleHitboxObject;
    }
}
