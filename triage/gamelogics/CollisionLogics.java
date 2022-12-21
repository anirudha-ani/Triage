package triage.gamelogics;

import engine.components.AudioComponent;
import engine.components.PhysicsComponent;
import engine.components.StatsComponent;
import engine.gameobjects.GameObject;
import engine.systems.CollisionHappened;
import javafx.scene.paint.Color;
import triage.GameState;
import triage.blueprints.AudioId;
import triage.generators.ObjectIds.GameObjectId;

import java.util.ArrayList;

public class CollisionLogics {
    GameState gameState;
    public CollisionLogics(GameState gameState) {
        this.gameState = gameState;
    }

    public void executeCollisionLogic() {
        ArrayList<CollisionHappened> collisionList = this.gameState.getGameWorld().collisionHappened;
        collisionList.forEach(collisionHappened -> {


            if (collisionHappened.getFirstObject().getId() == GameObjectId.BULLET.toString()) {
                handleBullet(collisionHappened.getSecondObject(), collisionHappened.getFirstObject());
            }
            if (collisionHappened.getSecondObject().getId() == GameObjectId.BULLET.toString()) {
                handleBullet(collisionHappened.getFirstObject(), collisionHappened.getSecondObject());
            }
        });


    }

    public void handleBullet(GameObject impactedObject, GameObject bullet) {
        StatsComponent impactedObjectStats = (StatsComponent) impactedObject.getComponent("stats");

        if(impactedObjectStats != null) {
            impactedObjectStats.setHealth(impactedObjectStats.getHealth() - 50);

            if(impactedObjectStats.getHealth() <= 0) {
                this.gameState.getGameWorld().removeGameObject(impactedObject);
            }
        }

        this.gameState.getGameWorld().removeGameObject(bullet);
    }
}
