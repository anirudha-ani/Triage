package engine;


import engine.UIElement.AffineWrapper;
import engine.UIElement.Viewport;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Affine;

import java.util.ArrayList;

public class Screen {

    private String screenID;
    private AffineWrapper screenAffine;
    private Vec2d originalWindowSize;
    private Vec2d currentWindowSize;
    private GameWorld gameWorldRef;
    private boolean hasViewPort = false;
    private Viewport viewport = null;

    public Screen() {

    }

    public Screen(String screenId) {
        this.screenID = screenId;
    }

    public Screen(String screenId, Vec2d windowSize, GameWorld gameWorld) {
        this.screenID = screenId;
        this.originalWindowSize = windowSize;
        this.gameWorldRef = gameWorld;
        this.currentWindowSize = windowSize;
        this.screenAffine = new AffineWrapper();
        gameWorldRef.setRefScreen(this);
    }

    public Screen(String screenId, Vec2d windowSize, GameWorld gameWorld, boolean hasViewPort) {
        this.screenID = screenId;
        this.originalWindowSize = windowSize;
        this.gameWorldRef = gameWorld;
        this.currentWindowSize = windowSize;
        this.screenAffine = new AffineWrapper();
        this.hasViewPort = hasViewPort;
        if (hasViewPort) {
            viewport = new Viewport();
            viewport.getAffine().getAffine().appendScale(2, 2);
        }
        gameWorldRef.setRefScreen(this);
    }

    public String getScreenID() {
        return screenID;
    }

    public AffineWrapper getScreenAffine() {
        return screenAffine;
    }

    public void setScreenAffine(Affine affine) {
        this.screenAffine = new AffineWrapper();
        this.screenAffine.deepCopy(affine);
    }

    public Vec2d getCurrentWindowSize() {
        return currentWindowSize;
    }

    public void setCurrentWindowSize(Vec2d currentWindowSize) {
        this.currentWindowSize = currentWindowSize;
    }

    public void draw(GraphicsContext g) {
        g.setTransform(screenAffine.getAffine());
        if (hasViewPort) {
            gameWorldRef.draw(g, viewport.getAffine());
        } else {
            gameWorldRef.draw(g, screenAffine);
        }

    }

    public ArrayList<String> onMouseClicked(MouseEvent e) {
        if (hasViewPort) {
            viewport.onMouseClicked(e);
            return gameWorldRef.onMouseClicked(e, viewport.getAffine());
        } else {
            return gameWorldRef.onMouseClicked(e, screenAffine);
        }
    }

    public void onKeyPressed(KeyEvent e) {
        if (hasViewPort) {
            viewport.onKeyPressed(e);
            gameWorldRef.onKeyPressed(e, viewport.getAffine());
        } else {
            gameWorldRef.onKeyPressed(e, screenAffine);
        }
    }

    public void onKeyReleased(KeyEvent e) {
        if (hasViewPort) {
//            viewport.onKeyPressed(e);
            gameWorldRef.onKeyReleased(e, viewport.getAffine());
        } else {
            gameWorldRef.onKeyReleased(e, screenAffine);
        }
    }

    public void onMouseWheelMoved(ScrollEvent e) {
        if (hasViewPort) {
            viewport.onMouseWheelMoved(e);
        }
        gameWorldRef.onMouseWheelMoved(e);
    }

    public void onMouseMoved(MouseEvent e) {
        if (hasViewPort) {
            viewport.onMouseMoved(e);
            gameWorldRef.onMouseMoved(e, viewport.getAffine());
        } else {
            gameWorldRef.onMouseMoved(e, screenAffine);
        }
    }

    public ArrayList<String> onMousePressed(MouseEvent e) {
        if (hasViewPort) {
            viewport.onMouseMoved(e);
            return gameWorldRef.onMousePressed(e, viewport.getAffine());
        } else {
            return gameWorldRef.onMousePressed(e, screenAffine);
        }
    }


    public void onMouseReleased(MouseEvent e) {
        if (hasViewPort) {
            viewport.onMouseMoved(e);
            gameWorldRef.onMouseReleased(e, viewport.getAffine());
        } else {
            gameWorldRef.onMouseReleased(e, screenAffine);
        }
    }

    public void onMouseDragged(MouseEvent e) {
        if (hasViewPort) {
            viewport.onMouseMoved(e);
            gameWorldRef.onMouseDragged(e, viewport.getAffine());
        } else {
            gameWorldRef.onMouseDragged(e, screenAffine);
        }
    }

    public void onResize(Vec2d newScreenSize) {

        setCurrentWindowSize(newScreenSize);
        screenAffine.scaleMaintainAspectRatio(currentWindowSize, originalWindowSize);
        if (hasViewPort) {
            viewport.setLatestScreenXScale(screenAffine.getXScale());
            viewport.setLastestScreenYScale(screenAffine.getYScale());
        }
    }


    public Viewport getViewport() {
        return viewport;
    }
}
