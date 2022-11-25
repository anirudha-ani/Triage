package engine.components;

import engine.gameobjects.GameObject;

public class DraggableComponent extends Component {
    public DraggableComponent(GameObject gameObject) {
        super("draggable", gameObject);
    }
}
