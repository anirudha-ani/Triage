package engine.gameobjects;

import engine.GameWorld;
import engine.UIElement.AffineWrapper;
import engine.components.CollisionComponent;
import engine.components.Component;
import engine.components.TransformComponent;
import engine.hitboxes.HitboxType;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameObject {
    private final ArrayList<Component> _components = new ArrayList<>();
    public TransformComponent transformComponent;
    public String status = "idle";
    private int zIndex = 3;
    private boolean viewPortSensitive = true;
    private String id = "";
    private Color defaultColor = Color.RED;
    private Color hoverColor = Color.TRANSPARENT;
    private Color clickColor = Color.TRANSPARENT;
    private boolean deleted = false;
    private int counter = 0;

    private GameWorld refGameWorld = null;

    public GameObject(String id, TransformComponent transformComponent) {
        this.transformComponent = transformComponent;
        this.transformComponent.setBackGroundColor(defaultColor);
        this.transformComponent.setGameObject(this);
        this.id = id;
    }

    public void addComponent(Component c) {
        this._components.add(c);
    }

    public void removeComponent(Component c) {
        for (int i = _components.size(); i >= 0; i--) {
            if (_components.get(i).equals(c)) {
                _components.remove(i);
            }
        }
    }

    public boolean isViewPortSensitive() {
        return viewPortSensitive;
    }

    public void setViewPortSensitive(boolean viewPortSensitive) {
        this.viewPortSensitive = viewPortSensitive;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
        transformComponent.setBackGroundColor(defaultColor);
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public Color getClickColor() {
        return clickColor;
    }

    public void setClickColor(Color clickColor) {
        this.clickColor = clickColor;
    }

    public Component getComponent(String tag) {
        for (int i = 0; i < _components.size(); i++) {
            if (_components.get(i).getTag() == tag) {
                return _components.get(i);
            }
        }
        return null;
    }


    public String getId() {
        return id;
    }

    public TransformComponent getTransformComponent() {

        return this.transformComponent;
    }

    public void setTransformComponent(TransformComponent transformComponent) {
        this.transformComponent = transformComponent;
    }

    public void onTick(long t) {
        this._components.forEach(component -> component.onTick(t));
    }

    public void lateTick() {

    }

    public void draw(GraphicsContext g, AffineWrapper viewPointAffine) {
        AffineWrapper screenAffine = new AffineWrapper();
        screenAffine.deepCopy(g.getTransform());
        if (viewPortSensitive) {
            g.setTransform(viewPointAffine.getAffine());
            this._components.forEach(gameComponent -> gameComponent.draw(g));
        } else {
            this._components.forEach(gameComponent -> gameComponent.draw(g));
        }
        g.setTransform(screenAffine.getAffine());

    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setStatus(String status) {
        this.status = status;
//        if (this.status == "") {
//            return;
//        }
        if (status != "idle") {
            if (getComponent(status) == null) {
                return;
            }
            if (getComponent("left") != null) {
                getComponent("left").setVisibile(false);
            }
            if (getComponent("right") != null) {
                getComponent("right").setVisibile(false);
            }
            if (getComponent("upRight") != null) {
                getComponent("upRight").setVisibile(false);
            }
            if (getComponent("down") != null) {
                getComponent("down").setVisibile(false);
            }
            if (getComponent("attackRight") != null) {
                getComponent("attackRight").setVisibile(false);
            }
            if (getComponent("attackLeft") != null) {
                getComponent("attackLeft").setVisibile(false);
            }
            if (getComponent("upLeft") != null) {
                getComponent("upLeft").setVisibile(false);
            }
            if (getComponent(status) != null) {
                getComponent(status).setVisibile(true);
            }
        }


    }

    public void setPositionOnWorld(Vec2d newPosition) {
        transformComponent.setPositionOnWorld(newPosition);
        for (Component component : get_components()) {
            component.setPositionOnWorld(newPosition);
        }
        CollisionComponent collisionComponent = (CollisionComponent) getComponent("collisionComponent");
        if (collisionComponent != null) {
            if (collisionComponent.getHitboxType() == HitboxType.AAB) {
//                Vec2d newHitboxPosition = new Vec2d(collisionComponent.getAabHitbox().getTopLeft().x + deltaX, collisionComponent.getAabHitbox().getTopLeft().y + deltaY);
                collisionComponent.getAabHitbox().setTopLeft(newPosition);
            } else {
            }
        } else {
        }
    }

    public ArrayList<Component> get_components() {
        return _components;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public GameWorld getRefGameWorld() {
        return refGameWorld;
    }

    public void setRefGameWorld(GameWorld refGameWorld) {
        this.refGameWorld = refGameWorld;
    }
}
