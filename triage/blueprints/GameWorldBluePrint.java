package triage.blueprints;

import engine.gameobjects.GameObject;
import triage.GameState;
import triage.generators.BackgrounGenerator;

public class GameWorldBluePrint {

    GameState currentGameState;
    public GameWorldBluePrint(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public void populateStartScreen() {
        GameObject backgroundObject = new BackgrounGenerator(this.currentGameState).generate();
        currentGameState.getGameWorld().addGameObject(backgroundObject);
    }
}
