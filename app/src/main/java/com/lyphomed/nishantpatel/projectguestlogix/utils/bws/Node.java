package com.lyphomed.nishantpatel.projectguestlogix.utils.bws;

import java.util.LinkedList;
import java.util.List;

/**
 * Node object containing IATA3 codes of airport as nodes
 * and all the adjacent nodes to make edges
 */
public class Node {
    private String nodeLabel;
    private List<Node> adjacentNodeList;

    public Node(String label) {
        nodeLabel = label;
        adjacentNodeList = new LinkedList<>();
    }

    public void addAdjacent(Node node) {
        adjacentNodeList.add(node);
    }

    public String getNodeLabel() {
        return nodeLabel;
    }

    public List<Node> getAdjacentNodeList() {
        return adjacentNodeList;
    }
}
