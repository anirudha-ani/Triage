package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.support.Vec2d;
import triage.GameState;
import javafx.scene.paint.Color;
import triage.blueprints.SpriteSheetId;
import triage.generators.ObjectIds.GameObjectId;

public class AirSentryGenerator {

    GameState currentGameState;
    public AirSentryGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(Vec2d positionInWorld) {

        GameObject sentry = new GameObject(
                GameObjectId.AIR_SENTRY.toString(),
                new TransformComponent( new Vec2d(positionInWorld.x, positionInWorld.y), new Vec2d(32, 32)));

        sentry.setzIndex(0);
//        sentry.setDefaultColor(Color.RED);
//        sentry.setHoverColor(Color.ORANGE);
//
//        DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(sentry);
//        sentry.addComponent(drawableComponent);

        StatsComponent statsComponent = new StatsComponent(50, 100);

        SpriteComponent spriteComponent1 =
                new SpriteComponent(
                        "right",
                        sentry,
                        currentGameState
                                .getGameAssets()
                                .getGameResource()
                                .getSpriteSheet(SpriteSheetId.UFO.toString()),
//                            positionInWorld,
                        // Sprite position and object position might not be same all the time.
                        new Vec2d(positionInWorld.x-20, positionInWorld.y-15),
                        new Vec2d(70, 70));

        PhysicsComponent physicsComponent = new PhysicsComponent(sentry, 5);
        physicsComponent.setVel(new Vec2d(.5,0));
        physicsComponent.setFrictionActivated(false);
        physicsComponent.setGravityActivated(false);

        AABHitbox hitbox = new AABHitbox(new Vec2d(positionInWorld.x, positionInWorld.y), new Vec2d(32, 32));
        CollisionComponent collisionComponent = new CollisionComponent(sentry, hitbox);

        AIComponent aiComponent = new AIComponent(sentry);

        sentry.setStatus("idle");


        sentry.addComponent(spriteComponent1);
        sentry.addComponent(aiComponent);
        sentry.addComponent(physicsComponent);
        sentry.addComponent(collisionComponent);
        sentry.addComponent(statsComponent);



        return sentry;
    }
}

