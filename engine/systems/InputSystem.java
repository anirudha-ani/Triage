package engine.systems;

import engine.GameWorld;
import engine.UIElement.AffineWrapper;
import engine.UIElement.Viewport;
import engine.components.Component;
import engine.components.PhysicsComponent;
import engine.components.RayComponent;
import engine.components.TransformComponent;
import engine.gameobjects.GameObject;
import engine.hitboxes.HitboxType;
import engine.support.Vec2d;
import javafx.scene.input.*;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.util.ArrayList;

public class InputSystem extends System {
    private final ArrayList<GameObject> mouseInputReactableObject = new ArrayList<>();
    private final ArrayList<GameObject> keyinputReactableObject = new ArrayList<>();

    private double currentMousePositionOnScreenX;
    private double currentMousePositionOnScreenY;

    public InputSystem(GameWorld gameWorld) {
        super(gameWorld);
    }

    public void addObject(GameObject g) {
        if (g.getId().contains("button")) {
            mouseInputReactableObject.add(g);
        }
        if (g.getId() == "player") {
            keyinputReactableObject.add(g);
            mouseInputReactableObject.add(g);
        }

    }

    public void removeObject(GameObject g) {
        mouseInputReactableObject.remove(g);
        if (g.getId() == "player") {
            keyinputReactableObject.remove(g);
        }

    }

    public void onKeyPressed(KeyEvent e, AffineWrapper viewPortAffine) {
        if (this.getRefGameWorld().getCurrentGraphicsContext() == null) {
            return;
        }
        keyinputReactableObject.forEach(gameObject -> {
            TransformComponent transform = gameObject.getTransformComponent();

            Component collisionComponent = gameObject.getComponent("collisionComponent");

            Affine affine;

            AffineWrapper screenAffine = new AffineWrapper();
            screenAffine.deepCopy(this.getRefGameWorld().getCurrentGraphicsContext().getTransform());

            if (gameObject.isViewPortSensitive()) {
                affine = viewPortAffine.getAffine();
            } else {
                affine = this.getRefGameWorld().getCurrentGraphicsContext().getTransform();
            }

            double deltaX = 0;
            double deltaY = 0;

            double prevX = transform.getPositionOnWorld().x;
            double prevY = transform.getPositionOnWorld().y;
            PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");

            if (physicsComponent == null) {
                return;
            }
            if (e.getCode() == KeyCode.D) {
                deltaX = 10;
                gameObject.setStatus("right");
                if (physicsComponent.getVel().x < 0) {
                    physicsComponent.setVel(new Vec2d(0, physicsComponent.getVel().y));
                }
                physicsComponent.applyImpulse(new Vec2d(5, 0));
            } else if (e.getCode() == KeyCode.A) {
                deltaX = -10;
                gameObject.setStatus("left");
                if (physicsComponent.getVel().x > 0) {
                    physicsComponent.setVel(new Vec2d(0, physicsComponent.getVel().y));
                }
                physicsComponent.applyImpulse(new Vec2d(-5, 0));
            } else if (e.getCode() == KeyCode.S) {
                deltaY = 10;
                gameObject.setStatus("down");
            } else if (e.getCode() == KeyCode.W) {
                deltaY = -10;
                if (physicsComponent.isGravityActivated()) {
                    return;
                }
                if (physicsComponent.getVel().y > 0) {
                    physicsComponent.setVel(new Vec2d(physicsComponent.getVel().x, 0));
                }
                physicsComponent.applyForce(new Vec2d(0, -10));
                physicsComponent.setGravityActivated(true);
                gameObject.setStatus("up");
            }


            Viewport viewport = getRefGameWorld().getRefScreen().getViewport();

            if (viewport != null) {
                getRefGameWorld().getRefScreen().getViewport().shiftViewPort(new Vec2d(-deltaX, -deltaY));
            }
        });
    }

    public void onKeyReleased(KeyEvent e, AffineWrapper viewPortAffine) {
        keyinputReactableObject.forEach(gameObject -> {
            gameObject.setStatus("idle");
        });

//    System.out.println("onKeyReleased called");
    }


    public void onMouseWheelMoved(ScrollEvent e) {

    }

    public void onMouseMoved(MouseEvent e, AffineWrapper viewPointAffine) {
        if (this.getRefGameWorld().getCurrentGraphicsContext() == null) {
            return;
        }


        currentMousePositionOnScreenX = e.getSceneX();
        currentMousePositionOnScreenY = e.getSceneY();
        mouseInputReactableObject.forEach(gameObject -> {
            if (gameObject.getId() == "static" || gameObject.isDeleted()) {
                return;
            }
            TransformComponent transform = gameObject.getTransformComponent();

            Affine affine;

            AffineWrapper screenAffine = new AffineWrapper();
            screenAffine.deepCopy(this.getRefGameWorld().getCurrentGraphicsContext().getTransform());

            if (gameObject.isViewPortSensitive()) {
                affine = viewPointAffine.getAffine();
            } else {
                affine = this.getRefGameWorld().getCurrentGraphicsContext().getTransform();
            }

            double originX = transform.getPositionOnWorld().x * affine.getMxx() + transform.getPositionOnWorld().y * affine.getMxy() + affine.getTx();
            double originY = transform.getPositionOnWorld().x * affine.getMyx() + transform.getPositionOnWorld().y * affine.getMyy() + affine.getTy();
            double sizeX = transform.getSizeOnWorld().x * affine.getMxx();
            double sizeY = transform.getSizeOnWorld().y * affine.getMyy();

            if (e.getX() >= originX && e.getX() <= originX + sizeX && e.getY() >= originY && e.getY() <= originY + sizeY) {
                transform.setBackGroundColor(gameObject.getHoverColor());
                transform.setMouseHovering(true);
            } else {
                transform.setBackGroundColor(gameObject.getDefaultColor());
                transform.setMouseHovering(false);
                transform.setMouseGrabbing(false);

            }
        });
    }

    public void onMouseDragged(MouseEvent e, AffineWrapper viewPointAffine) {
        if (e.getButton() == MouseButton.SECONDARY) {
            GameObject player = getRefGameWorld().getGameObject("player");

            if (player != null) {
                RayComponent rayComponent = (RayComponent) player.getComponent("ray");

                if (rayComponent != null) {
                    Affine affine = viewPointAffine.getAffine();
                    try {
                        affine = affine.createInverse();
                    } catch (NonInvertibleTransformException ex) {
                        throw new RuntimeException(ex);
                    }
                    double mouseX = e.getSceneX() * affine.getMxx() + e.getSceneY() * affine.getMxy() + affine.getTx();
                    double mouseY = e.getSceneX() * affine.getMyx() + e.getSceneY() * affine.getMyy() + affine.getTy();

                    rayComponent.setStart(player.getTransformComponent().getPositionOnWorld());
                    rayComponent.setEnd(new Vec2d(mouseX, mouseY));
                    rayComponent.setShow(true);
                }
            }
        }

        mouseInputReactableObject.forEach(gameObject -> {
            if (gameObject.getId() == "static" || gameObject.isDeleted()) {
                return;
            }

            TransformComponent transform = gameObject.getTransformComponent();
            Component spriteComponent = gameObject.getComponent("spriteComponent");
            Component collisionComponent = gameObject.getComponent("collisionComponent");
            if (!transform.isMouseGrabbing()) {
                return;
            }

            Affine affine;

            AffineWrapper screenAffine = new AffineWrapper();
            screenAffine.deepCopy(this.getRefGameWorld().getCurrentGraphicsContext().getTransform());

            if (gameObject.isViewPortSensitive()) {
                affine = viewPointAffine.getAffine();
            } else {
                affine = this.getRefGameWorld().getCurrentGraphicsContext().getTransform();
            }

            double deltaX = (e.getSceneX() - currentMousePositionOnScreenX) / affine.getMxx();
            double deltaY = (e.getSceneY() - currentMousePositionOnScreenY) / affine.getMyy();
            Vec2d newPosition = new Vec2d(transform.getPositionOnWorld().x + deltaX, transform.getPositionOnWorld().y + deltaY);

            transform.setPositionOnWorld(newPosition);
            if (spriteComponent != null) {
                Vec2d newSpritePosition = new Vec2d(spriteComponent.getPositionOnWorld().x + deltaX, spriteComponent.getPositionOnWorld().y + deltaY);

                spriteComponent.setPositionOnWorld((newSpritePosition));
            }
            if (collisionComponent != null) {
                if (collisionComponent.getHitboxType() == HitboxType.AAB) {
                    Vec2d newHitboxPosition = new Vec2d(collisionComponent.getAabHitbox().getTopLeft().x + deltaX, collisionComponent.getAabHitbox().getTopLeft().y + deltaY);
                    collisionComponent.getAabHitbox().setTopLeft(newHitboxPosition);
                } else {
                }
            } else {
            }

        });
        currentMousePositionOnScreenX = e.getSceneX();
        currentMousePositionOnScreenY = e.getSceneY();
    }

    public ArrayList<String> onMousePressed(MouseEvent e, AffineWrapper viewPointAffine) {
        if (e.getButton() == MouseButton.SECONDARY) {
            GameObject player = getRefGameWorld().getGameObject("player");

            if (player != null) {
                RayComponent rayComponent = (RayComponent) player.getComponent("ray");

                if (rayComponent != null) {
                    Affine affine = viewPointAffine.getAffine();
                    try {
                        affine = affine.createInverse();
                    } catch (NonInvertibleTransformException ex) {
                        throw new RuntimeException(ex);
                    }
                    double mouseX = e.getSceneX() * affine.getMxx() + e.getSceneY() * affine.getMxy() + affine.getTx();
                    double mouseY = e.getSceneX() * affine.getMyx() + e.getSceneY() * affine.getMyy() + affine.getTy();

                    rayComponent.setStart(player.getTransformComponent().getPositionOnWorld());
                    rayComponent.setEnd(new Vec2d(mouseX, mouseY));
                    rayComponent.setShow(true);
                }
            }
        }

        ArrayList<String> objectIDs = new ArrayList<>();
        mouseInputReactableObject.forEach(gameObject -> {
            if (gameObject.isDeleted()) {
                return;
            }
            TransformComponent transform = gameObject.getTransformComponent();
            if (transform.isMouseHovering()) {

                transform.setBackGroundColor(gameObject.getClickColor());
                if (gameObject.getComponent("draggable") != null) {
                    transform.setMouseGrabbing(true);
                }
                objectIDs.add(gameObject.getId());

            }
        });
        return objectIDs;
    }

    public void onMouseReleased(MouseEvent e, AffineWrapper viewPointAffine) {


        mouseInputReactableObject.forEach(gameObject -> {
            if (gameObject.getId() == "static" || gameObject.isDeleted()) {
                return;
            }
            TransformComponent transform = gameObject.getTransformComponent();

            if (transform.isMouseHovering()) {
                transform.setBackGroundColor(gameObject.getDefaultColor());
                transform.setMouseGrabbing(false);
            }
        });
    }

    public ArrayList<String> onMouseClick(MouseEvent e, AffineWrapper viewPointAffine) {
        ArrayList<String> objectIDs = new ArrayList<>();

        mouseInputReactableObject.forEach(gameObject -> {
            if (gameObject.getId() == "static" || gameObject.isDeleted()) {
                return;
            }
            TransformComponent transform = gameObject.getTransformComponent();

            if (transform.isMouseHovering()) {
                if (gameObject.getComponent("clickable") != null) {
                    objectIDs.add(gameObject.getId());
                }
            }
        });

        return objectIDs;
    }
}
