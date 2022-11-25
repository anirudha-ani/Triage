package engine.ai.algorithms;

import engine.support.Vec2d;

public class Node {

    public Vec2d positionOnMap;
    public double distance;
    public Node parent;

    public Node(Vec2d positionOnMap) {
        this.positionOnMap = positionOnMap;
    }
}
