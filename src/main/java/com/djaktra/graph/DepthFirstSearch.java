package com.djaktra.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Implementación recursiva de la búsqueda en profundidad (DFS). */
public final class DepthFirstSearch {

    private DepthFirstSearch() {
    }

    public static List<String> traverse(WeightedGraph graph, String source) {
        validateSource(graph, source);

        List<String> traversal = new ArrayList<>();
        visit(graph, source, new HashSet<>(), traversal);
        return List.copyOf(traversal);
    }

    public static List<String> findPath(
            WeightedGraph graph, String source, String destination) {
        validateSource(graph, source);
        if (!graph.contains(destination)) {
            throw new IllegalArgumentException(
                    "El vértice de destino no existe: " + destination);
        }

        List<String> path = new ArrayList<>();
        boolean found = searchPath(
                graph, source, destination, new HashSet<>(), path);
        return found ? List.copyOf(path) : List.of();
    }

    private static void visit(WeightedGraph graph, String vertex,
            Set<String> visited, List<String> traversal) {
        visited.add(vertex);
        traversal.add(vertex);

        for (WeightedGraph.Edge edge : graph.neighborsOf(vertex)) {
            if (!visited.contains(edge.destination())) {
                visit(graph, edge.destination(), visited, traversal);
            }
        }
    }

    private static boolean searchPath(WeightedGraph graph, String current,
            String destination, Set<String> visited, List<String> path) {
        visited.add(current);
        path.add(current);

        if (current.equals(destination)) {
            return true;
        }

        for (WeightedGraph.Edge edge : graph.neighborsOf(current)) {
            if (!visited.contains(edge.destination())
                    && searchPath(graph, edge.destination(), destination,
                            visited, path)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    private static void validateSource(WeightedGraph graph, String source) {
        if (!graph.contains(source)) {
            throw new IllegalArgumentException(
                    "El vértice inicial no existe: " + source);
        }
    }
}
