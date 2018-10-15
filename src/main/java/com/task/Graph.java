package com.task;

import java.util.*;

/**
 * Immutable directed graph with single purpose - detect a dependency cycle.
 */
public class Graph {
    private enum Marker { NON_VISITED, VISITING, VISITED }
    private final Map<Integer, List<Integer>> vertices;
    private final Map<Integer, Marker> markers;
    private Boolean isCyclic;

    public Graph(Map<Integer, List<Integer>> vertices, Set<Integer> nodes) {
        this.vertices = vertices;
        this.markers = new HashMap<>();
        for (var node : nodes) {
            markers.put(node, Marker.NON_VISITED);
        }
    }

    /**
     * Returns whether this graph has a cycle. Since graph is immutable, may cache the result.
     */
    public boolean findCycle() {
        if (isCyclic != null) {
            return isCyclic;
        }
        for (var node : markers.keySet()) {
            if (markers.get(node) == Marker.NON_VISITED) {
                if (dfs(node)) {
                    isCyclic = true;
                    return true;
                }
            }
        }
        isCyclic = false;
        return false;
    }

    private boolean dfs(int node) {
        this.markers.put(node, Marker.VISITING);
        var outgoing = this.vertices.get(node);
        if (outgoing != null) {
            for (Integer neighbor : outgoing) {
                var marker = markers.get(neighbor);
                if (marker == Marker.VISITING || (marker == Marker.NON_VISITED && dfs(neighbor))) {
                    return true;
                }
            }
        }
        markers.put(node, Marker.VISITED);
        return false;
    }
}
