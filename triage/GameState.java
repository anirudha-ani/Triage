package triage;

import engine.GameWorld;
import engine.Screen;
import engine.components.AudioComponent;
import engine.components.VideoComponent;
import engine.resources.FileLoader;
import engine.resources.MapLoader;
import engine.support.Vec2d;
import engine.systems.KeyEventHappened;
import triage.blueprints.AudioId;
import triage.blueprints.GameAssets;
import triage.blueprints.GameWorldBluePrint;

import java.util.ArrayList;

/**
 * I have introducing this object to hold all the necessary
 * values for the state in a single object
 * Feel free to add or remove things
 */
public class GameState {
    private GameWorld gameWorld = null;
    private Screen gameScreen = null;

    public boolean isVideoPlaying = false;

    GameWorldBluePrint bluePrint = null;
    private long microSecondPassedLastTick = 0;

    private int coinCount=0;

    private boolean isLevel2Saved;

    private long microSecondPassedLastKeyExecution = 0;
    private boolean firstTickHappened = false;
    private MapLoader map;
    private GameAssets gameAssets;
    private FileLoader saveFile;
    private App currentApp;

    private boolean boughtItem = false;
    private VideoComponent videoPlayer = new VideoComponent();

    ArrayList<AudioComponent> runningAudio = new ArrayList<AudioComponent>();

    public GameState(App app) {
        currentApp = app;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public Screen getGameScreen() {
        return gameScreen;
    }



    public void setGameScreen(Screen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public long getMicroSecondPassedLastTick() {
        return microSecondPassedLastTick;
    }

    public void setMicroSecondPassedLastTick(long secondPassedLastTick) {
        this.microSecondPassedLastTick = secondPassedLastTick;
    }

    public boolean isLevel2Saved() {
        return isLevel2Saved;
    }

    public void setLevel2Saved(boolean level2Saved) {
        isLevel2Saved = level2Saved;
    }

    public void setBoughtItem(boolean boughtItem) {
        this.boughtItem = boughtItem;
    }

    public boolean isBoughtItem() {
        return boughtItem;
    }

    public boolean getFirstTickHappened() {
        return firstTickHappened;
    }

    public void setFirstTickHappened(boolean firstTick) {
        this.firstTickHappened = firstTick;
    }

    public MapLoader getMap() {
        return map;
    }

    public void setMap(MapLoader map) {
        this.map = map;
    }

    public GameAssets getGameAssets() {
        return gameAssets;
    }

    public void setGameAssets(GameAssets gameAssets) {
        this.gameAssets = gameAssets;
    }

    public FileLoader getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(String filePath) {
        this.saveFile = new FileLoader(filePath);
    }

    public Vec2d getScreenSize() {
        return this.currentApp.getCurrentScreenSize();
    }

    public GameWorldBluePrint getBluePrint() {
        return bluePrint;
    }

    public void setBluePrint(GameWorldBluePrint bluePrint) {
        this.bluePrint = bluePrint;
    }

    public App getCurrentApp() {
        return currentApp;
    }

    public void setCurrentApp(App currentApp) {
        this.currentApp = currentApp;
    }

    public long getMicroSecondPassedLastKeyExecution() {
        return microSecondPassedLastKeyExecution;
    }

    public void setMicroSecondPassedLastKeyExecution(long microSecondPassedLastKeyExecution) {
        this.microSecondPassedLastKeyExecution = microSecondPassedLastKeyExecution;
    }
    public void addAudio(AudioComponent audioComponent) {
        runningAudio.add(audioComponent);
    }

    public ArrayList<AudioComponent> getRunningAudio() {
        return runningAudio;
    }

    public void removeAudio(AudioId audioId) {
        runningAudio.removeIf(audioComponent -> (audioComponent.getLocalId() == audioId.toString()));
    }

    public void removeAllAudio() {
        runningAudio.forEach(audioComponent -> {
            audioComponent.stopAudio();
        });
        runningAudio.clear();
    }

    public void playVideo(String source, boolean isLooping) {
        this.isVideoPlaying = true;
        videoPlayer.setSource(source);
        videoPlayer.setLooping(isLooping);
        videoPlayer.playVideo(
                this,
                getCurrentApp().getScene(),
                getCurrentApp().getStage(),
                getCurrentApp().getCurrentScreenSize().x,
                getCurrentApp().getCurrentScreenSize().y);
    }
}
