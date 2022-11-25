package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class Viewport {
    AffineWrapper viewPortAffine = new AffineWrapper();

    private double currentMousePositionOnScreenX;
    private double xOffset = 0;
    private double currentMousePositionOnScreenY;
    private double yOffset = 0;
    private double lastScreenXScale = 1;
    private double lastScreenYScale = 1;
    private double latestScreenXScale = 1;
    private double lastestScreenYScale = 1;
    private double screenXScaleRatio = 1;
    private double screenYScaleRatio = 1;

    private boolean isViewportKeyboardReactive = false;


    public double getLastScreenXScale() {
        return lastScreenXScale;
    }

    public void setLastScreenXScale(double lastScreenXScale) {
        this.lastScreenXScale = lastScreenXScale;
    }

    public double getLastScreenYScale() {
        return lastScreenYScale;
    }

    public void setLastScreenYScale(double lastScreenYScale) {
        this.lastScreenYScale = lastScreenYScale;
    }


    public void setLatestScreenXScale(double latestScreenXScale) {
        lastScreenXScale = this.latestScreenXScale;
        this.latestScreenXScale = latestScreenXScale;
        this.screenXScaleRatio = this.latestScreenXScale / this.lastScreenXScale;
        this.viewPortAffine.setXScale(this.viewPortAffine.getXScale() * this.screenXScaleRatio);
    }

    public void setLastestScreenYScale(double lastestScreenYScale) {
        lastScreenYScale = this.lastestScreenYScale;
        this.lastestScreenYScale = lastestScreenYScale;
        this.screenYScaleRatio = this.lastestScreenYScale / this.lastScreenYScale;
        this.viewPortAffine.setYScale(this.viewPortAffine.getYScale() * this.screenYScaleRatio);
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public void onKeyPressed(KeyEvent e) {
        if (isViewportKeyboardReactive) {
            if (e.getCode() == KeyCode.D) {
                xOffset -= (10 * viewPortAffine.getXScale());
                viewPortAffine.getAffine().appendTranslation(-10, 0);
            } else if (e.getCode() == KeyCode.A) {
                xOffset += (10 * viewPortAffine.getXScale());
                viewPortAffine.getAffine().appendTranslation(10, 0);
            } else if (e.getCode() == KeyCode.S) {
                yOffset -= (10 * viewPortAffine.getYScale());
                viewPortAffine.getAffine().appendTranslation(0, -10);
            } else if (e.getCode() == KeyCode.W) {
                yOffset += (10 * viewPortAffine.getYScale());
                viewPortAffine.getAffine().appendTranslation(0, 10);
            }
        }

    }


    public void shiftViewPort(Vec2d shift) {
//        if (true) {
//                return;
//        }
        xOffset += (shift.x * viewPortAffine.getXScale());
        yOffset += (shift.y * viewPortAffine.getYScale());
        viewPortAffine.getAffine().appendTranslation(shift.x, shift.y);
    }

    public void onMouseWheelMoved(ScrollEvent e) {
        Affine affine = viewPortAffine.getAffine();
        try {
            affine = affine.createInverse();
        } catch (NonInvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }
        double mouseX = e.getSceneX() * affine.getMxx() + e.getSceneY() * affine.getMxy() + affine.getTx();
        double mouseY = e.getSceneX() * affine.getMyx() + e.getSceneY() * affine.getMyy() + affine.getTy();

        if (e.getDeltaY() > 0 && viewPortAffine.getXScale() < 2) {
            viewPortAffine.getAffine().appendTranslation(mouseX, mouseY);
            viewPortAffine.getAffine().appendScale(1.01, 1.01);
            viewPortAffine.getAffine().appendTranslation(-1 * mouseX, -1 * mouseY);
        } else if (e.getDeltaY() < 0 && viewPortAffine.getXScale() > 0.5) {
            viewPortAffine.getAffine().appendTranslation(mouseX, (mouseY));
            viewPortAffine.getAffine().appendScale(.99, .99);
            viewPortAffine.getAffine().appendTranslation(-1 * (mouseX), -1 * (mouseY));
        }
    }

    public void onMouseMoved(MouseEvent e) {
        currentMousePositionOnScreenX = e.getSceneX();
        currentMousePositionOnScreenY = e.getSceneY();
    }

    public AffineWrapper getAffine() {
        return viewPortAffine;
    }

    public void onMouseClicked(MouseEvent e) {
        currentMousePositionOnScreenX = e.getSceneX();
        currentMousePositionOnScreenY = e.getSceneY();
    }

    public void printAffine() {
        System.out.println("X scale = " + viewPortAffine.getXScale() + " Y scale = " + viewPortAffine.getYScale() + " tx = " + viewPortAffine.getXTranslation() + " ty = " + viewPortAffine.getYTranslation() + " x shear " + viewPortAffine.getXShear() + " y shear " + viewPortAffine.getYShear());
    }

    public boolean isViewportKeyboardReactive() {
        return isViewportKeyboardReactive;
    }

    public void setViewportKeyboardReactive(boolean viewportKeyboardReactive) {
        isViewportKeyboardReactive = viewportKeyboardReactive;
    }
}
