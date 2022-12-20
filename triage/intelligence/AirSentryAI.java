package triage.intelligence;

import engine.ai.Sequence;
import engine.gameobjects.GameObject;

import java.util.ArrayList;

public class AirSentryAI extends Sequence {

    public AirSentryAI(GameObject enemy) {
        ChangeDirectionAction changeDirectionAction = new ChangeDirectionAction(enemy);

        this.addBehavior(changeDirectionAction);
    }
}
