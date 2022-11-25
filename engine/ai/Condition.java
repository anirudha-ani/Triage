package engine.ai;

public abstract class Condition implements BehaviorTreeNode {

    public long microSecondSinceLastExecution = 0;
    Status status;

    public void start() {
        this.status = Status.RUNNING;
    }

    @Override
    public void reset() {
        this.status = null;
    }

    public abstract Status checkCondition();

    @Override
    public Status update(long nanoSecondsSinceTick) {
        microSecondSinceLastExecution += nanoSecondsSinceTick / 1000;
        return checkCondition();
    }
}
