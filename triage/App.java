package triage;

import engine.Application;
import engine.support.Vec2d;
import triage.controllers.ScreenController;

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

    @Override
    protected void onTick(long nanosSincePreviousTick) {
        screenController.onTick(nanosSincePreviousTick);
    }

}
