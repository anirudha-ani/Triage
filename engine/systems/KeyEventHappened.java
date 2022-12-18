package engine.systems;

import engine.gameobjects.GameObject;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Set;

public class KeyEventHappened {
    private ArrayList<GameObject> keyInputReactableObject;
    private Set<KeyCode> activeKeyEvents;
    private KeyCode releasedKey;

    public KeyEventHappened(ArrayList<GameObject> keyInputReactableObject,
                            Set<KeyCode> activeKeyEvents,
                            KeyCode releasedKey) {
        this.keyInputReactableObject = keyInputReactableObject;
        this.activeKeyEvents = activeKeyEvents;
        this.releasedKey = releasedKey;
    }

    public KeyEventHappened(ArrayList<GameObject> keyInputReactableObjectIds,
                            Set<KeyCode> activeKeyEvents) {
        this.keyInputReactableObject = keyInputReactableObjectIds;
        this.activeKeyEvents = activeKeyEvents;
        this.releasedKey = null;
    }

    public KeyEventHappened() {}

    public ArrayList<GameObject> getKeyInputReactableObject() {
        return keyInputReactableObject;
    }

    public void setKeyInputReactableObject(ArrayList<GameObject> keyInputReactableObjectIds) {
        this.keyInputReactableObject = keyInputReactableObjectIds;
    }

    public Set<KeyCode> getActiveKeyEvents() {
        return activeKeyEvents;
    }

    public void setActiveKeyEvents(Set<KeyCode> activeKeyEvents) {
        this.activeKeyEvents = activeKeyEvents;
    }

    public KeyCode getReleasedKey() {
        return releasedKey;
    }

    public void setReleasedKey(KeyCode releasedKey) {
        this.releasedKey = releasedKey;
    }
}
