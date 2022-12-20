package triage.gamelogics;

import engine.components.PhysicsComponent;
import engine.components.StatsComponent;
import engine.support.Vec2d;
import engine.systems.KeyEventHappened;
import javafx.scene.input.KeyCode;
import triage.App;
import triage.generators.ObjectIds.GameObjectId;

public class KeyboardInputLogics {
    App currentApp;

    public KeyboardInputLogics(App app) {
        this.currentApp = app;
    }

    public void executeKeyInputLogic(KeyEventHappened keyEventHappened) {
        currentApp.getGameState().setMicroSecondPassedLastKeyExecution(
                currentApp.getGameState().getMicroSecondPassedLastKeyExecution()
                        + this.currentApp.getGameState().getMicroSecondPassedLastTick());

        // For now, I am only executing keyboard related logic after 50000 microsecond
        // Otherwise everything becomes too fast

        if (currentApp.getGameState().getMicroSecondPassedLastKeyExecution() >= 50000) {
            currentApp.getGameState().setMicroSecondPassedLastKeyExecution(0);
        } else {
            return;
        }

        keyEventHappened.getKeyInputReactableObject().forEach(gameObject -> {


            // This logic is only applicable for player
            if (gameObject.getId() == GameObjectId.player.toString()) {
                boolean movementHappening = false;
                PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");
                StatsComponent statsComponent = (StatsComponent) gameObject.getComponent("stats");

                // For any reason if the physics component is not there
                if (physicsComponent == null) {
                    return;
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.D)) {
                    movementHappening = true;
                    gameObject.setStatus("right");
                    statsComponent.setFacing("right");

                    /**
                     *  If the player is going on the opposite direction
                     *  This logic will put the player to stop immediately
                     *  To avoid jello effect
                     */
                    if (physicsComponent.getVel().x < 0) {
                        physicsComponent.setVel(new Vec2d(0, physicsComponent.getVel().y));
                    }

                    physicsComponent.applyImpulse(new Vec2d(5, 0));
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.A)) {
                    movementHappening = true;
                    gameObject.setStatus("left");
                    statsComponent.setFacing("left");

                    /**
                     *  If the player is going on the opposite direction
                     *  This logic will put the player to stop immediately
                     *  To avoid jello effect
                     */

                    if (physicsComponent.getVel().x > 0) {
                        physicsComponent.setVel(new Vec2d(0, physicsComponent.getVel().y));
                    }
                    
                    physicsComponent.applyImpulse(new Vec2d(-5, 0));
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.S)) {
                    movementHappening = true;
                    gameObject.setStatus("down");
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.W)) {
                    movementHappening = true;
                    if (physicsComponent.isGravityActivated()) {
                        return;
                    }

                    physicsComponent.setVel(new Vec2d(physicsComponent.getVel().x, 0));
                    physicsComponent.applyImpulse(new Vec2d(0, -50));

                    // Player will be on Air so activating gravity
                    physicsComponent.setGravityActivated(true);
                    gameObject.setStatus("up");
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.L) && statsComponent.getFacing() == "left") {
                    movementHappening = true;
                    gameObject.setStatus("attackLeft");
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.L) && statsComponent.getFacing() == "right") {
                    movementHappening = true;
                    gameObject.setStatus("attackRight");
                }


                if(!movementHappening) {
                    gameObject.setStatus("idle");
                }
            }
        });
    }
}
