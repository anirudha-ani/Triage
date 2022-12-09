package triage;

import engine.Application;
import engine.support.Vec2d;
import triage.controllers.ScreenController;

public class App extends Application {

    ScreenController screenController;

    public App(String title) {
        super(title);
        screenController = new ScreenController(this);
    }

    public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
        super(title, windowSize, debugMode, fullscreen);
        screenController = new ScreenController(this);
    }

    @Override
    protected void onTick(long nanosSincePreviousTick) {
        screenController.onTick(nanosSincePreviousTick);
    }
}
