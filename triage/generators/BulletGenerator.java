package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import triage.GameState;
import triage.blueprints.SpriteSheetId;
import triage.generators.ObjectIds.GameObjectId;

public class BulletGenerator {
    GameState currentGameState;

    public BulletGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(RayComponent rayComponent) {

        Vec2d projectilePos = rayComponent.getRay().dir.plus(5, 5).plus(rayComponent.getRay().src);

        GameObject bullet = new GameObject(GameObjectId.BULLET.toString(), new TransformComponent(projectilePos, new Vec2d(5, 5)));
        bullet.setzIndex(0);
        bullet.setDefaultColor(Color.RED);
        bullet.setHoverColor(Color.RED);

        SpriteComponent bulletSprite =
                new SpriteComponent(
                        bullet,
                        currentGameState
                                .getGameAssets()
                                .getGameResource()
                                .getSpriteSheet(SpriteSheetId.SHURIKEN.toString())
                        , projectilePos
                        ,  new Vec2d(10, 10));

        DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(bullet);

        PhysicsComponent physicsComponent = new PhysicsComponent(bullet, 1);
        physicsComponent.setGravityActivated(false);
        physicsComponent.setFrictionActivated(false);
        physicsComponent.setVelocityCapped(false);
        physicsComponent.setVel(rayComponent.getRay().dir.smult(10));
        AABHitbox hitbox = new AABHitbox(projectilePos, new Vec2d(10, 10));

        CollisionComponent collisionComponent5 = new CollisionComponent(bullet, hitbox);

        //bullet.addComponent(drawableComponent);
        bullet.addComponent(bulletSprite);
        bullet.addComponent(physicsComponent);
        bullet.addComponent(collisionComponent5);
        bullet.setStatus("idle");

        return bullet;

    }
}
