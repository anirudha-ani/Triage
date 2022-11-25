package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.transform.Affine;

public class AffineWrapper {
    private Affine affine;

    public AffineWrapper() {
        this.affine = new Affine();
    }

    public AffineWrapper(Affine affine) {
        this.affine = affine;
    }

    public Affine getAffine() {
        return affine;
    }

    public void setAffine(Affine affine) {
        this.affine = affine;
    }

    public double getXScale() {
        return affine.getMxx();
    }

    public double getYScale() {
        return affine.getMyy();
    }

    public double getXTranslation() {
        return affine.getTx();
    }

    public double getYTranslation() {
        return affine.getTy();
    }

    public double getXShear() {
        return affine.getMxy();
    }

    public double getYShear() {
        return affine.getMyx();
    }

    public void setXScale(double newValue) {
        affine.setMxx(newValue);
    }

    public void setYScale(double newValue) {
        affine.setMyy(newValue);
    }

    public void setXTranslation(double newValue) {
        affine.setTx(newValue);
    }

    public void setYTranslation(double newValue) {
        affine.setTy(newValue);
    }

    public void setXShear(double newValue) {
        affine.setMxy(newValue);
    }

    public void setYShear(double newValue) {
        affine.setMyx(newValue);
    }

    public void deepCopy(Affine newAffine) {
        affine.setMxx(newAffine.getMxx());
        affine.setMxy(newAffine.getMxy());
        affine.setMyx(newAffine.getMyx());
        affine.setMyy(newAffine.getMyy());
        affine.setTx(newAffine.getTx());
        affine.setTy(newAffine.getTy());
    }

    public void scaleMaintainAspectRatio(Vec2d newWindowSize, Vec2d originalWindowSize) {
        double newAdjustedHeight = newWindowSize.x * originalWindowSize.y / originalWindowSize.x;
        double newAdjustedWidth = newWindowSize.y * originalWindowSize.x / originalWindowSize.y;

        if (newAdjustedHeight / originalWindowSize.y > newAdjustedWidth / originalWindowSize.x) {
            setXScale(newAdjustedWidth / originalWindowSize.x);
            setYScale(newAdjustedWidth / originalWindowSize.x);
        } else {
            setXScale(newAdjustedHeight / originalWindowSize.y);
            setYScale(newAdjustedHeight / originalWindowSize.y);
        }
    }
}
