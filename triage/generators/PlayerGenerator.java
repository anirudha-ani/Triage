package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.support.Vec2d;
import triage.GameState;
import triage.blueprints.SpriteSheetId;
import triage.generators.ObjectIds.GameObjectId;

public class PlayerGenerator {

    GameState currentGameState;
    public PlayerGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(Vec2d positionInWorld) {

        GameObject player = new GameObject(
                GameObjectId.player.toString(),
                new TransformComponent( new Vec2d(positionInWorld.x+10, positionInWorld.y+10), new Vec2d(32 - 20, 32 - 15)));

        player.setzIndex(0);
//        player.setDefaultColor(Color.RED);
//        player.setHoverColor(Color.ORANGE);

//        DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(player);

        StatsComponent statsComponent = new StatsComponent(100, 100);

        RayComponent rayComponent = new RayComponent(player);
        rayComponent.setPositionOnWorld(player.getTransformComponent().getPositionOnWorld());

        // SpriteId "left" and "right" is a legacy id, don't rename it. It will break some engine side code
        SpriteComponent spriteComponent1 = new SpriteComponent(
                "left",
                player,
                currentGameState
                        .getGameAssets()
                        .getGameResource()
                        .getSpriteSheet(SpriteSheetId.PLAYER_LEFT.toString()),
                positionInWorld,
                new Vec2d(32, 32));

        SpriteComponent spriteComponent2 =
                new SpriteComponent(
                        "right",
                        player,
                        currentGameState
                                .getGameAssets()
                                .getGameResource()
                                .getSpriteSheet(SpriteSheetId.PLAYER_RIGHT.toString()),
                        positionInWorld,
                        new Vec2d(32, 32));

        PhysicsComponent physicsComponent = new PhysicsComponent(player, 10);

        AABHitbox hitbox = new AABHitbox(new Vec2d(positionInWorld.x+10, positionInWorld.y+10), new Vec2d(32 - 20, 32 - 15));
        CollisionComponent collisionComponent = new CollisionComponent(player, hitbox);

        AABHitbox gravityHitbox = new AABHitbox(new Vec2d(positionInWorld.x+10, positionInWorld.y+10+32-15+1), new Vec2d(32 - 20, 3));
        CollisionComponent gravityCollisionComponent = new CollisionComponent(player, gravityHitbox);
        gravityCollisionComponent.setTag("gravity");

        player.setStatus("idle");

//        player.addComponent(drawableComponent);
        player.addComponent(rayComponent);
        player.addComponent(spriteComponent1);
        player.addComponent(spriteComponent2);
        player.addComponent(physicsComponent);
        player.addComponent(collisionComponent);
        player.addComponent(gravityCollisionComponent);
        player.addComponent(statsComponent);

        return player;
    }
}
