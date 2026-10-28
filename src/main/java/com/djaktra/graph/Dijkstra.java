package com.djaktra.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/** Calcula la ruta de menor costo desde un vértice inicial. */
public final class Dijkstra {

    private static final int INFINITY = Integer.MAX_VALUE;

    private Dijkstra() {
    }

    public static Result shortestPaths(WeightedGraph graph, String source) {
        if (!graph.contains(source)) {
            throw new IllegalArgumentException("El vértice inicial no existe: " + source);
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<NodeDistance> pending = new PriorityQueue<>();

        distances.put(source, 0);
        pending.add(new NodeDistance(source, 0));

        while (!pending.isEmpty()) {
            NodeDistance current = pending.poll();
            int bestKnownDistance = distances.getOrDefault(current.vertex(), INFINITY);

            // Descarta entradas antiguas de la cola de prioridad.
            if (current.distance() != bestKnownDistance) {
                continue;
            }

            for (WeightedGraph.Edge edge : graph.neighborsOf(current.vertex())) {
                int candidateDistance = Math.addExact(current.distance(), edge.weight());
                int destinationDistance = distances.getOrDefault(
                        edge.destination(), INFINITY);

                // Relajación de la arista.
                if (candidateDistance < destinationDistance) {
                    distances.put(edge.destination(), candidateDistance);
                    previous.put(edge.destination(), current.vertex());
                    pending.add(new NodeDistance(edge.destination(), candidateDistance));
                }
            }
        }

        return new Result(source, distances, previous);
    }

    private record NodeDistance(String vertex, int distance)
            implements Comparable<NodeDistance> {

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(distance, other.distance);
        }
    }

    public record Result(String source, Map<String, Integer> distances,
            Map<String, String> previous) {

        public Result {
            distances = Map.copyOf(distances);
            previous = Map.copyOf(previous);
        }

        public boolean isReachable(String destination) {
            return distances.containsKey(destination);
        }

        public int distanceTo(String destination) {
            return distances.getOrDefault(destination, INFINITY);
        }

        public List<String> pathTo(String destination) {
            if (!isReachable(destination)) {
                return List.of();
            }

            List<String> path = new ArrayList<>();
            String current = destination;
            while (current != null) {
                path.add(current);
                current = previous.get(current);
            }

            Collections.reverse(path);
            return List.copyOf(path);
        }
    }
}
