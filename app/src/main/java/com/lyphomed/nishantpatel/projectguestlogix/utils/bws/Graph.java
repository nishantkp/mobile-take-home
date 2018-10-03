package com.lyphomed.nishantpatel.projectguestlogix.utils.bws;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private HashMap<String, Node> nodeLookUp = new HashMap<>();

    public void setNodeLookUp(HashMap<String, Node> nodeLookUp) {
        this.nodeLookUp = nodeLookUp;
    }

    public static class Node {
        private String id;
        LinkedList<Node> adjacent = new LinkedList<>();

        public Node(String id) {
            this.id = id;
        }
    }

    private Node getNode(String id) {
        return nodeLookUp.get(id);
    }

    public void addEdge(String origin, String destination) {
        Node o = getNode(origin);
        Node d = getNode(destination);
        o.adjacent.add(d);
    }

    public boolean hasPathBFS(Node origin, Node destination) {
        Queue<Node> nextToVisit = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        nextToVisit.add(origin);

        while (!nextToVisit.isEmpty()) {
            Node node = nextToVisit.remove();
            if (node == destination) {
                return true;
            }
            if (visited.contains(node.id)) {
                continue;
            }
            visited.add(node.id);

            nextToVisit.addAll(node.adjacent);
        }
        return false;
    }
}
