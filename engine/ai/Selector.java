package engine.ai;

import java.util.ArrayList;

public class Selector extends Composite {

    Status status;

    @Override
    public Status update(long seconds) {
        if (status == null) {
            status = Status.RUNNING;
            for (int i = 0; i < children.size(); i++) {
                BehaviorTreeNode node = children.get(i);
                Status childstatus = node.update(seconds);
                if (childstatus.equals(Status.SUCCESS)) {
                    status = null;
                    return Status.SUCCESS;
                }
            }
            status = null;
            return Status.FAIL;
        } else {
            return status;
        }


    }

    @Override
    public void reset() {
        children = new ArrayList<>();
    }
}
