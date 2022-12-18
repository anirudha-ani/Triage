package triage.gamelogics;

import engine.components.PhysicsComponent;
import engine.support.Vec2d;
import engine.systems.KeyEventHappened;
import javafx.scene.input.KeyCode;
import triage.App;
import triage.generators.GameObjectId;

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
                PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent("physics");

                // For any reason if the physics component is not there
                if (physicsComponent == null) {
                    return;
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.D)) {
                    gameObject.setStatus("right");

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
                    gameObject.setStatus("left");

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
                    gameObject.setStatus("down");
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.W)) {
                    if (physicsComponent.isGravityActivated()) {
                        return;
                    }

                    physicsComponent.setVel(new Vec2d(physicsComponent.getVel().x, 0));
                    physicsComponent.applyImpulse(new Vec2d(0, -50));

                    // Player will be on Air so activating gravity
                    physicsComponent.setGravityActivated(true);
                    gameObject.setStatus("up");
                }
            }

        });
    }
}
