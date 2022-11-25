package engine.ai;

public interface BehaviorTreeNode {
    Status update(long seconds);

    void reset();
}
