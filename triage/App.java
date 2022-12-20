package triage;

import engine.Application;
import engine.components.AIComponent;
import engine.gameobjects.GameObject;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import triage.controllers.ScreenController;
import triage.gamelogics.KeyboardInputLogics;
import triage.gamelogics.MouseInputLogics;
import triage.generators.GameObjectId;
import triage.intelligence.AirSentryAI;
import triage.intelligence.GroundSentryAI;

import java.util.ArrayList;

public class App extends Application {

    GameState gameState;
    ScreenController screenController;

    public App(String title) {
        super(title);
        gameState = new GameState(this);
        screenController = new ScreenController(gameState);
    }

    public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
        super(title, windowSize, debugMode, fullscreen);
        gameState = new GameState(this);
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
        triggeringAI(nanosSincePreviousTick);
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
