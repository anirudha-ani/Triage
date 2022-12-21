package triage;

import engine.Application;
import engine.components.AIComponent;
import engine.components.AudioComponent;
import engine.components.StatsComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import triage.controllers.ScreenController;
import triage.controllers.ScreensNames;
import triage.gamelogics.CollisionLogics;
import triage.gamelogics.KeyboardInputLogics;
import triage.gamelogics.MouseInputLogics;
import triage.generators.ObjectIds.GameObjectId;
import triage.intelligence.AirSentryAI;
import triage.intelligence.GroundSentryAI;
import triage.savefiles.SaveFileTags;

import java.util.ArrayList;

public class App extends Application {

    GameState gameState;
    ScreenController screenController;

    public App(String title) {
        super(title);
        this.gameState = new GameState(this);
        this.gameState.setSaveFile("triage/savefiles/savegame.xml");
        screenController = new ScreenController(gameState);

        /**
         * TO READ and WRITE to game file do this
         */

        // this.getGameState().getSaveFile().readElements(SaveFileTags.COINS.toString());
        // this.getGameState().getSaveFile().modifyElements(SaveFileTags.COINS.toString(), "3");
        int coins = Integer.parseInt(this.getGameState().getSaveFile().readElements((SaveFileTags.COINS.toString())));
        this.gameState.setCoinCount(coins);
        String level = this.getGameState().getSaveFile().readElements(SaveFileTags.LEVEL.toString());
        this.gameState.setLevel2Saved(level.equals("2"));

    }

    public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
        super(title, windowSize, debugMode, fullscreen);
        this.gameState = new GameState(this);
        this.gameState.setSaveFile("triage/savefiles/savegame.xml");
        screenController = new ScreenController(gameState);
    }

    public GameState getGameState() {
        return gameState;
    }

    public ScreenController getScreenController() {
        return screenController;
    }

    @Override
    protected void onTick(long nanosSincePreviousTick) {

        screenController.onTick(nanosSincePreviousTick);
        gameState.getGameWorld().tick(nanosSincePreviousTick);
        KeyboardInputLogics keyboardInputLogics = new KeyboardInputLogics(this);
        keyboardInputLogics.executeKeyInputLogic(getGameState().getGameWorld().getKeyEventHappened());
        CollisionLogics collisionLogics = new CollisionLogics(getGameState());
        collisionLogics.executeCollisionLogic();
        triggeringAI(nanosSincePreviousTick);
        ArrayList<AudioComponent> runningAudio = getGameState().getRunningAudio();


        // This hacky code is there to stop audio from playing in the middle of a video sequence
        if(gameState.isVideoPlaying == false ) {
            runningAudio.forEach(audioComponent -> {
                if(audioComponent.isPlaying == false && audioComponent.playedAfterVideo == false) {
                    audioComponent.playedAfterVideo = true;
                    audioComponent.playAudio();
                }
            });
        }

        if(isGameOver() == true) {
            screenController.reloadLevel();
        }

    }

    @Override
    protected void onMouseClicked(MouseEvent e) {
        ArrayList<String> clickedObjects = getGameState().getGameScreen().onMouseClicked(e);
        MouseInputLogics clickLogics = new MouseInputLogics(this);
        clickLogics.executeClickLogic(clickedObjects);
    }

    @Override
    protected void onMouseReleased(MouseEvent e) {
        getGameState().getGameScreen().onMouseReleased(e);
        MouseInputLogics clickLogics = new MouseInputLogics(this);
        clickLogics.executeClickReleaseLogic(e);
    }

    public boolean isGameOver() {

        if(screenController.getCurrentScreen() == ScreensNames.LevelOne || screenController.getCurrentScreen() == ScreensNames.LevelTwo) {
            GameObject player = getGameState().getGameWorld().getGameObject(GameObjectId.player.toString());
            if(player != null) {
                if(player.getTransformComponent().getPositionOnWorld().y > 540) {
                    return true;
                }
                StatsComponent playerStat = (StatsComponent) player.getComponent("stats");
                if(playerStat.getHealth()<=0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void triggeringAI(long nanosSincePreviousTick) {

        ArrayList<GameObject> gameObjects = getGameState().getGameWorld().getGameObjects();
        ArrayList<GameObject> platformObjects = getGameState().getGameWorld().getGameObjects(GameObjectId.HIDDEN_RECTANGLE_HITBOX.toString());

        gameObjects.forEach(gameObject -> {
            if (gameObject.getId() == GameObjectId.GROUND_SENTRY.toString()) {
                AIComponent aiComponent = (AIComponent) gameObject.getComponent("ai");
                if (aiComponent != null) {
                    GroundSentryAI sentryAI = (GroundSentryAI) aiComponent.getAi();
                    if (sentryAI == null) {
                        aiComponent.setAi(new GroundSentryAI(gameObject, platformObjects));
                    } else {
                        sentryAI.update(nanosSincePreviousTick);
                    }
                }
            }

            if (gameObject.getId() == GameObjectId.AIR_SENTRY.toString()) {
                AIComponent aiComponent = (AIComponent) gameObject.getComponent("ai");
                if (aiComponent != null) {
                    AirSentryAI sentryAI = (AirSentryAI) aiComponent.getAi();
                    if (sentryAI == null) {
                        aiComponent.setAi(new AirSentryAI(gameObject));
                    } else {
                        sentryAI.update(nanosSincePreviousTick);
                    }
                }
            }
        });
    }
}
