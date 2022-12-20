package engine.ai;

public interface BehaviorTreeNode {
    Status update(long nanoSecondSinceLastTick);

    void reset();
}
