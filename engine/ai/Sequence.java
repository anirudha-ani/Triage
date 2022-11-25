package engine.ai;

import java.util.ArrayList;

public class Sequence extends Composite {
    public long microSecondSinceLastExectution = 0;
    Status status;

    @Override
    public Status update(long seconds) {

        if (status == null) {
            lastRunning = null;
            status = Status.RUNNING;
            for (int i = 0; i < children.size(); i++) {
                BehaviorTreeNode node = children.get(i);

                lastRunning = node;
                Status childstatus = node.update(seconds);

                if (childstatus.equals(Status.FAIL)) {
                    lastRunning = null;
                    status = null;
                    return Status.FAIL;
                }
            }
            lastRunning = null;
            status = null;
            return Status.SUCCESS;
        } else {
            return status;
        }
    }

    @Override
    public void reset() {
        children = new ArrayList<>();
        lastRunning = null;
    }

}
