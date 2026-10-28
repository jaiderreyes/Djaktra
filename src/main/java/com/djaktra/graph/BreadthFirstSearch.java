package com.djaktra.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** Implementación de la búsqueda por anchura (BFS) mediante una cola. */
public final class BreadthFirstSearch {

    private BreadthFirstSearch() {
    }

    public static List<String> traverse(WeightedGraph graph, String source) {
        validateVertex(graph, source, "inicial");

        List<String> traversal = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> pending = new ArrayDeque<>();

        visited.add(source);
        pending.add(source);

        while (!pending.isEmpty()) {
            String current = pending.remove();
            traversal.add(current);

            for (WeightedGraph.Edge edge : graph.neighborsOf(current)) {
                if (visited.add(edge.destination())) {
                    pending.add(edge.destination());
                }
            }
        }

        return List.copyOf(traversal);
    }

    public static List<String> findPath(
            WeightedGraph graph, String source, String destination) {
        validateVertex(graph, source, "inicial");
        validateVertex(graph, destination, "de destino");

        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();
        Queue<String> pending = new ArrayDeque<>();

        visited.add(source);
        pending.add(source);

        while (!pending.isEmpty()) {
            String current = pending.remove();
            if (current.equals(destination)) {
                return reconstructPath(previous, destination);
            }

            for (WeightedGraph.Edge edge : graph.neighborsOf(current)) {
                if (visited.add(edge.destination())) {
                    previous.put(edge.destination(), current);
                    pending.add(edge.destination());
                }
            }
        }

        return List.of();
    }

    private static List<String> reconstructPath(
            Map<String, String> previous, String destination) {
        List<String> path = new ArrayList<>();
        String current = destination;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);
        return List.copyOf(path);
    }

    private static void validateVertex(
            WeightedGraph graph, String vertex, String type) {
        if (!graph.contains(vertex)) {
            throw new IllegalArgumentException(
                    "El vértice " + type + " no existe: " + vertex);
        }
    }
}
