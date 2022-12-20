package engine;

import engine.UIElement.AffineWrapper;
import engine.gameobjects.GameObject;
import engine.systems.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;


public class GameWorld {
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    public int localItemCounter = 0;
    public GraphicsContext currentGraphicsContext;
    public CollisionSystem collisionSystem = new CollisionSystem(this);
    public ArrayList<CollisionHappened> collisionHappened = new ArrayList<>();
    DrawSystem drawSystem = new DrawSystem(this);
    InputSystem inputSystem = new InputSystem(this);
    Screen refScreen;

    public Screen getRefScreen() {
        return refScreen;
    }

    public void setRefScreen(Screen refScreen) {
        this.refScreen = refScreen;
    }

    public GraphicsContext getCurrentGraphicsContext() {
        return currentGraphicsContext;
    }

    public void setCurrentGraphicsContext(GraphicsContext currentGraphicsContext) {
        this.currentGraphicsContext = currentGraphicsContext;
    }

    public void tick(long nonosSinceLastTick) {
        this.gameObjects.forEach(gameObject -> gameObject.onTick(nonosSinceLastTick));
        collisionHappened.clear();
        collisionSystem.checkCollision(collisionHappened);
        drawSystem.onTick(nonosSinceLastTick);
        inputSystem.onTick(nonosSinceLastTick);
    }

    public void draw(GraphicsContext g, AffineWrapper viewPointAffine) {
        this.currentGraphicsContext = g;
        drawSystem.draw(viewPointAffine);
    }

    public void onKeyPressed(KeyEvent e, AffineWrapper viewPointAffine) {
        inputSystem.onKeyPressed(e, viewPointAffine);
    }

    public void onKeyReleased(KeyEvent e, AffineWrapper viewPointAffine) {
        inputSystem.onKeyReleased(e, viewPointAffine);
    }

    public ArrayList<String> onMouseClicked(MouseEvent e, AffineWrapper viewPointAffine) {
//        localItemCounter += inputSystem.onMouseClick(e);
        return inputSystem.onMouseClick(e, viewPointAffine);
    }

    public void addGameObject(GameObject gameObject) {
        gameObject.setRefGameWorld(this);
        drawSystem.addObject(gameObject);
        inputSystem.addObject(gameObject);
        collisionSystem.addObject(gameObject);
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        drawSystem.removeObject(gameObject);
        inputSystem.removeObject(gameObject);
        collisionSystem.removeObject(gameObject);
    }

    public GameObject getGameObject(String id) {
        for (int i = 0; i < gameObjects.size(); i++) {
//            System.out.println("Gameworld getGameObject " + gameObjects.get(i).getId());
            if (gameObjects.get(i).getId() == id) {
                return gameObjects.get(i);
            }
        }
        return null;
    }

    // Returns all the objects with same ID
    public ArrayList<GameObject> getGameObjects(String id) {
        ArrayList <GameObject> queriedGameObjets = new ArrayList<>();
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i).getId() == id) {
                queriedGameObjets.add(gameObjects.get(i));
            }
        }
        return queriedGameObjets;
    }


    public void onMouseWheelMoved(ScrollEvent e) {
    }

    public void onMouseMoved(MouseEvent e, AffineWrapper viewPointAffine) {
        inputSystem.onMouseMoved(e, viewPointAffine);
    }

    public ArrayList<String> onMousePressed(MouseEvent e, AffineWrapper viewPointAffine) {
        return inputSystem.onMousePressed(e, viewPointAffine);
    }

    public void onMouseReleased(MouseEvent e, AffineWrapper viewPointAffine) {
        inputSystem.onMouseReleased(e, viewPointAffine);
    }

    public void onMouseDragged(MouseEvent e, AffineWrapper viewPointAffine) {

        inputSystem.onMouseDragged(e, viewPointAffine);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public KeyEventHappened getKeyEventHappened() {
        return inputSystem.getKeyEventHappened();
    }
}
