package triage.controllers;

import triage.App;
import triage.blueprints.GameAssets;

enum ScreensNames {
    MenuScreen,
    LevelOne,
    LevelTwo
}

public class ScreenController {

    App app;
    long microSecondPassedSincePreviousTick = -1;
    GameAssets gameAssets;

    public ScreenController(App app) {
        this.app = app;
        gameAssets = new GameAssets();
    }

    public void onTick(long nanosSincePreviousTick) {
        /**
         The reason behind not aggregating first tick is
         First tick value is really high because game is being initialized
         So it is better to skip the first tick
         And capture the time from second tick
         **/

        if (microSecondPassedSincePreviousTick == -1) {
            microSecondPassedSincePreviousTick = 0;

            /**
             In the first tick we have to load resources
             Cause if you try it before first tick it will throw error
             **/
            gameAssets.LoadResources();
        } else {
            microSecondPassedSincePreviousTick = (nanosSincePreviousTick / 1000);
        }
    }
}
