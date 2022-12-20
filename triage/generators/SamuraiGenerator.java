package triage.generators;

import engine.components.*;
import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import triage.GameState;
import triage.blueprints.SpriteSheetId;
import triage.generators.ObjectIds.GameObjectId;

public class SamuraiGenerator {

    GameState currentGameState;

    public SamuraiGenerator(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameObject generate(Vec2d positionInWorld) {

        GameObject player = new GameObject(GameObjectId.player.toString(), new TransformComponent(new Vec2d(positionInWorld.x + 10, positionInWorld.y + 10), new Vec2d(30 - 20, 30 - 15)));

        player.setzIndex(0);
        //TODO: Comment out the drawable thing to remove bounding box.
        player.setDefaultColor(Color.RED);
        player.setHoverColor(Color.ORANGE);

        DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(player);

        StatsComponent statsComponent = new StatsComponent(100, 100);
        statsComponent.setFacing("right");

        RayComponent rayComponent = new RayComponent(player);
        rayComponent.setPositionOnWorld(player.getTransformComponent().getPositionOnWorld());

        // SpriteId "left" and "right" is a legacy id, don't rename it. It will break some engine side code

        SpriteComponent runningLeftSprite = new SpriteComponent("left", player, currentGameState.getGameAssets().getGameResource().getSpriteSheet(SpriteSheetId.SAMURAI_LEFT.toString()),
//                            positionInWorld,
                // Sprite position and object position might not be same all the time.
                new Vec2d(positionInWorld.x - 50, positionInWorld.y - 50), new Vec2d(125, 125));
        runningLeftSprite.setVisibile(false);
        SpriteComponent runningRightSprite = new SpriteComponent("right", player, currentGameState.getGameAssets().getGameResource().getSpriteSheet(SpriteSheetId.SAMURAI_RIGHT.toString()),
//                            positionInWorld,
                // Sprite position and object position might not be same all the time.
                new Vec2d(positionInWorld.x - 50, positionInWorld.y - 50), new Vec2d(125, 125));
        runningRightSprite.setVisibile(true);

        SpriteComponent attackRightSprite = new SpriteComponent("attackRight", player, currentGameState.getGameAssets().getGameResource().getSpriteSheet(SpriteSheetId.SAMURAI_ATTACK.toString()),
//                            positionInWorld,
                // Sprite position and object position might not be same all the time.
                new Vec2d(positionInWorld.x - 50, positionInWorld.y - 50), new Vec2d(125, 125));
        attackRightSprite.setVisibile(false);
        SpriteComponent attackLeftSprite = new SpriteComponent("attackLeft", player, currentGameState.getGameAssets().getGameResource().getSpriteSheet(SpriteSheetId.SAMURAI_ATTACK_LEFT.toString()),
//                            positionInWorld,
                // Sprite position and object position might not be same all the time.
                new Vec2d(positionInWorld.x - 50, positionInWorld.y - 50), new Vec2d(125, 125));
        attackLeftSprite.setVisibile(false);
        PhysicsComponent physicsComponent = new PhysicsComponent(player, 10);

        AABHitbox hitbox = new AABHitbox(new Vec2d(positionInWorld.x + 10, positionInWorld.y + 10), new Vec2d(30 - 20, 30 - 15));
        CollisionComponent collisionComponent = new CollisionComponent(player, hitbox);

        AABHitbox gravityHitbox = new AABHitbox(new Vec2d(positionInWorld.x + 10, positionInWorld.y + 10 + 30 - 15 + 1), new Vec2d(30 - 20, 3));
        CollisionComponent gravityCollisionComponent = new CollisionComponent(player, gravityHitbox);
        gravityCollisionComponent.setTag("gravity");

        player.setStatus("idle");

        player.addComponent(drawableComponent);
        player.addComponent(rayComponent);
        //player.addComponent(spriteComponent3);

        player.addComponent(runningRightSprite);
        //player.addComponent(spriteComponent5);
        player.addComponent(attackLeftSprite);
        player.addComponent(runningLeftSprite);
        player.addComponent(attackRightSprite);
        player.addComponent(physicsComponent);
        player.addComponent(collisionComponent);
        player.addComponent(gravityCollisionComponent);
        player.addComponent(statsComponent);

        return player;
    }
}

