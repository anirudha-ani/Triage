package nin;

import engine.Application;
import engine.GameResource;
import engine.GameWorld;
import engine.Screen;
import engine.components.AudioComponent;
import engine.components.PhysicsComponent;
import engine.components.RayComponent;
import engine.gameobjects.GameObject;
import engine.resources.FileLoader;
import engine.resources.MapLoader;
import engine.support.Vec2d;
import engine.systems.CollisionHappened;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import nin.blueprints.GameAssets;
import nin.blueprints.GameWorldBluePrint;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;

enum ScreensNames {
    FirstGameScreen,
    SecondGameScreen,
    EndScreen,
    StartScreen,
    DeathScreen,
}

public class App extends Application {

    GameWorld gameWorld = null;
    Screen gameScreen = null;
    GameResource resources = null;
    GameWorldBluePrint bluePrint = null;
    long secondPassedLastMove = 0;
    boolean firstTick = false;
    MapLoader map;
    char[][] mapLayoutArray;
    GameAssets gameAssets;

    FileLoader saveFile;

    public App(String title) {
        super(title);
        secondPassedLastMove = 0;
        gameAssets = new GameAssets();
    }

    public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
        super(title, windowSize, debugMode, fullscreen);
        gameAssets = new GameAssets();
        secondPassedLastMove = 0;
    }

    @Override
    protected void onTick(long nanosSincePreviousTick) {
        secondPassedLastMove += (nanosSincePreviousTick / 1000);
        if (!this.firstTick) {
            secondPassedLastMove = 0;
            this.gameAssets.LoadResources();
            this.generateStartScreen();
        }
        this.firstTick = true;
        gameWorld.tick(nanosSincePreviousTick);
        ArrayList<CollisionHappened> collisionList = this.gameWorld.collisionHappened;
        collisionLogic(collisionList);
    }

    void collisionLogic(ArrayList<CollisionHappened> collisionList) {
        collisionList.forEach(collisionHappened -> {
            if (collisionHappened.getFirstObject().getId() == "projectile") {
                if (collisionHappened.getSecondObject().getId() == "breakableBlock") {
                    gameWorld.removeGameObject(collisionHappened.getSecondObject());
                }
                if (collisionHappened.getSecondObject().getId() == "trap") {
                    collisionHappened.getSecondObject().setDefaultColor(Color.RED);
                    PhysicsComponent trapPhysics = (PhysicsComponent) collisionHappened.getSecondObject().getComponent("physics");
                    trapPhysics.setGravityActivated(true);
                }
                gameWorld.removeGameObject(collisionHappened.getFirstObject());
            }
            if (collisionHappened.getSecondObject().getId() == "projectile") {
                if (collisionHappened.getFirstObject().getId() == "breakableBlock") {
                    gameWorld.removeGameObject(collisionHappened.getFirstObject());
                }
                if (collisionHappened.getFirstObject().getId() == "trap") {
                    collisionHappened.getFirstObject().setDefaultColor(Color.RED);
                    PhysicsComponent trapPhysics = (PhysicsComponent) collisionHappened.getFirstObject().getComponent("physics");
                    trapPhysics.setGravityActivated(true);
                }
                gameWorld.removeGameObject(collisionHappened.getSecondObject());
            }
            if (collisionHappened.getFirstObject().getId() == "player" ||
                    collisionHappened.getSecondObject().getId() == "player") {


                if (collisionHappened.getFirstObject().getId() == "switchBlock" ||
                        collisionHappened.getSecondObject().getId() == "switchBlock") {
                    GameObject trap = gameWorld.getGameObject("trap");
                    if (trap != null) {
                        PhysicsComponent trapPhysics = (PhysicsComponent) trap.getComponent("physics");
                        trapPhysics.setGravityActivated(true);
                    }

                }

                if (collisionHappened.getFirstObject().getId() == "portal" ||
                        collisionHappened.getSecondObject().getId() == "portal") {
                    if (getCurrentScreenId() == ScreensNames.FirstGameScreen.toString()) {
                        generateSecondMap();
                    } else {
                        generateEndScreen();
                    }

                }

                if ((collisionHappened.getFirstObject().getId() == "trap" && collisionHappened.getFirstObject().getDefaultColor() == Color.BLACK) ||
                        (collisionHappened.getSecondObject().getId() == "trap" && collisionHappened.getSecondObject().getDefaultColor() == Color.BLACK)) {

                    generateDeathScreen();

                }

            }
        });
    }

    @Override
    protected void onDraw(GraphicsContext g) {
        this.getScreenArrayList().forEach(screen -> {
            if (screen.getScreenID() == this.getCurrentScreenId()) {
                screen.draw(g);
            }
        });
        gameWorld.getGameObjects().forEach(gameObject -> {
            PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");
            if (physicsComponent != null && physicsComponent.isGravityActivated()) {
                physicsComponent.applyForce(new Vec2d(0, 0.1 * physicsComponent.getMass()));
            }
        });
    }

    public void generateFirstMap() {
        gameWorld = new GameWorld();
        gameScreen = new Screen(nin.ScreensNames.FirstGameScreen.toString(), this.getCurrentScreenSize(), gameWorld, false);
        resources = gameAssets.getGameResource();
        bluePrint = new GameWorldBluePrint(gameWorld, resources);
        this.setCurrentScreenId(nin.ScreensNames.FirstGameScreen.toString());
        flushAllScreens();
        this.addScreen(gameScreen);
        this.map = new MapLoader("nin/maps/Map1.txt");
        this.mapLayoutArray = map.getMapLayoutArray();

        this.bluePrint.PopulateGameWorld(this.map.getMapLayout(), "Easy Level", this.map.getMapLayoutArray());
        secondPassedLastMove = 0;
    }

    public void generateSecondMap() {
        // System.out.println("First map");
        gameWorld = new GameWorld();
        gameScreen = new Screen(nin.ScreensNames.SecondGameScreen.toString(), this.getCurrentScreenSize(), gameWorld, false);
        resources = gameAssets.getGameResource();
        bluePrint = new GameWorldBluePrint(gameWorld, resources);
        this.setCurrentScreenId(ScreensNames.SecondGameScreen.toString());
        flushAllScreens();
        this.addScreen(gameScreen);
        this.map = new MapLoader("nin/maps/Map2.txt");
        this.mapLayoutArray = map.getMapLayoutArray();

        this.bluePrint.PopulateGameWorld(this.map.getMapLayout(), "Hard Level", this.map.getMapLayoutArray());
        secondPassedLastMove = 0;
    }

    public void generateEndScreen() {
        gameWorld = new GameWorld();
        gameScreen = new Screen(nin.ScreensNames.EndScreen.toString(), this.getCurrentScreenSize(), gameWorld, false);
        resources = gameAssets.getGameResource();
        bluePrint = new GameWorldBluePrint(gameWorld, resources);
        this.setCurrentScreenId(nin.ScreensNames.EndScreen.toString());
        flushAllScreens();
        this.addScreen(gameScreen);
        this.bluePrint.PopulateEndScreen();
        secondPassedLastMove = 0;
    }

    public void generateStartScreen() {
        gameWorld = new GameWorld();
        gameScreen = new Screen(ScreensNames.StartScreen.toString(), this.getCurrentScreenSize(), gameWorld, false);
        resources = gameAssets.getGameResource();
        bluePrint = new GameWorldBluePrint(gameWorld, resources);
        this.setCurrentScreenId(nin.ScreensNames.StartScreen.toString());
        flushAllScreens();
        this.addScreen(gameScreen);
        this.bluePrint.PopulateStartScreen();
        secondPassedLastMove = 0;
    }

    public void generateDeathScreen() {
        gameWorld = new GameWorld();
        gameScreen = new Screen(ScreensNames.DeathScreen.toString(), this.getCurrentScreenSize(), gameWorld, false);
        resources = gameAssets.getGameResource();
        bluePrint = new GameWorldBluePrint(gameWorld, resources);
        this.setCurrentScreenId(nin.ScreensNames.DeathScreen.toString());
        flushAllScreens();
        this.addScreen(gameScreen);
        this.bluePrint.PopulateDeathScreen();
        secondPassedLastMove = 0;
    }

    void loadSaveFile() {
        try {
            saveFile = new FileLoader("nin/savefiles/savegame.xml");
        } catch (ParserConfigurationException ex) {
            System.out.println("ParserConfigurationException");
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            System.out.println("IOException");

            throw new RuntimeException(ex);
        } catch (SAXException ex) {
            System.out.println("SAXException");

            throw new RuntimeException(ex);
        }
    }

    void resetSaveFile() {
        if (saveFile == null) {
            loadSaveFile();
        }
        try {

            saveFile.modifyElements("level", "1");
            saveFile.modifyElements("playerPositionX", "-1");
            saveFile.modifyElements("playerPositionY", "-1");

        } catch (XPathExpressionException ex) {
            throw new RuntimeException(ex);
        }
    }

    void saveCurrentState() {
        if (saveFile == null) {
            loadSaveFile();
        }
        try {
            if (getCurrentScreenId() == ScreensNames.FirstGameScreen.toString()) {
                saveFile.modifyElements("level", "1");
            } else if (getCurrentScreenId() == ScreensNames.SecondGameScreen.toString()) {
                saveFile.modifyElements("level", "2");
            }
            Vec2d playerPosition = gameWorld.getGameObject("player").getTransformComponent().getPositionOnWorld();
            saveFile.modifyElements("playerPositionX", (Double.toString(playerPosition.x)));
            saveFile.modifyElements("playerPositionY", (Double.toString(playerPosition.y)));
        } catch (XPathExpressionException ex) {
            throw new RuntimeException(ex);
        }
    }

    void loadSavedState() {
//        generateFirstMap();
        if (saveFile == null) {
            loadSaveFile();
        }

        try {
            int mapID = Integer.parseInt(saveFile.readElements("level"));
            if (mapID == 1) {
                this.generateFirstMap();
            } else if (mapID == 2) {
                this.generateSecondMap();
            }
            double playerPositionX = Double.parseDouble(saveFile.readElements("playerPositionX"));
            double playerPositionY = Double.parseDouble(saveFile.readElements("playerPositionY"));

            if (playerPositionX < 0 && playerPositionY < 0) {
                return;
            }

            GameObject player = gameWorld.getGameObject("player");
            player.getTransformComponent().setPositionOnWorld(new Vec2d(playerPositionX, playerPositionY));

            PhysicsComponent physicsComponent = (PhysicsComponent) player.getComponent("physics");
            physicsComponent.setVel(new Vec2d(0, 0));
            physicsComponent.setGravityActivated(true);
        } catch (XPathExpressionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }

        if (e.getCode() == KeyCode.R) {
            secondPassedLastMove = 0;
            flushAllScreens();
            firstTick = false;
        }

        if (e.getCode() == KeyCode.P) {
            saveCurrentState();
        }
        if (e.getCode() == KeyCode.O) {
            loadSavedState();
        }
        if (e.getCode() == KeyCode.U) {
            System.out.println("nin/audiofiles/Dramatic-suspense-background-music.mp3");
            AudioComponent audioClip = new AudioComponent("nin/audiofiles/Dramatic-suspense-background-music.mp3", true);
            audioClip.playAudio();
        }


        this.getScreenArrayList().forEach(screen -> {
            if (screen.getScreenID() == this.getCurrentScreenId()) {
                screen.onKeyPressed(e);
            }
        });
    }

    @Override
    protected void onMousePressed(MouseEvent e) {

        for (int i = this.getScreenArrayList().size() - 1; i >= 0; i--) {
            Screen screen = this.getScreenArrayList().get(i);
            if (screen.getScreenID() == this.getCurrentScreenId()) {
                ArrayList<String> clickedObjects = screen.onMousePressed(e);
            }
        }

    }

    @Override
    protected void onMouseReleased(MouseEvent e) {
//        System.out.println("onMouseReleased" + e.getButton());

        if (e.getButton() == MouseButton.SECONDARY) {
            GameObject player = gameWorld.getGameObject("player");

            if (player != null) {
                RayComponent rayComponent = (RayComponent) player.getComponent("ray");

                if (rayComponent != null) {
                    rayComponent.setShow(false);
                    bluePrint.ShootProjectile();
                }
            }
        }

        for (int i = this.getScreenArrayList().size() - 1; i >= 0; i--) {
            Screen screen = this.getScreenArrayList().get(i);
            if (screen.getScreenID() == this.getCurrentScreenId()) {
                screen.onMouseReleased(e);
            }
        }

    }

    @Override
    protected void onMouseDragged(MouseEvent e) {
        //  System.out.println("onMouseDragged called");
        this.getScreenArrayList().forEach(screen -> {
            if (screen.getScreenID() == this.getCurrentScreenId()) {
                screen.onMouseDragged(e);
            }
        });
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {
        for (int i = this.getScreenArrayList().size() - 1; i >= 0; i--) {
            Screen screen = this.getScreenArrayList().get(i);
            if (screen.getScreenID() == this.getCurrentScreenId()) {
                ArrayList<String> clickedObjects = screen.onMouseClicked(e);
                if (clickedObjects.contains("newgamebutton")) {
                    this.resetSaveFile();
                    this.generateFirstMap();
                } else if (clickedObjects.contains("checkpointbutton")) {
                    this.loadSavedState();
                } else if (clickedObjects.contains("savebutton")) {
                    this.saveCurrentState();
                }
            }
        }
    }
}