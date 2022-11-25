package engine.systems;

import engine.GameWorld;
import engine.UIElement.AffineWrapper;
import engine.components.Component;
import engine.gameobjects.GameObject;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class DrawSystem extends System {
    private final ArrayList<GameObject> drawableObjects = new ArrayList<>();
    long secondPassedLastMove = 0;

    public DrawSystem(GameWorld gameWorld) {
        super(gameWorld);
    }

    public void addObject(GameObject g) {
        drawableObjects.add(g);
    }

    public void removeObject(GameObject g) {
        drawableObjects.remove(g);
    }


    public void draw(AffineWrapper viewPointAffine) {
        GraphicsContext graphicsContext = this.getRefGameWorld().getCurrentGraphicsContext();
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < drawableObjects.size(); j++) {
                if (drawableObjects.get(j).isDeleted()) {
                    continue;
                }
                if (drawableObjects.get(j).getzIndex() == i) {
                    drawableObjects.get(j).draw(graphicsContext, viewPointAffine);

                }
            }
        }
    }

    public void onTick(long nonosSinceLastTick) {
        secondPassedLastMove += (nonosSinceLastTick / 1000);
        int index = 0;
        if (secondPassedLastMove >= 1000000) {
            index = 0;
            secondPassedLastMove = 0;
        } else if (secondPassedLastMove >= 750000) {
            index = 3;
        } else if (secondPassedLastMove >= 500000) {
            index = 2;
        } else if (secondPassedLastMove >= 250000) {
            index = 1;
        }
        for (int j = 0; j < drawableObjects.size(); j++) {
            if (drawableObjects.get(j).isDeleted() || drawableObjects.get(j).status == "idle") {
                continue;
            }
            Component playerSprite = drawableObjects.get(j).getComponent(drawableObjects.get(j).status);
            if (playerSprite != null) {
                playerSprite.setSpriteIndexToLoad(index);
            }
        }

    }
}
