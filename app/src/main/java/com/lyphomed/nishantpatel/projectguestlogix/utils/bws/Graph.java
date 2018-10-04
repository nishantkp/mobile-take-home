package com.lyphomed.nishantpatel.projectguestlogix.utils.bws;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Class to find the minimum distances between two airport
 * i.e minimum distance is counted as least transfers aka via points
 * <p>
 * Algorithm Implemented : Breadth First Search
 */
public class Graph {
    /**
     * Map to store whole graph containing nodes and edges
     */
    private static Map<String, Node> nodeLookUp = new HashMap<>();
    /**
     * The shortest path between two nodes in a graph.
     */
    private static ArrayList<Node> shortestPath = new ArrayList<>();

    public void setNodeLookUp(Map<String, Node> nodes) {
        nodeLookUp = nodes;
    }

    /**
     * Call this method to find whether there is path exists between two airports or not
     *
     * @param origin      IATA3 of origin airport
     * @param destination IATA3 of destination airport
     * @return true or false depending upon whether path is available or not
     */
    public boolean hasPathBFS(String origin, String destination) {
        Node originNode = nodeLookUp.get(origin);
        Node destinationNode = nodeLookUp.get(destination);
        Queue<Node> nextToVisit = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        nextToVisit.add(originNode);

        while (!nextToVisit.isEmpty()) {
            Node node = nextToVisit.remove();
            if (node.getNodeLabel().equals(destinationNode.getNodeLabel())) {
                return true;
            }
            if (visited.contains(node.getNodeLabel())) {
                continue;
            }
            visited.add(node.getNodeLabel());

            nextToVisit.addAll(node.getAdjacentNodeList());
        }
        return false;
    }

    /**
     * Finds the shortest path between two nodes (source and destination) in a graph.
     *
     * @param source      IATA3 for the source
     * @param destination IATA3 fot the destination
     * @return the shortest path stored as a list of nodes.
     * or null if a path is not found.
     * <p>
     * Requires: source != null, destination != null
     */
    public ArrayList<Node> breadthFirstSearch(String source,
                                              String destination) {
        shortestPath.clear();
        Node sourceNode = nodeLookUp.get(source);
        Node destinationNode = nodeLookUp.get(destination);

        // A list that stores the path.
        ArrayList<Node> path = new ArrayList<>();

        // A queue to store the visited nodes.
        ArrayDeque<Node> queue = new ArrayDeque<>();
        ArrayDeque<Node> visited = new ArrayDeque<>();

        queue.offer(sourceNode);
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            visited.offer(currentNode);

            if (currentNode == null) break;

            List<Node> neighboursList = nodeLookUp.get(currentNode.getNodeLabel()).getAdjacentNodeList();

            for (Node neighbourNode : neighboursList) {
                path.add(neighbourNode);
                path.add(currentNode);

                if (neighbourNode.getNodeLabel().equals(destination)) {
                    return processPath(sourceNode, destinationNode, path);
                } else {
                    if (!visited.contains(neighbourNode)) {
                        queue.offer(neighbourNode);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Adds the nodes involved in the shortest path.
     *
     * @param sourceNode      The source node.
     * @param destinationNode The destination node.
     * @param path            The path that has nodes and their neighbours.
     * @return The shortest path.
     */
    private ArrayList<Node> processPath(Node sourceNode, Node destinationNode,
                                        ArrayList<Node> path) {

        // Finds out where the destination node directly comes from.
        int index = path.indexOf(nodeLookUp.get(destinationNode.getNodeLabel()));
        Node source = path.get(index + 1);

        // Adds the destination node to the shortestPath.
        shortestPath.add(0, nodeLookUp.get(destinationNode.getNodeLabel()));

        if (source.getNodeLabel().equals(sourceNode.getNodeLabel())) {
            // The original source node is found.
            shortestPath.add(0, nodeLookUp.get(sourceNode.getNodeLabel()));
            return shortestPath;
        } else {
            // We find where the source node of the destination node
            // comes from.
            // We then set the source node to be the destination node.
            return processPath(sourceNode, source, path);
        }
    }
}
