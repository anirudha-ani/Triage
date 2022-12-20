package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.support.Vec2d;
import triage.GameState;
import javafx.scene.paint.Color;
import triage.blueprints.SpriteSheetId;

public class AirSentryGenerator {

    GameState currentGameState;
    public AirSentryGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(Vec2d positionInWorld) {

        GameObject sentry = new GameObject(
                GameObjectId.AIR_SENTRY.toString(),
                new TransformComponent( new Vec2d(positionInWorld.x+10, positionInWorld.y+10), new Vec2d(32 - 20, 32 - 15)));

        sentry.setzIndex(0);
        sentry.setDefaultColor(Color.RED);
        sentry.setHoverColor(Color.ORANGE);

        DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(sentry);

        PhysicsComponent physicsComponent = new PhysicsComponent(sentry, 5);
        physicsComponent.setVel(new Vec2d(.5,0));
        physicsComponent.setFrictionActivated(false);
        physicsComponent.setGravityActivated(false);

        AABHitbox hitbox = new AABHitbox(new Vec2d(positionInWorld.x+10, positionInWorld.y+10), new Vec2d(32 - 20, 32 - 15));
        CollisionComponent collisionComponent = new CollisionComponent(sentry, hitbox);

        AIComponent aiComponent = new AIComponent(sentry);

        sentry.setStatus("idle");

        sentry.addComponent(drawableComponent);
        sentry.addComponent(aiComponent);
        sentry.addComponent(physicsComponent);
        sentry.addComponent(collisionComponent);

        return sentry;
    }
}

