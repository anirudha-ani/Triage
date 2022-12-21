package triage.controllers;

import engine.GameWorld;
import engine.Screen;
import engine.components.AudioComponent;
import engine.components.VideoComponent;
import triage.GameState;
import triage.blueprints.AudioId;
import triage.blueprints.GameAssets;
import triage.blueprints.GameWorldBluePrint;

public class ScreenController {

    GameState currentGameState;
    ScreensNames currentScreen = null;

    public ScreenController(GameState state) {
        this.currentGameState = state;
        currentGameState.setGameAssets(new GameAssets());
    }

    public void onTick(long nanosSincePreviousTick) {
        /**
         The reason behind not aggregating first tick is
         First tick value is really high because game is being initialized
         So it is better to skip the first tick
         And capture the time from second tick
         **/

        if (!currentGameState.getFirstTickHappened()) {
            executeOnFirstTick();
            currentGameState.setFirstTickHappened(true);
        } else {
            currentGameState.setMicroSecondPassedLastTick(nanosSincePreviousTick / 1000);
        }
    }

    // Things to execute only on first tick
    public void executeOnFirstTick() {
        /**
         In the first tick we have to load game resources like spritesheets
         Cause if you try it before first tick it will throw error
         and you should load this just one time because it is a heavy operation
         **/
        currentGameState.getGameAssets().LoadResources();
        playIntroSequence();
    }

    public void playIntroSequence() {
        resetScreen();
        /**
         * The reason playvideo is in state because I always one to keep just one instance
         * of video component.
         */

        currentGameState.playVideo("triage/videofiles/Opening.mp4", false);
        switchToMenuScreen();
    }

    public void playEndCredit() {
        currentGameState.playVideo("triage/videofiles/EndCredit.mp4", false);

        switchToMenuScreen();
    }

    /**
     * These switch screen methods do three things
     * Reset the current screen/GameWorld
     * Create a new Gameworld and populate it using Blueprints methods
     * Create a new screen which has a reference of this game world
     */

    public void switchToMenuScreen() {
        // This is necessary before switching to any new screen
        resetScreen();

        currentScreen = ScreensNames.MenuScreen;

        AudioComponent audioClip = new AudioComponent("triage/audiofiles/MainMenu.mp3", true);
        audioClip.setLocalId(AudioId.BACKGROUND_STAGE1.toString());
//        audioClip.playAudio();
        currentGameState.addAudio(audioClip);

        // Creating an instance of the new screen
        currentGameState.setGameScreen(
                new Screen(
                        ScreensNames.MenuScreen.toString(),
                        this.currentGameState.getCurrentApp().getCurrentScreenSize(),
                        currentGameState.getGameWorld(),
                        false));

        // App -> Application (parent) also saves this value
        this.currentGameState.getCurrentApp().setCurrentScreenId(ScreensNames.MenuScreen.toString());
        this.currentGameState.getCurrentApp().setCurrentScreen(currentGameState.getGameScreen());

        // Setting up the blueprint
        this.currentGameState.setBluePrint(new GameWorldBluePrint(currentGameState));

        // Invoking blueprint function to populate this particular screen
        this.currentGameState.getBluePrint().populateMenuScreen();

        // Resetting the time counter
        currentGameState.setMicroSecondPassedLastTick(0);
    }

    public void switchToCartScreen() {
        // This is necessary before switching to any new screen
        resetScreenWithoutAudio();

        currentScreen = ScreensNames.CartScreen;

        // Creating an instance of the new screen
        currentGameState.setGameScreen(
                new Screen(
                        ScreensNames.CartScreen.toString(),
                        this.currentGameState.getCurrentApp().getCurrentScreenSize(),
                        currentGameState.getGameWorld(),
                        false));

        // App -> Application (parent) also saves this value
        this.currentGameState.getCurrentApp().setCurrentScreenId(ScreensNames.CartScreen.toString());
        this.currentGameState.getCurrentApp().setCurrentScreen(currentGameState.getGameScreen());

        // Setting up the blueprint
        this.currentGameState.setBluePrint(new GameWorldBluePrint(currentGameState));

        // Invoking blueprint function to populate this particular screen
        this.currentGameState.getBluePrint().populateCartScreen();

        // Resetting the time counter
        currentGameState.setMicroSecondPassedLastTick(0);
    }

    public void switchToLoadScreen() {
        // This is necessary before switching to any new screen
        resetScreenWithoutAudio();

        currentScreen = ScreensNames.LoadScreen;

        // Creating an instance of the new screen
        currentGameState.setGameScreen(
                new Screen(
                        ScreensNames.LoadScreen.toString(),
                        this.currentGameState.getCurrentApp().getCurrentScreenSize(),
                        currentGameState.getGameWorld(),
                        false));

        // App -> Application (parent) also saves this value
        this.currentGameState.getCurrentApp().setCurrentScreenId(ScreensNames.LoadScreen.toString());
        this.currentGameState.getCurrentApp().setCurrentScreen(currentGameState.getGameScreen());

        // Setting up the blueprint
        this.currentGameState.setBluePrint(new GameWorldBluePrint(currentGameState));

        // Invoking blueprint function to populate this particular screen
        this.currentGameState.getBluePrint().populateLoadScreen();

        // Resetting the time counter
        currentGameState.setMicroSecondPassedLastTick(0);
    }


    public void switchToFirstLevelScreen() {
        // This is necessary before switching to any new screen
        resetScreen();
        currentScreen = ScreensNames.LevelOne;

        /**
         * I am registering this audio clip in gamestate because it needs to stay alive for the entire time
         * Otherwise it gets killed when the function finish execution and goes out of scope
         */
        AudioComponent audioClip = new AudioComponent("triage/audiofiles/Dramatic-suspense-background-music.mp3", true);
        audioClip.setLocalId(AudioId.BACKGROUND_STAGE1.toString());
        // audioClip.playAudio();
        currentGameState.addAudio(audioClip);

        // Creating an instance of the new screen
        currentGameState.setGameScreen(
                new Screen(
                        ScreensNames.LevelOne.toString(),
                        this.currentGameState.getCurrentApp().getCurrentScreenSize(),
                        currentGameState.getGameWorld(),
                        false));

        // App -> Application (parent) also saves this value
        this.currentGameState.getCurrentApp().setCurrentScreenId(ScreensNames.LevelOne.toString());
        this.currentGameState.getCurrentApp().setCurrentScreen(currentGameState.getGameScreen());

        // Setting up the blueprint
        this.currentGameState.setBluePrint(new GameWorldBluePrint(currentGameState));

        // Invoking blueprint function to populate this particular screen
        this.currentGameState.getBluePrint().populateFirstLevelScreen();

        // Resetting the time counter
        currentGameState.setMicroSecondPassedLastTick(0);

    }

    public void switchToSecondLevelScreen() {

    }

    public void resetScreen() {
        currentGameState.setGameWorld(new GameWorld());
        currentGameState.removeAllAudio();
    }
    public void resetScreenWithoutAudio() {
        currentGameState.setGameWorld(new GameWorld());
    }

    public void reloadLevel() {
        if(currentScreen == ScreensNames.LevelOne) {
            switchToFirstLevelScreen();
        }
        if(currentScreen == ScreensNames.LevelTwo) {
            switchToSecondLevelScreen();
        }
    }

    public ScreensNames getCurrentScreen() {
        return currentScreen;
    }
}
