package triage.gamelogics;

import engine.components.AudioComponent;
import engine.components.PhysicsComponent;
import engine.components.SpriteComponent;
import engine.components.StatsComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import engine.systems.KeyEventHappened;
import javafx.scene.input.KeyCode;
import triage.App;
import triage.blueprints.AudioId;
import triage.generators.ObjectIds.GameObjectId;
import triage.savefiles.SaveFileTags;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
                    keyEventHappened.getActiveKeyEvents().remove(KeyCode.L);
                    GameObject player = gameObject;
                    if(player != null ) {
                        ArrayList<GameObject> groundSentries = this.currentApp.getGameState().getGameWorld().getGameObjects(GameObjectId.GROUND_SENTRY.toString());
                        ArrayList<GameObject> airSentries = this.currentApp.getGameState().getGameWorld().getGameObjects(GameObjectId.AIR_SENTRY.toString());

                        boolean contact = false;
                        StatsComponent playerStats = (StatsComponent) player.getComponent("stats");
                        for (int i = 0 ; i < groundSentries.size() ; i++) {
                            Double maxX = groundSentries.get(i).getTransformComponent().getPositionOnWorld().x + groundSentries.get(i).getTransformComponent().getSizeOnWorld().x;
                            if(player.getTransformComponent().getPositionOnWorld().x-40 <= maxX && player.getTransformComponent().getPositionOnWorld().x + player.getTransformComponent().getSizeOnWorld().x > maxX) {
                                AudioComponent audioClip = new AudioComponent("triage/audiofiles/slash.mp3", false);
                                audioClip.setLocalId(AudioId.BULLET.toString());
                                audioClip.playAudio();
                                contact = true;
                                StatsComponent stats = (StatsComponent) groundSentries.get(i).getComponent("stats");
                                stats.setHealth(stats.getHealth() - playerStats.getAttack());
                            }
                        }
                        groundSentries.forEach(sentry->{
                            StatsComponent stats = (StatsComponent) sentry.getComponent("stats");
                            if(stats.getHealth() <= 0) {
                                currentApp.getGameState().getGameWorld().removeGameObject(sentry);
                                int noOfCoins = Integer.parseInt(currentApp.getGameState().getSaveFile().readElements(SaveFileTags.COINS.toString()));
                                currentApp.getGameState().getSaveFile().modifyElements(SaveFileTags.COINS.toString(), Integer.toString(noOfCoins+100));
                            }
                        });

                        for (int i = 0 ; i < airSentries.size() ; i++) {
                            Double maxX = airSentries.get(i).getTransformComponent().getPositionOnWorld().x + airSentries.get(i).getTransformComponent().getSizeOnWorld().x;
                            if(player.getTransformComponent().getPositionOnWorld().x-40 <= maxX && player.getTransformComponent().getPositionOnWorld().x + player.getTransformComponent().getSizeOnWorld().x > maxX) {
                                AudioComponent audioClip = new AudioComponent("triage/audiofiles/slash.mp3", false);
                                audioClip.setLocalId(AudioId.BULLET.toString());
                                audioClip.playAudio();
                                contact = true;
                                StatsComponent stats = (StatsComponent) airSentries.get(i).getComponent("stats");
                                stats.setHealth(stats.getHealth() - playerStats.getAttack());
                            }
                        }
                        airSentries.forEach(sentry->{
                            StatsComponent stats = (StatsComponent) sentry.getComponent("stats");
                            if(stats.getHealth() <= 0) {
                                currentApp.getGameState().getGameWorld().removeGameObject(sentry);
                                int noOfCoins = Integer.parseInt(currentApp.getGameState().getSaveFile().readElements(SaveFileTags.COINS.toString()));
                                currentApp.getGameState().getSaveFile().modifyElements(SaveFileTags.COINS.toString(), Integer.toString(noOfCoins+100));
                            }
                        });

                        if (contact == false) {
                            AudioComponent audioClip = new AudioComponent("triage/audiofiles/swordwhip.mp3", false);
                            audioClip.setLocalId(AudioId.BULLET.toString());
                            audioClip.playAudio();
                        }
                    }
                }

                if (keyEventHappened.getActiveKeyEvents().contains(KeyCode.L) && statsComponent.getFacing() == "right") {
                    movementHappening = true;
                    gameObject.setStatus("attackRight");
                    keyEventHappened.getActiveKeyEvents().remove(KeyCode.L);
                    GameObject player = this.currentApp.getGameState().getGameWorld().getGameObject(GameObjectId.player.toString());
                    if(player != null && player.status == "attackRight") {
                        ArrayList<GameObject> groundSentries = this.currentApp.getGameState().getGameWorld().getGameObjects(GameObjectId.GROUND_SENTRY.toString());
                        ArrayList<GameObject> airSentries = this.currentApp.getGameState().getGameWorld().getGameObjects(GameObjectId.AIR_SENTRY.toString());

                        boolean contact = false;
                        StatsComponent playerStats = (StatsComponent) player.getComponent("stats");

                        for (int i = 0 ; i < groundSentries.size() ; i++) {
                            Double minX = groundSentries.get(i).getTransformComponent().getPositionOnWorld().x;
                            if(player.getTransformComponent().getPositionOnWorld().x+60 >= minX && player.getTransformComponent().getPositionOnWorld().x < minX) {
                                AudioComponent audioClip = new AudioComponent("triage/audiofiles/slash.mp3", false);
                                audioClip.setLocalId(AudioId.BULLET.toString());
                                audioClip.playAudio();
                                contact = true;
                                StatsComponent stats = (StatsComponent) groundSentries.get(i).getComponent("stats");
                                stats.setHealth(stats.getHealth() - playerStats.getAttack());
                            }
                        }

                        groundSentries.forEach(sentry->{
                            StatsComponent stats = (StatsComponent) sentry.getComponent("stats");
                            if(stats.getHealth() <= 0) {
                                currentApp.getGameState().getGameWorld().removeGameObject(sentry);
                                int noOfCoins = Integer.parseInt(currentApp.getGameState().getSaveFile().readElements(SaveFileTags.COINS.toString()));
                                currentApp.getGameState().getSaveFile().modifyElements(SaveFileTags.COINS.toString(), Integer.toString(noOfCoins+100));
                            }
                        });

                        for (int i = 0 ; i < airSentries.size() ; i++) {
                            Double maxX = airSentries.get(i).getTransformComponent().getPositionOnWorld().x + airSentries.get(i).getTransformComponent().getSizeOnWorld().x;
                            if(player.getTransformComponent().getPositionOnWorld().x-40 <= maxX && player.getTransformComponent().getPositionOnWorld().x + player.getTransformComponent().getSizeOnWorld().x > maxX) {
                                AudioComponent audioClip = new AudioComponent("triage/audiofiles/slash.mp3", false);
                                audioClip.setLocalId(AudioId.BULLET.toString());
                                audioClip.playAudio();
                                contact = true;
                                StatsComponent stats = (StatsComponent) airSentries.get(i).getComponent("stats");
                                stats.setHealth(stats.getHealth() - playerStats.getAttack());
                            }
                        }
                        airSentries.forEach(sentry->{
                            StatsComponent stats = (StatsComponent) sentry.getComponent("stats");
                            if(stats.getHealth() <= 0) {
                                int noOfCoins = Integer.parseInt(currentApp.getGameState().getSaveFile().readElements(SaveFileTags.COINS.toString()));
                                currentApp.getGameState().getSaveFile().modifyElements(SaveFileTags.COINS.toString(), Integer.toString(noOfCoins+100));
                                currentApp.getGameState().getGameWorld().removeGameObject(sentry);
                            }
                        });

                        if (contact == false) {
                            AudioComponent audioClip = new AudioComponent("triage/audiofiles/swordwhip.mp3", false);
                            audioClip.setLocalId(AudioId.BULLET.toString());
                            audioClip.playAudio();
                        }
                    }
                }

                /**
                 * This is extremely hacky way to complete the attack animation.
                 * It assumes that your attack sprite has 3 frames
                 * Not an ideal solution
                 */
                if(movementHappening == false && (gameObject.status == "attackRight" || gameObject.status == "attackLeft")) {
                    SpriteComponent playerSprite = (SpriteComponent) gameObject.getComponent(gameObject.status);

                    if(playerSprite != null && playerSprite.spriteIndexToLoad != 1) {
                        movementHappening = true;
                    } else {
                        if(gameObject.status == "attackRight") {
                            gameObject.setStatus("right");
                        } else {
                            gameObject.setStatus("left");
                        }
                        movementHappening = true;

                    }
                }

                if(!movementHappening) {
                    gameObject.setStatus("idle");
                }
            }
        });
    }
}
