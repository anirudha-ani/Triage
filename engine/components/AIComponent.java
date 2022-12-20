package engine.components;

import engine.ai.Composite;
import engine.gameobjects.GameObject;


/**
 * This component itself doesn't do anything
 * You need to create a composite AI behaviour tree and
 * assign it to this to make it work
 */
public class AIComponent extends Component {
    private Composite ai = null;

    public AIComponent(GameObject gameObject) {
        super("ai", gameObject);
    }

    public AIComponent() {
        super("ai");
    }

    public Composite getAi() {
        return ai;
    }

    public void setAi(Composite ai) {
        this.ai = ai;
    }

    @Override
    public void onTick(long nanoSinceLastTick) {
        if(ai == null) {
            return;
        }
        ai.update(nanoSinceLastTick);
    }
}
