package engine.components;

import engine.gameobjects.GameObject;

public class ClickableComponent extends Component {
    public ClickableComponent(GameObject gameObject) {
        super("clickable", gameObject);
    }
}