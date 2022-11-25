package engine.systems;

import engine.GameWorld;

public abstract class System {
    private final GameWorld refGameWorld;

    public System(GameWorld gameWorld) {
        this.refGameWorld = gameWorld;
    }

    public GameWorld getRefGameWorld() {
        return refGameWorld;
    }
}