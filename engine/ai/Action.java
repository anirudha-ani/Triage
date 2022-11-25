package engine.ai;

public abstract class Action implements BehaviorTreeNode {

    public long microSecondSinceLastExecution = 0;
    Status status;

    public void start() {
        this.status = Status.RUNNING;
    }

    @Override
    public void reset() {
        this.status = null;
    }

    public abstract Status act();

    @Override
    public Status update(long nanoSecondsSinceTick) {

        microSecondSinceLastExecution += nanoSecondsSinceTick / 1000;
        return act();
    }
}
