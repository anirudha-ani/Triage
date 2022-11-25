package engine.components;

import engine.ai.Composite;
import engine.gameobjects.GameObject;

public class AIComponent extends Component {
    private Composite ai;

    public AIComponent(GameObject gameObject) {
        super("ai", gameObject);
    }

    public Composite getAi() {
        return ai;
    }

    public void setAi(Composite ai) {
        this.ai = ai;
    }
}
