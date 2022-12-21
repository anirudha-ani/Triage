package triage.blueprints;

import engine.GameResource;
import engine.components.SpriteComponent;
import engine.resources.SpriteSheet;
import javafx.scene.image.Image;

public class GameAssets {
    private final GameResource gameResource;

    public GameAssets() {
        gameResource = new GameResource();
    }

    public GameAssets(GameResource gameResource) {
        this.gameResource = gameResource;
    }

    public void LoadResources() {
        // Image takes a path, requested height, width, preserve ratio and smoothing
        Image playerLeft = new Image("file:triage/spritesheets/player/ACharLeft.png", 48, 48, false, true);
        Image playerRight = new Image("file:triage/spritesheets/player/ACharRight.png", 48, 48, false, true);
        Image spaceBackground = new Image("file:triage/spritesheets/backgrounds/SpaceBackground.jpg", 6000, 3000, false, true);
        Image galaxyBackground = new Image("file:triage/spritesheets/backgrounds/newspace.png", 2048, 1153, false, true);
        Image coin = new Image("file:triage/spritesheets/coins/Gold/Gold_21.png", 563,564,false,true);
        Image cart = new Image("file:triage/spritesheets/cart1.png", 512,512,false,true);
        Image cartbg = new Image("file:triage/spritesheets/cart-bg.png", 683,384,false,true);
        Image samurai = new Image("file:triage/spritesheets/Martial Hero/Sprites/idle.png",1600,200,false,true);
        Image huntress = new Image("file:triage/spritesheets/Huntress/Sprites/idle.png",1200,150,false,true);
        Image wizard = new Image("file:triage/spritesheets/Wizard Pack/idle.png",1386,190,false,true);
        Image warrior = new Image("file:triage/spritesheets/Fantasy Warrior/Sprites/idle.png",1620,162,false,true);

        Image samuraiMoveRight = new Image("file:triage/spritesheets/Martial Hero/Sprites/Run_R.png",1600,200,false,true);
        Image samuraiJump = new Image("file:triage/spritesheets/Martial Hero/Sprites/Jump.png",400,200,false,true);
        Image samuraiMoveLeft = new Image("file:triage/spritesheets/Martial Hero/Sprites/Run_L.png",1600,200,false,true);
        Image samuraiAttackRight = new Image("file:triage/spritesheets/Martial Hero/Sprites/Attack1.png",600,200,false,true);
        Image samuraiJumpLeft = new Image("file:triage/spritesheets/Martial Hero/Sprites/Jump_L.png",400,200,false,true);
        Image samuraiAttackLeft = new Image("file:triage/spritesheets/Martial Hero/Sprites/Attack1_L.png",600,200,false,true);

        Image mummyIdle = new Image("file:triage/spritesheets/enemy/5 Mummy/Mummy_idle.png",192,48,false,true);
        Image mummyLeft = new Image("file:triage/spritesheets/enemy/5 Mummy/Mummy_walk.png",288,48,false,true);
        Image mummyRight = new Image("file:triage/spritesheets/enemy/5 Mummy/Mummy_right.png",288,48,false,true);

        Image UFO = new Image("file:triage/spritesheets/enemy/ufo.gif",200,200,false,true);

        Image pause = new Image("file:triage/spritesheets/pause.png", 512,512,false,true);

        Image exit = new Image("file:triage/spritesheets/exit.png", 512,512,false,true);

        // Sprite height and width indicates individual sprite height and width in a sprite sheet
        gameResource.addSpriteSheet(SpriteSheetId.PLAYER_LEFT.toString(), playerLeft, 2, 2, 24, 24);
        gameResource.addSpriteSheet(SpriteSheetId.PLAYER_RIGHT.toString(), playerRight, 2, 2, 24, 24);
        gameResource.addSpriteSheet(SpriteSheetId.BACKGROUND_SPACE.toString(), spaceBackground,1, 1, 6000, 3375);
        gameResource.addSpriteSheet(SpriteSheetId.BACKGROUND_GALAXY.toString(), galaxyBackground,1, 1, 2000, 1125);
        gameResource.addSpriteSheet(SpriteSheetId.COIN_IMAGE.toString(),coin,1,1,563,564);
        gameResource.addSpriteSheet(SpriteSheetId.SHOPPING_CART.toString(),cart,1,1,512,512);
        gameResource.addSpriteSheet(SpriteSheetId.CART_BG.toString(),cartbg,1,1,683,384);
        gameResource.addSpriteSheet(SpriteSheetId.SAMURAI.toString(),samurai,8,1,200,200);
        gameResource.addSpriteSheet(SpriteSheetId.HUNTRESS.toString(),huntress,8,1,150,150);
        gameResource.addSpriteSheet(SpriteSheetId.WIZARD.toString(),wizard,6,1,231,190);
        gameResource.addSpriteSheet(SpriteSheetId.WARRIOR.toString(),warrior,10,1,162,162);
        gameResource.addSpriteSheet(SpriteSheetId.SAMURAI_RIGHT.toString(),samuraiMoveRight,8,1,200,200);
        gameResource.addSpriteSheet(SpriteSheetId.SAMURAI_JUMP.toString(),samuraiJump,2,1,200,200);
        gameResource.addSpriteSheet(SpriteSheetId.SAMURAI_LEFT.toString(),samuraiMoveLeft,8,1,200,200);
        gameResource.addSpriteSheet(SpriteSheetId.SAMURAI_ATTACK.toString(),samuraiAttackRight,3,1,200,200);
        gameResource.addSpriteSheet(SpriteSheetId.SAMURAI_JUMP_LEFT.toString(),samuraiJumpLeft,2,1,200,200);
        gameResource.addSpriteSheet(SpriteSheetId.SAMURAI_ATTACK_LEFT.toString(),samuraiAttackLeft,3,1,200,200);
        gameResource.addSpriteSheet(SpriteSheetId.MUMMY_LEFT.toString(),mummyLeft,6,1,48,48);
        gameResource.addSpriteSheet(SpriteSheetId.MUMMY_RIGHT.toString(),mummyRight,6,1,48,48);
        gameResource.addSpriteSheet(SpriteSheetId.MUMMY.toString(),mummyIdle,4,1,48,48);
        gameResource.addSpriteSheet(SpriteSheetId.UFO.toString(),UFO,1,1,200,200);
        gameResource.addSpriteSheet(SpriteSheetId.PAUSE.toString(),pause,1,1,512,512);
        gameResource.addSpriteSheet(SpriteSheetId.EXIT.toString(),exit,1,1,512,512);

    }

    public GameResource getGameResource() {
        return gameResource;
    }
}
