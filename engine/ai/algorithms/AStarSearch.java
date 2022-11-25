package engine.ai.algorithms;

import engine.support.Vec2d;

import java.util.*;

public class AStarSearch {

    char[][] mapLayout;

    public AStarSearch(char[][] mapLayout) {
        this.mapLayout = mapLayout;
    }

    boolean isAccessible(int row, int column) {
        return (row >= 0 && row < mapLayout.length && column >= 0 && column < mapLayout[0].length && mapLayout[row][column] != '#');

    }


    public List<Node> printPath(Node target) {
        List<Node> path = new ArrayList<Node>();


        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }


        Collections.reverse(path);

        return path;
    }

    public Node AstarSearch(Node source, Node goal) {
        // System.out.println("Astar start");

        boolean[][] explored = new boolean[this.mapLayout.length][this.mapLayout[0].length];
        for (int i = 0; i < explored.length; i++) {
            for (int j = 0; j < explored[0].length; j++) {
                explored[i][j] = mapLayout[i][j] == '#';

            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
                new Comparator<Node>() {
                    //override compare method
                    public int compare(Node i, Node j) {

                        if (i.distance + i.positionOnMap.dist(goal.positionOnMap) > j.distance + j.positionOnMap.dist(goal.positionOnMap)) {
                            return 1;
                        } else if (i.distance + i.positionOnMap.dist(goal.positionOnMap) < j.distance + j.positionOnMap.dist(goal.positionOnMap)) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }

                }
        );

        //cost from start
        source.distance = 0;

        queue.add(source);
        explored[(int) source.positionOnMap.y][(int) source.positionOnMap.x] = true;

        boolean found = false;

        while ((!queue.isEmpty()) && (!found)) {
            Node current = queue.poll();

            if ((int) current.positionOnMap.x == (int) goal.positionOnMap.x && (int) current.positionOnMap.y == (int) goal.positionOnMap.y) {
                found = true;
                return current;
            }

            List<Node> adjacentNodes = new ArrayList<>();

            if (isAccessible((int) current.positionOnMap.y - 1, (int) current.positionOnMap.x)
                    && !explored[(int) current.positionOnMap.y - 1][(int) current.positionOnMap.x]) {
                explored[(int) current.positionOnMap.y - 1][(int) current.positionOnMap.x] = true;
                Node node = new Node(new Vec2d(current.positionOnMap.x, current.positionOnMap.y - 1));
                adjacentNodes.add(node);
            }
            if (isAccessible((int) current.positionOnMap.y, (int) current.positionOnMap.x - 1)
                    && !explored[(int) current.positionOnMap.y][(int) current.positionOnMap.x - 1]) {
                explored[(int) current.positionOnMap.y][(int) current.positionOnMap.x - 1] = true;
                Node node = new Node(new Vec2d(current.positionOnMap.x - 1, current.positionOnMap.y));
                adjacentNodes.add(node);
            }
            if (isAccessible((int) current.positionOnMap.y + 1, (int) current.positionOnMap.x) && !explored[(int) current.positionOnMap.y + 1][(int) current.positionOnMap.x]) {
                explored[(int) current.positionOnMap.y + 1][(int) current.positionOnMap.x] = true;
                Node node = new Node(new Vec2d(current.positionOnMap.x, current.positionOnMap.y + 1));
                adjacentNodes.add(node);
            }
            if (isAccessible((int) current.positionOnMap.y, (int) current.positionOnMap.x + 1)
                    && !explored[(int) current.positionOnMap.y][(int) current.positionOnMap.x + 1]) {
                explored[(int) current.positionOnMap.y][(int) current.positionOnMap.x + 1] = true;
                Node node = new Node(new Vec2d(current.positionOnMap.x + 1, current.positionOnMap.y));
                adjacentNodes.add(node);
            }

            for (int i = 0; i < adjacentNodes.size(); i++) {
                Node node = adjacentNodes.get(i);
                node.distance = current.distance + 1;
                node.parent = current;

                queue.add(node);

            }
        }
        return null;
    }


}

