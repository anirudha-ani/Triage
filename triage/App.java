package triage;

import engine.Application;
import engine.support.Vec2d;

public class App extends Application {
    public App(String title) {
        super(title);
    }

    public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
        super(title, windowSize, debugMode, fullscreen);
    }
}
