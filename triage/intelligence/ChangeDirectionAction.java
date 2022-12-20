package triage.intelligence;

import engine.ai.Action;
import engine.ai.Status;
import engine.components.PhysicsComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;

import java.util.ArrayList;

public class ChangeDirectionAction extends Action {

    GameObject self;
    ArrayList<GameObject> platformObjects = null;

    double lastXVelocity = 0;
    int direction = 1; // 1 = right -1 = left
    public ChangeDirectionAction(GameObject enemy, ArrayList<GameObject> platformObjects) {
        super();
        this.self = enemy;
        this.platformObjects = platformObjects;
    }

    public ChangeDirectionAction(GameObject enemy) {
        super();
        this.self = enemy;
        this.platformObjects = null;
    }

    @Override
    public Status act() {
        // Self position
        double selfMinXPos = self.getTransformComponent().getPositionOnWorld().x;
        double selfMaxXPos = self.getTransformComponent().getPositionOnWorld().x + self.getTransformComponent().getSizeOnWorld().x;

        // Flag for unsafe position means getting out of platform
        boolean unsafe = false;

        // If there is no platform
        if(platformObjects == null) {
            double platformMinXPos = 10;
            double platformMaxXPos = 950;

            if(selfMinXPos>= platformMinXPos && selfMinXPos<= platformMaxXPos && selfMaxXPos > platformMaxXPos) {
                direction = -1;
                unsafe = true;
            }
            if(selfMaxXPos>= platformMinXPos && selfMaxXPos<= platformMaxXPos && selfMinXPos < platformMinXPos) {
                direction = 1;
                unsafe = true;
            }
        } else {
            // Iterating over all platforms
            for (int i = 0 ; i < platformObjects.size() ; i++) {
                GameObject platform = platformObjects.get(i);
                double platformMinXPos = platform.getTransformComponent().getPositionOnWorld().x;
                double platformMaxXPos = platform.getTransformComponent().getPositionOnWorld().x + platform.getTransformComponent().getSizeOnWorld().x;

                // Checking if position getting out of platform
                if(selfMinXPos>= platformMinXPos && selfMinXPos<= platformMaxXPos && selfMaxXPos > platformMaxXPos) {
                    direction = -1;
                    unsafe = true;
                }
                if(selfMaxXPos>= platformMinXPos && selfMaxXPos<= platformMaxXPos && selfMinXPos < platformMinXPos) {
                    direction = 1;
                    unsafe = true;
                }
            }
        }

        // If it is unsafe it flips the direction of velocity
        if(unsafe == true) {
            PhysicsComponent physicsComponent = (PhysicsComponent) self.getComponent("physics");
            physicsComponent.setVel(new Vec2d(Math.abs(physicsComponent.getVel().x) * direction, physicsComponent.getVel().y));
            return Status.SUCCESS;
        } else {
            return Status.FAIL;
        }
    }
}
