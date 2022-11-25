package engine.ai;

import java.util.ArrayList;

public abstract class Composite implements BehaviorTreeNode {

    ArrayList<BehaviorTreeNode> children = new ArrayList<>();
    BehaviorTreeNode lastRunning = null;

    public abstract Status update(long seconds);

    public abstract void reset();

    public void addBehavior(BehaviorTreeNode behavior) {
        this.children.add(behavior);
    }
}
