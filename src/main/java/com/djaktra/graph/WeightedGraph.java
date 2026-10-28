package com.djaktra.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Grafo dirigido y ponderado representado mediante listas de adyacencia. */
public final class WeightedGraph {

    private final Map<String, List<Edge>> adjacency = new HashMap<>();

    public void addVertex(String vertex) {
        adjacency.computeIfAbsent(vertex, ignored -> new ArrayList<>());
    }

    public void addEdge(String origin, String destination, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Dijkstra no admite pesos negativos");
        }

        addVertex(origin);
        addVertex(destination);
        adjacency.get(origin).add(new Edge(destination, weight));
    }

    public void addUndirectedEdge(String first, String second, int weight) {
        addEdge(first, second, weight);
        addEdge(second, first, weight);
    }

    public List<Edge> neighborsOf(String vertex) {
        return Collections.unmodifiableList(
                adjacency.getOrDefault(vertex, Collections.emptyList()));
    }

    public boolean contains(String vertex) {
        return adjacency.containsKey(vertex);
    }

    public record Edge(String destination, int weight) {
    }
}
