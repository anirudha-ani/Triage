package triage.intelligence;

import engine.ai.Sequence;
import engine.gameobjects.GameObject;

import java.util.ArrayList;

public class GroundSentryAI extends Sequence {

    public GroundSentryAI(GameObject enemy, ArrayList <GameObject> platformObjects) {
        ChangeDirectionAction changeDirectionAction = new ChangeDirectionAction(enemy, platformObjects);

        this.addBehavior(changeDirectionAction);
    }
}
