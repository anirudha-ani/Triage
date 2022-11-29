package nin.blueprints;

import engine.GameResource;
import engine.GameWorld;
import engine.components.*;
import engine.gameobjects.GameObject;
import engine.hitboxes.AABHitbox;
import engine.hitboxes.CircleHitbox;
import engine.hitboxes.PolygonHitbox;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameWorldBluePrint {

    private final GameWorld gameWorld;
    private final GameResource gameResource;

    public GameWorldBluePrint(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        gameResource = null;
    }

    public GameWorldBluePrint(GameWorld gameWorld, GameResource gameResource) {
        this.gameWorld = gameWorld;
        this.gameResource = gameResource;
    }

    public void PopulateGameWorld(ArrayList<String> map, String screenTitle, char[][] mapLayoutArray) {

        GameObject player = null;

        for (int i = 0; i < map.size(); i++) {
            String row = map.get(i);

            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '#') {
                    GameObject rock = new GameObject("rock", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    DrawableRectangleComponent drawableBackground = new DrawableRectangleComponent(rock);
                    rock.setDefaultColor(Color.TRANSPARENT);
                    rock.setHoverColor(Color.TRANSPARENT);
                    rock.setClickColor(Color.TRANSPARENT);
                    SpriteComponent backgroundSprite = new SpriteComponent(rock, gameResource.getSpriteSheet("rock"), new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    AABHitbox hitbox = new AABHitbox(new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    CollisionComponent collisionComponent = new CollisionComponent(rock, hitbox);
                    collisionComponent.setCollisionBehaviour(CollisionBehaviour.STATIC);
                    rock.addComponent(drawableBackground);
                    rock.addComponent(backgroundSprite);
                    rock.addComponent(collisionComponent);
                    gameWorld.addGameObject(rock);
                } else if (row.charAt(j) == '.') {
                    GameObject grass = new GameObject("grass", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    DrawableRectangleComponent drawableBackground = new DrawableRectangleComponent(grass);
                    grass.setDefaultColor(Color.TRANSPARENT);
                    grass.setHoverColor(Color.TRANSPARENT);
                    grass.setClickColor(Color.TRANSPARENT);
                    SpriteComponent backgroundSprite = new SpriteComponent(grass, gameResource.getSpriteSheet("grass"), new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    grass.addComponent(drawableBackground);
                    grass.addComponent(backgroundSprite);
                    gameWorld.addGameObject(grass);
                }

                if (row.charAt(j) == 'W') {
                    GameObject switchBlock = new GameObject("switchBlock", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    DrawableRectangleComponent drawableBackground = new DrawableRectangleComponent(switchBlock);
                    switchBlock.setDefaultColor(Color.ORANGE);
                    switchBlock.setHoverColor(Color.ORANGE);
                    switchBlock.setClickColor(Color.TRANSPARENT);
//                    SpriteComponent backgroundSprite = new SpriteComponent(rock, gameResource.getSpriteSheet("rock"), new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    AABHitbox hitbox = new AABHitbox(new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    CollisionComponent collisionComponent = new CollisionComponent(switchBlock, hitbox);
                    collisionComponent.setCollisionBehaviour(CollisionBehaviour.STATIC);
                    switchBlock.addComponent(drawableBackground);
//                    switchBlock.addComponent(backgroundSprite);
                    switchBlock.addComponent(collisionComponent);
                    gameWorld.addGameObject(switchBlock);
                }

                if (row.charAt(j) == 'D') {
                    GameObject breakableBlock = new GameObject("breakableBlock", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    DrawableRectangleComponent drawableBackground = new DrawableRectangleComponent(breakableBlock);
                    breakableBlock.setDefaultColor(Color.BLUE);
                    breakableBlock.setHoverColor(Color.BLUE);
                    breakableBlock.setClickColor(Color.TRANSPARENT);
//                    SpriteComponent backgroundSprite = new SpriteComponent(rock, gameResource.getSpriteSheet("rock"), new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    AABHitbox hitbox = new AABHitbox(new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    CollisionComponent collisionComponent = new CollisionComponent(breakableBlock, hitbox);
                    collisionComponent.setCollisionBehaviour(CollisionBehaviour.STATIC);
                    breakableBlock.addComponent(drawableBackground);
//                    switchBlock.addComponent(backgroundSprite);
                    breakableBlock.addComponent(collisionComponent);
                    gameWorld.addGameObject(breakableBlock);
                }
                if (row.charAt(j) == 'T') {
                    GameObject trap = new GameObject("trap", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    trap.setzIndex(0);
                    trap.setDefaultColor(Color.BLACK);
                    trap.setHoverColor(Color.BLACK);
                    trap.setClickColor(Color.TRANSPARENT);
                    DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(trap);

                    PhysicsComponent physicsComponent = new PhysicsComponent(trap, 5);
                    physicsComponent.setGravityActivated(false);
                    AABHitbox hitbox = new AABHitbox(new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
//                    AABHitbox gravityHitbox = new AABHitbox(new Vec2d(j * 32 + (32) / 2, i * 32 + 32 + 1), new Vec2d(3, 3));
                    CollisionComponent collisionComponent5 = new CollisionComponent(trap, hitbox);
//                    CollisionComponent gravityCollisionComponent = new CollisionComponent(mainCharacter, gravityHitbox);
//                    gravityCollisionComponent.setTag("gravity");


                    trap.addComponent(drawableComponent);

                    trap.addComponent(physicsComponent);
//                    mainCharacter.addComponent(gravityCollisionComponent);

                    trap.addComponent(collisionComponent5);

                    trap.setStatus("idle");
                    gameWorld.addGameObject(trap);
                }
                if (row.charAt(j) == 'P') {
                    GameObject mainCharacter = new GameObject("player", new TransformComponent(new Vec2d(j * 32 + 10, i * 32 + 10), new Vec2d(32 - 20, 32 - 15)));
                    mainCharacter.setzIndex(0);
                    mainCharacter.setDefaultColor(Color.TRANSPARENT);
                    mainCharacter.setHoverColor(Color.TRANSPARENT);
                    mainCharacter.setClickColor(Color.TRANSPARENT);


                    DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(mainCharacter);
                    mainCharacter.addComponent(drawableComponent);

                    RayComponent rayComponent = new RayComponent(mainCharacter);
                    rayComponent.setPositionOnWorld(mainCharacter.getTransformComponent().getPositionOnWorld());
                    mainCharacter.addComponent(rayComponent);


                    SpriteComponent spriteComponent1 = new SpriteComponent("left", mainCharacter, gameResource.getSpriteSheet("playerLeft"), new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    SpriteComponent spriteComponent2 = new SpriteComponent("right", mainCharacter, gameResource.getSpriteSheet("playerRight"), new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    mainCharacter.addComponent(spriteComponent1);
                    mainCharacter.addComponent(spriteComponent2);

                    PhysicsComponent physicsComponent = new PhysicsComponent(mainCharacter, 10);
                    mainCharacter.addComponent(physicsComponent);

                    AABHitbox hitbox = new AABHitbox(new Vec2d(j * 32 + 10, i * 32 + 10), new Vec2d(32 - 20, 32 - 15));
                    CollisionComponent collisionComponent = new CollisionComponent(mainCharacter, hitbox);
                    mainCharacter.addComponent(collisionComponent);

                    AABHitbox gravityHitbox = new AABHitbox(new Vec2d(j * 32 + 10 + (32 - 20) / 2, i * 32 + 10 + 32 - 15 + 1), new Vec2d(3, 3));
                    CollisionComponent gravityCollisionComponent = new CollisionComponent(mainCharacter, gravityHitbox);
                    gravityCollisionComponent.setTag("gravity");
                    mainCharacter.addComponent(gravityCollisionComponent);


                    mainCharacter.setStatus("idle");
                    gameWorld.addGameObject(mainCharacter);
                }
                if (row.charAt(j) == 'S') {
                    GameObject mainCharacter = new GameObject("box", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    mainCharacter.setzIndex(0);
                    mainCharacter.setDefaultColor(Color.RED);
                    mainCharacter.setHoverColor(Color.RED);
                    mainCharacter.setClickColor(Color.TRANSPARENT);
                    DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(mainCharacter);

                    PhysicsComponent physicsComponent = new PhysicsComponent(mainCharacter, 5);
                    AABHitbox hitbox = new AABHitbox(new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    AABHitbox gravityHitbox = new AABHitbox(new Vec2d(j * 32 + (32) / 2, i * 32 + 32 + 1), new Vec2d(3, 3));
                    CollisionComponent collisionComponent5 = new CollisionComponent(mainCharacter, hitbox);
                    CollisionComponent gravityCollisionComponent = new CollisionComponent(mainCharacter, gravityHitbox);
                    gravityCollisionComponent.setTag("gravity");


                    mainCharacter.addComponent(drawableComponent);

                    mainCharacter.addComponent(physicsComponent);
                    mainCharacter.addComponent(gravityCollisionComponent);

                    mainCharacter.addComponent(collisionComponent5);

                    mainCharacter.setStatus("idle");
                    gameWorld.addGameObject(mainCharacter);
                }
                if (row.charAt(j) == 'C') {
                    GameObject mainCharacter = new GameObject("box", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    mainCharacter.setzIndex(0);
                    mainCharacter.setDefaultColor(Color.RED);
                    mainCharacter.setHoverColor(Color.RED);
                    mainCharacter.setClickColor(Color.TRANSPARENT);
                    DrawableCircleComponent drawableComponent = new DrawableCircleComponent(mainCharacter);

                    PhysicsComponent physicsComponent = new PhysicsComponent(mainCharacter, 3);
                    CircleHitbox hitbox = new CircleHitbox(drawableComponent.getCenter(), drawableComponent.getRadius());
                    AABHitbox gravityHitbox = new AABHitbox(new Vec2d(j * 32 + (32) / 2, i * 32 + 32 + 1), new Vec2d(3, 3));
                    CollisionComponent collisionComponent5 = new CollisionComponent(mainCharacter, hitbox);
                    CollisionComponent gravityCollisionComponent = new CollisionComponent(mainCharacter, gravityHitbox);
                    gravityCollisionComponent.setTag("gravity");
                    mainCharacter.addComponent(drawableComponent);
                    mainCharacter.addComponent(physicsComponent);
                    mainCharacter.addComponent(gravityCollisionComponent);

                    mainCharacter.addComponent(collisionComponent5);
                    mainCharacter.setStatus("idle");
                    gameWorld.addGameObject(mainCharacter);
                }
                if (row.charAt(j) == 'L') {
                    GameObject mainCharacter = new GameObject("poly", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    mainCharacter.setzIndex(0);
                    mainCharacter.setDefaultColor(Color.RED);
                    mainCharacter.setHoverColor(Color.RED);
                    mainCharacter.setClickColor(Color.TRANSPARENT);
                    Vec2d[] points = {new Vec2d(j * 32, i * 32), new Vec2d(j * 32, i * 32 + 32), new Vec2d(j * 32 + 32, i * 32 + 32)};
                    DrawablePolygonComponent drawableComponent = new DrawablePolygonComponent(mainCharacter, points);

                    PhysicsComponent physicsComponent = new PhysicsComponent(mainCharacter, 3);
                    PolygonHitbox hitbox = new PolygonHitbox(points);
                    CollisionComponent collisionComponent = new CollisionComponent(mainCharacter, hitbox);

                    AABHitbox gravityHitbox = new AABHitbox(new Vec2d(j * 32 + (32) / 2, i * 32 + 32 + 1), new Vec2d(3, 3));
                    CollisionComponent gravityCollisionComponent = new CollisionComponent(mainCharacter, gravityHitbox);
                    gravityCollisionComponent.setTag("gravity");

                    mainCharacter.addComponent(drawableComponent);
                    mainCharacter.addComponent(physicsComponent);
                    mainCharacter.addComponent(gravityCollisionComponent);
                    mainCharacter.addComponent(collisionComponent);
                    mainCharacter.setStatus("idle");

                    gameWorld.addGameObject(mainCharacter);
                }
                if (row.charAt(j) == 'E') {
                    GameObject portal = new GameObject("portal", new TransformComponent(new Vec2d(j * 32, i * 32), new Vec2d(32, 32)));
                    DrawableRectangleComponent drawableBackground = new DrawableRectangleComponent(portal);
                    portal.setDefaultColor(Color.TRANSPARENT);
                    portal.setHoverColor(Color.TRANSPARENT);
                    portal.setClickColor(Color.TRANSPARENT);
                    SpriteComponent backgroundSprite = new SpriteComponent(portal, gameResource.getSpriteSheet("portal"), new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    AABHitbox hitbox = new AABHitbox(new Vec2d(j * 32, i * 32), new Vec2d(32, 32));
                    CollisionComponent collisionComponent = new CollisionComponent(portal, hitbox);
                    collisionComponent.setCollisionBehaviour(CollisionBehaviour.STATIC);
                    portal.addComponent(drawableBackground);
                    portal.addComponent(backgroundSprite);
                    portal.addComponent(collisionComponent);
                    gameWorld.addGameObject(portal);
                }
            }
        }

        GameObject button = new GameObject("checkpointbutton", new TransformComponent(new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 20,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y-70 - 20), new Vec2d(110, 52)));
        button.setzIndex(0);
        DrawableRectangleComponent drawableBackground2 = new DrawableRectangleComponent(button);
        TextComponent textField2 = new TextComponent("textComponent", button, "Restart", "Impact", 30, Color.BLACK, new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 10,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y -70 + 20), new Vec2d(0, 0));
        button.setDefaultColor(Color.TRANSPARENT);
        button.setHoverColor(Color.ORANGE);
        button.setClickColor(Color.RED);
        ClickableComponent clickableComponent2 = new ClickableComponent(button);
        button.addComponent(drawableBackground2);
        button.addComponent(textField2);
        button.addComponent(clickableComponent2);
        gameWorld.addGameObject(button);

        GameObject button2 = new GameObject("savebutton", new TransformComponent(new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 20,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y-130 - 20), new Vec2d(110, 52)));
        button2.setzIndex(0);
        DrawableRectangleComponent drawableBackground3 = new DrawableRectangleComponent(button2);
        TextComponent textField3 = new TextComponent("textComponent", button2, "Save", "Impact", 30, Color.BLACK, new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 10,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y -130 + 20), new Vec2d(0, 0));
        button2.setDefaultColor(Color.TRANSPARENT);
        button2.setHoverColor(Color.ORANGE);
        button2.setClickColor(Color.RED);
        ClickableComponent clickableComponent3 = new ClickableComponent(button2);
        button2.addComponent(drawableBackground3);
        button2.addComponent(textField3);
        button2.addComponent(clickableComponent3);
        gameWorld.addGameObject(button2);
    }

    public void ShootProjectile() {
        GameObject player = gameWorld.getGameObject("player");
        if(player != null) {
            RayComponent rayComponent = (RayComponent) player.getComponent("ray");

            if(rayComponent != null) {
                Vec2d projectilePos = rayComponent.getRay().dir.plus(5,5).plus(rayComponent.getRay().src);

                GameObject bullet = new GameObject("projectile", new TransformComponent(projectilePos, new Vec2d(5, 5)));
                bullet.setzIndex(0);
                bullet.setDefaultColor(Color.RED);
                bullet.setHoverColor(Color.RED);
                bullet.setClickColor(Color.TRANSPARENT);
                DrawableRectangleComponent drawableComponent = new DrawableRectangleComponent(bullet);

                PhysicsComponent physicsComponent = new PhysicsComponent(bullet, 1);
                physicsComponent.setGravityActivated(false);
                physicsComponent.setFrictionActivated(false);
                physicsComponent.setVel(rayComponent.getRay().dir.smult(10));
                AABHitbox hitbox = new AABHitbox(projectilePos, new Vec2d(5, 5));

                CollisionComponent collisionComponent5 = new CollisionComponent(bullet, hitbox);


                bullet.addComponent(drawableComponent);

                bullet.addComponent(physicsComponent);
//        mainCharacter.addComponent(gravityCollisionComponent);

                bullet.addComponent(collisionComponent5);

                bullet.setStatus("idle");
                gameWorld.addGameObject(bullet);
            }
        }
    }

    public void PopulateDeathScreen() {
        GameObject text = new GameObject("text", new TransformComponent(new Vec2d(this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 30, 150), new Vec2d(84, 32)));
        text.setzIndex(0);
        DrawableRectangleComponent drawableBackground = new DrawableRectangleComponent(text);
        TextComponent textField = new TextComponent("textComponent", text, "You Died!", "Impact", 50, Color.BLACK, new Vec2d(this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 30, 150), new Vec2d(0, 0));
        text.setDefaultColor(Color.TRANSPARENT);
        text.setHoverColor(Color.TRANSPARENT);
        text.setClickColor(Color.TRANSPARENT);
        text.addComponent(drawableBackground);
        text.addComponent(textField);
        gameWorld.addGameObject(text);


        GameObject button = new GameObject("newgamebutton", new TransformComponent(new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 20,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y-130 - 20), new Vec2d(150, 52)));
        button.setzIndex(0);
        DrawableRectangleComponent drawableBackground2 = new DrawableRectangleComponent(button);
        TextComponent textField2 = new TextComponent("textComponent", button, "New Game", "Impact", 30, Color.BLACK, new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 10,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y -130 + 20), new Vec2d(0, 0));
        button.setDefaultColor(Color.TRANSPARENT);
        button.setHoverColor(Color.ORANGE);
        button.setClickColor(Color.RED);
        ClickableComponent clickableComponent2 = new ClickableComponent(button);
        button.addComponent(drawableBackground2);
        button.addComponent(textField2);
        button.addComponent(clickableComponent2);
        gameWorld.addGameObject(button);

        GameObject button2 = new GameObject("checkpointbutton", new TransformComponent(new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 20,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y-70 - 20), new Vec2d(220, 52)));
        button2.setzIndex(0);
        DrawableRectangleComponent drawableBackground3 = new DrawableRectangleComponent(button2);
        TextComponent textField3 = new TextComponent("textComponent", button2, "Load Checkpoint", "Impact", 30, Color.BLACK, new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 10,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y -70 + 20), new Vec2d(0, 0));
        button2.setDefaultColor(Color.TRANSPARENT);
        button2.setHoverColor(Color.ORANGE);
        button2.setClickColor(Color.RED);
        ClickableComponent clickableComponent3 = new ClickableComponent(button2);
        button2.addComponent(drawableBackground3);
        button2.addComponent(textField3);
        button2.addComponent(clickableComponent3);
        gameWorld.addGameObject(button2);
    }

    public void PopulateEndScreen() {
        GameObject text = new GameObject("text", new TransformComponent(new Vec2d(this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 30, 150), new Vec2d(84, 32)));
        text.setzIndex(0);
        DrawableRectangleComponent drawableBackground = new DrawableRectangleComponent(text);
        TextComponent textField = new TextComponent("textComponent", text, "Winner!", "Impact", 50, Color.BLACK, new Vec2d(this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 30, 150), new Vec2d(0, 0));
        text.setDefaultColor(Color.TRANSPARENT);
        text.setHoverColor(Color.TRANSPARENT);
        text.setClickColor(Color.TRANSPARENT);
        text.addComponent(drawableBackground);
        text.addComponent(textField);
        gameWorld.addGameObject(text);


        GameObject button = new GameObject("newgamebutton", new TransformComponent(new Vec2d(this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 20, this.gameWorld.getRefScreen().getCurrentWindowSize().y / 2 - 20), new Vec2d(164, 52)));
        button.setzIndex(0);
        DrawableRectangleComponent drawableBackground2 = new DrawableRectangleComponent(button);
        TextComponent textField2 = new TextComponent("textComponent", button, "Play Again?", "Impact", 30, Color.BLACK, new Vec2d(this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 10, this.gameWorld.getRefScreen().getCurrentWindowSize().y / 2 + 20), new Vec2d(0, 0));
        button.setDefaultColor(Color.TRANSPARENT);
        button.setHoverColor(Color.ORANGE);
        button.setClickColor(Color.RED);
        ClickableComponent clickableComponent2 = new ClickableComponent(button);
        button.addComponent(drawableBackground2);
        button.addComponent(textField2);
        button.addComponent(clickableComponent2);
        gameWorld.addGameObject(button);
    }

    public void PopulateStartScreen() {
        GameObject text = new GameObject("text", new TransformComponent(new Vec2d(this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 30, 150), new Vec2d(84, 32)));
        text.setzIndex(0);
        DrawableRectangleComponent drawableBackground = new DrawableRectangleComponent(text);
        TextComponent textField = new TextComponent("textComponent", text, "NINJA", "Impact", 50, Color.BLACK, new Vec2d(this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 30, 150), new Vec2d(0, 0));
        text.setDefaultColor(Color.TRANSPARENT);
        text.setHoverColor(Color.TRANSPARENT);
        text.setClickColor(Color.TRANSPARENT);
        text.addComponent(drawableBackground);
        text.addComponent(textField);
        gameWorld.addGameObject(text);


        GameObject button = new GameObject("newgamebutton", new TransformComponent(new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 20,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y-130 - 20), new Vec2d(150, 52)));
        button.setzIndex(0);
        DrawableRectangleComponent drawableBackground2 = new DrawableRectangleComponent(button);
        TextComponent textField2 = new TextComponent("textComponent", button, "New Game", "Impact", 30, Color.BLACK, new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 10,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y -130 + 20), new Vec2d(0, 0));
        button.setDefaultColor(Color.TRANSPARENT);
        button.setHoverColor(Color.ORANGE);
        button.setClickColor(Color.RED);
        ClickableComponent clickableComponent2 = new ClickableComponent(button);
        button.addComponent(drawableBackground2);
        button.addComponent(textField2);
        button.addComponent(clickableComponent2);
        gameWorld.addGameObject(button);

        GameObject button2 = new GameObject("checkpointbutton", new TransformComponent(new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 20,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y-70 - 20), new Vec2d(220, 52)));
        button2.setzIndex(0);
        DrawableRectangleComponent drawableBackground3 = new DrawableRectangleComponent(button2);
        TextComponent textField3 = new TextComponent("textComponent", button2, "Load Checkpoint", "Impact", 30, Color.BLACK, new Vec2d(
                this.gameWorld.getRefScreen().getCurrentWindowSize().x / 2 - 10,
                this.gameWorld.getRefScreen().getCurrentWindowSize().y -70 + 20), new Vec2d(0, 0));
        button2.setDefaultColor(Color.TRANSPARENT);
        button2.setHoverColor(Color.ORANGE);
        button2.setClickColor(Color.RED);
        ClickableComponent clickableComponent3 = new ClickableComponent(button2);
        button2.addComponent(drawableBackground3);
        button2.addComponent(textField3);
        button2.addComponent(clickableComponent3);
        gameWorld.addGameObject(button2);
    }
}
