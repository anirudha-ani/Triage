package triage;

import engine.Application;
import engine.Screen;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import triage.controllers.ScreenController;
import triage.gamelogics.ClickLogics;

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
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {
        ArrayList<String> clickedObjects = getGameState().getGameScreen().onMouseClicked(e);
        ClickLogics clickLogics = new ClickLogics(this);
        clickLogics.executeLogic(clickedObjects);
    }
}
